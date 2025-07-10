package it.appaltiecontratti.inbox.entity.loaderappalto;

public class DatiW9FaseEntry {

	private Long codGara;
	private Long codLotto;
	private Long faseEsecuzione;
	private Long numProgressivoFase;

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

}
