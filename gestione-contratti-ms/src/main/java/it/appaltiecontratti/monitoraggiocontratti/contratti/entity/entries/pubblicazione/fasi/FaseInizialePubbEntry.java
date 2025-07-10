package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;
import java.util.List;

public class FaseInizialePubbEntry extends BaseFasePubblicazioneEntry {

	public Long num;
	public Date dataStipula;
	public Date dataEsecutivita;
	public Double importoCauzione;
	public Long incontriVigil;
	public Date dataInizioProg;
	public Date dataApprovazioneProg;
	public String frazionata;
	public Date dataVerbaleCons;
	public Date dataVerbaleDef;
	public String riserva;
	public Date dataVerbInizio;
	public Date dataTermine;
	FaseInizPubbEsitoPubbForm pubblicazioneEsito;
	SchedaSicurezzaPubbEntry schedaSicurezza;
	List<IncarichiProfPubbEntry> incarichiProfessionali;
	MisureAggiuntiveSicurezzaPubbEntry misureaggsicurezza;
	private Long numAppa;

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

	public FaseInizPubbEsitoPubbForm getPubblicazioneEsito() {
		return pubblicazioneEsito;
	}

	public void setPubblicazioneEsito(FaseInizPubbEsitoPubbForm pubblicazioneEsito) {
		this.pubblicazioneEsito = pubblicazioneEsito;
	}

	public SchedaSicurezzaPubbEntry getSchedaSicurezza() {
		return schedaSicurezza;
	}

	public void setSchedaSicurezza(SchedaSicurezzaPubbEntry schedaSicurezza) {
		this.schedaSicurezza = schedaSicurezza;
	}

	public List<IncarichiProfPubbEntry> getIncarichiProfessionali() {
		return incarichiProfessionali;
	}

	public void setIncarichiProfessionali(List<IncarichiProfPubbEntry> incarichiProfessionali) {
		this.incarichiProfessionali = incarichiProfessionali;
	}

	public MisureAggiuntiveSicurezzaPubbEntry getMisureaggsicurezza() {
		return misureaggsicurezza;
	}

	public void setMisureaggsicurezza(MisureAggiuntiveSicurezzaPubbEntry misureaggsicurezza) {
		this.misureaggsicurezza = misureaggsicurezza;
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

}
