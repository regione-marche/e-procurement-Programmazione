package it.maggioli.ssointegrms.services.general.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.common.EChangePasswordType;
import it.maggioli.ssointegrms.domain.*;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.general.InternalServerException;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.*;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.*;
import it.maggioli.ssointegrms.repositories.*;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.*;
import it.maggioli.ssointegrms.utils.*;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import it.toscana.regione.sitat.service.utils.UtilityFiscali;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.crypto.SecretKey;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${application.internalAuthentication.apiVersion}")
    private String apiVersion;

    @Value("${application.internalAuthentication.token.tokenExpireTime}")
    private int tokenExpireTime;

    @Value("${application.internalAuthentication.token.refreshTokenExpireTime}")
    private int refreshTokenExpireTime;

    @Value("${application.internalAuthentication.recuperoPasswordUrl}")
    private String recuperoPasswordUrl;

    @Value("${application.internalAuthentication.useExternalUrlForRecuperoPassword}")
    private Boolean useExternalUrlForRecuperoPassword;

    @Value("${it.eldasoft.account.opzioniDefault}")
    private String ouDefault;

    @Value("${application.jwt.claim.userCf.name}")
    private String jwtClaimUserCfName;

    @Value("${application.jwt.signatureAlgorithm}")
    private String jwtSignatureAlgorithm;

    @Autowired
    private WConfigService wConfigService;

    @Autowired
    private LoginKOService loginKOService;

    @Autowired
    private GDPRService gdprService;

    @Autowired
    private StoUteSysService stoUteSysService;

    @Autowired
    private WLogEventiService wLogEventiService;

    @Autowired
    private UserCriteriaRepository userCriteriaRepository;

    @Autowired
    private UsrCancRepository usrCancRepository;

    @Autowired
    private ProfiloService profiloService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailComponent emailComponent;

    @Autowired
    private WAccgrpRepository wAccgrpRepository;

    @Autowired
    private WGruppiRepository wGruppiRepository;

    @Autowired
    private GPermessiRepository gPermessiRepository;

    @Autowired
    private WCachemodparRepository wCachemodparRepository;

    @Autowired
    private UffintService uffintService;

    @Autowired
    private WUsrTokenRepository wUsrTokenRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private GestioneUtentiService gestioneUtentiService;

    @Autowired
    private OAuth2RestComponent oAuth2RestComponent;

    @Autowired
    private MTokenService mTokenService;

    @Autowired
    private WSsoJwtTokenRepository wSsoJwtTokenRepository;

    /**
     * Servizio per l'autenticazione
     * <p>
     * Messaggi di errore:
     * <p>
     * LOGIN_UNAUTHORIZED: Se l'utente non e' stato trovato oppure la vecchia
     * password e' errata
     * <p>
     * LOGIN_MAX_TENTATIVI: per indicare che la nuova password e la conferma
     * password non corrispondono
     * <p>
     * LOGIN_ACCOUNT_EXPIRED: Se l'account utente e' scaduto
     * <p>
     * LOGIN_PASSWORD_EXPIRED: Se la password e' scaduta
     * <p>
     * LOGIN_FIRST_ACCESS: Se e' il primo accesso
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LoginSuccessDTO executeAuthentication(final AuthenticationDTO authenticationDTO)
            throws CriptazioneException {

        if (authenticationDTO == null)
            throw new IllegalArgumentException("authenticationDTO null");

        LOGGER.debug("Execution start UserServiceImpl::executeAuthentication for user [ {} ]",
                authenticationDTO.getUsername());

        int livelloEvento = LogEventiUtils.LIVELLO_INFO;
        String errMsgEvento = "";

        AccountConfigurationDTO accountConfigurationDTO = getAccountConfiguration();

        // TODO blocco se ci sono piu' utenze con la stessa syslogin

        User found = userRepository.findByLoginIgnoreCase(authenticationDTO.getUsername());

        if (found == null) {
            LOGGER.error("Username o password errati per username [ {} ]", authenticationDTO.getUsername());

            // inserimento su g_loginko
            loginKOService.insertLoginKO(authenticationDTO.getUsername(), authenticationDTO.getIpAddress());

            livelloEvento = LogEventiUtils.LIVELLO_ERROR;
            errMsgEvento = "Autenticazione fallita (utente " + authenticationDTO.getUsername() + ")";

            throw new BadCredentialsException();
        }

        Long sysconFound = found.getSyscon();

        String passwordCifrata = super.getValoreCifrato(authenticationDTO.getPassword());

        if (!passwordCifrata.equals(found.getPassword())) {

            LOGGER.error("Username o password errati per username [ {} ]", found.getLogin());

            // inserimento su g_loginko
            loginKOService.insertLoginKO(found.getLogin(), authenticationDTO.getIpAddress());

            livelloEvento = LogEventiUtils.LIVELLO_ERROR;
            errMsgEvento = "Autenticazione fallita (utente " + found.getLogin() + ")";

            throw new BadCredentialsException();

        }

        // check presenza certificato e motivazione per utenti 47 e 48
        if (isMTokenRequired(found)) {
            if (StringUtils.isBlank(authenticationDTO.getCertificato())) {
                LOGGER.error("Certificato M-TOKEN non trovato");
                throw new CertificatoMTokenNotFoundException();
            }

            if (StringUtils.isBlank(authenticationDTO.getMotivazione())) {
                LOGGER.error("Motivazione per accesso con M-TOKEN non trovata");
                throw new MotivazioneMTokenNotFoundException();
            }
        }

        if (!isUserAbilitato(found)) {

            LOGGER.error("Account utente per username [ {} ] disabilitato", found.getLogin());

            // inserimento su g_loginko
            loginKOService.insertLoginKO(found.getLogin(), authenticationDTO.getIpAddress());

            livelloEvento = LogEventiUtils.LIVELLO_ERROR;
            errMsgEvento = "L'account " + found.getLogin() + " e' disabilitato";
            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginEvent(sysconFound,
                    authenticationDTO.getIpAddress(), codiceProdotto);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            throw new UserDisabledException();
        }

        // check GDPR 3A (tentativi accesso)
        boolean result3A = gdprService.executeCheck3A(accountConfigurationDTO, found, authenticationDTO.getIpAddress());

        if (!result3A) {

            LOGGER.error("Numero tentativi massimi raggiunto per username [ {} ]", authenticationDTO.getUsername());

            livelloEvento = LogEventiUtils.LIVELLO_ERROR;
            errMsgEvento = "Autenticazione fallita (utente " + found.getLogin() + ")";
            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginEvent(sysconFound,
                    authenticationDTO.getIpAddress(), codiceProdotto);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            logEventiDTO = LogEventiUtils.createLoginLockEvent(sysconFound, found.getLogin(),
                    authenticationDTO.getIpAddress());
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage("");
            wLogEventiService.insertLogEvent(logEventiDTO);

            throw new MaxTentativiException();

        }

        // check GDPR 6B (scadenza utente)
        boolean result6B = gdprService.executeCheck6B(accountConfigurationDTO, found);

        if (!result6B) {
            LOGGER.error("Account utente per username [ {} ] scaduto", found.getLogin());

            // disabilito utente (sysdisab = 1)
            gestioneUtentiService.disabilitaUtente(sysconFound);

            livelloEvento = LogEventiUtils.LIVELLO_ERROR;
            errMsgEvento = "L'account " + found.getLogin() + " e' scaduto in quanto sono trascorsi almeno "
                    + accountConfigurationDTO.getDurataAccount() + " giorni di inattivita'!";
            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginEvent(sysconFound,
                    authenticationDTO.getIpAddress(), codiceProdotto);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            throw new AccountExpiredException();
        }

        // check GDPR 6A (scadenza password)
        boolean result6A = gdprService.executeCheck6A(accountConfigurationDTO, found);

        if (!result6A) {
            LOGGER.error("Password utente per username [ {} ] scaduta", found.getLogin());

            livelloEvento = LogEventiUtils.LIVELLO_WARNING;
            errMsgEvento = "Login effettuato ma con cambio password necessario (utente " + found.getLogin() + ")";
            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginEvent(sysconFound,
                    authenticationDTO.getIpAddress(), codiceProdotto);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            throw new PasswordExpiredException();
        }

        // check GDPR 4A (primo accesso)
        boolean result4A = gdprService.executeCheck4A(found);

        if (!result4A) {
            LOGGER.error("Primo accesso per username [ {} ]", found.getLogin());

            livelloEvento = LogEventiUtils.LIVELLO_WARNING;
            errMsgEvento = "Login effettuato ma con cambio password necessario (utente " + found.getLogin() + ")";

            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginEvent(sysconFound,
                    authenticationDTO.getIpAddress(), codiceProdotto);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            throw new FirstAccessException();
        }

        // login corretta quindi rimuovo i tentativi falliti
        loginKOService.clearLoginAttempts(found.getLogin());

        // update last login date
        userRepository.updateDataUltimoAccesso(sysconFound, new Date());

        // refresh del dato
        found = userRepository.findById(sysconFound).orElse(null);

        if (isMTokenRequired(found)) {
            boolean mTokenOk = mTokenService.loginMToken(authenticationDTO);
            if (!mTokenOk) {
                throw new MTokenLoginFailedException();
            }
        } else {
            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginEvent(sysconFound,
                    authenticationDTO.getIpAddress(), codiceProdotto);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);
        }


        UserDTO userDTO = getUserDTOFromUser(found);

        String jwtToken = getInternalToken(userDTO, false);
        String refreshToken = getInternalToken(userDTO, true);

        String loginUppercase = found.getLogin().toUpperCase();

        // Delete da w_ssojwttoken
        if (wSsoJwtTokenRepository.existsById(loginUppercase))
            wSsoJwtTokenRepository.deleteById(loginUppercase);
        // Insert in w_ssojwttoken
        WSsoJwtToken wSsoJwtToken = new WSsoJwtToken();
        wSsoJwtToken.setSyslogin(loginUppercase);
        wSsoJwtToken.setAuthCode(generateNewAuthorizationCode());
        wSsoJwtToken.setJwtToken(jwtToken);
        wSsoJwtToken.setRefreshToken(refreshToken);
        wSsoJwtToken.setDtCreazione(new Date());
        wSsoJwtToken = wSsoJwtTokenRepository.save(wSsoJwtToken);

        LoginSuccessDTO dto = new LoginSuccessDTO();
        dto.setToken(jwtToken);
        dto.setRefreshToken(refreshToken);

        return dto;

    }

    /**
     * Servizio per il cambio password
     * <p>
     * Messaggi di errore:
     * <p>
     * CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH: per indicare che la nuova password
     * e la conferma password non corrispondono
     * <p>
     * LOGIN_UNAUTHORIZED: Se l'utente non e' stato trovato oppure la vecchia
     * password e' errata
     * <p>
     * LOGIN_ACCOUNT_EXPIRED: Se l'account utente e' scaduto
     * <p>
     * CHANGE_PASSWORD_LENGTH/CHANGE_PASSWORD_LENGTH_ADMIN: Se la lunghezza della
     * nuova password inserita non corrisponde alla minima richiesta per utente e
     * per amministratore
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS: Sono stati inseriti nella
     * nuova password dei caratteri non consentiti oppure non e' presente la
     * tipologia di caratteri minimi richiesti
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS: Nella nuova password non
     * sono presenti caratteri in minuscolo
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS: Nella nuova password non
     * sono presenti caratteri in maiuscolo
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS: Nella nuova password non sono
     * presenti caratteri numerici
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS: Nella nuova password non sono
     * presenti caratteri speciali
     * <p>
     * CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS: La nuova password contiene 4 o
     * piu' caratteri consecutivi che appartengono al nome utente
     * <p>
     * CHANGE_PASSWORD_ALREADY_USED: La nuova password e' gia' stata utilizzata
     * precedentemente
     * <p>
     * CHANGE_PASSWORD_CANNOT_CHANGE_TIME: Il tempo passato dal precedente cambio
     * password e' minore di quello impostato in configurazione
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO executeChangePassword(final ChangePasswordDTO changePasswordDTO) throws CriptazioneException {

        if (changePasswordDTO == null)
            throw new IllegalArgumentException("changePasswordDTO null");

        LOGGER.debug("Execution start UserServiceImpl::executeChangePassword for user [ {} ]",
                changePasswordDTO.getUsername());

        int livelloEvento = LogEventiUtils.LIVELLO_INFO;
        String errMsgEvento = "";

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
            LOGGER.error("Username o password errati per username [ {} ]", changePasswordDTO.getUsername());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH);

            return response;
        }

        AccountConfigurationDTO accountConfigurationDTO = getAccountConfiguration();

        User found = userRepository.findByLoginIgnoreCase(changePasswordDTO.getUsername());

        if (found == null) {
            LOGGER.error("Username o password errati per username [ {} ]", changePasswordDTO.getUsername());

            throw new BadCredentialsException();
        }

        Long sysconFound = found.getSyscon();

        String vecchiaPasswordCifrata = super.getValoreCifrato(changePasswordDTO.getOldPassword());

        if (!vecchiaPasswordCifrata.equals(found.getPassword())) {

            LOGGER.error("Username o password errati per username [ {} ]", found.getLogin());

            throw new BadCredentialsException();

        }

        if (!isUserAbilitato(found)) {
            LOGGER.error("Utente disabilitato per username [ {} ]", found.getLogin());

            // inserimento su g_loginko
            loginKOService.insertLoginKO(found.getLogin(), changePasswordDTO.getIpAddress());

            throw new UserDisabledException();
        }

        // check GDPR 6B (scadenza utente)
        boolean result6B = gdprService.executeCheck6B(accountConfigurationDTO, found);

        if (!result6B) {
            LOGGER.error("Account utente per username [ {} ] scaduto", found.getLogin());
            throw new AccountExpiredException();
        }

        // check GDPR 1 (lunghezza password)
        boolean result1 = gdprService.executeCheck1(found, changePasswordDTO);

        if (!result1) {
            LOGGER.error("Lunghezza password per username [ {} ] non valida", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            if (super.isUserAdministrator(found.getSyspwbou()))
                response.getMessages().add(AppConstants.CHANGE_PASSWORD_LENGTH_ADMIN);
            else
                response.getMessages().add(AppConstants.CHANGE_PASSWORD_LENGTH);

        }

        // check GDPR 2 (complessita' password)
        GDPRCheck2DTO result2 = gdprService.executeCheck2(found, changePasswordDTO);

        if (!result2.pass()) {
            LOGGER.error("Complessita' password per username [ {} ] non sufficiente", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);

            if (!result2.isAllowedCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS);
            if (!result2.isLowerCaseCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS);
            if (!result2.isUpperCaseCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS);
            if (!result2.isNumberCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS);
            if (!result2.isSpecialCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS);
        }

        // check GDPR 3B (caratteri consecutivi presenti nello username)
        boolean result3B = gdprService.executeCheck3B(found, changePasswordDTO);
        if (!result3B) {
            LOGGER.error("Contenuto password per username [ {} ] non valido", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS);
        }

        // check GDPR 5A e 5C (Password gia' utilizzata)
        boolean result5A5C = gdprService.executeCheck5A5C(found, changePasswordDTO);
        if (!result5A5C) {
            LOGGER.error("Password gia' utilizzata per username [ {} ]", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_ALREADY_USED);
        }

        // check intervallo minimo cambio password
        boolean resultIntervalloMinimoCambioPassword = checkPasswordCambiataDiRecente(accountConfigurationDTO, found);
        if (!resultIntervalloMinimoCambioPassword) {
            LOGGER.error("Password gia' cambiata di recente per username [ {} ]", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_CANNOT_CHANGE_TIME);
        }

        if (AppConstants.RESPONSE_DONE_Y.equals(response.getDone())) {

            // change password

            boolean result4A = gdprService.executeCheck4A(found);

            EChangePasswordType changeType;
            if (result4A) {
                // password scaduta
                changeType = EChangePasswordType.CHANGE_EXPIRED;
            } else {
                // primo accesso
                changeType = EChangePasswordType.CHANGE;
            }

            String nuovaPasswordCifrata = super.getValoreCifrato(changePasswordDTO.getNewPassword());

            userRepository.updatePassword(sysconFound, nuovaPasswordCifrata);

            found = userRepository.findById(sysconFound).orElse(null);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(found.getLogin());
            userDTO.setCodiceFiscale(found.getCodiceFiscale());
            userDTO.setNome(found.getSysute());
            userDTO.setCognome(found.getSysute());
            userDTO.setEmail(null);

            response.setResponse(userDTO);

            // insert into stoutesys
            stoUteSysService.insertChangedPassword(found, nuovaPasswordCifrata);

            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createChangePasswordEvent(sysconFound, found.getLogin(),
                    changePasswordDTO.getIpAddress(), changeType);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

        }

        return response;
    }

    /**
     * Servizio per il cambio password da utente autenticato
     * <p>
     * Messaggi di errore:
     * <p>
     * CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH: per indicare che la nuova password
     * e la conferma password non corrispondono
     * <p>
     * LOGIN_UNAUTHORIZED: Se l'utente non e' stato trovato oppure la vecchia
     * password e' errata
     * <p>
     * LOGIN_ACCOUNT_EXPIRED: Se l'account utente e' scaduto
     * <p>
     * CHANGE_PASSWORD_LENGTH/CHANGE_PASSWORD_LENGTH_ADMIN: Se la lunghezza della
     * nuova password inserita non corrisponde alla minima richiesta per utente e
     * per amministratore
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS: Sono stati inseriti nella
     * nuova password dei caratteri non consentiti oppure non e' presente la
     * tipologia di caratteri minimi richiesti
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS: Nella nuova password non
     * sono presenti caratteri in minuscolo
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS: Nella nuova password non
     * sono presenti caratteri in maiuscolo
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS: Nella nuova password non sono
     * presenti caratteri numerici
     * <p>
     * CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS: Nella nuova password non sono
     * presenti caratteri speciali
     * <p>
     * CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS: La nuova password contiene 4 o
     * piu' caratteri consecutivi che appartengono al nome utente
     * <p>
     * CHANGE_PASSWORD_ALREADY_USED: La nuova password e' gia' stata utilizzata
     * precedentemente
     * <p>
     * CHANGE_PASSWORD_CANNOT_CHANGE_TIME: Il tempo passato dal precedente cambio
     * password e' minore di quello impostato in configurazione
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO executeUserChangePassword(final UserDTO currentUser, final ChangePasswordUserDTO changePasswordUserDTO) throws CriptazioneException {

        if (changePasswordUserDTO == null)
            throw new IllegalArgumentException("changePasswordUserDTO null");

        LOGGER.debug("Execution start UserServiceImpl::executeUserChangePassword for user [ {} ]",
                currentUser.getUsername());

        Long syscon = currentUser.getSyscon();
        String username = currentUser.getUsername();

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setUsername(username);
        changePasswordDTO.setOldPassword(changePasswordUserDTO.getOldPassword());
        changePasswordDTO.setNewPassword(changePasswordUserDTO.getNewPassword());
        changePasswordDTO.setConfirmNewPassword(changePasswordUserDTO.getConfirmNewPassword());
        changePasswordDTO.setIpAddress(changePasswordUserDTO.getIpAddress());

        int livelloEvento = LogEventiUtils.LIVELLO_INFO;
        String errMsgEvento = "";

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
            LOGGER.error("Username o password errati per username [ {} ]", username);

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH);

            return response;
        }

        AccountConfigurationDTO accountConfigurationDTO = getAccountConfiguration();

        User found = userRepository.findById(currentUser.getSyscon()).orElse(null);

        if (found == null) {
            LOGGER.error("Username o password errati per username [ {} ]", username);

            throw new BadCredentialsException();
        }

        String vecchiaPasswordCifrata = super.getValoreCifrato(changePasswordDTO.getOldPassword());

        if (!vecchiaPasswordCifrata.equals(found.getPassword())) {

            LOGGER.error("Username o password errati per username [ {} ]", found.getLogin());

            throw new BadCredentialsException();

        }

        if (!isUserAbilitato(found)) {
            LOGGER.error("Utente disabilitato per username [ {} ]", found.getLogin());

            // inserimento su g_loginko
            loginKOService.insertLoginKO(found.getLogin(), changePasswordDTO.getIpAddress());

            throw new UserDisabledException();
        }

        // check GDPR 6B (scadenza utente)
        boolean result6B = gdprService.executeCheck6B(accountConfigurationDTO, found);

        if (!result6B) {
            LOGGER.error("Account utente per username [ {} ] scaduto", found.getLogin());
            throw new AccountExpiredException();
        }

        // check GDPR 1 (lunghezza password)
        boolean result1 = gdprService.executeCheck1(found, changePasswordDTO);

        if (!result1) {
            LOGGER.error("Lunghezza password per username [ {} ] non valida", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            if (super.isUserAdministrator(found.getSyspwbou()))
                response.getMessages().add(AppConstants.CHANGE_PASSWORD_LENGTH_ADMIN);
            else
                response.getMessages().add(AppConstants.CHANGE_PASSWORD_LENGTH);

        }

        // check GDPR 2 (complessita' password)
        GDPRCheck2DTO result2 = gdprService.executeCheck2(found, changePasswordDTO);

        if (!result2.pass()) {
            LOGGER.error("Complessita' password per username [ {} ] non sufficiente", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);

            if (!result2.isAllowedCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS);
            if (!result2.isLowerCaseCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS);
            if (!result2.isUpperCaseCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS);
            if (!result2.isNumberCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS);
            if (!result2.isSpecialCharacters())
                response.getMessages()
                        .add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS);
        }

        // check GDPR 3B (caratteri consecutivi presenti nello username)
        boolean result3B = gdprService.executeCheck3B(found, changePasswordDTO);
        if (!result3B) {
            LOGGER.error("Contenuto password per username [ {} ] non valido", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS);
        }

        // check GDPR 5A e 5C (Password gia' utilizzata)
        boolean result5A5C = gdprService.executeCheck5A5C(found, changePasswordDTO);
        if (!result5A5C) {
            LOGGER.error("Password gia' utilizzata per username [ {} ]", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_ALREADY_USED);
        }

        // check intervallo minimo cambio password
        boolean resultIntervalloMinimoCambioPassword = checkPasswordCambiataDiRecente(accountConfigurationDTO, found);
        if (!resultIntervalloMinimoCambioPassword) {
            LOGGER.error("Password gia' cambiata di recente per username [ {} ]", found.getLogin());

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.getMessages().add(AppConstants.CHANGE_PASSWORD_CANNOT_CHANGE_TIME);
        }

        if (AppConstants.RESPONSE_DONE_Y.equals(response.getDone())) {

            // change password
            EChangePasswordType changeType = EChangePasswordType.CHANGE_BY_ADMIN;

            String nuovaPasswordCifrata = super.getValoreCifrato(changePasswordDTO.getNewPassword());

            userRepository.updatePassword(syscon, nuovaPasswordCifrata);

            // recupero nuovamente l'utente dopo la modifica
            found = userRepository.findById(syscon).orElse(null);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(found.getLogin());
            userDTO.setCodiceFiscale(found.getCodiceFiscale());
            userDTO.setNome(found.getSysute());
            userDTO.setCognome(found.getSysute());
            userDTO.setEmail(null);

            response.setResponse(userDTO);

            // insert into stoutesys
            stoUteSysService.insertChangedPassword(found, nuovaPasswordCifrata);

            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createChangePasswordEvent(syscon, found.getLogin(),
                    changePasswordDTO.getIpAddress(), changeType);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO executeCheckToken(final String token, final String loginMode)
            throws TokenExpiredException, TokenInvalidException {

        LOGGER.debug("Execution start UserServiceImpl::executeCheckToken for token [ {} ]", token);

        if (StringUtils.isBlank(token))
            throw new IllegalArgumentException("token null");

        DecodedJWT jwt = null;
        try {
            byte[] jwtKey = getJWTKey();
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);

            if (!AppConstants.LOGIN_MODE_TOSCANA.equals(loginMode)) {
                Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            } else {
                // TODO da rimuovere prima di subito
                DecodedJWT tempClaims = JWT.decode(token);
                Claim internalClaim = tempClaims.getClaim("internal");
                boolean internal = internalClaim != null && internalClaim.asBoolean() != null ? internalClaim.asBoolean() : false;
                if (internal) {
                    // Verifica interna (richiesta da Toscana per il primo periodo di produzione)
                    Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
                } else {
                    // Verifica Oauth2
                    PublicKey publicKey = oAuth2RestComponent.retrievePublicKey();
                    Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token);
                }
            }

            jwt = JWT.decode(token);
            LOGGER.debug("Token decoded");
            if (jwt.getExpiresAt().getTime() < System.currentTimeMillis())
                throw new TokenExpiredException("Token expired");
        } catch (CriptazioneException e) {
            LOGGER.error("JWT Key error: {}", e.getMessage());
            throw new TokenInvalidException("JWT Key error");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token expired: {}", e.getMessage());
            throw new TokenExpiredException("Token expired");
        } catch (JwtException e) {
            LOGGER.error("Invalid token: {}", e.getMessage());
            throw new TokenInvalidException("Token Invalid");
        }

        LOGGER.debug("isExpired: {}", (jwt.getExpiresAt().getTime() < System.currentTimeMillis()));
        return getUserFromDecodedJWT(token, jwt, loginMode);
    }

    @Override
    public InitRicercaUtentiDTO initRicercaUtenti(final UserDTO currentUserDTO) {

        LOGGER.debug("Execution start UserServiceImpl::initRicercaUtenti");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO) || isUserAbilitatoGestioneUtentiReadonly(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        InitRicercaUtentiDTO dto = new InitRicercaUtentiDTO();

        User currentUser = getUserFromUserDTO(currentUserDTO);
        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        dto.setUtenteDelegatoGestioneUtenti(utenteDelegatoGestioneUtenti);
        dto.setStazioniAppaltantiAssociate(currentUser.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList()));
        dto.setProfiliAssociati(currentUser.getProfili().parallelStream().map(Profilo::getCodice).collect(Collectors.toList()));
        dto.setRegistrazioneLoginCF(isRegistrazioneLoginCF(utenteDelegatoGestioneUtenti));

        return dto;
    }

    @Override
    public ResponseListaDTO loadListaUtenti(final RicercaUtentiFormDTO form, final UserDTO currentUserDTO) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::loadListaUtenti for form [ {} ]", form);

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO) || isUserAbilitatoGestioneUtentiReadonly(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        User currentUser = getUserFromUserDTO(currentUserDTO);

        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);
        boolean registrazioneLoginCF = isRegistrazioneLoginCF(utenteDelegatoGestioneUtenti);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        // paginazione e ordinamento
        final Integer skip = form.getSkip();
        final Integer take = form.getTake();
        final String sort = form.getSort();
        final String sortDirection = form.getSortDirection();
        final String sortKey = StringUtils.isNotBlank(sort) ? sort : "sysute";
        final String sortDirectionKey = StringUtils.isNotBlank(sortDirection) ? sortDirection : "asc";
        final String ufficioIntestatarioKey = form.getUfficioIntestatarioKey();

        // clear attributi con stringa vuota
        form.setDenominazione(StringUtils.stripToNull(form.getDenominazione()));
        form.setUsername(StringUtils.stripToNull(form.getUsername()));
        form.setUsernameCF(StringUtils.stripToNull(form.getUsernameCF()));
        form.setCodiceFiscale(StringUtils.stripToNull(form.getCodiceFiscale()));
        form.setEmail(StringUtils.stripToNull(form.getEmail()));
        form.setGestioneUtenti(StringUtils.stripToNull(form.getGestioneUtenti()));
        form.setProfiloKey(StringUtils.stripToNull(form.getProfiloKey()));

        if (StringUtils.isNotBlank(ufficioIntestatarioKey)) {
            List<String> ufficioIntestatarioKeys = form.getUfficioIntestatarioKeys();
            if (ufficioIntestatarioKeys == null)
                ufficioIntestatarioKeys = new ArrayList<>();

            if (!ufficioIntestatarioKeys.contains(ufficioIntestatarioKey))
                ufficioIntestatarioKeys.add(ufficioIntestatarioKey);

            form.setUfficioIntestatarioKeys(ufficioIntestatarioKeys);
        }

        if (form.getUfficioIntestatarioKeys() != null && !form.getUfficioIntestatarioKeys().isEmpty()) {
            form.setUfficioIntestatarioKeys(form.getUfficioIntestatarioKeys().parallelStream().map(StringUtils::stripToNull).collect(Collectors.toList()));
        }

        // set stazione appaltante se l'utente e' delegato alla gestione utenti e ne ha una sola
        if (utenteDelegatoGestioneUtenti && currentUser.getUffints() != null) {
            if (currentUser.getUffints().size() == 1) {
                Uffint uffint = currentUser.getUffints().stream().findFirst().orElse(null);
                // C'e' sicuramente una stazione appaltante
                if (uffint != null) {
                    form.setUfficioIntestatarioKeys(Collections.singletonList(uffint.getCodice()));
                }
            } else {
                // check che l'utente ricerchi per una stazione appaltante a lui collegata oppure se la ricerca e' vuota
                // limitare indirettamente la ricerca alle stazioni appaltanti di competenza
                List<String> currentUffintsCodeins = currentUser.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList());
                if (form.getUfficioIntestatarioKeys() != null && !form.getUfficioIntestatarioKeys().isEmpty()) {
                    String codeinToSearch = form.getUfficioIntestatarioKeys().get(0);
                    if (!currentUffintsCodeins.contains(codeinToSearch)) {
                        // Errore
                        LOGGER.error("L'utente [ {} ] non ha i permessi di ricercare per l'ufficio intestatario [ {} ]", currentUserDTO.getSyscon(), codeinToSearch);

                        throw new UserPermissionException();
                    } else {
                        List<String> codeinsToSearch = new ArrayList<>();
                        codeinsToSearch.add(codeinToSearch);
                        form.setUfficioIntestatarioKeys(codeinsToSearch);
                    }
                } else {
                    // L'utente non ha filtrato per stazioni appaltanti, imposto in modo implicito le stazioni appaltanti a lui associate
                    form.setUfficioIntestatarioKeys(currentUffintsCodeins);
                }
            }
        }

        List<UserDTO> usersDTOList = new ArrayList<>();

        if (skip != null && take != null && skip >= 0L && take > 0L) {
            // ho la paginazione attiva

            UserSearchResultDTO searchResult = userCriteriaRepository.loadListaUtenti(form.getDenominazione(),
                    form.getUsername(), form.getUsernameCF(), form.getAbilitato(), form.getCodiceFiscale(), form.getEmail(),
                    form.getUfficioIntestatarioKeys(), form.getGestioneUtenti(), form.getAmministratore(),
                    form.getProfiloKey(), utenteDelegatoGestioneUtenti, registrazioneLoginCF, skip, take, sortKey, sortDirectionKey, true);

            response.setTotalCount(searchResult.getTotalCount());

            if (searchResult.getUserList() != null) {
                searchResult.getUserList().forEach(user -> {
                    UserDTO userDTO = getUserDTOFromUser(user);
                    usersDTOList.add(userDTO);
                });
            }

            response.setResponse(usersDTOList);
        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO insertUtente(final UserDTO currentUserDTO, final UserInsertDTO form, final String ipAddress)
            throws CriptazioneException {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::insertUtente for form [ {} ]", form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueInsert = true;
        List<String> errorMessages = new ArrayList<>();

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        // Utente loggato in sessione
        User currentUser = getUserFromUserDTO(currentUserDTO);
        final boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);
        final boolean registrazioneLoginCF = isRegistrazioneLoginCF(utenteDelegatoGestioneUtenti);

        final boolean isCurrentUserAdministrator = isUserDTOAdministrator(currentUserDTO);

        UserInsertDTO clearedForm = parseUtenteInsertDTO(form);

        // Prendo usernameCF come username e codice fiscale se sono un'utente delegato ed e' attiva la funzionalita' di registrazione.loginCF = 1
        String username = registrazioneLoginCF ? clearedForm.getUsernameCF() : clearedForm.getUsername();
        String codiceFiscale = registrazioneLoginCF ? clearedForm.getUsernameCF() : clearedForm.getCodiceFiscale();

        // Set password sicura per controlli GDPR
        boolean passwordSicuraEnabled = utenteDelegatoGestioneUtenti || (StringUtils.isNotBlank(clearedForm.getControlliGdpr())
                && AppConstants.COMBOBOX_SI.equals(clearedForm.getControlliGdpr()));

        final boolean isNewUserAdministrator = StringUtils.isNotBlank(clearedForm.getAmministratore())
                && AppConstants.COMBOBOX_SI.equals(clearedForm.getAmministratore());

        User user = userRepository.findByLoginIgnoreCase(username);

        // Controllo presenza dell'utente in database
        if (user != null) {

            LOGGER.error("Utente gia' presente per username [ {} ]", username);

            throw new UserAlreadyExistsException();
        }

        // Controllo match delle password
        if (!clearedForm.getPassword().equals(clearedForm.getConfermaPassword())) {

            LOGGER.error("Password e conferma password non coincidono per username [ {} ]", username);

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_UTENTE_PASSWORD_CONFIRM_PASSWORD_MISMATCH);

        }

        // check GDPR 1 (lunghezza password)
        boolean result1 = gdprService.executeCheck1Insert(clearedForm.getPassword(), isNewUserAdministrator, passwordSicuraEnabled);

        if (!result1) {
            LOGGER.error("Lunghezza password per username [ {} ] non valida", username);

            continueInsert = false;

            if (isNewUserAdministrator)
                errorMessages.add(AppConstants.CHANGE_PASSWORD_LENGTH_ADMIN);
            else
                errorMessages.add(AppConstants.CHANGE_PASSWORD_LENGTH);

        }

        // check GDPR 2 (complessita' password)
        GDPRCheck2DTO result2 = gdprService.executeCheck2Insert(clearedForm.getPassword(), passwordSicuraEnabled);

        if (!result2.pass()) {
            LOGGER.error("Complessita' password per username [ {} ] non sufficiente", username);

            continueInsert = false;

            if (!result2.isAllowedCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS);
            if (!result2.isLowerCaseCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS);
            if (!result2.isUpperCaseCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS);
            if (!result2.isNumberCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS);
            if (!result2.isSpecialCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS);
        }

        // check GDPR 3B (caratteri consecutivi presenti nello username)
        boolean result3B = gdprService.executeCheck3BInsert(username, clearedForm.getPassword(), passwordSicuraEnabled);
        if (!result3B) {
            LOGGER.error("Contenuto password per username [ {} ] non valido", username);

            continueInsert = false;
            errorMessages.add(AppConstants.CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS);
        }

        // Controllo presenza codice fiscale

        if (StringUtils.isNotBlank(codiceFiscale)) {

            if (!UtilityFiscali.isValidCodiceFiscale(codiceFiscale)) {

                continueInsert = false;
                errorMessages.add(AppConstants.GESTIONE_UTENTE_FORMATO_CODICE_FISCALE_NON_VALIDO);

            } else {

                // recupero una lista perche' potrei avere gia' CF duplicati
                List<User> listCheckCf = userRepository.findByCodiceFiscaleIgnoreCase(codiceFiscale);

                if (listCheckCf != null && !listCheckCf.isEmpty()) {

                    // se ne contiene uno lo verifico
                    if (listCheckCf.size() == 1) {

                        User checkCf = listCheckCf.get(0);

                        if (checkCf != null) {

                            // errore se il CF e' duplicato (non controllo syscon perche' e' inserimento)

                            continueInsert = false;
                            errorMessages.add(AppConstants.GESTIONE_UTENTE_CODICE_FISCALE_GIA_UTILIZZATO);

                        }
                    } else {

                        // altrimenti errore di default

                        continueInsert = false;
                        errorMessages.add(AppConstants.GESTIONE_UTENTE_CODICE_FISCALE_GIA_UTILIZZATO);

                    }
                }
            }
        }

        // Controllo presenza email
        if (StringUtils.isNotBlank(clearedForm.getEmail())) {

            // verifico la formalita' dell'indirizzo email
            boolean matches = Pattern.matches(AppConstants.REGEX_EMAIL, clearedForm.getEmail());

            if (!matches) {

                continueInsert = false;
                errorMessages.add(AppConstants.GESTIONE_UTENTE_FORMATO_EMAIL_NON_VALIDO);

            }
        }

        if (isCurrentUserAdministrator && isNewUserAdministrator) {

            // check presenza nella usrcanc se presente un utente con la stessa login e data
            // di cancellazione <= 6 mesi

            List<UsrCanc> listaCancellazioni = usrCancRepository.findByLoginIgnoreCase(username).orElse(null);

            if (listaCancellazioni != null && !listaCancellazioni.isEmpty()) {

                Date now = new Date();

                long intervalloCancellazione = 0L;

                UsrCanc found = listaCancellazioni.stream()
                        .filter(u -> u.getDataScadenza() != null
                                && (u.getDataScadenza().getTime() + intervalloCancellazione) < now.getTime())
                        .findFirst().orElse(null);

                if (found != null) {
                    continueInsert = false;
                    errorMessages.add(
                            AppConstants.GESTIONE_UTENTE_AMMINISTRATORE_CANCELLAZIONE_INTERVALLO_TEMPO);
                }
            }
        }

        if (continueInsert) {

            List<String> warningMessages = new ArrayList<>();

            List<String> listaOpzioniUtente = new ArrayList<>();

            // inserisco le opzioni di default
            if (StringUtils.isNotBlank(ouDefault)) {
                listaOpzioniUtente.addAll(getListaOpzioniUtente(ouDefault));
            }

            if (utenteDelegatoGestioneUtenti || StringUtils.isBlank(clearedForm.getAbilitaModificaUfficiIntestatari())
                    || (StringUtils.isNotBlank(clearedForm.getAbilitaModificaUfficiIntestatari())
                    && AppConstants.COMBOBOX_NO
                    .equals(clearedForm.getAbilitaModificaUfficiIntestatari()))) {
                addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_BLOCCA_UTENTE_MODIFICA_UFFICI_INTESTATARI);
            }

            if (!utenteDelegatoGestioneUtenti) {
                if (StringUtils.isNotBlank(clearedForm.getAbilitaTuttiUfficiIntestatari())
                        && AppConstants.COMBOBOX_SI.equals(clearedForm.getAbilitaTuttiUfficiIntestatari())) {
                    addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI);
                }

                if (StringUtils.isNotBlank(clearedForm.getGestioneUtenti())) {
                    if ("2".equals(clearedForm.getGestioneUtenti())) {
                        // sola lettura
                        // ou11 && ou12
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_UTENTI_COMPLETA);
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_UTENTI_OU12);
                    } else if ("3".equals(clearedForm.getGestioneUtenti())) {
                        // gestione completa
                        // ou11
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_UTENTI_COMPLETA);
                    }
                }

                if (isCurrentUserAdministrator && isNewUserAdministrator) {
                    addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_AMMINISTRATORE);
                    addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_INSERIMENTO_NOTE);
                }

                // Qui controllo le voci presenti nel menu strumenti (gestione modelli, gestione q-form, ecc...)
                if (StringUtils.isNotBlank(clearedForm.getGestioneModelli())) {
                    if ("2".equals(clearedForm.getGestioneModelli())) {
                        // solo modelli personali
                        // ou51
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI);
                    } else if ("3".equals(clearedForm.getGestioneModelli())) {
                        // gestione completa
                        // ou50
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_MODELLI_COMPLETA);
                    }
                }
            }

            if (passwordSicuraEnabled) {
                addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_UTENTE_PASSWORD_SICURA);
            }

            final String opzioniUtenteString = getStringOpzioniUtente(listaOpzioniUtente);

            final String passwordCifrata = super.getValoreCifrato(clearedForm.getPassword());

            Long currentMaxSyscon = this.userRepository.getMaxSyscon().orElse(0L);
            currentMaxSyscon++;

            User toInsert = new User();

            // Dati generali
            toInsert.setSyscon(currentMaxSyscon);
            toInsert.setSysute(clearedForm.getDenominazione());
            // sysnom
            toInsert.setNomeUtente(super.getValoreCifrato(username));
            // syslogin
            toInsert.setLogin(username);
            toInsert.setLdap(0);
            toInsert.setPassword(passwordCifrata);
            toInsert.setEmail(clearedForm.getEmail());
            // syscf
            toInsert.setCodiceFiscale(codiceFiscale);
            toInsert.setDisabilitato("0");

            if (!utenteDelegatoGestioneUtenti) {
                if (StringUtils.isNotBlank(clearedForm.getSysab3()))
                    toInsert.setSysab3(clearedForm.getSysab3());
                else
                    toInsert.setSysab3("U");

                if (StringUtils.isNotBlank(clearedForm.getSysabg()))
                    toInsert.setSysabg(clearedForm.getSysabg());
                else
                    toInsert.setSysabg("U");
            }

            if (clearedForm.getDataScadenzaAccount() != null)
                toInsert.setDataScadenzaAccount(clearedForm.getDataScadenzaAccount());

            // Autorizzazioni
            toInsert.setSyspwbou(opzioniUtenteString);

            // Set uffici intestatari se utente delegato
            if (utenteDelegatoGestioneUtenti && currentUser.getUffints() != null && !currentUser.getUffints().isEmpty()) {
                Set<Uffint> listaUfficiIntestatariUtente = toInsert.getUffints();
                if (listaUfficiIntestatariUtente == null)
                    listaUfficiIntestatariUtente = new HashSet<>();
                listaUfficiIntestatariUtente.addAll(currentUser.getUffints());
                toInsert.setUffints(listaUfficiIntestatariUtente);
                warningMessages.add(AppConstants.GESTIONE_UTENTE_USER_DELEGATO_ALL_SA);
            }

            // Set profili
            Set<Profilo> listaProfiliUtente = toInsert.getProfili();

            if (listaProfiliUtente == null)
                listaProfiliUtente = new HashSet<>();

            // set profili del gestore
            if (utenteDelegatoGestioneUtenti && currentUser.getProfili() != null && currentUser.getProfili().size() == 1) {
                listaProfiliUtente.addAll(currentUser.getProfili());
            }

            // Controllo il profilo di default
            String profiloDiDefault = wConfigService.getConfigurationValue(AppConstants.PROFILO_DEFAULT);

            if (StringUtils.isNotBlank(profiloDiDefault)) {

                Profilo profilo = profiloService.getProfiloInternal(profiloDiDefault);

                // set profilo di default
                if (profilo != null) {
                    listaProfiliUtente.add(profilo);
                }
            }

            toInsert.setProfili(listaProfiliUtente);

            toInsert = userRepository.save(toInsert);

            if (isCurrentUserAdministrator && isNewUserAdministrator) {
                String subject = "L'amministratore " + currentUserDTO.getUsername() + " (id = " + currentUser.getSyscon()
                        + ") ha inserito l'utenza amministrativa " + toInsert.getLogin() + " (id = "
                        + toInsert.getSyscon() + ")";
                String body = "";
                messageService.insertMessageToUsersAdministrator(subject, body, currentUser.getSyscon());
            }

            int livelloEvento = LogEventiUtils.LIVELLO_INFO;
            String errMsgEvento = null;

            WLogEventiDTO logEventiDTO = LogEventiUtils.createAddUserEvent(currentUser.getSyscon(),
                    currentUserDTO.getUsername(), currentMaxSyscon, toInsert.getLogin(), ipAddress);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            // check SA mismatch
            boolean saMismatch = checkUserSAEnabledMismatch(listaOpzioniUtente);
            if (saMismatch) {
                warningMessages.add(
                        AppConstants.GESTIONE_UTENTE_USER_EDIT_AMMINISTRAZIONI_WITHOUT_GESTIONE_COMPLETA);
            }

            UserDTO userDTO = getUserDTOFromUser(toInsert);

            response.setMessages(warningMessages);
            response.setResponse(userDTO);

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    public UserDTO getUtente(final Long syscon, final UserDTO currentUserDTO) {

        LOGGER.debug("Execution start UserServiceImpl::getUtente for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO) || isUserAbilitatoGestioneUtentiReadonly(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserPermissionException();
        }

        // Utente loggato in sessione
        User currentUser = getUserFromUserDTO(currentUserDTO);
        final boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        if (utenteDelegatoGestioneUtenti && (syscon == 47L || syscon == 48L || syscon == 49L || syscon == 50L)) {
            // Escludo syscon 47/48/49/50
            LOGGER.error("L'utente [ {} ] ha tentato di visualizzare il dettaglio dell'utente con syscon [ {} ]", currentUserDTO.getSyscon(), syscon);

            throw new UserPermissionException();
        }

        User user = userRepository.findById(syscon).orElse(null);

        if (user == null) {
            String message = "User not found for syscon [ " + syscon + " ]";
            LOGGER.error(message);
            throw new UserNotFoundException(message);
        }

        if (utenteDelegatoGestioneUtenti) {
            // Escludo admin e abilitati a tutte le SA
            boolean adminOrTutteSA = StringUtils.isNotBlank(user.getSyspwbou()) &&
                    (
                            user.getSyspwbou().toLowerCase().contains(AppConstants.OU_AMMINISTRATORE.toLowerCase())
                                    || user.getSyspwbou().toLowerCase().contains(AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI.toLowerCase())
                    );

            // Controllo che ci sia almeno una stazione appaltante associata a quella dell'utente gestore
            // Uso il metodo equals() che ho definito nell'oggetto Uffint necessario per l'esecuzione del metodo contains
            boolean containsAnySA = currentUser.getUffints() != null && user.getUffints() != null &&
                    currentUser.getUffints().stream().anyMatch(user.getUffints()::contains);

            if (adminOrTutteSA || !containsAnySA) {
                LOGGER.error("L'utente [ {} ] ha tentato di visualizzare il dettaglio dell'utente con syscon [ {} ]", currentUserDTO.getSyscon(), syscon);

                throw new UserPermissionException();
            }
        }

        return getUserDTOFromUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateUtente(final UserDTO currentUserDTO, final Long syscon, final UserEditDTO form,
                                    final String ipAddress) throws CriptazioneException {

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::updateUtente for syscon [ {} ] and form [ {} ]", syscon, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        // Utente loggato in sessione
        User currentUser = getUserFromUserDTO(currentUserDTO);
        final boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        if (utenteDelegatoGestioneUtenti && (syscon == 47L || syscon == 48L || syscon == 49L || syscon == 50L)) {
            // Escludo syscon 47/48/49/50
            LOGGER.error("L'utente [ {} ] ha tentato di modificare il dettaglio dell'utente con syscon [ {} ]", currentUserDTO.getSyscon(), syscon);

            throw new UserEditForbiddenException();
        }

        final boolean isCurrentUserAdministrator = isUserDTOAdministrator(currentUserDTO);

        UserEditDTO clearedForm = parseUtenteEditDTO(form);

        final boolean isUpdateUserAdministrator = StringUtils.isNotBlank(clearedForm.getAmministratore())
                && AppConstants.COMBOBOX_SI.equals(clearedForm.getAmministratore());

        User toUpdate = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (toUpdate == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        this.checkUserDelegatoEditPermissions(utenteDelegatoGestioneUtenti, currentUser, toUpdate);

        // Controllo presenza codice fiscale
        if (!utenteDelegatoGestioneUtenti && StringUtils.isNotBlank(clearedForm.getCodiceFiscale())) {

            if (!UtilityFiscali.isValidCodiceFiscale(clearedForm.getCodiceFiscale())) {

                continueUpdate = false;
                errorMessages.add(AppConstants.GESTIONE_UTENTE_FORMATO_CODICE_FISCALE_NON_VALIDO);

            } else {

                // recupero una lista perche' potrei avere gia' CF duplicati
                List<User> listCheckCf = userRepository.findByCodiceFiscaleIgnoreCase(clearedForm.getCodiceFiscale());

                if (listCheckCf != null && !listCheckCf.isEmpty()) {

                    // se ne contiene uno lo verifico
                    if (listCheckCf.size() == 1) {

                        User checkCf = listCheckCf.get(0);

                        if (checkCf != null && !checkCf.getSyscon().equals(syscon)) {

                            // errore se il CF e' duplicato e il syscon e' diverso dall'utente da
                            // inserire/modificare

                            continueUpdate = false;
                            errorMessages.add(AppConstants.GESTIONE_UTENTE_CODICE_FISCALE_GIA_UTILIZZATO);

                        }
                    } else {

                        // altrimenti errore di default

                        continueUpdate = false;
                        errorMessages.add(AppConstants.GESTIONE_UTENTE_CODICE_FISCALE_GIA_UTILIZZATO);

                    }
                }
            }
        }

        // Controllo presenza email
        if (StringUtils.isNotBlank(clearedForm.getEmail())) {

            // verifico la formalita' dell'indirizzo email
            boolean matches = Pattern.matches(AppConstants.REGEX_EMAIL, clearedForm.getEmail());

            if (!matches) {

                continueUpdate = false;
                errorMessages.add(AppConstants.GESTIONE_UTENTE_FORMATO_EMAIL_NON_VALIDO);

            }
        }

        if (isUpdateUserAdministrator) {

            // check presenza nella usrcanc se presente un utente con la stessa login e data
            // di cancellazione <= 6 mesi

            List<UsrCanc> listaCancellazioni = usrCancRepository.findByLoginIgnoreCase(clearedForm.getUsername())
                    .orElse(null);

            if (listaCancellazioni != null && !listaCancellazioni.isEmpty()) {

                Date now = new Date();

                long intervalloCancellazione = 0L;

                UsrCanc found = listaCancellazioni.stream()
                        .filter(u -> u.getDataScadenza() != null
                                && (u.getDataScadenza().getTime() + intervalloCancellazione) < now.getTime())
                        .findFirst().orElse(null);

                if (found != null) {
                    continueUpdate = false;
                    errorMessages.add(
                            AppConstants.GESTIONE_UTENTE_AMMINISTRATORE_CANCELLAZIONE_INTERVALLO_TEMPO);
                }
            }
        }

        if (continueUpdate) {

            List<String> warningMessages = new ArrayList<>();

            // Denominazione
            toUpdate.setSysute(clearedForm.getDenominazione());

            // controlli per utenti diversi da service.backoffice (47), ADMIN (48), EnteAdmin (49) e MANAGER (50)
            // controllo inoltre di non essere un utente gestore (delegato) e che la data di ultimo accesso sia null
            if (!utenteDelegatoGestioneUtenti && syscon != 47L && syscon != 48L && syscon != 49L && syscon != 50 && toUpdate.getDataUltimoAccesso() == null) {
                // Username
                toUpdate.setLogin(clearedForm.getUsername());
                toUpdate.setNomeUtente(super.getValoreCifrato(clearedForm.getUsername()));
            }

            // Codice fiscale (se non sono delegato)
            if (!utenteDelegatoGestioneUtenti)
                toUpdate.setCodiceFiscale(StringUtils.stripToNull(clearedForm.getCodiceFiscale()));

            // Email
            toUpdate.setEmail(StringUtils.stripToNull(clearedForm.getEmail()));

            List<String> listaOpzioniUtente = getListaOpzioniUtente(toUpdate.getSyspwbou());

            if (!utenteDelegatoGestioneUtenti) {
                // verifica blocco utente modifica uffici intestatari
                listaOpzioniUtente.remove(AppConstants.OU_BLOCCA_UTENTE_MODIFICA_UFFICI_INTESTATARI);

                if (StringUtils.isBlank(clearedForm.getAbilitaModificaUfficiIntestatari())
                        || (StringUtils.isNotBlank(clearedForm.getAbilitaModificaUfficiIntestatari())
                        && AppConstants.COMBOBOX_NO
                        .equals(clearedForm.getAbilitaModificaUfficiIntestatari()))) {
                    addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_BLOCCA_UTENTE_MODIFICA_UFFICI_INTESTATARI);
                }

                // verifica abilitazione tutti uffici intestatari
                listaOpzioniUtente.remove(AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI);

                if (StringUtils.isNotBlank(clearedForm.getAbilitaTuttiUfficiIntestatari())
                        && AppConstants.COMBOBOX_SI.equals(clearedForm.getAbilitaTuttiUfficiIntestatari())) {
                    addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI);
                }

                // Controlli GDPR
                listaOpzioniUtente.remove(AppConstants.OU_UTENTE_PASSWORD_SICURA);

                if (StringUtils.isNotBlank(clearedForm.getControlliGdpr())
                        && AppConstants.COMBOBOX_SI.equals(clearedForm.getControlliGdpr())) {
                    addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_UTENTE_PASSWORD_SICURA);
                }

                // Pulisco le OU che potrei aver rimosso oppure aggiornato
                listaOpzioniUtente.remove(AppConstants.OU_GESTIONE_MODELLI_COMPLETA);
                listaOpzioniUtente.remove(AppConstants.OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI);

                // Qui controllo le voci presenti nel menu strumenti (gestione modelli, gestione q-form, ecc...)
                if (StringUtils.isNotBlank(clearedForm.getGestioneModelli())) {
                    if ("2".equals(clearedForm.getGestioneModelli())) {
                        // solo modelli personali
                        // ou51
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI);
                    } else if ("3".equals(clearedForm.getGestioneModelli())) {
                        // gestione completa
                        // ou50
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_MODELLI_COMPLETA);
                    }
                }
            }

            boolean isUpdateUtenteAdminChanged = false;

            // controlli per utenti diversi da ADMIN (48) e EnteAdmin (49)
            if (syscon != 48L && syscon != 49L) {

                // Gestione utenti
                if (StringUtils.isNotBlank(clearedForm.getGestioneUtenti())) {

                    listaOpzioniUtente.remove(AppConstants.OU_GESTIONE_UTENTI_COMPLETA);

                    listaOpzioniUtente.remove(AppConstants.OU_GESTIONE_UTENTI_OU12);

                    if ("2".equals(clearedForm.getGestioneUtenti())) {
                        // sola lettura
                        // ou11 && ou12
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_UTENTI_COMPLETA);
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_UTENTI_OU12);
                    } else if ("3".equals(clearedForm.getGestioneUtenti())) {
                        // gestione completa
                        // ou11
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_GESTIONE_UTENTI_COMPLETA);
                    }
                }

                // Amministratore
                // se l'utente corrente e' amministratore
                if (isCurrentUserAdministrator) {

                    if ((listaOpzioniUtente.contains(AppConstants.OU_AMMINISTRATORE)
                            && !isUpdateUserAdministrator)
                            || (!listaOpzioniUtente.contains(AppConstants.OU_AMMINISTRATORE)
                            && isUpdateUserAdministrator))
                        isUpdateUtenteAdminChanged = true;

                    listaOpzioniUtente.remove(AppConstants.OU_AMMINISTRATORE);

                    listaOpzioniUtente.remove(AppConstants.OU_INSERIMENTO_NOTE);

                    // se per l'utente da aggiornare e' stato selezionato SI nel campo
                    // amministratore
                    if (isUpdateUserAdministrator) {
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_AMMINISTRATORE);
                        addOUToListIfNotPresent(listaOpzioniUtente, AppConstants.OU_INSERIMENTO_NOTE);
                    }
                }

            }

            final String opzioniUtenteString = getStringOpzioniUtente(listaOpzioniUtente);

            toUpdate.setSyspwbou(opzioniUtenteString);
            toUpdate.setDataScadenzaAccount(clearedForm.getDataScadenzaAccount());

            if (!utenteDelegatoGestioneUtenti && StringUtils.isNotBlank(clearedForm.getSysab3()))
                toUpdate.setSysab3(clearedForm.getSysab3());

            if (!utenteDelegatoGestioneUtenti && StringUtils.isNotBlank(clearedForm.getSysabg()))
                toUpdate.setSysabg(clearedForm.getSysabg());

            toUpdate = userRepository.save(toUpdate);

            if (isCurrentUserAdministrator && isUpdateUtenteAdminChanged) {
                String subject = "L'amministratore " + currentUserDTO.getUsername() + " (id = " + currentUser.getSyscon()
                        + ") ha aggiornato l'utenza amministrativa " + toUpdate.getLogin() + " (id = "
                        + toUpdate.getSyscon() + ") con opzioni utente = " + listaOpzioniUtente + ", sysab3 = "
                        + toUpdate.getSysab3() + ", sysabg = " + toUpdate.getSysabg();
                String body = "";
                messageService.insertMessageToUsersAdministrator(subject, body, currentUser.getSyscon());
            }

            // check SA mismatch
            boolean saMismatch = checkUserSAEnabledMismatch(listaOpzioniUtente);
            if (saMismatch) {
                warningMessages.add(
                        AppConstants.GESTIONE_UTENTE_USER_EDIT_AMMINISTRAZIONI_WITHOUT_GESTIONE_COMPLETA);
                response.setMessages(warningMessages);
            }

            UserDTO userDTO = getUserDTOFromUser(toUpdate);

            response.setResponse(userDTO);

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean abilitaDisabilitaUtente(final UserDTO currentUserDTO, final Long syscon, final String operazione, final String ipAddress) {

        LOGGER.debug("Execution start UserServiceImpl::abilitaDisabilitaUtente for syscon [ {} ] and operazione [ {} ]",
                syscon, operazione);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);
        User currentUser = getUserFromUserDTO(currentUserDTO);
        // Utente loggato in sessione
        final boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        User toUpdate = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (toUpdate == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        this.checkUserDelegatoEditPermissions(utenteDelegatoGestioneUtenti, currentUser, toUpdate);

        boolean mailInviata = true;

        if (operazione.equals(AppConstants.OPERAZIONE_ATTIVAZIONE)) {
            // Utente disabilitato
            if (StringUtils.isNotBlank(toUpdate.getDisabilitato()) && AppConstants.SYSDISAB_UTENTE_DISABILITATO.equals(toUpdate.getDisabilitato())) {

                toUpdate.setDisabilitato(AppConstants.SYSDISAB_UTENTE_ABILITATO);

                if (toUpdate.getDataUltimoAccesso() != null)
                    toUpdate.setDataUltimoAccesso(new Date());

                toUpdate = userRepository.save(toUpdate);

                // Tracciatura in w_logeventi
                WLogEventiDTO logEventiDTO = LogEventiUtils.createAbilitazioneUtenteEvent(currentUser.getSyscon(), toUpdate.getSyscon(), toUpdate.getLogin(),
                        ipAddress, codiceProdotto);
                wLogEventiService.insertLogEvent(logEventiDTO);

                // se da configurazione devo inviare la mail di attivazione e l'utente ha
                // salvata una mail allora la invio
                // EDIT 19/12/2022: Rimosso il controllo di funzionalita' attiva invio mail attivazione/disattivazione
                if (StringUtils.isNotBlank(toUpdate.getEmail())) {
                    mailInviata = inviaMailAttivazioneDisattivazione(toUpdate, true);
                }
            }
        } else if (operazione.equals(AppConstants.OPERAZIONE_DISATTIVAZIONE)) {
            // Utente abilitato
            if (StringUtils.isBlank(toUpdate.getDisabilitato())
                    || (StringUtils.isNotBlank(toUpdate.getDisabilitato()) && AppConstants.SYSDISAB_UTENTE_ABILITATO.equals(toUpdate.getDisabilitato()))) {

                toUpdate.setDisabilitato(AppConstants.SYSDISAB_UTENTE_DISABILITATO);

                toUpdate = userRepository.save(toUpdate);

                WLogEventiDTO logEventiDTO = LogEventiUtils.createDisabilitazioneUtenteEvent(currentUser.getSyscon(), toUpdate.getSyscon(), toUpdate.getLogin(),
                        ipAddress, codiceProdotto);
                wLogEventiService.insertLogEvent(logEventiDTO);

                // se da configurazione devo inviare la mail di disattivazione e l'utente ha
                // salvata una mail allora la invio
                // EDIT 19/12/2022: Rimosso il controllo di funzionalita' attiva invio mail attivazione/disattivazione
                if (StringUtils.isNotBlank(toUpdate.getEmail())) {
                    mailInviata = inviaMailAttivazioneDisattivazione(toUpdate, false);
                }
            }
        }

        return mailInviata;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteUtente(final UserDTO currentUserDTO, final Long syscon, final String ipAddress) {

        LOGGER.debug("Execution start UserServiceImpl::deleteUtente for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");


        User currentUser = getUserFromUserDTO(currentUserDTO);
        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);
        final boolean isCurrentUserAmministratore = isUserDTOAdministrator(currentUserDTO);
        // Utente loggato in sessione
        final boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        if (syscon == 48L || syscon == 49L || syscon == 50L) {

            LOGGER.error("L'utente indicato non puo' essere eliminato");

            throw new UserEditForbiddenException();
        }

        User toDelete = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (toDelete == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        this.checkUserDelegatoEditPermissions(utenteDelegatoGestioneUtenti, currentUser, toDelete);

        String username = toDelete.getLogin();

        if (toDelete.getDataUltimoAccesso() == null) {

            boolean isUserToDeleteAdministrator = isUserAdministrator(toDelete);

            if (isUserToDeleteAdministrator && !isCurrentUserAmministratore) {
                LOGGER.error(
                        "L'utente corrente non ha i permessi di amministratore per eliminare un altro amministratore");

                throw new UserNotAdministratorException();
            } else {
                // delete usrsys, usr_ein e w_accpro
                userRepository.delete(toDelete);
                // delete w_accgrp
                wAccgrpRepository.deleteByIdIdAccount(syscon);
                // delete g_permessi
                gPermessiRepository.deleteBySyscon(syscon);
                // delete stoutesys
                stoUteSysService.deleteBySyscon(syscon);
                // delete w_cachemodpar
                wCachemodparRepository.deleteByIdIdAccount(syscon);
                // delete w_usrtoken
                wUsrTokenRepository.deleteById(syscon);

                if (isUserToDeleteAdministrator) {
                    // non serve ma in caso il metodo esiste
//					usrCancService.insertCancellazione(syscon, username);

                    // invio messaggo agli amministratori
                    String subject = "L'amministratore " + currentUserDTO.getUsername() + " (id = "
                            + currentUserDTO.getSyscon() + ") ha rimosso l'utenza amministrativa " + toDelete.getLogin()
                            + " (id = " + syscon + ")";
                    String body = "";
                    messageService.insertMessageToUsersAdministrator(subject, body, currentUserDTO.getSyscon());
                }

                // inserimento evento di cancellazione
                WLogEventiDTO logEventiDTO = LogEventiUtils.createDeleteUserEvent(currentUserDTO.getSyscon(),
                        currentUserDTO.getUsername(), syscon, username, ipAddress);
                logEventiDTO.setCodApp(codiceProdotto);
                logEventiDTO.setLivelloEvento(LogEventiUtils.LIVELLO_INFO);
                logEventiDTO.setErrorMessage("");
                wLogEventiService.insertLogEvent(logEventiDTO);

                return true;
            }
        } else {
            LOGGER.warn("Attenzione: tentativo di eliminazione utente con data ultimo accesso non nulla");

            throw new UserDataUltimoAccessoNotNullException();
        }
    }

    @Override
    public List<ProfiloDTO> getProfiliUtente(final UserDTO currentUser, final Long syscon) {

        LOGGER.debug("Execution start UserServiceImpl::getProfiliUtente for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUser)
                || isUserAbilitatoGestioneUtentiReadonly(currentUser);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUser.getSyscon());

            throw new UserEditForbiddenException();
        }

        User found = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (found == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        List<ProfiloDTO> profileList = new ArrayList<>();

        if (found.getProfili() != null && !found.getProfili().isEmpty()) {
            profileList = found.getProfili().stream().map(p -> dtoMapper.convertTo(p, ProfiloDTO.class))
                    .collect(Collectors.toList());
            profileList.sort(Comparator.comparing(ProfiloDTO::getCodice));
        }

        return profileList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean setProfiliUtente(final UserDTO currentUserDTO, final Long syscon, final List<String> listaProfili,
                                    final String ipAddress) {

        LOGGER.debug("Execution start UserServiceImpl::setProfiliUtente for syscon [ {} ] and profili [ {} ]", syscon,
                listaProfili);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        User currentUser = getUserFromUserDTO(currentUserDTO);
        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        boolean removeAllProfiles = listaProfili == null || listaProfili.isEmpty();

        if (utenteDelegatoGestioneUtenti && removeAllProfiles) {
            LOGGER.error("Non e' possibile associare 0 profili, selezionarne almeno uno");
            throw new NoProfilesSelectedException();
        }

        User found = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (found == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        Set<Profilo> profiles = new HashSet<>();

        if (removeAllProfiles) {

            found.setProfili(null);

            wAccgrpRepository.deleteByIdIdAccount(syscon);

        } else {

            List<String> toBeRemoved = new ArrayList<>();
            List<String> toBeAdded = new ArrayList<>();

            // recupero i profili da disassociare
            for (Profilo p : found.getProfili()) {

                boolean isPresent = listaProfili.stream().anyMatch(pr -> pr.equals(p.getCodice()));

                if (!isPresent) {
                    toBeRemoved.add(p.getCodice());
                }

            }

            // recupero i profili da associare
            for (String p : listaProfili) {

                boolean isPresent = found.getProfili().stream().anyMatch(pr -> pr.getCodice().equals(p));

                if (!isPresent) {
                    toBeAdded.add(p);
                }

            }

            // rimuovi i gruppi dei profili disassociati
            List<WGruppi> listaGruppi = null;
            if (!toBeRemoved.isEmpty()) {
                listaGruppi = wGruppiRepository.findByCodiceProfiloIn(toBeRemoved).orElse(null);
                if (listaGruppi != null) {
                    for (WGruppi g : listaGruppi) {

                        WAccgrpPK id = new WAccgrpPK(syscon, g.getId());

                        Optional<WAccgrp> acgrpFound = wAccgrpRepository.findById(id);

                        if (acgrpFound.isPresent())
                            wAccgrpRepository.deleteById(id);

                    }
                }
            }

            // aggiungo i gruppo dei profili da associare se la gestione gruppi e'
            // disattivata
            if (!toBeAdded.isEmpty() && isGestioneGruppiDisattivata()) {
                listaGruppi = wGruppiRepository.findByCodiceProfiloIn(toBeAdded).orElse(null);
                if (listaGruppi != null) {
                    for (WGruppi g : listaGruppi) {
                        WAccgrp wacg = new WAccgrp();
                        wacg.setId(new WAccgrpPK(syscon, g.getId()));
                        wacg.setPriorita(0);
                        wAccgrpRepository.save(wacg);
                    }
                }
            }

            for (String p : listaProfili) {
                Profilo profilo = profiloService.getProfiloInternal(p);
                if (profilo != null)
                    profiles.add(profilo);
            }

            found.setProfili(profiles);
        }

        found = userRepository.save(found);

        // inserimento evento di cancellazione
        WLogEventiDTO logEventiDTO = LogEventiUtils.createChangeUserProfileEvent(currentUser.getSyscon(),
                found.getSysute(), syscon, profiles, ipAddress);
        logEventiDTO.setCodApp(codiceProdotto);
        logEventiDTO.setLivelloEvento(LogEventiUtils.LIVELLO_INFO);
        logEventiDTO.setErrorMessage("");
        wLogEventiService.insertLogEvent(logEventiDTO);

        return true;
    }

    @Override
    public List<UfficioIntestatarioDTO> getUfficiIntestatariUtente(final UserDTO currentUser, final Long syscon) {

        LOGGER.debug("Execution start UserServiceImpl::getUfficiIntestatariUtente for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUser)
                || isUserAbilitatoGestioneUtentiReadonly(currentUser);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUser.getSyscon());

            throw new UserEditForbiddenException();
        }

        User found = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (found == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        List<UfficioIntestatarioDTO> saList = new ArrayList<>();

        if (found.getUffints() != null && !found.getUffints().isEmpty()) {
            saList = found.getUffints().stream().map(s -> dtoMapper.convertTo(s, UfficioIntestatarioDTO.class))
                    .collect(Collectors.toList());
            saList.sort(Comparator.comparing(UfficioIntestatarioDTO::getCodice));
        }

        return saList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean setUfficiIntestatariUtente(final UserDTO currentUserDTO, final Long syscon,
                                              final List<String> listaUfficiIntestatari) {

        LOGGER.debug(
                "Execution start UserServiceImpl::setUfficiIntestatariUtente for syscon [ {} ] and uffici intestatari [ {} ]",
                syscon, listaUfficiIntestatari);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        User currentUser = getUserFromUserDTO(currentUserDTO);

        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        boolean removeAllUffints = listaUfficiIntestatari == null
                || listaUfficiIntestatari.isEmpty();

        if (removeAllUffints) {
            LOGGER.error("Non e' possibile associare 0 uffici intestatari, selezionarne almeno uno");
            throw new NoUffintSelectedException();
        }

        User found = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (found == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        if (utenteDelegatoGestioneUtenti) {
            // Controllo nella lista uffici intestatari da aggiungere se ci sono uffints non associate al gestore
            List<String> currentUserUfficiIntestatari = currentUser.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList());
            List<String> differences = listaUfficiIntestatari.stream()
                    .filter(element -> !currentUserUfficiIntestatari.contains(element))
                    .collect(Collectors.toList());
            if (!differences.isEmpty()) {
                LOGGER.error("L'utente [ {} ] non ha i permessi per modificare l'assegnazione di uffici intestatari a lui non assegnati per l'utente [ {} ]", currentUserDTO.getSyscon(), found.getSyscon());

                throw new UserPermissionException();
            }
        }

        Set<Uffint> uffints = listaUfficiIntestatari.parallelStream().map(sa -> uffintService.getUffintInternal(sa))
                .collect(Collectors.toSet());

        found.setUffints(uffints);

        found = userRepository.save(found);

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO changePasswordAdminUtente(final UserDTO currentUserDTO, final Long syscon,
                                                 final UserChangePasswordAdminDTO form, final String ipAddress) throws CriptazioneException {

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::changePasswordAdminUtente for syscon [ {} ]", syscon);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueChangePassword = true;
        List<String> errorMessages = new ArrayList<>();

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);
        User currentUser = getUserFromUserDTO(currentUserDTO);
        final boolean isCurrentUserAdministrator = isUserAdministrator(currentUser.getSyspwbou());
        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            LOGGER.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        if (!isCurrentUserAdministrator && (syscon == 48L || syscon == 49L || syscon == 50L)) {

            LOGGER.error("Per l'utente indicato non puo' essere cambiata la password");

            throw new UserEditForbiddenException();
        }

        User toUpdate = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (toUpdate == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        // Verifica permessi utente gestore
        this.checkUserDelegatoChangePasswordPermissions(utenteDelegatoGestioneUtenti, currentUser, toUpdate);

        // Controllo match delle password
        if (!form.getPassword().equals(form.getConfermaPassword())) {

            LOGGER.error("Password e conferma password non coincidono per username [ {} ]", toUpdate.getLogin());

            continueChangePassword = false;
            errorMessages.add(AppConstants.GESTIONE_UTENTE_PASSWORD_CONFIRM_PASSWORD_MISMATCH);

        }

        String oldPassword = super.getValoreNonCifrato(toUpdate.getPassword());

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setUsername(toUpdate.getLogin());
        changePasswordDTO.setOldPassword(oldPassword);
        changePasswordDTO.setNewPassword(form.getPassword());
        changePasswordDTO.setConfirmNewPassword(form.getConfermaPassword());

        // check GDPR 1 (lunghezza password)
        boolean result1 = gdprService.executeCheck1(toUpdate, changePasswordDTO);

        if (!result1) {
            LOGGER.error("Lunghezza password per username [ {} ] non valida", toUpdate.getLogin());

            continueChangePassword = false;

            if (super.isUserAdministrator(toUpdate.getSyspwbou()))
                errorMessages.add(AppConstants.CHANGE_PASSWORD_LENGTH_ADMIN);
            else
                errorMessages.add(AppConstants.CHANGE_PASSWORD_LENGTH);

        }

        // check GDPR 2 (complessita' password)
        GDPRCheck2DTO result2 = gdprService.executeCheck2(toUpdate, changePasswordDTO);

        if (!result2.pass()) {
            LOGGER.error("Complessita' password per username [ {} ] non sufficiente", toUpdate.getLogin());

            continueChangePassword = false;

            if (!result2.isAllowedCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS);
            if (!result2.isLowerCaseCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS);
            if (!result2.isUpperCaseCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS);
            if (!result2.isNumberCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS);
            if (!result2.isSpecialCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS);
        }

        // check GDPR 3B (caratteri consecutivi presenti nello username)
        boolean result3B = gdprService.executeCheck3B(toUpdate, changePasswordDTO);
        if (!result3B) {
            LOGGER.error("Contenuto password per username [ {} ] non valido", toUpdate.getLogin());

            continueChangePassword = false;
            errorMessages.add(AppConstants.CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS);
        }

        // check GDPR 5A e 5C (Password gia' utilizzata)
        boolean result5A5C = gdprService.executeCheck5A5C(toUpdate, changePasswordDTO);
        if (!result5A5C) {
            LOGGER.error("Password gia' utilizzata per username [ {} ]", toUpdate.getLogin());

            continueChangePassword = false;
            errorMessages.add(AppConstants.CHANGE_PASSWORD_ALREADY_USED);
        }

        if (continueChangePassword) {

            final String passwordCifrata = super.getValoreCifrato(form.getPassword());
            toUpdate.setPassword(passwordCifrata);

            toUpdate = userRepository.save(toUpdate);

            // insert into stoutesys
            stoUteSysService.insertChangedPassword(toUpdate, passwordCifrata);

            // inserimento log in w_logeventi
            WLogEventiDTO logEventiDTO = LogEventiUtils.createChangePasswordEvent(toUpdate.getSyscon(),
                    toUpdate.getLogin(), ipAddress, EChangePasswordType.CHANGE);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(LogEventiUtils.LIVELLO_INFO);
            logEventiDTO.setErrorMessage("");
            wLogEventiService.insertLogEvent(logEventiDTO);

            UserDTO userDTO = getUserDTOFromUser(toUpdate);

            response.setResponse(userDTO);

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    public boolean isUserDTOAdministrator(final UserDTO userDTO) {

        if (userDTO == null)
            throw new IllegalArgumentException("userDTO null");

        LOGGER.debug("Execution start UserServiceImpl::isUserDTOAdministrator for user [ {} ]", userDTO);

        User user = this.userRepository.findById(userDTO.getSyscon()).orElse(null);

        if (user == null)
            return false;

        return isUserAdministrator(user);
    }

    /**
     * Ritorno sempre true per non dare info all'utente ma loggo in w_logeventi e
     * console gli eventuali errori
     * <p>
     * 17/02/2023: Richiesta da parte di Lelio
     * Rimuovere Codice fiscale ed email in favore del solo Username.
     * L'email verra' recuperata dalla tabella usrsys
     *
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO requestPasswordRecovery(final PasswordRecoveryRequestDTO form) throws Exception {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::richiediRecuperoPassword for form [ {} ]", form);

        boolean valid = super.verifyCaptchaSolution(form.getCaptchaSolution());

        if (!valid) {
            LOGGER.error("Validazione captcha non superata");
            ResponseDTO response = new ResponseDTO();
            response.getMessages().add(AppConstants.CAPTCHA_ERROR);
            response.setDone(AppConstants.RESPONSE_DONE_N);
            return response;
        } else {

            int livelloEvento = LogEventiUtils.LIVELLO_INFO;
            String errMsgEvento = "";
            String logMessage = "";

            String username = form.getUsername().toLowerCase();

            // log in w_logeventi
            logMessage = "Richiesta recupero password";
            WLogEventiDTO logEventiDTO = LogEventiUtils.createPasswordRecoveryEvent(logMessage, null,
                    form.getIpAddress());
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            User found = userRepository.findByLoginIgnoreCase(username);

            if (found == null) {

                livelloEvento = LogEventiUtils.LIVELLO_ERROR;

                String messaggio = "Utente non trovato per username [ {0} ]";

                errMsgEvento = MessageFormat.format(messaggio, username);

                LOGGER.error(errMsgEvento);

                logMessage = "Utente non trovato";
                logEventiDTO = LogEventiUtils.createPasswordRecoveryEvent(logMessage, null, form.getIpAddress());
                logEventiDTO.setCodApp(codiceProdotto);
                logEventiDTO.setLivelloEvento(livelloEvento);
                logEventiDTO.setErrorMessage(errMsgEvento);
                wLogEventiService.insertLogEvent(logEventiDTO);

                return null;
            }

            if (StringUtils.isBlank(found.getEmail())) {

                livelloEvento = LogEventiUtils.LIVELLO_ERROR;

                String messaggio = "Email vuota per username [ {0} ]";

                errMsgEvento = MessageFormat.format(messaggio, username);

                LOGGER.error(errMsgEvento);

                logEventiDTO = LogEventiUtils.createPasswordRecoveryEvent(messaggio, null, form.getIpAddress());
                logEventiDTO.setCodApp(codiceProdotto);
                logEventiDTO.setLivelloEvento(livelloEvento);
                logEventiDTO.setErrorMessage(errMsgEvento);
                wLogEventiService.insertLogEvent(logEventiDTO);

                return null;
            }

            Long syscon = found.getSyscon();

            // rimuovo tutte le richieste precedenti da parte dell'utente
            wUsrTokenRepository.deleteByIdAndTokenType(syscon,
                    AppConstants.W_USRTOKEN_PASSWORD_RECOVERY_TOKEN_TYPE);

            // genero il nuovo token

            String token = generatePasswordRecoveryToken();

            WUsrToken wUsrToken = new WUsrToken();
            wUsrToken.setSyscon(syscon);
            wUsrToken.setToken(token);
            wUsrToken.setDataCreazioneToken(new Date());
            wUsrToken.setTokenType(AppConstants.W_USRTOKEN_PASSWORD_RECOVERY_TOKEN_TYPE);

            wUsrToken = wUsrTokenRepository.save(wUsrToken);

            // invio email
            inviaMailRecuperoPassword(found, wUsrToken, form.getIpAddress(), form.getCurrentUrl());

            // log in w_logeventi
            logMessage = MessageFormat.format("Richiesta recupero password con token [ {0} ]", token);
            logEventiDTO = LogEventiUtils.createPasswordRecoveryGenerateTokenEvent(logMessage, found.getSyscon(),
                    form.getIpAddress());
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            String emailOffuscata = offuscaEmail(found.getEmail());

            ResponseDTO response = new ResponseDTO();
            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setResponse(emailOffuscata);
            return response;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean checkPasswordRecoveryToken(final String token, final String ipAddress) {

        LOGGER.debug("Execution start UserServiceImpl::checkPasswordRecoveryToken for token [ {} ]", token);

        if (StringUtils.isBlank(token))
            throw new IllegalArgumentException("token null");

        List<WUsrToken> listaToken = wUsrTokenRepository.findAll();
        List<WUsrToken> toRemove = new ArrayList<>();

        Long validitaTokenMillis = getValiditaTokenMillis();

        Date now = new Date();

        for (WUsrToken wut : listaToken) {
            Long dataCreazioneMillis = wut.getDataCreazioneToken().getTime();

            if (dataCreazioneMillis + validitaTokenMillis < now.getTime())
                toRemove.add(wut);
        }

        // pulizia token scaduti
        wUsrTokenRepository.deleteAll(toRemove);

        WUsrToken found = wUsrTokenRepository.findByToken(token);

        if (found == null) {
            LOGGER.error("Token non trovato per token [ {} ]", token);

            return false;
        }

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO executePasswordRecovery(final PasswordRecoveryExecutionDTO form) throws CriptazioneException {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::executePasswordRecovery for token [ {} ]", form.getToken());

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        int livelloEvento = LogEventiUtils.LIVELLO_INFO;
        String errMsgEvento = "";
        String oggetto = "Richiesta recupero password";
        String logMessage = "";

        boolean continueChangePassword = true;
        List<String> errorMessages = new ArrayList<>();

        String token = form.getToken();

        WUsrToken tokenEntryFound = wUsrTokenRepository.findByToken(token);

        if (tokenEntryFound == null) {
            LOGGER.error("Token non trovato per token [ {} ]", token);

            errorMessages.add(AppConstants.PASSWORD_RECOVERY_TOKEN_NOT_FOUND);
            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

            return response;
        }

        Date now = new Date();
        Long validitaTokenMillis = getValiditaTokenMillis();

        // token scaduto
        if (tokenEntryFound.getDataCreazioneToken().getTime() + validitaTokenMillis < now.getTime()) {

            LOGGER.error("Token scaduto per token [ {} ]", token);

            wUsrTokenRepository.delete(tokenEntryFound);

            errorMessages.add(AppConstants.PASSWORD_RECOVERY_TOKEN_EXPIRED);
            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

            return response;
        }

        // Controllo match delle password
        if (!form.getPassword().equals(form.getConfermaPassword())) {

            LOGGER.error("Password e conferma password non coincidono per token [ {} ]", token);

            errorMessages.add(AppConstants.PASSWORD_RECOVERY_PASSWORD_CONFIRM_PASSWORD_MISMATCH);
            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

            return response;
        }

        Long syscon = tokenEntryFound.getSyscon();

        User toUpdate = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (toUpdate == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            errorMessages.add(AppConstants.PASSWORD_RECOVERY_USER_NOT_FOUND);
            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

            return response;
        }

        String oldPassword = super.getValoreNonCifrato(toUpdate.getPassword());

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setUsername(toUpdate.getLogin());
        changePasswordDTO.setOldPassword(oldPassword);
        changePasswordDTO.setNewPassword(form.getPassword());
        changePasswordDTO.setConfirmNewPassword(form.getConfermaPassword());

        // check GDPR 1 (lunghezza password)
        boolean result1 = gdprService.executeCheck1(toUpdate, changePasswordDTO);

        if (!result1) {
            LOGGER.error("Lunghezza password per username [ {} ] non valida", toUpdate.getLogin());

            continueChangePassword = false;

            if (super.isUserAdministrator(toUpdate.getSyspwbou()))
                errorMessages.add(AppConstants.CHANGE_PASSWORD_LENGTH_ADMIN);
            else
                errorMessages.add(AppConstants.CHANGE_PASSWORD_LENGTH);

        }

        // check GDPR 2 (complessita' password)
        GDPRCheck2DTO result2 = gdprService.executeCheck2(toUpdate, changePasswordDTO);

        if (!result2.pass()) {
            LOGGER.error("Complessita' password per username [ {} ] non sufficiente", toUpdate.getLogin());

            continueChangePassword = false;

            if (!result2.isAllowedCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS);
            if (!result2.isLowerCaseCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS);
            if (!result2.isUpperCaseCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS);
            if (!result2.isNumberCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS);
            if (!result2.isSpecialCharacters())
                errorMessages.add(AppConstants.CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS);
        }

        // check GDPR 3B (caratteri consecutivi presenti nello username)
        boolean result3B = gdprService.executeCheck3B(toUpdate, changePasswordDTO);
        if (!result3B) {
            LOGGER.error("Contenuto password per username [ {} ] non valido", toUpdate.getLogin());

            continueChangePassword = false;
            errorMessages.add(AppConstants.CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS);
        }

        // check GDPR 5A e 5C (Password gia' utilizzata)
        boolean result5A5C = gdprService.executeCheck5A5C(toUpdate, changePasswordDTO);
        if (!result5A5C) {
            LOGGER.error("Password gia' utilizzata per username [ {} ]", toUpdate.getLogin());

            continueChangePassword = false;
            errorMessages.add(AppConstants.CHANGE_PASSWORD_ALREADY_USED);
        }

        if (continueChangePassword) {

            final String passwordCifrata = super.getValoreCifrato(form.getPassword());
            toUpdate.setPassword(passwordCifrata);
            toUpdate.setDisabilitato("0");
            toUpdate.setDataScadenzaAccount(null);
            toUpdate.setDataUltimoAccesso(now);

            toUpdate = userRepository.save(toUpdate);

            // insert into stoutesys
            stoUteSysService.insertChangedPassword(toUpdate, passwordCifrata);

            // rimozione del token
            wUsrTokenRepository.delete(tokenEntryFound);

            // log in w_logeventi
            logMessage = MessageFormat.format("Richiesta recupero password con token [ {0} ] per syscon [ {1} ]", token, syscon);
            WLogEventiDTO logEventiDTO = LogEventiUtils.createPasswordRecoveryProcessTokenEvent(logMessage,
                    syscon, form.getIpAddress());
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            response.setResponse(true);

        } else {
            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);
        }

        return response;
    }

    @Override
    public UserConnectedDTO getUtenteConnesso(final UserDTO currentUser) {
        LOGGER.debug("Execution start UserServiceImpl::getUtenteConnesso");

        if (currentUser == null)
            throw new IllegalArgumentException("currentUser null");

        Long syscon = currentUser.getSyscon();

        User user = userRepository.findById(syscon).orElse(null);

        if (user == null) {
            String message = "User not found for syscon [ " + syscon + " ]";
            LOGGER.error(message);
            throw new UserNotFoundException(message);
        }

        return getUserConnectedDTOFromUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateUtenteConnesso(final UserDTO currentUser, final UserConnectedEditDTO form) {

        if (currentUser == null)
            throw new IllegalArgumentException("currentUser null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        Long syscon = currentUser.getSyscon();

        LOGGER.debug("Execution start UserServiceImpl::updateUtenteConnesso for syscon [ {} ] and form [ {} ]", syscon,
                form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        User toUpdate = userRepository.findById(syscon).orElse(null);

        // Controllo presenza dell'utente in database
        if (toUpdate == null) {

            LOGGER.error("Utente non presente per syscon [ {} ]", syscon);

            throw new UserNotFoundException();
        }

        // Controllo presenza email
        if (StringUtils.isNotBlank(form.getEmail())) {

            // verifico la formalita' dell'indirizzo email
            boolean matches = Pattern.matches(AppConstants.REGEX_EMAIL, form.getEmail());

            if (!matches) {

                continueUpdate = false;
                errorMessages.add(AppConstants.GESTIONE_UTENTE_FORMATO_EMAIL_NON_VALIDO);

            }
        }

        if (continueUpdate) {

            // Email
            toUpdate.setEmail(StringUtils.stripToNull(form.getEmail()));

            toUpdate = userRepository.save(toUpdate);

            UserConnectedDTO userConnectedDTO = getUserConnectedDTOFromUser(toUpdate);

            response.setResponse(userConnectedDTO);

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    public ResponseDTO loadCsvUtenti(final RicercaUtentiFormDTO form, final UserDTO currentUserDTO) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start UserServiceImpl::loadCsvUtenti for form [ {} ]", form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        // paginazione e ordinamento
        final String sort = form.getSort();
        final String sortDirection = form.getSortDirection();
        final String sortKey = StringUtils.isNotBlank(sort) ? sort : "sysute";
        final String sortDirectionKey = StringUtils.isNotBlank(sortDirection) ? sortDirection : "asc";

        // clear attributi con stringa vuota
        form.setDenominazione(StringUtils.stripToNull(form.getDenominazione()));
        form.setUsername(StringUtils.stripToNull(form.getUsername()));
        form.setUsernameCF(StringUtils.stripToNull(form.getUsernameCF()));
        form.setCodiceFiscale(StringUtils.stripToNull(form.getCodiceFiscale()));
        form.setEmail(StringUtils.stripToNull(form.getEmail()));
        form.setGestioneUtenti(StringUtils.stripToNull(form.getGestioneUtenti()));
        form.setProfiloKey(StringUtils.stripToNull(form.getProfiloKey()));

        User currentUser = getUserFromUserDTO(currentUserDTO);

        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);
        boolean registrazioneLoginCF = isRegistrazioneLoginCF(utenteDelegatoGestioneUtenti);

        // ho la paginazione attiva

        UserSearchResultDTO searchResult = userCriteriaRepository.loadListaUtenti(form.getDenominazione(),
                form.getUsername(), form.getUsernameCF(), form.getAbilitato(), form.getCodiceFiscale(), form.getEmail(),
                form.getUfficioIntestatarioKeys(), form.getGestioneUtenti(), form.getAmministratore(),
                form.getProfiloKey(), utenteDelegatoGestioneUtenti, registrazioneLoginCF, null, null, sortKey, sortDirectionKey, false);

        try {
            String csvFile = extractCsv(searchResult.getUserList());
            response.setResponse(csvFile);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }

        return response;
    }

    @Override
    public boolean executeCheckMToken(final CheckMTokenDTO checkMTokenDTO) {

        if (checkMTokenDTO == null)
            throw new IllegalArgumentException("checkMTokenDTO null");

        LOGGER.debug("Execution start UserServiceImpl::executeCheckMToken for user [ {} ]",
                checkMTokenDTO.getUsername());

        User found = userRepository.findByLoginIgnoreCase(checkMTokenDTO.getUsername());

        if (found == null)
            return false;

        return isMTokenRequired(found);
    }

    private byte[] getJWTKey() throws CriptazioneException {
        byte[] jwtKey = null;
        WConfigDTO configurazione = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_JWT_KEY_CHIAVE);

        if (configurazione != null) {
            if (StringUtils.isBlank(configurazione.getCriptato())
                    || (StringUtils.isNotBlank(configurazione.getCriptato())
                    && "0".equals(configurazione.getCriptato()))) {
                jwtKey = StringUtils.isNotBlank(configurazione.getValore()) ? configurazione.getValore().getBytes()
                        : null;
            } else if (AppConstants.W_CONFIG_SI.equals(configurazione.getCriptato())) {
                try {
                    ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                            FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, configurazione.getValore().getBytes(),
                            ICriptazioneByte.FORMATO_DATO_CIFRATO);

                    jwtKey = decrypt.getDatoNonCifrato();
                } catch (CriptazioneException e) {
                    LOGGER.error("Errore in fase di decrypt della chiave hash per generazione token", e);
                    throw e;
                }
            }
        }

        return jwtKey;
    }

    private String getInternalToken(UserDTO userDto, boolean refresh) throws CriptazioneException {

        String issuer = null;
        try {
            issuer = InetAddress.getLocalHost().getHostAddress() + "/internalAuthentication/" + apiVersion
                    + "/authorize";
        } catch (UnknownHostException e) {
            LOGGER.error("getInternalToken: metodo creazione token. Impossibile trovare HostName");
            issuer = "UnknownHost";
        }
        byte[] jwtKey = getJWTKey();
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);

        long nowMillis = System.currentTimeMillis();
        long tokenLife = (refresh ? refreshTokenExpireTime : tokenExpireTime) * 1000L;

        JwtBuilder builder = Jwts.builder() //
                .issuedAt(new Date(nowMillis)) //
                .subject(userDto.getCodiceFiscale()) //
                .issuer(issuer) //
                .signWith(secretKey, getSignatureAlgorithm(jwtSignatureAlgorithm)) //
                .expiration(getExpireDate(nowMillis, tokenLife)) //
                .claim(USER_LOGIN, userDto.getUsername())
                .claim(jwtClaimUserCfName, userDto.getCodiceFiscale()) //
                .claim("USER_NAME", userDto.getNome()) //
                .claim("USER_SURNAME", userDto.getCognome()) //
                .claim("USER_DESCRIPTION", userDto.getNome()) //
                .claim("USER_EMAIL", userDto.getEmail()) //
                .claim("internal", true) //
                // Sempre LOA 2 per autenticazione interna
                .claim("USER_LOA", AppConstants.USR_LOA_INTERNAL_AUTHENTICATION_LOA) //
                .claim("USER_IDP_TYPE", AppConstants.USR_LOA_INTERNAL_AUTHENTICATION_TYPE);

        if (currentLoginMode.equals(AppConstants.LOGIN_MODE_TOSCANA)) {
            // TODO rimuovere prima di subito

            String preferredUsername = "tinit-" + userDto.getUsername();

            builder = Jwts.builder() //
                    .issuedAt(new Date(nowMillis)) //
                    .subject(userDto.getCodiceFiscale()) //
                    .issuer(issuer) //
                    .signWith(secretKey, getSignatureAlgorithm(jwtSignatureAlgorithm)) //
                    .expiration(getExpireDate(nowMillis, tokenLife)) //
                    .claim("preferred_username", userDto.getUsername())
                    .claim("cf", userDto.getCodiceFiscale()) //
                    .claim("given_name", userDto.getNome()) //
                    .claim("family_name", userDto.getCognome()) //
                    .claim("USER_DESCRIPTION", userDto.getNome()) //
                    .claim("USER_EMAIL", userDto.getEmail()) //
                    .claim("internal", true) //
                    // Sempre LOA 2 per autenticazione interna
                    .claim("auth_level", AppConstants.USR_LOA_INTERNAL_AUTHENTICATION_LOA) //
                    .claim("auth_type", AppConstants.USR_LOA_INTERNAL_AUTHENTICATION_TYPE);

        }

        builder.audience().add(userDto.getCodiceFiscale());

        return builder.compact();
    }

    private static Date getExpireDate(long nowMillis, long ttlMillis) {
        return new Date(nowMillis + ttlMillis);
    }

    private AccountConfigurationDTO getAccountConfiguration() {

        WConfigDTO durataAccount = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_DURATA_ACCOUNT);
        WConfigDTO intervalloMinimoCambioPassword = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_INTERVALLO_MINIMO_CAMBIO_PASSWORD);
        WConfigDTO loginKODelaySecondi = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_LOGIN_KO_DELAY_SECONDI);
        WConfigDTO loginKODurataBloccoMinuti = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_LOGIN_KO_DURATA_BLOCCO_MINUTI);
        WConfigDTO loginKOMaxNumTentativi = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_LOGIN_KO_MAX_NUM_TENTATIVI);
        WConfigDTO durataPassword = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_DURATA_PASSWORD);

        AccountConfigurationDTO dto = new AccountConfigurationDTO();

        if (StringUtils.isNotBlank(durataAccount.getValore()))
            dto.setDurataAccount(Integer.valueOf(durataAccount.getValore()));

        if (StringUtils.isNotBlank(intervalloMinimoCambioPassword.getValore()))
            dto.setIntervalloMinimoCambioPassword(Integer.valueOf(intervalloMinimoCambioPassword.getValore()));

        if (StringUtils.isNotBlank(loginKODelaySecondi.getValore()))
            dto.setDelaySecondi(Integer.valueOf(loginKODelaySecondi.getValore()));

        if (StringUtils.isNotBlank(loginKODurataBloccoMinuti.getValore()))
            dto.setDurataBloccoMinuti(Integer.valueOf(loginKODurataBloccoMinuti.getValore()));

        if (StringUtils.isNotBlank(loginKOMaxNumTentativi.getValore()))
            dto.setMaxNumTentativi(Integer.valueOf(loginKOMaxNumTentativi.getValore()));

        if (StringUtils.isNotBlank(durataPassword.getValore()))
            dto.setDurataPassword(Integer.valueOf(durataPassword.getValore()));

        return dto;
    }

    private boolean checkPasswordCambiataDiRecente(final AccountConfigurationDTO accountConfigurationDTO,
                                                   final User user) {

        if (accountConfigurationDTO.getIntervalloMinimoCambioPassword() == null)
            return true;

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        Integer intervalloMinimoCambioPasswordSecondi = accountConfigurationDTO.getIntervalloMinimoCambioPassword();
        Integer intervalloMinimoCambioPasswordMillisecondi = intervalloMinimoCambioPasswordSecondi * 1000;

        try {
            String nomeUtenteCifrato = super.getValoreCifrato(user.getLogin());

            StoUteSysDTO dto = stoUteSysService.getCurrentPasswordInformation(nomeUtenteCifrato, user.getPassword());

            if (dto == null) {
                pass = true;
            } else {

                Date now = new Date();

                if (now.getTime() > dto.getSysdat().getTime() + intervalloMinimoCambioPasswordMillisecondi)
                    pass = true;

            }
        } catch (Exception e) {
            LOGGER.error("Eccezione durante la criptazione dell'username", e);
        }

        return pass;
    }

    private UserDTO getUserFromDecodedJWT(final String jwtToken, final DecodedJWT jwt, final String loginMode) throws TokenInvalidException {
        Claim userlogin = jwt.getClaim(USER_LOGIN);

        if (AppConstants.LOGIN_MODE_TOSCANA.equals(loginMode)) {
            userlogin = jwt.getClaim("preferred_username");
        }

        if (userlogin.isNull())
            throw new TokenInvalidException("Token Invalid");

        String username = userlogin.asString();
        if (StringUtils.isEmpty(username))
            throw new TokenInvalidException("Token Invalid");
        try {

            // sanitize
            username = username.toUpperCase().trim();
            if (username.startsWith("TINIT-"))
                username = username.replace("TINIT-", "");

            User user = userRepository.findByLoginIgnoreCase(username);

            if (user == null)
                throw new TokenInvalidException("Token Invalid");

            if (!AppConstants.LOGIN_MODE_TOSCANA.equals(loginMode)) {
                // Check in w_ssojwttoken
                Optional<WSsoJwtToken> optionalWSsoJwtToken = wSsoJwtTokenRepository.findById(username);
                if (optionalWSsoJwtToken.isEmpty())
                    throw new TokenInvalidException("Token Invalid");
                WSsoJwtToken wSsoJwtToken = optionalWSsoJwtToken.get();
                if (!wSsoJwtToken.getJwtToken().equals(jwtToken))
                    throw new TokenInvalidException("Token Invalid");
            }

            return getUserDTOFromUser(user);

        } catch (Exception e) {
            throw new TokenInvalidException("Token Invalid");
        }
    }

    private UserDTO getUserDTOFromUser(final User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setSyscon(user.getSyscon());
        userDTO.setUsername(user.getLogin());
        userDTO.setCodiceFiscale(user.getCodiceFiscale());
        userDTO.setNome(user.getSysute());
        userDTO.setCognome("");
        userDTO.setEmail(user.getEmail());

        boolean userLdap = user.getLdap() != null && (user.getLdap() == 1);
        userDTO.setLdap(userLdap);

        // Utente abilitato: sysdisab = null oppure sysdisab = 0, utente disabilitato: sysdisab != 0
        // Per considerare i casi anomali presenti in alcuni DB
        boolean userDisabilitato = !isUserAbilitato(user);

        userDTO.setDisabilitato(userDisabilitato);

        userDTO.setUfficioAppartenenza(user.getUfficioAppartenenza());
        userDTO.setCategoria(user.getCategoria());

        if (StringUtils.isNotBlank(user.getSyspwbou())) {
            userDTO.setOpzioniUtente(getListaOpzioniUtente(user.getSyspwbou()));
        }

        userDTO.setDataScadenzaAccount(user.getDataScadenzaAccount());
        userDTO.setDataUltimoAccesso(user.getDataUltimoAccesso());

        userDTO.setSysab3(user.getSysab3());

        boolean deletable = user.getSyscon() != 48L && user.getSyscon() != 49L && user.getSyscon() != 50L
                && user.getDataUltimoAccesso() == null;
        userDTO.setDeletable(deletable);

        if (user.getUffints() != null)
            userDTO.setUfficiIntestatari(user.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList()));

        return userDTO;
    }

    private UserConnectedDTO getUserConnectedDTOFromUser(final User user) {

        UserConnectedDTO userConnectedDTO = new UserConnectedDTO();

        userConnectedDTO.setSyscon(user.getSyscon());
        userConnectedDTO.setUsername(user.getLogin());
        userConnectedDTO.setDenominazione(user.getSysute());
        userConnectedDTO.setEmail(user.getEmail());

        return userConnectedDTO;
    }

    private UserInsertDTO parseUtenteInsertDTO(final UserInsertDTO dto) {

        // Uppercase username
        dto.setUsername(StringUtils.stripToNull(dto.getUsername()));
        if (StringUtils.isNotBlank(dto.getUsername()))
            dto.setUsername(dto.getUsername().toUpperCase());

        // Uppercase CF
        dto.setUsernameCF(StringUtils.stripToNull(dto.getUsernameCF()));
        if (StringUtils.isNotBlank(dto.getUsernameCF()))
            dto.setUsernameCF(dto.getUsernameCF().toUpperCase().trim());

        dto.setEmail(StringUtils.stripToNull(dto.getEmail()));
        if (StringUtils.isNotBlank(dto.getEmail()))
            dto.setEmail(dto.getEmail().trim());

        // Uppercase CF
        dto.setCodiceFiscale(StringUtils.stripToNull(dto.getCodiceFiscale()));
        if (StringUtils.isNotBlank(dto.getCodiceFiscale()))
            dto.setCodiceFiscale(dto.getCodiceFiscale().toUpperCase().trim());

        return dto;
    }

    private UserEditDTO parseUtenteEditDTO(final UserEditDTO dto) {

        // Uppercase username
        dto.setUsername(StringUtils.stripToNull(dto.getUsername()));
        if (StringUtils.isNotBlank(dto.getUsername()))
            dto.setUsername(dto.getUsername().toUpperCase());

        dto.setEmail(StringUtils.stripToNull(dto.getEmail()));
        if (StringUtils.isNotBlank(dto.getEmail()))
            dto.setEmail(dto.getEmail().trim());

        // Uppercase CF
        dto.setCodiceFiscale(StringUtils.stripToNull(dto.getCodiceFiscale()));
        if (StringUtils.isNotBlank(dto.getCodiceFiscale()))
            dto.setCodiceFiscale(dto.getCodiceFiscale().toUpperCase().trim());

        return dto;
    }

    private String getStringOpzioniUtente(final List<String> listaOpzioniUtente) {
        StringBuilder opzioniUtenteString = null;

        if (listaOpzioniUtente != null && !listaOpzioniUtente.isEmpty()) {
            opzioniUtenteString = new StringBuilder();
            listaOpzioniUtente.sort(OUOrderComparator.createOUOrderComparator());
            for (String opzione : listaOpzioniUtente) {
                opzioniUtenteString.append(opzione).append("|");
            }
        }

        return opzioniUtenteString.toString();
    }

    private boolean isUserAdministrator(final User user) {

        if (user == null)
            return false;

        return super.isUserAdministrator(user.getSyspwbou());
    }

    private boolean isUserAbilitato(final User user) {

        if (user == null)
            return false;

        return StringUtils.isBlank(user.getDisabilitato())
                || (StringUtils.isNotBlank(user.getDisabilitato()) && AppConstants.SYSDISAB_UTENTE_ABILITATO.equals(user.getDisabilitato()));
    }

    private boolean inviaMailAttivazioneDisattivazione(final User user, final boolean flagAbilitato) {

        String nomeMittente = wConfigService.getConfigurationValue(AppConstants.TITOLO_APPLICATIVO);

        String intestazione = user.getSysute();
        String login = user.getLogin();
        String oggettoMail = null;
        String templateFile = "email/{0}.html";

        if (flagAbilitato) {
            oggettoMail = "Abilitazione accesso al sistema {0}";
            templateFile = MessageFormat.format(templateFile, "abilitazioneutente");
        } else {
            oggettoMail = "Disabilitazione accesso al sistema {0}";
            templateFile = MessageFormat.format(templateFile, "disabilitazioneutente");
        }

        oggettoMail = MessageFormat.format(oggettoMail, nomeMittente);

        try {
            String emailMittente = emailComponent.getEmailMittente();
            JavaMailSender mailSender = emailComponent.getJavaMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(new InternetAddress(emailMittente, nomeMittente));

            Map<String, Object> properties = new HashMap<>();
            properties.put("intestazione", intestazione);
            properties.put("nomeMittente", nomeMittente);
            properties.put("login", login);

            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process(templateFile, context);
            helper.setText(html, true);

            List<String> receivers = new ArrayList<String>();
            receivers.add(user.getEmail());

            List<String> receiverCC = new ArrayList<String>();
            List<String> receiversCCn = new ArrayList<String>();

            helper.setTo(receivers.toArray(new String[0]));
            helper.setCc(receiverCC.toArray(new String[0]));
            helper.setBcc(receiversCCn.toArray(new String[0]));

            helper.setSubject(oggettoMail);
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            LOGGER.warn("Errore durante l'invio della mail di attivazione/disattivazione", e);
        }
        return false;
    }

    private boolean isGestioneGruppiDisattivata() {

        String value = wConfigService.getConfigurationValue(AppConstants.GESTIONE_GRUPPI_DISABILITATA);

        return StringUtils.isNotBlank(value) && AppConstants.W_CONFIG_SI.equals(value);
    }

    private String generatePasswordRecoveryToken() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    private boolean inviaMailRecuperoPassword(final User user, final WUsrToken wUsrToken, final String ipAddress,
                                              final String externalCurrentUrl) throws Exception {

        String nomeMittente = wConfigService.getConfigurationValue(AppConstants.TITOLO_APPLICATIVO);
        String oggettoMail = MessageFormat.format("Richiesta recupero password per l''applicativo {0}", nomeMittente);

        String link = null;

        if (useExternalUrlForRecuperoPassword) {
            String excu = externalCurrentUrl != null && externalCurrentUrl.endsWith("/") ? externalCurrentUrl.substring(0, externalCurrentUrl.lastIndexOf("/")) : externalCurrentUrl;
            link = excu + recuperoPasswordUrl;
        } else {
            link = recuperoPasswordUrl;
        }

        link += "?token=" + wUsrToken.getToken();

        try {
            String emailMittente = emailComponent.getEmailMittente();
            JavaMailSender mailSender = emailComponent.getJavaMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(new InternetAddress(emailMittente, nomeMittente));

            Map<String, Object> properties = new HashMap<>();
            properties.put("name", user.getSysute());
            properties.put("userName", user.getLogin());
            properties.put("link", link);

            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process("email/passwordrecovery.html", context);
            helper.setText(html, true);

            List<String> receivers = new ArrayList<String>();
            receivers.add(user.getEmail());

            List<String> receiverCC = new ArrayList<String>();
            List<String> receiversCCn = new ArrayList<String>();

            helper.setTo(receivers.toArray(new String[0]));
            helper.setCc(receiverCC.toArray(new String[0]));
            helper.setBcc(receiversCCn.toArray(new String[0]));

            helper.setSubject(oggettoMail);
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            LOGGER.warn("Errore durante l'invio della mail di recupero password", e);
            String errMsgEvento = "Errore durante l'invio della mail di recupero password: " + e.getMessage();

            int livelloEvento = LogEventiUtils.LIVELLO_ERROR;

            // log in w_logeventi
            String logMessage = MessageFormat.format("Richiesta recupero password con token [ {0} ]",
                    wUsrToken.getToken());
            WLogEventiDTO logEventiDTO = LogEventiUtils.createPasswordRecoveryEvent(logMessage,
                    user.getSyscon(), ipAddress);
            logEventiDTO.setCodApp(codiceProdotto);
            logEventiDTO.setLivelloEvento(livelloEvento);
            logEventiDTO.setErrorMessage(errMsgEvento);
            wLogEventiService.insertLogEvent(logEventiDTO);

            throw e;
        }
    }

    private Long getValiditaTokenMillis() {
        String validitaTokenMinutiString = wConfigService
                .getStandardConfigurationValue(AppConstants.W_CONFIG_VALIDITA_TOKEN_MINUTI);

        if (StringUtils.isNotBlank(validitaTokenMinutiString) && StringUtils.isNumeric(validitaTokenMinutiString))
            return Long.parseLong(validitaTokenMinutiString) * 60 * 1000;

        return null;
    }

    private boolean checkUserSAEnabledMismatch(final List<String> listaOpzioniUtente) {

        if (listaOpzioniUtente != null && !listaOpzioniUtente.isEmpty()) {
            /*
             * Se si salva un utente con "Gestione utenti/amministrazioni"<>Gestione
             * completa e "Abilita l'utente alla modifica delle amministrazioni"=si, dare un
             * warning:
             * "Attenzione: l'utente e' abilitato alla modifica delle amministrazioni, ma non ha accesso alla gestione utenti/amministrazioni"
             */
            return !listaOpzioniUtente.contains(AppConstants.OU_BLOCCA_UTENTE_MODIFICA_UFFICI_INTESTATARI)
                    && ((
                    // non contiene gestione utenti
                    !listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_COMPLETA)
                            && !listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_OU12)) || (
                    // contiene gestione utenti in sola lettura
                    listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_COMPLETA)
                            && listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_OU12)));
        }
        return false;
    }

    private String offuscaEmail(final String email) {
        return StringUtils.isNotBlank(email) ? email.replaceAll("(?<=.)[^@](?=[^@]*?@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*\\.)", "*") : null;
    }

    private String extractCsv(List<User> users) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringWriter out = new StringWriter();
        String[] HEADERS;
        if ("W9".equals(codiceProdotto)) {
            HEADERS = new String[]{"Codice utente", "Descrizione", "Login", "E-mail", "Enti associati", "Ruolo", "Data ultimo accesso"};
        } else {
            HEADERS = new String[]{"Codice utente", "Descrizione", "Login", "E-mail", "Enti associati", "Data ultimo accesso"};
        }

        final CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader(HEADERS)
                .setDelimiter(";")
                .setAllowMissingColumnNames(true)
                .build();

        CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                StringBuilder enti = new StringBuilder();
                for (Uffint uffint : user.getUffints()) {
                    enti.append(uffint.getDenominazione());
                    enti.append(",");
                }
                if (StringUtils.isNotBlank(enti)) {
                    enti.delete(enti.toString().length() - 1, enti.toString().length());
                }
                if ("W9".equals(codiceProdotto)) {
                    csvPrinter.printRecord(
                            user.getSyscon(),
                            user.getSysute(),
                            user.getLogin(),
                            user.getEmail() == null ? "" : CsvUtil.escapeCsv(user.getEmail()),
                            CsvUtil.escapeCsv(enti.toString()),
                            user.getSysab3() != null && user.getSysab3().equals("A") ? "Responsabile" : "Utente",
                            user.getDataUltimoAccesso() == null ? "" : CsvUtil.escapeCsv(sdf.format(user.getDataUltimoAccesso()))
                    );
                } else {
                    csvPrinter.printRecord(
                            user.getSyscon(),
                            user.getSysute(),
                            user.getLogin(),
                            user.getEmail() == null ? "" : CsvUtil.escapeCsv(user.getEmail()),
                            CsvUtil.escapeCsv(enti.toString()),

                            user.getDataUltimoAccesso() == null ? "" : CsvUtil.escapeCsv(sdf.format(user.getDataUltimoAccesso()))
                    );
                }

            }
        } else {
            return "";
        }

        return out.toString();

    }

    private boolean isRegistrazioneLoginCF(final boolean utenteDelegatoGestioneUtenti) {
        String registrazioneLoginCFString = wConfigService.getConfigurationValue(AppConstants.W_CONFIG_REGISTRAZIONE_LOGIN_CF);
        // considero registrazione.loginCF solamente per i delegati
        return utenteDelegatoGestioneUtenti && StringUtils.isNotBlank(registrazioneLoginCFString) && AppConstants.W_CONFIG_SI.equals(registrazioneLoginCFString);
    }

    private boolean checkAdminOrAllSA(User toUpdate) {
        // Escludo admin e abilitati a tutte le SA
        return StringUtils.isNotBlank(toUpdate.getSyspwbou()) &&
                (
                        toUpdate.getSyspwbou().toLowerCase().contains(AppConstants.OU_AMMINISTRATORE.toLowerCase())
                                || toUpdate.getSyspwbou().toLowerCase().contains(AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI.toLowerCase())
                );
    }

    /**
     * Metodo per verificare i permessi dell'utente in sessione alla modifica dell'utente selezionato
     * Dato che il getUffints() e' lazy, il metodo deve essere chiamato in una transazione per poter eseguire la join
     *
     * @param utenteDelegatoGestioneUtenti booleano che identifica se l'utente in sessione e' un delegato alla gestione utenti
     * @param sessionUser                  Utente in sessione
     * @param toUpdate                     Utente da modificare
     */
    private void checkUserDelegatoEditPermissions(final boolean utenteDelegatoGestioneUtenti, final User sessionUser, User toUpdate) {
        if (utenteDelegatoGestioneUtenti) {
            // Escludo admin e abilitati a tutte le SA
            boolean adminOrTutteSA = checkAdminOrAllSA(toUpdate);

            // Controllo che non ci siano stazioni appaltanti diverse da quelle associate all'utente gestore
            boolean containsOnlySaGestore = false;
            if (sessionUser.getUffints() != null && !sessionUser.getUffints().isEmpty() && toUpdate.getUffints() != null && !toUpdate.getUffints().isEmpty()) {
                List<String> toUpdateCodeins = super.getUffintsCodeinListFromUser(toUpdate);
                List<String> currentUserCodeins = super.getUffintsCodeinListFromUser(sessionUser);
                toUpdateCodeins.removeAll(currentUserCodeins);
                if (toUpdateCodeins.isEmpty()) {
                    containsOnlySaGestore = true;
                }
            }

            if (adminOrTutteSA || !containsOnlySaGestore) {
                LOGGER.error("L'utente [ {} ] ha tentato di modificare il dettaglio dell'utente con syscon [ {} ]", sessionUser.getSyscon(), toUpdate.getSyscon());

                throw new UserEditForbiddenException();
            }
        }
    }

    /**
     * Metodo per verificare i permessi dell'utente in sessione al cambio password dell'utente selezionato
     * Dato che il getUffints() e' lazy, il metodo deve essere chiamato in una transazione per poter eseguire la join
     *
     * @param utenteDelegatoGestioneUtenti booleano che identifica se l'utente in sessione e' un delegato alla gestione utenti
     * @param sessionUser                  Utente in sessione
     * @param toUpdate                     Utente da modificare
     */
    private void checkUserDelegatoChangePasswordPermissions(final boolean utenteDelegatoGestioneUtenti, final User sessionUser, User toUpdate) {
        if (utenteDelegatoGestioneUtenti) {
            // Escludo admin e abilitati a tutte le SA
            boolean adminOrTutteSA = checkAdminOrAllSA(toUpdate);

            // Controllo che non ci siano stazioni appaltanti diverse da quelle associate all'utente gestore
            boolean containsSaGestore = false;
            if (sessionUser.getUffints() != null && !sessionUser.getUffints().isEmpty() && toUpdate.getUffints() != null && !toUpdate.getUffints().isEmpty()) {
                List<String> toUpdateCodeins = super.getUffintsCodeinListFromUser(toUpdate);
                List<String> currentUserCodeins = super.getUffintsCodeinListFromUser(sessionUser);
                containsSaGestore = currentUserCodeins.stream().anyMatch(toUpdateCodeins::contains);
            }

            if (adminOrTutteSA || !containsSaGestore) {
                LOGGER.error("L'utente [ {} ] ha tentato di modificare la password dell'utente con syscon [ {} ]", sessionUser.getSyscon(), toUpdate.getSyscon());

                throw new UserEditForbiddenException();
            }
        }
    }

    private boolean isMTokenRequired(final User user) {
        return user.getSyscon() == 47L || user.getSyscon() == 48L;
    }
}
