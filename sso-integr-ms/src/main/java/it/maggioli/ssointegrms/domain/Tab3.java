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
@Table(name = "tab3")
public class Tab3 implements Serializable {

	private static final long serialVersionUID = 8757960908198856570L;

	@EmbeddedId
	private Tab3PK id;

	@Size(min = 0, max = 100)
	@Column(name = "tab3desc")
	private String descrizione;

	@Size(min = 0, max = 1)
	@Column(name = "tab3mod")
	private String bloccato;

	@Column(name = "tab3nord")
	private Double ordinamento;

	@Size(min = 0, max = 1)
	@Column(name = "tab3arc")
	private String archiviato;

	public Tab3PK getId() {
		return id;
	}

	public void setId(Tab3PK id) {
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
