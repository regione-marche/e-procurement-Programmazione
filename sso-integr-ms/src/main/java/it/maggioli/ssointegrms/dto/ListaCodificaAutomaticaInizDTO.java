package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ListaCodificaAutomaticaInizDTO implements Serializable {

	private static final long serialVersionUID = 1275062768348588679L;

	private String codiceApplicazione;
	private String titoloApplicazione;

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public String getTitoloApplicazione() {
		return titoloApplicazione;
	}

	public void setTitoloApplicazione(String titoloApplicazione) {
		this.titoloApplicazione = titoloApplicazione;
	}

}
