package it.appaltiecontratti.tabellati.controller;

import io.swagger.annotations.ApiParam;
import it.appaltiecontratti.tabellati.entity.ResponseListaStazioniAppaltantiOptions;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteSearchForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.tabellati.bl.TabellatiManager;
import it.appaltiecontratti.tabellati.entity.ResponseResult;

/**
 * Servizio REST esposto al path "${public.context-path}/gestioneTabellati".
 */
@Validated
@RestController
@CrossOrigin
@SuppressWarnings("java:S5122")
@EnableTransactionManagement
@RequestMapping(value = "${public.context-path}/gestioneTabellati")
public class TabellatiPublicController {

	/** Logger di classe. */
	private Logger logger = LogManager.getLogger(TabellatiPublicController.class);

	@Autowired
	private TabellatiManager tabellatiManager;

	@RequestMapping(method = RequestMethod.GET, value = "/title")
	@ApiOperation(nickname = "TabellatiController.getApplicationTitle", value = "Ritorna il titolo dell'applicazione letto dalla tabella w_config")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> getApplicationTitle() {

		
		logger.info("getApplicationTitle: inizio metodo");
		
		HttpStatus status = HttpStatus.OK;
		ResponseResult risultato = new ResponseResult();
		risultato = tabellatiManager.getApplicationTitle();
		if (risultato.getErrorData() != null) {
			status = HttpStatus.BAD_REQUEST;
		}
		logger.info("getApplicationTitle: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getStazioniAppaltantiOptions")
	@ApiOperation(nickname = "TabellatiController.getStazioniAppaltantiOptions", value = "Ritorna la lista delle stazioni appaltanti contenenti la string di ricerca nel codice fiscale o ragione sociale")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")})
	public ResponseEntity<ResponseListaStazioniAppaltantiOptions> getStazioniAppaltantiOptions(
			@ApiParam(value = "Campi necessari alla ricerca di una stazione appaltante", required = true) StazioneAppaltanteSearchForm form) {

		logger.info("getStazioniAppaltantiOptions: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseListaStazioniAppaltantiOptions risultato = new ResponseListaStazioniAppaltantiOptions();
		if (form == null || form.getDesc() == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Richiesta invalida, parametri obbligatori non valorizzati");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		risultato = tabellatiManager.getStazioniAppaltantiOptions(form);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getStazioniAppaltantiOptions: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
}
