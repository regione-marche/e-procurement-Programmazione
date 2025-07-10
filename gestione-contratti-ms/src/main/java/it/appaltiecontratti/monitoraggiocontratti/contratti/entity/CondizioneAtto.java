package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import org.apache.ibatis.annotations.Select;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttoEntry;

public class CondizioneAtto extends AttoEntry {

	@ApiModelProperty(value = "condizione da query per la visibilita dell'atto")
	private String condizione;
	
	
	private int numLottiTotali;
	
	private int numAtti;
	
	private int numLottiConAtto;
	
	private int numLottiAttoPubblicato;

	public String getCondizione() {
		return condizione;
	}

	public void setCondizione(String condizione) {
		this.condizione = condizione;
	}

	public int getNumLottiTotali() {
		return numLottiTotali;
	}

	public void setNumLottiTotali(int numLottiTotali) {
		this.numLottiTotali = numLottiTotali;
	}

	public int getNumAtti() {
		return numAtti;
	}

	public void setNumAtti(int numAtti) {
		this.numAtti = numAtti;
	}

	public int getNumLottiConAtto() {
		return numLottiConAtto;
	}

	public void setNumLottiConAtto(int numLottiConAtto) {
		this.numLottiConAtto = numLottiConAtto;
	}

	public int getNumLottiAttoPubblicato() {
		return numLottiAttoPubblicato;
	}

	public void setNumLottiAttoPubblicato(int numLottiAttoPubblicato) {
		this.numLottiAttoPubblicato = numLottiAttoPubblicato;
	}

}
