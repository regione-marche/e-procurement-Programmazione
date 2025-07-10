package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseConclusioneAffidamentoDirettoFactory extends AbstractFactory {

	private static final Long FASE_CONCLUSIONE = new Long(
			Integer.valueOf(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
				
		Date dataInizio = contrattiMapper.getFaseAffidamentiDirettiIniz(codGara, codLotto, num);
		Double importoCertificato = contrattiMapper.getFaseAffidamentiDirettiAvan(codGara, codLotto, num);
		Date dataUltimazione = contrattiMapper.getFaseAffidamentiDirettiConc(codGara, codLotto, num);
		
		if (dataInizio == null) {
			ValidateEntry entry = new ValidateEntry("Data inizio", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}
		
		if (importoCertificato == null) {
			ValidateEntry entry = new ValidateEntry("Importo liquidato", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}
		
		if (dataUltimazione == null) {
			ValidateEntry entry = new ValidateEntry("Data ultimazione", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}
		
		return risultato;

		
	}

}
