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
@Table(name = "w_config")
public class WConfig implements Serializable {

	private static final long serialVersionUID = 4632929928777492777L;

	@EmbeddedId
	private WConfigPK id;

	@Size(min = 0, max = 500)
	@Column(name = "valore")
	private String valore;

	@Size(min = 0, max = 500)
	@Column(name = "sezione")
	private String sezione;

	@Size(min = 0, max = 2000)
	@Column(name = "descrizione")
	private String descrizione;

	@Size(min = 0, max = 1)
	@Column(name = "criptato")
	private String criptato;

	public WConfigPK getId() {
		return id;
	}

	public void setId(WConfigPK id) {
		this.id = id;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCriptato() {
		return criptato;
	}

	public void setCriptato(String criptato) {
		this.criptato = criptato;
	}

}
