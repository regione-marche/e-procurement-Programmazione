package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiAvanzamentoEntry {

	private Long flagPagamento;
	private Date dataAnticipazione;
	private Double importoAnticipazione;
	private Date dataRaggiungimento;
	private Double importoSal;
	private Date dataCertificato;
	private Double importoCertificato;
	private Double importoPartecipazione;
	private String flagRitardo;
	private Long numGiorniScost;
	private Long numGiorniProroga;
	private String denomAvanzamento;
	private String idSchedaLocale;
	private String idSchedaSimog;
	private Long numAvan;

	public Long getFlagPagamento() {
		return flagPagamento;
	}

	public void setFlagPagamento(Long flagPagamento) {
		this.flagPagamento = flagPagamento;
	}

	public Date getDataAnticipazione() {
		return dataAnticipazione;
	}

	public void setDataAnticipazione(Date dataAnticipazione) {
		this.dataAnticipazione = dataAnticipazione;
	}

	public Double getImportoAnticipazione() {
		return importoAnticipazione;
	}

	public void setImportoAnticipazione(Double importoAnticipazione) {
		this.importoAnticipazione = importoAnticipazione;
	}

	public Date getDataRaggiungimento() {
		return dataRaggiungimento;
	}

	public void setDataRaggiungimento(Date dataRaggiungimento) {
		this.dataRaggiungimento = dataRaggiungimento;
	}

	public Double getImportoSal() {
		return importoSal;
	}

	public void setImportoSal(Double importoSal) {
		this.importoSal = importoSal;
	}

	public Date getDataCertificato() {
		return dataCertificato;
	}

	public void setDataCertificato(Date dataCertificato) {
		this.dataCertificato = dataCertificato;
	}

	public Double getImportoCertificato() {
		return importoCertificato;
	}

	public void setImportoCertificato(Double importoCertificato) {
		this.importoCertificato = importoCertificato;
	}

	public Double getImportoPartecipazione() {
		return importoPartecipazione;
	}

	public void setImportoPartecipazione(Double importoPartecipazione) {
		this.importoPartecipazione = importoPartecipazione;
	}

	public String getFlagRitardo() {
		return flagRitardo;
	}

	public void setFlagRitardo(String flagRitardo) {
		this.flagRitardo = flagRitardo;
	}

	public Long getNumGiorniScost() {
		return numGiorniScost;
	}

	public void setNumGiorniScost(Long numGiorniScost) {
		this.numGiorniScost = numGiorniScost;
	}

	public Long getNumGiorniProroga() {
		return numGiorniProroga;
	}

	public void setNumGiorniProroga(Long numGiorniProroga) {
		this.numGiorniProroga = numGiorniProroga;
	}

	public String getDenomAvanzamento() {
		return denomAvanzamento;
	}

	public void setDenomAvanzamento(String denomAvanzamento) {
		this.denomAvanzamento = denomAvanzamento;
	}

	public String getIdSchedaLocale() {
		return idSchedaLocale;
	}

	public void setIdSchedaLocale(String idSchedaLocale) {
		this.idSchedaLocale = idSchedaLocale;
	}

	public String getIdSchedaSimog() {
		return idSchedaSimog;
	}

	public void setIdSchedaSimog(String idSchedaSimog) {
		this.idSchedaSimog = idSchedaSimog;
	}

	public Long getNumAvan() {
		return numAvan;
	}

	public void setNumAvan(Long numAvan) {
		this.numAvan = numAvan;
	}

}
