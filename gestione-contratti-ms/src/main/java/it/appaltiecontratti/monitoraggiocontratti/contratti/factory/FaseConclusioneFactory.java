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

public class FaseConclusioneFactory extends AbstractFactory {

	private static final Long FASE_CONCLUSIONE = new Long(
			Integer.valueOf(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseConclusioneEntry fase = contrattiMapper.getFaseConclusioneContratto(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		Date dataVerbAggiudicazione = null;

		Double importoComplIntervento = null;
		
		if(chiaveGara != null) {
//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_CONCLUSIONE, fase.getNum())) {
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
			
			importoComplIntervento = super.getImportoComplessivoIntervento(chiaveGara.getCodGara(),
					chiaveGara.getCodLotto(), fase.getNumAppa(), lotto, sqlMapper);
		}

		if (fase.getDataRisoluzione() != null && dataVerbAggiudicazione != null
				&& fase.getDataRisoluzione().before(dataVerbAggiudicazione)) {
			ValidateEntry entry = new ValidateEntry("Data interruzione anticipata",
					"Deve essere successiva alla data di aggiudicazione definitiva", "E");
			risultato.add(entry);
		}

		if (fase.getInterruzioneAnticipata() == null) {
			ValidateEntry entry = new ValidateEntry("Interruzione anticipata del contratto?", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		if ("1".equals(fase.getInterruzioneAnticipata()) && fase.getMotivoInterruzione() == null) {
			ValidateEntry entry = new ValidateEntry("Causa dell'interruzione anticipata", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getMotivoInterruzione() != null && fase.getDataRisoluzione() == null) {
			ValidateEntry entry = new ValidateEntry("Data interruzione anticipata", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getDataRisoluzione() != null && dataVerbAggiudicazione != null
				&& fase.getDataRisoluzione().before(dataVerbAggiudicazione)) {
			ValidateEntry entry = new ValidateEntry("Data interruzione anticipata",
					"Deve essere successiva alla data di aggiudicazione definitiva", "E");
			risultato.add(entry);
		}

		if ((fase.getMotivoInterruzione() != null && (fase.getMotivoInterruzione() == 2L
				|| fase.getMotivoInterruzione() == 4L || fase.getMotivoInterruzione() == 5L))
				&& fase.getFlagOneri() == null) {
			ValidateEntry entry = new ValidateEntry("Oneri economici derivanti dalla risoluzione/recesso",
					"Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getFlagOneri() != null && !"0".equals(fase.getFlagOneri()) && fase.getImportoOneri() == null) {
			ValidateEntry entry = new ValidateEntry("Importo degli oneri", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getImportoOneri() != null && fase.getFlagOneri() != null && !"0".equals(fase.getFlagOneri())
				&& fase.getImportoOneri() == 0d) {
			ValidateEntry entry = new ValidateEntry("Importo degli oneri", "Il campo deve essere maggiore di 0",
					"E");
			risultato.add(entry);
		}

		if (fase.getImportoOneri() != null && importoComplIntervento != null
				&& fase.getImportoOneri() >= importoComplIntervento) {
			ValidateEntry entry = new ValidateEntry("Importo degli oneri",
					"Il campo deve essere minore dell'importo complessivo dell'intervento", "E");
			risultato.add(entry);
		}

		if (fase.getDataUltimazione() != null && dataVerbAggiudicazione != null
				&& fase.getDataUltimazione().before(dataVerbAggiudicazione)) {
			ValidateEntry entry = new ValidateEntry("Data ultimazione",
					"Deve essere successiva alla data di aggiudicazione definitiva", "E");
			risultato.add(entry);
		}

		if (fase.getMotivoInterruzione() != null && fase.getFlagPolizza() == null) {
			ValidateEntry entry = new ValidateEntry("Incamerata polizza?", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getDataUltimazione() == null && "2".equals(fase.getInterruzioneAnticipata())) {
			ValidateEntry entry = new ValidateEntry("Data ultimazione", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getNumInfortuni() == null && "2".equals(fase.getInterruzioneAnticipata())) {
			ValidateEntry entry = new ValidateEntry("Numero infortuni", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getNumInfortuniMortali() == null && "2".equals(fase.getInterruzioneAnticipata())) {
			ValidateEntry entry = new ValidateEntry("Numero infortuni mortali", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getNumInfortuniPermanenti() == null && "2".equals(fase.getInterruzioneAnticipata())) {
			ValidateEntry entry = new ValidateEntry("Numero infortuni con postumi permanenti", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		long numInfortuni = fase.getNumInfortuni() == null ? 0L : fase.getNumInfortuni();
		long numInfortuniPermanenti = fase.getNumInfortuniPermanenti() == null ? 0L : fase.getNumInfortuniPermanenti();
		long numInfortuniMortali = fase.getNumInfortuniMortali() == null ? 0L : fase.getNumInfortuniMortali();

		if (numInfortuni < (numInfortuniPermanenti + numInfortuniMortali)) {
			ValidateEntry entry = new ValidateEntry("Numero infortuni",
					"Numero infortuni dev'essere maggiore della somma del numero infortuni con postumi permanenti più il numero infortuni mortali",
					"E");
			risultato.add(entry);
		}

		if (numInfortuni > 9) {
			ValidateEntry entry = new ValidateEntry("Numero infortuni", "Numero infortuni maggiore di 9", "W");
			risultato.add(entry);
		}

		return risultato;
	

	}

}
