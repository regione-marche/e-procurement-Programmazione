package it.appaltiecontratti.inbox.entity.form;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RisolviAnomalieForm {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date dataInvioDa;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date dataInvioA;
    private String[] erroriSelezionati;
	public Date getDataInvioDa() {
		return dataInvioDa;
	}
	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}
	public Date getDataInvioA() {
		return dataInvioA;
	}
	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}
	public String[] getErroriSelezionati() {
		return erroriSelezionati;
	}
	public void setErroriSelezionati(String[] erroriSelezionati) {
		this.erroriSelezionati = erroriSelezionati;
	}

    
}
