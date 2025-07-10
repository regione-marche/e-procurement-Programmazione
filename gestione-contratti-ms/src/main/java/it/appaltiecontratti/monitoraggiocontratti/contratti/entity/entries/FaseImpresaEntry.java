package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.RuoloImpresa;

public class FaseImpresaEntry {
	
	private Long codGara;
	private Long codLotto;
	private Long num;
	private Long tipologiaSoggetto;
	private Long partecipante;
	private List<RuoloImpresa> imprese;
	private Long numRagg;
	private boolean pubblicata;
	
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
	public Long getTipologiaSoggetto() {
		return tipologiaSoggetto;
	}
	public void setTipologiaSoggetto(Long tipologiaSoggetto) {
		this.tipologiaSoggetto = tipologiaSoggetto;
	}
	public Long getPartecipante() {
		return partecipante;
	}
	public void setPartecipante(Long partecipante) {
		this.partecipante = partecipante;
	}
	public List<RuoloImpresa> getImprese() {
		return imprese;
	}
	public void setImprese(List<RuoloImpresa> imprese) {
		this.imprese = imprese;
	}
	public Long getNumRagg() {
		return numRagg;
	}
	public void setNumRagg(Long numRagg) {
		this.numRagg = numRagg;
	}
	

}
