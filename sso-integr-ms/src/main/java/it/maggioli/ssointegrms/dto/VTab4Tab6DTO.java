package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class VTab4Tab6DTO implements Serializable {

	private static final long serialVersionUID = 3329647055303097778L;

	private String tab46Cod;
	private String codiceTabellato;
	private String descrizioneTabellato;
	private String provenienza;

	public String getTab46Cod() {
		return tab46Cod;
	}

	public void setTab46Cod(String tab46Cod) {
		this.tab46Cod = tab46Cod;
	}

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

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	@Override
	public String toString() {
		return "VTab4Tab6DTO [tab46Cod=" + tab46Cod + ", codiceTabellato=" + codiceTabellato + ", descrizioneTabellato="
				+ descrizioneTabellato + ", provenienza=" + provenienza + "]";
	}

}
