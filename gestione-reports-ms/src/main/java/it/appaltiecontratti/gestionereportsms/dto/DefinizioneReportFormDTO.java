package it.appaltiecontratti.gestionereportsms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Data
@EqualsAndHashCode
@ToString
public class DefinizioneReportFormDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7006271566691000153L;

    private Long idRicerca;
    private String defSql;
}
