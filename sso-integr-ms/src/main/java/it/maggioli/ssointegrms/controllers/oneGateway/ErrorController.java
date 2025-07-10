package it.maggioli.ssointegrms.controllers.oneGateway;

import it.maggioli.ssointegrms.controllers.AbstractBaseController;
import it.maggioli.ssointegrms.exceptions.oneGateway.DecryptException;
import it.maggioli.ssointegrms.model.ResponseError;
import it.maggioli.ssointegrms.services.oneGateway.CryptoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Controller temporaneo per la gestione dell'errore
 *
 * @author Cristiano Perin
 */
//@Controller
public class ErrorController extends AbstractBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @Value("${application.baseUrl}")
    private String applicationBaseUrl;

    @Value("${application.errorContext}")
    private String errorContext;

    @Autowired
    private CryptoService cryptoService;

    public ErrorController() {
        super();
    }

    @PostMapping("/LoginOneGatewayError.html")
    public RedirectView callbackError(@ModelAttribute(value = "authResponse") String jwtResponse) {
        LOGGER.info("Execution start ErrorController::callbackError()");

        if (StringUtils.isNotBlank(jwtResponse)) {
            try {
                String mimsClientId = "SCPSA_coll";
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
