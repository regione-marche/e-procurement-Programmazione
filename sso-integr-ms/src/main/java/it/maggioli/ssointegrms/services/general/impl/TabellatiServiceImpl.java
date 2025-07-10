package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.*;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.repositories.*;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.TabellatiService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class TabellatiServiceImpl extends BaseService implements TabellatiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TabellatiServiceImpl.class);

    @Autowired
    private VTab4Tab6CriteriaRepository vTab4Tab6CriteriaRepository;

    @Autowired
    private Tab0Repository tab0Repository;

    @Autowired
    private Tab1Repository tab1Repository;

    @Autowired
    private Tab2Repository tab2Repository;

    @Autowired
    private Tab3Repository tab3Repository;

    @Autowired
    private Tab5Repository tab5Repository;

    @Override
    public ResponseListaDTO loadListaTabellati(final RicercaTabellatiFormDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start TabellatiServiceImpl::loadListaTabellati for form [ {} ]", form);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        // paginazione e ordinamento
        final Integer skip = form.getSkip();
        final Integer take = form.getTake();
        final String sort = form.getSort();
        final String sortDirection = form.getSortDirection();
        final String sortKey = StringUtils.isNotBlank(sort) ? sort : "descrizioneTabellato";
        final String sortDirectionKey = StringUtils.isNotBlank(sortDirection) ? sortDirection : "asc";

        // clear attributi con stringa vuota
        form.setCodiceTabellato(StringUtils.stripToNull(form.getCodiceTabellato()));
        form.setDescrizioneTabellato(StringUtils.stripToNull(form.getDescrizioneTabellato()));

        List<VTab4Tab6DTO> vTab4Tab6DTOList = new ArrayList<>();

        if (skip != null && take != null && skip >= 0L && take > 0L) {
            // ho la paginazione attiva

            VTab4Tab6SearchResultDTO searchResult = vTab4Tab6CriteriaRepository.loadListaTabellati(
                    form.getCodiceTabellato(), form.getDescrizioneTabellato(), skip, take, sortKey, sortDirectionKey);

            response.setTotalCount(searchResult.getTotalCount());

            if (searchResult.getTabellatiList() != null) {
                for (VTab4Tab6 tabellato : searchResult.getTabellatiList()) {
                    VTab4Tab6DTO vTab4Tab6DTO = new VTab4Tab6DTO();
                    vTab4Tab6DTO.setTab46Cod(tabellato.getId().getTab46Cod());
                    vTab4Tab6DTO.setCodiceTabellato(tabellato.getId().getTab46Tip());
                    vTab4Tab6DTO.setDescrizioneTabellato(tabellato.getTab46Desc());
                    vTab4Tab6DTO.setProvenienza(calcolaProvenienzaTabellato(tabellato.getId().getTab46Tip()));
                    vTab4Tab6DTOList.add(vTab4Tab6DTO);
                }
            }

            response.setResponse(vTab4Tab6DTOList);
        }

        return response;
    }

    @Override
    public ResponseListaDTO loadListaDettaglioTabellato(final ListaDettaglioTabellatoFormDTO loadForm) {

        if (loadForm == null)
            throw new IllegalArgumentException("loadForm null");

        LOGGER.debug("Execution start TabellatiServiceImpl::loadListaDettaglioTabellato for loadForm [ {} ]", loadForm);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        Integer skip = loadForm.getSkip();
        Integer take = loadForm.getTake();
        int pageNumber = skip / take;
        String provenienza = loadForm.getProvenienza();
        String codiceTabellato = loadForm.getCodiceTabellato();

        if ("TAB0".equals(provenienza)) {
            final Sort sort = Sort.by(Sort.Direction.ASC, "ordinamento", "id.tab0tip", "descrizione");
            final Pageable pageable = PageRequest.of(pageNumber, take, sort);
            Page<Tab0> page = tab0Repository.findByIdTab0cod(codiceTabellato, pageable);
            List<Tab0DTO> listaTabellatiDTO = page.getContent() //
                    .stream() //
                    .map(t -> getTab0DTOFromTab0(t)) //
                    .collect(Collectors.toList());
            response.setTotalCount(page.getTotalElements());
            response.setResponse(listaTabellatiDTO);
        } else if ("TAB1".equals(provenienza)) {
            final Sort sort = Sort.by(Sort.Direction.ASC, "ordinamento", "id.tab1tip", "descrizione");
            final Pageable pageable = PageRequest.of(pageNumber, take, sort);
            Page<Tab1> page = tab1Repository.findByIdTab1cod(codiceTabellato, pageable);
            List<Tab1DTO> listaTabellatiDTO = page.getContent() //
                    .stream() //
                    .map(t -> getTab1DTOFromTab1(t)) //
                    .collect(Collectors.toList());
            response.setTotalCount(page.getTotalElements());
            response.setResponse(listaTabellatiDTO);
        } else if ("TAB2".equals(provenienza)) {
            final Sort sort = Sort.by(Sort.Direction.ASC, "ordinamento", "id.tab2tip", "descrizione");
            final Pageable pageable = PageRequest.of(pageNumber, take, sort);
            Page<Tab2> page = tab2Repository.findByIdTab2cod(codiceTabellato, pageable);
            List<Tab2DTO> listaTabellatiDTO = page.getContent() //
                    .stream() //
                    .map(t -> getTab2DTOFromTab2(t)) //
                    .collect(Collectors.toList());
            response.setTotalCount(page.getTotalElements());
            response.setResponse(listaTabellatiDTO);
        } else if ("TAB3".equals(provenienza)) {
            final Sort sort = Sort.by(Sort.Direction.ASC, "ordinamento", "id.tab3tip", "descrizione");
            final Pageable pageable = PageRequest.of(pageNumber, take, sort);
            Page<Tab3> page = tab3Repository.findByIdTab3cod(codiceTabellato, pageable);
            List<Tab3DTO> listaTabellatiDTO = page.getContent() //
                    .stream() //
                    .map(t -> getTab3DTOFromTab3(t)) //
                    .collect(Collectors.toList());
            response.setTotalCount(page.getTotalElements());
            response.setResponse(listaTabellatiDTO);
        } else if ("TAB5".equals(provenienza)) {
            final Sort sort = Sort.by(Sort.Direction.ASC, "ordinamento", "id.tab5tip", "descrizione");
            final Pageable pageable = PageRequest.of(pageNumber, take, sort);
            Page<Tab5> page = tab5Repository.findByIdTab5cod(codiceTabellato, pageable);
            List<Tab5DTO> listaTabellatiDTO = page.getContent() //
                    .stream() //
                    .map(t -> getTab5DTOFromTab5(t)) //
                    .collect(Collectors.toList());
            response.setTotalCount(page.getTotalElements());
            response.setResponse(listaTabellatiDTO);
        }

        return response;

    }

    @Override
    public Object getDettaglioTabellato(final String provenienza, final String codiceTabellato,
                                        final String identificativo) {

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::getDettaglioTabellato for provenienza [ {} ], codiceTabellato [ {} ] and identificativo [ {} ]",
                provenienza, codiceTabellato, identificativo);

        if (StringUtils.isBlank(provenienza))
            throw new IllegalArgumentException("provenienza null");

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (StringUtils.isBlank(identificativo))
            throw new IllegalArgumentException("identificativo null");

        if ("TAB0".equals(provenienza)) {

            Tab0PK id = new Tab0PK(codiceTabellato, identificativo);
            Tab0 tabellato = tab0Repository.findById(id).orElse(null);

            if (tabellato != null)
                return getTab0DTOFromTab0(tabellato);

        } else if ("TAB1".equals(provenienza)) {

            Tab1PK id = new Tab1PK(codiceTabellato, Long.valueOf(identificativo));
            Tab1 tabellato = tab1Repository.findById(id).orElse(null);

            if (tabellato != null)
                return getTab1DTOFromTab1(tabellato);

        } else if ("TAB2".equals(provenienza)) {

            Tab2PK id = new Tab2PK(codiceTabellato, identificativo);
            Tab2 tabellato = tab2Repository.findById(id).orElse(null);

            if (tabellato != null)
                return getTab2DTOFromTab2(tabellato);

        } else if ("TAB3".equals(provenienza)) {

            Tab3PK id = new Tab3PK(codiceTabellato, identificativo);
            Tab3 tabellato = tab3Repository.findById(id).orElse(null);

            if (tabellato != null)
                return getTab3DTOFromTab3(tabellato);

        } else if ("TAB5".equals(provenienza)) {

            Tab5PK id = new Tab5PK(codiceTabellato, identificativo);
            Tab5 tabellato = tab5Repository.findById(id).orElse(null);

            if (tabellato != null)
                return getTab5DTOFromTab5(tabellato);
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO insertTab0(final String codiceTabellato, final Tab0DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start TabellatiServiceImpl::insertTab0 for codiceTabellato [ {} ] and form [ {} ]",
                codiceTabellato, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueInsert = true;
        List<String> errorMessages = new ArrayList<>();

        String identificativo = StringUtils.stripToNull(form.getTab0tip());

        if (StringUtils.isBlank(identificativo)) {

            LOGGER.error("identificativo null");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NULL);
        }

        Tab0PK id = new Tab0PK(codiceTabellato, identificativo);
        Tab0 found = tab0Repository.findById(id).orElse(null);

        if (found != null) {

            LOGGER.error("Identificativo gia' inserito");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED);

        }

        if (continueInsert) {

            Tab0 toInsert = new Tab0();
            toInsert.setId(id);
            toInsert.setTab0vid(form.getTab0vid());
            toInsert.setOrdinamento(form.getOrdinamento());
            toInsert.setDescrizione(form.getDescrizione());
            toInsert.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toInsert.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toInsert = tab0Repository.save(toInsert);

            response.setResponse(getTab0DTOFromTab0(toInsert));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO insertTab1(final String codiceTabellato, final Tab1DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start TabellatiServiceImpl::insertTab1 for codiceTabellato [ {} ] and form [ {} ]",
                codiceTabellato, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueInsert = true;
        List<String> errorMessages = new ArrayList<>();

        Long identificativo = form.getTab1tip();

        if (identificativo == null) {

            LOGGER.error("identificativo null");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NULL);
        }

        Tab1PK id = new Tab1PK(codiceTabellato, identificativo);
        Tab1 found = tab1Repository.findById(id).orElse(null);

        if (found != null) {

            LOGGER.error("Identificativo gia' inserito");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED);

        }

        if (continueInsert) {

            Tab1 toInsert = new Tab1();
            toInsert.setId(id);
            toInsert.setOrdinamento(form.getOrdinamento());
            toInsert.setDescrizione(form.getDescrizione());
            toInsert.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toInsert.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toInsert = tab1Repository.save(toInsert);

            response.setResponse(getTab1DTOFromTab1(toInsert));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO insertTab2(final String codiceTabellato, final Tab2DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start TabellatiServiceImpl::insertTab2 for codiceTabellato [ {} ] and form [ {} ]",
                codiceTabellato, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueInsert = true;
        List<String> errorMessages = new ArrayList<>();

        String identificativo = StringUtils.stripToNull(form.getTab2tip());

        if (StringUtils.isBlank(identificativo)) {

            LOGGER.error("identificativo null");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NULL);
        }

        Tab2PK id = new Tab2PK(codiceTabellato, identificativo);
        Tab2 found = tab2Repository.findById(id).orElse(null);

        if (found != null) {

            LOGGER.error("Identificativo gia' inserito");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED);

        }

        if (continueInsert) {

            Tab2 toInsert = new Tab2();
            toInsert.setId(id);
            toInsert.setOrdinamento(form.getOrdinamento());
            toInsert.setParametroAssociato(form.getParametroAssociato());
            toInsert.setDescrizione(form.getDescrizione());
            toInsert.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toInsert.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toInsert = tab2Repository.save(toInsert);

            response.setResponse(getTab2DTOFromTab2(toInsert));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO insertTab3(final String codiceTabellato, final Tab3DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start TabellatiServiceImpl::insertTab3 for codiceTabellato [ {} ] and form [ {} ]",
                codiceTabellato, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueInsert = true;
        List<String> errorMessages = new ArrayList<>();

        String identificativo = StringUtils.stripToNull(form.getTab3tip());

        if (StringUtils.isBlank(identificativo)) {

            LOGGER.error("identificativo null");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NULL);
        }

        Tab3PK id = new Tab3PK(codiceTabellato, identificativo);
        Tab3 found = tab3Repository.findById(id).orElse(null);

        if (found != null) {

            LOGGER.error("Identificativo gia' inserito");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED);

        }

        if (continueInsert) {

            Tab3 toInsert = new Tab3();
            toInsert.setId(id);
            toInsert.setOrdinamento(form.getOrdinamento());
            toInsert.setDescrizione(form.getDescrizione());
            toInsert.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toInsert.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toInsert = tab3Repository.save(toInsert);

            response.setResponse(getTab3DTOFromTab3(toInsert));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO insertTab5(final String codiceTabellato, final Tab5DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start TabellatiServiceImpl::insertTab5 for codiceTabellato [ {} ] and form [ {} ]",
                codiceTabellato, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueInsert = true;
        List<String> errorMessages = new ArrayList<>();

        String identificativo = StringUtils.stripToNull(form.getTab5tip());

        if (StringUtils.isBlank(identificativo)) {

            LOGGER.error("identificativo null");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NULL);
        }

        Tab5PK id = new Tab5PK(codiceTabellato, identificativo);
        Tab5 found = tab5Repository.findById(id).orElse(null);

        if (found != null) {

            LOGGER.error("Identificativo gia' inserito");

            continueInsert = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED);

        }

        if (continueInsert) {

            Tab5 toInsert = new Tab5();
            toInsert.setId(id);
            toInsert.setOrdinamento(form.getOrdinamento());
            toInsert.setDescrizione(form.getDescrizione());
            toInsert.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toInsert.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toInsert = tab5Repository.save(toInsert);

            response.setResponse(getTab5DTOFromTab5(toInsert));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateTab0(final String codiceTabellato, final String identificativo, final Tab0DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (StringUtils.isBlank(identificativo))
            throw new IllegalArgumentException("identificativo null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::updateTab0 for codiceTabellato [ {} ], identificativo [ {} ] and form [ {} ]",
                codiceTabellato, identificativo, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        Tab0PK id = new Tab0PK(codiceTabellato, identificativo);
        Tab0 toUpdate = tab0Repository.findById(id).orElse(null);

        if (toUpdate == null) {

            LOGGER.error("Identificativo non trovato");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

        }

        if (AppConstants.W_CONFIG_SI.equals(toUpdate.getBloccato())) {

            LOGGER.error("Tentativo di modifica di un dato tabellato bloccato: tabXmod = 1");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

        }

        if (continueUpdate) {

            toUpdate.setTab0vid(form.getTab0vid());
            toUpdate.setOrdinamento(form.getOrdinamento());
            toUpdate.setDescrizione(form.getDescrizione());
            toUpdate.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toUpdate.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toUpdate = tab0Repository.save(toUpdate);

            response.setResponse(getTab0DTOFromTab0(toUpdate));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateTab1(final String codiceTabellato, final Long identificativo, final Tab1DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (identificativo == null)
            throw new IllegalArgumentException("identificativo null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::updateTab1 for codiceTabellato [ {} ], identificativo [ {} ] and form [ {} ]",
                codiceTabellato, identificativo, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        Tab1PK id = new Tab1PK(codiceTabellato, identificativo);
        Tab1 toUpdate = tab1Repository.findById(id).orElse(null);

        if (toUpdate == null) {

            LOGGER.error("Identificativo non trovato");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

        }

        if (AppConstants.W_CONFIG_SI.equals(toUpdate.getBloccato())) {

            LOGGER.error("Tentativo di modifica di un dato tabellato bloccato: tabXmod = 1");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

        }

        if (continueUpdate) {

            toUpdate.setOrdinamento(form.getOrdinamento());
            toUpdate.setDescrizione(form.getDescrizione());
            toUpdate.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toUpdate.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toUpdate = tab1Repository.save(toUpdate);

            response.setResponse(getTab1DTOFromTab1(toUpdate));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateTab2(final String codiceTabellato, final String identificativo, final Tab2DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (StringUtils.isBlank(identificativo))
            throw new IllegalArgumentException("identificativo null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::updateTab2 for codiceTabellato [ {} ], identificativo [ {} ] and form [ {} ]",
                codiceTabellato, identificativo, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        Tab2PK id = new Tab2PK(codiceTabellato, identificativo);
        Tab2 toUpdate = tab2Repository.findById(id).orElse(null);

        if (toUpdate == null) {

            LOGGER.error("Identificativo non trovato");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

        }

        if (AppConstants.W_CONFIG_SI.equals(toUpdate.getBloccato())) {

            LOGGER.error("Tentativo di modifica di un dato tabellato bloccato: tabXmod = 1");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

        }

        if (continueUpdate) {

            toUpdate.setOrdinamento(form.getOrdinamento());
            toUpdate.setParametroAssociato(form.getParametroAssociato());
            toUpdate.setDescrizione(form.getDescrizione());
            toUpdate.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toUpdate.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toUpdate = tab2Repository.save(toUpdate);

            response.setResponse(getTab2DTOFromTab2(toUpdate));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateTab3(final String codiceTabellato, final String identificativo, final Tab3DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (StringUtils.isBlank(identificativo))
            throw new IllegalArgumentException("identificativo null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::updateTab3 for codiceTabellato [ {} ], identificativo [ {} ] and form [ {} ]",
                codiceTabellato, identificativo, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        Tab3PK id = new Tab3PK(codiceTabellato, identificativo);
        Tab3 toUpdate = tab3Repository.findById(id).orElse(null);

        if (toUpdate == null) {

            LOGGER.error("Identificativo non trovato");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

        }

        if (AppConstants.W_CONFIG_SI.equals(toUpdate.getBloccato())) {

            LOGGER.error("Tentativo di modifica di un dato tabellato bloccato: tabXmod = 1");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

        }

        if (continueUpdate) {

            toUpdate.setOrdinamento(form.getOrdinamento());
            toUpdate.setDescrizione(form.getDescrizione());
            toUpdate.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toUpdate.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toUpdate = tab3Repository.save(toUpdate);

            response.setResponse(getTab3DTOFromTab3(toUpdate));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO updateTab5(final String codiceTabellato, final String identificativo, final Tab5DTO form) {

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (StringUtils.isBlank(identificativo))
            throw new IllegalArgumentException("identificativo null");

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::updateTab5 for codiceTabellato [ {} ], identificativo [ {} ] and form [ {} ]",
                codiceTabellato, identificativo, form);

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueUpdate = true;
        List<String> errorMessages = new ArrayList<>();

        Tab5PK id = new Tab5PK(codiceTabellato, identificativo);
        Tab5 toUpdate = tab5Repository.findById(id).orElse(null);

        if (toUpdate == null) {

            LOGGER.error("Identificativo non trovato");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

        }

        if (AppConstants.W_CONFIG_SI.equals(toUpdate.getBloccato())) {

            LOGGER.error("Tentativo di modifica di un dato tabellato bloccato: tabXmod = 1");

            continueUpdate = false;
            errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

        }

        if (continueUpdate) {

            toUpdate.setOrdinamento(form.getOrdinamento());
            toUpdate.setDescrizione(form.getDescrizione());
            toUpdate.setBloccato(StringUtils.stripToNull(form.getBloccato()));
            toUpdate.setArchiviato(StringUtils.stripToNull(form.getArchiviato()));

            toUpdate = tab5Repository.save(toUpdate);

            response.setResponse(getTab5DTOFromTab5(toUpdate));

        } else {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseDTO deleteTabellato(final String provenienza, final String codiceTabellato,
                                       final String identificativo) {

        if (StringUtils.isBlank(provenienza))
            throw new IllegalArgumentException("provenienza null");

        if (StringUtils.isBlank(codiceTabellato))
            throw new IllegalArgumentException("codiceTabellato null");

        if (StringUtils.isBlank(identificativo))
            throw new IllegalArgumentException("identificativo null");

        LOGGER.debug(
                "Execution start TabellatiServiceImpl::deleteTabellato for provenienza [ {} ], codiceTabellato [ {} ] and identificativo [ {} ]",
                provenienza, codiceTabellato, identificativo);

        String deleteLockMessage = "Tentativo di cancellazione di un dato tabellato bloccato: tabXmod = 1";

        ResponseDTO response = new ResponseDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        boolean continueDelete = true;
        List<String> errorMessages = new ArrayList<>();

        if ("TAB0".equals(provenienza)) {

            Tab0PK id = new Tab0PK(codiceTabellato, identificativo);
            Tab0 toDelete = tab0Repository.findById(id).orElse(null);

            if (toDelete == null) {

                LOGGER.error("Identificativo non trovato");

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

            }

            if (AppConstants.W_CONFIG_SI.equals(toDelete.getBloccato())) {

                LOGGER.error(deleteLockMessage);

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

            }

            if (continueDelete) {
                tab0Repository.deleteById(id);
                response.setResponse(true);
            }

        } else if ("TAB1".equals(provenienza)) {

            Tab1PK id = new Tab1PK(codiceTabellato, Long.valueOf(identificativo));
            Tab1 toDelete = tab1Repository.findById(id).orElse(null);

            if (toDelete == null) {

                LOGGER.error("Identificativo non trovato");

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

            }

            if (AppConstants.W_CONFIG_SI.equals(toDelete.getBloccato())) {

                LOGGER.error(deleteLockMessage);

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

            }

            if (continueDelete) {
                tab1Repository.deleteById(id);
                response.setResponse(true);
            }

        } else if ("TAB2".equals(provenienza)) {

            Tab2PK id = new Tab2PK(codiceTabellato, identificativo);
            Tab2 toDelete = tab2Repository.findById(id).orElse(null);

            if (toDelete == null) {

                LOGGER.error("Identificativo non trovato");

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

            }

            if (AppConstants.W_CONFIG_SI.equals(toDelete.getBloccato())) {

                LOGGER.error(deleteLockMessage);

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

            }

            if (continueDelete) {
                tab2Repository.deleteById(id);
                response.setResponse(true);
            }

        } else if ("TAB3".equals(provenienza)) {

            Tab3PK id = new Tab3PK(codiceTabellato, identificativo);
            Tab3 toDelete = tab3Repository.findById(id).orElse(null);

            if (toDelete == null) {

                LOGGER.error("Identificativo non trovato");

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

            }

            if (AppConstants.W_CONFIG_SI.equals(toDelete.getBloccato())) {

                LOGGER.error(deleteLockMessage);

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

            }

            if (continueDelete) {
                tab3Repository.deleteById(id);
                response.setResponse(true);
            }

        } else if ("TAB5".equals(provenienza)) {

            Tab5PK id = new Tab5PK(codiceTabellato, identificativo);
            Tab5 toDelete = tab5Repository.findById(id).orElse(null);

            if (toDelete == null) {

                LOGGER.error("Identificativo non trovato");

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND);

            }

            if (AppConstants.W_CONFIG_SI.equals(toDelete.getBloccato())) {

                LOGGER.error(deleteLockMessage);

                continueDelete = false;
                errorMessages.add(AppConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO);

            }

            if (continueDelete) {
                tab5Repository.deleteById(id);
                response.setResponse(true);
            }

        }

        if (!continueDelete) {

            response.setDone(AppConstants.RESPONSE_DONE_N);
            response.setMessages(errorMessages);

        }

        return response;
    }

    private String calcolaProvenienzaTabellato(final String tabTip) {

        if (StringUtils.isBlank(tabTip) || (StringUtils.isNotBlank(tabTip) && tabTip.length() < 3))
            return null;

        String c = tabTip.substring(2, 3);

        switch (c) {
            case "x":
            case "y":
                return "TAB3";
            case "w":
            case "k":
                return "TAB0";
            case "v":
            case "z":
                return "TAB2";
            case "j":
                return "TAB5";
            default:
                return "TAB1";
        }
    }

    private Tab0DTO getTab0DTOFromTab0(final Tab0 tab0) {

        Tab0DTO dto = new Tab0DTO();

        dto.setTab0cod(tab0.getId().getTab0cod());
        dto.setTab0tip(tab0.getId().getTab0tip());
        dto.setTab0vid(tab0.getTab0vid());
        dto.setBloccato(tab0.getBloccato());
        dto.setOrdinamento(tab0.getOrdinamento());
        dto.setDescrizione(tab0.getDescrizione());
        dto.setArchiviato(tab0.getArchiviato());

        return dto;
    }

    private Tab1DTO getTab1DTOFromTab1(final Tab1 tab1) {

        Tab1DTO dto = new Tab1DTO();

        dto.setTab1cod(tab1.getId().getTab1cod());
        dto.setTab1tip(tab1.getId().getTab1tip());
        dto.setBloccato(tab1.getBloccato());
        dto.setOrdinamento(tab1.getOrdinamento());
        dto.setDescrizione(tab1.getDescrizione());
        dto.setArchiviato(tab1.getArchiviato());

        return dto;
    }

    private Tab2DTO getTab2DTOFromTab2(final Tab2 tab2) {

        Tab2DTO dto = new Tab2DTO();

        dto.setTab2cod(tab2.getId().getTab2cod());
        dto.setTab2tip(tab2.getId().getTab2tip());
        dto.setBloccato(tab2.getBloccato());
        dto.setOrdinamento(tab2.getOrdinamento());
        dto.setParametroAssociato(tab2.getParametroAssociato());
        dto.setDescrizione(tab2.getDescrizione());
        dto.setArchiviato(tab2.getArchiviato());

        return dto;
    }

    private Tab3DTO getTab3DTOFromTab3(final Tab3 tab3) {

        Tab3DTO dto = new Tab3DTO();

        dto.setTab3cod(tab3.getId().getTab3cod());
        dto.setTab3tip(tab3.getId().getTab3tip());
        dto.setBloccato(tab3.getBloccato());
        dto.setOrdinamento(tab3.getOrdinamento());
        dto.setDescrizione(tab3.getDescrizione());
        dto.setArchiviato(tab3.getArchiviato());

        return dto;
    }

    private Tab5DTO getTab5DTOFromTab5(final Tab5 tab5) {

        Tab5DTO dto = new Tab5DTO();

        dto.setTab5cod(tab5.getId().getTab5cod());
        dto.setTab5tip(tab5.getId().getTab5tip());
        dto.setBloccato(tab5.getBloccato());
        dto.setOrdinamento(tab5.getOrdinamento());
        dto.setDescrizione(tab5.getDescrizione());
        dto.setArchiviato(tab5.getArchiviato());

        return dto;
    }
}
