package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.InserimentoResult;

@ApiModel(description = "Risultato della pubblicazione")
public class ResponsePubblicaFase extends InserimentoResult implements
		Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private Boolean pubblicato;
	
	@ApiModelProperty(value = "Esito opetrazione di pubblicazione")
	private Boolean esito;
	private boolean errors;
	private List<String> internalErrors;
	private List<String> validationErrors;
	private List<String> anacErrors;
	
	private String govwayMessageId;
	private String govwayTransactionId;
	private String cartId;

	public String getGovwayMessageId() {
		return govwayMessageId;
	}

	public void setGovwayMessageId(String govwayMessageId) {
		this.govwayMessageId = govwayMessageId;
	}

	public String getGovwayTransactionId() {
		return govwayTransactionId;
	}

	public void setGovwayTransactionId(String govwayTransactionId) {
		this.govwayTransactionId = govwayTransactionId;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public List<String> getInternalErrors() {
		return internalErrors;
	}

	public void setInternalErrors(List<String> internalErrors) {
		this.internalErrors = internalErrors;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public List<String> getAnacErrors() {
		return anacErrors;
	}

	public void setAnacErrors(List<String> anacErrors) {
		this.anacErrors = anacErrors;
	}

	public Boolean getPubblicato() {
		return pubblicato;
	}

	public void setPubblicato(Boolean pubblicato) {
		this.pubblicato = pubblicato;
	}

	public boolean isErrors() {
		return errors;
	}

	public void setErrors(boolean errors) {
		this.errors = errors;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	
	

}