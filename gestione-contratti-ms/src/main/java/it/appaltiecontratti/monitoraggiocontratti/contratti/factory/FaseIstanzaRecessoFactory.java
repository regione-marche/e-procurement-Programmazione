package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseIstanzaRecessoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseIstanzaRecessoFactory extends AbstractFactory {

	private static final Long FASE_ISTANZA_RECESSO = new Long(Integer.valueOf(CostantiW9.ISTANZA_RECESSO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseIstanzaRecessoEntry fase = contrattiMapper.getFaseIstanzaRecesso(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		if (chiaveGara != null ) {

//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_ISTANZA_RECESSO, fase.getNum())) {
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
		if (fase.getDataTermine() == null) {
			ValidateEntry entry = new ValidateEntry("Termine previsto per la consegna", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getTipoComunicazione() == null) {
			ValidateEntry entry = new ValidateEntry("Tipologia comunicazione", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getDurataSospensione() == null) {
			ValidateEntry entry = new ValidateEntry("Durata della sospensione in giorni", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getDataIstanzaRecesso() == null) {
			ValidateEntry entry = new ValidateEntry("Data presentazione istanza recesso", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getFlagTardiva() == null) {
			ValidateEntry entry = new ValidateEntry("Si è proceduto a consegna tardiva?", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getFlagRipresa() == null) {
			ValidateEntry entry = new ValidateEntry("Si è proceduto alla ripresa dei lavori?", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		if (fase.getFlagRiserva() == null) {
			ValidateEntry entry = new ValidateEntry("L'appaltatore ha formulato riserve?", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		return risultato;

	}

	
}
