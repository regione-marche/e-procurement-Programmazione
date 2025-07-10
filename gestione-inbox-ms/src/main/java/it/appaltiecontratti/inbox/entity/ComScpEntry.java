package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class ComScpEntry {

	private Long idcomun;
	private Long area;
	private Long idAvviso;
	private Long numeroAtto;
	private Date dataInvio;
	private Long stato;
	private String contenutoJson;
	private String messaggio;
	private Long idRicevuto;
	private String idProgramma;
	private String denominazione;
	private String idGara;
	private String codFisc;
	private Long faseEsecuzione;
	private String smartCig;
		
	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}
	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}
	public Long getIdcomun() {
		return idcomun;
	}
	public void setIdcomun(Long idcomun) {
		this.idcomun = idcomun;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public Long getIdAvviso() {
		return idAvviso;
	}
	public void setIdAvviso(Long idAvviso) {
		this.idAvviso = idAvviso;
	}
	public Long getNumeroAtto() {
		return numeroAtto;
	}
	public void setNumeroAtto(Long numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public Long getStato() {
		return stato;
	}
	public void setStato(Long stato) {
		this.stato = stato;
	}
	public String getContenutoJson() {
		return contenutoJson;
	}
	public void setContenutoJson(String contenutoJson) {
		this.contenutoJson = contenutoJson;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public Long getIdRicevuto() {
		return idRicevuto;
	}
	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}
	public String getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(String idProgramma) {
		this.idProgramma = idProgramma;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getIdGara() {
		return idGara;
	}
	public void setIdGara(String idGara) {
		this.idGara = idGara;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public String getSmartCig() {
		return smartCig;
	}
	public void setSmartCig(String smartCig) {
		this.smartCig = smartCig;
	}
	
	
}
