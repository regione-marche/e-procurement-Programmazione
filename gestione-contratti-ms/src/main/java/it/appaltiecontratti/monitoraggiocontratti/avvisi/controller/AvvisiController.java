package it.appaltiecontratti.monitoraggiocontratti.avvisi.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.bl.AvvisiManager;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisiNonPaginatiSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoBaseForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoPubblicatoForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.DocAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseListaAvvisi;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseListaAvvisiNonPaginata;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseRequestPubblicazioneAvvisi;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.CambioSysconForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.GeneralBaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;

@Validated
@CrossOrigin
@RestController
@EnableTransactionManagement
@RequestMapping(value = "${protected.context-path}/gestioneAvvisi")
@SuppressWarnings("java:S5122")
public class AvvisiController {

	/**
	 * Servizio REST esposto al path "/Avvisi".
	 */

	/** Logger di classe. */
	protected Logger logger = LogManager.getLogger(AvvisiController.class);

	/**
	 * Wrapper della business logic a cui viene demandata la gestione
	 */
	private AvvisiManager avvisiManager;
	
	@Autowired
	private UserManager userManager;

	@Required
	@Autowired
	public void setAvvisiManager(AvvisiManager avvisiManager) {
		this.avvisiManager = avvisiManager;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/InsertAvviso")
	@ApiOperation(nickname = "AvvisiController.insertAvviso", value = "Inserisce un'avviso nel DB", notes = "Ritorna il risultato dell'inserimento e l'id assegnato dal sistema", response = ResponseResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> insertAvviso(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un avviso da inserire", required = true) @RequestBody @Valid AvvisoInsertForm form)

			throws ParseException {

		logger.info("create Avviso: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;

		try {
			risultato = avvisiManager.insert(form);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("create Avviso: fine metodo [http status " + status.value() + "]");
		}
		return ResponseEntity.status(status.value()).body(risultato);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/GetListaAvvisi")
	@ApiOperation(nickname = "AvvisiController.getListaAvvisi", value = "Ritorna la lista di avvisi filtrati nei campi della form", response = ResponseListaAvvisi.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaAvvisi> getListaAvvisi(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un avviso per la ricerca", required = true) AvvisoSearchForm form) {

		logger.info("get lista Avvisi: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseListaAvvisi risultato = new ResponseListaAvvisi();
		risultato = avvisiManager.list(form);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		logger.info("lista Avvisi: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/DeleteAvviso")
	@ApiOperation(nickname = "AvvisiController.deleteAvviso", value = "Cancella un avviso", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> deleteAvviso(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un avviso per la cancellazione", required = true) AvvisoBaseForm form) {

		logger.info("delete Avvisi: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		if (form.getStazioneAppaltante() == null || form.getNumeroAvviso() == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Request invalida, campi obbligatori a null");
			status = HttpStatus.BAD_REQUEST;
		}
		try {
			risultato = avvisiManager.delete(form);
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

	@RequestMapping(method = RequestMethod.GET, value = "/GetAvviso")
	@ApiOperation(nickname = "AvvisiController.getAvviso", value = "Ritorna l'avviso filtrato daii campi della form", response = ResponseListaAvvisi.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })

	public ResponseEntity<ResponseAvvisoEntry> getAvviso(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un avviso per il dettaglio", required = true) AvvisoBaseForm form) {

		logger.info("dettaglio Avviso: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseAvvisoEntry risultato = new ResponseAvvisoEntry();
		if (form.getStazioneAppaltante() == null || form.getNumeroAvviso() == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Request invalida, campi obbligatori a null");
			status = HttpStatus.BAD_REQUEST;
		}
		risultato = avvisiManager.dettaglio(form);

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		logger.info("dettaglio Avviso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);

	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/UpdateAvviso")
	@ApiOperation(nickname = "AvvisiController.updateAvviso", value = "Modifica un avviso nel DB", response = ResponseResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })

	public ResponseEntity<ResponseResult> updateAvviso(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un avviso per la modifica", required = true) @RequestBody @Valid AvvisoUpdateForm form) {

		logger.info("Modifica avviso: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;

		try {
			risultato = avvisiManager.update(form);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		logger.info("Modifica avviso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/SetPubbId")
	@ApiOperation(nickname = "AvvisiController.setPubbId", value = "Modifica un avviso nel DB, inserendo il campo id_pubblicazione", response = ResponseResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> SetPubbId(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String xLoginMode,
			@ApiParam(value = "Campi di un avviso per l'aggiornamento dell'id di pubblicazione") @RequestBody @Valid AvvisoPubblicatoForm form) {

		logger.info("Update id generato avviso: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		if (form.getStazioneAppaltante() == null || form.getNumeroAvviso() == null || form.getIdRicevuto() == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Request invalida, campi obbligatori a null");
			status = HttpStatus.BAD_REQUEST;
		}

		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		
		Long syscon = userAuthClaimsDTO.getSyscon();
	
		
		try {
			risultato = avvisiManager.updateIdRicevuto(form,syscon);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		logger.info("Update id generato avviso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/cambiaSyscon")
	@ApiOperation(nickname = "AvvisiController.cambiaSysconAvviso", value = "Cambia il syscon associato all'avviso", notes = "Ritorna l'esito del cambio di syscon", response = ResponseResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> cambiaSysconAvviso(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi per cambiare syscon", required = true) @RequestBody @Valid CambioSysconForm form)

			throws ParseException {

		logger.info("cambiaSysconAvviso: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;

		risultato = avvisiManager.cambiaSyscon(form);

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("cambiaSysconAvviso: fine metodo [http status " + status.value() + "]");
		}
		return ResponseEntity.status(status.value()).body(risultato);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getRequestPubblicazioneAvviso")
	@ApiOperation(nickname = "AvvisiController.getAvviso", value = "Ritorna l'avviso filtrato daii campi della form", response = ResponseListaAvvisi.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })

	public ResponseEntity<ResponseRequestPubblicazioneAvvisi> getRequestPubblicazioneAvviso(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Id Avviso", required = true) @RequestParam("idAvviso") Long idAvviso,
			@RequestHeader("X-loginMode") String xLoginMode,
			@ApiParam(value = "Codice Stazione appaltrante", required = true) @RequestParam("codiceStazioneAppaltante") String codiceStazioneAppaltante) {

		logger.info("Execution start::getRequestPubblicazioneAvviso");

		HttpStatus status = HttpStatus.OK;
		ResponseRequestPubblicazioneAvvisi risultato = new ResponseRequestPubblicazioneAvvisi();
		if (idAvviso == null || StringUtils.isBlank(codiceStazioneAppaltante)) {
			risultato.setEsito(false);
			risultato.setErrorData("Request non valida, campi obbligatori a null");
			status = HttpStatus.BAD_REQUEST;
		}
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		try {
			risultato = avvisiManager.getRequestPubblicazioneAvviso(idAvviso, codiceStazioneAppaltante,syscon);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (IllegalArgumentException e) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setErrorData(ResponseRequestPubblicazioneAvvisi.SA_NOT_FOUND);
			risultato.setEsito(false);
		}

		logger.info("Execution end::getRequestPubblicazioneAvviso [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);

	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, path = "/downloadDocumentoAvviso", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<GeneralBaseResponse<String>> downloadDocumentoAvviso(
			@RequestHeader("Authorization") String authorization, @RequestParam("idAvviso") final Integer idAvviso,
			@RequestParam("codiceStazioneAppaltante") final String codiceStazioneAppaltante,
			@RequestParam("numDoc") final Integer numDoc) throws IOException {
		logger.debug("Execution start::downloadDocumentoAvviso");

		HttpStatus status = HttpStatus.OK;
		GeneralBaseResponse<String> response = new GeneralBaseResponse<String>();

		try {
			DocAvvisoEntry doc = this.avvisiManager.downloadDocumentoAvviso(idAvviso, codiceStazioneAppaltante, numDoc);
			response.setEsito(true);
			response.setData(doc.getBinary());
		} catch (Exception e) {
			response.setEsito(false);
			response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		logger.debug("Execution end::downloadDocumentoAvviso");

		return ResponseEntity.status(status.value()).body(response);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/health")
	@ApiOperation(nickname = "AvvisiController.health", value = "Monitora lo stato di salute dell'applicazione", response = ResponseResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> health() {

		logger.info("health: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		risultato = avvisiManager.health();
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("health: fine metodo");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaAvvisiNonPaginata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaAvvisiNonPaginata> getListaAvvisiNonPaginata(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "form di ricerca") AvvisiNonPaginatiSearchForm form) {

		logger.info("getListaAvvisiNonPaginata: inizio metodo");

		ResponseListaAvvisiNonPaginata risultato = new ResponseListaAvvisiNonPaginata();
		HttpStatus status = HttpStatus.OK;
		if (form.getStazioneAppaltante() == null || form.getSyscon() == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = this.avvisiManager.getListaAvvisiNonPaginata(form);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getListaAvvisiNonPaginata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFlussoAvviso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFlussoAvviso(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "id dell'avviso", required = true) @RequestParam(value = "idAvviso") Long idAvviso) {

		logger.info("deleteFlussoAvviso: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		
		try {
			risultato = this.avvisiManager.deleteFlussoAvviso(idAvviso);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("deleteFlussoAvviso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
}