package it.appaltiecontratti.tabellati.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Wrapper di risposta parametrico
 *
 * @author Cristiano Perin
 */

@ApiModel(description = "Wrapper di risposta parametrico")
public class ResponseResultParametric<T> extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -2636537656483107089L;

    public static final String ERROR_UNEXPECTED = "unexpected-error";

    public static final String ERROR_ENTITY_HAS_CONNECTIONS = "ERROR_ENTITY_HAS_CONNECTIONS";

    public static final String ERROR_EXISTING_CF = "EXISTING-CF";

    public static final String ERROR_EXISTING_PARTITA_IVA = "EXISTING_PARTITA_IVA";

    public static final String ERROR_INVALID_CF = "INVALID_CF";

    public static final String ERROR_INVALID_PARTITA_IVA = "INVALID_PARTITA_IVA";

    public static final String ERROR_NOT_FOUND = "NOT-FOUND";

    @ApiModelProperty(value = "Risultato operazione")
    private boolean esito;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "data")
    private T data;

    public void setEsito(boolean esito) {
        this.esito = esito;
    }

    public boolean isEsito() {
        return esito;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}