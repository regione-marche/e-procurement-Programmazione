/*
 * Copyright (c) Maggioli S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di Maggioli S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con Maggioli.
 */
package it.appaltiecontratti.pubblprogrammi.entity;

import java.io.Serializable;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

/**
 * Dati di un'immobile.
 * 
 * @author Mirco.Franzoni
 */

@ApiModel(description = "Contenitore per dati di un'immobile")
public class ImmobileEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	@ApiModelProperty(hidden=true)
	private Long contri;
	
	@ApiModelProperty(hidden=true)
	private Long conint;
	
	@ApiModelProperty(hidden=true)
	private Long numimm;
	
	@ApiModelProperty(hidden=true)
	private Long numoi;
	
	@ApiModelProperty(value="Codice univoco immobile (�I� + CodiceFiscale Stazione Appaltante(11 char) + anno (4 char) + progressivo (5 char))", required=true)
	@Size(max=30)
	@XSSValidation
	private String cui;
	
	@ApiModelProperty(value="Localizzazione - Codice ISTAT del Comune (OBBLIGATORIO se nuts non valorizzato)")
	@Size(max=9)
	@XSSValidation
	private String istat;

	@ApiModelProperty(value="Localizzazione - NUTS (OBBLIGATORIO se istat non valorizzato)")
	@Size(max=5)
	@XSSValidation
	private String nuts;
	
	@ApiModelProperty(value="Trasferimento immobile a titolo corrispettivo (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TrasferimentoImmobile)", required=true)
	private Long trasferimentoTitoloCorrispettivo;
	
	@ApiModelProperty(value="Immobile disponibile art. 21-c5 (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=ImmobileDisponibile)", required=true)
	private Long immobileDisponibile;
	
	@ApiModelProperty(value="[Attributo non utilizzato] Alienati per il finanziamento e la realizzazione di opere pubbliche (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)")
	@Size(max=1)
	@XSSValidation
	private String alienati;
	
	@ApiModelProperty(value="Gi� incluso in programma di dismissione art. 27 DL 201/2011 (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=ProgrammaDismissione)", required=true)
	private Long inclusoProgrammaDismissione;
	
	@ApiModelProperty(value = "Descrizione dell'immobile", required=true)
	@Size(max=400)
	@XSSValidation
	private String descrizione;
	
	@ApiModelProperty(value="[Attributo non utilizzato] Tipo propriet� (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipoProprieta)")
	private Long tipoProprieta;
	
	@ApiModelProperty(value="Tipo propriet� (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipoDisponibilita)", required=true)
	private Long tipoDisponibilita;
	
	@ApiModelProperty(value="Valore stimato dell'immobile - Primo anno")
	private Double valoreStimato1;
	
	@ApiModelProperty(value="Valore stimato dell'immobile - Secondo anno")
	private Double valoreStimato2;
	
	@ApiModelProperty(value="Valore stimato dell'immobile - Terzo anno")
	private Double valoreStimato3;

	@ApiModelProperty(value="Valore stimato dell'immobile - Annualit� successive")
	private Double valoreStimatoSucc;
	
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

	public void setTrasferimentoTitoloCorrispettivo(
			Long trasferimentoTitoloCorrispettivo) {
		this.trasferimentoTitoloCorrispettivo = trasferimentoTitoloCorrispettivo;
	}

	public Long getTrasferimentoTitoloCorrispettivo() {
		return trasferimentoTitoloCorrispettivo;
	}

	public void setImmobileDisponibile(Long immobileDisponibile) {
		this.immobileDisponibile = immobileDisponibile;
	}

	public Long getImmobileDisponibile() {
		return immobileDisponibile;
	}

	public void setAlienati(String alienati) {
		this.alienati = alienati;
	}

	public String getAlienati() {
		return alienati;
	}

	public void setInclusoProgrammaDismissione(
			Long inclusoProgrammaDismissione) {
		this.inclusoProgrammaDismissione = inclusoProgrammaDismissione;
	}

	public Long getInclusoProgrammaDismissione() {
		return inclusoProgrammaDismissione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setValoreStimato1(Double valoreStimato1) {
		this.valoreStimato1 = valoreStimato1;
	}

	public Double getValoreStimato1() {
		return valoreStimato1;
	}

	public void setValoreStimato2(Double valoreStimato2) {
		this.valoreStimato2 = valoreStimato2;
	}

	public Double getValoreStimato2() {
		return valoreStimato2;
	}

	public void setValoreStimato3(Double valoreStimato3) {
		this.valoreStimato3 = valoreStimato3;
	}

	public Double getValoreStimato3() {
		return valoreStimato3;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCui() {
		return cui;
	}

	public void setTipoProprieta(Long tipoProprieta) {
		this.tipoProprieta = tipoProprieta;
	}

	public Long getTipoProprieta() {
		return tipoProprieta;
	}

	public void setTipoDisponibilita(Long tipoDisponibilita) {
		this.tipoDisponibilita = tipoDisponibilita;
	}

	public Long getTipoDisponibilita() {
		return tipoDisponibilita;
	}

	public void setValoreStimatoSucc(Double valoreStimatoSucc) {
		this.valoreStimatoSucc = valoreStimatoSucc;
	}

	public Double getValoreStimatoSucc() {
		return valoreStimatoSucc;
	}

	public void setContri(Long contri) {
		this.contri = contri;
	}

	@XmlTransient
	public Long getContri() {
		return contri;
	}

	public void setConint(Long conint) {
		this.conint = conint;
	}

	@XmlTransient
	public Long getConint() {
		return conint;
	}

	public void setNumimm(Long numimm) {
		this.numimm = numimm;
	}

	@XmlTransient
	public Long getNumimm() {
		return numimm;
	}

	public void setNumoi(Long numoi) {
		this.numoi = numoi;
	}

	@XmlTransient
	public Long getNumoi() {
		return numoi;
	}
	
	
}
