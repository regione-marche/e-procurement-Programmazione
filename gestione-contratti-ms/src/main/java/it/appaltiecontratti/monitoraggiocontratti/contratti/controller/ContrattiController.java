package it.appaltiecontratti.monitoraggiocontratti.contratti.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ImportAppaltoPcpManager;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v1021.SchedePcpManagerV1021;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.*;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.*;
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

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.DelegheManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.FasiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.SchedePcpManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.AttoDocument;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DocumentoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions.UnauthorizedUserException;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100.BaseResponsePcp;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.SimogManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.AllineaGaraForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.CheckMigrazioneForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.DatiGaraPcpForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ImportaGaraForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ImportaSmartCigInsertForm;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;

@Validated
@CrossOrigin
@RestController
@EnableTransactionManagement
@RequestMapping(value = "${protected.context-path}/gestioneContratti")
@SuppressWarnings("java:S5122")
public class ContrattiController {

	/**
	 * Servizio REST esposto al path "${jwt.context-path}/gestioneContratti".
	 */

	/** Logger di classe. */
	protected Logger logger = LogManager.getLogger(ContrattiController.class);

	/**
	 * Wrapper della business logic a cui viene demandata la gestione
	 */
	@Autowired
	private ContrattiManager contrattiManager;

	@Autowired
	private FasiManager fasiManager;

	@Autowired
	private SimogManager simogManager;
	
	@Autowired
	private DelegheManager delegheManager;
	
	@Autowired
	private SchedePcpManagerV102 schedePcpManagerV102;

	@Autowired
	private SchedePcpManagerV1021 schedePcpManagerV1021;

	@Autowired
	private ImportAppaltoPcpManager importAppaltoPcpManager;

	@Autowired
	ServletContext context;

	@Autowired
	private UserManager userManager;
	
	@Required
	@Autowired
	public void setContrattiManager(ContrattiManager contrattiManager) {
		this.contrattiManager = contrattiManager;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertGara(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi di una gara da inserire") @RequestBody @Valid GaraInsertForm form) {

		logger.info("insertGara: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		risultato = this.contrattiManager.insertGara(form);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("insertGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertSmartCig(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi di una gara (SMARTCIG) da inserire") @RequestBody @Valid SmartCigInsertForm form) {

		logger.info("insertSmartCig: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		try {
			risultato = this.contrattiManager.insertSmartCig(form);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("insertSmartCig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/trasferisciReferenteGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> trasferisciReferenteGara(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestBody @Valid CambioReferenteForm form) {

		logger.info("trasferisciReferenteGara: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		try {
			risultato = this.contrattiManager.trasferisciReferenteGara(form);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("trasferisciReferenteGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	
	
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertPubblicaSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePubblicaSmartCig> insertPubblicaSmartCig(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String xLoginMode,
			@ApiParam(value = "campi di una gara (SMARTCIG) da inserire e pubblicare") @RequestBody @Valid SmartCigInsertForm form) {

		logger.info("insertPubblicaSmartCig: inizio metodo");
		ResponsePubblicaSmartCig risultato = new ResponsePubblicaSmartCig();
		HttpStatus status = HttpStatus.OK;
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		String cf = userAuthClaimsDTO.getCf();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		try {
			risultato = this.contrattiManager.pubblicaSmartCig(authorization,form,syscon,cf);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("insertPubblicaSmartCig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/updatePubblicaSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePubblicaSmartCig> insertPubblicaSmartCig(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String xLoginMode,
			@ApiParam(value = "campi di una gara (SMARTCIG) da inserire e pubblicare") @RequestBody @Valid SmartCigUpdateForm form) {

		logger.info("updatePubblicaSmartCig: inizio metodo");
		ResponsePubblicaSmartCig risultato = new ResponsePubblicaSmartCig();
		HttpStatus status = HttpStatus.OK;
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		String cf = userAuthClaimsDTO.getCf();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		try {
			risultato = this.contrattiManager.updatePubblicaSmartCig(authorization,form,syscon,cf);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("updatePubblicaSmartCig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaGare")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaGare> getListaGare(@RequestHeader("X-loginMode") String xLoginMode,@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "form di ricerca") GareSearchForm form) {

		logger.info("getListaGare: inizio metodo");

		ResponseListaGare risultato = new ResponseListaGare();
		HttpStatus status = HttpStatus.OK;
		if (form.getStazioneAppaltante() == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = this.contrattiManager.searchGare(form,authorization,xLoginMode);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getListaGare: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDettaglioGara> getDettaglioGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara) {

		logger.info("getDettaglioGara: inizio metodo");

		ResponseDettaglioGara risultato = new ResponseDettaglioGara();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {

			risultato = this.contrattiManager.dettaglioGara(codGara,authorization,xLoginMode);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getDettaglioGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/matriceAtti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseMatriceAtti> matriceAtti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara) {

		logger.info("matriceAtti: inizio metodo");

		ResponseMatriceAtti risultato = new ResponseMatriceAtti();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {

			risultato = this.contrattiManager.matriceAtti(codGara);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("matriceAtti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDettaglioSmartCig> getDettaglioSmartCig(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara) {

		logger.info("getDettaglioSmartCig: inizio metodo");

		ResponseDettaglioSmartCig risultato = new ResponseDettaglioSmartCig();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		String cf = userAuthClaimsDTO.getCf();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if (permission) {

			risultato = this.contrattiManager.dettaglioSmartCig(codGara,syscon, cf);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getDettaglioSmartCig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/isGaraSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseGaraSmartCig> isGaraSmartCig(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara) {

		logger.info("isGaraSmartCig: inizio metodo");

		ResponseGaraSmartCig risultato = new ResponseGaraSmartCig();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {

			risultato = this.contrattiManager.isGaraSmartCig(codGara);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("isGaraSmartCig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi della gara da modificare", required = true) @RequestBody @Valid GaraUpdateForm form) {

		logger.info("updateGara: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.updateGara(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("updateGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateSmartCig(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi della gara da modificare", required = true) @RequestBody @Valid SmartCigUpdateForm form) {

		logger.info("updateSmartCig: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.updateSmartCig(form);
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
		logger.info("updateGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "id della gara", required = true) @RequestParam(value = "codGara") Long codGara) {

		logger.info("deleteGara: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.deleteGara(codGara);
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
		logger.info("updateGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteSmartcig(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "codice della gara", required = true) @RequestParam(value = "codGara") Long codGara) {

		logger.info("deleteSmartcig: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.deleteSmartcig(codGara);
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
		logger.info("deleteSmartcig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaLotti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaLotti> getListaLotti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi per la ricerca") LottoSearchForm searchForm) {

		logger.info("getListaLotti: inizio metodo");

		ResponseListaLotti risultato = new ResponseListaLotti();
		HttpStatus status = HttpStatus.OK;
		if (searchForm.getCodGara() == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(searchForm.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getListaLotti(searchForm);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("getListaLotti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDettaglioLotto> getDettaglioLotto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "numLotto") Long numLotto) {

		logger.info("getDettaglioLotto: inizio metodo");

		ResponseDettaglioLotto risultato = new ResponseDettaglioLotto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numLotto == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.dettaglioLotto(codGara, numLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("getDettaglioLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizInsertLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizInsertLotto> getInizInsertLotto(
			@RequestHeader("Authorization") String authorization) {

		logger.info("getInizInsertLotto: inizio metodo");

		ResponseInizInsertLotto risultato = new ResponseInizInsertLotto();
		HttpStatus status = HttpStatus.OK;
		risultato = this.contrattiManager.getInizInsertLotto();
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("ResponseInizInsertLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertLotto(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi di un lotto da inserire") @RequestBody @Valid LottoInsertForm form) {

		logger.info("insertLotto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		try {
			risultato = this.contrattiManager.insertLotto(form);
		} catch (Exception e) {
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("insertLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteLotto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "codice della gara", required = true) @RequestParam(value = "codGara") Long codGara,
			@ApiParam(value = "codice del lotto", required = true) @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("deleteLotto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.deleteLotto(codGara, codLotto);
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
		logger.info("deleteLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateLotto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un lotto da modificare", required = true) @RequestBody() @Valid LottoUpdateForm form) {

		logger.info("updateLotto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.updateLotto(form);
			} catch (Exception e) {
				risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				risultato.setEsito(false);
			}
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("updateLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getAttiAssociatiLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseAttiAssociatiLotto> getAttiAssociatiLotto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getAttiAssociatiLotto: inizio metodo");

		ResponseAttiAssociatiLotto risultato = new ResponseAttiAssociatiLotto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getAttiAssociatiLotto(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getAttiAssociatiLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaAtti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseAtti> getListaAtti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara) {

		logger.info("getListaAtti: inizio metodo");

		ResponseAtti risultato = new ResponseAtti();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getListaAtti(codGara);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getListaAtti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDettaglioAtto> getDettaglioAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "tipoDocumento") Long tipoDocumento, @RequestParam(value = "numPubb") Long numPubb) {

		logger.info("getDettaglioAtto: inizio metodo");

		ResponseDettaglioAtto risultato = new ResponseDettaglioAtto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || tipoDocumento == null || numPubb == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.dettaglioAtto(codGara, tipoDocumento, numPubb);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getDettaglioAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un atto da inserire", required = true) @RequestBody() @Valid AttoInsertForm form) {

		logger.info("insertAtto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.insertAtto(form, authorization, xLoginMode);
			} catch (Exception e) {
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
		logger.info("insertAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un atto da modificare", required = true) @RequestBody() @Valid AttoUpdateForm form) {

		logger.info("updateAtto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.updateAtto(form, authorization, xLoginMode);
			} catch (Exception e) {
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
		logger.info("updateAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "numPubb") Long numPubb) {

		logger.info("deleteAtto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numPubb == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.deleteAtto(codGara, numPubb);
			} catch (Exception e) {
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
		logger.info("deleteAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getLottiAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseAttiLotto> getLottiAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "numPubb") Long numPubb) {

		logger.info("getLottiAtto: inizio metodo");

		ResponseAttiLotto risultato = new ResponseAttiLotto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numPubb == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getAttoLotti(codGara, numPubb);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getLottiAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/associaLottiAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> associaLottiAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "numPubb") Long numPubb, @RequestParam(value = "idLotti") List<Long> codsLotto) {

		logger.info("associaLottiAtto: inizio metodo");

		BaseResponse risultato = new ResponseAttiLotto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codsLotto == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.associaAttoLotti(codGara, numPubb, codsLotto);
			} catch (Exception e) {
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
		logger.info("associaLottiAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFasiLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFasiLotto> getFasiLotto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "numAppa", required = false) Long numAppa) {

		logger.info("getFasiLotto: inizio metodo");

		ResponseFasiLotto risultato = new ResponseFasiLotto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.getFasiLotto(codGara, codLotto, numAppa);

			} catch (Exception e) {
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
		logger.info("getFasiLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getPubblicitaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePubblicitaGara> getPubblicitaGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara) {

		logger.info("getPubblicitaGara: inizio metodo");

		ResponsePubblicitaGara risultato = new ResponsePubblicitaGara();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getPubblicitaGara(codGara);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getPubblicitaGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertPubblicitaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> insertPubblicitaGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una pubblicità di gara da inserire", required = true) @RequestBody @Valid PubblicitaGaraInsertForm form) {

		logger.info("insertPubblicitaGara: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodiceGara(), authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.insertPubblicitaGara(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("insertPubblicitaGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/listaFasiVisibili")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFasiAbilitate> listaFasiVisibili(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "numLotto") Long numLotto) {

		logger.info("listaFasiVisibili: inizio metodo");

		ResponseFasiAbilitate risultato = new ResponseFasiAbilitate();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numLotto == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.listaFasiAbilitate(codGara, numLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("listaFasiVisibili: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseAggiudicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseAggiudicazione> getFaseAggiudicazione(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num", required = false) Long num) {

		logger.info("getFaseAggiudicazione: inizio metodo");

		ResponseFaseAggiudicazione risultato = new ResponseFaseAggiudicazione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseAggiudicazione(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseAggiudicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseAggiudicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseAggiudicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody @Valid FaseAggInsertForm form) {

		logger.info("insertFaseAggiudicazione: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		if (form.getCodGara() == null || form.getCodLotto() == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseAggiudicazione(form);
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
		logger.info("insertFaseAggiudicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseAggiudicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseAggiudicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody @Valid FaseAggInsertForm form) {

		logger.info("updateFaseAggiudicazione: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseAggiudicazione(form);
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
		logger.info("updateFaseAggiudicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseAggiudicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseAggiudicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseAggiudicazione: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseAggiudicazione(codGara, codLotto, num);
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
		logger.info("deleteFaseAggiudicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseAggiudicazioneSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseAggiudicazioneSemp> getFaseAggiudicazioneSemp(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "numAppa", required = false) Long numAppa) {

		logger.info("getFaseAggiudicazioneSemp: inizio metodo");

		ResponseFaseAggiudicazioneSemp risultato = new ResponseFaseAggiudicazioneSemp();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseAggiudicazioneSemp(codGara, codLotto, numAppa);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseAggiudicazioneSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseAggiudicazioneSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseAggiudicazioneSemp(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody() @Valid FaseAggSempInsertForm form) {

		logger.info("insertFaseAggiudicazioneSemp: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseAggiudicazioneSemp(form);
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
		logger.info("insertFaseAggiudicazioneSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseAggiudicazioneSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseAggiudicazioneSemp(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody() @Valid FaseAggSempInsertForm form) {

		logger.info("updateFaseAggiudicazioneSemp: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {

			try {
				risultato = this.fasiManager.updateFaseAggiudicazioneSemp(form);
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
		logger.info("updateFaseAggiudicazioneSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseAggiudicazioneSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseAggiudicazioneSemp(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseAggiudicazioneSemp: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {

			try {
				risultato = this.fasiManager.deleteFaseAggiudicazioneSemp(codGara, codLotto, num);
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
		logger.info("deleteFaseAggiudicazioneSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getImpreseAggiudicatarie")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseImprAggiudicatarie> getImpreseAggiudicatarie(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "numAppa") Long numAppa) {

		logger.info("getImpreseAggiudicatarie: inizio metodo");

		ResponseImprAggiudicatarie risultato = new ResponseImprAggiudicatarie();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getImpreseAggiudicatarie(codGara, codLotto, numAppa);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getImpreseAggiudicatarie: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getImpreseAggiudicatarieAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseImprAggiudicatarieAtto> getImpreseAggiudicatarieAtto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "numPubb") Long numPubb) {

		logger.info("getImpreseAggiudicatarieAtto: inizio metodo");

		ResponseImprAggiudicatarieAtto risultato = new ResponseImprAggiudicatarieAtto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numPubb == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getImpreseAggiudicatarieAtto(codGara, numPubb);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getImpreseAggiudicatarieAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertImpresaAggiudicatariaAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertImpresaAggiudicatariaAtto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una imp aggiudicataria da inserire", required = true) @RequestBody() @Valid ImpreseAggiudicatarieAttoInsertForm form) {

		logger.info("insertImpresaAggiudicataria: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.insertImpresaAggiudicatariaAtto(form);
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
		logger.info("insertImpresaAggiudicataria: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertImpresaAggiudicataria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertImpresaAggiudicataria(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una imp aggiudicataria da inserire", required = true) @RequestBody() @Valid ImpreseAggiudicatarieInsertForm form) {

		logger.info("insertImpresaAggiudicataria: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertImpresaAggiudicataria(form);
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
		logger.info("insertImpresaAggiudicataria: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/importImpresaAggiudicataria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> importImpresaAggiudicataria(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una imp aggiudicataria da inserire", required = true) @RequestBody() @Valid ImportImpresaAgiudicatariaForm form) {

		logger.info("importImpresaAggiudicataria: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.importImpresaAggiudicataria(form);
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
		logger.info("insertImpresaAggiudicataria: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteImpresaAggiudicataria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteImpresaAggiudicataria(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numAppa") Long numAppa,
			@RequestParam(value = "numAggi") Long numAggi) {

		logger.info("deleteImpresaAggiudicataria: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteImpresaAggiudicataria(codGara, codLotto, numAppa, numAggi);
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
		logger.info("deleteImpresaAggiudicataria: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteImpresaAggiudicatariaAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteImpresaAggiudicatariaAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "numPubb") Long numPubb) {

		logger.info("deleteImpresaAggiudicatariaAtto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numPubb == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteImpresaAggiudicatariaAtto(codGara, numPubb);
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
		logger.info("deleteImpresaAggiudicataria: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateImpresaAggiudicataria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateImpresaAggiudicataria(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una imp aggiudicataria da modificare", required = true) @RequestBody() @Valid ImpreseAggiudicatarieUpdateForm form) {

		logger.info("updateImpresaAggiudicataria: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateImpresaAggiudicataria(form);
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
		logger.info("updateImpresaAggiudicataria: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateImpresaAggiudicatariaAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateImpresaAggiudicatariaAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una imp aggiudicataria da modificare", required = true) @RequestBody() @Valid ImpreseAggiudicatarieAttoInsertForm form) {

		logger.info("updateImpresaAggiudicataria: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.updateImpresaAggiudicatariaAtto(form);
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
		logger.info("updateImpresaAggiudicatariaAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseIncarichiProfessionali> getIncarichiProfessionali(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num, @RequestParam(value = "codFase") Long codFase) {

		logger.info("getIncarichiProfessionali: inizio metodo");

		ResponseIncarichiProfessionali risultato = new ResponseIncarichiProfessionali();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null || codFase == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getIncarichiProfessionali(codGara, codLotto, num, codFase);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateIncarichiProfessionali(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una incarico professionale da modificare", required = true) @RequestBody() @Valid IncarichiProfessionaliInsertForm form) {

		logger.info("updateIncarichiProfessionali: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.updateIncarichiProfessionali(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("updateIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteIncarichiProfessionali(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numAppa") Long numAppa,
			@RequestParam(value = "numInca") Long numInca) {

		logger.info("deleteIncarichiProfessionali: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || numAppa == null || numInca == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteIncaricoProfessionale(codGara, codLotto, numAppa, numInca);
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
		logger.info("deleteIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseIncarichiProfessionali> getFaseIncarichiProfessionali(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseIncarichiProfessionali: inizio metodo");

		ResponseFaseIncarichiProfessionali risultato = new ResponseFaseIncarichiProfessionali();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore in validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseIncarichiProfessionali(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> insertFaseIncarichiProfessionali(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una incarico professionale da modificare", required = true) @RequestBody() @Valid IncarichiProfessionaliInsertForm form) {

		logger.info("insertFaseIncarichiProfessionali: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.insertFaseIncarichiProfessionali(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("insertFaseIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseIncarichiProfessionali(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una incarico professionale da modificare", required = true) @RequestBody() @Valid IncarichiProfessionaliInsertForm form) {

		logger.info("updateFaseIncarichiProfessionali: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.updateFaseIncarichiProfessionali(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("updateFaseIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseIncarichiProfessionali")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseIncarichiProfessionali(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseIncarichiProfessionali: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseIncaricoProfessionale(codGara, codLotto, num);
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
		logger.info("deleteFaseIncarichiProfessionali: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseVariazioneAggiudicatari")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getInizFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDto> getInizFaseVariazioneAggiudicatari(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseVariazioneAggiudicatari: inizio metodo");

		ResponseDto risultato = new ResponseDto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseVariazioneAggiudicatari(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseVariazioneAggiudicatari: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseVariazioneAggiudicatari")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseVariazioneAggiudicatari> getFaseVariazioneAggiudicatari(@RequestHeader("X-loginMode") String xLoginMode,
																@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
																@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseVariazioneAggiudicatari: inizio metodo");

		ResponseFaseVariazioneAggiudicatari risultato = new ResponseFaseVariazioneAggiudicatari();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseVariazioneAggiudicatari(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseVariazioneAggiudicatari: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseVariazioneAggiudicatari")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseVariazioneAggiudicatari(@RequestHeader("X-loginMode") String xLoginMode,
															 @RequestHeader("Authorization") String authorization,
															 @ApiParam(value = "Campi di una fase conclusione collaudo da inserire", required = true) @RequestBody() @Valid FaseVariazioneAggiudicatariInsertForm form) {

		logger.info("insertFaseVariazioneAggiudicatari: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseVariazioneAggiudicatari(form);
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
		logger.info("insertFaseVariazioneAggiudicatari: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseVariazioneAggiudicatari")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseVariazioneAggiudicatari(@RequestHeader("X-loginMode") String xLoginMode,
														   @RequestHeader("Authorization") String authorization,
														   @ApiParam(value = "Campi di una fase collaudo da inserire", required = true) @RequestBody() @Valid FaseVariazioneAggiudicatariInsertForm form) {

		logger.info("updateFaseVariazioneAggiudicatari: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseVariazioneAggiudicatari(form);
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
		logger.info("updateFaseConclusioneSempContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseVariazioneAggiudicatari")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseVariazioneAggiudicatari(@RequestHeader("X-loginMode") String xLoginMode,
														   @RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
														   @RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseVariazioneAggiudicatari: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.deleteFaseVariazioneAggiudicatari(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("deleteFaseVariazioneAggiudicatari: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFinanziamenti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFinanziamenti> getFinanziamenti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numAppa") Long numAppa) {

		logger.info("getFinanziamenti: inizio metodo");

		ResponseFinanziamenti risultato = new ResponseFinanziamenti();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || numAppa == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFinanziamenti(codGara, codLotto, numAppa);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFinanziamenti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFinanziamento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFinanziamento(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numAppa") Long numAppa,
			@RequestParam(value = "numFina") Long numFina) {

		logger.info("deleteFinanziamento: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || numAppa == null || numFina == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFinanziamento(codGara, codLotto, numAppa, numFina);
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
		logger.info("deleteFinanziamento: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFinanziamenti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFinanziamenti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi dei finanziamenti da modificare", required = true) @RequestBody() @Valid FinanziamentiInsertForm form) {

		logger.info("updateFinanziamenti: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFinanziamenti(form);
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
		logger.info("updateFinanziamenti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseImprese")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseImprese(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase imprese da inserire", required = true) @RequestBody() @Valid FaseImpresaInsertForm form) {

		logger.info("insertFaseImprese: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				ResponseGaraSmartCig isSmartCig = this.contrattiManager.isGaraSmartCig(form.getCodGara());
				risultato = this.fasiManager.insertFaseImpresa(form, isSmartCig.isData());
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
		logger.info("insertFaseImprese: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseImprese")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseImprese> getFaseImprese(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "numAppa", required = false) Long numAppa) {

		logger.info("getFaseImprese: inizio metodo");

		ResponseFaseImprese risultato = new ResponseFaseImprese();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseImprese(codGara, codLotto, numAppa);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseImprese: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/dettaglioFaseImprese")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseImprese> dettaglioFaseImprese(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "numRagg", required = false) Long numRagg) {

		logger.info("dettaglioFaseImprese: inizio metodo");

		ResponseFaseImprese risultato = new ResponseFaseImprese();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.dettaglioFaseImprese(codGara, codLotto, num, numRagg);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("dettaglioFaseImprese: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseImprese")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseImprese(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "numRagg", required = false) Long numRagg,
			@RequestParam(value = "updateDaexport", required = false) Boolean updateDaexport) {

		logger.info("deleteFaseImprese: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {

			try {
				ResponseGaraSmartCig isSmartCig = this.contrattiManager.isGaraSmartCig(codGara);
				risultato = this.fasiManager.deleteFaseImpresa(codGara, codLotto, num, numRagg, false,
						isSmartCig.isData(), updateDaexport);
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
		logger.info("deleteFaseImprese: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseImprese")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> updateFaseImprese(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody() @Valid FaseImpresaInsertForm form) {

		logger.info("updateFaseImprese: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		if (form.getCodGara() == null || form.getCodLotto() == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				ResponseGaraSmartCig isSmartCig = this.contrattiManager.isGaraSmartCig(form.getCodGara());
				risultato = this.fasiManager.updateFaseImpresa(form, isSmartCig.isData());
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
		logger.info("updateFaseImprese: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseComunicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseComunicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase comunicazione da inserire", required = true) @RequestBody() @Valid FaseComEsitoForm form) {

		logger.info("insertFaseComunicazione: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseComunicazione(form);
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
		logger.info("insertFaseComunicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseComunicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseComunicazione> getFaseComunicazione(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "numAppa", required = false) Long numAppa) {

		logger.info("getFaseComunicazione: inizio metodo");

		ResponseFaseComunicazione risultato = new ResponseFaseComunicazione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseComunicazione(codGara, codLotto, numAppa);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseComunicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseComunicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto) {

		logger.info("deleteFaseComunicazione: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseComunicazione(codGara, codLotto);
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
		logger.info("deleteFaseComunicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseComunicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseComunicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody() @Valid FaseComEsitoForm form) {

		logger.info("updateFaseComunicazione: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseComunicazione(form);
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
		logger.info("updateFaseComunicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseIniziale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseIniziale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase iniziale da inserire", required = true) @RequestBody() @Valid FaseInizialeEsecuzioneInsertForm form) {

		logger.info("insertFaseIniziale: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInizialeEsecuzione(form);
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
		logger.info("insertFaseIniziale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseIniziale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInizialeEsecuzione> getFaseIniziale(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseIniziale: inizio metodo");

		ResponseFaseInizialeEsecuzione risultato = new ResponseFaseInizialeEsecuzione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInizialeEsecuzione(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseIniziale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseIniziale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseIniziale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseIniziale: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInizialeEsecuzione(codGara, codLotto, num);
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
		logger.info("deleteFaseIniziale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseIniziale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseIniziale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase iniziale da modificare", required = true) @RequestBody() @Valid FaseInizialeEsecuzioneInsertForm form) {

		logger.info("updateFaseIniziale: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInizialeEsecuzione(form);
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
		logger.info("updateFaseIniziale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseInizialeSottoscrContr")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseInizialeSottoscrContr(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase iniziale da inserire", required = true) @RequestBody() @Valid FaseInizialeEsecuzioneInsertForm form) {

		logger.info("insertFaseInizialeSottoscrContr: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInizialeSottoscrizioneContratto(form);
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
		logger.info("insertFaseIniziale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInizialeSottoscrContr")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInizialeEsecuzione> getFaseInizialeSottoscrContr(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseIniziale: inizio metodo");

		ResponseFaseInizialeEsecuzione risultato = new ResponseFaseInizialeEsecuzione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInizialeSottoscrizioneContratto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInizialeSottoscrContr: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseInizialeSottoscrContr")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseInizialeSottoscrContr(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseInizialeSottoscrContr: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInizialeSottoscrizioneContratto(codGara, codLotto, num);
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
		logger.info("deleteFaseInizialeSottoscrContr: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseInizialeSottoscrContr")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseInizialeSottoscrContr(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase iniziale da modificare", required = true) @RequestBody() @Valid FaseInizialeEsecuzioneInsertForm form) {

		logger.info("updateFaseInizialeSottoscrContr: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInizialeSottoscrizioneContratto(form);
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
		logger.info("updateFaseInizialeSottoscrContr: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseAdesioneAccordoQuadro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseAdesioneAccordoQuadro> getFaseAdesioneAccordoQuadro(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseAdesioneAccordoQuadro: inizio metodo");

		ResponseFaseAdesioneAccordoQuadro risultato = new ResponseFaseAdesioneAccordoQuadro();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseAdesioneAccordo(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseAdesioneAccordoQuadro: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseAdesioneAccordoQuadro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseAggiudicazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase aggiudicazione da inserire", required = true) @RequestBody() @Valid FaseAdesioneAccordoQuadroInsertForm form) {

		logger.info("insertFaseAdesioneAccordoQuadro: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseAdesioneAccordo(form);
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
		logger.info("insertFaseAdesioneAccordoQuadro: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseAdesioneAccordoQuadro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseAdesioneAccordoQuadro(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase adesione accordo quadro da inserire", required = true) @RequestBody() @Valid FaseAdesioneAccordoQuadroInsertForm form) {

		logger.info("updateFaseAdesioneAccordoQuadro: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {

			try {
				risultato = this.fasiManager.updateFaseAdesioneAccordoQuadro(form);
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
		logger.info("updateFaseAdesioneAccordoQuadro: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseAdesioneAccordoQuadro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseAdesioneAccordoQuadro(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseAdesioneAccordoQuadro: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {

			try {
				risultato = this.fasiManager.deleteFaseAdesioneAccordo(codGara, codLotto, num);
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
		logger.info("deleteFaseAdesioneAccordoQuadro: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseConclusioneContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseConclusioneContratto> getFaseConclusioneContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseConclusioneContratto: inizio metodo");

		ResponseFaseConclusioneContratto risultato = new ResponseFaseConclusioneContratto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseConclusioneContratto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseConclusioneContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseConclusioneContratto> getInizFaseConclusioneContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseConclusioneContratto: inizio metodo");

		ResponseInizFaseConclusioneContratto risultato = new ResponseInizFaseConclusioneContratto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseConclusioneContratto(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseAdesioneAccordoQuadro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseAdesioneAccordoQuadro> getInizFaseAdesioneAccordo(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseAdesioneAccordo: inizio metodo");

		ResponseInizFaseAdesioneAccordoQuadro risultato = new ResponseInizFaseAdesioneAccordoQuadro();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseAdesioneAccordoQuadro(codGara, codLotto);
			if (!risultato.isEsito() && risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseAdesioneAccordo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseInizialeContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseInizialeContratto> getInizFaseInizialeContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseInizialeContratto: inizio metodo");

		ResponseInizFaseInizialeContratto risultato = new ResponseInizFaseInizialeContratto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseInizialeContratto(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseConclusioneContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseConclusioneContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione contratto da inserire", required = true) @RequestBody() @Valid FaseConclusioneInsertForm form) {

		logger.info("insertFaseConclusioneContratto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseConclusioneContratto(form);
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
		logger.info("insertFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseConclusioneContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseConclusioneContratto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione contratto quadro da inserire", required = true) @RequestBody() @Valid FaseConclusioneInsertForm form) {

		logger.info("updateFaseConclusioneContratto: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {

			try {
				risultato = this.fasiManager.updateFaseConclusioneContratto(form);
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
		logger.info("updateFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseConclusioneContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseConclusioneContratto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseConclusioneContratto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseConclusioneContratto(codGara, codLotto, num);
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
		logger.info("deleteFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseConclusioneAffidamentiDiretti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseConclusioneAffidamentiDiretti(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione contratto da inserire", required = true) @RequestBody() @Valid FaseConclusioneAffidamentiDirettiInsertForm form) {

		logger.info("insertFaseConclusioneAffidamentiDiretti: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseConclusioneAffidamentiDiretti(form);
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
		logger.info("insertFaseConclusioneAffidamentiDiretti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseConclusioneAffidamentiDiretti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseConclusioneAffidamentiDiretti> getFaseConclusioneAffidamentiDiretti(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseConclusioneAffidamentiDiretti: inizio metodo");

		ResponseFaseConclusioneAffidamentiDiretti risultato = new ResponseFaseConclusioneAffidamentiDiretti();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseConclusioneAffidamentiDiretti(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseConclusioneAffidamentiDiretti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseConclusioneAffidamentiDiretti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseConclusioneAffidamentiDiretti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione contratto quadro da inserire", required = true) @RequestBody() @Valid FaseConclusioneAffidamentiDirettiInsertForm form) {

		logger.info("updateFaseConclusioneAffidamentiDiretti: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {

			try {
				risultato = this.fasiManager.updateFaseConclusioneAffidamentiDiretti(form);
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
		logger.info("updateFaseConclusioneAffidamentiDiretti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseConclusioneAffidamentiDiretti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseConclusioneAffidamentiDiretti(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseConclusioneAffidamentiDiretti: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseConclusioneAffidamentiDiretti(codGara, codLotto, num);
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
		logger.info("deleteFaseConclusioneAffidamentiDiretti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseConclusioneSempContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseConclusioneSempContratto> getFaseConclusioneSempContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseConclusioneSempContratto: inizio metodo");

		ResponseFaseConclusioneSempContratto risultato = new ResponseFaseConclusioneSempContratto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseConclusioneSempContratto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseConclusioneSempContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseConclusioneSempContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseConclusioneSempContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione contratto da inserire", required = true) @RequestBody() @Valid FaseConclusioneSempInsertForm form) {

		logger.info("insertFaseConclusioneSempContratto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseConclusioneSempContratto(form);
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
		logger.info("insertFaseConclusioneSempContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseConclusioneSempContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseConclusioneContratto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione contratto quadro da inserire", required = true) @RequestBody() @Valid FaseConclusioneSempInsertForm form) {

		logger.info("updateFaseConclusioneSempContratto: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseConclusioneSempContratto(form);
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
		logger.info("updateFaseConclusioneSempContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseConclusioneSempContratto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseConclusioneSempContratto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("deleteFaseConclusioneSempContratto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.deleteFaseConclusioneSempContratto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("deleteFaseConclusioneSempContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseCollaudo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseCollaudo> getFaseCollaudo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseCollaudo: inizio metodo");

		ResponseFaseCollaudo risultato = new ResponseFaseCollaudo();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseCollaudo(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseCollaudo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseCollaudo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseCollaudo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase conclusione collaudo da inserire", required = true) @RequestBody() @Valid FaseCollaudoInsertForm form) {

		logger.info("insertFaseCollaudo: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseCollaudo(form);
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
		logger.info("insertFaseCollaudo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseCollaudo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseCollaudo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase collaudo da inserire", required = true) @RequestBody() @Valid FaseCollaudoInsertForm form) {

		logger.info("updateFaseCollaudo: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseCollaudo(form);
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
		logger.info("updateFaseConclusioneSempContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseCollaudo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseCollaudo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseCollaudo: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.deleteFaseCollaudo(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("deleteFaseCollaudo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInizialeEsecuzione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInizialeEsecuzione> getFaseInizialeEsecuzione(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseInizialeEsecuzione: inizio metodo");

		ResponseFaseInizialeEsecuzione risultato = new ResponseFaseInizialeEsecuzione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInizialeEsecuzione(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInizialeEsecuzione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getSchedaSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseSchedaSicurezza> getSchedaSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numIniz") Long numIniz) {

		logger.info("getSchedaSicurezza: inizio metodo");

		ResponseSchedaSicurezza risultato = new ResponseSchedaSicurezza();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getSchedaSicurezza(codGara, codLotto, numIniz);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getSchedaSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertSchedaSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertSchedaSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una scheda sicurezza da inserire", required = true) @RequestBody() @Valid SchedaSicurezzaInsertForm form) {

		logger.info("insertSchedaSicurezza: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertSchedaSicurezza(form);
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
		logger.info("insertSchedaSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteSchedaSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteSchedaSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numIniz") Long numIniz) {

		logger.info("deleteSchedaSicurezza: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteSchedaSicurezza(codGara, codLotto, numIniz);
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
		logger.info("deleteSchedaSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateSchedaSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateSchedaSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una scheda sicurezza da modificare", required = true) @RequestBody() @Valid SchedaSicurezzaInsertForm form) {

		logger.info("updateSchedaSicurezza: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateSchedaSicurezza(form);
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
		logger.info("updateSchedaSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getMisureAggiuntiveSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseMisureAggiuntiveSicurezza> getMisureAggiuntiveSicurezza(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "codFase") Long codFase,
			@RequestParam(value = "numIniz") Long numeroProgressivoFase) {

		logger.info("getMisureAggiuntiveSicurezza: inizio metodo");

		ResponseMisureAggiuntiveSicurezza risultato = new ResponseMisureAggiuntiveSicurezza();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || codFase == null || numeroProgressivoFase == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getMisureAggiuntiveSicurezza(codGara, codLotto, codFase,
					numeroProgressivoFase);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getMisureAggiuntiveSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertMisureAggiuntiveSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertMisureAggiuntiveSicurezza(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una scheda sicurezza da inserire", required = true) @RequestBody() @Valid MisureAggiuntivesicurezzaInsertForm form) {

		logger.info("insertMisureAggiuntiveSicurezza: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertMisureAggiuntiveSicurezza(form);
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
		logger.info("insertMisureAggiuntiveSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteMisureAggiuntiveSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteMisureAggiuntiveSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "codFase") Long codFase,
			@RequestParam(value = "numIniz") Long num) {

		logger.info("deleteMisureAggiuntiveSicurezza: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || codFase == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteMisureAggiuntiveSicurezza(codGara, codLotto, codFase, num);
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
		logger.info("deleteMisureAggiuntiveSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateMisureAggiuntiveSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateSchedaSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una scheda misure aggiuntive sicurezza da modificare", required = true) @RequestBody() @Valid MisureAggiuntivesicurezzaUpdateForm form) {

		logger.info("updateMisureAggiuntiveSicurezza: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateSchedaMisureaggiuntiveSicurezza(form);
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
		logger.info("updateMisureAggiuntiveSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseAvanzamento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseAvanzamento> getFaseAvanzamento(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseAvanzamento: inizio metodo");

		ResponseFaseAvanzamento risultato = new ResponseFaseAvanzamento();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseAvanzamento(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseAvanzamento: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getMaxNumAvan")
	@ApiResponses(value =
		{
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
		}
	)
	public ResponseEntity<ResponseMaxNumAvan> getMaxNumAvan(
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto
	) {

		logger.info("getMaxNumAvan: inizio metodo");

		ResponseMaxNumAvan risultato = new ResponseMaxNumAvan();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getMaxNumAvan(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getMaxNumAvan: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseAvanzamento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseAvanzamento(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase avanzamento da inserire", required = true) @RequestBody() @Valid FaseAvanzamentoInsertForm form) {

		logger.info("insertFaseAvanzamento: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseAvanzamento(form);
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
		logger.info("insertFaseCollaudo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseAvanzamento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseAvanzamento(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase avanzamento da inserire", required = true) @RequestBody() @Valid FaseAvanzamentoInsertForm form) {

		logger.info("updateFaseAvanzamento: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseAvanzamento(form);
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
		logger.info("updateFaseAvanzamento: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseAvanzamento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseAvanzamento(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseAvanzamento: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseAvanzamento(codGara, codLotto, num);
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
		logger.info("deleteFaseAvanzamento: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseAvanzamentoSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseAvanzamentoSemp> getFaseAvanzamentoSemp(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseAvanzamentoSemp: inizio metodo");

		ResponseFaseAvanzamentoSemp risultato = new ResponseFaseAvanzamentoSemp();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseAvanzamentoSemp(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseAvanzamentoSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseAvanzamentoSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseAvanzamentoSemp(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase avanzamento da inserire", required = true) @RequestBody() @Valid FaseAvanzamentoSempInsertForm form) {

		logger.info("insertFaseAvanzamentoSemp: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseAvanzamentoSemp(form);
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
		logger.info("insertFaseAvanzamentoSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseAvanzamentoSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseAvanzamentoSemp(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase avanzamento semplice da inserire", required = true) @RequestBody() @Valid FaseAvanzamentoSempInsertForm form) {

		logger.info("updateFaseAvanzamentoSemp: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseAvanzamentoSemp(form);
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
		logger.info("updateFaseAvanzamentoSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseAvanzamentoSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseAvanzamentoSemp(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseAvanzamento: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseAvanzamentoSemp(codGara, codLotto, num);
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
		logger.info("deleteFaseAvanzamentoSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseSospensione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSospensione> getFaseSospensione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseSospensione: inizio metodo");

		ResponseFaseSospensione risultato = new ResponseFaseSospensione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseSospensione(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseSospensione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseSospensione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseSospensione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase sospensione da inserire", required = true) @RequestBody() @Valid FaseSospensioneInsertForm form) {

		logger.info("insertFaseSospensione: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseSospensione(form);
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
		logger.info("insertFaseSospensione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseSospensione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseSospensione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase sospensione da inserire", required = true) @RequestBody() @Valid FaseSospensioneInsertForm form) {

		logger.info("updateFaseSospensione: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseSospensione(form);
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
		logger.info("updateFaseSospensione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseSospensione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseSospensione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseSospensione: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseSospensione(codGara, codLotto, num);
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
		logger.info("deleteFaseSospensione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseRipresaPrestazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseRipresaPrestazione> getInizFaseRipresaPrestazione(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseRipresaPrestazione: inizio metodo");

		ResponseInizFaseRipresaPrestazione risultato = new ResponseInizFaseRipresaPrestazione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseRirpesaPrestazione(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseRipresaPrestazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseRipresaPrestazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSospensione> getFaseRipresaPrestazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseRipresaPrestazione: inizio metodo");

		ResponseFaseSospensione risultato = new ResponseFaseSospensione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseRipresaPrestazione(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseRipresaPrestazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseRipresaPrestazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseRipresaPrestazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase sospensione da inserire", required = true) @RequestBody() @Valid FaseRipresaPrestazioneInsertForm form) {

		logger.info("insertFaseRipresaPrestazione: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseRipresaPrestazione(form);
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
		logger.info("insertFaseRipresaPrestazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseRipresaPrestazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseRipresaPrestazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase sospensione da inserire", required = true) @RequestBody() @Valid FaseRipresaPrestazioneInsertForm form) {

		logger.info("updateFaseRipresaPrestazione: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseRipresaPrestazione(form);
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
		logger.info("updateFaseRipresaPrestazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseRipresaPrestazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseRipresaPrestazione(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseRipresaPrestazione: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseRipresaPrestazione(codGara, codLotto, num);
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
		logger.info("deleteFaseRipresaPrestazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseSuperamentoQuartoContrattuale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSospensione> getFaseSuperamentoQuartoContrattuale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseSuperamentoQuartoContrattuale: inizio metodo");

		ResponseFaseSospensione risultato = new ResponseFaseSospensione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseSuperamentoQuartoContrattuale(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseSuperamentoQuartoContrattuale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseSuperamentoQuartoContrattuale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseSuperamentoQuartoContrattuale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase sospensione da inserire", required = true) @RequestBody() @Valid FaseSuperamentoQuartoContrattualeInsertForm form) {

		logger.info("insertFaseSuperamentoQuartoContrattuale: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseSuperamentoQuartoContrattuale(form);
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
		logger.info("insertFaseSuperamentoQuartoContrattuale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseSuperamentoQuartoContrattuale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseSuperamentoQuartoContrattuale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase sospensione da inserire", required = true) @RequestBody() @Valid FaseSuperamentoQuartoContrattualeInsertForm form) {

		logger.info("updateFaseSuperamentoQuartoContrattuale: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseSuperamentoQuartoContrattuale(form);
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
		logger.info("updateFaseSuperamentoQuartoContrattuale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseSuperamentoQuartoContrattuale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseSuperamentoQuartoContrattuale(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseSuperamentoQuartoContrattuale: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseSuperamentoQuartoContrattuale(codGara, codLotto, num);
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
		logger.info("deleteFaseSuperamentoQuartoContrattuale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseVariante")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseVariante> getFaseVariante(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseVariante: inizio metodo");

		ResponseFaseVariante risultato = new ResponseFaseVariante();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseVariante(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseVariante: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseVariante")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseVariante(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseVarianteInsertForm form) {

		logger.info("insertFaseVariante: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseVariante(form);
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
		logger.info("insertFaseVariante: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseVariante")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseVariante(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseVarianteInsertForm form) {

		logger.info("updateFaseVariante: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseVariante(form);
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
		logger.info("updateFaseVariante: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseVariante")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseVariante(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseVariante: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseVariante(codGara, codLotto, num);
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
		logger.info("deleteFaseVariante: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getTabellatoDinamicoFaseVariante")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseTabellato> getTabellatoDinamicoFaseVariante(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getTabellatoDinamicoFaseVariante: inizio metodo");

		ResponseTabellato risultato = new ResponseTabellato();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.getTabellatoDinamicoFaseVariante(codGara, codLotto);
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
		logger.info("getTabellatoDinamicoFaseVariante: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseAccordoBonario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseAccordoBonario> getFaseAccordoBonario(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseAccordoBonario: inizio metodo");

		ResponseFaseAccordoBonario risultato = new ResponseFaseAccordoBonario();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseAccordoBonario(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseAccordoBonario: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseAccordoBonario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseAccordoBonario(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseAccordoBonarioInsertForm form) {

		logger.info("insertFaseAccordoBonario: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseAccordoBonario(form);
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
		logger.info("insertFaseAccordoBonario: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseAccordoBonario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseAccordoBonario(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseAccordoBonarioInsertForm form) {

		logger.info("updateFaseAccordoBonario: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseAccordoBonario(form);
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
		logger.info("updateFaseAccordoBonario: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseAccordoBonario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseAccordoBonario(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseAccordoBonario: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseAccordoBonario(codGara, codLotto, num);
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
		logger.info("deleteFaseAccordoBonario: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getInizFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseSubappalto> getInizFaseSubappalto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseSubappalto: inizio metodo");

		ResponseInizFaseSubappalto risultato = new ResponseInizFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseSubappalto(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSubappalto> getFaseSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseSubappalto: inizio metodo");

		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseSubappalto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseSubappaltoInsertForm form) {

		logger.info("insertFaseSubappalto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseSubappalto(form);
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
		logger.info("insertFaseSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseSubappaltoInsertForm form) {

		logger.info("updateFaseSubappalto: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseSubappalto(form);
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
		logger.info("updateFaseSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseSubappalto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseSubappalto(codGara, codLotto, num);
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
		logger.info("deleteFaseSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseRichiestaSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSubappalto> getFaseRichiestaSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseRichiestaSubappalto: inizio metodo");

		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseRichiestaSubappalto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseRichiestaSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseRichiestaSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseRichiestaSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseSubappaltoInsertForm form) {

		logger.info("insertFaseRichiestaSubappalto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseRichiestaSubappalto(form);
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
		logger.info("insertFaseRichiestaSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseRichiestaSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseRichiestaSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseSubappaltoInsertForm form) {

		logger.info("updateFaseRichiestaSubappalto: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseRichiestaSubappalto(form);
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
		logger.info("updateFaseRichiestaSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseRichiestaSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseRichiestaSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseRichiestaSubappalto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseRichiestaSubappalto(codGara, codLotto, num);
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
		logger.info("deleteFaseRichiestaSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseEsitoSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getInizFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseSubappalto> getInizFaseEsitoSubappalto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseEsitoSubappalto: inizio metodo");

		ResponseInizFaseSubappalto risultato = new ResponseInizFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseEsitoSubappalto(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseEsitoSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseEsitoSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSubappalto> getFaseEsitoSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseEsitoSubappalto: inizio metodo");

		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseEsitoSubappalto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseEsitoSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseEsitoSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseEsitoSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseEsitoSubappaltoInsertForm form) {

		logger.info("insertFaseEsitoSubappalto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseEsitoSubappalto(form);
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
		logger.info("insertFaseEsitoSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseEsitoSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseEsitoSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseEsitoSubappaltoInsertForm form) {

		logger.info("updateFaseEsitoSubappalto: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseEsitoSubappalto(form);
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
		logger.info("updateFaseEsitoSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseEsitoSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseEsitoSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseEsitoSubappalto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseEsitoSubappalto(codGara, codLotto, num);
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
		logger.info("deleteFaseEsitoSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseConclusioneSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getInizFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseSubappalto> getInizFaseConclusioneSubappalto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseConclusioneSubappalto: inizio metodo");

		ResponseInizFaseSubappalto risultato = new ResponseInizFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseConclusioneSubappalto(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseConclusioneSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseConclusioneSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseSubappalto> getFaseConclusioneSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseConclusioneSubappalto: inizio metodo");

		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseConclusioneSubappalto(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseConclusioneSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseConclusioneSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseConclusioneSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseConclusioneSubappaltoInsertForm form) {

		logger.info("insertFaseConclusioneSubappalto: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseConclusioneSubappalto(form);
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
		logger.info("insertFaseConclusioneSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseConclusioneSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseConclusioneSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseConclusioneSubappaltoInsertForm form) {

		logger.info("updateFaseConclusioneSubappalto: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseConclusioneSubappalto(form);
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
		logger.info("updateFaseConclusioneSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseConclusioneSubappalto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseConclusioneSubappalto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseConclusioneSubappalto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseConclusioneSubappalto(codGara, codLotto, num);
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
		logger.info("deleteFaseConclusioneSubappalto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseIstanzaRecesso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseIstanzaRecesso> getFaseIstanzaRecesso(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseIstanzaRecesso: inizio metodo");

		ResponseFaseIstanzaRecesso risultato = new ResponseFaseIstanzaRecesso();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseIstanzaRecesso(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseIstanzaRecesso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseIstanzaRecesso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseIstanzaRecesso(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseIstanzaRecessoInsertForm form) {

		logger.info("insertFaseIstanzaRecesso: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseIstanzaRecesso(form);
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
		logger.info("insertFaseIstanzaRecesso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseIstanzaRecesso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseIstanzaRecesso(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseIstanzaRecessoInsertForm form) {

		logger.info("updateFaseIstanzaRecesso: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseIstanzaRecesso(form);
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
		logger.info("updateFaseIstanzaRecesso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseIstanzaRecesso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseIstanzaRecesso(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseIstanzaRecesso: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseIstanzaRecesso(codGara, codLotto, num);
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
		logger.info("deleteFaseIstanzaRecesso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInidoneitaTecnicoProf")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInidoneitaTecnicoProf> getFaseInidoneitaTecnicoProf(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseInidoneitaTecnicoProf: inizio metodo");

		ResponseFaseInidoneitaTecnicoProf risultato = new ResponseFaseInidoneitaTecnicoProf();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInidoneitaTecnicoProf(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInidoneitaTecnicoProf: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseInidoneitaTecnicoProf")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseInidoneitaTecnicoProf(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase inidoneita tecnico professionale da inserire", required = true) @RequestBody() @Valid FaseInidoneitaTecnicoProfInsertForm form) {

		logger.info("insertFaseInidoneitaTecnicoProf: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInidoneitaTecnicoProf(form);
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
		logger.info("insertFaseInidoneitaTecnicoProf: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseInidoneitaTecnicoProf")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseInidoneitaTecnicoProf(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase variante da inserire", required = true) @RequestBody() @Valid FaseInidoneitaTecnicoProfInsertForm form) {

		logger.info("updateFaseInidoneitaTecnicoProf: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInidoneitaTecnicoProf(form);
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
		logger.info("updateFaseInidoneitaTecnicoProf: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseInidoneitaTecnicoProf")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseIdoneitaTecnicoProf(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseInidoneitaTecnicoProf: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInidoneitaTecnicoProf(codGara, codLotto, num);
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
		logger.info("deleteFaseInidoneitaTecnicoProf: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInidoneitaContributiva")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseInidoneitaContributiva dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInidoneitaContributiva> getFaseInidoneitaContributiva(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseInidoneitaContributiva: inizio metodo");

		ResponseFaseInidoneitaContributiva risultato = new ResponseFaseInidoneitaContributiva();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInidoneitaContributiva(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInidoneitaContributiva: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseInidoneitaContributiva")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseInidoneitaContributiva(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase inidoneita contributiva da inserire", required = true) @RequestBody() @Valid FaseInidoneitaContributivaInsertForm form) {

		logger.info("insertFaseInidoneitaContributiva: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInidoneitaContributiva(form);
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
		logger.info("insertFaseInidoneitaContributiva: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseInidoneitaContributiva")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseInidoneitaContributiva(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase inidoneita contributiva da inserire", required = true) @RequestBody() @Valid FaseInidoneitaContributivaInsertForm form) {

		logger.info("updateFaseInidoneitaContributiva: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInidoneitaContributiva(form);
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
		logger.info("updateFaseInidoneitaContributiva: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseInidoneitaContributiva")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseInidoneitaContributiva(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("deleteFaseInidoneitaContributiva: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInidoneitaContributiva(codGara, codLotto, num);
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
		logger.info("deleteFaseInidoneitaContributiva: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInadempienzeSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInadempienzeSicurezza> getFaseInadempienzeSicurezza(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseInadempienzeSicurezza: inizio metodo");

		ResponseFaseInadempienzeSicurezza risultato = new ResponseFaseInadempienzeSicurezza();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInadempienzeSicurezza(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInadempienzeSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseInadempienzeSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseInadempienzeSicurezza(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase inadempienze sicurezza da inserire", required = true) @RequestBody() @Valid FaseInadempienzeSicurezzaInsertForm form) {

		logger.info("insertFaseInadempienzeSicurezza: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInadempienzeSicurezza(form);
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
		logger.info("insertFaseInadempienzeSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseInadempienzeSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseInadempienzeSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase inadempienze sicurerzza da inserire", required = true) @RequestBody() @Valid FaseInadempienzeSicurezzaInsertForm form) {

		logger.info("updateFaseInadempienzeSicurezza: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInadempienzeSicurezza(form);
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
		logger.info("updateFaseInadempienzeSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseInadempienzeSicurezza")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseInadempienzeSicurezza(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseInadempienzeSicurezza: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInadempienzeSicurezza(codGara, codLotto, num);
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
		logger.info("deleteFaseInadempienzeSicurezza: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInfortuni")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInfortuni> getFaseInfortuni(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseInfortuni: inizio metodo");

		ResponseFaseInfortuni risultato = new ResponseFaseInfortuni();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInfortuni(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInfortuni: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseInfortuni")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseInfortuni(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase infortuni da inserire", required = true) @RequestBody() @Valid FaseInfortuniInsertForm form) {

		logger.info("insertFaseInfortuni: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInfortuni(form);
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
		logger.info("insertFaseInfortuni: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseInfortuni")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseInfortuni(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase infortuni da inserire", required = true) @RequestBody() @Valid FaseInfortuniInsertForm form) {

		logger.info("updateFaseInfortuni: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInfortuni(form);
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
		logger.info("updateFaseInfortuni: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseInfortuni")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseInfortuni(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseInfortuni: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInfortuni(codGara, codLotto, num);
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
		logger.info("deleteFaseInfortuni: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseCantieri")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseCantieri> getFaseCantieri(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("getFaseCantieri: inizio metodo");

		ResponseFaseCantieri risultato = new ResponseFaseCantieri();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseCantieri(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseCantieri: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseCantieri")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseCantieri> getInizFaseCantieri(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseCantieri: inizio metodo");

		ResponseInizFaseCantieri risultato = new ResponseInizFaseCantieri();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseCantieri(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseConclusioneContratto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseCantieri")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseCantieri(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase cantieri da inserire", required = true) @RequestBody() @Valid FaseCantieriInsertForm form) {

		logger.info("insertFaseCantieri: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseCantieri(form);
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
		logger.info("insertFaseCantieri: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseCantieri")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseCantieri(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase infortuni da inserire", required = true) @RequestBody() @Valid FaseCantieriInsertForm form) {

		logger.info("updateFaseCantieri: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseCantieri(form);
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
		logger.info("updateFaseCantieri: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseCantieri")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseCantieri(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseCantieri: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseCantieri(codGara, codLotto, num);
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
		logger.info("deleteFaseCantieri: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseInizialeSemplificata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getFaseSubappalto dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseInizialeSemplificata> getFaseInizialeSemplificata(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseInizialeSemplificata: inizio metodo");

		ResponseFaseInizialeSemplificata risultato = new ResponseFaseInizialeSemplificata();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseInizialeSemplificata(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseInizialeSemplificata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseInizialeSemplificata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseInizialeSemplificata(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase iniziale semplificata da inserire", required = true) @RequestBody() @Valid FaseInizialeSemplificataInsertForm form) {

		logger.info("insertFaseInizialeSemplificata: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseInizialeSemplificata(form);
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
		logger.info("insertFaseInizialeSemplificata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseInizialeSemplificata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseInizialeSemplificata(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase infortuni da inserire", required = true) @RequestBody() @Valid FaseInizialeSemplificataInsertForm form) {

		logger.info("updateFaseInizialeSemplificata: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseInizialeSemplificata(form);
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
		logger.info("updateFaseInizialeSemplificata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseInizialeSemplificata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseInizialeSemplificata(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseInizialeSemplificata: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseInizialeSemplificata(codGara, codLotto, num);
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
		logger.info("deleteFaseInizialeSemplificata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseVariante")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante getInizFaseVariante dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseVarianteIniz> getInizFaseVariante(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseVariante: inizio metodo");

		ResponseFaseVarianteIniz risultato = new ResponseFaseVarianteIniz();
		HttpStatus status = HttpStatus.OK;
		risultato.setEsito(true);
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			FaseVarianteIniz lista = this.fasiManager.getInizFaseVariante(codGara, codLotto);
			risultato.setData(lista);

		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseVariante: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getFaseStipulaAccordo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseFaseStipulaAccordoQuadro> getFaseStipulaAccordo(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "num") Long num) {

		logger.info("getFaseStipulaAccordo: inizio metodo");

		ResponseFaseStipulaAccordoQuadro risultato = new ResponseFaseStipulaAccordoQuadro();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getFaseStipulaAccordoQuadro(codGara, codLotto, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getFaseStipulaAccordo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertFaseStipulaAccordo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertFaseStipulaAccordo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase stipula accordo da inserire", required = true) @RequestBody() @Valid FaseStipulaAccordoQuadroInsertForm form) {

		logger.info("insertFaseStipulaAccordo: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.insertFaseStipulaAccordoQuadro(form);
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
		logger.info("insertFaseStipulaAccordo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateFaseStipulaAccordo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateFaseStipulaAccordo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di una fase stipula accordo da modificare", required = true) @RequestBody() @Valid FaseStipulaAccordoQuadroInsertForm form) {

		logger.info("updateFaseStipulaAccordo: inizio metodo");

		BaseResponse risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.updateFaseStipulaAccordoQuadro(form);
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
		logger.info("updateFaseStipulaAccordo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFaseStipulaAccordo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFaseStipulaAccordo(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "num") Long num) {

		logger.info("deleteFaseStipulaAccordo: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.fasiManager.deleteFaseStipulaAccordoQuadro(codGara, codLotto, num);
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
		logger.info("deleteFaseStipulaAccordo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRequestPubblicazioneGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseRequestPubblicazione> getRequestPubblicazioneGara(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara) {

		logger.info("getPdf: inizio metodo");

		ResponseRequestPubblicazione risultato = new ResponseRequestPubblicazione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if (permission) {
			try {
				risultato = this.contrattiManager.getRequestPubblicazioneGara(codGara,syscon);
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
		logger.info("getRequestPubblicazioneGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRequestPubblicazioneSmartCig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseRequestPubblicazioneSmartCig> getRequestPubblicazioneSmartCig(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara) {

		logger.info("getPdf: inizio metodo");

		ResponseRequestPubblicazioneSmartCig risultato = new ResponseRequestPubblicazioneSmartCig();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		String cf = userAuthClaimsDTO.getCf();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if (permission) {
			try {
				risultato = this.contrattiManager.getRequestPubblicazioneSmartCig(codGara,syscon,cf);
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
		logger.info("getRequestPubblicazioneSmartCig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRequestPubblicazioneAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseRequestPubblicazioneAtti> getRequestPubblicazioneAtto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "tipoDocumento") Long tipoDocumento,
			@RequestParam(value = "numPubb") Long numPubb) {

		logger.info("getRequestPubblicazioneAtto: inizio metodo");

		ResponseRequestPubblicazioneAtti risultato = new ResponseRequestPubblicazioneAtti();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
		
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if (permission) {
			try {
				risultato = this.contrattiManager.getRequestPubblicazioneAtti(codGara, tipoDocumento, numPubb,syscon);
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
		logger.info("getRequestPubblicazioneAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/allineaPubblicazioneGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> allineaPubblicazioneGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codiceFiscaleSA") String codiceFiscaleSA,
			@RequestParam(value = "idRicevuto") Long idRicevuto, @RequestParam(value = "tipoInvio") Long tipoInvio,
			@RequestParam(value = "syscon") Long syscon,
			@RequestBody() String payload) {

		logger.info("allineaPubblicazioneGara: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codiceFiscaleSA == null || tipoInvio == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.allineaPubblicazioneGara(codGara, codiceFiscaleSA, idRicevuto,
						tipoInvio, syscon, payload);
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
		logger.info("allineaPubblicazioneGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/allineaPubblicazioneAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> allineaPubblicazioneAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "tipoDocumento") Long tipoDocumento, @RequestParam(value = "numPubb") Long numPubb,
			@RequestParam(value = "idGara") Long idGara, @RequestParam(value = "idRicevuto") Long idRicevuto,
			@RequestParam(value = "tipoInvio") Long tipoInvio, @RequestParam(value = "syscon") Long syscon,
			@RequestBody() String payload) {

		logger.info("allineaPubblicazioneAtto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || numPubb == null || tipoDocumento == null || idRicevuto == null || tipoInvio == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.allineaPubblicazioneAtto(codGara, tipoDocumento, numPubb, tipoInvio,
						idGara, idRicevuto, syscon, payload);
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
		logger.info("allineaPubblicazioneAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/allineaPubblicazioneFase")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> allineaPubblicazioneFase(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "numFase") Long numFase,
			@RequestParam(value = "num") Long num, @RequestParam(value = "tipoInvio") Long tipoInvio,
			@RequestParam(value = "codiceFiscaleSA") String codiceFiscaleSA,
			@RequestParam(value = "syscon") Long syscon,
			@RequestParam(value = "motivazione", required = false) String motivazione) {

		logger.info("allineaPubblicazioneFase: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || numFase == null || num == null || tipoInvio == null
				|| syscon == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.allineaPubblicazioneFase(codGara, codLotto, numFase, num, tipoInvio,
						codiceFiscaleSA, syscon, motivazione);
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
		try {
			String descrizione = "Trasmissione della scheda (numFase) "+ numFase +" per la gara "+ codGara;
			if(tipoInvio == -1L) {
				descrizione = "Cancellazione della scheda (numFase) "+ numFase +" per la gara "+ codGara;
			}
			Short livento = 1;
			this.simogManager.insertLogEventi(syscon, "SA-APPA-SITAR-A", LOG_EVENTI_SCHEDA_COD_EVENTO, descrizione, risultato.getErrorData(), livento, codGara.toString());
		}catch (Exception e) {
			logger.error("Errore inserimento w_logeventi", e);
		}
		logger.info("allineaPubblicazioneFase: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaPubblicazioniGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaPubblicazioneGara> getListaPubblicazioniGara(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara) {

		logger.info("getListaPubblicazioniGara: inizio metodo");

		ResponseListaPubblicazioneGara risultato = new ResponseListaPubblicazioneGara();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getListaPubblicazioniGara(codGara);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getListaPubblicazioniGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getTracciatoFlusso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> getTracciatoFlusso(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "idFlusso") Long idFlusso) {

		logger.info("getListaPubblicazioniGara: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		
		risultato = this.contrattiManager.getTracciatoFlusso(idFlusso);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("getTracciatoFlusso: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaPubblicazioniAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaPubblicazioneAtto> getListaPubblicazioniAtto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "num") Long num) {

		logger.info("getListaPubblicazioniAtto: inizio metodo");

		ResponseListaPubblicazioneAtto risultato = new ResponseListaPubblicazioneAtto();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getListaPubblicazioniAtto(codGara, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getListaPubblicazioniAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaPubblicazioniFase")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaPubblicazioneFase> getListaPubblicazioniFase(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "numFase") Long numFase, @RequestParam(value = "num") Long num) {

		logger.info("getListaPubblicazioniFase: inizio metodo");

		ResponseListaPubblicazioneFase risultato = new ResponseListaPubblicazioneFase();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || numFase == null || num == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getPubblicazioniFase(codGara, codLotto, numFase, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getListaPubblicazioniFase: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaInviiFasi")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaInviiFasi> getListaInviiFasi(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "codLotto") Long codLotto, @RequestParam(value = "sort") String sort,
			@RequestParam(value = "sortDirection") String sortDirection, @RequestParam(value = "skip") int skip,
			@RequestParam(value = "take") int take) {

		logger.info("getListaInviiFasi: inizio metodo");

		ResponseListaInviiFasi risultato = new ResponseListaInviiFasi();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (codGara == null || codLotto == null || sort == null || sortDirection == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		if (permission) {
			risultato = this.contrattiManager.getFlussiFase(codGara, codLotto, skip, take, sort, sortDirection);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getListaInviiFasi: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getIdSchedaLocale")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDto> getIdSchedaLocale(
		 @RequestHeader("X-loginMode") String xLoginMode,
		 @RequestHeader("Authorization") String authorization,
		 @RequestParam(value = "codGara") Long codGara,
		 @RequestParam(value = "codLotto") Long codLotto,
		 @RequestParam(value = "num") Long num,
		 @RequestParam(value = "numFase") Long numFase
	) {

		logger.info("getIdSchedaLocale: inizio metodo");

		ResponseDto risultato = new ResponseDto();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (codGara == null || codLotto == null || num == null || numFase == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		if (permission) {
			risultato = this.contrattiManager.getIdSchedaLocale(codGara, codLotto, num, numFase);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getIdSchedaLocale: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRequestPubblicazioneFase")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseRequestFase> getRequestPubblicazioneFase(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "codFase") Long codFase, @RequestParam(value = "num") Long num) {

		logger.info("getRequestPubblicazioneFase: inizio metodo");

		ResponseRequestFase risultato = new ResponseRequestFase();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null || codFase == null || num == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getRequestPubbFase(codGara, codLotto, codFase, num);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getRequestPubblicazioneFase: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	public boolean hasPermission(long codGara, String authorization, String loginMode) {

		try {
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

			String syslogin = userAuthClaimsDTO.getLogin();
			String cf = userAuthClaimsDTO.getCf();
			Long syscon = userAuthClaimsDTO.getSyscon();
			ResponseDettaglioGara dettaglioGara = this.contrattiManager.dettaglioGara(codGara,authorization,loginMode);
			GaraEntry gara = dettaglioGara.getData();
			if (gara == null) {
				return false;
			}
			String ruolo = this.contrattiManager.getRuolo(syscon);
			List<String> saList = this.contrattiManager.getSAList(syscon);
			String abilitazioniUtente = this.contrattiManager.getAbilitazioniUtente(syscon);
			
			if (abilitazioniUtente == null || (abilitazioniUtente != null && !abilitazioniUtente.contains("ou238")) && !saList.contains(gara.getCodiceStazioneAppaltante())) {
				return false;
			}

			if (gara.getSyscon() != null && gara.getSyscon().equals(syscon)) {
				return true;
			}

			if (!"A".equals(ruolo) && !"C".equals(ruolo)) {
				
				Boolean isRup = true;
				Boolean isCollab = true;
				Boolean isDrp = true;
				Boolean collaboratoreDrp = true;
				
				RupEntry tecnico = this.contrattiManager.getRup(gara.getCodiceTecnico());
				if (tecnico != null && StringUtils.isNotBlank(cf) && !cf.equals(tecnico.getCf())) {
					isRup = false;
				}
				
				if(gara.getDrp() != null && gara.isPcp()) {
					RupEntry drp = this.contrattiManager.getRup(gara.getDrp());
					if (drp != null && StringUtils.isNotBlank(cf) && !cf.equals(drp.getCf())) {
						isDrp = false;
					}
					if(drp != null) {
						Long collaboratore = this.delegheManager.getExistsCollaboratore(drp.getCf(),gara.getCodiceStazioneAppaltante(),syscon);
						if (collaboratore == 0L) {
							collaboratoreDrp = false;
						}
					}
				} else {
					isDrp = false;
					collaboratoreDrp = false;
				}
								
				if(tecnico != null) {
					Long collaboratore = this.delegheManager.getExistsCollaboratore(tecnico.getCf(),gara.getCodiceStazioneAppaltante(),syscon);
					if (collaboratore == 0L) {
						isCollab = false;
					}
				}
				
				if(gara.isPcp()) {
					if(!isRup && !isCollab && !isDrp && !collaboratoreDrp) {
						return false;
					}
				} else {
					if(!isRup && !isCollab) {
						return false;
					}
				}
				
				
			}
		} catch (Exception e) {
			logger.error("Errore durante il controllo dei permessi", e);
			return false;
		}
		return true;
	}

	// Autocomplete imprese filtrate

	@RequestMapping(method = RequestMethod.GET, value = "/getImpreseOptionsAggiSuba")
	@ApiOperation(nickname = "ContrattiController.getImpreseOptionsAggiSuba", value = "Ritorna la lista delle imprese contenenti la string di ricerca nel codice o ragione sociale filtrate per CODIMP presente in W9AGGI o W9SUBA")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 401, message = "Utente non autorizzato a lavorare sulla gara selezionata"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaImpreseOptions> getImpreseOptionsAggiSuba(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi necessari alla ricerca di un'impresa", required = true) SingolaImpresaAggiSubaSearchForm form) {

		logger.info("getImpreseOptionsAggiSuba: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
		if (form == null || form.getDesc() == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Richiesta non valida, parametri obbligatori non valorizzati");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = fasiManager.getImpreseOptionsAggiSuba(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.BAD_REQUEST;
		}
		logger.info("getImpreseOptionsAggiSuba: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getImpreseOptionsVaraggi")
	@ApiOperation(nickname = "ContrattiController.getImpreseOptionsVaraggi", value = "Ritorna la lista delle imprese contenenti la string di ricerca nel codice o ragione sociale filtrate per CODIMP presente in W9AGGI o W9SUBA")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 401, message = "Utente non autorizzato a lavorare sulla gara selezionata"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaImpreseOptions> getImpreseOptionsVaraggi(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi necessari alla ricerca di un'impresa", required = true) SingolaImpresaAggiSubaSearchForm form) {

		logger.info("getImpreseOptionsVaraggi: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
		if (form == null || form.getDesc() == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Richiesta non valida, parametri obbligatori non valorizzati");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = fasiManager.getImpreseOptionsVaraggi(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.BAD_REQUEST;
		}
		logger.info("getImpreseOptionsVaraggi: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getImpreseOptionsAggi")
	@ApiOperation(nickname = "ContrattiController.getImpreseOptionsAggi", value = "Ritorna la lista delle imprese contenenti la string di ricerca nel codice o ragione sociale filtrate per CODIMP presente in W9AGGI")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 401, message = "Utente non autorizzato a lavorare sulla gara selezionata"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaImpreseOptions> getImpreseOptionsAggi(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi necessari alla ricerca di un'impresa", required = true) SingolaImpresaAggiSubaSearchForm form) {

		logger.info("getImpreseOptionsAggi: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
		if (form == null || form.getDesc() == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Richiesta non valida, parametri obbligatori non valorizzati");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			risultato = fasiManager.getImpreseOptionsAggi(form);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.BAD_REQUEST;
		}
		logger.info("getImpreseOptionsAggi: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateRupGara")
	@ApiOperation(nickname = "ContrattiController.updateRupGara", value = "Metodo che aggiorna il RUP della gara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 401, message = "Utente non autorizzato a lavorare sulla gara selezionata"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseUpdateRupGara> updateRupGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestBody() @Valid RupGaraUpdateForm form) {

		logger.info("updateRupGara: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseUpdateRupGara risultato = new ResponseUpdateRupGara();
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.contrattiManager.updateRupGara(form);
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
		logger.info("updateRupGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/migraGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> migraGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Cod gara della collezione da migrare", required = true) @RequestBody MigrazioneGaraForm form) {
		boolean permission = hasPermission(form.getCodGara(), authorization, xLoginMode);
		HttpStatus status = HttpStatus.OK;
		BaseResponse risultato = new BaseResponse();
		logger.info("migraGara: inizio metodo");
		if (permission) {
			try {
				risultato = this.contrattiManager.migraGara(form);
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
		logger.info("migraGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/archiviaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> archiviaGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Cod della gara da archiviare", required = true) @RequestBody String codGara) {

		logger.info("archiviaGara: inizio metodo");

		Long codGaraLong = new Long(codGara);
		boolean permission = hasPermission(codGaraLong, authorization, xLoginMode);
		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (permission) {
			risultato = this.contrattiManager.archiviaGara(codGaraLong);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("archiviaGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	private static final String LOG_EVENTI_CREA_GARA_COD_EVENTO = "CREAGARA";
	private static final String LOG_EVENTI_SCHEDA_COD_EVENTO = "SCHEDA";
	
	private static final String LOG_EVENTI_CREA_GARA_DESCR = "Scarico della gara con idavgara/cig = ";
	private static final String LOG_EVENTI_CREA_GARA_DESCR_PRESA_CARICO = "inserimento/presa carico della gara con idappalto/cig = ";
	private static final String LOG_EVENTI_TRASMETTI_SCHEDA_DESCR = "Trasmissione della scheda {tipologia} per la gara {codgara}";
	private static final String LOG_EVENTI_CANCELLA_SCHEDA_DESCR = "Cancellazionedella scheda {tipologia} per la gara {codgara}";
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/importaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseImportaGara> importaGara(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@ApiParam(value = "Campi di ricerca da inviare al webservice SIMOG", required = true) @RequestBody ImportaGaraForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseImportaGara risultato = new ResponseImportaGara();
		logger.info("importaGaraSimog: inizio metodo");
		try {
			String version = this.contrattiManager.versionPcp();
			
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

			String cf = userAuthClaimsDTO.getCf();
			String loa = userAuthClaimsDTO.getLoa();
			String idp = userAuthClaimsDTO.getIdp();
			Long syscon = userAuthClaimsDTO.getSyscon();
					
			if(StringUtils.isNotBlank(form.getCfImpersonato()) && StringUtils.isNotBlank(form.getLoaImpersonato()) && StringUtils.isNotBlank(form.getIdpImpersonato()) && (syscon != null && (syscon == 48 || syscon == 49))) {
				loa = form.getLoaImpersonato();
				idp = form.getIdpImpersonato();
				cf = form.getCfImpersonato();
			}
			
			boolean importa = true;
			
			ResponseResult resSimog = this.contrattiManager.checkExistSimogEndpoint();
			boolean sim = resSimog != null && resSimog.getData() != null && resSimog.getData().equals("true") ? true : false;
			ResponseResult resPcp = this.contrattiManager.checkExistPcpEndpoint();
			boolean npa = resPcp != null && resPcp.getData() != null && resPcp.getData().equals("true") ? true : false;
			boolean loa3 = loa != null && (loa.equals("3") || loa.equals("4"));
			if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM") && loa3) {
				String multiEnte = this.contrattiManager.getMultiEnteConfig();
				String codein = form.getCodein();
				if(multiEnte == null || "0".equals(multiEnte)) {
					codein = "STD";
				}
				Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
				if(idps == 0) {
					risultato.setEsito(false);
					risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					importa = false;
				}
			}
				
			if(importa) {
				logger.info("sim: {} - npa: {} - loa: {} - input: {} - isCig: {} - isUUID: {}",sim,npa,loa,form.getCigIdAvGara(),this.simogManager.isCig(form.getCigIdAvGara()),this.contrattiManager.isUUID(form.getCigIdAvGara()));
				if(sim && !npa) {
					if(this.simogManager.isCig(form.getCigIdAvGara())) {
						if(form.getCigIdAvGara().startsWith("A") || (form.getCigIdAvGara().length() > 0 && Character.isDigit(form.getCigIdAvGara().charAt(0)))) {
							risultato = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), form.getCfSA(), form.getSyscon(),
									form.isSendMail(), authorization, loginMode);
						} else {
							risultato.setEsito(false);
							risultato.setErrorData(BaseResponse.ERROR_INVALID_VALUE);
						}					
					}  else if(this.contrattiManager.isUUID(form.getCigIdAvGara())) {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_INVALID_VALUE);
					}else {
						risultato = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), form.getCfSA(), form.getSyscon(),
								form.isSendMail(), authorization, loginMode);
					}
				} else if(sim && npa && !loa3) {
					if(this.simogManager.isCig(form.getCigIdAvGara())) {													
						if(form.getCigIdAvGara().startsWith("A") || (form.getCigIdAvGara().length() > 0 && Character.isDigit(form.getCigIdAvGara().charAt(0)))) {
							risultato = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), form.getCfSA(), form.getSyscon(),
									form.isSendMail(), authorization, loginMode);
						} else {
							risultato.setEsito(false);
							risultato.setErrorData(BaseResponse.ERROR_PERMISSION_PCP);
						}
					} else if(this.contrattiManager.isUUID(form.getCigIdAvGara())) {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_PCP);
					} else {
						risultato = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), form.getCfSA(), form.getSyscon(),
								form.isSendMail(), authorization, loginMode);
					}
				} else if(!sim && npa && loa3) {
					if(this.simogManager.isCig(form.getCigIdAvGara())) {
						if(form.getCigIdAvGara().startsWith("A") || (form.getCigIdAvGara().length() > 0 && Character.isDigit(form.getCigIdAvGara().charAt(0)))) {
							risultato.setEsito(false);
							risultato.setErrorData(BaseResponse.ERROR_INVALID_VALUE);
						} else {
							risultato = this.importAppaltoPcpManager.importaGaraPcp(form.getCfSA(), form.getCigIdAvGara().toUpperCase(), null, form.getSyscon(), cf, loa, idp, authorization, loginMode);
						}					
					} else if(this.contrattiManager.isUUID(form.getCigIdAvGara())) {
						risultato = this.importAppaltoPcpManager.importaGaraPcp(form.getCfSA(), null, form.getCigIdAvGara(), form.getSyscon(), cf, loa, idp, authorization, loginMode);
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_INVALID_VALUE);
					} 
				} else if(sim && npa && loa3) {
					if(this.simogManager.isCig(form.getCigIdAvGara())) {					
						if(form.getCigIdAvGara().startsWith("A") || (form.getCigIdAvGara().length() > 0 && Character.isDigit(form.getCigIdAvGara().charAt(0)))) {						
							risultato = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), form.getCfSA(), form.getSyscon(),
									form.isSendMail(), authorization, loginMode);
						} else {
							risultato = this.importAppaltoPcpManager.importaGaraPcp(form.getCfSA(), form.getCigIdAvGara().toUpperCase(), null, form.getSyscon(), cf,loa, idp, authorization, loginMode);
						}
					} else if(this.contrattiManager.isUUID(form.getCigIdAvGara())) {
							risultato = this.importAppaltoPcpManager.importaGaraPcp(form.getCfSA(), null, form.getCigIdAvGara(), form.getSyscon(), cf, loa, idp, authorization, loginMode);
					} else {
						risultato = this.simogManager.importaGaraDaSimog(form.getCigIdAvGara(), form.getCfSA(), form.getSyscon(),
								form.isSendMail(), authorization, loginMode);
					} 
				} else if(!sim && (!npa || !loa3)) {
					if(this.simogManager.isCig(form.getCigIdAvGara())) {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_FUNCTION_NOT_AVAILABLE);
					} else if(this.contrattiManager.isUUID(form.getCigIdAvGara())) {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_FUNCTION_NOT_AVAILABLE);
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_FUNCTION_NOT_AVAILABLE);
					} 
				}
			}
			
			
		} catch (Exception e) {
			logger.error("errore nel metodo importaGara - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("errore: {}",risultato.getErrorData());
		}
		
		try {
			Short livento = 1;
			if(risultato.getErrorData() != null) {
				livento = 3;
			}
			if(this.simogManager.isCig(form.getCigIdAvGara())) {
				this.simogManager.insertLogEventi(form.getSyscon(), form.getCodProfilo(), LOG_EVENTI_CREA_GARA_COD_EVENTO, LOG_EVENTI_CREA_GARA_DESCR + form.getCigIdAvGara(), risultato.getErrorData(), livento, null);
			} else{
				this.simogManager.insertLogEventi(form.getSyscon(), form.getCodProfilo(), LOG_EVENTI_CREA_GARA_COD_EVENTO, LOG_EVENTI_CREA_GARA_DESCR + form.getCigIdAvGara(), risultato.getErrorData(), livento, form.getCigIdAvGara());
			}

		}catch (Exception e) {
			logger.error("Errore inserimento w_logeventi", e);
		}
		logger.info("importaGaraSimog: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/presaCaricoImportaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseImportaGara> presaCaricoImportaGara(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@ApiParam(value = "Campi di ricerca da inviare al webservice SIMOG", required = true) @RequestBody ImportaGaraForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseImportaGara risultato = new ResponseImportaGara();
		logger.info("presaCaricoImportaGara: inizio metodo");
		try {
			String version = this.contrattiManager.versionPcp();
			
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

			String cf = userAuthClaimsDTO.getCf();
			String loa = userAuthClaimsDTO.getLoa();
			String idp = userAuthClaimsDTO.getIdp();
			Long syscon = userAuthClaimsDTO.getSyscon();
					
			if(StringUtils.isNotBlank(form.getCfImpersonato()) && StringUtils.isNotBlank(form.getLoaImpersonato()) && StringUtils.isNotBlank(form.getIdpImpersonato()) && (syscon != null && (syscon == 48 || syscon == 49))) {
				loa = form.getLoaImpersonato();
				idp = form.getIdpImpersonato();
				cf = form.getCfImpersonato();
			}
			
			boolean importa = true;
			
			boolean loa3 = loa != null && (loa.equals("3") || loa.equals("4"));
			if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM") && loa3) {
				String multiEnte = this.contrattiManager.getMultiEnteConfig();
				String codein = form.getCodein();
				if(multiEnte == null || "0".equals(multiEnte)) {
					codein = "STD";
				}
				Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
				if(idps == 0) {
					risultato.setEsito(false);
					risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					importa = false;
				}
			}
				
			if(importa) {
				if(this.contrattiManager.isUUID(form.getCigIdAvGara())) {
					risultato = this.importAppaltoPcpManager.executePresaCaricoImportGaraPcp(form.getCfSA(), null, form.getCigIdAvGara(), form.getSyscon(), cf, loa, idp, authorization, loginMode);
				} else if(this.simogManager.isCig(form.getCigIdAvGara())) {
					risultato = this.importAppaltoPcpManager.executePresaCaricoImportGaraPcp(form.getCfSA(), form.getCigIdAvGara(), null, form.getSyscon(), cf, loa, idp, authorization, loginMode);
				}
			}
								
		} catch (Exception e) {
			logger.error("errore nel metodo presaCaricoImportaGara - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("errore: {}",risultato.getErrorData());
		}
		
		try {
			Short livento = 1;
			if(risultato.getErrorData() != null) {
				livento = 3;
			}
			this.simogManager.insertLogEventi(form.getSyscon(), form.getCodProfilo(), LOG_EVENTI_CREA_GARA_COD_EVENTO, LOG_EVENTI_CREA_GARA_DESCR_PRESA_CARICO + form.getCigIdAvGara(), risultato.getErrorData(), livento,form.getCigIdAvGara());
		}catch (Exception e) {
			logger.error("Errore inserimento w_logeventi", e);
		}
		logger.info("presaCaricoImportaGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/allineaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> allineaGara(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@ApiParam(value = "Campi di ricerca da inviare al webservice SIMOG", required = true) @RequestBody AllineaGaraForm form) {

		HttpStatus status = HttpStatus.OK;
		BaseResponse risultato = new ResponseImportaGara();
		logger.info("importaGaraSimog: inizio metodo");
		try {
			risultato = this.simogManager.riallineaGaraDaSimog(form.getIdavGara(), form.getCodiceSA(), authorization,
					loginMode);
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

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/checkMigrazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseCheckMigrazione> checkMigrazione(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@ApiParam(value = "Campi di necessari al check se la gara può migrare", required = true) @RequestBody CheckMigrazioneForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseCheckMigrazione risultato = new ResponseCheckMigrazione();
		logger.info("CheckMigrazioneForm: inizio metodo");
		risultato = this.simogManager.checkMigrazione(form.getIdavGara(), form.getSyscon(),
				form.getCfStazioneAppaltante(), authorization, loginMode);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("checkMigrazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/annullaArchiviaGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> annullaArchiviaGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Cod della gara cui annullare l'archiviazione", required = true) @RequestBody String codGara) {

		logger.info("annullaArchiviaGara: inizio metodo");

		Long codGaraLong = new Long(codGara);
		boolean permission = hasPermission(codGaraLong, authorization, xLoginMode);
		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		if (permission) {
			risultato = this.contrattiManager.annullaArchiviaGara(codGaraLong);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("annullaArchiviaGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/isAnagraficaGaraPubblicata")
	@ApiOperation(nickname = "ContrattiController.isAnagraficaGaraPubblicata", value = "Ritorna un booleano rappresentante lo stato di pubblicazione dell'anagrafica di gara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 401, message = "Utente non autorizzato a lavorare sulla gara selezionata"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseAnagraficaGaraPubblicata> isAnagraficaGaraPubblicata(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Codice gara", required = true) @RequestParam(value = "codGara") Long codGara,
			@ApiParam(value = "Controlla SmartCig", required = true) @RequestParam(value = "checkSmartCig") Boolean checkSmartCig) {

		logger.info("isAnagraficaGaraPubblicata: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		ResponseAnagraficaGaraPubblicata risultato = new ResponseAnagraficaGaraPubblicata();
		if (codGara == null) {
			status = HttpStatus.BAD_REQUEST;
			risultato.setEsito(false);
			risultato.setErrorData("Richiesta non valida, parametri obbligatori non valorizzati");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = contrattiManager.isAnagraficaGaraPubblicata(codGara, checkSmartCig);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("isAnagraficaGaraPubblicata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFinanziamenti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFinanziamenti> getInizFinanziamenti(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFinanziamenti: inizio metodo");

		ResponseInizFinanziamenti risultato = new ResponseInizFinanziamenti();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFinanziamenti(codGara, codLotto);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFinanziamenti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaCollaborazioni")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaCollaborazioni> getListaCollaborazioni(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campo cf rup da inviare a SIMOG", required = true) @RequestParam(value = "cfRup") String cfRup) {

		HttpStatus status = HttpStatus.OK;
		ResponseListaCollaborazioni risultato = new ResponseListaCollaborazioni();
		logger.info("getListaCollaborazioni: inizio metodo");
		try {
			risultato = this.simogManager.getListaCollaborazioni(cfRup, null, null);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getListaCollaborazioni: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/checkLottiAccorpatiGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseLottoAccorpabile> checkLottiAccorpatiGara(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Codice della gara", required = true) @RequestParam(value = "codGara") Long codGara) {

		HttpStatus status = HttpStatus.OK;
		ResponseLottoAccorpabile risultato = new ResponseLottoAccorpabile();
		logger.info("checkLottiAccorpatiGara: inizio metodo");
		try {
			risultato = this.contrattiManager.checkLottiAccorpatiGara(codGara);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("checkLottiAccorpatiGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getMultiLottoOptions")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseMultilotto> getMultiLottoOptions(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Codice della gara", required = true) @RequestParam(value = "codGara") Long codGara,
			@ApiParam(value = "Occorrnza cig da cercare", required = true) @RequestParam(value = "occurrence") String occurrence) {

		HttpStatus status = HttpStatus.OK;
		ResponseMultilotto risultato = new ResponseMultilotto();
		logger.info("getMultiLottoOptions: inizio metodo");
		try {
			risultato = this.contrattiManager.getMultiLottoOptions(codGara, occurrence);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getMultiLottoOptions: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getLottiAccorpabili")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseMultilotto> getLottiAccorpabili(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Codice della gara", required = true) @RequestParam(value = "codGara") Long codGara,
			@ApiParam(value = "codLotto lotto master", required = true) @RequestParam(value = "codLotto") Long codLotto) {

		HttpStatus status = HttpStatus.OK;
		ResponseMultilotto risultato = new ResponseMultilotto();
		logger.info("getLottiAccorpabili: inizio metodo");
		try {
			risultato = this.contrattiManager.getLottiAccorpabili(codGara, codLotto);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getLottiAccorpabili: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/presaInCaricoGaraDelegata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePresaInCaricoGaraDelegata> presaInCaricoGaraDelegata(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Form di presa in carico", required = true) @RequestBody PresaInCaricoGaraDelegataForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponsePresaInCaricoGaraDelegata risultato = new ResponsePresaInCaricoGaraDelegata();
		logger.info("presaInCaricoGaraDelegata: inizio metodo");
		try {
			risultato = this.simogManager.presaInCaricoGaraDelegata(form);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("presaInCaricoGaraDelegata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/accorpaMultilotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseAccorpaMultilotto> accorpaMultilotto(
			@RequestHeader("Authorization") String authorization, @RequestBody @Valid AccorpaMultilottoForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseAccorpaMultilotto risultato = new ResponseAccorpaMultilotto();
		logger.info("accorpaMultilotto: inizio metodo");
		try {
			risultato = this.contrattiManager.accorpaMultilotto(form.getCodGara(), form.getCodLottoMaster(),
					form.getCodLottoAccorpati(), authorization, form.getSyscon());
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("accorpaMultilotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRiepilogoAccorpamenti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseRiepilogoAccorpamenti> getRiepilogoAccorpamenti(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Codice della gara", required = true) @RequestParam(value = "codGara") Long codGara) {

		HttpStatus status = HttpStatus.OK;
		ResponseRiepilogoAccorpamenti risultato = new ResponseRiepilogoAccorpamenti();
		logger.info("getRiepilogoAccorpamenti: inizio metodo");
		try {
			risultato = this.contrattiManager.getRiepilogoAccorpamenti(codGara);
		} catch (Exception e) {
			logger.error(e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getRiepilogoAccorpamenti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/pulisciAccorpamentiMultilotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> cleanAccorpamentiMultilotto(
			@RequestHeader("Authorization") String authorization,
			@RequestBody @Valid PulisciAccorpamentiMultilottoForm form) {

		HttpStatus status = HttpStatus.OK;
		BaseResponse risultato = new ResponseAccorpaMultilotto();
		logger.info("cleanAccorpamentiMultilotto: inizio metodo");
		try {
			risultato = this.contrattiManager.cleanAccorpamentiMultilotto(form.getCodGara());
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("cleanAccorpamentiMultilotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, path = "/downloadDocumentoAtto", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<GeneralBaseResponse<String>> downloadAllegatoAtto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam("codGara") final Long codGara,
			@RequestParam("idAllegato") final Long idAllegato
	) {
		logger.debug("Execution start::downloadAllegatoAtto");

		HttpStatus status = HttpStatus.OK;
		GeneralBaseResponse<String> response = new GeneralBaseResponse<String>();

		try {
			boolean permission = hasPermission(codGara, authorization, xLoginMode);
			if (permission) {
				AllegatoEntry doc = contrattiManager.downloadDocumentoAtto(codGara, idAllegato);
				response.setEsito(true);
				response.setData(doc.getBinary());
			} else {
				response.setEsito(false);
				response.setErrorData(BaseResponse.ERROR_PERMISSION);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			response.setEsito(false);
			response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		logger.debug("Execution end::downloadAllegatoAtto");

		return ResponseEntity.status(status.value()).body(response);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, path = "/downloadDocumentoMisureAggiuntiveSicurezza", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<GeneralBaseResponse<String>> downloadDocumentoMisureAggiuntiveSicurezza(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam("codGara") final Long codGara, @RequestParam("codLotto") final Long codLotto,
			@RequestParam("codFase") final Long codFase,
			@RequestParam("numeroProgressivoFase") final Long numeroProgressivoFase,
			@RequestParam("numDoc") final Long numDoc) {
		logger.debug("Execution start::downloadDocumentoMisureAggiuntiveSicurezza");

		HttpStatus status = HttpStatus.OK;
		GeneralBaseResponse<String> response = new GeneralBaseResponse<String>();

		try {
			boolean permission = hasPermission(codGara, authorization, xLoginMode);
			if (permission) {
				DocumentoFaseEntry doc = this.fasiManager.downloadDocumentoMisureAggiuntiveSicurezza(codGara, codLotto,
						codFase, numeroProgressivoFase, numDoc);
				response.setEsito(true);
				response.setData(doc.getBinary());
			} else {
				response.setEsito(false);
				response.setErrorData(BaseResponse.ERROR_PERMISSION);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			response.setEsito(false);
			response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		logger.debug("Execution end::downloadDocumentoMisureAggiuntiveSicurezza");

		return ResponseEntity.status(status.value()).body(response);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaGareNonPaginata")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaGareNonPaginata> getListaGareNonPaginata(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "form di ricerca") GareNonPaginateSearchForm form) {

		logger.info("getListaGareNonPaginata: inizio metodo");

		ResponseListaGareNonPaginata risultato = new ResponseListaGareNonPaginata();
		HttpStatus status = HttpStatus.OK;
		if (form.getStazioneAppaltante() == null || form.getSyscon() == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = this.contrattiManager.getListaGareNonPaginata(form);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getListaGareNonPaginata: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/importaSmartcig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> importaSmartcig(@RequestHeader("Authorization") String authorization,
			@RequestBody ImportaSmartCigInsertForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseInsert risultato = new ResponseInsert();
		logger.info("importaSmartcig: inizio metodo");
		try {
			risultato = this.contrattiManager.importaSmartcigAnac(form.getCig(), form.getUsername(), form.getPassword(),
					form.getCodiceStazioneAppaltante(), form.isSaveCredentials(), form.getSyscon());
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("importaSmartcig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseAggiudicazione")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseAggiudicazione> getInizFaseAggiudicazione(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseAggiudicazione: inizio metodo");

		ResponseInizFaseAggiudicazione risultato = new ResponseInizFaseAggiudicazione();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseAggiudicazione(codGara, codLotto);
			if (!risultato.isEsito() && risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseAggiudicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizImportSmartcig")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizImportSmartcig> getInizImportSmartcig(
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "syscon") Long syscon) {

		logger.info("getInizImportSmartcig: inizio metodo");

		ResponseInizImportSmartcig risultato = new ResponseInizImportSmartcig();
		HttpStatus status = HttpStatus.OK;
		if (syscon == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		risultato = this.contrattiManager.getInizImportSmartcig(syscon);
		if (!risultato.isEsito() && risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getInizImportSmartcig: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getInizFaseAggiudicazioneSemp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInizFaseAggiudicazioneSemp> getInizFaseAggiudicazioneSemp(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("getInizFaseAggiudicazioneSemp: inizio metodo");

		ResponseInizFaseAggiudicazioneSemp risultato = new ResponseInizFaseAggiudicazioneSemp();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || codLotto == null) {
			risultato.setEsito(false);
			risultato.setErrorData("Errore validazione request");
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.fasiManager.getInizFaseAggiudicazioneSemp(codGara, codLotto);
			if (!risultato.isEsito() && risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		logger.info("getInizFaseAggiudicazioneSemp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/exportListaGareCsv")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResultCsv> exportListaGareCsv(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String xLoginMode,
			@ApiParam(value = "form di ricerca") GareSearchForm form) {

		logger.info("exportListaGareCsv: inizio metodo");

		ResponseResultCsv risultato = new ResponseResultCsv();
		HttpStatus status = HttpStatus.OK;
		if (form.getStazioneAppaltante() == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = this.contrattiManager.exportListaGare(form, authorization, xLoginMode);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("exportListaGareCsv: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/pubblicaAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> pubblicaAtto(
			@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestParam(value = "codGara") Long codgara,
			@RequestParam(value = "numPubb") Long numPubb
	) {

		logger.info("pubblicaAtti: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		if (codgara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = this.contrattiManager.pubblicaAtto(authorization, xLoginMode, codgara, numPubb);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		logger.info("pubblicaAtti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/pubblicaAtti")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePubbAtti> pubblicaAtti(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codgara") Long codgara,
			@RequestParam(value = "syscon") Long syscon,
			@RequestParam(value = "pubblicatoTutto") Boolean pubblicatoTutto) {

		logger.info("pubblicaAtti: inizio metodo");

		ResponsePubbAtti risultato = new ResponsePubbAtti();
		HttpStatus status = HttpStatus.OK;
		if (codgara == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}
		 
		risultato = this.contrattiManager.pubblicaAtti(authorization,codgara,syscon,pubblicatoTutto);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("pubblicaAtti: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFlussoGara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFlussoGara(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "id della gara", required = true) @RequestParam(value = "codGara") Long codGara) {

		logger.info("deleteFlussoGara: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		
		try {
			risultato = this.contrattiManager.deleteFlussoGara(codGara);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("deleteFlussoGara: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFlussoAtto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFlussoAtto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "id della gara", required = true) @RequestParam(value = "codGara") Long codGara,
			@ApiParam(value = "numero della pubblicazione", required = true) @RequestParam(value = "numPubb") Long numPubb) {

		logger.info("deleteFlussoAtto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		
		try {
			risultato = this.contrattiManager.deleteFlussoAtto(codGara,numPubb);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("deleteFlussoAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteFlussoLotto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteFlussoLotto(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "id della gara", required = true) @RequestParam(value = "codGara") Long codGara,
			@ApiParam(value = "id del lotto", required = true) @RequestParam(value = "codLotto") Long codLotto) {

		logger.info("deleteFlussoLotto: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		
		try {
			risultato = this.contrattiManager.deleteFlussoLotto(codGara,codLotto);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("deleteFlussoLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getReportIndicatori")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<GeneralBaseResponse<String>> getReportIndicatori(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Codice della gara", required = true) @RequestParam(value = "codGara") String codGara,
			@ApiParam(value = "Codice del lotto", required = true) @RequestParam(value = "codLotto") String codLotto,
			@ApiParam(value = "Cig del lotto", required = true) @RequestParam(value = "cig") String cig,
			@ApiParam(value = "Tipo report indicatori", required = true) @RequestParam(value = "tipoReport") String tipoReport,
			@ApiParam(value = "Codice Stazione Appaltante", required = true) @RequestParam(value = "stazioneAppaltante") String stazioneAppaltante) {

		HttpStatus status = HttpStatus.OK;
		GeneralBaseResponse<String> risultato = new GeneralBaseResponse<String>();
		logger.info("getReportIndicatori: inizio metodo");
		
		if ("anomalia".equals(tipoReport)) {
			risultato = this.contrattiManager.creaReportAnomalia(Long.valueOf(codGara), Long.valueOf(codLotto), cig, stazioneAppaltante);
	    } else {
	    	risultato = this.contrattiManager.creaReportPreliminare(Long.valueOf(codGara), Long.valueOf(codLotto), cig, stazioneAppaltante);
	    }					
		
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getReportIndicatori: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/isAttivoIndicatoriLotto")
	@ApiOperation(nickname = "ContrattiController.isAnagraficaGaraPubblicata", value = "Ritorna un booleano rappresentante lo stato di pubblicazione dell'anagrafica di gara")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 401, message = "Utente non autorizzato a lavorare sulla gara selezionata"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<GeneralBaseResponse<Boolean>> isAttivoIndicatoriLotto(
			@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization) {

		logger.info("isAttivoIndicatoriLotto: inizio metodo");

		HttpStatus status = HttpStatus.OK;
		GeneralBaseResponse<Boolean> risultato = new GeneralBaseResponse<Boolean>();
				
		risultato = contrattiManager.isAttivoIndicatoriLotto();
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("isAttivoIndicatoriLotto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	


	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaDeleghe")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaDeleghe> getListaDeleghe(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @ApiParam(value = "campi per la ricerca") DelegaSearchForm searchForm) {

		logger.info("getListaDeleghe: inizio metodo");

		ResponseListaDeleghe risultato = new ResponseListaDeleghe();
		HttpStatus status = HttpStatus.OK;

		boolean permission = hasPermissionSA(searchForm.getStazioneAppaltante(), authorization, xLoginMode);
		if (permission) {
			risultato = this.delegheManager.getListaDeleghe(searchForm);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}
		
		logger.info("getListaDeleghe: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDettaglioDelega")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDettaglioDelega> getDettaglioDelega(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "id") Integer id) {

		logger.info("getDettaglioDelega: inizio metodo");

		ResponseDettaglioDelega risultato = new ResponseDettaglioDelega();
		HttpStatus status = HttpStatus.OK;
		if (id == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = this.delegheManager.dettaglioDelega(id);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		logger.info("getDettaglioDelega: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/insertDelega")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInsert> insertDelega(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "campi di un delega da inserire") @RequestBody DelegaInsertForm form) {

		logger.info("insertDelega: inizio metodo");

		ResponseInsert risultato = new ResponseInsert();
		HttpStatus status = HttpStatus.OK;

		boolean permission = hasPermissionSA(form.getStazioneAppaltante(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.delegheManager.insertDelega(form);
			} catch (Exception e) {
				risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				risultato.setEsito(false);
			}
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("insertDelega: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteDelega")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteDelega(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "syscon") Long syscon, @RequestParam(value = "idProfilo") String idProfilo,
			@ApiParam(value = "id della delega", required = true) @RequestParam(value = "id") Integer id) {

		logger.info("deleteDelega: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;

		try {
			risultato = this.delegheManager.deleteDelega(syscon, idProfilo, id);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		logger.info("deleteDelega: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateDelega")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> updateDelega(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Campi di un delega da modificare", required = true) @RequestBody() @Valid DelegaInsertForm form) {

		logger.info("updateDelega: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermissionSA(form.getStazioneAppaltante(), authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.delegheManager.updateDelega(form);
			} catch (Exception e) {
				risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				risultato.setEsito(false);
			}
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("updateDelega: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/checkRup")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseListaCollaborazioni> checkRup(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "stazioneAppaltante") String stazioneAppaltante,
			@RequestParam(value = "cfStazioneAppaltante") String cfStazioneAppaltante, @RequestParam(value = "syscon") Long syscon,
			@RequestParam(value = "skipRpntLogin") boolean skipRpntLogin,
			@RequestParam(value = "simogUsername", required = false) String simogUsername,
			@RequestParam(value = "simogPassword", required = false) String simogPassword,
			@RequestParam(value = "simogSaveCredentials") boolean simogSaveCredentials) {

		HttpStatus status = HttpStatus.OK;
		ResponseListaCollaborazioni risultato = new ResponseListaCollaborazioni();
		logger.info("checkRup: inizio metodo");
		try {
			
			risultato = this.delegheManager.checkRup(syscon, stazioneAppaltante, cfStazioneAppaltante, skipRpntLogin, simogUsername,
					simogPassword, simogSaveCredentials);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("checkRup: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/pubblicaSchedaPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponsePubblicaFase> pubblicaSchedaPcp(@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "codFase") Long codFase, @RequestParam(value = "num") Long num,
			@RequestParam(value = "codProfilo") String codProfilo,
			@RequestParam(value = "cfImpersonato", required=false) String cfImpersonato,
			@RequestParam(value = "loaImpersonato", required=false) String loaImpersonato,
			@RequestParam(value = "idpImpersonato", required=false) String idpImpersonato,
			@RequestParam(value = "codein") String codein) {
		
		logger.info("pubblicaSchedaPcp: inizio metodo");
		
		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		HttpStatus status = HttpStatus.OK;
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);

		String cf = userAuthClaimsDTO.getCf();
		String loa = userAuthClaimsDTO.getLoa();
		String idp = userAuthClaimsDTO.getIdp();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if(StringUtils.isNotBlank(cfImpersonato) && StringUtils.isNotBlank(loaImpersonato) && StringUtils.isNotBlank(idpImpersonato) && (syscon != null && (syscon == 48 || syscon == 49))) {
			loa = loaImpersonato;
			idp = idpImpersonato;
			cf = cfImpersonato;
		}
		
		try {
			String version = this.contrattiManager.versionPcp();											
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						codein = "STD";
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
					if(idps == 0) {
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.verificaSchedaPcp(codGara,codLotto,codFase,num);
							if(risultato != null && risultato.getValidate() != null && this.contrattiManager.isValidazioneSuperata(risultato.getValidate())) {
								risultato = this.schedePcpManagerV102.pubblicaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, cfImpersonato);
							}
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.verificaSchedaPcp(codGara,codLotto,codFase,num);
							if(risultato != null && risultato.getValidate() != null && this.contrattiManager.isValidazioneSuperata(risultato.getValidate())) {
								risultato = this.schedePcpManagerV1021.pubblicaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, cfImpersonato);
							}
						}
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {
					if("1.0.2".equals(version)) {
						risultato = this.schedePcpManagerV102.verificaSchedaPcp(codGara,codLotto,codFase,num);
						if(risultato != null && risultato.getValidate() != null && this.contrattiManager.isValidazioneSuperata(risultato.getValidate())) {
							risultato = this.schedePcpManagerV102.pubblicaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, cfImpersonato);
						}
					} else if("1.0.2.1".equals(version)){
						risultato = this.schedePcpManagerV1021.verificaSchedaPcp(codGara,codLotto,codFase,num);
						if(risultato != null && risultato.getValidate() != null && this.contrattiManager.isValidazioneSuperata(risultato.getValidate())) {
							risultato = this.schedePcpManagerV1021.pubblicaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, cfImpersonato);
						}
					}
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}
						
		} catch (UnauthorizedUserException e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponsePcp.ERROR_PERMISSION);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null || (risultato != null && risultato.getValidate() != null && !risultato.getValidate().isEmpty())) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("pubblicaSchedaPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/cancellaSchedaPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponsePcp> cancellaSchedaPcp(@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "codFase") Long codFase, @RequestParam(value = "num") Long num,
			@RequestParam(value = "codProfilo") String codProfilo,
			@RequestParam(value = "cfImpersonato", required=false) String cfImpersonato,
			@RequestParam(value = "loaImpersonato", required=false) String loaImpersonato,
			@RequestParam(value = "idpImpersonato", required=false) String idpImpersonato,
			@RequestParam(value = "motivazione") String motivazione,
			@RequestParam(value = "codein") String codein) {
		
		logger.info("cancellaSchedaPcp: inizio metodo");
		
		BaseResponsePcp risultato = new BaseResponsePcp();
		HttpStatus status = HttpStatus.OK;
		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);

		String cf = userAuthClaimsDTO.getCf();
		String loa = userAuthClaimsDTO.getLoa();
		String idp = userAuthClaimsDTO.getIdp();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if(StringUtils.isNotBlank(cfImpersonato) && StringUtils.isNotBlank(loaImpersonato) && StringUtils.isNotBlank(idpImpersonato) && (syscon != null && (syscon == 48 || syscon == 49))) {
			loa = loaImpersonato;
			idp = idpImpersonato;
			cf = cfImpersonato;
		}
		
		try {
			String version = this.contrattiManager.versionPcp();
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						codein = "STD";
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
					if(idps == 0) {
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.cancellaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, motivazione);
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.cancellaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, motivazione);
						}
						
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {
					if("1.0.2".equals(version)) {
						risultato = this.schedePcpManagerV102.cancellaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, motivazione);
					} else if("1.0.2.1".equals(version)){
						risultato = this.schedePcpManagerV1021.cancellaSchedaPcp(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp, motivazione);
					}
					
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}	
			
			
			
			
		} catch (UnauthorizedUserException e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponsePcp.ERROR_PERMISSION);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("cancellaSchedaPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/existSimogEndpoint")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> existSimogEndpoint(@RequestHeader("X-loginMode") String xLoginMode,@RequestHeader("Authorization") String authorization) {

		logger.info("existSimogEndpoint: inizio metodo");

		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;		
		
		risultato = this.contrattiManager.checkExistSimogEndpoint();
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("existSimogEndpoint: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDelegatoPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseInfoDelegati> getDelegatoPcp(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@RequestParam(value = "codGara") Long codGara,
			@RequestParam(value = "cfImpersonato", required=false) String cfImpersonato,
			@RequestParam(value = "loaImpersonato", required=false) String loaImpersonato,
			@RequestParam(value = "idpImpersonato", required=false) String idpImpersonato,
			@RequestParam(value = "codein") String codein) {

		HttpStatus status = HttpStatus.OK;
		ResponseInfoDelegati risultato = new ResponseInfoDelegati();
		logger.info("getDelegatoPcp: inizio metodo");
		try {
			String version = this.contrattiManager.versionPcp();
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

			String cf = userAuthClaimsDTO.getCf();
			String loa = userAuthClaimsDTO.getLoa();
			String idp = userAuthClaimsDTO.getIdp();
			Long syscon = userAuthClaimsDTO.getSyscon();
			
			if(StringUtils.isNotBlank(cfImpersonato) && StringUtils.isNotBlank(loaImpersonato) && StringUtils.isNotBlank(idpImpersonato) && (syscon != null && (syscon == 48 || syscon == 49))) {
				loa = loaImpersonato;
				idp = idpImpersonato;
				cf = cfImpersonato;
			}
			
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						codein = "STD";
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
					if(idps == 0) {
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.getDelegatoPcp(codGara, syscon, cf, loa, idp);
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.getDelegatoPcp(codGara, syscon, cf, loa, idp);
						}
						
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {
					if("1.0.2".equals(version)) {
						risultato = this.schedePcpManagerV102.getDelegatoPcp(codGara, syscon, cf, loa, idp);
					} else if("1.0.2.1".equals(version)){
						risultato = this.schedePcpManagerV1021.getDelegatoPcp(codGara, syscon, cf, loa, idp);
					}
					
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}			
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("getDelegatoPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/saveDelegatoPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponsePcp> saveDelegatoPcp(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@RequestParam(value = "codGara") String codGara,
			@RequestParam(value = "codtec") String codtec,
			@RequestParam(value = "cfImpersonato", required=false) String cfImpersonato,
			@RequestParam(value = "loaImpersonato", required=false) String loaImpersonato,
			@RequestParam(value = "idpImpersonato", required=false) String idpImpersonato,
			@RequestParam(value = "codein") String codein,
			@RequestParam(value = "inserisciDelegato") Boolean inserisciDelegato) {

		HttpStatus status = HttpStatus.OK;
		BaseResponsePcp risultato = new BaseResponsePcp();
		logger.info("saveDelegatoPcp: inizio metodo");
		try {
			String version = this.contrattiManager.versionPcp();
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

			String cf = userAuthClaimsDTO.getCf();
			String loa = userAuthClaimsDTO.getLoa();
			String idp = userAuthClaimsDTO.getIdp();
			Long syscon = userAuthClaimsDTO.getSyscon();
			
			if(StringUtils.isNotBlank(cfImpersonato) && StringUtils.isNotBlank(loaImpersonato) && StringUtils.isNotBlank(idpImpersonato) && (syscon != null && (syscon == 48 || syscon == 49))) {
				loa = loaImpersonato;
				idp = idpImpersonato;
				cf = cfImpersonato;
			}
			
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						codein = "STD";
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
					if(idps == 0) {
						if(inserisciDelegato) {
							if("1.0.2".equals(version)) {
								risultato = this.schedePcpManagerV102.saveDelegatoPcp(codGara, codtec, syscon, cf, loa, idp);
							} else if("1.0.2.1".equals(version)){
								risultato = this.schedePcpManagerV1021.saveDelegatoPcp(codGara, codtec, syscon, cf, loa, idp);
							}
							
						} else {
							if("1.0.2".equals(version)) {
								risultato = this.schedePcpManagerV102.cancellaDelegatoPcp(codGara, syscon, cf, loa, idp);
							} else if("1.0.2.1".equals(version)){
								risultato = this.schedePcpManagerV1021.cancellaDelegatoPcp(codGara, syscon, cf, loa, idp);
							}
							
						}
						
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {
					if(inserisciDelegato) {
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.saveDelegatoPcp(codGara, codtec, syscon, cf, loa, idp);
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.saveDelegatoPcp(codGara, codtec, syscon, cf, loa, idp);
						}
						
					} else{
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.cancellaDelegatoPcp(codGara, syscon, cf, loa, idp);
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.cancellaDelegatoPcp(codGara, syscon, cf, loa, idp);
						}
						
					}
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}
			
			
							
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("saveDelegatoPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/impersonificaRup")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> impersonificaRup(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@RequestBody ImpersonificaRupForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseResult risultato = new ResponseResult();
		logger.info("impersonificaRup: inizio metodo");
		try {
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
			Long syscon = userAuthClaimsDTO.getSyscon();
			
			if(syscon != null && (syscon == 48 || syscon == 49)) {
				if(StringUtils.isNotBlank(form.getCfImpersonato()) && StringUtils.isNotBlank(form.getIdpImpersonato()) && StringUtils.isNotBlank(form.getLoaImpersonato())){
					risultato.setEsito(true);
					this.contrattiManager.impersonificaRupLogEventi(form,syscon);
				} else {
					risultato.setEsito(false);
				}				
			} else {
				risultato.setEsito(false);
			}
			
							
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("impersonificaRup: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/verificaStatoScheda")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponsePcp> verificaStatoScheda(@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara, @RequestParam(value = "codLotto") Long codLotto,
			@RequestParam(value = "codFase") Long codFase, @RequestParam(value = "num") Long num,
			@RequestParam(value = "codProfilo") String codProfilo,
			@RequestParam(value = "cfImpersonato", required=false) String cfImpersonato,
			@RequestParam(value = "loaImpersonato", required=false) String loaImpersonato,
			@RequestParam(value = "idpImpersonato", required=false) String idpImpersonato,
			@RequestParam(value = "codein") String codein) {
		
		logger.info("verificaStatoScheda: inizio metodo");
		
		BaseResponsePcp risultato = new BaseResponsePcp();
		HttpStatus status = HttpStatus.OK;

		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);

		String cf = userAuthClaimsDTO.getCf();
		String loa = userAuthClaimsDTO.getLoa();
		String idp = userAuthClaimsDTO.getIdp();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if(StringUtils.isNotBlank(cfImpersonato) && StringUtils.isNotBlank(loaImpersonato) && StringUtils.isNotBlank(idpImpersonato) && (syscon != null && (syscon == 48 || syscon == 49))) {
			loa = loaImpersonato;
			idp = idpImpersonato;
			cf = cfImpersonato;
		}
		
		
		
		try {
			String version = this.contrattiManager.versionPcp();
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						codein = "STD";
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
					if(idps == 0) {
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.verificaStatoScheda(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp);
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.verificaStatoScheda(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp);
						}
						
					} else{
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {					
					if("1.0.2".equals(version)) {
						risultato = this.schedePcpManagerV102.verificaStatoScheda(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp);	
					} else if("1.0.2.1".equals(version)){
						risultato = this.schedePcpManagerV1021.verificaStatoScheda(codGara,codLotto,codFase,num,syscon,codProfilo,cf, loa, idp);
					}
									
				}
			}else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}
			
			
			
			
		} catch (UnauthorizedUserException e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponsePcp.ERROR_PERMISSION);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("verificaStatoScheda: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/riallineaAnac")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseRiallineaAnac> riallineaAnac(@RequestHeader("Authorization") String authorization,
			@RequestHeader("X-loginMode") String loginMode,
			@RequestBody RiallineaAnacForm body) {

		HttpStatus status = HttpStatus.OK;
		ResponseRiallineaAnac risultato = new ResponseRiallineaAnac();
		logger.info("riallineaAnac: inizio metodo");
		try {
			String version = this.contrattiManager.versionPcp();
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

			String cf = userAuthClaimsDTO.getCf();
			String loa = userAuthClaimsDTO.getLoa();
			String idp = userAuthClaimsDTO.getIdp();
			Long syscon = userAuthClaimsDTO.getSyscon();
			
			if(StringUtils.isNotBlank(body.getCfImpersonato()) && StringUtils.isNotBlank(body.getLoaImpersonato()) && StringUtils.isNotBlank(body.getIdpImpersonato()) && (syscon != null && (syscon == 48 || syscon == 49))) {
				loa = body.getLoaImpersonato();
				idp = body.getIdpImpersonato();
				cf = body.getCfImpersonato();
			}
			
			Boolean cancellaDatiEse = body.getCancellaDatiEse();
			
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						body.setCodein("STD");
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, body.getCodein());
					if(idps == 0) {
						risultato = this.importAppaltoPcpManager.riallineaAnac(body.getCodGara(), syscon, cf, loa, idp, cancellaDatiEse);
					} else {
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {
					risultato = this.importAppaltoPcpManager.riallineaAnac(body.getCodGara(), syscon, cf, loa, idp, cancellaDatiEse);
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}			
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		logger.info("riallineaAnac: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/presaCarico")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponsePcp> presaCarico(@RequestHeader("X-loginMode") String xLoginMode, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "codGara") Long codGara,		
			@RequestParam(value = "cfImpersonato", required=false) String cfImpersonato,
			@RequestParam(value = "loaImpersonato", required=false) String loaImpersonato,
			@RequestParam(value = "idpImpersonato", required=false) String idpImpersonato,
			@RequestParam(value = "codein") String codein) {
		
		logger.info("verificaStatoScheda: inizio metodo");
		
		BaseResponsePcp risultato = new BaseResponsePcp();
		HttpStatus status = HttpStatus.OK;

		UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);

		String cf = userAuthClaimsDTO.getCf();;
		String loa = userAuthClaimsDTO.getLoa();
		String idp = userAuthClaimsDTO.getIdp();
		Long syscon = userAuthClaimsDTO.getSyscon();
		
		if(StringUtils.isNotBlank(cfImpersonato) && StringUtils.isNotBlank(loaImpersonato) && StringUtils.isNotBlank(idpImpersonato) && (syscon != null && (syscon == 48 || syscon == 49))) {
			loa = loaImpersonato;
			idp = idpImpersonato;
			cf = cfImpersonato;
		}
		
		
		try {
			String version = this.contrattiManager.versionPcp();
			if(loa != null && (loa.equals("3") || loa.equals("4"))) {
				if(StringUtils.isNotBlank(idp) && idp.toUpperCase().equals("CUSTOM")) {
					String multiEnte = this.contrattiManager.getMultiEnteConfig();
					if(multiEnte != null && "0".equals(multiEnte)) {
						codein = "STD";
					}
					Long idps = this.contrattiManager.getIdpUserLoa(syscon, codein);
					if(idps == 0) {
						if("1.0.2".equals(version)) {
							risultato = this.schedePcpManagerV102.presaCarico(codGara,syscon,cf , loa, idp);
						} else if("1.0.2.1".equals(version)){
							risultato = this.schedePcpManagerV1021.presaCarico(codGara,syscon,cf , loa, idp);
						}
						
					} else{
						risultato.setEsito(false);
						risultato.setErrorData(BaseResponse.ERROR_PERMISSION_IDP);
					}
				} else {					
					if("1.0.2".equals(version)) {
						risultato = this.schedePcpManagerV102.presaCarico(codGara,syscon,cf, loa, idp);	
					} else if("1.0.2.1".equals(version)){
						risultato = this.schedePcpManagerV1021.presaCarico(codGara,syscon,cf, loa, idp);
					}
									
				}
			}else {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_PERMISSION_LOA);
			}
			
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("verificaStatoScheda: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/getDatiAppaltoPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseAppaltoPcp> getDatiAppaltoPcp(
			@ApiParam(value = "", required = true) @RequestBody DatiGaraPcpForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseAppaltoPcp risultato = new ResponseAppaltoPcp();
		logger.info("getDatiAppaltoPcp: inizio metodo");
		try {
			
			String version = this.contrattiManager.versionPcp();
						           	
            Claims tempClaims = this.contrattiManager.decryptJwt(form.getValue()).getPayload();         
            
			String cfSa =  tempClaims.get("cfSA") != null ? tempClaims.get("cfSA").toString() : null;
			String idAppalto = tempClaims.get("idAppalto") != null ? tempClaims.get("idAppalto").toString() : null;
			String cig = tempClaims.get("cig") != null ? tempClaims.get("cig").toString() : null;
			String cf = tempClaims.get("cf") != null ? tempClaims.get("cf").toString() : null;
			String loa = tempClaims.get("loa") != null ? tempClaims.get("loa").toString() : null;
			String idp = tempClaims.get("idp") != null ? tempClaims.get("idp").toString() : null;
			String codausa = tempClaims.get("codAusa") != null ? tempClaims.get("codAusa").toString() : null;
			Long syscon = userManager.getSysconFromLoginOrCf(cf);
			

			risultato = this.importAppaltoPcpManager.getDatiAppaltoPcp(cfSa, idAppalto, cig, syscon, cf, loa, idp, codausa);
									
		} catch (Exception e) {
			logger.error("errore nel metodo getDatiGaraPcp - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("getDatiAppaltoPcp: {}",risultato.getErrorData());
		}
		
		
		logger.info("getDatiGaraPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteAppaltoPcp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<BaseResponse> deleteAppaltoPcp(@RequestHeader("X-loginMode") String xLoginMode,
												   @RequestHeader("Authorization") String authorization,
												   @ApiParam(value = "id della gara", required = true) @RequestParam(value = "codGara") Long codGara) {

		logger.info("deleteAppaltoPcp: inizio metodo");

		BaseResponse risultato = new BaseResponse();
		HttpStatus status = HttpStatus.OK;
		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			try {
				risultato = this.schedePcpManagerV102.deleteAppaltoPcp(codGara);
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
		logger.info("deleteAppaltoPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getListaSchedeTrasmessePcp")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
	})
	public ResponseEntity<ResponseSchedeTrasmessePcp> getSchedeTrasmessePcp(
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Form di ricerca per schede trasmesse a PCP") SchedeTrasmessePcpForm form) {

		HttpStatus status = HttpStatus.OK;
		ResponseSchedeTrasmessePcp risultato = new ResponseSchedeTrasmessePcp();
		logger.info("getSchedeTrasmessePcp: inizio metodo");
		try {

			risultato = this.contrattiManager.searchSchedeTrasmessePcp(form, authorization, xLoginMode);

		} catch (Exception e) {
			logger.error("errore nel metodo getSchedeTrasmessePcp - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("getDatiAppaltoPcp: {}",risultato.getErrorData());
		}

		logger.info("getDatiGaraPcp: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	protected boolean hasPermissionSA(String codiceSA, String authorization, String loginMode) {
		return true;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getDatiContabilita")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseDatiContabilita> getDatiContabilita(@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization, @RequestParam(value = "codGara") Long codGara, @RequestParam(value = "cig") String cig) {

		logger.info("getDatiContabilita: inizio metodo");

		ResponseDatiContabilita risultato = new ResponseDatiContabilita();
		HttpStatus status = HttpStatus.OK;
		if (codGara == null || cig == null) {
			risultato.setEsito(false);
			status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status.value()).body(risultato);
		}

		boolean permission = hasPermission(codGara, authorization, xLoginMode);
		if (permission) {
			risultato = this.contrattiManager.getDatiContabilita(cig);
			if (risultato.getErrorData() != null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION);
			status = HttpStatus.UNAUTHORIZED;
		}

		logger.info("getDatiContabilita: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteAttoSingolo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
	})
	public ResponseEntity<ResponseResult> deleteAttoSingolo(
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@RequestParam("codGara") Long codGara,
			@RequestParam("numPubb") Long numPubb
	) {

		HttpStatus status = HttpStatus.OK;
		ResponseResult risultato = new ResponseResult();
		logger.info("deleteAttoSingolo: inizio metodo");
		try {

			risultato = this.contrattiManager.deleteAttoSingolo(codGara, numPubb);

		} catch (Exception e) {
			logger.error("errore nel metodo deleteAttoSingolo - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("deleteAttoSingolo: {}",risultato.getErrorData());
		}

		logger.info("deleteAttoSingolo: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/programmaPubbAtto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
	})
	public ResponseEntity<ResponseResult> programmaPubbAtto(
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@RequestParam("codGara") Long codGara,
			@RequestParam("numPubb") Long numPubb,
			@RequestParam("dataProgrammazione") Date dataProgrammazione
	) {

		HttpStatus status = HttpStatus.OK;
		ResponseResult risultato = new ResponseResult();
		logger.info("programmaPubbAtto: inizio metodo");
		try {

			risultato = this.contrattiManager.programmaPubblicazioneAtto(codGara, numPubb, dataProgrammazione);

		} catch (Exception e) {
			logger.error("errore nel metodo programmaPubbAtto - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("programmaPubbAtto: {}",risultato.getErrorData());
		}

		logger.info("programmaPubbAtto: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/annullaPubblicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
	})
	public ResponseEntity<ResponseResult> annullaPubblicazione(
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@RequestParam("codGara") Long codGara,
			@RequestParam("numPubb") Long numPubb
	) {

		HttpStatus status = HttpStatus.OK;
		ResponseResult risultato = new ResponseResult();
		logger.info("programmaPubbAtto: inizio metodo");
		try {

			risultato = this.contrattiManager.annullaPubblicazione(codGara, numPubb);

		} catch (Exception e) {
			logger.error("errore nel metodo annullaPubblicazione - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("annullaPubblicazione: {}",risultato.getErrorData());
		}

		logger.info("annullaPubblicazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/annullaPubblicazioneMotivazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)")
	})
	public ResponseEntity<ResponseResult> annullaPubblicazioneMotivazione(
			@RequestHeader("X-loginMode") String xLoginMode,
			@RequestHeader("Authorization") String authorization,
			@RequestParam("codGara") Long codGara,
			@RequestParam("numPubb") Long numPubb,
			@RequestParam("motivoCanc") String motivoCanc
	) {

		HttpStatus status = HttpStatus.OK;
		ResponseResult risultato = new ResponseResult();
		logger.info("annullaPubblicazioneMotivazione: inizio metodo");
		try {

			risultato = this.contrattiManager.annullaPubblicazioneMotivazione(authorization, xLoginMode, codGara, numPubb, motivoCanc);

		} catch (Exception e) {
			logger.error("errore nel metodo annullaPubblicazioneMotivazione - ContrattiController",e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("annullaPubblicazioneMotivazione: {}",risultato.getErrorData());
		}

		logger.info("annullaPubblicazioneMotivazione: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

}