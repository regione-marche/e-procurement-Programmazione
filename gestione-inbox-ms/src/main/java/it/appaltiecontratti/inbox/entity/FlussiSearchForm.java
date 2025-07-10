package it.appaltiecontratti.inbox.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

public class FlussiSearchForm{

	private String codFisc;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dataRicezioneDa;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dataRicezioneA;
	private Long area;
	private Long faseEsecuzione;
	private String  codOggetto;
	private Long numeroProg;
	
	@ApiModelProperty(value = "Numero di record da estrarre")
	private int take;
	@ApiModelProperty(value = "Indice di partenza per l'estrazione")
	private int skip;
	@ApiModelProperty(value = "Campo su cui effettuare l'ordinamento")
	private String sort;
	@ApiModelProperty(value = "Direzione ordinamento. asc|desc")
	private String sortDirection;
	
	
	public Long getNumeroProg() {
		return numeroProg;
	}
	public void setNumeroProg(Long numeroProg) {
		this.numeroProg = numeroProg;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}	
	public Date getDataRicezioneDa() {
		return dataRicezioneDa;
	}
	public void setDataRicezioneDa(Date dataRicezioneDa) {
		this.dataRicezioneDa = dataRicezioneDa;
	}
	public Date getDataRicezioneA() {
		return dataRicezioneA;
	}
	public void setDataRicezioneA(Date dataRicezioneA) {
		this.dataRicezioneA = dataRicezioneA;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}
	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}
	public String getCodOggetto() {
		return codOggetto;
	}
	public void setCodOggetto(String codOggetto) {
		this.codOggetto = codOggetto;
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
