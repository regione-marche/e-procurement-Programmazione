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
@Table(name = "g_confcod")
public class GConfCod implements Serializable {

	private static final long serialVersionUID = 3202239037626989332L;

	@EmbeddedId
	private GConfCodPK id;

	@Size(min = 0, max = 10)
	@Column(name = "codapp")
	private String codApp;

	@Size(min = 0, max = 100)
	@Column(name = "descam")
	private String desCam;

	@Column(name = "contat")
	private Long contat;

	@Size(min = 0, max = 100)
	@Column(name = "codcal")
	private String codCal;

	@Column(name = "numord")
	private Long numOrd;

	public GConfCodPK getId() {
		return id;
	}

	public void setId(GConfCodPK id) {
		this.id = id;
	}

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getDesCam() {
		return desCam;
	}

	public void setDesCam(String desCam) {
		this.desCam = desCam;
	}

	public Long getContat() {
		return contat;
	}

	public void setContat(Long contat) {
		this.contat = contat;
	}

	public String getCodCal() {
		return codCal;
	}

	public void setCodCal(String codCal) {
		this.codCal = codCal;
	}

	public Long getNumOrd() {
		return numOrd;
	}

	public void setNumOrd(Long numOrd) {
		this.numOrd = numOrd;
	}

}
