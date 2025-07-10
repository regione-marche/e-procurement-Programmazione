package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseConclusioneSempEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private String interruzioneAnticipata;
	private Long motivoInterruzione;
	private Long motivoRisoluzione;
	private Date dataRisoluzione;
	private String flagOneri;
	private Double importoOneri;
	private String flagPolizza;
	private Date dataUltimazione;
	private Double oreLavorate;
	private boolean pubblicata;
	private boolean faseInizialeExists;

	public boolean isPubblicata() {
		return pubblicata;
	}

	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}

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

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getInterruzioneAnticipata() {
		return interruzioneAnticipata;
	}

	public void setInterruzioneAnticipata(String interruzioneAnticipata) {
		this.interruzioneAnticipata = interruzioneAnticipata;
	}

	public Long getMotivoInterruzione() {
		return motivoInterruzione;
	}

	public void setMotivoInterruzione(Long motivoInterruzione) {
		this.motivoInterruzione = motivoInterruzione;
	}

	public Long getMotivoRisoluzione() {
		return motivoRisoluzione;
	}

	public void setMotivoRisoluzione(Long motivoRisoluzione) {
		this.motivoRisoluzione = motivoRisoluzione;
	}

	public Date getDataRisoluzione() {
		return dataRisoluzione;
	}

	public void setDataRisoluzione(Date dataRisoluzione) {
		this.dataRisoluzione = dataRisoluzione;
	}

	public String getFlagOneri() {
		return flagOneri;
	}

	public void setFlagOneri(String flagOneri) {
		this.flagOneri = flagOneri;
	}

	public Double getImportoOneri() {
		return importoOneri;
	}

	public void setImportoOneri(Double importoOneri) {
		this.importoOneri = importoOneri;
	}

	public String getFlagPolizza() {
		return flagPolizza;
	}

	public void setFlagPolizza(String flagPolizza) {
		this.flagPolizza = flagPolizza;
	}

	public Date getDataUltimazione() {
		return dataUltimazione;
	}

	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}

	public Double getOreLavorate() {
		return oreLavorate;
	}

	public void setOreLavorate(Double oreLavorate) {
		this.oreLavorate = oreLavorate;
	}

	public boolean isFaseInizialeExists() {
		return faseInizialeExists;
	}

	public void setFaseInizialeExists(boolean faseInizialeExists) {
		this.faseInizialeExists = faseInizialeExists;
	}

}
