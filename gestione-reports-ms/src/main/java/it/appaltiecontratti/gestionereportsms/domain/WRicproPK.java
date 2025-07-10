package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WRicproPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1560106264893046605L;

    @NotNull
    @Column(name = "id_ricerca")
    private Long idRicerca;

    @Size(min = 0, max = 20)
    @NotNull
    @Column(name = "cod_profilo")
    private String codProfilo;
}
