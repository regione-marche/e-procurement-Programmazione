package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class ExistingDocumentoFaseEntry {

	private Long codGara;
	private Long codLotto;
	private Long progressivoDocumento;
	private String titolo;
	private String tipoFile;

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

	public String getTipoFile() {
		return tipoFile;
	}

	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}

}
