package it.maggioli.ssointegrms.controllers.amministrazione;

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

import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaConfigurazioniFormDTO;
import it.maggioli.ssointegrms.dto.RicercaConfigurazioniInizDTO;
import it.maggioli.ssointegrms.dto.WConfigDTO;
import it.maggioli.ssointegrms.dto.WConfigEditDTO;
import it.maggioli.ssointegrms.services.general.WConfigService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestioneConfigurazioni")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneConfigurazioniController extends AbstractBaseAmministrazioneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestioneConfigurazioniController.class);

	@Autowired
	private WConfigService wConfigService;

	@GetMapping("/getInizRicercaConfigurazioni")
	public RicercaConfigurazioniInizDTO getInizRicercaConfigurazioni(@ApiIgnore Authentication authentication) {
		LOGGER.debug("Execution start GestioneConfigurazioniController::getInizRicercaConfigurazioni");

		super.validateAdminUser(authentication);

		return wConfigService.getInizRicercaConfigurazioni();
	}

	@GetMapping("/lista")
	public ResponseListaDTO listaConfigurazioni(@ApiIgnore Authentication authentication,
			@Valid final RicercaConfigurazioniFormDTO searchForm) {
		LOGGER.debug("Execution start GestioneConfigurazioniController::listaConfigurazioni with form [ {} ]",
				searchForm);

		super.validateAdminUser(authentication);

		return wConfigService.loadListaConfigurazioni(searchForm);
	}

	@GetMapping("/configurazione")
	public WConfigDTO getConfigurazione(@ApiIgnore Authentication authentication,
			@RequestParam(value = "codApp") final String codApp, @RequestParam(value = "chiave") final String chiave) {
		LOGGER.debug(
				"Execution start GestioneConfigurazioniController::getConfigurazione with codApp [ {} ] and chiave [ {} ]",
				codApp, chiave);

		super.validateAdminUser(authentication);

		return wConfigService.getDettaglioConfiguration(codApp, chiave);
	}

	@PutMapping("/configurazione")
	public WConfigDTO updateConfigurazione(@ApiIgnore Authentication authentication,
			@ApiIgnore HttpServletRequest request, @RequestBody @Valid final WConfigEditDTO form)
			throws CriptazioneException {
		LOGGER.debug("Execution start GestioneConfigurazioniController::updateConfigurazione with form [ {} ]", form);

		super.validateAdminUser(authentication);

		return this.wConfigService.updateConfiguration(form);
	}
}
