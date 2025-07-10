package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GenericDynamicFormValueDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4658078284982243044L;

    private Object dynamicFormValueDTO;

    private List<WRicParamDTO> allParams;
}
