package it.appaltiecontratti.tabellati.entity;

public class ImpresaEntry extends ImpresaBaseEntry {

	private Long formaGiuridica;
	private String numeroIscrizione;
	private String codiceInail;
	private String numeroIscrizioneAlbo;
	private String indirizzo;
	private String numeroCivico;
	private String provincia;
	private String cap;
	private Long nazione;
	private String telefono;
	private String email;
	private String pec;
	private String fax;
	private String codComune;
	private LegaleRappresentanteEntry rappresentante;

	public Long getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setFormaGiuridica(Long formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public String getNumeroIscrizione() {
		return numeroIscrizione;
	}

	public void setNumeroIscrizione(String numeroIscrizione) {
		this.numeroIscrizione = numeroIscrizione;
	}

	public String getCodiceInail() {
		return codiceInail;
	}

	public void setCodiceInail(String codiceInail) {
		this.codiceInail = codiceInail;
	}

	public String getNumeroIscrizioneAlbo() {
		return numeroIscrizioneAlbo;
	}

	public void setNumeroIscrizioneAlbo(String numeroIscrizioneAlbo) {
		this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Long getNazione() {
		return nazione;
	}

	public void setNazione(Long nazione) {
		this.nazione = nazione;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public LegaleRappresentanteEntry getRappresentante() {
		return rappresentante;
	}

	public void setRappresentante(LegaleRappresentanteEntry rappresentante) {
		this.rappresentante = rappresentante;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

}
