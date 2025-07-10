package it.maggioli.ssointegrms.services.general.impl;

import it.appaltiecontratti.security.maggiolicaptchachecker.exceptions.FriendlyCaptchaException;
import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.Eldaver;
import it.maggioli.ssointegrms.dto.RichiestaAssistenzaFormDTO;
import it.maggioli.ssointegrms.dto.RichiestaAssistenzaInitDTO;
import it.maggioli.ssointegrms.dto.RichiestaAssistenzaOggettoDTO;
import it.maggioli.ssointegrms.repositories.EldaverRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.RichiestaAssistenzaService;
import it.maggioli.ssointegrms.services.general.WConfigService;
import it.maggioli.ssointegrms.utils.EmailComponent;
import it.maggioli.ssointegrms.utils.FileUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cristiano Perin
 */
@Service
public class RichiestaAssistenzaServiceImpl extends BaseService implements RichiestaAssistenzaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RichiestaAssistenzaServiceImpl.class);

    private static final String MODO_ASSISTENZA_SERVIZIO = "1";
    private static final String MODO_ASSISTENZA_MAIL = "2";

    @Value("${application.gestioneUtenti.assistenzaAttiva:}")
    private String assistenzaAttiva;

    @Autowired
    private WConfigService wConfigService;

    @Autowired
    private EmailComponent emailComponent;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private EldaverRepository eldaverRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public boolean isRichiestaAssistenzaAttiva() {

        LOGGER.debug("Execution start RichiestaAssistenzaServiceImpl::isRichiestaAssistenzaAttiva");
        String modoAssistenza = null;
        if (!StringUtils.isEmpty(assistenzaAttiva)) {
            modoAssistenza = assistenzaAttiva;
        } else {
            modoAssistenza = wConfigService
                    .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_ABILITATA);
        }

        // TODO implementare richiesta assistenza tramite servizio
        return StringUtils.isNotBlank(modoAssistenza) && MODO_ASSISTENZA_MAIL.equals(modoAssistenza);
    }

    @Override
    public RichiestaAssistenzaInitDTO getInitRichiestaAssistenza() {

        LOGGER.debug("Execution start RichiestaAssistenzaServiceImpl::getInitRichiestaAssistenza");

        RichiestaAssistenzaInitDTO dto = new RichiestaAssistenzaInitDTO();

        // Init
        dto.setAssistenzaAttiva(false);
        dto.setAssistenzaEmail(false);
        dto.setAssistenzaTelefonica(false);

        String modoAssistenza = wConfigService
                .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_ABILITATA);

        if (StringUtils.isNotBlank(modoAssistenza) && (MODO_ASSISTENZA_SERVIZIO.equals(modoAssistenza) || MODO_ASSISTENZA_MAIL.equals(modoAssistenza))) {

            dto.setAssistenzaAttiva(true);

            if (MODO_ASSISTENZA_SERVIZIO.equals(modoAssistenza)) {
                dto.setAssistenzaTelefonica(true);
            } else if (MODO_ASSISTENZA_MAIL.equals(modoAssistenza)) {
                dto.setAssistenzaEmail(true);
            }

            String oggetto = wConfigService
                    .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_OGGETTO);
            dto.setListaOggetti(parseOggettoAssistenza(oggetto));

            String allegatoFileSize = wConfigService
                    .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_ALLEGATO_FILE_SIZE);

            if (StringUtils.isNotBlank(allegatoFileSize)) {
                dto.setAllegatoFileSize(allegatoFileSize);
            }

        }

        return dto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean postRichiestaAssistenza(final RichiestaAssistenzaFormDTO form) {

        LOGGER.debug("Execution start RichiestaAssistenzaServiceImpl::postRichiestaAssistenza for form [ {} ]", form);

        boolean valid = super.verifyCaptchaSolution(form.getCaptchaSolution());

        if (!valid) {
            String msg = "Validazione captcha non superata";
            LOGGER.error(msg);
            throw new FriendlyCaptchaException(msg);
        } else {
            String modoAssistenza = wConfigService
                    .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_ABILITATA);

            if (StringUtils.isNoneBlank(modoAssistenza) && (MODO_ASSISTENZA_SERVIZIO.equals(modoAssistenza) || MODO_ASSISTENZA_MAIL.equals(modoAssistenza))) {

                if (MODO_ASSISTENZA_SERVIZIO.equals(modoAssistenza)) {
                    // telefono
                    // per ora non implementata
                } else if (MODO_ASSISTENZA_MAIL.equals(modoAssistenza)) {
                    // mail

                    String emailAssistenza = wConfigService
                            .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_EMAIL);

                    return inviaMailRichiestaAssistenza(form, emailAssistenza);
                }

                return false;
            }
        }

        return false;
    }

    private boolean inviaMailRichiestaAssistenza(final RichiestaAssistenzaFormDTO form, final String emailAssistenza) {

        LOGGER.debug("Execution start RichiestaAssistenzaServiceImpl::inviaMailRichiestaAssistenza for form [ {} ] and email assistenza [ {} ]", form, emailAssistenza);

        String oggettoWConfig = wConfigService
                .getConfigurationValue(AppConstants.RICHIESTA_ASSISTENZA_OGGETTO);
        List<RichiestaAssistenzaOggettoDTO> listaTipologiaOggetto = parseOggettoAssistenza(oggettoWConfig);
        RichiestaAssistenzaOggettoDTO oggetto = listaTipologiaOggetto.stream()
                .filter(o -> o.getKey().equals(form.getTipologiaRichiesta())).findFirst().orElse(null);

        String oggettoMail = "Richiesta di assistenza: {0}";
        oggettoMail = MessageFormat.format(oggettoMail, oggetto != null ? oggetto.getValue() : "");
        String nominativoSa = StringUtils.isNotBlank(form.getNominativoEnteAmministrazione()) ? form.getNominativoEnteAmministrazione() : "";
        oggettoMail = nominativoSa + " - " +  oggettoMail;

        String templateFile = "email/richiestaassistenza.html";

        Map<String, Object> properties = new HashMap<>();
        properties.put("nominativoEnteAmministrazione", form.getNominativoEnteAmministrazione());
        properties.put("referenteDaContattare", form.getReferenteDaContattare());
        properties.put("email", form.getEmail());

        // check telefono
        if (StringUtils.isNotBlank(form.getTelefono())) {
            properties.put("showTelefono", true);
            properties.put("telefono", form.getTelefono());
        }

        // check descrizione
        if (StringUtils.isNotBlank(form.getDescrizione())) {
            properties.put("showDescrizione", true);
            properties.put("descrizione", form.getDescrizione());
        }

        String nomeProdotto = wConfigService.getConfigurationValue(AppConstants.TITOLO_APPLICATIVO);
        Eldaver ver = eldaverRepository.findById(codiceProdotto).orElse(null);
        String versioneProdotto = ver != null ? ver.getNumVer() : null;
        String acquirenteSW = wConfigService.getConfigurationValue(AppConstants.ACQUIRENTE_SW);

        properties.put("nomeProdotto", nomeProdotto);
        properties.put("versioneProdotto", versioneProdotto);
        properties.put("acquirenteSW", acquirenteSW);
        properties.put("osName", System.getProperty("os.name"));
        properties.put("dbName", getDB());
        properties.put("browserName", form.getBrowserName());
        properties.put("fullVersion", form.getFullVersion());
        properties.put("majorVersion", form.getMajorVersion());
        properties.put("navigatorAppName", form.getNavigatorAppName());
        properties.put("navigatorUserAgent", form.getNavigatorUserAgent());

        try {
            String emailMittente = emailComponent.getEmailMittente();
            JavaMailSender mailSender = emailComponent.getJavaMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(emailMittente, nomeProdotto));

            Context context = new Context();
            context.setVariables(properties);
            String html = templateEngine.process(templateFile, context);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(html, "text/html; charset=utf-8");

            // use a MimeMultipart as we need to handle the file attachments
            Multipart multipart = new MimeMultipart();

            List<String> receivers = new ArrayList<String>();
            receivers.add(emailAssistenza);

            // aggiungo l'allegato
            if (StringUtils.isNotBlank(form.getAllegato()) && StringUtils.isNotBlank(form.getAllegatoName())
                    && fileUtils.isEstensioneFileAmmessa(form.getAllegatoName())) {

                byte[] doc = Base64.decodeBase64(form.getAllegato());
                ByteArrayDataSource allegatoDataSource = new ByteArrayDataSource(doc, "text/plain;charset=UTF-8");

                BodyPart attachBodyPart = new MimeBodyPart();
                attachBodyPart.setDataHandler(new DataHandler(allegatoDataSource));
                attachBodyPart.setFileName(form.getAllegatoName());

                multipart.addBodyPart(attachBodyPart);
            }

            // add the message body to the mime message
            multipart.addBodyPart(messageBodyPart);

            // Put all message parts in the message
            message.setContent(multipart);

            List<String> receiverCC = new ArrayList<String>();
            List<String> receiversCCn = new ArrayList<String>();

            helper.setTo((String[]) receivers.toArray(new String[0]));
            helper.setCc((String[]) receiverCC.toArray(new String[0]));
            helper.setBcc((String[]) receiversCCn.toArray(new String[0]));

            helper.setSubject(oggettoMail);
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            LOGGER.warn("Errore durante l'invio della mail di richiesta di assistenza", e);
        }
        return false;
    }

    private List<RichiestaAssistenzaOggettoDTO> parseOggettoAssistenza(final String oggettoDaCfg) {
        List<RichiestaAssistenzaOggettoDTO> lista = new ArrayList<>();

        if (StringUtils.isNotBlank(oggettoDaCfg)) {
            String[] oggettoDaCfgDiviso = oggettoDaCfg
                    .split(AppConstants.RICHIESTA_ASSISTENZA_OGGETTO_SEPARATORE);
            for (String oggetto : oggettoDaCfgDiviso) {
                if (StringUtils.isNotBlank(oggetto)) {
                    int index = oggetto
                            .indexOf(AppConstants.RICHIESTA_ASSISTENZA_OGGETTO_SINGOLO_SEPARATORE);
                    if (index > -1) {
                        String key = oggetto.substring(0, index);
                        String value = oggetto;
                        RichiestaAssistenzaOggettoDTO dto = new RichiestaAssistenzaOggettoDTO(key, value);
                        lista.add(dto);
                    }
                }
            }
        }

        return lista;
    }

    private String getDB() {
        String propDBMS = wConfigService.getConfigurationValue(AppConstants.DATABASE);
        String db = "Oracle";
        if (AppConstants.DATABASE_SQL_SERVER.equals(propDBMS)) {
            db = "Microsoft SQL Server";
        } else if (AppConstants.DATABASE_DB2.equals(propDBMS)) {
            db = "IBM DB2";
        } else if (AppConstants.DATABASE_POSTGRES.equals(propDBMS)) {
            db = "PostgreSQL";
        }
        return db;
    }

}
