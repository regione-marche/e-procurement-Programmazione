package it.maggioli.ssointegrms.controllers.amministrazione;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.dto.GConfCodDTO;
import it.maggioli.ssointegrms.dto.GConfCodEditDTO;
import it.maggioli.ssointegrms.dto.ListaCodificaAutomaticaInizDTO;
import it.maggioli.ssointegrms.services.general.GConfCodService;
import it.maggioli.ssointegrms.services.general.WConfigService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@RestController
@RequestMapping("/amministrazione/${application.amministrazione.apiVersion}/gestioneCodificaAutomatica")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneCodificaAutomaticaController extends AbstractBaseAmministrazioneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestioneCodificaAutomaticaController.class);

    @Autowired
    private GConfCodService gConfCodService;

    @Autowired
    private WConfigService wConfigService;

    @GetMapping("/getInizLista")
    public ListaCodificaAutomaticaInizDTO getInizLista(@ApiIgnore Authentication authentication) {

        LOGGER.debug("Execution start GestioneCodificaAutomaticaController::getInizLista");

        super.validateAdminUser(authentication);

        String titoloApplicativo = wConfigService.getConfigurationValue(AppConstants.TITOLO_APPLICATIVO);

        ListaCodificaAutomaticaInizDTO dto = new ListaCodificaAutomaticaInizDTO();

        dto.setCodiceApplicazione(codiceProdotto);
        dto.setTitoloApplicazione(titoloApplicativo);

        return dto;
    }

    @GetMapping("/lista")
    public List<GConfCodDTO> listaCodificaAutomatica(@ApiIgnore Authentication authentication,
                                                     @RequestParam(value = "codApp", required = false) final String codApp) {
        LOGGER.debug("Execution start GestioneCodificaAutomaticaController::listaCodificaAutomatica with codApp [ {} ]",
                codApp);

        super.validateAdminUser(authentication);

        return gConfCodService.loadListaCodificaAutomatica(codApp);
    }

    @GetMapping("/codifica-automatica")
    public GConfCodDTO getCodificaAutomatica(@ApiIgnore Authentication authentication,
                                             @RequestParam(value = "nomEnt") final String nomEnt, @RequestParam(value = "nomCam") final String nomCam,
                                             @RequestParam(value = "tipCam") final Long tipCam) {
        LOGGER.debug(
                "Execution start GestioneCodificaAutomaticaController::getCodificaAutomatica with nomEnt [ {} ], nomCam [ {} ] and tipCam [ {} ]",
                nomEnt, nomCam, tipCam);

        super.validateAdminUser(authentication);

        return gConfCodService.getDettaglio(nomEnt, nomCam, tipCam);
    }

    @PutMapping("/codifica-automatica")
    public GConfCodDTO updateCodificaAutomatica(@ApiIgnore Authentication authentication,
                                                @RequestBody @Valid final GConfCodEditDTO form) throws CriptazioneException {
        LOGGER.debug("Execution start GestioneConfigurazioniController::updateCodificaAutomatica with form [ {} ]",
                form);

        super.validateAdminUser(authentication);

        return this.gConfCodService.updateCodificaAutomatica(form);
    }
}
