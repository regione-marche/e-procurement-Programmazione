package it.appaltiecontratti.monitoraggiocontratti.contratti.controller;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.SchedePcpManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.LoginResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.SimogManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ImportaGaraPublicForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseImportaGara;
import it.appaltiecontratti.sicurezza.bl.UserManager;

@Validated
@CrossOrigin
@RestController
@EnableTransactionManagement
@RequestMapping(value = "${public.context-path}/gestioneContratti")
@SuppressWarnings("java:S5122")
public class ContrattiPublicController {

	protected Logger logger = LogManager.getLogger(ContrattiPublicController.class);

	@Autowired
	private SimogManager simogManager;
	
	@Autowired
	private ContrattiManager contrattiManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private SchedePcpManagerV102 schedePcpManagerV102;

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/importaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> importaGara(@ApiParam(value = "Campi di ricerca da inviare al webservice SIMOG", required = true) @RequestBody ImportaGaraPublicForm form) {

		HttpStatus status = HttpStatus.OK;
		BaseResponse risultato = new BaseResponse();
		logger.info("importaGaraSimog: inizio metodo");
		try {
			LoginResult login = this.simogManager.hasPermission(form.getUsername(), form.getPassword());
			if(login.isEsito() == false) {
				logger.error("Errore inaspettato durante la verifica delle credenziali");
				risultato.setErrorData("Errore inaspettato durante la verifica delle credenziali");
				risultato.setEsito(false);
				return ResponseEntity.status(status.value()).body(risultato);
			}
			
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la verifica delle credenziali", e);
			risultato.setErrorData("Errore inaspettato durante la verifica delle credenziali");
			risultato.setEsito(false);
			return ResponseEntity.status(status.value()).body(risultato);
		}
		
		try {
			String uffintCodein = this.simogManager.getCodeinByCfein(form.getCfSA());
			if(uffintCodein == null) {
				logger.error("La stazione appaltante passata non esiste nella banca dati");
				risultato.setEsito(false);
				risultato.setErrorData("La stazione appaltante è inesistente");
				return ResponseEntity.status(status.value()).body(risultato);
			}
			
			ResponseImportaGara response = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), uffintCodein, form.getSyscon(),
					form.isSendMail(), null, null);
			risultato.setEsito(response.isEsito() && response.getData() != null);
			risultato.setErrorData(response.getErrorData());
			risultato.setErrorDataParameters(response.getErrorDataParameters());
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("importaGaraSimog: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	


}