package it.maggioli.ssointegrms.services.general.impl;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import it.maggioli.ssointegrms.domain.WGenChiavi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.maggioli.ssointegrms.repositories.WGenChiaviRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.WGenChiaviService;

/**
 * @author Cristiano Perin
 */
@Service
public class WGenChiaviServiceImpl extends BaseService implements WGenChiaviService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WGenChiaviServiceImpl.class);

    private static final long DEFAULT_INCREMENT = 1;

    @Autowired
    private WGenChiaviRepository wgcRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
    public Long getNextId(final String tabella) {
        LOGGER.debug("Execution start WGenChiaviServiceImpl::getNextId for tabella [ {} ]", tabella);
        WGenChiavi wGenChiavi = wgcRepository.findByTabellaIgnoreCase(tabella).orElseThrow(() -> new IllegalArgumentException("Tabella non trovata"));
        long chiaveIncrementata = wGenChiavi.getChiave() + DEFAULT_INCREMENT;
        wGenChiavi.setChiave(chiaveIncrementata);
        wGenChiavi = wgcRepository.save(wGenChiavi);
        return wGenChiavi.getChiave();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
    public Long getNextId(final String tabella, final Long increment) {
        LOGGER.debug("Execution start WGenChiaviServiceImpl::getNextId for tabella [ {} ] and increment [ {} ]",
                tabella, increment);
        WGenChiavi wGenChiavi = wgcRepository.findByTabellaIgnoreCase(tabella).orElseThrow(() -> new IllegalArgumentException("Tabella non trovata"));
        long chiaveIncrementata = wGenChiavi.getChiave() + increment;
        wGenChiavi.setChiave(chiaveIncrementata);
        wGenChiavi = wgcRepository.save(wGenChiavi);
        return wGenChiavi.getChiave();
    }

}
