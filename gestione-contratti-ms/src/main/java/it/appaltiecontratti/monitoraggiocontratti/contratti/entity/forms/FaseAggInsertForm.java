package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseAggInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	@XSSValidation
	private String codStrumento;
	private Double importoLavori;
	private Double importoServizi;
	private Double importoForniture;
	private Double importosubtotale;
	private Double importoSicurezza;
	private Double importoProgettazione;
	private Double impNonAssog;
	private Double importoComplAppalto;
	private Double iva;
	private Double importoIva;
	private Double altreSomme;
	private Double importoDisposizione;
	private Double importoComplIntervento;
	@XSSValidation
	private String opereUrbanizScomputo;
	private Long modalitaRiaggiudicazione;
	private Long requisitiSettSpec;
	@XSSValidation
	private String proceduraAcc;
	@XSSValidation
	private String preinformazione;
	@XSSValidation
	private String termineRidotto;
	private Long idIndizione;
	private Date dataManifestInteresse;
	private Long numManifestInteresse;
	private Date dataScadRichiestaInvito;
	private Long numImpreseRichiedenti;
	private Date dataInvito;
	private Long numImpreseInvitate;
	private Date dataScadenzaPresOfferta;
	private Long numImpreseOfferenti;
	private Long numOfferteAmmesse;
	private Long numOfferteEscluse;
	private Long impEsclusInsuffGiust;
	private Double offertaMassima;
	private Double offertaMinima;
	private Double valoreSogliaAnomalia;
	private Long numOfferteFuoriSoglia;
	@XSSValidation
	private String astaElettronica;
	private Double percentRibassoAgg;
	private Double percOffAumento;
	private Double importoAggiudicazione;
	private Date dataVerbAggiudicazione;
	private Long tipoAtto;
	private Date dataAtto;
	@XSSValidation
	private String numeroAtto;
	@XSSValidation
	private String flagRichSubappalto;
	@XSSValidation
	private String flagSubappFinReg;
	@XSSValidation
	private String flagImporti;
	@XSSValidation
	private String flagSicurezza;
	@XSSValidation
	private String flagLivelloProgettuale;
	@XSSValidation
	private String verificaCampione;
	private Long num;
	@XSSValidation
	private String flagRelazioneUnica;
	private Double importoSommeOpzioniRinnovi;
	private Double importoSommeripetizioni;

	@JsonIgnore
	private String cui;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public String getCodStrumento() {
		return codStrumento;
	}

	public void setCodStrumento(String codStrumento) {
		this.codStrumento = codStrumento;
	}

	public Double getImportoLavori() {
		return importoLavori;
	}

	public void setImportoLavori(Double importoLavori) {
		this.importoLavori = importoLavori;
	}

	public Double getImportoServizi() {
		return importoServizi;
	}

	public void setImportoServizi(Double importoServizi) {
		this.importoServizi = importoServizi;
	}

	public Double getImportoForniture() {
		return importoForniture;
	}

	public void setImportoForniture(Double importoForniture) {
		this.importoForniture = importoForniture;
	}

	public Double getImportosubtotale() {
		return importosubtotale;
	}

	public void setImportosubtotale(Double importosubtotale) {
		this.importosubtotale = importosubtotale;
	}

	public Double getImportoSicurezza() {
		return importoSicurezza;
	}

	public void setImportoSicurezza(Double importoSicurezza) {
		this.importoSicurezza = importoSicurezza;
	}

	public Double getImportoProgettazione() {
		return importoProgettazione;
	}

	public void setImportoProgettazione(Double importoProgettazione) {
		this.importoProgettazione = importoProgettazione;
	}

	public Double getImpNonAssog() {
		return impNonAssog;
	}

	public void setImpNonAssog(Double impNonAssog) {
		this.impNonAssog = impNonAssog;
	}

	public Double getImportoComplAppalto() {
		return importoComplAppalto;
	}

	public void setImportoComplAppalto(Double importoComplAppalto) {
		this.importoComplAppalto = importoComplAppalto;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getImportoIva() {
		return importoIva;
	}

	public void setImportoIva(Double importoIva) {
		this.importoIva = importoIva;
	}

	public Double getAltreSomme() {
		return altreSomme;
	}

	public void setAltreSomme(Double altreSomme) {
		this.altreSomme = altreSomme;
	}

	public Double getImportoDisposizione() {
		return importoDisposizione;
	}

	public void setImportoDisposizione(Double importoDisposizione) {
		this.importoDisposizione = importoDisposizione;
	}

	public Double getImportoComplIntervento() {
		return importoComplIntervento;
	}

	public void setImportoComplIntervento(Double importoComplIntervento) {
		this.importoComplIntervento = importoComplIntervento;
	}

	public String getOpereUrbanizScomputo() {
		return opereUrbanizScomputo;
	}

	public void setOpereUrbanizScomputo(String opereUrbanizScomputo) {
		this.opereUrbanizScomputo = opereUrbanizScomputo;
	}

	public Long getModalitaRiaggiudicazione() {
		return modalitaRiaggiudicazione;
	}

	public void setModalitaRiaggiudicazione(Long modalitaRiaggiudicazione) {
		this.modalitaRiaggiudicazione = modalitaRiaggiudicazione;
	}

	public Long getRequisitiSettSpec() {
		return requisitiSettSpec;
	}

	public void setRequisitiSettSpec(Long requisitiSettSpec) {
		this.requisitiSettSpec = requisitiSettSpec;
	}

	public String getProceduraAcc() {
		return proceduraAcc;
	}

	public void setProceduraAcc(String proceduraAcc) {
		this.proceduraAcc = proceduraAcc;
	}

	public String getPreinformazione() {
		return preinformazione;
	}

	public void setPreinformazione(String preinformazione) {
		this.preinformazione = preinformazione;
	}

	public String getTermineRidotto() {
		return termineRidotto;
	}

	public void setTermineRidotto(String termineRidotto) {
		this.termineRidotto = termineRidotto;
	}

	public Long getIdIndizione() {
		return idIndizione;
	}

	public void setIdIndizione(Long idIndizione) {
		this.idIndizione = idIndizione;
	}

	public Date getDataManifestInteresse() {
		return dataManifestInteresse;
	}

	public void setDataManifestInteresse(Date dataManifestInteresse) {
		this.dataManifestInteresse = dataManifestInteresse;
	}

	public Long getNumManifestInteresse() {
		return numManifestInteresse;
	}

	public void setNumManifestInteresse(Long numManifestInteresse) {
		this.numManifestInteresse = numManifestInteresse;
	}

	public Date getDataScadRichiestaInvito() {
		return dataScadRichiestaInvito;
	}

	public void setDataScadRichiestaInvito(Date dataScadRichiestaInvito) {
		this.dataScadRichiestaInvito = dataScadRichiestaInvito;
	}

	public Long getNumImpreseRichiedenti() {
		return numImpreseRichiedenti;
	}

	public void setNumImpreseRichiedenti(Long numImpreseRichiedenti) {
		this.numImpreseRichiedenti = numImpreseRichiedenti;
	}

	public Date getDataInvito() {
		return dataInvito;
	}

	public void setDataInvito(Date dataInvito) {
		this.dataInvito = dataInvito;
	}

	public Long getNumImpreseInvitate() {
		return numImpreseInvitate;
	}

	public void setNumImpreseInvitate(Long numImpreseInvitate) {
		this.numImpreseInvitate = numImpreseInvitate;
	}

	public Date getDataScadenzaPresOfferta() {
		return dataScadenzaPresOfferta;
	}

	public void setDataScadenzaPresOfferta(Date dataScadenzaPresOfferta) {
		this.dataScadenzaPresOfferta = dataScadenzaPresOfferta;
	}

	public Long getNumImpreseOfferenti() {
		return numImpreseOfferenti;
	}

	public void setNumImpreseOfferenti(Long numImpreseOfferenti) {
		this.numImpreseOfferenti = numImpreseOfferenti;
	}

	public Long getNumOfferteAmmesse() {
		return numOfferteAmmesse;
	}

	public void setNumOfferteAmmesse(Long numOfferteAmmesse) {
		this.numOfferteAmmesse = numOfferteAmmesse;
	}

	public Long getNumOfferteEscluse() {
		return numOfferteEscluse;
	}

	public void setNumOfferteEscluse(Long numOfferteEscluse) {
		this.numOfferteEscluse = numOfferteEscluse;
	}

	public Long getImpEsclusInsuffGiust() {
		return impEsclusInsuffGiust;
	}

	public void setImpEsclusInsuffGiust(Long impEsclusInsuffGiust) {
		this.impEsclusInsuffGiust = impEsclusInsuffGiust;
	}

	public Double getOffertaMassima() {
		return offertaMassima;
	}

	public void setOffertaMassima(Double offertaMassima) {
		this.offertaMassima = offertaMassima;
	}

	public Double getOffertaMinima() {
		return offertaMinima;
	}

	public void setOffertaMinima(Double offertaMinima) {
		this.offertaMinima = offertaMinima;
	}

	public Double getValoreSogliaAnomalia() {
		return valoreSogliaAnomalia;
	}

	public void setValoreSogliaAnomalia(Double valoreSogliaAnomalia) {
		this.valoreSogliaAnomalia = valoreSogliaAnomalia;
	}

	public Long getNumOfferteFuoriSoglia() {
		return numOfferteFuoriSoglia;
	}

	public void setNumOfferteFuoriSoglia(Long numOfferteFuoriSoglia) {
		this.numOfferteFuoriSoglia = numOfferteFuoriSoglia;
	}

	public String getAstaElettronica() {
		return astaElettronica;
	}

	public void setAstaElettronica(String astaElettronica) {
		this.astaElettronica = astaElettronica;
	}

	public Double getPercentRibassoAgg() {
		return percentRibassoAgg;
	}

	public void setPercentRibassoAgg(Double percentRibassoAgg) {
		this.percentRibassoAgg = percentRibassoAgg;
	}

	public Double getPercOffAumento() {
		return percOffAumento;
	}

	public void setPercOffAumento(Double percOffAumento) {
		this.percOffAumento = percOffAumento;
	}

	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Date getDataVerbAggiudicazione() {
		return dataVerbAggiudicazione;
	}

	public void setDataVerbAggiudicazione(Date dataVerbAggiudicazione) {
		this.dataVerbAggiudicazione = dataVerbAggiudicazione;
	}

	public Long getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(Long tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getFlagRichSubappalto() {
		return flagRichSubappalto;
	}

	public void setFlagRichSubappalto(String flagRichSubappalto) {
		this.flagRichSubappalto = flagRichSubappalto;
	}

	public String getFlagSubappFinReg() {
		return flagSubappFinReg;
	}

	public void setFlagSubappFinReg(String flagSubappFinReg) {
		this.flagSubappFinReg = flagSubappFinReg;
	}

	public String getFlagImporti() {
		return flagImporti;
	}

	public void setFlagImporti(String flagImporti) {
		this.flagImporti = flagImporti;
	}

	public String getFlagSicurezza() {
		return flagSicurezza;
	}

	public void setFlagSicurezza(String flagSicurezza) {
		this.flagSicurezza = flagSicurezza;
	}

	public String getFlagLivelloProgettuale() {
		return flagLivelloProgettuale;
	}

	public void setFlagLivelloProgettuale(String flagLivelloProgettuale) {
		this.flagLivelloProgettuale = flagLivelloProgettuale;
	}

	public String getVerificaCampione() {
		return verificaCampione;
	}

	public void setVerificaCampione(String verificaCampione) {
		this.verificaCampione = verificaCampione;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getFlagRelazioneUnica() {
		return flagRelazioneUnica;
	}

	public void setFlagRelazioneUnica(String flagRelazioneUnica) {
		this.flagRelazioneUnica = flagRelazioneUnica;
	}

	public Double getImportoSommeOpzioniRinnovi() {
		return importoSommeOpzioniRinnovi;
	}

	public void setImportoSommeOpzioniRinnovi(Double importoSommeOpzioniRinnovi) {
		this.importoSommeOpzioniRinnovi = importoSommeOpzioniRinnovi;
	}

	public Double getImportoSommeripetizioni() {
		return importoSommeripetizioni;
	}

	public void setImportoSommeripetizioni(Double importoSommeripetizioni) {
		this.importoSommeripetizioni = importoSommeripetizioni;
	}

}
