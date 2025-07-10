package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class WCacheRicParPK implements Serializable {
    @Serial
    private static final long serialVersionUID = -7806287954412773261L;

    @NotNull
    @Column(name = "id_account")
    private Long idAccount;

    @NotNull
    @Column(name = "id_ricerca")
    private Long idRicerca;

    @Size(min = 0, max = 30)
    @NotNull
    @Column(name = "codice")
    private String codice;
}
