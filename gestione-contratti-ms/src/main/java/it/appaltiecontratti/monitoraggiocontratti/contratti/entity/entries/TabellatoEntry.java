package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class TabellatoEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241410931174930670L;

	@ApiModelProperty(name = "code", value="Codice tabellato")  
	private Long codice;
	@ApiModelProperty(name = "value", value="Descrizione tabellato")  
	private String descrizione;
	@ApiModelProperty(name = "archiviato", value="flag che stabilisce se il valore tabellato è ancora valido oppure no")  
	private Long archiviato;
	

	/**
	 * @return Ritorna codice.
	 */
	public Long getCodice() {
		return codice;
	}

	/**
	 * @param codice
	 *            codice da settare internamente alla classe.
	 */
	public void setCodice(Long codice) {
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

	public Long getArchiviato() {
		return archiviato;
	}

	public void setArchiviato(Long archiviato) {
		this.archiviato = archiviato;
	}
	
	

	
}