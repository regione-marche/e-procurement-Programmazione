package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

public class FinanziamentiPubbEntry {
	
	private Long codGara;
	private Long codLotto;
	private Long numAppa;
	private Long numFina;
	private String idFinanziamento;
	private Double importoFinanziamento;
	
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
