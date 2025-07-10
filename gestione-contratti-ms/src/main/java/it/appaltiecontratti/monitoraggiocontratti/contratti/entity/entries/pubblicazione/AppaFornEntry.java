package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "*Modalit� acquisizione forniture")
public class AppaFornEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	@ApiModelProperty(value = "Codice della gara", hidden = true)
	private Long idGara;
	@ApiModelProperty(value = "Codice del lotto", hidden = true)
	private Long idLotto;
	@ApiModelProperty(value = "Numero progressivo modalit�", hidden = true)
	private Long numAppaForn;
	@ApiModelProperty(value = "modalità di acquisizione forniture/servizi", required = true)
	private Long modalitaAcquisizione;

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

	public void setNumAppaForn(Long numAppaForn) {
		this.numAppaForn = numAppaForn;
	}

	public Long getNumAppaForn() {
		return numAppaForn;
	}

	public void setModalitaAcquisizione(Long modalitaAcquisizione) {
		this.modalitaAcquisizione = modalitaAcquisizione;
	}

	public Long getModalitaAcquisizione() {
		return modalitaAcquisizione;
	}

}
