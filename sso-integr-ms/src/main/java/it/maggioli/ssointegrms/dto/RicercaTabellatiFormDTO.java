package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaTabellatiFormDTO extends BaseSearchFormDTO implements Serializable {

	private static final long serialVersionUID = -5312444778875244483L;

	private String codiceTabellato;
	private String descrizioneTabellato;

	public String getCodiceTabellato() {
		return codiceTabellato;
	}

	public void setCodiceTabellato(String codiceTabellato) {
		this.codiceTabellato = codiceTabellato;
	}

	public String getDescrizioneTabellato() {
		return descrizioneTabellato;
	}

	public void setDescrizioneTabellato(String descrizioneTabellato) {
		this.descrizioneTabellato = descrizioneTabellato;
	}

}
