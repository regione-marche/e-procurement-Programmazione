package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseInizialeEsecuzioneInsertForm {
	
	@NotNull
	public Long codGara;
	@NotNull
	public Long codLotto;
	public Date dataStipula;
	public Date dataEsecutivita;
	public Double importoCauzione;
	public Long incontriVigil;
	public Date dataInizioProg;
	public Date dataApprovazioneProg;
	@XSSValidation
	public String frazionata;
	public Date dataVerbaleCons;
	public Date dataVerbaleDef;
	@XSSValidation
	public String riserva;
	public Date dataVerbInizio;
	public Date dataTermine;
	FaseInizPubbEsitoForm pubblicazioneEsito;
	@JsonIgnore
	private Long numAppa;
	private Long num;
	@XSSValidation
	private String codContratto;
	
	
	public Date dataDecorrenza;
	public Date dataScadenza;
	
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getCodLotto() {
		return codLotto;
	}
	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}
	public Date getDataStipula() {
		return dataStipula;
	}
	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}
	public Date getDataEsecutivita() {
		return dataEsecutivita;
	}
	public void setDataEsecutivita(Date dataEsecutivita) {
		this.dataEsecutivita = dataEsecutivita;
	}
	public Double getImportoCauzione() {
		return importoCauzione;
	}
	public void setImportoCauzione(Double importoCauzione) {
		this.importoCauzione = importoCauzione;
	}
	public Long getIncontriVigil() {
		return incontriVigil;
	}
	public void setIncontriVigil(Long incontriVigil) {
		this.incontriVigil = incontriVigil;
	}
	public Date getDataInizioProg() {
		return dataInizioProg;
	}
	public void setDataInizioProg(Date dataInizioProg) {
		this.dataInizioProg = dataInizioProg;
	}
	public Date getDataApprovazioneProg() {
		return dataApprovazioneProg;
	}
	public void setDataApprovazioneProg(Date dataApprovazioneProg) {
		this.dataApprovazioneProg = dataApprovazioneProg;
	}
	public String getFrazionata() {
		return frazionata;
	}
	public void setFrazionata(String frazionata) {
		this.frazionata = frazionata;
	}
	public Date getDataVerbaleCons() {
		return dataVerbaleCons;
	}
	public void setDataVerbaleCons(Date dataVerbaleCons) {
		this.dataVerbaleCons = dataVerbaleCons;
	}
	public Date getDataVerbaleDef() {
		return dataVerbaleDef;
	}
	public void setDataVerbaleDef(Date dataVerbaleDef) {
		this.dataVerbaleDef = dataVerbaleDef;
	}
	public String getRiserva() {
		return riserva;
	}
	public void setRiserva(String riserva) {
		this.riserva = riserva;
	}
	public Date getDataVerbInizio() {
		return dataVerbInizio;
	}
	public void setDataVerbInizio(Date dataVerbInizio) {
		this.dataVerbInizio = dataVerbInizio;
	}
	public Date getDataTermine() {
		return dataTermine;
	}
	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}
	public FaseInizPubbEsitoForm getPubblicazioneEsito() {
		return pubblicazioneEsito;
	}
	public void setPubblicazioneEsito(FaseInizPubbEsitoForm pubblicazioneEsito) {
		this.pubblicazioneEsito = pubblicazioneEsito;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getCodContratto() {
		return codContratto;
	}
	public void setCodContratto(String codContratto) {
		this.codContratto = codContratto;
	}
	
}
