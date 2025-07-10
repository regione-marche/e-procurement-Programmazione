package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class W9FlussiEntry {

	private Long idFlusso;
	private Long area;
	private Long codGara;
	private Long codLotto;
	private Long faseEsecuzione;
	private Long numProgressivoFase;
	private Long tInvio2;
	private Date dataInvio;
	private String noteInvio;
	private String xml;
	private Long codComp;
	private Long idComunicazione;
	private String codiceFiscaleStazioneAppaltante;
	private String codiceOggetto;

	public Long getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
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

	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}

	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}

	public Long getNumProgressivoFase() {
		return numProgressivoFase;
	}

	public void setNumProgressivoFase(Long numProgressivoFase) {
		this.numProgressivoFase = numProgressivoFase;
	}

	public Long gettInvio2() {
		return tInvio2;
	}

	public void settInvio2(Long tInvio2) {
		this.tInvio2 = tInvio2;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getNoteInvio() {
		return noteInvio;
	}

	public void setNoteInvio(String noteInvio) {
		this.noteInvio = noteInvio;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getCodComp() {
		return codComp;
	}

	public void setCodComp(Long codComp) {
		this.codComp = codComp;
	}

	public Long getIdComunicazione() {
		return idComunicazione;
	}

	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public String getCodiceFiscaleStazioneAppaltante() {
		return codiceFiscaleStazioneAppaltante;
	}

	public void setCodiceFiscaleStazioneAppaltante(String codiceFiscaleStazioneAppaltante) {
		this.codiceFiscaleStazioneAppaltante = codiceFiscaleStazioneAppaltante;
	}

	public String getCodiceOggetto() {
		return codiceOggetto;
	}

	public void setCodiceOggetto(String codiceOggetto) {
		this.codiceOggetto = codiceOggetto;
	}

}
