package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class Tab2DTO implements Serializable {

	private static final long serialVersionUID = 5723180215300888452L;

	@NotBlank
	@Size(min = 1, max = 5)
	private String tab2cod;
	@NotBlank
	@Size(min = 1, max = 5)
	private String tab2tip;
	@Size(min = 0, max = 200)
	private String parametroAssociato;
	@Size(min = 0, max = 200)
	private String descrizione;
	private String bloccato;
	private Double ordinamento;
	private String archiviato;

	public String getTab2cod() {
		return tab2cod;
	}

	public void setTab2cod(String tab2cod) {
		this.tab2cod = tab2cod;
	}

	public String getTab2tip() {
		return tab2tip;
	}

	public void setTab2tip(String tab2tip) {
		this.tab2tip = tab2tip;
	}

	public String getParametroAssociato() {
		return parametroAssociato;
	}

	public void setParametroAssociato(String parametroAssociato) {
		this.parametroAssociato = parametroAssociato;
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
		return "Tab2DTO [tab2cod=" + tab2cod + ", tab2tip=" + tab2tip + ", parametroAssociato=" + parametroAssociato
				+ ", descrizione=" + descrizione + ", bloccato=" + bloccato + ", ordinamento=" + ordinamento
				+ ", archiviato=" + archiviato + "]";
	}

}
