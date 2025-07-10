package it.maggioli.ssointegrms.controllers.amministrazione;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaC0CampiFormDTO;
import it.maggioli.ssointegrms.services.general.C0CampiService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestioneC0Campi")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneC0CampiController extends AbstractBaseAmministrazioneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestioneC0CampiController.class);

	@Autowired
	private C0CampiService c0CampiService;

	@GetMapping("/lista")
	public ResponseListaDTO listaC0Campi(@ApiIgnore Authentication authentication,
			@Valid final RicercaC0CampiFormDTO searchForm) {
		LOGGER.debug("Execution start GestioneC0CampiController::listaC0Campi");

		super.validateAdminUser(authentication);

		return c0CampiService.loadListaC0Campi(searchForm);
	}

}
