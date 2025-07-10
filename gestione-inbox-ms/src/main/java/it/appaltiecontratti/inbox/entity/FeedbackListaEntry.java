package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class FeedbackListaEntry {

	private Long numXml;
	private String denominazione;
	private String cig;
	private Long faseEsecuzione;
	private Long nLotto;
	private Date dataTrasmissione;
	private Long codgara;
	private Long codlott;
	private Long numProgressivoFase;
	private Long numErrore;
	private Long idFlusso;
	private boolean canRetry;
	private Long esito;
	private String feedbackAnalisi;
	private boolean contieneXmlELiminazione;
	private String xml;

	
	public String getFeedbackAnalisi() {
		return feedbackAnalisi;
	}

	public void setFeedbackAnalisi(String feedbackAnalisi) {
		this.feedbackAnalisi = feedbackAnalisi;
	}

	public Long getEsito() {
		return esito;
	}

	public void setEsito(Long esito) {
		this.esito = esito;
	}

	public Long getNumXml() {
		return numXml;
	}

	public void setNumXml(Long numXml) {
		this.numXml = numXml;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}

	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}

	public Long getnLotto() {
		return nLotto;
	}

	public void setnLotto(Long nLotto) {
		this.nLotto = nLotto;
	}

	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}

	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}

	public Long getCodgara() {
		return codgara;
	}

	public void setCodgara(Long codgara) {
		this.codgara = codgara;
	}

	public Long getCodlott() {
		return codlott;
	}

	public void setCodlott(Long codlott) {
		this.codlott = codlott;
	}

	public Long getNumProgressivoFase() {
		return numProgressivoFase;
	}

	public void setNumProgressivoFase(Long numProgressivoFase) {
		this.numProgressivoFase = numProgressivoFase;
	}

	public Long getNumErrore() {
		return numErrore;
	}

	public void setNumErrore(Long numErrore) {
		this.numErrore = numErrore;
	}

	public Long getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}

	public boolean isCanRetry() {
		return canRetry;
	}

	public void setCanRetry(boolean canRetry) {
		this.canRetry = canRetry;
	}

	public boolean isContieneXmlELiminazione() {
		return contieneXmlELiminazione;
	}

	public void setContieneXmlELiminazione(boolean contieneXmlELiminazione) {
		this.contieneXmlELiminazione = contieneXmlELiminazione;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
