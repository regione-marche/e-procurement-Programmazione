package it.appaltiecontratti.gestionereportsms.scheduler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.WMail;
import it.appaltiecontratti.gestionereportsms.dto.*;
import it.appaltiecontratti.gestionereportsms.exceptions.GenericReportOperationException;
import it.appaltiecontratti.gestionereportsms.managers.RicParamManager;
import it.appaltiecontratti.gestionereportsms.managers.RicercheManager;
import it.appaltiecontratti.gestionereportsms.repositories.*;
import it.appaltiecontratti.gestionereportsms.service.MailService;
import it.appaltiecontratti.gestionereportsms.utils.LogEventiUtils;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ThreadPoolTaskSchedulerManager {

    private static final Logger logger = LogManager.getLogger(ThreadPoolTaskSchedulerManager.class);

    /**
     * Costanti
     * */

    @Value("${application.codiceProdotto}")
    private String COD_APP;
    private static final String CONFIGURAZIONE_MAIL_ID = "STD";
    private static final String TITOLO_APPLICAZIONE = "it.eldasoft.titolo";
    private static final String OGGETTO_MAIL = " - estrazione report pianificato";
    private static final String TESTO_MAIL_SUCCESS = "Gentile utente, l'esecuzione del report schedulato è avvenuta con successo.\nIn allegato è presente il report estratto.";
    private static final String TESTO_MAIL_ERROR = "Gentile utente, l'esecuzione del report schedulato è andata in errore per il seguente motivo:";
    private static final int TIMEOUT_NON_ATTIVO = 0;
    private static final int DEFAULT_TIMEOUT_MILLISEC = 5000;
    private static final String PROP_CONFIGURAZIONE_MAIL_STANDARD = "STD";
    private static final long DEFAULT_INCREMENT = 1;

    /**
     * Iniezioni dipendenze
     * */
    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    @Lazy
    private RicercheManager ricercheManager;

    @Autowired
    @Lazy
    private RicParamManager ricParamManager;

    @Autowired
    private WMailRepository mailRepository;

    @Autowired
    private WConfigRepository configRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private LogEventiUtils logEventiUtils;

    /**
     * Mappa per schedulati
     * */
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

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
     * Metodi di classe
     * */

    public ThreadPoolTaskSchedulerManager(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedulateOrUpdateReport(String cronExpression, Long idRicerca, Long syscon, String codProfilo, String ipEvento){

        logger.debug("START esecuzione in ThreadPoolTaskScheduler::schedulaReport per report con idRicerca [{}]", idRicerca);

        //Fermo lo scheduler specifico per idRicerca nel caso in cui l'espressione
        // è vuota e l'utente non vuole schedulare il report in questione.
        if(StringUtils.isEmpty(cronExpression)){
            cancellaSchedulazioneEsistente(idRicerca);
        }
        else {
            ScheduledFuture<?> scheduledTask = scheduler.schedule(
                    () -> {
                        try {
                            eseguiSchedulazione(idRicerca, syscon, codProfilo, ipEvento);
                        } catch (Exception e) {

                            throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_SCHEDULING_GENERIC_ERROR);
                        }
                    },
                    new CronTrigger(cronExpression)
            );

            //Sostituisco qualsiasi task esistente per questo idRicerca
            ScheduledFuture<?> existingTask = scheduledTasks.put(idRicerca, scheduledTask);
            if(existingTask != null){
                existingTask.cancel(false);
            }
        }

        logger.debug("END esecuzione in ThreadPoolTaskScheduler::schedulaReport per report con idRicerca [{}]", idRicerca);
    }

    public void cancellaSchedulazioneEsistente(Long idRicerca){

        logger.debug("START esecuzione in ThreadPoolTaskScheduler::cancellaSchedulazioneEsistente per report con idRicerca [{}]", idRicerca);

        ScheduledFuture<?> existingTask = scheduledTasks.remove(idRicerca);
        if(existingTask!= null){
            existingTask.cancel(false);
        }

        logger.debug("END esecuzione in ThreadPoolTaskScheduler::cancellaSchedulazioneEsistente per report con idRicerca [{}]", idRicerca);
    }

    public void cancellaTutteLeSchedulazioni(){

        logger.debug("START esecuzione in ThreadPoolTaskScheduler::cancellaTutteLeSchedulazioni per gli schedulati esistenti.");

        scheduledTasks.forEach((id, task) -> task.cancel(false));
        scheduledTasks.clear();

        logger.debug("END esecuzione in ThreadPoolTaskScheduler::cancellaTutteLeSchedulazioni per gli schedulati esistenti.");
    }

    public static boolean isValidEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    protected void eseguiSchedulazione(Long idRicerca, Long syscon, String codProfilo, String ipEvento) throws Exception{

        logger.debug("START esecuzione in ThreadPoolTaskSchedulerManager::eseguiSchedulazione per report con idRicerca: [{}]", idRicerca);

        ResponseListaDTO responseDetail = ricercheManager.getDetailReport(idRicerca);
        WRicercheDTO reportDetail = (WRicercheDTO) responseDetail.getResponse();

        Map<String, Object> dynamicValue = new HashMap<>();
        List<WRicParamDTO> allParamsReport = new ArrayList<>();

        GenericDynamicFormValueDTO genericDynamicFormValueDTO = new GenericDynamicFormValueDTO();
        genericDynamicFormValueDTO.setDynamicFormValueDTO(dynamicValue);
        genericDynamicFormValueDTO.setAllParams(allParamsReport);

        ResponseListaDTO responseExecution = new ResponseListaDTO();
        responseExecution.setDone(AppConstants.RESPONSE_DONE_Y);
        ResultQueryExecutionFormDTO result = new ResultQueryExecutionFormDTO();
        String formatoSched;
        List<WRicParamDTO> listaParametri;

        List<String> receivers = new ArrayList<String>();
        List<String> receiverCC = new ArrayList<String>();
        List<String> receiversCCn = new ArrayList<String>();

        String sender = mailRepository.getEmailMittente(COD_APP, CONFIGURAZIONE_MAIL_ID);

        //Costruzione oggetto della mail
        String titoloApplicativo = configRepository.getConfigurazione(COD_APP, TITOLO_APPLICAZIONE);
        String oggettoMail = "";
        if(!StringUtils.isEmpty(titoloApplicativo)){
            oggettoMail = titoloApplicativo + OGGETTO_MAIL + " '" + reportDetail.getNome() + "'";
        }

        //Setto l'invio a tutti gli indirizzi indicati.
        String[] receiversArray = reportDetail.getEmailSchedulazioneRisultato().split(",");
        for(String receiver : receiversArray){
            receiver = receiver.trim();

            if(isValidEmail(receiver)){
                receivers.add(receiver);
            }
            else{

                GenericReportOperationException ex = new GenericReportOperationException();
                ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SCHEDULING_INVALID_EMAIL);

                throw ex;
            }
        }

        try{

            //Gestire definizione SQL vuota prima di esecuzione per evitare errori banali in schedulazione.
            if(!StringUtils.isEmpty(reportDetail.getDefSql())){
                responseExecution = ricercheManager.executeReportWithParams(idRicerca, genericDynamicFormValueDTO, syscon,  null, null, false);
                result = (ResultQueryExecutionFormDTO) responseExecution.getResponse();
            }

            formatoSched = reportDetail.getFormatoSchedulazione();

            ResponseListaDTO allParams = ricParamManager.getListaParametri(idRicerca, syscon, null, null);
            listaParametri = (List<WRicParamDTO>) allParams.getResponse();

        } catch (GenericReportOperationException e){

            //Invio email a destinatari con messaggio d'errore
            String checkMail = sendMail(sender, oggettoMail, receivers, receiverCC, receiversCCn, null, null, false);

            //Log esecuzione e schedulazione su WLogEventi
            WLogEventiDTO wLogEventiDTO = logEventiUtils.createSchedulazioneReportErrorEvent(syscon, ipEvento, COD_APP, idRicerca, codProfilo, reportDetail.getDefSql());
            logEventiUtils.insertLogEvent(wLogEventiDTO);

            logger.debug("Errore durante l'esecuzione del report per idRicerca: [{}]", idRicerca);
            e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_EXECUTION_QUERY);

            throw e;
        }

        if(StringUtils.equals(responseExecution.getDone(), AppConstants.RESPONSE_DONE_Y) && listaParametri.isEmpty()){

            if(((reportDetail.getNoOutputVuoto() == 1) && result.getValues() != null && !result.getValues().isEmpty()) ||
                (reportDetail.getNoOutputVuoto() == 0)){

                //Da convertire e inviare i file direttamente completi via mail
                switch (formatoSched){
                    case AppConstants.EXPORT_FORMAT_CSV:
                        String zipFileNameCSV = reportDetail.getNome() + ".zip";
                        File zipFile = null;
                        String checkMailCSV;

                        if (!StringUtils.isEmpty(reportDetail.getDefSql())) {
                            try {
                                ResponseListaDTO resultCSV = ricercheManager.exportRisultatoReportCsv(result.getColumnNames(), result.getValues(), idRicerca, ipEvento, syscon, codProfilo);
                                ResponseEntity<String> csvFileResponseEntity = (ResponseEntity<String>) resultCSV.getResponse();
                                String csvFile = csvFileResponseEntity.getBody();

                                zipFile = createZipFile(csvFile, "Risultato_report_" + reportDetail.getIdRicerca() + ".csv", zipFileNameCSV);
                            } catch (GenericReportOperationException e) {
                                logger.error("Errore durante l'esportazione del report CSV", e);
                                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SCHEDULING_MAIL_ERROR);
                                throw e;
                            }
                        } else {
                            logger.warn("La definizione SQL del report è vuota. Invio email senza allegato.");
                        }

                        // Invio email a destinatari
                        checkMailCSV = sendMail(sender, oggettoMail, receivers, receiverCC, receiversCCn, zipFile, zipFile != null ? zipFileNameCSV : null, true);

                        if (zipFile == null) {
                            logger.info("Email inviata senza allegato per il report con ID: [{}]", reportDetail.getIdRicerca());
                        } else {
                            logger.info("Email inviata con allegato CSV per il report con ID: [{}]", reportDetail.getIdRicerca());
                        }

                        break;

                    case AppConstants.EXPORT_FORMAT_XLSX:

                        String zipFileNameXLSX = reportDetail.getNome() + ".zip";
                        File zipFileXLSX = null;
                        String checkMailXLSX;

                        if (!StringUtils.isEmpty(reportDetail.getDefSql())) {
                            try {
                                ResponseListaDTO resultXLSX = ricercheManager.exportRisultatoReportXlsx(result.getColumnNames(), result.getValues(), idRicerca, syscon, ipEvento, codProfilo);
                                byte[] xlsxFileResponseEntity = (byte[]) resultXLSX.getResponse();

                                zipFileXLSX = createZipFile(xlsxFileResponseEntity, "Risultato_report_" + reportDetail.getIdRicerca() + ".xlsx", zipFileNameXLSX);
                            } catch (GenericReportOperationException e) {
                                logger.error("Errore durante l'esportazione del report XLSX", e);
                                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SCHEDULING_MAIL_ERROR);
                                throw e;
                            }
                        } else {
                            logger.warn("La definizione SQL del report è vuota. Invio email senza allegato.");
                        }

                        // Invio email a destinatari
                        checkMailXLSX = sendMail(sender, oggettoMail, receivers, receiverCC, receiversCCn, zipFileXLSX, zipFileXLSX != null ? zipFileNameXLSX : null, true);

                        if (zipFileXLSX == null) {
                            logger.info("Email inviata senza allegato per il report con ID: [{}]", reportDetail.getIdRicerca());
                        } else {
                            logger.info("Email inviata con allegato XLSX per il report con ID: [{}]", reportDetail.getIdRicerca());
                        }

                        break;

                    case AppConstants.EXPORT_FORMAT_DOCX:

                        String zipFileNameDOCX = reportDetail.getNome() + ".zip";
                        File zipFileDOCX = null;
                        String checkMailDOCX;

                        if (!StringUtils.isEmpty(reportDetail.getDefSql())) {
                            try {
                                ResponseListaDTO resultDOCX = ricercheManager.exportRisultatoReportDocx(result.getColumnNames(), result.getValues(), idRicerca, ipEvento, syscon, codProfilo);
                                ResponseEntity<String> docxFileResponseEntity = (ResponseEntity<String>) resultDOCX.getResponse();
                                String docxFile = docxFileResponseEntity.getBody();
                                byte[] decodedDocxBytes = Base64.getDecoder().decode(docxFile);

                                zipFileDOCX = createZipFile(decodedDocxBytes, "Risultato_report_" + reportDetail.getIdRicerca() + ".docx", zipFileNameDOCX);
                            } catch (GenericReportOperationException e) {
                                logger.error("Errore durante l'esportazione del report DOCX", e);
                                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SCHEDULING_MAIL_ERROR);
                                throw e;
                            }
                        } else {
                            logger.warn("La definizione SQL del report è vuota. Invio email senza allegato.");
                        }

                        // Invio email a destinatari
                        checkMailDOCX = sendMail(sender, oggettoMail, receivers, receiverCC, receiversCCn, zipFileDOCX, zipFileDOCX != null ? zipFileNameDOCX : null, true);

                        if (zipFileDOCX == null) {
                            logger.info("Email inviata senza allegato per il report con ID: [{}]", reportDetail.getIdRicerca());
                        } else {
                            logger.info("Email inviata con allegato DOCX per il report con ID: [{}]", reportDetail.getIdRicerca());
                        }

                        break;

                    case AppConstants.EXPORT_FORMAT_PDF:

                        String zipFileNamePDF = reportDetail.getNome() + ".zip";
                        File zipFilePDF = null;
                        String checkMailPDF;

                        if (!StringUtils.isEmpty(reportDetail.getDefSql())) {
                            try {
                                ResponseListaDTO resultPDF = ricercheManager.exportRisultatoReportPdf(result.getColumnNames(), result.getValues(), idRicerca, syscon, ipEvento, codProfilo);
                                ResponseEntity<String> pdfFileResponseEntity = (ResponseEntity<String>) resultPDF.getResponse();
                                String pdfFile = pdfFileResponseEntity.getBody();
                                byte[] decodedPdfBytes = Base64.getDecoder().decode(pdfFile);

                                zipFilePDF = createZipFile(decodedPdfBytes, "Risultato_report_" + reportDetail.getIdRicerca() + ".pdf", zipFileNamePDF);
                            } catch (GenericReportOperationException e) {
                                logger.error("Errore durante l'esportazione del report PDF", e);
                                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SCHEDULING_MAIL_ERROR);
                                throw e;
                            }
                        } else {
                            logger.warn("La definizione SQL del report è vuota. Invio email senza allegato.");
                        }

                        // Invio email a destinatari
                        checkMailPDF = sendMail(sender, oggettoMail, receivers, receiverCC, receiversCCn, zipFilePDF, zipFilePDF != null ? zipFileNamePDF : null, true);

                        if (zipFilePDF == null) {
                            logger.info("Email inviata senza allegato per il report con ID: [{}]", reportDetail.getIdRicerca());
                        } else {
                            logger.info("Email inviata con allegato PDF per il report con ID: [{}]", reportDetail.getIdRicerca());
                        }

                        break;

                    case AppConstants.EXPORT_FORMAT_JSON:

                        String zipFileNameJSON = reportDetail.getNome() + ".zip";
                        File zipFileJSON = null;
                        String checkMailJSON;

                        if (!StringUtils.isEmpty(reportDetail.getDefSql())) {
                            try {
                                ResponseListaDTO resultJSON = ricercheManager.exportRisultatoReportJson(result.getColumnNames(), result.getValues(), idRicerca, syscon, ipEvento, codProfilo);
                                List<Map<String, Object>> jsonFileResponseEntity = (List<Map<String, Object>>) resultJSON.getResponse();
                                String jsonFile = objectMapper.writeValueAsString(jsonFileResponseEntity);

                                zipFileJSON = createZipFile(jsonFile, "Risultato_report_" + reportDetail.getIdRicerca() + ".json", zipFileNameJSON);
                            } catch (GenericReportOperationException e) {
                                logger.error("Errore durante l'esportazione del report JSON", e);
                                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_SCHEDULING_MAIL_ERROR);
                                throw e;
                            }
                        } else {
                            logger.warn("La definizione SQL del report è vuota. Invio email senza allegato.");
                        }

                        // Invio email a destinatari
                        checkMailJSON = sendMail(sender, oggettoMail, receivers, receiverCC, receiversCCn, zipFileJSON, zipFileJSON != null ? zipFileNameJSON : null, true);

                        if (zipFileJSON == null) {
                            logger.info("Email inviata senza allegato per il report con ID: [{}]", reportDetail.getIdRicerca());
                        } else {
                            logger.info("Email inviata con allegato JSON per il report con ID: [{}]", reportDetail.getIdRicerca());
                        }

                        break;

                    default:

                        GenericReportOperationException ex = new GenericReportOperationException();
                        ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_FORMAT_SCHED_UNRECOGNIZED);

                        throw ex;
                }

            } else {
                //Niente da trasmettere
                logger.debug("Nulla da trasmettere per report con idRicerca: [{}]", idRicerca);
            }

        }
        else{
            GenericReportOperationException ex = new GenericReportOperationException();
            ex.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_EXECUTION_ERROR_OR_PARAMS_ERROR);

            throw ex;
        }

        //Log esecuzione e schedulazione su WLogEventi
        WLogEventiDTO wLogEventiDTO = logEventiUtils.createSchedulazioneReportSuccessEvent(syscon, ipEvento, COD_APP, idRicerca, codProfilo, null);
        logEventiUtils.insertLogEvent(wLogEventiDTO);

        logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::eseguiSchedulazione per report con idRicerca: [{}]", idRicerca);
    }

    private String sendMail(String from, String subject, List<String> receivers, List<String> receiversCC, List<String> receiversCCN, File fileToSend, String zipFileName, Boolean result) throws Exception{

        logger.debug("START esecuzione in ThreadPoolTaskSchedulerManager::sendMail.");

        String check = checkDestinatari(false, receivers, receiversCC, receiversCCN, from);

        if(StringUtils.isBlank(check)){

            try{
                JavaMailSender mailSender = getJavaMailSender(PROP_CONFIGURAZIONE_MAIL_STANDARD);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                String senderName = configRepository.getConfigurazione(COD_APP, TITOLO_APPLICAZIONE);
                senderName = senderName != null ? senderName : ".";
                helper.setFrom(new InternetAddress(from, senderName));

                //Build mail result (Success or Error)
                if(result){
                    String content = mailService.buildMailSuccess(senderName, TESTO_MAIL_SUCCESS);
                    helper.setText(content, true);

                    //Aggiunta allegato
                    helper.addAttachment(zipFileName, fileToSend);
                }
                else {

                    String content = mailService.buildMailError(TESTO_MAIL_ERROR, senderName);
                    helper.setText(content, true);
                }

                receivers = striptToEmptyList(receivers);
                receiversCC = striptToEmptyList(receiversCC);
                receiversCCN = striptToEmptyList(receiversCCN);

                helper.setTo(receivers.toArray(new String[0]));
                helper.setCc(receiversCC.toArray(new String[0]));
                helper.setBcc(receiversCCN.toArray(new String[0]));

                helper.setSubject(subject);
                mailSender.send(message);

            } catch (Exception e){
                logger.error("Errore in invio mail ad admin", e);
                throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_SCHEDULING_MAIL_ERROR);
            }

        }

        logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::sendMail.");

        return check;
    }

    private File createZipFile(Object content, String fileName, String zipFileName) throws IOException {
        logger.debug("START esecuzione in ThreadPoolTaskSchedulerManager::createZipFile per report avente nome: [{}].", fileName);

        File zipFile = new File(zipFileName);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);

            if (content instanceof File) {
                try (FileInputStream fis = new FileInputStream((File) content)) {
                    writeToZipStream(fis, zipOut);
                }
            } else if (content instanceof String) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(((String) content).getBytes())) {
                    writeToZipStream(bis, zipOut);
                }
            } else if (content instanceof ByteArrayOutputStream) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(((ByteArrayOutputStream) content).toByteArray())) {
                    writeToZipStream(bis, zipOut);
                }
            } else if (content instanceof byte[]) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream((byte[]) content)) {
                    writeToZipStream(bis, zipOut);
                }
            } else if (content == null) {
                throw new IllegalArgumentException("Tipo di contenuto non supportato");
            } else {
                throw new IllegalArgumentException("Tipo di contenuto non supportato");
            }
        }

        logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::createZipFile per report avente nome: [{}].", fileName);

        return zipFile;
    }

    private void writeToZipStream(InputStream is, ZipOutputStream zipOut) throws IOException {

        logger.debug("START esecuzione in ThreadPoolTaskSchedulerManager::createZipFile.");

        byte[] bytes = new byte[1024];
        int length;
        while ((length = is.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::createZipFile.");
    }

    private String checkDestinatari(boolean admin, List<String> receivers, List<String> ccReceivers, List<String> ccnReceivers,
                                    String from) {

        logger.debug("START esecuzione in ThreadPoolTaskSchedulerManager::checkDestinatari.");

        if (receivers.isEmpty() && ccReceivers.isEmpty() && ccnReceivers.isEmpty()) {
            logger.error("Deve essere presente almeno un destinatario");
            return admin ? "ADMIN-DESTINATARIO-NON-PRESENTE" : "DESTINATARIO-NON-PRESENTE";
        }
        if (StringUtils.isEmpty(from)) {
            logger.error("Mittente non presente");
            return "MITTENTE-NON-PRESENTE";
        }

        logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::checkDestinatari.");

        return null;
    }

    public JavaMailSender getJavaMailSender(final String idCfg) {

        logger.debug("START esecuzione in ThreadPoolTaskSchedulerManager::getJavaMailSender.");

        int timeout = DEFAULT_TIMEOUT_MILLISEC;
        //Sostituire con COD_APP

        WMail mailInfo = mailRepository.getInfoMailServer(COD_APP, idCfg);

        if(mailInfo != null){

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailInfo.getServer());
            mailSender.setPort(Integer.parseInt(mailInfo.getPorta()));
            mailSender.setUsername(!StringUtils.isEmpty(mailInfo.getIdUtente()) ? mailInfo.getIdUtente() : mailInfo.getMailMittente());
            //Auth con password non cifrata.
            String password = mailInfo.getPassword();
            if (password != null && !password.isEmpty()) {
                try {
                    ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                            FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, password.getBytes(),
                            ICriptazioneByte.FORMATO_DATO_CIFRATO);

                    password = new String(decrypt.getDatoNonCifrato());
                } catch (CriptazioneException e) {

                    throw new GenericReportOperationException(AppConstants.GESTIONE_REPORTS_DECRIPTAZIONE_PWD_ERROR);
                }
            }

            mailSender.setPassword(password);

            if (StringUtils.isNotBlank(mailInfo.getTimeout())) {
                timeout = Integer.parseInt(mailInfo.getTimeout());
            }

            Properties props = mailSender.getJavaMailProperties();

            switch (mailInfo.getProtocollo()) {
                case "1":
                    // smtps
                case "2":
                    // smtp + start_tls
                    props = System.getProperties();
                    break;
            }

            boolean isPasswordMittente = mailInfo.getPassword() != null;

            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", mailInfo.getServer());
            props.put("mail.smtp.port", mailInfo.getPorta());
            props.put("mail.smtp.auth", Boolean.toString(isPasswordMittente));

            // timeout
            props.remove("mail.smtp.connectiontimeout");
            props.remove("mail.smtp.timeout");
            if (timeout > TIMEOUT_NON_ATTIVO) {
                props.put("mail.smtp.connectiontimeout", timeout);
                props.put("mail.smtp.timeout", timeout);
            }

            // rimozione ssl
            props.remove("mail.smtp.socketFactory.port");
            props.remove("mail.smtp.socketFactory.class");
            props.remove("mail.smtp.socketFactory.fallback");
            props.remove("mail.smtp.starttls.enable");

            switch (mailInfo.getProtocollo()) {
                case "1":
                    // smtps
                    props.put("mail.smtp.socketFactory.port", mailInfo.getPorta());
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.ssl.checkserveridentity", "true");
                    props.put("mail.smtp.socketFactory.fallback", "false");
                    break;
                case "2":
                    // smtp + start_tls
                    props.put("mail.smtp.starttls.enable", "true");
                    break;
                default:
                    // caso standard di smtp
                    break;
            }

            mailSender.setJavaMailProperties(props);

            logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::getJavaMailSender.");

            return mailSender;

        }
        else {

            logger.debug("END esecuzione in ThreadPoolTaskSchedulerManager::getJavaMailSender.");

            return new JavaMailSenderImpl();
        }
    }

    private List<String> striptToEmptyList(List<String> lista) {
        return lista == null ? new ArrayList<String>() : lista;
    }

}
