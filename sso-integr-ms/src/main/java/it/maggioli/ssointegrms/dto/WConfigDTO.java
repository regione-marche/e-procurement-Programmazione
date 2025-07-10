package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WConfigDTO implements Serializable {

	private static final long serialVersionUID = -2020710331026341945L;

	private String codApp;
	private String chiave;
	private String valore;
	private String sezione;
	private String descrizione;
	private String criptato;

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

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCriptato() {
		return criptato;
	}

	public void setCriptato(String criptato) {
		this.criptato = criptato;
	}

}
