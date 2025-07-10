package it.appaltiecontratti.tabellati.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author andrea.chinellato
 * */

@ApiModel(description="Contenitore per il risultato dell'operazione di reperimento lista dei CIG")
public class ResponseListaCig extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 5608137805537900921L;

    /** Codice indicante un errore inaspettato. */
    public static final String ERROR_UNEXPECTED = "unexpected-error";

    @ApiModelProperty(value="Risultato operazione di lettura")
    private boolean esito;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="CIG dei lotti")
    private List<String> data;


    public void setEsito(boolean esito) {
        this.esito = esito;
    }
    public boolean isEsito() {
        return esito;
    }

    public List<String> getData() { return data; }
    public void setData(List<String> data) { this.data = data; }
}
