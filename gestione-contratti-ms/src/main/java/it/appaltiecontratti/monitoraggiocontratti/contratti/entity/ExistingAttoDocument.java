package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class ExistingAttoDocument {

	@ApiModelProperty(value = "codice gara dell'atto")
	private Long codGara;
	@ApiModelProperty(value = "numero pubblicazione dell'atto")
	private Long numPubb;
	@ApiModelProperty(value = "numero univoco del documento")
	private Long numDoc;
	@ApiModelProperty(value = "titolo dell'atto")
	@XSSValidation
	private String titolo;
	@ApiModelProperty(value = "url del documento")
	@XSSValidation
	private String url;
	@ApiModelProperty(value = "tipologia file")
	@XSSValidation
	private String tipoFile;

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
	

}
