package it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.LotResultType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.SettledContractType;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.MandanteEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.AbstractManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.FasiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.WGenChiaviManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseElaborateAppaltoPcp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseInsert;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.FasiPcp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.SchedePcpUtils;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.*;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.contractawardnotice_2.ContractAwardNoticeType;
import oasis.names.specification.ubl.schema.xsd.contractnotice_2.ContractNoticeType;
import oasis.names.specification.ubl.schema.xsd.contractnotice_2.ObjectFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static it.appaltiecontratti.monitoraggiocontratti.contratti.utils.SchedePcpUtils.safeSum;

@Component(value = "ElaborateSchedeManagerV102")
public class ElaborateSchedeManagerV102 extends AbstractManager {

    /** Logger di classe. */
    private static final Logger logger = LogManager.getLogger(ElaborateSchedeManagerV102.class);

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

    private static ObjectMapper objectMapper;

    private static final Map<String, String> mapOggettoContratto;

    private static final Map<String, String> mapCriteriAggiudicazione;

    private static final Map<String, String> mapSchedaEformType;

    private static final Map<String, String> mapTipoOe;

    private static final Map<String, String> flagRitardoImportMap;

    private static final Map<String, String> motiviModificaImportMap;

    private String idPartecipante = null;

    //public Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String LOG_EVENTI_SCHEDA_PCP_COD_EVENTO = "SCHEDA_PCP";
    private static final String LOG_EVENTI_APPALTO_PCP_COD_EVENTO = "APPALTO_PCP";

    private Long numIncarico = null;

    private Long idIncarico = null;

    static {

        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<String, String> oggMap = new HashMap<String, String>();
        oggMap.put("works","L");
        oggMap.put("supplies","F");
        oggMap.put("services","S");
        mapOggettoContratto = Collections.unmodifiableMap(oggMap);

        Map<String, String> criAggMap = new HashMap<String, String>();
        criAggMap.put("quality","5");
        criAggMap.put("cost","3");
        criAggMap.put("price","4");
        mapCriteriAggiudicazione = Collections.unmodifiableMap(criAggMap);

        Map<String, String> esMap = new HashMap<String, String>();
        esMap.put(FasiPcp.SCHEDAP110, "PIN");
        esMap.put(FasiPcp.SCHEDAP111, "PIN");
        esMap.put(FasiPcp.SCHEDAP112, "PIN");
        esMap.put(FasiPcp.SCHEDAP113, "PIN");
        esMap.put(FasiPcp.SCHEDAP114, "PIN");
        esMap.put(FasiPcp.SCHEDAP1152, "CN");
        esMap.put(FasiPcp.SCHEDAP116, "CN");
        esMap.put(FasiPcp.SCHEDAP117, "CN");
        esMap.put(FasiPcp.SCHEDAP118, "CN");
        esMap.put(FasiPcp.SCHEDAP119, "CN");
        esMap.put(FasiPcp.SCHEDAP120, "CN");
        esMap.put(FasiPcp.SCHEDAP121, "CN");
        esMap.put(FasiPcp.SCHEDAP123, "CN");
        esMap.put(FasiPcp.SCHEDAP124, "CN");
        esMap.put(FasiPcp.SCHEDAAD125, "CAN");
        esMap.put(FasiPcp.SCHEDAAD126, "CAN");
        esMap.put(FasiPcp.SCHEDAAD127, "CAN");
        esMap.put(FasiPcp.SCHEDAAD128, "CAN");

        mapSchedaEformType = Collections.unmodifiableMap(esMap);


        Map<String, String> rMap = new HashMap<String, String>();
        rMap.put("1","3");
        rMap.put("2","2");
        rMap.put("3","2");
        rMap.put("4","1");
        rMap.put("5","2");
        rMap.put("6","1");
        rMap.put("7","4");
        mapTipoOe = Collections.unmodifiableMap(rMap);



        Map<String, String> aIMap = new HashMap<String, String>();
        aIMap.put("9","P");
        aIMap.put("10","A");
        aIMap.put("11","R");
        flagRitardoImportMap = Collections.unmodifiableMap(aIMap);



        Map<String, String> mIMap = new HashMap<String, String>();
        mIMap.put("add-wss","31");
        mIMap.put("mod-cir","32");
        mIMap.put("mod-minv","33");
        mIMap.put("mod-nons","34");
        mIMap.put("mod-repl","35");
        mIMap.put("mod-rev","36");
        mIMap.put("other","37");
        motiviModificaImportMap = Collections.unmodifiableMap(mIMap);

    }

    private JAXBContext jaxbContext;

    public ContractNoticeType unmarshalContractNoticeType(String xmlString) throws Exception {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            // Unmarshallers are not thread-safe.  Create a new one every time.
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            JAXBElement<ContractNoticeType> a =  (JAXBElement<ContractNoticeType>)unmarshaller.unmarshal(reader);
            return a.getValue();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public ContractAwardNoticeType unmarshalContractAwardNoticeType(String xmlString) throws Exception {
        try {
            jaxbContext = JAXBContext.newInstance(oasis.names.specification.ubl.schema.xsd.contractawardnotice_2.ObjectFactory.class);
            // Unmarshallers are not thread-safe.  Create a new one every time.
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            JAXBElement<ContractAwardNoticeType> a =  (JAXBElement<ContractAwardNoticeType>)unmarshaller.unmarshal(reader);
            return a.getValue();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }



    public void elaborateAggSchedaNAGType(SchedaNAGType schedaNAGType, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) {
        try {
            if(schedaNAGType != null && schedaNAGType.getAnacForm() != null && schedaNAGType.getAnacForm().getLotti() != null && schedaNAGType.getAnacForm().getLotti().size() > 0) {
                schedaNAGType.getAnacForm().getLotti().stream()
                        .filter(lotto -> lotto != null)
                        .forEach(lotto -> {
                            try {
                                Long codGara = res.getCodGara();
                                Long codLotto = res.getCigCodLotMap().get(lotto.getCig());

                                FaseComEsitoForm esito = new FaseComEsitoForm();

                                String esitoProceduraAnnullata = null;
                                if(lotto.getEsitoProceduraAnnullata() != null && lotto.getEsitoProceduraAnnullata().getCodice() != null) {
                                    esitoProceduraAnnullata = lotto.getEsitoProceduraAnnullata().getCodice();
                                    esito.setEsito(Long.valueOf(esitoProceduraAnnullata));
                                }

                                esito.setCodGara(codGara);
                                esito.setCodLotto(codLotto);

                                this.contrattiMapper.insertFaseComEsito(esito);
                                this.contrattiMapper.insertW9fase(codGara, codLotto, 984L, 1L, null, 1L, idScheda);
                                this.contrattiMapper.setW9faseExported(codGara, codLotto, 984L, 1L);
                                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 984L,3L, dataCreazione);

                            } catch (Exception e2) {
                                logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaNAGType", e2);
                            }
                        });
            }


        } catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaNAGType", e);
            throw e;
        }
    }


    public void elaborateSchedaSC1Type(SchedaSC1Type schedaSC1Type, ResponseElaborateAppaltoPcp res, String codein,
                                       Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception {

        try {
            Long codGara = res.getCodGara();
            Long codLotto = null;
            FaseInizialeEsecuzioneInsertForm fase = typeToFaseInizialeEsecuzioneEntry(schedaSC1Type.getAnacForm().getDatiContratto());
            String idContratto;
            String schedaJson = objectMapper.writeValueAsString(schedaSC1Type);
            if(schedaSC1Type.getAnacForm().getDatiContratto().getCig().size() == 1) {
                codLotto = res.getCigCodLotMap().get(schedaSC1Type.getAnacForm().getDatiContratto().getCig().get(0));
                Long countFase = this.contrattiMapper.getFase(codGara, codLotto, 13L);
                if(!allineaDaPcp && countFase > 0){
                    logger.warn("scheda sc1 duplicata, non inserisco");
                } else {
                    idContratto = idContrattoCig.get(schedaSC1Type.getAnacForm().getDatiContratto().getCig().get(0));
                    fase.setCodContratto(idContratto);
                    FaseInizialeEsecuzioneEntry faseIniz = this.contrattiMapper.getFaseInizialeEsecuzioneByCodContratto(idContratto);
                    if (allineaDaPcp) {
                        if (faseIniz != null) {
                            if (faseIniz.getCodContratto() != null && !faseIniz.getCodContratto().equals(idContratto)) {
                                this.contrattiMapper.setIdContrattoW9iniz(codGara, codLotto, 1L, idContratto);
                            }
                            FaseInizialeEsecuzioneInsertForm updateForm = new FaseInizialeEsecuzioneInsertForm();
                            updateForm.setCodGara(codGara);
                            updateForm.setCodLotto(codLotto);
                            updateForm.setNum(faseIniz.getNum());
                            fase.setNum(faseIniz.getNum());
                            this.contrattiMapper.updateFaseInizialeSottoscrizioneContrattoWithCodContratto(updateForm);
                        }
                        //this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, codLotto, 1L);
                        this.contrattiMapper.deleteW9FasePcpLotto(codGara, codLotto, 13L);
                        this.contrattiMapper.deleteFlussoPcpLotto(codGara, codLotto, 13L);
                    }
                    insertFaseSottoscrizioneContratto(codein, syscon, idScheda, codGara, codLotto, fase, dataCreazione, statoScheda, schedaJson, allineaDaPcp, faseIniz);
                }

            } else if(schedaSC1Type.getAnacForm().getDatiContratto().getCig().size() > 1) {
                String masterCig = schedaSC1Type.getAnacForm().getDatiContratto().getCig().get(0);
                for (String cig : schedaSC1Type.getAnacForm().getDatiContratto().getCig()) {
                    codLotto = res.getCigCodLotMap().get(cig);
                    Long countFase = this.contrattiMapper.getFase(codGara, codLotto, 13L);
                    if(!allineaDaPcp && countFase > 0){
                        logger.warn("scheda sc1 duplicata, non inserisco");
                    } else {
                        idContratto = idContrattoCig.get(cig);
                        fase.setCodContratto(idContratto);
                        FaseInizialeEsecuzioneEntry faseIniz = this.contrattiMapper.getFaseInizialeEsecuzioneByCodContratto(idContratto);
                        if (allineaDaPcp) {
                            if (faseIniz != null) {
                                if (faseIniz.getCodContratto() != null && !faseIniz.getCodContratto().equals(idContratto)) {
                                    this.contrattiMapper.setIdContrattoW9iniz(codGara, codLotto, 1L, idContratto);
                                }
                                FaseInizialeEsecuzioneInsertForm updateForm = new FaseInizialeEsecuzioneInsertForm();
                                updateForm.setCodGara(codGara);
                                updateForm.setCodLotto(codLotto);
                                updateForm.setNum(faseIniz.getNum());
                                fase.setNum(faseIniz.getNum());
                                this.contrattiMapper.updateFaseInizialeSottoscrizioneContrattoWithCodContratto(updateForm);
                            }
                            //this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, codLotto, 1L);
                            this.contrattiMapper.deleteW9FasePcpLotto(codGara, codLotto, 13L);
                            this.contrattiMapper.deleteFlussoPcpLotto(codGara, codLotto, 13L);
                        }
                        insertFaseSottoscrizioneContratto(codein, syscon, idScheda, codGara, codLotto, fase, dataCreazione, statoScheda, schedaJson, allineaDaPcp, faseIniz);
                    }

                    this.contrattiMapper.updateMasterCig(codGara, codLotto, masterCig);
                }
            }

        } catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaSC1Type", e);
            throw e;
        }

    }

    public void elaborateSchedaI1Type(SchedaI1Type schedaI1Type, ResponseElaborateAppaltoPcp res, String codein,
                                       Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaI1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;
            FaseInizialeEsecuzioneEntry iniz = this.contrattiMapper.getFaseInizialeEsecuzioneByCodContratto(schedaI1Type.getAnacForm().getIdContratto().toString());
            LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
            DatiInizioType dit = schedaI1Type.getAnacForm().getDatiInizio();

            Long countFase = this.contrattiMapper.getFase(codGara, codLotto, 2L);
            if(!allineaDaPcp && countFase > 0){
                logger.warn("scheda i1 duplicata, non inserisco");
            } else {
                if (iniz != null) {

                    FaseInizialeEsecuzioneInsertForm fiif = new FaseInizialeEsecuzioneInsertForm();
                    fiif.setCodGara(codGara);
                    fiif.setCodLotto(codLotto);

                    Long num = null;
                    if (iniz.getNum() != null) {
                        num = iniz.getNum();
                        fiif.setNum(iniz.getNum());
                    }

                    if (dit.getDataDisposizioneInizio() != null) {
                        fiif.setDataInizioProg(schedePcpUtils.offsetDateTimeToDate(dit.getDataDisposizioneInizio()));
                    }

                    if (dit.getDataApprovazione() != null) {
                        fiif.setDataApprovazioneProg(schedePcpUtils.offsetDateTimeToDate(dit.getDataApprovazione()));
                    }

                    if (lotto != null && lotto.getTipologia() != null && "L".equals(lotto.getTipologia())) {
                        if (dit.isConsegnaFrazionata() != null) {
                            fiif.setFrazionata(dit.isConsegnaFrazionata() ? "1" : "2");
                        }
                    } else if (lotto != null && lotto.getTipologia() != null && ("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia()))) {
                        if (dit.isAvvioPerFasi() != null) {
                            fiif.setFrazionata(dit.isAvvioPerFasi() ? "1" : "2");
                        }
                    }

                    if (dit.getDataVerbalePrimaConsegna() != null) {
                        fiif.setDataVerbaleCons(schedePcpUtils.offsetDateTimeToDate(dit.getDataVerbalePrimaConsegna()));
                    }

                    if (dit.getDataAvvioPrimaFase() != null) {
                        fiif.setDataVerbaleCons(schedePcpUtils.offsetDateTimeToDate(dit.getDataAvvioPrimaFase()));
                    }

                    if (dit.getDataVerbaleConsegnaDefinitiva() != null) {
                        fiif.setDataVerbaleDef(schedePcpUtils.offsetDateTimeToDate(dit.getDataVerbaleConsegnaDefinitiva()));
                    }

                    if (dit.isConsegnaSottoRiserva() != null) {
                        fiif.setRiserva(dit.isConsegnaSottoRiserva() ? "1" : "2");
                    }

                    if (dit.getDataEffettivoInizio() != null) {
                        fiif.setDataVerbInizio(schedePcpUtils.offsetDateTimeToDate(dit.getDataEffettivoInizio()));
                    }

                    if (dit.getDataFinePrevista() != null) {
                        fiif.setDataTermine(schedePcpUtils.offsetDateTimeToDate(dit.getDataFinePrevista()));
                    }

                    FaseInizPubbEsitoForm pubb = new FaseInizPubbEsitoForm();
                    pubb.setCodGara(codGara);
                    pubb.setCodLotto(codLotto);
                    ;
                    fiif.setPubblicazioneEsito(pubb);

                    BaseResponse response = this.fasiManager.insertFaseInizialeEsecuzione(fiif);
                    String schedaJson = objectMapper.writeValueAsString(schedaI1Type);
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 2L, num, 1L, dataCreazione, schedaJson, null, null);
                    if (statoScheda.equals("CONF")) {
                        this.contrattiMapper.setW9faseExported(codGara, codLotto, 2L, num);
                        this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 2L, num, 3L, dataCreazione, schedaJson, null, null);
                    }
                    this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 2L, num, idScheda);
                }
            }
        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaI1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaSA1Type( SchedaSA1Type schedaSA1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaSA1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseAvanzamentoInsertForm faif = new FaseAvanzamentoInsertForm();
            AvanzamentoType avan = schedaSA1Type.getAnacForm().getAvanzamento();
            faif.setCodGara(codGara);
            faif.setCodLotto(codLotto);

            if(avan.getDenominazioneAvanzamento() != null) {
                faif.setDenomAvanzamento(avan.getDenominazioneAvanzamento());
            }

            if(avan.getModalitaPagamento() != null) {
                faif.setFlagPagamento(Long.valueOf(avan.getModalitaPagamento().getCodice()));
            }

            if(avan.getImpAnticipazione() != null){
                faif.setImportoAnticipazione(avan.getImpAnticipazione());
            }

            if(avan.getDataCertificatoAnticipazione() != null){
                faif.setDataAnticipazione(schedePcpUtils.offsetDateTimeToDate(avan.getDataCertificatoAnticipazione()));
            }

            if(avan.getDataAvanzamento() != null){
                faif.setDataRaggiungimento(schedePcpUtils.offsetDateTimeToDate(avan.getDataAvanzamento()));
            }

            if(avan.getImpSal() != null){
                faif.setImportoSal(avan.getImpSal());
            }

            if(avan.getDataCertificatoMandatoPagamento() != null){
                faif.setDataCertificato(schedePcpUtils.offsetDateTimeToDate(avan.getDataCertificatoMandatoPagamento()));
            }

            if(avan.getImpCertificatoMandatoPagamento() != null){
                faif.setImportoCertificato(avan.getImpCertificatoMandatoPagamento());
            }

            if(avan.getAvanzamento() != null && StringUtils.isNotBlank(avan.getAvanzamento().getCodice())){
                faif.setFlagRitardo(flagRitardoImportMap.get(avan.getAvanzamento().getCodice()));
            }

            if(avan.getNumGiorniScostamento() != null){
                faif.setNumGiorniScost(avan.getNumGiorniScostamento().longValue());
            }

            if(avan.getNumGiorniProroga() != null){
                faif.setNumGiorniProroga(avan.getNumGiorniProroga().longValue());
            }

            ResponseInsert response = this.fasiManager.insertFaseAvanzamento(faif);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaSA1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 3L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 3L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 3L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 3L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaSA1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaCO1Type( SchedaCO1Type schedaCO1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = null;
            Long codLotto = null;
            if(schedaCO1Type.getAnacForm().getIdContratto() != null) {
                codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaCO1Type.getAnacForm().getIdContratto().toString());
                codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;
            } else {
                codLotto = this.contrattiMapper.getCodLottByCig(schedaCO1Type.getAnacForm().getCig(), codGara);
            }

            Long countFase = this.contrattiMapper.getFase(codGara, codLotto, 4L);
            if(!allineaDaPcp && countFase > 0){
                logger.warn("scheda co1 duplicata, non inserisco");
            } else {

                FaseConclusioneInsertForm fcif = new FaseConclusioneInsertForm();
                ConclusioneType conType = schedaCO1Type.getAnacForm().getConclusione();

                fcif.setCodGara(codGara);
                fcif.setCodLotto(codLotto);

                if (conType.getCausaInterruzioneAnticipata() != null) {
                    fcif.setInterruzioneAnticipata(conType.getCausaInterruzioneAnticipata().getCodice());
                }

                if (conType.getDataInterruzioneAnticipata() != null) {
                    fcif.setDataTermineContrattuale(schedePcpUtils.offsetDateTimeToDate(conType.getDataInterruzioneAnticipata()));
                }

                if (conType.getOneriEconomiciRisoluzioneRecesso() != null && conType.getOneriEconomiciRisoluzioneRecesso().getCodice() != null) {
                    fcif.setFlagOneri(conType.getOneriEconomiciRisoluzioneRecesso().getCodice());
                }

                if (conType.isIncamerataPolizza() != null) {
                    fcif.setFlagPolizza(conType.isIncamerataPolizza() ? "1" : "2");
                }

                if (conType.getDataUltimazione() != null) {
                    fcif.setDataUltimazione(schedePcpUtils.offsetDateTimeToDate(conType.getDataUltimazione()));
                }

                if (conType.getNumeroInfortuni() != null) {
                    fcif.setNumInfortuni(conType.getNumeroInfortuni().longValue());
                }

                if (conType.getDiCuiPostumiPermanenti() != null) {
                    fcif.setNumInfortuniPermanenti(conType.getDiCuiPostumiPermanenti().longValue());
                }

                if (conType.getDiCuiMortali() != null) {
                    fcif.setNumInfortuniMortali(conType.getDiCuiMortali().longValue());
                }

                ResponseInsert response = this.fasiManager.insertFaseConclusioneContratto(fcif);
                Long num = response.getData();
                String schedaJson = objectMapper.writeValueAsString(schedaCO1Type);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 4L, num, 1L, dataCreazione, schedaJson, null, null);
                if (statoScheda.equals("CONF")) {
                    this.contrattiMapper.setW9faseExported(codGara, codLotto, 4L, num);
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 4L, num, 3L, dataCreazione, schedaJson, null, null);
                }
                this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 4L, num, idScheda);
            }
        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaCO1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaCO2Type( SchedaCO2Type schedaCO2Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            Long codLotto = this.contrattiMapper.getCodLottByCig(schedaCO2Type.getAnacForm().getCig(), codGara);

            Long countFase = this.contrattiMapper.getFase(codGara, codLotto, 19L);
            if(!allineaDaPcp && countFase > 0){
                logger.warn("scheda co2 duplicata, non inserisco");
            } else {
                FaseConclusioneAffidamentiDirettiInsertForm cad = new FaseConclusioneAffidamentiDirettiInsertForm();
                ConclusioneType1 conType = schedaCO2Type.getAnacForm().getConclusione();

                cad.setCodGara(codGara);
                cad.setCodLotto(codLotto);

                if (conType.getDataInizio() != null) {
                    cad.setDataVerbInizio(schedePcpUtils.offsetDateTimeToDate(conType.getDataInizio()));
                }

                if (conType.getDataUltimazione() != null) {
                    cad.setDataUltimazione(schedePcpUtils.offsetDateTimeToDate(conType.getDataUltimazione()));
                }

                if (conType.getImporto() != null) {
                    cad.setImportoCertificato(conType.getImporto());
                }

                ResponseInsert response = this.fasiManager.insertFaseConclusioneAffidamentiDiretti(cad);
                Long num = response.getData();
                String schedaJson = objectMapper.writeValueAsString(schedaCO2Type);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 19L, num, 1L, dataCreazione, schedaJson, null, null);
                if (statoScheda.equals("CONF")) {
                    this.contrattiMapper.setW9faseExported(codGara, codLotto, 19L, num);
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 19L, num, 3L, dataCreazione, schedaJson, null, null);
                }
                this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 19L, num, idScheda);
            }
        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaCO2Type", e);
            throw e;
        }
    }

    public void elaborateSchedaCL1Type( SchedaCL1Type schedaCL1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaCL1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            Long countFase = this.contrattiMapper.getFase(codGara, codLotto, 5L);
            if(!allineaDaPcp && countFase > 0){
                logger.warn("scheda cl1 duplicata, non inserisco");
            } else {
                FaseCollaudoInsertForm fcif = new FaseCollaudoInsertForm();
                CollaudoType collType = schedaCL1Type.getAnacForm().getCollaudo();

                fcif.setCodGara(codGara);
                fcif.setCodLotto(codLotto);

                if (collType.getDataCollaudo() != null) {
                    fcif.setDataCollaudoStatico(schedePcpUtils.offsetDateTimeToDate(collType.getDataCollaudo()));
                }

                if (collType.getDataCertificato() != null) {
                    fcif.setDataCertEsecuzione(schedePcpUtils.offsetDateTimeToDate(collType.getDataCertificato()));
                }

                if (collType.getModoCollaudo() != null) {
                    fcif.setModalitaCollaudo(Long.valueOf(collType.getModoCollaudo().getCodice()));
                }

                if (collType.getDataNomina() != null) {
                    fcif.setDataNomina(schedePcpUtils.offsetDateTimeToDate(collType.getDataNomina()));
                }


                if (collType.getDataInizio() != null) {
                    fcif.setDataInizioOperazioni(schedePcpUtils.offsetDateTimeToDate(collType.getDataInizio()));
                }

                if (collType.getDataRedazioneCertificato() != null) {
                    fcif.setDataRedazioneCertificato(schedePcpUtils.offsetDateTimeToDate(collType.getDataRedazioneCertificato()));
                }

                if (collType.getDataDeliberaAmmissibilita() != null) {
                    fcif.setDataDelibera(schedePcpUtils.offsetDateTimeToDate(collType.getDataDeliberaAmmissibilita()));
                }

                if (collType.getEsito() != null) {
                    if (StringUtils.equals(collType.getEsito().getValue(), "POSITIVO")) {
                        fcif.setEsitoCollaudo("P");
                    } else {
                        fcif.setEsitoCollaudo("N");
                    }
                }

                if (collType.getQuadroEconomicoStandard() != null) {
                    QuadroEconomicoType qe = collType.getQuadroEconomicoStandard();
                    if (qe.getImpLavori() != null) {
                        fcif.setImportoFinaleLavori(qe.getImpLavori());
                    }

                    if (qe.getImpServizi() != null) {
                        fcif.setImportoFinaleServizi(qe.getImpServizi());
                    }

                    if (qe.getImpForniture() != null) {
                        fcif.setImportoFinaleForniture(qe.getImpForniture());
                    }

                    if (qe.getImpTotaleSicurezza() != null) {
                        fcif.setImportoFinaleSicurezza(qe.getImpTotaleSicurezza());
                    }

                    if (qe.getUlterioriSommeNoRibasso() != null) {
                        fcif.setImpNonAssog(qe.getUlterioriSommeNoRibasso());
                    }

                    if (qe.getImpProgettazione() != null) {
                        fcif.setImportoProgettazione(qe.getImpProgettazione());
                    }

                    if (qe.getSommeOpzioniRinnovi() != null) {
                        fcif.setImpFinaleOpzioni(qe.getSommeOpzioniRinnovi());
                    }

                    if (qe.getSommeRipetizioni() != null) {
                        fcif.setImpFinaleRipetizioni(qe.getSommeRipetizioni());
                    }

                    if (qe.getSommeADisposizione() != null) {
                        fcif.setImportoDisposizione(qe.getSommeADisposizione());
                    }

                }

                if (collType.getOneri() != null) {
                    fcif.setOneriDerivanti(collType.getOneri());
                }

                if (collType.getNumeroTotaleRiserve() != null) {
                    fcif.setNumRiserve(collType.getNumeroTotaleRiserve().longValue());
                }

                //modalita definizione non aggiunta
                /* rimossi temporameamente per err40
                issue anac https://github.com/anticorruzione/npa/issues/1192 */

                ResponseInsert response = this.fasiManager.insertFaseCollaudo(fcif);
                Long num = response.getData();
                String schedaJson = objectMapper.writeValueAsString(schedaCL1Type);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 5L, num, 1L, dataCreazione, schedaJson, null, null);
                if (statoScheda.equals("CONF")) {
                    this.contrattiMapper.setW9faseExported(codGara, codLotto, 5L, num);
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto, codein, syscon, 5L, num, 3L, dataCreazione, schedaJson, null, null);
                }
                this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 5L, num, idScheda);
            }

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaCL1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaM1Type( SchedaM1Type schedaM1Type, ResponseElaborateAppaltoPcp res, String codein,
                                        Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaM1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseVarianteInsertForm fvif = new FaseVarianteInsertForm();
            ModificaContrattualeType m1 = schedaM1Type.getAnacForm().getModificaContrattuale();
            String eform = schedaM1Type.getEform();

            fvif.setCodGara(codGara);
            fvif.setCodLotto(codLotto);

            if(m1.getDataApprovazione() != null){
                fvif.setDataVerbaleAppr(schedePcpUtils.offsetDateTimeToDate(m1.getDataApprovazione()));
            }

            if(m1.getUrlDocumentazione() != null){
                fvif.setUrlVariantiCo(m1.getUrlDocumentazione());
            }

            if(m1.getMotivoRevisionePrezzi() != null){
                fvif.setMotivoRevPrezzi(Long.valueOf(m1.getMotivoRevisionePrezzi().getCodice()));
            }

            if(m1.getCigNuovaProcedura() != null){
                fvif.setCigNuovaProc(m1.getCigNuovaProcedura());
            }

            if(m1.getQuadroEconomicoStandardRideterminato() != null){
                QuadroEconomicoModificaContrattualeType qemc = m1.getQuadroEconomicoStandardRideterminato();

                if(qemc.getImpLavori() != null){
                    fvif.setImportoRideterminatoLavori(qemc.getImpLavori());
                }

                if(qemc.getImpServizi() != null){
                    fvif.setImportoRideterminatoServizi(qemc.getImpServizi());
                }

                if(qemc.getImpForniture() != null){
                    fvif.setImportoRideterminatoForniture(qemc.getImpForniture());
                }

                if(qemc.getImpTotaleSicurezza() != null){
                    fvif.setImportoSicurezza(qemc.getImpTotaleSicurezza());
                }

                if(qemc.getImpProgettazione() != null){
                    fvif.setImportoProgettazione(qemc.getImpProgettazione());
                }

                if(qemc.getUlterioriSommeNoRibasso() != null){
                    fvif.setImportoNonAssog(qemc.getUlterioriSommeNoRibasso());
                }

                if(qemc.getSommeADisposizione() != null){
                    fvif.setImportoDisposizione(qemc.getSommeADisposizione());
                }

                if(qemc.getNumGiorniProroga() != null){
                    fvif.setNumGiorniProroga(qemc.getNumGiorniProroga().longValue());
                }
            }

            if(StringUtils.isNotBlank(eform)) {
                fvif.setEform(eform);
            }

            fvif.setImportoSubtotale(safeSum(
                    fvif.getImportoRideterminatoLavori(),
                    fvif.getImportoRideterminatoServizi(),
                    fvif.getImportoRideterminatoForniture()
            ));

            fvif.setImportoComplAppalto(safeSum(
                    fvif.getImportoSubtotale(),
                    fvif.getImportoSicurezza(),
                    fvif.getImportoProgettazione(),
                    fvif.getImportoNonAssog()
            ));

            fvif.setImportoComplIntervento(safeSum(
                    fvif.getImportoComplAppalto(),
                    fvif.getImportoDisposizione()
            ));

            ResponseInsert response = this.fasiManager.insertFaseVariante(fvif);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaM1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 7L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 7L, num, idScheda);


        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaM1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaM140Type( SchedaM140Type schedaM140Type, ResponseElaborateAppaltoPcp res, String codein,
                                          Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaM140Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseVarianteInsertForm fvif = new FaseVarianteInsertForm();
            ModificaContrattuale40Type m140 = schedaM140Type.getAnacForm().getModificaContrattuale();
            String eform = schedaM140Type.getEform();

            fvif.setCodGara(codGara);
            fvif.setCodLotto(codLotto);

            if(m140.getDataApprovazione() != null){
                fvif.setDataVerbaleAppr(schedePcpUtils.offsetDateTimeToDate(m140.getDataApprovazione()));
            }

            if(m140.getUrlDocumentazione() != null){
                fvif.setUrlVariantiCo(m140.getUrlDocumentazione());
            }

            if(m140.getMotivoRevisionePrezzi() != null){
                fvif.setMotivoRevPrezzi(Long.valueOf(m140.getMotivoRevisionePrezzi().getCodice()));
            }

            if(m140.getCigNuovaProcedura() != null){
                fvif.setCigNuovaProc(m140.getCigNuovaProcedura());
            }

            if(m140.getQuadroEconomicoConcessioniRideterminato() != null){

                QuadroEconomicoConcessioniType qect = m140.getQuadroEconomicoConcessioniRideterminato();

                if(qect.getImpLavori() != null){
                    fvif.setImportoRideterminatoLavori(qect.getImpLavori());
                }

                if(qect.getImpForniture() != null){
                    fvif.setImportoRideterminatoForniture(qect.getImpForniture());
                }

                if(qect.getImpServizi() != null){
                    fvif.setImportoRideterminatoServizi(qect.getImpServizi());
                }

                if(qect.getFinanziamentiCanoniPA() != null){
                    fvif.setImpFinpa(qect.getFinanziamentiCanoniPA());
                }

                if(qect.getEntrateUtenza() != null){
                    fvif.setEntrUtenza(qect.getEntrateUtenza());
                }

                if(qect.getIntroitoAttivo() != null){
                    fvif.setIntrAttivo(qect.getIntroitoAttivo());
                }

                if(qect.getImpTotaleSicurezza() != null){
                    fvif.setImportoSicurezza(qect.getImpTotaleSicurezza());
                }

                if(qect.getUlterioriSommeNoRibasso() != null){
                    fvif.setImportoNonAssog(qect.getUlterioriSommeNoRibasso());
                }

                if(qect.getSommeOpzioniRinnovi() != null){
                    fvif.setImpOpzioni(qect.getSommeOpzioniRinnovi());
                }

                if(qect.getSommeADisposizione() != null){
                    fvif.setImportoDisposizione(qect.getSommeADisposizione());
                }
            }

            if(StringUtils.isNotBlank(eform)) {
                fvif.setEform(eform);
            }

            fvif.setImportoSubtotale(safeSum(
                    fvif.getImportoRideterminatoLavori(),
                    fvif.getImportoRideterminatoServizi(),
                    fvif.getImportoRideterminatoForniture()
            ));

            fvif.setImportoComplAppalto(safeSum(
                    fvif.getImportoSubtotale(),
                    fvif.getImportoSicurezza(),
                    fvif.getImportoProgettazione(),
                    fvif.getImportoNonAssog()
            ));

            fvif.setImportoComplIntervento(safeSum(
                    fvif.getImportoComplAppalto(),
                    fvif.getImportoDisposizione()
            ));

            ResponseInsert response = this.fasiManager.insertFaseVariante(fvif);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaM140Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 7L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 7L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaM140Type", e);
            throw e;
        }
    }

    public void elaborateSchedaM2Type( SchedaM2Type schedaM2Type, ResponseElaborateAppaltoPcp res, String codein,
                                        Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaM2Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseVarianteInsertForm fvif = new FaseVarianteInsertForm();
            ModificaContrattualeType1 m2 = schedaM2Type.getAnacForm().getModificaContrattuale();

            fvif.setCodGara(codGara);
            fvif.setCodLotto(codLotto);

            if(m2.getDataApprovazione() != null){
                fvif.setDataVerbaleAppr(schedePcpUtils.offsetDateTimeToDate(m2.getDataApprovazione()));
            }

            if(m2.getUrlDocumentazione() != null){
                fvif.setUrlVariantiCo(m2.getUrlDocumentazione());
            }

            if(m2.getMotivoRevisionePrezzi() != null){
                fvif.setMotivoRevPrezzi(Long.valueOf(m2.getMotivoRevisionePrezzi().getCodice()));
            }

            if(m2.getCigNuovaProcedura() != null){
                fvif.setCigNuovaProc(m2.getCigNuovaProcedura());
            }

            if(m2.getQuadroEconomicoStandardRideterminato() != null){
                QuadroEconomicoModificaContrattualeType qemc = m2.getQuadroEconomicoStandardRideterminato();

                if(qemc.getImpForniture() != null){
                    fvif.setImportoRideterminatoForniture(qemc.getImpForniture());
                }

                if(qemc.getImpServizi() != null){
                    fvif.setImportoRideterminatoServizi(qemc.getImpServizi());
                }

                if(qemc.getImpLavori() != null){
                    fvif.setImportoRideterminatoLavori(qemc.getImpLavori());
                }

                if(qemc.getImpTotaleSicurezza() != null){
                    fvif.setImportoSicurezza(qemc.getImpTotaleSicurezza());
                }

                if(qemc.getImpProgettazione() != null){
                    fvif.setImportoProgettazione(qemc.getImpProgettazione());
                }

                if(qemc.getUlterioriSommeNoRibasso() != null){
                    fvif.setImportoNonAssog(qemc.getUlterioriSommeNoRibasso());
                }

                if(qemc.getSommeADisposizione() != null){
                    fvif.setImportoDisposizione(qemc.getSommeADisposizione());
                }

                if(qemc.getNumGiorniProroga() != null){
                    fvif.setNumGiorniProroga(qemc.getNumGiorniProroga().longValue());
                }
            }

            if(m2.getDatiBaseModificaContrattuale() != null){
                DatiBaseModificaContrattualeType dbmc = m2.getDatiBaseModificaContrattuale();

                if(dbmc.getDataSottoscrizione() != null){
                    fvif.setDataAttoAggiuntivo(schedePcpUtils.offsetDateTimeToDate(dbmc.getDataSottoscrizione()));
                }

                if(dbmc.getMotiviModifica() != null){

                    LottoDynamicValue ldv = null;
                    List<LottoDynamicValue> motiviModificaList = new ArrayList<>();

                    for(Map.Entry<String, String> lotEntry : motiviModificaImportMap.entrySet()) {
                        String codice = lotEntry.getKey();
                        String value = lotEntry.getValue();

                        ldv = new LottoDynamicValue();
                        ldv.setCodice(Long.valueOf(value));

                        if(codice.equals(dbmc.getMotiviModifica().getCodice())){
                            ldv.setValue(1L);
                        } else {
                            ldv.setValue(2L);
                        }

                        motiviModificaList.add(ldv);
                    }

                    fvif.setMotivazioniVariante(motiviModificaList);
                }

                if(dbmc.getCausaModifica() !=  null){
                    fvif.setAltreMotivazioni(dbmc.getCausaModifica());
                }
            }

            fvif.setImportoSubtotale(safeSum(
                    fvif.getImportoRideterminatoLavori(),
                    fvif.getImportoRideterminatoServizi(),
                    fvif.getImportoRideterminatoForniture()
            ));

            fvif.setImportoComplAppalto(safeSum(
                    fvif.getImportoSubtotale(),
                    fvif.getImportoSicurezza(),
                    fvif.getImportoProgettazione(),
                    fvif.getImportoNonAssog()
            ));

            fvif.setImportoComplIntervento(safeSum(
                    fvif.getImportoComplAppalto(),
                    fvif.getImportoDisposizione()
            ));

            ResponseInsert response = this.fasiManager.insertFaseVariante(fvif);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaM2Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 7L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 7L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaM2Type", e);
            throw e;
        }
    }

    public void elaborateSchedaM240Type( SchedaM240Type schedaM240Type, ResponseElaborateAppaltoPcp res, String codein,
                                          Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaM240Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseVarianteInsertForm fvif = new FaseVarianteInsertForm();
            ModificaContrattualeType2 m240 = schedaM240Type.getAnacForm().getModificaContrattuale();

            fvif.setCodGara(codGara);
            fvif.setCodLotto(codLotto);

            if(m240.getDataApprovazione() != null){
                fvif.setDataVerbaleAppr(schedePcpUtils.offsetDateTimeToDate(m240.getDataApprovazione()));
            }

            if(m240.getUrlDocumentazione() != null){
                fvif.setUrlVariantiCo(m240.getUrlDocumentazione());
            }

            if(m240.getMotivoRevisionePrezzi() != null){
                fvif.setMotivoRevPrezzi(Long.valueOf(m240.getMotivoRevisionePrezzi().getCodice()));
            }

            if(m240.getCigNuovaProcedura() != null){
                fvif.setCigNuovaProc(m240.getCigNuovaProcedura());
            }

            if(m240.getQuadroEconomicoConcessioniRideterminato() != null){

                QuadroEconomicoConcessioniType qec = m240.getQuadroEconomicoConcessioniRideterminato();

                if(qec.getImpLavori() != null){
                    fvif.setImportoRideterminatoLavori(qec.getImpLavori());
                }

                if(qec.getImpServizi() != null){
                    fvif.setImportoRideterminatoServizi(qec.getImpServizi());
                }

                if(qec.getImpForniture() != null){
                    fvif.setImportoRideterminatoForniture(qec.getImpForniture());
                }

                if(qec.getFinanziamentiCanoniPA() != null){
                    fvif.setImpFinpa(qec.getFinanziamentiCanoniPA());
                }

                if(qec.getEntrateUtenza() != null){
                    fvif.setEntrUtenza(qec.getEntrateUtenza());
                }

                if(qec.getIntroitoAttivo() != null){
                    fvif.setIntrAttivo(qec.getIntroitoAttivo());
                }

                if(qec.getImpTotaleSicurezza() != null){
                    fvif.setImportoSicurezza(qec.getImpTotaleSicurezza());
                }

                if(qec.getUlterioriSommeNoRibasso() != null){
                    fvif.setImportoNonAssog(qec.getUlterioriSommeNoRibasso());
                }

                if(qec.getSommeOpzioniRinnovi() != null){
                    fvif.setImpOpzioni(qec.getSommeOpzioniRinnovi());
                }

                if(qec.getSommeADisposizione() != null){
                    fvif.setImportoDisposizione(qec.getSommeADisposizione());
                }
            }

            if(m240.getDatiBaseModificaContrattuale() != null){

                DatiBaseModificaContrattualeType dbmc = m240.getDatiBaseModificaContrattuale();

                if(dbmc.getDataSottoscrizione() != null){
                    fvif.setDataAttoAggiuntivo(schedePcpUtils.offsetDateTimeToDate(dbmc.getDataSottoscrizione()));
                }

                if(dbmc.getMotiviModifica() != null){

                    LottoDynamicValue ldv = null;
                    List<LottoDynamicValue> motiviModificaList = new ArrayList<>();

                    for(Map.Entry<String, String> lotEntry : motiviModificaImportMap.entrySet()) {
                        String codice = lotEntry.getKey();
                        String value = lotEntry.getValue();

                        ldv = new LottoDynamicValue();
                        ldv.setCodice(Long.valueOf(value));

                        if(codice.equals(dbmc.getMotiviModifica().getCodice())){
                            ldv.setValue(1L);
                        } else {
                            ldv.setValue(2L);
                        }

                        motiviModificaList.add(ldv);
                    }
                    fvif.setMotivazioniVariante(motiviModificaList);
                }

                if(dbmc.getCausaModifica() != null){
                    fvif.setAltreMotivazioni(dbmc.getCausaModifica());
                }
            }

            fvif.setImportoSubtotale(safeSum(
                    fvif.getImportoRideterminatoLavori(),
                    fvif.getImportoRideterminatoServizi(),
                    fvif.getImportoRideterminatoForniture()
            ));

            fvif.setImportoComplAppalto(safeSum(
                    fvif.getImportoSubtotale(),
                    fvif.getImportoSicurezza(),
                    fvif.getImportoProgettazione(),
                    fvif.getImportoNonAssog()
            ));

            fvif.setImportoComplIntervento(safeSum(
                    fvif.getImportoComplAppalto(),
                    fvif.getImportoDisposizione()
            ));

            ResponseInsert response = this.fasiManager.insertFaseVariante(fvif);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaM240Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 7L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 7L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 7L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaM240Type", e);
            throw e;
        }
    }

    public void elaborateSchedaAC1Type( SchedaAC1Type schedaAC1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaAC1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseAccordoBonarioInsertForm fabif = new FaseAccordoBonarioInsertForm();
            AccordoBonarioType abType = schedaAC1Type.getAnacForm().getAccordoBonario();

            fabif.setCodGara(codGara);
            fabif.setCodLotto(codLotto);

            if(abType.getDataAccordo() != null){
                fabif.setDataAccordo(schedePcpUtils.offsetDateTimeToDate(abType.getDataAccordo()));
            }

            if(abType.getNumeroRiserve() != null){
                fabif.setNumRiserve(abType.getNumeroRiserve().longValue());
            }

            if(abType.getOneriDerivanti() != null){
                fabif.setOneriDerivanti(abType.getOneriDerivanti());
            }



            ResponseInsert response = this.fasiManager.insertFaseAccordoBonario(fabif);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaAC1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 8L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 8L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 8L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 8L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaAC1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaSO1Type( SchedaSO1Type schedaSO1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaSO1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseSospensioneInsertForm fso = new FaseSospensioneInsertForm();
            SospensioneType soType = schedaSO1Type.getAnacForm().getSospensione();

            fso.setCodGara(codGara);
            fso.setCodLotto(codLotto);

            if(soType.isSospensioneParziale() != null) {
                fso.setSospParziale(soType.isSospensioneParziale() ? "1" : "2");
            }

            if(soType.getDataVerbaleSospensione() != null) {
                fso.setDataVerbSosp(schedePcpUtils.offsetDateTimeToDate(soType.getDataVerbaleSospensione()));
            }

            if(soType.getMotivoSospensione() != null && soType.getMotivoSospensione().getCodice() != null) {
                fso.setMotivoSosp(Long.valueOf(soType.getMotivoSospensione().getCodice()));
            }

            ResponseInsert response = this.fasiManager.insertFaseSospensione(fso);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaSO1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 6L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 6L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 6L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 6L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaSO1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaSQ1Type( SchedaSQ1Type schedaSQ1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdScheda(schedaSQ1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            List<Long> numList = this.contrattiMapper.getW9fasiNumByIdScheda(schedaSQ1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long num = numList != null && !numList.isEmpty() ? numList.get(0) : null;

            FaseSuperamentoQuartoContrattualeInsertForm sq = new FaseSuperamentoQuartoContrattualeInsertForm();
            SospensioneType1 so1Type = schedaSQ1Type.getAnacForm().getSospensione();

            sq.setCodGara(codGara);
            sq.setCodLotto(codLotto);
            sq.setNum(num);

            if(so1Type.getDataSuperamento() != null) {
                sq.setDataSuperQuarto(schedePcpUtils.offsetDateTimeToDate(so1Type.getDataSuperamento()));
            }

            ResponseInsert response = this.fasiManager.insertFaseSuperamentoQuartoContrattuale(sq);
            String schedaJson = objectMapper.writeValueAsString(schedaSQ1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 14L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 14L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 14L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 14L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaSQ1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaRI1Type( SchedaRI1Type schedaRI1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdScheda(schedaRI1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            List<Long> numList = this.contrattiMapper.getW9fasiNumByIdScheda(schedaRI1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long num = numList != null && !numList.isEmpty() ? numList.get(0) : null;

            FaseRipresaPrestazioneInsertForm rp = new FaseRipresaPrestazioneInsertForm();
            SospensioneType2 so2Type = schedaRI1Type.getAnacForm().getSospensione();

            rp.setCodGara(codGara);
            rp.setCodLotto(codLotto);
            rp.setNum(num);;

            if(so2Type.getDataVerbaleRipresa() != null) {
                rp.setDataVerbRipr(schedePcpUtils.offsetDateTimeToDate(so2Type.getDataVerbaleRipresa()));
            }

            if(so2Type.getIncidenzaCronoprogramma() != null) {
                rp.setIncCrono(so2Type.getIncidenzaCronoprogramma());
            }

            String flagSuperoTempo = so2Type.isSuperatoTempo() ? "1" : "2";

            if(so2Type.isRiserve() != null) {
                rp.setFlagRiserve(so2Type.isRiserve() ? "1" : "2");
            }

            if(so2Type.isVerbaleNonSottoscritto() != null) {
                rp.setFlagVerbale(so2Type.isVerbaleNonSottoscritto() ? "1" : "2");
            }

            ResponseInsert response = this.fasiManager.insertFaseRipresaPrestazione(rp);
            this.contrattiMapper.updateFlagSuperamentoQuartoTemporale(codGara, codLotto, num, flagSuperoTempo);
            String schedaJson = objectMapper.writeValueAsString(schedaRI1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 15L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 15L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 15L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 15L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaRI1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaRSU1Type( SchedaRSU1Type schedaRSU1Type, ResponseElaborateAppaltoPcp res, String codein,
                                          Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaRSU1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseSubappaltoInsertForm frsu = new FaseSubappaltoInsertForm();
            SubappaltoType subaType = schedaRSU1Type.getAnacForm().getSubappalto();

            frsu.setCodGara(codGara);
            frsu.setCodLotto(codLotto);

            if(schedaRSU1Type.getEspd() != null) {
                frsu.setDgue(schedaRSU1Type.getEspd());
            }

            if(subaType.getCodiciFiscaliOE() != null ) {
                List<MandanteEntry> mandanti = new ArrayList<MandanteEntry>();
                if(subaType.getCodiciFiscaliOE().size() > 0) {
                    Long i = 1L;
                    for (String CfImp : subaType.getCodiciFiscaliOE()) {
                        List<String> codImpList = this.contrattiMapper.esisteImpresaCfList(CfImp, codein);
                        String codImp = codImpList != null && !codImpList.isEmpty() ? codImpList.get(0) : null;
                        if(codImp == null) {
                            ImpresaEntry imp = new ImpresaEntry();
                            codImp = this.calcolaCodificaAutomatica("IMPR", "CODIMP");
                            imp.setCodiceImpresa(codImp);
                            imp.setCodiceFiscale(CfImp);
                            imp.setStazioneAppaltante(codein);
                            imp.setRagioneSociale(CfImp);
                            this.contrattiMapper.insertImpresa(imp);

                            this.contrattiMapper.insertTeim(codImp, "", "", CfImp);
                            Long maxId = wgcManager.getNextId("IMPLEG");
                            this.contrattiMapper.insertImpleg(maxId, codImp, "");
                        }
                        if(i > 1L) {
                            MandanteEntry mand = new MandanteEntry();
                            mand.setNomeMandante(codImp);
                            mandanti.add(mand);
                        } else {
                            frsu.setCodImpresa(codImp);;
                        }
                        i++;
                    }
                    frsu.setMandanti(mandanti);;
                }
            }

            if(subaType.getCodiceFiscaleAggiudicatario() != null) {

                List<String> codImpList = this.contrattiMapper.esisteImpresaCfList(subaType.getCodiceFiscaleAggiudicatario(), codein);
                String codImp = codImpList != null && !codImpList.isEmpty() ? codImpList.get(0) : null;
                if(codImp == null) {
                    ImpresaEntry imp = new ImpresaEntry();
                    codImp = this.calcolaCodificaAutomatica("IMPR", "CODIMP");
                    imp.setCodiceImpresa(codImp);
                    imp.setCodiceFiscale(subaType.getCodiceFiscaleAggiudicatario());
                    imp.setStazioneAppaltante(codein);
                    imp.setRagioneSociale(subaType.getCodiceFiscaleAggiudicatario());
                    this.contrattiMapper.insertImpresa(imp);

                    this.contrattiMapper.insertTeim(codImp, "", "", subaType.getCodiceFiscaleAggiudicatario());
                    Long maxId = wgcManager.getNextId("IMPLEG");
                    this.contrattiMapper.insertImpleg(maxId, codImp, "");
                }
                frsu.setCodImpresaAgg(codImp);
            }

            if(subaType.getOggetto() != null) {
                frsu.setOggetto(subaType.getOggetto());
            }

            if(subaType.getImportoPresunto() != null) {
                frsu.setImportoPresunto(subaType.getImportoPresunto());
            }

            if(subaType.getCategoria() != null) {
                frsu.setIdCategoria(subaType.getCategoria().getCodice());
            }

            if(subaType.getCpv() != null && subaType.getCpv().size() > 0) {
                String cpvLike = subaType.getCpv().get(0).getCodice() + "%";
                List<String> descCpvList = this.contrattiMapper.getCpvLike(cpvLike);
                if(descCpvList != null && descCpvList.size() > 0 && descCpvList.get(0) != null) {
                    frsu.setIdCpv(descCpvList.get(0));
                }
            }

            ResponseInsert response = this.fasiManager.insertFaseRichiestaSubappalto(frsu);
            Long num = response.getData();
            String schedaJson = objectMapper.writeValueAsString(schedaRSU1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 16L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 16L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 16L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 16L, num, idScheda);
        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaRSU1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaES1Type( SchedaES1Type schedaES1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdScheda(schedaES1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            List<Long> numList = this.contrattiMapper.getW9fasiNumByIdScheda(schedaES1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long num = numList != null && !numList.isEmpty() ? numList.get(0) : null;

            FaseEsitoSubappaltoInsertForm fes = new FaseEsitoSubappaltoInsertForm();
            SubappaltoType1 suba1Type = schedaES1Type.getAnacForm().getSubappalto();

            fes.setCodGara(codGara);
            fes.setCodLotto(codLotto);
            fes.setNum(num);

            if(suba1Type.getDataAutorizzazione() != null) {
                fes.setDataAuth(schedePcpUtils.offsetDateTimeToDate(suba1Type.getDataAutorizzazione()));
            }

            if(suba1Type.getMotivoMancatoSubappalto() != null && suba1Type.getMotivoMancatoSubappalto().getCodice() != null) {
                fes.setMotivoMancatoSub(Long.valueOf(suba1Type.getMotivoMancatoSubappalto().getCodice()));
            }

            ResponseInsert response = this.fasiManager.insertFaseEsitoSubappalto(fes);
            String schedaJson = objectMapper.writeValueAsString(schedaES1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 17L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 17L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 17L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 17L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaES1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaCS1Type( SchedaCS1Type schedaCS1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{
            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdScheda(schedaCS1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            List<Long> numList = this.contrattiMapper.getW9fasiNumByIdScheda(schedaCS1Type.getAnacForm().getIdScheda().toString(), codGara);
            Long num = numList != null && !numList.isEmpty() ? numList.get(0) : null;

            FaseConclusioneSubappaltoInsertForm fcs = new FaseConclusioneSubappaltoInsertForm();
            SubappaltoType2 suba2Type = schedaCS1Type.getAnacForm().getSubappalto();

            fcs.setCodGara(codGara);
            fcs.setCodLotto(codLotto);
            fcs.setNum(num);

            if(suba2Type.getImportoEffettivo() != null) {
                fcs.setImportoEffettivo(suba2Type.getImportoEffettivo());
            }

            if(suba2Type.getDataUltimazione() != null) {
                fcs.setDataUltimazione(schedePcpUtils.offsetDateTimeToDate(suba2Type.getDataUltimazione()));
            }

            if(suba2Type.getMotivoMancataEsecuzioneSubappalto() != null && suba2Type.getMotivoMancataEsecuzioneSubappalto().getCodice()!= null) {
                fcs.setMotivoMancataEsec(Long.valueOf(suba2Type.getMotivoMancataEsecuzioneSubappalto().getCodice()));
            }

            ResponseInsert response = this.fasiManager.insertFaseConclusioneSubappalto(fcs);

            String schedaJson = objectMapper.writeValueAsString(schedaCS1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 18L, num, 1L, dataCreazione, schedaJson, null, null);
            if(statoScheda.equals("CONF")) {
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 18L, num);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 18L, num, 3L, dataCreazione, schedaJson, null, null);
            }
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 18L, num, idScheda);
        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaCS1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaIR1Type( SchedaIR1Type schedaIR1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda) throws Exception{

        try{

            Long codGara = res.getCodGara();
            List<Long> codLottoList = this.contrattiMapper.getCodLottByIdContratto(schedaIR1Type.getAnacForm().getIdContratto().toString());
            Long codLotto = codLottoList != null && !codLottoList.isEmpty() ? codLottoList.get(0) : null;

            FaseIstanzaRecessoInsertForm firif = new FaseIstanzaRecessoInsertForm();
            RitardoType abType = schedaIR1Type.getAnacForm().getRitardo();

            firif.setCodGara(codGara);
            firif.setCodLotto(codLotto);

            if(abType.getDataTermine() != null){
                firif.setDataTermine(schedePcpUtils.offsetDateTimeToDate(abType.getDataTermine()));
            }

            if(abType.getTipoComunicazione() != null){
                firif.setTipoComunicazione(abType.getTipoComunicazione().getCodice());
            }

            if(abType.getDurataSospensione() != null){
                firif.setDurataSospensione(abType.getDurataSospensione().longValue());
            }

            if(abType.getMotivoSospensione() != null){
                firif.setMotivoSospensione(abType.getMotivoSospensione());
            }

            if(abType.getDataIstanzaRecesso() != null){
                firif.setDataIstanzaRecesso(schedePcpUtils.offsetDateTimeToDate(abType.getDataIstanzaRecesso()));
            }

            if(abType.isIstanzaAccolta() != null) {
                firif.setFlagAccolta(abType.isIstanzaAccolta() ? "1" : "2");
            }

            if(abType.isIstanzaTardiva() != null) {
                firif.setFlagTardiva(abType.isIstanzaTardiva() ? "1" : "2");
            }

            if(abType.isIstanzaRipresa() != null) {
                firif.setFlagRipresa(abType.isIstanzaRipresa() ? "1" : "2");
            }

            if(abType.isIstanzaRiserva() != null) {
                firif.setFlagRiserva(abType.isIstanzaRiserva() ? "1" : "2");
            }

            if(abType.getImpSpese() != null){
                firif.setImportoSpese(abType.getImpSpese());
            }

            if(abType.getImpOneri() != null){
                firif.setImportoOneri(abType.getImpOneri());
            }

            ResponseInsert response = this.fasiManager.insertFaseIstanzaRecesso(firif);
            Long num = response.getData();
            this.contrattiMapper.setW9faseExported(codGara, codLotto, 10L, num);
            String schedaJson = objectMapper.writeValueAsString(schedaIR1Type);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 10L, num, 1L, dataCreazione, schedaJson, null, null);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 10L, num, 3L, dataCreazione, schedaJson, null, null);
            this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 10L, num, idScheda);

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaIR1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaCM1Type( SchedaCM1Type schedaCM1Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda, boolean isAd5) throws Exception{

        try{

            if(schedaCM1Type != null && schedaCM1Type.getAnacForm() != null){
                Long codGara = res.getCodGara();
                Long codLotto = this.contrattiMapper.getCodLottByCig(schedaCM1Type.getAnacForm().getCig(), codGara);
                String idContratto = idContrattoCig.get(schedaCM1Type.getAnacForm().getCig());
                if(schedaCM1Type.getAnacForm().getComunicazione() != null){
                    Double importo = null;
                    String cup = null;
                    if(schedaCM1Type.getAnacForm().getComunicazione().getImporto() != null){
                        importo = schedaCM1Type.getAnacForm().getComunicazione().getImporto();
                    }

                    if(StringUtils.isNotBlank(schedaCM1Type.getAnacForm().getComunicazione().getCup())){
                        cup = schedaCM1Type.getAnacForm().getComunicazione().getCup();
                    }

                    if(cup != null && cup.length() <= 15) {
                        this.contrattiMapper.updateCup(codGara, codLotto, cup);
                        this.contrattiMapper.updateEsenteCupSNo(codGara, codLotto);
                        this.contrattiMapper.deleteW9LottoCup(codGara, codLotto);
                        this.contrattiMapper.insertW9lottcup(codGara, codLotto, 1L, cup, null);
                    }

                    if(importo != null){
                        this.contrattiMapper.updateValBaseAsta(codGara, codLotto, importo);
                        this.contrattiMapper.updateImportoGara(codGara, importo);
                        if(isAd5){
                            List<Long> numAppaList = this.contrattiMapper.getNumAppaFromCodContratto(codGara, codLotto, idContratto);
                            Long numAppa = numAppaList != null && !numAppaList.isEmpty() ? numAppaList.get(0) : null;
                            this.contrattiMapper.updateimportoAggiudicazione(codGara, codLotto, numAppa, importo);
                        }
                    }
                }
            }

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaCM1Type", e);
            throw e;
        }
    }

    public void elaborateSchedaCM2Type( SchedaCM2Type schedaCM2Type, ResponseElaborateAppaltoPcp res, String codein,
                                         Long syscon, String idScheda, Map<String, String> idContrattoCig, Date dataCreazione, Boolean allineaDaPcp, String statoScheda, boolean isAd5) throws Exception{

        try{

            if(schedaCM2Type != null && schedaCM2Type.getAnacForm() != null){
                Long codGara = res.getCodGara();
                Long codLotto = this.contrattiMapper.getCodLottByCig(schedaCM2Type.getAnacForm().getCig(), codGara);
                String idContratto = idContrattoCig.get(schedaCM2Type.getAnacForm().getCig());
                if(schedaCM2Type.getAnacForm().getComunicazione() != null){
                    Double importo = null;
                    String cup = null;
                    if(schedaCM2Type.getAnacForm().getComunicazione().getImporto() != null){
                        importo = schedaCM2Type.getAnacForm().getComunicazione().getImporto();
                    }

                    if(StringUtils.isNotBlank(schedaCM2Type.getAnacForm().getComunicazione().getCup())){
                        cup = schedaCM2Type.getAnacForm().getComunicazione().getCup();
                    }

                    if(cup != null && cup.length() <= 15) {
                        this.contrattiMapper.updateCup(codGara, codLotto, cup);
                        this.contrattiMapper.updateEsenteCupSNo(codGara, codLotto);
                        this.contrattiMapper.deleteW9LottoCup(codGara, codLotto);
                        this.contrattiMapper.insertW9lottcup(codGara, codLotto, 1L, cup, null);
                    }

                    if(importo != null){
                        this.contrattiMapper.updateValBaseAsta(codGara, codLotto, importo);
                        this.contrattiMapper.updateImportoGara(codGara, importo);
                        if(isAd5){
                            List<Long> numAppaList = this.contrattiMapper.getNumAppaFromCodContratto(codGara, codLotto, idContratto);
                            Long numAppa = numAppaList != null && !numAppaList.isEmpty() ? numAppaList.get(0) : null;
                            this.contrattiMapper.updateimportoAggiudicazione(codGara, codLotto, numAppa, importo);
                        }
                    }
                }
            }

        } catch (Exception e){
            logger.error("Errore in fase di importazione fase pcp: elaborateSchedaCM2Type", e);
            throw e;
        }
    }

    private void insertFaseSottoscrizioneContratto(String codein, Long syscon, String idScheda, Long codGara,
                                                   Long codLotto, FaseInizialeEsecuzioneInsertForm fase, Date dataCreazione, String statoScheda, String schedaJson, boolean allineaDaPcp,
                                                   FaseInizialeEsecuzioneEntry faseIniz) throws Exception {

        Long numAppa = 1L;
        Long id = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, 13L);
        Long numIniz = this.contrattiMapper.getMaxNumIniz(codGara, codLotto);
        fase.setNumAppa(numAppa);
        if(faseIniz != null){
            if(fase.getNum() == null){
                if (numIniz == null) {
                    numIniz = 1L;
                } else {
                    numIniz++;
                }
                fase.setNum(numIniz);
            }
            this.contrattiMapper.updateFaseInizialeSottoscrizioneContrattoWithCodContratto(fase);
        } else{

            fase.setCodGara(codGara);
            fase.setCodLotto(codLotto);

            if (numIniz == null) {
                numIniz = 1L;
            } else {
                numIniz++;
            }
            fase.setNum(numIniz);

            this.contrattiMapper.insertFaseInizialeEsecuzione(fase);
        }


        LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
        // Trovo l'id per l'inserimento enlla w9fasi

        if (id == null) {
            id = 1L;
        } else {
            id++;
        }

        String idSchedaLoc = lotto.getCig() + "_13_" + calcolaIdScheda(id);
        this.contrattiMapper.insertW9fase(codGara, codLotto, 13L, id, idSchedaLoc, numAppa, idScheda);
        this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 13L, 1L, 1L, dataCreazione, schedaJson, null, null);
        if(statoScheda.equals("CONF")) {
            this.contrattiMapper.setW9faseExported(codGara, codLotto, 13L, 1L);
            this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 13L, 1L, 3L, dataCreazione, schedaJson, null, null);
        }

        this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 13L, fase.getNum(), idScheda);


    }



    private FaseInizialeEsecuzioneInsertForm typeToFaseInizialeEsecuzioneEntry(DatiContrattoType dct) {

        FaseInizialeEsecuzioneInsertForm fase = new FaseInizialeEsecuzioneInsertForm();

        // Conversione delle date da OffsetDateTime a Date
        if (dct.getDataDecorrenza() != null) {
            fase.setDataDecorrenza(schedePcpUtils.offsetDateTimeToDate(dct.getDataDecorrenza()));
        }
        if (dct.getDataStipula() != null) {
            fase.setDataStipula(schedePcpUtils.offsetDateTimeToDate(dct.getDataStipula()));
        }
        if (dct.getDataScadenza() != null) {
            fase.setDataScadenza(schedePcpUtils.offsetDateTimeToDate(dct.getDataScadenza()));
        }
        if (dct.getDataEsecutivita() != null) {
            fase.setDataEsecutivita(schedePcpUtils.offsetDateTimeToDate(dct.getDataEsecutivita()));
        }

        // Impostazione dell'importo cauzione
        if (dct.getImportoCauzione() != null) {
            fase.setImportoCauzione(dct.getImportoCauzione());
        }

        return fase;
    }


    public void elaborateAggSchedaA712Type(SchedaA712Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA712Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                boolean inseritoW9aggi = inserisciPartecipantiAdPostPubb(agg.getPartecipanti(), codGara, codLotto, codein);
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA712Type");
            throw e;
        }

    }


    public void elaborateAggSchedaA711Type(SchedaA711Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA7Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                boolean inseritoW9aggi = inserisciPartecipantiAdPostPubb(agg.getPartecipanti(), codGara, codLotto, codein);
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA711Type");
            throw e;
        }

    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA46Type(SchedaA46Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA4Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);
            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA46Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA45Type(SchedaA45Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA4Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA45Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA44Type(SchedaA44Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA4Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA44Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA43Type(SchedaA43Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA4Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA44Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA42Type(SchedaA42Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA4Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA42Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA41Type(SchedaA41Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                           String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA4Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA41Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA35Type(SchedaA35Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA35Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA35Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA34Type(SchedaA34Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA34Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA32Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA33Type(SchedaA33Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA33Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA33Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA32Type(SchedaA32Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA32Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA32Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA31Type(SchedaA31Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein,
                                           Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA31Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA31Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA237Type(SchedaA237Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA237Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaEmpty(codGara, codLotto, valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA237Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA236Type(SchedaA236Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA236Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaEmpty(codGara, codLotto, valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA236Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA235Type(SchedaA235Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA235Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA235Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA234Type(SchedaA234Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA234Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA234Type");
            throw e;
        }
        return offertePresentate;
    }


    public void elaborateAggSchedaS3Type(SchedaS3Type schedaS3Type, ResponseElaborateAppaltoPcp res, String codein,Long syscon, String idScheda, Date dataCreazione, String statoScheda) throws Exception {
        try {
            Long codGara = res.getCodGara();
            Long codLotto = null;
            List<IncaricoProfessionaleInsertForm> incarichi = new ArrayList<IncaricoProfessionaleInsertForm>();
            if(numIncarico == null) {
                numIncarico = this.contrattiMapper.getMaxNumInca(codGara, codLotto, 1L);
            }
            if(idIncarico == null) {
                idIncarico = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, 20L);
            }

            for (ElencoIncarichiType inc : schedaS3Type.getAnacForm().getElencoIncarichi()) {
                codLotto = res.getCigCodLotMap().get(inc.getCig());

                IncaricoProfessionaleInsertForm incarichiProfEntry = new IncaricoProfessionaleInsertForm();
                RupEntry tecnico = new RupEntry();
                if(inc.getIncarichi() != null) {
                    for (IncaricoType i : inc.getIncarichi()) {
                        incarichiProfEntry = new IncaricoProfessionaleInsertForm();
                        if(i.getTipoIncarico() != null && StringUtils.isNotBlank(i.getTipoIncarico().getCodice())) {
                            incarichiProfEntry.setIdRuolo(Long.valueOf(i.getTipoIncarico().getCodice()));
                        }
                        tecnico = new RupEntry();
                        if(i.getDatiPersonaFisica() != null) {
                            List<RupEntry> tecList = this.contrattiMapper.getTecnicoByCfAndSaList(i.getDatiPersonaFisica().getCodiceFiscale(), codein);
                            tecnico = tecList != null && !tecList.isEmpty() ? tecList.get(0) : null;
                            if(tecnico == null) {
                                tecnico = mapToRupEntryFromPersonaFisica(i.getDatiPersonaFisica());
                                tecnico.setCodice(this.calcolaCodificaAutomatica("TECNI", "CODTEC"));
                                String nome = tecnico.getNome() != null ? tecnico.getNome() : "-";
                                String cognome = tecnico.getCognome() != null ? tecnico.getCognome() : "-";
                                tecnico.setNominativo(nome + " "+ cognome);
                                tecnico.setStazioneAppaltante(codein);
                                this.contrattiMapper.insertRUP(tecnico);
                            }
                            incarichiProfEntry.setCodiceTecnico(tecnico.getCodice());
                        }

                        if(i.getDatiPersonaGiuridica() != null && i.getDatiPersonaGiuridica().size() > 0) {
                            tecnico = this.contrattiMapper.getTecnicoByCfAndSa(i.getDatiPersonaGiuridica().get(0).getCodiceFiscale(), codein);
                            if(tecnico == null) {
                                tecnico = mapToPersonaGiuridicaFromPersonaGiuridica(i.getDatiPersonaGiuridica().get(0));
                                tecnico.setCodice(this.calcolaCodificaAutomatica("TECNI", "CODTEC"));
                                String nome = tecnico.getNome() != null ? tecnico.getNome() : "-";
                                String cognome = tecnico.getCognome() != null ? tecnico.getCognome() : "-";
                                tecnico.setNominativo(nome + " "+ cognome);
                                tecnico.setStazioneAppaltante(codein);
                                this.contrattiMapper.insertRUP(tecnico);
                            }
                            incarichiProfEntry.setCodiceTecnico(tecnico.getCodice());
                        }

                        incarichi.add(incarichiProfEntry);
                    }
                }


                if(inc.getPrestazioni() != null) {
                    for (PrestazioneType p : inc.getPrestazioni()) {
                        incarichiProfEntry = new IncaricoProfessionaleInsertForm();

//							if(p.getTipoProgettazione() != null) {
//								incarichiProfEntry.setTipoProgettazione(Long.valueOf(p.getTipoProgettazione().getCodice()));
//							}

                        if(p.getCig() != null) {
                            incarichiProfEntry.setCigProgEsterna(p.getCig());
                        }

                        if(p.getDataAffidamentoIncarico() != null) {
                            incarichiProfEntry.setDataAffProgEsterna(Date.from(p.getDataAffidamentoIncarico().toInstant()));
                        }

                        if(p.getDataConsegna() != null) {
                            incarichiProfEntry.setDataConsProgEsterna(Date.from(p.getDataConsegna().toInstant()));
                        }

                        if(p.getTipoSoggetto() != null && StringUtils.isNotBlank(p.getTipoSoggetto().getCodice())) {
                            incarichiProfEntry.setIdRuolo(Long.valueOf(p.getTipoSoggetto().getCodice()));
                        }
                        tecnico = new RupEntry();
                        if(p.getDatiPersonaFisica() != null) {
                            tecnico = this.contrattiMapper.getTecnicoByCfAndSa(p.getDatiPersonaFisica().getCodiceFiscale(), codein);
                            if(tecnico == null) {
                                tecnico = mapToRupEntryFromPersonaFisica(p.getDatiPersonaFisica());
                                tecnico.setCodice(this.calcolaCodificaAutomatica("TECNI", "CODTEC"));
                                String nome = tecnico.getNome() != null ? tecnico.getNome() : "-";
                                String cognome = tecnico.getCognome() != null ? tecnico.getCognome() : "-";
                                tecnico.setNominativo(nome + " "+ cognome);
                                tecnico.setStazioneAppaltante(codein);
                                this.contrattiMapper.insertRUP(tecnico);
                            }
                            incarichiProfEntry.setCodiceTecnico(tecnico.getCodice());
                        }

                        if(p.getDatiPersonaGiuridica() != null && p.getDatiPersonaGiuridica().size() > 0) {
                            tecnico = this.contrattiMapper.getTecnicoByCfAndSa(p.getDatiPersonaGiuridica().get(0).getCodiceFiscale(), codein);
                            if(tecnico == null) {
                                tecnico = mapToPersonaGiuridicaFromPersonaGiuridica(p.getDatiPersonaGiuridica().get(0));
                                tecnico.setCodice(this.calcolaCodificaAutomatica("TECNI", "CODTEC"));
                                String nome = tecnico.getNome() != null ? tecnico.getNome() : "-";
                                String cognome = tecnico.getCognome() != null ? tecnico.getCognome() : "-";
                                tecnico.setNominativo(nome + " "+ cognome);
                                tecnico.setStazioneAppaltante(codein);
                                this.contrattiMapper.insertRUP(tecnico);
                            }
                            incarichiProfEntry.setCodiceTecnico(tecnico.getCodice());
                        }
                        incarichi.add(incarichiProfEntry);
                    }
                }


                if (numIncarico == null) {
                    numIncarico = 1L;
                } else {
                    numIncarico++;
                }
                if (idIncarico == null) {
                    idIncarico = 1L;
                } else {
                    idIncarico++;
                }
                for (IncaricoProfessionaleInsertForm incarico : incarichi) {
                    incarico.setSezione("PCP");
                    this.contrattiMapper.insertIncaricoProfessionaleForm(codGara, codLotto, idIncarico, numIncarico, incarico);
                    numIncarico++;
                }



                String idS = inc.getCig() + "_20_" + calcolaIdScheda(idIncarico);
                this.contrattiMapper.insertW9fase(codGara, codLotto, 20L, idIncarico, idS, 1L, idScheda);
                this.contrattiMapper.setW9faseExported(codGara, codLotto, 20L, idIncarico);
                String schedaJson = objectMapper.writeValueAsString(schedaS3Type);
                this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 20L, idIncarico, 1L, dataCreazione, schedaJson, null, null);
                if(statoScheda.equals("CONF")){
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 20L, idIncarico, 3L, dataCreazione, schedaJson, null, null);
                }
            }






        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaS3Type");
            throw e;
        }

    }

    public void elaborateAggSchedaS4Type(SchedaS4Type schedaS4Type, ResponseElaborateAppaltoPcp res, String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        try {
            Long codGara = res.getCodGara();
            Long codLotto = this.contrattiMapper.getCodLottByCig(schedaS4Type.getAnacForm().getCig(), codGara);
            String idPartecipante = null;

            if(schedaS4Type.getAnacForm().getPartecipante() != null){
                if(schedaS4Type.getAnacForm().getPartecipante().getIdPartecipante() != null){
                    idPartecipante = schedaS4Type.getAnacForm().getPartecipante().getIdPartecipante().toString();
                }

                Long existsAggi = this.contrattiMapper.checkExistAggiudicatario(codGara,codLotto,idPartecipante);
                if(existsAggi > 0){
                    List<String> codImpList = this.contrattiMapper.esisteImpresaCfList(schedaS4Type.getAnacForm().getPartecipante().getCodiceFiscale(), codein);
                    String codImp = codImpList != null && !codImpList.isEmpty() ? codImpList.get(0) : null;
                    if(codImp == null) {
                        ImpresaEntry imp = new ImpresaEntry();
                        codImp = this.calcolaCodificaAutomatica("IMPR", "CODIMP");
                        imp.setCodiceImpresa(codImp);
                        imp.setCodiceFiscale(schedaS4Type.getAnacForm().getPartecipante().getCodiceFiscale());
                        imp.setStazioneAppaltante(codein);
                        imp.setRagioneSociale(schedaS4Type.getAnacForm().getPartecipante().getDenominazione());
                        this.contrattiMapper.insertImpresa(imp);

                        this.contrattiMapper.insertTeim(codImp, "", "", schedaS4Type.getAnacForm().getPartecipante().getCodiceFiscale());
                        Long maxId = wgcManager.getNextId("IMPLEG");
                        this.contrattiMapper.insertImpleg(maxId, codImp, "");
                    }


                    FaseVariazioneAggiudicatariInsertForm vaIf = typeToVariazioneAggiudicatari(schedaS4Type.getAnacForm());
                    vaIf.setCodImpresa(codImp);
                    vaIf.setCodGara(codGara);
                    vaIf.setCodLotto(codLotto);


                    ResponseInsert response = this.fasiManager.insertFaseVariazioneAggiudicatari(vaIf);
                    Long num = response.getData();
                    String schedaJson = objectMapper.writeValueAsString(schedaS4Type);
                    this.contrattiMapper.setW9faseExported(codGara, codLotto, 21L, num);
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 21L, num, 1L, dataCreazione, schedaJson, null, null);
                    this.schedePcpUtils.inserisciW9flussiSchedeEse(codGara, codLotto,codein, syscon, 21L, num, 3L, dataCreazione, schedaJson, null, null);
                    this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, 21L, num, idScheda);
                }

            }



        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaS4Type");
            throw e;
        }

    }

    private FaseVariazioneAggiudicatariInsertForm typeToVariazioneAggiudicatari(AnacFormS4Type s4) {
        FaseVariazioneAggiudicatariInsertForm vaIf = new FaseVariazioneAggiudicatariInsertForm();

        if (s4.getMotivoVariazione() != null) {
            vaIf.setMotivoVariazione(Long.parseLong(s4.getMotivoVariazione().getCodice()));
        }

        if (s4.getPartecipante() != null) {
            PartecipanteType part = s4.getPartecipante();

            if (part.getIdPartecipante() != null) {
                vaIf.setIdPartecipante(part.getIdPartecipante().toString());
            }

            if (part.isAvvalimento() != null) {
                vaIf.setFlagAvvalimento(part.isAvvalimento() ? 1L : 0L);
            }

            if (part.getTipologiaAvvalimento() != null) {
                vaIf.setFlagAvvalimento(Long.parseLong(part.getTipologiaAvvalimento().getCodice()));
            }

            if (part.getRuoloOE() != null) {
                vaIf.setIdRuolo(Long.parseLong(part.getRuoloOE().getCodice()));
            }

            if (part.getTipoOE() != null) {
                vaIf.setTipoOe(Long.parseLong(part.getTipoOE().getCodice()));
            }
        }

        return vaIf;
    }

    public RupEntry mapToRupEntryFromPersonaFisica(DatiPersonaFisicaType personaFisica) {
        RupEntry tecnico = new RupEntry();

        if (personaFisica.getCodiceFiscale() != null) {
            tecnico.setCf(personaFisica.getCodiceFiscale());
        }

        if (personaFisica.getCognome() != null) {
            tecnico.setCognome(personaFisica.getCognome());
        }

        if (personaFisica.getNome() != null) {
            tecnico.setNome(personaFisica.getNome());
        }

        if (personaFisica.getTelefono() != null && personaFisica.getTelefono().length() <= 50) {
            tecnico.setTelefono(personaFisica.getTelefono());
        }

        if (personaFisica.getFax() != null) {
            tecnico.setFax(personaFisica.getFax());
        }

        if (personaFisica.getEmail() != null) {
            tecnico.setEmail(personaFisica.getEmail());
        }

        if (personaFisica.getIndirizzo() != null) {
            tecnico.setIndirizzo(personaFisica.getIndirizzo());
        }

        if (personaFisica.getCap() != null && personaFisica.getCap().length() <= 5) {
            tecnico.setCap(personaFisica.getCap());
        }

        if (personaFisica.getCodIstat() != null) {
            tecnico.setCodIstat(personaFisica.getCodIstat().getCodice());
        }

        return tecnico;
    }

    public RupEntry mapToPersonaGiuridicaFromPersonaGiuridica(DatiPersonaGiuridicaType personaGiuridica) {
        RupEntry tecnico = new RupEntry();

        if (personaGiuridica.getCodiceFiscale() != null) {
            tecnico.setCf(personaGiuridica.getCodiceFiscale());
        }

        if (personaGiuridica.getDenominazione() != null) {
            tecnico.setNominativo(personaGiuridica.getDenominazione());
        }

        return tecnico;
    }



    public void elaborateAggSchedaS2Type(SchedaS2Type schedaS2Type, ResponseElaborateAppaltoPcp res, String codein,
                                          Long syscon, String idScheda, Date dataCreazione, Map<String,Map<String,List<Map<String, Object>>>> offertePresentate) throws Exception {
        try {
            Long codGara = res.getCodGara();
            Long codLotto = null;


            Long tipoRaggruppamento = 0L;
            Long tipoRaggruppamentoAgg = 0L;

            Long numAggi = 1L;
            //rimuovo le offerte presentate che contengono lo stesso cig
            List<ElencoSoggettiType> elencoSoggettifiltrati = new ArrayList<>(
                    schedaS2Type.getAnacForm().getElencoSoggetti().stream()
                            .collect(Collectors.toMap(ElencoSoggettiType::getCig, e -> e, (existing, replacement) -> existing))
                            .values()
            );
            for (ElencoSoggettiType agg : elencoSoggettifiltrati) {
                boolean inseritoW9imprese = false;
                if(agg != null) {
                    codLotto = res.getCigCodLotMap().get(agg.getCig());
                    Long id = this.contrattiMapper.getMaxIdFaseImpresa(codGara, codLotto);
                    Map<String, List<Map<String, Object>>> offertePresentatePerLottoObj = null;
                    Map<String, List<it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.OfferteType>> offertePresentatePerLotto = null;
                    if(offertePresentate != null && !offertePresentate.isEmpty()){
                        offertePresentatePerLottoObj = offertePresentate.get(agg.getCig());
                        if(offertePresentatePerLottoObj != null){
                            offertePresentatePerLotto = offertePresentatePerLottoObj.entrySet().stream()
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey, // Manteniamo la chiave idPartecipante
                                            entry -> entry.getValue().stream() // Otteniamo la lista di Map<String, Object>
                                                    .map(mappa -> objectMapper.convertValue(mappa, it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.OfferteType.class)) // Convertiamo in OfferteType
                                                    .collect(Collectors.toList()) // Convertiamo in lista
                                    ));
                        }
                    }
                    Map<String, List<PartecipanteType>> listaPerPartecipante = agg.getPartecipanti().stream()
                            .filter(Objects::nonNull)
                            .filter(e->StringUtils.isNotBlank(e.getCodiceFiscale()))
                            .collect(Collectors.groupingBy(e-> e.getIdPartecipante().toString(), Collectors.toList()));


                    for (Map.Entry<String, List<PartecipanteType>> p : listaPerPartecipante.entrySet()) {

                        if(p.getValue().size()>1 && offertePresentatePerLotto != null && offertePresentatePerLotto.size()> 0 &&
                                offertePresentatePerLotto.get(p.getKey()) != null &&
                                offertePresentatePerLotto.get(p.getKey()).size() > 0) {
                            //raggruppamento
                            tipoRaggruppamentoAgg++;
                        } else if(p.getValue().size()>1) {
                            //raggruppamento
                            tipoRaggruppamento++;
                        }

                        for (PartecipanteType part : p.getValue()) {
                            if(part != null) {
                                String CodFisc = part.getCodiceFiscale() != null ? part.getCodiceFiscale().toUpperCase().trim() : "";
                                if(StringUtils.isNotBlank(CodFisc) && CodFisc.length() > 30) {
                                    CodFisc = StringUtils.truncate(CodFisc, 30);
                                }
                                List<String> codImpList = this.contrattiMapper.esisteImpresaCfList(CodFisc, codein);
                                String codImp = codImpList != null && !codImpList.isEmpty() ? codImpList.get(0) : null;
                                if(codImp == null) {
                                    ImpresaEntry imp = new ImpresaEntry();
                                    codImp = this.calcolaCodificaAutomatica("IMPR", "CODIMP");
                                    imp.setCodiceImpresa(codImp);
                                    imp.setCodiceFiscale(CodFisc);
                                    imp.setStazioneAppaltante(codein);
                                    imp.setRagioneSociale(part.getDenominazione());
                                    this.contrattiMapper.insertImpresa(imp);

                                    this.contrattiMapper.insertTeim(codImp, "", "", CodFisc);
                                    Long maxId = wgcManager.getNextId("IMPLEG");
                                    this.contrattiMapper.insertImpleg(maxId, codImp, "");
                                }

                                if(offertePresentatePerLotto != null && offertePresentatePerLotto.size()> 0 &&
                                        part.getIdPartecipante() != null &&
                                        offertePresentatePerLotto.get(part.getIdPartecipante().toString()) != null &&
                                        offertePresentatePerLotto.get(part.getIdPartecipante().toString()).size() > 0) {

                                    //insert w9aggi

                                    Double importo = offertePresentatePerLotto.get(part.getIdPartecipante().toString()).get(0).getImporto();
                                    Double ribasso = offertePresentatePerLotto.get(part.getIdPartecipante().toString()).get(0).getOffertaEconomica();
                                    Double offAumento = offertePresentatePerLotto.get(part.getIdPartecipante().toString()).get(0).getOffertaInAumento();

                                    Long tipo = part.getTipoOE() != null && part.getTipoOE().getCodice() != null ? Long.valueOf(mapTipoOe.get(part.getTipoOE().getCodice())) : null;
                                    Long ruolo = part.getRuoloOE() != null && part.getRuoloOE().getCodice() != null ? Long.valueOf(part.getRuoloOE().getCodice()) : null;
                                    if(ruolo != null && ruolo != 4) {
                                        if(ruolo != 1 && ruolo != 2) {
                                            ruolo = null;
                                        }
                                        if(p.getValue().size() > 1) {
                                            this.inserisciAggiudicazioneW9Aggi(codGara, codLotto, part.getIdPartecipante().toString(), codImp, tipoRaggruppamentoAgg, tipo, ruolo, importo, ribasso, offAumento, numAggi);
                                            numAggi++;
                                        } else {
                                            this.inserisciAggiudicazioneW9Aggi(codGara, codLotto, part.getIdPartecipante().toString(), codImp , null, tipo, ruolo, importo, ribasso, offAumento, numAggi);
                                            numAggi++;
                                        }
                                    }

                                }
                                //insert in w9imprese
                                inseritoW9imprese = true;
                                if (id == null || id < 1) {
                                    id = 1L;
                                } else {
                                    id++;
                                }
                                Long tipo = part.getTipoOE() != null && part.getTipoOE().getCodice() != null ? Long.valueOf(mapTipoOe.get(part.getTipoOE().getCodice())) : null;
                                Long ruolo = part.getRuoloOE() != null && part.getRuoloOE().getCodice() != null ? Long.valueOf(part.getRuoloOE().getCodice()) : null;
                                if(ruolo != null && ruolo != 4) {
                                    if(ruolo != 1 && ruolo != 2) {
                                        ruolo = null;
                                    }
                                    if(p.getValue().size() > 1) {
                                        this.contrattiMapper.insertFaseImpresa(codGara, codLotto, id, codImp, ruolo, tipo, 1L, tipoRaggruppamento);
                                    } else {
                                        this.contrattiMapper.insertFaseImpresa(codGara, codLotto, id, codImp, ruolo, tipo, 1L, null);
                                    }
                                }
                            }
                        }



                    }
                    if(agg.getInvitatiCheNonHannoPresentatoOfferta() != null) {
                        for (InvitatoType inv : agg.getInvitatiCheNonHannoPresentatoOfferta()) {
                            if(inv != null) {
                                String CodFisc = inv.getCodiceFiscale() != null ? inv.getCodiceFiscale().toUpperCase().trim() : "";
                                if(StringUtils.isNotBlank(CodFisc) && CodFisc.length() > 30) {
                                    CodFisc = StringUtils.truncate(CodFisc, 30);
                                }
                                if(StringUtils.isNotBlank(CodFisc)) {
                                    List<String> codImpList = this.contrattiMapper.esisteImpresaCfList(CodFisc, codein);
                                    String codImp = codImpList != null && !codImpList.isEmpty() ? codImpList.get(0) : null;
                                    if(codImp == null) {
                                        ImpresaEntry imp = new ImpresaEntry();
                                        codImp = this.calcolaCodificaAutomatica("IMPR", "CODIMP");
                                        imp.setCodiceImpresa(codImp);
                                        imp.setCodiceFiscale(CodFisc);
                                        imp.setStazioneAppaltante(codein);
                                        imp.setRagioneSociale(inv.getDenominazione());
                                        this.contrattiMapper.insertImpresa(imp);

                                        this.contrattiMapper.insertTeim(codImp, "", "", CodFisc);
                                        Long maxId = wgcManager.getNextId("IMPLEG");
                                        this.contrattiMapper.insertImpleg(maxId, codImp, "");
                                    }

                                    //insert in w9imprese
                                    inseritoW9imprese = true;
                                    if (id == null || id < 1) {
                                        id = 1L;
                                    } else {
                                        id++;
                                    }
                                    Long tipo = inv.getTipoOE() != null && inv.getTipoOE().getCodice() != null ? Long.valueOf(mapTipoOe.get(inv.getTipoOE().getCodice())) : null;
                                    Long ruolo = inv.getRuoloOE() != null && inv.getRuoloOE().getCodice() != null ? Long.valueOf(inv.getRuoloOE().getCodice()) : null;

                                    this.contrattiMapper.insertFaseImpresa(codGara, codLotto, id, codImp, ruolo, tipo, 2L, null);

                                }
                            }
                        }
                    }
                }
                if(inseritoW9imprese) {
                    LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
                    Long idNumFase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, 101L);
                    if (idNumFase == null) {
                        idNumFase = 1L;
                    } else {
                        idNumFase++;
                    }

                    String idscheda = lotto.getCig() + "_101_" + calcolaIdScheda(idNumFase);
                    this.contrattiMapper.insertW9fase(codGara, codLotto, 101L, idNumFase, idscheda, 1L,idScheda);
                    this.contrattiMapper.setW9faseExported(codGara, codLotto, 101L, 1L);
                    this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 101L,3L, dataCreazione);
                }
            }






        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaS2Type");
            throw e;
        }

    }

    private static String calcolaIdScheda(Long num) {
        String output = num.toString();
        while (output.length() < 3)
            output = "0" + output;
        return output;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA233Type(SchedaA233Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA233Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA233Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA232Type(SchedaA232Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA232Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA232Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA231Type(SchedaA231Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA231Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaEmpty(codGara, codLotto, valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA231Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA230Type(SchedaA230Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA230Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA230Type");
            throw e;
        }
        return offertePresentate;
    }



    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA229Type(SchedaA229Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            for (AggiudicazioneA229Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = agg.getDatiBaseAggiudicazioneAppalto() != null && agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione() != null ? new Date(agg.getDatiBaseAggiudicazioneAppalto().getDataAggiudicazione().toInstant().toEpochMilli()) : null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA229Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA137Type(SchedaA137Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA137Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;
                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaEmpty(codGara, codLotto, valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA137Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA136Type(SchedaA136Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA136Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaEmpty(codGara, codLotto, valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA136Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA135Type(SchedaA135Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA135Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA135Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA134Type(SchedaA134Type schedaAgg, ResponseElaborateAppaltoPcp res,
                                            String codein, Long syscon, String idScheda, Date dataCreazione)  throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA134Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA134Type");
            throw e;
        }
        return offertePresentate;
    }




    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA133Type(SchedaA133Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA133Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA133Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA132Type(SchedaA132Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA132Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione  = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaCon(codGara, codLotto, agg.getQuadroEconomicoConcessioni(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA132Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA131Type(SchedaA131Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA131Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = null;
                Long numeroOfferteAmmesse = null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9AppaEmpty(codGara, codLotto, valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA131Type");
            throw e;
        }
        return offertePresentate;
    }


    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA130Type(SchedaA130Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();
            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);
            for (AggiudicazioneA130Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());

                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }
        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA130Type");
            throw e;
        }
        return offertePresentate;
    }

    private void inserisciAggiudicazioneW9flussi(Long codGara, Long codLotto,String codiceSA, Long syscon) throws Exception {

        FlussoEntry flusso = new FlussoEntry();
        try {
            String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(codiceSA);
            Long idFlusso = wgcManager.getNextId("W9FLUSSI");
            flusso.setId(idFlusso);
            flusso.setArea(1L);
            flusso.setKey01(codGara);
            flusso.setKey02(codLotto);
            flusso.setKey03(1L);
            flusso.setKey04(1L);
            flusso.setTipoInvio(3L);
            flusso.setDataInvio(new Date());
            flusso.setCodiceFiscaleSA(cfStazioneAppaltante);
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            this.contrattiMapper.insertFlusso(flusso);

        } catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: inserisciAggiudicazioneW9flussi" , e);
            throw e;
        }
    }

    public Map<String,Map<String,List<Map<String, Object>>>> elaborateAggSchedaA129Type(SchedaA129Type schedaAgg, ResponseElaborateAppaltoPcp res, String codein, Long syscon, String idScheda, Date dataCreazione) throws Exception {
        Map<String,Map<String,List<Map<String, Object>>>> offertePresentate = new HashMap<String,Map<String,List<Map<String, Object>>>>();
        try {
            Long codGara = res.getCodGara();

            ContractAwardNoticeType eform  = unmarshalContractAwardNoticeType(new String(Base64.decodeBase64(schedaAgg.getEform().getBytes()), StandardCharsets.UTF_8));
            Map<String, Map<String, String>> datiEform = prelevaDatiEformAggiudicazioneCAN(eform);

            for (AggiudicazioneA129Type agg : schedaAgg.getAnacForm().getAggiudicazioni()) {
                Long codLotto = res.getCigCodLotMap().get(agg.getCig());


                Double valoreSogliaAnomalia = agg.getValoreSogliaAnomalia() != null ? agg.getValoreSogliaAnomalia() : null;
                Long numeroOfferteAmmesse = agg.getNumeroOfferteAmmesse() != null ? agg.getNumeroOfferteAmmesse().longValue() : null;
                Date dataAggiudicazione = null;

                Double importo = null;
                Double ribasso = null;
                Double offAumento = null;

                if(datiEform != null && datiEform.get(res.getCigLotIdMap().get(agg.getCig())) != null) {
                    if(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione") != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                        try {
                            Date date = sdf.parse(datiEform.get(res.getCigLotIdMap().get(agg.getCig())).get("dataAggiudicazione"));
                            dataAggiudicazione = date;
                        } catch (Exception e) {
                            logger.error("errore conversione data", e);
                        }
                    }
                }

                if(agg.getOffertePresentate() != null && agg.getOffertePresentate().size() > 0) {
                    for (OfferteType off : agg.getOffertePresentate()) {
                        if(off != null && off.isAggiudicatario()) {
                            importo = off.getImporto() != null ? off.getImporto() : null;
                            ribasso = off.getOffertaEconomica() != null ? off.getOffertaEconomica() : null;
                            offAumento = off.getOffertaInAumento() != null ? off.getOffertaInAumento() : null;
                            break;
                        }
                    }
                    Map<String, List<Map<String, Object>>> offerte = agg.getOffertePresentate().stream()
                            .filter(Objects::nonNull)
                            .filter(OfferteType::isAggiudicatario)
                            .collect(Collectors.groupingBy(
                                    e -> e.getIdPartecipante().toString(),
                                    Collectors.mapping(
                                            e -> {
                                                Map<String, Object> mappa = new HashMap<>();
                                                mappa.put("idPartecipante", e.getIdPartecipante());
                                                mappa.put("offertaEconomica", e.getOffertaEconomica());
                                                mappa.put("importo", e.getImporto());
                                                mappa.put("offertaInAumento", e.getOffertaInAumento());
                                                // Aggiungi altri campi necessari
                                                return mappa;
                                            },
                                            Collectors.toList()
                                    )
                            ));
                    offertePresentate.put(agg.getCig(), offerte);
                }
                inserisciAggiudicazioneW9fasi(codGara, codLotto, idScheda);
                inserisciAggiudicazioneW9Appa(codGara, codLotto, agg.getQuadroEconomicoStandard(), valoreSogliaAnomalia, numeroOfferteAmmesse, dataAggiudicazione, importo, ribasso, offAumento);
                this.schedePcpUtils.inserisciW9flussi(codGara, codLotto,codein, syscon, 1L,3L, dataCreazione);

            }

        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: elaborateAggSchedaA129Type");
            throw e;
        }
        return offertePresentate;
    }




    private void inserisciAggiudicazioneW9fasi(Long codGara, Long codLotto, String idScheda) {
        try {
            this.contrattiMapper.insertW9fase(codGara, codLotto,1L,1L,null, 1L,idScheda);
            this.contrattiMapper.setW9faseExported(codGara, codLotto, 1L, 1L);
        } catch (Exception e) {
            logger.error("Errore in fase di importazione gara pcp: inserisciAggiudicazioneW9fasi");
            throw e;
        }

    }

    private void inserisciAggiudicazioneW9Appa(Long codGara, Long codLotto, QuadroEconomicoType quadroEconomicoStandard, Double valoreSogliaAnomalia, Long numeroOfferteAmmesse, Date dataAggiudicazione, Double importo, Double ribasso, Double offAumento) {
        try {
            FaseAggInsertForm aggiudicazione = new FaseAggInsertForm();
            aggiudicazione.setCodGara(codGara);
            aggiudicazione.setCodLotto(codLotto);
            aggiudicazione.setNum(1L);

            if(quadroEconomicoStandard != null) {
                Double importoLavori = quadroEconomicoStandard.getImpLavori() != null ? quadroEconomicoStandard.getImpLavori() : 0D;
                Double importoServizi = quadroEconomicoStandard.getImpServizi() != null ? quadroEconomicoStandard.getImpServizi() : 0D;
                Double importoForniture = quadroEconomicoStandard.getImpForniture() != null ? quadroEconomicoStandard.getImpForniture() : 0D;
                Double importoSubtotale = importoLavori + importoServizi + importoForniture;
                aggiudicazione.setImportoLavori(importoLavori);
                aggiudicazione.setImportoServizi(importoServizi);
                aggiudicazione.setImportoForniture(importoForniture);
                aggiudicazione.setImportosubtotale(importoSubtotale);

                Double importoSicurezza = quadroEconomicoStandard.getImpTotaleSicurezza() != null ? quadroEconomicoStandard.getImpTotaleSicurezza() : 0D;
                Double importoProgettazione = quadroEconomicoStandard.getImpProgettazione() != null ? quadroEconomicoStandard.getImpProgettazione() : 0D;
                Double importoUlterioriSommeRib = quadroEconomicoStandard.getUlterioriSommeNoRibasso() != null ? quadroEconomicoStandard.getUlterioriSommeNoRibasso() : 0D;
                Double importoComplAppalto = importoSubtotale + importoSicurezza + importoProgettazione + importoUlterioriSommeRib;
                aggiudicazione.setImportoSicurezza(importoSicurezza);
                aggiudicazione.setImportoProgettazione(importoProgettazione);
                aggiudicazione.setImpNonAssog(importoUlterioriSommeRib);
                aggiudicazione.setImportoComplAppalto(importoComplAppalto);


                Double importoDisposizione = quadroEconomicoStandard.getSommeADisposizione() != null ? quadroEconomicoStandard.getSommeADisposizione() : 0D;
                Double importoSommeOpzioniRinnovi = quadroEconomicoStandard.getSommeOpzioniRinnovi() != null ? quadroEconomicoStandard.getSommeOpzioniRinnovi() : 0D;
                Double importoSommeripetizioni = quadroEconomicoStandard.getSommeRipetizioni() != null ? quadroEconomicoStandard.getSommeRipetizioni() : 0D;
                aggiudicazione.setImportoSommeOpzioniRinnovi(importoSommeOpzioniRinnovi);
                aggiudicazione.setImportoSommeripetizioni(importoSommeripetizioni);
                Double importoComplIntervento = importoComplAppalto + importoSommeOpzioniRinnovi + importoSommeripetizioni + importoDisposizione;
                aggiudicazione.setImportoComplIntervento(importoComplIntervento);
                aggiudicazione.setImportoDisposizione(importoDisposizione);
                aggiudicazione.setImportoComplIntervento(importoComplIntervento + importoDisposizione);

            }


            if(valoreSogliaAnomalia != null && SchedePcpUtils.isValidNumeric139(valoreSogliaAnomalia)) {
                aggiudicazione.setValoreSogliaAnomalia(valoreSogliaAnomalia);
            }

            if(numeroOfferteAmmesse != null) {
                aggiudicazione.setNumOfferteAmmesse(numeroOfferteAmmesse);
            }

            if(dataAggiudicazione != null) {
                aggiudicazione.setDataVerbAggiudicazione(dataAggiudicazione);
            }

            if(importo != null) {
                aggiudicazione.setImportoAggiudicazione(importo);
            }

            if(ribasso != null && SchedePcpUtils.isValidNumeric139(ribasso)) {
                aggiudicazione.setPercentRibassoAgg(ribasso);
            }

            if(offAumento != null && SchedePcpUtils.isValidNumeric139(offAumento)) {
                aggiudicazione.setPercOffAumento(offAumento);
            }

            this.contrattiMapper.insertFaseAggiudicazione(aggiudicazione);
        } catch (Exception e) {
            logger.error("Errore in fase di importazione gara pcp: inserisciAggiudicazioneW9Appa");
            throw e;
        }

    }

    private void inserisciAggiudicazioneW9AppaEmpty(Long codGara, Long codLotto, Double valoreSogliaAnomalia, Long numeroOfferteAmmesse, Date dataAggiudicazione, Double importo, Double ribasso, Double offAumento) {
        try {
            FaseAggInsertForm aggiudicazione = new FaseAggInsertForm();
            aggiudicazione.setCodGara(codGara);
            aggiudicazione.setCodLotto(codLotto);
            aggiudicazione.setNum(1L);

            if(valoreSogliaAnomalia != null && SchedePcpUtils.isValidNumeric139(valoreSogliaAnomalia)) {
                aggiudicazione.setValoreSogliaAnomalia(valoreSogliaAnomalia);
            }

            if(numeroOfferteAmmesse != null) {
                aggiudicazione.setNumOfferteAmmesse(numeroOfferteAmmesse);
            }

            if(dataAggiudicazione != null) {
                aggiudicazione.setDataVerbAggiudicazione(dataAggiudicazione);
            }

            if(importo != null) {
                aggiudicazione.setImportoAggiudicazione(importo);
            }

            if(ribasso != null && SchedePcpUtils.isValidNumeric139(ribasso)) {
                aggiudicazione.setPercentRibassoAgg(ribasso);
            }

            if(offAumento != null && SchedePcpUtils.isValidNumeric139(offAumento)) {
                aggiudicazione.setPercOffAumento(offAumento);
            }

            this.contrattiMapper.insertFaseAggiudicazione(aggiudicazione);
        } catch (Exception e) {
            logger.error("Errore in fase di importazione gara pcp: inserisciAggiudicazioneW9Appa");
            throw e;
        }

    }

    private void inserisciAggiudicazioneW9AppaCon(Long codGara, Long codLotto,
                                                  QuadroEconomicoConcessioniType quadroEconomicoConcessioni, Double valoreSogliaAnomalia, Long numeroOfferteAmmesse, Date dataAggiudicazione, Double importo, Double ribasso, Double offAumento) {
        try {
            FaseAggInsertForm aggiudicazione = new FaseAggInsertForm();
            aggiudicazione.setCodGara(codGara);
            aggiudicazione.setCodLotto(codLotto);
            aggiudicazione.setNum(1L);

            if(quadroEconomicoConcessioni != null) {
                Double importoLavori = quadroEconomicoConcessioni.getImpLavori() != null ? quadroEconomicoConcessioni.getImpLavori() : 0D;
                Double importoServizi = quadroEconomicoConcessioni.getImpServizi() != null ? quadroEconomicoConcessioni.getImpServizi() : 0D;
                Double importoForniture = quadroEconomicoConcessioni.getImpForniture() != null ? quadroEconomicoConcessioni.getImpForniture() : 0D;
                Double importoSubtotale = importoLavori + importoServizi + importoForniture;
                aggiudicazione.setImportoLavori(importoLavori);
                aggiudicazione.setImportoServizi(importoServizi);
                aggiudicazione.setImportoForniture(importoForniture);
                aggiudicazione.setImportosubtotale(importoSubtotale);

                Double importoSicurezza = quadroEconomicoConcessioni.getImpTotaleSicurezza() != null ? quadroEconomicoConcessioni.getImpTotaleSicurezza() : 0D;
                Double importoProgettazione = 0D;
                Double importoUlterioriSommeRib = quadroEconomicoConcessioni.getUlterioriSommeNoRibasso() != null ? quadroEconomicoConcessioni.getUlterioriSommeNoRibasso() : 0D;
                Double importoComplAppalto = importoSicurezza + importoProgettazione + importoUlterioriSommeRib;
                aggiudicazione.setImportoSicurezza(importoSicurezza);
                aggiudicazione.setImportoProgettazione(importoProgettazione);
                aggiudicazione.setImpNonAssog(importoUlterioriSommeRib);
                aggiudicazione.setImportoComplAppalto(importoComplAppalto);


                Double importoDisposizione = quadroEconomicoConcessioni.getSommeADisposizione() != null ? quadroEconomicoConcessioni.getSommeADisposizione() : 0D;
                Double importoSommeOpzioniRinnovi = quadroEconomicoConcessioni.getSommeOpzioniRinnovi() != null ? quadroEconomicoConcessioni.getSommeOpzioniRinnovi() : 0D;
                Double importoSommeripetizioni = 0D;
                aggiudicazione.setImportoSommeOpzioniRinnovi(importoSommeOpzioniRinnovi);
                aggiudicazione.setImportoSommeripetizioni(importoSommeripetizioni);
                Double importoComplIntervento = importoComplAppalto + importoSommeOpzioniRinnovi + importoSommeripetizioni + importoDisposizione;
                aggiudicazione.setImportoComplIntervento(importoComplIntervento);

                aggiudicazione.setImportoDisposizione(importoDisposizione);

            }
            if(valoreSogliaAnomalia != null && SchedePcpUtils.isValidNumeric139(valoreSogliaAnomalia)) {
                aggiudicazione.setValoreSogliaAnomalia(valoreSogliaAnomalia);
            }

            if(numeroOfferteAmmesse != null) {
                aggiudicazione.setNumOfferteAmmesse(numeroOfferteAmmesse);
            }

            if(dataAggiudicazione != null) {
                aggiudicazione.setDataVerbAggiudicazione(dataAggiudicazione);
            }

            if(importo != null) {
                aggiudicazione.setImportoAggiudicazione(importo);
            }

            if(ribasso != null && SchedePcpUtils.isValidNumeric139(ribasso)) {
                aggiudicazione.setPercentRibassoAgg(ribasso);
            }

            if(offAumento != null && SchedePcpUtils.isValidNumeric139(offAumento)) {
                aggiudicazione.setPercOffAumento(offAumento);
            }

            this.contrattiMapper.insertFaseAggiudicazione(aggiudicazione);
        } catch (Exception e) {
            logger.error("Errore in fase di importazione gara pcp: inserisciAggiudicazioneW9AppaCon");
            throw e;
        }

    }

    private void inserisciAggiudicazioneW9Aggi(Long codGara, Long codLotto, String idPartecipante, String codImp, Long tipoRaggruppamento,Long tipo, Long ruolo, Double importo, Double ribasso, Double offAumento, Long numAggi) {
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

    private boolean inserisciPartecipantiAdPostPubb(List<PartecipanteADType> partecipanti, Long codGara, Long codLotto,
                                                    String codein) throws Exception {
        boolean inseritoW9aggi = false;
        try {


            Long tipoRaggruppamento = 0L;

            if(partecipanti != null){
                Map<String, List<PartecipanteADType>> listaPerPartecipante = partecipanti.stream()
                        .filter(Objects::nonNull)
                        .filter(e->StringUtils.isNotBlank(e.getCodiceFiscale()))
                        .collect(Collectors.groupingBy(e-> e.getIdPartecipante().toString(), Collectors.toList()));

                Long numAggi = 1L;
                for (Map.Entry<String, List<PartecipanteADType>> p : listaPerPartecipante.entrySet()) {
                    if(p.getValue().size()>1) {

                        tipoRaggruppamento++;
                    }

                    for (it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.PartecipanteADType part : p.getValue()) {

                        if(part != null) {
                            String CodFisc = part.getCodiceFiscale() != null ? part.getCodiceFiscale().toUpperCase().trim() : "";
                            if(StringUtils.isNotBlank(CodFisc) && CodFisc.length() > 30) {
                                CodFisc = StringUtils.truncate(CodFisc, 30);
                            }
                            List<String> codImpList = this.contrattiMapper.esisteImpresaCfList(CodFisc, codein);
                            String codImp = codImpList != null && !codImpList.isEmpty() ? codImpList.get(0) : null;
                            if(codImp == null) {
                                ImpresaEntry imp = new ImpresaEntry();
                                codImp = this.calcolaCodificaAutomatica("IMPR", "CODIMP");
                                imp.setCodiceImpresa(codImp);
                                imp.setCodiceFiscale(CodFisc);
                                imp.setStazioneAppaltante(codein);
                                imp.setRagioneSociale(part.getDenominazione());
                                this.contrattiMapper.insertImpresa(imp);

                                this.contrattiMapper.insertTeim(codImp, "", "", CodFisc);
                                Long maxId = wgcManager.getNextId("IMPLEG");
                                this.contrattiMapper.insertImpleg(maxId, codImp, "");
                            }

                            Double importo = part.getImporto() != null ? part.getImporto() : 0D;

                            Long tipo = part.getTipoOE() != null && part.getTipoOE().getCodice() != null ? Long.valueOf(mapTipoOe.get(part.getTipoOE().getCodice())) : null;
                            Long ruolo = part.getRuoloOE() != null && part.getRuoloOE().getCodice() != null ? Long.valueOf(part.getRuoloOE().getCodice()) : null;
                            if(ruolo != null && ruolo != 4) {
                                if(ruolo != 1 && ruolo != 2) {
                                    ruolo = null;
                                }
                                if(p.getValue().size() > 1) {
                                    this.inserisciAggiudicazioneW9Aggi(codGara, codLotto, part.getIdPartecipante().toString(), codImp, tipoRaggruppamento, tipo, ruolo, importo, null, null, numAggi);
                                    numAggi++;
                                } else {
                                    this.inserisciAggiudicazioneW9Aggi(codGara, codLotto, part.getIdPartecipante().toString(), codImp , null, tipo, ruolo, importo, null, null, numAggi);
                                    numAggi++;
                                }
                                inseritoW9aggi = true;
                            }

                        }
                    }
                }
            }




        }catch (Exception e) {
            logger.error("Errore in fase di importazione fase pcp: inserisciPartecipantiAd");
            throw e;
        }


        return inseritoW9aggi;

    }

    private Map<String, Map<String, String>> prelevaDatiEformAggiudicazioneCAN(ContractAwardNoticeType eform) {
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        //dataAggiudicazione (bt-1451)
        //offertaMassimoRibasso (bt-710 e bt-711)

        Map<String, String> datiAgg = new HashMap<String, String>();

        try {
            if(eform != null) {
                if(eform.getUBLExtensions() != null) {
                    if(eform.getUBLExtensions().getUBLExtension() != null &&
                            eform.getUBLExtensions().getUBLExtension().size() > 0 &&
                            eform.getUBLExtensions().getUBLExtension().get(0) != null) {
                        UBLExtensionType ext = eform.getUBLExtensions().getUBLExtension().get(0);
                        if(ext.getExtensionContent() != null &&
                                ext.getExtensionContent().getEformsExtension() != null &&
                                ext.getExtensionContent().getEformsExtension().getNoticeResult() != null) {

                            if(ext.getExtensionContent().getEformsExtension().getNoticeResult().getLotResult() != null &&
                                    ext.getExtensionContent().getEformsExtension().getNoticeResult().getLotResult().size() > 0) {
                                for (LotResultType lot : ext.getExtensionContent().getEformsExtension().getNoticeResult().getLotResult()) {
                                    if(lot != null) {
                                        if(lot.getLowerTenderAmount() != null && lot.getLowerTenderAmount().getValue() != null) {
                                            datiAgg.put("offertaMinimo" ,lot.getLowerTenderAmount().getValue().toString());
                                        }
                                        if(lot.getHigherTenderAmount() != null && lot.getHigherTenderAmount().getValue() != null) {
                                            datiAgg.put("offertaMassimo" ,lot.getHigherTenderAmount().getValue().toString());
                                        }

                                        if(lot.getSettledContract() != null && lot.getSettledContract().size() > 0 && lot.getSettledContract().get(0) != null
                                                && lot.getSettledContract().get(0).getID() != null && lot.getSettledContract().get(0).getID().getValue() != null) {
                                            String settledContract = lot.getSettledContract().get(0).getID().getValue();
                                            if(ext.getExtensionContent().getEformsExtension().getNoticeResult().getSettledContract() != null &&
                                                    ext.getExtensionContent().getEformsExtension().getNoticeResult().getSettledContract().size() > 0) {
                                                for (SettledContractType sc : ext.getExtensionContent().getEformsExtension().getNoticeResult().getSettledContract()) {
                                                    if(sc != null && sc.getAwardDate() != null) {
                                                        if(sc.getID() != null && sc.getID().getValue() != null && settledContract.equals(sc.getID().getValue())) {
                                                            XMLGregorianCalendar xmlGregorianCalendar = sc.getAwardDate().getValue();
                                                            Date dataAggiudicazione = schedePcpUtils.convertXMLGregorianCalendarToDate(xmlGregorianCalendar);
                                                            datiAgg.put("dataAggiudicazione" ,dataAggiudicazione.toString());
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if(lot.getTenderLot() != null && lot.getTenderLot().getID() != null && lot.getTenderLot().getID().getSchemeName() != null && lot.getTenderLot().getID().getSchemeName().toLowerCase().equals("lot")) {
                                            result.put(lot.getTenderLot().getID().toString(), datiAgg);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("Errore in fase di lettura eform: prelevaDatiEformCAN");
            throw e;
        }

        return result;
    }
}
