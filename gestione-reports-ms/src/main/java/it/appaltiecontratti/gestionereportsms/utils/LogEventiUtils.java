package it.appaltiecontratti.gestionereportsms.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.domain.WLogEventi;
import it.appaltiecontratti.gestionereportsms.dto.WLogEventiDTO;
import it.appaltiecontratti.gestionereportsms.repositories.UserRepository;
import it.appaltiecontratti.gestionereportsms.repositories.WGenChiaviRepository;
import it.appaltiecontratti.gestionereportsms.repositories.WLogEventiRepository;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author Andrea Chinellato
 */
@Service
public class LogEventiUtils {

    private static final Logger logger = LoggerFactory.getLogger(LogEventiUtils.class);

    public static final String COD_EVENTO_GET_LISTA_REPORT = "GET_LISTA_REPORT";
    public static final String COD_EVENTO_GET_DETTAGLIO_REPORT = "GET_DETTAGLIO_REPORT";
    public static final String COD_EVENTO_UPDATE_DETTAGLIO_REPORT = "UPDATE_DETTAGLIO_REPORT";
    public static final String COD_EVENTO_UPDATE_DEFINIZIONE_REPORT = "UPDATE_DEFINIZIONE_REPORT";
    public static final String COD_EVENTO_GET_PARAMETRI_REPORT = "GET_PARAMETRI_REPORT";
    public static final String COD_EVENTO_UPDATE_PARAMETRO_REPORT = "UPDATE_PARAMETRO_REPORT";
    public static final String COD_EVENTO_INSERT_PARAMETRO_REPORT = "INSERT_PARAMETRO_REPORT";
    public static final String COD_EVENTO_DELETE_PARAMETRO_REPORT = "DELETE_PARAMETRO_REPORT";
    public static final String COD_EVENTO_GET_PROFILI_REPORT = "GET_PROFILI_REPORT";
    public static final String COD_EVENTO_SET_PROFILI_REPORT = "SET_PROFILI_REPORT";
    public static final String COD_EVENTO_GET_UFFINT_REPORT = "GET_UFFINT_REPORT";
    public static final String COD_EVENTO_SET_UFFINT_REPORT = "SET_UFFINT_REPORT";
    public static final String COD_EVENTO_INSERIMENTO_NEW_REPORT = "INSERT_NEW_REPORT";
    public static final String COD_EVENTO_DELETE_REPORT = "DELETE_REPORT";
    public static final String COD_EVENTO_DUPLICA_REPORT = "DUPLICA_REPORT";
    public static final String COD_EVENTO_ESEGUI_REPORT = "ESEGUI_REPORT";
    public static final String COD_EVENTO_ESPORTA_REPORT_CSV = "ESPORTA_REPORT_CSV";
    public static final String COD_EVENTO_ESPORTA_REPORT_XLSX = "ESPORTA_REPORT_XLSX";
    public static final String COD_EVENTO_ESPORTA_REPORT_PDF_A = "ESPORTA_REPORT_PDF_A";
    public static final String COD_EVENTO_ESPORTA_REPORT_JSON = "ESPORTA_REPORT_JSON";
    public static final String COD_EVENTO_ESPORTA_REPORT_RTF = "ESPORTA_REPORT_RTF";
    public static final String COD_EVENTO_ESPORTA_REPORT_DOCX = "ESPORTA_REPORT_DOCX";
    public static final String COD_EVENTO_SAVE_CACHE_REPORT = "SAVE_CACHE_REPORT";

    public static final String COD_EVENTO_GET_LISTA_REPORT_PREDEFINITI = "GET_LISTA_REPORT_PREDEFINITI";
    public static final String COD_EVENTO_ESEGUI_REPORT_PREDEFINITO = "ESEGUI_REPORT_PREDEFINITO";
    public static final String COD_EVENTO_ESEGUI_REPORT_SCHEDULATO_SUCCESS = "ESEGUI_REPORT_SCHEDULATO_SUCCESS";
    public static final String COD_EVENTO_ESEGUI_REPORT_SCHEDULATO_ERROR = "ESEGUI_REPORT_SCHEDULATO_SUCCESS_ERROR";

    /**
     * Livello di tracciatura info.
     */
    public static final int LIVELLO_INFO = 1;
    /**
     * Livello di tracciatura errore non bloccante.
     */
    public static final int LIVELLO_WARNING = 2;
    /**
     * Livello di tracciatura errore bloccante.
     */
    public static final int LIVELLO_ERROR = 3;

    private static final long DEFAULT_INCREMENT = 1;

    /**
     * Dipendenze repository
     * */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WLogEventiRepository wLogEventiRepository;

    @Autowired
    private WGenChiaviRepository wGenChiaviRepository;

    /**
     * Object Mapper
     * */
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Metodi di Utility
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLogEvent(final WLogEventiDTO dto) {

        if (dto == null)
            throw new IllegalArgumentException("dto null");

        logger.debug("Execution start WLogEventiServiceImpl::insertLogEvent for dto [ {} ]", dto);

        WLogEventi po = objectMapper.convertValue(dto, WLogEventi.class);

        User user = null;

        if (dto.getSyscon() != null)
            user = userRepository.findById(dto.getSyscon()).orElse(null);

        po.setUser(user);

        Long id = getNextId("W_LOGEVENTI");

        po.setIdEvento(id);

        wLogEventiRepository.save(po);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60, rollbackFor=Exception.class)
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") })
    public Long getNextId(final String tabella) {

        logger.debug("START esecuzione in RicercheManager nel metodo getNextId per la tabella [ {} ]", tabella);

        Long chiave = wGenChiaviRepository.findByTabellaIgnoreCase(tabella).get().getChiave();

        long chiaveIncrementata = chiave + DEFAULT_INCREMENT;
        wGenChiaviRepository.incrementChiavePerTabella(chiaveIncrementata, tabella);

        logger.debug("END esecuzione in RicercheManager nel metodo getNextId per la tabella [ {} ]", tabella);

        return wGenChiaviRepository.findByTabellaIgnoreCase(tabella).get().getChiave();
    }

    /**
     * Metodi di classe per log.
     * */
    public WLogEventiDTO createGetListaReportEvent(final Long syscon, final String ipEvento, final String appCode, final String codProfilo) {

        logger.debug("Execution start LogEventiUtils::createGetListaReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Get lista report disponibli all''utente: {0}";

        if (syscon != null){

            msg = MessageFormat.format(msg, syscon);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        //dto.setOggettoEvento(msg);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_GET_LISTA_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createGetDettaglioReportEvent(final Long syscon, final String ipEvento, final String codProfilo, final String appCode, final Long idRicerca) {

        logger.debug("Execution start LogEventiUtils::createGetDettaglioReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Get dettaglio report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_GET_DETTAGLIO_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createUpdateDettaglioReportEvent(final Long syscon, final String ipEvento, final String appCode, final Long idRicerca, final String codProfilo) {

        logger.debug("Execution start LogEventiUtils::createUpdateDettaglioReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Update dettaglio report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setOggettoEvento(String.valueOf(idRicerca));
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_UPDATE_DETTAGLIO_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }


    public WLogEventiDTO createUpdateDefinizioneReportEvent(final Long syscon, final String ipEvento, final String appCode, final Long idRicerca, final String codProfilo) {

        logger.debug("Execution start LogEventiUtils::createUpdateDefinizioneReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Update definizione report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_UPDATE_DEFINIZIONE_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createGetListaParametriReportEvent(final Long syscon, final String ipEvento, final String appCode, final Long idRicerca, final String codProfilo) {

        logger.debug("Execution start LogEventiUtils::createUpdateDefinizioneReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Get Lista Parametri per report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_GET_PARAMETRI_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createInsertParamReportEvent(
            final Long syscon,
            final String codice,
            final Long progressivo,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createUpdateDefinizioneReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Insert parametro in Lista parametri per report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        String obj = "[" + "id:" + idRicerca + "," + "cod:" + codice + "," + "progr:" + progressivo + "]";

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setOggettoEvento(obj);
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_INSERT_PARAMETRO_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createGetProfiliReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createGetProfiliReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Get profili per report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_GET_PROFILI_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createSetProfiliReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ){
        logger.debug("Execution start LogEventiUtils::createSetProfiliReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Set profili per report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_SET_PROFILI_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createGetUfficiIntestatariReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ){
        logger.debug("Execution start LogEventiUtils::createGetUfficiIntestatariReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Get Uffint per report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_GET_UFFINT_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createSetUfficiIntestatariReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ){
        logger.debug("Execution start LogEventiUtils::createSetUfficiIntestatariReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Set Uffint per report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_SET_UFFINT_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createInsertReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createInsertReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Insert nuovo report";

        if (idRicerca != null){

            dto.setOggettoEvento("[idRicerca:" + idRicerca + "]");
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_INSERIMENTO_NEW_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createDeleteReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createDeleteReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Delete report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_DELETE_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createDuplicaReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicercaOld,
            final Long idRicercaNew,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createDuplicaReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Duplica report con idRicerca: {0}. Il nuovo report ha idRicerca: {1}";

        if (idRicercaOld != null && idRicercaNew != null){

            msg = MessageFormat.format(msg, idRicercaOld, idRicercaNew);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_DUPLICA_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createDeleteParamReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codice,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createDeleteParamReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Delete parametro report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        if(idRicerca != null && codice != null){

            dto.setOggettoEvento("[idRicerca:" + idRicerca + ",codice:" + codice + "]");
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_DELETE_PARAMETRO_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createUpdateParamReportEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codice,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createUpdateParamReportEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Update parametro report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        if(idRicerca != null && codice != null){

            dto.setOggettoEvento("[idRicerca:" + idRicerca + ",codice:" + codice + "]");
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_UPDATE_PARAMETRO_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public WLogEventiDTO createExecuteReportWithParamsEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo,
            final String defSql
    ) {

        logger.debug("Execution start LogEventiUtils::createExecuteReportWithParamsEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Eseguito report con idRicerca: {0}. DefSQL eseguita: [{1}]";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca, defSql);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESEGUI_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createExportReportCSVEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createExportReportCSVEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Esportato CSV risultato report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESPORTA_REPORT_CSV);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createExportReportXLSXEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createExportReportXLSXEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Esportato XLSX risultato report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESPORTA_REPORT_XLSX);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createExportReportJSONEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createExportReportJSONEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Esportato JSON risultato report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESPORTA_REPORT_JSON);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createExportReportRTFEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createExportReportRTFEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Esportato RTF risultato report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESPORTA_REPORT_RTF);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createExportReportDOCXEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createExportReportDOCXEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Esportato DOCX risultato report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESPORTA_REPORT_DOCX);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createExportReportPDFAEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createExportReportPDFAEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Esportato PDF/A risultato report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESPORTA_REPORT_PDF_A);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createSaveParamsWCacheRicParEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createSaveParamsWCacheRicParEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Salvati parametri per il report con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_SAVE_CACHE_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createGetListaReportPredefinitiEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createGetListaReportPredefinitiEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Get Lista Report predefiniti all'utente: {0}";

        if (syscon != null){

            msg = MessageFormat.format(msg, syscon);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_GET_LISTA_REPORT_PREDEFINITI);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createEseguiReportPredefinitoEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createEseguiReportPredefinitoEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Eseguito report predefinito con idRicerca: {0}";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESEGUI_REPORT_PREDEFINITO);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createSchedulazioneReportSuccessEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo,
            final String defSQL
    ) {

        logger.debug("Execution START LogEventiUtils::createSchedulazioneReportSuccessEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg;
        if(!StringUtils.isEmpty(defSQL)){
            msg = "Eseguito report schedulato con idRicerca: [{0}] e defSQL: [{1}]";
            if (idRicerca != null){
                msg = MessageFormat.format(msg, idRicerca, defSQL);
            }
        }
        else {
            msg = "Eseguito report schedulato con idRicerca: [{0}] e defSQL vuota.";
            if (idRicerca != null){
                msg = MessageFormat.format(msg, idRicerca);
            }
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESEGUI_REPORT_SCHEDULATO_SUCCESS);
        dto.setDescrizione(msg);

        logger.debug("Execution END LogEventiUtils::createSchedulazioneReportSuccessEvent");

        return dto;
    }

    public WLogEventiDTO createSchedulazioneReportErrorEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idRicerca,
            final String codProfilo,
            final String defSQL
    ) {

        logger.debug("Execution START LogEventiUtils::createSchedulazioneReportSuccessEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Eseguito report schedulato con idRicerca: [{0}] e defSQL: [{1}]";

        if (idRicerca != null){

            msg = MessageFormat.format(msg, idRicerca, defSQL);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_ESEGUI_REPORT_SCHEDULATO_ERROR);
        dto.setDescrizione(msg);

        logger.debug("Execution END LogEventiUtils::createSchedulazioneReportSuccessEvent");

        return dto;
    }

    public WLogEventiDTO createRimuoviReportPreferitoEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idPreferito,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createRimuoviReportPreferitoEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Rimosso report preferito con id: {0}";

        if (idPreferito != null){

            msg = MessageFormat.format(msg, idPreferito);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_DELETE_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    public WLogEventiDTO createInsertReportPreferitoEvent(
            final Long syscon,
            final String ipEvento,
            final String appCode,
            final Long idPreferito,
            final String codProfilo
    ) {

        logger.debug("Execution start LogEventiUtils::createInsertReportPreferitoEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Aggiunto report preferito con id: {0}";

        if (idPreferito != null){

            msg = MessageFormat.format(msg, idPreferito);
        }

        dto.setSyscon(syscon);
        dto.setCodApp(appCode);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodProfilo(codProfilo);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodiceEvento(COD_EVENTO_DELETE_REPORT);
        dto.setDescrizione(msg);

        return dto;
    }

    private String getIpEvento(final String ipEvento) {
        return StringUtils.isNotBlank(ipEvento) && ipEvento.length() > 40 ? ipEvento.substring(0, 40) : ipEvento;
    }
}
