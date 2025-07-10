package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.programmi.entity.validazione.ValidateEntry;

@ApiModel(description = "Contenitore per il risultato di una operazione di inserimento")
public class InserimentoResultSCP implements Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice di errore in fase di controllo di validazione dei dati */
	public static final String ERROR_VALIDATION = "errore validazione dati";

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String error;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale lista dei controlli di validazione che hanno generato errore")
	private List<ValidateEntry> validate;

	public void setError(String errorData) {
		this.error = errorData;
	}

	public String getError() {
		return error;
	}

	public void setValidate(List<ValidateEntry> validate) {
		this.validate = validate;
	}

	public List<ValidateEntry> getValidate() {
		return validate;
	}

}
