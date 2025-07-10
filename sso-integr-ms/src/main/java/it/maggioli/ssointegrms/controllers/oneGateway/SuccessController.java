package it.maggioli.ssointegrms.controllers.oneGateway;

import java.util.Map;

import it.maggioli.ssointegrms.controllers.AbstractBaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.maggioli.ssointegrms.common.ExceptionCodes;
import it.maggioli.ssointegrms.exceptions.oneGateway.DecryptException;
import it.maggioli.ssointegrms.exceptions.oneGateway.TokenExpiredException;
import it.maggioli.ssointegrms.model.PostFormObject;
import it.maggioli.ssointegrms.model.ResponseSuccess;
import it.maggioli.ssointegrms.services.oneGateway.CryptoService;
import it.maggioli.ssointegrms.services.oneGateway.ServiceProviderService;

/**
 * Controller temporaneo per la gestione del success
 * 
 * @author Cristiano Perin
 *
 */
//@Controller
public class SuccessController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuccessController.class);

	@Value("${application.baseUrl}")
	private String applicationBaseUrl;

	@Autowired
	private CryptoService cryptoService;

	@Autowired
	private ServiceProviderService serviceProviderService;

	public SuccessController() {
		super();
	}

	@PostMapping("/{mimsClientId}/it/access_administrations.page")
	public String callbackSuccess(Model model, @PathVariable(value = "mimsClientId") String mimsClientId,
			@ModelAttribute(value = "authResponse") String jwtResponse) {
		LOGGER.info("Execution start SuccessController::callbackSuccess()");

		mimsClientId = "SCPSA_coll";
		if (!serviceProviderService.existsMimsClientId(mimsClientId)) {
			LOGGER.error(ExceptionCodes.MIMS_AUTH_003, mimsClientId);
			throw new IllegalArgumentException(ExceptionCodes.MIMS_AUTH_003);
		}

		if (StringUtils.isNotBlank(jwtResponse)) {
			try {
				Map<String, Object> map = cryptoService.decryptAndVerifyIdpJwtToken(mimsClientId, jwtResponse);
				ResponseSuccess rs = getObjectMapper().convertValue(map, ResponseSuccess.class);
				LOGGER.info("Success {}", rs);

				super.checkTokenExpiration(rs);

				String spJwtToken = cryptoService.encryptSpJwtToken(rs);
				LOGGER.info("SpJwtToken {}", spJwtToken);

				String url = serviceProviderService.getRedirectUrlByMimsClientId(mimsClientId);

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
}
