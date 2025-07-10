package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "eldaver")
public class Eldaver implements Serializable {

	private static final long serialVersionUID = -8242071746357635748L;

	@Id
	@NotBlank
	@Size(min = 1, max = 10)
	@Column(name = "codapp")
	private String codApp;

	@Size(min = 0, max = 10)
	@Column(name = "numver")
	private String numVer;

	@Column(name = "datvet")
	private Date datVet;

	@Size(min = 0, max = 2000)
	@Column(name = "msg")
	private String msg;

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getNumVer() {
		return numVer;
	}

	public void setNumVer(String numVer) {
		this.numVer = numVer;
	}

	public Date getDatVet() {
		return datVet;
	}

	public void setDatVet(Date datVet) {
		this.datVet = datVet;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
