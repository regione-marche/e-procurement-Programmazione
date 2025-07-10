package it.maggioli.ssointegrms.controllers.amministrazione;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.maggioli.ssointegrms.dto.ListaDettaglioTabellatoFormDTO;
import it.maggioli.ssointegrms.dto.ResponseDTO;
import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaTabellatiFormDTO;
import it.maggioli.ssointegrms.dto.Tab0DTO;
import it.maggioli.ssointegrms.dto.Tab1DTO;
import it.maggioli.ssointegrms.dto.Tab2DTO;
import it.maggioli.ssointegrms.dto.Tab3DTO;
import it.maggioli.ssointegrms.dto.Tab5DTO;
import it.maggioli.ssointegrms.services.general.TabellatiService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Cristiano Perin
 *
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestioneTabellati")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneTabellatiController extends AbstractBaseAmministrazioneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestioneTabellatiController.class);

	@Autowired
	private TabellatiService tabellatiService;

	@GetMapping("/lista")
	public ResponseListaDTO listaTabellati(@ApiIgnore Authentication authentication,
			@Valid final RicercaTabellatiFormDTO searchForm) {
		LOGGER.debug("Execution start GestioneTabellatiController::listaTabellati with form [ {} ]", searchForm);

		super.validateAdminUser(authentication);

		return tabellatiService.loadListaTabellati(searchForm);
	}

	@GetMapping("/listaDettaglioTabellato")
	public ResponseListaDTO listaDettaglioTabellato(@ApiIgnore Authentication authentication,
			@Valid final ListaDettaglioTabellatoFormDTO loadForm) {
		LOGGER.debug("Execution start GestioneTabellatiController::listaDettaglioTabellato with loadForm [ {} ]",
				loadForm);

		super.validateAdminUser(authentication);

		return tabellatiService.loadListaDettaglioTabellato(loadForm);
	}

	@GetMapping("/dettaglioTabellato")
	public Object getDettaglioTabellato(@ApiIgnore Authentication authentication,
			@RequestParam(value = "provenienza") final String provenienza,
			@RequestParam(value = "codiceTabellato") final String codiceTabellato,
			@RequestParam(value = "identificativo") final String identificativo) {
		LOGGER.debug(
				"Execution start GestioneTabellatiController::getDettaglioTabellato with provenienza [ {} ], codiceTabellato [ {} ] and identificativo [ {} ]",
				provenienza, codiceTabellato, identificativo);

		super.validateAdminUser(authentication);

		return tabellatiService.getDettaglioTabellato(provenienza, codiceTabellato, identificativo);
	}

	@PostMapping("/insertTab0/{codiceTabellato}")
	public ResponseDTO insertTab0(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@Valid @RequestBody final Tab0DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::insertTab0 with codiceTabellato [ {} ] and form [ {} ]",
				codiceTabellato, form);

		super.validateAdminUser(authentication);

		return tabellatiService.insertTab0(codiceTabellato, form);
	}

	@PostMapping("/insertTab1/{codiceTabellato}")
	public ResponseDTO insertTab1(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@Valid @RequestBody final Tab1DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::insertTab1 with codiceTabellato [ {} ] and form [ {} ]",
				codiceTabellato, form);

		super.validateAdminUser(authentication);

		return tabellatiService.insertTab1(codiceTabellato, form);
	}

	@PostMapping("/insertTab2/{codiceTabellato}")
	public ResponseDTO insertTab2(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@Valid @RequestBody final Tab2DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::insertTab2 with codiceTabellato [ {} ] and form [ {} ]",
				codiceTabellato, form);

		super.validateAdminUser(authentication);

		return tabellatiService.insertTab2(codiceTabellato, form);
	}

	@PostMapping("/insertTab3/{codiceTabellato}")
	public ResponseDTO insertTab3(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@Valid @RequestBody final Tab3DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::insertTab3 with codiceTabellato [ {} ] and form [ {} ]",
				codiceTabellato, form);

		super.validateAdminUser(authentication);

		return tabellatiService.insertTab3(codiceTabellato, form);
	}

	@PostMapping("/insertTab5/{codiceTabellato}")
	public ResponseDTO insertTab5(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@Valid @RequestBody final Tab5DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::insertTab5 with codiceTabellato [ {} ] and form [ {} ]",
				codiceTabellato, form);

		super.validateAdminUser(authentication);

		return tabellatiService.insertTab5(codiceTabellato, form);
	}

	@PutMapping("/updateTab0/{codiceTabellato}/{identificativo}")
	public ResponseDTO updateTab0(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@PathVariable(value = "identificativo") final String identificativo,
			@Valid @RequestBody final Tab0DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::updateTab0 with codiceTabellato [ {} ], idenficativo [ {} ] and form [ {} ]",
				codiceTabellato, identificativo, form);

		super.validateAdminUser(authentication);

		return tabellatiService.updateTab0(codiceTabellato, identificativo, form);
	}

	@PutMapping("/updateTab1/{codiceTabellato}/{identificativo}")
	public ResponseDTO updateTab1(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@PathVariable(value = "identificativo") final Long identificativo,
			@Valid @RequestBody final Tab1DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::updateTab1 with codiceTabellato [ {} ], idenficativo [ {} ] and form [ {} ]",
				codiceTabellato, identificativo, form);

		super.validateAdminUser(authentication);

		return tabellatiService.updateTab1(codiceTabellato, identificativo, form);
	}

	@PutMapping("/updateTab2/{codiceTabellato}/{identificativo}")
	public ResponseDTO updateTab2(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@PathVariable(value = "identificativo") final String identificativo,
			@Valid @RequestBody final Tab2DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::updateTab2 with codiceTabellato [ {} ], idenficativo [ {} ] and form [ {} ]",
				codiceTabellato, identificativo, form);

		super.validateAdminUser(authentication);

		return tabellatiService.updateTab2(codiceTabellato, identificativo, form);
	}

	@PutMapping("/updateTab3/{codiceTabellato}/{identificativo}")
	public ResponseDTO updateTab3(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@PathVariable(value = "identificativo") final String identificativo,
			@Valid @RequestBody final Tab3DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::updateTab3 with codiceTabellato [ {} ], idenficativo [ {} ] and form [ {} ]",
				codiceTabellato, identificativo, form);

		super.validateAdminUser(authentication);

		return tabellatiService.updateTab3(codiceTabellato, identificativo, form);
	}

	@PutMapping("/updateTab5/{codiceTabellato}/{identificativo}")
	public ResponseDTO updateTab5(@ApiIgnore Authentication authentication,
			@PathVariable(value = "codiceTabellato") final String codiceTabellato,
			@PathVariable(value = "identificativo") final String identificativo,
			@Valid @RequestBody final Tab5DTO form) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::updateTab5 with codiceTabellato [ {} ], idenficativo [ {} ] and form [ {} ]",
				codiceTabellato, identificativo, form);

		super.validateAdminUser(authentication);

		return tabellatiService.updateTab5(codiceTabellato, identificativo, form);
	}

	@DeleteMapping("/deleteTabellato")
	public ResponseDTO deleteTabellato(@ApiIgnore Authentication authentication,
			@RequestParam(value = "provenienza") final String provenienza,
			@RequestParam(value = "codiceTabellato") final String codiceTabellato,
			@RequestParam(value = "identificativo") final String identificativo) {

		LOGGER.debug(
				"Execution start GestioneTabellatiController::deleteTabellato with provenienza [ {} ], codiceTabellato [ {} ] and idenficativo [ {} ]",
				provenienza, codiceTabellato, identificativo);

		super.validateAdminUser(authentication);

		return tabellatiService.deleteTabellato(provenienza, codiceTabellato, identificativo);
	}

}
