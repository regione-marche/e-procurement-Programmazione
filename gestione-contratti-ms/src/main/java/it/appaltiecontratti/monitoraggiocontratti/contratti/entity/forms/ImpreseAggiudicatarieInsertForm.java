package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ImpreseAggiudicatarieInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long idTipoAgg;
	List<ImpresaAggiudicatariaInsertForm> imprese;

	
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

	public List<ImpresaAggiudicatariaInsertForm> getImprese() {
		return imprese;
	}

	public void setImprese(List<ImpresaAggiudicatariaInsertForm> imprese) {
		this.imprese = imprese;
	}

	public Long getIdTipoAgg() {
		return idTipoAgg;
	}

	public void setIdTipoAgg(Long idTipoAgg) {
		this.idTipoAgg = idTipoAgg;
	}
	
	
}
