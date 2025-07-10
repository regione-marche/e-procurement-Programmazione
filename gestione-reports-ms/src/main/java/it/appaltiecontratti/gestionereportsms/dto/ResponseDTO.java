package it.appaltiecontratti.gestionereportsms.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2190287268345390374L;

    private String done;
    private Object response;
    private List<String> messages = new ArrayList<>();
    private String detailedErrorQuery;
}
