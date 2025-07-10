package it.appaltiecontratti.inbox.entity.contratti;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class PubblicitaGaraEntry {

	@NotNull
	private Long codiceGara;
	private Date gazzettaUffEuropea;
	private Date gazzettaUffItaliana;
	private Date alboPretorioComuni;
	private Long numQuotidianiNazionali;
	private Long numQuotidianiLocali;
	private String profiloCommittente;
	private String sitoMinisteriInfr;
	private String sitoOsservatorioContratti;
	private Date dataBollettino;
	private Long numPeriodici;
	
	public Long getCodiceGara() {
		return codiceGara;
	}
	public void setCodiceGara(Long codiceGara) {
		this.codiceGara = codiceGara;
	}
	public Date getGazzettaUffEuropea() {
		return gazzettaUffEuropea;
	}
	public void setGazzettaUffEuropea(Date gazzettaUffEuropea) {
		this.gazzettaUffEuropea = gazzettaUffEuropea;
	}
	public Date getGazzettaUffItaliana() {
		return gazzettaUffItaliana;
	}
	public void setGazzettaUffItaliana(Date gazzettaUffItaliana) {
		this.gazzettaUffItaliana = gazzettaUffItaliana;
	}
	public Date getAlboPretorioComuni() {
		return alboPretorioComuni;
	}
	public void setAlboPretorioComuni(Date alboPretorioComuni) {
		this.alboPretorioComuni = alboPretorioComuni;
	}
	public Long getNumQuotidianiNazionali() {
		return numQuotidianiNazionali;
	}
	public void setNumQuotidianiNazionali(Long numQuotidianiNazionali) {
		this.numQuotidianiNazionali = numQuotidianiNazionali;
	}
	public Long getNumQuotidianiLocali() {
		return numQuotidianiLocali;
	}
	public void setNumQuotidianiLocali(Long numQuotidianiLocali) {
		this.numQuotidianiLocali = numQuotidianiLocali;
	}
	public String getProfiloCommittente() {
		return profiloCommittente;
	}
	public void setProfiloCommittente(String profiloCommittente) {
		this.profiloCommittente = profiloCommittente;
	}
	public String getSitoMinisteriInfr() {
		return sitoMinisteriInfr;
	}
	public void setSitoMinisteriInfr(String sitoMinisteriInfr) {
		this.sitoMinisteriInfr = sitoMinisteriInfr;
	}
	public String getSitoOsservatorioContratti() {
		return sitoOsservatorioContratti;
	}
	public void setSitoOsservatorioContratti(String sitoOsservatorioContratti) {
		this.sitoOsservatorioContratti = sitoOsservatorioContratti;
	}
	public Date getDataBollettino() {
		return dataBollettino;
	}
	public void setDataBollettino(Date dataBollettino) {
		this.dataBollettino = dataBollettino;
	}
	public Long getNumPeriodici() {
		return numPeriodici;
	}
	public void setNumPeriodici(Long numPeriodici) {
		this.numPeriodici = numPeriodici;
	}

}