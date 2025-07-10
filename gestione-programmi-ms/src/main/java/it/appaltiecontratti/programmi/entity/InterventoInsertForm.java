package it.appaltiecontratti.programmi.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class InterventoInsertForm {

	@NotNull
	private Long idProgramma;
	private Long id;
	@NotNull
	private Long annoAvvio;
	private Long numProgetto;
	@XSSValidation
	private String numeroCui;
	@NotNull
	@XSSValidation
	private String descrizione;
	private Double importoTotale;
	@XSSValidation
	private String inPianoAnnuale ;
	private Long tipologiaIntervento;
	@XSSValidation
	private String settore;
	@XSSValidation
	private String codiceInterno;
	private Long annoInsAcquisto;
	private Long meseAvvioProc;
	@XSSValidation
	private String esenteCup;
	@XSSValidation
	private String codiceCup;
	private Long acquistoRicompreso;
	@XSSValidation
	private String cuiCollegato;
	@XSSValidation
	private String codIstatComune;
	@XSSValidation
	private String codiceNuts;
	@XSSValidation
	private String codCpv;
	private Double quantita;
	private Long unitaMisura;
	private Long livelloPriorita;
	@XSSValidation
	private String codiceRup;
	@XSSValidation
	private String direzioneGenerale;
	@XSSValidation
	private String strutturaOperativa;
	@XSSValidation
	private String dirigenteUfficio;
	@XSSValidation
	private String lottoFunzionale;
	@XSSValidation
	private String lavoroComplesso;
	private Long durataContratto;
	@XSSValidation
	private String nuovoAffidamento;
	@XSSValidation
	private String classificazioneIntervento;
	@XSSValidation
	private String categoriaIntervento;
	private Date scadenzaUtilizzoFinanziamento;
	@XSSValidation
	private String finalitaIntervento;
	@XSSValidation
	private String verificaConfUrbanistica;
	@XSSValidation
	private String verificaConfAmbiente;
	@XSSValidation
	private String statoProgettazioneApprovata;
	private Double impDestVincolo1;
	private Double impDestVincolo2;
	private Double impDestVincolo3;
	private Double impDestVincoloSucc;
	private Double importoAcqMututo1;
	private Double importoAcqMututo2;
	private Double importoAcqMututo3;
	private Double importoAcqMututoSucc;
	private Double importoCapPriv1;
	private Double importoCapPriv2;
	private Double importoCapPriv3;
	private Double importoCapPrivSucc;
	private Double stanziamentiBilancio1;
	private Double stanziamentiBilancio2;
	private Double stanziamentiBilancio3;
	private Double stanziamentiBilancioSucc;
	private Double importoFinanz1;
	private Double importoFinanz2;
	private Double importoFinanz3;
	private Double importoFinanzSucc;
	private Double trasfImmobili1;
	private Double trasfImmobili2;
	private Double trasfImmobili3;
	private Double trasfImmobiliSucc;
	private Double altreRisorseDisp1;
	private Double altreRisorseDisp2;
	private Double altreRisorseDisp3;
	private Double altreRisorseDispSucc;
	private Double importoDispPriv1;
	private Double importoDispPriv2;
	private Double importoDispPriv3;
	private Double importoDispPrivSucc;
	private Double importoIva1;
	private Double importoIva2;
	private Double importoIva3;
	private Double importoIvaSucc;
	@XSSValidation
	private String tipologiaCapitalePrivato;
	private Double importoRisorseRegionali;
	private Double importoRisorseFinanz;
	private Double importoRisorseFinanzAltro;
	@XSSValidation
	private String coperturaFinanziaria;
	private Long acqVerdi;
	@XSSValidation
	private String normativaRiferimento;
	@XSSValidation
	private String oggettoAv;
	@XSSValidation
	private String avcpv;
	private Double importoAlnettoIvaAv;
	private Double importoIvaAv;
	private Double importoTotaleAv;
	private Double speseSostenute;
	private Long acquistoMaterialiRiciclati;
	@XSSValidation
	private String oggettoMr;
	@XSSValidation
	private String mrcpv;
	private Double importoAlnettoIvaMr;
	private Double importoIvaMr;
	private Double importoTotaleMr;
	private Long proceduraAffidamento;
	@XSSValidation
	private String delegaProcAff;
	@XSSValidation
	private String codiceAusa;
	@XSSValidation
	private String soggettoDelegato;
	private Long variato;
	@XSSValidation
	private String cigAccordoQuadro;
	@XSSValidation
	private String referenteDatiComunicati;
	private Long valutazioneResp;
	@XSSValidation
	private String note;
	private Double totImpDispFin;
	private Double totaleCapitalePriv;
	private Long acquistoBeniRiciclati;
	List<ImmobileInsertForm> immobili;
	
	public Long getIdProgramma() {
		return idProgramma;
	}
	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long idIntervento) {
		this.id = idIntervento;
	}
	public Long getAnnoAvvio() {
		return annoAvvio;
	}
	public void setAnnoAvvio(Long annoAvvio) {
		this.annoAvvio = annoAvvio;
	}
	public Long getNumProgetto() {
		return numProgetto;
	}
	public void setNumProgetto(Long numProgetto) {
		this.numProgetto = numProgetto;
	}
	public String getNumeroCui() {
		return numeroCui;
	}
	public void setNumeroCui(String numeroCui) {
		this.numeroCui = numeroCui;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Double getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}
	public String getInPianoAnnuale() {
		return inPianoAnnuale;
	}
	public void setInPianoAnnuale(String inPianoAnnuale) {
		this.inPianoAnnuale = inPianoAnnuale;
	}
	public Long getTipologiaIntervento() {
		return tipologiaIntervento;
	}
	public void setTipologiaIntervento(Long tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public Long getAnnoInsAcquisto() {
		return annoInsAcquisto;
	}
	public void setAnnoInsAcquisto(Long annoInsAcquisto) {
		this.annoInsAcquisto = annoInsAcquisto;
	}
	public Long getMeseAvvioProc() {
		return meseAvvioProc;
	}
	public void setMeseAvvioProc(Long meseAvvioProc) {
		this.meseAvvioProc = meseAvvioProc;
	}
	public String getEsenteCup() {
		return esenteCup;
	}
	public void setEsenteCup(String esenteCup) {
		this.esenteCup = esenteCup;
	}
	public String getCodiceCup() {
		return codiceCup;
	}
	public void setCodiceCup(String codiceCup) {
		this.codiceCup = codiceCup;
	}
	public Long getAcquistoRicompreso() {
		return acquistoRicompreso;
	}
	public void setAcquistoRicompreso(Long acquistoRicompreso) {
		this.acquistoRicompreso = acquistoRicompreso;
	}
	public String getCuiCollegato() {
		return cuiCollegato;
	}
	public void setCuiCollegato(String cuiCollegato) {
		this.cuiCollegato = cuiCollegato;
	}
	public String getCodIstatComune() {
		return codIstatComune;
	}
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}
	public String getCodiceNuts() {
		return codiceNuts;
	}
	public void setCodiceNuts(String codiceNuts) {
		this.codiceNuts = codiceNuts;
	}
	public String getCodCpv() {
		return codCpv;
	}
	public void setCodCpv(String codCpv) {
		this.codCpv = codCpv;
	}
	public Double getQuantita() {
		return quantita;
	}
	public void setQuantita(Double quantita) {
		this.quantita = quantita;
	}
	public Long getUnitaMisura() {
		return unitaMisura;
	}
	public void setUnitaMisura(Long unitaMisura) {
		this.unitaMisura = unitaMisura;
	}
	public Long getLivelloPriorita() {
		return livelloPriorita;
	}
	public void setLivelloPriorita(Long livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	public String getCodiceRup() {
		return codiceRup;
	}
	public void setCodiceRup(String codiceRup) {
		this.codiceRup = codiceRup;
	}
	public String getDirezioneGenerale() {
		return direzioneGenerale;
	}
	public void setDirezioneGenerale(String direzioneGenerale) {
		this.direzioneGenerale = direzioneGenerale;
	}
	public String getStrutturaOperativa() {
		return strutturaOperativa;
	}
	public void setStrutturaOperativa(String strutturaOperativa) {
		this.strutturaOperativa = strutturaOperativa;
	}
	public String getDirigenteUfficio() {
		return dirigenteUfficio;
	}
	public void setDirigenteUfficio(String dirigenteUfficio) {
		this.dirigenteUfficio = dirigenteUfficio;
	}
	public String getLottoFunzionale() {
		return lottoFunzionale;
	}
	public void setLottoFunzionale(String lottoFunzionale) {
		this.lottoFunzionale = lottoFunzionale;
	}
	public String getLavoroComplesso() {
		return lavoroComplesso;
	}
	public void setLavoroComplesso(String lavoroComplesso) {
		this.lavoroComplesso = lavoroComplesso;
	}
	public Long getDurataContratto() {
		return durataContratto;
	}
	public void setDurataContratto(Long durataContratto) {
		this.durataContratto = durataContratto;
	}
	public String getNuovoAffidamento() {
		return nuovoAffidamento;
	}
	public void setNuovoAffidamento(String nuovoAffidamento) {
		this.nuovoAffidamento = nuovoAffidamento;
	}
	public String getClassificazioneIntervento() {
		return classificazioneIntervento;
	}
	public void setClassificazioneIntervento(String classificazioneIntervento) {
		this.classificazioneIntervento = classificazioneIntervento;
	}
	public String getCategoriaIntervento() {
		return categoriaIntervento;
	}
	public void setCategoriaIntervento(String categoriaIntervento) {
		this.categoriaIntervento = categoriaIntervento;
	}
	public Date getScadenzaUtilizzoFinanziamento() {
		return scadenzaUtilizzoFinanziamento;
	}
	public void setScadenzaUtilizzoFinanziamento(Date scadenzaUtilizzoFinanziamento) {
		this.scadenzaUtilizzoFinanziamento = scadenzaUtilizzoFinanziamento;
	}
	public String getFinalitaIntervento() {
		return finalitaIntervento;
	}
	public void setFinalitaIntervento(String finalitaIntervento) {
		this.finalitaIntervento = finalitaIntervento;
	}
	public String getVerificaConfUrbanistica() {
		return verificaConfUrbanistica;
	}
	public void setVerificaConfUrbanistica(String verificaConfUrbanistica) {
		this.verificaConfUrbanistica = verificaConfUrbanistica;
	}
	public String getVerificaConfAmbiente() {
		return verificaConfAmbiente;
	}
	public void setVerificaConfAmbiente(String verificaConfAmbiente) {
		this.verificaConfAmbiente = verificaConfAmbiente;
	}
	public String getStatoProgettazioneApprovata() {
		return statoProgettazioneApprovata;
	}
	public void setStatoProgettazioneApprovata(String statoProgettazioneApprovata) {
		this.statoProgettazioneApprovata = statoProgettazioneApprovata;
	}
	public Double getImpDestVincolo1() {
		return impDestVincolo1;
	}
	public void setImpDestVincolo1(Double impDestVincolo1) {
		this.impDestVincolo1 = impDestVincolo1;
	}
	public Double getImpDestVincolo2() {
		return impDestVincolo2;
	}
	public void setImpDestVincolo2(Double impDestVincolo2) {
		this.impDestVincolo2 = impDestVincolo2;
	}
	public Double getImpDestVincolo3() {
		return impDestVincolo3;
	}
	public void setImpDestVincolo3(Double impDestVincolo3) {
		this.impDestVincolo3 = impDestVincolo3;
	}
	public Double getImpDestVincoloSucc() {
		return impDestVincoloSucc;
	}
	public void setImpDestVincoloSucc(Double impDestVincoloSucc) {
		this.impDestVincoloSucc = impDestVincoloSucc;
	}
	public Double getImportoAcqMututo1() {
		return importoAcqMututo1;
	}
	public void setImportoAcqMututo1(Double importoAcqMututo1) {
		this.importoAcqMututo1 = importoAcqMututo1;
	}
	public Double getImportoAcqMututo2() {
		return importoAcqMututo2;
	}
	public void setImportoAcqMututo2(Double importoAcqMututo2) {
		this.importoAcqMututo2 = importoAcqMututo2;
	}
	public Double getImportoAcqMututo3() {
		return importoAcqMututo3;
	}
	public void setImportoAcqMututo3(Double importoAcqMututo3) {
		this.importoAcqMututo3 = importoAcqMututo3;
	}
	public Double getImportoAcqMututoSucc() {
		return importoAcqMututoSucc;
	}
	public void setImportoAcqMututoSucc(Double importoAcqMututoSucc) {
		this.importoAcqMututoSucc = importoAcqMututoSucc;
	}
	public Double getImportoCapPriv1() {
		return importoCapPriv1;
	}
	public void setImportoCapPriv1(Double importoCapPriv1) {
		this.importoCapPriv1 = importoCapPriv1;
	}
	public Double getImportoCapPriv2() {
		return importoCapPriv2;
	}
	public void setImportoCapPriv2(Double importoCapPriv2) {
		this.importoCapPriv2 = importoCapPriv2;
	}
	public Double getImportoCapPriv3() {
		return importoCapPriv3;
	}
	public void setImportoCapPriv3(Double importoCapPriv3) {
		this.importoCapPriv3 = importoCapPriv3;
	}
	public Double getImportoCapPrivSucc() {
		return importoCapPrivSucc;
	}
	public void setImportoCapPrivSucc(Double importoCapPrivSucc) {
		this.importoCapPrivSucc = importoCapPrivSucc;
	}
	public Double getStanziamentiBilancio1() {
		return stanziamentiBilancio1;
	}
	public void setStanziamentiBilancio1(Double stanziamentiBilancio1) {
		this.stanziamentiBilancio1 = stanziamentiBilancio1;
	}
	public Double getStanziamentiBilancio2() {
		return stanziamentiBilancio2;
	}
	public void setStanziamentiBilancio2(Double stanziamentiBilancio2) {
		this.stanziamentiBilancio2 = stanziamentiBilancio2;
	}
	public Double getStanziamentiBilancio3() {
		return stanziamentiBilancio3;
	}
	public void setStanziamentiBilancio3(Double stanziamentiBilancio3) {
		this.stanziamentiBilancio3 = stanziamentiBilancio3;
	}
	public Double getStanziamentiBilancioSucc() {
		return stanziamentiBilancioSucc;
	}
	public void setStanziamentiBilancioSucc(Double stanziamentiBilancioSucc) {
		this.stanziamentiBilancioSucc = stanziamentiBilancioSucc;
	}
	public Double getImportoFinanz1() {
		return importoFinanz1;
	}
	public void setImportoFinanz1(Double importoFinanz1) {
		this.importoFinanz1 = importoFinanz1;
	}
	public Double getImportoFinanz2() {
		return importoFinanz2;
	}
	public void setImportoFinanz2(Double importoFinanz2) {
		this.importoFinanz2 = importoFinanz2;
	}
	public Double getImportoFinanz3() {
		return importoFinanz3;
	}
	public void setImportoFinanz3(Double importoFinanz3) {
		this.importoFinanz3 = importoFinanz3;
	}
	public Double getImportoFinanzSucc() {
		return importoFinanzSucc;
	}
	public void setImportoFinanzSucc(Double importoFinanzSucc) {
		this.importoFinanzSucc = importoFinanzSucc;
	}
	public Double getTrasfImmobili1() {
		return trasfImmobili1;
	}
	public void setTrasfImmobili1(Double trasfImmobili1) {
		this.trasfImmobili1 = trasfImmobili1;
	}
	public Double getTrasfImmobili2() {
		return trasfImmobili2;
	}
	public void setTrasfImmobili2(Double trasfImmobili2) {
		this.trasfImmobili2 = trasfImmobili2;
	}
	public Double getTrasfImmobili3() {
		return trasfImmobili3;
	}
	public void setTrasfImmobili3(Double trasfImmobili3) {
		this.trasfImmobili3 = trasfImmobili3;
	}
	public Double getTrasfImmobiliSucc() {
		return trasfImmobiliSucc;
	}
	public void setTrasfImmobiliSucc(Double trasfImmobiliSucc) {
		this.trasfImmobiliSucc = trasfImmobiliSucc;
	}
	public Double getAltreRisorseDisp1() {
		return altreRisorseDisp1;
	}
	public void setAltreRisorseDisp1(Double altreRisorseDisp1) {
		this.altreRisorseDisp1 = altreRisorseDisp1;
	}
	public Double getAltreRisorseDisp2() {
		return altreRisorseDisp2;
	}
	public void setAltreRisorseDisp2(Double altreRisorseDisp2) {
		this.altreRisorseDisp2 = altreRisorseDisp2;
	}
	public Double getAltreRisorseDisp3() {
		return altreRisorseDisp3;
	}
	public void setAltreRisorseDisp3(Double altreRisorseDisp3) {
		this.altreRisorseDisp3 = altreRisorseDisp3;
	}
	public Double getAltreRisorseDispSucc() {
		return altreRisorseDispSucc;
	}
	public void setAltreRisorseDispSucc(Double altreRisorseDispSucc) {
		this.altreRisorseDispSucc = altreRisorseDispSucc;
	}
	public Double getImportoDispPriv1() {
		return importoDispPriv1;
	}
	public void setImportoDispPriv1(Double importoDispPriv1) {
		this.importoDispPriv1 = importoDispPriv1;
	}
	public Double getImportoDispPriv2() {
		return importoDispPriv2;
	}
	public void setImportoDispPriv2(Double importoDispPriv2) {
		this.importoDispPriv2 = importoDispPriv2;
	}
	public Double getImportoDispPriv3() {
		return importoDispPriv3;
	}
	public void setImportoDispPriv3(Double importoDispPriv3) {
		this.importoDispPriv3 = importoDispPriv3;
	}
	public Double getImportoDispPrivSucc() {
		return importoDispPrivSucc;
	}
	public void setImportoDispPrivSucc(Double importoDispPrivSucc) {
		this.importoDispPrivSucc = importoDispPrivSucc;
	}
	public Double getImportoIva1() {
		return importoIva1;
	}
	public void setImportoIva1(Double importoIva1) {
		this.importoIva1 = importoIva1;
	}
	public Double getImportoIva2() {
		return importoIva2;
	}
	public void setImportoIva2(Double importoIva2) {
		this.importoIva2 = importoIva2;
	}
	public Double getImportoIvaSucc() {
		return importoIvaSucc;
	}
	public void setImportoIvaSucc(Double importoIvaSucc) {
		this.importoIvaSucc = importoIvaSucc;
	}
	public String getTipologiaCapitalePrivato() {
		return tipologiaCapitalePrivato;
	}
	public void setTipologiaCapitalePrivato(String tipologiaCapitalePrivato) {
		this.tipologiaCapitalePrivato = tipologiaCapitalePrivato;
	}
	public Double getImportoRisorseRegionali() {
		return importoRisorseRegionali;
	}
	public void setImportoRisorseRegionali(Double importoRisorseRegionali) {
		this.importoRisorseRegionali = importoRisorseRegionali;
	}
	public Double getImportoRisorseFinanz() {
		return importoRisorseFinanz;
	}
	public void setImportoRisorseFinanz(Double importoRisorseFinanz) {
		this.importoRisorseFinanz = importoRisorseFinanz;
	}
	public Double getImportoRisorseFinanzAltro() {
		return importoRisorseFinanzAltro;
	}
	public void setImportoRisorseFinanzAltro(Double importoRisorseFinanzAltro) {
		this.importoRisorseFinanzAltro = importoRisorseFinanzAltro;
	}
	public String getCoperturaFinanziaria() {
		return coperturaFinanziaria;
	}
	public void setCoperturaFinanziaria(String coperturaFinanziaria) {
		this.coperturaFinanziaria = coperturaFinanziaria;
	}
	public Long getAcqVerdi() {
		return acqVerdi;
	}
	public void setAcqVerdi(Long acqVerdi) {
		this.acqVerdi = acqVerdi;
	}
	public String getNormativaRiferimento() {
		return normativaRiferimento;
	}
	public void setNormativaRiferimento(String normativaRiferimento) {
		this.normativaRiferimento = normativaRiferimento;
	}
	public String getOggettoAv() {
		return oggettoAv;
	}
	public void setOggettoAv(String oggettoAv) {
		this.oggettoAv = oggettoAv;
	}
	public String getAvcpv() {
		return avcpv;
	}
	public void setAvcpv(String avcpv) {
		this.avcpv = avcpv;
	}
	public Double getImportoAlnettoIvaAv() {
		return importoAlnettoIvaAv;
	}
	public void setImportoAlnettoIvaAv(Double importoAlnettoIvaAv) {
		this.importoAlnettoIvaAv = importoAlnettoIvaAv;
	}
	public Double getImportoIvaAv() {
		return importoIvaAv;
	}
	public void setImportoIvaAv(Double importoIvaAv) {
		this.importoIvaAv = importoIvaAv;
	}
	public Double getImportoTotaleAv() {
		return importoTotaleAv;
	}
	public void setImportoTotaleAv(Double importoTotaleAv) {
		this.importoTotaleAv = importoTotaleAv;
	}
	public Double getSpeseSostenute() {
		return speseSostenute;
	}
	public void setSpeseSostenute(Double speseSostenute) {
		this.speseSostenute = speseSostenute;
	}
	public Long getAcquistoMaterialiRiciclati() {
		return acquistoMaterialiRiciclati;
	}
	public void setAcquistoMaterialiRiciclati(Long acquistoMaterialiRiciclati) {
		this.acquistoMaterialiRiciclati = acquistoMaterialiRiciclati;
	}
	public String getOggettoMr() {
		return oggettoMr;
	}
	public void setOggettoMr(String oggettoMr) {
		this.oggettoMr = oggettoMr;
	}
	public String getMrcpv() {
		return mrcpv;
	}
	public void setMrcpv(String mrcpv) {
		this.mrcpv = mrcpv;
	}
	public Double getImportoAlnettoIvaMr() {
		return importoAlnettoIvaMr;
	}
	public void setImportoAlnettoIvaMr(Double importoAlnettoIvaMr) {
		this.importoAlnettoIvaMr = importoAlnettoIvaMr;
	}
	public Double getImportoIvaMr() {
		return importoIvaMr;
	}
	public void setImportoIvaMr(Double importoIvaMr) {
		this.importoIvaMr = importoIvaMr;
	}
	public Double getImportoTotaleMr() {
		return importoTotaleMr;
	}
	public void setImportoTotaleMr(Double importoTotaleMr) {
		this.importoTotaleMr = importoTotaleMr;
	}
	public Long getProceduraAffidamento() {
		return proceduraAffidamento;
	}
	public void setProceduraAffidamento(Long proceduraAffidamento) {
		this.proceduraAffidamento = proceduraAffidamento;
	}
	public String getDelegaProcAff() {
		return delegaProcAff;
	}
	public void setDelegaProcAff(String delegaProcAff) {
		this.delegaProcAff = delegaProcAff;
	}
	public String getCodiceAusa() {
		return codiceAusa;
	}
	public void setCodiceAusa(String codiceAusa) {
		this.codiceAusa = codiceAusa;
	}
	public String getSoggettoDelegato() {
		return soggettoDelegato;
	}
	public void setSoggettoDelegato(String soggettoDelegato) {
		this.soggettoDelegato = soggettoDelegato;
	}
	public Long getVariato() {
		return variato;
	}
	public void setVariato(Long variato) {
		this.variato = variato;
	}
	public String getReferenteDatiComunicati() {
		return referenteDatiComunicati;
	}
	public void setReferenteDatiComunicati(String referenteDatiComunicati) {
		this.referenteDatiComunicati = referenteDatiComunicati;
	}
	public Long getValutazioneResp() {
		return valutazioneResp;
	}
	public void setValutazioneResp(Long valutazioneResp) {
		this.valutazioneResp = valutazioneResp;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Double getTotImpDispFin() {
		return totImpDispFin;
	}
	public void setTotImpDispFin(Double totImpDispFin) {
		this.totImpDispFin = totImpDispFin;
	}
	public Double getTotaleCapitalePriv() {
		return totaleCapitalePriv;
	}
	public void setTotaleCapitalePriv(Double totaleCapitalePriv) {
		this.totaleCapitalePriv = totaleCapitalePriv;
	}
	public Long getAcquistoBeniRiciclati() {
		return acquistoBeniRiciclati;
	}
	public void setAcquistoBeniRiciclati(Long acquistoBeniRiciclati) {
		this.acquistoBeniRiciclati = acquistoBeniRiciclati;
	}
	public List<ImmobileInsertForm> getImmobili() {
		if(immobili == null){
			return new ArrayList<ImmobileInsertForm>();
		} else {
			return immobili;
		}
	}
	public void setImmobili(List<ImmobileInsertForm> immobili) {
		this.immobili = immobili;
	}
	public Double getImportoIva3() {
		return importoIva3;
	}
	public void setImportoIva3(Double importoIva3) {
		this.importoIva3 = importoIva3;
	}

	public String getCigAccordoQuadro() {
		return cigAccordoQuadro;
	}

	public void setCigAccordoQuadro(String cigAccordoQuadro) {
		this.cigAccordoQuadro = cigAccordoQuadro;
	}
}
