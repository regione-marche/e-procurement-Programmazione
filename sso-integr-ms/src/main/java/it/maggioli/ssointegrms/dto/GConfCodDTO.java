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
public class GConfCodDTO implements Serializable {

	private static final long serialVersionUID = 5467381532331668677L;

	@NotBlank
	private String nomEnt;

	@NotBlank
	private String nomCam;

	@NotNull
	private Long tipCam;

	@Size(min = 0, max = 10)
	private String codApp;

	@Size(min = 0, max = 100)
	private String desCam;

	private Long contat;

	@Size(min = 0, max = 100)
	private String codCal;

	private Long numOrd;

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

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getDesCam() {
		return desCam;
	}

	public void setDesCam(String desCam) {
		this.desCam = desCam;
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

	public Long getNumOrd() {
		return numOrd;
	}

	public void setNumOrd(Long numOrd) {
		this.numOrd = numOrd;
	}

}
