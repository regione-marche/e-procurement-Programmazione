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
@Table(name = "tab0")
public class Tab0 implements Serializable {

	private static final long serialVersionUID = 4764855042785860701L;

	@EmbeddedId
	private Tab0PK id;

	@Size(min = 0, max = 3)
	@Column(name = "tab0vid")
	private String tab0vid;

	@Size(min = 0, max = 60)
	@Column(name = "tab0desc")
	private String descrizione;

	@Size(min = 0, max = 1)
	@Column(name = "tab0mod")
	private String bloccato;

	@Column(name = "tab0nord")
	private Double ordinamento;

	@Size(min = 0, max = 1)
	@Column(name = "tab0arc")
	private String archiviato;

	public Tab0PK getId() {
		return id;
	}

	public void setId(Tab0PK id) {
		this.id = id;
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

}
