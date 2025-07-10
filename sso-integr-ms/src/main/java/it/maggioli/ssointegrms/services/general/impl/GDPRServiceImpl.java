package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.GDPRService;
import it.maggioli.ssointegrms.services.general.LoginKOService;
import it.maggioli.ssointegrms.services.general.StoUteSysService;
import it.maggioli.ssointegrms.services.general.WLogEventiService;
import it.maggioli.ssointegrms.utils.LogEventiUtils;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Cristiano Perin
 */
@Service
public class GDPRServiceImpl extends BaseService implements GDPRService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GDPRServiceImpl.class);

    private static final int LUNGHEZZA_MINIMA_PASSWORD_USER = 8;
    private static final int LUNGHEZZA_MINIMA_PASSWORD_ADMIN = 14;
    // Suppress 'PASSWORD' detected in this expression, review this potentially hard-coded password.
    @SuppressWarnings("java:S2068")
    private static final String CARATTERI_AMMESSI_PASSWORD = " ~#\"$%&'()*+,-./0123456789:;<=>?!@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_abcdefghijklmnopqrstuvwxyz";
    private static final Pattern PATTERN_MINUSCOLE = Pattern.compile("[a-z]+");
    private static final Pattern PATTERN_MAIUSCOLE = Pattern.compile("[A-Z]+");
    private static final Pattern PATTERN_NUMERI = Pattern.compile("[0-9]+");
    private static final String PATTERN_CARATTERI_SPECIALI = "~#\"$%&'()*+,-./:;<=>?!@[\\]^_";
    private static final int NUMERO_MASSIMO_CARATTERI_RIPETUTI = 4;

    @Autowired
    private LoginKOService loginKOService;

    @Autowired
    private WLogEventiService wLogEventiService;

    @Autowired
    private StoUteSysService stoUteSysService;

    /**
     * GDPR Check 1
     * <p>
     * Lunghezza Password: Deve essere necessario utilizzare per tutte le utenze una
     * password con lunghezza minima di 8 caratteri, 14 per quelle amministrative
     */
    @Override
    public boolean executeCheck1(final User user, final ChangePasswordDTO changePasswordDTO) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        if (changePasswordDTO == null)
            throw new IllegalArgumentException("changePasswordDTO null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck1 for username [ {} ]", user.getLogin());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        boolean isAdministrator = super.isUserAdministrator(user.getSyspwbou());

        if (isAdministrator) {
            // check a 14

            pass = changePasswordDTO.getNewPassword().length() >= LUNGHEZZA_MINIMA_PASSWORD_ADMIN;

        } else {
            // check a 8

            pass = changePasswordDTO.getNewPassword().length() >= LUNGHEZZA_MINIMA_PASSWORD_USER;
        }

        return pass;
    }

    @Override
    public boolean executeCheck1Insert(final String newPassword, final boolean isAdministrator, final boolean passwordSicuraEnabled) {

        if (StringUtils.isBlank(newPassword))
            throw new IllegalArgumentException("newPassword null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck1Insert");

        if (!passwordSicuraEnabled)
            return true;

        boolean pass = false;

        if (isAdministrator) {
            // check a 14

            pass = newPassword.length() >= LUNGHEZZA_MINIMA_PASSWORD_ADMIN;

        } else {
            // check a 8

            pass = newPassword.length() >= LUNGHEZZA_MINIMA_PASSWORD_USER;
        }

        return pass;
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
    @Override
    public GDPRCheck2DTO executeCheck2(final User user, final ChangePasswordDTO changePasswordDTO) {

        if (changePasswordDTO == null)
            throw new IllegalArgumentException("changePasswordDTO null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck2 for username [ {} ]",
                changePasswordDTO.getUsername());

        GDPRCheck2DTO pass = new GDPRCheck2DTO();

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou())) {
            pass.setAllowedCharacters(true);
            pass.setLowerCaseCharacters(true);
            pass.setUpperCaseCharacters(true);
            pass.setNumberCharacters(true);
            pass.setSpecialCharacters(true);
            return pass;
        }

        final String password = changePasswordDTO.getNewPassword();

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

    @Override
    public GDPRCheck2DTO executeCheck2Insert(final String newPassword, final boolean passwordSicuraEnabled) {

        if (StringUtils.isBlank(newPassword))
            throw new IllegalArgumentException("newPassword null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck2Insert");

        GDPRCheck2DTO pass = new GDPRCheck2DTO();

        if (!passwordSicuraEnabled) {
            pass.setAllowedCharacters(true);
            pass.setLowerCaseCharacters(true);
            pass.setUpperCaseCharacters(true);
            pass.setNumberCharacters(true);
            pass.setSpecialCharacters(true);
            return pass;
        }

        boolean passwordContainsAllowedCharacters = checkIfPasswordContainsAllowedCharacters(newPassword);
        boolean passwordContainsLowerCase = containsLowerCase(newPassword);
        boolean passwordContainsUpperCase = containsUpperCase(newPassword);
        boolean passwordContainsNumber = containsNumbers(newPassword);
        boolean passwordContainsSpecialCharacters = containsSpecialCharacters(newPassword);

        pass.setAllowedCharacters(passwordContainsAllowedCharacters);
        pass.setLowerCaseCharacters(passwordContainsLowerCase);
        pass.setUpperCaseCharacters(passwordContainsUpperCase);
        pass.setNumberCharacters(passwordContainsNumber);
        pass.setSpecialCharacters(passwordContainsSpecialCharacters);

        return pass;
    }

    /**
     * GDPR Check 3A
     * <p>
     * Blocco utenza su tentativi falliti: aggiungere un blocco temporaneo di 30
     * minuti (impostabile) dopo n tentativi falliti (con n >=3 impostabile) se non
     * e' gia' presente il blocco dell'utenza in caso di tentativi falliti;
     */
    @Override
    public boolean executeCheck3A(final AccountConfigurationDTO accountConfigurationDTO, final User user,
                                  final String ipAddress) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck3A for username [ {} ]", user.getLogin());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        int countTentativiEffettutati = loginKOService.countCurrentLoginAttempts(user.getLogin());

        /*
         * TODO controllare anche se gli ultimi n tentativi = maxNumTentativi sono tra
         * di loro distanziati al piu' durataBlocco
         */
        if (countTentativiEffettutati >= accountConfigurationDTO.getMaxNumTentativi()) {
            /*
             * Sono in una potenziale situazione di blocco, devo verificare la data attuale
             * se e' maggiore della data dell'ultimo tentativo di login sommata al tempo di
             * blocco indicato in configurazione
             */

            LoginKODTO loginKODTO = loginKOService.getLastLoginAttempt(user.getLogin());

            // deve esistere l'oggetto, me lo garantisce il count dei tentativi effettuati
            if (loginKODTO != null) {
                Date now = new Date();
                // e' passato il tempo di blocco, posso proseguire
                if (now.getTime() > loginKODTO.getLoginTime().getTime()
                        + getDurataBloccoInMillisecondi(accountConfigurationDTO.getDurataBloccoMinuti())) {

                    // clear ?

                    loginKOService.clearLoginAttempts(user.getLogin());

                    int livelloEvento = LogEventiUtils.LIVELLO_INFO;
                    String errMsgEvento = "";

                    // inserimento log in w_logeventi
                    WLogEventiDTO logEventiDTO = LogEventiUtils.createLoginUnlockEvent(user.getSyscon(),
                            user.getLogin(), ipAddress);
                    logEventiDTO.setCodApp(codiceProdotto);
                    logEventiDTO.setLivelloEvento(livelloEvento);
                    logEventiDTO.setErrorMessage(errMsgEvento);
                    wLogEventiService.insertLogEvent(logEventiDTO);

                    pass = true;
                } else {
                    pass = false;
                }
            }
        } else {
            /*
             * il numero di tentativi effettuato non ha ancora raggiunto il limite massimo
             * impostato da configurazione quindi posso proseguire
             */
            pass = true;
        }

        return pass;
    }

    /**
     * GDPR Check 3B
     * <p>
     * Controllo contenuto password: Inserire un controllo di non presenza di almeno
     * 4 caratteri consecutivi presenti nella LOGIN (mantenere parametrizzabile il
     * numero di caratteri consecutivi da controllare). Inoltre, solo se non troppo
     * oneroso, aggiungere un file di configurazione, o una tabella, o altro nel
     * quale impostare un elenco di password non accettabili.
     */
    @Override
    public boolean executeCheck3B(final User user, final ChangePasswordDTO changePasswordDTO) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        if (changePasswordDTO == null)
            throw new IllegalArgumentException("changePasswordDTO null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck3B for username [ {} ]", user.getLogin());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        final String username = user.getLogin().toLowerCase();
        final String password = changePasswordDTO.getNewPassword().toLowerCase();

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

    @Override
    public boolean executeCheck3BInsert(final String username, final String newPassword, final boolean passwordSicuraEnabled) {

        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("username null");

        if (StringUtils.isBlank(newPassword))
            throw new IllegalArgumentException("newPassword null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck3BInsert for username [ {} ]", username);

        if (!passwordSicuraEnabled)
            return true;

        boolean pass = false;

        final String usernameL = username.toLowerCase();
        final String passwordL = newPassword.toLowerCase();

        List<String> combinations = getNPartOfString(usernameL, NUMERO_MASSIMO_CARATTERI_RIPETUTI);

        boolean found = false;

        for (int i = 0; i < combinations.size() && !found; i++) {
            String combination = combinations.get(i);
            if (passwordL.contains(combination))
                found = true;
        }

        pass = !found;

        return pass;
    }

    /**
     * GDPR Check 4A
     * <p>
     * Controllo prima password: i controlli applicati per la prima password devono
     * essere i medesimi utilizzati per i successivi cambi. La prima password, se
     * generata automaticamente dal sistema (ad esempio durante la creazione
     * automatica delle login), dovra' richiedere un cambio obbligatorio al primo
     * accesso dell'utente cosi' come se questa viene generata da un terzo
     * (amministratore). La prima password potra' essere riportata in chiaro
     * all'interno di mail automatiche per l'invio agli utenti.
     */
    @Override
    public boolean executeCheck4A(final User user) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck4A for user [ {} ]", user.getLogin());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        Integer counter = stoUteSysService.countUserPasswordInformationBySyscon(user.getSyscon());

        if (counter == 0) {
            // primo accesso
            pass = false;
        } else {
            // controllo
            pass = true;
        }

        return pass;
    }

    /**
     * GDPR Check 5A
     * <p>
     * Memorizzazione ultime password: Prevediamo su tutti i prodotti la
     * memorizzazione delle ultime 10 password (mantenere parametrizzabile il numero
     * di password da conservare). Le ultime password devono essere memorizzate
     * criptate e in nessun caso devono essere visibili.
     * <p>
     * GDPR Check 5C
     * <p>
     * Memorizzazione ultime password: La stessa password non puo' essere
     * riutilizzata prima di 6 mesi non solo per le utenze amministrative ma anche
     * per le utenze normali. Questa si aggiunge al password history del punto
     * precedente.
     *
     * @throws CriptazioneException
     */
    @Override
    public boolean executeCheck5A5C(final User user, final ChangePasswordDTO changePasswordDTO)
            throws CriptazioneException {

        if (user == null)
            throw new IllegalArgumentException("user null");

        if (changePasswordDTO == null)
            throw new IllegalArgumentException("changePasswordDTO null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck5A5C for username [ {} ]", user.getLogin());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        List<String> listaPasswordCambiate = stoUteSysService.getChangedPasswords(user.getSyscon());

        if (listaPasswordCambiate == null || (listaPasswordCambiate != null && listaPasswordCambiate.size() == 0)) {

            // non sono mai state cambiate password
            pass = true;

        } else {

            String passwordCriptata = super.getValoreCifrato(changePasswordDTO.getNewPassword());

            if (listaPasswordCambiate.contains(passwordCriptata)) {
                pass = false;
            } else {
                pass = true;
            }

        }

        return pass;
    }

    /**
     * GDPR Check 6A
     * <p>
     * Scadenze password: Prevedere come standard l'applicazione di 3 mesi come
     * scadenza della password. Il valore puo' essere ridotto (nel caso si vogliano
     * applicare regole piu' restrittive) ed eventualmente portato a 0 nel caso in
     * cui non si voglia applicare le scadenza della Password. Questo sara'
     * applicato solo su utenze "di sistema". Rendere comunque parametrizzabile il
     * numero massimo di mesi (per superare il valore di 3) ma attraverso un
     * parametro di configurazione valido per tutto l'impianto del cliente onde
     * evitare che possa essere variato in modo "leggero". Questa soluzione potrebbe
     * garantire una certa sicurezza senza ingessare il sistema
     */
    @Override
    public boolean executeCheck6A(final AccountConfigurationDTO accountConfigurationDTO, final User user) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck6A for user [ {} ]", user.getLogin());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        try {
            String nomeUtenteCifrato = super.getValoreCifrato(user.getLogin());
            StoUteSysDTO dto = stoUteSysService.getCurrentPasswordInformation(nomeUtenteCifrato, user.getPassword());

            if (dto == null) {
                /*
                 * Situazione anomala: dovrebbe esserci un record altrimenti -> primo accesso
                 */
                pass = true;
            } else {
                if (dto.getSysdat() != null) {
                    Date now = new Date();
                    if (dto.getSysdat().getTime()
                            + getDurataPasswordInMillisecondi(accountConfigurationDTO.getDurataPassword()) >= now
                            .getTime()) {
                        // password ancora valida
                        pass = true;
                    } else {
                        // password scaduta
                        pass = false;
                    }
                } else {
                    // errore particolare, la data non e' stata registrata al momento del cambio
                    // password/registrazione utente
                    LOGGER.error(
                            "La data per la registrazione utente/cambio password con syscon [ {} ] non e' stata registrata, non posso eseguire la verifica di GDPR 6A",
                            user.getSyscon());
                    pass = true;
                }
            }
        } catch (CriptazioneException e) {
            LOGGER.error("Errore durante la criptazione della login", e);
        }

        return pass;
    }

    /**
     * GDPR Check 6B
     * <p>
     * Scadenze utenti: Prevedere come standard l'applicazione di 6 mesi come
     * scadenza dell'utenza. Il valore puo' essere ridotto (nel caso si vogliano
     * applicare regole piu' restrittive) ed eventualmente portato a 0 nel caso in
     * cui non si voglia applicare le scadenza di qualche utenza. Questo sara'
     * applicato solo su utenze "di sistema". Rendere comunque parametrizzabile il
     * numero massimo di mesi ma attraverso un parametro di configurazione valido
     * per tutto l'impianto del cliente onde evitare che possa essere variato in
     * podo "leggero". Questa soluzione potrebbe garantire una certa sicurezza senza
     * ingessare il sistema
     */
    @Override
    public boolean executeCheck6B(final AccountConfigurationDTO accountConfigurationDTO, final User user) {

        if (user == null)
            throw new IllegalArgumentException("user null");

        LOGGER.debug("Execution start GDPRServiceImpl::executeCheck6B for syscon [ {} ]", user.getSyscon());

        if (!super.isPasswordSicuraEnabled(user.getSyspwbou()))
            return true;

        boolean pass = false;

        if (user.getDataUltimoAccesso() == null) {
            pass = true;
        } else {
            Date now = new Date();
            if (user.getDataUltimoAccesso().getTime()
                    + getDurataAccountInMillisecondi(accountConfigurationDTO.getDurataAccount()) >= now.getTime()) {
                // account ancora valido
                pass = true;
            } else {
                // account scaduto
                pass = false;
            }
        }

        return pass;
    }

    private static long getDurataBloccoInMillisecondi(final long durataBloccoMinuti) {
        return durataBloccoMinuti * 60 * 1000;
    }

    private static long getDurataAccountInMillisecondi(final long durataAccountGiorni) {
        return durataAccountGiorni * 24 * 60 * 60 * 1000;
    }

    private static long getDurataPasswordInMillisecondi(final long durataPasswordGiorni) {
        return durataPasswordGiorni * 24 * 60 * 60 * 1000;
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
