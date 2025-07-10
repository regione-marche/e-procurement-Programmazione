package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseAccordoBonarioEntry extends FaseBaseEntry {
	
	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataAccordo;
	private Double oneriDerivanti;
	private Long numRiserve;
	private Date dataInizioAcc;
	private Date dataFineAcc;
	private boolean pubblicata;
	private Long numAppa;
	
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
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Date getDataAccordo() {
		return dataAccordo;
	}
	public void setDataAccordo(Date dataAccordo) {
		this.dataAccordo = dataAccordo;
	}
	public Double getOneriDerivanti() {
		return oneriDerivanti;
	}
	public void setOneriDerivanti(Double oneriDerivanti) {
		this.oneriDerivanti = oneriDerivanti;
	}
	public Long getNumRiserve() {
		return numRiserve;
	}
	public void setNumRiserve(Long numRiserve) {
		this.numRiserve = numRiserve;
	}
	public Date getDataInizioAcc() {
		return dataInizioAcc;
	}
	public void setDataInizioAcc(Date dataInizioAcc) {
		this.dataInizioAcc = dataInizioAcc;
	}
	public Date getDataFineAcc() {
		return dataFineAcc;
	}
	public void setDataFineAcc(Date dataFineAcc) {
		this.dataFineAcc = dataFineAcc;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
}
