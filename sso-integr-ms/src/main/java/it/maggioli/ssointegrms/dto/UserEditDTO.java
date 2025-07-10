package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserEditDTO implements Serializable {

	private static final long serialVersionUID = -3130607645978085571L;

	@NotBlank
	@Size(min = 1, max = 161)
	private String denominazione;
	@Size(min = 1, max = 60)
	private String username;
	@Size(min = 0, max = 100)
	private String email;
	@Size(min = 0, max = 16)
	private String codiceFiscale;
	private String gestioneUtenti;
	private String amministratore;
	private String scadenzaAccount;
	private Date dataScadenzaAccount;
	private String controlliGdpr;
	private String abilitaModificaUfficiIntestatari;
	@Size(min = 0, max = 5)
	private String sysab3;
	@Size(min = 0, max = 5)
	private String sysabg;
	private String abilitaTuttiUfficiIntestatari;
	private String gestioneModelli;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getGestioneUtenti() {
		return gestioneUtenti;
	}

	public void setGestioneUtenti(String gestioneUtenti) {
		this.gestioneUtenti = gestioneUtenti;
	}

	public String getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(String amministratore) {
		this.amministratore = amministratore;
	}

	public String getScadenzaAccount() {
		return scadenzaAccount;
	}

	public void setScadenzaAccount(String scadenzaAccount) {
		this.scadenzaAccount = scadenzaAccount;
	}

	public Date getDataScadenzaAccount() {
		return dataScadenzaAccount;
	}

	public void setDataScadenzaAccount(Date dataScadenzaAccount) {
		this.dataScadenzaAccount = dataScadenzaAccount;
	}

	public String getControlliGdpr() {
		return controlliGdpr;
	}

	public void setControlliGdpr(String controlliGdpr) {
		this.controlliGdpr = controlliGdpr;
	}

	public String getAbilitaModificaUfficiIntestatari() {
		return abilitaModificaUfficiIntestatari;
	}

	public void setAbilitaModificaUfficiIntestatari(String abilitaModificaUfficiIntestatari) {
		this.abilitaModificaUfficiIntestatari = abilitaModificaUfficiIntestatari;
	}

	public String getSysab3() {
		return sysab3;
	}

	public void setSysab3(String sysab3) {
		this.sysab3 = sysab3;
	}

	public String getSysabg() {
		return sysabg;
	}

	public void setSysabg(String sysabg) {
		this.sysabg = sysabg;
	}

	public String getAbilitaTuttiUfficiIntestatari() {
		return abilitaTuttiUfficiIntestatari;
	}

	public void setAbilitaTuttiUfficiIntestatari(String abilitaTuttiUfficiIntestatari) {
		this.abilitaTuttiUfficiIntestatari = abilitaTuttiUfficiIntestatari;
	}

	public String getGestioneModelli() {
		return gestioneModelli;
	}

	public void setGestioneModelli(String gestioneModelli) {
		this.gestioneModelli = gestioneModelli;
	}
}
