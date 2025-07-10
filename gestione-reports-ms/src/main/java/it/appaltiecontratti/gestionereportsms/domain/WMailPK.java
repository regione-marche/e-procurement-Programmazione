package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class WMailPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 4618658296570643034L;

    @NotBlank
    @Size(min = 1, max = 10)
    @Column(name = "codapp")
    private String codApp;

    @NotBlank
    @Size(min = 1, max = 16)
    @Column(name = "idcfg")
    private String configurazione;
}
