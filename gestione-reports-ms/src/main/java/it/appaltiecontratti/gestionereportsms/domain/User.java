package it.appaltiecontratti.gestionereportsms.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Cristiano Perin
 */
@Entity
@Table(name = "usrsys")
@Getter
@Setter
@EqualsAndHashCode
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -5945138874235891846L;

    @Id
    @NotNull
    @Column(name = "syscon")
    private Long syscon;

    @Size(min = 0, max = 161)
    @Column(name = "sysute")
    private String sysute;

    @Size(min = 0, max = 61)
    @Column(name = "sysnom")
    private String nomeUtente;

    @Size(min = 0, max = 31)
    @Column(name = "syspwd")
    private String password;

    @Size(min = 0, max = 500)
    @Column(name = "syspwbou")
    private String syspwbou;

    @Size(min = 0, max = 16)
    @Column(name = "syscf")
    private String codiceFiscale;

    @Size(min = 0, max = 60)
    @Column(name = "syslogin")
    private String login;

    @Size(min = 0, max = 1)
    @Column(name = "sysdisab")
    private String disabilitato;

    @Column(name = "flag_ldap")
    private Integer ldap;

    @Size(min = 0, max = 100)
    @Column(name = "email")
    private String email;

    @Column(name = "sysuffapp")
    private Integer ufficioAppartenenza;

    @Column(name = "syscateg")
    private Integer categoria;

    @Column(name = "sysscad")
    private Date dataScadenzaAccount;

    @Column(name = "sysultacc")
    private Date dataUltimoAccesso;

    @Size(min = 0, max = 6)
    @Column(name = "sysab3")
    private String sysab3;

    @Size(min = 0, max = 5)
    @Column(name = "sysabg")
    private String sysabg;

    @ManyToMany
    @JoinTable( //
            name = "usr_ein", //
            joinColumns = @JoinColumn(name = "syscon"), //
            inverseJoinColumns = @JoinColumn(name = "codein"))
    private Set<Uffint> uffints = new HashSet<Uffint>();

    @ManyToMany
    @JoinTable( //
            name = "w_accpro", //
            joinColumns = @JoinColumn(name = "id_account", referencedColumnName = "syscon"), //
            inverseJoinColumns = @JoinColumn(name = "cod_profilo"))
    private Set<Profilo> profili = new HashSet<Profilo>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "reports")
    private List<WRicerche> reports;

}
