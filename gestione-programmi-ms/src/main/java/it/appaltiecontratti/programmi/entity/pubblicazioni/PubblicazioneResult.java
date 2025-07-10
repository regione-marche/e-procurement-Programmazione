/*
 * Created on 01/giu/2017
 *
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.programmi.entity.pubblicazioni;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Risultato pubblicazione.
 * 
 * @author Mirco.Franzoni
 */
@ApiModel(description = "Risultato della pubblicazione")
public class PubblicazioneResult extends InserimentoResult implements
		Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(value = "Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive")
	private Long id;
	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

	private List<String> validationError;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public List<String> getValidationError() {
		return validationError;
	}

	public void setValidationError(List<String> validationError) {
		this.validationError = validationError;
	}
}
