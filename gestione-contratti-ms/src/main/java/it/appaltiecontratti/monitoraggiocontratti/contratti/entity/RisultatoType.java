package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import java.io.Serializable;



public class RisultatoType implements Serializable{
	  private CodiceRisultatoType codice;

	  private DescrizioneRisultatoType descrizione;

	  private String idTransazione;

	public CodiceRisultatoType getCodice() {
		return codice;
	}

	public void setCodice(CodiceRisultatoType codice) {
		this.codice = codice;
	}

	public DescrizioneRisultatoType getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(DescrizioneRisultatoType descrizione) {
		this.descrizione = descrizione;
	}

	public String getIdTransazione() {
		return idTransazione;
	}

	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}
	  
	  
}
