package it.maggioli.ssointegrms.controllers.gestioneUtenti;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.controllers.AbstractBaseController;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.ChangePasswordException;
import it.maggioli.ssointegrms.services.general.ProfiloService;
import it.maggioli.ssointegrms.services.general.UffintService;
import it.maggioli.ssointegrms.services.general.UserService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@RestController
@RequestMapping("/gestioneUtenti/${application.gestioneUtenti.apiVersion}")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class GestioneUtentiController extends AbstractBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestioneUtentiController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UffintService uffintService;

    @Autowired
    private ProfiloService profiloService;

    @GetMapping("/initRicercaUtenti")
    public InitRicercaUtentiDTO initRicercaUtenti(@ApiIgnore Authentication authentication) {
        LOGGER.debug("Execution start GestioneUtentiController::initRicercaUtenti");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return userService.initRicercaUtenti(user);
    }

    @GetMapping("/lista")
    public ResponseListaDTO listaUtenti(@ApiIgnore Authentication authentication,
                                        @Valid final RicercaUtentiFormDTO searchForm) {
        LOGGER.debug("Execution start GestioneUtentiController::listaUtenti with form [ {} ]", searchForm);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return userService.loadListaUtenti(searchForm, user);
    }

    @PostMapping("/utente")
    public ResponseDTO insertUtente(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
                                    @RequestBody @Valid final UserInsertDTO form) throws CriptazioneException {
        LOGGER.debug("Execution start GestioneUtentiController::insertUtente with form [ {} ]", form);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.insertUtente(user, form, ipAddress);
    }

    @GetMapping("/utente/{syscon}")
    public UserDTO getUtente(@ApiIgnore Authentication authentication,
                             @PathVariable(value = "syscon") final Long syscon) {
        LOGGER.debug("Execution start GestioneUtentiController::getUtente with syscon [ {} ]", syscon);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return userService.getUtente(syscon, user);
    }

    @PutMapping("/utente/{syscon}")
    public ResponseDTO updateUtente(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
                                    @PathVariable(value = "syscon") final Long syscon, @RequestBody @Valid final UserEditDTO form) throws CriptazioneException {
        LOGGER.debug("Execution start GestioneUtentiController::updateUtente with syscon [ {} ] and form [ {} ]",
                syscon, form);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.updateUtente(user, syscon, form, ipAddress);
    }

    @GetMapping("/ufficioIntestatario/{codice}")
    public UfficioIntestatarioDTO getUfficioIntestatario(@ApiIgnore Authentication authentication,
                                                         @PathVariable(value = "codice") final String codice) {
        LOGGER.debug("Execution start GestioneUtentiController::getUfficioIntestatario for codice [ {} ]", codice);

        return uffintService.getUfficioIntestatario(codice);
    }

    @GetMapping("/ufficiIntestatari/options")
    public List<UfficioIntestatarioDTO> getListaOpzioniUfficioIntestatario(@ApiIgnore Authentication authentication,
                                                                           @RequestParam(value = "searchData") final String searchData) {
        LOGGER.debug(
                "Execution start GestioneUtentiController::getListaOpzioniUfficioIntestatario for searchData [ {} ]",
                searchData);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return uffintService.getListaOpzioniUfficioIntestatario(searchData, user);
    }

    @GetMapping("/profilo/{codice}")
    public ProfiloDTO getProfilo(@ApiIgnore Authentication authentication,
                                 @PathVariable(value = "codice") final String codice) {
        LOGGER.debug("Execution start GestioneUtentiController::getProfilo for codice [ {} ]", codice);

        return profiloService.getProfilo(codice);
    }

    @GetMapping("/profili")
    public List<ProfiloDTO> getListaProfili(@ApiIgnore Authentication authentication) {
        LOGGER.debug("Execution start GestioneUtentiController::getListaProfili");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return profiloService.getListaProfili(user);
    }

    @GetMapping("/profili/options")
    public List<ProfiloDTO> getListaOpzioniProfilo(@ApiIgnore Authentication authentication,
                                                   @RequestParam(value = "searchData") final String searchData) {
        LOGGER.debug("Execution start GestioneUtentiController::getListaOpzioniProfilo for searchData [ {} ]",
                searchData);

        return profiloService.getListaOpzioniProfilo(searchData);
    }

    @PutMapping("/utente/{syscon}/abilita")
    public boolean abilitaUtente(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
                                 @PathVariable(value = "syscon") final Long syscon) {
        LOGGER.debug("Execution start GestioneUtentiController::abilitaUtente with syscon [ {} ]", syscon);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.abilitaDisabilitaUtente(user, syscon,
                AppConstants.OPERAZIONE_ATTIVAZIONE, ipAddress);
    }

    @PutMapping("/utente/{syscon}/disabilita")
    public boolean disabilitaUtente(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
                                    @PathVariable(value = "syscon") final Long syscon) {
        LOGGER.debug("Execution start GestioneUtentiController::disabilitaUtente with syscon [ {} ]", syscon);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.abilitaDisabilitaUtente(user, syscon,
                AppConstants.OPERAZIONE_DISATTIVAZIONE, ipAddress);
    }

    @DeleteMapping("/utente/{syscon}")
    public boolean deleteUtente(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
                                @PathVariable(value = "syscon") final Long syscon) {
        LOGGER.debug("Execution start GestioneUtentiController::deleteUtente with syscon [ {} ]", syscon);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.deleteUtente(user, syscon, ipAddress);
    }

    @GetMapping("/utente/{syscon}/profili")
    public List<ProfiloDTO> getProfiliUtente(@ApiIgnore Authentication authentication,
                                             @PathVariable(value = "syscon") final Long syscon) {
        LOGGER.debug("Execution start GestioneUtentiController::getProfiliUtente for syscon [ {} ]", syscon);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.getProfiliUtente(user, syscon);
    }

    @PutMapping("/utente/{syscon}/profili")
    public boolean setProfiliUtente(@ApiIgnore Authentication authentication, @ApiIgnore HttpServletRequest request,
                                    @PathVariable(value = "syscon") final Long syscon,
                                    @RequestBody @Valid final ProfiliUtenteEditDTO profiliUtente) {
        LOGGER.debug("Execution start GestioneUtentiController::setProfiliUtente for syscon [ {} ] and profili [ {} ]", syscon,
                profiliUtente);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.setProfiliUtente(user, syscon, profiliUtente.getListaProfili(), ipAddress);
    }

    @GetMapping("/utente/{syscon}/ufficiIntestatari")
    public List<UfficioIntestatarioDTO> getUfficiIntestatariUtente(@ApiIgnore Authentication authentication,
                                                                   @PathVariable(value = "syscon") final Long syscon) {
        LOGGER.debug("Execution start GestioneUtentiController::getUfficiIntestatariUtente for syscon [ {} ]", syscon);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.getUfficiIntestatariUtente(user, syscon);
    }

    @PutMapping("/utente/{syscon}/ufficiIntestatari")
    public boolean setUfficiIntestatariUtente(@ApiIgnore Authentication authentication,
                                              @PathVariable(value = "syscon") final Long syscon,
                                              @RequestBody @Valid final UfficioIntestatarioEditDTO ufficiIntestatariUtente) {
        LOGGER.debug(
                "Execution start GestioneUtentiController::setUfficiIntestatariUtente for syscon [ {} ] and uffici intestatari [ {} ]",
                syscon, ufficiIntestatariUtente);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.setUfficiIntestatariUtente(user, syscon,
                ufficiIntestatariUtente.getListaUfficiIntestatari());
    }

    @GetMapping("/ufficiIntestatari")
    public List<UfficioIntestatarioDTO> getListaUfficiIntestatari(@ApiIgnore Authentication authentication) {

        LOGGER.debug("Execution start GestioneUtentiController::getListaUfficiIntestatari");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return uffintService.getListaUfficiIntestatari(user);
    }

    @PutMapping("/utente/{syscon}/changePassword")
    public ResponseDTO changePasswordAdminUtente(@ApiIgnore Authentication authentication,
                                                 @ApiIgnore HttpServletRequest request, @PathVariable(value = "syscon") final Long syscon,
                                                 @RequestBody @Valid final UserChangePasswordAdminDTO form) throws CriptazioneException {
        LOGGER.debug("Execution start GestioneUtentiController::changePasswordAdminUtente with syscon [ {} ]", syscon);

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return this.userService.changePasswordAdminUtente(user, syscon, form, ipAddress);
    }

    @GetMapping("/utenteConnesso")
    public UserConnectedDTO getUtenteConnesso(@ApiIgnore Authentication authentication) {
        LOGGER.debug("Execution start GestioneUtentiController::getUtenteConnesso");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return userService.getUtenteConnesso(user);
    }

    @PutMapping("/utenteConnesso")
    public ResponseDTO updateUtenteConnesso(@ApiIgnore Authentication authentication, @RequestBody @Valid final UserConnectedEditDTO form) {
        LOGGER.debug("Execution start GestioneUtentiController::updateUtenteConnesso");

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return userService.updateUtenteConnesso(user, form);
    }

    @PostMapping("/changeUserPassword")
    public ResponseDTO postChangeUserPassword(HttpServletRequest request,
                                              @ApiIgnore Authentication authentication,
                                              @Valid @RequestBody ChangePasswordUserDTO changePasswordUserDTO) throws CriptazioneException {

        final String ipAddress = resolveRemoteIpAddress(request);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        LOGGER.debug("Execution start AuthenticationController::postChangeUserPassword for user [ {} ]",
                user.getUsername());

        if (StringUtils.isNotBlank(ipAddress) && changePasswordUserDTO != null)
            changePasswordUserDTO.setIpAddress(ipAddress);

        final ResponseDTO responseDTO = userService.executeUserChangePassword(user, changePasswordUserDTO);

        if (AppConstants.RESPONSE_DONE_N.equals(responseDTO.getDone())) {
            ChangePasswordException ex = new ChangePasswordException();
            ex.setMessages(responseDTO.getMessages());
            throw ex;
        }

        return responseDTO;
    }

    @GetMapping("/csvUtenti")
    public ResponseDTO csvUtenti(@ApiIgnore Authentication authentication,
            @Valid final RicercaUtentiFormDTO searchForm) {
        LOGGER.debug("Execution start GestioneUtentiController::csvUtenti with form [ {} ]", searchForm);

        UserDTO user = (UserDTO) authentication.getPrincipal();

        return userService.loadCsvUtenti(searchForm, user);
    }
}
