package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class StatiId {
	
	@ApiModelProperty(value="stato dell'atto")  
	private String stato;
	@ApiModelProperty(value="lista degli id di pubblicazione associati all'atto")
	private List<IdPubblicati> ids;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public List<IdPubblicati> getIds() {
		return ids;
	}
	public void setIds(List<IdPubblicati> ids) {
		this.ids = ids;
	}

}
