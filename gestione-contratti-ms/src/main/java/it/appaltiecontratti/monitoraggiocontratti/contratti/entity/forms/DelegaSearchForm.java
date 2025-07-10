package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import io.swagger.annotations.ApiModelProperty;

public class DelegaSearchForm {

	@ApiModelProperty(value = "Numero di record da estrarre")
	private int take;
	@ApiModelProperty(value = "Indice di partenza per l'estrazione")
	private int skip;
	@ApiModelProperty(value = "Campo su cui effettuare l'ordinamento")
	private String sort;
	@ApiModelProperty(value = "Direzione ordinamento. asc|desc")
	private String sortDirection;

	@ApiModelProperty(value = "cf utente connesso")
	private String cfrup;

	@ApiModelProperty(value = "Stazione appaltante")
	private String stazioneAppaltante;

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

	public String getCfrup() {
		return cfrup;
	}

	public void setCfrup(String cfrup) {
		this.cfrup = cfrup;
	}

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

}