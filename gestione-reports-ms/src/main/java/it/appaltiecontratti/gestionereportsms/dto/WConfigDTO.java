package it.appaltiecontratti.gestionereportsms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Cristiano Perin
 */
@Data
@EqualsAndHashCode
@ToString
public class WConfigDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2020710331026341945L;

    private String codApp;
    private String chiave;
    private String valore;
    private String sezione;
    private String descrizione;
    private String criptato;

}
