package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class ProfiloDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2644728270605870292L;

    private String codice;
    private String codiceApplicazione;
    private String nome;
    private String descrizione;
}
