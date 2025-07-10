package it.appaltiecontratti.monitoraggiocontratti.contratti.controller;

import javax.ws.rs.core.MediaType;

import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v1021.SchedePcpManagerV1021;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.SchedePcpManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePubblicaFase;

@SuppressWarnings("java:S5122")
@CrossOrigin
@RestController
@RequestMapping(value = "${protected.context-path}/gestioneContratti")
@Validated
public class SchedeController {

	/**
	 * Servizio REST esposto al path "${jwt.context-path}/gestioneContratti".
	 */
	
	@Autowired
	private SchedePcpManagerV102 schedePcpManagerV102;

	@Autowired
	private SchedePcpManagerV1021 schedePcpManagerV1021;
	
	@Autowired
	private ContrattiManager contrattiManager;

	/** Logger di classe. */
	protected Logger logger = LogManager.getLogger(SchedeController.class);


	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/verificaSchedaPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePubblicaFase> verificaScheda(@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "codFase") Long codFase, @RequestParam(value = "num") Long num) {
		
		logger.info("verificaScheda: inizio metodo");
		
		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		HttpStatus status = HttpStatus.OK;		
		try {
			String version = this.contrattiManager.versionPcp();
			if("1.0.2".equals(version)) {
				risultato = this.schedePcpManagerV102.verificaSchedaPcp(codGara,codLotto,codFase,num);
			} else if("1.0.2.1".equals(version)) {
				risultato = this.schedePcpManagerV1021.verificaSchedaPcp(codGara,codLotto,codFase,num);
			}
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("verificaScheda: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/deleteFase")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
//			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
//	public ResponseEntity<ResponsePubblicaFase> deleteFase(@RequestHeader("Authorization") String authorization,
//			@RequestHeader("applicationToken") String applicationToken,
//			@RequestParam(value = "cig") String cig,
//			@RequestParam(value = "codFase") Long codFase, @RequestParam(value = "num") Long num,
//			@RequestParam(value = "stazioneAppaltante") String stazioneAppaltante,
//			@RequestParam(value = "idAvGara") String idAvGara
//			) {
//
//		
//		logger.info("deleteFase: inizio metodo");
//		
//		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
//		HttpStatus status = HttpStatus.OK;
//		TokenValidationResult tokenValidate = validateToken(applicationToken);
//		if(!tokenValidate.isValidated()){
//			status = HttpStatus.UNAUTHORIZED;
//			risultato = new ResponsePubblicaFase();
//			risultato.setErrorData(tokenValidate.getError());
//			return ResponseEntity.status(status.value()).body(risultato);
//		}
//		try {
//			risultato = this.schedeManager.deleteFase(cig, codFase, num, stazioneAppaltante,idAvGara);
//		} catch (Exception e) {
//			risultato.setEsito(false);
//			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
//		}
//		if (risultato.getErrorData() != null) {
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		logger.info("deleteFase: fine metodo [http status " + status.value() + "]");
//		return ResponseEntity.status(status.value()).body(risultato);
//	}

	
	
}
