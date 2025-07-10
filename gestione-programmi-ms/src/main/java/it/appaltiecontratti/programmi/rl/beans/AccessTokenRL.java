package it.appaltiecontratti.programmi.rl.beans;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class AccessTokenRL {

    private String token;
    private Integer expiresIn;
    private LocalDateTime expiresAt=null;
    private LocalDateTime issuedAt;

    public boolean isExpired(){
        if(expiresIn==null) return true;
        return LocalDateTime.now()
                .isAfter(expiresAt);
    }


    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", expiresAt=" + expiresAt +
                ", issuedAt=" + issuedAt +
                '}';
    }
}
