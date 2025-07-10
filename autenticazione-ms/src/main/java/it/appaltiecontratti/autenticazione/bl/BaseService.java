package it.appaltiecontratti.autenticazione.bl;

import it.appaltiecontratti.security.maggiolicaptchachecker.dto.FriendlyCaptchaSiteVerifyResponseDTO;
import it.appaltiecontratti.security.maggiolicaptchachecker.dto.ProxyConfigurationDTO;
import it.appaltiecontratti.security.maggiolicaptchachecker.exceptions.FriendlyCaptchaException;
import it.appaltiecontratti.security.maggiolicaptchachecker.services.MaggioliCaptchaCheckerService;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public abstract class BaseService {

    @Value("${application.codiceProdotto}")
    protected String codiceProdotto;

    @Autowired
    protected UserManager userManager;

    @Autowired
    protected WConfigManager wConfigManager;

    @Autowired
    protected WLogEventiService wLogEventiService;

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

    private static final Logger LOGGER = LogManager.getLogger(BaseService.class);

    protected static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

    protected static Date getExpDate(long nowMillis, long ttlMillis) {
        return new Date(nowMillis + ttlMillis);
    }

    /**
     * Metodo di utilita' per decodificare una chiave di cifratura jwt
     *
     * @param encodedKey chiave cifrata
     * @return la chiave decifrata
     * @throws CriptazioneException
     */
    protected byte[] decodeKey(final String encodedKey) throws CriptazioneException {
        try {
            ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, encodedKey.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return decrypt.getDatoNonCifrato();
        } catch (CriptazioneException e) {
            LOGGER.error("Errore in fase di decrypt della chiave hash per la verifica token SSO", e);
            throw e;
        }
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

    /**
     * Crea e configura un oggetto ProxyConfigurationDTO con le impostazioni del proxy.
     *
     * @return ProxyConfigurationDTO configurato con le impostazioni del proxy correnti.
     */
    private ProxyConfigurationDTO createProxyConfiguration() {
        // Crea una nuova istanza di ProxyConfigurationDTO
        ProxyConfigurationDTO proxyConfigurationDTO = new ProxyConfigurationDTO();

        // Imposta se il proxy è abilitato o meno
        proxyConfigurationDTO.setEnableProxy(enableProxy);

        // Imposta l'indirizzo del proxy HTTP
        proxyConfigurationDTO.setHttpProxy(httpProxy);

        // Imposta l'indirizzo del proxy HTTPS
        proxyConfigurationDTO.setHttpsProxy(httpsProxy);

        // Imposta gli indirizzi che dovrebbero bypassare il proxy
        proxyConfigurationDTO.setNoProxy(noProxy);

        // Imposta il valore del timeout di connessione
        proxyConfigurationDTO.setConnectionTimeout(connectionTimeout);

        // Restituisce l'oggetto ProxyConfigurationDTO configurato
        return proxyConfigurationDTO;
    }
}
