package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.io.Serializable;

public class ExistingDocAvvisoEntry implements Serializable {

	private static final long serialVersionUID = 4896676687444938917L;

	private String codein;
	private Integer idAvviso;
	private Integer numDoc;
	private String titolo;
	private String url;
	private String tipoFile;
	private Integer codSistema;

	public String getCodein() {
		return codein;
	}

	public void setCodein(String codein) {
		this.codein = codein;
	}

	public Integer getIdAvviso() {
		return idAvviso;
	}

	public void setIdAvviso(Integer idAvviso) {
		this.idAvviso = idAvviso;
	}

	public Integer getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Integer numDoc) {
		this.numDoc = numDoc;
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

	public String getTipoFile() {
		return tipoFile;
	}

	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}

	public Integer getCodSistema() {
		return codSistema;
	}

	public void setCodSistema(Integer codSistema) {
		this.codSistema = codSistema;
	}

}