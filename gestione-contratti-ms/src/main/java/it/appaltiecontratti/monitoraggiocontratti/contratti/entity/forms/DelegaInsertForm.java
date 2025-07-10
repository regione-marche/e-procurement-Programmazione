package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaEntry;

public class DelegaInsertForm extends DelegaEntry {

	@XSSValidation
	private String stazioneAppaltante;
	private Long syscon;
	@XSSValidation
	private String idProfilo;

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

	public String getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}

}
