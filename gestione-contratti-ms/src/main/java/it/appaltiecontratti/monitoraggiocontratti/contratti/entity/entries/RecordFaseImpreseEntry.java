package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class RecordFaseImpreseEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private String codImpresa;
	private Long tipologiaSoggetto;
	private Long ruolo;
	private Long partecipante;
	private Long numRaggruppamento;
	private ImpresaEntry impresa;
	
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
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public Long getTipologiaSoggetto() {
		return tipologiaSoggetto;
	}
	public void setTipologiaSoggetto(Long tipologiaSoggetto) {
		this.tipologiaSoggetto = tipologiaSoggetto;
	}
	public Long getRuolo() {
		return ruolo;
	}
	public void setRuolo(Long ruolo) {
		this.ruolo = ruolo;
	}
	public Long getPartecipante() {
		return partecipante;
	}
	public void setPartecipante(Long partecipante) {
		this.partecipante = partecipante;
	}
	public Long getNumRaggruppamento() {
		return numRaggruppamento;
	}
	public void setNumRaggruppamento(Long numRaggruppamento) {
		this.numRaggruppamento = numRaggruppamento;
	}
	public ImpresaEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}
	
	
}
