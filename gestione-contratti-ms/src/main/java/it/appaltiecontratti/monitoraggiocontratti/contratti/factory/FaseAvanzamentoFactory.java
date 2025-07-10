package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseAvanzamentoFactory extends AbstractFactory {

	private static final Long FASE_AVANZAMENTO = new Long(
			Integer.valueOf(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseAvanzamentoEntry fase = contrattiMapper.getFaseAvanzamento(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		Double importoComplessivoAppalto = null;
		Double importoFaseVariante = null;
		Date dataVerbAggiudicazione = null;
		if(chiaveGara != null) {
//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_AVANZAMENTO, fase.getNum())) {
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
		
			importoComplessivoAppalto = getImportoComplessivoAppalto(chiaveGara.getCodGara(),
				chiaveGara.getCodLotto(), fase.getNumAppa(), lotto, sqlMapper);
			importoFaseVariante = sqlMapper.getImportoUltimaFaseVariante(chiaveGara.getCodGara(),
				chiaveGara.getCodLotto());		

			dataVerbAggiudicazione = getDataVerbAggiudicazione(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
				fase.getNumAppa(), lotto, sqlMapper, false);
		}

		if (fase.getImportoCertificato() == null) {
			ValidateEntry entry = new ValidateEntry("Importo del certificato di pagamento", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		if ((importoFaseVariante == null && fase.getImportoCertificato() != null && fase.getImportoCertificato() > importoComplessivoAppalto)
				|| (importoFaseVariante != null && fase.getImportoCertificato() != null && fase.getImportoCertificato() > importoFaseVariante)) {
			ValidateEntry entry = new ValidateEntry("Importo certificato", "L'importo non può eccedere l'importo complessivo dell'appalto o dell'ultima modifica contrattuale", "W");
			risultato.add(entry);
		}

		if (fase.getImportoAnticipazione() != null && (fase.getImportoAnticipazione() > importoComplessivoAppalto
				|| (importoFaseVariante != null && fase.getImportoAnticipazione() > importoFaseVariante))) {
			ValidateEntry entry = new ValidateEntry("Importo anticipazione",
					"L'importo non può eccedere l'importo complessivo dell'appalto o dell'ultima modifica contrattuale", "W");
			risultato.add(entry);
		}

		if (fase.getImportoSal() == null) {
			ValidateEntry entry = new ValidateEntry("Importo SAL", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getImportoSal() != null
				&& (importoFaseVariante == null && fase.getImportoSal() > importoComplessivoAppalto
						|| (importoFaseVariante != null && fase.getImportoSal() > importoFaseVariante))) {
			ValidateEntry entry = new ValidateEntry("Importo SAL",
					"L'importo non può eccedere l'importo complessivo dell'appalto o dell'ultima modifica contrattuale", "W");
			risultato.add(entry);
		}

		if (fase.getFlagRitardo() == null) {
			ValidateEntry entry = new ValidateEntry("Ritardo/anticipo", "Valorizzare il campo", "E");
			risultato.add(entry);
		} else if (!fase.getFlagRitardo().equals("P") && fase.getNumGiorniScost() == null) {
			ValidateEntry entry = new ValidateEntry("Numero giorni di scostamento", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getNumGiorniScost() != null && fase.getNumGiorniScost() < 1) {
			ValidateEntry entry = new ValidateEntry("Numero giorni di scostamento",
					"Valore invalido (dev'essere maggiore di 0)", "E");
			risultato.add(entry);
		}

		if (fase.getFlagPagamento() == null) {
			ValidateEntry entry = new ValidateEntry("Modalità di pagamento del corrispettivo", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		if ((fase.getImportoAnticipazione() != null && fase.getImportoAnticipazione() > 0 && fase.getDataAnticipazione() == null)
				|| (fase.getImportoAnticipazione() == null && fase.getDataAnticipazione() != null )) {
			ValidateEntry entry = new ValidateEntry("Data del certificato anticipazione / importo anticipazione",
					"Valorizzare entrambi i campi o nessuno dei due", "E");
			risultato.add(entry);
		}

		if ((fase.getImportoAnticipazione() != null || fase.getDataAnticipazione() != null) && fase.getNum() != 1L) {
			ValidateEntry entry = new ValidateEntry("Data del certificato anticipazione / importo anticipazione",
					"Anticipazione non ammessa in uno stato di avanzamento successivo al primo", "W");
			risultato.add(entry);
		}

		if (fase.getDataAnticipazione() != null && dataVerbAggiudicazione != null
				&& fase.getDataAnticipazione().before(dataVerbAggiudicazione)) {
			ValidateEntry entry = new ValidateEntry("Data del certificato anticipazione",
					"Deve essere successiva alla data di aggiudicazione definitiva", "W");
			risultato.add(entry);
		}

		if (fase.getDataRaggiungimento() == null) {
			ValidateEntry entry = new ValidateEntry("Data dello stato di avanzamento", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getDataRaggiungimento() != null && dataVerbAggiudicazione != null) {
			LocalDate dataSAL = fase.getDataRaggiungimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dataAggiudicazione = dataVerbAggiudicazione.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if(dataSAL.isBefore(dataAggiudicazione)) {
				ValidateEntry entry = new ValidateEntry("Data dello stato di avanzamento",
					"Deve essere successiva alla data di aggiudicazione definitiva", "E");
				risultato.add(entry);
			}
		}

		if (fase.getDenomAvanzamento() == null) {
			ValidateEntry entry = new ValidateEntry("Denominazione stato di avanzamento", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		return risultato;

	}


}
