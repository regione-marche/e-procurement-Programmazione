package it.appaltiecontratti.gestionereportsms.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andrea.chinellato
 * Description: Generic Exception for all operations within the microservice
 * */

@Getter
@Setter
public class GenericReportOperationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1849824381162368815L;

    private List<String> errorMessages = new ArrayList<>();

    public GenericReportOperationException() {
        super();
    }

    public GenericReportOperationException(final String message) {
        super(message);
    }

    public GenericReportOperationException(final String message, final String detailedErrorQuery) {
        super(message);
        this.errorMessages.add(message);
        this.errorMessages.add(detailedErrorQuery);
    }

}
