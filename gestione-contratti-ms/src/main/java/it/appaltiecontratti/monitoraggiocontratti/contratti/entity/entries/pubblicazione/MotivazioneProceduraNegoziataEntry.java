package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Motivazione ricorso a procedura negoziata")
public class MotivazioneProceduraNegoziataEntry implements Serializable {

	private static final long serialVersionUID = -4433185026855332865L;

	@ApiModelProperty(value = "Codice della gara", hidden = true)
	private Long idGara;
	@ApiModelProperty(value = "Codice del lotto", hidden = true)
	private Long idLotto;
	@ApiModelProperty(value = "Numero progressivo condizione", hidden = true)
	private Long numCondizione;
	@ApiModelProperty(value = "Condizione", required = true)
	private Long condizione;

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

	public void setNumCondizione(Long numCondizione) {
		this.numCondizione = numCondizione;
	}

	public Long getNumCondizione() {
		return numCondizione;
	}

	public void setCondizione(Long condizione) {
		this.condizione = condizione;
	}

	public Long getCondizione() {
		return condizione;
	}

}
