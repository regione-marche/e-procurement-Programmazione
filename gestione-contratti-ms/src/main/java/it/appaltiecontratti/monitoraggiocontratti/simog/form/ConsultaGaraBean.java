package it.appaltiecontratti.monitoraggiocontratti.simog.form;

public class ConsultaGaraBean {

	private String codiceCIG;
	private boolean caricato;
	private String msg;

	public ConsultaGaraBean(String codiceCIG) {
		this.codiceCIG = codiceCIG;
		this.caricato = false;
		this.msg = null;
	}

	public ConsultaGaraBean(String codiceCIG, boolean caricato) {
		this.codiceCIG = codiceCIG;
		this.caricato = caricato;
		this.msg = null;
	}

	public ConsultaGaraBean(String codiceCIG, boolean caricato, String messaggio) {
		this.codiceCIG = codiceCIG;
		this.caricato = caricato;
		this.msg = messaggio;
	}

	/**
	 * @return the caricato
	 */
	public boolean isCaricato() {
		return caricato;
	}

	/**
	 * @param caricato the caricato to set
	 */
	public void setCaricato(boolean caricato) {
		this.caricato = caricato;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the codiceCIG
	 */
	public String getCodiceCIG() {
		return codiceCIG;
	}

}