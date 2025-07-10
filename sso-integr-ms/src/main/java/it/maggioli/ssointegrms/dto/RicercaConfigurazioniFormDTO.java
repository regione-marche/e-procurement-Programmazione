package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaConfigurazioniFormDTO extends BaseSearchFormDTO implements Serializable {

	private static final long serialVersionUID = -5913882024853723712L;

	private String sezione;
	private String chiave;
	private String valore;
	private String descrizione;

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
