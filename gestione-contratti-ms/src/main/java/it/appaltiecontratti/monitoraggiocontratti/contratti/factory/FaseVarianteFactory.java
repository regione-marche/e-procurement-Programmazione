package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseVarianteEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseVarianteFactory extends AbstractFactory {

	private static final Long FASE_VARIANTE = new Long(Integer.valueOf(CostantiW9.VARIANTE_CONTRATTO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseVarianteEntry fase = contrattiMapper.getFaseVariante(codGara, codLotto, num);
		fase.setCountW9moti(contrattiMapper.getCountW9moti(codGara, codLotto));
		fase.setMotivazioniVariante(getMotivazioniFaseVariante(codGara, codLotto, num, contrattiMapper));		
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		int countIdMotivoVar = sqlMapper.getCountIdMotivoVar(chiaveGara.getCodGara(), chiaveGara.getCodLotto());
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		if(chiaveGara != null) {

//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_VARIANTE, fase.getNum())) {
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
		if (fase.getDataVerbaleAppr() == null) {
			ValidateEntry entry = new ValidateEntry("Data di approvazione della modifica", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		Date dataVerbIniziale = sqlMapper.getDataVerbInizio(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
				fase.getNumAppa());
		if (dataVerbIniziale != null && fase.getDataVerbaleAppr() != null
				&& fase.getDataVerbaleAppr().before(dataVerbIniziale)) {
			ValidateEntry entry = new ValidateEntry("Data di approvazione della modifica",
					"Data antecedente la data di effettivo inizio", "W");
			risultato.add(entry);
		}

		BigDecimal importoRideterminatoForniture = new BigDecimal(
				super.convertiImporto(fase.getImportoRideterminatoForniture()));
		BigDecimal importoRideterminatoServizi = new BigDecimal(
				super.convertiImporto(fase.getImportoRideterminatoServizi()));
		BigDecimal importoRideterminatoLavori = new BigDecimal(
				super.convertiImporto(fase.getImportoRideterminatoLavori()));

		if ((fase.getImportoRideterminatoLavori() == null || importoRideterminatoLavori.compareTo(BigDecimal.ZERO) == 0)
				&& (fase.getImportoRideterminatoForniture() == null
						|| importoRideterminatoForniture.compareTo(BigDecimal.ZERO) == 0)
				&& (fase.getImportoRideterminatoServizi() == null
						|| importoRideterminatoServizi.compareTo(BigDecimal.ZERO) == 0)) {
			ValidateEntry entry = new ValidateEntry(
					"Importo componente lavori al netto della sicurezza, Importo componente servizi al netto della sicurezza"
							+ ", Importo componente forniture al netto della sicurezza",
					"L'importo di almeno uno dei tre campi indicati deve essere > 0", "E");
			risultato.add(entry);
		}

		if (fase.getImportoSicurezza() == null || fase.getImportoSicurezza() <= 0d) {
			ValidateEntry entry = new ValidateEntry("Importo totale per l'attuazione della sicurezza",
					"Valorizzare il campo", "W");
			risultato.add(entry);
		}

		if (fase.getImportoProgettazione() == null || fase.getImportoProgettazione() <= 0d) {
			ValidateEntry entry = new ValidateEntry("Importo progettazione", "Valorizzare il campo", "W");
			risultato.add(entry);
		}

		if (fase.getImportoNonAssog() == null || fase.getImportoNonAssog() <= 0d) {
			ValidateEntry entry = new ValidateEntry("Eventuali ulteriori somme non assoggettate al ribasso d'asta",
					"Valorizzare il campo", "W");
			risultato.add(entry);
		}

		if (dataVerbIniziale != null && fase.getDataAttoAggiuntivo() != null
				&& fase.getDataAttoAggiuntivo().before(dataVerbIniziale)) {
			ValidateEntry entry = new ValidateEntry("Data sottoscrizione eventuale atto aggiuntivo",
					"Data antecedente la data di effettivo inizio", "W");
			risultato.add(entry);
		}

//		if (fase.getImportoComplIntervento() == null) {
//			ValidateEntry entry = new ValidateEntry("Importo complessivo intervento", "Valorizzare il campo", "E");
//			risultato.add(entry);
//		}
//
//		if (fase.getImportoComplAppalto() == null) {
//			ValidateEntry entry = new ValidateEntry("Importo complessivo appalto rideterminato", "Valorizzare il campo",
//					"E");
//			risultato.add(entry);
//		}
//
//		if (fase.getImportoSubtotale() == null) {
//			ValidateEntry entry = new ValidateEntry("Importo subtotale", "Valorizzare il campo", "E");
//			risultato.add(entry);
//		}

		BigDecimal importoSubtotale = new BigDecimal(super.convertiImporto(fase.getImportoSubtotale()));

		if (!importoSubtotale.equals(super.sommaImporti(importoRideterminatoForniture, importoRideterminatoLavori,
				importoRideterminatoServizi))) {
			ValidateEntry entry = new ValidateEntry("Importo al netto della sicurezza",
					"Importo subtotale diverso dalla somma degli importi", "E");
			risultato.add(entry);
		}

		BigDecimal importoSicurezza = new BigDecimal(super.convertiImporto(fase.getImportoSicurezza()));
		BigDecimal importoProgettazione = new BigDecimal(super.convertiImporto(fase.getImportoProgettazione()));
		BigDecimal importoNonAssog = new BigDecimal(super.convertiImporto(fase.getImportoNonAssog()));
		BigDecimal importoComplAppalto = new BigDecimal(super.convertiImporto(fase.getImportoComplAppalto()));

		if (!importoComplAppalto.equals(
				super.sommaImporti(importoSicurezza, importoSubtotale, importoProgettazione, importoNonAssog))) {
			ValidateEntry entry = new ValidateEntry("Importo complessivo appalto",
					"Importo complessivo appalto rideterminato diverso dalla somma degli importi", "E");
			risultato.add(entry);
		}

		BigDecimal importoComplIntervento = new BigDecimal(super.convertiImporto(fase.getImportoComplIntervento()));
		BigDecimal importoDisposizione = new BigDecimal(super.convertiImporto(fase.getImportoDisposizione()));

		if (!importoComplIntervento.equals(super.sommaImporti(importoDisposizione, importoComplAppalto))) {
			ValidateEntry entry = new ValidateEntry("Importo complessivo intervento",
					"Importo complessivo intervento diverso dalla somma degli importi", "E");
			risultato.add(entry);
		}

		if ((fase.getNumGiorniProroga() == null || fase.getNumGiorniProroga() == 0L)
				&& fase.getMotivazioniVariante() != null) {
			for (LottoDynamicValue a : fase.getMotivazioniVariante()) {
				if (a.getCodice() == 18L) {
					ValidateEntry entry = new ValidateEntry(
							"Giorni di proroga concessi/tempo aggiuntivo rispetto ai termini contrattuali",
							"Valorizzare il campo", "E");
					risultato.add(entry);
					break;
				}
			}
		}

		if (fase.getMotivazioniVariante() == null || fase.getMotivazioniVariante().size() == 0) {
			ValidateEntry entry = new ValidateEntry("Motivazione della modifica contratttuale", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		boolean hasMotivazione8 = false;
		boolean hasMotivazione18 = false;
		boolean hasMotivazione7 = false;
		boolean hasMotivazione32 = false;
		boolean hasMotivazione36 = false;


		if (fase.getMotivazioniVariante() != null) {
			for (LottoDynamicValue v : fase.getMotivazioniVariante()) {
				if (v.getCodice() == null) {
					ValidateEntry entry = new ValidateEntry("Motivazione della modifica contratttuale",
							"Valorizzare il campo", "E");
					risultato.add(entry);
				}

				if (v.getCodice() == 18L) {
					hasMotivazione18 = true;
				}
				if (v.getCodice() == 8L && v.getValue() == 1L) {
					hasMotivazione8 = true;
				}
				if (v.getCodice() == 7L && v.getValue() == 1L) {
					hasMotivazione7 = true;
				}
				if(v.getCodice() == 36L && v.getValue() == 1L) {
					hasMotivazione36 = true;
				}
				if(v.getCodice() == 32L && v.getValue() == 1L) {
					hasMotivazione32 = true;
				}
			}
		}
		
		if (hasMotivazione18 && StringUtils.isBlank(fase.getAltreMotivazioni())
				&& fase.getCigNuovaProc() == null) {
			ValidateEntry entry = new ValidateEntry("CIG della nuova procedura avviata", "In caso di proroga tecnica è necessario indicare il CIG della nuova procedura avviata ovvero le motivazioni della proroga nel campo 'Altre motivazioni'", "E");
			risultato.add(entry);
		}

		if (StringUtils.isBlank(fase.getAltreMotivazioni())) {
			ValidateEntry entry = new ValidateEntry("Altre motivazioni", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (hasMotivazione18 && (fase.getNumGiorniProroga() == null || fase.getNumGiorniProroga() == 0L)) {
			ValidateEntry entry = new ValidateEntry("Numero giorni proroga", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (hasMotivazione8 && fase.getAltreMotivazioni() == null) {
			ValidateEntry entry = new ValidateEntry("Altre motivazioni", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if(fase.getUrlVariantiCo() == null && (hasMotivazione7 || hasMotivazione8)) {
			ValidateEntry entry = new ValidateEntry("URL documentazione varianti in corso d'opera", "Per i contratti pubblici di importo superiore alla soglia comunitaria, se la variante in corso d’opera eccede il 10% dell’importo originario del contratto, è necessario fornire il link ‘URL documentazione varianti in corso d’opera'", "W");
			risultato.add(entry);
		}

		if(fase.getUrlVariantiCo() == null && hasMotivazione32) {
			ValidateEntry entry = new ValidateEntry("URL documentazione varianti in corso d'opera", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if(fase.getMotivoRevPrezzi() == null && hasMotivazione36) {
			ValidateEntry entry = new ValidateEntry("Motivo Revisione prezzi", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		return risultato;

	}
	
	private List<LottoDynamicValue> getMotivazioniFaseVariante(Long codGara, Long numLotto, Long num, ContrattiMapper contrattiMapper) throws Exception {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			GaraFullEntry gara = contrattiMapper.dettaglioGaraCompleto(codGara);
			LottoEntry lotto = contrattiMapper.getDettaglioLotto(codGara, numLotto);

			if (gara != null && lotto != null) {

				String tipoContrattoLotto = lotto.getTipologia();
				List<TabellatoEntry> tabellatoMotivazioni = new ArrayList<TabellatoEntry>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataRiferimento = sdf.parse("2016-04-18");
				boolean dataPubblicazioneBefore = gara.getDataCreazione() == null
						|| (gara.getDataCreazione() != null && (gara.getDataCreazione().before(dataRiferimento)
								|| gara.getDataCreazione().equals(dataRiferimento)));

				if (dataPubblicazioneBefore) {
					if ("L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar2();						
					} else if (!"L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar1();
					}
				} else {
					if (gara.getVersioneSimog() < 3L) {
						tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar3();						
					}
				}
				
				if(gara.getVersioneSimog() >= 3L && gara.getVersioneSimog() < 9L) {
					tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar4();
				}
				
				if(gara.getVersioneSimog() == 9L) {
					tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar5();
				}
				
				List<Long> codiciCondizioni = contrattiMapper.getMotiviVariazioneByFase(codGara, numLotto, num);
				for (TabellatoEntry e : tabellatoMotivazioni) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciCondizioni.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return risultato;
	}	

}
