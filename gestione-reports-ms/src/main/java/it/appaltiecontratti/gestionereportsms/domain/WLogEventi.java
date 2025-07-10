package it.appaltiecontratti.gestionereportsms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Andrea Chinellato
 */
@Entity
@Table(name = "w_logeventi")
@Getter
@Setter
@ToString
public class WLogEventi implements Serializable {

	@Serial
	private static final long serialVersionUID = -1037677342042277440L;

	@Id
	@NotNull
	@Column(name = "idevento")
	private Long idEvento;

	@Size(min = 0, max = 10)
	@Column(name = "codapp")
	private String codApp;

	@Size(min = 0, max = 20)
	@Column(name = "cod_profilo")
	private String codProfilo;

	@Size(min = 0, max = 40)
	@Column(name = "ipevento")
	private String ipEvento;

	@Column(name = "dataora")
	private Date dataOra;

	@Size(min = 0, max = 40)
	@Column(name = "oggevento")
	private String oggettoEvento;

	@Column(name = "livevento")
	private Integer livelloEvento;

	@Size(min = 0, max = 40)
	@Column(name = "codevento")
	private String codiceEvento;

	@Size(min = 0, max = 500)
	@Column(name = "descr")
	private String descrizione;

	@Column(name = "errmsg")
	private String errorMessage;

	@ManyToOne
	@JoinColumn(name = "syscon")
	private User user;

}
