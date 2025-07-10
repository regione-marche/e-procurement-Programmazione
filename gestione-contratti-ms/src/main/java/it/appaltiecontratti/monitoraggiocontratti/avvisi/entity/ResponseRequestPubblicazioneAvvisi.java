package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per il risultato dell'operazione di get request pubblicazione avviso")
public class ResponseRequestPubblicazioneAvvisi implements Serializable {

	private static final long serialVersionUID = 8385232236735829706L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	public static final String SA_NOT_FOUND = "SA-NOT-FOUND";

	@ApiModelProperty(value = "Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati dell'avviso")
	private PubblicazioneAvvisoEntry data;

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

	public PubblicazioneAvvisoEntry getData() {
		return data;
	}

	public void setData(PubblicazioneAvvisoEntry data) {
		this.data = data;
	}

}
