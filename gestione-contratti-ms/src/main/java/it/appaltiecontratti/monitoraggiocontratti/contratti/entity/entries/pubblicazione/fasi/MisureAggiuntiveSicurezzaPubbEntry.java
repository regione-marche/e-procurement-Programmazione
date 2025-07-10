package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.List;

public class MisureAggiuntiveSicurezzaPubbEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Long numIniz;
	private String descrizione;
	private String codiceImpresa;
	private ImpresaFasePubbEntry impresa;
	private List<DocumentoFasePubbEntry> documents;

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

	public ImpresaFasePubbEntry getImpresa() {
		return impresa;
	}

	public void setImpresa(ImpresaFasePubbEntry impresa) {
		this.impresa = impresa;
	}

	public List<DocumentoFasePubbEntry> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentoFasePubbEntry> documents) {
		this.documents = documents;
	}

	public Long getNumIniz() {
		return numIniz;
	}

	public void setNumIniz(Long numIniz) {
		this.numIniz = numIniz;
	}

}
