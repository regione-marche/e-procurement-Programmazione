package it.appaltiecontratti.tabellati.entity;

public class ImpresaBaseEntry {

	private String codiceImpresa;
	private String ragioneSociale;
	private String nomImp;
	private String codiceFiscale;
	private String partitaIva;
	private String comune;
	private boolean deletable;
	private String stazioneAppaltante;
	private String nominativoStazioneAppaltante;

	public String getCodiceImpresa() {
		return codiceImpresa;
	}

	public void setCodiceImpresa(String codiceImpresa) {
		this.codiceImpresa = codiceImpresa;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getNominativoStazioneAppaltante() {
		return nominativoStazioneAppaltante;
	}

	public void setNominativoStazioneAppaltante(String nominativoStazioneAppaltante) {
		this.nominativoStazioneAppaltante = nominativoStazioneAppaltante;
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

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

}
