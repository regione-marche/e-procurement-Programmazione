package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class RichiestaAssistenzaFormDTO extends BrowserInfoDTO implements Serializable {

	private static final long serialVersionUID = 3447064472964137990L;

	@NotBlank
	@Size(min = 1, max = 254)
	private String nominativoEnteAmministrazione;
	@NotBlank
	@Size(min = 1, max = 100)
	private String referenteDaContattare;
	@NotBlank
	@Size(min = 1, max = 100)
	private String email;
	private String telefono;
	@NotBlank
	@Size(min = 1, max = 100)
	private String tipologiaRichiesta;
	private String descrizione;
	private String allegato;
	private String allegatoName;
	private String captchaSolution;

	public String getNominativoEnteAmministrazione() {
		return nominativoEnteAmministrazione;
	}

	public void setNominativoEnteAmministrazione(String nominativoEnteAmministrazione) {
		this.nominativoEnteAmministrazione = nominativoEnteAmministrazione;
	}

	public String getReferenteDaContattare() {
		return referenteDaContattare;
	}

	public void setReferenteDaContattare(String referenteDaContattare) {
		this.referenteDaContattare = referenteDaContattare;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipologiaRichiesta() {
		return tipologiaRichiesta;
	}

	public void setTipologiaRichiesta(String tipologiaRichiesta) {
		this.tipologiaRichiesta = tipologiaRichiesta;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getAllegato() {
		return allegato;
	}

	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}

	public String getAllegatoName() {
		return allegatoName;
	}

	public void setAllegatoName(String allegatoName) {
		this.allegatoName = allegatoName;
	}

	public String getCaptchaSolution() {
		return captchaSolution;
	}

	public void setCaptchaSolution(String captchaSolution) {
		this.captchaSolution = captchaSolution;
	}

	@Override
	public String toString() {
		return "RichiestaAssistenzaFormDTO [nominativoEnteAmministrazione=" + nominativoEnteAmministrazione
				+ ", referenteDaContattare=" + referenteDaContattare + ", email=" + email + ", telefono=" + telefono
				+ ", tipologiaRichiesta=" + tipologiaRichiesta + ", descrizione=" + descrizione + ", allegato="
				+ allegato + ", allegatoName=" + allegatoName + ", BrowserInfoDTO::toString()=" + super.toString()
				+ "]";
	}

}
