package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;

public class InterventiDaRiproporre {
	
	@ApiModelProperty(value = "Id del programma")
	private Long idProgramma;
	@ApiModelProperty(value = "Id intervento")
	private Long id;
	@ApiModelProperty(value = "Numero CUI")
	private String cui;
	@ApiModelProperty(value = "Descrizione")
	private String descrizione;
	@ApiModelProperty(value = "Importo totale")
	private double importo;
	@ApiModelProperty(value = "Anno")
	private Long anno;
	@ApiModelProperty(value = "Anno avvio")
	private Long annoAvvio;
	private String piatriId;
	
	public Long getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCui() {
		return cui;
	}
	public void setCui(String cui) {
		this.cui = cui;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public double getImporto() {
		return importo;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public Long getAnno() {
		return anno;
	}
	public void setAnno(Long anno) {
		this.anno = anno;
	}
	public Long getAnnoAvvio() {
		return annoAvvio;
	}
	public void setAnnoAvvio(Long annoAvvio) {
		this.annoAvvio = annoAvvio;
	}
	public String getPiatriId() {
		return piatriId;
	}
	public void setPiatriId(String piatriId) {
		this.piatriId = piatriId;
	}	
	

}
