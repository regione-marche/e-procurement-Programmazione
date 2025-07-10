package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.List;

import javax.validation.constraints.NotNull;

public class MisureAggiuntivesicurezzaInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long codFase;
	private Long num;
	private Long numIniz;
	@XSSValidation
	private String descrizione;
	@XSSValidation
	private String codiceImpresa;
	private List<DocumentoFaseInsertForm> documents;

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

	public List<DocumentoFaseInsertForm> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentoFaseInsertForm> documents) {
		this.documents = documents;
	}

	public Long getNumIniz() {
		return numIniz;
	}

	public void setNumIniz(Long numIniz) {
		this.numIniz = numIniz;
	}
	

}
