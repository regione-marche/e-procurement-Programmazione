package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;

public class BaseSearchForm {

	
	@ApiModelProperty(value = "Numero di record da estrarre")
	private int take;
	@ApiModelProperty(value = "Indice di partenza per l'estrazione")
	private int skip;
	@ApiModelProperty(value = "Campo su cui effettuare l'ordinamento")
	private String sort;
	@ApiModelProperty(value = "Direzione ordinamento. asc|desc")
	private String sortDirection;
	
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
}
