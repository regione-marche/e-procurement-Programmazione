package it.maggioli.ssointegrms.controllers.amministrazione;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.maggioli.ssointegrms.dto.WQuartzDTO;
import it.maggioli.ssointegrms.dto.WQuartzEditDTO;
import it.maggioli.ssointegrms.services.general.WQuartzService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestionePianificazioni")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestionePianificazioniController extends AbstractBaseAmministrazioneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestionePianificazioniController.class);

	@Autowired
	private WQuartzService wQuartzService;

	@GetMapping("/lista")
	public List<WQuartzDTO> listaPianificazioni(@ApiIgnore Authentication authentication) {
		LOGGER.debug("Execution start GestionePianificazioniController::listaPianificazioni");

		super.validateAdminUser(authentication);

		return wQuartzService.listaPianificazioni();
	}

	@GetMapping("/pianificazione")
	public WQuartzDTO getPianificazione(@ApiIgnore Authentication authentication,
			@RequestParam(value = "codApp") final String codApp, @RequestParam(value = "beanId") final String beanId) {
		LOGGER.debug(
				"Execution start GestionePianificazioniController::getPianificazione with codApp [ {} ] and beanId [ {} ]",
				codApp, beanId);

		super.validateAdminUser(authentication);

		return wQuartzService.getPianificazione(codApp, beanId);
	}

	@PutMapping("/pianificazione")
	public WQuartzDTO updatePianificazione(@ApiIgnore Authentication authentication,
			@ApiIgnore HttpServletRequest request, @RequestBody @Valid final WQuartzEditDTO form)
			throws CriptazioneException {
		LOGGER.debug("Execution start GestionePianificazioniController::updatePianificazione with form [ {} ]", form);

		super.validateAdminUser(authentication);

		return wQuartzService.updatePianificazione(form);
	}
}
