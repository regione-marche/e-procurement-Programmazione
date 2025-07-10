package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Andrea Chinellato
 */

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class WRicParamPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 3252277146004952923L;

    @NotBlank
    @Size(min = 1, max = 10)
    @Column(name = "id_ricerca")
    private Long idRicerca;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "progr")
    private Long progressivo;
}
