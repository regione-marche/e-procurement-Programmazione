package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class ImpresaBaseEntry {

	private String codiceImpresa;
	private String ragioneSociale;
	private String nomImp;
	private String codiceFiscale;
	private String partitaIva;
	private String comune;
	private boolean deletable;

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

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

}
