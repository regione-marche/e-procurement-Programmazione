package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class TabellatoIstatEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241410931174930670L;

	@ApiModelProperty(name = "code", value = "Codice tabellato")
	private String codice;
	@ApiModelProperty(name = "value", value = "Descrizione tabellato")
	private String descrizione;
	@ApiModelProperty(name = "codistat", value = "codice istat di appartenenza")
	private String codistat;
	@ApiModelProperty(name = "cap", value = "CAP appartenenza")
	private String cap;
	@ApiModelProperty(name = "archiviato", value = "flag che stabilisce se il valore tabellato è ancora valido oppure no")
	private String archiviato;
	@ApiModelProperty(name = "codiceProvincia", value = "Codice di due lettere della provincia associata al comune")
	private String codiceProvincia;

	public TabellatoIstatEntry() {
	}

	/**
	 * @return Ritorna codice.
	 */
	public String getCodice() {
		return codice;
	}

	/**
	 * @param codice codice da settare internamente alla classe.
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 * @return Ritorna descrizione.
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione descrizione da settare internamente alla classe.
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodistat() {
		return codistat;
	}

	public void setCodistat(String codistat) {
		this.codistat = codistat;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getArchiviato() {
		return archiviato;
	}

	public void setArchiviato(String archiviato) {
		this.archiviato = archiviato;
	}

	public String getCodiceProvincia() {
		return codiceProvincia;
	}

	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}

}