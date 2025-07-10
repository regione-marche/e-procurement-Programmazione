package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "usrcanc")
public class UsrCanc implements Serializable {

	private static final long serialVersionUID = 2012585926156798750L;

	@Id
	@NotNull
	@Column(name = "id")
	private Long id;

	@Column(name = "syscon")
	private Long syscon;

	@Size(min = 0, max = 60)
	@Column(name = "syslogin")
	private String login;

	@Column(name = "sysscad")
	private Date dataScadenza;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

}
