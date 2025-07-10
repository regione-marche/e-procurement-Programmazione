package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FinanziamentoInsertForm {

	private Long numFina;
	@XSSValidation
	private String idFinanziamento;
	private Double importoFinanziamento;

	public Long getNumFina() {
		return numFina;
	}

	public void setNumFina(Long numFina) {
		this.numFina = numFina;
	}

	public String getIdFinanziamento() {
		return idFinanziamento;
	}

	public void setIdFinanziamento(String idFinanziamento) {
		this.idFinanziamento = idFinanziamento;
	}

	public Double getImportoFinanziamento() {
		return importoFinanziamento;
	}

	public void setImportoFinanziamento(Double importoFinanziamento) {
		this.importoFinanziamento = importoFinanziamento;
	}

}