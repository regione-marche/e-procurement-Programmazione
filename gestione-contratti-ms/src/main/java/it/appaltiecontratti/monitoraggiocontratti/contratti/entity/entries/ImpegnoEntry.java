package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class ImpegnoEntry {

	@XSSValidation
	private String codiceImpegno;
	@XSSValidation
	private Date dataImpegno;
	private Double importo;
	@XSSValidation
	private String descrizione;
	@XSSValidation
	private String atto;
	@XSSValidation
	private String impresa;

	public String getCodiceImpegno() {
		return codiceImpegno;
	}

	public void setCodiceImpegno(String codiceImpegno) {
		this.codiceImpegno = codiceImpegno;
	}

	public Date getDataImpegno() {
		return dataImpegno;
	}

	public void setDataImpegno(Date dataImpegno) {
		this.dataImpegno = dataImpegno;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getAtto() {
		return atto;
	}

	public void setAtto(String atto) {
		this.atto = atto;
	}

	public String getImpresa() {
		return impresa;
	}

	public void setImpresa(String impresa) {
		this.impresa = impresa;
	}

}
