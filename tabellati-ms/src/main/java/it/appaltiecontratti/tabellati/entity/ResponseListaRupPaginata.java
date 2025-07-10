package it.appaltiecontratti.tabellati.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di lista programmi")
public class ResponseListaRupPaginata implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dei tecnici")
	private List<RupEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale dei tecnici estraibili dalla form di ricerca")
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

	public List<RupEntry> getData() {
		return data;
	}

	public void setData(List<RupEntry> data) {
		this.data = data;
	}
	
}