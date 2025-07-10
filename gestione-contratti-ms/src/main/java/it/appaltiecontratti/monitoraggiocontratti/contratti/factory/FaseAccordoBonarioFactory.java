package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAccordoBonarioEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseAccordoBonarioFactory extends AbstractFactory {

	private static final Long FASE_ACCORDO_BONARIO = new Long(Integer.valueOf(CostantiW9.ACCORDO_BONARIO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseAccordoBonarioEntry fase = contrattiMapper.getFaseAccordoBonario(codGara, codLotto, num);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		Date dataStipula = null;
		if(chiaveGara != null) {
			dataStipula = sqlMapper.getDataStipula(chiaveGara.getCodGara(), chiaveGara.getCodLotto());
//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_ACCORDO_BONARIO, fase.getNum())) {
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


		if (fase.getDataAccordo() == null) {
			ValidateEntry entry = new ValidateEntry("Data dell'accordo bonario", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		if (fase.getDataAccordo() != null && dataStipula != null && fase.getDataAccordo().before(dataStipula)) {
			ValidateEntry entry = new ValidateEntry("Data dell'accordo bonario",
					"Deve essere successiva a data stipula", "E");
			risultato.add(entry);
		}
		if (fase.getOneriDerivanti() == null) {
			ValidateEntry entry = new ValidateEntry("Oneri derivanti", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		if (fase.getNumRiserve() == null) {
			ValidateEntry entry = new ValidateEntry("Num. riserve transate", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		return risultato;

	}

}
