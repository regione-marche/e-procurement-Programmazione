package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author andrea.chinellato
 * */

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "stoutesys")
public class StoUteSys implements Serializable {

    @Serial
    private static final long serialVersionUID = 7034384977819507362L;

    @EmbeddedId
    private StoUteSysPK id;

    @Column(name = "syscon")
    private Long syscon;

    @Column(name = "sysdat")
    private Date sysdat;

    @Size(min = 0, max = 60)
    @Column(name = "syslogin")
    private String syslogin;

}
