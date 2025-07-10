package it.appaltiecontratti.inbox.entity;

public class ReinvioSchedaEntry {

	private Long codGara;
	private Long codLotto;
	private Long fase;
	private Long num;
	
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
	public Long getFase() {
		return fase;
	}
	public void setFase(Long fase) {
		this.fase = fase;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
}
