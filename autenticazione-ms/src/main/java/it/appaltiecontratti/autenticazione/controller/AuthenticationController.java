package it.appaltiecontratti.autenticazione.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.autenticazione.Constants;
import it.appaltiecontratti.autenticazione.bl.AccountManager;
import it.appaltiecontratti.autenticazione.entity.*;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Servizio REST esposto al path "/autenticazione".
 */
@RestController
@RequestMapping(value = {"${protected.context-path}/gestioneUtenti"})
@CrossOrigin
@EnableTransactionManagement
@SuppressWarnings("java:S5122")
public class AuthenticationController extends AbstractBaseController {
    /**
     * Logger di classe.
     */
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private UserManager userManager;

    /**
     * Estrae le info relative ad un account.
     *
     * @param searchUfficioIntestatario ricerca ufficio intestatario
     * @return JSON contenente una lista della classe UserAccountResult
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/AccountInfo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 404, message = "L'username non e' censito o non ha accesso a nessuna stazione appaltante"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<UserAccountResult> accountInfo(@RequestHeader("Authorization") String authorization,
                                                         @RequestHeader("X-loginMode") String xLoginMode,
                                                         @ApiParam(value = "ricerca ufficio intestatario") @RequestParam(value = "searchUfficioIntestatario", required = false) String searchUfficioIntestatario) {

        logger.info("accountInfo: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        UserAccountResult risultato = new UserAccountResult();
        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
        risultato = this.accountManager.getUserInfo(syscon, searchUfficioIntestatario);
        if (risultato.getErrorData() != null) {
            if (UserAccountResult.NO_SA.equals(risultato.getErrorData())
                    || UserAccountResult.NO_USER.equals(risultato.getErrorData())) {
                status = HttpStatus.NOT_FOUND;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        logger.info("accountInfo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/GetSAInfo")
    @ApiOperation(nickname = "TabellatiController.getStazioneAppaltanteInfo", value = "Restituisce i dati della stazione appaltante", response = ResponseStazioneAppaltante.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseStazioneAppaltante> getStazioneAppaltanteInfo(
            @ApiIgnore HttpServletRequest request,
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Codice stazione appaltante da cercare", required = true) @RequestParam(value = "stazioneAppaltante") String stazioneAppaltante,
            @ApiParam(value = "codice identificativo dell'utente", required = true) @RequestParam(value = "syscon") Long syscon,
            @ApiParam(value = "codice applicazione per il reprimento dei profili", required = true) @RequestParam(value = "codapp") String codapp,
            @ApiParam(value = "booleano per indicare la selezione della SA", required = false) @RequestParam(value = "selezioneSA", required = false) Boolean selezioneSA
    ) {

        logger.info("get info stazione appaltante: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        final String ipAddress = resolveRemoteIpAddress(request);
        ResponseStazioneAppaltante risultato = new ResponseStazioneAppaltante();
        if (stazioneAppaltante == null || syscon == null || codapp == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Passati parametri obbligatori a null");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        if(selezioneSA == null) {
            risultato = accountManager.getStazioneAppaltanteInfo(stazioneAppaltante, syscon, codapp, false, null);
        }
        else {
            risultato = accountManager.getStazioneAppaltanteInfo(stazioneAppaltante, syscon, codapp, selezioneSA, ipAddress);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        logger.info("get info stazione appaltante: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/GetAbilitazioni")
    @ApiOperation(value = "Restituisce i codici di abilitazioni dell'utente", response = ResponseStazioneAppaltante.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseAbilitazioni> getAbilitazioni(@RequestHeader("Authorization") String authorization,
                                                                @RequestHeader("X-loginMode") String xLoginMode) {

        logger.info("get abilitazioni: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseAbilitazioni risultato = new ResponseAbilitazioni();
        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
        risultato = accountManager.getAbilitazioniUtenteEdOpzioniProdotto(syscon);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.BAD_REQUEST;

        }
        logger.info("get abilitazioni: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/GetProfilo")
    @ApiOperation(value = "Restituisce i dati del profilo", response = ResponseStazioneAppaltante.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseConfigurazioneProfilo> getProfiloInfo(@ApiIgnore HttpServletRequest request,
                                                                        @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
                                                                        @ApiParam(value = "Codice profilo da cercare", required = true) @RequestParam(value = "codProfilo") String codProfilo) {

        logger.info("get info profilo: inizio metodo");

        final String ipAddress = resolveRemoteIpAddress(request);
        final Long syscon = getUserSyscon(authorization, xLoginMode);

        HttpStatus status = HttpStatus.OK;
        ResponseConfigurazioneProfilo risultato = new ResponseConfigurazioneProfilo();
        if (codProfilo == null || "".equals(codProfilo)) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("codProfilo obbligatorio");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = accountManager.getProfilo(codProfilo, syscon, ipAddress);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.BAD_REQUEST;

        }
        logger.info("get info profilo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userRegistration")
    @ApiOperation(nickname = "AuthenticationController.refreshToken", value = "Genera un nuovo jwt token", response = ResponseResult.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<UserRegistrationResponse> userRegistration(
            @RequestHeader("Authorization") String authorization, @RequestBody @Valid UserAccountForm userAccountForm) {

        logger.info("userRegistration: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        UserRegistrationResponse risultato = new UserRegistrationResponse();
        try {
            risultato = this.accountManager.userRegistration(userAccountForm, false);
        } catch (MailSendException ex) {
            logger.error("Errore in fase di registrazione dell'utente " + userAccountForm.getCodiceFiscale(), ex);
            risultato.setEsito(false);
            risultato.setErrorData(UserRegistrationResponse.MAIL_NOT_SENT);
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        } catch (Exception e) {
            logger.error("Errore in fase di registrazione dell'utente " + userAccountForm.getCodiceFiscale(), e);
            risultato.setEsito(false);
            risultato.setErrorData(UserRegistrationResponse.ERROR_UNEXPECTED);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("userRegistration: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkUserExists")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseUserExists> checkUserExists(final HttpServletRequest request,
                                                              @RequestHeader("Authorization") String authorization,
                                                              @RequestParam(value = "username") String username) {

        logger.info("checkUserExists: inizio metodo");

        ResponseUserExists risultato = new ResponseUserExists();
        HttpStatus status = HttpStatus.OK;
        if (username == null || "".contentEquals(username)) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("username obbligatorio");
            risultato.setEsito(false);
            return ResponseEntity.status(status.value()).body(risultato);
        }
        final String ipAddress = resolveRemoteIpAddress(request);
        risultato = this.accountManager.checkUserExists(username, ipAddress);
        logger.info("userRegistration: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUtentiStazioneAppaltante")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseUtentiStazioneAppaltante> getUtentiStazioneAppaltante(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "codiceSA") String codiceSA) {

        logger.info("getUtentiStazioneAppaltante: inizio metodo");

        ResponseUtentiStazioneAppaltante risultato = new ResponseUtentiStazioneAppaltante();
        HttpStatus status = HttpStatus.OK;
        if (StringUtils.isBlank(codiceSA)) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("codiceSA obbligatorio");
            risultato.setEsito(false);
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasGestioneUtentiCompletaPermission(authorization, xLoginMode);
        if (permission) {

            risultato = this.accountManager.getUtentiStazioneAppaltante(codiceSA);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUtentiStazioneAppaltante.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getUtentiStazioneAppaltante: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUtenti")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseUtentiStazioneAppaltante> getAllUtenti(
            @RequestHeader("Authorization") String authorization) {

        logger.info("getAllUtenti: inizio metodo");

        ResponseUtentiStazioneAppaltante risultato = new ResponseUtentiStazioneAppaltante();
        HttpStatus status = HttpStatus.OK;
        risultato = this.accountManager.getAllUtenti();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getAllUtenti: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/setUtentiStazioneAppaltante")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseUpdateUtentiStazioneAppaltante> setUtentiStazioneAppaltante(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "campi della gara da modificare", required = true) @RequestBody @Valid UtentiStazioneAppaltanteForm form) {

        logger.info("setUtentiStazioneAppaltante: inizio metodo");

        ResponseUpdateUtentiStazioneAppaltante risultato = new ResponseUpdateUtentiStazioneAppaltante();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasGestioneUtentiCompletaPermission(authorization, xLoginMode);
        if (permission) {

            risultato = this.accountManager.setUtentiStazioneAppaltante(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUtentiStazioneAppaltante.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("setUtentiStazioneAppaltante: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    private boolean hasGestioneUtentiCompletaPermission(String authorization, String loginMode) {

        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();
            ResponseAbilitazioni ra = this.accountManager.getAbilitazioniUtenteEdOpzioniProdotto(syscon);
            if (ra != null && ra.getData() != null) {
                OpzioniUtenteProdotto oup = ra.getData();
                return oup.getOu().contains(Constants.OU_GESTIONE_UTENTI_COMPLETA)
                        && !oup.getOu().contains(Constants.OU_GESTIONE_UTENTI_OU12);
            }
        } catch (Exception e) {
            logger.error("Errore durante il controllo dei permessi", e);
            return false;
        }
        return false;
    }

    private boolean hasGestioneUtentiOrUtenteGestore(final String authorization, final String loginMode) {

        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();
            ResponseAbilitazioni ra = this.accountManager.getAbilitazioniUtenteEdOpzioniProdotto(syscon);
            if (ra != null && ra.getData() != null) {
                OpzioniUtenteProdotto oup = ra.getData();
                List<String> listaOpzioniUtente = oup.getOu();
                // Gestione completa
                boolean hasGestioneUtentiCompleta = listaOpzioniUtente.contains(Constants.OU_GESTIONE_UTENTI_COMPLETA) && !listaOpzioniUtente.contains(Constants.OU_GESTIONE_UTENTI_OU12);
                boolean isAdministrator = listaOpzioniUtente.contains(Constants.OU_AMMINISTRATORE);
                boolean hasAllSAAccess = listaOpzioniUtente.contains(Constants.OU_ABILITA_TUTTE_SA);

                return hasGestioneUtentiCompleta && (isAdministrator || hasAllSAAccess);
            }
        } catch (Exception e) {
            logger.error("Errore durante il controllo dei permessi", e);
            return false;
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getConfigurazioniUtenti")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponsePersonalizzazioniUtente> getConfigurazioniUtenti(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "syscon utente") @RequestParam(value = "syscon") Long syscon) {

        logger.info("getConfigurazioniUtenti: inizio metodo");

        ResponsePersonalizzazioniUtente risultato = new ResponsePersonalizzazioniUtente();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasGestioneUtentiCompletaPermission(authorization, xLoginMode);
        if (permission) {

            risultato = this.accountManager.getConfigurazioniUtenti(syscon);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUtentiStazioneAppaltante.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getConfigurazioniUtenti: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/modificaConfigurazioniUtenti")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> modificaConfigurazioniUtenti(@RequestHeader("X-loginMode") String xLoginMode,
                                                                       @RequestHeader("Authorization") String authorization,
                                                                       @RequestBody @Valid PersonalizzazioniUtentiInsertForm insertForm) {

        logger.info("modificaConfigurazioniUtenti: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasGestioneUtentiOrUtenteGestore(authorization, xLoginMode);
        if (permission) {

            risultato = this.accountManager.modificaConfigurazioniUtenti(insertForm.getSyscon(), insertForm.getSysab3(),
                    insertForm.getIdCentroCosto());
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(ResponseUtentiStazioneAppaltante.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("modificaConfigurazioniUtenti: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    private Long getUserSyscon(String authorization, String loginMode) {

        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();
            return syscon;
        } catch (Exception e) {
            logger.error("Errore durante il controllo dei permessi", e);
        }
        return null;
    }

}
