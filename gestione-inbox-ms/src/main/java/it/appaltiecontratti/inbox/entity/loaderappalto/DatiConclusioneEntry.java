package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiConclusioneEntry {

	private Long motivoInterr;
	private Long motivoRisol;
	private Date dataRisol;
	private String flagOneri;
	private Double oneriRisoluzione;
	private String flagPolizza;
	private Date dataUltimazione;
	private Long numInfortuni;
	private Long numInfPerm;
	private Long numInfortMortali;
	private Date termineContrattoUltimo;
	private Long numGiorniProroga;
	private String idSchedaLocale;
	private String idSchedaSimog;
    private Date dataVerbale;
	
	public Long getMotivoInterr() {
		return motivoInterr;
	}

	public void setMotivoInterr(Long motivoInterr) {
		this.motivoInterr = motivoInterr;
	}

	public Long getMotivoRisol() {
		return motivoRisol;
	}

	public void setMotivoRisol(Long motivoRisol) {
		this.motivoRisol = motivoRisol;
	}

	public Date getDataRisol() {
		return dataRisol;
	}

	public void setDataRisol(Date dataRisol) {
		this.dataRisol = dataRisol;
	}

	public String getFlagOneri() {
		return flagOneri;
	}

	public void setFlagOneri(String flagOneri) {
		this.flagOneri = flagOneri;
	}

	public Double getOneriRisoluzione() {
		return oneriRisoluzione;
	}

	public void setOneriRisoluzione(Double oneriRisoluzione) {
		this.oneriRisoluzione = oneriRisoluzione;
	}

	public String getFlagPolizza() {
		return flagPolizza;
	}

	public void setFlagPolizza(String flagPolizza) {
		this.flagPolizza = flagPolizza;
	}

	public Date getDataUltimazione() {
		return dataUltimazione;
	}

	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}

	public Long getNumInfortuni() {
		return numInfortuni;
	}

	public void setNumInfortuni(Long numInfortuni) {
		this.numInfortuni = numInfortuni;
	}

	public Long getNumInfPerm() {
		return numInfPerm;
	}

	public void setNumInfPerm(Long numInfPerm) {
		this.numInfPerm = numInfPerm;
	}

	public Long getNumInfortMortali() {
		return numInfortMortali;
	}

	public void setNumInfortMortali(Long numInfortMortali) {
		this.numInfortMortali = numInfortMortali;
	}

	public Date getTermineContrattoUltimo() {
		return termineContrattoUltimo;
	}

	public void setTermineContrattoUltimo(Date termineContrattoUltimo) {
		this.termineContrattoUltimo = termineContrattoUltimo;
	}

	public Long getNumGiorniProroga() {
		return numGiorniProroga;
	}

	public void setNumGiorniProroga(Long numGiorniProroga) {
		this.numGiorniProroga = numGiorniProroga;
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

	public Date getDataVerbale() {
		return dataVerbale;
	}

	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}

}
