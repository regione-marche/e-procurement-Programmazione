package it.appaltiecontratti.gestionereportsms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@Data
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3496336115888190078L;

    private Long syscon;
    private String username;
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String email;
    private boolean disabilitato;
    private boolean ldap;
    private Integer ufficioAppartenenza;
    private Integer categoria;
    private List<String> opzioniUtente;
    private Date dataScadenzaAccount;
    private Date dataUltimoAccesso;
    private String sysab3;
    private String sysabg;
    private boolean deletable;
    private List<String> ufficiIntestatari;

}
