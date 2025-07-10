package it.maggioli.ssointegrms.services.oneGateway.impl;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.exceptions.oneGateway.EncryptException;
import it.maggioli.ssointegrms.model.SpidJwtToken;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.oneGateway.CryptoService;
import it.maggioli.ssointegrms.services.oneGateway.GatewayService;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Service("spidGatewayService")
@Qualifier("spidGatewayService")
public class SpidGatewayServiceImpl extends BaseService implements GatewayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpidGatewayServiceImpl.class);

	@Value("${application.oneGateway.oneGatewaySpidUrl}")
	private String oneGatewaySpidUrl;

	@Autowired
	private CryptoService cryptoService;

	public String authenticate(final String clientId) throws EncryptException {
		LOGGER.info("Execution start SpidGatewayServiceImpl::authenticate() for clientId {}", clientId);

		if (StringUtils.isBlank(clientId) || !OneGatewayAppConstants.SERVICE_PROVIDERS.containsKey(clientId))
			return null;

		long timestamp = Instant.now().getEpochSecond();
		LOGGER.info("Executing authentication at time {}", timestamp);

		String uuid = super.generateUUID();

		SpidJwtToken spidJwtTokenObj = new SpidJwtToken();
		spidJwtTokenObj.setUuid(uuid);
		spidJwtTokenObj.setTimestamp(timestamp);
		spidJwtTokenObj.setAttrcsidx(1);
//		spidJwtTokenObj.setPurpose(AppConstants.ID_PERSONA_FISICA);

		try {
			String jsonRequest = new ObjectMapper().writeValueAsString(spidJwtTokenObj);
			LOGGER.info("SPID Json {}", jsonRequest);
		} catch (JsonProcessingException e) {
			LOGGER.error("Errore durante il log del json di richiesta", e);
		}

		try {
			String jwtToken = cryptoService.encryptIdpJwtToken(clientId, spidJwtTokenObj);
			LOGGER.info("JWT Token {}", jwtToken);

			String mimsClientId = OneGatewayAppConstants.SERVICE_PROVIDERS.get(clientId).getMimsClientId();

			String url = UriComponentsBuilder.fromHttpUrl(oneGatewaySpidUrl).queryParam("client-id", mimsClientId)
					.queryParam("request", jwtToken).toUriString();

			return url;
		} catch (EncryptException e) {
			LOGGER.error("Errore EncryptException", e);
			throw e;
		}
	}

}
