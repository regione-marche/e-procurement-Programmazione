package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ResultQueryExecutionFormDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4734274797662033219L;

    private List<String> columnNames;

    private List<Map<String, Object>> values;

    private String queryReplacedWithParams;

    private Map<String, Object> paramsToShowUser;
}
