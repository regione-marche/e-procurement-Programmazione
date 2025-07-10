package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.RuoloImpresa;

public class FaseImpresaInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Long tipologiaSoggetto;
	private Long partecipante;
	private Long numRagg;
	private List<RuoloImpresa> imprese; 
	private boolean updateDaexport;
	
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
	public Long getNumRagg() {
		return numRagg;
	}
	public void setNumRagg(Long numRagg) {
		this.numRagg = numRagg;
	}
	public List<RuoloImpresa> getImprese() {
		return imprese;
	}
	public void setImprese(List<RuoloImpresa> imprese) {
		this.imprese = imprese;
	}
	public boolean isUpdateDaexport() {
		return updateDaexport;
	}
	public void setUpdateDaexport(boolean updateDaexport) {
		this.updateDaexport = updateDaexport;
	}
	
}
