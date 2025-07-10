package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

public class FaseImpresaPubbEntry {
	
	private Long codGara;
	private Long codLotto;
	private Long num;
	private Long tipologiaSoggetto;
	private Long partecipante;
	private Long ruolo;
	private String codImpresa;
	private Long numRagg;
	private ImpresaFasePubbEntry impresa;
	
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
	public Long getRuolo() {
		return ruolo;
	}
	public void setRuolo(Long ruolo) {
		this.ruolo = ruolo;
	}
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public Long getNumRagg() {
		return numRagg;
	}
	public void setNumRagg(Long numRagg) {
		this.numRagg = numRagg;
	}
	public ImpresaFasePubbEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaFasePubbEntry impresa) {
		this.impresa = impresa;
	}
	
}
