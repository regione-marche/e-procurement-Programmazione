package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.io.Serializable;

public class PubblicazioneDocumentoAvvisoEntry implements Serializable {

	private static final long serialVersionUID = -1808710481090051657L;

	private String titolo;
	private String file_allegato;
	private String url;
	private String tipoFile;

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getFile_allegato() {
		return file_allegato;
	}

	public void setFile_allegato(String file_allegato) {
		this.file_allegato = file_allegato;
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
