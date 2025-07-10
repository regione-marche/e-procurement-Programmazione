package it.appaltiecontratti.inbox.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

public class ComScpSearchForm {

	private String codFisc;
	private Long area;
	private String codice;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dataInvioDa;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dataInvioA;
	private Long stato;
	
	@ApiModelProperty(value = "Numero di record da estrarre")
	private int take;
	@ApiModelProperty(value = "Indice di partenza per l'estrazione")
	private int skip;
	@ApiModelProperty(value = "Campo su cui effettuare l'ordinamento")
	private String sort;
	@ApiModelProperty(value = "Direzione ordinamento. asc|desc")
	private String sortDirection;
	
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}	
	
	public Date getDataInvioDa() {
		return dataInvioDa;
	}
	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}
	public Date getDataInvioA() {
		return dataInvioA;
	}
	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}
	public Long getStato() {
		return stato;
	}
	public void setStato(Long stato) {
		this.stato = stato;
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
	
	
}
