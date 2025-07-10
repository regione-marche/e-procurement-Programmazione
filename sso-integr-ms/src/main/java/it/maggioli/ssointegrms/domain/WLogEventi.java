package it.maggioli.ssointegrms.domain;

import org.hibernate.annotations.NotFound;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static org.hibernate.annotations.NotFoundAction.IGNORE;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_logeventi")
public class WLogEventi implements Serializable {

	private static final long serialVersionUID = -5417774848905616639L;

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
	@NotFound(action = IGNORE)
	private User user;

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getCodProfilo() {
		return codProfilo;
	}

	public void setCodProfilo(String codProfilo) {
		this.codProfilo = codProfilo;
	}

	public String getIpEvento() {
		return ipEvento;
	}

	public void setIpEvento(String ipEvento) {
		this.ipEvento = ipEvento;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getOggettoEvento() {
		return oggettoEvento;
	}

	public void setOggettoEvento(String oggettoEvento) {
		this.oggettoEvento = oggettoEvento;
	}

	public Integer getLivelloEvento() {
		return livelloEvento;
	}

	public void setLivelloEvento(Integer livelloEvento) {
		this.livelloEvento = livelloEvento;
	}

	public String getCodiceEvento() {
		return codiceEvento;
	}

	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "WLogEventi [idEvento=" + idEvento + ", codApp=" + codApp + ", codProfilo=" + codProfilo + ", ipEvento="
				+ ipEvento + ", dataOra=" + dataOra + ", oggettoEvento=" + oggettoEvento + ", livelloEvento="
				+ livelloEvento + ", codiceEvento=" + codiceEvento + ", descrizione=" + descrizione + ", errorMessage="
				+ errorMessage + "]";
	}

}
