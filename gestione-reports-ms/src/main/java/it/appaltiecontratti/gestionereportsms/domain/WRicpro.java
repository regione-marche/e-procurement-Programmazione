package it.appaltiecontratti.gestionereportsms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Setter
@Getter
@ToString
@Entity
@Table(name = "w_ricpro")
@EqualsAndHashCode
public class WRicpro implements Serializable {

    @Serial
    private static final long serialVersionUID = -5857154673013397015L;

    @EmbeddedId
    private WRicproPK id;

    @ManyToOne
    @MapsId("idRicerca")
    @JsonBackReference(value = "reportsProfiles")
    @JoinColumn(name = "id_ricerca", referencedColumnName = "id_ricerca", insertable = false, updatable = false)
    private WRicerche reportsProfiles;

}
