package it.maggioli.ssointegrms.controllers.internalAuthentication;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.controllers.AbstractBaseController;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.ChangePasswordException;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.ClientIDNotFoundException;
import it.maggioli.ssointegrms.model.ApplicationInfo;
import it.maggioli.ssointegrms.services.general.UserService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Cristiano Perin
 */
@RestController
@RequestMapping("/internalAuthentication/${application.internalAuthentication.apiVersion}")
@SuppressWarnings("java:S5122")
@CrossOrigin
public class AuthenticationController extends AbstractBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/checkMToken")
    public ResponseDTO checkMToken(@Valid @RequestBody CheckMTokenDTO checkMTokenDTO) {
        LOGGER.debug("Execution start AuthenticationController::checkMToken for user [ {} ]",
                checkMTokenDTO.getUsername());

        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setDone(AppConstants.RESPONSE_DONE_Y);
        responseDTO.setResponse(userService.executeCheckMToken(checkMTokenDTO));

        return responseDTO;
    }

    @PostMapping("/authorize")
    public ResponseDTO postAuthentication(HttpServletRequest request,
                                          @Valid @RequestBody AuthenticationDTO authenticationDTO) throws CriptazioneException {
        LOGGER.debug("Execution start AuthenticationController::postAuthentication for user [ {} ]",
                authenticationDTO.getUsername());

        final String ipAddress = resolveRemoteIpAddress(request);

        if (StringUtils.isNotBlank(ipAddress) && authenticationDTO != null)
            authenticationDTO.setIpAddress(ipAddress);

        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setDone(AppConstants.RESPONSE_DONE_Y);
        responseDTO.setResponse(userService.executeAuthentication(authenticationDTO));

        return responseDTO;
    }

    @PostMapping("/changePassword")
    public ResponseDTO postChangePassword(HttpServletRequest request,
                                          @Valid @RequestBody ChangePasswordDTO changePasswordDTO) throws CriptazioneException {
        LOGGER.debug("Execution start AuthenticationController::postChangePassword for user [ {} ]",
                changePasswordDTO.getUsername());

        final String ipAddress = resolveRemoteIpAddress(request);

        if (StringUtils.isNotBlank(ipAddress) && changePasswordDTO != null)
            changePasswordDTO.setIpAddress(ipAddress);

        final ResponseDTO responseDTO = userService.executeChangePassword(changePasswordDTO);

        if (AppConstants.RESPONSE_DONE_N.equals(responseDTO.getDone())) {
            ChangePasswordException ex = new ChangePasswordException();
            ex.setMessages(responseDTO.getMessages());
            throw ex;
        }

        return responseDTO;
    }

    @GetMapping("/loadAuthenticationMethods")
    public AuthenticationMethodsDTO loadAuthenticationMethods(@RequestParam(value = "clientId") String clientId) {
        LOGGER.debug("Execution start AuthenticationController::loadAuthenticationMethods for clientId [ {} ]",
                clientId);

        if (StringUtils.isBlank(clientId) || (!OneGatewayAppConstants.SERVICE_PROVIDERS.containsKey(clientId))) {
            throw new ClientIDNotFoundException();
        }

        AuthenticationMethodsDTO dto = new AuthenticationMethodsDTO();

        ApplicationInfo applicationInfo = OneGatewayAppConstants.SERVICE_PROVIDERS.get(clientId);

        if (applicationInfo != null) {
            dto.setInternal(applicationInfo.isInternalAuthenticationActive());
            dto.setInternalRegistration(applicationInfo.getInternalAuthenticationRegistrationUrl());
            dto.setSpid(applicationInfo.isSpidAuthenticationActive());
            dto.setCie(applicationInfo.isCieAuthenticationActive());
            dto.setCns(applicationInfo.isCnsAuthenticationActive());
            dto.setEidas(applicationInfo.isEidasAuthenticationActive());
        }

        return dto;
    }

    @GetMapping("/getInternalAuthenticationRedirectUrl")
    public ResponseDTO getInternalAuthenticationRedirectUrl(@RequestParam(value = "clientId") String clientId) {
        LOGGER.debug(
                "Execution start AuthenticationController::getInternalAuthenticationRedirectUrl for clientId [ {} ]",
                clientId);

        if (StringUtils.isNotBlank(clientId) && OneGatewayAppConstants.SERVICE_PROVIDERS.containsKey(clientId)
                && OneGatewayAppConstants.SERVICE_PROVIDERS.get(clientId).isInternalAuthenticationActive()) {
            ResponseDTO response = new ResponseDTO();
            response.setDone(AppConstants.RESPONSE_DONE_Y);

            String url = OneGatewayAppConstants.SERVICE_PROVIDERS.containsKey(clientId)
                    ? OneGatewayAppConstants.SERVICE_PROVIDERS.get(clientId).getInternalAuthenticationReturnUrl()
                    : null;

            response.setResponse(url);
            return response;
        }

        return null;
    }

    @PostMapping("/requestPasswordRecovery")
    public ResponseDTO postRequestPasswordRecovery(HttpServletRequest request,
                                                   @Valid @RequestBody final PasswordRecoveryRequestDTO form) {

        LOGGER.debug("Execution start AuthenticationController::postRequestPasswordRecovery for form [ {} ]", form);

        final String ipAddress = resolveRemoteIpAddress(request);

        if (StringUtils.isNotBlank(ipAddress) && form != null)
            form.setIpAddress(ipAddress);

        try {
            return userService.requestPasswordRecovery(form);
        } catch (Exception e) {
            LOGGER.error("Si e' verificato un problema durante la richiesta di recupero della password", e);
        }
        return null;
    }

    @GetMapping("/checkPasswordRecoveryToken")
    public boolean checkPasswordRecoveryToken(HttpServletRequest request,
                                              @RequestParam(value = "token") final String token) {

        LOGGER.debug("Execution start AuthenticationController::checkPasswordRecoveryToken for token [ {} ]", token);

        final String ipAddress = resolveRemoteIpAddress(request);

        return userService.checkPasswordRecoveryToken(token, ipAddress);
    }

    @PostMapping("/executePasswordRecovery")
    public ResponseDTO executePasswordRecovery(HttpServletRequest request,
                                               @Valid @RequestBody final PasswordRecoveryExecutionDTO form) throws CriptazioneException {

        LOGGER.debug("Execution start AuthenticationController::executePasswordRecovery for form [ {} ]", form);

        final String ipAddress = resolveRemoteIpAddress(request);

        if (StringUtils.isNotBlank(ipAddress) && form != null)
            form.setIpAddress(ipAddress);

        return userService.executePasswordRecovery(form);
    }
}
