package it.maggioli.ssointegrms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.maggioli.ssointegrms.exceptions.oneGateway.TokenExpiredException;
import it.maggioli.ssointegrms.model.ResponseSuccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since giu 09, 2023
 */
public abstract class AbstractBaseController {

    @Value("${application.codiceProdotto}")
    protected String codiceProdotto;

    private ObjectMapper objectMapper;

    public AbstractBaseController() {
        objectMapper = new ObjectMapper();
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected void checkTokenExpiration(final ResponseSuccess rs) {
        // controllo la scadenza della richiesta
        long timestamp = (long) rs.get_ts_();
        long now = Instant.now().getEpochSecond();
        long elapsed = now - timestamp;
        if (elapsed > 5 * 60) {
            throw new TokenExpiredException("Richiesta scaduta");
        }
    }

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
}
