package it.appaltiecontratti.autenticazione.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class CentroCosto {

	private Long id;
	@XSSValidation
	private String denom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenom() {
		return denom;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}

}
