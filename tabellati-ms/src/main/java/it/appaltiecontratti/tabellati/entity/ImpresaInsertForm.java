package it.appaltiecontratti.tabellati.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class ImpresaInsertForm {

	@XSSValidation
	private String codiceImpresa;
	@XSSValidation
	private String ragioneSociale;
	@XSSValidation
	private String nomImp;
	@XSSValidation
	private String codiceFiscale;
	@XSSValidation
	private String partitaIva;
	@XSSValidation
	private String comune;
	private Long formaGiuridica;
	@XSSValidation
	private String numeroIscrizione;
	@XSSValidation
	private String codiceInail;
	@XSSValidation
	private String numeroIscrizioneAlbo;
	@XSSValidation
	private String indirizzo;
	@XSSValidation
	private String numeroCivico;
	@XSSValidation
	private String provincia;
	@XSSValidation
	private String cap;
	private Long nazione;
	@XSSValidation
	private String telefono;
	@XSSValidation
	private String email;
	@XSSValidation
	private String pec;
	@XSSValidation
	private String fax;
	private LegaleRappresentanteEntry rappresentante;
	@XSSValidation
	private String stazioneAppaltante;

	public String getCodiceImpresa() {
		return codiceImpresa;
	}

	public void setCodiceImpresa(String codiceImpresa) {
		this.codiceImpresa = codiceImpresa;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getNomImp() {
		return nomImp;
	}

	public void setNomImp(String nomImp) {
		this.nomImp = nomImp;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

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

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

}
