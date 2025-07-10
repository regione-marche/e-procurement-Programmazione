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
@Table(name = "tab2")
public class Tab2 implements Serializable {

	private static final long serialVersionUID = -5692293878002503675L;

	@EmbeddedId
	private Tab2PK id;

	@Size(min = 0, max = 200)
	@Column(name = "tab2d1")
	private String parametroAssociato;

	@Size(min = 0, max = 200)
	@Column(name = "tab2d2")
	private String descrizione;

	@Size(min = 0, max = 1)
	@Column(name = "tab2mod")
	private String bloccato;

	@Column(name = "tab2nord")
	private Double ordinamento;

	@Size(min = 0, max = 1)
	@Column(name = "tab2arc")
	private String archiviato;

	public Tab2PK getId() {
		return id;
	}

	public void setId(Tab2PK id) {
		this.id = id;
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

}
