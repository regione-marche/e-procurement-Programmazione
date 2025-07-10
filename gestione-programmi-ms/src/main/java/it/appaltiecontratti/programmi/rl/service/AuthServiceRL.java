package it.appaltiecontratti.programmi.rl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.appaltiecontratti.programmi.rl.beans.AccessTokenRL;
import it.appaltiecontratti.programmi.rl.beans.ConfigServiceRL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthServiceRL {

    private static final Logger logger = LogManager.getLogger(AuthServiceRL.class);

    public static final String GRANT_TYPE="client_credentials";

    final AccessTokenRL accessTokenRL;
    final RestTemplate restTemplate;

    public AuthServiceRL(RestTemplate restTemplate, AccessTokenRL accessTokenRL) {
        this.restTemplate = restTemplate;
        this.accessTokenRL = accessTokenRL;
    }

    public void invokeToken(ConfigServiceRL configServiceRL) throws JsonProcessingException {
        logger.info("Get access token");
        String oauth2Scope= configServiceRL.getScope();
        String oauth2ConsumerKey=configServiceRL.getKey();
        String oauth2ConsumerSecret=configServiceRL.getSecret();
        String oauth2Url=configServiceRL.getAuthServerUrl();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("scope", oauth2Scope);
        logger.info("OAUTH2_URL: {}",oauth2Url.concat("/token"));
        logger.info("CONSUMER_KEY: {}",oauth2ConsumerKey);
        logger.info("request-access-token: {}",body);

        if (accessTokenRL.isExpired()) {
            logger.info("Request new access token");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            restTemplate.getInterceptors().add(
                    new BasicAuthenticationInterceptor(oauth2ConsumerKey, oauth2ConsumerSecret)
            );

            ResponseEntity<String> responseRestTemplate = restTemplate.exchange(oauth2Url.concat("/token"), HttpMethod.POST, entity, String.class);
            String result = responseRestTemplate.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResult = objectMapper.readTree(result);
            logger.info("RESULT=" + jsonResult);
            if (responseRestTemplate.getStatusCode() != HttpStatus.OK) {
                logger.error("Code error: {}", responseRestTemplate.getStatusCode());
            } else {
                accessTokenRL.setToken(jsonResult.path("access_token").asText());
                accessTokenRL.setIssuedAt(LocalDateTime.now());
                accessTokenRL.setExpiresIn(jsonResult.path("expires_in").asInt());
                accessTokenRL.setExpiresAt(LocalDateTime.now().plusSeconds(accessTokenRL.getExpiresIn()));
            }
        }else{
            logger.info("Skip request new access token");
        }

        logger.debug("access-token={}", accessTokenRL);
    }

}
