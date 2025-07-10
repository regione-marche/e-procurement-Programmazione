package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailInizDTO implements Serializable {

	private static final long serialVersionUID = -2429356264286702311L;

	private List<ValoreTabellato> listaConfigurazioni;
	private String timeout;
	private Long dimTotaleAllegati;
	private Long delay;

	public List<ValoreTabellato> getListaConfigurazioni() {
		return listaConfigurazioni;
	}

	public void setListaConfigurazioni(List<ValoreTabellato> listaConfigurazioni) {
		this.listaConfigurazioni = listaConfigurazioni;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public Long getDimTotaleAllegati() {
		return dimTotaleAllegati;
	}

	public void setDimTotaleAllegati(Long dimTotaleAllegati) {
		this.dimTotaleAllegati = dimTotaleAllegati;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

}
