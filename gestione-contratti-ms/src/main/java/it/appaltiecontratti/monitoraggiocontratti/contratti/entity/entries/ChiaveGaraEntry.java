package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class ChiaveGaraEntry {
	
	private Long codGara;
	
	private Long codLotto;

	
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
	
	public ChiaveGaraEntry(Long codGara, Long codLotto) {
		this.codGara = codGara;
		this.codLotto = codLotto;
	}


}
