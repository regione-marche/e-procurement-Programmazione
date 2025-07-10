package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100.BaseResponsePcp;

public class ResponseAppaltoPcp extends BaseResponsePcp {

	private static final long serialVersionUID = -8616663721395876079L;
	
	private GaraEntry gara;
	private List<LottoFullEntry> lotti;
	private List<FaseAggiudicazioneEntry> fasiAggiudicazione;
	private List<ImpresaAggiudicatariaEntry> fasiImpreseAggiudicatarie;
	private List<FaseImpresaEntry> fasiImpresePartecipanti;
	private List<FaseInizialeEsecuzioneEntry> faseInizio;
	private List<FullFlussiFase> flussiFaseInizio;
	
	public GaraEntry getGara() {
		return gara;
	}
	public void setGara(GaraEntry gara) {
		this.gara = gara;
	}
	public List<LottoFullEntry> getLotti() {
		return lotti;
	}
	public void setLotti(List<LottoFullEntry> lotti) {
		this.lotti = lotti;
	}
	public List<FaseAggiudicazioneEntry> getFasiAggiudicazione() {
		return fasiAggiudicazione;
	}
	public void setFasiAggiudicazione(List<FaseAggiudicazioneEntry> fasiAggiudicazione) {
		this.fasiAggiudicazione = fasiAggiudicazione;
	}
	public List<ImpresaAggiudicatariaEntry> getFasiImpreseAggiudicatarie() {
		return fasiImpreseAggiudicatarie;
	}
	public void setFasiImpreseAggiudicatarie(List<ImpresaAggiudicatariaEntry> fasiImpreseAggiudicatarie) {
		this.fasiImpreseAggiudicatarie = fasiImpreseAggiudicatarie;
	}
	public List<FaseImpresaEntry> getFasiImpresePartecipanti() {
		return fasiImpresePartecipanti;
	}
	public void setFasiImpresePartecipanti(List<FaseImpresaEntry> fasiImpresePartecipanti) {
		this.fasiImpresePartecipanti = fasiImpresePartecipanti;
	}

	public List<FaseInizialeEsecuzioneEntry> getFaseInizio() {
		return faseInizio;
	}

	public void setFaseInizio(List<FaseInizialeEsecuzioneEntry> faseInizio) {
		this.faseInizio = faseInizio;
	}

	public List<FullFlussiFase> getFlussiFaseInizio() {
		return flussiFaseInizio;
	}

	public void setFlussiFaseInizio(List<FullFlussiFase> flussiFaseInizio) {
		this.flussiFaseInizio = flussiFaseInizio;
	}
}
