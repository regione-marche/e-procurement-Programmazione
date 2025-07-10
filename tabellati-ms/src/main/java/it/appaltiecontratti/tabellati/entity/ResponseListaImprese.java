package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
*
* @author Michele.DiNapoli
*/

@ApiModel(description="Contenitore per il risultato dell'operazione di lista programmi")
public class ResponseListaImprese implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base delle imprese")
	private List<ImpresaBaseEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale delle imprese estraibili dalla form di ricerca")
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

	public List<ImpresaBaseEntry> getData() {
		return data;
	}

	public void setData(List<ImpresaBaseEntry> data) {
		this.data = data;
	}
	
}