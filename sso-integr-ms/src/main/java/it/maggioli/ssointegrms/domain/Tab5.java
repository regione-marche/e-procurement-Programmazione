package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "tab5")
public class Tab5 implements Serializable {

	private static final long serialVersionUID = 6131068067666883042L;

	@EmbeddedId
	private Tab5PK id;

	@Size(min = 0, max = 240)
	@Column(name = "tab5desc")
	private String descrizione;

	@Size(min = 0, max = 1)
	@Column(name = "tab5mod")
	private String bloccato;

	@Column(name = "tab5nord")
	private Double ordinamento;

	@Size(min = 0, max = 1)
	@Column(name = "tab5arc")
	private String archiviato;

	public Tab5PK getId() {
		return id;
	}

	public void setId(Tab5PK id) {
		this.id = id;
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

}
