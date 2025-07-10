package it.appaltiecontratti.programmi.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ProgrammaInsertForm {
	
	
	private Long id;
	@XSSValidation
	private String idProgramma;
	@NotNull
	@XSSValidation
	private String stazioneappaltante;
	@NotNull
	private Long tipoProg;
	@XSSValidation
	private String descrizioneBreve;
	@NotNull
	private Long annoInizio;
	@XSSValidation
	private String numeroAtto; 
	private Date dataPubblicazioneAdo;
	private Date dataAtto;
	@XSSValidation
	private String titoloAdozione;
	@XSSValidation
	private String urlAdozione;
	@XSSValidation
	private String numeroApprovazione;
	private Date dataPubblicazioneAppr;
	private Date dataApprovazione;
	@XSSValidation
	private String titoloApprovazione;
	@XSSValidation
	private String urlApprovazione;
	private Long syscon;
	@XSSValidation
	private String pubblica;
	@NotNull
	@XSSValidation
	private String responsabile;
	private Long norma;
	private Long idUfficio;
	private Boolean soloScheda;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(String idProgramma) {
		this.idProgramma = idProgramma;
	}
	public String getStazioneappaltante() {
		return stazioneappaltante;
	}
	public void setStazioneappaltante(String stazioneappaltante) {
		this.stazioneappaltante = stazioneappaltante;
	}
	public Long getTipoProg() {
		return tipoProg;
	}
	public void setTipoProg(Long tipoProg) {
		this.tipoProg = tipoProg;
	}
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}
	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}
	public Long getAnnoInizio() {
		return annoInizio;
	}
	public void setAnnoInizio(Long annoInizio) {
		this.annoInizio = annoInizio;
	}
	public String getNumeroAtto() {
		return numeroAtto;
	}
	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
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
	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}
	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
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
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	public String getPubblica() {
		return pubblica;
	}
	public void setPubblica(String pubblica) {
		this.pubblica = pubblica;
	}
	public String getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}
	public Long getNorma() {
		return norma;
	}
	public void setNorma(Long norma) {
		this.norma = norma;
	}
	public Long getIdUfficio() {
		return idUfficio;
	}
	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}
	public Boolean getSoloScheda() {
		return soloScheda;
	}
	public void setSoloScheda(Boolean soloScheda) {
		this.soloScheda = soloScheda;
	}
	

}
