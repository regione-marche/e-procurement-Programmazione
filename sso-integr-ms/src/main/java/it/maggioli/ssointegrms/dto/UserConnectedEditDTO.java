package it.maggioli.ssointegrms.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Cristiano Perin
 */
public class UserConnectedEditDTO implements Serializable {

    private static final long serialVersionUID = 7239821122129396301L;

    @Size(min = 0, max = 100)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
