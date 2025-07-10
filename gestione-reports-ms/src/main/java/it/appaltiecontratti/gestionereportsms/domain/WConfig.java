package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Cristiano Perin
 */
@Entity
@Table(name = "w_config")
@Data
@EqualsAndHashCode
@ToString
public class WConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 4632929928777492777L;

    @EmbeddedId
    private WConfigPK id;

    @Size(min = 0, max = 500)
    @Column(name = "valore")
    private String valore;

    @Size(min = 0, max = 500)
    @Column(name = "sezione")
    private String sezione;

    @Size(min = 0, max = 2000)
    @Column(name = "descrizione")
    private String descrizione;

    @Size(min = 0, max = 1)
    @Column(name = "criptato")
    private String criptato;

}
