package it.appaltiecontratti.programmi.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ProgrammaUpdateForm {

	@NotNull
	private Long id;
	@XSSValidation
	private String descrizioneBreve;
	@XSSValidation
	private String numeroAtto;
	@XSSValidation
	private String numeroApprovazione;
	private Date dataPubblicazioneAdo;
	private Date dataAtto;
	@XSSValidation
	private String titoloAdozione;
	@XSSValidation
	private String urlAdozione;
	private Date dataPubblicazioneAppr;
	private Date dataApprovazione;
	@XSSValidation
	private String titoloApprovazione;
	@XSSValidation
	private String urlApprovazione;
	@XSSValidation
	private String responsabile;
	private Long idUfficio;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}
	public void setDescrizioneBreve(String descrzioneBreve) {
		this.descrizioneBreve = descrzioneBreve;
	}
	public String getNumeroAtto() {
		return numeroAtto;
	}
	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}
	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
	}
	public Date getDataPubblicazioneAdo() {
		return dataPubblicazioneAdo;
	}
	public void setDataPubblicazioneAdo(Date dataPubblicazioneAdo) {
		this.dataPubblicazioneAdo = dataPubblicazioneAdo;
	}
	public Date getDataAtto() {
		return dataAtto;
	}
	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}
	public String getTitoloAdozione() {
		return titoloAdozione;
	}
	public void setTitoloAdozione(String titoloAdozione) {
		this.titoloAdozione = titoloAdozione;
	}
	public String getUrlAdozione() {
		return urlAdozione;
	}
	public void setUrlAdozione(String urlAdozione) {
		this.urlAdozione = urlAdozione;
	}
	public Date getDataPubblicazioneAppr() {
		return dataPubblicazioneAppr;
	}
	public void setDataPubblicazioneAppr(Date dataPubblicazioneAppr) {
		this.dataPubblicazioneAppr = dataPubblicazioneAppr;
	}
	public Date getDataApprovazione() {
		return dataApprovazione;
	}
	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	public String getTitoloApprovazione() {
		return titoloApprovazione;
	}
	public void setTitoloApprovazione(String titoloApprovazione) {
		this.titoloApprovazione = titoloApprovazione;
	}
	public String getUrlApprovazione() {
		return urlApprovazione;
	}
	public void setUrlApprovazione(String urlApprovazione) {
		this.urlApprovazione = urlApprovazione;
	}
	public String getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}
	public Long getIdUfficio() {
		return idUfficio;
	}
	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}
	
}
