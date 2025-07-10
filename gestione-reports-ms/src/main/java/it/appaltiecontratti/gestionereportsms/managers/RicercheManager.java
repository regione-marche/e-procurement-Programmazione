package it.appaltiecontratti.gestionereportsms.managers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.pdfa.PdfADocument;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.*;
import it.appaltiecontratti.gestionereportsms.dto.*;
import it.appaltiecontratti.gestionereportsms.exceptions.*;
import it.appaltiecontratti.gestionereportsms.repositories.*;
import it.appaltiecontratti.gestionereportsms.scheduler.ThreadPoolTaskSchedulerManager;
import it.appaltiecontratti.gestionereportsms.specifications.RicercheSpecification;
import it.appaltiecontratti.gestionereportsms.utils.CsvUtils;
import it.appaltiecontratti.gestionereportsms.utils.DtoMapper;
import it.appaltiecontratti.gestionereportsms.utils.LogEventiUtils;
import it.appaltiecontratti.gestionereportsms.utils.UtilsMethods;
import jakarta.persistence.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author andrea.chinellato
 * */

@Service
@Validated
public class RicercheManager {

    /**
     * Logger di classe
     * */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Variabile d'ambiente. Vedere application.properties
     * */
    @Value("${application.codiceProdotto}")
    private String COD_APP;

    /**
     * DTO Mapper/Entity manager/Scheduler
     * */
    @Autowired
    protected DtoMapper dtoMapper = null;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ThreadPoolTaskSchedulerManager schedulerManager;

    /**
     * Repositories di ogni entity.
     * */
    @Autowired
    private WRicercheRepository wrRepository;

    @Autowired
    private WRicParamRepository wRicParamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfiloRepository profiloRepository;

    @Autowired
    private UffIntRepository uffintRepository;

    @Autowired
    private WRicProRepository  wRicProRepository;

    @Autowired
    private WRicUffintRepository wRicUffintRepository;

    @Autowired
    private WCacheRicParRepository wCacheRicParRepository;

    @Autowired
    WConfigRepository wConfigRepository;

    @Autowired
    WPreferitiRepository wPreferitiRepository;

    @Autowired
    LogEventiUtils logEventiUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
     * Metodi del manager
     * */
    public ResponseListaDTO getListaReport(
            String codApp,
            Integer famiglia,
            String nome,
            String descrizione,
            Integer disp,
            String sysute,
            String fieldToSort,
            String sortDirection,
            Long syscon
    ){

        logger.info("START esecuzione in RicercheManager del metodo getListaReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{
            List<WRicerche> ricercheList = wrRepository.findAll(
                    RicercheSpecification.getRicercheSpecification(
                            codApp,
                            famiglia,
                            nome,
                            descrizione,
                            disp,
                            sysute,
                            fieldToSort,
                            sortDirection
                    )
            );

            response.setTotalCount((long) ricercheList.size());

            //Recupero la lista dei report preferiti
            List<WPreferiti> reportPreferiti = wPreferitiRepository.findBySyscon(syscon);

            List<WRicercheDTO> ricercheMapped = new ArrayList<>();
            for(WRicerche single : ricercheList ){

                String utenteCreatore = single.getUser().getSysute();
                Long pubblicato = single.getPubblicato();
                Long sysconDTO = single.getUser().getSyscon();

                single.setUser(null);
                //single.setPubblicato(null);

                WRicercheDTO report = objectMapper.convertValue(single, WRicercheDTO.class);
                report.setSysute(utenteCreatore);
                report.setPubblicato(UtilsMethods.mapFieldTwoValues(pubblicato, 0L, "No", 1L, "Sì"));
                report.setSyscon(sysconDTO);

                report.setIsPreferito(!reportPreferiti.isEmpty() && reportPreferiti.stream()
                        .anyMatch((reportPrefe) ->
                                reportPrefe.getKey1().equalsIgnoreCase(report.getIdRicerca().toString())));

                ricercheMapped.add(report);
            }

            response.setResponse(ricercheMapped);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (GenericReportOperationException e){

            e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_LISTA_REPORT);
            logger.error("Si è verificato un errore durante la get lista dei report disponibili: ", e);

            throw e;
        }

        //TODO_Da testare
        //WLogEventiDTO wLogEventiDTO = logEventiUtils.createGetListaReportEvent(syscon, ipEvento, COD_APP, codProfilo);
        //insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager del metodo getListaReport");

        return response;
    }

    public ResponseListaDTO getDetailReport ( Long idRicerca ) {

        logger.info("START esecuzione in RicercheManager::getDetailReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            Optional<WRicerche> reportDetail = wrRepository.findById(idRicerca);
            if(reportDetail.isPresent()) {

                String utenteCreatore = reportDetail.get().getUser().getSysute();
                Long pubblicato = reportDetail.get().getPubblicato();
                Long sysconReport = reportDetail.get().getUser().getSyscon();
                Long tuttiUffici = reportDetail.get().getTuttiUffici();
                Long tuttiProfili = reportDetail.get().getTuttiProfili();

                String codReportWs = reportDetail.get().getCodReportWs();
                String cronExpression = reportDetail.get().getCronExpression();

                reportDetail.get().setUser(null);

                WRicercheDTO result = objectMapper.convertValue(reportDetail.get(), WRicercheDTO.class);
                result.setPubblicato(UtilsMethods.mapFieldTwoValues(pubblicato, 0L, "0", 1L, "1"));
                result.setTuttiProfili(UtilsMethods.mapFieldTwoValues(tuttiProfili, 0L, "0", 1L, "1"));
                result.setTuttiUffici(UtilsMethods.mapFieldTwoValues(tuttiUffici, 0L, "0", 1L, "1"));
                result.setSysute(utenteCreatore);
                result.setSyscon(sysconReport);

                if(!StringUtils.isEmpty(codReportWs)){
                    result.setEsponiComeServizio("1");
                }
                else{
                    result.setEsponiComeServizio("0");
                }

                //In apertura scheda dettaglio se cronExpression != null e il report non contiene parametri da inserire, allora il campo fittizio "Schedula" vale Si.
                List<WRicParam> paramsReport = wRicParamRepository.findByIdRicerca(idRicerca);
                result.setContieneParametri(!paramsReport.isEmpty());

                if(!StringUtils.isEmpty(cronExpression) && paramsReport.isEmpty()){
                    result.setSchedula("1");
                    result.setNoOutputVuoto(reportDetail.get().getNoOutputVuoto());
                }
                else{
                    result.setSchedula("0");
                    result.setNoOutputVuoto(reportDetail.get().getNoOutputVuoto());
                }

                result.setEmailSchedulazioneRisultato(reportDetail.get().getEmailRisultatoSchedulazione());
                result.setFormatoSchedulazione(String.valueOf(reportDetail.get().getFormatoRisultatoSchedulazione()));

                List<WRicParam> ricercheList = wRicParamRepository.findByIdRicerca(idRicerca);
                String defSql = wrRepository.getDefSqlByIdRicerca(idRicerca);

                if(!StringUtils.isEmpty(defSql)){
                    defSql = UtilsMethods.removeCommentsAndSeparators(defSql);

                    List<String> paramsInDefSql = new ArrayList<>();
                    paramsInDefSql = UtilsMethods.extractSubstrings(defSql);

                    List<String> mandatoryParams = new ArrayList<>();
                    for(WRicParam param : ricercheList){
                        if (param.getObbligatorio() != null && param.getObbligatorio() == 1L){
                            mandatoryParams.add(param.getCodice());
                        }
                    }

                    List<String> mandatoryParamsNotUsed = new ArrayList<>();
                    for(String param : mandatoryParams){
                        if(!paramsInDefSql.contains(param)) {
                            mandatoryParamsNotUsed.add(param);
                        }
                    }

                    response.setMandatoryParamsNotUsed(mandatoryParamsNotUsed);
                }

                response.setResponse(result);
                response.setDone(AppConstants.RESPONSE_DONE_Y);
            }
            //Non si dovrebbe mai entrare qui!!
            else {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_NOT_FOUND_REPORT);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);
                throw ex;
                //return response;
            }

        }
        catch (GenericReportOperationException e){
            logger.error("errore metodo getDetailReport: ", e);
            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_ERROR_REPORT);
                logger.error("Si è verificato un errore in fase di getDetail del report con idRicerca [{}]", idRicerca);

            }
            throw e;
        } catch (Exception e){
            logger.error("errore metodo getDetailReport: ", e);
            throw e;
        }

        //Loggo la WLogEventi
        //WLogEventiDTO wLogEventiDTO = logEventiUtils.createGetDettaglioReportEvent(syscon, ipEvento, codProfilo, COD_APP, idRicerca);
        //insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::getDetailReport");

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO updateDefinizioneSqlReport ( Long idRicerca, DefinizioneReportFormDTO form, Long syscon, String ipEvento, String codProfilo ) {

        logger.info("START esecuzione in RicercheManager::updateDefinizioneSqlReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            WRicerche reportToUpdate = wrRepository.findById(idRicerca).orElse(null);

            //Controllo presenza del report a DB. Non si dovrebbe mai entrare qui!!
            if(reportToUpdate == null) {

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_NOT_FOUND_REPORT);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);

                throw ex;
            }

            //Controllo esistenza della definizione SQL nel form che mi arriva.
            if(StringUtils.isEmpty(form.getDefSql())){

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_RESPONSE_DEFSQL_NOT_FOUND);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);

                throw ex;
            }

            if(!UtilsMethods.checkMaliciousInputQuery(form.getDefSql())){

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DEFSQL_OPERATION_NOT_ALLOWED);
                logger.error("Definizione SQL sospetta. Operazione di aggiornamento annullata. DEFSQL: [{}]", form.getDefSql());

                throw ex;
            }

            reportToUpdate.setDefSql(form.getDefSql());
            reportToUpdate = wrRepository.save(reportToUpdate);

            WRicercheDTO result = objectMapper.convertValue(reportToUpdate, WRicercheDTO.class);

            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setResponse(result);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_UPDATE_ERROR_DEFINIZIONE_REPORT);
                logger.error("Si è verificato un errore in fase di updateDefinizioneSqlReport del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createUpdateDefinizioneReportEvent(syscon, ipEvento, COD_APP, idRicerca, codProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::updateDefinizioneSqlReport");

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO updateDatiGeneraliReport ( Long idRicerca, WRicercheDTO form, UserDTO currentUserDTO, Long syscon, String ipEvento, String codProfilo) {

        logger.info("START esecuzione in RicercheManager::updateDatiGeneraliReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            WRicerche datiGeneraliReportToUpdate = wrRepository.findById(idRicerca).orElse(null);

            //Controllo presenza del report a DB. Non si dovrebbe mai entrare qui!!
            if(datiGeneraliReportToUpdate == null) {

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_NOT_FOUND_REPORT);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);

                throw ex;
            }

            Long tuttiProfiliBefore = datiGeneraliReportToUpdate.getTuttiProfili();
            Long tuttiUfficiBefore = datiGeneraliReportToUpdate.getTuttiUffici();

            datiGeneraliReportToUpdate.setNome(form.getNome());
            datiGeneraliReportToUpdate.setDescrizione(form.getDescrizione());
            datiGeneraliReportToUpdate.setPubblicato(form.getPubblicato() != null ? Long.parseLong(form.getPubblicato()) : 0L);
            datiGeneraliReportToUpdate.setTuttiProfili(form.getTuttiProfili() != null ? Long.parseLong(form.getTuttiProfili()) : 0L);
            datiGeneraliReportToUpdate.setTuttiUffici(form.getTuttiUffici() != null ? Long.parseLong(form.getTuttiUffici()) : 0L);

            if(StringUtils.equals(form.getEsponiComeServizio(), "0")){
                datiGeneraliReportToUpdate.setCodReportWs(null);
            }
            else {
                // Controllo univocità del campo codReportWs.
                // Se esiste già un report con un codReportWs uguale, impedisco il salvataggio e ritorno il messaggio d'errore.
                Long codReportWsUnivoco = wrRepository.countByCodReportWs(form.getCodReportWs());

                if(codReportWsUnivoco == 0L || (codReportWsUnivoco == 1 && StringUtils.equals(form.getCodReportWs(), datiGeneraliReportToUpdate.getCodReportWs()))){
                    datiGeneraliReportToUpdate.setCodReportWs(form.getCodReportWs());
                }
                else {
                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_CODREPORTWS_NON_UNIVOCO);

                    throw ex;
                }
            }

            if(StringUtils.equals(form.getSchedula(), "0")){
                datiGeneraliReportToUpdate.setCronExpression(null);
                datiGeneraliReportToUpdate.setFormatoRisultatoSchedulazione(null);
                datiGeneraliReportToUpdate.setEmailRisultatoSchedulazione(null);
                datiGeneraliReportToUpdate.setNoOutputVuoto(0L);

                schedulerManager.schedulateOrUpdateReport(null, idRicerca, syscon, codProfilo, ipEvento);
            } else {
                datiGeneraliReportToUpdate.setCronExpression(!StringUtils.isEmpty(form.getCronExpression()) ? form.getCronExpression() : null);
                datiGeneraliReportToUpdate.setFormatoRisultatoSchedulazione(!StringUtils.isEmpty(form.getFormatoSchedulazione()) ? Long.valueOf(form.getFormatoSchedulazione()) : null);
                datiGeneraliReportToUpdate.setEmailRisultatoSchedulazione(!StringUtils.isEmpty(form.getEmailSchedulazioneRisultato()) ? form.getEmailSchedulazioneRisultato() : null);
                datiGeneraliReportToUpdate.setNoOutputVuoto(form.getNoOutputVuoto() != null ? form.getNoOutputVuoto() : null);

                if(CronExpression.isValidExpression(form.getCronExpression())){
                    schedulerManager.schedulateOrUpdateReport(form.getCronExpression(), idRicerca, syscon, codProfilo, ipEvento);
                }
                else{
                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_CRON_EXPRESSION_NOT_VALID);

                    throw ex;
                }
            }

            datiGeneraliReportToUpdate = wrRepository.save(datiGeneraliReportToUpdate);

            //Prima di convertire il tutto in un DTO, provvedo ad associare tutti i profili se richiesto dall'utente.
            //Non è necessario associare ogni profilo all'idRicerca del report voluto, ma viene solamente settato il campo tuttiProfili = 1 nella WRicerche.
            if(tuttiProfiliBefore == 0 && Long.parseLong(form.getTuttiProfili()) == 1){
                rendiDisponibileTuttiProfili(idRicerca);
            }
            //Medesima cosa per gli uffici intestatari.
            if(tuttiUfficiBefore == 0 && form.getTuttiUffici() != null && Long.parseLong(form.getTuttiUffici()) == 1){
                rendiDisponibileTuttiUffici(idRicerca);
            }

            WRicercheDTO result = objectMapper.convertValue(datiGeneraliReportToUpdate, WRicercheDTO.class);

            result.setEsponiComeServizio(StringUtils.equals(form.getEsponiComeServizio(), "1") ? "Si" : "No");
            result.setSchedula(StringUtils.equals(form.getSchedula(), "1") ? "Si" : "No");

            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setResponse(result);

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_UPDATE_ERROR_DATI_GENERALI_REPORT);
                logger.error("Si è verificato un errore in fase di updateDatiGeneraliReport del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createUpdateDettaglioReportEvent(syscon, ipEvento, COD_APP, form.getIdRicerca(), codProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::updateDatiGeneraliReport");

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO duplicaRigaReport ( Long idRicerca, String ipEvento, Long syscon, String idProfilo, UserDTO currentUserDTO ) {

        logger.info("START esecuzione in RicercheManager::duplicaRigaReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        Long idRicercaMax;

        try {

            Optional<WRicerche> reportToDuplicate = wrRepository.findById(idRicerca);

            //Se il report è presente a DB lo clono e salvo la nuova riga in tabella.
            if(reportToDuplicate.isPresent()){

                WRicerche clonedReport = new WRicerche();

                idRicercaMax = logEventiUtils.getNextId("W_RICERCHE");

                String utenteCreatore = reportToDuplicate.get().getUser().getSyscon().toString();
                Long pubblicato = reportToDuplicate.get().getPubblicato();
                //Genero il nuovo nome del report basandomi sul numero di copie che possiede oppure se non possiede alcuna copia di se stesso.
                String clonedReportName = generaNuovoNomeReport(reportToDuplicate.get().getNome());

                clonedReport.setIdRicerca(idRicercaMax);
                clonedReport.setNome(clonedReportName);
                clonedReport.setDescrizione(reportToDuplicate.get().getDescrizione());
                clonedReport.setDefSql(reportToDuplicate.get().getDefSql());
                clonedReport.setCodApp(reportToDuplicate.get().getCodApp());
                clonedReport.setTipo(reportToDuplicate.get().getTipo());
                clonedReport.setValoriDistinti(reportToDuplicate.get().getValoriDistinti());
                clonedReport.setRisperpag(reportToDuplicate.get().getRisperpag());
                clonedReport.setVismodelli(reportToDuplicate.get().getVismodelli());
                clonedReport.setEntprinc(reportToDuplicate.get().getEntprinc());
                clonedReport.setUserOwner(reportToDuplicate.get().getUserOwner());
                clonedReport.setFamiglia(reportToDuplicate.get().getFamiglia());
                clonedReport.setIdProspetto(reportToDuplicate.get().getIdProspetto());
                clonedReport.setPersonale(reportToDuplicate.get().getPersonale());
                clonedReport.setFiltroUtente(reportToDuplicate.get().getFiltroUtente());
                clonedReport.setProfiloOwner(reportToDuplicate.get().getProfiloOwner());
                clonedReport.setVisparam(reportToDuplicate.get().getVisparam());
                clonedReport.setLinkScheda(reportToDuplicate.get().getLinkScheda());
                clonedReport.setCodReportWs(reportToDuplicate.get().getCodReportWs());
                clonedReport.setFiltroUffInt(reportToDuplicate.get().getFiltroUffInt());
                clonedReport.setDefSql(reportToDuplicate.get().getDefSql());
                clonedReport.setUser(reportToDuplicate.get().getUser());
                clonedReport.setPubblicato(reportToDuplicate.get().getPubblicato());
                clonedReport.setTuttiUffici(reportToDuplicate.get().getTuttiUffici());
                clonedReport.setTuttiProfili(reportToDuplicate.get().getTuttiProfili());

                if(clonedReport.getTuttiProfili() == 1){

                    rendiDisponibileTuttiProfili(clonedReport.getIdRicerca());
                }
                else{

                    //Aggiungo eventuali profili del report da cui sto partendo per creare il clone
                    List<WRicpro> allProfilesToClone = wRicProRepository.findByIdRicerca(reportToDuplicate.get().getIdRicerca());
                    if(allProfilesToClone!= null &&!allProfilesToClone.isEmpty()) {
                        for (WRicpro profile : allProfilesToClone) {

                            String queryToInsert = "INSERT INTO w_ricpro (id_ricerca, cod_profilo) VALUES (?,?)";
                            entityManager.createNativeQuery(queryToInsert)
                                    .setParameter(1, clonedReport.getIdRicerca())
                                    .setParameter(2, profile.getId().getCodProfilo())
                                    .executeUpdate();
                        }
                    }
                }
                //Medesima cosa per gli uffici intestatari
                if(clonedReport.getTuttiUffici() == 1){

                    rendiDisponibileTuttiUffici(clonedReport.getIdRicerca());
                }
                else{

                    //Aggiungo eventuali uffInt del report da cui sto partendo per creare il clone
                    List<WRicuffint> allUffIntToClone = wRicUffintRepository.findByIdRicerca(reportToDuplicate.get().getIdRicerca());
                    if(allUffIntToClone!= null &&!allUffIntToClone.isEmpty()) {
                        for (WRicuffint uffInt : allUffIntToClone) {

                            String queryToInsert = "INSERT INTO w_ricuffint (id_ricerca, codein) VALUES (?,?)";
                            entityManager.createNativeQuery(queryToInsert)
                                    .setParameter(1, clonedReport.getIdRicerca())
                                    .setParameter(2, uffInt.getId().getCodein())
                                    .executeUpdate();
                        }
                    }
                }

                //Aggiungo eventuali parametri del report da cui sto partendo per creare il clone.
                List<WRicParam> paramsToClone = wRicParamRepository.findByIdRicerca(reportToDuplicate.get().getIdRicerca());
                if(paramsToClone != null && !paramsToClone.isEmpty()){
                    for(WRicParam param : paramsToClone){

                        String queryToInsert = "INSERT INTO w_ricparam (id_ricerca, progr, codice, nome, descr, tipo, tabcod, obblig, menu) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
                        entityManager.createNativeQuery(queryToInsert)
                                .setParameter(1, clonedReport.getIdRicerca())
                                .setParameter(2, param.getId().getProgressivo())
                                .setParameter(3, param.getCodice())
                                .setParameter(4, param.getNome())
                                .setParameter(5, param.getDescrizione())
                                .setParameter(6, param.getTipo())
                                .setParameter(7, param.getCodiceTabellato())
                                .setParameter(8, param.getObbligatorio())
                                .setParameter(9, param.getMenu())
                                .executeUpdate();
                    }
                }

                clonedReport = wrRepository.save(clonedReport);

                WRicercheDTO result = objectMapper.convertValue(clonedReport, WRicercheDTO.class);

                result.setSysute(utenteCreatore);
                result.setPubblicato(UtilsMethods.mapFieldTwoValues(pubblicato, 0L, "No", 1L, "Sì"));
                result.setIsPreferito(false);

                response.setDone(AppConstants.RESPONSE_DONE_Y);
                response.setResponse(result);

            }
            else {

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_NOT_FOUND_REPORT);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);

                throw ex;
            }

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DUPLICA_REPORT_ERROR);
                logger.error("Si è verificato un errore in fase di duplicaRigaReport del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createDuplicaReportEvent(syscon, ipEvento, COD_APP, idRicerca, idRicercaMax, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::duplicaRigaReport");

        return response;
    }

    /**
     * Generate the new report name searching between reports that have been copied and those that have not.
     *
     * @return the new Report name based on existent or non-existents copies.
     * */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected String generaNuovoNomeReport(String nomeReport) {

        logger.info("START esecuzione in RicercheManager::generaNuovoNomeReport");

        //Due casi:
        // 1) Ho il nome che presenta Copia N o che presenta "Copia di Copia ..."
        // 2) Ho solo il nome e devo aggiungerci il Copia N
        String newNameReport = "";
        try{

            if(!nomeReport.contains("- Copia")){

                int numCopieCount = 1;
                while(wrRepository.existsByNome(nomeReport + " - Copia " + numCopieCount)){
                    numCopieCount++;
                }
                newNameReport =  nomeReport + " - Copia " + numCopieCount;
            }
            else {

                int splitPoint = nomeReport.indexOf(" - Copia");
                if(splitPoint != -1){
                    String soloNome = nomeReport.substring(0, splitPoint);
                    String secondaParte = nomeReport.substring(splitPoint + 3);

                    int numCopieCount = 1;
                    while(wrRepository.existsByNome(soloNome + " - Copia " + numCopieCount + " di " + secondaParte)){
                        numCopieCount++;
                    }
                    newNameReport = soloNome + " - Copia " + numCopieCount + " di " + secondaParte;
                }
            }

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DUPLICA_NOME_REPORT_ERROR);
                logger.error("Errore durante la duplicazione del nome del report avente nome: [{}]", nomeReport);
            }
            throw e;
        }

        logger.info("END esecuzione in RicercheManager::generaNuovoNomeReport");

        return newNameReport;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO deleteRowReport ( Long idRicerca, Long syscon, String ipEvento, String idProfilo ) {

        logger.info("START esecuzione in RicercheManager::deleteRowReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            Optional<WRicerche> reportToDelete = wrRepository.findById(idRicerca);

            //Se il report è presente a DB, procedo con la sua eliminazione
            if(reportToDelete.isPresent()){

                WRicerche reportToConvert = reportToDelete.get();
                WRicercheDTO result = objectMapper.convertValue(reportToConvert, WRicercheDTO.class);
                wrRepository.deleteById(idRicerca);

                //Cancello i parametri del report dalla w_ricparam
                String deleteQueryWRicParam = "DELETE FROM w_ricparam WHERE id_ricerca = ?";
                entityManager.createNativeQuery(deleteQueryWRicParam)
                        .setParameter(1, idRicerca)
                        .executeUpdate();

                //Cancello i record dalla w_cachericpar
                String deleteQueryWCacheRicParam = "DELETE FROM w_cachericpar WHERE id_ricerca = ?";
                entityManager.createNativeQuery(deleteQueryWCacheRicParam)
                        .setParameter(1, idRicerca)
                        .executeUpdate();

                //Cancello i record dalla w_ricuffint
                String deleteQueryWRicUffInt = "DELETE FROM w_ricuffint WHERE id_ricerca = ?";
                entityManager.createNativeQuery(deleteQueryWRicUffInt)
                        .setParameter(1, idRicerca)
                        .executeUpdate();

                //Cancello i record dalla w_ricpro
                String deleteQueryWRicPro = "DELETE FROM w_ricpro WHERE id_ricerca = ?";
                entityManager.createNativeQuery(deleteQueryWRicPro)
                        .setParameter(1, idRicerca)
                        .executeUpdate();

                //Cancello i record dalla w_preferiti legati a syscon e idRicerca
                String deleteQueryWPreferiti = "DELETE FROM w_preferiti WHERE syscon = ? AND key1 = ? AND ent = ?";
                entityManager.createNativeQuery(deleteQueryWPreferiti)
                        .setParameter(1, syscon)
                        .setParameter(2, String.valueOf(idRicerca))
                        .setParameter(3, "W_RICERCHE")
                        .executeUpdate();

                response.setDone(AppConstants.RESPONSE_DONE_Y);
                response.setResponse(result);
            }
            //Non si dovrebbe mai entrare qui!!
            else {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DELETE_REPORT_NOT_FOUND);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DELETE_REPORT_ERROR);
                logger.error("Si è verificato un errore in fase di deleteRowReport del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createDeleteReportEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::deleteRowReport");

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO insertNewParamReport (WRicParamDTO form, String ipEvento, Long syscon, String idProfilo) {

        logger.info("START esecuzione in RicercheManager::insertNewParamReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        Long progressivoMax;

        String[] menuValues = new String[0];
        try {

            //Controllo se mi arriva come tipo parametro il "Menu". Va controllata la validità ovvero le scelte devono essere suddivise da un |
            if(StringUtils.equals(form.getTipo(), "M")){

                if(!form.getMenuField().contains("|")){
                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_PIPE_NOT_FOUND_MENU_PARAM_ERROR);

                    throw ex;
                }
                else{
                    menuValues = form.getMenuField().split("\\|");
                }
            }

            WRicParamPK nuovoParamId = new WRicParamPK();

            //Calcolo campo "Progressivo" per l'ordinamento dei parametri in tabella
            progressivoMax = wRicParamRepository.getMaxProgr(form.getIdRicerca());
            progressivoMax = progressivoMax == null || progressivoMax == 0L ? 1L : progressivoMax + 1;

            //Imposto idRicerca e Progressivo
            nuovoParamId.setIdRicerca(form.getIdRicerca());
            nuovoParamId.setProgressivo(progressivoMax);

            Optional<WRicParam> paramCheckPresence = wRicParamRepository.findById(nuovoParamId);

            //Controllo se il parametro è presente per il report xx.
            //Se non c'è, lo creo e lo inserisco.
            if(paramCheckPresence.isEmpty()){

                WRicParam nuovoParam = new WRicParam();

                nuovoParam.setId(nuovoParamId);
                //Controllo per verificare che non esista già un parametro con quel codice.
                if(!wRicParamRepository.existsByCodiceReport(form.getCodice(), form.getIdRicerca())){
                    nuovoParam.setNome(form.getNome());
                }
                else{
                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_INSERT_NEW_PARAM_ALREADY_PRESENT);

                    throw ex;
                }
                nuovoParam.setCodice(form.getCodice());
                nuovoParam.setDescrizione(form.getDescrizione());
                nuovoParam.setCodiceTabellato(AppConstants.TAB3_TIPO_PARAMETRO_REPORT);
                nuovoParam.setTipo(form.getTipo());
                nuovoParam.setObbligatorio(form.getObbligatorio() == null ? 0L : Long.parseLong(form.getObbligatorio()));
                if(menuValues.length > 0){
                    nuovoParam.setMenu(form.getMenuField());
                }

                nuovoParam = wRicParamRepository.save(nuovoParam);

                WRicParamDTO result = objectMapper.convertValue(nuovoParam, WRicParamDTO.class);
                result.setIdRicerca(form.getIdRicerca());
                result.setProgressivo(progressivoMax);
                result.setMenuField(form.getMenuField());

                response.setDone(AppConstants.RESPONSE_DONE_Y);
                response.setResponse(result);

            }
            else {

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_INSERT_NEW_PARAM_ALREADY_PRESENT);
                logger.error("Parametro già presente per idRicerca: [{}]", form.getIdRicerca());

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_INSERT_NEW_PARAM_ERROR);
                logger.error("Si è verificato un errore in fase di insertNewParamReport del report con idRicerca: [{}]", form.getIdRicerca());
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createInsertParamReportEvent(syscon, form.getCodice(), progressivoMax, ipEvento, COD_APP, form.getIdRicerca(), idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::insertNewParamReport");

        return response;
    }

    public ResponseListaDTO getDetailParamReport ( Long idRicerca, String codice ) {

        logger.info("START esecuzione in RicercheManager::getDetailParamReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            Optional<WRicParam> paramDetail = wRicParamRepository.findByIdRicercaAndCodice(idRicerca, codice);
            if(paramDetail.isPresent()) {

                Long idRicercaParam = paramDetail.get().getId().getIdRicerca();
                Long progressivoParam = paramDetail.get().getId().getProgressivo();
                Long obbligatorioParam = paramDetail.get().getObbligatorio();

                //Mappo l'istanza e la ritorno semplicemente. Provo solo a mapparla, non dovrebbero esserci problemi
                WRicParamDTO result = objectMapper.convertValue(paramDetail.get(), WRicParamDTO.class);
                result.setIdRicerca(idRicercaParam);
                result.setProgressivo(progressivoParam);
                result.setObbligatorio(UtilsMethods.mapFieldTwoValues(obbligatorioParam, 0L, "0", 1L, "1"));
                result.setMenuField(paramDetail.get().getMenu());

                response.setDone(AppConstants.RESPONSE_DONE_Y);
                response.setResponse(result);
            }
            else {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_NOT_FOUND);
                logger.error("Parametro non presente a DB per idRicerca [{}] e codice [{}]", idRicerca, codice);

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_ERROR);
                logger.error("Si è verificato un errore in fase di getDetailParamReport del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        logger.info("END esecuzione in RicercheManager::getDetailParamReport");

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO updateDettaglioParametroReport ( WRicParamDTO form, Long syscon, String ipEvento, String idProfilo ) {

        logger.info("START esecuzione in RicercheManager::updateDettaglioParametroReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            WRicParam paramDetailToUpdate = wRicParamRepository.findByIdRicercaAndCodice(form.getIdRicerca(), form.getCodice()).orElse(null);

            //Controllo presenza del report a DB. Non si dovrebbe mai entrare qui!!
            if(paramDetailToUpdate == null) {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_NOT_FOUND);
                logger.error("Parametro non presente a DB per report con idRicerca: [{}] e codice: [{}]", form.getIdRicerca(), form.getCodice());

                throw ex;
            }

            //Aggiorno nome e descrizione del report controllando se sono presenti nel form passato.
            if(!StringUtils.isEmpty(form.getNome())){
                paramDetailToUpdate.setNome(form.getNome());
            }

            paramDetailToUpdate.setDescrizione(!StringUtils.isEmpty(form.getDescrizione()) ? form.getDescrizione() : null);

            //Controllo se il campo Obbligatorio sia stato modificato
            if(paramDetailToUpdate.getObbligatorio() != null && !Objects.equals(form.getObbligatorio(), paramDetailToUpdate.getObbligatorio().toString())){
                paramDetailToUpdate.setObbligatorio(Long.valueOf(UtilsMethods.mapFieldTwoValues(form.getObbligatorio(), "0", 0L, "1", 1L)));
            }

            paramDetailToUpdate.setTipo(form.getTipo());
            paramDetailToUpdate.setMenu(form.getMenuField());

            Long idRicercaParam = paramDetailToUpdate.getId().getIdRicerca();
            Long progressivoParam = paramDetailToUpdate.getId().getProgressivo();
            Long obbligatorioParam = paramDetailToUpdate.getObbligatorio() == null ? 0L : paramDetailToUpdate.getObbligatorio();

            paramDetailToUpdate = wRicParamRepository.save(paramDetailToUpdate);

            WRicParamDTO result = objectMapper.convertValue(paramDetailToUpdate, WRicParamDTO.class);
            result.setIdRicerca(idRicercaParam);
            result.setProgressivo(progressivoParam);
            result.setObbligatorio(UtilsMethods.mapFieldTwoValues(obbligatorioParam, 0L, "No", 1L, "Si"));
            result.setTipo(form.getTipo());
            result.setMenuField(form.getMenuField());

            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setResponse(result);

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_UPDATE_ERROR_NEW_PARAM_REPORT);
                logger.error("Si è verificato un errore in fase di updateDettaglioParametroReport del parametro con codice: [{}] del report con idRicerca: [{}]", form.getCodice(), form.getIdRicerca());
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createUpdateParamReportEvent(syscon, ipEvento, COD_APP, form.getIdRicerca(), form.getCodice(), idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::updateDettaglioParametroReport");

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteParamRowReport ( Long idRicerca, String codice, String ipEvento, Long syscon, String idProfilo ) {

        logger.info("START esecuzione in RicercheManager::deleteParamRowReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            //L'idea alla base del metodo è quella di ovviare un problema che altrimenti non sarebbe facilmente risolvibile con jpa o hibernate.
            //Al posto di eliminare la riga corrispondente e operare uno shift di tutti i progressivi all'indietro di 1, sovrascrivo tutti i dati del
            //record in posizione (N + 1) nella posizione (N) senza toccare gli id. In questo modo vado avanti finché non avrò le ultime
            //due righe uguali e potrò eliminare l'ultima in quanto l'unica con i dati duplicati.
            //Durante la duplicazione delle righe verso il basso, viene a mancare il vincolo di unicità "un_ricparam_codice".
            // Per questo motivo viene inizialmente droppato il vincolo.
            // Durante l'esecuzione mi assicuro che non vi siano chiavi duplicate ed infine riapplico il vincolo per riportare tutto allo stato iniziale.

            int progressivo = 0;
            Optional<WRicParam> reportToDelete = wRicParamRepository.findByIdRicercaAndCodice(idRicerca, codice);

            if(reportToDelete.isPresent()){

                //Rimuovo TEMPORANEAMENTE il vincolo di unicità dalla tabella. Essendo in una transazione, le operazioni effettuate all'interno non possono essere interrotte
                //o modificate da procedure esterne.
                entityManager.createNativeQuery("ALTER TABLE w_ricparam DROP CONSTRAINT un_ricparam_codice").executeUpdate();

                while(wRicParamRepository.existsByIdRicercaAndProgressivo(idRicerca, reportToDelete.get().getId().getProgressivo() + progressivo)){

                    Optional<WRicParam> paramN = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, reportToDelete.get().getId().getProgressivo() + progressivo); //progressivo già a progressivoToDelete.
                    Optional<WRicParam> paramN1 = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, reportToDelete.get().getId().getProgressivo() + progressivo + 1); //progressivo del successivo al progressivoToDelete.

                    //Sovrascrivo i risultati di paramN1 in paramN
                    if(paramN.isPresent() && paramN1.isPresent()){
                        paramN.get().setTipo(paramN1.get().getTipo());
                        paramN.get().setCodice(paramN1.get().getCodice());
                        paramN.get().setNome(paramN1.get().getNome());
                        paramN.get().setDescrizione(paramN1.get().getDescrizione());
                        paramN.get().setObbligatorio(paramN1.get().getObbligatorio());
                        paramN.get().setCodiceTabellato(paramN1.get().getCodiceTabellato());
                    }

                    progressivo += 1;
                }

                //Opero la cancellazione nel momento subito prima di creare un duplicato così da evitare la violazione del vincolo di integrità.
                //Procedo ad eliminare l'ultima riga che risulta duplicata
                Optional<WRicParam> paramToDelete = wRicParamRepository.findByIdRicercaAndMaxProgressivo(idRicerca);
                paramToDelete.ifPresent(wRicParam -> entityManager.remove(wRicParam));

                //Prima di eseguire il flush dell'operazione mi assicuro di rimettere il vincolo di unicità per idRicerca e codice.
                entityManager.createNativeQuery("ALTER TABLE w_ricparam ADD CONSTRAINT un_ricparam_codice UNIQUE (id_ricerca, codice)").executeUpdate();
                entityManager.flush();
            }
            else {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_NOT_FOUND);
                logger.error("Parametro non presente a DB per idRicerca [ {} ] e codice [ {} ]", idRicerca, codice);

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DELETE_PARAM_REPORT);
                logger.error("Si è verificato un errore in fase di cancellazione del parametro del report con idRicerca: [{}] e codice: [{}]",idRicerca, codice);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createDeleteParamReportEvent(syscon, ipEvento, COD_APP, idRicerca, codice, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::deleteParamRowReport");
    }

    @Transactional(rollbackFor=Exception.class)
    public void moveParamUp ( Long idRicerca, String codice ) {

        logger.info("START esecuzione in RicercheManager::moveParamUp");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            Optional<WRicParam> toShift = wRicParamRepository.findByIdRicercaAndCodice(idRicerca, codice);
            //Bisogna invertire la riga del parametro corrispondente a (idRicerca, progressivo) con quella "superiore" (progressivo - 1)
            //Nel caso in cui il progressivo del parametro corrente sia 1, non ho nulla da spostare. -> Nessun errore.
            if(toShift.isEmpty() || toShift.get().getId().getProgressivo() == 1){
                return;
            }

            Optional<WRicParam> reportCurrent = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, toShift.get().getId().getProgressivo());
            Optional<WRicParam> reportUp = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, toShift.get().getId().getProgressivo() - 1);

            WRicParam reportCurrentCopy = new WRicParam();

            if(reportCurrent.isPresent() && reportUp.isPresent()){

                //Rimuovo TEMPORANEAMENTE il vincolo di unicità dalla tabella. Essendo in una transazione, le operazioni effettuate all'interno non possono essere interrotte
                //o modificate da procedure esterne.
                entityManager.createNativeQuery("ALTER TABLE w_ricparam DROP CONSTRAINT un_ricparam_codice").executeUpdate();

                //Scrivo i risultati da reportUp a reportCurrentCopy
                reportCurrentCopy.setTipo(reportUp.get().getTipo());
                reportCurrentCopy.setCodice(reportUp.get().getCodice());
                reportCurrentCopy.setNome(reportUp.get().getNome());
                reportCurrentCopy.setDescrizione(reportUp.get().getDescrizione());
                reportCurrentCopy.setObbligatorio(reportUp.get().getObbligatorio());
                reportCurrentCopy.setCodiceTabellato(reportUp.get().getCodiceTabellato());

                //Sovrascrivo i risultati da reportCurrent a reportUp
                reportUp.get().setTipo(reportCurrent.get().getTipo());
                reportUp.get().setCodice(reportCurrent.get().getCodice());
                reportUp.get().setNome(reportCurrent.get().getNome());
                reportUp.get().setDescrizione(reportCurrent.get().getDescrizione());
                reportUp.get().setObbligatorio(reportCurrent.get().getObbligatorio());
                reportUp.get().setCodiceTabellato(reportCurrent.get().getCodiceTabellato());

                //Sovrascrivo i risultati da reportCurrentCopy a reportCurrent
                reportCurrent.get().setTipo(reportCurrentCopy.getTipo());
                reportCurrent.get().setCodice(reportCurrentCopy.getCodice());
                reportCurrent.get().setNome(reportCurrentCopy.getNome());
                reportCurrent.get().setDescrizione(reportCurrentCopy.getDescrizione());
                reportCurrent.get().setObbligatorio(reportCurrentCopy.getObbligatorio());
                reportCurrent.get().setCodiceTabellato(reportCurrentCopy.getCodiceTabellato());

                //Prima di eseguire il flush dell'operazione mi assicuro di rimettere il vincolo di unicità per idRicerca e codice.
                entityManager.createNativeQuery("ALTER TABLE w_ricparam ADD CONSTRAINT un_ricparam_codice UNIQUE (id_ricerca, codice)").executeUpdate();
                entityManager.flush();
            }
            else {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_NOT_FOUND);
                logger.error("Parametro non presente a DB per idRicerca [ {} ] e codice [ {} ]", idRicerca, toShift.get().getId().getProgressivo());

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_MOVE_UP_PARAM_REPORT);
                logger.error("Si è verificato un errore in fase di spostamento dei parametri del report con idRicerca: [{}] e codice: [{}]", idRicerca, codice);
            }
            throw e;
        }

        logger.info("END esecuzione in RicercheManager::moveParamUp");
    }

    @Transactional(rollbackFor=Exception.class)
    public void moveParamDown ( Long idRicerca, String codice ) {

        logger.info("START esecuzione in RicercheManager::moveParamDown");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            //Bisogna invertire la riga del parametro corrispondente a (idRicerca, progressivo) con quella "inferiore" (progressivo + 1)
            //Nel caso in cui il progressivo del parametro corrente sia vuoto, ritorno.
            Optional<WRicParam> toShift = wRicParamRepository.findByIdRicercaAndCodice(idRicerca, codice);

            if(toShift.isEmpty()){
                return;
            }
            else{
                Optional<WRicParam> nextToShiftBack = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, toShift.get().getId().getProgressivo());
                //Caso base in cui non serve fare lo swap in quanto sto provando a scambiare la riga corrente con una riga inesistente.
                if(nextToShiftBack.isEmpty()){
                    return;
                }
            }

            Optional<WRicParam> reportCurrent = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, toShift.get().getId().getProgressivo());
            Optional<WRicParam> reportUp = wRicParamRepository.findByIdRicercaAndProgressivo(idRicerca, toShift.get().getId().getProgressivo() + 1);

            WRicParam reportCurrentCopy = new WRicParam();

            if(reportCurrent.isPresent() && reportUp.isPresent()){

                //Rimuovo TEMPORANEAMENTE il vincolo di unicità dalla tabella. Essendo in una transazione, le operazioni effettuate all'interno non possono essere interrotte
                //o modificate da procedure esterne.
                entityManager.createNativeQuery("ALTER TABLE w_ricparam DROP CONSTRAINT un_ricparam_codice").executeUpdate();

                //Scrivo i risultati da reportUp a reportCurrentCopy
                reportCurrentCopy.setTipo(reportUp.get().getTipo());
                reportCurrentCopy.setCodice(reportUp.get().getCodice());
                reportCurrentCopy.setNome(reportUp.get().getNome());
                reportCurrentCopy.setDescrizione(reportUp.get().getDescrizione());
                reportCurrentCopy.setObbligatorio(reportUp.get().getObbligatorio());
                reportCurrentCopy.setCodiceTabellato(reportUp.get().getCodiceTabellato());

                //Sovrascrivo i risultati da reportCurrent a reportUp
                reportUp.get().setTipo(reportCurrent.get().getTipo());
                reportUp.get().setCodice(reportCurrent.get().getCodice());
                reportUp.get().setNome(reportCurrent.get().getNome());
                reportUp.get().setDescrizione(reportCurrent.get().getDescrizione());
                reportUp.get().setObbligatorio(reportCurrent.get().getObbligatorio());
                reportUp.get().setCodiceTabellato(reportCurrent.get().getCodiceTabellato());

                //Sovrascrivo i risultati da reportCurrentCopy a reportCurrent
                reportCurrent.get().setTipo(reportCurrentCopy.getTipo());
                reportCurrent.get().setCodice(reportCurrentCopy.getCodice());
                reportCurrent.get().setNome(reportCurrentCopy.getNome());
                reportCurrent.get().setDescrizione(reportCurrentCopy.getDescrizione());
                reportCurrent.get().setObbligatorio(reportCurrentCopy.getObbligatorio());
                reportCurrent.get().setCodiceTabellato(reportCurrentCopy.getCodiceTabellato());

                //Prima di eseguire il flush dell'operazione mi assicuro di rimettere il vincolo di unicità per idRicerca e codice.
                entityManager.createNativeQuery("ALTER TABLE w_ricparam ADD CONSTRAINT un_ricparam_codice UNIQUE (id_ricerca, codice)").executeUpdate();
                entityManager.flush();
            }
            else {

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_PARAM_REPORT_NOT_FOUND);
                logger.error("Parametro non presente a DB per idRicerca [ {} ] e codice [ {} ]", idRicerca, toShift.get().getId().getProgressivo());

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_MOVE_DOWN_PARAM_REPORT);
                logger.error("Si è verificato un errore in fase di spostamento dei parametri del report con idRicerca: [{}] e codice: [{}]", idRicerca, codice);
            }
            throw e;
        }

        logger.info("END esecuzione in RicercheManager::moveParamDown");
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor=Exception.class)
    public ResponseListaDTO executeReportWithParams(Long idRicerca, GenericDynamicFormValueDTO form, Long syscon, String ipEvento, String idProfilo, Boolean isExternalApi){

        logger.info("START esecuzione in RicercheManager::executeReportWithParams");

        ResponseListaDTO response = new ResponseListaDTO();
        ResultQueryExecutionFormDTO resultQueryExecutionFormDTO = new ResultQueryExecutionFormDTO();
        List<Map<String, Object>> resultMappedWithColumnNames = new ArrayList<>();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        String finalCleanedQuery = "";

        try{

            //Recupero i parametri inseriti dall'utente
            Map<String, Map<String, Object>> paramsList = (Map<String, Map<String, Object>>) form.getDynamicFormValueDTO();
            List<WRicParamDTO> allParams = form.getAllParams();

            //Creo mappa per parametri obbligatori e per parametri non obbligatori.
            Map<String, Object> paramObbligatori = new HashMap<>();
            Map<String, Object> paramFacoltativi = new HashMap<>();

            //For annidati per popolare le due mappe di paramObbligatori e paramFacoltativi.
            for(WRicParamDTO paramDTO : allParams){
                for(Map.Entry<String, Map<String, Object>> param : paramsList.entrySet()){

                    String codiceKey = param.getKey();
                    Map<String, Object> valuesMap = param.getValue();

                    for(Map.Entry<String, Object> value : valuesMap.entrySet()){

                        String tipoParam = value.getKey();
                        Object valueParam = value.getValue();

                        if(StringUtils.equals(paramDTO.getCodice(), param.getKey())){

                            if(StringUtils.equals(paramDTO.getObbligatorio(), "Si")){

                                if(StringUtils.equals(tipoParam, "I") || StringUtils.equals(tipoParam, "F") || StringUtils.equals(tipoParam, "UC") || StringUtils.equals(tipoParam, "M")){

                                    paramObbligatori.put(codiceKey, Long.parseLong(valueParam.toString()));
                                }
                                else if(StringUtils.equals(tipoParam, "D")){
                                    if(valueParam == null){
                                        paramObbligatori.put(codiceKey, null);
                                    }
                                    else {
                                        try {
                                            //Imposto il timestamp da passare al DB nel momento in cui viene eseguita la query.
                                            Timestamp dataParam = UtilsMethods.parseAndFormatDate(valueParam.toString());

                                            if(StringUtils.isEmpty(dataParam.toString())){
                                                paramObbligatori.put(codiceKey, null);
                                            }
                                            else{
                                                paramObbligatori.put(codiceKey, dataParam);
                                            }
                                        }
                                        catch (GenericReportOperationException e){
                                            logger.error("Errore durante il parsing della data ricevuta in input: [{}]", valueParam.toString());
                                            throw new DateTimeParseException(AppConstants.GESTIONE_REPORTS_ERROR_DATE_PARSING, valueParam.toString(), 0, e);
                                        }
                                    }

                                }
                                else{
                                    paramObbligatori.put(codiceKey,  valueParam);
                                }
                            }
                            else{

                                if(StringUtils.equals(tipoParam, "I") || StringUtils.equals(tipoParam, "F") || StringUtils.equals(tipoParam, "UC") || StringUtils.equals(tipoParam, "M")){

                                    if(valueParam == null){

                                        paramFacoltativi.put(codiceKey, null);
                                    }
                                    else if(StringUtils.isEmpty(valueParam.toString())){

                                        paramFacoltativi.put(codiceKey, null);
                                    }
                                    else{

                                        paramFacoltativi.put(codiceKey, Long.parseLong(valueParam.toString()));
                                    }
                                }
                                else if(StringUtils.equals(tipoParam, "D")){
                                    if(valueParam == null){
                                        paramFacoltativi.put(codiceKey, null);
                                    }
                                    else {
                                        try {
                                            //Imposto il timestamp da passare al DB nel momento in cui viene eseguita la query.
                                            Timestamp dataParam = UtilsMethods.parseAndFormatDate(valueParam.toString());

                                            if(StringUtils.isEmpty(dataParam.toString())){
                                                paramFacoltativi.put(codiceKey, null);
                                            }
                                            else{
                                                paramFacoltativi.put(codiceKey, dataParam);
                                            }
                                        }
                                        catch (GenericReportOperationException e){
                                            logger.error("Errore durante il parsing della data ricevuta in input: [{}]", valueParam);
                                            assert valueParam != null;
                                            throw new DateTimeParseException(AppConstants.GESTIONE_REPORTS_ERROR_DATE_PARSING, (CharSequence) valueParam, 0, e);
                                        }
                                    }
                                }
                                else{
                                    if(valueParam == null){
                                        paramFacoltativi.put(codiceKey, null);
                                    }
                                    else if(StringUtils.isEmpty(valueParam.toString())){
                                        paramFacoltativi.put(codiceKey, null);
                                    }
                                    else{
                                        paramFacoltativi.put(codiceKey, valueParam);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Controllo della defSql nel caso in cui non contenga l'operazione di SELECT.
            //Tolgo i caratteri vuoti prima del primo carattere e poi controllo la presenza della stringa "SELECT". Altrimenti aborto l'operazione.
            //Non solo, se sono presenti operazioni di modifica di una tabella (Vedere AppConstants per le costanti inserite), abortire l'esecuzione.
            //Fare parsing della defSql nel caso in cui sia presente #value#
            //I cancelletti danno indicazione del fatto che sia presente un parametro da inserire.
            //Se sono presenti a gruppi, 2 cancelletti identificano un parametro.

            //Trovo il report da eseguire per estrarre la sua defSql
            Optional<WRicerche> reportToExecute = wrRepository.findById(idRicerca);

            //Non si dovrebbe mai entrare qui!!
            if(reportToExecute.isEmpty()){
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DETAIL_NOT_FOUND_REPORT);
                logger.error("Report non trovato per idRicerca: [{}]", idRicerca);

                throw ex;
            }

            //Controllo che la defSql non sia vuota. Caso possibile alla creazione da zero (manuale) del report se l'utente
            // lo esegue subito senza aver definito l'SQL per il report.
            if (reportToExecute.get().getDefSql() == null || reportToExecute.get().getDefSql().isEmpty() || reportToExecute.get().getDefSql().isBlank()) {

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DEFSQL_NOT_VALID);
                logger.error("Definizione SQL vuota per il report con idRicerca: [{}]", idRicerca);

                throw ex;
            }

            String defSql = reportToExecute.get().getDefSql().trim();
            String cleanedQuery = UtilsMethods.removeCommentsAndSeparators(defSql);

            //Controllo sulla sicurezza della defSql scritta dall'utente
            if(!UtilsMethods.checkMaliciousInputQuery(cleanedQuery)){

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DEFSQL_OPERATION_NOT_ALLOWED);
                logger.error("Definizione SQL sospetta. Esecuzione report annullato. DEFSQL: [{}]", cleanedQuery);

                throw ex;
            }

            List<String> allParamsDefSQL = new ArrayList<>();
            Map<Integer, Object> paramsToPreparedStatement = new LinkedHashMap<>();
            Map<String, Object> paramsToShowUser = new HashMap<>();
            int countParamsOccurences = 1;
            Pattern pattern = Pattern.compile("#(.*?)#");
            Matcher matcher = pattern.matcher(cleanedQuery);
            //Trovo tutte le occorrenze delle stringhe comprese tra ##, se presenti.
            while (matcher.find()) {
                allParamsDefSQL.add(matcher.group(1));
            }

            //Inizio controlli parametri obbligatori e sostituzione
            for(Map.Entry<String, Object> paramObbligatorio : paramObbligatori.entrySet()){

                String paramObbligKey = paramObbligatorio.getKey();
                Object paramObblValue = paramObbligatorio.getValue();

                String toFind = "#" + paramObbligKey + "#";

                if(allParamsDefSQL.contains(paramObbligKey)){

                    //Per formattare direttamente i parametri da mostrare all'utente nella pagina di risultato, solo il tipo "Date"
                    // va parsato nuovamente per passare da timestamp a SimpleDateFormat sottoforma di stringa.
                    if (paramObblValue instanceof Timestamp) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                        String formattedDate = sdf.format((Timestamp) paramObblValue);
                        paramsToShowUser.put(paramObbligKey, formattedDate);
                    }
                    else{
                        paramsToShowUser.put(paramObbligKey, paramObblValue);
                    }

                    paramsToPreparedStatement.put(countParamsOccurences, paramObbligatori.get(paramObbligKey));
                    while(cleanedQuery.contains(toFind)){
                        cleanedQuery = cleanedQuery.replace(toFind, "?" + countParamsOccurences);
                    }
                    countParamsOccurences++;
                }
            }

            //Inizio controlli parametri facoltativi e sostituzione
            for(Map.Entry<String, Object> paramFacoltativo: paramFacoltativi.entrySet()){

                String paramFacoltativoKey = paramFacoltativo.getKey();
                Object paramFacoltativoValue = paramFacoltativo.getValue();

                String toFind = "#" + paramFacoltativoKey + "#";

                if(allParamsDefSQL.contains(paramFacoltativoKey)){

                    paramsToShowUser.put(paramFacoltativoKey, paramFacoltativoValue);
                    paramsToPreparedStatement.put(countParamsOccurences, paramFacoltativi.get(paramFacoltativoKey));
                    while(cleanedQuery.contains(toFind)){
                        cleanedQuery = cleanedQuery.replace(toFind, "?" + countParamsOccurences);
                    }
                    countParamsOccurences++;
                }
            }

            resultQueryExecutionFormDTO.setParamsToShowUser(paramsToShowUser);

            //Controllo che non siano rimasti parametri non sostituiti all'interno della query
            Pattern patternCheck = Pattern.compile("#(.*?)#");
            Matcher matcherCheck = patternCheck.matcher(cleanedQuery);
            //se ci sono ancora occorrenze non sostituite in query allora c'è qualcosa che non va e ritorno errore.
            if(matcherCheck.find()){

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_PARAM_NOT_SUBSTITUTED);
                logger.error("Parametri non sostituiti in query. DEFSQL: [{}]", cleanedQuery);

                throw ex;
            }

            // Creare una mappa per tenere traccia delle posizioni dei parametri
            Map<Integer, List<Integer>> paramPositions = new HashMap<>();
            Pattern pattern1 = Pattern.compile("\\?(\\d+)");
            Matcher matcher1 = pattern1.matcher(cleanedQuery);
            int position = 0;
            while (matcher1.find()) {
                int paramNumber = Integer.parseInt(matcher1.group(1));
                paramPositions.computeIfAbsent(paramNumber, k -> new ArrayList<>()).add(position);
                position++;
            }

            // Creo un array di parametri ordinati basato sulle posizioni trovate
            Object[] orderedParams = new Object[position];
            int[] types = new int[position];
            for (Map.Entry<Integer, List<Integer>> entry : paramPositions.entrySet()) {
                Object paramValue = paramsToPreparedStatement.get(entry.getKey());
                for (Integer pos : entry.getValue()) {
                    orderedParams[pos] = paramValue;
                    types[pos] = SqlTypeValue.TYPE_UNKNOWN;
                }
            }

            // Modifico la query sostituendo i parametri numerati con "?" semplici
            String jdbcQuery = cleanedQuery.replaceAll("\\?\\d+", "?");
            String finalQuery = jdbcQuery;
            resultQueryExecutionFormDTO.setQueryReplacedWithParams(finalQuery);

            try {
                //Esecuzione query
                resultMappedWithColumnNames = jdbcTemplate.query(
                        jdbcQuery,
                        orderedParams,
                        types,
                        (ResultSet rs, int rowNum) -> {
                            Map<String, Object> row = new LinkedHashMap<>();
                            ResultSetMetaData metaData = rs.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            for(int i = 1; i <= columnCount; i++){
                                String columnName = metaData.getColumnLabel(i);
                                Object columnValue = rs.getObject(i);
                                row.put(columnName, columnValue);
                            }
                            return row;
                        }
                );
            } catch (Throwable e){

                logger.error("Errore durante l'esecuzione del report con idRicerca: [{}]", idRicerca, e);
                String errorMessage = AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_QUERY;
                String detailedErrorQuery = e.getMessage();

                throw new GenericReportOperationException(errorMessage, detailedErrorQuery);
            }

            try {
                //Rework completo dell'esecuzione della query per controllare i metadati delle colonne al fine di capire se siamo in presenza di un tipo "bytea".
                //Viene utilizzato jdbcTemplate.
                final List<Map<String, Object>> finalResultMappedWithColumnNames = new ArrayList<>(resultMappedWithColumnNames);

                jdbcTemplate.query(jdbcQuery, orderedParams, types, (ResultSet rs) -> {
                    if(rs.next()) { //Processo solo la prima riga
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        // Creo una mappa di metadati
                        Map<String, String> columnTypes = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnLabel(i);
                            String columnTypeName = metaData.getColumnTypeName(i);
                            columnTypes.put(columnName, columnTypeName);
                        }

                        // Aggiorno i valori basandomi sui metadati della mappa columnTypes.
                        for (Map<String, Object> map : finalResultMappedWithColumnNames) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                String columnName = entry.getKey();
                                String columnTypeName = columnTypes.get(columnName);
                                if (StringUtils.equalsIgnoreCase(columnTypeName, "bytea") && !isExternalApi) {
                                    entry.setValue("BYTE");
                                }
                                //Caso in cui a chiamare il metodo è il servizio API esposto.
                                //In questo caso ogni colonna di tipo bytea viene convertita in Base64.
                                else if(StringUtils.equalsIgnoreCase(columnTypeName, "bytea") && isExternalApi) {
                                    byte[] byteaData = (byte[]) entry.getValue();
                                    String base64Data = Base64.getEncoder().encodeToString(byteaData);
                                    entry.setValue(base64Data);
                                }
                            }
                        }
                    }
                    return null;
                });

                // Aggiorno resultMappedWithColumnNames dopo la modifica
                resultMappedWithColumnNames = new ArrayList<>(finalResultMappedWithColumnNames);

            } catch (Exception e) {
                logger.error("Errore durante il controllo dei dati per colonne di tipo bytea per il report con idRicerca: [{}]", idRicerca, e);
                throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_QUERY);
            }

            //Salvo i nomi delle colonne e dei valori in due strutture dati d'appoggio che popoleranno il form da mandare al frontend.
            List<String> columnNames = new ArrayList<>();

            if(!resultMappedWithColumnNames.isEmpty()){
                //Popolo la struttura dati per i soli nomi delle colonne
                for(Map.Entry<String, Object> entry : resultMappedWithColumnNames.get(0).entrySet()){

                    columnNames.add(entry.getKey());
                }
            }

            //Popolo il form finale da ritornare
            resultQueryExecutionFormDTO.setValues(resultMappedWithColumnNames);
            resultQueryExecutionFormDTO.setColumnNames(columnNames);

            response.setResponse(resultQueryExecutionFormDTO);
            response.setTotalCount((long) resultMappedWithColumnNames.size());

            //Controllo per numero massimo di righe. Nel caso in cui superi il numero impostato
            //in config 'it.eldasoft.generatoreRicerche.maxNumRecord', non verrà ritornato alcun risultato.
            String maxNumRecord = wConfigRepository.getConfigurazione(COD_APP, AppConstants.W_CONFIG_MAX_NUM_RECORD);
            response.setMaxNumRecordConfig(Long.parseLong(maxNumRecord));
            if(response.getTotalCount() != null && !StringUtils.isEmpty(maxNumRecord) && response.getTotalCount() > Long.parseLong(maxNumRecord)) {

                resultQueryExecutionFormDTO.setValues(null);
                resultQueryExecutionFormDTO.setColumnNames(null);

                response.setResponse(null);
                response.setTotalCount(0L);

                if(!response.getMessages().isEmpty()){
                    response.getMessages().add(AppConstants.GESTIONE_REPORTS_MAX_NUM_RECORDS_REACHED);
                }
                else {
                    List<String> messages = new ArrayList<>();
                    messages.add(AppConstants.GESTIONE_REPORTS_MAX_NUM_RECORDS_REACHED);
                    response.setMessages(messages);
                }
            }

            response.setDone(AppConstants.RESPONSE_DONE_Y);

            if(!StringUtils.isEmpty(finalCleanedQuery)){
                finalCleanedQuery = cleanedQuery;
            }

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_QUERY);
                logger.error("Si è verificato un errore in fase di esecuzione del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        if(idProfilo != null || syscon != null){
            WLogEventiDTO wLogEventiDTO = logEventiUtils.createExecuteReportWithParamsEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo, finalCleanedQuery);
            logEventiUtils.insertLogEvent(wLogEventiDTO);
        }

        logger.info("END esecuzione in RicercheManager::executeReportWithParams");

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO insertNewReport (WRicercheDTO form, Long syscon, String ipEvento, String idProfilo, UserDTO currentUserDTO) {

        logger.info("START esecuzione in RicercheManager::insertNewReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        WRicerche newReport = new WRicerche();

        try {

            //Genero idRicerca nuovo dalla WGenChiavi.
            Long progressivoMax = logEventiUtils.getNextId("W_RICERCHE");

            if(form == null){
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_NULL_FORM);
                logger.error("Il form con i dati da inserire nel nuovo report è nullo.");

                throw ex;
            }

            Optional<User> utente = userRepository.findUserBySyscon(syscon);
            if(utente.isEmpty()){

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_INSERT_NEW_REPORT_USER_NOT_FOUND);
                logger.error("utente non presente per syscon: [{}]", syscon);

                throw ex;
            }

            newReport.setIdRicerca(progressivoMax);

            //Controllo univocità nome report.
            Long univocitaNomeReport = wrRepository.countByNome(form.getNome());
            if(univocitaNomeReport == 0) {
                newReport.setNome(form.getNome());
            }
            else{
                //Nessuna eccezione.
                //Esiste già un report con questo nome.
                List<String> messages = new ArrayList<>();
                messages.add(AppConstants.GESTIONE_REPORTS_ERROR_NEW_NAME_REPORT_DUPLICATO);
                //response.setDone(AppConstants.RESPONSE_DONE_Y);
                response.setMessages(messages);
                return response;
            }

            newReport.setDefSql(null);
            newReport.setRisperpag(50L);
            newReport.setTipo(String.valueOf(0L));
            newReport.setEntprinc(null);
            newReport.setPersonale(0L);
            newReport.setVismodelli(0L);
            newReport.setDescrizione(!StringUtils.isEmpty(form.getDescrizione()) ? form.getDescrizione() : "");
            newReport.setTuttiUffici(form.getTuttiUffici() != null ? Long.parseLong(form.getTuttiUffici()) : 0L);
            newReport.setTuttiProfili(form.getTuttiProfili() != null ? Long.parseLong(form.getTuttiProfili()) : 0L);
            newReport.setPubblicato(form.getPubblicato() != null ? Long.parseLong(form.getPubblicato()) : 0L);
            newReport.setFamiglia(4L);
            newReport.setCodApp(COD_APP);
            newReport.setValoriDistinti(0L);
            newReport.setFiltroUtente(0L);
            newReport.setFiltroUffInt(0L);
            newReport.setUserOwner(utente.get().getSyscon());
            newReport.setCodReportWs(form.getCodReportWs());
            newReport.setCronExpression(form.getCronExpression());
            newReport.setFormatoRisultatoSchedulazione(!StringUtils.isEmpty(form.getFormatoSchedulazione()) ? Long.valueOf(form.getFormatoSchedulazione()) : null);
            newReport.setEmailRisultatoSchedulazione(form.getEmailSchedulazioneRisultato());
            newReport.setNoOutputVuoto(1L);

            String sysute = utente.get().getSysute();

            wrRepository.save(newReport);

            //Se necessario schedulo il report appena creato
            if(StringUtils.equals(form.getSchedula(), "1")) {
                if(!StringUtils.isEmpty(form.getCronExpression()) && CronExpression.isValidExpression(form.getCronExpression())){
                    schedulerManager.schedulateOrUpdateReport(form.getCronExpression(), progressivoMax, syscon, idProfilo, ipEvento);
                }
                else{
                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_CRON_EXPRESSION_NOT_VALID);

                    throw ex;
                }
            }

            //Prima di convertire in DTO il risultato ed inviare tutto a frontend, provvedo ad associare i profili se richiesti dall'utente in fase di creazione
            //Prima di convertire il tutto in un DTO, provvedo ad associare tutti i profili se richiesto dall'utente.
            if(newReport.getTuttiProfili() == 1){

                rendiDisponibileTuttiProfili(newReport.getIdRicerca());
            }
            //Medesima cosa per gli uffici intestatari
            if(newReport.getTuttiUffici() == 1){

                rendiDisponibileTuttiUffici(newReport.getIdRicerca());
            }

            WRicercheDTO reportToReturn = objectMapper.convertValue(newReport, WRicercheDTO.class);
            reportToReturn.setSysute(sysute);

            response.setResponse(reportToReturn);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_INSERT_NEW_REPORT);
                logger.error("Si è verificato un errore in fase di inserimento del nuovo report.");
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createInsertReportEvent(syscon, ipEvento, COD_APP, newReport.getIdRicerca(), idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::insertNewReport");

        return response;
    }

    public ResponseListaDTO getListaProfili(final UserDTO currentUserDTO) {

        logger.info("START esecuzione metodo di RicercheManager::getListaProfili");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{

            List<ProfiloDTO> profileList = new ArrayList<>();

            User currentUser = getUserFromUserDTO(currentUserDTO);

            boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

            List<Profilo> profili = null;

            if (utenteDelegatoGestioneUtenti) {
                List<String> listaProfiliGestore = currentUser.getProfili().parallelStream().map(Profilo::getCodice).collect(Collectors.toList());
                if (!listaProfiliGestore.isEmpty())
                    profili = profiloRepository.findByIds(listaProfiliGestore);
            } else {
                profili = profiloRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizione", "codice"));
            }

            if (profili != null && !profili.isEmpty()) {
                profileList = profili.stream().map(p -> dtoMapper.convertTo(p, ProfiloDTO.class))
                        .collect(Collectors.toList());
                profileList.sort(Comparator.comparing(ProfiloDTO::getCodice));
            }

            response.setResponse(profileList);
            response.setTotalCount((long) profileList.size());
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_PROFILI_ERROR);
                logger.error("Errore durante l'estrazione dei profili richiesta dall'utente: [{}].", currentUserDTO.getSyscon());
            }
            throw e;
        }

        logger.info("END esecuzione metodo di RicercheManager::getListaProfili");

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean setProfiliUtenteReport(final UserDTO currentUserDTO, final Long idRicerca, final List<String> listaProfili,
                                    final String ipEvento, final Long syscon, final String idProfilo) {

        logger.info("START esecuzione di RicercheManager::setProfiliUtenteReport per idRicerca [ {} ] e profili [ {} ]", idRicerca,
                listaProfili);

        if (idRicerca == null){
            throw new IllegalArgumentException("idRicerca null");
        }

        final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);

        if (!isCurrentUserAbilitatoGestioneUtenti) {

            logger.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

            throw new UserEditForbiddenException();
        }

        User currentUser = getUserFromUserDTO(currentUserDTO);
        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);


        if (utenteDelegatoGestioneUtenti) {
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_PROFILI_NOT_SELECTED);
            logger.error("Non è possibile associare 0 profili, selezionarne almeno uno");

            throw ex;
        }

        try {

            List<String> toBeAdded = new ArrayList<>();
            List<ProfiloDTO> allProfiles = (List<ProfiloDTO>) getListaProfili(currentUserDTO).getResponse();

            // recupero i profili da associare nella w_ricpro
            for(ProfiloDTO profile : allProfiles){
                if(listaProfili.contains(profile.getCodice())){
                    toBeAdded.add(profile.getCodice());
                }
            }

            // Rimuovo tutti i profili dalla w_ricpro per il report corrispondente
            List<WRicpro> allProfilesToDelete = wRicProRepository.findByIdRicerca(idRicerca);
            if(!allProfilesToDelete.isEmpty()){
                //wRicProRepository.deleteByIdRicerca(idRicerca);
                String queryToDelete = "DELETE FROM w_ricpro WHERE id_ricerca = ?";
                entityManager.createNativeQuery(queryToDelete)
                        .setParameter(1, idRicerca)
                        .executeUpdate();
            }

            // aggiungo i gruppo dei profili da associare nella w_ricpro con (idRicerca, codice_profilo)
            for (String profiloDaAggiungere : toBeAdded) {

                String queryToInsert = "INSERT INTO w_ricpro (id_ricerca, cod_profilo) VALUES (?,?)";
                entityManager.createNativeQuery(queryToInsert)
                        .setParameter(1, idRicerca)
                        .setParameter(2, profiloDaAggiungere)
                        .executeUpdate();
            }

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SET_PROFILI_REPORT_ERROR);
                logger.error("Errore durante il set dei profili da associare al report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WlogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createSetProfiliReportEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione di RicercheManager::setProfiliUtenteReport per idRicerca [{}] e profili [{}]", idRicerca,
                listaProfili);

        return true;
    }

    protected User getUserFromUserDTO(final UserDTO userDTO) {
        if (userDTO == null)
            return null;

        return this.userRepository.findById(userDTO.getSyscon()).orElse(null);
    }

    protected boolean isUtenteDelegatoAllaGestioneUtenti(final User user) {

        if (user == null)
            return false;

        if (StringUtils.isBlank(user.getSyspwbou()))
            return false;

        List<String> listaOpzioniUtente = getListaOpzioniUtente(user.getSyspwbou());

        // Gestione completa
        boolean hasGestioneUtenti = listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_COMPLETA) || //
                ( //
                        // Sola lettura
                        listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_COMPLETA) && //
                                listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_OU12) //
                );

        boolean isAdministrator = listaOpzioniUtente.contains(AppConstants.OU_AMMINISTRATORE);
        boolean hasAllSAAccess = listaOpzioniUtente.contains(AppConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI);

        return hasGestioneUtenti && !isAdministrator && !hasAllSAAccess;
    }

    protected List<String> getListaOpzioniUtente(final String opzioniUtenteString) {
        List<String> opzioniUtenteList = null;

        if (StringUtils.isNotBlank(opzioniUtenteString)) {
            opzioniUtenteList = new ArrayList<>(
                    Arrays.asList(opzioniUtenteString.split(AppConstants.OU_SEPARATORE_REGEX)));
        } else {
            opzioniUtenteList = new ArrayList<>();
        }

        return opzioniUtenteList;
    }

    public ResponseListaDTO getUfficiIntestatari(final UserDTO currentUserDTO) {

        logger.info("START esecuzione RicercheManager::getUfficiIntestatariReport");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{

            List<UfficioIntestatarioDTO> listaDTO = new ArrayList<>();

            User currentUser = getUserFromUserDTO(currentUserDTO);

            boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

            List<Uffint> listaDomain = null;

            if (utenteDelegatoGestioneUtenti) {
                List<String> listaUfficiIntestatariGestore = currentUser.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList());
                if (!listaUfficiIntestatariGestore.isEmpty())
                    listaDomain = uffintRepository.findByIds(listaUfficiIntestatariGestore);
            } else {
                listaDomain = uffintRepository.findAll(Sort.by(Sort.Direction.ASC, "denominazione", "codice"));
            }

            if (listaDomain != null && !listaDomain.isEmpty()) {
                listaDTO = listaDomain.stream().map(u -> dtoMapper.convertTo(u, UfficioIntestatarioDTO.class))
                        .collect(Collectors.toList());
            }

            response.setResponse(listaDTO);
            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setTotalCount((long) listaDTO.size());

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_UFFICI_ERROR);
                logger.error("Errore durante l'estrazione degli uffici intestatari.");
            }
            throw e;
        }

        logger.info("END esecuzione RicercheManager::getUfficiIntestatari");

        return response;
    }

    public ResponseListaDTO getUfficiIntestatariReport(final UserDTO currentUser, final Long idRicerca) {

        logger.info("START esecuzione RicercheManager::getUfficiIntestatariReport per syscon [{}]", idRicerca);

        //List<UfficioIntestatarioDTO>
        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        if (idRicerca == null){
            throw new IllegalArgumentException("idRicerca null");
        }

        try{

            //Trovo tutti gli ufffici intestatari associati al report con idRicerca passato a parametro nella w_ricuffint
            List<WRicuffint> allRicUffInt = wRicUffintRepository.findByIdRicerca(idRicerca);
            List<String> allCodeIn = new ArrayList<>();
            List<UfficioIntestatarioDTO> allUffIntToReturnDTO = new ArrayList<>();

            //Mi salvo tutti i codici degli uffici intestatari
            for(WRicuffint uffint : allRicUffInt){
                allCodeIn.add(uffint.getId().getCodein());
            }

            //Trovo tutti gli uffici intestatari in base ai codeIn che posseggo nella w_ricuffint e li salvo in allUffIntToReturnDTO
            for(String codeIn : allCodeIn) {

                Uffint singleUffInt = uffintRepository.findByCodeIn(codeIn);

                if(singleUffInt != null) {
                    UfficioIntestatarioDTO singleUffIntDTO = dtoMapper.convertTo(singleUffInt, UfficioIntestatarioDTO.class);

                    allUffIntToReturnDTO.add(singleUffIntDTO);
                }
            }

            response.setResponse(allUffIntToReturnDTO);
            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setTotalCount((long) allUffIntToReturnDTO.size());

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_UFFICI_ERROR);
                logger.error("Errore durante l'estrazione degli uffici intestatari.");
            }
            throw e;
        }

        //Loggo la WLogEventi
        //WLogEventiDTO wLogEventiDTO = logEventiUtils.createGetUfficiIntestatariReportEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        //logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione RicercheManager::getUfficiIntestatariReport per syscon [{}]", idRicerca);

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public ResponseListaDTO setUfficiIntestatariReport(
            final UserDTO currentUserDTO,
            final Long idRicerca,
            final Long syscon,
            final String ipEvento,
            final String idProfilo,
            final List<String> listaUfficiIntestatari
    ) {

        logger.info("START esecuzione in RicercheManager::setUfficiIntestatariReport per idRicerca [ {} ] e uffici intestatari [ {} ]", idRicerca, listaUfficiIntestatari);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            if (idRicerca == null){
                throw new IllegalArgumentException("idRicerca null");
            }

        /*if (listaUfficiIntestatari == null || listaUfficiIntestatari.isEmpty()){
            throw new IllegalArgumentException("listaUfficiIntestatari null");
        }*/

            final boolean isCurrentUserAbilitatoGestioneUtenti = isUserAbilitatoGestioneUtenti(currentUserDTO);

            if (!isCurrentUserAbilitatoGestioneUtenti) {

                logger.error("L'utente [ {} ] non ha i permessi di gestione utente", currentUserDTO.getSyscon());

                throw new UserEditForbiddenException();
            }

            User currentUser = getUserFromUserDTO(currentUserDTO);
            boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);


            if (utenteDelegatoGestioneUtenti) {
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_PROFILI_NOT_SELECTED);
                logger.error("Selezionare almeno un ufficio intestatario.");

                throw ex;
            }

            List<String> toBeAdded = new ArrayList<>();
            List<UfficioIntestatarioDTO> allUffInt = (List<UfficioIntestatarioDTO>) getUfficiIntestatari(currentUserDTO).getResponse();

            //Recupero gli uffici intestatari da associare nella w_ricuffint
            for(UfficioIntestatarioDTO uffint : allUffInt){
                if(listaUfficiIntestatari.contains(uffint.getCodice())){
                    toBeAdded.add(uffint.getCodice());
                }
            }

            //Rimuovo tutti gli uffici intestatari dalla w_ricuffint per il report corrispondente
            List<WRicuffint> allUffintToDelete = wRicUffintRepository.findByIdRicerca(idRicerca);
            if(!allUffintToDelete.isEmpty()){
                String queryToDelete = "DELETE FROM w_ricuffint WHERE id_ricerca = ?";
                entityManager.createNativeQuery(queryToDelete)
                        .setParameter(1, idRicerca)
                        .executeUpdate();
            }

            //Aggiungo gli uffici intestatari nuovi presenti all'interno della listaUfficiIntestatari
            for(String uffintDaAggiungere : toBeAdded){
                String queryToInsert = "INSERT INTO w_ricuffint (id_ricerca, codein) VALUES (?, ?)";
                entityManager.createNativeQuery(queryToInsert)
                        .setParameter(1, idRicerca)
                        .setParameter(2, uffintDaAggiungere)
                        .executeUpdate();
            }

            response.setResponse(true);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_UPDATE_ERROR_UPDATE_UFFINT_REPORT);
                logger.error("Errore durante l'associazione degli uffici instestatari al report con idRicerca: [{}] ", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createSetUfficiIntestatariReportEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::setUfficiIntestatariReport per idRicerca [ {} ] e uffici intestatari [ {} ]", idRicerca, listaUfficiIntestatari);

        return response;
    }

    protected boolean isUserAbilitatoGestioneUtenti(final UserDTO userDTO) {

        if (userDTO == null)
            return false;

        User user = this.userRepository.findById(userDTO.getSyscon()).orElse(null);

        if (user == null)
            return false;

        if (StringUtils.isBlank(user.getSyspwbou()))
            return false;

        List<String> listaOpzioniUtente = getListaOpzioniUtente(user.getSyspwbou());
        return listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_COMPLETA)
                && !listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_OU12);
    }

    public ResponseListaDTO getProfiliReport(Long idRicerca){

        logger.info("START esecuzione in RicercheManager::getProfiliReport per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{

            List<WRicpro> allProfilesReport = wRicProRepository.findByIdRicerca(idRicerca);
            List<ProfiloDTO> profilesToReturn = new ArrayList<>();

            List<String> codProfili = new ArrayList<>();
            for(WRicpro profile : allProfilesReport){
                codProfili.add(profile.getId().getCodProfilo());
            }

            for(String codProfilo : codProfili) {

                Profilo profiloToAdd = profiloRepository.findByCodProfilo(codProfilo);

                ProfiloDTO profiloConvert = objectMapper.convertValue(profiloToAdd, ProfiloDTO.class);
                profilesToReturn.add(profiloConvert);
            }

            response.setResponse(profilesToReturn);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_PROFILI_ERROR);
                logger.error("Errore generico in fase di get dei profili associati al report con idRicerca [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        //WLogEventiDTO wLogEventiDTO = logEventiUtils.createGetProfiliReportEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        //logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::getProfiliReport per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO exportRisultatoReportCsv(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca, String ipEvento, Long syscon, String idProfilo){

        logger.info("START esecuzione in RicercheManager::exportRisultatoReportCsv per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {
            //Controllo se il form contiene elementi da poter passare alla generazione del nuovo csv

            //Primo controllo per le colonne
            if(!columnNames.isEmpty() && !values.isEmpty()){
                response.setTotalCount((long) values.size());
                if(values.size() > 1000) {
                    values = values.subList(0, 1000);
                }

                ResponseEntity<String> csvFile = extractCsv(columnNames,values, idRicerca);
                //Se qualcosa nell'estrazione è andato male, ritorno con messaggio d'errore generico
                if(csvFile.getStatusCode().is5xxServerError()){

                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_CSV_ERROR);

                    logger.error("Errore durante l'estrazione del csv per il report con idRicerca: [{}]", idRicerca);

                    throw ex;
                }
                response.setResponse(csvFile);
                response.setDone(AppConstants.RESPONSE_DONE_Y);
            }
            //Se non sono presenti record nel form passato, ritorno un csv vuoto.
            //In questo caso non c'è nessun errore da segnalare.
            else {
                response.setTotalCount(0L);
                response.setResponse("");
                response.setDone(AppConstants.RESPONSE_DONE_Y);
            }

        } catch (GenericReportOperationException e) {

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_ERROR_GENERIC);
                logger.error("Errore inaspettato durante l'estrazione del csv per il report: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createExportReportCSVEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::exportRisultatoReportCsv per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    protected ResponseEntity<String> extractCsv(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca) {

        logger.info("START esecuzione in RicercheManager::extractCsv");

        StringWriter out = new StringWriter();

        try {

            if(columnNames != null){

                final CSVFormat csvFormat = CSVFormat.Builder.create()
                        .setHeader(columnNames.toArray(new String[0]))
                        .setDelimiter(";")
                        .setAllowMissingColumnNames(true)
                        .build();

                CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);

                for (Map<String, Object> entryRow : values) {

                    //Stampo la singola riga
                    for(Map.Entry<String, Object> entryElem : entryRow.entrySet()){

                        csvPrinter.print(entryElem.getValue() == null ? "" : CsvUtils.escapeCsv(entryElem.getValue().toString()));
                    }
                    //Vado a capo alla fine.
                    csvPrinter.println();
                }
            } else {
                //Non mando nulla in stampa, ma ritorno un csv vuoto all'utente.
                out.write("");
            }
        } catch (Exception e) {

            logger.error("Errore durante l'estrazione del CSV per il report con idRicerca: [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_CSV_ERROR);
        }

        logger.info("END esecuzione in RicercheManager::extractCsv");

        // Convertire il documento in una stringa Base64 da decodare a Frontend
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(out.toString());
    }

    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO exportRisultatoReportDocx(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca, String ipEvento, Long syscon, String idProfilo) {

        logger.info("START esecuzione in RicercheManager::exportRisultatoReportDocx per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {
            //Controllo se il form contiene elementi da poter passare alla generazione del nuovo csv

            //Primo controllo per le colonne
            if(!columnNames.isEmpty() && !values.isEmpty()){
                response.setTotalCount((long) values.size());

                ResponseEntity<String> docxFile = extractDocx(columnNames, values, idRicerca);

                response.setResponse(docxFile);
                response.setDone(AppConstants.RESPONSE_DONE_Y);

            }
            //Se non sono presenti record nel form passato, ritorno una response vuota senza messaggio d'errore.
            else {
                response.setTotalCount(0L);
                response.setMessages(null);
                response.setResponse(null);
                response.setDone(AppConstants.RESPONSE_DONE_Y);
            }

        } catch (Exception e) {

            logger.error("Errore inaspettato durante l'estrazione in formato DOCX per il report: [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_ERROR_GENERIC);
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createExportReportDOCXEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::exportRisultatoReportRtf per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    protected ResponseEntity<String> extractDocx(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca) {

        logger.info("START esecuzione in RicercheManager::extractDocx");

        try {

            // Creare un documento DOCX
            XWPFDocument document = new XWPFDocument();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Imposta l'orientamento della pagina in orizzontale
            CTBody body = document.getDocument().getBody();
            if (!body.isSetSectPr()) body.addNewSectPr();
            CTSectPr section = body.getSectPr();
            if (!section.isSetPgSz()) section.addNewPgSz();
            section.getPgSz().setOrient(STPageOrientation.LANDSCAPE);
            section.getPgSz().setW(BigInteger.valueOf(16837)); //Larghezza in twip
            section.getPgSz().setH(BigInteger.valueOf(11905)); //Altezza in twip.

            //Creo la tabella sulla base di columnNames e values.
            XWPFTable table = document.createTable();
            table.setWidth("100%");
            table.setCellMargins(2, 2, 2, 2);

            //Scrivo l'header della tabella
            int rowCount = 0;
            XWPFTableRow row = table.getRow(rowCount);
            for(String columnName : columnNames){

                XWPFRun run = row.addNewTableCell().getParagraphs().get(0).createRun();
                run.setText(columnName);
                run.setBold(true);
                run.setFontFamily("Helvetica");
                run.setFontSize(13);
            }

            //Rimuove la cella vuota iniziale dell'header delle colonne.
            // Creazione della riga di intestazione (header)
            XWPFTableRow headerRow = table.getRow(0);
            // Rimuove la cella vuota iniziale aggiunta automaticamente
            if (headerRow.getTableCells().size() > columnNames.size()) {
                headerRow.removeCell(0);
            }

            //Scrivo le righe contenute in values
            for ( Map<String, Object> value : values ) {
                XWPFTableRow rowValues = table.createRow();

                for( String columnName : columnNames ) {

                    Object cellValue = value.get(columnName);
                    XWPFRun run = rowValues.addNewTableCell().getParagraphs().get(0).createRun();
                    run.setText(cellValue != null ? cellValue.toString() : "");
                    run.setFontFamily("Helvetica");
                    run.setFontSize(11);

                    //Viene creata una riga automatica in bianco come primo elemento della riga. Va rimossa
                    rowValues.removeCell(0);
                }
            }

            document.write(byteArrayOutputStream);
            document.close();

            // Convertire il documento in una stringa Base64 da decodare a Frontend
            byte[] docxBytes = byteArrayOutputStream.toByteArray();
            String resultString = Base64.getEncoder().encodeToString(docxBytes);

            logger.info("END esecuzione in RicercheManager::extractDocx");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resultString);

        } catch (Exception e) {

            logger.error("Errore durante l'estrazione in formato DOCX per il report con idRicerca: [{}]", idRicerca, e);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_RTF_ERROR);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO exportRisultatoReportPdf(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca, Long syscon, String ipEvento, String idProfilo) {

        logger.info("START esecuzione in RicercheManager::exportRisultatoReportPdf per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {
            //Controllo se il form contiene elementi da poter passare alla generazione del nuovo csv

            //Primo controllo per le colonne
            if(!columnNames.isEmpty() && !values.isEmpty()){
                response.setTotalCount((long) values.size());
                if(values.size() > 1000) {
                    values = values.subList(0, 1000);
                }

                ResponseEntity<String> pdfFile = extractPdf(columnNames,values, idRicerca);
                //Se qualcosa nell'estrazione è andato male, ritorno con messaggio d'errore generico
                if(pdfFile.getStatusCode().is5xxServerError()){

                    GenericReportOperationException ex = new GenericReportOperationException();
                    ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_PDF_ERROR);
                    logger.error("Errore durante l'esportazione in PDF per il report con idRicerca: [{}]", idRicerca);

                    throw ex;
                }
                response.setResponse(pdfFile);
                response.setDone(AppConstants.RESPONSE_DONE_Y);

            }
            //Se non sono presenti record nel form passato, ritorno una response vuota.
            else {
                response.setTotalCount(0L);
                response.setResponse(null);
                response.setDone(AppConstants.RESPONSE_DONE_Y);
            }

        } catch (Exception e) {

            logger.error("Errore durante l'estrazione in formato PDF per il report con idRicerca: [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_ERROR_GENERIC);
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createExportReportPDFAEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::exportRisultatoReportPdf per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    protected ResponseEntity<String> extractPdf(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca) {

        logger.info("START esecuzione in RicercheManager::extractPdf per il report con idRicerca [{}]", idRicerca);

        try {

            // Creo il byte array di output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Creo un PdfWriter per il documento
            PdfWriter writer = new PdfWriter(baos);

            // Carico il profilo ICC
            InputStream iccProfilePath = RicercheManager.class.getResourceAsStream("/icc/sRGB_ICC_v4.icc");
            PdfOutputIntent outputIntent = new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", iccProfilePath);

            DocumentProperties properties = new DocumentProperties();

            // Crea un PdfADocument con il livello di conformità PDF/A-1B
            PdfADocument pdfDoc = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_1B, outputIntent, properties);

            // Aggiungi informazioni di conformità
            pdfDoc.setTagged();
            pdfDoc.getCatalog().setLang(new PdfString("en-US"));
            pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
            pdfDoc.getDocumentInfo().setTitle("File PDF/A");

            //Faccio l'embedding del font al documento secondo le specifiche
            String regularFontPath = "/fonts/Helvetica.ttf";
            String boldFontPath = "/fonts/Helvetica-Bold.ttf";
            PdfFont regularFont = PdfFontFactory.createFont(regularFontPath, PdfEncodings.IDENTITY_H);
            PdfFont boldFont = PdfFontFactory.createFont(boldFontPath, PdfEncodings.IDENTITY_H);

            // Crea il documento
            Document document = new Document(pdfDoc, PageSize.A4.rotate());
            document.setMargins(20, 20, 20, 20);

            int numColumns = columnNames.size();
            //Creo la tabella corretta con le colonne che arrivano da parametro
            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(columnNames.size())).useAllAvailableWidth();

            //Scrivo l'header della tabella
            for (String columnName : columnNames) {
                table.addCell(
                                new Cell()
                                .add(new Paragraph(columnName).setFont(boldFont))
                             )
                             .setTextAlignment(TextAlignment.CENTER)
                             .setAutoLayout()
                             .setBold();
            }

            // Scrivo le righe contenute in values
            for (Map<String, Object> row : values) {
                for (String columnName : columnNames) {
                    Object cellValue = row.get(columnName);
                    table.addCell(
                                    new Cell()
                                    .add(new Paragraph(cellValue != null ? cellValue.toString() : "")
                                            .setFont(regularFont)
                                            .setTextAlignment(TextAlignment.CENTER)
                                    )
                                 );
                }
            }

            document.add(table);
            document.close();

            byte[] pdfBytes = baos.toByteArray();

            String resultString = Base64.getEncoder().encodeToString(pdfBytes);

            logger.info("END esecuzione in RicercheManager::extractPdf");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resultString);

        } catch (Exception e) {

            logger.error("Errore durante l'estrazione in formato PDF per il report con idRicerca: [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_PDF_ERROR);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO exportRisultatoReportJson(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca, Long syscon, String ipEvento, String idProfilo){

        logger.info("START esecuzione in RicercheManager::exportRisultatoReportJson per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{

            List<Map<String, Object>> jsonMap = new ArrayList<>();
            int totalCount = 0;

            for(Map<String, Object> row : values){

                Map<String, Object> rowN = new HashMap<>();
                //Inserisco per ogni colonna, il contenuto di ogni cella corrispondente alla n-esima riga.
                for(String column : columnNames){

                    rowN.put(column, row.get(column) == null || StringUtils.isEmpty(row.get(column).toString()) ? "" : row.get(column));
                }

                totalCount++;
                jsonMap.add(rowN);
            }

            response.setResponse(jsonMap);
            response.setTotalCount((long) totalCount);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (Exception e){

            logger.error("Errore durante l'estrazione in formato JSON per il report con idRicerca: [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_JSON_ERROR);
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createExportReportJSONEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::exportRisultatoReportJson per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO exportRisultatoReportXlsx(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca, Long syscon, String ipEvento, String idProfilo){

        logger.info("START esecuzione in RicercheManager::exportRisultatoReportXlsx per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        long totalCount;

        try{

            Object[] excelExtraction = extractXlsx(columnNames, values, idRicerca);

            byte[] excelFileContent = (byte[]) excelExtraction[0];
            totalCount = (Integer) excelExtraction[1];

            response.setResponse(excelFileContent);
            response.setTotalCount(totalCount);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (Exception e){
            logger.error("Errore durante l'estrazione in formato XLSX per il report con idRicerca: [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_XLSX_ERROR);
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createExportReportXLSXEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::exportRisultatoReportXlsx per il report con idRicerca [{}]", idRicerca);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    protected Object[] extractXlsx(List<String> columnNames, List<Map<String, Object>> values, Long idRicerca){

        logger.info("START esecuzione in RicercheManager::extractXlsx per il report con idRicerca [{}]", idRicerca);

        try(Workbook workbook = new XSSFWorkbook()){

            int totalCount = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet("Risultato_report");

            int rowCount = 0;
            //Aggiungo i nomi delle colonne
            Row firstRow = sheet.createRow(rowCount++);
            int colFirstRowCount = 0;
            for(String colFirstRow : columnNames){
                org.apache.poi.ss.usermodel.Cell cell = firstRow.createCell(colFirstRowCount++);
                cell.setCellValue(colFirstRow);
            }

            //Aggiungo i valori effettivi al file
            for(Map<String, Object> value : values){
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                for(String column : columnNames){

                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(value.get(column) == null || StringUtils.isEmpty(value.get(column).toString())? "" : value.get(column).toString());
                }
                totalCount++;
            }

            workbook.write(baos);

            logger.info("END esecuzione in RicercheManager::extractXlsx per il report con idRicerca [{}]", idRicerca);

            return new Object[]{ baos.toByteArray(), totalCount };

        } catch (Exception e){

            logger.error("ERRORE esecuzione in RicercheManager del metodo extractXlsx per il report con idRicerca [{}]", idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_XLSX_ERROR);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO saveCache(Long idAccount, Long idRicerca, GenericDynamicFormValueDTO form, String ipEvento, String codProfilo){

        logger.info("START esecuzione in RicercheManager::saveCache per il report con idRicerca [{}] e syscon [{}]", idRicerca, idAccount);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try {

            if (idAccount == null || idRicerca == null || form == null) {
                throw new IllegalArgumentException("Invalid input parameters");
            }

            // Recupero tutto il form con i parametri immessi dall'utente.
            List<WRicParamDTO> allParams = form.getAllParams();

            //Rimuovo i parametri da allParams che non hanno valori da salvare in quanto vuoti.
            //allParams.removeIf(param -> param.getValue() == null);

            //Per ogni parametro con il campo value valorizzato, lo inserisco nella tabella WCacheRicParam che funge da cache temporanea per salvare
            //i valori per parametri già immessi in precedenza con il rispettivo (idAccount, idRicerca, codice).
            for(WRicParamDTO param : allParams){

                String codiceParam = param.getCodice();
                Optional<WCacheRicPar> checkPresenceParam = wCacheRicParRepository.findByIdAccountAndIdRicercaAndCodice(idAccount, idRicerca, codiceParam);

                //Se esiste già un parametro nella WCacheRicPar che corrisponde al trio (idAccount, idRicerca, codice), lo aggiorno con il nuovo valore.
                if(checkPresenceParam.isPresent()) {

                    String updateParamCacheValues = "UPDATE w_cachericpar SET valore = ? WHERE id_account = ? AND id_ricerca = ? AND codice = ?";
                    entityManager.createNativeQuery(updateParamCacheValues)
                            .setParameter(1, param.getValue())
                            .setParameter(2, idAccount)
                            .setParameter(3, idRicerca)
                            .setParameter(4, codiceParam)
                            .executeUpdate();
                    entityManager.flush();
                }
                //Altrimenti lo creo e lo salvo in tabella.
                else {
                    Long idRicercaParam = param.getIdRicerca();
                    String value = param.getValue();

                    WCacheRicParPK newCacheParamPk = new WCacheRicParPK();
                    newCacheParamPk.setIdAccount(idAccount);
                    newCacheParamPk.setIdRicerca(idRicercaParam);
                    newCacheParamPk.setCodice(codiceParam);

                    WCacheRicPar newCacheParam = new WCacheRicPar();
                    newCacheParam.setId(newCacheParamPk);
                    newCacheParam.setValore(value);

                    wCacheRicParRepository.save(newCacheParam);
                }
            }

            response.setResponse(true);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (Exception e) {

            logger.error("Errore durante il salvataggio del parametro con idAccount: [{}] and idRicerca: [{}]", idAccount, idRicerca);
            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_ERROR_GENERIC_CACHE_SAVE);
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createSaveParamsWCacheRicParEvent(idAccount, ipEvento, COD_APP, idRicerca, codProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::saveCache per il report con idRicerca [{}] e syscon [{}]", idRicerca, idAccount);

        return response;
    }

    @Transactional(rollbackFor=Exception.class)
    protected void rendiDisponibileTuttiProfili(Long idRicerca){

        logger.info("START esecuzione in RicercheManager::rendiDisponibileTuttiProfili");

        try {

            // Rimuovo tutti i profili dalla w_ricpro per il report corrispondente
            List<WRicpro> allProfilesToDelete = wRicProRepository.findByIdRicerca(idRicerca);
            if(!allProfilesToDelete.isEmpty()){

                //Si rende disponibile il report per ogni profilo. Risulta sufficiente controllare se in WRicerche il campo tuttiProfili = 1.
                wRicProRepository.deleteByIdRicerca(idRicerca);
            }

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_RENDI_DISPONIBILE_PROFILI);
                logger.error("Errore durante l'assegnazione di tutti i profili per il report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        logger.info("END esecuzione in RicercheManager::rendiDisponibileTuttiProfili");
    }

    @Transactional(rollbackFor=Exception.class)
    protected void rendiDisponibileTuttiUffici(Long idRicerca){

        logger.info("START esecuzione in RicercheManager::rendiDisponibileTuttiUffici");

        try{

            // Rimuovo tutti gli uffint dalla w_ricuffint per il report corrispondente
            List<WRicuffint> allUffIntToDelete = wRicUffintRepository.findByIdRicerca(idRicerca);
            if(!allUffIntToDelete.isEmpty()){

                //Si rende disponibile il report per ogni ufficio intestatario. Risulta sufficiente controllare se in WRicerche il campo tuttiUffici = 1.
                wRicUffintRepository.deleteByIdRicerca(idRicerca);
            }

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_RENDI_DISPONIBILE_UFFICI);
                logger.error("Errore durante l'assegnazione di tutti gli uffInt al report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }


        logger.info("END esecuzione in RicercheManager::rendiDisponibileTuttiUffici");
    }

    public ResponseListaDTO getListaReportPredefiniti(
            String syscon,
            String idProfilo,
            String uffInt,
            String codApp,
            String nome,
            String descrizione,
            String fieldToSort,
            String sortDirection,
            Long sysconUser,
            String ipEvento
    ){

        logger.info("START esecuzione in RicercheManager::getListaReportPredefiniti");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{

            List<WRicerche> allReportPredefiniti = wrRepository.findAll(
                    RicercheSpecification.getReportPredifinitiSpecification(
                            syscon,
                            idProfilo,
                            uffInt,
                            codApp,
                            nome,
                            descrizione,
                            fieldToSort,
                            sortDirection
                    )
            );

            //Recupero la lista dei report preferiti
            List<WPreferiti> reportPreferiti = wPreferitiRepository.findBySyscon(sysconUser);

            response.setTotalCount((long) allReportPredefiniti.size());

            List<WRicercheDTO> reportPredefinitiMapped = new ArrayList<>();
            for(WRicerche single : allReportPredefiniti ){

                String utenteCreatore = single.getUser().getSysute();
                Long pubblicatoReport = single.getPubblicato();
                Long sysconReport = single.getUser().getSyscon();

                single.setUser(null);

                WRicercheDTO report = objectMapper.convertValue(single, WRicercheDTO.class);
                report.setSysute(utenteCreatore);
                report.setPubblicato(UtilsMethods.mapFieldTwoValues(pubblicatoReport, 0L, "No", 1L, "Sì"));
                report.setSyscon(sysconReport);

                report.setIsPreferito(!reportPreferiti.isEmpty() && reportPreferiti.stream()
                        .anyMatch((reportPrefe) ->
                                reportPrefe.getKey1().equalsIgnoreCase(report.getIdRicerca().toString())));

                reportPredefinitiMapped.add(report);
            }

            response.setResponse(reportPredefinitiMapped);
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_ERROR_GENERIC_REPORT_PREDEFINITI);
                logger.error("Errore durante l'estrazione dei report predefiniti per l'utente: [{}]", syscon);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createGetListaReportPredefinitiEvent(Long.valueOf(syscon), ipEvento, COD_APP, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::getListaReportPredefiniti");

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> executeReportWSParams(String nomeReport, String ipEvento, ParametriReportWS parametriReportWS) throws Exception {

        logger.info("START esecuzione in RicercheManager::executeReportWSParams");

        try {

            //Trovo il report da eseguire in base al nome.
            //Trovo i parametri del report in base all'idRicerca ottenuto.
            Optional<WRicerche> reportToExecute = wrRepository.findByCodReportWs(nomeReport);
            if(reportToExecute.isEmpty()){

                logger.error("Report da eseguire non trovato. Controllare il nome inserito dall'utente.");
                return ResponseEntity.notFound().build();
            }

            Long idRicerca = reportToExecute.get().getIdRicerca();
            List<WRicParam> paramsReport = wRicParamRepository.findByIdRicerca(idRicerca);
            List<WRicParamDTO> paramsDTOReport = new ArrayList<>();

            //Adeguamento dei parametri inseriti dall'utente prima dell'esecuzione del report.
            GenericDynamicFormValueDTO form = new GenericDynamicFormValueDTO();

            if(!paramsReport.isEmpty()){

                for(WRicParam param : paramsReport){

                    Long idRicercaSingleParam = param.getId().getIdRicerca();
                    Long progressivoSingleParam = param.getId().getProgressivo();
                    WRicParamDTO paramDTO = objectMapper.convertValue(param, WRicParamDTO.class);

                    paramDTO.setIdRicerca(idRicercaSingleParam);
                    paramDTO.setProgressivo(progressivoSingleParam);

                    String codiceParam = param.getCodice();
                    if(parametriReportWS.getParametri().containsKey(codiceParam)){

                        Object valueParam = parametriReportWS.getParametri().get(codiceParam);
                        paramDTO.setValue(valueParam.toString());

                        paramsDTOReport.add(paramDTO);
                    }

                }
            }

            Map<String, Object> values = new HashMap<>();

            for(WRicParamDTO param : paramsDTOReport){

                Map<String, Object> nestedTypesAndValues = new HashMap<>();

                String codiceParam = param.getCodice();
                String tipoParam = param.getTipo();
                Object value = param.getValue();

                nestedTypesAndValues.put(tipoParam, value);
                values.put(codiceParam, nestedTypesAndValues);
            }

            //Parametri ordinati da inserire
            form.setAllParams(paramsDTOReport);

            //valori effettivi di ogni parametro
            form.setDynamicFormValueDTO(values);

            ResultQueryExecutionFormDTO responseExecution = (ResultQueryExecutionFormDTO) executeReportWithParams(idRicerca, form, null, ipEvento, null, true).getResponse();

            List<String> columnNamesResponse = responseExecution.getColumnNames();
            List<Map<String, Object>> valuesResponse = responseExecution.getValues();

            List<Map<String, Object>> jsonMap = new ArrayList<>();

            for(Map<String, Object> row : valuesResponse){

                Map<String, Object> rowN = new HashMap<>();
                //Inserisco per ogni colonna, il contenuto di ogni cella corrispondente alla n-esima riga.
                for(String column : columnNamesResponse){

                    rowN.put(column, row.get(column) == null || StringUtils.isEmpty(row.get(column).toString()) ? "" : row.get(column));
                }

                jsonMap.add(rowN);
            }

            logger.info("END esecuzione in RicercheManager::executeReportWSParams");

            return new ResponseEntity<>(objectMapper.writeValueAsString(jsonMap), HttpStatus.OK);
        } catch (Exception e){

            logger.error("Errore durante l'esecuzione del report chiamato tramite API pubblica: [{}]", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseListaDTO getAbilitazioneFiltroUffInt(){

        logger.info("START esecuzione in RicercheManager::getAbilitazioneFiltroUffInt");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{

            String filterConfig = wConfigRepository.getConfigurazione(COD_APP, AppConstants.APPLICATION_UFFINT_FILTER);
            Long valueConfig = Long.parseLong(filterConfig);

            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setResponse(valueConfig);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ESTRAZIONE_CONFIG_UFFINT_ERROR);
                logger.error("Errore durante il recupero della configurazione della proprietà uffInt.");
            }
            throw e;
        }

        logger.info("END esecuzione in RicercheManager::getAbilitazioneFiltroUffInt");

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseListaDTO rimuoviReportPreferito (Long idRicerca, Long syscon, String idProfilo, String ipEvento) {
        logger.info("START esecuzione in RicercheManager::rimuoviReportPreferito");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        Long idPreferito;

        try {

            Optional<WPreferiti> favoriteReportToDelete = wPreferitiRepository.findBySysconAndByIdRicercaAndByKey1(syscon, String.valueOf(idRicerca), "W_RICERCHE");

            //Se il report è presente a DB, procedo con la sua eliminazione dalla WPreferiti
            if(favoriteReportToDelete.isPresent()){
                idPreferito = favoriteReportToDelete.get().getIdPreferito();
                wPreferitiRepository.deleteById(idPreferito);

                response.setDone(AppConstants.RESPONSE_DONE_Y);
                response.setResponse("done");
            }
            //Non si dovrebbe mai entrare qui!!
            else {
                idPreferito = null;

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_REPORT_NOT_FOUND);
                logger.error("Report non presente per idRicerca: [{}]", idRicerca);

                throw ex;
            }

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_DELETE_FAVORITE_REPORT_ERROR);
                logger.error("Si è verificato un errore nel metodo rimuoviReportPreferito del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createRimuoviReportPreferitoEvent(syscon, ipEvento, COD_APP, idPreferito, idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::rimuoviReportPreferito");

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseListaDTO aggiungiReportPreferito(Long idRicerca, Long syscon, String idProfilo, String ipEvento) {
        logger.info("START esecuzione in RicercheManager::aggiungiReportPreferito");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);
        WPreferiti newReportPreferito = new WPreferiti();

        try {

            //Genero idPreferito nuovo dalla WGenChiavi.
            Long progressivoMax = logEventiUtils.getNextId("W_PREFERITI");

            if(idRicerca == null){
                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_NULL_FORM);
                logger.error("Errore nel metodo aggiungiReportPreferito: idRicerca nullo");

                throw ex;
            }

            newReportPreferito.setIdPreferito(progressivoMax);
            newReportPreferito.setSyscon(syscon);
            newReportPreferito.setTabella("W_RICERCHE");
            newReportPreferito.setKey1(idRicerca.toString());

            wPreferitiRepository.save(newReportPreferito);

            response.setResponse("done");
            response.setDone(AppConstants.RESPONSE_DONE_Y);

        }   catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_INSERT_NEW_REPORT);
                logger.error("Si è verificato un errore in fase di inserimento del nuovo report preferito.");
            }
            throw e;
        }

        //Loggo la WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createInsertReportPreferitoEvent(syscon, ipEvento, COD_APP, newReportPreferito.getIdPreferito(), idProfilo);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.info("END esecuzione in RicercheManager::aggiungiReportPreferito");

        return response;
    }
}
