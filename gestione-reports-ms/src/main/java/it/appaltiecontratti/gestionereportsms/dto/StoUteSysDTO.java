package it.appaltiecontratti.gestionereportsms.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StoUteSysDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9097754744987302721L;

    private String sysnom;
    private String syspwd;
    private Long syscon;
    private Date sysdat;
    private String syslogin;
}
