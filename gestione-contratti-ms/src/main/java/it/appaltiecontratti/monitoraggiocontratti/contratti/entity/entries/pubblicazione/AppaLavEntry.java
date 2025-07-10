package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "*Tipologia del lavoro")
public class AppaLavEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	@ApiModelProperty(value = "Codice della gara", hidden = true)
	private Long idGara;
	@ApiModelProperty(value = "Codice del lotto", hidden = true)
	private Long idLotto;
	@ApiModelProperty(value = "Numero progressivo tipologia", hidden = true)
	private Long numAppaLav;
	@ApiModelProperty(value = "Tipologia del lavoro", required = true)
	private Long tipologiaLavoro;

	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	public Long getIdGara() {
		return idGara;
	}

	public void setIdLotto(Long idLotto) {
		this.idLotto = idLotto;
	}

	public Long getIdLotto() {
		return idLotto;
	}

	public void setNumAppaLav(Long numAppaLav) {
		this.numAppaLav = numAppaLav;
	}

	public Long getNumAppaLav() {
		return numAppaLav;
	}

	public void setTipologiaLavoro(Long tipologiaLavoro) {
		this.tipologiaLavoro = tipologiaLavoro;
	}

	public Long getTipologiaLavoro() {
		return tipologiaLavoro;
	}

}
