package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.ElaborateAppaltiManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.ElaborateSchedeManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v1021.ElaborateAppaltiManagerV1021;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v1021.ElaborateSchedeManagerV1021;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseElaborateAppaltoPcp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseFaseImprese;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseImprAggiudicatarie;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.FasiPcp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.SchedePcpUtils;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseAppaltoPcp;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component(value = "ElaborateSchedeGeneralManager")
public class ElaborateSchedeGeneralManager  extends AbstractManager{

    /** Logger di classe. */
    private static final Logger logger = LogManager.getLogger(ElaborateSchedeGeneralManager.class);

    @Autowired
    private ContrattiMapper contrattiMapper;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    protected WGenChiaviManager wgcManager;

    @Autowired
    protected FasiManager fasiManager;

    @Autowired
    private SchedePcpUtils schedePcpUtils;

    @Autowired
    private ElaborateSchedeManagerV102 elaborateSchedePcpV102;

    @Autowired
    private ElaborateAppaltiManagerV102 elaborateAppaltiPcpV102;

    @Autowired
    private ElaborateSchedeManagerV1021 elaborateSchedePcpV1021;

    @Autowired
    private ElaborateAppaltiManagerV1021 elaborateAppaltiPcpV1021;


    private static final ObjectMapper objectMapper;



    private String idPartecipante = null;

    private Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<>();


    private static final String LOG_EVENTI_SCHEDA_PCP_COD_EVENTO = "SCHEDA_PCP";
    private static final String LOG_EVENTI_APPALTO_PCP_COD_EVENTO = "APPALTO_PCP";

    private Long numIncarico = null;

    private Long idIncarico = null;

    static {

        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ResponseElaborateAppaltoPcp insertAppaltoClassByCodiceScheda(Map<String,Object> scheda, String codein, List<Map<String,Object>> schedeAgg, Map<String, String> lotIdCigMap, List<Map<String,Object>> soggetti,
                                                                        String idAppalto, Long syscon, Map<String, String> idContrattoCig, Long codGara, Boolean cancellaDatiEse) throws AppaltoPcpCastException, Exception, UnauthorizedSAException, SottoSogliaLottoException, AppaltoAnnullatoException, AppaltoAnnullatoEliminareException {
        ResponseElaborateAppaltoPcp res = null;
        numIncarico = null;
        idIncarico = null;
        offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        boolean isAd5 = false;
        try {
            String idScheda = null;
            if(scheda.get("idScheda") != null){
                idScheda = scheda.get("idScheda").toString();
            }
            if(idScheda == null && scheda.get("_idScheda") != null){
                idScheda = scheda.get("_idScheda").toString();
            }
            Date dataCreazione = schedePcpUtils.offsetDateTimeToDate(OffsetDateTime.parse(scheda.get("dataCreazione").toString()));
            Boolean allineaDaPcp = codGara != null;
            if(allineaDaPcp) {
                /*
                 * elimino
                 * 1: W9APPA, W9FINA, W9AGGI
                 * 101: W9IMPRESE
                 * 984: W9ESITO
                 * w9fasi
                 * w9flussi

                 */
                this.contrattiMapper.deleteFaseAggiudicazionePcp(codGara, 1L);
                this.contrattiMapper.deleteFinanziamentiAllPcp(codGara, 1L);
                this.contrattiMapper.deleteImpreseAggiudicatarieAll(codGara, 1L);
                this.contrattiMapper.deleteFaseImpresaAllPcp(codGara);
                this.contrattiMapper.deleteFaseComEsitoAllPcp(codGara);
                this.contrattiMapper.deleteIncarichiProfAll(codGara);

                this.contrattiMapper.deleteW9FasePcp(codGara, 1L);
                this.contrattiMapper.deleteW9FasePcp(codGara, 101L);
                this.contrattiMapper.deleteW9FasePcp(codGara, 984L);
                this.contrattiMapper.deleteW9FasePcp(codGara, 20L);

                this.contrattiMapper.deleteFlussoPcp(codGara, 1L);
                this.contrattiMapper.deleteFlussoPcp(codGara, 101L);
                this.contrattiMapper.deleteFlussoPcp(codGara, 984L);
                this.contrattiMapper.deleteFlussoPcp(codGara, 20L);

            }


            String codiceScheda = (String) ((Map<String, Object>)scheda.get("codice")).get("codice");

            switch (codiceScheda) {
                case FasiPcp.SCHEDAA36:
                    try{
                        var schedaA36Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaA36Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaA36Type(schedaA36Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaA36Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaA36Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaA36Type(schedaA36Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }

                case FasiPcp.SCHEDAAD125:
                    try{
                        var schedaAD125Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD125Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD125Type(schedaAD125Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD125Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD125Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD125Type(schedaAD125Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD126:
                    try{
                        var schedaAD126Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD126Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD126Type(schedaAD126Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD126Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD126Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD126Type(schedaAD126Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD127:
                    try{
                        var schedaAD127Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD127Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD127Type(schedaAD127Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD127Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD127Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD127Type(schedaAD127Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD128:
                    try{
                        var schedaAD128Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD128Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD128Type(schedaAD128Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD128Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD128Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD128Type(schedaAD128Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD225:
                    try{
                        var schedaAD225Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD225Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD225Type(schedaAD225Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD225Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD225Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD225Type(schedaAD225Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD226:
                    try{
                        var schedaAD226Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD226Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD226Type(schedaAD226Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD226Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD226Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD226Type(schedaAD226Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD227:
                    try{
                        var schedaAD227Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD227Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD227Type(schedaAD227Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD227Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD227Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD227Type(schedaAD227Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD228:
                    try{
                        var schedaAD228Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD228Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD228Type(schedaAD228Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD228Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD228Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD228Type(schedaAD228Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD3:
                    try{
                        var schedaAD3Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD3Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD3Type(schedaAD3Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD3Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD3Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD3Type(schedaAD3Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD4:
                    try{
                        var schedaAD4Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD4Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD4Type(schedaAD4Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD4Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD4Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD4Type(schedaAD4Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD5:
                    try{
                        var schedaAD5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD5Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD5Type(schedaAD5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD5Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD5Type(schedaAD5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP110:
                    try{
                        var schedaP110Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP110Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaboratSchedaP110Type(schedaP110Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP110Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP110Type.class);
                            res = this.elaborateAppaltiPcpV102.elaboratSchedaP110Type(schedaP110Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP111:
                    try{
                        var schedaP111Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP111Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaboratSchedaP111Type(schedaP111Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP111Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP111Type.class);
                            res = this.elaborateAppaltiPcpV102.elaboratSchedaP111Type(schedaP111Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP112:
                    try{
                        var schedaP112Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP112Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP112Type(schedaP112Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP112Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP112Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP112Type(schedaP112Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP113:
                    try{
                        var schedaP113Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP113Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP113Type(schedaP113Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP113Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP113Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP113Type(schedaP113Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP114:
                    try{
                        var schedaP114Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP114Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborattSchedaP114Type(schedaP114Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP114Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP114Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborattSchedaP114Type(schedaP114Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP1152:
                    try{
                        var schedaP1152Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP1152Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP1152Type(schedaP1152Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP1152Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP1152Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP1152Type(schedaP1152Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP116:
                    try{
                        var schedaP116Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP116Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP116Type(schedaP116Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP116Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP116Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP116Type(schedaP116Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////
                case FasiPcp.SCHEDAP117:
                    try{
                        var schedaP117Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP117Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP117Type(schedaP117Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP117Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP117Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP117Type(schedaP117Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP118:
                    try{
                        var schedaP118Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP118Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP118Type(schedaP118Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP118Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP118Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP118Type(schedaP118Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP119:
                    try{
                        var schedaP119Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP119Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP119Type(schedaP119Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP119Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP119Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP119Type(schedaP119Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP120:
                    try{
                        var schedaP120Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP120Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP120Type(schedaP120Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP120Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP120Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP120Type(schedaP120Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP121:
                    try{
                        var schedaP121Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP121Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP121Type(schedaP121Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP121Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP121Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP121Type(schedaP121Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP123:
                    try{
                        var schedaP123Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP123Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP123Type(schedaP123Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP123Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP123Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP123Type(schedaP123Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP124:
                    try{
                        var schedaP124Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP124Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP124Type(schedaP124Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP124Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP124Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP124Type(schedaP124Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP210:
                    try{
                        var schedaP210Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP210Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP210Type(schedaP210Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP210Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP210Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP210Type(schedaP210Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP211:
                    try{
                        var schedaP211Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP211Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP211Type(schedaP211Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP211Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP211Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP211Type(schedaP211Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP212:
                    try{
                        var schedaP212Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP212Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP212Type(schedaP212Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP212Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP212Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP212Type(schedaP212Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP213:
                    try{
                        var schedaP213Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP213Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP213Type(schedaP213Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP213Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP213Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP213Type(schedaP213Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP214:
                    try{
                        var schedaP214Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP214Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP214Type(schedaP214Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP214Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP214Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP214Type(schedaP214Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP216:
                    try{
                        var schedaP216Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP216Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP216Type(schedaP216Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP216Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP216Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP216Type(schedaP216Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////
                case FasiPcp.SCHEDAP217:
                    try{
                        var schedaP217Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP217Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP217Type(schedaP217Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP217Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP217Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP217Type(schedaP217Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP218:
                    try{
                        var schedaP218Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP218Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP218Type(schedaP218Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP218Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP218Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP218Type(schedaP218Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP219:
                    try{
                        var schedaP219Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP219Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP219Type(schedaP219Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP219Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP219Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP219Type(schedaP219Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP220:
                    try{
                        var schedaP220Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP220Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP220Type(schedaP220Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP220Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP220Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP220Type(schedaP220Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP221:
                    try{
                        var schedaP221Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP221Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP221Type(schedaP221Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP221Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP221Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP221Type(schedaP221Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP223:
                    try{
                        var schedaP223Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP223Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP223Type(schedaP223Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP223Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP223Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP223Type(schedaP223Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP224:
                    try{
                        var schedaP224Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP224Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP224Type(schedaP224Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP224Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP224Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP224Type(schedaP224Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP31:
                    try{
                        var schedaP31Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP31Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP31Type(schedaP31Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP31Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP31Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP31Type(schedaP31Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP32:
                    try{
                        var schedaP32Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP32Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP32Type(schedaP32Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP32Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP32Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP32Type(schedaP32Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP33:
                    try{
                        var schedaP33Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP33Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP33Type(schedaP33Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP33Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP33Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP33Type(schedaP33Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP34:
                    try{
                        var schedaP34Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP34Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP34Type(schedaP34Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP34Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP34Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP34Type(schedaP34Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP35:
                    try{
                        var schedaP35Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP35Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP35Type(schedaP35Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP35Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP35Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP35Type(schedaP35Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP41:
                    try{
                        var schedaP41Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP41Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP41Type(schedaP41Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP41Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP41Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP41Type(schedaP41Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP42:
                    try{
                        var schedaP42Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP42Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP42Type(schedaP42Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP42Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP42Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP42Type(schedaP42Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP43:
                    try{
                        var schedaP43Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP43Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP43Type(schedaP43Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP43Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP43Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP43Type(schedaP43Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP44:
                    try{
                        var schedaP44Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP44Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP44Type(schedaP44Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP44Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP44Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP44Type(schedaP44Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP45:
                    try{
                        var schedaP45Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP45Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP45Type(schedaP45Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP45Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP45Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP45Type(schedaP45Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP46:
                    try{
                        var schedaP46Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP46Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP46Type(schedaP46Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP46Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP46Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP46Type(schedaP46Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP5:
                    try{
                        var schedaP5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP5Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP5Type(schedaP5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP5Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP5Type(schedaP5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP61:
                    try{
                        var schedaP61Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP61Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP61Type(schedaP61Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP61Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP61Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP61Type(schedaP61Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP62:
                    try{
                        var schedaP62Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP62Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP62Type(schedaP62Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP62Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP62Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP62Type(schedaP62Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP711:
                    try{
                        var schedaP711Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP711Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP711Type(schedaP711Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP711Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP711Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP711Type(schedaP711Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP712:
                    try{
                        var schedaP712Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP712Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP712Type(schedaP712Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP712Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP712Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP712Type(schedaP712Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP713:
                    try{
                        var schedaP713Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP713Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP713Type(schedaP713Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP713Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP713Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP713Type(schedaP713Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP72:
                    try{
                        var schedaP72Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP72Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP72Type(schedaP72Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP72Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP72Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP72Type(schedaP72Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, codGara, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                default: logger.info("Scheda non trovata");
            }

            this.contrattiMapper.updateImportoGaraDaLotti(res.getCodGara());
            String logEventiMessage = null;
            if(!allineaDaPcp) {
                logEventiMessage = "Importazione appalto con idappalto: " + idAppalto + " - tipologia scheda: " + codiceScheda;
            } else {
                logEventiMessage = "riallinea appalto con idappalto: " + idAppalto + " - tipologia scheda: " + codiceScheda;
            }
            Short livEvento = 1;
            schedePcpUtils.insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, null,livEvento );

            if(schedeAgg != null && schedeAgg.size() > 0) {

		    	/* ordino le schede prelevate da pcp mettendo come primi elementi della lista le schede di aggiudicazione così da inserirle per prime
		    	 * siccome nelle A1/2 prelevo le offertePresentate e nella S2 le inserisco nella w9aggi, se importo prima la S2 la w9aggi sarà vuota

		    	 * Se il codice inizia con "A1", "A2", "A3", "A4" o "A7", mettendo questi elementi in cima.
                    Altrimenti, ordina gli elementi per data di creazione.*/
                schedeAgg.sort((s1, s2) -> {
                    // Estrai i valori dalle mappe
                    String codice1 = (String) ((Map<String, Object>)s1.get("codice")).get("codice");
                    String codice2 = (String) ((Map<String, Object>)s2.get("codice")).get("codice");
                    String dataCreazione1 = (String) s1.get("dataCreazione");
                    String dataCreazione2 = (String) s2.get("dataCreazione");

                    // Controlla se i codici iniziano con uno dei valori specificati
                    boolean s1StartsWithA1orA2 = codice1 != null && (codice1.startsWith("A1") || codice1.startsWith("A2") || codice1.startsWith("A3") || codice1.startsWith("A4") || codice1.startsWith("A7"));
                    boolean s2StartsWithA1orA2 = codice2 != null && (codice2.startsWith("A1") || codice2.startsWith("A2") || codice2.startsWith("A3") || codice2.startsWith("A4") || codice2.startsWith("A7"));

                    // Logica di ordinamento
                    return s1StartsWithA1orA2 && !s2StartsWithA1orA2 ? -1 :
                            !s1StartsWithA1orA2 && s2StartsWithA1orA2 ? 1 :
                                    dataCreazione1 == null && dataCreazione2 != null ? 1 :
                                            dataCreazione1 != null && dataCreazione2 == null ? -1 :
                                                    dataCreazione1 == null || dataCreazione2 == null ? 0 :
                                                            dataCreazione1.compareTo(dataCreazione2);
                });
                Set<String> codiciVisti = new HashSet<>();
                // Filtriamo la lista eliminando duplicati eventiali di schede confermate che dovrebbero essere singole ma che nella pratica non sono
                /*schedeAgg = schedeAgg.stream()
                        .filter(agg -> {
                            String codice =  (String) ((Map<String, Object>)agg.get("codice")).get("codice");
                            // Controlliamo solo i codici "CO1" e "CO2"
                            if ("CO1".equals(codice) ||
                                    "CO2".equals(codice) ||
                                    "SC1".equals(codice) ||
                                    "I1".equals(codice) ||
                                    "CL1".equals(codice)) {
                                // Se il codice non è stato ancora visto, aggiungiamolo al set
                                if (!codiciVisti.contains(codice)) {
                                    codiciVisti.add(codice);
                                    return true; // Manteniamo l'elemento nello stream
                                }
                                return false; // Scartiamo l'elemento duplicato
                            }
                            // Manteniamo tutti gli altri codici
                            return true;
                        })
                        .collect(Collectors.toList());

                 */
                schedeAgg = schedePcpUtils.filtraS2eS2R(schedeAgg);
                if(cancellaDatiEse) {
                    schedePcpUtils.deleteDatiSchedeEse(codGara, codiceScheda);
                }
                for (Map<String, Object> schedaPostPubblicazione : schedeAgg) {
                    insertAggiudicazioneClassByCodiceScheda(schedaPostPubblicazione, res, codein, syscon, idContrattoCig, idAppalto, allineaDaPcp, cancellaDatiEse, isAd5);
                }
            }
        } catch (UnauthorizedSAException ex) {
            logger.error("Errore in fase di importazione fase pcp: insertAppaltoClassByCodiceScheda",ex);
            throw ex;
        } catch (SottoSogliaLottoException ex2) {
            logger.error("Errore in fase di importazione fase pcp: insertAppaltoClassByCodiceScheda",ex2);
            throw ex2;
        } catch (AppaltoAnnullatoException ex3) {
            logger.error("Errore in fase di importazione fase pcp: insertAppaltoClassByCodiceScheda",ex3);
            throw ex3;
        } catch (AppaltoAnnullatoEliminareException ex4) {
            logger.error("Errore in fase di importazione fase pcp: insertAppaltoClassByCodiceScheda",ex4);
            throw ex4;
        } catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: insertAppaltoClassByCodiceScheda",e);
            throw e;
        }

        return res;
    }



    private void insertAggiudicazioneClassByCodiceScheda(Map<String, Object> scheda, ResponseElaborateAppaltoPcp res, String codein, Long syscon, Map<String, String> idContrattoCig, String idAppalto,
                                                         Boolean allineaDaPcp, Boolean cancellaDatiEse, boolean isAd5) throws Exception,AppaltoAnnullatoException, AppaltoAnnullatoEliminareException {

        try {
            String idScheda = null;
            if(scheda.get("idScheda") != null){
                idScheda = scheda.get("idScheda").toString();
            }
            if(idScheda == null && scheda.get("_idScheda") != null){
                idScheda = scheda.get("_idScheda").toString();
            }
            Date dataCreazione = schedePcpUtils.offsetDateTimeToDate(OffsetDateTime.parse(scheda.get("dataCreazione").toString()));  // Estrai e converte la data dalla mappa
            String statoScheda = (String) ((Map<String, Object>)scheda.get("stato")).get("codice");  // Estrai il codice dello stato dalla mappa
            String codiceScheda = (String) ((Map<String, Object>)scheda.get("codice")).get("codice");
            switch (codiceScheda) {
                case FasiPcp.SCHEDAA129 :
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA129Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA129Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA129Type(schedaA129Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA129Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA129Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA129Type(schedaA129Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA130:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA130Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA130Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA130Type(schedaA130Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA130Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA130Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA130Type(schedaA130Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA131:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA131Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA131Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA131Type(schedaA131Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA131Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA131Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA131Type(schedaA131Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA132:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA132Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA132Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA132Type(schedaA132Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA132Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA132Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA132Type(schedaA132Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA133:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA133Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA133Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA133Type(schedaA133Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA133Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA133Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA133Type(schedaA133Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA134:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA134Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA134Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA134Type(schedaA134Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA134Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA134Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA134Type(schedaA134Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA135:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA135Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA135Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA135Type(schedaA135Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA135Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA135Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA135Type(schedaA135Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA136:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA136Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA136Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA136Type(schedaA136Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA136Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA136Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA136Type(schedaA136Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA137:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA137Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA137Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA137Type(schedaA137Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA137Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA137Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA137Type(schedaA137Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA229:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA229Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA229Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA229Type(schedaA229Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA229Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA229Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA229Type(schedaA229Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA230:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA230Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA230Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA230Type(schedaA230Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA230Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA230Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA230Type(schedaA230Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA231:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA231Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA231Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA231Type(schedaA231Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA231Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA231Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA231Type(schedaA231Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA232:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA232Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA232Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA232Type(schedaA232Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA232Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA232Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA232Type(schedaA232Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA233:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA233Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA233Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA233Type(schedaA233Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA233Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA233Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA233Type(schedaA233Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA234:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA234Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA234Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA234Type(schedaA234Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA234Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA234Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA234Type(schedaA234Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA235:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA235Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA235Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA235Type(schedaA235Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA235Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA235Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA235Type(schedaA235Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA236:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA236Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA236Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA236Type(schedaA236Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA236Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA236Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA236Type(schedaA236Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA237:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA237Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA237Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA237Type(schedaA237Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA237Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA237Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA237Type(schedaA237Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA31:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA31Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA31Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA31Type(schedaA31Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA31Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA31Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA31Type(schedaA31Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA32:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA32Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA32Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA32Type(schedaA32Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA32Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA32Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA32Type(schedaA32Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA33:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA33Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA33Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA33Type(schedaA33Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA33Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA33Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA33Type(schedaA33Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA34:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA34Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA34Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA34Type(schedaA34Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA34Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA34Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA34Type(schedaA34Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA35:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA35Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA35Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA35Type(schedaA35Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA35Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA35Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA35Type(schedaA35Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA41:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA41Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA41Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA41Type(schedaA41Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA41Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA41Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA41Type(schedaA41Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA42:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA42Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA42Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA42Type(schedaA42Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA42Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA42Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA42Type(schedaA42Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA43:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA43Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA43Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA43Type(schedaA43Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA43Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA43Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA43Type(schedaA43Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA44:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA44Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA44Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA44Type(schedaA44Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA44Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA44Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA44Type(schedaA44Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA45:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA45Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA45Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA45Type(schedaA45Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA45Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA45Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA45Type(schedaA45Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA46:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA46Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA46Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA46Type(schedaA46Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA46Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA46Type.class);
                                offertePresentate = this.elaborateSchedePcpV102.elaborateAggSchedaA46Type(schedaA46Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA711:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA711Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA711Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA711Type(schedaA711Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA711Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA711Type.class);
                                this.elaborateSchedePcpV102.elaborateAggSchedaA711Type(schedaA711Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAA712:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaA712Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaA712Type.class);
                            offertePresentate = this.elaborateSchedePcpV1021.elaborateAggSchedaA712Type(schedaA712Type, res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaA712Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaA712Type.class);
                                this.elaborateSchedePcpV102.elaborateAggSchedaA712Type(schedaA712Type, res, codein, syscon, idScheda, dataCreazione);
                            }catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDANAG:
                    try{
                        var schedaNAGType = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaNAGType.class);
                        this.elaborateSchedePcpV1021.elaborateAggSchedaNAGType(schedaNAGType,res, codein, syscon, idScheda, dataCreazione);
                    } catch (IllegalArgumentException e){
                        try{
                            var schedaNAGType = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaNAGType.class);
                            this.elaborateSchedePcpV102.elaborateAggSchedaNAGType(schedaNAGType,res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e2){
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    break;
                case FasiPcp.SCHEDAS2:
                    try{
                        var schedaS2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaS2Type.class);
                        this.elaborateSchedePcpV1021.elaborateAggSchedaS2Type(schedaS2Type,res, codein, syscon, idScheda, dataCreazione, offertePresentate);
                    } catch (IllegalArgumentException e){
                        try{
                            var schedaS2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaS2Type.class);
                            this.elaborateSchedePcpV102.elaborateAggSchedaS2Type(schedaS2Type,res, codein, syscon, idScheda, dataCreazione, offertePresentate);
                        } catch (IllegalArgumentException e2){
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    break;
                case FasiPcp.SCHEDAS2R:
                    try{
                        var schedaS2RType = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaS2RType.class);
                        this.elaborateSchedePcpV1021.elaborateAggSchedaS2RType(schedaS2RType,res, codein, syscon, idScheda, dataCreazione, offertePresentate);
                    } catch (IllegalArgumentException e){
                        logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                        throw new AppaltoPcpCastException();
                    }
                    break;
                case FasiPcp.SCHEDAS3:
                    try{
                        var schedaS3Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaS3Type.class);
                        this.elaborateSchedePcpV1021.elaborateAggSchedaS3Type(schedaS3Type,res, codein, syscon, idScheda, dataCreazione, statoScheda);
                    } catch (IllegalArgumentException e){
                        try{
                            var schedaS3Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaS3Type.class);
                            this.elaborateSchedePcpV102.elaborateAggSchedaS3Type(schedaS3Type,res, codein, syscon, idScheda, dataCreazione, statoScheda);
                        } catch (IllegalArgumentException e2){
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    break;
/////////////////////////////////////////////////////////////////// schede esecuzione /////////////////////////////////////////////////////////////////////////
                case FasiPcp.SCHEDAS4:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaS4Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaS4Type.class);
                            this.elaborateSchedePcpV1021.elaborateAggSchedaS4Type(schedaS4Type,res, codein, syscon, idScheda, dataCreazione);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaS4Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaS4Type.class);
                                this.elaborateSchedePcpV102.elaborateAggSchedaS4Type(schedaS4Type,res, codein, syscon, idScheda, dataCreazione);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDASC1:
                    try{
                        var schedaSC1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaSC1Type.class);
                        this.elaborateSchedePcpV1021.elaborateSchedaSC1Type(schedaSC1Type,res, codein, syscon, idScheda, idContrattoCig, dataCreazione,allineaDaPcp, statoScheda);
                    } catch (IllegalArgumentException e){
                        try{
                            var schedaSC1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSC1Type.class);
                            this.elaborateSchedePcpV102.elaborateSchedaSC1Type(schedaSC1Type,res, codein, syscon, idScheda, idContrattoCig, dataCreazione,allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e2){
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    break;
                case FasiPcp.SCHEDAI1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaI1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaI1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaI1Type(schedaI1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaI1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaI1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaI1Type(schedaI1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDASA1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaSA1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaSA1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaSA1Type(schedaSA1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaSA1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSA1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaSA1Type(schedaSA1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDACO1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaCO1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaCO1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaCO1Type(schedaCO1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaCO1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCO1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaCO1Type(schedaCO1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDACO2:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaCO2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaCO2Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaCO2Type(schedaCO2Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaCO2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCO2Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaCO2Type(schedaCO2Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDACL1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaCL1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaCL1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaCL1Type(schedaCL1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaCL1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCL1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaCL1Type(schedaCL1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAM1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaM1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaM1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaM1Type(schedaM1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaM1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaM1Type(schedaM1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAM140:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaM140Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaM140Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaM140Type(schedaM140Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaM140Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM140Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaM140Type(schedaM140Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAM2:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaM2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaM2Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaM2Type(schedaM2Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaM2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM2Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaM2Type(schedaM2Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAM240:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaM240Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaM240Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaM240Type(schedaM240Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaM240Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM240Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaM240Type(schedaM240Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAAC1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaAC1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaAC1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaAC1Type(schedaAC1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaAC1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaAC1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaAC1Type(schedaAC1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDASO1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaSO1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaSO1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaSO1Type(schedaSO1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaSO1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSO1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaSO1Type(schedaSO1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDASQ1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaSQ1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaSQ1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaSQ1Type(schedaSQ1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaSQ1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSQ1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaSQ1Type(schedaSQ1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDARI1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaRI1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaRI1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaRI1Type(schedaRI1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaRI1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaRI1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaRI1Type(schedaRI1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDARSU1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaRSU1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaRSU1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaRSU1Type(schedaRSU1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaRSU1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaRSU1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaRSU1Type(schedaRSU1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAES1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaES1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaES1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaES1Type(schedaES1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaES1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaES1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaES1Type(schedaES1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDACS1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaCS1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaCS1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaCS1Type(schedaCS1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaCS1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCS1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaCS1Type(schedaCS1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAIR1:
                    if(cancellaDatiEse || !allineaDaPcp) {
                        try{
                            var schedaIR1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaIR1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaIR1Type(schedaIR1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaIR1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaIR1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaIR1Type(schedaIR1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDACM1:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaCM1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaCM1Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaCM1Type(schedaCM1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda, isAd5);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaCM1Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCM1Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaCM1Type(schedaCM1Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda, isAd5);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDACM2:
                    if(statoScheda.equals("CONF")) {
                        try{
                            var schedaCM2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.SchedaCM2Type.class);
                            this.elaborateSchedePcpV1021.elaborateSchedaCM2Type(schedaCM2Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda, isAd5);
                        } catch (IllegalArgumentException e){
                            try{
                                var schedaCM2Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCM2Type.class);
                                this.elaborateSchedePcpV102.elaborateSchedaCM2Type(schedaCM2Type, res, codein, syscon,  idScheda, idContrattoCig, dataCreazione, allineaDaPcp, statoScheda, isAd5);
                            } catch (IllegalArgumentException e2){
                                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAggiudicazioneClassByCodiceScheda ",e);
                                throw new AppaltoPcpCastException();
                            }
                        }
                    }
                    break;
                case FasiPcp.SCHEDAANN:
                    if(statoScheda.equals("CONF")) {
                        if(allineaDaPcp) {
                            throw new AppaltoAnnullatoEliminareException(BaseResponse.ERROR_APPALTO_ANNULLATO_ELIMINARE);
                        } else{
                            throw new AppaltoAnnullatoException(BaseResponse.ERROR_APPALTO_ANNULLATO);
                        }
                    }
                    break;
                default:
                    logger.info("Scheda non trovata");
            }

            String logEventiMessage = "Importazione scheda " + codiceScheda + " con idscheda: " + idScheda + "dell'appalto con idappalto: " + idAppalto;
            Short livEvento = 1;
            schedePcpUtils.insertLogEventi(syscon, null, LOG_EVENTI_SCHEDA_PCP_COD_EVENTO, logEventiMessage, null, livEvento);

        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: insertAggiudicazioneClassByCodiceScheda");
            throw e;
        }
    }



    public Boolean getCfSaPcp(Map<String, Object> scheda, String cfSa) {
        try {
            if(scheda != null){
                Map<String, Object> appalto = (Map<String, Object>)scheda.get("body");
                if(appalto != null && appalto.get("anacForm") != null){
                    Map<String, Object> anacForm = (Map<String, Object>)scheda.get("anacForm");
                    if(anacForm != null && anacForm.get("stazioniAppaltanti") != null){
                        List<Map<String, Object>> stazioniAppaltanti = (List<Map<String, Object>>)anacForm.get("stazioniAppaltanti");
                        for (Map<String, Object> sa : stazioniAppaltanti) {
                            if(StringUtils.isNotBlank((String)sa.get("codiceFiscale")) && sa.get("codiceFiscale").equals(cfSa)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("Errore nel metodo getCfSaPcp",e);
            throw e;
        }
        return false;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseAppaltoPcp getAppaltoClassByCodiceScheda(Map<String, Object> scheda, String codein, List<Map<String, Object>> schedeAgg, Map<String, String> lotIdCigMap,
                                                            List<Map<String, Object>> soggetti, String idAppalto, Long syscon, Map<String, String> idContrattoCig) throws Exception, UnauthorizedSAException, SottoSogliaLottoException {
        ResponseElaborateAppaltoPcp res = null;
        ResponseAppaltoPcp risultato = new ResponseAppaltoPcp();
        boolean isAd5 = false;
        try {
            String idScheda = scheda.get("idScheda").toString();
            Date dataCreazione = schedePcpUtils.offsetDateTimeToDate(OffsetDateTime.parse(scheda.get("dataCreazione").toString()));
            String codiceScheda = (String) ((Map<String, Object>)scheda.get("codice")).get("codice");

            switch (codiceScheda) {
                case FasiPcp.SCHEDAA36:
                    try{
                        var schedaA36Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaA36Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaA36Type(schedaA36Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaA36Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaA36Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaA36Type(schedaA36Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }

                case FasiPcp.SCHEDAAD125:
                    try{
                        var schedaAD125Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD125Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD125Type(schedaAD125Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD125Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD125Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD125Type(schedaAD125Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD126:
                    try{
                        var schedaAD126Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD126Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD126Type(schedaAD126Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD126Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD126Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD126Type(schedaAD126Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD127:
                    try{
                        var schedaAD127Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD127Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD127Type(schedaAD127Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD127Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD127Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD127Type(schedaAD127Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD128:
                    try{
                        var schedaAD128Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD128Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD128Type(schedaAD128Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD128Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD128Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD128Type(schedaAD128Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD225:
                    try{
                        var schedaAD225Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD225Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD225Type(schedaAD225Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD225Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD225Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD225Type(schedaAD225Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD226:
                    try{
                        var schedaAD226Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD226Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD226Type(schedaAD226Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD226Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD226Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD226Type(schedaAD226Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD227:
                    try{
                        var schedaAD227Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD227Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD227Type(schedaAD227Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD227Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD227Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD227Type(schedaAD227Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD228:
                    try{
                        var schedaAD228Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD228Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD228Type(schedaAD228Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD228Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD228Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD228Type(schedaAD228Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD3:
                    try{
                        var schedaAD3Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD3Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD3Type(schedaAD3Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD3Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD3Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD3Type(schedaAD3Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD4:
                    try{
                        var schedaAD4Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD4Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD4Type(schedaAD4Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD4Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD4Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD4Type(schedaAD4Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAAD5:
                    try{
                        var schedaAD5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaAD5Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaAD5Type(schedaAD5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaAD5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaAD5Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaAD5Type(schedaAD5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, idScheda, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP110:
                    try{
                        var schedaP110Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP110Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaboratSchedaP110Type(schedaP110Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP110Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP110Type.class);
                            res = this.elaborateAppaltiPcpV102.elaboratSchedaP110Type(schedaP110Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP111:
                    try{
                        var schedaP111Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP111Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaboratSchedaP111Type(schedaP111Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP111Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP111Type.class);
                            res = this.elaborateAppaltiPcpV102.elaboratSchedaP111Type(schedaP111Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP112:
                    try{
                        var schedaP112Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP112Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP112Type(schedaP112Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP112Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP112Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP112Type(schedaP112Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP113:
                    try{
                        var schedaP113Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP113Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP113Type(schedaP113Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP113Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP113Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP113Type(schedaP113Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP114:
                    try{
                        var schedaP114Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP114Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborattSchedaP114Type(schedaP114Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP114Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP114Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborattSchedaP114Type(schedaP114Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP1152:
                    try{
                        var schedaP1152Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP1152Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP1152Type(schedaP1152Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP1152Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP1152Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP1152Type(schedaP1152Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP116:
                    try{
                        var schedaP116Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP116Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP116Type(schedaP116Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP116Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP116Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP116Type(schedaP116Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////
                case FasiPcp.SCHEDAP117:
                    try{
                        var schedaP117Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP117Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP117Type(schedaP117Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP117Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP117Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP117Type(schedaP117Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP118:
                    try{
                        var schedaP118Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP118Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP118Type(schedaP118Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP118Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP118Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP118Type(schedaP118Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP119:
                    try{
                        var schedaP119Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP119Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP119Type(schedaP119Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP119Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP119Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP119Type(schedaP119Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP120:
                    try{
                        var schedaP120Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP120Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP120Type(schedaP120Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP120Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP120Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP120Type(schedaP120Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP121:
                    try{
                        var schedaP121Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP121Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP121Type(schedaP121Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP121Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP121Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP121Type(schedaP121Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP123:
                    try{
                        var schedaP123Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP123Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP123Type(schedaP123Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP123Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP123Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP123Type(schedaP123Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP124:
                    try{
                        var schedaP124Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP124Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP124Type(schedaP124Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP124Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP124Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP124Type(schedaP124Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP210:
                    try{
                        var schedaP210Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP210Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP210Type(schedaP210Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP210Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP210Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP210Type(schedaP210Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP211:
                    try{
                        var schedaP211Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP211Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP211Type(schedaP211Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP211Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP211Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP211Type(schedaP211Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP212:
                    try{
                        var schedaP212Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP212Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP212Type(schedaP212Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP212Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP212Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP212Type(schedaP212Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP213:
                    try{
                        var schedaP213Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP213Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP213Type(schedaP213Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP213Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP213Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP213Type(schedaP213Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP214:
                    try{
                        var schedaP214Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP214Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP214Type(schedaP214Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP214Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP214Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP214Type(schedaP214Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP216:
                    try{
                        var schedaP216Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP216Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP216Type(schedaP216Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP216Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP216Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP216Type(schedaP216Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////
                case FasiPcp.SCHEDAP217:
                    try{
                        var schedaP217Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP217Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP217Type(schedaP217Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP217Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP217Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP217Type(schedaP217Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP218:
                    try{
                        var schedaP218Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP218Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP218Type(schedaP218Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP218Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP218Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP218Type(schedaP218Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP219:
                    try{
                        var schedaP219Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP219Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP219Type(schedaP219Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP219Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP219Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP219Type(schedaP219Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP220:
                    try{
                        var schedaP220Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP220Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP220Type(schedaP220Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP220Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP220Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP220Type(schedaP220Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP221:
                    try{
                        var schedaP221Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP221Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP221Type(schedaP221Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP221Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP221Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP221Type(schedaP221Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP223:
                    try{
                        var schedaP223Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP223Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP223Type(schedaP223Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP223Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP223Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP223Type(schedaP223Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP224:
                    try{
                        var schedaP224Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP224Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP224Type(schedaP224Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP224Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP224Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP224Type(schedaP224Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP31:
                    try{
                        var schedaP31Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP31Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP31Type(schedaP31Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP31Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP31Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP31Type(schedaP31Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP32:
                    try{
                        var schedaP32Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP32Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP32Type(schedaP32Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP32Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP32Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP32Type(schedaP32Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP33:
                    try{
                        var schedaP33Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP33Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP33Type(schedaP33Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP33Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP33Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP33Type(schedaP33Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP34:
                    try{
                        var schedaP34Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP34Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP34Type(schedaP34Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP34Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP34Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP34Type(schedaP34Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP35:
                    try{
                        var schedaP35Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP35Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP35Type(schedaP35Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP35Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP35Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP35Type(schedaP35Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP41:
                    try{
                        var schedaP41Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP41Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP41Type(schedaP41Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP41Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP41Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP41Type(schedaP41Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP42:
                    try{
                        var schedaP42Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP42Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP42Type(schedaP42Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP42Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP42Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP42Type(schedaP42Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP43:
                    try{
                        var schedaP43Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP43Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP43Type(schedaP43Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP43Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP43Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP43Type(schedaP43Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP44:
                    try{
                        var schedaP44Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP44Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP44Type(schedaP44Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP44Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP44Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP44Type(schedaP44Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP45:
                    try{
                        var schedaP45Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP45Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP45Type(schedaP45Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP45Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP45Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP45Type(schedaP45Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP46:
                    try{
                        var schedaP46Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP46Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP46Type(schedaP46Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP46Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP46Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP46Type(schedaP46Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP5:
                    try{
                        var schedaP5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP5Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP5Type(schedaP5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP5Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP5Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP5Type(schedaP5Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP61:
                    try{
                        var schedaP61Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP61Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP61Type(schedaP61Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP61Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP61Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP61Type(schedaP61Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP62:
                    try{
                        var schedaP62Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP62Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP62Type(schedaP62Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP62Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP62Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP62Type(schedaP62Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP711:
                    try{
                        var schedaP711Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP711Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP711Type(schedaP711Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP711Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP711Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP711Type(schedaP711Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP712:
                    try{
                        var schedaP712Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP712Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP712Type(schedaP712Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP712Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP712Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP712Type(schedaP712Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP713:
                    try{
                        var schedaP713Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP713Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP713Type(schedaP713Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP713Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP713Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP713Type(schedaP713Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                case FasiPcp.SCHEDAP72:
                    try{
                        var schedaP72Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v1021.comunicaAppalto.SchedaP72Type.class);
                        res = this.elaborateAppaltiPcpV1021.elaborateSchedaP72Type(schedaP72Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                        isAd5 = true;
                        break;
                    }catch (IllegalArgumentException e) {
                        try{
                            var schedaP72Type = objectMapper.convertValue(scheda.get("body"), it.appaltiecontratti.pcp.v102.comunicaAppalto.SchedaP72Type.class);
                            res = this.elaborateAppaltiPcpV102.elaborateSchedaP72Type(schedaP72Type, codein, codiceScheda, lotIdCigMap, soggetti, idAppalto, syscon, null, dataCreazione);
                            isAd5 = true;
                            break;
                        }catch (IllegalArgumentException e2) {
                            logger.error("Errore in fase di conversione json in classe dell'appalto pcp: insertAppaltoClassByCodiceScheda ",e);
                            throw new AppaltoPcpCastException();
                        }
                    }
                default: logger.info("Scheda non trovata");
            }

            String logEventiMessage = null;

            logEventiMessage = "lettura appalto con idappalto: " + idAppalto + " - tipologia scheda: " + codiceScheda;

            Short livEvento = 1;
            schedePcpUtils.insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, null,livEvento );

            if(schedeAgg != null && schedeAgg.size() > 0) {

		    	/* ordino le schede prelevate da pcp mettendo come primi elementi della lista le schede di aggiudicazione così da inserirle per prime
		    	 * siccome nelle A1/2 prelevo le offertePresentate e nella S2 le inserisco nella w9aggi, se importo prima la S2 la w9aggi sarà vuota

		    	 * Se il codice inizia con "A1", "A2", "A3", "A4" o "A7", mettendo questi elementi in cima.
                    Altrimenti, ordina gli elementi per data di creazione.*/
                schedeAgg.sort((s1, s2) -> {
                    // Estrai i valori dalle mappe
                    String codice1 = (String) ((Map<String, Object>)s1.get("codice")).get("codice");
                    String codice2 = (String) ((Map<String, Object>)s2.get("codice")).get("codice");
                    String dataCreazione1 = (String) s1.get("dataCreazione");
                    String dataCreazione2 = (String) s2.get("dataCreazione");

                    // Controlla se i codici iniziano con uno dei valori specificati
                    boolean s1StartsWithA1orA2 = codice1 != null && (codice1.startsWith("A1") || codice1.startsWith("A2") || codice1.startsWith("A3") || codice1.startsWith("A4") || codice1.startsWith("A7"));
                    boolean s2StartsWithA1orA2 = codice2 != null && (codice2.startsWith("A1") || codice2.startsWith("A2") || codice2.startsWith("A3") || codice2.startsWith("A4") || codice2.startsWith("A7"));

                    // Logica di ordinamento
                    return s1StartsWithA1orA2 && !s2StartsWithA1orA2 ? -1 :
                            !s1StartsWithA1orA2 && s2StartsWithA1orA2 ? 1 :
                                    dataCreazione1 == null && dataCreazione2 != null ? 1 :
                                            dataCreazione1 != null && dataCreazione2 == null ? -1 :
                                                    dataCreazione1 == null || dataCreazione2 == null ? 0 :
                                                            dataCreazione1.compareTo(dataCreazione2);
                });
                Set<String> codiciVisti = new HashSet<>();
                // Filtriamo la lista eliminando duplicati eventiali di schede confermate che dovrebbero essere singole ma che nella pratica non sono
                schedeAgg = schedeAgg.stream()
                        .filter(agg -> {
                            String codice =  (String) ((Map<String, Object>)agg.get("codice")).get("codice");
                            // Controlliamo solo i codici "CO1" e "CO2"
                            if ("CO1".equals(codice) ||
                                    "CO2".equals(codice) ||
                                    "SC1".equals(codice) ||
                                    "I1".equals(codice) ||
                                    "CL1".equals(codice)) {
                                // Se il codice non è stato ancora visto, aggiungiamolo al set
                                if (!codiciVisti.contains(codice)) {
                                    codiciVisti.add(codice);
                                    return true; // Manteniamo l'elemento nello stream
                                }
                                return false; // Scartiamo l'elemento duplicato
                            }
                            // Manteniamo tutti gli altri codici
                            return true;
                        })
                        .collect(Collectors.toList());
                for (Map<String, Object> schedaPostPubblicazione : schedeAgg) {
                    insertAggiudicazioneClassByCodiceScheda(schedaPostPubblicazione, res, codein, syscon, idContrattoCig, idAppalto, false, false, isAd5);
                }
            }

            Long codGara = res.getCodGara();

            GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
            List<LottoFullEntry> lotti = this.contrattiMapper.getFullLottiGara(codGara);
            List<FaseAggiudicazioneEntry> aggiudicazioni = this.contrattiMapper.getFasiAggiudicazione(codGara);
            List<ImpresaAggiudicatariaEntry> fasiImpreseAggiudicatarie = new ArrayList<ImpresaAggiudicatariaEntry>();
            List<FaseImpresaEntry> fasiImpresePartecipanti = new ArrayList<FaseImpresaEntry>();

            FaseInizialeEsecuzioneEntry faseInizio;
            List<FaseInizialeEsecuzioneEntry> faseInizioList = new ArrayList<>();
            List<FullFlussiFase> flussiFaseInizioList = new ArrayList<>();
            for (LottoFullEntry lotto : lotti) {
                ResponseImprAggiudicatarie impAgg = fasiManager.getImpreseAggiudicatarie(Long.valueOf(lotto.getCodgara()), Long.valueOf(lotto.getCodLotto()), 1L);
                ResponseFaseImprese imp = fasiManager.dettaglioFaseImprese(Long.valueOf(lotto.getCodgara()), Long.valueOf(lotto.getCodLotto()), 1L, null);
                faseInizio = this.contrattiMapper.getFaseInizialeEsecuzione(codGara, Long.valueOf(lotto.getCodLotto()), 1L);
                List<FullFlussiFase> flussiFaseInizio = this.contrattiMapper.getListaPubblicazioniFase(codGara,Long.valueOf(lotto.getCodLotto()), 13L, 1L );
                if(impAgg != null && impAgg.getData() != null) {
                    fasiImpreseAggiudicatarie.addAll(impAgg.getData());
                }

                if(imp != null && imp.getData() != null) {
                    fasiImpresePartecipanti.addAll(imp.getData());
                }

                faseInizioList.add(faseInizio);
                flussiFaseInizioList.addAll(flussiFaseInizio);

            }

            risultato.setGara(gara);
            risultato.setLotti(lotti);
            risultato.setFasiAggiudicazione(aggiudicazioni);
            risultato.setFasiImpreseAggiudicatarie(fasiImpreseAggiudicatarie);
            risultato.setFasiImpresePartecipanti(fasiImpresePartecipanti);
            risultato.setFaseInizio(faseInizioList);
            risultato.setFlussiFaseInizio(flussiFaseInizioList);

            throw new GetDatiAppaltoException("Eccezione gestita per evitare il salvataggio dei dati a db", risultato);

        } catch (UnauthorizedSAException ex) {
            logger.error("Errore in fase di get fase pcp: getAppaltoClassByCodiceScheda",ex);
            throw ex;
        } catch (SottoSogliaLottoException ex2) {
            logger.error("Errore in fase di get fase pcp: getAppaltoClassByCodiceScheda",ex2);
            throw ex2;
        } catch (GetDatiAppaltoException ex3) {
            logger.error("Eccezione gestita getAppaltoClassByCodiceScheda",ex3.getMessage());
            throw ex3;
        } catch (Exception e) {
            logger.error("Errore in fase di get fase pcp: getAppaltoClassByCodiceScheda",e);
            throw e;
        }
    }
}
