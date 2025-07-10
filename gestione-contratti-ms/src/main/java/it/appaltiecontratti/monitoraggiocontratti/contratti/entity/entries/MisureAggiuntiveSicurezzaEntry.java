package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class MisureAggiuntiveSicurezzaEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private String descrizione;
	private String codiceImpresa;
	private ImpresaEntry impresa;
	private List<ExistingDocumentoFaseEntry> existingDocuments;

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

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceImpresa() {
		return codiceImpresa;
	}

	public void setCodiceImpresa(String codiceImpresa) {
		this.codiceImpresa = codiceImpresa;
	}

	public ImpresaEntry getImpresa() {
		return impresa;
	}

	public void setImpresa(ImpresaEntry impresa) {
		this.impresa = impresa;
	}

	public List<ExistingDocumentoFaseEntry> getExistingDocuments() {
		return existingDocuments;
	}

	public void setExistingDocuments(List<ExistingDocumentoFaseEntry> existingDocuments) {
		this.existingDocuments = existingDocuments;
	}

}
