package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DocumentoFasePubbEntry {

	private Long codGara;
	private Long codLotto;
	private Long codFase;
	private Long numeroProgressivoFase;
	
	@JsonIgnore
	private Long progressivoDocumento;
	
	private String titolo;
	
	@JsonIgnore
	private byte[] fileAllegato;
	
	private String binary;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public Long getCodFase() {
		return codFase;
	}

	public void setCodFase(Long codFase) {
		this.codFase = codFase;
	}

	public Long getNumeroProgressivoFase() {
		return numeroProgressivoFase;
	}

	public void setNumeroProgressivoFase(Long numeroProgressivoFase) {
		this.numeroProgressivoFase = numeroProgressivoFase;
	}

	public Long getProgressivoDocumento() {
		return progressivoDocumento;
	}

	public void setProgressivoDocumento(Long progressivoDocumento) {
		this.progressivoDocumento = progressivoDocumento;
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
}
