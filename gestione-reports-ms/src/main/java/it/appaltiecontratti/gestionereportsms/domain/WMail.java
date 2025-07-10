package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Entity
@Table(name = "w_mail")
@Data
@EqualsAndHashCode
@ToString
public class WMail implements Serializable {
    @Serial
    private static final long serialVersionUID = 2890892870909094335L;

    @EmbeddedId
    private WMailPK id;

    @Size(min = 0, max = 100)
    @Column(name = "server")
    private String server;

    @Size(min = 0, max = 100)
    @Column(name = "porta")
    private String porta;

    @Size(min = 0, max = 100)
    @Column(name = "protocollo")
    private String protocollo;

    @Size(min = 0, max = 100)
    @Column(name = "timeout")
    private String timeout;

    @Size(min = 0, max = 1)
    @Column(name = "tracciatura_messaggi")
    private String tracciaturaMessaggi;

    @Size(min = 0, max = 100)
    @Column(name = "mail_mittente")
    private String mailMittente;

    @Size(min = 0, max = 100)
    @Column(name = "password")
    private String password;

    @Size(min = 0, max = 100)
    @Column(name = "id_user")
    private String idUtente;

    @Size(min = 0, max = 100)
    @Column(name = "dim_tot_allegati")
    private String dimensioneTotaleAllegati;

    @Size(min = 0, max = 6)
    @Column(name = "delay")
    private String delay;

    @Size(min = 0, max = 100)
    @Column(name = "server_imap")
    private String serverImap;

    @Size(min = 0, max = 100)
    @Column(name = "porta_imap")
    private String portaImap;

}
