package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Andrea Chinellato
 * */

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class ProfiliUtenteReportEditDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 414115031317067170L;

    private List<String> listaProfili;
}
