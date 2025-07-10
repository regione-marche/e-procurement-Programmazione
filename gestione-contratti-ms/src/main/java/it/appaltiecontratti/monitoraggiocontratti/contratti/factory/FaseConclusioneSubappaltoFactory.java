package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSubappaltoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseConclusioneSubappaltoFactory extends AbstractFactory {

	private static final Long FASE_SUBAPPALTO = new Long(Integer.valueOf(CostantiW9.SUBAPPALTO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseSubappaltoEntry fase = contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		if (chiaveGara != null ) {

//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_SUBAPPALTO, fase.getNum())) {
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
		
		if (fase.getImportoEffettivo() == null) {
			ValidateEntry entry = new ValidateEntry("Importo effettivo", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if (fase.getDataUltimazione() == null) {
			ValidateEntry entry = new ValidateEntry("Data ultimazione", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		return risultato;

	}

	
	
}
