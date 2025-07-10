package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.dto.AuthenticationDTO;
import it.maggioli.ssointegrms.dto.WLogEventiDTO;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.UnexpectedErrorException;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.MTokenService;
import it.maggioli.ssointegrms.services.general.WLogEventiService;
import it.maggioli.ssointegrms.utils.LogEventiUtils;
import it.maggioli.ssointegrms.utils.MToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
@Service
public class MTokenServiceImpl extends BaseService implements MTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MTokenServiceImpl.class);

    @Value("${application.internalAuthentication.caCertificateLocation}")
    private String caCertificateLocation;

    @Autowired
    private WLogEventiService wLogEventiService;

    @Override
    public boolean loginMToken(final AuthenticationDTO authenticationDTO) {
        if (authenticationDTO == null)
            throw new IllegalArgumentException("authenticationAdminDTO null");

        LOGGER.debug("Execution start {}::loginMToken", getClass().getSimpleName());

        int livelloEvento = LogEventiUtils.LIVELLO_INFO;
        String logEventoDescr = null;
        String errorMessage = null;

        String utente = null;
        String email = null;

        try {
            InputStream caCertificate = loadCaCertificate();

            MToken client = new MToken(caCertificate);

            if (StringUtils.isNotBlank(authenticationDTO.getCertificato())) {
                client.setMtokenCredentials(authenticationDTO.getCertificato());
            }

            if (client.isEsito()) {
                utente = StringUtils.stripToNull(client.getUtente());
                email = StringUtils.stripToNull(client.getEmail());

                logEventoDescr = "Login mediante Mtoken da parte dell'amministratore impersonato da " + utente + " (" + email + ") " +
                        "con motivazione di accesso o ticket: " + authenticationDTO.getMotivazione() + " " +
                        "con SHA1 del certificato " + client.getSha1();
            } else {
                livelloEvento = LogEventiUtils.LIVELLO_ERROR;
                logEventoDescr = "Tentativo di login dell'amministratore non validato";
            }

        } catch (Exception e) {
            livelloEvento = LogEventiUtils.LIVELLO_ERROR;
            errorMessage = e.getMessage();
            logEventoDescr = "Tentativo di login dell'amministratore non validato";
        }

        final WLogEventiDTO wLogEventiDTO = LogEventiUtils.createMTokenLogin(logEventoDescr, authenticationDTO.getIpAddress(), codiceProdotto);
        wLogEventiDTO.setLivelloEvento(livelloEvento);
        wLogEventiDTO.setErrorMessage(errorMessage);

        wLogEventiService.insertLogEvent(wLogEventiDTO);

        return livelloEvento == LogEventiUtils.LIVELLO_INFO;
    }

    private InputStream loadCaCertificate() {
        try {
            ClassPathResource cpr = super.loadResourceFromClasspath(caCertificateLocation);
            return cpr.getInputStream();
        } catch (IOException e) {
            LOGGER.error("Errore durante il caricamento del CA Certificate", e);
            throw new UnexpectedErrorException(e);
        }
    }
}
