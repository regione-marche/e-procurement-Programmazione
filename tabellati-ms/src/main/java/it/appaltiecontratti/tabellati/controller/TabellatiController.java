package it.appaltiecontratti.tabellati.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import it.appaltiecontratti.sicurezza.entity.UserDTO;
import it.appaltiecontratti.tabellati.bl.TabellatiManager;
import it.appaltiecontratti.tabellati.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Servizio REST esposto al path "/Tabellati".
 */
@Validated
@RestController
@CrossOrigin
@EnableTransactionManagement
@SuppressWarnings("java:S5122")
@RequestMapping(value = "${protected.context-path}/gestioneTabellati")
public class TabellatiController {
    /**
     * Logger di classe.
     */
    private final Logger logger = LogManager.getLogger(TabellatiController.class);

    @Autowired
    private TabellatiManager tabellatiManager;

    @Autowired
    private UserManager userManager;

    @Value("${application.codiceProdotto}")
    private String COD_APP;

    /**
     * Estrae le righe del tabellato richiesto mediante chiamata GET e risposta
     * JSON.
     *
     * @param cod      codifica del tabellato
     * @param language lingua di restituzione del del tabellato
     * @return JSON contenente una lista della classe TabellatoEntry
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/Valori")
    @ApiOperation(nickname = "TabellatiRestService.valori", value = "Estrae le voci del tabellato sulla base dei criteri di filtro impostati")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 404, message = "Il tabellato richiesto non � stato trovato (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<TabellatoIstatResult> valori(@RequestHeader("Authorization") String authorization,
                                                       @ApiParam(value = "codice descrittivo del tabellato richiesto", allowableValues = "TipoAvviso", required = true) @QueryParam("cod") String cod,
                                                       @ApiParam(value = "Lingua", required = false) @QueryParam("lingua") String lingua) {

        logger.info("valori(" + cod + "): inizio metodo");
        TabellatoIstatResult risultato = new TabellatoIstatResult();
        HttpStatus status = HttpStatus.OK;
        if (cod == null || "".equals("cod")) {
            risultato.setErrorData("Parametro cod non valorizzato");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.tabellatiManager.getValori(cod);

        if (risultato.getErrorData() != null) {
            if (TabellatoIstatResult.ERROR_NOT_FOUND.equals(risultato.getErrorData())) {
                status = HttpStatus.NOT_FOUND;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        logger.info("valori: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/listaTabellati")
    @ApiOperation(nickname = "TabellatiRestService.tabellatiProgrammi", value = "Estrae tutte le voci del tabellato passate come parametro")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<MultiTabellatoResult> listaTabellati(@RequestHeader("Authorization") String authorization,
                                                               @ApiParam(value = "codici della riga del tabellato", required = true) @RequestParam(value = "cods") List<String> cods,
                                                               @ApiParam(value = "Lingua", required = false) @QueryParam("lingua") String lingua) {

        logger.info("tabellati programmi: inizio metodo");

        MultiTabellatoResult risultato = new MultiTabellatoResult();
        HttpStatus status = HttpStatus.OK;
        if (cods == null || "".equals("cod")) {
            risultato.setErrorData("Parametro cod non valorizzato");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.tabellatiManager.getValoriMultipli(cods);
        if (risultato.getErrorData() != null) {
            if (TabellatoIstatResult.ERROR_NOT_FOUND.equals(risultato.getErrorData())) {
                status = HttpStatus.NOT_FOUND;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        logger.info("valore: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/InsertRUP")
    @ApiOperation(nickname = "TabellatiController.insertRUP", value = "Inserisce o modifica un tecnico nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> insertRUP(@RequestHeader("Authorization") String authorization,
                                                    @ApiParam(value = "Campi necessari all'inserimento di un RUP", required = true) @RequestBody @Valid RupInsertForm form) {

        logger.info("Insert RUP: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        risultato = tabellatiManager.insertRup(form);

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("Insert RUP: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/GetRupOptions")
    @ApiOperation(nickname = "TabellatiController.getRupOptions", value = "Ritorna la lista dei rup contenenti la string di ricerca nel nome o cognome")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaRup> getRupOptions(@RequestHeader("Authorization") String authorization,
                                                          @ApiParam(value = "Campi necessari alla ricerca di un RUP", required = true) RupSearchForm form) {

        logger.info("get RUP Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaRup risultato = new ResponseListaRup();

        if (form == null || form.getRup() == null || form.getStazioneAppaltante() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = tabellatiManager.getRupOptions(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("get RUP Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/GetCigOptions")
    @ApiOperation(nickname = "TabellatiController.getCigOptions", value = "Ritorna la lista dei CIG legati alla porzione di CIG cercata dall'utente")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaCig> getCigOptions(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-loginMode") String xLoginMode,
            @ApiParam(value = "cig") String cig,
            @ApiParam(value = "stazioneAppaltante") String stazioneAppaltante
    ) {

        logger.info("get RUP Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaCig risultato = new ResponseListaCig();

        if (cig == null || stazioneAppaltante == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = tabellatiManager.getCigOptions(cig, stazioneAppaltante, authorization, xLoginMode);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("get RUP Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUtentiOptions")
    @ApiOperation(nickname = "TabellatiController.getRupOptions", value = "Ritorna la lista dei rup contenenti la string di ricerca nel nome o cognome")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaUsers> getUtenteOptions(@RequestHeader("Authorization") String authorization,
                                                               @ApiParam(value = "Campi necessari alla ricerca di un RUP", required = true) RupSearchForm form) {

        logger.info("get RUP Options: inizio metodo");
        HttpStatus status = HttpStatus.OK;
        ResponseListaUsers risultato = new ResponseListaUsers();
        if (form == null || form.getRup() == null || form.getStazioneAppaltante() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getUtenteOptions(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("get RUP Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getUserOptions")
    @ApiOperation(nickname = "TabellatiController.getRupOptions", value = "Ritorna la lista degli utenti contenenti la string di ricerca nella colonna sysute")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaUsers> getUserOptions(@RequestHeader("Authorization") String authorization,
                                                             @RequestParam(value = "search") String search,
                                                             @RequestParam(value = "stazioneAppaltante") String stazioneAppaltante) {

        logger.info("get RUP Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaUsers risultato = new ResponseListaUsers();

        if (search == null || stazioneAppaltante == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = tabellatiManager.getUserOptions(search, stazioneAppaltante);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("get user Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/GetSuggestedRUP")
    @ApiOperation(nickname = "TabellatiController.getSuggestedRup", value = "Restituisce il RUP principale per la stazione appaltante")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseListaRup> getSuggestedRup(@RequestHeader("Authorization") String authorization,
                                                            @ApiParam(value = "Campi necessari alla ricerca del RUP principale per la SA", required = true) SearchMainSARupForm form) {

        logger.info("getSuggestedRup: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaRup risultato = new ResponseListaRup();
        if (form == null || form.getStazioneAppaltante() == null || form.getSyscon() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getSuggestedRup(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getSuggestedRup: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUffOptions")
    @ApiOperation(nickname = "TabellatiController.getUffOptions", value = "Ritorna la lista degli uffici contenenti la string di ricerca nella descrizione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaUffici> getUffOptions(@RequestHeader("Authorization") String authorization,
                                                             @ApiParam(value = "Campi necessari alla ricerca di un ufficio", required = true) UffSearchForm form) {

        logger.info("get Uff. Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaUffici risultato = new ResponseListaUffici();
        if (form == null || form.getStazioneAppaltante() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getUffOptions(form, false);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("get Uff. Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUffOptionsProgrammazione")
    @ApiOperation(nickname = "TabellatiController.getUffOptions", value = "Ritorna la lista degli uffici contenenti la string di ricerca nella descrizione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaUffici> getUffOptionsProgrammazione(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi necessari alla ricerca di un ufficio", required = true) UffSearchForm form) {

        logger.info("getUffOptionsProgrammazione Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaUffici risultato = new ResponseListaUffici();
        if (form == null || form.getStazioneAppaltante() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getUffOptions(form, true);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getUffOptionsProgrammazione Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getComuniOptions")
    @ApiOperation(nickname = "TabellatiController.getComuniOptions", value = "Ritorna la lista dei comuni contenenti la string di ricerca nella descrizione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaComuni> getComuniOptions(@RequestHeader("Authorization") String authorization,
                                                                @ApiParam(value = "Campi necessari alla ricerca di un ufficio", required = true) @QueryParam("search") String search) {

        logger.info("getComuniOptions: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaComuni risultato = new ResponseListaComuni();
        if (search == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getComuniOptions(search);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getComuniOptions: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertUff")
    @ApiOperation(nickname = "TabellatiController.insertUff", value = "Inserisce o modifica un ufficio nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> insertUff(@RequestHeader("Authorization") String authorization,
                                                    @ApiParam(value = "Campi necessari all'inserimento di un ufficio", required = true) @RequestBody @Valid UffInsertForm form) {

        logger.info("Insert Uff: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        risultato = tabellatiManager.insertUff(form);

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        logger.info("Insert Uff: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaUffici")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaUfficiPaginata> getListaUffici(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Form per la ricerca") UfficiSearchForm searchForm) {

        logger.info("getListaUffici: inizio metodo");

        ResponseListaUfficiPaginata risultato = new ResponseListaUfficiPaginata();
        HttpStatus status = HttpStatus.OK;
        risultato = this.tabellatiManager.getListaUffici(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaUffici: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getUfficio")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseUfficio> getUfficio(@RequestHeader("X-loginMode") String xLoginMode,
                                                      @RequestHeader("Authorization") String authorization,
                                                      @ApiParam(value = "id dell'ufficio") @RequestParam(value = "id") Long id) {

        logger.info("getUfficio: inizio metodo");

        ResponseUfficio risultato = new ResponseUfficio();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        boolean permission = hasUfficioPermission(id, authorization, xLoginMode);
        if (permission) {

            risultato = this.tabellatiManager.getUfficio(id);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }

        logger.info("getUfficio: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteUfficio")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> deleteUfficio(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "id dell'ufficio") @RequestParam(value = "id") Long id) {

        logger.info("deleteUfficio: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.tabellatiManager.deleteUfficio(id);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("deleteUfficio: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getInfoCampo")
    @ApiOperation(nickname = "TabellatiController.getDescrizioneCampo", value = "Restituisce la descrizione di un campo associata al suo mnemonico")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseInfoCampo> getInfoCampo(@RequestHeader("Authorization") String authorization,
                                                          @ApiParam(value = "Mnemonico campo", required = true) @RequestParam(value = "mnemonico") String mnemonico) {

        logger.info("getDescrizioneCampo: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseInfoCampo risultato = new ResponseInfoCampo();
        if (mnemonico == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getInfoCampo(mnemonico);

        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        logger.info("getDescrizioneCampo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTabNuts")
    @ApiOperation(nickname = "TabellatiController.getTabNuts", value = "Restituisce la mappa dei dati per le tab NUts")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseTabNuts> getTabNuts(@RequestHeader("Authorization") String authorization) {

        logger.info("getTabNuts: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseTabNuts risultato = new ResponseTabNuts();
        risultato = tabellatiManager.getNutsData();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getTabNuts: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllCpv")
    @ApiOperation(nickname = "TabellatiController.getAllCpv", value = "Restituisce la mappa dei dati per l'albero CPV")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseTabellatoCpv> getAllCpv(@RequestHeader("Authorization") String authorization) {

        logger.info("getAllCpv: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseTabellatoCpv risultato = new ResponseTabellatoCpv();
        risultato = tabellatiManager.getAllCpv();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getAllCpv: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaTecnici")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaRupPaginata> getListaTecnici(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "string per la ricerca") TecniciSearchForm searchForm) {

        logger.info("getListaTecnici: inizio metodo");

        ResponseListaRupPaginata risultato = new ResponseListaRupPaginata();
        HttpStatus status = HttpStatus.OK;
        risultato = this.tabellatiManager.getListaTecnici(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaTecnici: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getTecnico")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseRup> getTecnico(@RequestHeader("Authorization") String authorization,
                                                  @ApiParam(value = "id del tecnico") @RequestParam(value = "id") String id) {

        logger.info("getTecnico: inizio metodo");

        ResponseRup risultato = new ResponseRup();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.tabellatiManager.getTecnico(id);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaTecnici: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteTecnico")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> deleteTecnico(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "id del tecnico") @RequestParam(value = "id") String id) {

        logger.info("deleteTecnico: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (id == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.tabellatiManager.deleteTecnico(id);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("deleteTecnico: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaImprese")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaImprese> getListaImprese(@RequestHeader("Authorization") String authorization,
                                                                @ApiParam(value = "string per la ricerca") ImpreseSearchForm searchForm) {

        logger.info("getListaImprese: inizio metodo");

        ResponseListaImprese risultato = new ResponseListaImprese();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null || searchForm.getStazioneAppaltante() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.tabellatiManager.getListaImprese(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaImprese: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getImpresa")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseImpresa> getImpresa(@RequestHeader("Authorization") String authorization,
                                                      @ApiParam(value = "id dell'impresa") @RequestParam(value = "codiceImpresa") String codiceImpresa) {

        logger.info("getImpresa: inizio metodo");

        ResponseImpresa risultato = new ResponseImpresa();
        HttpStatus status = HttpStatus.OK;
        if (codiceImpresa == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = this.tabellatiManager.getImpresa(codiceImpresa);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaTecnici: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteImpresa")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> deleteImpresa(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "id del tecnico") @RequestParam(value = "codiceImpresa") String codiceImpresa) {

        logger.info("deleteImpresa: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (codiceImpresa == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            List<EntitaCollegate> entita = this.tabellatiManager.getEntitaCollegateImpr();
            if (this.tabellatiManager.checkDelete(codiceImpresa, entita)) {
                risultato = this.tabellatiManager.deleteImpresa(codiceImpresa);
            } else {
                risultato.setErrorData(ResponseResult.ERROR_ENTITY_HAS_CONNECTIONS);
                risultato.setEsito(false);
            }

        } catch (Exception e) {
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaTecnici: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/InsertImpresa")
    @ApiOperation(nickname = "TabellatiController.InsertImpresa", value = "Inserisce o modifica un impresa nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> InsertImpresa(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "Campi necessari all'inserimento di un impresa", required = true) @RequestBody @Valid ImpresaInsertForm form) {

        logger.info("Insert impresa: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        try {
            risultato = tabellatiManager.insertImpresa(form);
        } catch (Exception e) {
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("Insert impresa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateImpresa")
    @ApiOperation(nickname = "TabellatiController.updateImpresa", value = "Modifica un impresa nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> updateImpresa(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "Campi necessari all'inserimento di un impresa", required = true) @RequestBody @Valid ImpresaInsertForm form) {

        logger.info("updateImpresa: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        try {
            risultato = tabellatiManager.updateImpresa(form);
        } catch (Exception e) {
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("updateImpresa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaCdc")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaCentriDiCosto> getListaCdc(@RequestHeader("Authorization") String authorization,
                                                                  @ApiParam(value = "campi per la ricerca") CentriDiCostoSearchform searchForm) {

        logger.info("getListaCdc: inizio metodo");

        ResponseListaCentriDiCosto risultato = new ResponseListaCentriDiCosto();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.tabellatiManager.getListaCentriDiCosto(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaCdc: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCdc")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseCentroDiCosto> getCdc(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "id del cdc") @RequestParam(value = "codiceCdc") Long codiceCdc) {

        logger.info("getCdc: inizio metodo");

        ResponseCentroDiCosto risultato = new ResponseCentroDiCosto();
        HttpStatus status = HttpStatus.OK;
        if (codiceCdc == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.tabellatiManager.getCentroDiCosto(codiceCdc);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getCdc: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteCdc")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> deleteMessage(@RequestHeader("Authorization") String authorization,
                                                        @ApiParam(value = "id del cdc") @RequestParam(value = "codiceCdc") Long codiceCdc) {

        logger.info("deleteCdc: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (codiceCdc == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.tabellatiManager.deleteCentroDiCosto(codiceCdc);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("deleteCdc: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertCdc")
    @ApiOperation(nickname = "TabellatiController.InsertCdc", value = "Inserisce un centro di costo nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> insertCdc(@RequestHeader("Authorization") String authorization,
                                                    @ApiParam(value = "Campi necessari all'inserimento di un centro di costo", required = true) @RequestBody @Valid CentroDiCostoInsertForm form) {

        logger.info("insertCdc: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        try {
            risultato = tabellatiManager.insertCdc(form);
        } catch (Throwable e) {
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("insertCdc: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateCdc")
    @ApiOperation(nickname = "TabellatiController.InsertImpresa", value = "Modifica un centro di costo nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> updateCdc(@RequestHeader("Authorization") String authorization,
                                                    @ApiParam(value = "Campi necessari all'inserimento di un centro di costo", required = true) @RequestBody @Valid CentroDiCostoInsertForm form) {

        logger.info("updateCdc: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        try {
            risultato = tabellatiManager.updateCdc(form);
        } catch (Throwable e) {
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("updateCdc: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCdcOptions")
    @ApiOperation(nickname = "TabellatiController.getCdcOptions", value = "Ritorna la lista dei centri di costo contenenti la string di ricerca nella descrizione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaCentriDiCostoOptions> getCdcOptions(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi necessari alla ricerca di un centro di costo", required = true) CentriDiCostoOptionsSearchForm form) {

        logger.info("getCdcOptions. Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaCentriDiCostoOptions risultato = new ResponseListaCentriDiCostoOptions();
        if (form == null || form.getStazioneAppaltante() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getCdcOptions(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getCdcOptions. Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getMessageReceiver")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseMessageReceiver> getMessageReceiver(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-loginMode") String xLoginMode,
            @ApiParam(value = "stringa per la ricerca in like") @RequestParam(value = "searchString") String searchString) {

        logger.info("getMessageReceiver: inizio metodo");

        ResponseMessageReceiver risultato = new ResponseMessageReceiver();
        HttpStatus status = HttpStatus.OK;
        if (searchString == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
        risultato = this.tabellatiManager.getMessageReceiver(syscon, searchString);
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }
        logger.info("getMessageReceiver: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/sendMessage")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> sendMessage(@RequestHeader("Authorization") String authorization,
                                                      @RequestHeader("X-loginMode") String xLoginMode,
                                                      @ApiParam(value = "Campi di un messaggio da inviare", required = true) @RequestBody @Valid MessageForm form) {

        logger.info("sendMessage: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        try {
            Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
            risultato = this.tabellatiManager.sendMessage(syscon, form);
        } catch (Exception e) {
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }
        logger.info("sendMessage: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getMessages")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseMessages> getMessages(@RequestHeader("Authorization") String authorization,
                                                        @RequestHeader("X-loginMode") String xLoginMode,
                                                        @ApiParam(value = "syscon dell'utente") @RequestParam(value = "syscon") Long syscon,
                                                        @ApiParam(value = "origine del messaggio (Inviato/ricevuto)") @RequestParam(value = "origin") String origin) {

        logger.info("getMessages: inizio metodo");

        ResponseMessages risultato = new ResponseMessages();
        HttpStatus status = HttpStatus.OK;
        if (syscon == null || origin == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        Long sysconLogin = getSysconFromJwtToken(authorization, xLoginMode);
        risultato = this.tabellatiManager.getMessages(sysconLogin, syscon, origin);
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }
        logger.info("sendMessage: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/setMessageSeen")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> setMessageSeen(@RequestHeader("Authorization") String authorization,
                                                         @RequestHeader("X-loginMode") String xLoginMode,
                                                         @ApiParam(value = "id del messaggio") @RequestParam(value = "messageId") Long messageId,
                                                         @ApiParam(value = "flag lettura: 0 non letto 1 letto") @RequestParam(value = "letto") Long letto) {

        logger.info("setMessageSeen: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
        risultato = this.tabellatiManager.setMessageSeen(syscon, messageId, letto);
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }
        logger.info("setMessageSeen: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteMessage")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> deleteMessage(@RequestHeader("Authorization") String authorization,
                                                        @RequestHeader("X-loginMode") String xLoginMode,
                                                        @ApiParam(value = "id del messaggio") @RequestParam(value = "messageId") Long messageId,
                                                        @ApiParam(value = "origine del messaggio (Inviato/ricevuto)") @RequestParam(value = "origin") String origin) {

        logger.info("deleteMessage: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (messageId == null || origin == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
            risultato = this.tabellatiManager.deleteMessage(syscon, messageId, origin);
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }
        logger.info("deleteMessage: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCurrentMessagesStatus")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResultParametric<Integer>> getCurrentMessagesStatus(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-loginMode") String xLoginMode
    ) {

        logger.info("getCurrentMessagesStatus: inizio metodo");

        ResponseResultParametric<Integer> risultato = new ResponseResultParametric<>();
        HttpStatus status = HttpStatus.OK;
        Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
        risultato = this.tabellatiManager.getCurrentMessagesStatus(syscon);
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }

        logger.info("getCurrentMessagesStatus: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    /**
     * Metodo per eliminare un messaggio da parte dell'utente prendendo le info dal token di autenticazione
     *
     * @param authorization
     * @param messageId
     * @return
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteCurrentMessage/{messageId}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResultParametric<Boolean>> deleteCurrentMessage(
            @RequestHeader("X-loginMode") String xLoginMode,
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "id del messaggio") @PathVariable(value = "messageId") Long messageId) {

        logger.info("deleteCurrentMessage: inizio metodo");

        ResponseResultParametric<Boolean> risultato = new ResponseResultParametric<>();
        HttpStatus status = HttpStatus.OK;
        if (messageId == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        try {
            Long syscon = getSysconFromJwtToken(authorization, xLoginMode);
            risultato = this.tabellatiManager.deleteCurrentMessage(syscon, messageId);
        } catch (Exception e) {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        if (risultato.getErrorData() != null) {
            status = setMessagesResponseErrorHttpStatus(risultato.getErrorData());
        }
        logger.info("deleteCurrentMessage: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/GetImpreseOptions")
    @ApiOperation(nickname = "TabellatiController.getImpreseOptions", value = "Ritorna la lista delle imprese contenenti la string di ricerca nel codice o ragione sociale")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaImpreseOptions> getImpreseOptions(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Campi necessari alla ricerca di un'impresa", required = true) SingolaImpresaSearchForm form) {

        logger.info("get RUP Options: inizio metodo");

        HttpStatus status = HttpStatus.OK;
        ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
        if (form == null || form.getDesc() == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        risultato = tabellatiManager.getImpreseOptions(form);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("get Imprese Options: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/health")
    @ApiOperation(nickname = "AvvisiController.health", value = "Monitora lo stato di salute dell'applicazione")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> health() {

        logger.info("health: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        risultato = tabellatiManager.health();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("health: fine metodo");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertSa")
    @ApiOperation(nickname = "TabellatiController.insertSa", value = "Inserisce una stazione appaltante nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<ResponseResult> insertSa(@RequestHeader("X-loginMode") String xLoginMode,
                                                   @RequestHeader("Authorization") String authorization,
                                                   @ApiParam(value = "Campi necessari all'inserimento di una stazione appaltante", required = true) @RequestBody @Valid StazioneAppaltanteInsertForm form) {

        logger.info("insertSa: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        boolean permission = hasSaPermission(authorization, xLoginMode);
        if (permission) {

            risultato = tabellatiManager.insertSa(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("insertSa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateSa")
    @ApiOperation(nickname = "TabellatiController.updateSa", value = "Aggiorna una stazione appaltante nel DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})

    public ResponseEntity<BaseResponse> updateSa(@RequestHeader("X-loginMode") String xLoginMode,
                                                 @RequestHeader("Authorization") String authorization,
                                                 @ApiParam(value = "Campi necessari per l'aggiornamento di una stazione appaltante", required = true) @RequestBody @Valid StazioneAppaltanteUpdateForm form) {

        logger.info("updateSa: inizio metodo");

        BaseResponse risultato = new BaseResponse();
        HttpStatus status = HttpStatus.OK;

        boolean permission = hasSaPermission(authorization, xLoginMode);
        if (permission) {

            risultato = tabellatiManager.updateSa(form);
            if (risultato.getErrorData() != null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("updateSa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    private boolean hasUfficioPermission(Long idUfficio, String authorization, String loginMode) {

        try {
            Long syscon = getSysconFromJwtToken(authorization, loginMode);
            return this.tabellatiManager.hasPermissionOnUfficio(syscon, idUfficio);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean hasSaPermission(String authorization, String loginMode) {

        try {
            Long syscon = getSysconFromJwtToken(authorization, loginMode);
            return this.tabellatiManager.hasPermissionOnSa(syscon);
        } catch (Exception e) {
            return false;
        }
    }

    private Long getSysconFromJwtToken(final String authorization, final String loginMode) {
        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

            return userAuthClaimsDTO.getSyscon();

        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaSa")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaStazioneAppaltante> getListaSa(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "campi per la ricerca") ArchivioSaSearchform searchForm) {

        logger.info("getListaSa: inizio metodo");

        ResponseListaStazioneAppaltante risultato = new ResponseListaStazioneAppaltante();
        HttpStatus status = HttpStatus.OK;

        if (searchForm == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.tabellatiManager.getListaStazioneAppaltante(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaSa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteSa")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> deleteSa(@RequestHeader("X-loginMode") String xLoginMode,
                                                   @RequestHeader("Authorization") String authorization,
                                                   @ApiParam(value = "id della stazione appaltante") @RequestParam(value = "codiceSA") String codiceSA) {

        logger.info("deleteSa: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;
        if (codiceSA == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }
        boolean permission = hasSaPermission(authorization, xLoginMode);
        if (permission) {

            try {
                risultato = this.tabellatiManager.deleteStazioneAppaltante(codiceSA);
            } catch (Exception e) {
                risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
                risultato.setEsito(false);
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
            status = HttpStatus.UNAUTHORIZED;
        }
        logger.info("deleteSa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getSa")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseStazioneAppaltante> getSa(@RequestHeader("Authorization") String authorization,
                                                            @ApiParam(value = "id della stazione appaltante") @RequestParam(value = "codiceSA") String codiceSA) {

        logger.info("getSa: inizio metodo");

        ResponseStazioneAppaltante risultato = new ResponseStazioneAppaltante();
        HttpStatus status = HttpStatus.OK;
        if (codiceSA == null) {
            status = HttpStatus.BAD_REQUEST;
            risultato.setEsito(false);
            risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
            return ResponseEntity.status(status.value()).body(risultato);
        }

        risultato = this.tabellatiManager.getStazioneAppaltante(codiceSA);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getSa: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getSaByCodiceFiscale")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseBaseStazioneAppaltante> getSaByCodiceFiscale(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Codice fiscale della stazione appaltante") @RequestParam(value = "codiceFiscale") String codiceFiscale) {

        logger.info("getSaByCodiceFiscale: inizio metodo");

        ResponseBaseStazioneAppaltante risultato = new ResponseBaseStazioneAppaltante();
        HttpStatus status = HttpStatus.OK;

        risultato = this.tabellatiManager.getSaByCodiceFiscale(codiceFiscale);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getSaByCodiceFiscale" + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getLogoutUrl")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseResult> getLogoutUrl(@RequestHeader("Authorization") String authorization) {

        logger.info("getLogoutUrl: inizio metodo");

        ResponseResult risultato = new ResponseResult();
        HttpStatus status = HttpStatus.OK;

        risultato = this.tabellatiManager.getLogoutUrl();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getLogoutUrl" + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaTecniciNonPaginata")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaRup> getListaTecniciNonPaginata(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Form di ricerca") TecniciNonPaginatiSearchForm searchForm) {

        logger.info("getListaTecniciNonPaginata: inizio metodo");

        ResponseListaRup risultato = new ResponseListaRup();
        HttpStatus status = HttpStatus.OK;
        risultato = this.tabellatiManager.getListaTecniciNonPaginata(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaTecniciNonPaginata: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaImpreseNonPaginata")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaImprese> getListaImpreseNonPaginata(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Form di ricerca") ImpreseNonPaginateSearchForm searchForm) {

        logger.info("getListaImpreseNonPaginata: inizio metodo");

        ResponseListaImprese risultato = new ResponseListaImprese();
        HttpStatus status = HttpStatus.OK;
        risultato = this.tabellatiManager.getListaImpreseNonPaginata(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaImpreseNonPaginata: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaCdcNonPaginata")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaCentriDiCosto> getListaCdcNonPaginata(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Form di ricerca") CdcNonPaginatiSearchForm searchForm) {

        logger.info("getListaCdcNonPaginata: inizio metodo");

        ResponseListaCentriDiCosto risultato = new ResponseListaCentriDiCosto();
        HttpStatus status = HttpStatus.OK;
        risultato = this.tabellatiManager.getListaCdcNonPaginata(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaCdcNonPaginata: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaUfficiNonPaginata")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseListaUffici> getListaUfficiNonPaginata(
            @RequestHeader("Authorization") String authorization,
            @ApiParam(value = "Form di ricerca") UfficiNonPaginatiSearchForm searchForm) {

        logger.info("getListaUfficiNonPaginata: inizio metodo");

        ResponseListaUffici risultato = new ResponseListaUffici();
        HttpStatus status = HttpStatus.OK;
        risultato = this.tabellatiManager.getListaUfficiNonPaginata(searchForm);
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        logger.info("getListaUfficiNonPaginata: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getAppInfo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
            @ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
    public ResponseEntity<ResponseDto> getAppInfo(@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization) {

        logger.info("getAppInfo: inizio metodo");

        ResponseDto risultato;
        HttpStatus status = HttpStatus.OK;

        risultato = this.tabellatiManager.getAppInfo();
        if (risultato.getErrorData() != null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getAppInfo: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(risultato);
    }

    private HttpStatus setMessagesResponseErrorHttpStatus(String errorData) {
        switch (errorData) {
            case ResponseResult.ERROR_PERMISSION:
            case ResponseResult.ERROR_DISABLED:
                return HttpStatus.FORBIDDEN;
            case ResponseResult.ERROR_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Operation(
            summary = "Restituisce il count dei report predefiniti associati alla stazione appaltante, al profilo e all'utente.",
            description = "Restituisce il count dei report predefiniti associati alla stazione appaltante, al profilo e all'utente.",
            operationId = "getCountListaReportPredefiniti",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseCountReportPredefiniti.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getCountListaReportPredefiniti", consumes = org.springframework.http.MediaType.ALL_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCountReportPredefiniti> getCountListaReportPredefiniti(
            @Parameter(hidden = true) Authentication authentication,
            @RequestParam(value = "idProfilo") String idProfilo,
            @RequestParam(value = "uffInt") String uffInt,
            @RequestParam(value = "syscon") Long syscon
    ){

        logger.debug("START esecuzione controller TabellatiController::getCountListaReportPredefiniti");

        ResponseCountReportPredefiniti response = null;
        HttpStatus status = HttpStatus.OK;

        response = this.tabellatiManager.getCountListaReportPredefiniti(
                syscon,
                idProfilo,
                uffInt,
                COD_APP
        );

        if (response.getMessages() != null && !response.getMessages().isEmpty()) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        logger.debug("END esecuzione controller TabellatiController nel metodo getCountListaReportPredefiniti");

        return ResponseEntity.status(status.value()).body(response);
    }
}
