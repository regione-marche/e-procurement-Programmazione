package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiRitardoEntry {

	private Date dataTermine;
	private Date dataConsegna;
	private String tipoComun;
	private Long durataSosp;
	private String motivoSosp;
	private Date dataIstRecesso;
	private String flagAccolta;
	private String flagTardiva;
	private String flagRipresa;
	private String flagRiserva;
	private Double importoSpese;
	private Double importoOneri;
	private String idSchedaLocale;
	private String idSchedaSimog;

	public Date getDataTermine() {
		return dataTermine;
	}

	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public String getTipoComun() {
		return tipoComun;
	}

	public void setTipoComun(String tipoComun) {
		this.tipoComun = tipoComun;
	}

	public Long getDurataSosp() {
		return durataSosp;
	}

	public void setDurataSosp(Long durataSosp) {
		this.durataSosp = durataSosp;
	}

	public String getMotivoSosp() {
		return motivoSosp;
	}

	public void setMotivoSosp(String motivoSosp) {
		this.motivoSosp = motivoSosp;
	}

	public Date getDataIstRecesso() {
		return dataIstRecesso;
	}

	public void setDataIstRecesso(Date dataIstRecesso) {
		this.dataIstRecesso = dataIstRecesso;
	}

	public String getFlagAccolta() {
		return flagAccolta;
	}

	public void setFlagAccolta(String flagAccolta) {
		this.flagAccolta = flagAccolta;
	}

	public String getFlagTardiva() {
		return flagTardiva;
	}

	public void setFlagTardiva(String flagTardiva) {
		this.flagTardiva = flagTardiva;
	}

	public String getFlagRipresa() {
		return flagRipresa;
	}

	public void setFlagRipresa(String flagRipresa) {
		this.flagRipresa = flagRipresa;
	}

	public String getFlagRiserva() {
		return flagRiserva;
	}

	public void setFlagRiserva(String flagRiserva) {
		this.flagRiserva = flagRiserva;
	}

	public Double getImportoSpese() {
		return importoSpese;
	}

	public void setImportoSpese(Double importoSpese) {
		this.importoSpese = importoSpese;
	}

	public Double getImportoOneri() {
		return importoOneri;
	}

	public void setImportoOneri(Double importoOneri) {
		this.importoOneri = importoOneri;
	}

	public String getIdSchedaLocale() {
		return idSchedaLocale;
	}

	public void setIdSchedaLocale(String idSchedaLocale) {
		this.idSchedaLocale = idSchedaLocale;
	}

	public String getIdSchedaSimog() {
		return idSchedaSimog;
	}

	public void setIdSchedaSimog(String idSchedaSimog) {
		this.idSchedaSimog = idSchedaSimog;
	}

}
