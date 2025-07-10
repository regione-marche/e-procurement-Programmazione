package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.domain.WLogEventi;
import it.maggioli.ssointegrms.dto.WLogEventiDTO;
import it.maggioli.ssointegrms.services.general.WLogEventiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Cristiano Perin
 */
public class WLogEventiServiceSQLServerImpl extends WLogEventiServiceBase implements WLogEventiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WLogEventiServiceSQLServerImpl.class);

    /**
     * Propagation.REQUIRES_NEW
     * <p>
     * per garantire la scrittura dei log anche quando la transazione fallisce per
     * qualche motivo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void insertLogEvent(final WLogEventiDTO dto) {

        if (dto == null)
            throw new IllegalArgumentException("dto null");

        LOGGER.debug("Execution start WLogEventiServiceSQLServerImpl::insertLogEvent for dto [ {} ]", dto);

        WLogEventi po = dtoMapper.convertTo(dto, WLogEventi.class);

        User user = null;

        if (dto.getSyscon() != null)
            user = userRepository.findById(dto.getSyscon()).orElse(null);

        po.setUser(user);

        Long id = wgcService.getNextId(AppConstants.W_GENCHIAVI_W_LOGEVENTI);


        po.setIdEvento(id);

        po = wLogEventiRepository.save(po);

        super.writeToFile(po);

    }

}
