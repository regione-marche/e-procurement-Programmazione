package it.appaltiecontratti.autenticazione.bl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import it.appaltiecontratti.autenticazione.Constants;
import it.appaltiecontratti.autenticazione.EmailConfig;
import it.appaltiecontratti.autenticazione.entity.*;
import it.appaltiecontratti.autenticazione.exceptions.UserPublicRegistrationPasswordException;
import it.appaltiecontratti.autenticazione.mapper.AccountMapper;
import it.appaltiecontratti.autenticazione.mapper.SqlMapper;
import it.appaltiecontratti.autenticazione.utils.OUOrderComparator;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import javax.crypto.SecretKey;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

/**
 * Manager per la gestione della business logic.
 *
 * @author Michele.diNapoli
 */
@Component(value = "accountManager")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AccountManager extends BaseService {

    /**
     * Logger di classe.
     */
    private static final Logger logger = LogManager.getLogger(AccountManager.class);

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private GDPRManager gdprManager;

    @Autowired
    private SqlMapper sqlMapper;

    @Value("${sso.token.expireTime}")
    private int tokenExpTime;
    @Value("${sso.refreshToken.expireTime}")
    private int refreshTokenExpTime;

    @Value("${it.eldasoft.account.opzioniDefault:#{null}}")
    private String ouDefault;

    @Value("${assistenzaAttiva:#{null}}")
    private String assistenzaAttiva;

    private static final String CHIAVE_PROFILI_DEFAULT = "registrazione.profiliDisponibili";

    private static final String CHIAVE_MAIL_SENDER = "it.eldasoft.registrazione.utente.mail.indirizzoMittente";

    private static final String CHIAVE_MAIL_SUBJECT = "it.eldasoft.registrazione.utente.mail.utente.oggetto";

    private static final String CHIAVE_MAIL_SUBJECT_ADMIN = "it.eldasoft.registrazione.utente.mail.amministratore.oggetto";

    private static final String CHIAVE_MAIL_ADDRESS_ADMIN = "it.eldasoft.registrazione.mailAmministratore";

    private static final String CHIAVE_REGISTRAZIONE_ATTIVA = "it.eldasoft.registrazione.attivaForm";

    public static final String W_CONFIG_DURATA_ACCOUNT = "account.durata";

    public static final String W_CONFIG_APP_TITLE = "it.eldasoft.titolo";

    public static final String W_CONFIG_REGISTRAZIONE_ANAGRAFICA = "registrazione.integrazioneAnagrafica";

    public static final String CODAPP = "W9";

    public static final String IDCFG = "STD";

    public static final String TECNI = "TECNI";


    public UserAccountResult getUserInfo(final Long loggedUserSyscon, final String searchUfficioIntestatario) {
        logger.info("getUserInfo");
        UserAccountResult risultato = new UserAccountResult();
        UserAccountInfo info = new UserAccountInfo();
        try {
            String searchUfficioIntestatarioCleaned = StringUtils.stripToNull(searchUfficioIntestatario);
            if (searchUfficioIntestatarioCleaned != null) {
                searchUfficioIntestatarioCleaned = "%" + searchUfficioIntestatarioCleaned.toUpperCase() + "%";
            }

            if (loggedUserSyscon == null) {
                risultato.setErrorData(UserAccountResult.NO_USER);
                return risultato;
            }
            boolean isUtenteAbilitatoTutteSA = isUtenteAbilitatoTutteSA(loggedUserSyscon);

            List<SABaseEntry> stazioniAppaltanti = null;
            RowBounds rowBounds;
            if (isUtenteAbilitatoTutteSA) {
                long count = accountMapper.countAllBaseSAInfo();
                info.setTotalSACount(count);
                if (count > 100L) {
                    rowBounds = new RowBounds(0, 100);
                    stazioniAppaltanti = accountMapper.getAllBaseSAInfoLimited(searchUfficioIntestatarioCleaned, rowBounds);
                } else {
                    stazioniAppaltanti = accountMapper.getAllBaseSAInfo();
                }
            } else {
                long count = accountMapper.countSAList(loggedUserSyscon);
                info.setTotalSACount(count);
                if (count == 0L) {
                    risultato.setErrorData(UserAccountResult.NO_SA);
                    return risultato;
                }
                if (count > 100L) {
                    rowBounds = new RowBounds(0, 100);
                    stazioniAppaltanti = accountMapper.getSAList(loggedUserSyscon, searchUfficioIntestatarioCleaned, rowBounds);
                } else {
                    stazioniAppaltanti = accountMapper.getSAList(loggedUserSyscon, null);
                }
            }

            List<ChiaveConfigurazione> chiaviAccessoOrt = this.accountMapper.getChiaviAccessoOrt();
            ChiaviAccesso chiaviAccesso = new ChiaviAccesso();
            for (ChiaveConfigurazione chiave : chiaviAccessoOrt) {
                if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.username")) {
                    chiaviAccesso.setUsername(chiave.getValore());
                }
                if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.clientKey")) {
                    chiaviAccesso.setClientKey(chiave.getValore());
                }
                if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.clientId")) {
                    chiaviAccesso.setClientId(chiave.getValore());
                }
                if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.password")) {
                    chiaviAccesso.setPassword(chiave.getValore());
                }
            }
            String ruolo = this.accountMapper.getRuoloUtente(loggedUserSyscon);
            String userEmail = this.accountMapper.getUserEmail(loggedUserSyscon);
            String urlManuali = this.wConfigManager.getConfigurationValue(Constants.W_CONFIG_URL_MANUALI);
            String username = this.accountMapper.findUsernameBySyscon(loggedUserSyscon);
            
            String integrazioneJSerfin = wConfigManager.getConfigurationValue("integrazione.jserfin");
            String integrazioneApk = wConfigManager.getConfigurationValue("integrazione.apk");

            info.setRuolo(ruolo);
            info.setUsername(username);
            info.setSyscon(String.valueOf(loggedUserSyscon));
            info.setStazioniAppaltanti(stazioniAppaltanti);
            info.setChiaviAccesso(chiaviAccesso);
            if (assistenzaAttiva != null) {
                info.setRichiestaAssistenzaAttiva("2".equals(assistenzaAttiva));
            } else {
                String modoAssistenza = this.wConfigManager.getConfigurationValue(Constants.ASSISTENZA_MODO);
                info.setRichiestaAssistenzaAttiva(StringUtils.isNotBlank(modoAssistenza)
                        && "2".equals(modoAssistenza));
            }
            info.setUserEmail(userEmail);
            info.setUrlManuali(urlManuali);
            info.setMessaggisticaInternaAttiva(userManager.isMessaggisticaInternaEnabled());
            info.setAbilitaIntegrazioneJSerfin("1".equals(integrazioneJSerfin));
            info.setAbilitaIntegrazioneAPK("1".equals(integrazioneApk));
            

            risultato.setData(info);

        } catch (Exception ex) {
            logger.error("Errore in get User Info ", ex);
            risultato.setErrorData(UserAccountResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseAbilitazioni getAbilitazioniUtenteEdOpzioniProdotto(final Long loggedUserSyscon) {

        logger.info("getAbilitazioniUtenteEdOpzioniProdotto");
        ResponseAbilitazioni risultato = new ResponseAbilitazioni();
        try {
            risultato.setEsito(true);

            OpzioniUtenteProdotto oup = new OpzioniUtenteProdotto();

            String abilitazioniString = this.accountMapper.getAbilitazioniUtente(loggedUserSyscon);

            if (abilitazioniString != null) {
                oup.setOu(new ArrayList<>(Arrays.asList(abilitazioniString.split(Constants.OU_SEPARATORE_REGEX))));
            } else {
                oup.setOu(new ArrayList<String>());
            }

            String opzioniProdotto = this.accountMapper.getOpzioniProdotto(codiceProdotto,
                    Constants.W_ATT_OPZIONI_PRODOTTO_CHIAVE);
            if (opzioniProdotto != null) {
                oup.setOp(new ArrayList<>(Arrays.asList(opzioniProdotto.split(Constants.OP_SEPARATORE_REGEX))));
            } else {
                oup.setOp(new ArrayList<String>());
            }

            risultato.setData(oup);

        } catch (Exception ex) {
            logger.error("Errore in getAbilitazioniUtenteEdOpzioniProdotto ", ex);
            risultato.setErrorData("Si e' verificato un errore imprevisto. si prega di riprovare l'operazione");
            risultato.setEsito(false);
        }
        return risultato;
    }

    private AuthResponse validateInternalToken(String auth) {
        AuthResponse authResponse = new AuthResponse();
        String token = auth.replace("Bearer ", "");
        try {
            byte[] jwtKey = userManager.getJWTKey();
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);
            Jws<Claims> claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            authResponse.setValid(true);

        } catch (SignatureException e) {
            authResponse.setValid(true);
            authResponse.setErrorCode(AuthResponse.TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            authResponse.setValid(false);
            authResponse.setErrorCode(AuthResponse.TOKEN_EXPIRED);
            authResponse.setErrorMessage("Il token è scaduto");
        } catch (Exception e) {
            authResponse.setValid(false);
            authResponse.setErrorCode(AuthResponse.TOKEN_EXPIRED);
            authResponse.setErrorMessage("Errore generico");
        }
        return authResponse;
    }

//    public String getInternalToken(AuthData userData, boolean refresh) throws CriptazioneException {
//
//        String issuer = null;
//        try {
//            issuer = InetAddress.getLocalHost().getHostAddress() + "/Account/LoginPubblica";
//        } catch (UnknownHostException e) {
//            logger.debug("loginPubblica: metodo creazione token. Impossibile trovare HostName");
//            issuer = "UnknownHost";
//        }
//        byte[] jwtKey = userManager.getJWTKey();
//        long nowMillis = System.currentTimeMillis();
//        long tokenLife = refresh ? refreshTokenExpTime : tokenExpTime;
//        JwtBuilder builder = Jwts.builder().setIssuedAt(new Date(nowMillis)).setSubject(userData.getCodiceFiscale())
//                .setIssuer(issuer).setAudience(userData.getCodiceFiscale()).signWith(SignatureAlgorithm.HS512, jwtKey)
//                .setExpiration(getExpDate(nowMillis, tokenLife)).claim("USER_CF", userData.getCodiceFiscale())
//                .claim("USER_NAME", userData.getNome()).claim("USER_SURNAME", userData.getCognome());
//        return builder.compact();
//    }
//
//    public String getRefreshToken(String username, String syscon) throws CriptazioneException {
//
//        String issuer = null;
//        try {
//            issuer = InetAddress.getLocalHost().getHostAddress() + "/Account/LoginPubblica";
//        } catch (UnknownHostException e) {
//            logger.debug("loginPubblica: metodo creazione refresh token. Impossibile trovare HostName");
//            issuer = "UnknownHost";
//        }
//        byte[] jwtKey = userManager.getJWTKey();
//        long nowMillis = System.currentTimeMillis();
//        JwtBuilder builder = Jwts.builder().setIssuedAt(new Date(nowMillis)).setSubject(username).setIssuer(issuer)
//                .setAudience(username).signWith(SignatureAlgorithm.HS512, jwtKey)
//                .setExpiration(getExpDate(nowMillis, refreshTokenExpTime)).claim("syscon", syscon)
//                .claim("role", "refresh");
//        return builder.compact();
//    }

    public ResponseStazioneAppaltante getStazioneAppaltanteInfo(String stazioneAppaltante, Long syscon, String codapp, Boolean selezioneSA, String ipAddress) {

        ResponseStazioneAppaltante risultato = new ResponseStazioneAppaltante();
        risultato.setEsito(true);

        try {
            SAEntry entry = null;
            if ("*".equals(stazioneAppaltante)) {
                entry = new SAEntry();
                entry.setCodFiscale("*");
                entry.setCodice("*");
                entry.setNome("Tutte le Stazioni Appaltanti");
            } else {
                entry = this.accountMapper.getStazioneAppaltanteInfo(stazioneAppaltante);
            }

            if (entry != null) {
                List<ProfiloInfo> profiliResult = new ArrayList<ProfiloInfo>();
                List<ProfiloInfo> profili = this.accountMapper.getProfili(syscon, codapp);
                if (profili != null) {

                    for (ProfiloInfo profilo : profili) {
                        profilo.setOk(checkCRCProfilo(profilo));
                        profiliResult.add(profilo);
                    }
                    entry.setProfili(profiliResult);
                }
                risultato.setData(entry);
            }

            //VIGILANZA2-375: Tracciatura su WLogEventi per selezione della stazione appaltante da parte dell'utente.
            if(Boolean.TRUE.equals(selezioneSA)) {
                wLogEventiService.insertSetSALogEvent(syscon, entry != null ? entry.getCodice() : null, ipAddress);
            }

        } catch (Exception t) {
            logger.error("Errore inaspettato durante ricerca stazione appaltante: SA =" + stazioneAppaltante, t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    private boolean checkCRCProfilo(ProfiloInfo profilo) {
        return (profilo.getCrc() != null && getCRCProfilo(profilo).equals(profilo.getCrc()));
    }

    public ResponseConfigurazioneProfilo getProfilo(String codProfilo, final Long syscon, final String ipAddress) {

        ResponseConfigurazioneProfilo risultato = new ResponseConfigurazioneProfilo();
        risultato.setEsito(true);
        ProfiloInfo profiloSelezionato = this.accountMapper.getInfoProfilo(codProfilo);

        if (profiloSelezionato != null) {

            // Solitamente si arriva a questo metodo con profili validi in testata, quindi
            // il terzo parametro del costruttore (isOk) � sempre a true
            ConfigurazioneProfilo config = new ConfigurazioneProfilo(codProfilo, profiloSelezionato.getNome(), true);
            // Aggiungo tutti i dati di default
            HashMap<String, Long> hashAppoggio = new HashMap<String, Long>();
            List<AzioneEntry> azioni = this.accountMapper.getAllAzioni();
            try {

                if (azioni != null) {
                    for (AzioneEntry azione : azioni) {
                        Long crc = calcolaCrcAzione(azione);
                        if (crc != null) {

                            config.addProtec(azione.getTipo(), azione.getAzione(), azione.getOggetto(),
                                    "1".equals(azione.getValore().toString()), true, crc.equals(azione.getCrc()),
                                    azione.getValore());

                            hashAppoggio.put(azione.getTipo() + "." + azione.getAzione() + "."
                                    + azione.getOggetto(), azione.getValore());

                            if (!crc.equals(azione.getCrc())) {
                                String msgErr = "Caricamento profilo " + codProfilo
                                        + ": profilo corrotto a causa dell'errato valore del CRC nella tabella W_AZIONI del record con "
                                        + "TIPO=" + azione.getTipo() + ", AZIONE="
                                        + azione.getAzione() + ", OGGETTO=" + azione.getOggetto()
                                        + ". CRC su DB = " + azione.getCrc().toString() + "; CRC calcolato = " + crc
                                        + ".";
                                logger.error(msgErr);
                            }
                        }
                    }
                }

            } catch (Exception t) {
                logger.error("Errore inaspettato durante caricamento Profilo =" + codProfilo, t);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
            }

            List<BaseAzioneEntry> azioniProaziAnd = this.accountMapper.getAzioniProaziAnd(codProfilo);
            try {

                if (azioniProaziAnd != null) {
                    for (BaseAzioneEntry azioneProaziAnd : azioniProaziAnd) {
                        Long crc = calcolaCRCproazi(azioneProaziAnd);
                        if (crc != null) {

                            config.addProtec(azioneProaziAnd.getTipo(), azioneProaziAnd.getAzione(),
                                    azioneProaziAnd.getOggetto(), "1".equals(azioneProaziAnd.getValore().toString()),
                                    false, crc.equals(azioneProaziAnd.getCrc()),
                                    hashAppoggio.get(azioneProaziAnd.getTipo() + "."
                                            + azioneProaziAnd.getAzione() + "."
                                            + azioneProaziAnd.getOggetto()));

                            if (!crc.equals(azioneProaziAnd.getCrc())) {
                                String msgErr = "Caricamento profilo " + codProfilo
                                        + ": profilo corrotto a causa dell'errato valore del CRC nella tabella W_PROAZI del record con "
                                        + "TIPO=" + azioneProaziAnd.getTipo() + ", AZIONE="
                                        + azioneProaziAnd.getAzione() + ", OGGETTO="
                                        + azioneProaziAnd.getOggetto() + ". CRC su DB = "
                                        + azioneProaziAnd.getCrc().toString() + "; CRC calcolato = " + crc + ".";
                                logger.error(msgErr);
                            }
                        }
                    }
                }

            } catch (Exception t) {
                logger.error("Errore inaspettato durante caricamento Profilo =" + codProfilo, t);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
            }

            List<BaseAzioneEntry> azioniProaziOr = this.accountMapper.getAzioniProaziOr(codProfilo);
            try {

                if (azioniProaziOr != null) {
                    for (BaseAzioneEntry azioneProaziOr : azioniProaziOr) {
                        Long crc = calcolaCRCproazi(azioneProaziOr);
                        if (crc != null) {
                            config.addProtec(azioneProaziOr.getTipo(), azioneProaziOr.getAzione(),
                                    azioneProaziOr.getOggetto(), "1".equals(azioneProaziOr.getValore().toString()),
                                    false, crc.equals(azioneProaziOr.getCrc()),
                                    hashAppoggio.get(azioneProaziOr.getTipo() + "."
                                            + azioneProaziOr.getAzione() + "."
                                            + azioneProaziOr.getOggetto()));

                            if (!crc.equals(azioneProaziOr.getCrc())) {
                                String msgErr = "Caricamento profilo " + codProfilo
                                        + ": profilo corrotto a causa dell'errato valore del CRC nella tabella W_PROAZI del record con "
                                        + "TIPO=" + azioneProaziOr.getTipo() + ", AZIONE="
                                        + azioneProaziOr.getAzione() + ", OGGETTO="
                                        + azioneProaziOr.getOggetto() + ". CRC su DB = "
                                        + azioneProaziOr.getCrc().toString() + "; CRC calcolato = " + crc + ".";
                                logger.error(msgErr);
                            }
                        }
                    }
                }

            } catch (Exception t) {
                logger.error("Errore inaspettato durante caricamento Profilo =" + codProfilo, t);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
            }

            ProfiloFullInfo profiloCompleto = new ProfiloFullInfo();
            profiloCompleto.setIdProfilo(config.getIdProfilo());
            profiloCompleto.setNome(config.getNome());
            profiloCompleto.setOk(config.isOk());

            ArrayList<ValoreDatoProfilo> configurazioni = new ArrayList<ValoreDatoProfilo>();
            for (String key : config.keySet()) {
                ValoreDatoProfilo configurazione = config.get(key);
                configurazione.setKey(key);
                configurazioni.add(configurazione);
            }

            String documentiAssociatiDB = this.wConfigManager.getConfigurationValue("it.eldasoft.documentiAssociatiDB");
            profiloCompleto.setDocumentiAssociatiDB("1".equals(documentiAssociatiDB));

            var woggettiRows = accountMapper.getWOggetti();
            List<String> woggetti = new ArrayList<String>();
            for (OggettoEntry ogg : woggettiRows) {
                woggetti.add(ogg.getTipo() + "-" + ogg.getOggetto());
            }
            profiloCompleto.setWoggetti(woggetti);


            profiloCompleto.setConfigurazioni(configurazioni);

            ArrayList<String> campiSolaLettura = new ArrayList<String>();
            String conf = this.wConfigManager.getConfigurationValue("elencoCampi.solaLettura");
            if (conf != null) {
                String[] campi = conf.split(";");
                Collections.addAll(campiSolaLettura, campi);
            }
            profiloCompleto.setCampiSolaLettura(campiSolaLettura);

            wLogEventiService.insertSetProfiloLogEvent(syscon, codProfilo, ipAddress);

            risultato.setData(profiloCompleto);
        }
        return risultato;

    }

    public Long getCRCProfilo(ProfiloInfo profilo) {
        String buf = profilo.getCodProfilo() +
                profilo.getCodProfilo() +
                profilo.getNome() +
                profilo.getCodapp() +
                profilo.getFlagInterno() +
                profilo.getCodCliente();
        CRC32 crc = new CRC32();
        crc.update(buf.getBytes());
        return crc.getValue();
    }

    public Long calcolaCrcAzione(AzioneEntry azione) {
        String buf = azione.getTipo() +
                azione.getAzione() +
                azione.getOggetto() +
                azione.getValore() +
                azione.getInor() +
                azione.getViselenco();
        CRC32 crc = new CRC32();
        crc.update(buf.getBytes());
        return crc.getValue();

    }

    public Long calcolaCRCproazi(BaseAzioneEntry azione) {
        String buf = azione.getTipo() +
                azione.getAzione() +
                azione.getOggetto() +
                azione.getValore();
        CRC32 crc = new CRC32();
        crc.update(buf.getBytes());
        return crc.getValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserRegistrationResponse userRegistration(final UserAccountForm userForm, final boolean registrazionePubblica) throws Exception {
        UserRegistrationResponse risultato = new UserRegistrationResponse();

        List<String> receivers = new ArrayList<String>();
        receivers.add(userForm.getEmail());
        List<String> receiversAdmin = new ArrayList<String>();

        List<String> receiverCC = new ArrayList<String>();
        List<String> receiversCCn = new ArrayList<String>();
        risultato.setEsito(true);

        boolean valid = super.verifyCaptchaSolution(userForm.getCaptchaSolution());

        if (!valid) {
            logger.error("Validazione captcha non superata");
            risultato.setEsito(false);
            risultato.setErrorData(UserRegistrationResponse.CAPTCHA_ERROR);
            return risultato;
        }

        String codiceFiscaleLogin = registrazionePubblica ? userForm.getCodiceFiscaleLogin() : userForm.getCodiceFiscale();
        if (StringUtils.isNotBlank(codiceFiscaleLogin))
            codiceFiscaleLogin = codiceFiscaleLogin.toUpperCase().trim();

        try {
            Long count = this.accountMapper.countByUsername(codiceFiscaleLogin);
            if (count != null && count > 0L) {
                risultato.setEsito(false);
                risultato.setErrorData(UserRegistrationResponse.USERNAME_ALREADY_EXISTS);
                return risultato;
            }

            if (registrazionePubblica) {
                // Controllo match delle password
                if (!userForm.getPassword().equals(userForm.getConfermaPassword())) {
                    logger.error("Password e conferma password non coincidono per username [ {} ]", codiceFiscaleLogin);
                    risultato.setEsito(false);
                    risultato.setErrorData(UserRegistrationResponse.PASSWORD_CONFIRM_PASSWORD_MISMATCH);
                    return risultato;
                }

                boolean continueInsert = true;
                List<String> errorMessages = new ArrayList<>();

                // check GDPR 1 (lunghezza password)
                boolean result1 = gdprManager.executeCheck1(userForm);

                if (!result1) {
                    logger.error("Lunghezza password per username [ {} ] non valida", codiceFiscaleLogin);
                    continueInsert = false;
                    errorMessages.add(UserRegistrationResponse.PASSWORD_LENGTH);
                }

                // check GDPR 2 (complessita' password)
                GDPRCheck2DTO result2 = gdprManager.executeCheck2(userForm);

                if (!result2.pass()) {
                    logger.error("Complessita' password per username [ {} ] non sufficiente", codiceFiscaleLogin);

                    continueInsert = false;

                    if (!result2.isAllowedCharacters())
                        errorMessages.add(UserRegistrationResponse.PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS);
                    if (!result2.isLowerCaseCharacters())
                        errorMessages.add(UserRegistrationResponse.PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS);
                    if (!result2.isUpperCaseCharacters())
                        errorMessages.add(UserRegistrationResponse.PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS);
                    if (!result2.isNumberCharacters())
                        errorMessages.add(UserRegistrationResponse.PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS);
                    if (!result2.isSpecialCharacters())
                        errorMessages.add(UserRegistrationResponse.PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS);
                }

                // check GDPR 3B (caratteri consecutivi presenti nello username)
                boolean result3B = gdprManager.executeCheck3B(userForm);
                if (!result3B) {
                    logger.error("Contenuto password per username [ {} ] non valido", codiceFiscaleLogin);

                    continueInsert = false;

                    errorMessages.add(UserRegistrationResponse.PASSWORD_CONTENT_FOLLOWING_CHARACTERS);
                }

                if (!continueInsert) {
                    throw new UserPublicRegistrationPasswordException(errorMessages);
                }
            }


            Long idAccount = this.insertAccount(userForm, codiceFiscaleLogin, registrazionePubblica);
            risultato.setData(idAccount + "");
            if (idAccount == null) {
                throw new Exception(
                        "Errore inaspettato durante la registrazione dell'utente " + userForm.getCodiceFiscale());
            } else {
                if (userForm.getApplicativiSelezionati() != null) {
                    for (String applicativo : userForm.getApplicativiSelezionati()) {

                        String profilo = getProfiloByCodApp(applicativo);
                        if (profilo != null) {
                            this.accountMapper.insertAssociazioneUteProfilo(idAccount, profilo);
                        }
                    }
                }
                if (userForm.getStazioneAppaltante() != null) {
                    this.accountMapper.insertAssociazioneUteSa(idAccount, userForm.getStazioneAppaltante());

                    String configTecni = this.wConfigManager.getConfigurationValue(W_CONFIG_REGISTRAZIONE_ANAGRAFICA);
                    //Se la config è uguale a 'TECNI': controllo se esiste già in TECNI un record associato (con gli stessi valori di CGENTEI e CFTEC sovrascrivendo il syscon),
                    // altrimenti lo creo valorizzando i dati presenti.
                    if(StringUtils.equals(configTecni, TECNI)) {

                        List<RupEntry> tecnici = this.accountMapper.getTecnici(codiceFiscaleLogin.toUpperCase(), userForm.getStazioneAppaltante().toUpperCase());

                        if(tecnici == null || tecnici.isEmpty()) {
                            //Creo un nuovo tecnico e lo inserisco con i dati a disposizione.
                            String codTec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
                            RupInsertForm nuovoRup = new RupInsertForm();
                            nuovoRup.setCodice(codTec);
                            nuovoRup.setCognome(userForm.getCognome());
                            nuovoRup.setNome(userForm.getNome());
                            nuovoRup.setCf(codiceFiscaleLogin.toUpperCase());
                            nuovoRup.setNominativo(userForm.getCognome() + " " + userForm.getNome());
                            nuovoRup.setStazioneAppaltante(userForm.getStazioneAppaltante());
                            nuovoRup.setEmail(userForm.getEmail());
                            nuovoRup.setSyscon(idAccount);

                            this.accountMapper.insertTecnico(nuovoRup);
                        }
                        else {
                            //Aggiorno il syscon dell'utente appena creato per il primo tecnico recuperato.
                            RupEntry primoTecnico;
                            primoTecnico = tecnici.get(0);

                            this.accountMapper.updateTecnico(primoTecnico.getCodice(), idAccount);
                        }
                    }
                }

                //String sender = this.wConfigService.getConfigurationValue(CHIAVE_MAIL_SENDER);
                String sender = this.accountMapper.getEmailMittente(CODAPP, IDCFG);
                String oggettoMail = this.wConfigManager.getConfigurationValue(CHIAVE_MAIL_SUBJECT);
                String oggettoMailAdmin = this.wConfigManager.getConfigurationValue(CHIAVE_MAIL_SUBJECT_ADMIN);
                String mailAdmin = this.wConfigManager.getConfigurationValue(CHIAVE_MAIL_ADDRESS_ADMIN);
                receiversAdmin.add(mailAdmin);

                String checkMail = sendEmail(sender, oggettoMail, receivers, receiverCC, receiversCCn, userForm, codiceFiscaleLogin);
                String checkMailAdmin = sendEmailAdmin(sender, oggettoMailAdmin, receiversAdmin, receiverCC, receiversCCn, userForm);

                if (StringUtils.isNotBlank(checkMail) || StringUtils.isNotBlank(checkMailAdmin)) {
                    if (StringUtils.isNotBlank(checkMail)) {
                        risultato.setErrorData(checkMail);
                    } else if (StringUtils.isNotBlank(checkMailAdmin)) {
                        risultato.setErrorData(checkMailAdmin);
                    }
                }
            }
        } catch (MailSendException ex) {
            logger.error("Errore inaspettato durante la registrazione dell'utente:" + userForm.getCodiceFiscale(), ex);
            throw ex;
        } catch (UserPublicRegistrationPasswordException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Errore inaspettato durante la registrazione dell'utente:" + userForm.getCodiceFiscale(), ex);
            throw ex;
        }
        return risultato;
    }

    @SuppressWarnings("java:S2068")
    private Long insertAccount(final UserAccountForm userAccountForm, final String codiceFiscaleLogin, final boolean registrazionePubblica) throws Exception {
        long idAccount;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("insertAccount: inizio metodo");
            }
            Long maxSyscon = this.accountMapper.getMaxSyscon();
            if (maxSyscon == null) {
                idAccount = 1L;
            } else {
                idAccount = maxSyscon + 1L;
            }

            ICriptazioneByte criptatore = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, codiceFiscaleLogin.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

            // verifico se e' presente ou214 nelle default altrimenti la inserisco
            String defaultAbilitazioni = this.handleOpzioniUtente(ouDefault);

            String cryptedPassword;
            if (registrazionePubblica) {
                criptatore = FactoryCriptazioneByte.getInstance(
                        FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, userAccountForm.getPassword().getBytes(),
                        ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
                cryptedPassword = new String(criptatore.getDatoCifrato(), StandardCharsets.UTF_8);
            } else {
                cryptedPassword = this.wConfigManager.getConfigurationValue(Constants.W_CONFIG_SSO_DEFAULT_PASSWORD);
                if (StringUtils.isBlank(cryptedPassword))
                    cryptedPassword = "!h";
            }

            UserAccountInsertForm insertForm = new UserAccountInsertForm();
            insertForm.setIdAccount(idAccount);
            insertForm.setPassword(cryptedPassword);
            insertForm.setCodfisc(codiceFiscaleLogin);
            insertForm.setDataInserimento(new Date());
            insertForm.setEmail(userAccountForm.getEmail());
            insertForm.setLogin(codiceFiscaleLogin);
            insertForm.setNome(userAccountForm.getNome() + " " + userAccountForm.getCognome());
            insertForm.setOpzioniUtente(defaultAbilitazioni);
            insertForm.setLoginCriptata(new String(criptatore.getDatoCifrato()));

            this.accountMapper.insertAccount(insertForm);

            // insert into stoutesys
            StoUteSys stoUteSys = new StoUteSys();
            stoUteSys.setSysnom(insertForm.getLoginCriptata());
            stoUteSys.setSyspwd(insertForm.getPassword());
            stoUteSys.setSyscon(insertForm.getIdAccount());
            stoUteSys.setSysdat(new Date());
            stoUteSys.setSyslogin(insertForm.getLogin());
            this.accountMapper.insertStoUteSysChangedPassword(stoUteSys);

            if (logger.isDebugEnabled()) {
                logger.debug("insertAccount: fine metodo");
            }
        } catch (Exception ex) {
            logger.error(
                    "Errore inaspettato durante la registrazione dell'utente:" + userAccountForm.getCodiceFiscale(),
                    ex);
            throw ex;
        }
        return idAccount;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseUserExists checkUserExists(String username, final String ipAddress) {
        ResponseUserExists risultato = new ResponseUserExists();
        risultato.setEsito(true);
        try {
            Long syscon = this.userManager.getSysconFromLoginOrCf(username.toUpperCase());
            String esito = null;
            if (syscon == null) {
                esito = ResponseUserExists.NO_USER;
            } else {

                String disabilitato = this.accountMapper.checkUserEnabledBySyscon(syscon);
                esito = disabilitato == null || "0".equals(disabilitato) ? ResponseUserExists.OK_USER
                        : ResponseUserExists.DISABLED_USER;


                if (ResponseUserExists.OK_USER.equals(esito)) {
                    String durataAccount = wConfigManager.getConfigurationValue(W_CONFIG_DURATA_ACCOUNT);
                    Date lastLoginDate = this.accountMapper.getLastLoginDate(syscon);

                    if (lastLoginDate != null && lastLoginDate.getTime() + getDurataAccountInMillisecondi(Integer.parseInt(durataAccount)) <= new Date().getTime()) {
                        esito = ResponseUserExists.EXPIRED_USER;
                    }
                }
            }

            if (esito.equals(ResponseUserExists.OK_USER)) {
                accountMapper.updateLoginAccess(syscon, LocalDateTime.now());
                wLogEventiService.insertLoginLogEvent(syscon, ipAddress);
            }

            UserExistsInfo info = new UserExistsInfo();
            String registrazioneAttiva = this.wConfigManager.getConfigurationValue(CHIAVE_REGISTRAZIONE_ATTIVA);

            info.setAbilitaRegistrazione("1".equals(registrazioneAttiva));
            info.setExist(esito);
            risultato.setData(info);
        } catch (Exception t) {
            logger.error("Errore inaspettato durante il controllo sull'esistenza dell'username:" + username, t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    private static long getDurataAccountInMillisecondi(final long durataAccountGiorni) {
        return durataAccountGiorni * 24 * 60 * 60 * 1000;
    }

    private String sendEmail(String from, String subject, List<String> receivers, List<String> ccReceivers,
                             List<String> ccnReceivers, UserAccountForm userAccountForm, final String codiceFiscaleLogin) throws Exception {
        String check = checkDestinatari(false, receivers, ccReceivers, ccnReceivers, from);
        if (StringUtils.isBlank(check)) {
            try {

                RegistrationMailModel registrationMail = new RegistrationMailModel();
                registrationMail.setCfUtente(codiceFiscaleLogin);
                registrationMail.setEmailUtente(userAccountForm.getEmail());
                registrationMail.setNominativoTecnico(userAccountForm.getNome());
                registrationMail.setCognomeTecnico(userAccountForm.getCognome());
                registrationMail.setMessaggioMail(userAccountForm.getMessaggioAmministratore());
                registrationMail.setStatoRegistrazioneUtente("disabilitato");
                SAEntry saentry = null;
                if (userAccountForm.getStazioneAppaltante() != null) {
                    saentry = this.accountMapper.getSAInfo(userAccountForm.getStazioneAppaltante());
                }
                if (userAccountForm.getApplicativiSelezionati() != null) {
                    List<ApplicativiModel> applicativi = new ArrayList<>();
                    for (String applicativo : userAccountForm.getApplicativiSelezionati()) {
                        ApplicativiModel model = new ApplicativiModel();
                        if ("W9-PT".equals(applicativo)) {
                            model.setNome("Comunicazioni di programmi");
                            model.setDescrizione(
                                    "Comunicazioni di eventi di programmi triennali di lavori, forniture e servizi");
                        } else if ("W9-AEC".equals(applicativo)) {
                            model.setNome("Gestione appalti");
                            model.setDescrizione(
                                    "Compilazione e trasmissione ad ANAC di dati inerenti alle procedure d'appalto");
                        }
                        applicativi.add(model);
                    }
                    registrationMail.setListaApplicativi(applicativi);
                }
                if (saentry != null) {
                    registrationMail.setCfEnte(saentry.getCodFiscale());
                    registrationMail.setNomeEnte(saentry.getNome());
                    registrationMail.setTipologia(saentry.getCodice());
                    registrationMail.setIndirizzoEnte(saentry.getIndirizzo());
                    registrationMail.setTelefono(saentry.getTelefono());
                    registrationMail.setFax(saentry.getFax());
                    registrationMail.setEmailEnte(saentry.getEmail());
                }
                JavaMailSender mailSender = emailConfig.getJavaMailSender();
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                String senderName = this.wConfigManager.getConfigurationValue(W_CONFIG_APP_TITLE);
                senderName = senderName != null ? senderName : ".";
                helper.setFrom(new InternetAddress(from, senderName));

                String content = mailService.buildMail(registrationMail);

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(content, "text/html; charset=utf-8"); // <----

                // use a MimeMultipart as we need to handle the file attachments
                Multipart multipart = new MimeMultipart();

                // add the message body to the mime message
                multipart.addBodyPart(messageBodyPart);

                // Put all message parts in the message
                message.setContent(multipart);

                receivers = striptToEmptyList(receivers);
                ccReceivers = striptToEmptyList(ccReceivers);
                ccnReceivers = striptToEmptyList(ccnReceivers);

                helper.setTo(receivers.toArray(new String[0]));
                helper.setCc(ccReceivers.toArray(new String[0]));
                helper.setBcc(ccnReceivers.toArray(new String[0]));

                helper.setSubject(subject);
                mailSender.send(message);
            } catch (Exception e) {
                logger.error("Errore in invio mail ad admin", e);
                check = "EMAIL-GENERIC-ERROR";
            }
        }
        return check;
    }

    private String sendEmailAdmin(String from, String subject, List<String> receivers, List<String> ccReceivers,
                                  List<String> ccnReceivers, UserAccountForm userAccountForm) throws Exception {
        String check = checkDestinatari(true, receivers, ccReceivers, ccnReceivers, from);
        if (StringUtils.isBlank(check)) {
            try {

                RegistrationAdminMailModel registrationAdminMail = new RegistrationAdminMailModel();
                registrationAdminMail.setNomeUtente(userAccountForm.getNome());
                registrationAdminMail.setCognomeUtente(userAccountForm.getCognome());
                registrationAdminMail.setEmailUtente(userAccountForm.getEmail());
                registrationAdminMail.setStatoRegistrazioneUtente("disabilitato");
                registrationAdminMail.setTelefonoUtente(userAccountForm.getTelefono());
                registrationAdminMail.setMessaggioMail(userAccountForm.getMessaggioAmministratore());
                if (userAccountForm.getStazioneAppaltante() != null) {
                    SAEntry saentry = this.accountMapper.getSAInfo(userAccountForm.getStazioneAppaltante());
                    registrationAdminMail.setNomeEnte(saentry.getNome());
                }

                if (userAccountForm.getApplicativiSelezionati() != null) {
                    List<ApplicativiModel> applicativi = new ArrayList<>();
                    for (String applicativo : userAccountForm.getApplicativiSelezionati()) {
                        ApplicativiModel model = new ApplicativiModel();
                        if ("W9-PT".equals(applicativo)) {
                            model.setNome("Comunicazioni di programmi");
                            model.setDescrizione(
                                    "Comunicazioni di eventi di programmi triennali di lavori, forniture e servizi");
                        } else if ("W9-AEC".equals(applicativo)) {
                            model.setNome("Gestione appalti");
                            model.setDescrizione(
                                    "Compilazione e trasmissione ad ANAC di dati inerenti alle procedure d'appalto");
                        }
                        applicativi.add(model);
                    }
                    registrationAdminMail.setListaApplicativi(applicativi);
                }

                JavaMailSender mailSender = emailConfig.getJavaMailSender();
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                String contentAdmin = mailService.buildAdminMail(registrationAdminMail);
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(contentAdmin, "text/html; charset=utf-8"); // <----

                // use a MimeMultipart as we need to handle the file attachments
                Multipart multipart = new MimeMultipart();

                // add the message body to the mime message
                multipart.addBodyPart(messageBodyPart);

                // Put all message parts in the message
                message.setContent(multipart);

                String senderName = this.wConfigManager.getConfigurationValue(W_CONFIG_APP_TITLE);
                senderName = senderName != null ? senderName : ".";
                helper.setFrom(new InternetAddress(from, senderName));
                receivers = striptToEmptyList(receivers);
                ccReceivers = striptToEmptyList(ccReceivers);
                ccnReceivers = striptToEmptyList(ccnReceivers);
                helper.setTo(receivers.toArray(new String[0]));
                helper.setCc(ccReceivers.toArray(new String[0]));
                helper.setBcc(ccnReceivers.toArray(new String[0]));

                helper.setSubject(subject);

                String fileExtension = FilenameUtils.getExtension(userAccountForm.getFileName());
                String mimeType = Constants.FILE_TYPES.getOrDefault(fileExtension, "text/plain");

                BodyPart attachBodyPart = new MimeBodyPart();
                ByteArrayDataSource source = new ByteArrayDataSource(userAccountForm.getDocumentoFirmato(), mimeType + ";charset=UTF-8");
                attachBodyPart.setDataHandler(new DataHandler(source));
                attachBodyPart.setFileName(userAccountForm.getFileName());
                // add the message body to the mime message
                multipart.addBodyPart(attachBodyPart);
                // Put all message parts in the message
                message.setContent(multipart);
                mailSender.send(message);
            } catch (Exception e) {
                logger.error("Errore in invio mail ad admin", e);
                check = "EMAIL-GENERIC-ERROR";
            }
        }

        return check;
    }

    private String getProfiloByCodApp(String appCode) {
        String profiliString = this.wConfigManager.getConfigurationValue(CHIAVE_PROFILI_DEFAULT);
        if (StringUtils.isNotBlank(profiliString)) {
            String[] profili = profiliString.split(";");
            for (String profilo : profili) {
                ProfiloInfo pInfo = this.accountMapper.getInfoProfilo(profilo);
                if (pInfo.getCodapp().equals(appCode)) {
                    return profilo;
                }
            }
        }
        return null;
    }

    private String checkDestinatari(boolean admin, List<String> receivers, List<String> ccReceivers, List<String> ccnReceivers,
                                    String from) {

        if (isEmptyList(receivers) && isEmptyList(ccReceivers) && isEmptyList(ccnReceivers)) {
            logger.error("Deve essere presente almeno un destinatario");
            return admin ? "ADMIN-DESTINATARIO-NON-PRESENTE" : "DESTINATARIO-NON-PRESENTE";
        }
        if (from == null || from.equals("")) {
            logger.error("Mittente non presente");
            return "MITTENTE-NON-PRESENTE";
        }
        return null;
    }

    private boolean isEmptyList(List<String> lista) {
        return lista == null || lista.size() == 0;
    }

    private List<String> striptToEmptyList(List<String> lista) {
        return lista == null ? new ArrayList<String>() : lista;
    }

    public Boolean isCorrectPassword(String passwordInChiaro, String passwordCriptata) {
        String passwordDecifrata = "";
        try {
            ICriptazioneByte decriptatorePsw = FactoryCriptazioneByte.getInstance("LEG", passwordCriptata.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);
            passwordDecifrata = new String(decriptatorePsw.getDatoNonCifrato());
        } catch (Exception ex) {
        }
        return passwordDecifrata.equals(passwordInChiaro);
    }

    public ResponseUtentiStazioneAppaltante getUtentiStazioneAppaltante(final String codiceSA) {
        ResponseUtentiStazioneAppaltante risultato = new ResponseUtentiStazioneAppaltante();

        risultato.setEsito(true);

        try {

            List<UsrSysconEntry> listaUtenti = this.accountMapper.getUserListOfSA(codiceSA);

            if (listaUtenti == null) {
                listaUtenti = new ArrayList<>();
            }

            risultato.setData(listaUtenti);

        } catch (Exception t) {
            logger.error("Errore inaspettato durante il getUtentiStazioneAppaltante per codiceSA " + codiceSA, t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    public ResponseUtentiStazioneAppaltante getAllUtenti() {
        ResponseUtentiStazioneAppaltante risultato = new ResponseUtentiStazioneAppaltante();

        risultato.setEsito(true);

        try {

            List<UsrSysconEntry> listaUtenti = this.accountMapper.getAllUtenti();

            if (listaUtenti == null) {
                listaUtenti = new ArrayList<>();
            }

            risultato.setData(listaUtenti);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante il getAllUtenti", e);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseUpdateUtentiStazioneAppaltante setUtentiStazioneAppaltante(final UtentiStazioneAppaltanteForm form) {

        ResponseUpdateUtentiStazioneAppaltante risultato = new ResponseUpdateUtentiStazioneAppaltante();

        risultato.setEsito(true);

        try {

            if (form == null || StringUtils.isBlank(form.getCodice())) {
                throw new IllegalArgumentException("form o codice null");
            }

            String codiceSA = form.getCodice();

            this.accountMapper.deleteAllUsersForSA(codiceSA);

            if (form.getListaUtentiStazioneAppaltante() != null && form.getListaUtentiStazioneAppaltante().size() > 0) {
                for (Long syscon : form.getListaUtentiStazioneAppaltante()) {
                    int count = this.accountMapper.countUserExist(syscon);
                    if (count > 0) {
                        this.accountMapper.insertAssociazioneUteSa(syscon, codiceSA);
                    }
                }
            }

            risultato.setData(true);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante il setUtentiStazioneAppaltante", e);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    private boolean isUtenteAbilitatoTutteSA(final Long syscon) {

        if (syscon == null)
            return false;

        String opzioniUtenteString = accountMapper.getAbilitazioniUtente(syscon);

        if (StringUtils.isBlank(opzioniUtenteString))
            return false;

        List<String> opzioniUtente = Arrays.stream(opzioniUtenteString.split(Constants.OU_SEPARATORE_REGEX))
                .filter(str -> !str.isEmpty()).collect(Collectors.toList());

        if (opzioniUtente == null || opzioniUtente.isEmpty())
            return false;

        return opzioniUtente.contains(Constants.OU_ABILITA_TUTTE_SA);
    }

    public ResponsePersonalizzazioniUtente getConfigurazioniUtenti(Long syscon) {
        ResponsePersonalizzazioniUtente risultato = new ResponsePersonalizzazioniUtente();
        risultato.setEsito(true);
        try {
            PersonalizzazioneUtente personalizzazioni = this.accountMapper.getPersonalizzazioniUtente(syscon);
            List<CentroCosto> centriCosto = this.accountMapper.getCentriDiCostoUtente(syscon);
            personalizzazioni.setCentriCosto(centriCosto);
            risultato.setData(personalizzazioni);
        } catch (Exception t) {
            logger.error("Errore inaspettato durante il getConfigurazioniUtenti", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult modificaConfigurazioniUtenti(Long syscon, String sysab3, Long centroCosto) {
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);
        try {
            this.accountMapper.modificaPersonalizzazioniUtente(sysab3, centroCosto, syscon);

        } catch (Exception t) {
            logger.error("Errore inaspettato durante il modificaConfigurazioniUtenti", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUpdateUtentiStazioneAppaltante.ERROR_UNEXPECTED);
        }
        return risultato;
    }


    private String handleOpzioniUtente(String opzioniUtente) {
        // preparo la lista
        List<String> opzioniUtenteList;

        // se le opzioni recuperate dalla w_config sono blank
        if (StringUtils.isBlank(opzioniUtente)) {
            // inizializzo a vuota la lista
            opzioniUtenteList = new ArrayList<>();
        } else {
            // altrimenti converto le opzioni utente in lista
            opzioniUtenteList = new ArrayList<>(Arrays.asList(opzioniUtente.split(Constants.OU_SEPARATORE_REGEX)));
        }

        if (!opzioniUtenteList.contains(Constants.OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI)) {
            opzioniUtenteList.add(Constants.OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI);
        }

        // Aggiunta controlli GDPR
        opzioniUtenteList.add(Constants.OU_UTENTE_PASSWORD_SICURA);

        return getStringOpzioniUtente(opzioniUtenteList);
    }

    private String getStringOpzioniUtente(final List<String> listaOpzioniUtente) {
        String opzioniUtenteString = null;

        if (listaOpzioniUtente != null && listaOpzioniUtente.size() > 0) {
            opzioniUtenteString = "";
            listaOpzioniUtente.sort(OUOrderComparator.createOUOrderComparator());
            for (String opzione : listaOpzioniUtente) {
                opzioniUtenteString += opzione + "|";
            }
        }

        return opzioniUtenteString;
    }

    public String calcolaCodificaAutomatica(String entita, String campoChiave) throws Exception {
        String codice = "1";
        String formatoCodice = null;
        String codcal = null;
        Long cont = null;
        try {
            String query = "select CODCAL, CONTAT from G_CONFCOD where NOMENT = '" + entita + "'";
            List<Map<String, Object>> confcod = sqlMapper.select(query);
            if (confcod != null && confcod.size() > 0) {
                for (Map<String, Object> row : confcod) {
                    if (row.containsKey("CODCAL")) {
                        codcal = row.get("CODCAL").toString();
                    } else {
                        codcal = row.get("codcal").toString();
                    }
                    if (row.containsKey("CONTAT")) {
                        cont = Long.valueOf(row.get("CONTAT").toString());
                    } else {
                        cont = Long.valueOf(row.get("contat").toString());
                    }
                    break;
                }
                boolean codiceUnivoco = false;
                int numeroTentativi = 0;
                StringBuffer strBuffer = null;
                long tmpContatore = cont.longValue();
                while (!codiceUnivoco && numeroTentativi < 5) {
                    strBuffer = new StringBuffer("");
                    // Come prima cosa eseguo l'update del contatore
                    tmpContatore++;
                    sqlMapper.execute(
                            "update G_CONFCOD set contat = " + tmpContatore + " where NOMENT = '" + entita + "'");

                    strBuffer = new StringBuffer("");
                    formatoCodice = codcal;
                    while (formatoCodice.length() > 0) {
                        switch (formatoCodice.charAt(0)) {
                            case '<': // Si tratta di un'espressione numerica
                                String strNum = formatoCodice.substring(1, formatoCodice.indexOf('>'));
                                if (strNum.charAt(0) == '0') {
                                    // Giustificato a destra
                                    for (int i = 0; i < (strNum.length() - String.valueOf(tmpContatore).length()); i++) {
                                        strBuffer.append('0');
                                    }
                                }
                                strBuffer.append(String.valueOf(tmpContatore));

                                formatoCodice = formatoCodice.substring(formatoCodice.indexOf('>') + 1);
                                break;
                            case '"': // Si tratta di una parte costante
                                strBuffer.append(formatoCodice.substring(1, formatoCodice.indexOf('"', 1)));
                                formatoCodice = formatoCodice.substring(formatoCodice.indexOf('"', 1) + 1);
                                break;
                        }
                    }
                    int occorrenze = sqlMapper
                            .count(entita + " WHERE " + campoChiave + " ='" + strBuffer.toString() + "'");
                    if (occorrenze == 0) {
                        codiceUnivoco = true;
                        codice = strBuffer.toString();
                    } else {
                        numeroTentativi++;
                    }
                }
                if (!codiceUnivoco) {
                    logger.error("numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
                    throw new Exception(
                            "numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
                }
            }
        } catch (Exception ex) {
            logger.error("Errore inaspettato durante il calcolo per la codifica automatica " + entita, ex);
            throw new Exception(ex);
        }
        return codice;
    }
}
