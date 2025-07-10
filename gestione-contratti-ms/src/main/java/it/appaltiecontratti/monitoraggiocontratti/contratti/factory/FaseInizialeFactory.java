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

public class FaseInizialeFactory extends AbstractFactory {

	private static final Long FASE_INIZIALE = new Long(
			Integer.valueOf(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseInizialeEsecuzioneEntry fase = contrattiMapper.getFaseInizialeEsecuzione(codGara, codLotto, num);
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
			dataVerbAggiudicazione = getDataVerbAggiudicazione(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
						fase.getNumAppa(), lotto, sqlMapper, true);
			
		}


		

		if (fase.getDataVerbInizio() == null) {
			ValidateEntry entry = new ValidateEntry("Data di effettivo inizio lavori/servizi/forniture",
					"Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getDataApprovazioneProg() != null && fase.getDataInizioProg() != null
				&& (fase.getDataApprovazioneProg().before(fase.getDataInizioProg())
						|| fase.getDataApprovazioneProg().equals(fase.getDataInizioProg()))) {
			ValidateEntry entry = new ValidateEntry("Data approvazione del progetto esecutivo",
					"Non può essere antecedente alla data disposizione dell'inizio della prog. esecutiva", "E");
			risultato.add(entry);
		}

		if (fase.getFrazionata() == null) {
			if("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia())) {
				ValidateEntry entry = new ValidateEntry("Avvio esecuzione contratto per fasi", "Valorizzare il campo", "E");
				risultato.add(entry);
			}else {			
				ValidateEntry entry = new ValidateEntry("Consegna frazionata?", "Valorizzare il campo", "E");
				risultato.add(entry);
			}
		} else if (fase.getFrazionata().equals("1") && fase.getDataVerbaleCons() == null) {
			if("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia())) {
				ValidateEntry entry = new ValidateEntry("Data verbale avvio prima fase esecuzione contratto", "Valorizzare il campo", "E");
				risultato.add(entry);
			}else {
				ValidateEntry entry = new ValidateEntry("Data verbale prima consegna lavori", "Valorizzare il campo", "E");
				risultato.add(entry);
			}
		}

		if (("2").equals(fase.getRiserva()) && fase.getDataVerbaleCons() != null && fase.getDataStipula() != null
				&& fase.getDataVerbaleCons().before(fase.getDataStipula())) {
			if("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia())) {
				ValidateEntry entry = new ValidateEntry("Data verbale avvio prima fase esecuzione contratto",
						"Non può essere antecedente alla data di stipula", "E");
				risultato.add(entry);				
			}else {
				ValidateEntry entry = new ValidateEntry("Data verbale prima consegna lavori",
						"Non può essere antecedente alla data di stipula", "E");
				risultato.add(entry);
			}
			
		}

		if (("2").equals(fase.getRiserva()) && fase.getDataVerbaleDef() != null && fase.getDataStipula() != null
				&& fase.getDataVerbaleDef().before(fase.getDataStipula())) {
			if("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia())) {
				ValidateEntry entry = new ValidateEntry("Data verbale avvio esecuzione contratto",
						"Non può essere antecedente alla data di stipula", "W");
				risultato.add(entry);
			}else {
				ValidateEntry entry = new ValidateEntry("Data verbale consegna definitiva",
						"Non può essere antecedente alla data di stipula", "W");
				risultato.add(entry);
			}			
		}

		if ("2".equals(fase.getFrazionata()) && fase.getDataVerbaleDef() == null) {
			if("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia())) {
				ValidateEntry entry = new ValidateEntry("Data verbale avvio esecuzione contratto", "Valorizzare il campo", "E");
				risultato.add(entry);
			}else {
				ValidateEntry entry = new ValidateEntry("Data verbale consegna definitiva", "Valorizzare il campo", "E");
				risultato.add(entry);
			}			
		}

		if (fase.getRiserva() == null) {
			ValidateEntry entry = new ValidateEntry("Consegnata sotto riserva di legge?", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (("2").equals(fase.getRiserva()) && fase.getDataVerbInizio() != null && fase.getDataStipula() != null
				&& fase.getDataVerbInizio().before(fase.getDataStipula())) {
			ValidateEntry entry = new ValidateEntry("Data effettivo inizio lavori/forniture/servizi",
					"Non può essere antecedente alla data di stipula", "W");
			risultato.add(entry);
		}

		if (fase.getDataTermine() == null) {
			ValidateEntry entry = new ValidateEntry(
					"Data fine prevista per dare ultimazione ai lavori/servizi/forniture", "Valorizzare il campo", "E");
			risultato.add(entry);
		} else if ("2".equals(fase.getFrazionata()) && fase.getDataStipula() != null
				&& (fase.getDataTermine().before(fase.getDataStipula())
				&& fase.getDataVerbInizio() != null && fase.getDataTermine().before(fase.getDataVerbInizio()))) {
			ValidateEntry entry = new ValidateEntry(
					"Data fine prevista per dare ultimazione ai lavori/servizi/forniture",
					"Non può essere antecedente alla data di stipula e alla data di effettivo inizio", "E");
			risultato.add(entry);
		}

		return risultato;

	}

}
