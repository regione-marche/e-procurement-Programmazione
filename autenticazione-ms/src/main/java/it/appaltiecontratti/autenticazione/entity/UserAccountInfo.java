package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Contenitore per le informazioni dell'account.
 *
 * @author Cristiano Perin
 */
public class UserAccountInfo implements Serializable {

	private static final long serialVersionUID = 6835882344421006103L;
	@ApiModelProperty(name = "username", value = "Username dell'utente")
	private String username;
	@ApiModelProperty(name = "syscon", value = "Syscon dell'utente")
	private String syscon;
	@ApiModelProperty(name = "stazioniAppaltanti", value = "Lista delle stazioni appaltanti abilitate per l'utente")
	private List<SABaseEntry> stazioniAppaltanti;
	@ApiModelProperty(name = "chiaviAccesso", value = "Chiavi di accesso per l'autenticazione verso OR")
	private ChiaviAccesso chiaviAccesso;
	@ApiModelProperty(name = "ruolo dell'utente", value = "ruolo dell'utente")
	private String ruolo;
	private boolean richiestaAssistenzaAttiva;
	private String userEmail;
	@ApiModelProperty(name = "urlManuali", value = "Url dei manuali")
	private String urlManuali;
	private Long totalSACount;
	private boolean messaggisticaInternaAttiva;
	
	@ApiModelProperty(name = "abilitaIntegrazioneJSerfin", value = "Abilita l' integrazione con JSerfin")
	private boolean abilitaIntegrazioneJSerfin;
	
	@ApiModelProperty(name = "abilitaIntegrazioneAPK", value = "Abilita l' integrazione con APK")
	private boolean abilitaIntegrazioneAPK;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSyscon() {
		return syscon;
	}

	public void setSyscon(String syscon) {
		this.syscon = syscon;
	}

	public List<SABaseEntry> getStazioniAppaltanti() {
		return stazioniAppaltanti;
	}

	public void setStazioniAppaltanti(List<SABaseEntry> stazioniAppaltanti) {
		this.stazioniAppaltanti = stazioniAppaltanti;
	}

	public ChiaviAccesso getChiaviAccesso() {
		return chiaviAccesso;
	}

	public void setChiaviAccesso(ChiaviAccesso chiaviAccesso) {
		this.chiaviAccesso = chiaviAccesso;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public boolean isRichiestaAssistenzaAttiva() {
		return richiestaAssistenzaAttiva;
	}

	public void setRichiestaAssistenzaAttiva(boolean richiestaAssistenzaAttiva) {
		this.richiestaAssistenzaAttiva = richiestaAssistenzaAttiva;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUrlManuali() {
		return urlManuali;
	}

	public void setUrlManuali(String urlManuali) {
		this.urlManuali = urlManuali;
	}

	public Long getTotalSACount() {
		return totalSACount;
	}

	public void setTotalSACount(Long totalSACount) {
		this.totalSACount = totalSACount;
	}

	public boolean isMessaggisticaInternaAttiva() {
		return messaggisticaInternaAttiva;
	}

	public void setMessaggisticaInternaAttiva(boolean messaggisticaInternaAttiva) {
		this.messaggisticaInternaAttiva = messaggisticaInternaAttiva;
	}

	public boolean isAbilitaIntegrazioneJSerfin() {
		return abilitaIntegrazioneJSerfin;
	}

	public void setAbilitaIntegrazioneJSerfin(boolean abilitaIntegrazioneJSerfin) {
		this.abilitaIntegrazioneJSerfin = abilitaIntegrazioneJSerfin;
	}

	public boolean isAbilitaIntegrazioneAPK() {
		return abilitaIntegrazioneAPK;
	}

	public void setAbilitaIntegrazioneAPK(boolean abilitaIntegrazioneAPK) {
		this.abilitaIntegrazioneAPK = abilitaIntegrazioneAPK;
	}

}
