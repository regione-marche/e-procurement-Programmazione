package it.appaltiecontratti.programmi.entity;

public class InterventiSearchForm extends ModulesSearchForm{

	private String numeroCui;
	
	private String codInterno;
	
	private String descrizione;
	
	private String annualita;
	
	private String codiceCUP;
	
	private String rup;
	
	private Double importoTotaleDa;

	private Double importoTotaleA;

	public String getNumeroCui() {
		return numeroCui;
	}

	public void setNumeroCui(String numeroCui) {
		this.numeroCui = numeroCui;
	}

	public String getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(String codInterno) {
		this.codInterno = codInterno;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getCodiceCUP() {
		return codiceCUP;
	}

	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public Double getImportoTotaleDa() { return importoTotaleDa; }

	public void setImportoTotaleDa(Double importoTotaleDa) { this.importoTotaleDa = importoTotaleDa; }

    public Double getImportoTotaleA() { return importoTotaleA; }

	public void setImportoTotaleA(Double importoTotaleA) { this.importoTotaleA = importoTotaleA; }
}
