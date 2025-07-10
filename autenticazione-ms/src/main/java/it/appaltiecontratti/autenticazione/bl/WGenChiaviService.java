package it.appaltiecontratti.autenticazione.bl;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.autenticazione.mapper.WGenChiaviMapper;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Service
public class WGenChiaviService {

	private static final Logger LOGGER = LogManager.getLogger(WGenChiaviService.class);

	private static final long DEFAULT_INCREMENT = 1;

	@Autowired
	private WGenChiaviMapper wgcMapper;

	@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") })
	public Long getNextId(final String tabella) {
		LOGGER.debug("Execution start WGenChiaviServiceImpl::getNextId for tabella [ {} ]", tabella);
		Long chiave = wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
		long chiaveIncrementata = chiave + DEFAULT_INCREMENT;
		wgcMapper.incrementChiavePerTabella(chiaveIncrementata, tabella);
		return wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") })
	public Long getNextId(final String tabella, final Long increment) {
		LOGGER.debug("Execution start WGenChiaviServiceImpl::getNextId for tabella [ {} ] and increment [ {} ]",
				tabella, increment);
		Long chiave = wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
		long chiaveIncrementata = chiave + increment;
		wgcMapper.incrementChiavePerTabella(chiaveIncrementata, tabella);
		return wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
	}
}
