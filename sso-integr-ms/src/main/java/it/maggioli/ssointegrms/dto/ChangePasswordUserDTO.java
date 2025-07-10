package it.maggioli.ssointegrms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since dic 28, 2022
 */
public class ChangePasswordUserDTO implements Serializable {

    private static final long serialVersionUID = 7953462239003008008L;

    @Size(min = 1, max = 30)
    @NotBlank(message = "Vecchia Password obbligatoria")
    private String oldPassword;

    @Size(min = 1, max = 30)
    @NotBlank(message = "Nuova Password obbligatoria")
    private String newPassword;

    @Size(min = 1, max = 30)
    @NotBlank(message = "Conferma Nuova Password obbligatoria")
    private String confirmNewPassword;

    @JsonIgnore
    private String ipAddress;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
