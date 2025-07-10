package it.maggioli.ssointegrms.utils;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.services.general.WConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cristiano Perin
 */
@Component
public class FileUtils {

    @Autowired
    private WConfigService wConfigService;

    /**
     * Utility per il controllo della validit&agrave; dell'estensione di un file
     * secondo la configurazione impostata da applicativo.
     *
     * @param nomeFile nome del file da controllare
     * @return true se la configurazione non contiene valori e quindi qualsiasi
     * estensione del file &egrave; ammessa, oppure l'estensione del file in
     * input rientra in una delle estensioni ammesse, false altrimenti
     */
    public boolean isEstensioneFileAmmessa(String nomeFile) {
        boolean esito = true;
        String estensioniAmmesse = StringUtils
                .stripToNull(wConfigService.getConfigurationValue(AppConstants.ESTENSIONI_AMMESSE));
        if (estensioniAmmesse != null) {
            // si controlla se il nome del file caricato rientra in una delle estensioni
            // ammesse da configurazione
            if (nomeFile != null) {
                String estensione = StringUtils.substringAfterLast(nomeFile, ".");
                if (estensione != null) {
                    Set<String> setEstensioni = new HashSet<String>(
                            Arrays.asList(StringUtils.split(estensioniAmmesse.toUpperCase(), ';')));
                    if (!setEstensioni.contains(estensione.toUpperCase())) {
                        // l'estensione non risulta tra quelle ammesse, quindi fallisco il controllo
                        esito = false;
                    }
                } else {
                    // esistono delle estensioni ammesse, il file non ha estensione
                    esito = false;
                }
            }
        }
        return esito;
    }

}
