package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class GaraBaseEntry {

	@ApiModelProperty(value = "codice univoco della gara")
	private Long codgara;
	@ApiModelProperty(value = "oggetto di gara")
	private String oggetto;
	@ApiModelProperty(value = "situazione di gara")
	private Long situazione;
	@ApiModelProperty(value = "tipologia di gara")
	private Long tipoApp;
	@ApiModelProperty(value = "importo della gara")
	private double importoGara;
	@ApiModelProperty(value = "flag cancellazione abilitata/disabilitata")
	private boolean deletable;
	@ApiModelProperty(value = "numero ANAC della gara")
	private String identificativoGara;
	@ApiModelProperty(value = "Codici CIG dei lotti della gara")
	private String cigLotti;
	@ApiModelProperty(value = "RUP della gara")
	private String rup;
	@ApiModelProperty(value = "Nominativo RUP della gara")
	private String descRup;
	@ApiModelProperty(value = "flag trasferimento RUP abilitato/disabilitato")
	private boolean trasferimentoRUP;
	@ApiModelProperty(value = "flag che identifica se la gara è uno SMART CIG")
	private boolean smartCig;
	@ApiModelProperty(value = "Data pubblicazione gara")
	private Date dataPubblicazione;
	private String idAppalto;
	private Long versioneSimog;

	public Long getCodgara() {
		return codgara;
	}

	public void setCodgara(Long codgara) {
		this.codgara = codgara;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Long getSituazione() {
		return situazione;
	}

	public void setSituazione(Long situazione) {
		this.situazione = situazione;
	}

	public Long getTipoApp() {
		return tipoApp;
	}

	public void setTipoApp(Long tipoApp) {
		this.tipoApp = tipoApp;
	}

	public double getImportoGara() {
		return importoGara;
	}

	public void setImportoGara(double importoGara) {
		this.importoGara = importoGara;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public String getIdentificativoGara() {
		return identificativoGara;
	}

	public void setIdentificativoGara(String identificativoGara) {
		this.identificativoGara = identificativoGara;
	}

	public String getCigLotti() {
		return cigLotti;
	}

	public void setCigLotti(String cigLotti) {
		this.cigLotti = cigLotti;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public boolean isTrasferimentoRUP() {
		return trasferimentoRUP;
	}

	public void setTrasferimentoRUP(boolean trasferimentoRUP) {
		this.trasferimentoRUP = trasferimentoRUP;
	}

	public boolean isSmartCig() {
		return smartCig;
	}

	public void setSmartCig(boolean smartCig) {
		this.smartCig = smartCig;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public String getDescRup() {
		return descRup;
	}

	public void setDescRup(String descRup) {
		this.descRup = descRup;
	}

	public String getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(String idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getVersioneSimog() {
		return versioneSimog;
	}

	public void setVersioneSimog(Long versioneSimog) {
		this.versioneSimog = versioneSimog;
	}

	

}
