package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaseEntry {

	private Long codGara;
	private Long codLotto;
	private Long fase;
	private Long progressivo;

	@JsonIgnore
	private String daExportDb;

	private boolean daExport;
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

}
