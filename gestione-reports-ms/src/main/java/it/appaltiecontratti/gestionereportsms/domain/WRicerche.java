package it.appaltiecontratti.gestionereportsms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Entity per gestione della tabella w_ricerche
 *
 * @author andrea.chinellato
 *
 * */

@Getter
@Setter
@Entity
@Table(name = "w_ricerche")
@EqualsAndHashCode
public class WRicerche implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1734680818850259247L;

    @Id
    @NotNull
    @Column(name = "id_ricerca")
    private Long idRicerca;

    @Size(min = 0, max = 10)
    @Column(name = "codapp")
    private String codApp;

    @Size(min = 0, max = 2)
    @Column(name = "tipo")
    private String tipo;

    @Size(min = 0, max = 50)
    @Column(name = "nome")
    private String nome;

    @Size(min = 0, max = 200)
    @Column(name = "descr")
    private String descrizione;

    @Column(name = "disp")
    private Long pubblicato;

    @Column(name = "valdistinti")
    private Long valoriDistinti;

    @Column(name = "risperpag")
    private Long risperpag;

    @Column(name = "vismodelli")
    private Long vismodelli;

    @Size(min = 0, max = 35)
    @Column(name = "entprinc")
    private String entprinc;

    @Column(name = "famiglia")
    private Long famiglia;

    @Column(name = "id_prospetto")
    private Long idProspetto;

    @Column(name = "personale")
    private Long personale;

    @Column(name = "filtroutente")
    private Long filtroUtente;

    @Size(min = 0, max = 20)
    @Column(name = "profilo_owner")
    private String profiloOwner;

    @Column(name = "visparam")
    private Long visparam;

    @Column(name = "linkscheda")
    private Long linkScheda;

    @Column(name = "filtrouffint")
    private Long filtroUffInt;

    @Column(name = "defsql")
    private String defSql;

    @Column(name = "owner")
    private Long userOwner;

    @Column(name = "tutti_profili")
    private Long tuttiProfili;

    @Column(name = "tutti_uffici")
    private Long tuttiUffici;

    @Size(min = 0, max = 30)
    @Column(name = "codreportws")
    private String codReportWs;

    @Size(min = 0, max = 300)
    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "formato_sched")
    private Long formatoRisultatoSchedulazione;

    @Size(min = 0, max = 2000)
    @Column(name = "email_sched")
    private String emailRisultatoSchedulazione;

    @Column(name = "no_output_vuoto")
    private Long noOutputVuoto;

    /**
     * Chiavi esterne
     * */
    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "syscon", insertable = false, updatable = false)
    @JsonBackReference(value = "reports")
    private User user;

    @OneToMany(mappedBy = "reportsProfiles")
    @JsonManagedReference(value = "reportsProfiles")
    private List<WRicpro> profili;

    @OneToMany(mappedBy = "reportsUffInt")
    @JsonManagedReference(value = "reportsUffInt")
    private List<WRicuffint> uffInt;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
