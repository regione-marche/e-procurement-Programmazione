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
@Table(name = "w_cachemodpar")
public class WCachemodpar implements Serializable {

	private static final long serialVersionUID = 7198420394926092077L;

	@EmbeddedId
	private WCachemodparPK id;

	@Size(min = 0, max = 512)
	@Column(name = "valore")
	private String valore;

	public WCachemodparPK getId() {
		return id;
	}

	public void setId(WCachemodparPK id) {
		this.id = id;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}
