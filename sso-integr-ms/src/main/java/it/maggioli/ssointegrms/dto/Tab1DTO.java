package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class Tab1DTO implements Serializable {

	private static final long serialVersionUID = -395141591345973767L;

	@NotBlank
	@Size(min = 1, max = 5)
	private String tab1cod;
	@NotNull
	private Long tab1tip;
	@Size(min = 0, max = 200)
	private String descrizione;
	private String bloccato;
	private Double ordinamento;
	private String archiviato;

	public String getTab1cod() {
		return tab1cod;
	}

	public void setTab1cod(String tab1cod) {
		this.tab1cod = tab1cod;
	}

	public Long getTab1tip() {
		return tab1tip;
	}

	public void setTab1tip(Long tab1tip) {
		this.tab1tip = tab1tip;
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
		return "Tab1DTO [tab1cod=" + tab1cod + ", tab1tip=" + tab1tip + ", descrizione=" + descrizione + ", bloccato="
				+ bloccato + ", ordinamento=" + ordinamento + ", archiviato=" + archiviato + "]";
	}

}
