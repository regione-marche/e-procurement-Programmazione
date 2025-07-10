package it.appaltiecontratti.inbox.entity.avvisi;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description="Contenitore per i dati di un documento dell'avviso preesistente (in fase di modifica)")
public class DocAvvisoEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="Numero dell'avviso associato al documento")
	private String codein;
	@ApiModelProperty(value="Id dell'avviso associato al documento")
	private Integer idavviso;
	@ApiModelProperty(hidden = true)
	private Integer codsistema;
	@ApiModelProperty(value="Numero del doc associato all'avviso")
	private Integer numdoc;
	@ApiModelProperty(hidden = true)
	private String titolo;
	@ApiModelProperty(hidden = true)
	private byte[] file_allegato;
	@ApiModelProperty(hidden = true)
	private String url;
	@ApiModelProperty(value="String binaria del contenuto del file")
	private String binary;
	
	public String getCodein() {
		return codein;
	}
	public void setCodein(String codein) {
		this.codein = codein;
	}
	public Integer getIdavviso() {
		return idavviso;
	}
	public void setIdavviso(Integer idavviso) {
		this.idavviso = idavviso;
	}
	public Integer getCodsistema() {
		return codsistema;
	}
	public void setCodsistema(Integer codsistema) {
		this.codsistema = codsistema;
	}
	public Integer getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(Integer numdoc) {
		this.numdoc = numdoc;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public byte[] getFile_allegato() {
		return file_allegato;
	}
	public void setFile_allegato(byte[] file_allegato) {
		this.file_allegato = file_allegato;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBinary() {
		return binary;
	}
	public void setBinary(String binary) {
		this.binary = binary;
	}

}