package it.appaltiecontratti.autenticazione.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class SpidInizData {
	
	private Boolean multiente;
	
	private Boolean visibleValidator;
	
	private List<SABaseEntry> stazioniAppaltanti;

	public Boolean getMultiente() {
		return multiente;
	}

	public void setMultiente(Boolean multiente) {
		this.multiente = multiente;
	}

	public Boolean getVisibleValidator() {
		return visibleValidator;
	}

	public void setVisibleValidator(Boolean visibleValidator) {
		this.visibleValidator = visibleValidator;
	}

	public List<SABaseEntry> getStazioniAppaltanti() {
		return stazioniAppaltanti;
	}

	public void setStazioniAppaltanti(List<SABaseEntry> stazioniAppaltanti) {
		this.stazioniAppaltanti = stazioniAppaltanti;
	}
	
	
	

}
