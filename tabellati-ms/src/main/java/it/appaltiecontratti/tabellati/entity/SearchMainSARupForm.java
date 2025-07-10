package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModelProperty;

public class SearchMainSARupForm {
	
	@ApiModelProperty(value = "Codice stazione appaltante RUP da cercare")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "Syscon del RUP da cercare")
	private Long syscon;
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	
	

}
