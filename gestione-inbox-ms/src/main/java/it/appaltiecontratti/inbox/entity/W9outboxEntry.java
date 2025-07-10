package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class W9outboxEntry {

	private Long idcomun;
	private Long area;
	private Long key01;
	private Long key04;
	private Long key03;
	private Date dataInvio;
	private Long stato;
	private String contenutoJson;
	private String messaggio;
	private Long idRicevuto;
	private String codFisc;
	
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
	public Long getKey03() {
		return key03;
	}
	public void setKey03(Long key03) {
		this.key03 = key03;
	}
	public Long getKey01() {
		return key01;
	}
	public void setKey01(Long key01) {
		this.key01 = key01;
	}
	public Long getKey04() {
		return key04;
	}
	public void setKey04(Long key04) {
		this.key04 = key04;
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
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	
	
}
