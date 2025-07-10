package it.appaltiecontratti.programmi.rl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class InterventoDto implements Serializable {

    private String cui;
    private String codIntAmministrazione;
    private String flSoggettoACup;
    private String cup;
    private Integer anno;
    private String nomeProf;
    private String cognomeProf;
    private String cfProf;
    private String emailProf;
    private String nazioneProf;
    private String cdCatastaleProf;
    private String flLottoFunzionale;
    private String flLavoroComplesso;
    private String cdRegione;
    private String siglaProvincia;
    private String cdCatastale;
    private String codiceNUTS;
    private Integer idTipoIntervento;
    private Integer idSettore;
    private Integer idSottoSettore;
    private String descrizione;
    private Long idLivelloPriorita;
    private Long risorseVincolatePerLegge1;
    private Long risorseVincolatePerLegge2;
    private Long risorseVincolatePerLegge3;
    private Long risorseVincolatePerLeggeSucc;
    private Long risorseMutuo1;
    private Long risorseMutuo2;
    private Long risorseMutuo3;
    private Long risorseMutuoSucc;
    private Long risorsePrivati1;
    private Long risorsePrivati2;
    private Long risorsePrivati3;
    private Long risorsePrivatiSucc;
    private Long risorseBilancio1;
    private Long risorseBilancio2;
    private Long risorseBilancio3;
    private Long risorseBilancioSucc;
    private Long risorseArt31;
    private Long risorseArt32;
    private Long risorseArt33;
    private Long risorseArt3Succ;
    private Long risorseImmobili1;
    private Long risorseImmobili2;
    private Long risorseImmobili3;
    private Long risorseImmobiliSucc;
    private Long risorseAltro1;
    private Long risorseAltro2;
    private Long risorseAltro3;
    private Long risorseAltroSucc;
    private Long spese;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date scadenzaTemporale;

    private Integer idCapitalePrivato;
    private Long idInterventoAggiunto;
    private Integer idFinalita;
    private String flConformita;
    private String flVerificaAmbientale;
    private Integer idProgettazione;
    private String flCentraleCommittenza;
    private String codiceAUSA;
    private String denominazione;
    private String note;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getCodIntAmministrazione() {
        return codIntAmministrazione;
    }

    public void setCodIntAmministrazione(String codIntAmministrazione) {
        this.codIntAmministrazione = codIntAmministrazione;
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

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
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

    public String getFlLottoFunzionale() {
        return flLottoFunzionale;
    }

    public void setFlLottoFunzionale(String flLottoFunzionale) {
        this.flLottoFunzionale = flLottoFunzionale;
    }

    public String getFlLavoroComplesso() {
        return flLavoroComplesso;
    }

    public void setFlLavoroComplesso(String flLavoroComplesso) {
        this.flLavoroComplesso = flLavoroComplesso;
    }

    public String getCdRegione() {
        return cdRegione;
    }

    public void setCdRegione(String cdRegione) {
        this.cdRegione = cdRegione;
    }

    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    public String getCdCatastale() {
        return cdCatastale;
    }

    public void setCdCatastale(String cdCatastale) {
        this.cdCatastale = cdCatastale;
    }

    public String getCodiceNUTS() {
        return codiceNUTS;
    }

    public void setCodiceNUTS(String codiceNUTS) {
        this.codiceNUTS = codiceNUTS;
    }

    public Integer getIdTipoIntervento() {
        return idTipoIntervento;
    }

    public void setIdTipoIntervento(Integer idTipoIntervento) {
        this.idTipoIntervento = idTipoIntervento;
    }

    public Integer getIdSettore() {
        return idSettore;
    }

    public void setIdSettore(Integer idSettore) {
        this.idSettore = idSettore;
    }

    public Integer getIdSottoSettore() {
        return idSottoSettore;
    }

    public void setIdSottoSettore(Integer idSottoSettore) {
        this.idSottoSettore = idSottoSettore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getIdLivelloPriorita() {
        return idLivelloPriorita;
    }

    public void setIdLivelloPriorita(Long idLivelloPriorita) {
        this.idLivelloPriorita = idLivelloPriorita;
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

    public Date getScadenzaTemporale() {
        return scadenzaTemporale;
    }

    public void setScadenzaTemporale(Date scadenzaTemporale) {
        this.scadenzaTemporale = scadenzaTemporale;
    }

    public Integer getIdCapitalePrivato() {
        return idCapitalePrivato;
    }

    public void setIdCapitalePrivato(Integer idCapitalePrivato) {
        this.idCapitalePrivato = idCapitalePrivato;
    }

    public Long getIdInterventoAggiunto() {
        return idInterventoAggiunto;
    }

    public void setIdInterventoAggiunto(Long idInterventoAggiunto) {
        this.idInterventoAggiunto = idInterventoAggiunto;
    }

    public Integer getIdFinalita() {
        return idFinalita;
    }

    public void setIdFinalita(Integer idFinalita) {
        this.idFinalita = idFinalita;
    }

    public String getFlConformita() {
        return flConformita;
    }

    public void setFlConformita(String flConformita) {
        this.flConformita = flConformita;
    }

    public String getFlVerificaAmbientale() {
        return flVerificaAmbientale;
    }

    public void setFlVerificaAmbientale(String flVerificaAmbientale) {
        this.flVerificaAmbientale = flVerificaAmbientale;
    }

    public Integer getIdProgettazione() {
        return idProgettazione;
    }

    public void setIdProgettazione(Integer idProgettazione) {
        this.idProgettazione = idProgettazione;
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

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "InterventoDto{" +
                "cui='" + cui + '\'' +
                ", codIntAmministrazione='" + codIntAmministrazione + '\'' +
                ", flSoggettoACup='" + flSoggettoACup + '\'' +
                ", cup='" + cup + '\'' +
                ", anno=" + anno +
                ", nomeProf='" + nomeProf + '\'' +
                ", cognomeProf='" + cognomeProf + '\'' +
                ", cfProf='" + cfProf + '\'' +
                ", emailProf='" + emailProf + '\'' +
                ", nazioneProf='" + nazioneProf + '\'' +
                ", cdCatastaleProf='" + cdCatastaleProf + '\'' +
                ", flLottoFunzionale='" + flLottoFunzionale + '\'' +
                ", flLavoroComplesso='" + flLavoroComplesso + '\'' +
                ", cdRegione='" + cdRegione + '\'' +
                ", siglaProvincia='" + siglaProvincia + '\'' +
                ", cdCatastale='" + cdCatastale + '\'' +
                ", codiceNUTS='" + codiceNUTS + '\'' +
                ", idTipoIntervento=" + idTipoIntervento +
                ", idSettore=" + idSettore +
                ", idSottoSettore=" + idSottoSettore +
                ", descrizione='" + descrizione + '\'' +
                ", idLivelloPriorita=" + idLivelloPriorita +
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
                ", scadenzaTemporale='" + scadenzaTemporale + '\'' +
                ", idCapitalePrivato=" + idCapitalePrivato +
                ", idInterventoAggiunto=" + idInterventoAggiunto +
                ", idFinalita=" + idFinalita +
                ", flConformita='" + flConformita + '\'' +
                ", flVerificaAmbientale='" + flVerificaAmbientale + '\'' +
                ", idProgettazione=" + idProgettazione +
                ", flCentraleCommittenza='" + flCentraleCommittenza + '\'' +
                ", codiceAUSA='" + codiceAUSA + '\'' +
                ", denominazione='" + denominazione + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}