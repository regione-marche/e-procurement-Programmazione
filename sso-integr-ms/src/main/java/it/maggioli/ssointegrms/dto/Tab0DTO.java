package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class Tab0DTO implements Serializable {

	private static final long serialVersionUID = 3047834631279151711L;

	@NotBlank
	@Size(min = 1, max = 5)
	private String tab0cod;
	@NotBlank
	@Size(min = 1, max = 1)
	private String tab0tip;
	@Size(min = 0, max = 3)
	private String tab0vid;
	@Size(min = 0, max = 60)
	private String descrizione;
	private String bloccato;
	private Double ordinamento;
	private String archiviato;

	public String getTab0cod() {
		return tab0cod;
	}

	public void setTab0cod(String tab0cod) {
		this.tab0cod = tab0cod;
	}

	public String getTab0tip() {
		return tab0tip;
	}

	public void setTab0tip(String tab0tip) {
		this.tab0tip = tab0tip;
	}

	public String getTab0vid() {
		return tab0vid;
	}

	public void setTab0vid(String tab0vid) {
		this.tab0vid = tab0vid;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getBloccato() {
		return bloccato;
	}

	public void setBloccato(String bloccato) {
		this.bloccato = bloccato;
	}

	public Double getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Double ordinamento) {
		this.ordinamento = ordinamento;
	}

	public String getArchiviato() {
		return archiviato;
	}

	public void setArchiviato(String archiviato) {
		this.archiviato = archiviato;
	}

	@Override
	public String toString() {
		return "Tab0DTO [tab0cod=" + tab0cod + ", tab0tip=" + tab0tip + ", tab0vid=" + tab0vid + ", descrizione="
				+ descrizione + ", bloccato=" + bloccato + ", ordinamento=" + ordinamento + ", archiviato=" + archiviato
				+ "]";
	}

}
