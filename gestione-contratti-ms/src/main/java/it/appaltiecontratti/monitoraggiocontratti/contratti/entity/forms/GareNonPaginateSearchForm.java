package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GareNonPaginateSearchForm {

	private String searchString;
	@JsonIgnore
	private String searchStringLike;
	private String stazioneAppaltante;
	private Long syscon;
	@JsonIgnore
	private String codiceTecnico;

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

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getCodiceTecnico() {
		return codiceTecnico;
	}

	public void setCodiceTecnico(String codiceTecnico) {
		this.codiceTecnico = codiceTecnico;
	}

}
