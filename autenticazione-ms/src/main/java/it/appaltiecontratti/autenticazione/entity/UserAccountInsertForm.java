package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAccountInsertForm implements Serializable {
	private static final long serialVersionUID = 2572089103460119720L;
	public static final String STATO_ATTIVO = "A";
	public static final String STATO_SOSPESO = "S";
	public static final String STATO_REVOCATO = "R";
	private long idAccount;
	private String login;
	private String loginCriptata;
	private String password;
	private String nome;
	private String opzioniUtente;
	private Date dataInserimento;
	private String email;
	private Date scadenzaAccount;
	private String codfisc;
	private String stazioneAppaltante;
	
	public long getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(long idAccount) {
		this.idAccount = idAccount;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLoginCriptata() {
		return loginCriptata;
	}
	public void setLoginCriptata(String loginCriptata) {
		this.loginCriptata = loginCriptata;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getOpzioniUtente() {
		return opzioniUtente;
	}
	public void setOpzioniUtente(String opzioniUtente) {
		this.opzioniUtente = opzioniUtente;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getScadenzaAccount() {
		return scadenzaAccount;
	}
	public void setScadenzaAccount(Date scadenzaAccount) {
		this.scadenzaAccount = scadenzaAccount;
	}
	public String getCodfisc() {
		return codfisc;
	}
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	
}
