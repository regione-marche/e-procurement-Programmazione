package it.appaltiecontratti.gestionereportsms.controllers;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.dto.ResponseListaDTO;
import it.appaltiecontratti.gestionereportsms.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * @author andrea.chinellato
 * Description: Centralized handler for every exception type thrown in the microservice.
 * Usage: Throw the exception type wanted in the desired method by passing an error message as parameter.
 *        The message will be sent to frontend in the message field of the response object in which it will be displayed to the user.
 * */

@ControllerAdvice
public class GestioneReportExceptionHandler extends ResponseEntityExceptionHandler {

    //Ogni eccezione generica per il microservizio di gestione report arriva qui e viene gestita in base al messaggio d'errore passato a parametro nel campo errorMessages.
    @ExceptionHandler(GenericReportOperationException.class)
    public ResponseEntity<Object> handleGenericOperationException(final GenericReportOperationException e, final WebRequest request) {
        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        response.setTotalCount(0L);
        HttpStatus httpStatusCode;

        String errorMessage = e.getMessage(); // Messaggio principale
        List<String> errorMessages = e.getErrorMessages(); // Lista di messaggi di errore

        // Aggiungi tutti i messaggi di errore alla risposta
        response.setMessages(errorMessages);

        if( e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_RESPONSE_NULLA) ||
            e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_INSERT_NEW_PARAM_ALREADY_PRESENT) ||
            e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_TOO_MANY_OCCUR) ||
            e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_PARAMS_DIFF_COUNT) ||
            e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_ERROR_NULL_FORM) ||
            e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_PROFILI_NOT_SELECTED) ||
            e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_UFFICI_NOT_SELECTED)
        ){
            response.setMessages(e.getErrorMessages());
            httpStatusCode = HttpStatus.BAD_REQUEST;
        }
        else if(e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_DEFSQL_NOT_VALID) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_DEFSQL_OPERATION_NOT_ALLOWED) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORT_NOT_AUTHORIZED_CRYPT_KEY)
        ){
            response.setMessages(e.getErrorMessages());
            httpStatusCode = HttpStatus.FORBIDDEN;
        }
        else if(e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_DETAIL_NOT_FOUND_REPORT) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_RESPONSE_DEFSQL_NOT_FOUND) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_DELETE_REPORT_NOT_FOUND) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_NOT_FOUND) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_PARAM_NOT_FOUND) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_ERROR_INSERT_NEW_REPORT_USER_NOT_FOUND) ||
                e.getErrorMessages().contains(AppConstants.GESTIONE_REPORTS_PARAM_OBBLIGATORIO_NOT_FOUND)
        ){
            response.setMessages(e.getErrorMessages());
            httpStatusCode = HttpStatus.NOT_FOUND;
        }
        else {
            response.setMessages(e.getErrorMessages());
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return handleExceptionInternal(e, response, new HttpHeaders(), httpStatusCode, request);
    }

    //Metodo per gestione dell'eccezione ricevuta dal parsing errato del formato data.
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParseException(final DateTimeParseException e, final WebRequest request) {
        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        response.setTotalCount(0L);
        response.getMessages().add(e.getMessage());

        return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //Metodo per controllo del tipo delle colonne presenti in query in riferimento alle colonne di tipo "bytea".
    //Solo in quel caso ho bisogno di gestire l'eccezione che arriva come eccezione generica.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExecutionQueryException(final Exception e, final WebRequest request) {
        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        response.setTotalCount(0L);
        HttpStatus httpStatusCode;

        if(e.getMessage().contains(AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_QUERY)){
            response.getMessages().add(e.getMessage());
            httpStatusCode = HttpStatus.BAD_REQUEST;
        }
        else{
            response.getMessages().add(e.getMessage());
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return handleExceptionInternal(e, response, new HttpHeaders(), httpStatusCode, request);
    }

}
