package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailEditPasswordDTO implements Serializable {

	private static final long serialVersionUID = 4585743873183264315L;

	@NotBlank
	@Size(min = 1, max = 16)
	private String idCfg;
	@Size(min = 0, max = 100)
	private String vecchiaPassword;
	@Size(min = 0, max = 100)
	private String nuovaPassword;
	@Size(min = 0, max = 100)
	private String confermaNuovaPassword;

	public String getIdCfg() {
		return idCfg;
	}

	public void setIdCfg(String idCfg) {
		this.idCfg = idCfg;
	}

	public String getVecchiaPassword() {
		return vecchiaPassword;
	}

	public void setVecchiaPassword(String vecchiaPassword) {
		this.vecchiaPassword = vecchiaPassword;
	}

	public String getNuovaPassword() {
		return nuovaPassword;
	}

	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}

	public String getConfermaNuovaPassword() {
		return confermaNuovaPassword;
	}

	public void setConfermaNuovaPassword(String confermaNuovaPassword) {
		this.confermaNuovaPassword = confermaNuovaPassword;
	}

}
