package it.appaltiecontratti.inbox.entity;

import javax.validation.constraints.NotNull;

public class RichiestaCancellazioneActionForm {

	@NotNull
	private Long idComunicazione;
	private String motivazione;

	public Long getIdComunicazione() {
		return idComunicazione;
	}

	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

}
