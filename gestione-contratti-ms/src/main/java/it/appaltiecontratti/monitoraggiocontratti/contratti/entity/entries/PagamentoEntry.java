package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class PagamentoEntry {

	private int idx;
	@XSSValidation
	private String codicePagamento;
	@XSSValidation
	private Date dataPagamento;
	private Double importo;
	@XSSValidation
	private String oggetto;
	@XSSValidation
	private String impresa;
	@XSSValidation
	private String utilizzato;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getCodicePagamento() {
		return codicePagamento;
	}

	public void setCodicePagamento(String codicePagamento) {
		this.codicePagamento = codicePagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getImpresa() {
		return impresa;
	}

	public void setImpresa(String impresa) {
		this.impresa = impresa;
	}

	public String getUtilizzato() {
		return utilizzato;
	}

	public void setUtilizzato(String utilizzato) {
		this.utilizzato = utilizzato;
	}

}
