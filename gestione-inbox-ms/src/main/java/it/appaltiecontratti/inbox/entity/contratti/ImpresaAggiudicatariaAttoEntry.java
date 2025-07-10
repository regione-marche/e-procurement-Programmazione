package it.appaltiecontratti.inbox.entity.contratti;

import it.appaltiecontratti.inbox.entity.ImpresaEntry;

public class ImpresaAggiudicatariaAttoEntry {

	
	private Long codGara;
	private Long numPubb;
	private Long numAggi;
	private Long idTipoAgg;
	private Long ruolo;
	private String codInps;	
	private String codiceCassa;
	private String codImpresa;
	private Long idGruppo ;
	private ImpresaEntry impresa;
	
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getNumPubb() {
		return numPubb;
	}
	public void setNumPubb(Long numPubb) {
		this.numPubb = numPubb;
	}
	public Long getNumAggi() {
		return numAggi;
	}
	public void setNumAggi(Long numAggi) {
		this.numAggi = numAggi;
	}
	public Long getIdTipoAgg() {
		return idTipoAgg;
	}
	public void setIdTipoAgg(Long idTipoAgg) {
		this.idTipoAgg = idTipoAgg;
	}
	public Long getRuolo() {
		return ruolo;
	}
	public void setRuolo(Long ruolo) {
		this.ruolo = ruolo;
	}
	public String getCodInps() {
		return codInps;
	}
	public void setCodInps(String codInps) {
		this.codInps = codInps;
	}
	public String getCodiceCassa() {
		return codiceCassa;
	}
	public void setCodiceCassa(String codiceCassa) {
		this.codiceCassa = codiceCassa;
	}
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public Long getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}
	public ImpresaEntry getImpresa() {
		return impresa;
	}
	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}
	
	
}
