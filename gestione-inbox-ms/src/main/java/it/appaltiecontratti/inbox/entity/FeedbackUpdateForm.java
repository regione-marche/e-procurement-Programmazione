package it.appaltiecontratti.inbox.entity;

import javax.validation.constraints.NotNull;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FeedbackUpdateForm {

	@XSSValidation
	private String note;
	@XSSValidation
	private String feedbackAnalisi;
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLott;
	@NotNull
	private Long numXml;
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFeedbackAnalisi() {
		return feedbackAnalisi;
	}
	public void setFeedbackAnalisi(String feedbackAnalisi) {
		this.feedbackAnalisi = feedbackAnalisi;
	}
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getCodLott() {
		return codLott;
	}
	public void setCodLott(Long codLott) {
		this.codLott = codLott;
	}
	public Long getNumXml() {
		return numXml;
	}
	public void setNumXml(Long numXml) {
		this.numXml = numXml;
	}
	
	
}
