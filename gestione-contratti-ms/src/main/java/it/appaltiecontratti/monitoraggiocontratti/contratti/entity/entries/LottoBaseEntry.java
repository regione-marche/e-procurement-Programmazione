package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class LottoBaseEntry {

	@ApiModelProperty(value = "codice gara del lotto")
	private String codgara;
	@ApiModelProperty(value = "codice univioco del lotto per la gara")
	private String codLotto;
	@ApiModelProperty(value = "cig del lotto")
	private String cig;
	@ApiModelProperty(value = "oggetto del lotto")
	private String oggetto;
	@ApiModelProperty(value = "situazione del lotto")
	private Long situazione;
	@ApiModelProperty(value = "tipologia del lotto")
	private String tipologia;
	@ApiModelProperty(value = "importo netto del lotto")
	private Double importoNetto;
	@ApiModelProperty(value = "flag cancellazione abilitata/disabilitata")
	private boolean deletable;
	@ApiModelProperty(value = "cig master in caso di lotti accorpati")
	private String masterCig;
	@ApiModelProperty(value = "Numero lotto")
	private Long numLotto;
	private Double importoTotale;
	@ApiModelProperty(value = "Numero fasi lotto")
	private Long numFasi;
	@ApiModelProperty(value = "Numero pubblicazioni atti lotto")
	private Long numPubblicazioneAtti;
	private List<LottoBaseEntry> lottiAccorpati;

	public String getCodgara() {
		return codgara;
	}

	public void setCodgara(String codgara) {
		this.codgara = codgara;
	}

	public String getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(String codLotto) {
		this.codLotto = codLotto;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
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

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public Double getImportoNetto() {
		return importoNetto;
	}

	public void setImportoNetto(Double importoNetto) {
		this.importoNetto = importoNetto;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public String getMasterCig() {
		return masterCig;
	}

	public void setMasterCig(String masterCig) {
		this.masterCig = masterCig;
	}

	public List<LottoBaseEntry> getLottiAccorpati() {
		return lottiAccorpati;
	}

	public void setLottiAccorpati(List<LottoBaseEntry> lottiAccorpati) {
		this.lottiAccorpati = lottiAccorpati;
	}

	public Long getNumLotto() {
		return numLotto;
	}

	public void setNumLotto(Long numLotto) {
		this.numLotto = numLotto;
	}

	public Double getImportoTotale() {
		return importoTotale;
	}

	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}

	public Long getNumFasi() {
		return numFasi;
	}

	public void setNumFasi(Long numFasi) {
		this.numFasi = numFasi;
	}

	public Long getNumPubblicazioneAtti() {
		return numPubblicazioneAtti;
	}

	public void setNumPubblicazioneAtti(Long numPubblicazioneAtti) {
		this.numPubblicazioneAtti = numPubblicazioneAtti;
	}
	
	

}
