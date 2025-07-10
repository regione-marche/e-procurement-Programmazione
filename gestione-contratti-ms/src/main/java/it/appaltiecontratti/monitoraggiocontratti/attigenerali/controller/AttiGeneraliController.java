package it.appaltiecontratti.monitoraggiocontratti.attigenerali.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.bl.AttiGeneraliManager;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.ResponseDettaglioAttoGenerale;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.ResponseListaAttiGenerali;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.AttoGeneraleInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.AttoGeneraleUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.RicercaAttiGeneraliForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.GeneralBaseResponse;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Validated
@CrossOrigin
@RestController
@EnableTransactionManagement
@RequestMapping(value = "${protected.context-path}/gestioneAttiGenerali")
@SuppressWarnings("java:S5122")
public class AttiGeneraliController {

    /**
     * Servizio REST esposto al path "/AttiGenerali".
     */

    /**
     * Wrapper della business logic a cui viene demandata la gestione
     */
    private AttiGeneraliManager attiGeneraliManager;

    /** Logger di classe. */
    protected Logger logger = LogManager.getLogger(AttiGeneraliController.class);

    @Autowired
    private UserManager userManager;

    @Required
    @Autowired
    public void setAttiGeneraliManager(AttiGeneraliManager attiGeneraliManager ) {
        this.attiGeneraliManager = attiGeneraliManager;
    }

    protected String resolveRemoteIpAddress(final HttpServletRequest request) {
        String xRealIp = request.getHeader("X-Real-IP");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        if (StringUtils.isNotBlank(xRealIp))
            return xRealIp;

        String ip = StringUtils.firstNonBlank(xForwardedFor, remoteAddr);

        if (StringUtils.isNotBlank(ip) && ip.contains(","))
            ip = ip.substring(0, ip.indexOf(","));

        return ip;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/getListaAttiGenerali")
    @ApiOperation(nickname = "AttiGeneraliController.getListaAttiGenerali", value = "Ritorna la lista di atti generali filtrati nei campi della form", response = ResponseListaAttiGenerali.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseListaAttiGenerali> getListaAttiGenerali(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi di un atto generale per la ricerca") RicercaAttiGeneraliForm form) {

        logger.info("getListaAttiGenerali::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaAttiGenerali risultato = new ResponseListaAttiGenerali();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.searchAttiGenerali(form, authorization, xLoginMode);

        } catch (Exception e) {
            logger.error("errore nel metodo getListaAttiGenerali - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("getListaAttiGenerali: {}",risultato.getErrorData());
        }

        logger.info("getListaAttiGenerali: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/getDettaglioAttoGenerale")
    @ApiOperation(
            nickname = "AttiGeneraliController.getDettaglioAttoGenerale",
            value = "Ritorna il dettaglio di un atto generale filtrato per ID atto dalla griglia di tutti gli atti",
            response = ResponseDettaglioAttoGenerale.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseDettaglioAttoGenerale> getDettaglioAttoGeneraleMap(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi di un atto generale per la ricerca") RicercaAttiGeneraliForm form) {

        logger.info("getDettaglioAttoGeneraleMap::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseDettaglioAttoGenerale risultato = new ResponseDettaglioAttoGenerale();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.getDettaglioAttoGeneraleMap(form, authorization, xLoginMode);

        } catch (Exception e) {
            logger.error("errore nel metodo getDettaglioAttoGeneraleMap - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("getDettaglioAttoGeneraleMap: {}",risultato.getErrorData());
        }

        logger.info("getDettaglioAttoGeneraleMap: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT, value = "/updateDettaglioAttoGenerale")
    @ApiOperation(
            nickname = "AttiGeneraliController.updateDettaglioAttoGenerale",
            value = "Ritorna il dettaglio di un atto generale aggiornato in base al form passato a parametro e filtrato per ID atto.",
            response = ResponseDettaglioAttoGenerale.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseResult> updateDettaglioAttoGenerale(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi di un atto generale per la ricerca") @RequestBody AttoGeneraleUpdateForm form) {

        logger.info("updateDettaglioAttoGenerale::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.updateDettaglioAttoGenerale(form, authorization, xLoginMode);

        } catch (Exception e) {
            logger.error("errore nel metodo updateDettaglioAttoGenerale - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("updateDettaglioAttoGenerale: {}",risultato.getErrorData());
        }

        logger.info("updateDettaglioAttoGenerale::fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, value = "/creaNuovoAttoGenerale")
    @ApiOperation(
            nickname = "AttiGeneraliController.insertAttoGenerale",
            value = "Inserisce un nuovo atto generale ritornando l'atto inserito e la risposta dell'avvenuta operazione.",
            response = ResponseDettaglioAttoGenerale.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseResult> insertAttoGenerale(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @RequestBody @ApiParam(value = "Campi di inserimento per un nuovo atto generale") AttoGeneraleInsertForm form) {

        logger.info("insertAttoGenerale::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.insertAttoGenerale(form, authorization, xLoginMode);

        } catch (Exception e) {
            logger.error("errore nel metodo insertAttoGenerale - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("insertAttoGenerale: {}",risultato.getErrorData());
        }

        logger.info("insertAttoGenerale::fine metodo http status [{}]", status.value());
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = javax.ws.rs.core.MediaType.APPLICATION_JSON, path = "/downloadDocumentoAttoGenerale", method = RequestMethod.GET)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<GeneralBaseResponse<String>> downloadDocumentoAttoGenerale(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("idAtto") final String idAtto,
            @RequestParam("idAllegato") final Long idAllegato
    ) {

        logger.debug("Execution start::downloadDocumentoAttoGenerale");

        HttpStatus status = HttpStatus.OK;
        GeneralBaseResponse<String> response = new GeneralBaseResponse<String>();

        try {
            AllegatoEntry doc = this.attiGeneraliManager.downloadDocumentoAttoGenerale(idAtto, idAllegato);
            response.setEsito(true);
            response.setData(doc.getBinary());
        } catch (Exception e) {
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        logger.debug("Execution end::downloadDocumentoAttoGenerale");

        return ResponseEntity.status(status.value()).body(response);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAttoGenerale")
    @ApiOperation(nickname = "AttiGeneraliController.deleteAttoGenerale", value = "Cancella un atto generale", response = ResponseEntity.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseResult> deleteAttoGenerale(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestParam(value = "idAtto") String idAtto,
            @RequestParam(value = "codProfilo") String codProfilo
    ) {

        logger.info("AttiGeneraliController::deleteAttoGenerale inizio metodo");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (StringUtils.isEmpty(idAtto)) {
            risultato.setEsito(false);
            risultato.setErrorData("Request invalida, campi obbligatori a null");
            status = HttpStatus.BAD_REQUEST;
        }
        try {
            risultato = this.attiGeneraliManager.deleteAttoGenerale(idAtto, codProfilo, ipEvento, authorization, xLoginMode);
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("deletei: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/annullaPubblicazione")
    @ApiOperation(
            nickname = "AttiGeneraliController.annullaPubblicazione",
            value = "Cancella la programmazione della pubblicazione di un atto generale",
            response = ResponseEntity.class
    )
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
                @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
                @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
            })
    public ResponseEntity<ResponseResult> annullaPubblicazione(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestParam(value = "idAtto") String idAtto,
            @RequestParam(value = "codProfilo") String codProfilo
    ) {

        logger.info("AttiGeneraliController::annullaPubblicazione inizio metodo");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (StringUtils.isEmpty(idAtto)) {
            risultato.setEsito(false);
            risultato.setErrorData("Request invalida, campi obbligatori a null");
            status = HttpStatus.BAD_REQUEST;
        }
        try {
            risultato = this.attiGeneraliManager.annullaPubblicazione(idAtto, codProfilo, ipEvento, authorization, xLoginMode);
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("annullaPubblicazione::fine metodo http status [{}]", status.value());
        return ResponseEntity.status(status.value()).body(risultato);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/annullaPubblicazioneMotivazione")
    @ApiOperation(
            nickname = "AttiGeneraliController.annullaPubblicazioneMotivazione",
            value = "Annulla la pubblicazione di un atto generale inserendo la motivazione",
            response = ResponseEntity.class
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
                    @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
                    @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
            })
    public ResponseEntity<ResponseResult> annullaPubblicazioneMotivazione(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idAtto") String idAtto,
            @RequestParam(value = "motivoCanc") String motivoCanc,
            @RequestParam(value = "codProfilo") String codProfilo
    ) {

        logger.info("AttiGeneraliController::annullaPubblicazioneMotivazione inizio metodo");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (StringUtils.isEmpty(idAtto)) {
            risultato.setEsito(false);
            risultato.setErrorData("Request invalida, campi obbligatori a null");
            status = HttpStatus.BAD_REQUEST;
        }
        try {
            risultato = this.attiGeneraliManager.annullaPubblicazioneMotivazione(idAtto, motivoCanc, ipEvento, codProfilo, xLoginMode, authorization );
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("annullaPubblicazione::fine metodo http status [{}]", status.value());
        return ResponseEntity.status(status.value()).body(risultato);

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT, value = "/pubblicaAttoGenerale")
    @ApiOperation(
            nickname = "AttiGeneraliController.pubblicaAttoGenerale",
            value = "Pubblica un atto generale modificando la data di pubblicazione a quella odierna.",
            response = ResponseDettaglioAttoGenerale.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseResult> pubblicaAttoGenerale(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idAtto") Long idAtto) {

        logger.info("pubblicaAttoGenerale::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.pubblicaAttoGenerale(idAtto);

        } catch (Exception e) {
            logger.error("errore nel metodo pubblicaAttoGenerale - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("pubblicaAttoGenerale: {}",risultato.getErrorData());
        }

        logger.info("pubblicaAttoGenerale::fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT, value = "/pubblicaAttoGeneraleFuturo")
    @ApiOperation(
            nickname = "AttiGeneraliController.pubblicaAttoGeneraleFuturo",
            value = "Programma la pubblicazione di un atto generale impostando una data futura.",
            response = ResponseEntity.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseResult> pubblicaAttoGeneraleFuturo(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idAtto") Long idAtto,
            @RequestParam(value = "dataProgrammazione") Date dataProgrammazione
            ) {

        logger.info("pubblicaAttoGeneraleFuturo::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.pubblicaAttoGeneraleFuturo(idAtto, dataProgrammazione);

        } catch (Exception e) {
            logger.error("errore nel metodo pubblicaAttoGeneraleFuturo - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("pubblicaAttoGeneraleFuturo: {}",risultato.getErrorData());
        }

        logger.info("pubblicaAttoGeneraleFuturo::fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/generaUrlAnac")
    @ApiOperation(
            nickname = "AttiGeneraliController.generaUrlAnac",
            value = "Genera un nuovo URL per i servizi ANAC.",
            response = ResponseEntity.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
    public ResponseEntity<ResponseResult> generaUrlAnac(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization
    ) {

        logger.info("generaUrlAnac::inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            risultato = this.attiGeneraliManager.generaUrlAnac();

        } catch (Exception e) {
            logger.error("errore nel metodo generaUrlAnac - AttiGeneraliController",e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("generaUrlAnac: {}",risultato.getErrorData());
        }

        logger.info("generaUrlAnac::fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }
}
