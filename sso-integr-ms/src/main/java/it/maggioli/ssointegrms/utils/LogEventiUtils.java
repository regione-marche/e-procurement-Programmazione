package it.maggioli.ssointegrms.utils;

import it.maggioli.ssointegrms.common.EChangePasswordType;
import it.maggioli.ssointegrms.domain.Profilo;
import it.maggioli.ssointegrms.dto.WLogEventiDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
public class LogEventiUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogEventiUtils.class);

    public static final String COD_EVENTO_LOGIN = "LOGIN";
    public static final String COD_EVENTO_SET_PROFILO = "SET_PROFILO";
    public static final String COD_EVENTO_SET_UFFINT = "SET_UFFINT";
    public static final String COD_EVENTO_OPEN_APPLICATION = "OPEN_APPLICATION";
    public static final String COD_EVENTO_LOGOUT = "LOGOUT";
    public static final String COD_EVENTO_INVCOM = "INVCOM";
    // Suppress 'PASSWORD' detected in this expression, review this potentially hard-coded password.
    @SuppressWarnings("java:S2068")
    public static final String COD_EVENTO_CHANGE_PASSWORD = "CHANGE_PSW";
    public static final String COD_EVENTO_ADD_USER = "ADD_USER";
    public static final String COD_EVENTO_DEL_USER = "DELETE_USER";
    public static final String COD_EVENTO_READ_ART80 = "READ_ART80";
    public static final String COD_EVENTO_LOCK_LOGIN_UTENTE = "LOGIN_LOCK";
    public static final String COD_EVENTO_UNLOCK_LOGIN_UTENTE = "LOGIN_UNLOCK";
    public static final String COD_EVENTO_CHANGE_PROFILO = "CHANGE_USER_PROFILE";
    public static final String COD_EVENTO_LOGEVENTI = "LOGEVENTI";
    // Recupero password
    public static final String COD_EVENTO_PASSWORDRECOVERY = "PASSWORDRECOVERY";
    public static final String COD_EVENTO_GENTOKEN = "GENTOKEN";
    public static final String COD_EVENTO_PROCTOKEN = "PROCTOKEN";
    public static final String COD_EVENTO_ENABLE_USER = "ENABLE_USER";
    public static final String COD_EVENTO_DISABLE_USER = "DISABLE_USER";

    /**
     * Livello di tracciatura info.
     */
    public static final int LIVELLO_INFO = 1;
    /**
     * Livello di tracciatura errore non bloccante.
     */
    public static final int LIVELLO_WARNING = 2;
    /**
     * Livello di tracciatura errore bloccante.
     */
    public static final int LIVELLO_ERROR = 3;

    public static WLogEventiDTO createLoginEvent(final Long syscon, final String ipEvento, final String appCode) {

        LOGGER.debug("Execution start LogEventiUtils::createLoginEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Login app {0} con id {1}";

        if (syscon != null)
            msg = MessageFormat.format(msg, appCode, syscon);

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_LOGIN);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createLoginLockEvent(final Long syscon, final String username, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createLoginLockEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        final String msg = "Blocco temporaneo utente = " + username;

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_LOCK_LOGIN_UTENTE);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createLoginUnlockEvent(final Long syscon, final String username,
                                                       final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createLoginUnlockEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        final String msg = "Rimozione blocco temporaneo per utente = " + username;

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_UNLOCK_LOGIN_UTENTE);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createChangePasswordEvent(final Long syscon, final String username,
                                                          final String ipEvento, final EChangePasswordType changeType) {

        LOGGER.debug("Execution start LogEventiUtils::createChangePasswordEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = null;

        if (changeType == null) {
            msg = "Cambio password";
        } else {
            switch (changeType) {
                case CHANGE_BY_ADMIN:
                    msg = "Cambio password con id = " + syscon + " e login " + username;
                    break;
                case CHANGE_EXPIRED:
                    msg = "Cambio password scaduta";
                    break;
                case CHANGE:
                default:
                    msg = "Cambio password";
                    break;
            }
        }

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_CHANGE_PASSWORD);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createAddUserEvent(final Long syscon, final String username, final Long addedSyscon,
                                                   final String addedSyslogin, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createAddUserEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        final String msg = "L'utente = " + username + " ha inserito l'utente con id=" + addedSyscon + " e login = "
                + addedSyslogin;

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_ADD_USER);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createDeleteUserEvent(final Long syscon, final String username,
                                                      final Long deletedSyscon, final String deletedSyslogin, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createDeleteUserEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        final String msg = "L'utente = " + username + " ha cancellato l'utente con id=" + deletedSyscon + " e login = "
                + deletedSyslogin;

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_DEL_USER);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createChangeUserProfileEvent(final Long syscon, final String changedUsername,
                                                             final Long changedSyscon, final Set<Profilo> listaProfili, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createChangeUserProfileEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String profili = "";

        if (listaProfili != null && !listaProfili.isEmpty()) {
            profili = listaProfili.stream().map(Profilo::getCodice).collect(Collectors.joining(","));
        }

        final String msg = "Utente = " + changedUsername + " (" + changedSyscon + ") associato ai profili: " + profili;

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_CHANGE_PROFILO);
        dto.setDescrizione(msg);

        return dto;
    }

    public static WLogEventiDTO createLogEventiEvent(final Long syscon, final String riferimentoEvento,
                                                     final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createLogEventiEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        final String msg = "Accesso al dettaglio tracciatura evento con id = " + riferimentoEvento;

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_LOGEVENTI);
        dto.setDescrizione(msg);

        return dto;
    }

    private static WLogEventiDTO createPasswordRecoveryInternalEvent(final String codEvento,
                                                                     final String message, final Long syscon, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createPasswordRecoveryInternalEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        dto.setSyscon(syscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(codEvento);
        dto.setDescrizione(message);

        return dto;
    }

    public static WLogEventiDTO createPasswordRecoveryEvent(final String message,
                                                            final Long syscon, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createPasswordRecoveryEvent");

        return createPasswordRecoveryInternalEvent(COD_EVENTO_PASSWORDRECOVERY, message, syscon, ipEvento);
    }

    public static WLogEventiDTO createPasswordRecoveryGenerateTokenEvent(final String message,
                                                                         final Long syscon, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createPasswordRecoveryGenerateTokenEvent");

        return createPasswordRecoveryInternalEvent(COD_EVENTO_GENTOKEN, message, syscon, ipEvento);
    }

    public static WLogEventiDTO createPasswordRecoveryProcessTokenEvent(final String message,
                                                                        final Long syscon, final String ipEvento) {

        LOGGER.debug("Execution start LogEventiUtils::createPasswordRecoveryProcessTokenEvent");

        return createPasswordRecoveryInternalEvent(COD_EVENTO_PROCTOKEN, message, syscon, ipEvento);
    }

    public static WLogEventiDTO createAbilitazioneUtenteEvent(final Long currentUserSyscon, final Long editUserSyscon, final String editUserUsername, final String ipEvento, final String codiceProdotto) {

        LOGGER.debug("Execution start LogEventiUtils::createAbilitazioneUtenteEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "L''utente {0} ha abilitato l''utente con id = {1} e login = {2}";
        msg = MessageFormat.format(msg, currentUserSyscon, editUserSyscon, editUserUsername);

        dto.setSyscon(currentUserSyscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_ENABLE_USER);
        dto.setDescrizione(msg);
        dto.setCodApp(codiceProdotto);
        dto.setLivelloEvento(LogEventiUtils.LIVELLO_INFO);
        dto.setErrorMessage(null);

        return dto;
    }

    public static WLogEventiDTO createDisabilitazioneUtenteEvent(final Long currentUserSyscon, final Long editUserSyscon, final String editUserUsername, final String ipEvento, final String codiceProdotto) {

        LOGGER.debug("Execution start LogEventiUtils::createDisabilitazioneUtenteEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "L''utente {0} ha disabilitato l''utente con id = {1} e login = {2}";
        msg = MessageFormat.format(msg, currentUserSyscon, editUserSyscon, editUserUsername);

        dto.setSyscon(currentUserSyscon);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_DISABLE_USER);
        dto.setDescrizione(msg);
        dto.setCodApp(codiceProdotto);
        dto.setLivelloEvento(LogEventiUtils.LIVELLO_INFO);
        dto.setErrorMessage(null);

        return dto;
    }

    public static WLogEventiDTO createMTokenLogin(final String descrizione, final String ipEvento, final String codiceProdotto) {

        LOGGER.debug("Execution start LogEventiUtils::createMTokenLogin");

        final WLogEventiDTO dto = new WLogEventiDTO();

        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_LOGIN);
        dto.setDescrizione(descrizione);
        dto.setCodApp(codiceProdotto);
        dto.setErrorMessage(null);

        return dto;
    }

    private static String getIpEvento(final String ipEvento) {
        return StringUtils.isNotBlank(ipEvento) && ipEvento.length() > 40 ? ipEvento.substring(0, 40) : ipEvento;
    }

    public static WLogEventiDTO createLogoutEvent(final Long syscon, final String ipEvento, final String appCode) {

        LOGGER.debug("Execution start LogEventiUtils::createLogoutEvent");

        final WLogEventiDTO dto = new WLogEventiDTO();

        String msg = "Logout app {0} con id {1}";

        if (syscon != null)
            msg = MessageFormat.format(msg, appCode, syscon);

        dto.setSyscon(syscon);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setIpEvento(getIpEvento(ipEvento));
        dto.setDataOra(new Date());
        dto.setCodApp(appCode);
        dto.setCodiceEvento(COD_EVENTO_LOGOUT);
        dto.setDescrizione(msg);

        return dto;
    }
}
