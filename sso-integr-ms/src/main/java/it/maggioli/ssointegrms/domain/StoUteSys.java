package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "stoutesys")
public class StoUteSys implements Serializable {

	private static final long serialVersionUID = 1297745743587073364L;

	@EmbeddedId
	private StoUteSysPK id;

	@Column(name = "syscon")
	private Long syscon;

	@Column(name = "sysdat")
	private Date sysdat;

	@Size(min = 0, max = 60)
	@Column(name = "syslogin")
	private String syslogin;

	public StoUteSysPK getId() {
		return id;
	}

	public void setId(StoUteSysPK id) {
		this.id = id;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public Date getSysdat() {
		return sysdat;
	}

	public void setSysdat(Date sysdat) {
		this.sysdat = sysdat;
	}

	public String getSyslogin() {
		return syslogin;
	}

	public void setSyslogin(String syslogin) {
		this.syslogin = syslogin;
	}

}
