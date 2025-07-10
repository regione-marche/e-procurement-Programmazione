package it.maggioli.ssointegrms.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.maggioli.ssointegrms.dto.OAuth2IssuerResponseDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Classe per gestire la chiamata REST GET per recuperare la chiave pubblica dell'authorization server.
 *
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since feb 16, 2024
 */
@Component
public class OAuth2RestComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2RestComponent.class);

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:#{null}}")
    private String oAuth2IssuerUri;

    public PublicKey retrievePublicKey() {

        LOGGER.debug("Execution start {}::retrievePublicKey", getClass().getSimpleName());

        if (StringUtils.isBlank(oAuth2IssuerUri)) {
            String msg = "ISSUER URI null";
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }

        try {
            URI uri = new URI(oAuth2IssuerUri);

            HttpRequest.Builder builder = HttpRequest.newBuilder() //
                    .uri(uri) //
                    .header("Content-Type", "application/json")
                    .GET();

            HttpRequest httpRequest = builder.build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            OAuth2IssuerResponseDTO responseDTO = objectMapper.readValue(response.body(), OAuth2IssuerResponseDTO.class);

            byte[] decoded = Base64.decodeBase64(responseDTO.getPublicKey());

            String algorithm = "RSA";
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            return kf.generatePublic(new X509EncodedKeySpec(decoded));
        } catch (Exception e) {
            LOGGER.error("Errore", e);
        }

        return null;
    }
}
