package it.appaltiecontratti.programmi.rl.beans;

import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

public class ConfigServiceRL {

    private static final Logger logger = LogManager.getLogger(ConfigServiceRL.class);

    public static final String PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA="programmazione.integrazioneRLo";
    public static final String PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_SCOPE="programmazione.integrazioneRLo.scope";
    public static final String PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_URLAUT="programmazione.integrazioneRLo.urlAut";
    public static final String PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_URL="programmazione.integrazioneRLo.url";
    public static final String PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_KEY="programmazione.integrazioneRLo.key";
    public static final String PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_SECRET="programmazione.integrazioneRLo.secret";

    private String scope;
    private String key;
    private String secret;
    private String endpointApi;
    private String authServerUrl;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthServerUrl() {
        return authServerUrl;
    }

    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }

    public String getEndpointApi() {
        return endpointApi;
    }

    public void setEndpointApi(String endpointApi) {
        this.endpointApi = endpointApi;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static ConfigServiceRL build(WConfigManager wConfigManager) {
        ConfigServiceRL configServiceRL = new ConfigServiceRL();
        configServiceRL.setScope(wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_SCOPE));
        configServiceRL.setKey(wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_KEY));
        String encSecret=wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_SECRET);
        if (StringUtils.hasText(encSecret)) configServiceRL.setSecret(getEncryptedPwd(encSecret));
        configServiceRL.setEndpointApi(wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_URL));
        configServiceRL.setAuthServerUrl(wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_REGIONE_LOMBARDIA_URLAUT));
        return configServiceRL;
    }

    private static String getEncryptedPwd(String criptedKey) {
        try {
            ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY,
                    criptedKey.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return new String(decrypt.getDatoNonCifrato(), StandardCharsets.UTF_8);
        } catch (CriptazioneException e) {
            logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "ConfigServiceRL{" +
                "scope='" + scope + '\'' +
                ", key='" + key + '\'' +
                ", secret='" + secret + '\'' +
                ", endpointApi='" + endpointApi + '\'' +
                ", authServerUrl='" + authServerUrl + '\'' +
                '}';
    }
}
