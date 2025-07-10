package it.appaltiecontratti.autenticazione.bl;

import it.appaltiecontratti.autenticazione.entity.GDPRCheck2DTO;
import it.appaltiecontratti.autenticazione.entity.UserAccountForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since feb 12, 2024
 */
@Component
public class GDPRManager extends BaseService {
    private static final Logger LOGGER = LogManager.getLogger(GDPRManager.class);

    private static final int LUNGHEZZA_MINIMA_PASSWORD_USER = 8;
    // Suppress 'PASSWORD' detected in this expression, review this potentially hard-coded password.
    @SuppressWarnings("java:S2068")
    private static final String CARATTERI_AMMESSI_PASSWORD = " ~#\"$%&'()*+,-./0123456789:;<=>?!@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_abcdefghijklmnopqrstuvwxyz";
    private static final Pattern PATTERN_MINUSCOLE = Pattern.compile("[a-z]+");
    private static final Pattern PATTERN_MAIUSCOLE = Pattern.compile("[A-Z]+");
    private static final Pattern PATTERN_NUMERI = Pattern.compile("[0-9]+");
    private static final String PATTERN_CARATTERI_SPECIALI = "~#\"$%&'()*+,-./:;<=>?!@[\\]^_";
    private static final int NUMERO_MASSIMO_CARATTERI_RIPETUTI = 4;

    /**
     * GDPR Check 1
     * <p>
     * Lunghezza Password: Deve essere necessario utilizzare per tutte le utenze una
     * password con lunghezza minima di 8 caratteri, 14 per quelle amministrative
     */
    public boolean executeCheck1(final UserAccountForm user) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRManager::executeCheck1 for username [ {} ]", user.getCodiceFiscaleLogin());

        // check a 8
        return user.getPassword().length() >= LUNGHEZZA_MINIMA_PASSWORD_USER;
    }

    /**
     * GDPR Check 2
     * <p>
     * Fattori controllati: sono richieste password (per utenti normali e
     * amministratori) che prevedano la presenza contemporanea di tutti i seguenti
     * fattori:
     * <p>
     * 1) Almeno una maiuscola
     * <p>
     * 2) Almeno una minuscola
     * <p>
     * 3) Almeno un numero
     * <p>
     * 4) Almeno un carattere speciale
     */
    public GDPRCheck2DTO executeCheck2(final UserAccountForm user) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRManager::executeCheck2 for username [ {} ]",
                user.getCodiceFiscaleLogin());

        GDPRCheck2DTO pass = new GDPRCheck2DTO();

        final String password = user.getPassword();

        boolean passwordContainsAllowedCharacters = checkIfPasswordContainsAllowedCharacters(password);
        boolean passwordContainsLowerCase = containsLowerCase(password);
        boolean passwordContainsUpperCase = containsUpperCase(password);
        boolean passwordContainsNumber = containsNumbers(password);
        boolean passwordContainsSpecialCharacters = containsSpecialCharacters(password);

        pass.setAllowedCharacters(passwordContainsAllowedCharacters);
        pass.setLowerCaseCharacters(passwordContainsLowerCase);
        pass.setUpperCaseCharacters(passwordContainsUpperCase);
        pass.setNumberCharacters(passwordContainsNumber);
        pass.setSpecialCharacters(passwordContainsSpecialCharacters);

        return pass;
    }

    /**
     * GDPR Check 3B
     *
     * Controllo contenuto password: Inserire un controllo di non presenza di almeno
     * 4 caratteri consecutivi presenti nella LOGIN (mantenere parametrizzabile il
     * numero di caratteri consecutivi da controllare). Inoltre, solo se non troppo
     * oneroso, aggiungere un file di configurazione, o una tabella, o altro nel
     * quale impostare un elenco di password non accettabili.
     */
    public boolean executeCheck3B(final UserAccountForm user) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRManager::executeCheck3B for username [ {} ]", user.getCodiceFiscaleLogin());

        boolean pass = false;

        final String username = user.getCodiceFiscaleLogin().toLowerCase();
        final String password = user.getPassword().toLowerCase();

        List<String> combinations = getNPartOfString(username, NUMERO_MASSIMO_CARATTERI_RIPETUTI);

        boolean found = false;

        for (int i = 0; i < combinations.size() && !found; i++) {
            String combination = combinations.get(i);
            if (password.contains(combination))
                found = true;
        }

        pass = !found;

        return pass;
    }

    private static boolean checkIfPasswordContainsAllowedCharacters(final String password) {

        if (StringUtils.isBlank(password))
            return false;

        boolean wrongCharacter = false;
        for (int i = 0; i < password.length() && !wrongCharacter; i++) {
            char c = password.charAt(i);
            if (!CARATTERI_AMMESSI_PASSWORD.contains(String.valueOf(c)))
                wrongCharacter = true;
        }

        return !wrongCharacter;
    }

    private static boolean containsLowerCase(final String str) {
        return PATTERN_MINUSCOLE.matcher(str).find();
    }

    private static boolean containsUpperCase(final String str) {
        return PATTERN_MAIUSCOLE.matcher(str).find();
    }

    private static boolean containsNumbers(final String str) {
        return PATTERN_NUMERI.matcher(str).find();
    }

    private boolean containsSpecialCharacters(final String str) {
        boolean found = false;
        for (int i = 0; i < str.length() && !found; i++) {
            String c = String.valueOf(str.charAt(i));
            if (PATTERN_CARATTERI_SPECIALI.contains(c))
                found = true;
        }

        return found;
    }

    private List<String> getNPartOfString(final String str, final int maxCharacters) {

        if (StringUtils.isBlank(str))
            return null;

        List<String> parts = new ArrayList<>();

        if (str.length() >= maxCharacters) {
            for (int i = 0; i < str.length(); i++) {
                if (i + maxCharacters <= str.length()) {
                    String part = str.substring(i, i + maxCharacters);
                    parts.add(part);
                }
            }
        }

        return parts;
    }
}
