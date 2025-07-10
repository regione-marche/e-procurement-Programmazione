package it.appaltiecontratti.programmi.entity;

public class ProgrammiSearchForm extends BaseSearchForm{
	
	private Long syscon;
	private Long tipologia;
	private String stazioneAppaltante;
	private String searchString;
	private String searchStringLike;
	private Long idCentroCosto;
	
	public Long getSyscon() {
		return syscon;
	}
	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}
	public Long getTipologia() {
		return tipologia;
	}
	public void setTipologia(Long tipologia) {
		this.tipologia = tipologia;
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
	public Long getIdCentroCosto() {
		return idCentroCosto;
	}
	public void setIdCentroCosto(Long idCentroCosto) {
		this.idCentroCosto = idCentroCosto;
	}
	
	

	
}
