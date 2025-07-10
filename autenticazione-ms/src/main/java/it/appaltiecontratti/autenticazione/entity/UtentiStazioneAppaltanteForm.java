package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class UtentiStazioneAppaltanteForm implements Serializable {

	private static final long serialVersionUID = 8301026347383386002L;

	@NotNull
	private String codice;
	@NotNull
	private List<Long> listaUtentiStazioneAppaltante;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public List<Long> getListaUtentiStazioneAppaltante() {
		return listaUtentiStazioneAppaltante;
	}

	public void setListaUtentiStazioneAppaltante(List<Long> listaUtentiStazioneAppaltante) {
		this.listaUtentiStazioneAppaltante = listaUtentiStazioneAppaltante;
	}

}
