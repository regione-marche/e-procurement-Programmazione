package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DocumentoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeEsecuzioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.MisureAggiuntiveSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SchedaSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseInizPubbEsitoForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseIncarichiProfessionaliFactory extends AbstractFactory {

	private static final Long FASE_INIZIALE = new Long(
			Integer.valueOf(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		List<IncarichiProfEntry> incarichiProfessionali = contrattiMapper.getIncarichiProfessionali(codGara, codLotto, num);
		for (IncarichiProfEntry incarico : incarichiProfessionali) {
			incarico.setTecnico(contrattiMapper.getTecnico(incarico.getCodiceTecnico()));
		}
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		Date dataVerbAggiudicazione = null;
		if(chiaveGara != null) {

//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_INIZIALE, fase.getNum())) {
//				if (tipoInvio == 1L) {
//					ValidateEntry entry = new ValidateEntry("Tipo invio", "La fase è già stata inviata", "W");
//					risultato.add(entry);
//				}
//			} else {
//				if (tipoInvio == 2L) {
//					ValidateEntry entry = new ValidateEntry("Tipo invio", "La fase non è mai stata inviata in precedenza",
//							"W");
//					risultato.add(entry);
//				}
//			}
			
			
		}
		
		List<ValidateEntry> validazioneIncarichi = super.validateIncarichiProfessionali(
				incarichiProfessionali, true);
		if (validazioneIncarichi != null) {
			risultato.addAll(validazioneIncarichi);
		}


		return risultato;

	}

}
