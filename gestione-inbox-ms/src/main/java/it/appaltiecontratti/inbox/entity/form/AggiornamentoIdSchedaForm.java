package it.appaltiecontratti.inbox.entity.form;

public class AggiornamentoIdSchedaForm {

	private Long codGara;
	private Long codLotto;
	private Long faseEsecuzione;
	private Long numProgressivoFase;
	private String idSchedaLocale;
	private String idSchedaSimog;
	private String scheda;

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

	public String getIdSchedaLocale() {
		return idSchedaLocale;
	}

	public void setIdSchedaLocale(String idSchedaLocale) {
		this.idSchedaLocale = idSchedaLocale;
	}

	public String getIdSchedaSimog() {
		return idSchedaSimog;
	}

	public void setIdSchedaSimog(String idSchedaSimog) {
		this.idSchedaSimog = idSchedaSimog;
	}

	public String getScheda() {
		return scheda;
	}

	public void setScheda(String scheda) {
		this.scheda = scheda;
	}

}
