package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;

public class InizFaseCantieriEntry {

	private Long idAppalto;
	private Date dataInizioLavori;
	private List<LottoDynamicValue> destinatariNotifica;

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Date getDataInizioLavori() {
		return dataInizioLavori;
	}

	public void setDataInizioLavori(Date dataInizioLavori) {
		this.dataInizioLavori = dataInizioLavori;
	}

	public List<LottoDynamicValue> getDestinatariNotifica() {
		return destinatariNotifica;
	}

	public void setDestinatariNotifica(List<LottoDynamicValue> destinatariNotifica) {
		this.destinatariNotifica = destinatariNotifica;
	}

}
