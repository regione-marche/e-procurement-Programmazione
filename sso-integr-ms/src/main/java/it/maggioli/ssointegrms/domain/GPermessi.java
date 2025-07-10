package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "g_permessi")
public class GPermessi implements Serializable {

	private static final long serialVersionUID = 3204580523261031965L;

	@Id
	@NotNull
	@Column(name = "numper")
	private Long numper;

	@Column(name = "syscon")
	private Long syscon;

	public Long getNumper() {
		return numper;
	}

	public void setNumper(Long numper) {
		this.numper = numper;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

}
