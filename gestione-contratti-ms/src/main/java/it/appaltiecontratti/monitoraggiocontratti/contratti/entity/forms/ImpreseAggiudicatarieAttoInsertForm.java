package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ImpreseAggiudicatarieAttoInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long numPubb;
	private Long idTipoAgg;
	List<ImpresaAggiudicatariaAttoInsertForm> imprese;

	
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

	public List<ImpresaAggiudicatariaAttoInsertForm> getImprese() {
		return imprese;
	}

	public void setImprese(List<ImpresaAggiudicatariaAttoInsertForm> imprese) {
		this.imprese = imprese;
	}

	public Long getIdTipoAgg() {
		return idTipoAgg;
	}

	public void setIdTipoAgg(Long idTipoAgg) {
		this.idTipoAgg = idTipoAgg;
	}
}
