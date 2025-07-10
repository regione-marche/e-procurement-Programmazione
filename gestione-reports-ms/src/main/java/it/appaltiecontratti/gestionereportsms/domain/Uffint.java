package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "uffint")
@Data
@EqualsAndHashCode
@ToString
public class Uffint implements Serializable {

    @Serial
    private static final long serialVersionUID = 7266453888424580288L;

    @Id
    @NotBlank
    @Size(min = 1, max = 16)
    @Column(name = "codein")
    private String codice;

    @Size(min = 0, max = 254)
    @Column(name = "nomein")
    private String denominazione;

    @Size(min = 0, max = 16)
    @Column(name = "cfein")
    private String codiceFiscale;

}
