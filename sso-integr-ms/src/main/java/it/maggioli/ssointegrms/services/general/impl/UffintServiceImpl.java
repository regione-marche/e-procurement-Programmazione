package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.domain.Uffint;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.UfficioIntestatarioDTO;
import it.maggioli.ssointegrms.dto.UserDTO;
import it.maggioli.ssointegrms.repositories.UffintRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.UffintService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class UffintServiceImpl extends BaseService implements UffintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UffintServiceImpl.class);

    @Autowired
    private UffintRepository uffintRepository;

    @Override
    public List<UfficioIntestatarioDTO> getListaOpzioniUfficioIntestatario(final String searchData, final UserDTO currentUserDTO) {

        LOGGER.debug("Execution start UffintServiceImpl::getListaOpzioniUfficioIntestatario for searchData [ {} ]",
                searchData);

        if (StringUtils.isBlank(searchData))
            throw new IllegalArgumentException("searchData null");

        User currentUser = getUserFromUserDTO(currentUserDTO);

        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        List<UfficioIntestatarioDTO> listaDTO = new ArrayList<>();

        List<Uffint> listaDomain = null;

        if (utenteDelegatoGestioneUtenti) {
            List<String> listaStazioniAppaltantiAssociate = new ArrayList<>();
            if (currentUser.getUffints() != null && !currentUser.getUffints().isEmpty()) {
                listaStazioniAppaltantiAssociate = currentUser.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList());
            }
            listaDomain = uffintRepository.getListaOpzioniUffintDelegato(listaStazioniAppaltantiAssociate, searchData.trim().toLowerCase());
        } else {
            listaDomain = uffintRepository.getListaOpzioniUffint(searchData.trim().toLowerCase());
        }

        if (listaDomain != null && !listaDomain.isEmpty()) {
            listaDTO = listaDomain.stream().map(u -> dtoMapper.convertTo(u, UfficioIntestatarioDTO.class))
                    .collect(Collectors.toList());

            listaDTO.sort(Comparator.comparing(UfficioIntestatarioDTO::getDenominazione));
        }

        return listaDTO;
    }

    @Override
    public UfficioIntestatarioDTO getUfficioIntestatario(final String codice) {

        LOGGER.debug("Execution start UffintServiceImpl::getUfficioIntestatario for codice [ {} ]", codice);

        if (StringUtils.isBlank(codice))
            throw new IllegalArgumentException("codice null");

        Uffint ufficioIntestatario = uffintRepository.findById(codice).orElse(null);

        if (ufficioIntestatario != null)
            return dtoMapper.convertTo(ufficioIntestatario, UfficioIntestatarioDTO.class);

        return null;
    }

    @Override
    public Uffint getUffintInternal(final String codice) {

        LOGGER.debug("Execution start UffintServiceImpl::getUffintInternal for codice [ {} ]", codice);

        if (StringUtils.isBlank(codice))
            throw new IllegalArgumentException("codice null");

        return uffintRepository.findById(codice).orElse(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<UfficioIntestatarioDTO> getListaUfficiIntestatari(final UserDTO currentUserDTO) {

        LOGGER.debug("Execution start UffintServiceImpl::getListaUfficiIntestatari");

        List<UfficioIntestatarioDTO> listaDTO = new ArrayList<>();

        User currentUser = getUserFromUserDTO(currentUserDTO);

        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        List<Uffint> listaDomain = null;

        if (utenteDelegatoGestioneUtenti) {
            List<String> listaUfficiIntestatariGestore = currentUser.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList());
            if (!listaUfficiIntestatariGestore.isEmpty())
                listaDomain = uffintRepository.findByIds(listaUfficiIntestatariGestore);
        } else {
            listaDomain = uffintRepository.findAll(Sort.by(Sort.Direction.ASC, "denominazione", "codice"));
        }

        if (listaDomain != null && !listaDomain.isEmpty()) {
            listaDTO = listaDomain.stream().map(u -> dtoMapper.convertTo(u, UfficioIntestatarioDTO.class))
                    .collect(Collectors.toList());
        }

        return listaDTO;
    }
}
