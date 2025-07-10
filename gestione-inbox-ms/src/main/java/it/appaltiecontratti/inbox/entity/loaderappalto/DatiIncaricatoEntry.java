package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiIncaricatoEntry {

	private String sezione;
	private Long idRuolo;
	private String cigProgEsterna;
	private Date dataAffProgEsterna;
	private Date dataConsProgEsterna;
	private String codiceFiscaleResponsabile;
	private String pIvaTec;

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getCigProgEsterna() {
		return cigProgEsterna;
	}

	public void setCigProgEsterna(String cigProgEsterna) {
		this.cigProgEsterna = cigProgEsterna;
	}

	public Date getDataAffProgEsterna() {
		return dataAffProgEsterna;
	}

	public void setDataAffProgEsterna(Date dataAffProgEsterna) {
		this.dataAffProgEsterna = dataAffProgEsterna;
	}

	public Date getDataConsProgEsterna() {
		return dataConsProgEsterna;
	}

	public void setDataConsProgEsterna(Date dataConsProgEsterna) {
		this.dataConsProgEsterna = dataConsProgEsterna;
	}

	public String getCodiceFiscaleResponsabile() {
		return codiceFiscaleResponsabile;
	}

	public void setCodiceFiscaleResponsabile(String codiceFiscaleResponsabile) {
		this.codiceFiscaleResponsabile = codiceFiscaleResponsabile;
	}

	public String getpIvaTec() {
		return pIvaTec;
	}

	public void setpIvaTec(String pIvaTec) {
		this.pIvaTec = pIvaTec;
	}

}
