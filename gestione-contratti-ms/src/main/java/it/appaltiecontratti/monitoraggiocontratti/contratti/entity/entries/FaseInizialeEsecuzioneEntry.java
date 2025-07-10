package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseInizPubbEsitoForm;

public class FaseInizialeEsecuzioneEntry extends FaseBaseEntry{
	
	public Long codGara;
	public Long codLotto;
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
	FaseInizPubbEsitoForm pubblicazioneEsito;
	private boolean pubblicata;
	public Long num;
	public Long numAppa;
	private Date dataScadenza;
	private Date dataDecorrenza;
	private String codContratto;
	private Long ggDataAgg;

	public Long getGgDataStipula() {
		return ggDataStipula;
	}

	public void setGgDataStipula(Long ggDataStipula) {
		this.ggDataStipula = ggDataStipula;
	}

	public Long getGgDataAgg() {
		return ggDataAgg;
	}

	public void setGgDataAgg(Long ggDataAgg) {
		this.ggDataAgg = ggDataAgg;
	}

	public Long ggDataStipula;
	
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
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
	public String getCodContratto() {
		return codContratto;
	}
	public void setCodContratto(String codContratto) {
		this.codContratto = codContratto;
	}
	
}
