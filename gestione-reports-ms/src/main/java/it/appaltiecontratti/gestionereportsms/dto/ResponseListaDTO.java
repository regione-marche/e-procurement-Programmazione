package it.appaltiecontratti.gestionereportsms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseListaDTO extends ResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7760167089562192583L;

    private Long totalCount;
    private String detailedErrorQuery;
    private List<String> mandatoryParamsNotUsed;
    private Long maxNumRecordConfig;
}
