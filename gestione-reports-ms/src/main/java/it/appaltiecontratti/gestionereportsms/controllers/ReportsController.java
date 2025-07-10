package it.appaltiecontratti.gestionereportsms.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.dto.*;
import it.appaltiecontratti.gestionereportsms.exceptions.GenericReportOperationException;
import it.appaltiecontratti.gestionereportsms.managers.RicParamManager;
import it.appaltiecontratti.gestionereportsms.managers.RicercheManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author andrea.chinellato
 * */

@CrossOrigin
@RestController
@RequestMapping("/protected/gestioneReports")
public class ReportsController extends AbstractBaseController {

	/**
     * Logger di classe.
     * */
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    /**
     * Managers/Utilities
     * */
    @Autowired
    private RicercheManager rManager;

    @Autowired
    private RicParamManager ricParamManager;

    /**
     * Variabile d'ambiente. Vedere application.properties
     * */
    @Value("${application.codiceProdotto}")
    private String COD_APP;

    /**
     * Object Mapper
     * */
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Metodi di classe
     * */
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

    /**
     * Endpoint di classe
     * */

    @Operation(
            summary = "Restituisce una lista di report",
            description = "Restitiuisce una lista di report",
            operationId = "getListaReports",
            responses = {
                    @ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getListaReports", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getListaReports(
        @Parameter(hidden = true) Authentication authentication,
        @Parameter(description = "Titolo del report") @RequestParam(required = false) String nome,
        @Parameter(description = "Descrizione del report") @RequestParam(required = false) String descrizione,
        @Parameter(description = "Stato di pubblicazione del report") @RequestParam(required = false) Integer disp,
        @Parameter(description = "Utente creatore") @RequestParam(required = false) String sysute
    ){

        logger.info("START esecuzione controller ReportsController nel metodo getListaReports");

        ResponseListaDTO response = null;
        try {

            UserDTO user = (UserDTO) authentication.getPrincipal();

            response = rManager.getListaReport(
                COD_APP,
                4,
                nome,
                descrizione,
                disp,
                sysute,
                "nome",
                "asc",
                user.getSyscon()
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getListaReports");

        return response;
    }

    @Operation(
            summary = "Restituisce i dettagli di un report",
            description = "Restitiuisce i dettagli di un report",
            operationId = "getDetailReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getDetailReport/{idRicerca}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getDetailReport(
            @PathVariable(value = "idRicerca") Long idRicerca
    ) {
        logger.info("START esecuzione controller ReportsController nel metodo getDetailReports");

        ResponseListaDTO response = null;
        try{
            response = rManager.getDetailReport(idRicerca);
        } catch (Exception e){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(response != null && !AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getDetailReports");

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di parametri di un singolo report",
            description = "Restituisce una lista di parametri di un singolo report",
            operationId = "getListaParametri",
            responses = {
                    @ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getListaParametri/{idRicerca}/{syscon}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getListaParametri(
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "syscon") Long syscon
    ){

        logger.info("START esecuzione controller ReportsController nel metodo getListaParams");

        ResponseListaDTO response = null;
        try {
            response = ricParamManager.getListaParametri(idRicerca, syscon, "progressivo", "asc");
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getListaParams");

        return response;
    }

    @Operation(
            summary = "Aggiorna la definizione SQL di un singolo report",
            description = "Aggiorna la definizione SQL di un singolo report",
            operationId = "updateDefSqlReport",
            responses = {
                    @ApiResponse(
                            description = "DefinizioneReportFormDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping(path = "/updateDefSqlReport/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO updateDefinizioneSqlReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestBody DefinizioneReportFormDTO form,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController nel metodo updateDefinizioneSqlReport");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.updateDefinizioneSqlReport(form.getIdRicerca(), form, syscon, ipEvento, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo updateDefinizioneSqlReport");

        return response;
    }

    @Operation(
            summary = "Aggiorna i dati generali di un singolo report",
            description = "Aggiorna i dati generali di un singolo report",
            operationId = "updateDatiGeneraliReport",
            responses = {
                    @ApiResponse(
                            description = "DatiGeneraliFormDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping(path = "/updateDatiGeneraliReport/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO updateDatiGeneraliReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(hidden = true) Authentication authentication,
            @RequestBody WRicercheDTO form,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo updateDatiGeneraliReport");

        String ipEvento = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.updateDatiGeneraliReport(form.getIdRicerca(), form, user, syscon, ipEvento, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo updateDatiGeneraliReport");

        return response;
    }

    @Operation(
            summary = "Inserisce un nuovo parametro nella tabella w_ricparam di un singolo report",
            description = "Inserisce un nuovo parametro nella tabella w_ricparam di un singolo report",
            operationId = "insertNewParamReport",
            responses = {
                    @ApiResponse(
                            description = "DatiGeneraliFormDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PostMapping(path = "/insertNewParamReport/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO insertNuovoParametroReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestBody WRicParamDTO form,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController::insertNuovoParametroReport");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.insertNewParamReport(form, ipEvento, syscon, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController::insertNuovoParametroReport");

        return response;
    }

    @Operation(
            summary = "Copia il report corrispondente all'idRicerca passato in input inserendolo nella w_ricerche",
            description = "Copia il report corrispondente all'idRicerca passato in input inserendolo nella w_ricerche",
            operationId = "duplicaRigaReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PostMapping(path = "/copyRowReport/{idRicerca}/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO duplicaRigaReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(hidden = true) Authentication authentication,
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo duplicaRigaReport");

        String ipEvento = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.duplicaRigaReport(idRicerca, ipEvento, syscon, idProfilo, user);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo duplicaRigaReport");

        return response;
    }

    @Operation(
            summary = "Cancella il report corrispondente all'idRicerca passato in input cancellandolo dalla w_ricerche",
            description = "Cancella il report corrispondente all'idRicerca passato in input cancellandolo dalla w_ricerche",
            operationId = "deleteRowReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @DeleteMapping(path = "/deleteRowReport/{idRicerca}/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO deleteRowReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo deleteRowReport");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.deleteRowReport(idRicerca, syscon, ipEvento, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo deleteRowReport");

        return response;
    }

    @Operation(
            summary = "Recupera il dettaglio di un parametro appartenente al report con idRicerca passato a parametro",
            description = "Recupera il dettaglio di un parametro appartenente al report con idRicerca passato a parametro",
            operationId = "getDetailParamReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getDetailParamReport/{idRicerca}/{codice}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getDetailParamReport(
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "codice") String codice
    ){

        logger.info("START esecuzione controller ReportsController nel metodo getDetailParamReport");

        ResponseListaDTO response = null;
        try {
            response = rManager.getDetailParamReport(idRicerca, codice);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getDetailParamReport");

        return response;
    }

    @Operation(
            summary = "Aggiorna il parametro correlato al report passato nel form",
            description = "Aggiorna il parametro correlato al report passato nel form",
            operationId = "updateDettaglioParametroReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping(path = "/updateDettaglioParametroReport/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO updateDettaglioParametroReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestBody WRicParamDTO form,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController nel metodo getDetailParamReport");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.updateDettaglioParametroReport(form, syscon, ipEvento, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getDetailParamReport");

        return response;
    }

    @Operation(
            summary = "Cancella il parametro corrispondente ad idRicerca e codice passato a parametro",
            description = "Cancella il parametro corrispondente ad idRicerca e codice passato a parametro",
            operationId = "deleteParamRowReport",
            responses = {
                    @ApiResponse(
                            description = "void",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @DeleteMapping(path = "/deleteParamRowReport/{idRicerca}/{codice}/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteParamRowReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "codice") String codice,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController nel metodo deleteRowParamReport");

        String ipEvento = resolveRemoteIpAddress(request);

        try {
            rManager.deleteParamRowReport(idRicerca, codice, ipEvento, syscon, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo deleteRowParamReport");
    }

    @Operation(
            summary = "Inverte la riga corrispondente alla posizione del parametro con la riga del progressivo precedente",
            description = "Inverte la riga corrispondente alla posizione del parametro con la riga del progressivo precedente",
            operationId = "moveParamRowUp",
            responses = {
                    @ApiResponse(
                            description = "void",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping(path = "/moveParamRowUp/{idRicerca}/{codice}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void moveParamRowUp(
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "codice") String codice
    ){

        logger.info("START esecuzione controller ReportsController nel metodo moveParamRowUp");

        try {
            rManager.moveParamUp(idRicerca, codice);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo moveParamRowUp");
    }

    @Operation(
            summary = "Inverte la riga corrispondente alla posizione del parametro con la riga del progressivo successivo",
            description = "Inverte la riga corrispondente alla posizione del parametro con la riga del progressivo successivo",
            operationId = "moveParamRowDown",
            responses = {
                    @ApiResponse(
                            description = "void",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping(path = "/moveParamRowDown/{idRicerca}/{codice}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void moveParamRowDown(
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "codice") String codice
    ){

        logger.info("START esecuzione controller ReportsController nel metodo moveParamRowDown");

        try {
            rManager.moveParamDown(idRicerca, codice);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo moveParamRowDown");
    }

    @Operation(
            summary = "Esegue il report con idRicerca e valori dei parametri dinamici inseriti dall'utente",
            description = "Esegue il report con idRicerca e valori dei parametri dinamici inseriti dall'utente",
            operationId = "executeReportWithParams",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping(path = "/executeReportWithParams/{idRicerca}/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO executeReportWithParams(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idRicerca") Long idRicerca,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo,
            @RequestBody GenericDynamicFormValueDTO form
    ){

        logger.info("START esecuzione controller ReportsController nel metodo executeReportWithParams");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO responseSaveCache = null;
        ResponseListaDTO responseExecution = null;
        try {
            //Questa response è fittizia in quanto torna solamente true o false, validando o meno l'esecuzione vera e propria del report nel metodo executeReportWithParams
            //In sostanza questo metodo salva nella WCacheRicPar i parametri inseriti dall'utente. In questo modo se l'utente dovesse reinserire i valori per lo stesso parametro,
            //si ritroverebbe il campo prepopolato con lo stesso valore salvato in precedenza.
            responseSaveCache = rManager.saveCache(syscon, idRicerca, form, ipEvento, idProfilo);

            //Metodo di esecuzione del report con i parametri inseriti dall'utente.
            responseExecution = rManager.executeReportWithParams(idRicerca, form, syscon, ipEvento, idProfilo, false);

            //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
            if(responseSaveCache == null || responseExecution == null){

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

                throw ex;
            }
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(responseSaveCache.getDone()) || !AppConstants.RESPONSE_DONE_Y.equals(responseExecution.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo executeReportWithParams");

        return responseExecution;
    }

    @Operation(
            summary = "Inserisce un nuovo report nella tabella w_ricerche",
            description = "Inserisce un nuovo report nella tabella w_ricerche",
            operationId = "insertNewReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PostMapping(path = "/insertNewReport/{syscon}/{idProfilo}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO creaNuovoReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(hidden = true) Authentication authentication,
            @RequestBody WRicercheDTO form,
            @PathVariable(value = "syscon") Long syscon,
            @PathVariable(value = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController::creaNuovoReport");

        String ipEvento = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.insertNewReport(form, syscon, ipEvento, idProfilo, user);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(response.getMessages() != null && !AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){
            return response;
        }
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController::creaNuovoReport");

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di profili",
            description = "Restitiuisce una lista di profili",
            operationId = "getListaProfili",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(value ="/profili", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getListaProfili(@Parameter(hidden = true) Authentication authentication) {
        logger.info("START esecuzione controller ReportsController nel metodo getListaProfili");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.getListaProfili(user);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getListaProfili");

        return response;
    }

    @Operation(
            summary = "Aggiorna i profili dell'utente nella tabella w_ricpro",
            description = "Aggiorna i profili dell'utente nella tabella w_ricpro",
            operationId = "setProfiliUtenteReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/setProfiliReport/{idRicerca}/{syscon}/{idProfilo}/profili")
    public boolean setProfiliUtenteReport(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idRicerca") final Long idRicerca,
            @PathVariable(value = "syscon") final Long syscon,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody @Valid final ProfiliUtenteReportEditDTO profiliUtente) {

        logger.info("START esecuzione metodo di ReportsController di setProfiliUtenteReport per syscon [ {} ] e profili [ {} ]", idRicerca,
                profiliUtente);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        boolean response;
        try {
            response = rManager.setProfiliUtenteReport(user, idRicerca, profiliUtente.getListaProfili(), ipAddress, syscon, idProfilo);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di profili",
            description = "Restitiuisce una lista di profili",
            operationId = "getListaProfili",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(value ="/getProfiliReport/{idRicerca}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getProfiliReport(
            @PathVariable(value = "idRicerca") final Long idRicerca
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo getProfiliReport");

        ResponseListaDTO response = null;
        try {
            response = rManager.getProfiliReport(idRicerca);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getListaProfili");

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di uffici intestatari",
            description = "Restitiuisce una lista di uffici intestatari",
            operationId = "getUfficiIntestatariReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping("/getListaUfficiIntestatari")
    public ResponseListaDTO getListaUfficiIntestatari(
            @Parameter(hidden = true) Authentication authentication
    ) {
        logger.info("START esecuzione nel controller ReportsController per il metodo getUfficiIntestatariReport");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.getUfficiIntestatari(user);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione nel controller ReportsController per il metodo getUfficiIntestatariReport");

        return response;
    }

    @Operation(
            summary = "Aggiorna gli uffici intestatari associati al report corrispondente passato a parametro",
            description = "Aggiorna gli uffici intestatari associati al report corrispondente passato a parametro",
            operationId = "setUfficiIntestatariReport",
            responses = {
                    @ApiResponse(
                            description = "boolean",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/setUfficiIntestatariReport/{idRicerca}/{syscon}/{idProfilo}")
    public ResponseListaDTO setUfficiIntestatariReport(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(hidden = true) Authentication authentication,
            @PathVariable(value = "idRicerca") final Long idRicerca,
            @PathVariable(value = "syscon") final Long syscon,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody @Valid final UfficioIntestatarioEditDTO ufficiIntestatariUtente) {

        logger.info("START esecuzione controller ReportsController nel metodo setUfficiIntestatariUtente");

        final String ipEvento = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.setUfficiIntestatariReport(user, idRicerca, syscon, ipEvento, idProfilo, ufficiIntestatariUtente.getListaUfficiIntestatari());
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo setUfficiIntestatariUtente");

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di uffici intestatari associati ad un report",
            description = "Restituisce una lista di uffici intestatari associati ad un report",
            operationId = "getUfficiIntestatariReport",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping("/getUfficiIntestatariReport/{idRicerca}")
    public ResponseListaDTO getUfficiIntestatariReport(
            @Parameter(hidden = true) Authentication authentication,
            @PathVariable(value = "idRicerca") final Long idRicerca
    ) {

        logger.info("START esecuzione ReportsController getUfficiIntestatariReport per il report con idRicerca [{}]", idRicerca);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        ResponseListaDTO response = null;
        try {
            response = rManager.getUfficiIntestatariReport(user, idRicerca);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione ReportsController getUfficiIntestatariReport per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Operation(
            summary = "Restituisce il risultato del report esportato in formato CSV",
            description = "Restituisce il risultato del report esportato in formato CSV",
            operationId = "exportRisultatoReportCsv",
            responses = {
                    @ApiResponse(
                            description = "boolean",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/exportRisultatoReportCsv/{idProfilo}")
    public ResponseListaDTO exportRisultatoReportCsv (
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody ResultReportQueryParamsDTO formResultParamsReport
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo exportRisultatoReportCsv");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.exportRisultatoReportCsv(
                formResultParamsReport.getColumnNames(),
                formResultParamsReport.getValues(),
                formResultParamsReport.getIdRicerca(),
                ipEvento,
                formResultParamsReport.getSyscon(),
                idProfilo
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo exportRisultatoReportCsv");

        return response;
    }

    @Operation(
            summary = "Restituisce il risultato del report esportato in formato DOCX",
            description = "Restituisce il risultato del report esportato in formato DOCX",
            operationId = "exportRisultatoReportDocx",
            responses = {
                    @ApiResponse(
                            description = "boolean",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/exportRisultatoReportDocx/{idProfilo}")
    public ResponseListaDTO exportRisultatoReportDocx (
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody ResultReportQueryParamsDTO formResultParamsReport
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo exportRisultatoReportDocx");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.exportRisultatoReportDocx(
                formResultParamsReport.getColumnNames(),
                formResultParamsReport.getValues(),
                formResultParamsReport.getIdRicerca(),
                ipEvento,
                formResultParamsReport.getSyscon(),
                idProfilo
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo exportRisultatoReportDocx");

        return response;
    }

    @Operation(
            summary = "Restituisce il risultato del report esportato in formato PDF",
            description = "Restituisce il risultato del report esportato in formato PDF",
            operationId = "exportRisultatoReportPdf",
            responses = {
                    @ApiResponse(
                            description = "boolean",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/exportRisultatoReportPdf/{idProfilo}")
    public ResponseListaDTO exportRisultatoReportPdf (
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody ResultReportQueryParamsDTO formResultParamsReport
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo exportRisultatoReportRtf");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.exportRisultatoReportPdf(
                formResultParamsReport.getColumnNames(),
                formResultParamsReport.getValues(),
                formResultParamsReport.getIdRicerca(),
                formResultParamsReport.getSyscon(),
                ipEvento,
                idProfilo
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo exportRisultatoReportRtf");

        return response;
    }

    @Operation(
            summary = "Restituisce il risultato del report esportato in formato JSON",
            description = "Restituisce il risultato del report esportato in formato JSON",
            operationId = "exportRisultatoReportJson",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/exportRisultatoReportJson/{idProfilo}")
    public ResponseListaDTO exportRisultatoReportJson (
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody ResultReportQueryParamsDTO formResultParamsReport
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo exportRisultatoReportJson");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.exportRisultatoReportJson(
                formResultParamsReport.getColumnNames(),
                formResultParamsReport.getValues(),
                formResultParamsReport.getIdRicerca(),
                formResultParamsReport.getSyscon(),
                ipEvento,
                idProfilo
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo exportRisultatoReportJson");

        return response;
    }

    @Operation(
            summary = "Restituisce il risultato del report esportato in formato XLSX",
            description = "Restituisce il risultato del report esportato in formato XLSX",
            operationId = "exportRisultatoReportXlsx",
            responses = {
                    @ApiResponse(
                            description = "ResponseListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Richiesta non valida",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PutMapping("/exportRisultatoReportXlsx/{idProfilo}")
    public ResponseListaDTO exportRisultatoReportXlsx (
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable(value = "idProfilo") final String idProfilo,
            @RequestBody ResultReportQueryParamsDTO formResultParamsReport
    ) {

        logger.info("START esecuzione controller ReportsController nel metodo exportRisultatoReportXlsx");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            response = rManager.exportRisultatoReportXlsx(
                formResultParamsReport.getColumnNames(),
                formResultParamsReport.getValues(),
                formResultParamsReport.getIdRicerca(),
                formResultParamsReport.getSyscon(),
                ipEvento,
                idProfilo
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo exportRisultatoReportXlsx");

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di report predefiniti",
            description = "Restitiuisce una lista di report predefiniti",
            operationId = "getListaReportPredefiniti",
            responses = {
                    @ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getListaReportPredefiniti/{syscon}/{idProfilo}/{uffInt}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getListaReportPredefiniti(
            @PathVariable(value = "syscon") String syscon,
            @PathVariable(value = "idProfilo") String idProfilo,
            @PathVariable(value = "uffInt") String uffInt,
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "Titolo del report") @RequestParam(required = false) String nome,
            @Parameter(description = "Descrizione del report") @RequestParam(required = false) String descrizione
    ){

        logger.info("START esecuzione controller ReportsController nel metodo getListaReportPredefiniti");

        ResponseListaDTO response = null;
        try {

            UserDTO user = (UserDTO) authentication.getPrincipal();
            String ipEvento = resolveRemoteIpAddress(request);

            response = rManager.getListaReportPredefiniti(
                syscon,
                idProfilo,
                uffInt,
                COD_APP,
                nome,
                descrizione,
                "nome",
                "asc",
                user.getSyscon(),
                ipEvento
            );
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController nel metodo getListaReportPredefiniti");

        return response;
    }

    @Operation(
            summary = "Restituisce una lista di report",
            description = "Restitiuisce una lista di report",
            operationId = "getListaReports",
            responses = {
                    @ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @GetMapping(path = "/getAbilitazioniUffInt", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO getAbilitazioneFiltroUffInt(){

        logger.info("START esecuzione controller ReportsController::getAbilitazioneFiltroUffInt");

        ResponseListaDTO response = null;
        try {
            response = rManager.getAbilitazioneFiltroUffInt();
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController::getAbilitazioneFiltroUffInt");

        return response;
    }

    @Operation(
            summary = "Cancella un report preferito dalla WPreferiti",
            description = "Cancella un report preferito dalla WPreferiti",
            operationId = "rimuoviReportPreferito",
            responses = {
                    @ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @DeleteMapping(path = "/rimuoviReportPreferito", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO rimuoviReportPreferito(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(description = "idRicerca") Long idRicerca,
            @Parameter(description = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController::rimuoviReportPreferito");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {
            UserDTO user = (UserDTO) authentication.getPrincipal();

            response = rManager.rimuoviReportPreferito(idRicerca, user.getSyscon(), idProfilo, ipEvento);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController::rimuoviReportPreferito");

        return response;
    }

    @Operation(
            summary = "Aggiunge un nuovo report preferito dalla WPreferiti",
            description = "Aggiunge un nuovo report preferito dalla WPreferiti",
            operationId = "aggiungiReportPreferito",
            responses = {
                    @ApiResponse(
                            description = "RicercheListaDTO",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseListaDTO.class)
                            )
                    )
            }
    )
    @Hidden
    @PostMapping(path = "/aggiungiReportPreferito", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListaDTO aggiungiReportPreferito(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(description = "idRicerca") Long idRicerca,
            @Parameter(description = "idProfilo") String idProfilo
    ){

        logger.info("START esecuzione controller ReportsController::aggiungiReportPreferito");

        String ipEvento = resolveRemoteIpAddress(request);

        ResponseListaDTO response = null;
        try {

            UserDTO user = (UserDTO) authentication.getPrincipal();

            response = rManager.aggiungiReportPreferito(idRicerca, user.getSyscon(), idProfilo, ipEvento);
        } catch (Exception e) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);
            throw ex;
        }

        //Controllo response nulla. L'eccezione viene catturata dall'ExceptionHandler
        if(response == null){
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA);

            throw ex;
        }

        //Controllo che il metodo abbia terminato correttamente.
        if(!AppConstants.RESPONSE_DONE_Y.equals(response.getDone())){

            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_NOT_DONE);
            throw ex;
        }

        logger.info("END esecuzione controller ReportsController::aggiungiReportPreferito");

        return response;
    }
}