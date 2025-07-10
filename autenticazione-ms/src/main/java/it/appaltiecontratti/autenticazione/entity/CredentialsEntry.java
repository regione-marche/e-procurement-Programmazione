package it.appaltiecontratti.autenticazione.entity;

import it.appaltiecontratti.autenticazione.utils.ValidLoginResult;

public class CredentialsEntry {

	private ValidLoginResult validLogin;

	private String token;
	private String refreshToken;

	public ValidLoginResult getValidLogin() {
		return validLogin;
	}

	public void setValidLogin(ValidLoginResult validLogin) {
		this.validLogin = validLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
