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

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UfficioIntestatarioDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8940448875598976231L;

    private String codice;
    private String denominazione;
    private String codiceFiscale;
}
