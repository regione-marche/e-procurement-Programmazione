package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class AssociazionePagamentiEntry {

	private String codeCampo;
	private String nomeCampo;
	private Double importoCampo;
	private Double importoTotalePagamenti;
	private List<PagamentoEntry> pagamenti;

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public List<PagamentoEntry> getPagamenti() {
		return pagamenti;
	}

	public void setPagamenti(List<PagamentoEntry> pagamenti) {
		this.pagamenti = pagamenti;
	}

	public String getCodeCampo() {
		return codeCampo;
	}

	public void setCodeCampo(String codeCampo) {
		this.codeCampo = codeCampo;
	}

	public Double getImportoCampo() {
		return importoCampo;
	}

	public void setImportoCampo(Double importoCampo) {
		this.importoCampo = importoCampo;
	}

	public Double getImportoTotalePagamenti() {
		return importoTotalePagamenti;
	}

	public void setImportoTotalePagamenti(Double importoTotalePagamenti) {
		this.importoTotalePagamenti = importoTotalePagamenti;
	}

}
