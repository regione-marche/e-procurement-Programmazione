package it.appaltiecontratti.inbox.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.inbox.bl.InboxManager;
import it.appaltiecontratti.inbox.bl.SimogManager;
import it.appaltiecontratti.inbox.common.Constants;
import it.appaltiecontratti.inbox.entity.*;
import it.appaltiecontratti.inbox.entity.form.CancellaAnomalieForm;
import it.appaltiecontratti.inbox.entity.form.ReinviaSchedaForm;
import it.appaltiecontratti.inbox.entity.form.RisolviAnomalieForm;
import it.appaltiecontratti.inbox.entity.form.SearchCountAnomalieForm;
import it.appaltiecontratti.inbox.entity.responses.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Servizio REST esposto al path "/inbox".
 */
@SuppressWarnings("java:S5122")
@RestController
@CrossOrigin
@EnableTransactionManagement
@RequestMapping(value = {"${protected.context-path}/gestioneInbox"})
public class InboxController {
    /**
     * Logger di classe.
     */
    private final Logger logger = LogManager.getLogger(InboxController.class);

    @Autowired
    private InboxManager inboxManager;

    @Autowired
    private SimogManager simogManager;

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaFlussi")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaFlussi> getListaFlussi(@RequestHeader("Authorization") String authorization,
                                                              @ApiParam(value = "campi per la ricerca") FlussiSearchForm searchForm) {

        logger.info("getListaFlussi: inizio metodo");

        ResponseListaFlussi risultato = new ResponseListaFlussi();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getListaFlussi(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaFlussi: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioFlusso")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioFlusso> getDettaglioFlusso(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idComunicazione") Long idComunicazione) {
        logger.info("getDettaglioFlussi: inizio metodo");

        ResponseDettaglioFlusso risultato = new ResponseDettaglioFlusso();
        HttpStatus status = HttpStatus.OK;

        if (idComunicazione == null) {
            risultato.setEsito(false);
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getDettaglioFlusso(idComunicazione);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getDettaglioFlussi: fine metodo [http status " + status.value() + "]");

        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaFeedback")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaFeedback> getListaFeedback(@RequestHeader("Authorization") String authorization,
                                                                  @ApiParam(value = "campi per la ricerca") FeedbackSearchForm searchForm) {

        logger.info("getListaFeedback: inizio metodo");

        ResponseListaFeedback risultato = new ResponseListaFeedback();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getListaFeedBack(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaFeedback: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioFeedback")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),

            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioFeedback> getDettaglioFeedback(
            @RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
            @RequestParam(value = "codLott") Long codLott, @RequestParam(value = "numXml") Long numXml) {

        logger.info("getDettaglioFeedback: inizio metodo");

        ResponseDettaglioFeedback risultato = new ResponseDettaglioFeedback();
        HttpStatus status = HttpStatus.OK;

        if (codGara == null || codLott == null || numXml == null) {
            risultato.setEsito(false);
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getDettaglioFeedback(numXml, codGara, codLott);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getDettaglioFeedback: fine metodo [http status " + status.value() + "]");

        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFeedback")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> updateFeedback(@RequestHeader("Authorization") String authorization,
                                                       @ApiParam(value = "Campi necessari per l'aggiornamento di un feedback", required = true) @RequestBody @Valid FeedbackUpdateForm form) {

        logger.info("updateFeedback: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        risultato = this.inboxManager.updateFeedback(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("updateFeedback: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaComScp")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaComScp> getListaComScp(@RequestHeader("Authorization") String authorization,
                                                              @ApiParam(value = "campi per la ricerca") ComScpSearchForm searchForm) {

        logger.info("getListaComScp: inizio metodo");

        ResponseListaComScp risultato = new ResponseListaComScp();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getListaComScp(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaComScp: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioComscp")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioComScp> getDettaglioComscp(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idComunicazione") Long idComunicazione) {

        logger.info("getDettaglioComscp: inizio metodo");

        ResponseDettaglioComScp risultato = new ResponseDettaglioComScp();
        HttpStatus status = HttpStatus.OK;

        if (idComunicazione == null) {
            risultato.setEsito(false);
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getDettaglioComscp(idComunicazione);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getDettaglioComscp: fine metodo [http status " + status.value() + "]");

        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteComscp")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteComunicationScp(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idComunicazione") Long idComunicazione) {

        logger.info(
                "Execution start InboxController::deleteComunicationScp for idComun [{}]", idComunicazione);
        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        risultato = this.inboxManager.deleteComunicationScp(idComunicazione);

        if (risultato.getErrorData() != null) {
            if (risultato.getErrorData().equals(FullResponse.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            }
            else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        logger.info("Execution end InboxController::deleteComunicationScp [http status {}], for idComunicazione: [{}]", status.value(), idComunicazione);
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/reinviaComunicazioneScp")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> reinviaComunicazioneScp(@RequestHeader("Authorization") String authorization,
                                                                @RequestParam(value = "idComunicazione") Long idComunicazione) {

        logger.info("reinviaComunicazioneScp: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        if (idComunicazione == null) {
            risultato.setEsito(false);
            status = HttpStatus.BAD_REQUEST;
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.reinviaComScp(idComunicazione);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("reinviaComunicazioneScp: fine metodo [http status " + status.value() + "]");

        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaRichiesteCancellazione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaRichiesteCancellazione> getListaRichiesteCancellazione(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "campi per la ricerca") RichiestaCancellazioneForm searchForm) {

        if (logger.isDebugEnabled()) {
            logger.debug("getListaRichiesteCancellazione: inizio metodo");
        }

        ResponseListaRichiesteCancellazione risultato = new ResponseListaRichiesteCancellazione();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.inboxManager.getListaRichiesteCancellazione(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.debug("getListaRichiesteCancellazione: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/rifiutaRichiestaCancellazione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> rifiutaRichiestaCancellazione(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi necessari per il rifiuto di una richiesta di cancellazione", required = true) @RequestBody @Valid RichiestaCancellazioneActionForm form) {

        logger.info("rifiutaRichiestaCancellazione: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        risultato = this.inboxManager.rifiutaRichiestaCancellazione(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("rifiutaRichiestaCancellazione: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/accettaRichiestaCancellazione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> accettaRichiestaCancellazione(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi necessari per il rifiuto di una richiesta di cancellazione", required = true) @RequestBody @Valid RichiestaCancellazioneActionForm form) {

        logger.info("accettaRichiestaCancellazione: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        try {
            risultato = this.inboxManager.accettaRichiestaCancellazione(form);
            if (risultato.getErrorData() != null && !risultato.getErrorData().equals(Constants.STATO_IMPORTATA_ERRORE_CANCELLAZIONE_DATI_COMUNI)) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (Exception e) {
            risultato.setErrorData(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("accettaRichiestaCancellazione: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getNumeroErroriComunicazioni")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseNumeroErroriComunicazioni> getNumeroErroriComunicazioni(
            @RequestHeader("Authorization") String authorization) {

        logger.info("getNumeroErroriComunicazioni: inizio metodo");

        ResponseNumeroErroriComunicazioni risultato = new ResponseNumeroErroriComunicazioni();
        HttpStatus status = HttpStatus.OK;

        risultato = this.inboxManager.getNumeroErroriComunicazioni();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getNumeroErroriComunicazioni: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaCodiciAnomaliaFeedback")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<FullResponse<List<String>>> getListaCodiciAnomaliaFeedback(
            @RequestHeader("Authorization") String authorization) {

        logger.info("getListaCodiciAnomaliaFeedback: inizio metodo");

        FullResponse<List<String>> risultato = new FullResponse<List<String>>();
        HttpStatus status = HttpStatus.OK;

        risultato = this.inboxManager.getListaCodiciAnomaliaFeedback();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaCodiciAnomaliaFeedback: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/reinviaScheda")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<FullResponse<Boolean>> reinviaScheda(@RequestHeader("Authorization") String authorization,
                                                               @RequestBody @Valid ReinviaSchedaForm form) {

        logger.info("reinviaScheda: inizio metodo");

        FullResponse<Boolean> risultato = new FullResponse<Boolean>();
        HttpStatus status = HttpStatus.OK;

        try {
            risultato = this.inboxManager.reinviaScheda(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (IllegalArgumentException e) {
            risultato.setErrorData(BaseResponse.BAD_REQUEST);
            status = HttpStatus.BAD_REQUEST;
        }

        logger.info("reinviaScheda: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/countAnomalie")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<FullResponse<Long>> countAnomalie(@RequestHeader("Authorization") String authorization,
                                                            @ApiParam(value = "form di ricerca") SearchCountAnomalieForm form) {

        logger.info("countAnomalie: inizio metodo");

        FullResponse<Long> risultato = new FullResponse<Long>();
        HttpStatus status = HttpStatus.OK;
        risultato = this.inboxManager.countAnomalie(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("countAnomalie: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/reinviaAnomalie")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> reinviaAnomalie(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody SearchCountAnomalieForm form) {

        logger.info("reinviaAnomalie: inizio metodo");
        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        risultato = this.inboxManager.reinviaAnomalie(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("reinviaAnomalie: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/risolviAnomalie")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> risolviAnomalie(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody RisolviAnomalieForm form) {

        logger.info("risolviAnomalie: inizio metodo");
        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        risultato = inboxManager.risolviAnomalie(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("risolviAnomalie: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/cancellaAnomalie")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> cancellaAnomalie(@RequestHeader("Authorization") String authorization,
                                                         @RequestBody CancellaAnomalieForm form) {

        logger.info("cancellaAnomalie: inizio metodo");
        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        risultato = inboxManager.cancellaAnomalie(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("cancellaAnomalie: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/riallineaIndiciSIMOG")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> riallineaIndiciSIMOG(@RequestHeader("Authorization") String authorization,
                                                             @RequestBody String cig) {

        logger.info("riallineaIndiciSIMOG: inizio metodo");
        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        risultato = simogManager.riallineaIndiciSimog(cig);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("riallineaIndiciSIMOG: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFeedback")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<FullResponse<Boolean>> deleteFeedback(@RequestHeader("Authorization") String authorization,
                                                                @RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
                                                                @RequestParam(value = "numXml") Long numXml) {

        logger.info(
                "Execution start InboxController::deleteFeedback for codGara [ {} ], codLotto [ {} ], numXml [ {} ]",
                codGara, codLotto, numXml);
        FullResponse<Boolean> risultato = new FullResponse<Boolean>();
        HttpStatus status = HttpStatus.OK;
        risultato = this.inboxManager.deleteFeedback(codGara, codLotto, numXml);
        if (risultato.getErrorData() != null) {
            if (risultato.getErrorData().equals(FullResponse.ERROR_NOT_FOUND))
                status = HttpStatus.NOT_FOUND;
            else
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("deleteFeedback: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }
    
    
    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/cancellaCascata")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> cancellaCascata(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi necessari per il rifiuto di una richiesta di cancellazione", required = true) @RequestBody @Valid RichiestaCancellazioneActionForm form) {

        logger.info("accettaRichiestaCancellazione: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        try {
            risultato = this.inboxManager.cancellaCascata(form);
            if (risultato.getErrorData() != null && !risultato.getErrorData().equals(Constants.STATO_IMPORTATA_ERRORE_CANCELLAZIONE_DATI_COMUNI)) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (Exception e) {
            risultato.setErrorData(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("accettaRichiestaCancellazione: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }
}
