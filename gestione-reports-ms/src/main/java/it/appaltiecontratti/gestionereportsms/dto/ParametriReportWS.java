package it.appaltiecontratti.gestionereportsms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ParametriReportWS implements Serializable {
    @Serial
    private static final long serialVersionUID = 3122375235962950459L;

    @NotNull
    private Map<String, Object> parametri;
}
