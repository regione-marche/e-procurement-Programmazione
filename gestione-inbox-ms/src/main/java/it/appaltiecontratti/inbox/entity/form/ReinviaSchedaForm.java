package it.appaltiecontratti.inbox.entity.form;

import javax.validation.constraints.NotNull;

public class ReinviaSchedaForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	@NotNull
	private Long faseEsecuzione;
	@NotNull
	private Long numProgressivoFase;
	private Long idFlusso;
	private Long numXml;

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

	public Long getFaseEsecuzione() {
		return faseEsecuzione;
	}

	public void setFaseEsecuzione(Long faseEsecuzione) {
		this.faseEsecuzione = faseEsecuzione;
	}

	public Long getNumProgressivoFase() {
		return numProgressivoFase;
	}

	public void setNumProgressivoFase(Long numProgressivoFase) {
		this.numProgressivoFase = numProgressivoFase;
	}

	public Long getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}

	public Long getNumXml() {
		return numXml;
	}

	public void setNumXml(Long numXml) {
		this.numXml = numXml;
	}

}
