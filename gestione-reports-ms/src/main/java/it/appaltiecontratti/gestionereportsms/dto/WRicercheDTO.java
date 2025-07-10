package it.appaltiecontratti.gestionereportsms.dto;

import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.domain.WRicerche;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * DTO per il dato {@link WRicerche WRicerche}
 *
 * @author andrea.chinellato
 *
 * */

@Data
@EqualsAndHashCode
public class WRicercheDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3776739605061228966L;

    private Long idRicerca;
    private String codApp;
    private String tipo;
    private String nome;
    private String descrizione;
    private String pubblicato;
    private Long valoriDistinti;
    private Long risperpag;
    private Long vismodelli;
    private String entprinc;
    private Long userOwner;
    private Long famiglia;
    private Long idProspetto;
    private Long personale;
    private Long filtroUtente;
    private String profiloOwner;
    private Long visparam;
    private Long linkScheda;
    private Long filtroUffInt;
    private String defSql;
    private User user;
    private String sysute;
    private String tuttiProfili;
    private String tuttiUffici;
    private Long syscon;
    private String esponiComeServizio;
    private String codReportWs;
    private String schedula;
    private String cronExpression;
    private String formatoSchedulazione;
    private String emailSchedulazioneRisultato;
    private Long noOutputVuoto;
    private Boolean contieneParametri;
    private Boolean isPreferito;
}
