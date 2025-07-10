package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ExistingDocumentoFaseEntry;

public class MisureAggiuntivesicurezzaUpdateForm {

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
	private List<ExistingDocumentoFaseEntry> existingDocuments;
	private List<DocumentoFaseInsertForm> newDocuments;

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

	public Long getNumIniz() {
		return numIniz;
	}

	public void setNumIniz(Long numIniz) {
		this.numIniz = numIniz;
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

	public List<ExistingDocumentoFaseEntry> getExistingDocuments() {
		return existingDocuments;
	}

	public void setExistingDocuments(List<ExistingDocumentoFaseEntry> existingDocuments) {
		this.existingDocuments = existingDocuments;
	}

	public List<DocumentoFaseInsertForm> getNewDocuments() {
		return newDocuments;
	}

	public void setNewDocuments(List<DocumentoFaseInsertForm> newDocuments) {
		this.newDocuments = newDocuments;
	}

}
