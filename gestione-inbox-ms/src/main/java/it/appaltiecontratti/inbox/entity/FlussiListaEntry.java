package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class FlussiListaEntry {

	private String  denominazione;
	private Date  dataRicezione;
	private Long  tipologiaFlusso;
	private String  codOggetto;
	private Long tipoInvio;
	private String codFiscSa;
	private Long idComunicazione;
	private Long area;
	private Long statoComunicazione;
	private Long numProgFase;

	
	public Long getNumProgFase() {
		return numProgFase;
	}
	public void setNumProgFase(Long numProgFase) {
		this.numProgFase = numProgFase;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}	
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public Long getTipologiaFlusso() {
		return tipologiaFlusso;
	}
	public void setTipologiaFlusso(Long tipologiaFlusso) {
		this.tipologiaFlusso = tipologiaFlusso;
	}
	public String getCodOggetto() {
		return codOggetto;
	}
	public void setCodOggetto(String codOggetto) {
		this.codOggetto = codOggetto;
	}
	public Long getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(Long tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public String getCodFiscSa() {
		return codFiscSa;
	}
	public void setCodFiscSa(String codFiscSa) {
		this.codFiscSa = codFiscSa;
	}
	public Long getIdComunicazione() {
		return idComunicazione;
	}
	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public Long getStatoComunicazione() {
		return statoComunicazione;
	}
	public void setStatoComunicazione(Long statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}
	
	
}
