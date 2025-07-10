package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.LoginKO;
import it.maggioli.ssointegrms.dto.LoginKODTO;
import it.maggioli.ssointegrms.repositories.LoginKORepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.LoginKOService;
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
public class LoginKOServiceImpl extends BaseService implements LoginKOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginKOServiceImpl.class);

    @Autowired
    private WGenChiaviService wgcService;

    @Autowired
    private LoginKORepository loginKORepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertLoginKO(final String username, final String ipAddress) {

        LOGGER.debug("Execution start LoginKOServiceImpl::insertLoginKO for username [ {} ] e ipAddress [ {} ]",
                username, ipAddress);

        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("username null");

        Long id = wgcService.getNextId(AppConstants.W_GENCHIAVI_G_LOGINKO);

        LoginKO loginKO = new LoginKO();
        loginKO.setId(id);
        loginKO.setUsername(username);
        // now
        loginKO.setLoginTime(new Date());
        loginKO.setIpAddress(ipAddress);

        loginKORepository.save(loginKO);
    }

    @Override
    public Integer countCurrentLoginAttempts(final String username) {

        LOGGER.debug("Execution start LoginKOServiceImpl::countCurrentLoginAttempts for username [ {} ]", username);

        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("username null");

        String localUsername = username.toLowerCase();

        Integer counter = loginKORepository.countByUsername(localUsername).orElse(0);

        return counter;

    }

    @Override
    public LoginKODTO getLastLoginAttempt(final String username) {

        LOGGER.debug("Execution start LoginKOServiceImpl::getLastLoginAttempt for username [ {} ]", username);

        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("username null");

        String localUsername = username.toLowerCase();

        LoginKO attempt = loginKORepository.getLastLoginAttempt(localUsername).orElse(null);

        LoginKODTO dto = null;

        if (attempt != null)
            dto = dtoMapper.convertTo(attempt, LoginKODTO.class);

        return dto;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void clearLoginAttempts(final String username) {

        LOGGER.debug("Execution start LoginKOServiceImpl::clearLoginAttempts for username [ {} ]", username);

        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("username null");

        loginKORepository.deleteByUsernameIgnoreCase(username);

    }
}
