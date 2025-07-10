package it.appaltiecontratti.inbox.entity.form;

import java.util.Date;

public class W9XmlAppaltoResponseForm {

	private Long codGara;
	private Long codLotto;
	private Long numXml;
	private Date dataFeedback;
	private Date dataElaborazione;
	private Long numElaborate;
	private Long numErrore;
	private Long numWarning;
	private Long numCaricate;
	private String feedbackAnalisi;

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

	public Long getNumXml() {
		return numXml;
	}

	public void setNumXml(Long numXml) {
		this.numXml = numXml;
	}

	public Date getDataFeedback() {
		return dataFeedback;
	}

	public void setDataFeedback(Date dataFeedback) {
		this.dataFeedback = dataFeedback;
	}

	public Date getDataElaborazione() {
		return dataElaborazione;
	}

	public void setDataElaborazione(Date dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}

	public Long getNumElaborate() {
		return numElaborate;
	}

	public void setNumElaborate(Long numElaborate) {
		this.numElaborate = numElaborate;
	}

	public Long getNumErrore() {
		return numErrore;
	}

	public void setNumErrore(Long numErrore) {
		this.numErrore = numErrore;
	}

	public Long getNumWarning() {
		return numWarning;
	}

	public void setNumWarning(Long numWarning) {
		this.numWarning = numWarning;
	}

	public Long getNumCaricate() {
		return numCaricate;
	}

	public void setNumCaricate(Long numCaricate) {
		this.numCaricate = numCaricate;
	}

	public String getFeedbackAnalisi() {
		return feedbackAnalisi;
	}

	public void setFeedbackAnalisi(String feedbackAnalisi) {
		this.feedbackAnalisi = feedbackAnalisi;
	}

}
