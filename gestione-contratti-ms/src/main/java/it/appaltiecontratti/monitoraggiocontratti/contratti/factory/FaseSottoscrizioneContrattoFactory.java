package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeEsecuzioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseSottoscrizioneContrattoFactory extends AbstractFactory {

	private static final Long FASE_INIZIALE = new Long(
			Integer.valueOf(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseInizialeEsecuzioneEntry fase  = contrattiMapper.getFaseInizialeSottoscrizioneContratto(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		Date dataVerbAggiudicazione = null;
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		GaraEntry gara = contrattiMapper.dettaglioGara(codGara);
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
			dataVerbAggiudicazione = getDataVerbAggiudicazione(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
						fase.getNumAppa(), lotto, sqlMapper, true);
			
		}

//		if (fase.getDataStipula() == null && gara.getSommaUrgenza() != null
//				&& (gara.getVersioneSimog() != null && "1".equals(gara.getSommaUrgenza().toString())
//						&& gara.getVersioneSimog() == 1L)
//				|| (fase.getDataStipula() != null && dataVerbAggiudicazione != null
//						&& dataVerbAggiudicazione.after(fase.getDataStipula()))) {
//			ValidateEntry entry = new ValidateEntry("Data stipula contratto", "Valore invalido", "E");
//			risultato.add(entry);
//		}
//
//		if (gara.getSommaUrgenza() != null && gara.getSommaUrgenza() != 1L 
//				&& fase.getDataEsecutivita() != null && fase.getDataStipula() != null
//				&& fase.getDataEsecutivita().before(fase.getDataStipula())) {
//			ValidateEntry entry = new ValidateEntry("Data esecutività contratto",
//					"Non può essere antecedente alla data di stipula del contratto", "E");
//			risultato.add(entry);
//		}
//		
		if(fase.getDataStipula() == null){
			ValidateEntry entry = new ValidateEntry("Data stipula contratto", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if(fase.getDataStipula() != null && fase.getDataScadenza() != null && 
				(fase.getDataStipula().after(fase.getDataScadenza()) ||
				 fase.getDataStipula().equals(fase.getDataScadenza()))) {
			ValidateEntry entry = new ValidateEntry("Data stipula contratto",
					"Deve essere minore alla data di scadenza", "E");
			risultato.add(entry);
		}
		
		if(fase.getDataEsecutivita() != null && fase.getDataStipula() != null && 
				fase.getDataEsecutivita().before(fase.getDataStipula())) {
			ValidateEntry entry = new ValidateEntry("Data esecutività contratto",
					"Minore alla data di stipula del contratto", "W");
			risultato.add(entry);
		}
		
		if(fase.getDataDecorrenza() == null) {
			ValidateEntry entry = new ValidateEntry("Data decorrenza contrattuale", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if(fase.getDataScadenza() == null) {
			ValidateEntry entry = new ValidateEntry("Data scadenza contrattuale", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getImportoCauzione() == null) {
			ValidateEntry entry = new ValidateEntry("Importo cauzione", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		return risultato;

	}

		
}
