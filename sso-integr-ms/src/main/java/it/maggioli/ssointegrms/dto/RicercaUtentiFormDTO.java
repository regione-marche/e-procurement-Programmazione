package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RicercaUtentiFormDTO extends BaseSearchFormDTO implements Serializable {

	private static final long serialVersionUID = -1154707475913508906L;

	private String denominazione;
	private String username;
	private String usernameCF;
	private String abilitato;
	private String codiceFiscale;
	private String email;
	private String ufficioIntestatarioKey;
	private List<String> ufficioIntestatarioKeys;
	private String gestioneUtenti;
	private String amministratore;
	private String profiloKey;

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

	public String getUsernameCF() {
		return usernameCF;
	}

	public void setUsernameCF(String usernameCF) {
		this.usernameCF = usernameCF;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUfficioIntestatarioKey() {
		return ufficioIntestatarioKey;
	}

	public void setUfficioIntestatarioKey(String ufficioIntestatarioKey) {
		this.ufficioIntestatarioKey = ufficioIntestatarioKey;
	}

	public List<String> getUfficioIntestatarioKeys() {
		return ufficioIntestatarioKeys;
	}

	public void setUfficioIntestatarioKeys(List<String> ufficioIntestatarioKeys) {
		this.ufficioIntestatarioKeys = ufficioIntestatarioKeys;
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

	public String getProfiloKey() {
		return profiloKey;
	}

	public void setProfiloKey(String profiloKey) {
		this.profiloKey = profiloKey;
	}

	@Override
	public String toString() {
		return "RicercaUtentiFormDTO{" +
				"denominazione='" + denominazione + '\'' +
				", username='" + username + '\'' +
				", usernameCF='" + usernameCF + '\'' +
				", abilitato='" + abilitato + '\'' +
				", codiceFiscale='" + codiceFiscale + '\'' +
				", email='" + email + '\'' +
				", ufficioIntestatarioKey='" + ufficioIntestatarioKey + '\'' +
				", ufficioIntestatarioKeys=" + ufficioIntestatarioKeys +
				", gestioneUtenti='" + gestioneUtenti + '\'' +
				", amministratore='" + amministratore + '\'' +
				", profiloKey='" + profiloKey + '\'' +
				", skip=" + skip +
				", take=" + take +
				", sort='" + sort + '\'' +
				", sortDirection='" + sortDirection + '\'' +
				'}';
	}
}
