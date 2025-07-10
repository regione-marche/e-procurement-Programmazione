package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseCollaudoEntry extends FaseBaseEntry{

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataCertEsecuzione;
	private Date dataCollaudoStatico;
	private Long modalitaCollaudo;
	private Date dataNomina;
	private Date dataInizioOperazioni;
	private Date dataRedazioneCertificato;
	private Date dataDelibera;
	private String esitoCollaudo;
	private Double importoFinaleLavori;
	private Double importoFinaleServizi;
	private Double importoFinaleForniture;
	private Double importoSubtotale;
	private Double importoFinaleSicurezza;
	private Double importoProgettazione;
	private Double importoComplessivoAppalto;
	private Double importoDisposizione;
	private Double importoComplessivoIntervento;
	private String lavoriEstesi;
	private Double importoEventualeDefAmm;
	private Long numeroRiserveDefiniteAmm;
	private Long numeroRiserveDaDefinireAmm;
	private Double importoTotaleRichiestoAmm;
	private Double importoEventualeDefArb;
	private Long numeroRiserveDefiniteArb;
	private Long numeroRiserveDaDefinireArb;
	private Double importoTotaleRichiestoArb;
	private Double importoEventualeDefGiu;
	private Long numeroRiserveDefiniteGiu;
	private Long numeroRiserveDaDefinireGiu;
	private Double importoTotaleRichiestoGiu;
	private Double importoEventualeDefTra;
	private Long numeroRiserveDefiniteTra;
	private Long numeroRiserveDaDefinireTra;
	private Double importoTotaleRichiestoTra;
	
	private String flagSingoloCommissione;
	private Date dataApprovazione;
	private String flagImporti;
	private String flagSicurezza;
	private String flagSubappaltatori;
	private Date dataInizioRiserveAmm;
	private Date dataFineRiserveAmm;
	private Date dataInizioRiserveArb;
	private Date dataFineRiserveArb;
	private Date dataInizioRiserveGiu;
	private Date dataFineRiserveGiu;
	private Date dataInizioRiserveTra;
	private Date dataFineRiserveTra;
	private boolean pubblicata;
	private Long tipoCollaudo;
	private Double impNonAssog;
	private Double impFinaleOpzioni;
	private Double impFinaleRipetizioni;	
	private Long numRiserve;
	private Double oneriDerivanti;
	private Long numAppa;
	private Double scostImpPrev;
	
	public Long getNumRiserve() {
		return numRiserve;
	}
	public void setNumRiserve(Long numRiserve) {
		this.numRiserve = numRiserve;
	}
	public Double getOneriDerivanti() {
		return oneriDerivanti;
	}
	public void setOneriDerivanti(Double oneriDerivanti) {
		this.oneriDerivanti = oneriDerivanti;
	}
	public Double getImpNonAssog() {
		return impNonAssog;
	}
	public void setImpNonAssog(Double impNonAssog) {
		this.impNonAssog = impNonAssog;
	}
	public Double getImpFinaleOpzioni() {
		return impFinaleOpzioni;
	}
	public void setImpFinaleOpzioni(Double impFinaleOpzioni) {
		this.impFinaleOpzioni = impFinaleOpzioni;
	}
	public Double getImpFinaleRipetizioni() {
		return impFinaleRipetizioni;
	}
	public void setImpFinaleRipetizioni(Double impFinaleRipetizioni) {
		this.impFinaleRipetizioni = impFinaleRipetizioni;
	}	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
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
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Date getDataCertEsecuzione() {
		return dataCertEsecuzione;
	}
	public void setDataCertEsecuzione(Date dataCertEsecuzione) {
		this.dataCertEsecuzione = dataCertEsecuzione;
	}
	public Date getDataCollaudoStatico() {
		return dataCollaudoStatico;
	}
	public void setDataCollaudoStatico(Date dataCollaudoStatico) {
		this.dataCollaudoStatico = dataCollaudoStatico;
	}
	public Long getModalitaCollaudo() {
		return modalitaCollaudo;
	}
	public void setModalitaCollaudo(Long modalitaCollaudo) {
		this.modalitaCollaudo = modalitaCollaudo;
	}
	public Date getDataNomina() {
		return dataNomina;
	}
	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}
	public Date getDataInizioOperazioni() {
		return dataInizioOperazioni;
	}
	public void setDataInizioOperazioni(Date dataInizioOperazioni) {
		this.dataInizioOperazioni = dataInizioOperazioni;
	}
	public Date getDataRedazioneCertificato() {
		return dataRedazioneCertificato;
	}
	public void setDataRedazioneCertificato(Date dataRedazioneCertificato) {
		this.dataRedazioneCertificato = dataRedazioneCertificato;
	}
	public Date getDataDelibera() {
		return dataDelibera;
	}
	public void setDataDelibera(Date dataDelibera) {
		this.dataDelibera = dataDelibera;
	}
	public String getEsitoCollaudo() {
		return esitoCollaudo;
	}
	public void setEsitoCollaudo(String esitoCollaudo) {
		this.esitoCollaudo = esitoCollaudo;
	}
	public Double getImportoFinaleLavori() {
		return importoFinaleLavori;
	}
	public void setImportoFinaleLavori(Double importoFinaleLavori) {
		this.importoFinaleLavori = importoFinaleLavori;
	}
	public Double getImportoFinaleServizi() {
		return importoFinaleServizi;
	}
	public void setImportoFinaleServizi(Double importoFinaleServizi) {
		this.importoFinaleServizi = importoFinaleServizi;
	}
	public Double getImportoFinaleForniture() {
		return importoFinaleForniture;
	}
	public void setImportoFinaleForniture(Double importoFinaleForniture) {
		this.importoFinaleForniture = importoFinaleForniture;
	}
	public Double getImportoSubtotale() {
		return importoSubtotale;
	}
	public void setImportoSubtotale(Double importoSubtotale) {
		this.importoSubtotale = importoSubtotale;
	}
	public Double getImportoFinaleSicurezza() {
		return importoFinaleSicurezza;
	}
	public void setImportoFinaleSicurezza(Double importoFinaleSicurezza) {
		this.importoFinaleSicurezza = importoFinaleSicurezza;
	}
	public Double getImportoProgettazione() {
		return importoProgettazione;
	}
	public void setImportoProgettazione(Double importoProgettazione) {
		this.importoProgettazione = importoProgettazione;
	}
	public Double getImportoComplessivoAppalto() {
		return importoComplessivoAppalto;
	}
	public void setImportoComplessivoAppalto(Double importoComplessivoAppalto) {
		this.importoComplessivoAppalto = importoComplessivoAppalto;
	}
	public Double getImportoDisposizione() {
		return importoDisposizione;
	}
	public void setImportoDisposizione(Double importoDisposizione) {
		this.importoDisposizione = importoDisposizione;
	}
	public Double getImportoComplessivoIntervento() {
		return importoComplessivoIntervento;
	}
	public void setImportoComplessivoIntervento(Double importoComplessivoIntervento) {
		this.importoComplessivoIntervento = importoComplessivoIntervento;
	}
	public String getLavoriEstesi() {
		return lavoriEstesi;
	}
	public void setLavoriEstesi(String lavoriEstesi) {
		this.lavoriEstesi = lavoriEstesi;
	}
	public Double getImportoEventualeDefAmm() {
		return importoEventualeDefAmm;
	}
	public void setImportoEventualeDefAmm(Double importoEventualeDefAmm) {
		this.importoEventualeDefAmm = importoEventualeDefAmm;
	}
	public Long getNumeroRiserveDefiniteAmm() {
		return numeroRiserveDefiniteAmm;
	}
	public void setNumeroRiserveDefiniteAmm(Long numeroRiserveDefiniteAmm) {
		this.numeroRiserveDefiniteAmm = numeroRiserveDefiniteAmm;
	}
	public Long getNumeroRiserveDaDefinireAmm() {
		return numeroRiserveDaDefinireAmm;
	}
	public void setNumeroRiserveDaDefinireAmm(Long numeroRiserveDaDefinireAmm) {
		this.numeroRiserveDaDefinireAmm = numeroRiserveDaDefinireAmm;
	}
	public Double getImportoTotaleRichiestoAmm() {
		return importoTotaleRichiestoAmm;
	}
	public void setImportoTotaleRichiestoAmm(Double importoTotaleRichiestoAmm) {
		this.importoTotaleRichiestoAmm = importoTotaleRichiestoAmm;
	}
	public Double getImportoEventualeDefArb() {
		return importoEventualeDefArb;
	}
	public void setImportoEventualeDefArb(Double importoEventualeDefArb) {
		this.importoEventualeDefArb = importoEventualeDefArb;
	}
	public Long getNumeroRiserveDefiniteArb() {
		return numeroRiserveDefiniteArb;
	}
	public void setNumeroRiserveDefiniteArb(Long numeroRiserveDefiniteArb) {
		this.numeroRiserveDefiniteArb = numeroRiserveDefiniteArb;
	}
	public Long getNumeroRiserveDaDefinireArb() {
		return numeroRiserveDaDefinireArb;
	}
	public void setNumeroRiserveDaDefinireArb(Long numeroRiserveDaDefinireArb) {
		this.numeroRiserveDaDefinireArb = numeroRiserveDaDefinireArb;
	}
	public Double getImportoTotaleRichiestoArb() {
		return importoTotaleRichiestoArb;
	}
	public void setImportoTotaleRichiestoArb(Double importoTotaleRichiestoArb) {
		this.importoTotaleRichiestoArb = importoTotaleRichiestoArb;
	}
	public Double getImportoEventualeDefGiu() {
		return importoEventualeDefGiu;
	}
	public void setImportoEventualeDefGiu(Double importoEventualeDefGiu) {
		this.importoEventualeDefGiu = importoEventualeDefGiu;
	}
	public Long getNumeroRiserveDefiniteGiu() {
		return numeroRiserveDefiniteGiu;
	}
	public void setNumeroRiserveDefiniteGiu(Long numeroRiserveDefiniteGiu) {
		this.numeroRiserveDefiniteGiu = numeroRiserveDefiniteGiu;
	}
	public Long getNumeroRiserveDaDefinireGiu() {
		return numeroRiserveDaDefinireGiu;
	}
	public void setNumeroRiserveDaDefinireGiu(Long numeroRiserveDaDefinireGiu) {
		this.numeroRiserveDaDefinireGiu = numeroRiserveDaDefinireGiu;
	}
	public Double getImportoTotaleRichiestoGiu() {
		return importoTotaleRichiestoGiu;
	}
	public void setImportoTotaleRichiestoGiu(Double importoTotaleRichiestoGiu) {
		this.importoTotaleRichiestoGiu = importoTotaleRichiestoGiu;
	}
	/**
	 * @return the importoEventualeDefTra
	 */
	public Double getImportoEventualeDefTra() {
		return importoEventualeDefTra;
	}
	/**
	 * @param importoEventualeDefTra the importoEventualeDefTra to set
	 */
	public void setImportoEventualeDefTra(Double importoEventualeDefTra) {
		this.importoEventualeDefTra = importoEventualeDefTra;
	}
	/**
	 * @return the numeroRiserveDefiniteTra
	 */
	public Long getNumeroRiserveDefiniteTra() {
		return numeroRiserveDefiniteTra;
	}
	/**
	 * @param numeroRiserveDefiniteTra the numeroRiserveDefiniteTra to set
	 */
	public void setNumeroRiserveDefiniteTra(Long numeroRiserveDefiniteTra) {
		this.numeroRiserveDefiniteTra = numeroRiserveDefiniteTra;
	}
	/**
	 * @return the numeroRiserveDaDefinireTra
	 */
	public Long getNumeroRiserveDaDefinireTra() {
		return numeroRiserveDaDefinireTra;
	}
	/**
	 * @param numeroRiserveDaDefinireTra the numeroRiserveDaDefinireTra to set
	 */
	public void setNumeroRiserveDaDefinireTra(Long numeroRiserveDaDefinireTra) {
		this.numeroRiserveDaDefinireTra = numeroRiserveDaDefinireTra;
	}
	/**
	 * @return the importoTotaleRichiestoTra
	 */
	public Double getImportoTotaleRichiestoTra() {
		return importoTotaleRichiestoTra;
	}
	/**
	 * @param importoTotaleRichiestoTra the importoTotaleRichiestoTra to set
	 */
	public void setImportoTotaleRichiestoTra(Double importoTotaleRichiestoTra) {
		this.importoTotaleRichiestoTra = importoTotaleRichiestoTra;
	}
	/**
	 * @return the flagSingoloCommissione
	 */
	public String getFlagSingoloCommissione() {
		return flagSingoloCommissione;
	}
	/**
	 * @param flagSingoloCommissione the flagSingoloCommissione to set
	 */
	public void setFlagSingoloCommissione(String flagSingoloCommissione) {
		this.flagSingoloCommissione = flagSingoloCommissione;
	}
	/**
	 * @return the dataApprovazione
	 */
	public Date getDataApprovazione() {
		return dataApprovazione;
	}
	/**
	 * @param dataApprovazione the dataApprovazione to set
	 */
	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	/**
	 * @return the flagImporti
	 */
	public String getFlagImporti() {
		return flagImporti;
	}
	/**
	 * @param flagImporti the flagImporti to set
	 */
	public void setFlagImporti(String flagImporti) {
		this.flagImporti = flagImporti;
	}
	/**
	 * @return the flagSicurezza
	 */
	public String getFlagSicurezza() {
		return flagSicurezza;
	}
	/**
	 * @param flagSicurezza the flagSicurezza to set
	 */
	public void setFlagSicurezza(String flagSicurezza) {
		this.flagSicurezza = flagSicurezza;
	}
	/**
	 * @return the flagSubappaltatori
	 */
	public String getFlagSubappaltatori() {
		return flagSubappaltatori;
	}
	/**
	 * @param flagSubappaltatori the flagSubappaltatori to set
	 */
	public void setFlagSubappaltatori(String flagSubappaltatori) {
		this.flagSubappaltatori = flagSubappaltatori;
	}
	/**
	 * @return the dataInizioRiserveAmm
	 */
	public Date getDataInizioRiserveAmm() {
		return dataInizioRiserveAmm;
	}
	/**
	 * @param dataInizioRiserveAmm the dataInizioRiserveAmm to set
	 */
	public void setDataInizioRiserveAmm(Date dataInizioRiserveAmm) {
		this.dataInizioRiserveAmm = dataInizioRiserveAmm;
	}
	/**
	 * @return the dataFineRiserveAmm
	 */
	public Date getDataFineRiserveAmm() {
		return dataFineRiserveAmm;
	}
	/**
	 * @param dataFineRiserveAmm the dataFineRiserveAmm to set
	 */
	public void setDataFineRiserveAmm(Date dataFineRiserveAmm) {
		this.dataFineRiserveAmm = dataFineRiserveAmm;
	}
	/**
	 * @return the dataInizioRiserveArb
	 */
	public Date getDataInizioRiserveArb() {
		return dataInizioRiserveArb;
	}
	/**
	 * @param dataInizioRiserveArb the dataInizioRiserveArb to set
	 */
	public void setDataInizioRiserveArb(Date dataInizioRiserveArb) {
		this.dataInizioRiserveArb = dataInizioRiserveArb;
	}
	/**
	 * @return the dataFineRiserveArb
	 */
	public Date getDataFineRiserveArb() {
		return dataFineRiserveArb;
	}
	/**
	 * @param dataFineRiserveArb the dataFineRiserveArb to set
	 */
	public void setDataFineRiserveArb(Date dataFineRiserveArb) {
		this.dataFineRiserveArb = dataFineRiserveArb;
	}
	/**
	 * @return the dataInizioRiserveGiu
	 */
	public Date getDataInizioRiserveGiu() {
		return dataInizioRiserveGiu;
	}
	/**
	 * @param dataInizioRiserveGiu the dataInizioRiserveGiu to set
	 */
	public void setDataInizioRiserveGiu(Date dataInizioRiserveGiu) {
		this.dataInizioRiserveGiu = dataInizioRiserveGiu;
	}
	/**
	 * @return the dataFineRiserveGiu
	 */
	public Date getDataFineRiserveGiu() {
		return dataFineRiserveGiu;
	}
	/**
	 * @param dataFineRiserveGiu the dataFineRiserveGiu to set
	 */
	public void setDataFineRiserveGiu(Date dataFineRiserveGiu) {
		this.dataFineRiserveGiu = dataFineRiserveGiu;
	}
	/**
	 * @return the dataInizioRiserveTra
	 */
	public Date getDataInizioRiserveTra() {
		return dataInizioRiserveTra;
	}
	/**
	 * @param dataInizioRiserveTra the dataInizioRiserveTra to set
	 */
	public void setDataInizioRiserveTra(Date dataInizioRiserveTra) {
		this.dataInizioRiserveTra = dataInizioRiserveTra;
	}
	/**
	 * @return the dataFineRiserveTra
	 */
	public Date getDataFineRiserveTra() {
		return dataFineRiserveTra;
	}
	/**
	 * @param dataFineRiserveTra the dataFineRiserveTra to set
	 */
	public void setDataFineRiserveTra(Date dataFineRiserveTra) {
		this.dataFineRiserveTra = dataFineRiserveTra;
	}
	
	public Long getTipoCollaudo() {
		return tipoCollaudo;
	}
	
	public void setTipoCollaudo(Long tipoCollaudo) {
		this.tipoCollaudo = tipoCollaudo;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

	public Double getScostImpPrev() {
		return scostImpPrev;
	}

	public void setScostImpPrev(Double scostImpPrev) {
		this.scostImpPrev = scostImpPrev;
	}
}
