package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author andrea.chinellato
 * */

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UfficioIntestatarioEditDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2410617803600566471L;

    private List<String> listaUfficiIntestatari;
}
