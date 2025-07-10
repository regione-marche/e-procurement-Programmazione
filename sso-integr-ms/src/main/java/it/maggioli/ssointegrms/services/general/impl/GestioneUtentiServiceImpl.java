package it.maggioli.ssointegrms.services.general.impl;

import java.util.Optional;

import it.maggioli.ssointegrms.common.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.maggioli.ssointegrms.domain.User;
import it.maggioli.ssointegrms.exceptions.internalAuthentication.BadCredentialsException;
import it.maggioli.ssointegrms.repositories.UserRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.GestioneUtentiService;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Service
public class GestioneUtentiServiceImpl extends BaseService implements GestioneUtentiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GestioneUtentiServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void disabilitaUtente(final Long syscon) {

		LOGGER.debug("Execution start GestioneUtentiServiceImpl::disabilitaUtente for syscon [ {} ]", syscon);

		if (syscon == null)
			throw new IllegalArgumentException("syscon null");

		Optional<User> user = userRepository.findById(syscon);

		if (!user.isPresent()) {
			LOGGER.error("User not found for syscon [ {} ]", syscon);
			throw new BadCredentialsException();
		}

		User found = user.get();

		found.setDisabilitato(AppConstants.W_CONFIG_SI);

		found = userRepository.save(found);

	}
}
