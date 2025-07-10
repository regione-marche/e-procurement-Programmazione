package it.appaltiecontratti.autenticazione.entity;

public class AccountInfo {

	private String nominativo;
	private String cf;
	private String password;
	private String syslogin;

	public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSyslogin() {
		return syslogin;
	}

	public void setSyslogin(String syslogin) {
		this.syslogin = syslogin;
	}

}
