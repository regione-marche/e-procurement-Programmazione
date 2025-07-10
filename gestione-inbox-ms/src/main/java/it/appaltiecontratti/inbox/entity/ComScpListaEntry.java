package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class ComScpListaEntry {

	private String denominazione;
	private Date dataInvio;
	private Long stato;
	private Long idComun;
	private Long area;
	private Long key01;
	private Long key02;
	private Long key03;
	private Long key04;
	private String key01S;
	private String key02S;
	private String key03S;
	private String key04S;
	private String codFisc;
	private String codOggetto;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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

	public Long getIdComun() {
		return idComun;
	}

	public void setIdComun(Long idComun) {
		this.idComun = idComun;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getKey01() {
		return key01;
	}

	public void setKey01(Long key01) {
		this.key01 = key01;
	}

	public Long getKey02() {
		return key02;
	}

	public void setKey02(Long key02) {
		this.key02 = key02;
	}

	public Long getKey03() {
		return key03;
	}

	public void setKey03(Long key03) {
		this.key03 = key03;
	}

	public Long getKey04() {
		return key04;
	}

	public void setKey04(Long key04) {
		this.key04 = key04;
	}

	public String getKey01S() {
		return key01S;
	}

	public void setKey01S(String key01s) {
		key01S = key01s;
	}

	public String getKey02S() {
		return key02S;
	}

	public void setKey02S(String key02s) {
		key02S = key02s;
	}

	public String getKey03S() {
		return key03S;
	}

	public void setKey03S(String key03s) {
		key03S = key03s;
	}

	public String getKey04S() {
		return key04S;
	}

	public void setKey04S(String key04s) {
		key04S = key04s;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getCodOggetto() {
		return codOggetto;
	}

	public void setCodOggetto(String codOggetto) {
		this.codOggetto = codOggetto;
	}

	
}
