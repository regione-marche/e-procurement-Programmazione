package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_genchiavi")
public class WGenChiavi implements Serializable {

	private static final long serialVersionUID = 5039038104904879351L;
	
	@Id
	private String tabella;
	private Long chiave;

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	public Long getChiave() {
		return chiave;
	}

	public void setChiave(Long chiave) {
		this.chiave = chiave;
	}

	@Override
	public String toString() {
		return "WGenChiavi [" + (tabella != null ? "tabella=" + tabella + ", " : "")
				+ (chiave != null ? "chiave=" + chiave : "") + "]";
	}
}
