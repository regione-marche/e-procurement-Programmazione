package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Andrea Chinellato
 */

@Data
@Entity
@Table(name = "w_ricparam")
@EqualsAndHashCode
public class WRicParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 6294254701884048513L;

    @EmbeddedId
    private WRicParamPK id;

    @Size(min = 0, max = 30)
    @Column(name = "codice")
    private String codice;

    @Size(min = 0, max = 100)
    @Column(name = "nome")
    private String nome;

    @Size(min = 0, max = 2000)
    @Column(name = "descr")
    private String descrizione;

    @Size(min = 0, max = 2)
    @Column(name = "tipo")
    private String tipo;

    @Size(min = 0, max = 5)
    @Column(name = "tabcod")
    private String codiceTabellato;

    @Column(name = "obblig")
    private Long obbligatorio;

    @Column(name = "menu")
    private String menu;
}
