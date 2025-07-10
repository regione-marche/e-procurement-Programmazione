package it.appaltiecontratti.autenticazione.entity;

import javax.validation.constraints.NotNull;

public class LoginWithCredentialsForm {

	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String passwordAmministratore;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordAmministratore() {
		return passwordAmministratore;
	}

	public void setPasswordAmministratore(String passwordAmministratore) {
		this.passwordAmministratore = passwordAmministratore;
	}

}
