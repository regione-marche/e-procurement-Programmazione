package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati da filtrare nella ricerca degli avvisi")
public class AvvisoSearchForm {

	@ApiModelProperty(value = "Codice stazione appaltante avviso da cercare")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "Numero avviso da cercare")
	private Integer numeroAvviso;
	@ApiModelProperty(value = "CIG avviso da cercare")
	private String cig;
	@ApiModelProperty(value = "Tipologia avviso da cercare")
	private Integer tipologia;
	@ApiModelProperty(value = "Descrizione avviso da cercare(in like)")
	private String descrizione;
	@ApiModelProperty(value = "Limite inferiore data avviso da cercare")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String dataDa;
	@ApiModelProperty(value = "Limite superiore data avviso da cercare")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String dataA;
	@ApiModelProperty(value = "Limite inferiore data scadenza da cercare")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String dataScadenzaDa;
	@ApiModelProperty(value = "Limite inferiore data scadenza da cercare")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String dataScadenzaA;
	@ApiModelProperty(value = "Syscon dell'avviso da cercare")
	private Long syscon;
	@ApiModelProperty(value = "Numero di record da estrarre")
	private int take;
	@ApiModelProperty(value = "Indice di partenza per l'estrazione")
	private int skip;
	@ApiModelProperty(value = "Campo su cui effettuare l'ordinamento")
	private String sort;
	@ApiModelProperty(value = "Direzione ordinamento. asc|desc")
	private String sortDirection;
	private String cup;
	
	private String codiceTecnico;
	
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public Integer getNumeroAvviso() {
		return numeroAvviso;
	}
	public void setNumeroAvviso(Integer numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public Integer getTipologia() {
		return tipologia;
	}
	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDataDa() {
		return dataDa;
	}
	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}
	public String getDataA() {
		return dataA;
	}
	public void setDataA(String dataA) {
		this.dataA = dataA;
	}
	public String getDataScadenzaDa() {
		return dataScadenzaDa;
	}
	public void setDataScadenzaDa(String dataScadenzaDa) {
		this.dataScadenzaDa = dataScadenzaDa;
	}
	public String getDataScadenzaA() {
		return dataScadenzaA;
	}
	public void setDataScadenzaA(String dataScadenzaA) {
		this.dataScadenzaA = dataScadenzaA;
	}
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
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
	public String getCodiceTecnico() {
		return codiceTecnico;
	}
	public void setCodiceTecnico(String codiceTecnico) {
		this.codiceTecnico = codiceTecnico;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	

}
