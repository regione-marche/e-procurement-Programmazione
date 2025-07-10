package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author andrea.chinellato
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di lista atti generali")
public class ResponseListaAttiGenerali implements Serializable {

    private static final long serialVersionUID = -5376882272689851112L;

    /** Codice indicante un errore inaspettato. */
    public static final String ERROR_UNEXPECTED = "unexpected-error";

    @ApiModelProperty(value="Risultato operazione")
    private boolean esito;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
    private String errorData;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="Dati degli atti generali")
    private List<AttoGeneraleEntry> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="Numero totale degli atti generali estraibili dalla form di ricerca")
    private int totalCount;

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }
    public String getErrorData() {
        return errorData;
    }

    public void setEsito(boolean esito) {
        this.esito = esito;
    }
    public boolean isEsito() {
        return esito;
    }

    public List<AttoGeneraleEntry> getData() { return data; }
    public void setData(List<AttoGeneraleEntry> entry) { this.data = entry; }

    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
