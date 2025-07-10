package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.domain.StoUteSys;
import it.maggioli.ssointegrms.domain.StoUteSysPK;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.dto.StoUteSysDTO;
import it.maggioli.ssointegrms.repositories.StoUteSysRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.StoUteSysService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class StoUteSysServiceImpl extends BaseService implements StoUteSysService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoUteSysServiceImpl.class);

    @Autowired
    private StoUteSysRepository stoUteSysRepository;

    @Override
    public StoUteSysDTO getCurrentPasswordInformation(final String sysnom, final String syspwd) {

        LOGGER.debug("Execution start StoUteSysServiceImpl::getCurrentPasswordInformation for sysnom [ {} ]", sysnom);

        if (StringUtils.isBlank(sysnom))
            throw new IllegalArgumentException("sysnom null");

        if (StringUtils.isBlank(syspwd))
            throw new IllegalArgumentException("syspwd null");

        StoUteSysPK id = new StoUteSysPK(sysnom, syspwd);

        StoUteSys po = stoUteSysRepository.findById(id).orElse(null);

        StoUteSysDTO dto = null;

        if (po != null)
            dto = dtoMapper.convertTo(po, StoUteSysDTO.class);

        return dto;
    }

    @Override
    public Integer countUserPasswordInformationBySyscon(final Long syscon) {

        LOGGER.debug("Execution start StoUteSysServiceImpl::countUserPasswordInformationBySyscon for syscon [ {} ]",
                syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        Integer counter = stoUteSysRepository.countBySyscon(syscon).orElse(0);

        return counter;

    }

    @Override
    public List<String> getChangedPasswords(final Long syscon) {

        LOGGER.debug("Execution start StoUteSysServiceImpl::getChangedPasswords for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        List<StoUteSys> found = stoUteSysRepository.findBySysconOrderBySysdatDesc(syscon).orElse(new ArrayList<>());

        List<String> changedPasswordsList = found.stream().map(s -> s.getId().getSyspwd()).collect(Collectors.toList());

        return changedPasswordsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StoUteSysDTO insertChangedPassword(final User user, final String passwordCifrata) throws CriptazioneException {

        if (user == null)
            throw new IllegalArgumentException("user null");

        if (StringUtils.isBlank(passwordCifrata))
            throw new IllegalArgumentException("passwordCifrata null");

        LOGGER.debug("Execution start StoUteSysServiceImpl::insertChangedPassword for username [ {} ]",
                user.getLogin());

        try {
            String nomeUtenteCifrato = super.getValoreCifrato(user.getLogin());

            StoUteSysPK id = new StoUteSysPK();
            id.setSysnom(nomeUtenteCifrato);
            id.setSyspwd(passwordCifrata);
            StoUteSys stoUteSys = new StoUteSys();
            stoUteSys.setId(id);
            stoUteSys.setSyscon(user.getSyscon());
            stoUteSys.setSyslogin(user.getLogin());
            stoUteSys.setSysdat(new Date());

            stoUteSys = stoUteSysRepository.save(stoUteSys);

            return dtoMapper.convertTo(stoUteSys, StoUteSysDTO.class);
        } catch (CriptazioneException e) {
            LOGGER.error("Errore durante la criptazione della login", e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBySyscon(final Long syscon) {

        LOGGER.debug("Execution start StoUteSysServiceImpl::deleteBySyscon for syscon [ {} ]", syscon);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        stoUteSysRepository.deleteBySyscon(syscon);
    }

}
