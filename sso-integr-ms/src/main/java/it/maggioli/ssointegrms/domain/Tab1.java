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
@Table(name = "tab1")
public class Tab1 implements Serializable {

	private static final long serialVersionUID = -100365119482432116L;

	@EmbeddedId
	private Tab1PK id;

	@Size(min = 0, max = 200)
	@Column(name = "tab1desc")
	private String descrizione;

	@Size(min = 0, max = 1)
	@Column(name = "tab1mod")
	private String bloccato;

	@Column(name = "tab1nord")
	private Double ordinamento;

	@Size(min = 0, max = 1)
	@Column(name = "tab1arc")
	private String archiviato;

	public Tab1PK getId() {
		return id;
	}

	public void setId(Tab1PK id) {
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
