/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.inbox.entity.avvisi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.contratti.DatiGeneraliTecnicoEntry;

@ApiModel(description = "Contenitore per i dati di pubblicazione dell'avviso")
public class PubblicaAvvisoEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(hidden=true)
	private String codiceSA;
	
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@ApiModelProperty(value = "Identificativo syscon del cliente")
	private Long syscon;
	
	@ApiModelProperty(value = "Codice Fiscale Stazione appaltante", required = true)
	@Size(max=16, min=11)
	private String codiceFiscaleSA;

	@ApiModelProperty(hidden=true)
	private Long codiceSistema;

	@ApiModelProperty(value = "Identificativo del client", required = true)
	private String clientId;

	@ApiModelProperty(hidden=true)
	private Long idUfficio;
	  
	@ApiModelProperty(value="Ufficio/area di pertinenza")
	@Size(max=250)
	private String ufficio;
	  
	@ApiModelProperty(value = "Tipologia avviso (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipoAvviso)", required = true)
	private Long tipologia;

	@ApiModelProperty(value = "Data dell'avviso")
	private Date data;

	@ApiModelProperty(value = "Descrizione dell'avviso", required = true)
	@Size(max=500, min=1)
	private String descrizione;

	@ApiModelProperty(value = "CIG")
	@Size(max=10)
	private String cig;
	
	@ApiModelProperty(value = "Codice CUP")
	@Size(max=15)
	private String cup;

	@ApiModelProperty(value = "Numero intervento CUI")
	@Size(max=20)
	private String cui;
	
	@ApiModelProperty(value = "Data scadenza")
	private Date scadenza;
	
	@ApiModelProperty(value="Documenti allegati alla pubblicazione", required = true)
	@Size(min=1)
	private List<AllegatoAvvisiEntry> documenti = new ArrayList<AllegatoAvvisiEntry>();
	
	@ApiModelProperty(hidden=true)
	private String codiceRup;
	
	@ApiModelProperty(value = "Responsabile dell'avviso", required = true)
	private DatiGeneraliTecnicoEntry rup;
	
	@ApiModelProperty(value = "Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive")
	private Long idRicevuto;
	
	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setRup(DatiGeneraliTecnicoEntry rup) {
		this.rup = rup;
	}

	public DatiGeneraliTecnicoEntry getRup() {
		return rup;
	}

	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = codiceFiscaleSA;
	}

	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}

	public void setCodiceSistema(Long codiceSistema) {
		this.codiceSistema = codiceSistema;
	}

	@XmlTransient
	public Long getCodiceSistema() {
		return codiceSistema;
	}

	public void setTipologia(Long tipologia) {
		this.tipologia = tipologia;
	}

	public Long getTipologia() {
		return tipologia;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCig() {
		return cig;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup() {
		return cup;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCui() {
		return cui;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setDocumenti(List<AllegatoAvvisiEntry> documenti) {
		this.documenti = documenti;
	}

	public List<AllegatoAvvisiEntry> getDocumenti() {
		return documenti;
	}

	public void setCodiceSA(String codiceSA) {
		this.codiceSA = codiceSA;
	}

	@XmlTransient
	public String getCodiceSA() {
		return codiceSA;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setCodiceRup(String codiceRup) {
		this.codiceRup = codiceRup;
	}

	@XmlTransient
	public String getCodiceRup() {
		return codiceRup;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

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

}
