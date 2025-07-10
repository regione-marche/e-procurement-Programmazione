package it.appaltiecontratti.autenticazione.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.autenticazione.Constants;
import it.appaltiecontratti.autenticazione.bl.AccountManager;
import it.appaltiecontratti.autenticazione.bl.SSOGeneralManager;
import it.appaltiecontratti.autenticazione.entity.*;
import it.appaltiecontratti.autenticazione.exceptions.UserPublicRegistrationPasswordException;
import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 02, 2023
 */
@RestController
@RequestMapping("${public.context-path}/gestioneUtenti")
@CrossOrigin
@SuppressWarnings("java:S5122")
public class AuthenticationPublicController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationPublicController.class);

    @Autowired
    private SSOGeneralManager ssoGeneralManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private WConfigManager wConfigManager;

    @GetMapping(value = "/retrieveAuthenticationToken", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 404, message = "L'authorizationCode non e' valido"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione")})
    public ResponseEntity<ParametricResponseResult<SSOTokenInfo>> retrieveAuthenticationToken(
            @RequestParam("authorizationCode") final String authorizationCode
    ) {
        LOGGER.debug("Execution start AuthenticationPublicController::retrieveAuthenticationToken for authorizationCode [ {} ]", authorizationCode);

        ParametricResponseResult<SSOTokenInfo> response = ssoGeneralManager.retrieveAuthenticationToken(authorizationCode);

        HttpStatus status = HttpStatus.OK;

        if (!response.isEsito()) {
            if (ParametricResponseResult.ERROR_NOT_FOUND.equals(response.getErrorData()))
                status = HttpStatus.NOT_FOUND;
            else
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status.value()).body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getInizSpid")
    public ResponseEntity<ResponseSpidIniz> getInizSpid() {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getInizSpid: inizio metodo");
        }
        ResponseSpidIniz risultato = new ResponseSpidIniz();
        HttpStatus status = HttpStatus.OK;
        risultato = this.ssoGeneralManager.getInizSpid();
        if (!risultato.isEsito()) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        LOGGER.debug("getInizSpid: fine metodo");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSpidSa")
    public ResponseEntity<SpidSAResponse> getSpidSa(
            @RequestParam(value = "searchUfficioIntestatario", required = false) String searchUfficioIntestatario) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getSpidSa: inizio metodo");
        }
        SpidSAResponse risultato = new SpidSAResponse();
        HttpStatus status = HttpStatus.OK;
        risultato = this.ssoGeneralManager.getSaSpid(searchUfficioIntestatario);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        LOGGER.debug("getSpidSa: fine metodo");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getInfoSpid")
    public ResponseEntity<ResponseSpidUrl> getInfoSpid(
            @RequestParam(value = "idProvider") String idProvider,
            @RequestParam(value = "callbackUrl") String callbackUrl,
            @RequestParam(value = "codein") String codein) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getInfoSpid: inizio metodo");
        }
        ResponseSpidUrl risultato = new ResponseSpidUrl();
        HttpStatus status = HttpStatus.OK;
        if (idProvider == null || "".equals(idProvider) || callbackUrl == null || "".equals(callbackUrl)) {
            risultato.setEsito(false);
            risultato.setErrorData("Parametri obbligatori non valorizzati");
            status = HttpStatus.BAD_REQUEST;
        } else {
            risultato = this.ssoGeneralManager.prepareLoginSpid(idProvider, callbackUrl, codein);
            if (!risultato.isEsito()) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        LOGGER.debug("getInfoSpid: fine metodo");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSpidUser")
    public ResponseEntity<ResponseSpidAccount> getSpidUser(
            @RequestParam(value = "authId") String authId,
            @RequestParam(value = "codein") String codein,
            @RequestParam(value = "provider") String provider) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getSpidUser: inizio metodo");
        }
        ResponseSpidAccount risultato = null;
        HttpStatus status = HttpStatus.OK;
        risultato = this.ssoGeneralManager.getUserAuth(authId, codein, provider);
        if (!risultato.isEsito()) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        LOGGER.debug("getSpidUser: fine metodo");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getInfoCie")
    public ResponseEntity<ResponseSpidUrl> getInfoCie(
            @RequestParam(value = "callbackUrl") String callbackUrl) {


        LOGGER.info("getInfoCie: inizio metodo");

        ResponseSpidUrl risultato = new ResponseSpidUrl();
        HttpStatus status = HttpStatus.OK;
        if (callbackUrl == null || "".equals(callbackUrl)) {
            risultato.setEsito(false);
            risultato.setErrorData("Parametri obbligatori non valorizzati");
            status = HttpStatus.BAD_REQUEST;
        } else {
            risultato = this.ssoGeneralManager.prepareLoginCie(callbackUrl);
            if (!risultato.isEsito()) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        LOGGER.info("getInfoCie: fine metodo");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userRegistration")
    @ApiOperation(nickname = "AuthenticationController.userRegistration", value = "Registrazione utente pubblica", response = UserRegistrationResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<UserRegistrationResponse> userPublicRegistration(
            @Valid @RequestBody UserAccountForm userAccountForm) {

        LOGGER.info("userPublicRegistration: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        UserRegistrationResponse risultato = new UserRegistrationResponse();
        try {

            if (userAccountForm != null) {
                // check CF
                if (StringUtils.isBlank(userAccountForm.getCodiceFiscaleLogin())) {
                    status = HttpStatus.BAD_REQUEST;
                    risultato.setEsito(false);
                    List<String> errors = new ArrayList<>();
                    errors.add(UserRegistrationResponse.CODICE_FISCALE_LOGIN_NULL);
                    risultato.setPasswordErrors(errors);
                } else if (StringUtils.isBlank(userAccountForm.getPassword())) {
                    status = HttpStatus.BAD_REQUEST;
                    risultato.setEsito(false);
                    List<String> errors = new ArrayList<>();
                    errors.add(UserRegistrationResponse.PASSWORD_NULL);
                    risultato.setPasswordErrors(errors);
                } else if (StringUtils.isBlank(userAccountForm.getConfermaPassword())) {
                    status = HttpStatus.BAD_REQUEST;
                    risultato.setEsito(false);
                    List<String> errors = new ArrayList<>();
                    errors.add(UserRegistrationResponse.CONFERMA_PASSWORD_NULL);
                    risultato.setPasswordErrors(errors);
                } else {
                    risultato = this.accountManager.userRegistration(userAccountForm, true);
                    if (!risultato.isEsito() && StringUtils.isNotBlank(risultato.getErrorData())) {
                        status = HttpStatus.BAD_REQUEST;
                    }
                }
            }
        } catch (MailSendException ex) {
            LOGGER.error("Errore in fase di registrazione dell'utente " + userAccountForm.getCodiceFiscale(), ex);
            risultato.setEsito(false);
            risultato.setErrorData(UserRegistrationResponse.MAIL_NOT_SENT);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (UserPublicRegistrationPasswordException ex) {
            risultato.setEsito(false);
            risultato.setPasswordErrors(ex.getErrors());
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            LOGGER.error("Errore in fase di registrazione dell'utente " + userAccountForm.getCodiceFiscale(), e);
            risultato.setEsito(false);
            risultato.setErrorData(UserRegistrationResponse.ERROR_UNEXPECTED);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        LOGGER.info("userPublicRegistration: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/isUserRegistrationActive")
    @ApiOperation(nickname = "AuthenticationController.isUserRegistrationActive", value = "Recupera lo stato di attivazione della form di registrazione", response = ResponseResult.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> isUserRegistrationActive() {
        LOGGER.debug("Execution start AuthenticationController::isUserRegistrationActive");

        ResponseResult response = new ResponseResult();
        HttpStatus status = HttpStatus.OK;

        response.setEsito(true);

        String value = wConfigManager.getConfigurationValue(Constants.W_CONFIG_REGISTRAZIONE_ATTIVA);
        String active = StringUtils.isNotBlank(value) && "1".equals(value) ? "1" : "0";
        response.setData(active);

        return ResponseEntity.status(status.value()).body(response);
    }
}
