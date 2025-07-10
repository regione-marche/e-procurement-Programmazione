package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description = "Contenitore per il risultato dell'operazione di accesso al servizio di Inserimento di un avviso")
public class ResponseResult extends BaseResponse implements Serializable{
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	public static final String ERROR_ENTITY_HAS_CONNECTIONS = "ERROR_ENTITY_HAS_CONNECTIONS";

	public static final String ERROR_EXISTING_CF = "EXISTING-CF";
	
	public static final String ERROR_EXISTING_PARTITA_IVA = "EXISTING_PARTITA_IVA";
	
	public static final String ERROR_INVALID_CF = "INVALID_CF";
	
	public static final String ERROR_INVALID_CF_LEGALE = "INVALID_CF_LEGALE";
	
	public static final String ERROR_INVALID_PARTITA_IVA = "INVALID_PARTITA_IVA";
	
	public static final String ERROR_NOT_FOUND = "NOT-FOUND";

	@ApiModelProperty(value = "Risultato operazione di inserimento")
	private boolean esito;


	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "numero avviso creato")
	private String data;

	


	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}