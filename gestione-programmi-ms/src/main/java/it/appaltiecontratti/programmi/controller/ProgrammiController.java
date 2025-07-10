package it.appaltiecontratti.programmi.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.programmi.bl.*;
import it.appaltiecontratti.programmi.rl.beans.ConfigServiceRL;
import it.appaltiecontratti.programmi.entity.*;
import it.appaltiecontratti.programmi.entity.pubblicazioni.*;
import it.appaltiecontratti.programmi.entity.validazione.ValidateEntry;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@Validated
@EnableTransactionManagement
@SuppressWarnings("java:S5122")
@RequestMapping(value = "${protected.context-path}/gestioneProgrammi")
public class ProgrammiController {

    /**
     * Servizio REST esposto al path "/gestioneProgrammi".
     */

    /**
     * Logger di classe.
     */
    protected Logger logger = LogManager.getLogger(ProgrammiController.class);

    /**
     * Wrapper della business logic a cui viene demandata la gestione
     */
    @Autowired
    private ProgrammiManager programmiManager;

    @Autowired
    private ExportInterventiManager exportInterventiManager;

    @Autowired
    private ExportSoggettiAttuatoriManager exportSoggettiAttuatiriManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ValidateManager validateManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private WConfigManager wConfigManager;

    @Autowired
    ServletContext context;

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaProgrammi")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaProgrammi> getProgrammi(@RequestHeader("Authorization") String authorization,
                                                               @RequestHeader("X-loginMode") String xLoginMode,
                                                               @ApiParam(value = "string per la ricerca") ProgrammiSearchForm searchForm) {

        logger.info("getProgrammi: inizio metodo");

        ResponseListaProgrammi risultato = new ResponseListaProgrammi();
        HttpStatus status = HttpStatus.OK;

        if (searchForm.getStazioneAppaltante() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }

        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);

        risultato = this.programmiManager.getListaProgrammi(syscon, searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getProgrammi: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/copiaProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInsert> copiaProgramma(@RequestHeader("X-loginMode") String xLoginMode,
                                                         @RequestHeader("Authorization") String authorization,
                                                         @RequestParam(value = "id") Long id) {

        logger.info("copiaProgramma: inizio metodo");

        ResponseInsert risultato = new ResponseInsert();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(id, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.duplicaProgramma(id);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getProgrammi: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioProgrammi> getProgramma(@RequestHeader("X-loginMode") String xLoginMode,
                                                                   @RequestHeader("Authorization") String authorization, @RequestParam(value = "id") Long id) {

        logger.info("getProgramma: inizio metodo");

        ResponseDettaglioProgrammi risultato = new ResponseDettaglioProgrammi();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(id, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getProgramma(id);
            if (risultato.getErrorData() != null) {
                if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                    status = HttpStatus.NOT_FOUND;
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getProgramma: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInsert> insertProgramma(@RequestHeader("Authorization") String authorization,
                                                          @RequestHeader("X-loginMode") String xLoginMode,
                                                          @ApiParam(value = "Campi di un programma da inserire", required = true) @RequestBody() @Valid ProgrammaInsertForm form) {

        logger.info("insertProgramma: inizio metodo");

        ResponseInsert risultato = new ResponseInsert();
        HttpStatus status = HttpStatus.OK;

        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);

        risultato = this.programmiManager.insertProgramma(syscon, form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("insertProgramma: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/updateProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> updateProgramma(@RequestHeader("X-loginMode") String xLoginMode,
                                                        @RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "Campi di un programma da modificare", required = true) @RequestBody() @Valid ProgrammaUpdateForm form) {

        logger.info("updateProgramma: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getId(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.updateProgramma(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("updateProgramma: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getIntervento")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioIntervento> getIntervento(@RequestHeader("X-loginMode") String xLoginMode,
                                                                     @RequestHeader("Authorization") String authorization, @RequestParam(value = "idProgramma") Long idProgramma,
                                                                     @RequestParam(value = "idIntervento") Long idIntervento) {

        logger.info("getIntervento: inizio metodo");

        ResponseDettaglioIntervento risultato = new ResponseDettaglioIntervento();
        HttpStatus status = HttpStatus.OK;

        if (idProgramma == null || idIntervento == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }

        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getIntervento(idProgramma, idIntervento);
            if (risultato.getErrorData() != null) {
                if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                    status = HttpStatus.NOT_FOUND;
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getIntervento: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaInterventi")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaInterventi> getListaInterventi(@RequestHeader("X-loginMode") String xLoginMode,
                                                                      @RequestHeader("Authorization") String authorization,
                                                                      @ApiParam(value = "campi per la ricerca") InterventiSearchForm searchForm) {

        logger.info("getListaInterventi: inizio metodo");

        ResponseListaInterventi risultato = new ResponseListaInterventi();
        HttpStatus status = HttpStatus.OK;
        risultato.setEsito(true);

        if (searchForm.getIdProgramma() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }

        boolean permission = hasPermission(searchForm.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getInterventi(searchForm);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getListaInterventi: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaInterventiNonRiproposti")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaInterventiNR> getListaInterventiNonRiproposti(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "campi per la ricerca") ModulesSearchForm searchForm) {

        logger.info("getListaInterventiNonRiproposti: inizio metodo");

        ResponseListaInterventiNR risultato = new ResponseListaInterventiNR();
        HttpStatus status = HttpStatus.OK;
        risultato.setEsito(true);
        if (searchForm.getIdProgramma() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(searchForm.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getInterventiNonRiproposti(searchForm);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getListaInterventiNonRiproposti: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaOpereIncompiute")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaOpereIncompiute> getListaOpereIncompiute(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "campi per la ricerca") ModulesSearchForm searchForm) {

        logger.info("getListaOpereIncompiute: inizio metodo");

        ResponseListaOpereIncompiute risultato = new ResponseListaOpereIncompiute();
        HttpStatus status = HttpStatus.OK;
        risultato.setEsito(true);
        if (searchForm.getIdProgramma() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(searchForm.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getOpereIncompiute(searchForm);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getListaOpereIncompiute: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteProgramma(@RequestHeader("X-loginMode") String xLoginMode,
                                                        @RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "id") Long id) {

        logger.info("deleteProgramma: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(id, authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.deleteProgramma(id);
            } catch (Exception e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }

            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("deleteProgramma: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertIntervento")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInsert> insertIntervento(@RequestHeader("X-loginMode") String xLoginMode,
                                                           @RequestHeader("Authorization") String authorization,
                                                           @ApiParam(value = "Campi di un intervento da inserire", required = true) @RequestBody() @Valid InterventoInsertForm form) {

        logger.info("insertIntervento: inizio metodo");

        ResponseInsert risultato = new ResponseInsert();
        HttpStatus status = HttpStatus.OK;
        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.insertIntervento(form);
            } catch (Throwable e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.isEsito() == true) {
                try {
                    this.programmiManager.aggiornaCostiPiatri(form.getIdProgramma());
                } catch (Throwable e) {
                    logger.error("Errore in aggiornamento costi piatri: contri = " + form.getIdProgramma(), e);
                    risultato.setEsito(false);
                    risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
                }
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("insertIntervento: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/updateIntervento")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> updateIntervento(@RequestHeader("X-loginMode") String xLoginMode,
                                                         @RequestHeader("Authorization") String authorization,
                                                         @ApiParam(value = "Campi di un intervento da modificare", required = true) @RequestBody() @Valid InterventoInsertForm form) {

        logger.info("updateIntervento: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.updateIntervento(form);
            } catch (Exception e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            try {
                this.programmiManager.aggiornaCostiPiatri(form.getIdProgramma());
            } catch (Throwable e) {
                logger.error("Errore in aggiornamento costi piatri: contri = " + form.getIdProgramma()
                        + ", idIntervento = " + form.getId(), e);
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("updateIntervento: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertOperaIncompiuta")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInsert> insertOperaIncompiuta(@RequestHeader("X-loginMode") String xLoginMode,
                                                                @RequestHeader("Authorization") String authorization,
                                                                @ApiParam(value = "Campi di un opera incompiuta da inserire", required = true) @RequestBody() @Valid OperaIncompiutaInsertForm form) {

        logger.info("insertOperaIncompiuta: inizio metodo");

        ResponseInsert risultato = new ResponseInsert();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.insertOperaIncompiuta(form);
            } catch (Exception e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }

            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("insertOperaIncompiuta: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/updateOperaIncompiuta")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> updateOperaIncompiuta(@RequestHeader("X-loginMode") String xLoginMode,
                                                              @RequestHeader("Authorization") String authorization,
                                                              @ApiParam(value = "Campi di un opera da modificare", required = true) @RequestBody() @Valid OperaIncompiutaInsertForm form) {

        logger.info("updateOperaIncompiuta: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.updateOperaIncompiuta(form);
            } catch (Exception e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("updateOperaIncompiuta: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertInterventoNonRiproposto")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInsert> insertInterventoNonRiproposto(@RequestHeader("X-loginMode") String xLoginMode,
                                                                        @RequestHeader("Authorization") String authorization,
                                                                        @ApiParam(value = "Campi di un intervento non riproposto da inserire", required = true) @RequestBody() @Valid InterventoNonRipropostoInsertForm form) {

        logger.info("insertInterventoNonRiproposto: inizio metodo");

        ResponseInsert risultato = new ResponseInsert();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.insertInterventoNonRiproposto(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("insertInterventoNonRiproposto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/updateInterventoNonRiproposto")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> updateInterventoNonRiproposto(@RequestHeader("X-loginMode") String xLoginMode,
                                                                      @RequestHeader("Authorization") String authorization,
                                                                      @ApiParam(value = "Campi di un intervento non riproposto da modificare", required = true) @RequestBody() @Valid InterventoNonRipropostoUpdateForm form) {

        logger.info("updateInterventoNonRiproposto: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.updateInterventoNonRiproposto(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("updateInterventoNonRiproposto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteIntervento")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteIntervento(@RequestHeader("X-loginMode") String xLoginMode,
                                                         @RequestHeader("Authorization") String authorization,
                                                         @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma,
                                                         @ApiParam(value = "Id dell'intervento", required = true) @RequestParam(value = "idIntervento") Long idIntervento) {

        logger.info("deleteIntervento: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || idIntervento == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {

            try {
                risultato = this.programmiManager.deleteIntervento(idProgramma, idIntervento);
            } catch (Exception e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            try {
                this.programmiManager.aggiornaCostiPiatri(idProgramma);
            } catch (Throwable e) {
                logger.error("Errore in aggiornamento costi piatri: contri = " + idProgramma + ", idIntervento = "
                        + idIntervento, e);
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("deleteIntervento: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteInterventoNonRiproposto")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteInterventoNonRiproposto(@RequestHeader("X-loginMode") String xLoginMode,
                                                                      @RequestHeader("Authorization") String authorization,
                                                                      @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma,
                                                                      @ApiParam(value = "Id dell'intervento", required = true) @RequestParam(value = "idIntervento") Long idIntervento) {

        logger.info("deleteInterventoNonRiproposto: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || idIntervento == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.deleteInterventoNonRiproposto(idProgramma, idIntervento);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("deleteInterventoNonRiproposto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteOperaIncompiuta")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteOperaIncompiuta(@RequestHeader("X-loginMode") String xLoginMode,
                                                              @RequestHeader("Authorization") String authorization,
                                                              @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma,
                                                              @ApiParam(value = "Id dell'opera", required = true) @RequestParam(value = "idOpera") Long idOpera) {

        logger.info("deleteOperaIncompiuta: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || idOpera == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {

            try {
                risultato = this.programmiManager.deleteOperaIncompiuta(idProgramma, idOpera);
            } catch (Exception e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("deleteOperaIncompiuta: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInterventoNonRiproposto")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioInterventoNR> getInterventoNonRiproposto(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma,
            @RequestParam(value = "id") Long id) {

        logger.info("getInterventoNonRiproposto: inizio metodo");

        ResponseDettaglioInterventoNR risultato = new ResponseDettaglioInterventoNR();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || id == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getInterventoNonRiproposto(idProgramma, id);
            if (risultato.getErrorData() != null) {
                if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                    status = HttpStatus.NOT_FOUND;
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getInterventoNonRiproposto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getOperaIncompiuta")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioOperaIncompiuta> getOperaIncompiuta(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma,
            @ApiParam(value = "Id dell'opera", required = true) @RequestParam(value = "id") Long id) {

        logger.info("getOperaIncompiuta: inizio metodo");

        ResponseDettaglioOperaIncompiuta risultato = new ResponseDettaglioOperaIncompiuta();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || id == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getOperaIncompiuta(idProgramma, id);
            if (risultato.getErrorData() != null) {
                if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                    status = HttpStatus.NOT_FOUND;
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getOperaIncompiuta: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRisorsaDiCapitolo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioRisorsaCapitolo> getRisorsaDiCapitolo(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma,
            @ApiParam(value = "Id dell'intervento", required = true) @RequestParam(value = "idIntervento") Long idIntervento,
            @ApiParam(value = "Id dell'opera", required = true) @RequestParam(value = "id") Long id) {

        logger.info("getRisorsaDiCapitolo: inizio metodo");

        ResponseDettaglioRisorsaCapitolo risultato = new ResponseDettaglioRisorsaCapitolo();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || idIntervento == null || id == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getRisorsaDiCapitolo(idProgramma, idIntervento, id);
            if (risultato.getErrorData() != null) {
                if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                    status = HttpStatus.NOT_FOUND;
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getRisorsaDiCapitolo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRisorseDiCapitolo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaRisorsaCapitolo> getRisorseDiCapitolo(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "campi per la ricerca") RisorseCapitoloSearchForm searchForm) {

        logger.info("getRisorseDiCapitolo: inizio metodo");

        ResponseListaRisorsaCapitolo risultato = new ResponseListaRisorsaCapitolo();
        HttpStatus status = HttpStatus.OK;
        if (searchForm.getIdProgramma() == null || searchForm.getIdIntervento() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(searchForm.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getRisorseDiCapitolo(searchForm);
            if (risultato.getErrorData() != null) {
                if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                    status = HttpStatus.NOT_FOUND;
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getRisorseDiCapitolo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertRisorsaDiCapitolo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInsert> insertRisorsaDiCapitolo(@RequestHeader("X-loginMode") String xLoginMode,
                                                                  @RequestHeader("Authorization") String authorization,
                                                                  @ApiParam(value = "Campi di una risorsa di capitolo da inserire", required = true) @RequestBody() @Valid RisorsaDiCapitoloInsertForm form) {

        logger.info("insertRisorsaDiCapitolo: inizio metodo");

        ResponseInsert risultato = new ResponseInsert();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.insertRisorsaDiCapitolo(form);
            try {
                this.programmiManager.aggiornaCostiInttri(form.getIdProgramma(), form.getIdIntervento());
            } catch (Throwable e) {
                logger.error("Errore in aggiornamento costi inttri", e);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("insertRisorsaDiCapitolo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/updateRisorsaDiCapitolo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> updateRisorsaDiCapitolo(@RequestHeader("X-loginMode") String xLoginMode,
                                                                @RequestHeader("Authorization") String authorization,
                                                                @ApiParam(value = "Campi di una risorsa di capitolo da modificare", required = true) @RequestBody() @Valid RisorsaDiCapitoloInsertForm form) {

        logger.info("updateRisorsaDiCapitolo: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.updateRisorsaDiCapitolo(form);
            try {
                this.programmiManager.aggiornaCostiInttri(form.getIdProgramma(), form.getIdIntervento());
            } catch (Throwable e) {
                logger.error("Errore in aggiornamento costi inttri", e);
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("insertRisorsaDiCapitolo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteRisorsaDiCapitolo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteRisorsaDiCapitolo(@RequestHeader("X-loginMode") String xLoginMode,
                                                                @RequestHeader("Authorization") String authorization, @RequestParam(value = "idProgramma") Long idProgramma,
                                                                @RequestParam(value = "idIntervento") Long idIntervento,
                                                                @RequestParam(value = "idRisorsa") Long idRisorsa) {

        logger.info("deleteRisorsaDiCapitolo: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || idIntervento == null || idRisorsa == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.deleteRisorsaDiCapitolo(idProgramma, idIntervento, idRisorsa);
            try {
                this.programmiManager.aggiornaCostiInttri(idProgramma, idIntervento);
            } catch (Throwable e) {
                logger.error("Errore in aggiornamento costi inttri", e);
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("deleteRisorsaDiCapitolo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInterventiNRDaImportare")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInterventiDaRiproporre> getInterventiNRDaImportare(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") Long idProgramma) {

        logger.info("getInterventiNRDaImportare: inizio metodo");

        ResponseInterventiDaRiproporre risultato = new ResponseInterventiDaRiproporre();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getListaInterventiNRToImport(idProgramma);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getInterventiNRDaImportare: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInterventiDaImportare")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInterventiDaRiproporre> getInterventiDaImportare(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") Long idProgramma) {

        logger.info("getInterventiNRDaImportare: inizio metodo");

        ResponseInterventiDaRiproporre risultato = new ResponseInterventiDaRiproporre();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getListaInterventiToImport(idProgramma);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getInterventiNRDaImportare: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/importaInterventiNonRip")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> importaInterventiNonRip(@RequestHeader("X-loginMode") String xLoginMode,
                                                                @RequestHeader("Authorization") String authorization, @RequestBody() @Valid ImportInterventiForm form) {

        logger.info("importaInterventiNonRip: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.importaInterventiNonRip(form);
            } catch (Throwable e) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("importaInterventiNonRip: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/importaInterventi")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> importaInterventi(@RequestHeader("X-loginMode") String xLoginMode,
                                                          @RequestHeader("Authorization") String authorization, @RequestBody() @Valid ImportInterventiForm form) {

        logger.info("importaInterventi: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;
        boolean permission = hasPermission(form.getIdProgramma(), authorization, xLoginMode);
        if (permission) {
            try {
                risultato = this.programmiManager.importaInterventi(form);
            } catch (Throwable t) {
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
            if (risultato.isEsito() == true) {
                try {
                    this.programmiManager.aggiornaCostiPiatri(form.getIdProgramma());
                } catch (Throwable e) {
                    logger.error("Errore in aggiornamento costi piatri: contri = " + form.getIdProgramma(), e);
                    risultato.setEsito(false);
                    risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
                }
            }

            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("importaInterventi: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getPubblicazioni")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponsePubblicazioni> getPubblicazioni(@RequestHeader("X-loginMode") String xLoginMode,
                                                                  @RequestHeader("Authorization") String authorization,
                                                                  @RequestParam(value = "idProgramma") String idProgramma,
                                                                  @RequestParam(value = "stazioneAppaltante") String stazioneAppaltante) {

        logger.info("getPubblicazioni: inizio metodo");

        ResponsePubblicazioni risultato = new ResponsePubblicazioni();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || stazioneAppaltante == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(Long.valueOf(idProgramma), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getPubblicazioni(stazioneAppaltante, idProgramma);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getPubblicazioni: fine metodo[http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRequestPubblicazioneLavori")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponsePubblicaLavori> getRequestPubblicazioneLavori(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") Long idProgramma) {

        logger.info("getRequestPubblicazioneLavori: inizio metodo");

        ResponsePubblicaLavori risultato = new ResponsePubblicaLavori();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);


        UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
        Long syscon = userAuthClaimsDTO.getSyscon();

        if (permission) {
            risultato = this.programmiManager.getProgrammaLavoriForPublish(idProgramma, syscon);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getRequestPubblicazioneLavori: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRequestPubblicazioneForniture")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponsePubblicaServizi> getRequestPubblicazioneForniture(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") Long idProgramma) {

        logger.info("getRequestPubblicazioneForniture: inizio metodo");

        ResponsePubblicaServizi risultato = new ResponsePubblicaServizi();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasPermission(idProgramma, authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getProgrammaServiziForPublish(idProgramma);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("getRequestPubblicazioneForniture: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/allineaPubblicazione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> allineaPubblicazione(@RequestHeader("Authorization") String authorization,
                                                             @RequestHeader("X-loginMode") String xLoginMode,
                                                             @RequestBody() @Valid ProgrammaPubblicatoForm form) {

        logger.info("allineaPubblicazione: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);

        Long syscon = userAuthClaimsDTO.getSyscon();

        try {
            risultato = this.programmiManager.updateIdRicevuto(form, syscon);
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getRequestPubblicazioneForniture: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getPdf")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseFile> getPdf(@RequestHeader("Authorization") String authorization,
                                               @RequestParam(value = "idProgramma") String idProgramma,
                                               @RequestParam(value = "tipoProg") String tipoProg) {

        logger.info("getPdf: inizio metodo");

        ResponseFile risultato = new ResponseFile();
        HttpStatus status = HttpStatus.OK;
        if (idProgramma == null || tipoProg == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            risultato = this.programmiManager.creaPdfNuovaNormativa(idProgramma, tipoProg);
            return ResponseEntity.status(status.value()).body(risultato);
        } catch (Exception e) {
            logger.error("Errorer in fase di generazione del pdf per idProgramma" + idProgramma);
        }
        logger.info("getPdf: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/cambiaSyscon")
    @ApiOperation(nickname = "ProgrammiController.cambiaSysconProgramma", value = "Cambia il syscon associato al programma", notes = "Ritorna l'esito del cambio di syscon", response = BaseResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> cambiaSysconProgramma(@RequestHeader("Authorization") String authorization,
                                                              @ApiParam(value = "Campi per cambiare syscon", required = true) @RequestBody @Valid CambioSysconForm form)

            throws ParseException {

        logger.info("cambiaSysconProgramma: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        risultato = programmiManager.cambiaSyscon(form);

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("cambiaSysconProgrammaa: fine metodo [http status " + status.value() + "]");
        }
        return ResponseEntity.status(status.value()).body(risultato);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCuiOptions")
    @ApiOperation(nickname = "ProgrammiController.getCuiOptions", value = "Ritorna la lista dei cui degli interventi contenenti la string di ricerca nel codice")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaCuiOptions> getCuiOptions(@RequestHeader("Authorization") String authorization,
                                                                 @ApiParam(value = "Campi necessari alla ricerca di un cui", required = true) CuiSearchForm form) {

        logger.info("getCuiOptions: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaCuiOptions risultato = new ResponseListaCuiOptions();
        if (form == null || StringUtils.isBlank(form.getStazioneAppaltante()) || form.getDesc() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta non valida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = programmiManager.getCuiOptions(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getCuiOptions: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    private boolean hasPermission(Long contri, String authorization, String loginMode) {

        try {
            Long syscon = getSysconFromJwtToken(authorization, loginMode);

            ResponseDettaglioProgrammi response = this.programmiManager.getProgramma(contri);
            ProgrammaEntry programma = response.getData();
            if (programma == null) {
                return false;
            }
            boolean isUtenteAbilitatoTutteSA = programmiManager.isUtenteAbilitatoTutteSA(syscon);
            boolean hasUserSaPermission = programmiManager.hasUserSaPermission(syscon, programma.getStazioneappaltante());
            if (!isUtenteAbilitatoTutteSA && !hasUserSaPermission) {
                return false;
            }

            // filtro per utente
            boolean filterBySyscon = programmiManager.filterProgrammaBySyscon(syscon);
            if (filterBySyscon && !programma.getSyscon().equals(syscon)) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaProgrammiNonPaginata")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaProgrammiNonPaginata> getListaProgrammiNonPaginata(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "form di ricerca") ProgrammiNonPaginatiSearchForm form) {

        logger.info("getListaProgrammiNonPaginata: inizio metodo");

        ResponseListaProgrammiNonPaginata risultato = new ResponseListaProgrammiNonPaginata();
        HttpStatus status = HttpStatus.OK;
        if (form.getStazioneAppaltante() == null || form.getSyscon() == null) {
            risultato.setEsito(false);
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.programmiManager.getListaProgrammiNonPaginata(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaProgrammiNonPaginata: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizNuovoProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInizNuovoProgramma> getInizNuovoProgramma(
            @RequestHeader("Authorization") String authorization, @RequestParam(value = "syscon") String syscon,
            @RequestParam(value = "stazioneAppaltante") String stazioneAppaltante) {

        logger.info("getInizNuovoProgramma: inizio metodo");

        ResponseInizNuovoProgramma risultato = new ResponseInizNuovoProgramma();
        HttpStatus status = HttpStatus.OK;
        if (stazioneAppaltante == null || syscon == null) {
            risultato.setEsito(false);
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.programmiManager.getInizNuovoProgramma(stazioneAppaltante, syscon);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getInizNuovoProgramma: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/reportSoggAggregatori")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> reportSoggAggregatori(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") String idProgramma) {

        logger.info("reportSoggAggregatori: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        risultato.setEsito(true);

        if (idProgramma == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }


        risultato = this.exportSoggettiAttuatiriManager.reportSoggAggregatori(idProgramma);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        logger.info("reportSoggAggregatori: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getShowSoggReport")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> getShowSoggReport(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") String idProgramma) {

        logger.info("getShowSoggReport: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        risultato.setEsito(true);

        if (idProgramma == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }


        risultato = this.programmiManager.showSoggReport(idProgramma);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        logger.info("getShowSoggReport: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFlussoProgramma")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<BaseResponse> deleteFlussoProgramma(@RequestHeader("X-loginMode") String xLoginMode,
                                                              @RequestHeader("Authorization") String authorization,
                                                              @ApiParam(value = "id del programma", required = true) @RequestParam(value = "idProgramma") Long idProgramma) {

        logger.info("deleteFlussoProgramma: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        try {
            risultato = this.programmiManager.deleteFlussoProgramma(idProgramma);
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("deleteFlussoProgramma: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/getDettaglioCup")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDettaglioCup> getDettaglioCup(@RequestHeader("X-loginMode") String xLoginMode,
                                                                @RequestHeader("Authorization") String authorization,
                                                                @RequestBody @Valid DettaglioCupForm form) {

        logger.info("getDettaglioCup: inizio metodo");

        ResponseDettaglioCup risultato = new ResponseDettaglioCup();
        HttpStatus status = HttpStatus.OK;
        risultato = this.programmiManager.getDettaglioCup(form);
        if (risultato.getErrorData() != null) {
            if (risultato.getErrorData().equals(BaseResponse.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getDettaglioCup: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizDettaglioCup")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseInizDettaglioCUP> getInizImportSmartcig(
            @RequestHeader("Authorization") String authorization, @RequestParam(value = "syscon") Long syscon) {

        logger.info("getInizDettaglioCup: inizio metodo");
        ResponseInizDettaglioCUP risultato = new ResponseInizDettaglioCUP();
        HttpStatus status = HttpStatus.OK;
        if (syscon == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.programmiManager.getInizDettaglioCup(syscon);
        if (!risultato.isEsito() && risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getInizDettaglioCup: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @PostMapping(value = "exportInterventiAcquisti", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public @ResponseBody ResponseEntity<ResponseFile> exportInterventiAcquisti(@RequestBody final ExportInterventiAquistiForm form) {

        logger.info("exportInterventiAcquisti: inizio metodo");

        ResponseFile risultato = new ResponseFile();
        HttpStatus status = HttpStatus.OK;
        if (form == null || form.getContri() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            risultato = this.exportInterventiManager.exportInterventiAquisti(form);
            if (!risultato.isEsito() && risultato.getErrorData().equals(ResponseFile.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status.value()).body(risultato);
        } catch (Exception e) {
            logger.error("Errore in fase di generazione del csv per contri" + form.getContri());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("exportInterventiAcquisti: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @PostMapping(value = "exportInterventiAcquistiNonRiproposti", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public @ResponseBody ResponseEntity<ResponseFile> exportInterventiAcquistiNonRiproposti(@RequestBody final ExportInterventiAquistiNonRipropostiForm form) {

        logger.info("exportInterventiAcquistiNonRiproposti: inizio metodo");

        ResponseFile risultato = new ResponseFile();
        HttpStatus status = HttpStatus.OK;
        if (form == null || form.getContri() == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            risultato = this.exportInterventiManager.exportInterventiAcquistiNonRiproposti(form);
            if (!risultato.isEsito() && risultato.getErrorData().equals(ResponseFile.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status.value()).body(risultato);
        } catch (Exception e) {
            logger.error("Errore in fase di generazione del csv per contri [ {} ]", form.getContri());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("exportInterventiAcquistiNonRiproposti: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @GetMapping(value = "listaProgrammiDaConfronto", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public @ResponseBody ResponseEntity<GeneralBaseResponse<List<ProgrammaBaseEntry>>> listaProgrammiDaConfronto(@RequestParam("contri") final Long contri) {

        logger.info("listaProgrammiDaConfronto: inizio metodo");

        GeneralBaseResponse<List<ProgrammaBaseEntry>> risultato = new GeneralBaseResponse<>();
        HttpStatus status = HttpStatus.OK;
        if (contri == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            risultato = this.programmiManager.listaProgrammiDaConfronto(contri);
            if (!risultato.isEsito() && risultato.getErrorData().equals(ResponseFile.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status.value()).body(risultato);
        } catch (Exception e) {
            logger.error("Errore in fase recupero programmi da confronto per contri [ {} ]", contri);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("listaProgrammiDaConfronto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @GetMapping(value = "listaInterventiDaConfronto", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public @ResponseBody ResponseEntity<GeneralBaseResponse<List<InterventiDaConfrontoDTO>>> listaInterventiDaConfronto(@RequestParam("contri") final Long contri, @RequestParam("contriDaConfrontare") final Long contriDaConfrontare) {

        logger.info("listaInterventiDaConfronto: inizio metodo");

        GeneralBaseResponse<List<InterventiDaConfrontoDTO>> risultato = new GeneralBaseResponse<>();
        HttpStatus status = HttpStatus.OK;
        if (contri == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            risultato = this.programmiManager.listaInterventiDaConfronto(contri, contriDaConfrontare);
            if (!risultato.isEsito() && risultato.getErrorData().equals(ResponseFile.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status.value()).body(risultato);
        } catch (Exception e) {
            logger.error("Errore in fase recupero interventi da confronto per contri [ {} ] e contri da confrontare [ {} ]", contri, contriDaConfrontare);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("listaInterventiDaConfronto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @PostMapping(value = "downloadListaInterventiDaConfronto", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public @ResponseBody ResponseEntity<ResponseFile> downloadListaInterventiDaConfronto(@RequestParam("contri") final Long contri, @RequestParam("contriDaConfrontare") final Long contriDaConfrontare) {

        logger.info("downloadListaInterventiDaConfronto: inizio metodo");

        ResponseFile risultato = new ResponseFile();
        HttpStatus status = HttpStatus.OK;
        if (contri == null) {
            risultato.setEsito(false);
            risultato.setErrorData("Errore validazione request");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            risultato = this.programmiManager.downloadListaInterventiDaConfronto(contri, contriDaConfrontare);
            if (!risultato.isEsito() && risultato.getErrorData().equals(ResponseFile.ERROR_NOT_FOUND)) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status.value()).body(risultato);
        } catch (Exception e) {
            logger.error("Errore in fase download csv interventi da confronto per contri [ {} ] e contri da confrontare [ {} ]", contri, contriDaConfrontare);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("downloadListaInterventiDaConfronto: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getTipoInstallazione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> getTipoInstallazione() {

        logger.info("getTipoInstallazione: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;

        risultato = this.programmiManager.getTipoInstallazione();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        logger.info("getTipoInstallazione: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    public TokenValidationResult validateToken(String token) {
        TokenValidationResult result = new TokenValidationResult();
        try {
            byte[] jwtKey = this.accountManager.getJWTKey();
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);
            Jws<Claims> claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            String clientId = claims.getPayload().get("clientId").toString();
            Long syscon = Long.valueOf(claims.getPayload().get("syscon").toString());
            result.setClientId(clientId);
            result.setSyscon(syscon);
            result.setValidated(true);
            return result;
        } catch (Exception e) {
            result.setValidated(false);
            result.setError("Il token non è valido.");
            return result;
        }
    }

    private boolean isValid(List<ValidateEntry> validate) {

        if (validate != null) {
            for (ValidateEntry entry : validate) {
                if (entry.getTipo().equals("E")) {
                    return false;
                }
            }
        }
        return true;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/PubblicaFornitureServizi")
    @ApiOperation(nickname = "ProgrammiRestService.pubblicaFornitureServizi", value = "Pubblica i dati e i documenti relativi ad un programma di forniture e servizi", notes = "Ritorna il risultato della pubblicazione e l'id assegnato dal sistema, che dovrà essere riutilizzato per successive pubblicazioni", response = PubblicazioneResult.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<PubblicazioneResult> pubblicaFornitureServizi(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") String idProgramma,
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "syscon") String syscon,
            @RequestParam("modalitaInvio") String modalitaInvio)
            throws ParseException, IOException {

        PubblicazioneResult risultato = new PubblicazioneResult();

        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(Long.valueOf(idProgramma), authorization, xLoginMode);
        if (permission) {
            ResponsePubblicaServizi res = this.programmiManager.getProgrammaServiziForPublish(Long.valueOf(idProgramma));
            if (res.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
                return ResponseEntity.status(status.value()).body(risultato);
            } else {
                if (res != null && res.getData() != null) {
                    PubblicaProgrammaFornitureServiziEntry programma = res.getData();
                    logger.info("pubblicaFornitureServizi(" + programma.getId() + "): inizio metodo");


                    List<ValidateEntry> controlli = new ArrayList<ValidateEntry>();
                    programma.setClientId(clientId);
                    programma.setSyscon(Long.valueOf(syscon));
                    this.validateManager.validatePubblicaProgrammaFornitureServizi(programma, controlli);
                    if (!isValid(controlli)) {
                        // se non supero la validazione
                        risultato = new PubblicazioneResult();
                        if (modalitaInvio != null && modalitaInvio.equals("2")) {
                            risultato.setErrorData(PubblicazioneResult.ERROR_VALIDATION);
                        }
                        risultato.setValidate(controlli);
                    } else {
                        if (modalitaInvio != null && modalitaInvio.equals("2")) {
                            try {
                                //TODO : Configurazione Regione Lombardia
                                String enableRL = this.wConfigManager.getConfigurationValue(ConfigServiceRL.PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA);
                                if (StringUtils.isNotBlank(enableRL) && enableRL.equals("1")){
                                    logger.info("===> Configurazione per Regione Lombardia <===");
                                    UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
                                    risultato = this.programmiManager.pubblicaFornitureServiziRegioneLombardia(userAuthClaimsDTO,programma);
                                }else{
                                    logger.info("===> Configurazione GENERALE <===");
                                    risultato = this.programmiManager.pubblicaFornitureServizi(programma);
                                }
                                if(risultato.isEsito()){
                                    this.programmiManager.AggiornaImportiProgramma(programma.getContri(), 2L);
                                    // aggiorno importi e genero pdf
                                    try {
                                        this.programmiManager.creaPdfNuovaNormativa(programma.getId(), programma.getContri(),
                                                2L, programma.getIdRicevuto(), context);
                                    } catch (Exception ex) {
                                        logger.error("Errore durante la generazione PDF del programma dm 11/2011", ex);
                                    }
                                }
                               
                            } catch (Exception ex) {
                                logger.error("Errore inaspettato durante la pubblicazione del programma forniture e servizi", ex);
                                risultato = new PubblicazioneResult();
                                risultato.setErrorData(PubblicazioneResult.ERROR_UNEXPECTED + ": " + ex.getMessage());
                            }
                        } else {
                            risultato = new PubblicazioneResult();
                            risultato.setValidate(controlli);
                        }
                    }

                    if (risultato.getErrorData() != null) {

                        if (PubblicazioneResult.ERROR_VALIDATION.equals(risultato.getErrorData())) {
                            if (modalitaInvio.equals("1")) {
                                status = HttpStatus.OK;
                            } else {
                                status = HttpStatus.BAD_REQUEST;
                            }
                        } else {
                            status = HttpStatus.INTERNAL_SERVER_ERROR;
                        }
                    }
                }
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            return ResponseEntity.status(status.value()).body(risultato);
        }


        logger.info("pubblicaFornitureServizi: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/PubblicaLavori")
    @ApiOperation(nickname = "ProgrammiRestService.pubblicaLavori", value = "Pubblica i dati e i documenti relativi ad un programma di lavori", notes = "Ritorna il risultato della pubblicazione e l'id assegnato dal sistema, che dovrà essere riutilizzato per successive pubblicazioni", response = PubblicazioneResult.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<PubblicazioneResult> PubblicaLavori(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma") String idProgramma,
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "syscon") String syscon,
            @RequestParam("modalitaInvio") String modalitaInvio)
            throws ParseException, IOException {

        PubblicazioneResult risultato = new PubblicazioneResult();

        HttpStatus status = HttpStatus.OK;

        boolean permission = hasPermission(Long.valueOf(idProgramma), authorization, xLoginMode);
        if (permission) {
            ResponsePubblicaLavori res = this.programmiManager.getProgrammaLavoriForPublish(Long.valueOf(idProgramma), Long.valueOf(syscon));
            if (res.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                risultato.setEsito(false);
                risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
                return ResponseEntity.status(status.value()).body(risultato);
            } else {
                if (res != null && res.getData() != null) {
                    PubblicaProgrammaLavoriEntry programma = res.getData();
                    logger.info("PubblicaLavori(" + programma.getId() + "): inizio metodo");


                    List<ValidateEntry> controlli = new ArrayList<ValidateEntry>();
                    programma.setClientId(clientId);
                    programma.setSyscon(Long.valueOf(syscon));
                    this.validateManager.validatePubblicaProgrammaLavori(programma, controlli);
                    if (!isValid(controlli)) {
                        // se non supero la validazione
                        risultato = new PubblicazioneResult();
                        if (modalitaInvio != null && modalitaInvio.equals("2")) {
                            risultato.setErrorData(PubblicazioneResult.ERROR_VALIDATION);
                        }
                        risultato.setValidate(controlli);
                    } else {
                        if (modalitaInvio != null && modalitaInvio.equals("2")) {
                            // procedo con l'inserimento
                            try {
                                //TODO : Configurazione Regione Lombardia
                                String enableRL = this.wConfigManager.getConfigurationValue(ConfigServiceRL.PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA);
                                if (StringUtils.isNotBlank(enableRL) && enableRL.equals("1")){
                                    logger.info("===> Configurazione per Regione Lombardia <===");
                                    UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
                                    risultato = this.programmiManager.pubblicaLavoriRegioneLombardia(userAuthClaimsDTO,programma);
                                }else{
                                    logger.info("===> Configurazione Generale <===");
                                    risultato = this.programmiManager.pubblicaLavori(programma);
                                }
                                if(risultato.isEsito()){
                                    this.programmiManager.AggiornaImportiProgramma(programma.getContri(), 2L);
                                    // aggiorno importi e genero pdf
                                    try {
                                        this.programmiManager.creaPdfNuovaNormativa(programma.getId(), programma.getContri(),
                                                2L, programma.getIdRicevuto(), context);
                                    } catch (Exception ex) {
                                        logger.error("Errore durante la generazione PDF del programma dm 11/2011", ex);
                                    }
                                }
                            } catch (Exception ex) {
                                logger.error("Errore inaspettato durante la pubblicazione del programma lavori", ex);
                                risultato = new PubblicazioneResult();
                                risultato.setErrorData(PubblicazioneResult.ERROR_UNEXPECTED + ": " + ex.getMessage());
                            }
                        } else {
                            risultato = new PubblicazioneResult();
                            risultato.setValidate(controlli);
                        }
                    }

                    if (risultato.getErrorData() != null) {

                        if (PubblicazioneResult.ERROR_VALIDATION.equals(risultato.getErrorData())) {
                            if (modalitaInvio.equals("1")) {
                                status = HttpStatus.OK;
                            } else {
                                status = HttpStatus.BAD_REQUEST;
                            }
                        } else {
                            status = HttpStatus.INTERNAL_SERVER_ERROR;
                        }
                    }
                }
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            return ResponseEntity.status(status.value()).body(risultato);
        }


        logger.info("pubblicaLavori: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioInterventoNr")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDto> getDettaglioInterventoNr(
            @RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "idProgramma", required = true) String idProgramma,
            @RequestParam(value = "idIntervento", required = true) String idIntervento) {

        logger.info("getDettaglioInterventoNr: inizio metodo");

        ResponseDto risultato = new ResponseDto();
        HttpStatus status = HttpStatus.OK;
        risultato.setEsito(true);

        boolean permission = hasPermission(Long.valueOf(idProgramma), authorization, xLoginMode);
        if (permission) {
            risultato = this.programmiManager.getDettaglioInterventoNonRiproposti(Long.valueOf(idProgramma), Long.valueOf(idIntervento));
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getDettaglioInterventoNr: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    private Long getSysconFromJwtToken(final String authorization, final String loginMode) {
        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

            return userAuthClaimsDTO.getSyscon();

        } catch (Exception e) {
            return null;
        }
    }
}
