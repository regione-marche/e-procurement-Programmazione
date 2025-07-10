package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import javax.validation.constraints.NotNull;

public class RupGaraUpdateForm {

	@NotNull
	private Long codGara;
	private String rup;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}
}
