package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author andrea.chinellato
 * */

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class ResultReportQueryParamsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3038153345504419104L;

    //private ResultQueryExecutionFormDTO formParams;
    private List<String> columnNames;
    private List<Map<String, Object>> values;
    private Long idRicerca;
    private Long syscon;
}
