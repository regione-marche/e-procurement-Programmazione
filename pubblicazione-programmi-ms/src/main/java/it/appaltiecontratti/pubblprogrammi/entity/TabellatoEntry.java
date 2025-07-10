package it.appaltiecontratti.pubblprogrammi.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * Dati codice - valore.
 * 
 * @author Michele.DiNapoli
 */
public class TabellatoEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241410931174930670L;

	@XmlElement(name = "code")
	@ApiModelProperty(name = "code", value="Codice tabellato")  
	private String codice;
	@XmlElement(name = "value")
	@ApiModelProperty(name = "value", value="Descrizione tabellato")  
	private String descrizione;

	public TabellatoEntry() {
		;
	}

	/**
	 * @return Ritorna codice.
	 */
	public String getCodice() {
		return codice;
	}

	/**
	 * @param codice
	 *            codice da settare internamente alla classe.
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
	 * @param descrizione
	 *            descrizione da settare internamente alla classe.
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
