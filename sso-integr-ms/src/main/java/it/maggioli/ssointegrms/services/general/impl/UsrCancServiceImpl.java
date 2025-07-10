package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.UsrCanc;
import it.maggioli.ssointegrms.repositories.UsrCancRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.UsrCancService;
import it.maggioli.ssointegrms.services.general.WGenChiaviService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Cristiano Perin
 */
@Service
public class UsrCancServiceImpl extends BaseService implements UsrCancService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsrCancServiceImpl.class);

    @Autowired
    private WGenChiaviService wgcService;

    @Autowired
    private UsrCancRepository usrCancRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UsrCanc insertCancellazione(final Long syscon, final String syslogin) {

        LOGGER.debug("Execution start UsrCancServiceImpl::insertCancellazione for syscon [ {} ] e syslogin [ {} ]",
                syscon, syslogin);

        if (syscon == null)
            throw new IllegalArgumentException("syscon null");

        if (StringUtils.isBlank(syslogin))
            throw new IllegalArgumentException("syslogin null");

        Long idCancellazione = wgcService.getNextId(AppConstants.W_GENCHIAVI_USRCANC);

        UsrCanc toSave = new UsrCanc();
        toSave.setId(idCancellazione);
        toSave.setSyscon(syscon);
        toSave.setLogin(syslogin);
        toSave.setDataScadenza(new Date());

        toSave = usrCancRepository.save(toSave);

        return toSave;
    }
}
