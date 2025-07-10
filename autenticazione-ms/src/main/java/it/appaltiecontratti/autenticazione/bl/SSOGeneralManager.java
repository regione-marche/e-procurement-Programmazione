package it.appaltiecontratti.autenticazione.bl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import it.appaltiecontratti.autenticazione.Constants;
import it.appaltiecontratti.autenticazione.entity.*;
import it.appaltiecontratti.autenticazione.mapper.AccountMapper;
import it.appaltiecontratti.autenticazione.soap.SoapClientConnector;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.WConfigDTO;
import it.cedaf.authservice.service.*;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("java:S5542")
@Component(value = "ssoGeneralManager")
public class SSOGeneralManager extends BaseService {

    private static Logger logger = LogManager.getLogger(SSOGeneralManager.class);

    private static final String SPID_AUTHLEVEL_URL = "https://www.spid.gov.it/Spid";

    private static final String CHIAVE_REGISTRAZIONE_ATTIVA = "it.eldasoft.registrazione.attivaForm";

    @Value("${sso.token.expireTime}")
    private int tokenExpTime;

    @Value("${sso.refreshToken.expireTime}")
    private int refreshTokenExpTime;

    @Value("${sso.standardSigningKey}")
    private String standardSigningKey;

    @Value("${sso.standardSigningKeyEncrypted:#{false}}")
    private boolean standardSigningKeyEncrypted;

    @Value("${sso.spid.endpoint}")
    private String spidUrl;

    @Value("${sso.spid.spidServiceIndex}")
    private String spidServiceIndex;

    @Value("${sso.spid.authlevel}")
    private String spidAuthLevel;

    @Value("${sso.cohesion.key}")
    private String cohesionEncryptedKey;

    @Value("${auth.sso.cohesion.mapping.nome}")
    private String attributoNome;

    @Value("${auth.sso.cohesion.mapping.cognome}")
    private String attributoCognome;

    @Value("${auth.sso.cohesion.mapping.cf}")
    private String attributoLoginCodiceFiscale;

    @Value("${auth.sso.cohesion.mapping.email}")
    private String attributoEmail;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserManager userManager;

    @Autowired
    private SoapClientConnector soapClientConnector;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String authenticateUser(final Map<String, String> headers, final String authType) throws Exception {
        logger.info("authenticateUser: Inizio Metodo");
        SSOUserInfo userInfo = loadSSOConfigs(headers, authType);

        if (userInfo.getLogin() == null) {
            return null;
        }

        try {
            return this.generateWSsoJwtToken(userInfo);
        } catch (Exception e) {
            logger.error("Non e' stato possibile autenticare l'utente", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String authenticateUserStandard(final String responseJwtToken) throws Exception {
        Jws<Claims> jws;

        try {

            byte[] key;

            if (standardSigningKeyEncrypted)
                key = super.decodeKey(standardSigningKey);
            else
                key = standardSigningKey.getBytes();

            SecretKey secretKey = Keys.hmacShaKeyFor(key);
            jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(responseJwtToken);

            SSOUserInfo userInfo = new SSOUserInfo();
            userInfo.setCodiceFiscale(jws.getPayload().get("codiceFiscale", String.class));
            userInfo.setLogin(jws.getPayload().get("codiceFiscale", String.class));
            userInfo.setNome(jws.getPayload().get("nome", String.class));
            userInfo.setCognome(jws.getPayload().get("cognome", String.class));
            userInfo.setEmail(jws.getPayload().get("email", String.class));
            if (jws.getPayload().containsKey("userIdpType"))
                userInfo.setProvider(jws.getPayload().get("userIdpType", String.class));
            if (jws.getPayload().containsKey("userLoa"))
                userInfo.setLevelOfAccess(jws.getPayload().get("userLoa", String.class));

            if (StringUtils.isNotBlank(userInfo.getLogin()))
                userInfo.setLogin(userInfo.getLogin().toUpperCase());

            if (StringUtils.isNotBlank(userInfo.getCodiceFiscale()))
                userInfo.setCodiceFiscale(userInfo.getCodiceFiscale().toUpperCase());

            userInfo = elaborateSSOUserInfo(userInfo);

            handleLoaStandard(userInfo);

            try {
                return this.generateWSsoJwtToken(userInfo);
            } catch (Exception e) {
                logger.error("Non e' stato possibile autenticare l'utente", e);
                throw e;
            }

        } catch (JwtException ex) {
            logger.error("JwtException", ex);
            throw ex;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ParametricResponseResult<SSOTokenInfo> retrieveAuthenticationToken(final String authorizationCode) {

        logger.debug("Execution start SSOGeneralManager::retrieveAuthenticationToken for authorizationCode [ {} ]",
                authorizationCode);

        ParametricResponseResult<SSOTokenInfo> response = new ParametricResponseResult<>();

        try {

            WSsoJwtToken wSsoJwtToken = accountMapper.findWSsoJwtTokenByAuthorizationCode(authorizationCode);

            if (wSsoJwtToken == null) {
                response.setEsito(false);
                response.setErrorData(ParametricResponseResult.ERROR_NOT_FOUND);
                return response;
            }

            // VIGILANZA2-480: rimossa delete dalla tabella w_ssojwttoken. La rimozione avverrà al logout e al login
//            accountMapper.deleteWSsoJwtTokenBySyslogin(wSsoJwtToken.getSyslogin().toUpperCase());

            Date now = new Date();
            Date expDate = super.getExpDate(wSsoJwtToken.getDtCreazione().getTime(), tokenExpTime);

            if (expDate.before(now)) {
                response.setEsito(false);
                response.setErrorData(ParametricResponseResult.ERROR_NOT_FOUND);
                return response;
            }
            SSOTokenInfo ssoTokenInfo = new SSOTokenInfo();

            ssoTokenInfo.setCreatedAt(wSsoJwtToken.getDtCreazione());
            ssoTokenInfo.setJwtToken(wSsoJwtToken.getJwtToken());
            ssoTokenInfo.setRefreshToken(wSsoJwtToken.getRefreshToken());
            response.setEsito(true);
            response.setData(ssoTokenInfo);

        } catch (Exception e) {
            logger.error("Exception", e);
            response.setEsito(false);
            response.setErrorData(ParametricResponseResult.ERROR_UNEXPECTED);
        }

        return response;
    }

    private SSOUserInfo mapAuthDataToSSOUserInfo(AuthData userInfo, final String codiceFiscale, String provider) {
        SSOUserInfo user = new SSOUserInfo();
        user.setCodiceFiscale(codiceFiscale);
        user.setCognome(userInfo.getCognome());
        user.setEmail(userInfo.getNome());
        user.setLogin(codiceFiscale);
        user.setNome(userInfo.getNome());
        user.setNomeCompleto(userInfo.getNome() + " " + userInfo.getCognome());
        String livelloAccessoDesc = userInfo.getLivelloAutenticazione();
        String livelloAccesso = "1";
        if (livelloAccessoDesc != null) {
            livelloAccesso = mapAuthLevel(livelloAccessoDesc);
        }
        user.setLevelOfAccess(livelloAccesso);
        user.setProvider(provider);
        return user;
    }

    private String mapAuthLevel(String livelloAccessoDesc) {
        switch (livelloAccessoDesc) {
            case "https://www.spid.gov.it/SpidL1":
                return "2";
            case "https://www.spid.gov.it/SpidL2":
                return "3";
            case "https://www.spid.gov.it/SpidL3":
                return "4";
            default:
                return "1";
        }

    }

    private SSOUserInfo loadSSOConfigs(final Map<String, String> headers, final String authType) {
        // trasformo tutte le chiavi degli headers in uppercase
        Map<String, String> headersModificati = headersUppercase(headers);
        Map<String, String> headersFinali = null;
        String[] configurazioni = null;
        if (StringUtils.isBlank(authType)) {
            // Load Standards
            configurazioni = new String[]{
                    Constants.SSO_KEY_CODICE_FISCALE,
                    Constants.SSO_KEY_NOME,
                    Constants.SSO_KEY_COGNOME,
                    Constants.SSO_KEY_NOME_COMPLETO,
                    Constants.SSO_KEY_LOGIN,
                    Constants.SSO_KEY_EMAIL,
            };
        } else {
            // Load Specific
            configurazioni = new String[]{
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_CODICE_FISCALE, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_NOME, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_COGNOME, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_NOME_COMPLETO, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOGIN, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_EMAIL, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOA, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOA_ENCODING, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_IDP_TYPE, authType),
                    MessageFormat.format(Constants.SSO_SPECIFIC_KEY_IDP_TYPE_ENCODING, authType)
            };
        }

        headersFinali = Arrays.stream(configurazioni).map(
                        c -> wConfigManager.getConfiguration(c)
                ).filter(Objects::nonNull)
                .filter(c -> StringUtils.isNotBlank(c.getValore()))
                .collect(Collectors.toMap(
                        WConfigDTO::getChiave,
                        c -> {
                            String valueUpperCase = c.getValore().toUpperCase();
                            if (c.getChiave().equals(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOA_ENCODING, authType)) ||
                                    c.getChiave().equals(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_IDP_TYPE_ENCODING, authType))
                            ) {
                                return c.getValore();
                            } else if (headersModificati.containsKey(valueUpperCase)) {
                                return headersModificati.get(valueUpperCase);
                            }
                            return "";
                        }
                ));

        return getUserInfoFromHeaders(headersFinali, authType);
    }

    private SSOUserInfo getUserInfoFromHeaders(final Map<String, String> headers, final String authType) {
        SSOUserInfo userInfo = new SSOUserInfo();

        if (StringUtils.isNotBlank(authType)) {
            // recupero codice fiscale
            userInfo.setCodiceFiscale(
                    headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_CODICE_FISCALE, authType)));
            // recupero nome
            userInfo.setNome(headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_NOME, authType)));
            // recupero congome
            userInfo.setCognome(headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_COGNOME, authType)));
            // recupero nome completo
            userInfo.setNomeCompleto(
                    headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_NOME_COMPLETO, authType)));
            // recupero usename di login
            userInfo.setLogin(headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOGIN, authType)));
            // recupero email
            userInfo.setEmail(headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_EMAIL, authType)));
            // recupero provider
            userInfo.setProvider(headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_IDP_TYPE, authType)));
            // recupero LOA
            userInfo.setLevelOfAccess(headers.get(MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOA, authType)));

            String loaEncodingKey = MessageFormat.format(Constants.SSO_SPECIFIC_KEY_LOA_ENCODING, authType);
            if (headers.containsKey(loaEncodingKey)) {
                String loAEncoding = headers.get(loaEncodingKey);
                logger.debug("Found loa encoding {}", loAEncoding);

                // Caso LoA ed encoding valorizzato
                if (StringUtils.isNotBlank(userInfo.getLevelOfAccess()) && StringUtils.isNotBlank(loAEncoding)) {
                    // mappo la lista valoresorgente1|loanumericodestinazione1;valoresorgente2|loanumericodestinazione2 in una mappa
                    Map<String, Integer> mappa = Arrays.stream(
                                    loAEncoding.split(";")).map(next -> next.split("\\|"))
                            .collect(Collectors.toMap(entry -> entry[0], entry -> Integer.parseInt(entry[1])));
                    Integer loa = mappa.get(userInfo.getLevelOfAccess());
                    userInfo.setLevelOfAccess(String.valueOf(loa));
                } else if (StringUtils.isBlank(userInfo.getLevelOfAccess()) && StringUtils.isNotBlank(loAEncoding)) {
                    // Caso solo Encoding valorizzato, prendo tutto il contenuto di encoding
                    userInfo.setLevelOfAccess(loAEncoding);
                } else if (StringUtils.isBlank(userInfo.getLevelOfAccess()) && StringUtils.isBlank(loAEncoding)) {
                    userInfo.setLevelOfAccess(Constants.DEFAULT_LOA);
                }
            }

            String idpTypeEncodingKey = MessageFormat.format(Constants.SSO_SPECIFIC_KEY_IDP_TYPE_ENCODING, authType);
            if (headers.containsKey(idpTypeEncodingKey)) {
                String idpTypeEncoding = headers.get(idpTypeEncodingKey);
                logger.debug("Found idp type encoding {}", idpTypeEncoding);

                // Caso IDP TYPE ed encoding valorizzato
                if (StringUtils.isNotBlank(userInfo.getProvider()) && StringUtils.isNotBlank(idpTypeEncoding)) {
                    // mappo la lista valoresorgente1|loanumericodestinazione1;valoresorgente2|loanumericodestinazione2 in una mappa
                    Map<String, String> mappa = Arrays.stream(
                                    idpTypeEncoding.split(";")).map(next -> next.split("\\|"))
                            .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
                    String idpType = mappa.get(userInfo.getProvider());
                    userInfo.setProvider(String.valueOf(idpType));
                } else if (StringUtils.isBlank(userInfo.getProvider()) && StringUtils.isNotBlank(idpTypeEncoding)) {
                    // Caso solo Encoding valorizzato, prendo tutto il contenuto di encoding
                    userInfo.setProvider(idpTypeEncoding);
                } else if (StringUtils.isBlank(userInfo.getProvider()) && StringUtils.isBlank(idpTypeEncoding)) {
                    userInfo.setProvider(Constants.DEFAULT_IDP_TYPE);
                }
            }

            userInfo = elaborateSSOUserInfo(userInfo);

        } else {
            // recupero codice fiscale
            userInfo.setCodiceFiscale(headers.get(Constants.SSO_KEY_CODICE_FISCALE));
            // recupero nome
            userInfo.setNome(headers.get(Constants.SSO_KEY_NOME));
            // recupero congome
            userInfo.setCognome(headers.get(Constants.SSO_KEY_COGNOME));
            // recupero nome completo
            userInfo.setNomeCompleto(headers.get(Constants.SSO_KEY_NOME_COMPLETO));
            // recupero usename di login
            userInfo.setLogin(headers.get(Constants.SSO_KEY_LOGIN));
            // recupero email
            userInfo.setEmail(headers.get(Constants.SSO_KEY_EMAIL));
        }

        if (userInfo.getCodiceFiscale() == null) {
            userInfo.setCodiceFiscale(userInfo.getLogin());
        }

        handleLoaStandard(userInfo);

        logger.debug("User Info {}", userInfo);

        return userInfo;
    }

    private String getInternalToken(SSOUserInfo userInfo, boolean refresh) throws CriptazioneException {
        return getInternalToken(userInfo, refresh, null);
    }

    private String getInternalToken(SSOUserInfo userInfo, boolean refresh, final SingleSignOutResponse singleSignOutResponse) throws CriptazioneException {

        String issuer = null;
        try {
            issuer = InetAddress.getLocalHost().getHostAddress() + "/ssoAuthentication/executeAuthentication";
        } catch (UnknownHostException e) {
            logger.error("executeAuthentication: metodo creazione token. Impossibile trovare HostName");
            issuer = "UnknownHost";
        }
        byte[] jwtKey = userManager.getJWTKey();
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);
        long nowMillis = System.currentTimeMillis();
        long tokenLife = refresh ? refreshTokenExpTime : tokenExpTime;
        JwtBuilder builder = Jwts.builder() //
                .issuedAt(new Date(nowMillis)) //
                .subject(userInfo.getCodiceFiscale()) //
                .issuer(issuer) //
                .audience().add(userInfo.getCodiceFiscale()).and() //
                .signWith(secretKey, getSignatureAlgorithm("HS512")) //
                .expiration(getExpDate(nowMillis, tokenLife)) //
                .claim("USER_LOGIN", userInfo.getLogin()) //
                .claim("USER_CF", userInfo.getCodiceFiscale()) //
                .claim("USER_NAME", userInfo.getNome()) //
                .claim("USER_SURNAME", userInfo.getCognome()) //
                .claim("USER_DESCRIPTION", userInfo.getNomeCompleto()) //
                .claim("USER_EMAIL", userInfo.getEmail()) //
                .claim("USER_LOA", userInfo.getLevelOfAccess())
                .claim("USER_IDP_TYPE", userInfo.getProvider())
                .claim("internal", false);

        if (singleSignOutResponse != null && StringUtils.isNotBlank(singleSignOutResponse.getSingleSignOutReturn())) {
            builder = builder.claim("SSO_LOGOUT_URL", singleSignOutResponse.getSingleSignOutReturn());
        }

        return builder.compact();
    }

    protected MacAlgorithm getSignatureAlgorithm(final String signatureAlgorithm) {
        if (StringUtils.isNotBlank(signatureAlgorithm) && signatureAlgorithmAllowed(signatureAlgorithm)) {
            return (MacAlgorithm) Jwts.SIG.get().forKey(signatureAlgorithm);
        }
        return (MacAlgorithm) Jwts.SIG.get().forKey("HS512");
    }

    private boolean signatureAlgorithmAllowed(final String signatureAlgorithm) {
        return "HS256".equals(signatureAlgorithm) || "HS384".equals(signatureAlgorithm) || "HS512".equals(signatureAlgorithm);
    }

    private String generateWSsoJwtToken(final SSOUserInfo userInfo) throws CriptazioneException {

        if (userInfo == null || StringUtils.isBlank(userInfo.getLogin()))
            return null;

        String jwtToken = getInternalToken(userInfo, false);
        String refreshToken = getInternalToken(userInfo, true);

        return generateWSsoJwtToken(jwtToken, refreshToken, userInfo);
    }

    private String generateWSsoJwtToken(final String jwtToken, final String refreshToken, final SSOUserInfo userInfo) {
        Date creationDate = new Date();

        String authorizationCode = SSOGeneralManager.generateNewAuthorizationCode();

        String login = userInfo.getLogin().toUpperCase();

        WSsoJwtToken wSsoJwtToken = new WSsoJwtToken();
        wSsoJwtToken.setSyslogin(login);
        wSsoJwtToken.setAuthorizationCode(authorizationCode);
        wSsoJwtToken.setJwtToken(jwtToken);
        wSsoJwtToken.setRefreshToken(refreshToken);
        wSsoJwtToken.setDtCreazione(creationDate);
        wSsoJwtToken.setLevelOfAccess(userInfo.getLevelOfAccess());

        accountMapper.deleteWSsoJwtTokenBySyslogin(login);
        accountMapper.insertWSsoJwtToken(wSsoJwtToken);

        return authorizationCode;
    }

    private static String generateNewAuthorizationCode() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        String result = uuid1.toString().replaceAll("-", "") + uuid2.toString().replaceAll("-", "");

        return result;
    }

    private boolean isStandardSSOKey(final String key) {
        return key.equals(Constants.SSO_KEY_CODICE_FISCALE) || key.equals(Constants.SSO_KEY_NOME)
                || key.equals(Constants.SSO_KEY_COGNOME) || key.equals(Constants.SSO_KEY_NOME_COMPLETO)
                || key.equals(Constants.SSO_KEY_LOGIN) || key.equals(Constants.SSO_KEY_EMAIL);
    }

    protected static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

    @Value("${sso.cie.url}")
    private String cieUrl;

    @Value("${sso.cie.authlevel}")
    private String cieAuthLevel;

    @Value("${sso.cie.serviceprovider}")
    private String cieServiceprovider;

    @Value("${sso.cie.serviceindex}")
    private Integer cieServiceindex;

    public ResponseSpidUrl prepareLoginSpid(String idProvider, String callbackUrl, String codein) {

        ResponseSpidUrl risultato = new ResponseSpidUrl();
        risultato.setEsito(true);
        try {
            String url = spidUrl;
            String authSystem = "spid";
            String serviceIndex = spidServiceIndex;
            String authLevel = spidAuthLevel;

            String serviceProvider = this.wConfigManager.getConfigurationValue("auth.sso.spid.serviceprovider");
            if (StringUtils.isNotBlank(codein) && !"-".equals(codein)) {
                serviceProvider = this.wConfigManager.getConfigurationValue("loginMultiEnte." + codein);
            }

            if (StringUtils.isEmpty(url)) {
                logger.error("url non valorizzato");
                throw new Exception("url non valorizzato");
            }
            if (StringUtils.isEmpty(serviceProvider)) {
                logger.error("serviceProvider non valorizzato");
                throw new Exception("serviceProvider non valorizzato");
            }
            if (StringUtils.isEmpty(authLevel)) {
                logger.error("authLevel non valorizzato");
                throw new Exception("authLevel non valorizzato");
            }
            if (StringUtils.isEmpty(idProvider)) {
                logger.error("idProvider non valorizzato");
                throw new Exception("idProvider non valorizzato");
            }

            /**
             * backurl url di ritorno dell’ SP authSystem spid (default) authId token
             * ottenuto via soap getAuthId (validità temporale limitata) serviceProvider
             * alias configurato su AuthserviceSPID authLevel definisce L1 L2 L3 di SPID
             * https://www.spid.gov.it/SpidL1 idp entityID ricavato dai metadata dell’IDP
             */
            // richiedi il token temporaneo al sevizio SPID...
            // e salvalo in sessione per il login...
            ObjectFactory of = new ObjectFactory();
            GetAuthId getAuthId = of.createGetAuthId();
            GetAuthIdResponse response = (GetAuthIdResponse) soapClientConnector.callWebService(url, getAuthId);
            String authId = response.getGetAuthIdReturn();

            // invia la richiesta di login al servizio SPID...
            int i = url.indexOf("/services/");
            url = (i > 0 ? url.substring(0, i) : url);

            String urlLogin = url + "/auth.jsp" + "?backUrl=" + callbackUrl + "&authSystem=" + authSystem + "&authId="
                    + authId + "&serviceProvider=" + serviceProvider + "&serviceIndex=" + serviceIndex + "&authLevel="
                    + SPID_AUTHLEVEL_URL + authLevel + "&idp=" + idProvider;

            SpidUrlData spidData = new SpidUrlData();
            spidData.setUrl(urlLogin);
            spidData.setAuthId(authId);
            risultato.setData(spidData);

        } catch (Exception e) {
            logger.error("Errore in chiamata webservice SPID", e);
            risultato.setEsito(false);
            risultato.setErrorData(ParametricResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    protected static final String CIE_AUTHSYSTEM_DEFAULT = "cieid";

    public ResponseSpidUrl prepareLoginCie(String callbackUrl) {

        ResponseSpidUrl risultato = new ResponseSpidUrl();
        risultato.setEsito(true);
        try {

            String url = cieUrl;
            String authSystem = CIE_AUTHSYSTEM_DEFAULT;
            String serviceProvider = cieServiceprovider;
            Integer serviceIndex = cieServiceindex;
            String authLevel = cieAuthLevel;

            if (StringUtils.isEmpty(url)) {
                logger.error("url non valorizzato");
                throw new Exception("url non valorizzato");
            }
            if (StringUtils.isEmpty(serviceProvider)) {
                logger.error("serviceProvider non valorizzato");
                throw new Exception("serviceProvider non valorizzato");
            }
            if (StringUtils.isEmpty(authLevel)) {
                logger.error("authLevel non valorizzato");
                throw new Exception("authLevel non valorizzato");
            }

            /**
             * backurl url di ritorno dell’ SP authSystem spid (default) authId token
             * ottenuto via soap getAuthId (validità temporale limitata) serviceProvider
             * alias configurato su AuthserviceSPID authLevel definisce L1 L2 L3 di SPID
             * https://www.spid.gov.it/SpidL1 idp entityID ricavato dai metadata dell’IDP
             */
            // richiedi il token temporaneo al sevizio SPID...
            // e salvalo in sessione per il login...
            ObjectFactory of = new ObjectFactory();
            GetAuthId getAuthId = of.createGetAuthId();
            GetAuthIdResponse response = (GetAuthIdResponse) soapClientConnector.callWebService(url, getAuthId);
            String authId = response.getGetAuthIdReturn();

            // invia la richiesta di login al servizio SPID...
            int i = url.indexOf("/services/");
            url = (i > 0 ? url.substring(0, i) : url);
            logger.error("CALCOLO URL");
            String urlLogin = url + "/auth.jsp" + "?backUrl=" + callbackUrl + "&authSystem=" + authSystem + "&authId="
                    + authId + "&serviceProvider=" + serviceProvider + "&serviceIndex=" + serviceIndex + "&authLevel="
                    + SPID_AUTHLEVEL_URL + cieAuthLevel;

            SpidUrlData spidData = new SpidUrlData();
            spidData.setUrl(urlLogin);
            spidData.setAuthId(authId);
            risultato.setData(spidData);

        } catch (Exception e) {
            logger.error("Errore in chiamata webservice SPID", e);
            risultato.setEsito(false);
            risultato.setErrorData(ParametricResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseSpidAccount getUserAuth(String authId, String codein, String provider) {
        ResponseSpidAccount risultato = new ResponseSpidAccount();
        risultato.setEsito(true);
        try {
            TokenContentInfo tokenInfo = new TokenContentInfo();

            String url = "CIE".equalsIgnoreCase(provider) ? cieUrl : spidUrl;

            logger.debug("provider {}", provider);

            ObjectFactory of = new ObjectFactory();
            RetrieveUserData retrieveUserData = of.createRetrieveUserData();
            retrieveUserData.setAuthId(authId);
            RetrieveUserDataResponse response = (RetrieveUserDataResponse) soapClientConnector.callWebService(url, retrieveUserData);
            AuthData userInfo = response.getRetrieveUserDataReturn();

            SingleSignOut singleSignOut = of.createSingleSignOut();
            singleSignOut.setAuthId(authId);
            SingleSignOutResponse singleSignOutResponse = (SingleSignOutResponse) soapClientConnector.callWebService(url, singleSignOut);
            logger.debug("LOGOUT {}", singleSignOutResponse.getSingleSignOutReturn());

            String codiceFiscale = userInfo.getCodiceFiscale();

            if (StringUtils.isNotBlank(codiceFiscale))
                codiceFiscale = codiceFiscale.toUpperCase().trim();

            SSOUserInfo user = this.mapAuthDataToSSOUserInfo(userInfo, codiceFiscale, provider);
            String jwtToken = getInternalToken(user, false, singleSignOutResponse);
            String refreshToken = getInternalToken(user, true, singleSignOutResponse);
            tokenInfo.setToken(jwtToken);
            tokenInfo.setRefreshToken(refreshToken);
            tokenInfo.setLoa(user.getLevelOfAccess());
            Long syscon = this.userManager.getSysconFromLoginOrCf(codiceFiscale);
            tokenInfo.setUserExists(syscon != null);
            String registrazioneAttiva = this.wConfigManager.getConfigurationValue(CHIAVE_REGISTRAZIONE_ATTIVA);
            tokenInfo.setAbilitaRegistrazione("1".equals(registrazioneAttiva));
            if (codein != null && !codein.isEmpty() && !"-".equals(codein)) {
                if (syscon != null) {
                    Long count = this.accountMapper.checkUserAbilitato(syscon, codein);
                    tokenInfo.setUffintOk(count != 0L);
                    Long loaToSave = StringUtils.isBlank(user.getLevelOfAccess()) ? Long.parseLong(Constants.DEFAULT_LOA) : Long.parseLong(user.getLevelOfAccess());
                    this.handleLoa(syscon, codein, loaToSave, provider);
                }

            } else if (syscon != null) {
                Long loaToSave = StringUtils.isBlank(user.getLevelOfAccess()) ? Long.parseLong(Constants.DEFAULT_LOA) : Long.parseLong(user.getLevelOfAccess());
                this.handleLoa(syscon, "STD", loaToSave, provider);
            }

            // VIGILANZA2-480: Salviamo sempre in w_ssojwttoken
            String ssoJwtToken = this.generateWSsoJwtToken(jwtToken, refreshToken, user);
            tokenInfo.setSsoJwtToken(ssoJwtToken);

            risultato.setData(tokenInfo);
        } catch (Exception e) {
            logger.error("errore metodo getUserAuth", e);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseSpidAccount.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseSpidIniz getInizSpid() {
        ResponseSpidIniz risultato = new ResponseSpidIniz();
        risultato.setEsito(true);
        SpidInizData data = new SpidInizData();
        try {
            String isMultiEnte = this.wConfigManager.getConfigurationValue("loginMultiEnte");
            String spidValidator = this.wConfigManager.getConfigurationValue("sso.spid.validator");
            data.setVisibleValidator(spidValidator == null || "0".equals(spidValidator) ? false : true);
            if (("1").equals(isMultiEnte)) {
                data.setMultiente(true);
            } else {
                data.setMultiente(false);
            }
            risultato.setData(data);
        } catch (Exception e) {
            logger.error("errore metodo getInizSpid", e);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseSpidAccount.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public SpidSAResponse getSaSpid(String searchUfficioIntestatario) {
        SpidSAResponse risultato = new SpidSAResponse();
        risultato.setEsito(true);
        try {
            String searchUfficioIntestatarioCleaned = StringUtils.stripToNull(searchUfficioIntestatario);
            if (searchUfficioIntestatarioCleaned != null) {
                searchUfficioIntestatarioCleaned = "%" + searchUfficioIntestatarioCleaned.toUpperCase() + "%";
            }
            List<SABaseEntry> stazioniAppaltanti = new ArrayList<SABaseEntry>();
            SpidSAData data = new SpidSAData();
            long count = accountMapper.countSAListUffintFiltered();
            if (count == 0L) {
                risultato.setErrorData(UserAccountResult.NO_SA);
                return risultato;
            }

            RowBounds rowBounds = new RowBounds(0, 100);
            stazioniAppaltanti = accountMapper.getAllBaseSAInfoLimitedSpid(searchUfficioIntestatarioCleaned, rowBounds);

            data.setStazioniAppaltanti(stazioniAppaltanti);
            risultato.setData(data);

        } catch (Exception e) {
            logger.error("errore metodo getSaSpid", e);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseSpidAccount.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String authenticateUserCohesion(final String token) throws Exception {


        if (token != null) {
            String cohesionResponse;
            if (!"".equals(cohesionEncryptedKey)) {
                try {
                    cohesionResponse = new String(cipher3DES(false,
                            base64Decode(token),
                            cohesionEncryptedKey.trim().getBytes()));

                    Document cohesionResponseXML = getXmlDocFromString(cohesionResponse);
                    logger.info("cohesionResponse: {}",cohesionResponse);
                    cohesionResponseXML.getDocumentElement().normalize();

                    String nome = getCohesionElement(cohesionResponseXML,
                            attributoNome);
                    String cognome = getCohesionElement(cohesionResponseXML,
                            attributoCognome);
                    String login = getCohesionElement(cohesionResponseXML,
                            attributoLoginCodiceFiscale);
                    String email = getCohesionElement(cohesionResponseXML,
                            attributoEmail);

                    String standardProvider = "CUSTOM";
                    Integer loa = 2;
                    String base64info = getCohesionElement(cohesionResponseXML,"samlResponseBase64");
                    String info =  new String(base64Decode(base64info));
                    Document infoXML = getXmlDocFromString(info);
                    infoXML.getDocumentElement().normalize();

                    String saml = "saml2:";
                    if (getCohesionElement(infoXML, saml + "Assertion") == null) {
                        // per autenticazione SPID e CIE si riceve un inner xml con namespace saml2, mentre per utente/psw si riceve saml
                        saml = "saml:";
                    }
                    String issuer=getCohesionElement(infoXML, saml + "Issuer");

                    logger.info("issuer: {}",issuer);
                    if (StringUtils.isNotBlank(issuer)) {
                        String authnContextClassRef = getCohesionElement(infoXML, saml + "AuthnContextClassRef");
                        logger.info("authnContextClassRef: {}",authnContextClassRef);
                        if ("cohesion2.regione.marche.it:idp".equals(issuer)) {
                            logger.info("authenticateUserCohesion::1");
                            // autenticazione user+psw, eventualmente con PIN oppure OTP
                            if ("urn:oasis:names:tc:SAML:2.0:ac:classes:Smartcard".equals(authnContextClassRef)) {
                                // autenticazione con PIN oppure OTP viene considerata L3 SPID (LoA4)
                                loa = 4;
                            } else if ("urn:oasis:names:tc:SAML:2.0:ac:classes:Password".equals(authnContextClassRef)) {
                                // autenticazione con PIN oppure OTP viene considerata L2 SPID (LoA3)
                                loa = 3;
                            }
                        } else if ("https://idserver.servizicie.interno.gov.it/idp/profile/SAML2/POST/SSO".equals(issuer)) {
                            logger.info("authenticateUserCohesion::2");
                            logger.info("authnContextClassRef splittato {}", authnContextClassRef.split("SpidL")[1]);
                            // autenticazione CIE
                            standardProvider = "CIE";
                            loa = Integer.parseInt(authnContextClassRef.split("SpidL")[1]) + 1;
                        } else {
                            logger.info("authenticateUserCohesion::3");
                            logger.info("authnContextClassRef splittato {}", authnContextClassRef.split("SpidL")[1]);
                            // autenticazione SPID
                            standardProvider = "SPID";
                            loa = Integer.parseInt(authnContextClassRef.split("SpidL")[1]) + 1;
                        }
                    }
                    logger.info("loa: {}",loa);

                    SSOUserInfo userInfo = new SSOUserInfo();
                    userInfo.setCodiceFiscale(login);
                    userInfo.setCognome(cognome);
                    userInfo.setEmail(email);
                    userInfo.setLogin(login);
                    userInfo.setNome(nome);
                    userInfo.setNomeCompleto(nome + " " + cognome);
                    userInfo.setLevelOfAccess(loa + "");
                    userInfo.setProvider(standardProvider);
                    Long syscon = this.userManager.getSysconFromLoginOrCf(userInfo.getCodiceFiscale());
                    if (syscon != null)
                        this.handleLoa(syscon, "STD", Long.valueOf(userInfo.getLevelOfAccess()), userInfo.getProvider());
                    return this.generateWSsoJwtToken(userInfo);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }

        return null;
    }

    private byte[] base64Decode(String data) {
        byte[] result = new byte[0];
        try {
            result = DatatypeConverter.parseBase64Binary(data);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return result;
    }

    private Document getXmlDocFromString(String xml) {

        Document xmlDoc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setNamespaceAware(true);
            xmlDoc = dbf.newDocumentBuilder().parse(
                    new ByteArrayInputStream(xml.getBytes()));
        } catch (Exception ex) {
            logger.error(ex);
        }
        return xmlDoc;
    }

    @SuppressWarnings("java:S5547")
    private byte[] cipher3DES(boolean encrypt, byte[] message, byte[] key)
            throws Exception {
        byte[] cipher = new byte[0];
        try {
            if (key.length != 24) {
                throw new Exception("key size must be 24 bytes");
            }
            int cipherMode = Cipher.DECRYPT_MODE;
            if (encrypt) {
                cipherMode = Cipher.ENCRYPT_MODE;
            }
            Cipher sendCipher = Cipher.getInstance("DESede/ECB/NoPadding");
            SecretKey myKey = new SecretKeySpec(key, "DESede");
            sendCipher.init(cipherMode, myKey);
            cipher = sendCipher.doFinal(message);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return cipher;
    }

    private String getCohesionElement(Document doc, String name) {
        String value = null;
        try {
            value = StringUtils.stripToNull(doc.getElementsByTagName(name)
                    .item(0).getTextContent());
        } catch (Exception e) {
            value = null;
        }
        return value;
    }


    private void handleLoa(Long syscon, String codeIn, Long loa, String provider) {

        Long existentLoa = this.accountMapper.getUserLoa(syscon, codeIn, provider);
        if (existentLoa == null) {
            this.accountMapper.insertLoa(syscon, codeIn, loa, new Date(), provider);
        } else {
            if (existentLoa < loa) {
                this.accountMapper.updateLoa(syscon, codeIn, provider, loa, new Date());
            } else {
                this.accountMapper.updateLoa(syscon, codeIn, provider, existentLoa, new Date());
            }
        }
    }

    private SSOUserInfo elaborateSSOUserInfo(final SSOUserInfo userInfo) {

        // Manage IDP Type

        if (StringUtils.isBlank(userInfo.getProvider()))
            userInfo.setProvider(Constants.DEFAULT_IDP_TYPE);

        if (StringUtils.isNotBlank(userInfo.getProvider()))
            userInfo.setProvider(userInfo.getProvider().toUpperCase());

        // Set default LoA
        if (StringUtils.isBlank(userInfo.getLevelOfAccess())) {
            userInfo.setLevelOfAccess(Constants.DEFAULT_LOA);
        } else {
            try {
                int usrLoa = Integer.parseInt(userInfo.getLevelOfAccess());
                if (usrLoa < 2 || usrLoa > 4) {
                    // se non rientra nel range 2,3,4 allora lo resetto al valore minimo ovvero 2
                    userInfo.setLevelOfAccess(Constants.DEFAULT_LOA);
                }
            } catch (NumberFormatException nfe) {
                logger.error("NumberFormatException, LoA not a number, setting default LoA 2", nfe);
                userInfo.setLevelOfAccess(Constants.DEFAULT_LOA);
            }
        }

        return userInfo;
    }

    private void handleLoaStandard(final SSOUserInfo userInfo) {
        if (StringUtils.isNotBlank(userInfo.getCodiceFiscale())) {
            Long syscon = this.userManager.getSysconFromLoginOrCf(userInfo.getLogin());
            if (syscon != null) {
                Long loaToSave = StringUtils.isBlank(userInfo.getLevelOfAccess()) ? Long.parseLong(Constants.DEFAULT_LOA) : Long.parseLong(userInfo.getLevelOfAccess());
                this.handleLoa(syscon, "STD", loaToSave, userInfo.getProvider());
            }
        }
    }

    private Map<String, String> headersUppercase(final Map<String, String> headers) {
        return headers.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toUpperCase(), Map.Entry::getValue));
    }
}
