package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

public class SSOUserInfo implements Serializable {

	private static final long serialVersionUID = -5473581953485525252L;

	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String nomeCompleto;
	private String login;
	private String email;
	private String levelOfAccess;
	private String provider;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLevelOfAccess() {
		return levelOfAccess;
	}

	public void setLevelOfAccess(String levelOfAccess) {
		this.levelOfAccess = levelOfAccess;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "SSOUserInfo{" +
				"codiceFiscale='" + codiceFiscale + '\'' +
				", nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", nomeCompleto='" + nomeCompleto + '\'' +
				", login='" + login + '\'' +
				", email='" + email + '\'' +
				", levelOfAccess='" + levelOfAccess + '\'' +
				", provider='" + provider + '\'' +
				'}';
	}
}
