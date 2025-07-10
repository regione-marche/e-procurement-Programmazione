package it.appaltiecontratti.monitoraggiocontratti.contratti.utils;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.WGenChiaviManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.SchedePcpManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LogEventiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.ImpresaAggiudicatariaInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SchedePcpUtils{

    /** Logger di classe. */
    private static final Logger logger = LogManager.getLogger(SchedePcpUtils.class);

    @Autowired
    private ContrattiMapper contrattiMapper;

    @Autowired
    protected WGenChiaviManager wgcManager;

    @Autowired
    private SqlMapper sqlMapper;

    private static final double MAX_PERCENTUALE_AMMISSIBILE = 10000.0;

    // Funzione di conversione da OffsetDateTime a Date
    public static Date offsetDateTimeToDate(OffsetDateTime offsetDateTime) {
        if(offsetDateTime != null){
            LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            Instant istante = zonedDateTime.toInstant();
            Date date = Date.from(istante);
            return date;
        }
        return null;
    }

    public void insertLogEventi(Long syscon, String codProfilo, String codEvento, String descr, String errmsg, Short livEvento) {
        try {

            LogEventiEntry entry = new LogEventiEntry();
            entry.setIdevento(wgcManager.getNextId("W_LOGEVENTI"));
            entry.setCodapp("W9");
            entry.setSyscon(syscon);
            entry.setCodProfilo(codProfilo);
            entry.setDataora(new Date());
            entry.setCodevento(codEvento);
            entry.setDescr(descr);
            entry.setErrmsg(errmsg);
            entry.setLivevento(livEvento);
            contrattiMapper.insertLogEventi(entry);
        }catch (Exception e) {
            throw e;
        }
    }

    public void deleteDatiSchedeEse(Long codGara, String codiceScheda) {
        try {
            // elimino I1
            this.contrattiMapper.deleteW9FasePcp(codGara, 2L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 2L);
            this.contrattiMapper.deleteFaseInizPubbEsitoAll(codGara);

            // elimino SA1
            this.contrattiMapper.deleteFaseAvanzamentoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 3L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 3L);

            // elimino CO1
            this.contrattiMapper.deleteFaseConclusioneContrattoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 4L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 4L);

            // elimino CO2
            //per eliminare la CO2 devo cancellare i record su w9avan , w9conc, w9iniz ma viene già fatto
            if(codiceScheda.equals("AD5")) {
                this.contrattiMapper.deleteFaseInizialeEsecuzionePcp(codGara);
            }
            this.contrattiMapper.deleteW9FasePcp(codGara, 19L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 19L);

            // elimino CL1
            this.contrattiMapper.deleteFaseCollaudoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 5L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 5L);

            // elimino AC1
            this.contrattiMapper.deleteFaseAccordoBonarioAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 8L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 8L);

            // elimino IR1
            this.contrattiMapper.deleteFaseIstanzaRecessoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 10L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 10L);

            // elimino M1 M1-40 M2 M2-40
            this.contrattiMapper.deleteFaseVariantiAll(codGara);
            this.contrattiMapper.deleteFaseVariantiMotiAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 7L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 7L);

            // elimino SO1
            this.contrattiMapper.deleteFaseSospensioneAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 6L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 6L);

            // elimino SQ1
            this.contrattiMapper.deleteW9FasePcp(codGara, 14L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 14L);

            // elimino RI1
            this.contrattiMapper.deleteW9FasePcp(codGara, 15L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 15L);

            // elimino RSU1
            this.contrattiMapper.deleteFaseSubappaltoAll(codGara);
            this.contrattiMapper.deleteW9FaseSubaImprAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 16L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 16L);

            // elimino ES1
            this.contrattiMapper.deleteW9FasePcp(codGara, 17L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 17L);

            // elimino CS1
            this.contrattiMapper.deleteW9FasePcp(codGara, 18L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 18L);

            // elimino S4
            this.contrattiMapper.deleteFaseVariazioneAggiudicatariAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 21L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 21L);

        } catch (Exception e) {
            logger.error("Errore nel metodo deleteDatiSchede");
            throw e;
        }

    }

    public void inserisciW9flussi(Long codGara, Long codLotto,String codiceSA, Long syscon, Long numProg, Long tipoInvio, Date dataInvio) throws Exception {

        FlussoEntry flusso = new FlussoEntry();
        try {
            String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(codiceSA);
            Long idFlusso = wgcManager.getNextId("W9FLUSSI");
            flusso.setId(idFlusso);
            flusso.setArea(1L);
            flusso.setKey01(codGara);
            flusso.setKey02(codLotto);
            flusso.setKey03(numProg);
            flusso.setKey04(1L);
            flusso.setTipoInvio(tipoInvio);
            if(dataInvio != null) {
                flusso.setDataInvio(dataInvio);
            } else {
                flusso.setDataInvio(new Date());
            }

            flusso.setCodiceFiscaleSA(cfStazioneAppaltante);
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            this.contrattiMapper.insertFlusso(flusso);

        } catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: inserisciW9flussi" , e);
            throw e;
        }
    }

    public void inserisciW9flussiSchedeEse(Long codGara, Long codLotto,String codiceSA, Long syscon, Long numFase, Long num, Long tipoInvio, Date dataInvio,String schedaJson, String govwayMessageId, String govwayTransactionId) throws Exception {

        FlussoEntry flusso = new FlussoEntry();
        try {
            String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(codiceSA);
            Long idFlusso = wgcManager.getNextId("W9FLUSSI");
            flusso.setId(idFlusso);
            flusso.setArea(1L);
            flusso.setKey01(codGara);
            flusso.setKey02(codLotto);
            flusso.setKey03(numFase);
            flusso.setKey04(num);
            flusso.setTipoInvio(tipoInvio);
            if(dataInvio != null) {
                flusso.setDataInvio(dataInvio);
            } else {
                flusso.setDataInvio(new Date());
            }

            flusso.setCodiceFiscaleSA(cfStazioneAppaltante);
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));

            flusso.setJson(schedaJson);
            flusso.setGovwayMessageId(govwayMessageId);
            flusso.setGovwayTransactionId(govwayTransactionId);
            this.contrattiMapper.insertFlusso(flusso);

        } catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: inserisciW9flussi" , e);
            throw e;
        }
    }

    public static Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        GregorianCalendar gregorianCalendar = xmlGregorianCalendar.toGregorianCalendar();
        return gregorianCalendar.getTime();
    }

    public static double safeSum(Double... values) {
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
    
    public static boolean isValidNumeric139(Double value) {
        if (value == null) {
            return false;
        }

        final double MAX_VALUE = 9999.999999999;
        final double MIN_VALUE = -9999.999999999;

        // Controlla se il valore è nel range consentito
        if (value < MIN_VALUE || value > MAX_VALUE) {
            return false;
        }

        // Controlla se il numero di cifre decimali non supera 9
        String[] parts = value.toString().split("\\.");
        if (parts.length > 1 && parts[1].length() > 9) {
            return false;
        }

        // Controlla se il numero totale di cifre non supera 13
        String valueString = value.toString().replace(".", "").replace("-", "");
        if (valueString.length() > 13) {
            return false;
        }

        return true;
    }

    public void inserisciAggiudicazioneW9fasi(Long codGara, Long codLotto, String idScheda) {
        try {
            this.contrattiMapper.insertW9fase(codGara, codLotto,1L,1L,null, 1L,idScheda);
            this.contrattiMapper.setW9faseExported(codGara, codLotto, 1L, 1L);
        } catch (Exception e) {
            logger.error("Errore in fase di importazione gara pcp: inserisciAggiudicazioneW9fasi");
            throw e;
        }

    }

    public void inserisciAggiudicazioneW9Aggi(Long codGara, Long codLotto, String idPartecipante, String codImp, Long tipoRaggruppamento,Long tipo, Long ruolo, Double importo, Double ribasso, Double offAumento, Long numAggi) {
        try {
            ImpresaAggiudicatariaInsertForm imp = new ImpresaAggiudicatariaInsertForm();
            imp.setCodGara(codGara);
            imp.setCodLotto(codLotto);
            imp.setNumAppa(1L);
            imp.setNumAggi(numAggi);
            imp.setCodImpresa(codImp);
            imp.setIdTipoAgg(tipo);
            imp.setRuolo(ruolo);
            imp.setIdGruppo(tipoRaggruppamento);
            imp.setImportoAggiudicazione(importo);
            imp.setRibassoAggiudicazione(ribasso);
            imp.setOffertaAumento(offAumento);
            imp.setIdPartecipante(idPartecipante);
            this.contrattiMapper.insertImpresaAggiudicataria(imp);
        } catch (Exception e) {
            logger.error("Errore in fase di importazione gara pcp: inserisciAggiudicazioneW9Aggi");
            throw e;
        }


    }

    public String calculateFlagEnteSpeciale(String codScheda) {
        if(codScheda.equals(FasiPcp.SCHEDAP111) ||
                codScheda.equals(FasiPcp.SCHEDAP113) ||
                codScheda.equals(FasiPcp.SCHEDAP1151) ||
                codScheda.equals(FasiPcp.SCHEDAP1152) ||
                codScheda.equals(FasiPcp.SCHEDAP117) ||
                codScheda.equals(FasiPcp.SCHEDAP121) ||
                codScheda.equals(FasiPcp.SCHEDAP124) ||
                codScheda.equals(FasiPcp.SCHEDAP211) ||
                codScheda.equals(FasiPcp.SCHEDAP213) ||
                codScheda.equals(FasiPcp.SCHEDAP217) ||
                codScheda.equals(FasiPcp.SCHEDAP221) ||
                codScheda.equals(FasiPcp.SCHEDAP224) ||
                codScheda.equals(FasiPcp.SCHEDAAD126) ||
                codScheda.equals(FasiPcp.SCHEDAAD226)) {
            return Constants.FLAG_ENTE_SPECIALE_S;
        } else if(codScheda.equals(FasiPcp.SCHEDAAD3) ||
                codScheda.equals(FasiPcp.SCHEDAAD4) ||
                codScheda.equals(FasiPcp.SCHEDAAD5)) {
            return null;
        } else {
            return Constants.FLAG_ENTE_SPECIALE_O;
        }

    }

    public Long calculateTipoApp(String codScheda, String tipologia) {
        if(codScheda.equals(FasiPcp.SCHEDAP114) ||
                codScheda.equals(FasiPcp.SCHEDAP119) ||
                codScheda.equals(FasiPcp.SCHEDAP214) ||
                codScheda.equals(FasiPcp.SCHEDAP219)) {
            if(tipologia.equals("L")) {
                return 3L;
            } else if(tipologia.equals("F") || tipologia.equals("S")) {
                return 4L;
            }
        } else if(codScheda.equals(FasiPcp.SCHEDAAD4)) {
            return 11L;
        } else {
            return 1L;
        }
        return 1L;

    }

    public List<Map<String, Object>> filtraS2eS2R(List<Map<String, Object>> schedeAgg) {
        Map<String, Object> ultimoS2R = null;

        boolean esisteS2R = false;
        List<Map<String, Object>> risultato = new ArrayList<>();

        // Primo passaggio: Identifica l'esistenza di "S2" e salva l'ultimo "S2R"
        for (Map<String, Object> agg : schedeAgg) {
            String codice = (String) ((Map<String, Object>) agg.get("codice")).get("codice");

            if ("S2R".equals(codice)) {
                esisteS2R = true;
                ultimoS2R = agg; // Mantiene solo l'ultimo "S2R"
            }
        }

        // Secondo passaggio: Costruisce la lista filtrata
        for (Map<String, Object> agg : schedeAgg) {
            String codice = (String) ((Map<String, Object>) agg.get("codice")).get("codice");

            if ("S2".equals(codice) && esisteS2R) {
                continue; // Saltiamo "S2" se esiste almeno un "S2R"
            }

            if ("S2R".equals(codice) && agg != ultimoS2R) {
                continue; // Manteniamo solo l'ultimo "S2R"
            }

            risultato.add(agg);
        }

        return risultato;
    }


}
