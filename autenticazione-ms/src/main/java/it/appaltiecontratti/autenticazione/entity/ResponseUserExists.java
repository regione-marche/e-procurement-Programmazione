package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Risultato servizio verifica esistenza utente
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di controllo esistenza utente")
public class ResponseUserExists implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 4631493267480782357L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	public static final String NO_USER = "NO_USER";
	
	public static final String OK_USER = "OK_USER";
	
	public static final String DISABLED_USER = "DISABLED_USER";
	
	public static final String EXPIRED_USER = "EXPIRED_USER";

	@ApiModelProperty(value="Risultato operazione di verifica")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Flag esistenza utente/ abilitazione form di registrazione")
	private UserExistsInfo data;
	
	

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

	public UserExistsInfo getData() {
		return data;
	}

	public void setData(UserExistsInfo data) {
		this.data = data;
	}
}