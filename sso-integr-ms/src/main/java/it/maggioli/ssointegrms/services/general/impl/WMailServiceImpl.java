package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.Uffint;
import it.maggioli.ssointegrms.domain.WMail;
import it.maggioli.ssointegrms.domain.WMailId;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.amministrazione.MailErrorException;
import it.maggioli.ssointegrms.exceptions.amministrazione.WMailAlreadyExistsException;
import it.maggioli.ssointegrms.exceptions.amministrazione.WMailNotFoundException;
import it.maggioli.ssointegrms.exceptions.amministrazione.WMailTimeoutNotANumberException;
import it.maggioli.ssointegrms.repositories.UffintRepository;
import it.maggioli.ssointegrms.repositories.WMailRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.WConfigService;
import it.maggioli.ssointegrms.services.general.WMailService;
import it.maggioli.ssointegrms.utils.EmailComponent;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class WMailServiceImpl extends BaseService implements WMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WMailServiceImpl.class);

    @Autowired
    private WMailRepository wMailRepository;

    @Autowired
    private UffintRepository uffintRepository;

    @Autowired
    private EmailComponent emailComponent;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private WConfigService wConfigService;

    @Override
    public List<WMailDTO> loadListaWMail() {
        LOGGER.debug("Execution start WMailServiceImpl::loadListaWMail");

        List<WMail> lista = wMailRepository.findAll(Sort.by(Sort.Direction.ASC, "id.codApp", "id.idCfg"));

        if (lista != null) {
            List<WMailDTO> listaDTO = lista.stream().map(m -> getWMailDTOFromWMail(m)).collect(Collectors.toList());
            return listaDTO;
        }

        return null;
    }

    @Override
    public WMailDTO getWMail(final String idCfg) {

        LOGGER.debug("Execution start WMailServiceImpl::getWMail for idCfg [ {} ]", idCfg);

        if (StringUtils.isBlank(idCfg))
            throw new IllegalArgumentException("idCfg null");

        WMailId id = new WMailId(codiceProdotto, idCfg);
        WMail mail = wMailRepository.findById(id).orElse(null);

        if (mail == null) {
            LOGGER.error("WMail non presente per idCfg [ {} ]", idCfg);

            throw new WMailNotFoundException();
        }

        return getWMailDTOFromWMail(mail);
    }

    @Override
    public WMailInizDTO getInizNewWMail() {

        LOGGER.debug("Execution start WMailServiceImpl::getInizNewWMail");

        WMailInizDTO dto = new WMailInizDTO();
        List<ValoreTabellato> listaConfigurazioni = new ArrayList<>();

        WMail std = wMailRepository.getInfoMailServer(codiceProdotto,
                AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD);

        if (std == null) {
            ValoreTabellato tabellatoSTD = new ValoreTabellato();
            tabellatoSTD.setCodice(AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD);
            tabellatoSTD.setDescrizione(AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD);
            listaConfigurazioni.add(tabellatoSTD);
        }

        // load stazioni appaltanti
        List<Uffint> listaUffints = uffintRepository.findAll(Sort.by(Sort.Order.asc("denominazione")));
        if (listaUffints != null && listaUffints.size() > 0) {
            List<ValoreTabellato> listaTabellatiSA = listaUffints.stream()
                    .map(u -> new ValoreTabellato(u.getCodice(),
                            (u.getDenominazione().length() > 90)
                                    ? u.getCodice() + " - " + u.getDenominazione().substring(0, 90) + "..."
                                    : u.getCodice() + " - " + u.getDenominazione()))
                    .collect(Collectors.toList());
            listaConfigurazioni.addAll(listaTabellatiSA);
        }

        WMail conversazioni = wMailRepository.getInfoMailServer(codiceProdotto,
                AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD_CONV);

        if (conversazioni == null) {
            ValoreTabellato tabellatoConversazioni = new ValoreTabellato();
            tabellatoConversazioni.setCodice(AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD_CONV);
            tabellatoConversazioni.setDescrizione(AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD_CONV);
            listaConfigurazioni.add(tabellatoConversazioni);
        }

        dto.setListaConfigurazioni(listaConfigurazioni);
        dto.setTimeout("10000");
        dto.setDimTotaleAllegati(5L);
        dto.setDelay(20000L);

        return dto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WMailDTO insertWMail(final WMailEditDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start WMailServiceImpl::insertWMail for form [ {} ]", form);

        String idCfg = form.getIdCfg().trim();

        WMail found = wMailRepository.getInfoMailServer(codiceProdotto, idCfg);

        if (found != null) {
            LOGGER.error("WMail gia' presente per idCfg [ {} ]", idCfg);

            throw new WMailAlreadyExistsException();
        }

        WMailId id = new WMailId(codiceProdotto, idCfg);

        WMail toInsert = new WMail();
        toInsert.setId(id);

        toInsert.setServer(form.getServer().trim());
        String porta = form.getPorta() == null ? "25" : String.valueOf(form.getPorta());
        toInsert.setPorta(porta);
        toInsert.setProtocollo(form.getProtocollo());
        String timeout = StringUtils.isBlank(form.getTimeout()) ? "5000" : form.getTimeout();
        try {
            Long.parseLong(timeout);
        } catch (NumberFormatException e) {
            LOGGER.error("Il campo timeout non e' un numero");
            throw new WMailTimeoutNotANumberException();
        }
        toInsert.setTimeout(timeout);
        String tracciaturaMessaggi = StringUtils.isBlank(form.getTracciaturaMessaggi()) ? "2"
                : form.getTracciaturaMessaggi().trim();
        toInsert.setTracciaturaMessaggi(tracciaturaMessaggi);
        toInsert.setMailMittente(form.getMailMittente().trim());
        toInsert.setIdUser(form.getIdUser());
        String dimTotaleAllegati = form.getDimTotaleAllegati() != null ? String.valueOf(form.getDimTotaleAllegati())
                : null;
        toInsert.setDimTotaleAllegati(dimTotaleAllegati);
        String delay = form.getDelay() != null ? String.valueOf(form.getDelay()) : null;
        toInsert.setDelay(delay);

        toInsert = wMailRepository.save(toInsert);

        return getWMailDTOFromWMail(toInsert);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WMailDTO updateWMail(final WMailEditDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start WMailServiceImpl::updateWMail for form [ {} ]", form);

        String idCfg = form.getIdCfg().trim();

        WMail toUpdate = wMailRepository.getInfoMailServer(codiceProdotto, idCfg);

        if (toUpdate == null) {
            LOGGER.error("WMail non presente per idCfg [ {} ]", idCfg);

            throw new WMailNotFoundException();
        }

        toUpdate.setServer(form.getServer().trim());
        String porta = form.getPorta() == null ? "25" : String.valueOf(form.getPorta());
        toUpdate.setPorta(porta);
        toUpdate.setProtocollo(form.getProtocollo());
        String timeout = StringUtils.isBlank(form.getTimeout()) ? "5000" : form.getTimeout();
        try {
            Long.parseLong(timeout);
        } catch (NumberFormatException e) {
            LOGGER.error("Il campo timeout non e' un numero");
            throw new WMailTimeoutNotANumberException();
        }
        toUpdate.setTimeout(timeout);
        String tracciaturaMessaggi = StringUtils.isBlank(form.getTracciaturaMessaggi()) ? "2"
                : form.getTracciaturaMessaggi().trim();
        toUpdate.setTracciaturaMessaggi(tracciaturaMessaggi);
        toUpdate.setMailMittente(form.getMailMittente().trim());
        toUpdate.setIdUser(form.getIdUser());
        String dimTotaleAllegati = form.getDimTotaleAllegati() != null ? String.valueOf(form.getDimTotaleAllegati())
                : null;
        toUpdate.setDimTotaleAllegati(dimTotaleAllegati);
        String delay = form.getDelay() != null ? String.valueOf(form.getDelay()) : null;
        toUpdate.setDelay(delay);

        toUpdate = wMailRepository.save(toUpdate);

        return getWMailDTOFromWMail(toUpdate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteWMail(final String idCfg) {

        LOGGER.debug("Execution start WMailServiceImpl::deleteWMail for idCfg [ {} ]", idCfg);

        if (StringUtils.isBlank(idCfg))
            throw new IllegalArgumentException("idCfg null");

        WMailId id = new WMailId(codiceProdotto, idCfg);
        WMail found = wMailRepository.findById(id).orElse(null);

        if (found == null) {
            LOGGER.error("WMail non presente per idCfg [ {} ]", idCfg);

            throw new WMailNotFoundException();
        }

        wMailRepository.deleteById(id);

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateWMailPassword(final WMailEditPasswordDTO form) throws CriptazioneException {

        if (form == null)
            throw new IllegalArgumentException("form null");

        String idCfg = form.getIdCfg().trim();

        LOGGER.debug("Execution start WMailServiceImpl::updateWMailPassword for idCfg [ {} ]", idCfg);

        WMailId id = new WMailId(codiceProdotto, idCfg);
        WMail found = wMailRepository.findById(id).orElse(null);

        if (found == null) {
            LOGGER.error("WMail non presente per idCfg [ {} ]", idCfg);

            throw new WMailNotFoundException();
        }

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueChangePassword = true;
        List<String> errorMessages = new ArrayList<>();

        String cleanVecchiaPassword = StringUtils.stripToNull(form.getVecchiaPassword());
        String cleanNuovaPassword = StringUtils.stripToNull(form.getNuovaPassword());
        String cleanConfermaNuovaPassword = StringUtils.stripToNull(form.getConfermaNuovaPassword());

        if ((cleanVecchiaPassword == null && cleanNuovaPassword == null) || (cleanVecchiaPassword != null
                && cleanNuovaPassword != null && cleanVecchiaPassword.equals(cleanNuovaPassword))) {

            continueChangePassword = false;
            errorMessages.add(AppConstants.GESTIONE_WMAIL_OLD_NEW_PASSWORD_MATCH);

        }

        if (StringUtils.isNotBlank(found.getPassword())) {
            // check vecchia password

            if (StringUtils.isBlank(cleanVecchiaPassword)) {

                continueChangePassword = false;
                errorMessages.add(AppConstants.GESTIONE_WMAIL_OLD_PASSWORD_EMPTY);

            } else {
                String vecchiaPasswordCifrata = super.getValoreCifrato(form.getVecchiaPassword());

                if (!vecchiaPasswordCifrata.equals(found.getPassword())) {

                    continueChangePassword = false;
                    errorMessages.add(AppConstants.GESTIONE_WMAIL_OLD_PASSWORD_MISMATCH);

                }
            }
        }

        if ((cleanNuovaPassword == null && cleanConfermaNuovaPassword != null)
                || (cleanNuovaPassword != null && cleanConfermaNuovaPassword == null)
                || (cleanNuovaPassword != null && cleanConfermaNuovaPassword != null
                && !cleanNuovaPassword.equals(cleanConfermaNuovaPassword))) {

            continueChangePassword = false;
            errorMessages.add(AppConstants.GESTIONE_WMAIL_NEW_PASSWORD_MISMATCH);

        }

        if (continueChangePassword) {

            String nuovaPasswordCifrata = cleanNuovaPassword == null ? null
                    : super.getValoreCifrato(cleanNuovaPassword);
            found.setPassword(nuovaPasswordCifrata);

            found = wMailRepository.save(found);

            response.setResponse(getWMailDTOFromWMail(found));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;

    }

    @Override
    public boolean testSendEmail(final WMailTestSendDTO form) {

        String nomeMittente = wConfigService.getConfigurationValue(AppConstants.TITOLO_APPLICATIVO);

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start WMailServiceImpl::testSendEmail for form [ {} ]", form);

        String idCfg = StringUtils.stripToNull(form.getIdCfg());
        String sendEmail = StringUtils.stripToNull(form.getTestEmail());

        if (StringUtils.isBlank(idCfg)) {

            LOGGER.error("idCfg null");

            throw new IllegalArgumentException("idCfg null");
        }

        if (StringUtils.isBlank(sendEmail)) {

            LOGGER.error("test email null");

            throw new IllegalArgumentException("test email null");
        }

        String oggettoMail = "TEST invio mail";

        try {
            String emailMittente = emailComponent.getEmailMittente(idCfg);
            JavaMailSender mailSender = emailComponent.getJavaMailSender(idCfg);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(new InternetAddress(emailMittente, nomeMittente));

            Context context = new Context();
            String html = templateEngine.process("email/testsend.html", context);
            helper.setText(html, true);

            List<String> receivers = new ArrayList<String>();
            receivers.add(sendEmail);

            List<String> receiverCC = new ArrayList<String>();
            List<String> receiversCCn = new ArrayList<String>();

            helper.setTo((String[]) receivers.toArray(new String[0]));
            helper.setCc((String[]) receiverCC.toArray(new String[0]));
            helper.setBcc((String[]) receiversCCn.toArray(new String[0]));

            helper.setSubject(oggettoMail);
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            LOGGER.warn("Errore durante l'invio della mail di test", e);
            throw new MailErrorException();
        }
    }

    private WMailDTO getWMailDTOFromWMail(final WMail mail) {

        WMailDTO dto = new WMailDTO();

        dto.setCodApp(mail.getId().getCodApp());
        dto.setIdCfg(mail.getId().getIdCfg());

        if (!AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD.equals(mail.getId().getIdCfg())
                && !AppConstants.PROP_CONFIGURAZIONE_MAIL_STANDARD_CONV
                .equals(mail.getId().getIdCfg())) {
            // set idCfgReadonly with uffint info
            Uffint uffint = uffintRepository.findById(mail.getId().getIdCfg()).orElse(null);
            if (uffint != null) {
                String idCfg = (uffint.getDenominazione().length() > 90)
                        ? mail.getId().getIdCfg() + " - " + uffint.getDenominazione().substring(0, 90) + "..."
                        : mail.getId().getIdCfg() + " - " + uffint.getDenominazione();
                dto.setIdCfgReadonly(idCfg);
            }
        } else {
            dto.setIdCfgReadonly(dto.getIdCfg());
        }

        dto.setServer(mail.getServer());
        dto.setPorta(mail.getPorta());
        dto.setProtocollo(mail.getProtocollo());
        dto.setTimeout(mail.getTimeout());
        dto.setTracciaturaMessaggi(mail.getTracciaturaMessaggi());
        dto.setMailMittente(mail.getMailMittente());
        dto.setIdUser(mail.getIdUser());
        dto.setDimTotaleAllegati(mail.getDimTotaleAllegati());
        dto.setDelay(mail.getDelay());
        boolean passwordImpostata = StringUtils.isNotBlank(mail.getPassword());
        dto.setPasswordImpostata(passwordImpostata);

        return dto;
    }

}
