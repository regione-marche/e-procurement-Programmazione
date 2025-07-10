package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Contenitore per i dati di un nuovo documento dell'avviso in fase di inserimento e modifica")
public class AvvisoDocument {
	
	@ApiModelProperty(value="Numero dell'avviso associato al documento")
	private Integer idavviso;
	@ApiModelProperty(value="Titolo del documento")
    private String titolo;
	@ApiModelProperty(value="Url associata al documento")
    private String url;
	@ApiModelProperty(value="Binario del contenuto associato al documento")
    private String binary;
	@ApiModelProperty(hidden=true)
    private String id;
	private String tipoFile;
    
	public Integer getIdavviso() {
		return idavviso;
	}
	public void setIdavviso(Integer idavviso) {
		this.idavviso = idavviso;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipoFile() {
		return tipoFile;
	}
	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}
	
}
