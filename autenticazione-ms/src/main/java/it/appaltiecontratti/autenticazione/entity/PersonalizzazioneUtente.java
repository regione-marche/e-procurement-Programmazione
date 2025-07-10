package it.appaltiecontratti.autenticazione.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.List;

public class PersonalizzazioneUtente {

	@XSSValidation
	private String sysab3;
	@XSSValidation
	private String centroCosto;
	private Long idCentroCosto;
	List<CentroCosto> centriCosto;

	public String getSysab3() {
		return sysab3;
	}

	public void setSysab3(String sysab3) {
		this.sysab3 = sysab3;
	}

	public String getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Long getIdCentroCosto() {
		return idCentroCosto;
	}

	public void setIdCentroCosto(Long idCentroCosto) {
		this.idCentroCosto = idCentroCosto;
	}

	public List<CentroCosto> getCentriCosto() {
		return centriCosto;
	}

	public void setCentriCosto(List<CentroCosto> centriCosto) {
		this.centriCosto = centriCosto;
	}

}
