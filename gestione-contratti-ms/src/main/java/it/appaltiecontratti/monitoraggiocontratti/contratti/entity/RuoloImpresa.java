package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;

public class RuoloImpresa {
	
	private Long ruolo;
	private String codImpresa;
	private ImpresaEntry impresa;
	
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
	public ImpresaEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}
}
