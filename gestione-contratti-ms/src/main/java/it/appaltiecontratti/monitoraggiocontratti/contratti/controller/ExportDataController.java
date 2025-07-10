package it.appaltiecontratti.monitoraggiocontratti.contratti.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.GareSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaGare;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePubblicaFase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.time.OffsetDateTime;

@Validated
@CrossOrigin
@RestController
@EnableTransactionManagement
@RequestMapping(value = "${protected.context-path}/gestioneContratti")
@SuppressWarnings("java:S5122")
public class ExportDataController {


    /**
     * Servizio REST esposto al path "${jwt.context-path}/gestioneContratti".
     */

    /** Logger di classe. */
    protected Logger logger = LogManager.getLogger(ExportDataController.class);

    @Autowired
    private ContrattiManager contrattiManager;

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getJsonListaSchede")
    public ResponseEntity<ResponseDto> getJsonListaSchede( @RequestHeader("Authorization") String authorization,
                                                       @RequestParam(value = "cig") String cig) {

        logger.info("getJsonListaSchede: inizio metodo");
        HttpStatus status = HttpStatus.OK;
        ResponseDto result = this.contrattiManager.getJsonListaSchede(cig);

        if(result.getErrorData() != null){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getJsonListaSchede: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(result);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaCig")
    public ResponseEntity<ResponseDto> getListaCig( @RequestHeader("Authorization") String authorization,
                                                    @RequestParam(value = "dataDa", required = false) String dataDa,
                                                    @RequestParam(value = "dataA", required = false) String dataA) {

        logger.info("getListaCig: inizio metodo");
        HttpStatus status = HttpStatus.OK;
        ResponseDto result = this.contrattiManager.getListaCig(dataDa,dataA);

        if(result.getErrorData() != null){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.info("getListaCig: fine metodo [http status " + status.value() + "]");
        return ResponseEntity.status(status.value()).body(result);
    }


}
