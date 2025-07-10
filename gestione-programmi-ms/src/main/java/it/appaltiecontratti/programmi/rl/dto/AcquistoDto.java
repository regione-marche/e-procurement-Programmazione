package it.appaltiecontratti.programmi.rl.dto;

import java.io.Serializable;

public class AcquistoDto implements Serializable {

    private String cui;
    private Long anno;
    private String flSoggettoACup;
    private String cup;
    private Long idAcquistoRicompreso;
    private String cuilavoro;
    private String flLottoFunzionale;
    private String codiceNUTS;
    private String codiceCpvSpc;
    private Integer idSettore;
    private String descrizioneAcquisto;
    private Long idLivelloPriorita;
    private Long durataDelContratto;
    private String flnuovoAffidamento;

    // Dati del professionista
    private String nomeProf;
    private String cognomeProf;
    private String cfProf;
    private String emailProf;
    private String nazioneProf;
    private String cdCatastaleProf;

    // Altri dati
    private Integer idCapitalePrivato;
    private String flCentraleCommittenza;
    private String codiceAUSA;
    private String denominazioneAUSA;
    private Long idAcquistoAggVar;
    private String note;

    // Risorse vincolate per legge
    private Long risorseVincolatePerLegge1;
    private Long risorseVincolatePerLegge2;
    private Long risorseVincolatePerLegge3;
    private Long risorseVincolatePerLeggeSucc;

    // Risorse mutuo
    private Long risorseMutuo1;
    private Long risorseMutuo2;
    private Long risorseMutuo3;
    private Long risorseMutuoSucc;

    // Risorse privati
    private Long risorsePrivati1;
    private Long risorsePrivati2;
    private Long risorsePrivati3;
    private Long risorsePrivatiSucc;

    // Risorse bilancio
    private Long risorseBilancio1;
    private Long risorseBilancio2;
    private Long risorseBilancio3;
    private Long risorseBilancioSucc;

    // Risorse Art3
    private Long risorseArt31;
    private Long risorseArt32;
    private Long risorseArt33;
    private Long risorseArt3Succ;

    // Risorse immobili
    private Long risorseImmobili1;
    private Long risorseImmobili2;
    private Long risorseImmobili3;
    private Long risorseImmobiliSucc;

    // Risorse altro
    private Long risorseAltro1;
    private Long risorseAltro2;
    private Long risorseAltro3;
    private Long risorseAltroSucc;

    private Long spese;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public Long getAnno() {
        return anno;
    }

    public void setAnno(Long anno) {
        this.anno = anno;
    }

    public String getFlSoggettoACup() {
        return flSoggettoACup;
    }

    public void setFlSoggettoACup(String flSoggettoACup) {
        this.flSoggettoACup = flSoggettoACup;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public Long getIdAcquistoRicompreso() {
        return idAcquistoRicompreso;
    }

    public void setIdAcquistoRicompreso(Long idAcquistoRicompreso) {
        this.idAcquistoRicompreso = idAcquistoRicompreso;
    }

    public String getCuilavoro() {
        return cuilavoro;
    }

    public void setCuilavoro(String cuilavoro) {
        this.cuilavoro = cuilavoro;
    }

    public String getFlLottoFunzionale() {
        return flLottoFunzionale;
    }

    public void setFlLottoFunzionale(String flLottoFunzionale) {
        this.flLottoFunzionale = flLottoFunzionale;
    }

    public String getCodiceNUTS() {
        return codiceNUTS;
    }

    public void setCodiceNUTS(String codiceNUTS) {
        this.codiceNUTS = codiceNUTS;
    }

    public String getCodiceCpvSpc() {
        return codiceCpvSpc;
    }

    public void setCodiceCpvSpc(String codiceCpvSpc) {
        this.codiceCpvSpc = codiceCpvSpc;
    }

    public Integer getIdSettore() {
        return idSettore;
    }

    public void setIdSettore(Integer idSettore) {
        this.idSettore = idSettore;
    }

    public String getDescrizioneAcquisto() {
        return descrizioneAcquisto;
    }

    public void setDescrizioneAcquisto(String descrizioneAcquisto) {
        this.descrizioneAcquisto = descrizioneAcquisto;
    }

    public Long getIdLivelloPriorita() {
        return idLivelloPriorita;
    }

    public void setIdLivelloPriorita(Long idLivelloPriorita) {
        this.idLivelloPriorita = idLivelloPriorita;
    }

    public Long getDurataDelContratto() {
        return durataDelContratto;
    }

    public void setDurataDelContratto(Long durataDelContratto) {
        this.durataDelContratto = durataDelContratto;
    }

    public String getFlnuovoAffidamento() {
        return flnuovoAffidamento;
    }

    public void setFlnuovoAffidamento(String flnuovoAffidamento) {
        this.flnuovoAffidamento = flnuovoAffidamento;
    }

    public String getNomeProf() {
        return nomeProf;
    }

    public void setNomeProf(String nomeProf) {
        this.nomeProf = nomeProf;
    }

    public String getCognomeProf() {
        return cognomeProf;
    }

    public void setCognomeProf(String cognomeProf) {
        this.cognomeProf = cognomeProf;
    }

    public String getCfProf() {
        return cfProf;
    }

    public void setCfProf(String cfProf) {
        this.cfProf = cfProf;
    }

    public String getEmailProf() {
        return emailProf;
    }

    public void setEmailProf(String emailProf) {
        this.emailProf = emailProf;
    }

    public String getNazioneProf() {
        return nazioneProf;
    }

    public void setNazioneProf(String nazioneProf) {
        this.nazioneProf = nazioneProf;
    }

    public String getCdCatastaleProf() {
        return cdCatastaleProf;
    }

    public void setCdCatastaleProf(String cdCatastaleProf) {
        this.cdCatastaleProf = cdCatastaleProf;
    }

    public Integer getIdCapitalePrivato() {
        return idCapitalePrivato;
    }

    public void setIdCapitalePrivato(Integer idCapitalePrivato) {
        this.idCapitalePrivato = idCapitalePrivato;
    }

    public String getFlCentraleCommittenza() {
        return flCentraleCommittenza;
    }

    public void setFlCentraleCommittenza(String flCentraleCommittenza) {
        this.flCentraleCommittenza = flCentraleCommittenza;
    }

    public String getCodiceAUSA() {
        return codiceAUSA;
    }

    public void setCodiceAUSA(String codiceAUSA) {
        this.codiceAUSA = codiceAUSA;
    }

    public String getDenominazioneAUSA() {
        return denominazioneAUSA;
    }

    public void setDenominazioneAUSA(String denominazioneAUSA) {
        this.denominazioneAUSA = denominazioneAUSA;
    }

    public Long getIdAcquistoAggVar() {
        return idAcquistoAggVar;
    }

    public void setIdAcquistoAggVar(Long idAcquistoAggVar) {
        this.idAcquistoAggVar = idAcquistoAggVar;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getRisorseVincolatePerLegge1() {
        return risorseVincolatePerLegge1;
    }

    public void setRisorseVincolatePerLegge1(Long risorseVincolatePerLegge1) {
        this.risorseVincolatePerLegge1 = risorseVincolatePerLegge1;
    }

    public Long getRisorseVincolatePerLegge2() {
        return risorseVincolatePerLegge2;
    }

    public void setRisorseVincolatePerLegge2(Long risorseVincolatePerLegge2) {
        this.risorseVincolatePerLegge2 = risorseVincolatePerLegge2;
    }

    public Long getRisorseVincolatePerLegge3() {
        return risorseVincolatePerLegge3;
    }

    public void setRisorseVincolatePerLegge3(Long risorseVincolatePerLegge3) {
        this.risorseVincolatePerLegge3 = risorseVincolatePerLegge3;
    }

    public Long getRisorseVincolatePerLeggeSucc() {
        return risorseVincolatePerLeggeSucc;
    }

    public void setRisorseVincolatePerLeggeSucc(Long risorseVincolatePerLeggeSucc) {
        this.risorseVincolatePerLeggeSucc = risorseVincolatePerLeggeSucc;
    }

    public Long getRisorseMutuo1() {
        return risorseMutuo1;
    }

    public void setRisorseMutuo1(Long risorseMutuo1) {
        this.risorseMutuo1 = risorseMutuo1;
    }

    public Long getRisorseMutuo2() {
        return risorseMutuo2;
    }

    public void setRisorseMutuo2(Long risorseMutuo2) {
        this.risorseMutuo2 = risorseMutuo2;
    }

    public Long getRisorseMutuo3() {
        return risorseMutuo3;
    }

    public void setRisorseMutuo3(Long risorseMutuo3) {
        this.risorseMutuo3 = risorseMutuo3;
    }

    public Long getRisorseMutuoSucc() {
        return risorseMutuoSucc;
    }

    public void setRisorseMutuoSucc(Long risorseMutuoSucc) {
        this.risorseMutuoSucc = risorseMutuoSucc;
    }

    public Long getRisorsePrivati1() {
        return risorsePrivati1;
    }

    public void setRisorsePrivati1(Long risorsePrivati1) {
        this.risorsePrivati1 = risorsePrivati1;
    }

    public Long getRisorsePrivati2() {
        return risorsePrivati2;
    }

    public void setRisorsePrivati2(Long risorsePrivati2) {
        this.risorsePrivati2 = risorsePrivati2;
    }

    public Long getRisorsePrivati3() {
        return risorsePrivati3;
    }

    public void setRisorsePrivati3(Long risorsePrivati3) {
        this.risorsePrivati3 = risorsePrivati3;
    }

    public Long getRisorsePrivatiSucc() {
        return risorsePrivatiSucc;
    }

    public void setRisorsePrivatiSucc(Long risorsePrivatiSucc) {
        this.risorsePrivatiSucc = risorsePrivatiSucc;
    }

    public Long getRisorseBilancio1() {
        return risorseBilancio1;
    }

    public void setRisorseBilancio1(Long risorseBilancio1) {
        this.risorseBilancio1 = risorseBilancio1;
    }

    public Long getRisorseBilancio2() {
        return risorseBilancio2;
    }

    public void setRisorseBilancio2(Long risorseBilancio2) {
        this.risorseBilancio2 = risorseBilancio2;
    }

    public Long getRisorseBilancio3() {
        return risorseBilancio3;
    }

    public void setRisorseBilancio3(Long risorseBilancio3) {
        this.risorseBilancio3 = risorseBilancio3;
    }

    public Long getRisorseBilancioSucc() {
        return risorseBilancioSucc;
    }

    public void setRisorseBilancioSucc(Long risorseBilancioSucc) {
        this.risorseBilancioSucc = risorseBilancioSucc;
    }

    public Long getRisorseArt31() {
        return risorseArt31;
    }

    public void setRisorseArt31(Long risorseArt31) {
        this.risorseArt31 = risorseArt31;
    }

    public Long getRisorseArt32() {
        return risorseArt32;
    }

    public void setRisorseArt32(Long risorseArt32) {
        this.risorseArt32 = risorseArt32;
    }

    public Long getRisorseArt33() {
        return risorseArt33;
    }

    public void setRisorseArt33(Long risorseArt33) {
        this.risorseArt33 = risorseArt33;
    }

    public Long getRisorseArt3Succ() {
        return risorseArt3Succ;
    }

    public void setRisorseArt3Succ(Long risorseArt3Succ) {
        this.risorseArt3Succ = risorseArt3Succ;
    }

    public Long getRisorseImmobili1() {
        return risorseImmobili1;
    }

    public void setRisorseImmobili1(Long risorseImmobili1) {
        this.risorseImmobili1 = risorseImmobili1;
    }

    public Long getRisorseImmobili2() {
        return risorseImmobili2;
    }

    public void setRisorseImmobili2(Long risorseImmobili2) {
        this.risorseImmobili2 = risorseImmobili2;
    }

    public Long getRisorseImmobili3() {
        return risorseImmobili3;
    }

    public void setRisorseImmobili3(Long risorseImmobili3) {
        this.risorseImmobili3 = risorseImmobili3;
    }

    public Long getRisorseImmobiliSucc() {
        return risorseImmobiliSucc;
    }

    public void setRisorseImmobiliSucc(Long risorseImmobiliSucc) {
        this.risorseImmobiliSucc = risorseImmobiliSucc;
    }

    public Long getRisorseAltro1() {
        return risorseAltro1;
    }

    public void setRisorseAltro1(Long risorseAltro1) {
        this.risorseAltro1 = risorseAltro1;
    }

    public Long getRisorseAltro2() {
        return risorseAltro2;
    }

    public void setRisorseAltro2(Long risorseAltro2) {
        this.risorseAltro2 = risorseAltro2;
    }

    public Long getRisorseAltro3() {
        return risorseAltro3;
    }

    public void setRisorseAltro3(Long risorseAltro3) {
        this.risorseAltro3 = risorseAltro3;
    }

    public Long getRisorseAltroSucc() {
        return risorseAltroSucc;
    }

    public void setRisorseAltroSucc(Long risorseAltroSucc) {
        this.risorseAltroSucc = risorseAltroSucc;
    }

    public Long getSpese() {
        return spese;
    }

    public void setSpese(Long spese) {
        this.spese = spese;
    }

    @Override
    public String toString() {
        return "AcquistoDto{" +
                "cui='" + cui + '\'' +
                ", anno=" + anno +
                ", flSoggettoACup='" + flSoggettoACup + '\'' +
                ", cup='" + cup + '\'' +
                ", idAcquistoRicompreso=" + idAcquistoRicompreso +
                ", cuilavoro='" + cuilavoro + '\'' +
                ", flLottoFunzionale='" + flLottoFunzionale + '\'' +
                ", codiceNUTS='" + codiceNUTS + '\'' +
                ", codiceCpvSpc='" + codiceCpvSpc + '\'' +
                ", idSettore=" + idSettore +
                ", descrizioneAcquisto='" + descrizioneAcquisto + '\'' +
                ", idLivelloPriorita=" + idLivelloPriorita +
                ", durataDelContratto=" + durataDelContratto +
                ", flnuovoAffidamento='" + flnuovoAffidamento + '\'' +
                ", nomeProf='" + nomeProf + '\'' +
                ", cognomeProf='" + cognomeProf + '\'' +
                ", cfProf='" + cfProf + '\'' +
                ", emailProf='" + emailProf + '\'' +
                ", nazioneProf='" + nazioneProf + '\'' +
                ", cdCatastaleProf='" + cdCatastaleProf + '\'' +
                ", idCapitalePrivato=" + idCapitalePrivato +
                ", flCentraleCommittenza='" + flCentraleCommittenza + '\'' +
                ", codiceAUSA='" + codiceAUSA + '\'' +
                ", denominazioneAUSA='" + denominazioneAUSA + '\'' +
                ", idAcquistoAggVar=" + idAcquistoAggVar +
                ", note='" + note + '\'' +
                ", risorseVincolatePerLegge1=" + risorseVincolatePerLegge1 +
                ", risorseVincolatePerLegge2=" + risorseVincolatePerLegge2 +
                ", risorseVincolatePerLegge3=" + risorseVincolatePerLegge3 +
                ", risorseVincolatePerLeggeSucc=" + risorseVincolatePerLeggeSucc +
                ", risorseMutuo1=" + risorseMutuo1 +
                ", risorseMutuo2=" + risorseMutuo2 +
                ", risorseMutuo3=" + risorseMutuo3 +
                ", risorseMutuoSucc=" + risorseMutuoSucc +
                ", risorsePrivati1=" + risorsePrivati1 +
                ", risorsePrivati2=" + risorsePrivati2 +
                ", risorsePrivati3=" + risorsePrivati3 +
                ", risorsePrivatiSucc=" + risorsePrivatiSucc +
                ", risorseBilancio1=" + risorseBilancio1 +
                ", risorseBilancio2=" + risorseBilancio2 +
                ", risorseBilancio3=" + risorseBilancio3 +
                ", risorseBilancioSucc=" + risorseBilancioSucc +
                ", risorseArt31=" + risorseArt31 +
                ", risorseArt32=" + risorseArt32 +
                ", risorseArt33=" + risorseArt33 +
                ", risorseArt3Succ=" + risorseArt3Succ +
                ", risorseImmobili1=" + risorseImmobili1 +
                ", risorseImmobili2=" + risorseImmobili2 +
                ", risorseImmobili3=" + risorseImmobili3 +
                ", risorseImmobiliSucc=" + risorseImmobiliSucc +
                ", risorseAltro1=" + risorseAltro1 +
                ", risorseAltro2=" + risorseAltro2 +
                ", risorseAltro3=" + risorseAltro3 +
                ", risorseAltroSucc=" + risorseAltroSucc +
                ", spese=" + spese +
                '}';
    }
}