package it.appaltiecontratti.programmi.rl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.appaltiecontratti.programmi.entity.pubblicazioni.AcquistoNonRipropostoPubbEntry;
import it.appaltiecontratti.programmi.entity.pubblicazioni.AcquistoPubbEntry;
import it.appaltiecontratti.programmi.entity.pubblicazioni.DatiGeneraliTecnicoPubbEntry;
import it.appaltiecontratti.programmi.entity.pubblicazioni.PubblicaProgrammaFornitureServiziSCPEntry;
import it.appaltiecontratti.programmi.rl.dto.AcquistoDto;
import it.appaltiecontratti.programmi.rl.dto.AcquistoNonRipropostoDto;
import it.appaltiecontratti.programmi.rl.dto.DatiTrasmissioneDto;
import it.appaltiecontratti.programmi.rl.dto.FornitureAcquistiDto;
import it.appaltiecontratti.programmi.rl.mapper.SqlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgrammiBiennaliRLService extends ARestService<FornitureAcquistiDto,PubblicaProgrammaFornitureServiziSCPEntry> {

    private static final Logger logger = LogManager.getLogger(ProgrammiBiennaliRLService.class);

    ProgrammiBiennaliRLService(RestTemplate restTemplate, SqlUtil sqlUtil) {
        super(restTemplate, "/programmibiennali", sqlUtil);
    }

    @Override
    public String getJson(PubblicaProgrammaFornitureServiziSCPEntry programma, Map<String, Object> params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper(programma, params));
        logger.debug(">>>");
        logger.debug(json);
        logger.debug(">>>");
        return json;
    }

    @Override
    public FornitureAcquistiDto mapper(PubblicaProgrammaFornitureServiziSCPEntry programma, Map<String, Object> params) {
        FornitureAcquistiDto programmazione = new FornitureAcquistiDto();

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
        //datiTrasmissione.setNumeroAdozione(); // OMESSO
        //datiTrasmissione.setUrlAdozione(); // OMESSO
        //datiTrasmissione.setDataAdozione(); // OMESSO
        //datiTrasmissione.setDataPubblicazioneAdozione(); // OMESSO
        datiTrasmissione.setNumeroApprovazione(programma.getNumeroApprovazione());
        datiTrasmissione.setUrlApprovazione(programma.getUrlAttoApprovazione());
        datiTrasmissione.setDataApprovazione(programma.getDataApprovazione());
        datiTrasmissione.setDataPubblicazioneApprovazione(programma.getDataPubblicazioneApprovazione());
        programmazione.setDatiTrasmissione(datiTrasmissione);

        params.put(PARAM_PROG_ANNO_INIZIO,annoInizio);

        // Lista Acquisti
        List<AcquistoDto> listaAcquisti = programma.getAcquisti().stream()
                .map(r -> mapperAcquisti(r,params))
                .collect(Collectors.toList());
        programmazione.setListaAcquisti(listaAcquisti);

        // Lista Acquisti Non Ricompresi
        List<AcquistoNonRipropostoDto> listaAcquistiNonRiproposti = programma.getAcquistiNonRiproposti().stream()
                .map(this::mapperAcquistoNonRipoposto)
                .collect(Collectors.toList());
        programmazione.setListaAcquistiNonRiproposti(listaAcquistiNonRiproposti);

        return programmazione;
    }

    private AcquistoDto mapperAcquisti(AcquistoPubbEntry r, Map<String, Object> params) {
        AcquistoDto dto = new AcquistoDto();

        Integer annoInizio = (Integer) params.get(PARAM_PROG_ANNO_INIZIO);
        dto.setAnno(annoInizio + r.getAnno() - 1);
        dto.setCui(r.getCui());
        if (StringUtils.hasText(r.getEsenteCup())) {
            if (r.getEsenteCup().charAt(0) == '1') dto.setFlSoggettoACup("0"); //0 = NO, 1 = SI
            else if (r.getEsenteCup().charAt(0) == '2') dto.setFlSoggettoACup("1");
        }
        dto.setCup(r.getCup());
        dto.setIdAcquistoRicompreso(r.getAcquistoRicompreso());
        dto.setCuilavoro(r.getCui());  // OBBLIGATORIO SE idAcquistoRicompreso=1
        dto.setFlLottoFunzionale(sN(r.getLottoFunzionale()));
        dto.setCodiceNUTS(r.getNuts());
        dto.setCodiceCpvSpc(r.getCpv());

        if (StringUtils.hasText(r.getSettore()))
            if (r.getSettore().equals("F")) dto.setIdSettore(1); // 1=FORNITURE 2=SERVIZI
            else if (r.getSettore().equals("S")) dto.setIdSettore(2);

        dto.setDescrizioneAcquisto(r.getDescrizione());
        dto.setIdLivelloPriorita(r.getPriorita()); // 1=PRIORITÀ MASSIMA  2=PRIORITÀ MEDIA  3=PRIORITÀ MINIMA
        dto.setDurataDelContratto(r.getDurataInMesi());
        dto.setFlnuovoAffidamento(sN(r.getNuovoAffidamento()));

        DatiGeneraliTecnicoPubbEntry rup = r.getRup();
        dto.setNomeProf(rup.getNome());
        dto.setCognomeProf(rup.getCognome());
        dto.setCfProf(rup.getCfPiva());
        dto.setEmailProf(rup.getMail());

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

        dto.setIdCapitalePrivato(toIntOrNull(r.getTipologiaCapitalePrivato()));
        dto.setFlCentraleCommittenza(sN(r.getDelega()));
        dto.setCodiceAUSA(r.getCodiceSoggettoDelegato());
        dto.setDenominazioneAUSA(r.getNomeSoggettoDelegato());
        dto.setIdAcquistoAggVar(r.getVariato());
        dto.setSpese(toLongOrZero(r.getSpeseSostenute()));

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
        if (StringUtils.hasText(r.getNote()))
            dto.setNote(r.getNote().length() > 499 ? r.getNote().substring(0, 499) : r.getNote());
        return dto;
    }

    private AcquistoNonRipropostoDto mapperAcquistoNonRipoposto(AcquistoNonRipropostoPubbEntry r) {
        AcquistoNonRipropostoDto dto = new AcquistoNonRipropostoDto();
        dto.setCui(r.getCui());
        dto.setMotivo(r.getMotivo());
        return dto;
    }

}
