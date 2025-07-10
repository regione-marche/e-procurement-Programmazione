package it.appaltiecontratti.autenticazione.entity;

import java.util.List;

public class RegistrationAdminMailModel {

	private String nomeUtente;
	private List<ApplicativiModel> listaApplicativi;
	private String cognomeUtente;
	private String telefonoUtente;
	private String emailUtente;
	private String messaggioMail;
	private String statoRegistrazioneUtente;
	private String nomeEnte;
	
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public List<ApplicativiModel> getListaApplicativi() {
		return listaApplicativi;
	}
	public void setListaApplicativi(List<ApplicativiModel> listaApplicativi) {
		this.listaApplicativi = listaApplicativi;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public String getTelefonoUtente() {
		return telefonoUtente;
	}
	public void setTelefonoUtente(String telefonoUtente) {
		this.telefonoUtente = telefonoUtente;
	}
	public String getEmailUtente() {
		return emailUtente;
	}
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
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
	public String getNomeEnte() {
		return nomeEnte;
	}
	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	
}