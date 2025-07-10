package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * Contenitore per la response alla richiesta di informazioni dell'account.
 *
 * @author Michele.DiNapoli
 */
public class UserAccountResult {
	
	
	/** Codice di errore nel caso utente non trovato */
	public static final String NO_USER = "NO-USER";
	/** Codice di errore nel caso non sia associato a nessuna stazione appaltante */
	public static final String NO_SA = "NO-SA";
	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";
	
	

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati info dell'account")
	private UserAccountInfo data;

	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale codice di errore")
	public UserAccountInfo getData() {
		return data;
	}

	public void setData(UserAccountInfo data) {
		this.data = data;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
}
