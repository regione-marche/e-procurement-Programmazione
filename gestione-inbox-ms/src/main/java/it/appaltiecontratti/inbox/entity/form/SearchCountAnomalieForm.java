package it.appaltiecontratti.inbox.entity.form;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchCountAnomalieForm {

    private String stazioneAppaltante;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date dataInvioDa; 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date dataInvioA;
    private String cig;
    private String codiceAnomalia;
    private String descrizioneAnomalia;
    
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
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
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getCodiceAnomalia() {
		return codiceAnomalia;
	}
	public void setCodiceAnomalia(String codiceAnomalia) {
		this.codiceAnomalia = codiceAnomalia;
	}
	public String getDescrizioneAnomalia() {
		return descrizioneAnomalia;
	}
	public void setDescrizioneAnomalia(String descrizioneAnomalia) {
		this.descrizioneAnomalia = descrizioneAnomalia;
	}
    
}
