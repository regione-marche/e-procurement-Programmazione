package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaEventiFormDTO extends BaseSearchFormDTO implements Serializable {

	private static final long serialVersionUID = -6739955633624409267L;

	private Long idEvento;
	private Date dataOraDa;
	private Date dataOraA;
	private String descrizioneUtente;
	private String livelloEvento;
	private String codiceEvento;
	private String oggettoEvento;
	private String descrizione;

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Date getDataOraDa() {
		return dataOraDa;
	}

	public void setDataOraDa(Date dataOraDa) {
		this.dataOraDa = dataOraDa;
	}

	public Date getDataOraA() {
		return dataOraA;
	}

	public void setDataOraA(Date dataOraA) {
		this.dataOraA = dataOraA;
	}

	public String getDescrizioneUtente() {
		return descrizioneUtente;
	}

	public void setDescrizioneUtente(String descrizioneUtente) {
		this.descrizioneUtente = descrizioneUtente;
	}

	public String getLivelloEvento() {
		return livelloEvento;
	}

	public void setLivelloEvento(String livelloEvento) {
		this.livelloEvento = livelloEvento;
	}

	public String getCodiceEvento() {
		return codiceEvento;
	}

	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}

	public String getOggettoEvento() {
		return oggettoEvento;
	}

	public void setOggettoEvento(String oggettoEvento) {
		this.oggettoEvento = oggettoEvento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "RicercaEventiFormDTO [idEvento=" + idEvento + ", dataOraDa=" + dataOraDa + ", dataOraA=" + dataOraA
				+ ", descrizioneUtente=" + descrizioneUtente + ", livelloEvento=" + livelloEvento + ", codiceEvento="
				+ codiceEvento + ", oggettoEvento=" + oggettoEvento + ", descrizione=" + descrizione + "]";
	}

}
