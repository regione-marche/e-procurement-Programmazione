package it.appaltiecontratti.inbox.entity.contratti;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.InserimentoResult;

/**
 * Risultato pubblicazione.
 * 
 * @author Mirco.Franzoni
 */
@ApiModel(description = "Risultato della pubblicazione")
public class PubblicazioneAttoResult extends InserimentoResult implements
		Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(value = "Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive")
	private Long idExArt29;

	@ApiModelProperty(value = "Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive")
	private Long idGara;
	
	@ApiModelProperty(value = "Esito opetrazione di pubblicazione")
	private Boolean esito;

	public void setIdExArt29(Long idExArt29) {
		this.idExArt29 = idExArt29;
	}

	public Long getIdExArt29() {
		return idExArt29;
	}

	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	public Long getIdGara() {
		return idGara;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	
	

}
