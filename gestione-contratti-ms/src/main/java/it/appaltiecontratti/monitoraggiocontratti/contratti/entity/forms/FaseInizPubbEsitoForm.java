package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.Date;

public class FaseInizPubbEsitoForm {
	
	private Long codGara;
	private Long codLotto;
	private Long numIniziale;
	private Long counter;
	private Date dataGuce;
	private Date dataGuri;
	private Date dataAlbo;
	private Long quotidianiNaz;
	private Long quotidianiReg;
	@XSSValidation
	private String profiloCommittente;
	@XSSValidation
	private String sitoMinInfTrasp;
	@XSSValidation
	private String sitoOsservatorio;
	
	
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
	public Long getNumIniziale() {
		return numIniziale;
	}
	public void setNumIniziale(Long numIniziale) {
		this.numIniziale = numIniziale;
	}
	public Long getCounter() {
		return counter;
	}
	public void setCounter(Long counter) {
		this.counter = counter;
	}
	public Date getDataGuce() {
		return dataGuce;
	}
	public void setDataGuce(Date dataGuce) {
		this.dataGuce = dataGuce;
	}
	public Date getDataGuri() {
		return dataGuri;
	}
	public void setDataGuri(Date dataGuri) {
		this.dataGuri = dataGuri;
	}
	public Date getDataAlbo() {
		return dataAlbo;
	}
	public void setDataAlbo(Date dataAlbo) {
		this.dataAlbo = dataAlbo;
	}
	public Long getQuotidianiNaz() {
		return quotidianiNaz;
	}
	public void setQuotidianiNaz(Long quotidianiNaz) {
		this.quotidianiNaz = quotidianiNaz;
	}
	public Long getQuotidianiReg() {
		return quotidianiReg;
	}
	public void setQuotidianiReg(Long quotidianiReg) {
		this.quotidianiReg = quotidianiReg;
	}
	public String getProfiloCommittente() {
		return profiloCommittente;
	}
	public void setProfiloCommittente(String profiloCommittente) {
		this.profiloCommittente = profiloCommittente;
	}
	public String getSitoMinInfTrasp() {
		return sitoMinInfTrasp;
	}
	public void setSitoMinInfTrasp(String sitoMinInfTrasp) {
		this.sitoMinInfTrasp = sitoMinInfTrasp;
	}
	public String getSitoOsservatorio() {
		return sitoOsservatorio;
	}
	public void setSitoOsservatorio(String sitoOsservatorio) {
		this.sitoOsservatorio = sitoOsservatorio;
	}

}
