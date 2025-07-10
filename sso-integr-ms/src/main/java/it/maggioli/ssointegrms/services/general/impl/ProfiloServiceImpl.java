package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.domain.Profilo;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.ProfiloDTO;
import it.maggioli.ssointegrms.dto.UserDTO;
import it.maggioli.ssointegrms.repositories.ProfiloRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.ProfiloService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class ProfiloServiceImpl extends BaseService implements ProfiloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfiloServiceImpl.class);

    @Autowired
    private ProfiloRepository profiloRepository;

    @Override
    public List<ProfiloDTO> getListaOpzioniProfilo(final String searchData) {

        LOGGER.debug("Execution start ProfiloServiceImpl::getListaOpzioniProfilo for searchData [ {} ]", searchData);

        if (StringUtils.isBlank(searchData))
            throw new IllegalArgumentException("searchData null");

        List<ProfiloDTO> listaDTO = new ArrayList<>();

        List<Profilo> listaDomain = profiloRepository.getListaOpzioniProfilo(searchData.trim().toLowerCase());

        if (listaDomain != null && listaDomain.size() > 0) {
            listaDTO = listaDomain.stream().map(u -> dtoMapper.convertTo(u, ProfiloDTO.class))
                    .collect(Collectors.toList());
        }

        return listaDTO;
    }

    @Override
    public ProfiloDTO getProfilo(final String codice) {

        LOGGER.debug("Execution start ProfiloServiceImpl::getProfilo for codice [ {} ]", codice);

        if (StringUtils.isBlank(codice))
            throw new IllegalArgumentException("codice null");

        Profilo profilo = profiloRepository.findById(codice).orElse(null);

        if (profilo != null)
            return dtoMapper.convertTo(profilo, ProfiloDTO.class);

        return null;

    }

    @Override
    public Profilo getProfiloInternal(final String codice) {

        LOGGER.debug("Execution start ProfiloServiceImpl::getProfiloInternal for codice [ {} ]", codice);

        if (StringUtils.isBlank(codice))
            throw new IllegalArgumentException("codice null");

        return profiloRepository.findById(codice).orElse(null);

    }

    @Override
    @Transactional
    public List<ProfiloDTO> getListaProfili(final UserDTO currentUserDTO) {

        LOGGER.debug("Execution start ProfiloServiceImpl::getListaProfili");

        List<ProfiloDTO> profileList = new ArrayList<>();

        User currentUser = getUserFromUserDTO(currentUserDTO);

        boolean utenteDelegatoGestioneUtenti = isUtenteDelegatoAllaGestioneUtenti(currentUser);

        List<Profilo> profili = null;

        if (utenteDelegatoGestioneUtenti) {
            List<String> listaProfiliGestore = currentUser.getProfili().parallelStream().map(Profilo::getCodice).collect(Collectors.toList());
            if (!listaProfiliGestore.isEmpty())
                profili = profiloRepository.findByIds(listaProfiliGestore);
        } else {
            profili = profiloRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizione", "codice"));
        }

        if (profili != null && !profili.isEmpty()) {
            profileList = profili.stream().map(p -> dtoMapper.convertTo(p, ProfiloDTO.class))
                    .collect(Collectors.toList());
            profileList.sort(Comparator.comparing(ProfiloDTO::getCodice));
        }

        return profileList;
    }
}
