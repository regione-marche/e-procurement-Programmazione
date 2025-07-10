package it.maggioli.ssointegrms.controllers.gestioneUtenti;

import it.maggioli.ssointegrms.dto.RichiestaAssistenzaFormDTO;
import it.maggioli.ssointegrms.dto.RichiestaAssistenzaInitDTO;
import it.maggioli.ssointegrms.services.general.RichiestaAssistenzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Cristiano Perin
 */
@RestController
@RequestMapping("/gestioneUtenti/public/${application.gestioneUtenti.apiVersion}")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneUtentiPublicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestioneUtentiPublicController.class);

    @Autowired
    private RichiestaAssistenzaService richiestaAssistenzaService;

    @GetMapping("/isRichiestaAssistenzaAttiva")
    public boolean isRichiestaAssistenzaAttiva() {
        LOGGER.debug("Execution start GestioneUtentiPublicController::isRichiestaAssistenzaAttiva");

        return this.richiestaAssistenzaService.isRichiestaAssistenzaAttiva();
    }

    @GetMapping("/richiestaAssistenza")
    public RichiestaAssistenzaInitDTO getInitRichiestaAssistenza() {
        LOGGER.debug("Execution start GestioneUtentiPublicController::getInitRichiestaAssistenza");

        return this.richiestaAssistenzaService.getInitRichiestaAssistenza();
    }

    @PostMapping("/richiestaAssistenza")
    public boolean richiestaAssistenza(@RequestBody @Valid final RichiestaAssistenzaFormDTO form) {
        LOGGER.debug("Execution start GestioneUtentiPublicController::richiestaAssistenza with form [ {} ]", form);

        return this.richiestaAssistenzaService.postRichiestaAssistenza(form);
    }
}
