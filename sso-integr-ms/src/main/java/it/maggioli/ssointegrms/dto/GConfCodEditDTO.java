package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class GConfCodEditDTO implements Serializable {

	private static final long serialVersionUID = 6526960213126288147L;

	@NotBlank
	private String nomEnt;

	@NotBlank
	private String nomCam;

	@NotNull
	private Long tipCam;

	private Long contat;

	@Size(min = 0, max = 100)
	private String codCal;

	private String codificaAutomaticaAttiva;

	public String getNomEnt() {
		return nomEnt;
	}

	public void setNomEnt(String nomEnt) {
		this.nomEnt = nomEnt;
	}

	public String getNomCam() {
		return nomCam;
	}

	public void setNomCam(String nomCam) {
		this.nomCam = nomCam;
	}

	public Long getTipCam() {
		return tipCam;
	}

	public void setTipCam(Long tipCam) {
		this.tipCam = tipCam;
	}

	public Long getContat() {
		return contat;
	}

	public void setContat(Long contat) {
		this.contat = contat;
	}

	public String getCodCal() {
		return codCal;
	}

	public void setCodCal(String codCal) {
		this.codCal = codCal;
	}

	public String getCodificaAutomaticaAttiva() {
		return codificaAutomaticaAttiva;
	}

	public void setCodificaAutomaticaAttiva(String codificaAutomaticaAttiva) {
		this.codificaAutomaticaAttiva = codificaAutomaticaAttiva;
	}

}
