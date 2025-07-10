package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Entity
@Table(name = "w_genchiavi")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WGenChiavi implements Serializable {
    @Serial
    private static final long serialVersionUID = 6239546059613767036L;

    @Id
    private String tabella;
    private Long chiave;
}
