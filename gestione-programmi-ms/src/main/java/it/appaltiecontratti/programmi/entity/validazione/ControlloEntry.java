package it.appaltiecontratti.programmi.entity.validazione;

import java.io.Serializable;

public class ControlloEntry implements Serializable {

	private static final long serialVersionUID = -4433185026855332865L;

	private Long numero;

	private String sezione;

	private String titolo;

	private String messaggio;

	private String tipo;

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getNumero() {
		return numero;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSezione() {
		return sezione;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
