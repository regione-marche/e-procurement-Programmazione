package it.appaltiecontratti.autenticazione.controller;

import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 12, 2024
 */
public abstract class AbstractBaseController {

    @Autowired
    private UserManager userManager;

    protected String resolveRemoteIpAddress(final HttpServletRequest request) {
        String xRealIp = request.getHeader("X-Real-IP");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        if (StringUtils.isNotBlank(xRealIp))
            return xRealIp;

        String ip = StringUtils.firstNonBlank(xForwardedFor, remoteAddr);

        if (StringUtils.isNotBlank(ip) && ip.contains(","))
            ip = ip.substring(0, ip.indexOf(","));

        return ip;
    }

    protected Long getSysconFromJwtToken(final String authorization, final String loginMode) {
        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

            return userAuthClaimsDTO.getSyscon();

        } catch (Exception e) {
            return null;
        }
    }
}
