package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

public class ResponseMaxNumAvan implements Serializable {

    private static final long serialVersionUID = 7427267255865578849L;

    private Long data;
    private Boolean esito;
    private String errorData;
    private Long totalCount;

    public void setData(Long data) { this.data = data; }
    public Long getData() { return data; }

    public void setEsito(Boolean esito) { this.esito = esito; }
    public Boolean getEsito() { return esito; }

    public void setErrorData(String errorData) { this.errorData = errorData; }
    public String getErrorData() { return errorData; }

    public void setTotalCount(Long totalCount) { this.totalCount = totalCount; }
    public Long getTotalCount() { return totalCount; }
}
