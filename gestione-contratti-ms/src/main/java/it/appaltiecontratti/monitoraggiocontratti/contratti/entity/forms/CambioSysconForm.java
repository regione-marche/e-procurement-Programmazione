package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import javax.validation.constraints.NotNull;

public class CambioSysconForm {
	
	@NotNull
	private String idAvviso;
	@NotNull
	private Long syscon;
	@NotNull
	private String stazioneAppaltante;
	
	public String getIdAvviso() {
		return idAvviso;
	}
	public void setIdAvviso(String idAvviso) {
		this.idAvviso = idAvviso;
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
