package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
public class StoUteSysPK implements Serializable {
    @Serial
    private static final long serialVersionUID = -5466906155964079284L;

    @NotBlank
    @Size(min = 1, max = 61)
    @Column(name = "sysnom")
    private String sysnom;

    @NotBlank
    @Size(min = 1, max = 31)
    @Column(name = "syspwd")
    private String syspwd;

    public StoUteSysPK() {
    }

    public StoUteSysPK(@NotBlank @Size(min = 1, max = 61) String sysnom,
                       @NotBlank @Size(min = 1, max = 31) String syspwd) {
        this.sysnom = sysnom;
        this.syspwd = syspwd;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StoUteSysPK other = (StoUteSysPK) obj;
        return Objects.equals(sysnom, other.sysnom) && Objects.equals(syspwd, other.syspwd);
    }
}
