package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.domain.WLogEventi;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.amministrazione.EventoNotFoundException;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.UnexpectedErrorException;
import it.maggioli.ssointegrms.repositories.WLogEventiCriteriaRepository;
import it.maggioli.ssointegrms.repositories.WLogEventiRepository;
import it.maggioli.ssointegrms.repositories.WSsoJwtTokenRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.WGenChiaviService;
import it.maggioli.ssointegrms.services.general.WLogEventiService;
import it.maggioli.ssointegrms.utils.LogEventiUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Cristiano Perin
 */
public abstract class WLogEventiServiceBase extends BaseService implements WLogEventiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WLogEventiServiceBase.class);

    private static final DateFormat YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    private static final String CSV_SEPARATOR = ";";
    private static final String CSV_VALUE_BEGIN = "\"";
    private static final String CSV_VALUE_END = "\"";
    private static final Logger loggerEvents = LoggerFactory.getLogger("events");
    public static final String RESPONSE_DONE_Y = "Y";
    public static final String RESPONSE_DONE_N = "N";
    public static final String ERROR_UNEXPECTED = "unexpected-error";

    @Autowired
    protected WGenChiaviService wgcService;

    @Autowired
    protected WLogEventiRepository wLogEventiRepository;

    @Autowired
    private WLogEventiCriteriaRepository wLogEventiCriteriaRepository;

    @Autowired
    private WSsoJwtTokenRepository wSsoJwtTokenRepository;

    @Override
    public WLogEventiDTO getLastLogin(final Long syscon) {

        LOGGER.debug("Execution start WLogEventiServiceImpl::getLastLogin for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        WLogEventi wLogEventi = wLogEventiRepository.getLastLogBySyscon(LogEventiUtils.COD_EVENTO_LOGIN, syscon)
                .orElse(null);

        WLogEventiDTO dto = null;

        if (wLogEventi != null) {
            dto = dtoMapper.convertTo(wLogEventi, WLogEventiDTO.class);
            if (wLogEventi.getUser() != null) {
                dto.setSyscon(wLogEventi.getUser().getSyscon());
                dto.setDescrizioneUtente(wLogEventi.getUser().getSysute());
            }
        }

        return dto;
    }

    @Override
    public abstract void insertLogEvent(final WLogEventiDTO dto);

    @Override
    public RicercaEventiInizDTO getInizRicercaEventi() {

        LOGGER.debug("Execution start WLogEventiServiceImpl::getInizRicercaEventi");

        RicercaEventiInizDTO dto = new RicercaEventiInizDTO();

        List<String> codiciEvento = wLogEventiRepository.findCodiciEventiDistinct(codiceProdotto);

        codiciEvento.removeIf(Objects::isNull);
        codiciEvento.removeIf(String::isEmpty);

        dto.setListaCodiciEventi(codiciEvento);

        return dto;
    }

    @Override
    public ResponseListaDTO loadListaEventi(final RicercaEventiFormDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start WLogEventiServiceImpl::loadListaEventi for form [ {} ]", form);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        // paginazione e ordinamento
        final Integer skip = form.getSkip();
        final Integer take = form.getTake();
        final String sort = form.getSort();
        final String sortDirection = form.getSortDirection();
        final String sortKey = StringUtils.isNotBlank(sort) ? sort : "idEvento";
        final String sortDirectionKey = StringUtils.isNotBlank(sortDirection) ? sortDirection : "asc";

        // clear attributi con stringa vuota
        if (StringUtils.isBlank(form.getDescrizioneUtente()))
            form.setDescrizioneUtente(null);

        if (StringUtils.isBlank(form.getLivelloEvento()))
            form.setLivelloEvento(null);

        if (StringUtils.isBlank(form.getCodiceEvento()))
            form.setCodiceEvento(null);

        if (StringUtils.isBlank(form.getOggettoEvento()))
            form.setOggettoEvento(null);

        if (StringUtils.isBlank(form.getDescrizione()))
            form.setDescrizione(null);

        List<WLogEventiDTO> wLogEventiDTOList = new ArrayList<>();

        if (skip != null && take != null && skip >= 0L && take > 0L) {
            // ho la paginazione attiva

            WLogEventiSearchResultDTO searchResult = wLogEventiCriteriaRepository.loadListaEventi(codiceProdotto,
                    form.getIdEvento(), form.getDataOraDa(), form.getDataOraA(), form.getDescrizioneUtente(),
                    form.getLivelloEvento(), form.getCodiceEvento(), form.getOggettoEvento(), form.getDescrizione(),
                    skip, take, sortKey, sortDirectionKey);

            response.setTotalCount(searchResult.getTotalCount());

            if (searchResult.getEventsList() != null) {
                for (WLogEventi event : searchResult.getEventsList()) {
                    WLogEventiDTO wLogEventiDTO = dtoMapper.convertTo(event, WLogEventiDTO.class);
                    if (event.getUser() != null) {
                        wLogEventiDTO.setSyscon(event.getUser().getSyscon());
                        wLogEventiDTO.setDescrizioneUtente(event.getUser().getSysute());
                    }
                    wLogEventiDTOList.add(wLogEventiDTO);
                }
            }

            response.setResponse(wLogEventiDTOList);
        }

        return response;

    }

    @Override
    public ResponseListaDTO loadListaUltimiAccessi(Long syscon) {

        LOGGER.debug("Execution start WLogEventiServiceImpl::loadListaUltimiAccessi");

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        List<WLogEventiDTO> wLogEventiDTOList = new ArrayList<>();

        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        // Convertire LocalDateTime in java.util.Date per JPA
        Date trentaGiorni = Date.from(thirtyDaysAgo.atZone(ZoneId.systemDefault()).toInstant());

        List<WLogEventi> wLogEventi = wLogEventiRepository.loadUltimiAccessi(syscon, LogEventiUtils.COD_EVENTO_LOGIN, LogEventiUtils.COD_EVENTO_LOGOUT, trentaGiorni);

        response.setTotalCount((long) wLogEventi.size());


        for (WLogEventi event : wLogEventi) {
            WLogEventiDTO wLogEventiDTO = dtoMapper.convertTo(event, WLogEventiDTO.class);

            if (event.getDataOra() != null) {

                Date dataOra = event.getDataOra();

                // Formattatore per visualizzare la data nel formato indicato.
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                // Formattare la data in stringa.
                String formattedDate = formatter.format(dataOra);

                wLogEventiDTO.setDataOraFormatted(formattedDate);
            }

            if (event.getUser() != null) {

                wLogEventiDTO.setSyscon(event.getUser().getSyscon());
                wLogEventiDTO.setDescrizioneUtente(event.getUser().getSysute());
            }
            wLogEventiDTOList.add(wLogEventiDTO);
        }
        response.setResponse(wLogEventiDTOList);

        return response;
    }

    /**
     * Metodo che ritorna il dettaglio di un evento dato il suo id
     * <p>
     * Istanzia una transazione per salvare un evento di accesso da parte
     * dell'utente corrente
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WLogEventiDTO getEvento(final Long idEvento, final Long currentUserSyscon, final String ipAddress) {

        LOGGER.debug("Execution start WLogEventiServiceImpl::getEvento for idEvento [ {} ]", idEvento);

        WLogEventi evento = wLogEventiRepository.findById(idEvento).orElse(null);

        if (evento == null) {
            LOGGER.error("Evento non presente per idEvento [ {} ]", idEvento);

            throw new EventoNotFoundException();
        }

        // scrittura in w_logeventi
        WLogEventiDTO toInsert = LogEventiUtils.createLogEventiEvent(currentUserSyscon,
                String.valueOf(evento.getIdEvento()), ipAddress);
        toInsert.setCodApp(codiceProdotto);
        toInsert.setLivelloEvento(LogEventiUtils.LIVELLO_INFO);
        toInsert.setErrorMessage(null);

        WLogEventi po = dtoMapper.convertTo(toInsert, WLogEventi.class);

        User user = userRepository.findById(toInsert.getSyscon()).orElse(null);

        po.setUser(user);

        Long id = wgcService.getNextId(AppConstants.W_GENCHIAVI_W_LOGEVENTI);

        if (po != null) {

            po.setIdEvento(id);

            po = wLogEventiRepository.save(po);

            writeToFile(po);
        }

        WLogEventiDTO wLogEventiDTO = dtoMapper.convertTo(evento, WLogEventiDTO.class);
        if (evento.getUser() != null) {
            wLogEventiDTO.setSyscon(evento.getUser().getSyscon());
            wLogEventiDTO.setDescrizioneUtente(evento.getUser().getSysute());
        }

        return wLogEventiDTO;
    }

    protected void writeToFile(final WLogEventi logEvento) {
        StringBuilder value = new StringBuilder();
        value.append(CSV_VALUE_BEGIN).append(logEvento.getIdEvento()).append(CSV_VALUE_END).append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN).append(YYYYMMDD_HHMMSS.format(logEvento.getDataOra().getTime()))
                .append(CSV_VALUE_END).append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN).append(logEvento.getLivelloEvento()).append(CSV_VALUE_END)
                .append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN)
                .append((logEvento.getCodiceEvento() != null ? logEvento.getCodiceEvento() : "")).append(CSV_VALUE_END)
                .append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN)
                .append(logEvento.getOggettoEvento() != null ? logEvento.getOggettoEvento() : "").append(CSV_VALUE_END)
                .append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN).append(logEvento.getDescrizione() != null ? logEvento.getDescrizione() : "")
                .append(CSV_VALUE_END).append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN).append(logEvento.getErrorMessage() != null ? logEvento.getErrorMessage() : "")
                .append(CSV_VALUE_END).append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN).append((logEvento.getCodProfilo() != null ? logEvento.getCodProfilo() : ""))
                .append(CSV_VALUE_END).append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN)
                .append((logEvento.getUser() != null && logEvento.getUser().getSyscon() != null
                        ? logEvento.getUser().getSyscon()
                        : ""))
                .append(CSV_VALUE_END).append(CSV_SEPARATOR) //
                .append(CSV_VALUE_BEGIN).append(logEvento.getIpEvento() != null ? logEvento.getIpEvento() : "")
                .append(CSV_VALUE_END).append(CSV_SEPARATOR);

        if (loggerEvents != null) {
            loggerEvents.info(value.toString());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO createLogoutEvent(final Long syscon, final String ipAddress) {

        LOGGER.info("Inizio metodo WLogEventiServiceBase::createLogoutEvent");

        ResponseDTO response = new ResponseDTO();
        response.setDone(RESPONSE_DONE_Y);

        try {
            WLogEventiDTO logEventiDTO = LogEventiUtils.createLogoutEvent(syscon, ipAddress, codiceProdotto);

            WLogEventi logEventi = dtoMapper.convertTo(logEventiDTO, WLogEventi.class);

            User user = userRepository.findById(syscon).orElse(null);

            if (user != null) {
                wSsoJwtTokenRepository.deleteById(user.getLogin().toUpperCase());
            }

            logEventi.setUser(user);

            Long id = wgcService.getNextId(AppConstants.W_GENCHIAVI_W_LOGEVENTI);
            logEventi.setIdEvento(id);

            wLogEventiRepository.save(logEventi);
        } catch (Exception e) {
            LOGGER.error("Errore inaspettato durante l'inserimento dell'evento di logout per il syscon: {}", syscon);
            response.setDone(RESPONSE_DONE_N);
            List<String> messages = new ArrayList<>();
            messages.add(ERROR_UNEXPECTED);

            response.setMessages(messages);
            throw new UnexpectedErrorException(e);
        }

        return response;
    }

}
