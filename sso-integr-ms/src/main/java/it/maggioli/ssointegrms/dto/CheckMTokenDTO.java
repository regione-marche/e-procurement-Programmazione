package it.maggioli.ssointegrms.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public class CheckMTokenDTO implements Serializable {
    private static final long serialVersionUID = 2682854746952407622L;
    @NotBlank(message = "Username obbligatorio")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
