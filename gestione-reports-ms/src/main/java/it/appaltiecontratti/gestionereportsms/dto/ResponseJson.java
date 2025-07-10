package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author andrea.chinellato
 * */

@Getter
@Setter
@EqualsAndHashCode
public class ResponseJson implements Serializable {
    @Serial
    private static final long serialVersionUID = 1377363701624276077L;

    public ResponseJson(String token){
        this.token = token;
    }

    private String token;
}
