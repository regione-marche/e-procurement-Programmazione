package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity per gestione della tabella w_preferiti
 *
 * @author andrea.chinellato
 *
 * */

@Getter
@Setter
@Entity
@Table(name = "w_preferiti")
@EqualsAndHashCode
public class WPreferiti implements Serializable {

    @Serial
    private static final long serialVersionUID = 3334081834917598983L;

    @Id
    @NotNull
    @Column(name = "id")
    private Long idPreferito;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "ent")
    private String tabella;

    @Column(name = "syscon")
    private Long syscon;

    @Size(min = 0, max = 30)
    @Column(name = "key1")
    private String key1;

    @Size(min = 0, max = 30)
    @Column(name = "key2")
    private String key2;

    @Size(min = 0, max = 30)
    @Column(name = "key3")
    private String key3;

    @Size(min = 0, max = 30)
    @Column(name = "key4")
    private String key4;

    @Size(min = 0, max = 30)
    @Column(name = "key5")
    private String key5;
}
