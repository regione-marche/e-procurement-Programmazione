package it.maggioli.ssointegrms.controllers.oneGateway;

import java.util.Map;

import it.maggioli.ssointegrms.controllers.AbstractBaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.common.ExceptionCodes;
import it.maggioli.ssointegrms.exceptions.oneGateway.DecryptException;
import it.maggioli.ssointegrms.exceptions.oneGateway.TokenExpiredException;
import it.maggioli.ssointegrms.model.PostFormObject;
import it.maggioli.ssointegrms.model.ResponseError;
import it.maggioli.ssointegrms.model.ResponseSuccess;
import it.maggioli.ssointegrms.services.oneGateway.CryptoService;
import it.maggioli.ssointegrms.services.oneGateway.GatewayService;
import it.maggioli.ssointegrms.services.oneGateway.ServiceProviderService;

/**
 * Controller per l'autenticazione al sistema OneGateway
 * 
 * @author Cristiano Perin
 *
 */
@Controller
@RequestMapping("/oneGateway/${application.oneGateway.apiVersion}")
public class OneGatewayController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OneGatewayController.class);

	@Value("${application.baseUrl}")
	private String applicationBaseUrl;

	@Value("${application.errorContext}")
	private String errorContext;

	@Autowired
	private ServiceProviderService serviceProviderService;

	@Autowired
	private CryptoService cryptoService;

	private GatewayService spidGatewayService;
	private GatewayService cnsGatewayService;
	private GatewayService cieGatewayService;

	public OneGatewayController(@Qualifier("spidGatewayService") GatewayService spidGatewayService,
			@Qualifier("cnsGatewayService") GatewayService cnsGatewayService,
			@Qualifier("cieGatewayService") GatewayService cieGatewayService) {
		super();
		this.spidGatewayService = spidGatewayService;
		this.cnsGatewayService = cnsGatewayService;
		this.cieGatewayService = cieGatewayService;
	}

	/**
	 * Metodo che esegue l'autenticazione
	 * 
	 * @param clientId Client ID richiesta autenticazione
	 * @param authenticationType Tipologia autenticazione
	 * @return Redirect alla pagina di autenticazione
	 */
	@GetMapping("/auth")
	public RedirectView executeAuthentication(@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "authenticationType") String authenticationType) {

		LOGGER.info("Execution start OneGatewayController::executeAuthentication() clientId {}, authenticationType {}",
				clientId, authenticationType);

		if (!serviceProviderService.existsClientId(clientId)) {
			LOGGER.error(ExceptionCodes.MIMS_AUTH_001, clientId);
			throw new IllegalArgumentException(ExceptionCodes.MIMS_AUTH_001);
		}

		String url = "";

		if (OneGatewayAppConstants.AUTH_TYPE_SPID.equals(authenticationType)) {
			url = spidGatewayService.authenticate(clientId);
		} else if (OneGatewayAppConstants.AUTH_TYPE_CNS.equals(authenticationType)) {
			url = cnsGatewayService.authenticate(clientId);
		} else if (OneGatewayAppConstants.AUTH_TYPE_CIE.equals(authenticationType)) {
			url = cieGatewayService.authenticate(clientId);
		} else {
			LOGGER.error(ExceptionCodes.MIMS_AUTH_002, authenticationType);
			throw new IllegalArgumentException(ExceptionCodes.MIMS_AUTH_002);
		}

		return new RedirectView(url);
	}

	@PostMapping("/{mimsClientId}")
	public String callbackSuccess(Model model, @PathVariable(value = "mimsClientId") String mimsClientId,
			@ModelAttribute(value = "authResponse") String jwtResponse) {
		LOGGER.info("Execution start OneGatewayController::callbackSuccess() mimsClientId [ {} ]", mimsClientId);

		if (!serviceProviderService.existsMimsClientId(mimsClientId)) {
			LOGGER.error(ExceptionCodes.MIMS_AUTH_003, mimsClientId);
			throw new IllegalArgumentException(ExceptionCodes.MIMS_AUTH_003);
		}

		if (StringUtils.isNotBlank(jwtResponse)) {
			try {
				Map<String, Object> map = cryptoService.decryptAndVerifyIdpJwtToken(mimsClientId, jwtResponse);
				LOGGER.debug("Map {}", map);
				ResponseSuccess rs = getObjectMapper().convertValue(map, ResponseSuccess.class);
				LOGGER.debug("Success {}", rs);

				super.checkTokenExpiration(rs);

				String spJwtToken = cryptoService.encryptSpJwtToken(rs);
				LOGGER.debug("SpJwtToken {}", spJwtToken);

				String url = serviceProviderService.getRedirectUrlByMimsClientId(mimsClientId);
				
				LOGGER.info("Redirect URL {}", url);

				PostFormObject pfo = new PostFormObject(spJwtToken);

				model.addAttribute("redirectUrl", url);
				model.addAttribute("formObject", pfo);

				return "post-request";
			} catch (DecryptException e) {
				LOGGER.error("Errore DecryptException", e);
			} catch (TokenExpiredException e) {
				LOGGER.error("Errore TokenExpiredException", e);
			}
		}

		return applicationBaseUrl;
	}

	@PostMapping("/errore/{mimsClientId}")
	public RedirectView callbackError(@PathVariable(value = "mimsClientId") String mimsClientId,
			@ModelAttribute(value = "authResponse") String jwtResponse) {
		LOGGER.info("Execution start OneGatewayController::callbackError()");

		if (StringUtils.isNotBlank(jwtResponse)) {
			try {
				Map<String, Object> map = cryptoService.decryptAndVerifyIdpJwtToken(mimsClientId, jwtResponse);
				ResponseError re = getObjectMapper().convertValue(map, ResponseError.class);
				LOGGER.info("Error {}", re);

				String url = UriComponentsBuilder.fromHttpUrl(applicationBaseUrl + errorContext)
						.queryParam("errorCode", re.getErrorCode()).queryParam("errorMessage", re.getErrorMessage())
						.toUriString();

				return new RedirectView(url);
			} catch (DecryptException e) {
				LOGGER.error("Errore DecryptException", e);
			}
		}

		return new RedirectView(applicationBaseUrl);
	}

}
