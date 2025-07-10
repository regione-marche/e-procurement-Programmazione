package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Risultato pubblicazione.
 * 
 * @author Mirco.Franzoni
 */
@ApiModel(description = "Risultato della pubblicazione")
public class PubblicazioneResult extends InserimentoResult implements
		Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(value = "Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive")
	private Long id;
	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

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

}
