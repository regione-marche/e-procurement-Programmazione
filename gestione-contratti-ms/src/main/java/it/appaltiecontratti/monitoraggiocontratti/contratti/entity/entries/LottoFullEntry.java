package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

public class LottoFullEntry extends LottoEntry{

	private String codiceInterno;
	
	private String contrattoEscluso161718;
	
	private String sommaUrgenza;
	
	private String urlCommittente;

	public String getCodiceInterno() {
		return codiceInterno;
	}

	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}

	public String getContrattoEscluso161718() {
		return contrattoEscluso161718;
	}

	public void setContrattoEscluso161718(String contrattoEscluso161718) {
		this.contrattoEscluso161718 = contrattoEscluso161718;
	}

	public String getSommaUrgenza() {
		return sommaUrgenza;
	}

	public void setSommaUrgenza(String sommaUrgenza) {
		this.sommaUrgenza = sommaUrgenza;
	}

	public String getUrlCommittente() {
		return urlCommittente;
	}

	public void setUrlCommittente(String urlCommittente) {
		this.urlCommittente = urlCommittente;
	}
	
	
	
}
