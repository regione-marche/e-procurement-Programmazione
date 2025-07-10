package it.appaltiecontratti.tabellati.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CdcNonPaginatiSearchForm {

	private String stazioneAppaltante;
	private String searchString;
	@JsonIgnore
	private String searchStringLike;

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchStringLike() {
		return searchStringLike;
	}

	public void setSearchStringLike(String searchStringLike) {
		this.searchStringLike = searchStringLike;
	}

}
