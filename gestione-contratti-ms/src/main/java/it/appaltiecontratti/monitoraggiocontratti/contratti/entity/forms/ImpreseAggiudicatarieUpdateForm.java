package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ImpreseAggiudicatarieUpdateForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	@NotNull
	private Long numAppa;
	List<Long> impreseDaCancellare;
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
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	public List<Long> getImpreseDaCancellare() {
		return impreseDaCancellare;
	}
	public void setImpreseDaCancellare(List<Long> impreseDaCancellare) {
		this.impreseDaCancellare = impreseDaCancellare;
	}
	public Long getIdTipoAgg() {
		return idTipoAgg;
	}
	public void setIdTipoAgg(Long idTipoAgg) {
		this.idTipoAgg = idTipoAgg;
	}
	public List<ImpresaAggiudicatariaInsertForm> getImprese() {
		return imprese;
	}
	public void setImprese(List<ImpresaAggiudicatariaInsertForm> imprese) {
		this.imprese = imprese;
	}
	
	
}
