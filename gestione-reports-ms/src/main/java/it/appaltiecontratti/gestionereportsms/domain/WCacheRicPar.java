package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Getter
@Setter
@ToString
@Entity
@Table(name = "w_cachericpar")
public class WCacheRicPar implements Serializable {
    @Serial
    private static final long serialVersionUID = -5199714145552625190L;

    @EmbeddedId
    private WCacheRicParPK id;

    @Size(min = 0, max = 512)
    @Column(name = "valore")
    private String valore;
}
