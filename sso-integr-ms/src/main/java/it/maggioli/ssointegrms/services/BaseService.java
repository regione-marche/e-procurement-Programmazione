package it.maggioli.ssointegrms.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import it.appaltiecontratti.security.maggiolicaptchachecker.dto.FriendlyCaptchaSiteVerifyResponseDTO;
import it.appaltiecontratti.security.maggiolicaptchachecker.dto.ProxyConfigurationDTO;
import it.appaltiecontratti.security.maggiolicaptchachecker.exceptions.FriendlyCaptchaException;
import it.appaltiecontratti.security.maggiolicaptchachecker.services.MaggioliCaptchaCheckerService;
import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.domain.Uffint;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.UserDTO;
import it.maggioli.ssointegrms.model.ApplicationInfo;
import it.maggioli.ssointegrms.repositories.UserRepository;
import it.maggioli.ssointegrms.utils.DtoMapper;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Value("${application.captcha.enabled}")
    private boolean captchaEnabled;

    @Value("${application.captcha.friendlyCaptchaSiteVerifyApi:#{null}}")
    private String friendlyCaptchaSiteVerifyApi;

    @Value("${application.enableProxy}")
    protected boolean enableProxy;
    @Value("${http_proxy:#{'default'}}")
    protected String httpProxy;
    @Value("${no_proxy:#{'default'}}")
    protected String noProxy;
    @Value("${https_proxy:#{'default'}}")
    protected String httpsProxy;
    @Value("${application.connectionTimeout}")
    protected Long connectionTimeout;

    protected static final ObjectMapper OBJECT_MAPPER;

    @Autowired
    protected DtoMapper dtoMapper = null;

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

    protected String getLocalClientIdByMimsClientId(final String mimsClientId) {

        if (StringUtils.isBlank(mimsClientId))
            return null;

        ApplicationInfo applicationInfo = OneGatewayAppConstants.getApplicationInfoByMimsClientId(mimsClientId);

        if (applicationInfo == null)
            return null;

        return applicationInfo.getLocalClientId();
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

    /**
     * Metodo per ritornare la lista dei codici delle Uffints associate ad un utente
     *
     * @param user L'utente
     * @return La lista dei codici delle Uffints associate ad un utente
     */
    protected List<String> getUffintsCodeinListFromUser(User user) {
        if (user == null)
            return null;
        return user.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList());
    }

    /**
     * Metodo per verificare la soluzione del captcha
     *
     * @param captchaSolution Soluzione del captcha
     * @return Booleano di success
     */
    protected boolean verifyCaptchaSolution(final String captchaSolution) {

        // Se il captcha non è abilitato, la validazione ha sempre successo
        if (!captchaEnabled)
            return true;

        // Se la soluzione del captcha è vuota o null, la validazione fallisce
        if (StringUtils.isBlank(captchaSolution))
            return false;

        // Verifica la soluzione del captcha utilizzando il servizio FriendlyCaptcha
        FriendlyCaptchaSiteVerifyResponseDTO responseDTO = MaggioliCaptchaCheckerService.verifyFriendlyCaptchaChallengeSuccess(
                friendlyCaptchaSiteVerifyApi,
                captchaSolution,
                createProxyConfiguration()
        );

        // Se la risposta è null, la validazione fallisce
        if (responseDTO == null)
            return false;

        // Se la verifica non ha avuto successo e c'è un messaggio di errore,
        // lancia un'eccezione FriendlyCaptchaException con il messaggio di errore
        if (!responseDTO.isSuccess() && StringUtils.isNotBlank(responseDTO.getError())) {
            throw new FriendlyCaptchaException(responseDTO.getError());
        }

        // Restituisce il risultato della verifica (la validazione ha successo se isSuccess() è true, altrimenti fallisce)
        return responseDTO.isSuccess();
    }

    private ProxyConfigurationDTO createProxyConfiguration() {
        ProxyConfigurationDTO proxyConfigurationDTO = new ProxyConfigurationDTO();
        proxyConfigurationDTO.setEnableProxy(enableProxy);
        proxyConfigurationDTO.setHttpProxy(httpProxy);
        proxyConfigurationDTO.setHttpsProxy(httpsProxy);
        proxyConfigurationDTO.setNoProxy(noProxy);
        proxyConfigurationDTO.setConnectionTimeout(connectionTimeout);
        return proxyConfigurationDTO;
    }

    protected void addOUToListIfNotPresent(List<String> list, String value) {
        if (!list.contains(value))
            list.add(value);
    }

    protected String generateNewAuthorizationCode() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        return uuid1.toString().replaceAll("-", "") + uuid2.toString().replaceAll("-", "");
    }
}
