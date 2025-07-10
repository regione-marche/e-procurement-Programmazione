package it.appaltiecontratti.programmi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProgrammiNonPaginatiSearchForm {

	private Long syscon;
	private String stazioneAppaltante;
	private String searchString;
	@JsonIgnore
	private String searchStringLike;

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

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
