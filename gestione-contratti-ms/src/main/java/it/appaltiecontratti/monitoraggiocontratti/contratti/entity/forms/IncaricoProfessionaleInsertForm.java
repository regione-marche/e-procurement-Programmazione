package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

public class IncaricoProfessionaleInsertForm {
	
	private String sezione;
	private Long idRuolo;
	private String cigProgEsterna;
	private Date dataAffProgEsterna;
	private Date dataConsProgEsterna;
	private String codiceTecnico;
	private Long tipoProgettazione;
	
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
	public String getCodiceTecnico() {
		return codiceTecnico;
	}
	public void setCodiceTecnico(String codiceTecnico) {
		this.codiceTecnico = codiceTecnico;
	}
	public Long getTipoProgettazione() {
		return tipoProgettazione;
	}
	public void setTipoProgettazione(Long tipoProgettazione) {
		this.tipoProgettazione = tipoProgettazione;
	}

}
