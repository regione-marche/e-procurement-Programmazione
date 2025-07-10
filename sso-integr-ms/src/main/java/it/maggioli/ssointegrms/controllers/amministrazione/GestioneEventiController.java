package it.maggioli.ssointegrms.controllers.amministrazione;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import it.maggioli.ssointegrms.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import it.maggioli.ssointegrms.services.general.WLogEventiService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestioneEventi")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneEventiController extends AbstractBaseAmministrazioneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestioneEventiController.class);

	public static final String RESPONSE_DONE_Y = "Y";
	public static final String RESPONSE_DONE_N = "N";
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@Autowired
	private WLogEventiService wLogEventiService;

	@GetMapping("/getInizRicercaEventi")
	public RicercaEventiInizDTO getInizRicercaEventi(@ApiIgnore Authentication authentication) {
		LOGGER.debug("Execution start GestioneEventiController::getInizRicercaEventi");

		super.validateAdminUser(authentication);

		return wLogEventiService.getInizRicercaEventi();
	}

	@GetMapping("/lista")
	public ResponseListaDTO listaEventi(@ApiIgnore Authentication authentication,
			@Valid final RicercaEventiFormDTO searchForm) {
		LOGGER.debug("Execution start GestioneEventiController::listaEventi with form [ {} ]", searchForm);

		super.validateAdminUser(authentication);

		return wLogEventiService.loadListaEventi(searchForm);
	}

	@GetMapping("/ultimiAccessi")
	public ResponseListaDTO listaUltimiAccessi(
			@ApiIgnore Authentication authentication
	) {

		LOGGER.debug("Execution start GestioneEventiController::listaUltimiAccessi.");

		UserDTO user = (UserDTO) authentication.getPrincipal();
		Long syscon = user.getSyscon();

		return wLogEventiService.loadListaUltimiAccessi(syscon);
	}

	@GetMapping("/evento/{idEvento}")
	public WLogEventiDTO getEvento(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
			@PathVariable(value = "idEvento") final Long idEvento) {
		LOGGER.debug("Execution start GestioneEventiController::getEvento with idEvento [ {} ]", idEvento);

		final String ipAddress = resolveRemoteIpAddress(request);

		UserDTO user = (UserDTO) authentication.getPrincipal();

		super.validateAdminUser(authentication);

		return wLogEventiService.getEvento(idEvento, user.getSyscon(), ipAddress);
	}

	@PostMapping("/logout")
	public ResponseDTO logoutEvent(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request) {
		LOGGER.debug("Execution start GestioneEventiController::logoutEvent");

		ResponseDTO response = new ResponseDTO();
		response.setDone(RESPONSE_DONE_Y);

		final String ipAddress = resolveRemoteIpAddress(request);

		UserDTO user = (UserDTO) authentication.getPrincipal();
		Long syscon = user.getSyscon();

		try {

			response = wLogEventiService.createLogoutEvent(syscon, ipAddress);

		} catch (Exception t) {
            LOGGER.error("Errore inaspettato durante l'inserimento del log dell'evento di logout per il syscon:{}", user.getSyscon(), t);
			response.setDone(RESPONSE_DONE_N);

			List<String> messages = new ArrayList<>();
			messages.add(ERROR_UNEXPECTED);

			response.setMessages(messages);
		}

		return response;
	}
}
