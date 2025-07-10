package it.appaltiecontratti.gestionereportsms.dto;

import it.appaltiecontratti.gestionereportsms.domain.WRicerche;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * DTO per il dato {@link it.appaltiecontratti.gestionereportsms.domain.WRicParam WRicParam}
 *
 * @author andrea.chinellato
 *
 * */

@Data
@EqualsAndHashCode
@ToString
public class WRicParamDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7751002462326162117L;

    private Long idRicerca;
    private Long progressivo;
    private String codice;
    private String nome;
    private String descrizione;
    private String tipo;
    private String codiceTabellato;
    private String obbligatorio;
    private String menuField;
    private String value;
}
