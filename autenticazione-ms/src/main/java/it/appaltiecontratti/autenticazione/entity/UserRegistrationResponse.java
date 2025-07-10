package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class UserRegistrationResponse implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	public static final String MAIL_NOT_SENT = "Errore. Non è stato possibile recapitare l'email all'indirizzo inserito.";
		
	/** Codice indicante un errore inaspettato. */
	public static final String USERNAME_ALREADY_EXISTS = "already-exists";

	public static final String CODICE_FISCALE_LOGIN_NULL = "CODICE_FISCALE_LOGIN_NULL";
	public static final String PASSWORD_NULL = "PASSWORD_NULL";
	public static final String CONFERMA_PASSWORD_NULL = "CONFERMA_PASSWORD_NULL";
	public static final String PASSWORD_CONFIRM_PASSWORD_MISMATCH = "PASSWORD_CONFIRM_PASSWORD_MISMATCH";
	public static final String PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS = "PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS";
	public static final String PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS = "PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS";
	public static final String PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS = "PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS";
	public static final String PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS = "PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS";
	public static final String PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS = "PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS";
	public static final String PASSWORD_CONTENT_FOLLOWING_CHARACTERS = "PASSWORD_CONTENT_FOLLOWING_CHARACTERS";

	public static final String PASSWORD_LENGTH = "PASSWORD_LENGTH";

	public static final String CAPTCHA_ERROR = "CAPTCHA.ERROR-SOLUTION";

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggi di errore di formalita' password")
	private List<String> passwordErrors;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="syscon utente creato")
	private String data;

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public List<String> getPasswordErrors() {
		return passwordErrors;
	}

	public void setPasswordErrors(List<String> passwordErrors) {
		this.passwordErrors = passwordErrors;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}