package it.appaltiecontratti.gestionereportsms.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Andrea Chinellato
 */
@Getter
@Setter
@ToString
public class WLogEventiDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 2044784437555608440L;

	private Long idEvento;
	private String codApp;
	private String codProfilo;
	private Long syscon;
	private String descrizioneUtente;
	private String ipEvento;
	private Date dataOra;
	private String oggettoEvento;
	private Integer livelloEvento;
	private String codiceEvento;
	private String descrizione;
	private String errorMessage;

}
