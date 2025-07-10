package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Cristiano Perin
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class WConfigPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 5379141628474508541L;

    @NotBlank
    @Size(min = 1, max = 10)
    @Column(name = "codapp")
    private String codApp;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "chiave")
    private String chiave;

}
