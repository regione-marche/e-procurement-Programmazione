package it.maggioli.ssointegrms.services.oneGateway.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.exceptions.oneGateway.EncryptException;
import it.maggioli.ssointegrms.model.CieJwtToken;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.oneGateway.CryptoService;
import it.maggioli.ssointegrms.services.oneGateway.GatewayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

/**
 * @author Cristiano Perin
 */
@Service("cieGatewayService")
@Qualifier("cieGatewayService")
public class CieGatewayServiceImpl extends BaseService implements GatewayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CieGatewayServiceImpl.class);

    @Value("${application.oneGateway.oneGatewayCieUrl}")
    private String oneGatewayCieUrl;

    @Value("${application.oneGateway.cieEnv}")
    private String cieEnv;

    @Autowired
    private CryptoService cryptoService;

    public String authenticate(final String clientId) throws EncryptException {
        LOGGER.info("Execution start CieGatewayServiceImpl::authenticate() for clientId {}", clientId);

        if (StringUtils.isBlank(clientId))
            return null;

        long timestamp = Instant.now().getEpochSecond();
        LOGGER.info("Executing authentication at time {}", timestamp);

        String uuid = super.generateUUID();

        CieJwtToken cieJwtTokenObj = new CieJwtToken();
        cieJwtTokenObj.setUuid(uuid);
        cieJwtTokenObj.setTimestamp(timestamp);
        cieJwtTokenObj.setAttrcsidx(0);
        cieJwtTokenObj.setEnv(cieEnv);

        try {
            String jsonRequest = new ObjectMapper().writeValueAsString(cieJwtTokenObj);
            LOGGER.info("CIE Json {}", jsonRequest);
        } catch (JsonProcessingException e) {
            LOGGER.error("Errore durante il log del json di richiesta", e);
        }

        try {
            String jwtToken = cryptoService.encryptIdpJwtToken(clientId, cieJwtTokenObj);
            LOGGER.info("JWT Token {}", jwtToken);

            String mimsClientId = OneGatewayAppConstants.SERVICE_PROVIDERS.get(clientId).getMimsClientId();

            String url = UriComponentsBuilder.fromHttpUrl(oneGatewayCieUrl).queryParam("client-id", mimsClientId)
                    .queryParam("request", jwtToken).toUriString();

            return url;
        } catch (EncryptException e) {
            LOGGER.error("Errore EncryptException", e);
            throw e;
        }
    }

}
