package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.WGenChiaviMapper;

@Component
public class WGenChiaviManager {

	private final static Logger logger = LoggerFactory.getLogger(WGenChiaviManager.class);
	
	@SuppressWarnings("java:S3749")
	private final long DEFAULT_INCREMENT = 1;
	
	@Autowired
	private SqlMapper sqlMapper;	

	@Autowired
	private WGenChiaviMapper wgcMapper;

	@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") })
	public Long getNextId(final String tabella) {
		logger.debug("Execution start WGenChiaviServiceImpl::getNextId for tabella [ {} ]", tabella);
		Long chiave = wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
		long chiaveIncrementata = chiave + DEFAULT_INCREMENT;
		wgcMapper.incrementChiavePerTabella(chiaveIncrementata, tabella);
		return wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") })
	public Long getNextId(final String tabella, final Long increment) {
		logger.debug("Execution start WGenChiaviServiceImpl::getNextId for tabella [ {} ] and increment [ {} ]",
				tabella, increment);
		Long chiave = wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
		long chiaveIncrementata = chiave + increment;
		wgcMapper.incrementChiavePerTabella(chiaveIncrementata, tabella);
		return wgcMapper.findByTabellaIgnoreCase(tabella).getChiave();
	}
		
}
