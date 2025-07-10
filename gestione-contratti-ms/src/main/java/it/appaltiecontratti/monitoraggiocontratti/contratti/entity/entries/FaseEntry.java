package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaseEntry {

	private Long codGara;
	private Long codLotto;
	private Long fase;
	private Long progressivo;
	private boolean deleteFisica;
	private boolean deleteLogica;
	private boolean pubblicabile;
	private Long numFlussi;
	private Long numRichCanc;
	private Long recordTinvio2Uguale3;
	private Long recordTinvio2Uguale1;
	@JsonIgnore
	private String daExportDb;

	private boolean daExport;
	private Long daExportNum;
	private boolean pubblicata;

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

	public Long getFase() {
		return fase;
	}

	public void setFase(Long fase) {
		this.fase = fase;
	}

	public Long getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Long progressivo) {
		this.progressivo = progressivo;
	}

	public boolean isDeleteFisica() {
		return deleteFisica;
	}

	public void setDeleteFisica(boolean deleteFisica) {
		this.deleteFisica = deleteFisica;
	}

	public boolean isDeleteLogica() {
		return deleteLogica;
	}

	public void setDeleteLogica(boolean deleteLogica) {
		this.deleteLogica = deleteLogica;
	}

	public String getDaExportDb() {
		return daExportDb;
	}

	public void setDaExportDb(String daExportDb) {
		this.daExportDb = daExportDb;
		this.setDaExport("1".equals(daExportDb) ? true : false);
	}

	public boolean isDaExport() {
		return daExport;
	}

	public void setDaExport(boolean daExport) {
		this.daExport = daExport;
	}

	public boolean isPubblicata() {
		return pubblicata;
	}

	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}

	public boolean isPubblicabile() {
		return pubblicabile;
	}

	public void setPubblicabile(boolean pubblicabile) {
		this.pubblicabile = pubblicabile;
	}

	public Long getNumFlussi() {
		return numFlussi;
	}

	public void setNumFlussi(Long numFlussi) {
		this.numFlussi = numFlussi;
	}

	public Long getNumRichCanc() {
		return numRichCanc;
	}

	public void setNumRichCanc(Long numRichCanc) {
		this.numRichCanc = numRichCanc;
	}

	public Long getDaExportNum() {
		return daExportNum;
	}

	public void setDaExportNum(Long daExportNum) {		
		this.daExportNum = daExportNum;
	}

	public Long getRecordTinvio2Uguale3() {
		return recordTinvio2Uguale3;
	}

	public void setRecordTinvio2Uguale3(Long recordTinvio2Uguale3) {
		this.recordTinvio2Uguale3 = recordTinvio2Uguale3;
	}

	public Long getRecordTinvio2Uguale1() {
		return recordTinvio2Uguale1;
	}

	public void setRecordTinvio2Uguale1(Long recordTinvio2Uguale1) {
		this.recordTinvio2Uguale1 = recordTinvio2Uguale1;
	}
	
}
