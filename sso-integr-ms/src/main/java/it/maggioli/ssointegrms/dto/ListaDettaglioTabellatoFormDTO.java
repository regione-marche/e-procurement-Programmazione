package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ListaDettaglioTabellatoFormDTO implements Serializable {

	private static final long serialVersionUID = -5609020281162051525L;

	@NotNull
	private Integer skip;
	@NotNull
	private Integer take;
	@NotBlank
	private String provenienza;
	@NotBlank
	private String codiceTabellato;

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getTake() {
		return take;
	}

	public void setTake(Integer take) {
		this.take = take;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getCodiceTabellato() {
		return codiceTabellato;
	}

	public void setCodiceTabellato(String codiceTabellato) {
		this.codiceTabellato = codiceTabellato;
	}

	@Override
	public String toString() {
		return "ListaDettaglioTabellatoFormDTO [skip=" + skip + ", take=" + take + ", provenienza=" + provenienza
				+ ", codiceTabellato=" + codiceTabellato + "]";
	}

}
