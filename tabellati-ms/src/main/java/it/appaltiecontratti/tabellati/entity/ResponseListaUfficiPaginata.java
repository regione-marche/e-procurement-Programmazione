package it.appaltiecontratti.tabellati.entity;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Cristiano Perin
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di lista Uffici")
public class ResponseListaUfficiPaginata implements Serializable {
	
	private static final long serialVersionUID = -5272957384737034723L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dei tecnici")
	private List<UffEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale degli uffici estraibili dalla form di ricerca")
	private int totalCount;

	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";
	
	public static final String ERROR_NOT_FOUND = "not-found";

	@ApiModelProperty(value="Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	
	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<UffEntry> getData() {
		return data;
	}

	public void setData(List<UffEntry> data) {
		this.data = data;
	}
	
}