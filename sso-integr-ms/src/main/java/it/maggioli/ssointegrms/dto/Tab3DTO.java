package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class Tab3DTO implements Serializable {

	private static final long serialVersionUID = -6145599009112217171L;

	@NotBlank
	@Size(min = 1, max = 5)
	private String tab3cod;
	@NotBlank
	@Size(min = 1, max = 5)
	private String tab3tip;
	@Size(min = 0, max = 100)
	private String descrizione;
	private String bloccato;
	private Double ordinamento;
	private String archiviato;

	public String getTab3cod() {
		return tab3cod;
	}

	public void setTab3cod(String tab3cod) {
		this.tab3cod = tab3cod;
	}

	public String getTab3tip() {
		return tab3tip;
	}

	public void setTab3tip(String tab3tip) {
		this.tab3tip = tab3tip;
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
		return "Tab3DTO [tab3cod=" + tab3cod + ", tab3tip=" + tab3tip + ", descrizione=" + descrizione + ", bloccato="
				+ bloccato + ", ordinamento=" + ordinamento + ", archiviato=" + archiviato + "]";
	}

}
