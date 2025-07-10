package it.appaltiecontratti.inbox.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class FeedbackSearchForm {

	private String codFisc;
	private String feedbackAnalisi;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataTrasmissioneDa;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataTrasmissioneA;
	private String cig;
	@JsonIgnore
	private Long numErrore;
	private String presenzaErrori;
	private String codiceAnomalia;
	private String fase;
	private Long faseNum;

	@ApiModelProperty(value = "Numero di record da estrarre")
	private int take;
	@ApiModelProperty(value = "Indice di partenza per l'estrazione")
	private int skip;
	@ApiModelProperty(value = "Campo su cui effettuare l'ordinamento")
	private String sort;
	@ApiModelProperty(value = "Direzione ordinamento. asc|desc")
	private String sortDirection;
	private boolean escludiEliminazioni;

	
	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public Long getFaseNum() {
		return faseNum;
	}

	public void setFaseNum(Long faseNum) {
		this.faseNum = faseNum;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getFeedbackAnalisi() {
		return feedbackAnalisi;
	}

	public void setFeedbackAnalisi(String feedbackAnalisi) {
		this.feedbackAnalisi = feedbackAnalisi;
	}

	public Date getDataTrasmissioneDa() {
		return dataTrasmissioneDa;
	}

	public void setDataTrasmissioneDa(Date dataTrasmissioneDa) {
		this.dataTrasmissioneDa = dataTrasmissioneDa;
	}

	public Date getDataTrasmissioneA() {
		return dataTrasmissioneA;
	}

	public void setDataTrasmissioneA(Date dataTrasmissioneA) {
		this.dataTrasmissioneA = dataTrasmissioneA;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public Long getNumErrore() {
		return numErrore;
	}

	public void setNumErrore(Long numErrore) {
		this.numErrore = numErrore;
	}

	public String getPresenzaErrori() {
		return presenzaErrori;
	}

	public void setPresenzaErrori(String presenzaErrori) {
		this.presenzaErrori = presenzaErrori;
	}

	public String getCodiceAnomalia() {
		return codiceAnomalia;
	}

	public void setCodiceAnomalia(String codiceAnomalia) {
		this.codiceAnomalia = codiceAnomalia;
	}

	public int getTake() {
		return take;
	}

	public void setTake(int take) {
		this.take = take;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public boolean getEscludiEliminazioni() {
		return escludiEliminazioni;
	}

	public void setEscludiEliminazioni(boolean escludiEliminazioni) {
		this.escludiEliminazioni = escludiEliminazioni;
	}

}
