package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import javax.validation.constraints.NotNull;

public class PulisciAccorpamentiMultilottoForm {

	@NotNull
	private Long codGara;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

}
