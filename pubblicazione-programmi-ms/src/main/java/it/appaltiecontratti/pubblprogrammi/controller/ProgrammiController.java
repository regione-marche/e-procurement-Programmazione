package it.appaltiecontratti.pubblprogrammi.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.appaltiecontratti.pubblprogrammi.bl.AccountManager;
import it.appaltiecontratti.pubblprogrammi.bl.ProgrammiManager;
import it.appaltiecontratti.pubblprogrammi.bl.ValidateManager;
import it.appaltiecontratti.pubblprogrammi.entity.LoginResult;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicaProgrammaFornitureServiziEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicaProgrammaLavoriEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicazioneResult;
import it.appaltiecontratti.pubblprogrammi.entity.ResponseResult;
import it.appaltiecontratti.pubblprogrammi.entity.TokenValidationResult;
import it.appaltiecontratti.pubblprogrammi.entity.UsrSysEntry;
import it.appaltiecontratti.pubblprogrammi.entity.ValidateEntry;

/**
 * Servizio REST esposto al path "/Avvisi".
 */

@SuppressWarnings("java:S5122")
@CrossOrigin
@RestController
@RequestMapping(value = "${protected.context-path}/pubblicazioneProgrammi")
public class ProgrammiController {

	/** Logger di classe. */
	protected Logger logger = LogManager.getLogger(ProgrammiController.class);

	@Autowired
	ServletContext context;

	/**
	 * Wrapper della business logic a cui viene demandata la gestione
	 */
	private ProgrammiManager programmiManager;

	/**
	 * @param avvisiManager avvisiManager da settare internamente alla classe.
	 */
	@Required
	@Autowired
	public void setAvvisiManager(ProgrammiManager programmiManager) {
		this.programmiManager = programmiManager;
	}

	/**
	 * Wrapper della business logic a cui viene demandata la gestione
	 */
	protected ValidateManager validateManager;

	/**
	 * @param validateManager validateManager da settare internamente alla classe.
	 */
	@Required
	@Autowired
	public void setValidateManager(ValidateManager validateManager) {
		this.validateManager = validateManager;
	}

	/**
	 * Wrapper della business logic a cui viene demandata la gestione
	 */
	protected AccountManager accountManager;

	/**
	 * @param validateManager validateManager da settare internamente alla classe.
	 */
	@Required
	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/PubblicaLavori")
	@ApiOperation(nickname = "ProgrammiRestService.pubblicaLavori", value = "Pubblica i dati e i documenti relativi ad un programma di lavori", notes = "Ritorna il risultato della pubblicazione e l'id assegnato dal sistema, che dovrà essere riutilizzato per successive pubblicazioni", response = PubblicazioneResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<PubblicazioneResult> pubblicaLavori(@RequestHeader("Authorization") String authorization,
			@RequestHeader("applicationToken") String applicationToken,
			@ApiParam(value = "Programma da pubblicare [Model=PubblicaProgrammaLavoriEntry]", required = true) @RequestBody PubblicaProgrammaLavoriEntry programma,
			@ApiParam(value = "Se valorizzato a '1' effettua solo il controllo dei dati, '2' effettua controllo e pubblicazione", required = true) @RequestParam("modalitaInvio") String modalitaInvio

	) throws ParseException, IOException {

		PubblicazioneResult risultato = new PubblicazioneResult();
		HttpStatus status = HttpStatus.OK;

		// FASE PRELIMINARE DI CONTROLLO DEI PARAMETRI PRIMA DI INOLTRARE ALLA
		// BUSINESS LOGIC
		
			logger.info("pubblicaLavori(" + programma.getId() + "): inizio metodo");
		
		TokenValidationResult tokenValidate = validateToken(applicationToken);
		if(!tokenValidate.isValidated()){
			status = HttpStatus.UNAUTHORIZED;
			risultato = new PubblicazioneResult();
			risultato.setErrorData(tokenValidate.getError());
			return ResponseEntity.status(status.value()).body(risultato);
		}
		List<ValidateEntry> controlli = new ArrayList<ValidateEntry>();
		programma.setClientId(tokenValidate.getClientId());
		programma.setSyscon(tokenValidate.getSyscon());
		this.validateManager.validatePubblicaProgrammaLavori(programma, controlli);
		if (!isValid(controlli)) {
			// se non supero la validazione
			risultato = new PubblicazioneResult();
			risultato.setErrorData(PubblicazioneResult.ERROR_VALIDATION);
			risultato.setValidate(controlli);
		} else {
			if (modalitaInvio != null && modalitaInvio.equals("2")) {
				// procedo con l'inserimento
				try {
					risultato = this.programmiManager.pubblicaLavori(programma);
					this.programmiManager.AggiornaImportiProgramma(programma.getContri(), new Long(1));
					// aggiorno importi e genero pdf
					try {
						this.programmiManager.creaPdfNuovaNormativa(programma.getId(),programma.getContri(),
						 new Long(1), programma.getIdRicevuto(), context);
					} catch (Exception ex) {
						logger.error("Errore durante la generazione PDF del programma dm 11/2011", ex);
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

		logger.info("pubblicaLavori: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	private boolean isValid(List<ValidateEntry> validate) {
		
		if(validate != null) {
			for(ValidateEntry entry : validate) {
				if(entry.getTipo().equals("E")) {
					return false;
				}
			}
		}
		return true;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/PubblicaFornitureServizi")
	@ApiOperation(nickname = "ProgrammiRestService.pubblicaFornitureServizi", value = "Pubblica i dati e i documenti relativi ad un programma di forniture e servizi", notes = "Ritorna il risultato della pubblicazione e l'id assegnato dal sistema, che dovrà essere riutilizzato per successive pubblicazioni", response = PubblicazioneResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<PubblicazioneResult> pubblicaFornitureServizi(@RequestHeader("Authorization") String authorization,
			@RequestHeader("applicationToken") String applicationToken,
			@ApiParam(value = "Programma da pubblicare [Model=PubblicaProgrammaFornitureServiziEntry]", required = true) @RequestBody PubblicaProgrammaFornitureServiziEntry programma,
			@ApiParam(value = "Se valorizzato a '1' effettua solo il controllo dei dati, '2' effettua controllo e pubblicazione", required = true) @RequestParam("modalitaInvio") String modalitaInvio)
			throws ParseException, IOException {

		PubblicazioneResult risultato = new PubblicazioneResult();

		HttpStatus status = HttpStatus.OK;

		
		logger.info("pubblicaFornitureServizi(" + programma.getId() + "): inizio metodo");
		

		List<ValidateEntry> controlli = new ArrayList<ValidateEntry>();
		TokenValidationResult tokenValidate = validateToken(applicationToken);
		if(!tokenValidate.isValidated()){
			status = HttpStatus.UNAUTHORIZED;
			risultato = new PubblicazioneResult();
			risultato.setErrorData(tokenValidate.getError());
			return ResponseEntity.status(status.value()).body(risultato);
		}
		programma.setClientId(tokenValidate.getClientId());
		programma.setSyscon(tokenValidate.getSyscon());
		this.validateManager.validatePubblicaProgrammaFornitureServizi(programma, controlli);
		if (!isValid(controlli)) {
			// se non supero la validazione
			risultato = new PubblicazioneResult();
			risultato.setErrorData(PubblicazioneResult.ERROR_VALIDATION);
			risultato.setValidate(controlli);
		} else {
			if (modalitaInvio != null && modalitaInvio.equals("2")) {
				// procedo con l'inserimento
				try {
					risultato = this.programmiManager.pubblicaFornitureServizi(programma);
					this.programmiManager.AggiornaImportiProgramma(programma.getContri(), new Long(2));
					// aggiorno importi e genero pdf
					try {
						this.programmiManager.creaPdfNuovaNormativa(programma.getId(),programma.getContri(),
								new Long(2), programma.getIdRicevuto(), context);
					} catch (Exception ex) {
						logger.error("Errore durante la generazione PDF del programma dm 11/2011", ex);
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

		logger.info("pubblicaFornitureServizi: fine metodo [http status " + status.value() + "]");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	
	@RequestMapping( method = RequestMethod.GET, value = "/serviceAccessPubblica")
	@ApiOperation(nickname = "AvvisiRestService.serviceAccessPubblica", value="Verifica le credenziali per l'accesso al servizio di pubblicazione", notes = "Ritorna l'esito della verifica delle credenziali")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<LoginResult> loginPubblica(
		@RequestHeader("Authorization") String authorization,	
		@ApiParam(value = "Username utente", required = true) @RequestParam("username") String username,
		@ApiParam(value = "Password utente", required = true) @RequestParam("password") String password,
		@ApiParam(value = "Client/Application key", required = true) @RequestParam("clientKey") String clientKey,
		@ApiParam(value = "Client/Application Id", required = true) @RequestParam("clientId") String clientId) {

		
		logger.info("serviceAccessPubblica(" + username + "," + password + "): inizio metodo");
		
		LoginResult risultato = userLogin(username, password);

		HttpStatus status = HttpStatus.OK;
		if (risultato.getErrorData() != null) {
			if (LoginResult.ERROR_NOT_FOUND.equals(risultato.getErrorData())) {
				status = HttpStatus.NOT_FOUND;
			} else if (LoginResult.ERROR_USERNAME_PASSWORD_EMPTY
					.equals(risultato.getErrorData())) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			logger.info("loginPubblica: fine metodo [http status "
					+ status.value() + "]");
			return ResponseEntity.status(status.value()).body(risultato);
		}

		risultato = applicationLogin(clientId, clientKey);
		
		if (risultato.getErrorData() != null) {
			if (LoginResult.ERROR_NOT_FOUND.equals(risultato.getErrorData())) {
				status = HttpStatus.NOT_FOUND;
			} else if (LoginResult.ERROR_CLIENTID_CLIENTKEY_EMPTY
					.equals(risultato.getErrorData())) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			logger.info("serviceAccessPubblica: fine metodo [http status "
					+ status.value() + "]");
			return ResponseEntity.status(status.value()).body(risultato);
		}
		try {
			byte[] jwtKey = accountManager.getJWTKey();
			risultato.setToken(getToken(username, clientId, jwtKey));
			logger.info("loginPubblica: fine metodo [http status "
					+ status.value() + "]");
			return ResponseEntity.status(status.value()).body(risultato);
		} catch (Exception e) {
			logger.error("Errore in generazione JWT TOKEN",e);
			risultato.setErrorData(e.getMessage());
			risultato.setEsito(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return ResponseEntity.status(status.value()).body(risultato);
		}

	}

	private LoginResult userLogin(String user, String pwd){
		LoginResult risultato = null;
		String username = StringUtils.stripToNull(user);
		String password = StringUtils.stripToNull(pwd);
		if (username == null || password == null) {
			risultato = new LoginResult();
			risultato.setErrorData(LoginResult.ERROR_USERNAME_PASSWORD_EMPTY);
		} else {
			username = username.toUpperCase();
			risultato = accountManager.getLoginPubblica(username, password);
		}
		return risultato;
	}
	
	private LoginResult applicationLogin(String clientId, String clientKey){
		LoginResult risultato = null;
		String username = StringUtils.stripToNull(clientId);
		String password = StringUtils.stripToNull(clientKey);
		if (username == null || password == null) {
			risultato = new LoginResult();
			risultato.setErrorData(LoginResult.ERROR_CLIENTID_CLIENTKEY_EMPTY);
		} else {
			risultato = accountManager.getLoginApplicazione(username, password);
		}
		return risultato;
	}
	
	private static final long ttlMillis = 3600000;

	public String getToken(String username, String clientId, byte[] jwtKey) {

		String issuer = null;
		try {
			issuer = InetAddress.getLocalHost().getHostAddress()
					+ "/Account/LoginPubblica";
		} catch (UnknownHostException e) {
			logger.info("loginPubblica: metodo creazione token. Impossibile trovare HostName");
			issuer = "UnknownHost";
		}
		SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);
		UsrSysEntry sysEntry = accountManager.getUsrSysEntry(username);
		long nowMillis = System.currentTimeMillis();
		JwtBuilder builder = Jwts.builder().issuedAt(new Date(nowMillis))
				.subject(username).issuer(issuer)
				.signWith(secretKey, getSignatureAlgorithm("HS512"))
				.expiration(getExpDate(nowMillis, ttlMillis))
				.claim("syscon", sysEntry.getSyscon())
				.claim("modificaArchivioStazioneAppaltante", !sysEntry.getSyspwbou().contains("ou214"))
				.claim("clientId", clientId);
		return builder.compact();
	}

	private static Date getExpDate(long nowMillis, long ttlMillis) {
		return new Date(nowMillis + ttlMillis);
	}
	
	public TokenValidationResult validateToken(String token){
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
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/health")
	@ApiOperation(nickname = "AvvisiController.health", value = "Monitora lo stato di salute dell'applicazione", response = ResponseResult.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> health() {

		
			logger.info("health: inizio metodo");
		
		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		risultato = programmiManager.health();
		if (risultato.getErrorData() != null) {
			status = HttpStatus.BAD_REQUEST;
		}
		logger.info("health: fine metodo");
		return ResponseEntity.status(status.value()).body(risultato);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getPubblicazioneLink")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Errore nell'avvio dell'applicazione"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public ResponseEntity<ResponseResult> getPubblicazioneLink(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "id_ricevuto", required = true) @RequestParam("idRicevuto") Long idRicevuto) {

		
		logger.info("getPubblicazioneLink: inizio metodo");
		
		ResponseResult risultato = new ResponseResult();
		HttpStatus status = HttpStatus.OK;
		risultato = programmiManager.getPubblicazioneLink(idRicevuto);
		if (risultato.getErrorData() != null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("getPubblicazioneLink: fine metodo");
		return ResponseEntity.status(status.value()).body(risultato);
	}

	private MacAlgorithm getSignatureAlgorithm(final String signatureAlgorithm) {
		if (StringUtils.isNotBlank(signatureAlgorithm) && signatureAlgorithmAllowed(signatureAlgorithm)) {
			return (MacAlgorithm) Jwts.SIG.get().forKey(signatureAlgorithm);
		}
		return (MacAlgorithm) Jwts.SIG.get().forKey("HS512");
	}

	private boolean signatureAlgorithmAllowed(final String signatureAlgorithm) {
		return "HS256".equals(signatureAlgorithm) || "HS384".equals(signatureAlgorithm) || "HS512".equals(signatureAlgorithm);
	}
}