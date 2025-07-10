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
@Table(name = "w_att")
public class WAtt implements Serializable {

	private static final long serialVersionUID = 468380347944355779L;

	@EmbeddedId
	private WAttPK id;

	@Size(min = 0, max = 2000)
	@Column(name = "valore")
	private String valore;

	public WAttPK getId() {
		return id;
	}

	public void setId(WAttPK id) {
		this.id = id;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}
