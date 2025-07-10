package it.appaltiecontratti.gestionereportsms.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Cristiano Perin
 */
@Entity
@Table(name = "w_profili")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Profilo implements Serializable {

    @Serial
    private static final long serialVersionUID = -284969260980549222L;

    @Id
    @NotBlank
    @Size(min = 1, max = 20)
    @Column(name = "cod_profilo")
    private String codice;

    @Size(min = 0, max = 10)
    @Column(name = "codapp")
    private String codiceApplicazione;

    @Size(min = 0, max = 100)
    @Column(name = "nome")
    private String nome;

    @Size(min = 0, max = 500)
    @Column(name = "descrizione")
    private String descrizione;

}
