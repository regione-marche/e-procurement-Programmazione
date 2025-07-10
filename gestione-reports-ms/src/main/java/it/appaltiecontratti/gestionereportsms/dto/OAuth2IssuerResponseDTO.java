package it.appaltiecontratti.gestionereportsms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since feb 16, 2024
 */
@Data
@EqualsAndHashCode
@ToString
public class OAuth2IssuerResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7652552022243055286L;
    private String realm;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("token-service")
    private String tokenService;
    @JsonProperty("account-service")
    private String accountService;
    @JsonProperty("tokens-not-before")
    private Double tokensNotBefore;
}
