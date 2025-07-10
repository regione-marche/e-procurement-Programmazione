package it.appaltiecontratti.inbox.entity.contratti;

import io.swagger.annotations.ApiModelProperty;

public class AttoDocument {

	@ApiModelProperty(value = "codice gara dell'atto")
	private Long codGara;
	@ApiModelProperty(value = "numero pubblicazione dell'atto")
	private Long numPubb;
	@ApiModelProperty(value = "numero univoco del documento")
	private Long numDoc;
	@ApiModelProperty(value = "titolo dell'atto")
	private String titolo;
	@ApiModelProperty(value = "contenuto del file (byte array)")
	private byte[] fileAllegato;
	@ApiModelProperty(value = "contenuto del file (stringa)")
	private String binary;
	@ApiModelProperty(value = "url del documento")
	private String url;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getNumPubb() {
		return numPubb;
	}

	public void setNumPubb(Long numPubb) {
		this.numPubb = numPubb;
	}

	public Long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Long numDoc) {
		this.numDoc = numDoc;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public byte[] getFileAllegato() {
		return fileAllegato;
	}

	public void setFileAllegato(byte[] fileAllegato) {
		this.fileAllegato = fileAllegato;
	}

	public String getBinary() {
		return binary;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
