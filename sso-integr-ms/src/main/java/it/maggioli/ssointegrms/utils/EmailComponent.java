package it.maggioli.ssointegrms.utils;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.WMail;
import it.maggioli.ssointegrms.repositories.WMailRepository;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 */
@Component
public class EmailComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailComponent.class);

    private static final String DEFAULT_MAIL_PASSWORD = ".";

    protected static final String MESSAGE_ID_REFERENCE_HEADER_NAME = "X-Riferimento-Message-ID";

    private static final int DEFAULT_TIMEOUT_MILLISEC = 5000;
    private static final int TIMEOUT_NON_ATTIVO = 0;

    @Value("${application.codiceProdotto}")
    private String CONFIG_CODICE_APP;

    @Autowired
    private WMailRepository wMailRepository;

    public JavaMailSender getJavaMailSender() {
        return getJavaMailSender(AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD);
    }

    public JavaMailSender getJavaMailSender(final String idCfg) {

        LOGGER.debug("Execution start EmailComponent::getJavaMailSender");

        WMail mailInfo = wMailRepository.getInfoMailServer(CONFIG_CODICE_APP, idCfg);
        return getJavaMailSender(mailInfo);
    }

    public JavaMailSender getJavaMailSender(final WMail mailInfo) {
        int timeout = DEFAULT_TIMEOUT_MILLISEC;
        String password = DEFAULT_MAIL_PASSWORD;
        if (mailInfo != null) {
            if (mailInfo.getPassword() != null && !"".equals(mailInfo.getPassword())) {
                try {
                    ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                            FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, mailInfo.getPassword().getBytes(),
                            ICriptazioneByte.FORMATO_DATO_CIFRATO);

                    password = new String(decrypt.getDatoNonCifrato());
                } catch (CriptazioneException e) {

                }
            }

            if (StringUtils.isNotBlank(mailInfo.getTimeout())) {
                timeout = Integer.parseInt(mailInfo.getTimeout());
            }

            String username = StringUtils.isNotBlank(mailInfo.getIdUser()) ? mailInfo.getIdUser() : mailInfo.getMailMittente();

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailInfo.getServer());
            mailSender.setPort(mailInfo.getPorta() == null ? 25 : Integer.parseInt(mailInfo.getPorta()));
            mailSender.setDefaultEncoding("true");
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();

            switch (mailInfo.getProtocollo()) {
                case "1":
                    // smtps
                case "2":
                    // smtp + start_tls
                    props = System.getProperties();
                    break;
            }

            props.put("mail.transport.protocol", "smtp");

            // rimozione ssl
            props.remove("mail.smtp.auth");
            props.remove("mail.smtp.starttls.enable");
            props.remove("mail.smtp.socketFactory.class");
            props.remove("mail.smtp.socketFactory.fallback");

            // timeout
            props.remove("mail.smtp.connectiontimeout");
            props.remove("mail.smtp.timeout");
            if (timeout > TIMEOUT_NON_ATTIVO) {
                props.put("mail.smtp.connectiontimeout", timeout);
                props.put("mail.smtp.timeout", timeout);
            }

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
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    break;
                default:
                    // caso standard di smtp
                    break;
            }

            mailSender.setJavaMailProperties(props);

            return mailSender;
        } else {
            return new JavaMailSenderImpl();
        }
    }

    public String getEmailMittente() {
        return getEmailMittente(AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD);
    }

    public String getEmailMittente(final String idCfg) {
        return wMailRepository.getEmailMittente(CONFIG_CODICE_APP, idCfg);
    }
}
