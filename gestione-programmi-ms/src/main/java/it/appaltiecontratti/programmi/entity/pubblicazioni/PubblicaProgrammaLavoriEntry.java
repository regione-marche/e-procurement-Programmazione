/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.programmi.entity.pubblicazioni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dati per la pubblicazione di un programma per lavori.
 * 
 * @author Mirco.Franzoni
 */

@ApiModel(description = "Contenitore per i dati di pubblicazione di un programma per lavori")
public class PubblicaProgrammaLavoriEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(hidden=true)
	private String codiceSA;

	@ApiModelProperty(hidden=true)
	private String codein;
	
	@ApiModelProperty(hidden=true)
	private Long contri;
	
	@ApiModelProperty
	private Long syscon;
	
	@ApiModelProperty(value = "ID del programma('LP' + CodiceFiscaleSA(11 caratteri) + anno(4 cifre) + progressivo(3 cifre))", required = true)
	@Size(max=25, min=20)
	private String id;
	
	@ApiModelProperty(hidden=true)
	private String clientId;

	@ApiModelProperty(value = "Codice Fiscale Stazione appaltante", required = true)
	@Size(max=16, min=11)
	private String codiceFiscaleSA;

	@ApiModelProperty(hidden=true)
	private Long idUfficio;
	  
	@ApiModelProperty(value="Ufficio/area di pertinenza")
	@Size(max=250)
	private String ufficio;
	  
	@ApiModelProperty(value = "Anno di inizio del programma", required = true)
	private Long anno;

	@ApiModelProperty(value = "Descrizione del Programma", required = true)
	@Size(max=500, min=1)
	private String descrizione;
	
	@ApiModelProperty(value = "Numero approvazione")
	@Size(max=50)
	private String numeroApprovazione;
	
	@ApiModelProperty(value = "Autore del programma")
	@Size(max=50)
	private String autore;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value="Data approvazione (OBBLIGATORIO se non ci sono i dati di adozione)")  
	private Date dataApprovazione; 

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value="Data pubblicazione approvazione (OBBLIGATORIO se non ci sono i dati di adozione)")  
	private Date dataPubblicazioneApprovazione; 
	  
	@ApiModelProperty(value = "Titolo atto approvazione (OBBLIGATORIO se non ci sono i dati di adozione)")
	@Size(max=250)
	private String titoloAttoApprovazione;
		
	@ApiModelProperty(value = "Url atto di approvazione (OBBLIGATORIO se non ci sono i dati di adozione)")
	@Size(max=2000)
	private String urlAttoApprovazione;
	
	@ApiModelProperty(value = "Numero adozione")
	@Size(max=50)
	private String numeroAdozione;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value="Data adozione (OBBLIGATORIO se non ci sono i dati di approvazione)")  
	private Date dataAdozione; 

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value="Data pubblicazione adozione (OBBLIGATORIO se non ci sono i dati di approvazione)")  
	private Date dataPubblicazioneAdozione; 
	  
	@ApiModelProperty(value = "Titolo atto adozione (OBBLIGATORIO se non ci sono i dati di approvazione)")
	@Size(max=250)
	private String titoloAttoAdozione;
		
	@ApiModelProperty(value = "Url atto di adozione (OBBLIGATORIO se non ci sono i dati di approvazione)")
	@Size(max=2000)
	private String urlAttoAdozione;
	
	@ApiModelProperty(hidden=true)
	private String codiceReferente;
	
	@ApiModelProperty(value = "Referente del programma", required = true)
	private DatiGeneraliTecnicoPubbEntry referente;
	
	@ApiModelProperty(value="Lista opere incompiute")
	@Size(min=0)
    private List<OperaIncompiutaPubbEntry> opereIncompiute = new ArrayList<OperaIncompiutaPubbEntry>();

	@ApiModelProperty(value="Lista interventi")
	@Size(min=0)
    private List<InterventoPubbEntry> interventi = new ArrayList<InterventoPubbEntry>();

	@ApiModelProperty(value="Lista interventi non riproposti")
	@Size(min=0)
    private List<InterventoNonRipropostoPubbEntry> interventiNonRiproposti = new ArrayList<InterventoNonRipropostoPubbEntry>();

	@ApiModelProperty(value = "Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive")
	private Long idRicevuto;
	
	private Long norma;
	
	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = codiceFiscaleSA;
	}

	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCodiceSA(String codiceSA) {
		this.codiceSA = codiceSA;
	}

	@XmlTransient
	public String getCodiceSA() {
		return codiceSA;
	}

	public void setContri(Long contri) {
		this.contri = contri;
	}

	@XmlTransient
	public Long getContri() {
		return contri;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Long getAnno() {
		return anno;
	}

	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
	}

	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}

	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public Date getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataPubblicazioneApprovazione(
			Date dataPubblicazioneApprovazione) {
		this.dataPubblicazioneApprovazione = dataPubblicazioneApprovazione;
	}

	public Date getDataPubblicazioneApprovazione() {
		return dataPubblicazioneApprovazione;
	}

	public void setTitoloAttoApprovazione(String titoloAttoApprovazione) {
		this.titoloAttoApprovazione = titoloAttoApprovazione;
	}

	public String getTitoloAttoApprovazione() {
		return titoloAttoApprovazione;
	}

	public void setUrlAttoApprovazione(String urlAttoApprovazione) {
		this.urlAttoApprovazione = urlAttoApprovazione;
	}

	public String getUrlAttoApprovazione() {
		return urlAttoApprovazione;
	}

	public void setNumeroAdozione(String numeroAdozione) {
		this.numeroAdozione = numeroAdozione;
	}

	public String getNumeroAdozione() {
		return numeroAdozione;
	}

	public void setDataAdozione(Date dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public Date getDataAdozione() {
		return dataAdozione;
	}

	public void setDataPubblicazioneAdozione(Date dataPubblicazioneAdozione) {
		this.dataPubblicazioneAdozione = dataPubblicazioneAdozione;
	}

	public Date getDataPubblicazioneAdozione() {
		return dataPubblicazioneAdozione;
	}

	public void setTitoloAttoAdozione(String titoloAttoAdozione) {
		this.titoloAttoAdozione = titoloAttoAdozione;
	}

	public String getTitoloAttoAdozione() {
		return titoloAttoAdozione;
	}

	public void setUrlAttoAdozione(String urlAttoAdozione) {
		this.urlAttoAdozione = urlAttoAdozione;
	}

	public String getUrlAttoAdozione() {
		return urlAttoAdozione;
	}

	public void setCodiceReferente(String codiceReferente) {
		this.codiceReferente = codiceReferente;
	}

	@XmlTransient
	public String getCodiceReferente() {
		return codiceReferente;
	}

	public void setReferente(DatiGeneraliTecnicoPubbEntry referente) {
		this.referente = referente;
	}

	public DatiGeneraliTecnicoPubbEntry getReferente() {
		return referente;
	}

	public void setOpereIncompiute(List<OperaIncompiutaPubbEntry> opereIncompiute) {
		this.opereIncompiute = opereIncompiute;
	}

	public List<OperaIncompiutaPubbEntry> getOpereIncompiute() {
		return opereIncompiute;
	}

	public void setInterventi(List<InterventoPubbEntry> interventi) {
		this.interventi = interventi;
	}

	public List<InterventoPubbEntry> getInterventi() {
		return interventi;
	}

	public void setInterventiNonRiproposti(List<InterventoNonRipropostoPubbEntry> interventiNonRiproposti) {
		this.interventiNonRiproposti = interventiNonRiproposti;
	}

	public List<InterventoNonRipropostoPubbEntry> getInterventiNonRiproposti() {
		return interventiNonRiproposti;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@XmlTransient
	public String getClientId() {
		return clientId;
	}

	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}

	@XmlTransient
	public Long getIdUfficio() {
		return idUfficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	@XmlTransient
	public Long getSyscon() {
		return syscon;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public Long getNorma() {
		return norma;
	}

	public void setNorma(Long norma) {
		this.norma = norma;
	}


	public String getCodein() {
		return codein;
	}

	public void setCodein(String codein) {
		this.codein = codein;
	}
}
