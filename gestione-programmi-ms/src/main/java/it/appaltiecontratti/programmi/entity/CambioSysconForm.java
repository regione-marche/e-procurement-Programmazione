package it.appaltiecontratti.programmi.entity;

import javax.validation.constraints.NotNull;

public class CambioSysconForm {
	
	@NotNull
	private Long contri;
	@NotNull
	private Long syscon;
	@NotNull
	private String stazioneAppaltante;
	
	public Long getContri() {
		return contri;
	}
	public void setContri(Long contri) {
		this.contri = contri;
	}
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	
}
