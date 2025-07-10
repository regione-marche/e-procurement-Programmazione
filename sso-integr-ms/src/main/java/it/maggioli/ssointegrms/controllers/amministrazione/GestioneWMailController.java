package it.maggioli.ssointegrms.controllers.amministrazione;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.maggioli.ssointegrms.dto.ResponseDTO;
import it.maggioli.ssointegrms.dto.WMailDTO;
import it.maggioli.ssointegrms.dto.WMailEditDTO;
import it.maggioli.ssointegrms.dto.WMailEditPasswordDTO;
import it.maggioli.ssointegrms.dto.WMailInizDTO;
import it.maggioli.ssointegrms.dto.WMailTestSendDTO;
import it.maggioli.ssointegrms.services.general.WMailService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestioneWMail")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneWMailController extends AbstractBaseAmministrazioneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestioneWMailController.class);

	@Autowired
	private WMailService wMailService;

	@GetMapping("/lista")
	public List<WMailDTO> listaWMail(@ApiIgnore Authentication authentication) {
		LOGGER.debug("Execution start GestioneWMailController::listaWMail");

		super.validateAdminUser(authentication);

		return wMailService.loadListaWMail();
	}

	@GetMapping("/wmail")
	public WMailDTO getWMail(@ApiIgnore Authentication authentication,
			@RequestParam(value = "idCfg") final String idCfg) {
		LOGGER.debug("Execution start GestioneWMailController::getWMail for idCfg [ {} ]", idCfg);

		super.validateAdminUser(authentication);

		return wMailService.getWMail(idCfg);
	}

	@GetMapping("/wmail/iniz")
	public WMailInizDTO getInizNewWMail(@ApiIgnore Authentication authentication) {
		LOGGER.debug("Execution start GestioneWMailController::getInizNewWMail");

		super.validateAdminUser(authentication);

		return wMailService.getInizNewWMail();
	}

	@PostMapping("/wmail")
	public WMailDTO insertWMail(@ApiIgnore Authentication authentication, @Valid @RequestBody final WMailEditDTO form) {
		LOGGER.debug("Execution start GestioneWMailController::insertWMail for form [ {} ]", form);

		super.validateAdminUser(authentication);

		return wMailService.insertWMail(form);
	}

	@PutMapping("/wmail")
	public WMailDTO updateWMail(@ApiIgnore Authentication authentication, @Valid @RequestBody final WMailEditDTO form) {
		LOGGER.debug("Execution start GestioneWMailController::updateWMail for form [ {} ]", form);

		super.validateAdminUser(authentication);

		return wMailService.updateWMail(form);
	}

	@DeleteMapping("/wmail")
	public boolean deleteWMail(@ApiIgnore Authentication authentication,
			@RequestParam(value = "idCfg") final String idCfg) {
		LOGGER.debug("Execution start GestioneWMailController::deleteWMail for idCfg [ {} ]", idCfg);

		super.validateAdminUser(authentication);

		return wMailService.deleteWMail(idCfg);
	}

	@PutMapping("/wmail/password")
	public ResponseDTO updateWMailPassword(@ApiIgnore Authentication authentication,
			@Valid @RequestBody final WMailEditPasswordDTO form) throws CriptazioneException {
		LOGGER.debug("Execution start GestioneWMailController::updateWMailPassword for idCfg [ {} ]", form.getIdCfg());

		super.validateAdminUser(authentication);

		return wMailService.updateWMailPassword(form);
	}

	@PutMapping("/wmail/testSend")
	public boolean testSendEmail(@ApiIgnore Authentication authentication,
			@Valid @RequestBody final WMailTestSendDTO form) throws CriptazioneException {
		LOGGER.debug("Execution start GestioneWMailController::testSendEmail for form [ {} ]", form);

		super.validateAdminUser(authentication);

		return wMailService.testSendEmail(form);
	}
}
