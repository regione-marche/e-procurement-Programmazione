package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@ApiModel(description = "Contenitore per il risultato dell'operazione di lista avvisi non paginata")
public class ResponseListaAvvisiNonPaginata implements Serializable {
	
	private static final long serialVersionUID = -1499226258800560448L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value = "Risultato operazione di lista avvisi non paginata")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Lista avvisi non paginata")
	private List<AvvisoEntry> data;

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

	public List<AvvisoEntry> getData() {
		return data;
	}

	public void setData(List<AvvisoEntry> entry) {
		this.data = entry;
	}

}
