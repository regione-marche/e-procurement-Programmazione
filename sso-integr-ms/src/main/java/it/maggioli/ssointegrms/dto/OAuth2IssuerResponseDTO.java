package it.maggioli.ssointegrms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since feb 16, 2024
 */
public class OAuth2IssuerResponseDTO implements Serializable {
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

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getTokenService() {
        return tokenService;
    }

    public void setTokenService(String tokenService) {
        this.tokenService = tokenService;
    }

    public String getAccountService() {
        return accountService;
    }

    public void setAccountService(String accountService) {
        this.accountService = accountService;
    }

    public Double getTokensNotBefore() {
        return tokensNotBefore;
    }

    public void setTokensNotBefore(Double tokensNotBefore) {
        this.tokensNotBefore = tokensNotBefore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2IssuerResponseDTO that = (OAuth2IssuerResponseDTO) o;
        return Objects.equals(realm, that.realm) && Objects.equals(publicKey, that.publicKey) && Objects.equals(tokenService, that.tokenService) && Objects.equals(accountService, that.accountService) && Objects.equals(tokensNotBefore, that.tokensNotBefore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realm, publicKey, tokenService, accountService, tokensNotBefore);
    }

    @Override
    public String toString() {
        return "OAuth2IssuerResponseDTO{" +
                "realm='" + realm + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", tokenService='" + tokenService + '\'' +
                ", accountService='" + accountService + '\'' +
                ", tokensNotBefore=" + tokensNotBefore +
                '}';
    }
}
