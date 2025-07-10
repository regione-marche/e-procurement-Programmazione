package it.maggioli.ssointegrms.controllers;

import it.appaltiecontratti.security.maggiolicaptchachecker.exceptions.FriendlyCaptchaException;
import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.common.ExceptionCodes;
import it.maggioli.ssointegrms.dto.ResponseDTO;
import it.maggioli.ssointegrms.exceptions.amministrazione.*;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.*;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.*;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Cristiano Perin
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Lo nomino LOG anziche' LOGGER perche' sonarqube lo flagga come code smell
     * (e' presente un logger minuscolo nella superclasse)
     */
    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.warn("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        ResponseDTO responseDto = new ResponseDTO();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            final String message = "Field: " + fieldError.getField() + ", Message: " + fieldError.getDefaultMessage();
            responseDto.getMessages().add(message);
        }
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);
        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ClientIDNotFoundException.class})
    public ResponseEntity<Object> handleClientIDNotFoundException(final ClientIDNotFoundException ex,
                                                                  final WebRequest request) {
        LOG.error("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        final ResponseDTO responseDto = new ResponseDTO();
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);
        responseDto.getMessages().add(ExceptionCodes.MIMS_AUTH_001);

        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({BadCredentialsException.class, MaxTentativiException.class, AccountExpiredException.class,
            PasswordExpiredException.class, FirstAccessException.class, UserDisabledException.class})
    public ResponseEntity<Object> handleBadCredentialsException(final Exception ex, final WebRequest request) {
        LOG.error("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        final ResponseDTO responseDto = new ResponseDTO();
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);

        if (ex instanceof MaxTentativiException) {
            responseDto.getMessages().add(AppConstants.LOGIN_MAX_TENTATIVI);
        } else if (ex instanceof AccountExpiredException) {
            responseDto.getMessages().add(AppConstants.LOGIN_ACCOUNT_EXPIRED);
        } else if (ex instanceof PasswordExpiredException) {
            responseDto.getMessages().add(AppConstants.LOGIN_PASSWORD_EXPIRED);
        } else if (ex instanceof FirstAccessException) {
            responseDto.getMessages().add(AppConstants.LOGIN_FIRST_ACCESS);
        } else if (ex instanceof UserDisabledException) {
            responseDto.getMessages().add(AppConstants.LOGIN_USER_DISABLED);
        } else {
            responseDto.getMessages().add(AppConstants.LOGIN_UNAUTHORIZED);
        }

        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({ChangePasswordException.class})
    public ResponseEntity<Object> handleChangePasswordErrorsException(final ChangePasswordException ex,
                                                                      final WebRequest request) {
        LOG.error("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        final ResponseDTO responseDto = new ResponseDTO();
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);

        if (ex != null) {
            responseDto.getMessages().addAll(ex.getMessages());
        }

        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * HTTP STATUS 400
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({UserAlreadyExistsException.class, WMailAlreadyExistsException.class,
            WMailTimeoutNotANumberException.class, MailErrorException.class,
            CodificaAutomaticaSintassiNonCorrettaException.class, NoUffintSelectedException.class, NoProfilesSelectedException.class,
            CertificatoMTokenNotFoundException.class, MotivazioneMTokenNotFoundException.class})
    public ResponseEntity<Object> handleBadRequestException(final Exception ex, final WebRequest request) {
        LOG.error("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        final ResponseDTO responseDto = new ResponseDTO();
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);

        if (ex instanceof UserAlreadyExistsException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_USER_ALREADY_EXISTS);
        } else if (ex instanceof WMailAlreadyExistsException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_WMAIL_WMAIL_ALREADY_EXISTS);
        } else if (ex instanceof WMailTimeoutNotANumberException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_WMAIL_TIMEOUT_NOT_A_NUMBER);
        } else if (ex instanceof MailErrorException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_WMAIL_ERROR_TEST_SEND);
        } else if (ex instanceof NoUffintSelectedException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_NO_UFFINT_SELECTED);
        } else if (ex instanceof NoProfilesSelectedException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_NO_PROFILES_SELECTED);
        } else if (ex instanceof CertificatoMTokenNotFoundException) {
            responseDto.getMessages().add(AppConstants.LOGIN_MTOKEN_CERT_NOT_FOUND);
        } else if (ex instanceof MotivazioneMTokenNotFoundException) {
            responseDto.getMessages().add(AppConstants.LOGIN_MTOKEN_MOTIVAZIONE_NOT_FOUND);
        } else if (ex instanceof CodificaAutomaticaSintassiNonCorrettaException) {
            responseDto.getMessages().add(ex.getMessage());
        }

        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * HTTP STATUS 403
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({UserPermissionException.class, UserEditForbiddenException.class, UserNotAdministratorException.class,
            UserDataUltimoAccessoNotNullException.class, MTokenLoginFailedException.class})
    public ResponseEntity<Object> handleForbiddenException(final Exception ex, final WebRequest request) {
        LOG.error("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        final ResponseDTO responseDto = new ResponseDTO();
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);

        if (ex instanceof UserPermissionException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_USER_FORBIDDEN);
        } else if (ex instanceof UserEditForbiddenException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_USER_EDIT_FORBIDDEN);
        } else if (ex instanceof UserNotAdministratorException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_USER_NOT_ADMINISTRATOR);
        } else if (ex instanceof UserDataUltimoAccessoNotNullException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_USER_DATA_ULTIMO_ACCESSO_NOT_NULL);
        } else if (ex instanceof MTokenLoginFailedException) {
            responseDto.getMessages().add(AppConstants.LOGIN_MTOKEN_FAILED);
        }

        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * HTTP STATUS 404
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({UserNotFoundException.class, ConfigurationNotFoundException.class,
            QuartzNotFoundException.class, EventoNotFoundException.class, WMailNotFoundException.class,
            CodificaAutomaticaNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(final Exception ex, final WebRequest request) {
        LOG.error("Exception handle in RestResponseEntityExceptionHandler. See details", ex);
        final ResponseDTO responseDto = new ResponseDTO();
        responseDto.setDone(AppConstants.RESPONSE_DONE_N);

        if (ex instanceof UserNotFoundException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_UTENTE_USER_NOT_FOUND);
        } else if (ex instanceof ConfigurationNotFoundException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_CONFIGURAZIONI_CONFIGURAZIONE_NOT_FOUND);
        } else if (ex instanceof QuartzNotFoundException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_PIANIFICAZIONI_PIANIFICAZIONE_NOT_FOUND);
        } else if (ex instanceof EventoNotFoundException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_EVENTI_EVENTO_NOT_FOUND);
        } else if (ex instanceof WMailNotFoundException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_WMAIL_WMAIL_NOT_FOUND);
        } else if (ex instanceof CodificaAutomaticaNotFoundException) {
            responseDto.getMessages().add(AppConstants.GESTIONE_CODIFICA_AUTOMATICA_G_CONFCOD_NOT_FOUND);
        }

        return handleExceptionInternal(ex, responseDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * HTTP STATUS 500
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {CriptazioneException.class, UnexpectedErrorException.class, FriendlyCaptchaException.class})
    protected ResponseEntity<Object> handleInternalServerErrorException(Exception ex, WebRequest request) {
        LOG.error("Exception handle in InternalServerErrorException. See details", ex);
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setDone(AppConstants.RESPONSE_DONE_N);
        if (ex instanceof FriendlyCaptchaException) {
            LOG.error("Errore captcha {}", ex.getMessage());
            responseDTO.getMessages().add(AppConstants.CAPTCHA_ERROR);
        } else {
            responseDTO.getMessages().add(ex.getMessage());
        }

        return handleExceptionInternal(ex, responseDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
