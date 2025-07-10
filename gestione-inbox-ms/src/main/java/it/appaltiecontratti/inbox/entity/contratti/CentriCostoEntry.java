package it.appaltiecontratti.inbox.entity.contratti;

import io.swagger.annotations.ApiModelProperty;

public class CentriCostoEntry {

	@ApiModelProperty(value="id del centro di costo")
	private Long id;
	@ApiModelProperty(value="codice del centro di costo")
	private String codiceCDC;
	@ApiModelProperty(value="nominativo del centro di costo")
	private String nominativoCDC;
	@ApiModelProperty(value="tipologia del centro di costo")
	private String tipologia;
	@ApiModelProperty(value="eventuali note del centro di costo")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceCDC() {
		return codiceCDC;
	}

	public void setCodiceCDC(String codiceCDC) {
		this.codiceCDC = codiceCDC;
	}

	public String getNominativoCDC() {
		return nominativoCDC;
	}

	public void setNominativoCDC(String nominativoCDC) {
		this.nominativoCDC = nominativoCDC;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
