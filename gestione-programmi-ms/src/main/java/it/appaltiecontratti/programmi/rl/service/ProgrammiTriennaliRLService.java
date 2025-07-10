package it.appaltiecontratti.programmi.rl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.appaltiecontratti.programmi.entity.pubblicazioni.*;
import it.appaltiecontratti.programmi.rl.dto.*;
import it.appaltiecontratti.programmi.rl.mapper.SqlUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgrammiTriennaliRLService extends ARestService<LavoriDto, PubblicaProgrammaLavoriSCPEntry>{

    private static final Logger logger = LogManager.getLogger(ProgrammiTriennaliRLService.class);

    ProgrammiTriennaliRLService(RestTemplate restTemplate, SqlUtil sqlUtil) {
        super(restTemplate,"/programmitriennali",sqlUtil);
    }

    @Override
    public String getJson(PubblicaProgrammaLavoriSCPEntry programma, Map<String,Object> params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper(programma, params));
        logger.debug(">>>");
        logger.debug(json);
        logger.debug(">>>");
        return json;
    }


    @Override
    public LavoriDto mapper(PubblicaProgrammaLavoriSCPEntry programma, Map<String, Object> params) {
        LavoriDto programmazione = new LavoriDto();

        // Dati base
        if (programma.getIdRicevuto() != null && programma.getIdRicevuto() > 0) {
            programmazione.setId(programma.getIdRicevuto());
        }

        DatiGeneraliTecnicoPubbEntry referente = programma.getReferente();
        int annoInizio = programma.getAnno().intValue();
        programmazione.setResponsabileNome(referente.getNome());
        programmazione.setResponsabileCognome(referente.getCognome());
        programmazione.setResponsabileCdFiscale(referente.getCfPiva());
        programmazione.setAnno(annoInizio);
        programmazione.setEnteCdFiscale(programma.getCodiceFiscaleSA());
        //programmazione.setStato("INSERITO"); // OMESSO

        // Dati Trasmissione
        DatiTrasmissioneDto datiTrasmissione = new DatiTrasmissioneDto();
        datiTrasmissione.setNumeroAdozione(programma.getNumeroAdozione());
        datiTrasmissione.setUrlAdozione(programma.getUrlAttoAdozione());
        datiTrasmissione.setDataAdozione(programma.getDataAdozione());
        datiTrasmissione.setDataPubblicazioneAdozione(programma.getDataPubblicazioneAdozione());
        datiTrasmissione.setNumeroApprovazione(programma.getNumeroApprovazione());
        datiTrasmissione.setUrlApprovazione(programma.getUrlAttoApprovazione());
        datiTrasmissione.setDataApprovazione(programma.getDataApprovazione());
        datiTrasmissione.setDataPubblicazioneApprovazione(programma.getDataPubblicazioneApprovazione());
        programmazione.setDatiTrasmissione(datiTrasmissione);

        params.put(PARAM_PROG_ANNO_INIZIO,annoInizio);

        // Lista Opere Incompiute
        List<OperaIncompiutaDto> listaOpereIncompiute = programma.getOpereIncompiute().stream()
                .map(r -> {
                    return mapperOperaIncompiuta(r,params);
                })
                .collect(Collectors.toList());
        programmazione.setListaOpereIncompiute(listaOpereIncompiute);

        // Lista Immobili
        List<ImmobileDto> listaImmobili=new ArrayList<>();
        programma.getInterventi().forEach(r -> {
                r.getImmobili().forEach(t->{
                    listaImmobili.add(mapperImmobile(t,r,null));
                });
            });
        programma.getOpereIncompiute().forEach(r -> {
            r.getImmobili().forEach(t->{
                listaImmobili.add(mapperImmobile(t,null,r));
            });
        });
        programmazione.setListaImmobili(listaImmobili);

        // Lista Interventi
        List<InterventoDto> listaInterventi = programma.getInterventi().stream()
                .map(r -> mapperIntervento(r,params))
                .collect(Collectors.toList());
        programmazione.setListaInterventi(listaInterventi);

        // Lista Interventi Non Ricompresi
        List<InterventoNonRipropostoDto> listaInterventiNonRiproposti = programma.getInterventiNonRiproposti().stream()
                .map(this::mappeInterrventoNonRipoposto)
                .collect(Collectors.toList());
        programmazione.setListaInterventiNonRicompresi(listaInterventiNonRiproposti);

        return programmazione;
    }

    private OperaIncompiutaDto mapperOperaIncompiuta(OperaIncompiutaPubbEntry o, Map<String, Object> params){
        OperaIncompiutaDto operaIncompiutaDto=new OperaIncompiutaDto();

        if (StringUtils.hasText(o.getCup())) {
            operaIncompiutaDto.setFlSoggettoACup("1");
            operaIncompiutaDto.setCup(o.getCup());
        } else operaIncompiutaDto.setFlSoggettoACup("0");

        operaIncompiutaDto.setDescrizione(o.getDescrizione());
        operaIncompiutaDto.setIdDeterminazione(mapToId(o.getDeterminazioneAmministrazione(),Map.of(
                "a", 1,
                "b", 2,
                "c", 3,
                "d", 4
        )));
        operaIncompiutaDto.setIdAmbito(mapToId(o.getAmbito(),Map.of(
                "a", 1,
                "b", 2,
                "c", 3,
                "d", 4
        )));
        operaIncompiutaDto.setAnnoQuadro(toIntOrNull(o.getAnno()));
        operaIncompiutaDto.setImportoIntervento(toLongOrZero(o.getImportoIntervento()));
        operaIncompiutaDto.setImportoLavori(toLongOrZero(o.getImportoLavori()));
        operaIncompiutaDto.setImportoOneriLavori(toLongOrZero(o.getOneri()));
        operaIncompiutaDto.setImportoSal(toLongOrZero(o.getImportoAvanzamento()));
        operaIncompiutaDto.setIdCausa(mapToId(o.getCausa(),Map.of(
                "a", 1,
                "b1", 2,
                "b2", 3,
                "c", 4,
                "d", 5,
                "e", 6
        )));
        operaIncompiutaDto.setFlOperaFruibile(sN(o.getFruibile()));
        operaIncompiutaDto.setIdstatoRealizzazione(mapToId(o.getStato(),Map.of(
                "a", 1,
                "b", 2,
                "c", 3
        )));
        operaIncompiutaDto.setFlUsoRidimensionato(sN(o.getRidimensionato()));
        operaIncompiutaDto.setIdDestinazione(mapToId(o.getDestinazioneUso(),Map.of(
                "a", 1,
                "b", 2
        )));
        operaIncompiutaDto.setFlCessione(sN(o.getCessione()));

        if (StringUtils.hasText(o.getPrevistaVendita())) {
            if (o.getPrevistaVendita().charAt(0) == '1') operaIncompiutaDto.setFlDemolizione("1");
        }
        if (!StringUtils.hasText(operaIncompiutaDto.getFlDemolizione())){
            if (StringUtils.hasText(o.getDemolizione())) {
                if (o.getDemolizione().charAt(0) == '2') operaIncompiutaDto.setFlDemolizione("0");
                else if (o.getDemolizione().charAt(0) == '1') operaIncompiutaDto.setFlDemolizione("2");
            }
        }

        operaIncompiutaDto.setImportoOneriRiqualificazione(toLongOrZero(o.getOneriSito()));
        operaIncompiutaDto.setFlInfrastrutturaRete(sN(o.getInfrastruttura()));
        operaIncompiutaDto.setFlDiscontinuitaRete(sN(o.getDiscontinuitaRete()));

        AltriDatiOperaIncompiutaPubbEntry a=o.getAltriDati();
        operaIncompiutaDto.setMisuraDimensionamento(a.getUnitaMisura());
        operaIncompiutaDto.setValoreDimensionamento(toIntOrNull(a.getDimensione()));
        operaIncompiutaDto.setFlRequisitiCapitolato(sN(a.getRequisitiCapitolato()));
        operaIncompiutaDto.setFlRequisitiProgetto(sN(a.getRequisitiApprovato()));
        operaIncompiutaDto.setFlSponsorizzazione(sN(a.getSponsorizzazione()));
        operaIncompiutaDto.setFlFinanzaProgetto(sN(a.getFinanzaDiProgetto()));

        operaIncompiutaDto.setCostoProgetto(toLongOrNull(a.getCostoProgetto()));
        operaIncompiutaDto.setFinanziamento(toLongOrNull(a.getFinanziamento()));

        Triple<String,String,String> resultIstat=getInfoFromIstat(a.getIstat());
        operaIncompiutaDto.setCdRegione(resultIstat.getLeft());
        operaIncompiutaDto.setSiglaProvincia(resultIstat.getMiddle());
        operaIncompiutaDto.setCdCatastale(resultIstat.getRight());

        operaIncompiutaDto.setCodiceNUTS(a.getNuts());
        operaIncompiutaDto.setIdTipologia(toIntOrNull(a.getTipologiaIntervento()));

        Pair<Integer,Integer> resultCategoria=getInfoFromCategoria(a.getCategoriaIntervento());
        operaIncompiutaDto.setIdSettoreIntervento(resultCategoria.getLeft());
        operaIncompiutaDto.setIdSottoSettore(resultCategoria.getRight());

        operaIncompiutaDto.setFlComunitaria(sN(a.getCoperturaComunitaria()));
        operaIncompiutaDto.setFlStatale(sN(a.getCoperturaStatale()));
        operaIncompiutaDto.setFlRegionale(sN(a.getCoperturaRegionale()));
        operaIncompiutaDto.setFlProvinciale(sN(a.getCoperturaProvinciale()));
        operaIncompiutaDto.setFlComunale(sN(a.getCoperturaComunale()));
        operaIncompiutaDto.setFlAltraPubblica(sN(a.getCoperturaAltro()));
        operaIncompiutaDto.setFlPrivata(sN(a.getCoperturaPrivata()));

        // o.getPercentualeAvanzamento() NON ESISTE IN OI RL
        // operaIncompiutaDto.setNote(o.); NIENTE NOTE
        return operaIncompiutaDto;
    }


    private ImmobileDto mapperImmobile(ImmobilePubbEntry i, InterventoPubbEntry intervento, OperaIncompiutaPubbEntry operaIncompiuta){
        logger.debug(">>> CUI="+i.getCui());
        ImmobileDto immobileDto=new ImmobileDto();
        immobileDto.setCui(i.getCui());
        if (intervento!=null){
            logger.debug(">>> CUI-Intervento="+intervento.getCui());
            immobileDto.setCuiIntervento(intervento.getCui()); // RELATIVO ALL'INTERVENTO
        }else if (operaIncompiuta!=null){
            logger.debug(">>> CUP-OperaIncompiuta="+operaIncompiuta.getCup());
            immobileDto.setCupRiferimento(operaIncompiuta.getCup()); // RELATIVO ALL'OPERA INCOMPIUTA
        }

        immobileDto.setDescrizioneImmobile(i.getDescrizione());

        Triple<String,String,String> resultIstat=getInfoFromIstat(i.getIstat());
        immobileDto.setCdRegione(resultIstat.getLeft());
        immobileDto.setSiglaProvincia(resultIstat.getMiddle());
        immobileDto.setCdCatastale(resultIstat.getRight());

        immobileDto.setCodiceNuts(i.getNuts());
        immobileDto.setIdCessione(i.getTrasferimentoTitoloCorrispettivo());
        immobileDto.setIdConcessione(i.getImmobileDisponibile());

        // NON E' MAI VALORIZZATO i.getAlienati()
        if (StringUtils.hasText(i.getAlienati())) {
            if (i.getAlienati().charAt(0) == '2') immobileDto.setFlAlienato("0"); //0 = NO, 1 = SI
            else if (i.getAlienati().charAt(0) == '1') immobileDto.setFlAlienato("1");
        }else{
            immobileDto.setFlAlienato("0");
        }

        immobileDto.setIdDismissione(i.getInclusoProgrammaDismissione());
        immobileDto.setIdDisponibilita(i.getTipoDisponibilita());

        immobileDto.setImportoAnno1(toLongOrZero(i.getValoreStimato1()));
        immobileDto.setImportoAnno2(toLongOrZero(i.getValoreStimato2()));
        immobileDto.setImportoAnno3(toLongOrZero(i.getValoreStimato3()));
        immobileDto.setImportoAnnoSuccessivo(toLongOrZero(i.getValoreStimatoSucc()));
        return immobileDto;
    }

    private InterventoDto mapperIntervento(InterventoPubbEntry r, Map<String, Object> params) {
        InterventoDto dto = new InterventoDto();

        dto.setCui(r.getCui());
        dto.setCodIntAmministrazione(r.getCodiceInterno()); // VERIFICARE
        if (StringUtils.hasText(r.getEsenteCup())) {
            if (r.getEsenteCup().charAt(0) == '1') dto.setFlSoggettoACup("0"); //0 = NO, 1 = SI
            else if (r.getEsenteCup().charAt(0) == '2') dto.setFlSoggettoACup("1");
        }

        dto.setCup(r.getCup());
        Integer annoInizio = (Integer) params.get(PARAM_PROG_ANNO_INIZIO);
        dto.setAnno(annoInizio + r.getAnno().intValue() - 1);

        DatiGeneraliTecnicoPubbEntry rup = r.getRup();
        dto.setNomeProf(rup.getNome());
        dto.setCognomeProf(rup.getCognome());
        dto.setCfProf(rup.getCfPiva());
        dto.setEmailProf(rup.getMail());
        dto.setNazioneProf((String) params.get(PARAM_RUP_NAZIONE));

        /* INIZIO - Determinazione informazioni RUP*/
        String codeinSA=(String) params.get(PARAM_SA_CODICE);
        Map<String,Object> infoSA= sqlUtil.getInfoFromCodiceSA(codeinSA);
        String codiceIstatSA="";
        String emailSA="";
        if (!infoSA.isEmpty()) {
            if (infoSA.get("emaiin") != null) emailSA = (String) infoSA.get("emaiin");
            if (infoSA.get("codcit") != null) codiceIstatSA = (String) infoSA.get("codcit");
        }
        logger.debug("codeinSA="+codeinSA+" emailSA="+emailSA+ " codiceIstatSA="+codiceIstatSA);
        String codiceIstat=rup.getLuogoIstat();
        logger.debug(dto.getCfProf()+" - luogoIstat: "+codiceIstat);
        dto.setNazioneProf((String) params.get(PARAM_RUP_NAZIONE));
        if (!StringUtils.hasText(codiceIstat)) codiceIstat=codiceIstatSA;
        logger.debug(">>> codiceIstat="+codiceIstat);
        String codiceCatastale=sqlUtil.getCodiceCatastaleComuneFromCodiceIstat(codiceIstat);
        logger.debug(">>> codiceCatastale="+codiceCatastale);
        dto.setCdCatastaleProf(codiceCatastale);
        /* Valutazione email RUP */
        if (!StringUtils.hasText(dto.getEmailProf())) {
            if (StringUtils.hasText(emailSA))
                dto.setEmailProf(emailSA);
            else
                dto.setEmailProf("--");
        }
        logger.debug(">>> emailProf="+dto.getEmailProf());
        /* FINE - Determinazione informazioni RUP*/

        dto.setFlLottoFunzionale(sN(r.getLottoFunzionale()));
        dto.setFlLavoroComplesso(sN(r.getLavoroComplesso()));
        Triple<String,String,String> resultIstat=getInfoFromIstat(r.getIstat());
        dto.setCdRegione(resultIstat.getLeft());
        dto.setSiglaProvincia(resultIstat.getMiddle());
        dto.setCdCatastale(resultIstat.getRight());
        dto.setCodiceNUTS(r.getNuts());
        dto.setIdTipoIntervento(toIntOrNull(r.getTipologia()));

        Pair<Integer,Integer> resultCategoria=getInfoFromCategoria(r.getCategoria());
        dto.setIdSettore(resultCategoria.getLeft());
        dto.setIdSottoSettore(resultCategoria.getRight());

        dto.setDescrizione(r.getDescrizione());
        dto.setIdLivelloPriorita(r.getPriorita());

        dto.setRisorseVincolatePerLegge1(toLongOrZero(r.getRisorseVincolatePerLegge1()));
        dto.setRisorseVincolatePerLegge2(toLongOrZero(r.getRisorseVincolatePerLegge2()));
        dto.setRisorseVincolatePerLegge3(toLongOrZero(r.getRisorseVincolatePerLegge3()));
        dto.setRisorseVincolatePerLeggeSucc(toLongOrZero(r.getRisorseVincolatePerLeggeSucc()));
        dto.setRisorseMutuo1(toLongOrZero(r.getRisorseMutuo1()));
        dto.setRisorseMutuo2(toLongOrZero(r.getRisorseMutuo2()));
        dto.setRisorseMutuo3(toLongOrZero(r.getRisorseMutuo3()));
        dto.setRisorseMutuoSucc(toLongOrZero(r.getRisorseMutuoSucc()));
        dto.setRisorsePrivati1(toLongOrZero(r.getRisorsePrivati1()));
        dto.setRisorsePrivati2(toLongOrZero(r.getRisorsePrivati2()));
        dto.setRisorsePrivati3(toLongOrZero(r.getRisorsePrivati3()));
        dto.setRisorsePrivatiSucc(toLongOrZero(r.getRisorsePrivatiSucc()));
        dto.setRisorseBilancio1(toLongOrZero(r.getRisorseBilancio1()));
        dto.setRisorseBilancio2(toLongOrZero(r.getRisorseBilancio2()));
        dto.setRisorseBilancio3(toLongOrZero(r.getRisorseBilancio3()));
        dto.setRisorseBilancioSucc(toLongOrZero(r.getRisorseBilancioSucc()));
        dto.setRisorseArt31(toLongOrZero(r.getRisorseArt3_1()));
        dto.setRisorseArt32(toLongOrZero(r.getRisorseArt3_2()));
        dto.setRisorseArt33(toLongOrZero(r.getRisorseArt3_3()));
        dto.setRisorseArt3Succ(toLongOrZero(r.getRisorseArt3_Succ()));
        dto.setRisorseImmobili1(toLongOrZero(r.getRisorseImmobili1()));
        dto.setRisorseImmobili2(toLongOrZero(r.getRisorseImmobili2()));
        dto.setRisorseImmobili3(toLongOrZero(r.getRisorseImmobili3()));
        dto.setRisorseImmobiliSucc(toLongOrZero(r.getRisorseImmobiliSucc()));
        dto.setRisorseAltro1(toLongOrZero(r.getRisorseAltro1()));
        dto.setRisorseAltro2(toLongOrZero(r.getRisorseAltro2()));
        dto.setRisorseAltro3(toLongOrZero(r.getRisorseAltro3()));
        dto.setRisorseAltroSucc(toLongOrZero(r.getRisorseAltroSucc()));
        dto.setSpese(toLongOrZero(r.getSpeseSostenute()));
        logger.debug(">>> ");
        logger.debug(">>> "+r.getScadenzaFinanziamento());
        logger.debug(">>> ");
        dto.setScadenzaTemporale(r.getScadenzaFinanziamento());
        dto.setIdCapitalePrivato(toIntOrNull(r.getTipologiaCapitalePrivato()));
        dto.setIdInterventoAggiunto(r.getVariato());
        dto.setIdFinalita(mapToId(r.getFinalita(),Map.of(
                "ADN", 1,
                "AMB", 2,
                "COP", 3,
                "CPA", 4,
                "MIS", 5,
                "URB", 6,
                "VAB", 7,
                "DEM", 8,
                "DEOP", 9
        )));
        dto.setFlConformita(sN(r.getConformitaUrbanistica()));
        dto.setFlVerificaAmbientale(sN(r.getConformitaAmbientale()));
        dto.setIdProgettazione(toIntOrNull(r.getStatoProgettazione()));
        dto.setFlCentraleCommittenza(sN(r.getDelega()));
        dto.setCodiceAUSA(r.getCodiceSoggettoDelegato());
        dto.setDenominazione(r.getNomeSoggettoDelegato());
        if (StringUtils.hasText(r.getNote()))
            dto.setNote(r.getNote().length() > 499 ? r.getNote().substring(0, 499) : r.getNote());
        return dto;
    }

    private InterventoNonRipropostoDto mappeInterrventoNonRipoposto(InterventoNonRipropostoPubbEntry r) {
        InterventoNonRipropostoDto dto = new InterventoNonRipropostoDto();
        dto.setCui(r.getCui());
        dto.setMotivo(r.getMotivo());
        return dto;
    }

}

