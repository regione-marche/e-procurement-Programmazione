package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserInsertConfigurationDTO implements Serializable {

	private static final long serialVersionUID = -7176364165118827484L;

	private boolean ufficioAppartenenzaObbligatorio;

	public boolean isUfficioAppartenenzaObbligatorio() {
		return ufficioAppartenenzaObbligatorio;
	}

	public void setUfficioAppartenenzaObbligatorio(boolean ufficioAppartenenzaObbligatorio) {
		this.ufficioAppartenenzaObbligatorio = ufficioAppartenenzaObbligatorio;
	}

}
