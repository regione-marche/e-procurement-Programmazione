package it.appaltiecontratti.gestionereportsms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Getter
@Setter
@Entity
@Table(name = "w_ricuffint")
@EqualsAndHashCode
public class WRicuffint implements Serializable {

    @Serial
    private static final long serialVersionUID = -5773174472518978465L;

    @EmbeddedId
    private WRicuffintPK id;

    @ManyToOne
    @MapsId("idRicerca")
    @JsonBackReference(value = "reportsUffInt")
    @JoinColumn(name = "id_ricerca", referencedColumnName = "id_ricerca", insertable = false, updatable = false)
    private WRicerche reportsUffInt;
}
