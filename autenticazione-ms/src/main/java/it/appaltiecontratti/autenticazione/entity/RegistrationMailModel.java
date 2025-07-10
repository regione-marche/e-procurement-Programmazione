package it.appaltiecontratti.autenticazione.entity;

import java.util.ArrayList;
import java.util.List;

public class RegistrationMailModel {
	
	private String nominativoTecnico;
	private String cognomeTecnico;
	private String cfEnte;
	private String nomeEnte;
	private String tipologia;
	private String indirizzoEnte;
	private String telefono;
	private String fax;
	private String emailEnte;
	private String cfUtente;
	private String password;
	private String emailUtente;
	private String telefonoUtente;
	private List<ApplicativiModel> listaApplicativi;
	private String messaggioMail;
	private String statoRegistrazioneUtente;
	
	
	
	public String getNominativoTecnico() {
		return nominativoTecnico;
	}
	public void setNominativoTecnico(String nominativoTecnico) {
		this.nominativoTecnico = nominativoTecnico;
	}
	public String getCognomeTecnico() {
		return cognomeTecnico;
	}
	public void setCognomeTecnico(String cognomeTecnico) {
		this.cognomeTecnico = cognomeTecnico;
	}
	public String getCfEnte() {
		return cfEnte;
	}
	public void setCfEnte(String cfEnte) {
		this.cfEnte = cfEnte;
	}
	public String getNomeEnte() {
		return nomeEnte;
	}
	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getIndirizzoEnte() {
		return indirizzoEnte;
	}
	public void setIndirizzoEnte(String indirizzoEnte) {
		this.indirizzoEnte = indirizzoEnte;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmailEnte() {
		return emailEnte;
	}
	public void setEmailEnte(String emailEnte) {
		this.emailEnte = emailEnte;
	}
	public String getCfUtente() {
		return cfUtente;
	}
	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailUtente() {
		return emailUtente;
	}
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}
	public String getTelefonoUtente() {
		return telefonoUtente;
	}
	public void setTelefonoUtente(String telefonoUtente) {
		this.telefonoUtente = telefonoUtente;
	}
	public List<ApplicativiModel> getListaApplicativi() {
		return listaApplicativi;
	}
	public void setListaApplicativi(List<ApplicativiModel> listaApplicativi) {
		this.listaApplicativi = listaApplicativi;
	}
	public String getMessaggioMail() {
		return messaggioMail;
	}
	public void setMessaggioMail(String messaggioMail) {
		this.messaggioMail = messaggioMail;
	}
	public String getStatoRegistrazioneUtente() {
		return statoRegistrazioneUtente;
	}
	public void setStatoRegistrazioneUtente(String statoRegistrazioneUtente) {
		this.statoRegistrazioneUtente = statoRegistrazioneUtente;
	}
}