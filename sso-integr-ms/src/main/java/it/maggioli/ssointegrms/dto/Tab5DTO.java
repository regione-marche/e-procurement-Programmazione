package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class Tab5DTO implements Serializable {

	private static final long serialVersionUID = -770988700740529892L;

	@NotBlank
	@Size(min = 1, max = 5)
	private String tab5cod;
	@NotBlank
	@Size(min = 1, max = 15)
	private String tab5tip;
	@Size(min = 0, max = 240)
	private String descrizione;
	private String bloccato;
	private Double ordinamento;
	private String archiviato;

	public String getTab5cod() {
		return tab5cod;
	}

	public void setTab5cod(String tab5cod) {
		this.tab5cod = tab5cod;
	}

	public String getTab5tip() {
		return tab5tip;
	}

	public void setTab5tip(String tab5tip) {
		this.tab5tip = tab5tip;
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
		return "Tab5DTO [tab5cod=" + tab5cod + ", tab5tip=" + tab5tip + ", descrizione=" + descrizione + ", bloccato="
				+ bloccato + ", ordinamento=" + ordinamento + ", archiviato=" + archiviato + "]";
	}

}
