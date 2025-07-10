package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

public class UsrSysconEntry implements Serializable {

	private static final long serialVersionUID = -2172589847487137764L;

	private Long syscon;
	private String nome;
	private String cf;

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

}
