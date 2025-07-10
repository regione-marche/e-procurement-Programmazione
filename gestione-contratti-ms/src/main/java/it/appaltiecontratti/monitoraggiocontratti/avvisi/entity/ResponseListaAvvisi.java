package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di lista avvisi")
public class ResponseListaAvvisi implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati degli avvisi")
	private List<AvvisoEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale degli avvisi estraibili dalla form di ricerca")
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

	public List<AvvisoEntry> getData() {
		return data;
	}

	public void setData(List<AvvisoEntry> entry) {
		this.data = entry;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
