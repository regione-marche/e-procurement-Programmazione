package it.appaltiecontratti.gestionereportsms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.dto.UserDTO;
import it.appaltiecontratti.gestionereportsms.repositories.UserRepository;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;

/**
 * Classe astratta che racchiude i metodi comuni
 *
 * @author Cristiano Perin
 */
public abstract class BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    protected static final String USER_LOGIN = "USER_LOGIN";

    @Value("${application.codiceProdotto}")
    protected String codiceProdotto;

    @Value("${application.currentLoginMode}")
    protected String currentLoginMode;

    protected static final ObjectMapper OBJECT_MAPPER;
    
    @Autowired
    protected UserRepository userRepository;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    protected String generateUUID() {
        return UUID.randomUUID().toString();
    }

    protected ClassPathResource loadResourceFromClasspath(final String location) {
        ClassPathResource cpr = new ClassPathResource(location);
        return cpr;
    }  

    protected boolean isUserAdministrator(final String syspwbow) {

        if (StringUtils.isBlank(syspwbow))
            return false;

        return syspwbow.contains(AppConstants.OU_AMMINISTRATORE);
    }

    protected boolean isPasswordSicuraEnabled(final String syspwbow) {

        if (StringUtils.isBlank(syspwbow))
            return false;

        return syspwbow.contains(AppConstants.OU_UTENTE_PASSWORD_SICURA);
    }

    protected String getValoreCifrato(final String valoreNonCifrato) throws CriptazioneException {

        if (StringUtils.isBlank(valoreNonCifrato))
            throw new IllegalArgumentException("valoreNonCifrato null");

        try {
            ICriptazioneByte crypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, valoreNonCifrato.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

            return new String(crypt.getDatoCifrato());
        } catch (CriptazioneException e) {
            LOGGER.error("Errore in fase di crittazione del valore", e);
            throw e;
        }
    }

    protected String getValoreNonCifrato(final String valoreCifrato) throws CriptazioneException {

        if (StringUtils.isBlank(valoreCifrato))
            throw new IllegalArgumentException("valoreCifrato null");

        try {
            ICriptazioneByte crypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, valoreCifrato.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return new String(crypt.getDatoNonCifrato());
        } catch (CriptazioneException e) {
            LOGGER.error("Errore durante la decodifica del valore cifrato", e);
            throw e;
        }
    }

    protected MacAlgorithm getSignatureAlgorithm(final String signatureAlgorithm) {
        if (StringUtils.isNotBlank(signatureAlgorithm) && signatureAlgorithmAllowed(signatureAlgorithm)) {
            return (MacAlgorithm) Jwts.SIG.get().forKey(signatureAlgorithm);
        }
        return (MacAlgorithm) Jwts.SIG.get().forKey("HS512");
    }

    private boolean signatureAlgorithmAllowed(final String signatureAlgorithm) {
        return "HS256".equals(signatureAlgorithm) || "HS384".equals(signatureAlgorithm) || "HS512".equals(signatureAlgorithm);
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

    protected boolean isUserAbilitatoGestioneUtentiReadonly(final UserDTO userDTO) {

        if (userDTO == null)
            return false;

        User user = this.userRepository.findById(userDTO.getSyscon()).orElse(null);

        if (user == null)
            return false;

        if (StringUtils.isBlank(user.getSyspwbou()))
            return false;

        List<String> listaOpzioniUtente = getListaOpzioniUtente(user.getSyspwbou());
        return listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_COMPLETA)
                && listaOpzioniUtente.contains(AppConstants.OU_GESTIONE_UTENTI_OU12);
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
}
