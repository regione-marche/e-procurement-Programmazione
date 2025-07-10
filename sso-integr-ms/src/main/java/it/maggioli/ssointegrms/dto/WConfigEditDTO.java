package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WConfigEditDTO implements Serializable {

	private static final long serialVersionUID = -317358174595442894L;

	@NotBlank
	private String codApp;
	@NotBlank
	private String chiave;
	@Size(min = 0, max = 500)
	private String valore;

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}
