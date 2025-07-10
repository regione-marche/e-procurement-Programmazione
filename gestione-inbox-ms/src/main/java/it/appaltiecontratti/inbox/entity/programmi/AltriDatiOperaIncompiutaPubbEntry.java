/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.inbox.entity.programmi;

import java.io.Serializable;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Altri Dati di un'opera incompiuta.
 * 
 * @author Mirco.Franzoni
 */

@ApiModel(description = "Contenitore per gli altri dati di un'opera incompiuta")
public class AltriDatiOperaIncompiutaPubbEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(hidden=true)
	private Long contri;
	
	@ApiModelProperty(hidden=true)
	private Long numoi;
	
	@ApiModelProperty(value="Localizzazione - Codice ISTAT del Comune (OBBLIGATORIO se nuts non valorizzato)")
	@Size(max=9)
	private String istat;

	@ApiModelProperty(value="Localizzazione - NUTS (OBBLIGATORIO se istat non valorizzato)")
	@Size(max=20)
	private String nuts;
	
	@ApiModelProperty(value="Classificazione intervento: Tipologia (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipologiaIntervento)", required=true)
	@Size(max=5)
	private String tipologiaIntervento;
	
	@ApiModelProperty(value="Classificazione intervento: Categoria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=CategoriaIntervento)", required=true)
	@Size(max=6)
	private String categoriaIntervento;
	
	@ApiModelProperty(value="Opera rispondente a tutti i requisiti di capitolato? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String requisitiCapitolato;
	
	@ApiModelProperty(value="Opera rispondente a tutti i requisiti dell'ultimo progetto approvato? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String requisitiApprovato;
	
	@ApiModelProperty(value = "Dimensionamento dell'opera (unita' di misura)", required=true)
	@Size(max=10)
	private String unitaMisura;

	@ApiModelProperty(value="Dimensionamento dell'opera (valore)", required=true)
	private Double dimensione;
	
	@ApiModelProperty(value="Sponsorizzazione (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String sponsorizzazione;
	
	@ApiModelProperty(value="Finanza di progetto (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String finanzaDiProgetto;
	
	@ApiModelProperty(value="Costo progetto", required=true)
	private Double costoProgetto;
	
	@ApiModelProperty(value="Finanziamento assegnato", required=true)
	private Double finanziamento;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Statale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaStatale;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Regionale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaRegionale;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Provinciale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaProvinciale;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Comunale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaComunale;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Altra Pubblica (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaAltro;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Comunitaria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaComunitaria;
	
	@ApiModelProperty(value="Tipologia copertura finanziaria Privata (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)", required=true)
	@Size(max=1)
	private String coperturaPrivata;

	public void setIstat(String istat) {
		this.istat = istat;
	}

	public String getIstat() {
		return istat;
	}

	public void setNuts(String nuts) {
		this.nuts = nuts;
	}

	public String getNuts() {
		return nuts;
	}

	public void setTipologiaIntervento(String tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

	public String getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setCategoriaIntervento(String categoriaIntervento) {
		this.categoriaIntervento = categoriaIntervento;
	}

	public String getCategoriaIntervento() {
		return categoriaIntervento;
	}

	public void setRequisitiCapitolato(String requisitiCapitolato) {
		this.requisitiCapitolato = requisitiCapitolato;
	}

	public String getRequisitiCapitolato() {
		return requisitiCapitolato;
	}

	public void setRequisitiApprovato(String requisitiApprovato) {
		this.requisitiApprovato = requisitiApprovato;
	}

	public String getRequisitiApprovato() {
		return requisitiApprovato;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setDimensione(Double dimensione) {
		this.dimensione = dimensione;
	}

	public Double getDimensione() {
		return dimensione;
	}

	public void setSponsorizzazione(String sponsorizzazione) {
		this.sponsorizzazione = sponsorizzazione;
	}

	public String getSponsorizzazione() {
		return sponsorizzazione;
	}

	public void setFinanzaDiProgetto(String finanzaDiProgetto) {
		this.finanzaDiProgetto = finanzaDiProgetto;
	}

	public String getFinanzaDiProgetto() {
		return finanzaDiProgetto;
	}

	public void setCostoProgetto(Double costoProgetto) {
		this.costoProgetto = costoProgetto;
	}

	public Double getCostoProgetto() {
		return costoProgetto;
	}

	public void setFinanziamento(Double finanziamento) {
		this.finanziamento = finanziamento;
	}

	public Double getFinanziamento() {
		return finanziamento;
	}

	public void setCoperturaStatale(String coperturaStatale) {
		this.coperturaStatale = coperturaStatale;
	}

	public String getCoperturaStatale() {
		return coperturaStatale;
	}

	public void setCoperturaRegionale(String coperturaRegionale) {
		this.coperturaRegionale = coperturaRegionale;
	}

	public String getCoperturaRegionale() {
		return coperturaRegionale;
	}

	public void setCoperturaProvinciale(String coperturaProvinciale) {
		this.coperturaProvinciale = coperturaProvinciale;
	}

	public String getCoperturaProvinciale() {
		return coperturaProvinciale;
	}

	public void setCoperturaComunale(String coperturaComunale) {
		this.coperturaComunale = coperturaComunale;
	}

	public String getCoperturaComunale() {
		return coperturaComunale;
	}

	public void setCoperturaAltro(String coperturaAltro) {
		this.coperturaAltro = coperturaAltro;
	}

	public String getCoperturaAltro() {
		return coperturaAltro;
	}

	public void setCoperturaComunitaria(String coperturaComunitaria) {
		this.coperturaComunitaria = coperturaComunitaria;
	}

	public String getCoperturaComunitaria() {
		return coperturaComunitaria;
	}

	public void setCoperturaPrivata(String coperturaPrivata) {
		this.coperturaPrivata = coperturaPrivata;
	}

	public String getCoperturaPrivata() {
		return coperturaPrivata;
	}

	public void setContri(Long contri) {
		this.contri = contri;
	}

	@XmlTransient
	public Long getContri() {
		return contri;
	}

	public void setNumoi(Long numoi) {
		this.numoi = numoi;
	}

	@XmlTransient
	public Long getNumoi() {
		return numoi;
	}

	
}
