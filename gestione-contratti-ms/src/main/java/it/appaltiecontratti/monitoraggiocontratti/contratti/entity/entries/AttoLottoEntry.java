package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import io.swagger.annotations.ApiModelProperty;

public class AttoLottoEntry extends LottoBaseEntry{

	@ApiModelProperty(value="flag che identifica se l'atto sia associato al lotto")
	private boolean associato;

	public boolean isAssociato() {
		return associato;
	}

	public void setAssociato(boolean associato) {
		this.associato = associato;
	}
	
	
}
