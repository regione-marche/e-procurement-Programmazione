package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSospensioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseRipresaPrestazioneFactory extends AbstractFactory {

	private static final Long FASE_SOSPENSIONE = new Long(
			Integer.valueOf(CostantiW9.SOSPENSIONE_CONTRATTO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseSospensioneEntry fase = contrattiMapper.getFaseSospensione(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		if (chiaveGara != null ) {

//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_SOSPENSIONE, fase.getNum())) {
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
		if (fase.getDataVerbRipr() != null &&
			fase.getDataVerbSosp() != null &&
			fase.getDataVerbRipr().before(fase.getDataVerbSosp())
		) {
			ValidateEntry entry = new ValidateEntry("data del verbale di ripresa",
					"Dev'essere successiva alla data del verbale di sospensione", "E");
			risultato.add(entry);
		}

		if (fase.getDataVerbRipr() == null) {
			ValidateEntry entry = new ValidateEntry("Valorizzare il campoe", "E");
			risultato.add(entry);
		}


			if(fase.getFlagRiserve() == null) {
			ValidateEntry entry = new ValidateEntry("Iscrizione di riserve", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if(fase.getFlagVerbale() == null) {
			ValidateEntry entry = new ValidateEntry("Verbale non sottoscritto dall'appaltatore", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		return risultato;

	}



}
