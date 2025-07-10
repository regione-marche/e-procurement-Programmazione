package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

public class ImpresaAggiudicatariaAttoInsertForm {
	private Long idTipoAgg;
	private Long ruolo;
	private String codImpresa;
	private Long idGruppo;
	
	
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
}
