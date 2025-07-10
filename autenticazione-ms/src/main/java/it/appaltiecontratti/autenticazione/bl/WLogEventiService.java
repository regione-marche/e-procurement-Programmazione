package it.appaltiecontratti.autenticazione.bl;

import it.appaltiecontratti.autenticazione.Constants;
import it.appaltiecontratti.autenticazione.entity.WLogEventi;
import it.appaltiecontratti.autenticazione.mapper.WLogEventiMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author Cristiano Perin
 */
@Service
public class WLogEventiService {

    private static final Logger LOGGER = LogManager.getLogger(WLogEventiService.class);

    public static final String COD_EVENTO_LOGIN = "LOGIN";
    public static final String COD_EVENTO_SET_PROFILO = "SET_PROFILO";
    public static final String COD_EVENTO_SET_UFFINT = "SET_UFFINT";

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

    @Value("${application.codiceProdotto}")
    private String codiceProdotto;

    @Autowired
    private WGenChiaviService wgcService;

    @Autowired
    private WLogEventiMapper wLogEventiMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLoginLogEvent(final Long syscon, final String ipEvento) {

        LOGGER.debug("Execution start WLogEventiService::insertLogEvent");

        final WLogEventi dto = new WLogEventi();

        String msg = "Login app {0} con id {1}";

        if (syscon != null)
            msg = MessageFormat.format(msg, codiceProdotto, syscon);

        final String ipEventoSub = StringUtils.isNotBlank(ipEvento) && ipEvento.length() > 40 ? ipEvento.substring(0, 40) : ipEvento;

        dto.setSyscon(syscon);
        dto.setIpEvento(ipEventoSub);
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_LOGIN);
        dto.setDescrizione(msg);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodApp(codiceProdotto);
        dto.setErrorMessage(null);

        try {
            Long id = wgcService.getNextId(Constants.W_GENCHIAVI_W_LOGEVENTI);

            dto.setIdEvento(id);

            wLogEventiMapper.insertLogEvento(dto);
        } catch (Exception e) {
            LOGGER.error("Errore durante l'inserimento dell'evento", e);
            throw e;
        }
    }

    /**
     * Propagation.REQUIRES_NEW
     * <p>
     * per garantire la scrittura dei log anche quando la transazione fallisce per
     * qualche motivo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertSetProfiloLogEvent(final Long syscon, final String codiceProfilo, final String ipEvento) {

        LOGGER.debug("Execution start WLogEventiService::insertLogEvent for codiceProfilo [ {} ]", codiceProfilo);

        final WLogEventi dto = new WLogEventi();

        String msg = "Accesso al profilo applicativo {0}";

        if (StringUtils.isNotBlank(codiceProfilo))
            msg = MessageFormat.format(msg, codiceProfilo);

        final String ipEventoSub = StringUtils.isNotBlank(ipEvento) && ipEvento.length() > 40 ? ipEvento.substring(0, 40) : ipEvento;

        dto.setSyscon(syscon);
        dto.setIpEvento(ipEventoSub);
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_SET_PROFILO);
        dto.setDescrizione(msg);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodApp(codiceProdotto);
        dto.setErrorMessage(null);
        dto.setCodProfilo(codiceProfilo);

        try {
            Long id = wgcService.getNextId(Constants.W_GENCHIAVI_W_LOGEVENTI);

            dto.setIdEvento(id);

            wLogEventiMapper.insertLogEvento(dto);
        } catch (Exception e) {
            LOGGER.error("Errore durante l'inserimento dell'evento", e);
            throw e;
        }
    }

    /**
     * Propagation.REQUIRES_NEW
     * <p>
     * per garantire la scrittura dei log anche quando la transazione fallisce per
     * qualche motivo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertSetSALogEvent(final Long syscon, final String codein, final String ipEvento) {

        LOGGER.debug("Execution start WLogEventiService::insertSetSALogEvent for codein [ {} ]", codein);

        final WLogEventi dto = new WLogEventi();

        String msg = "Selezione ufficio intestatario ({0})";

        if (StringUtils.isNotBlank(codein)) {
            msg = MessageFormat.format(msg, codein);
        }

        final String ipEventoSub = StringUtils.isNotBlank(ipEvento) && ipEvento.length() > 40 ? ipEvento.substring(0, 40) : ipEvento;

        dto.setSyscon(syscon);
        dto.setIpEvento(ipEventoSub);
        dto.setDataOra(new Date());
        dto.setCodiceEvento(COD_EVENTO_SET_UFFINT);
        dto.setDescrizione(msg);
        dto.setLivelloEvento(LIVELLO_INFO);
        dto.setCodApp(codiceProdotto);
        dto.setErrorMessage(null);

        try {
            Long id = wgcService.getNextId(Constants.W_GENCHIAVI_W_LOGEVENTI);

            dto.setIdEvento(id);

            wLogEventiMapper.insertLogEvento(dto);
        } catch (Exception e) {
            LOGGER.error("Errore durante l'inserimento dell'evento", e);
            throw e;
        }
    }
}
