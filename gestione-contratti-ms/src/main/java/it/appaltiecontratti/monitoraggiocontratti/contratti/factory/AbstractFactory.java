package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSospensioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FinanziamentiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;

abstract class AbstractFactory implements FaseFactory {
	
	public abstract List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception;

	

	private String calcolaIdScheda(Long num) {
		return StringUtils.leftPad(num.toString(), 3, "0");
	}

//	private static String calcolaIdScheda(Long num) {
//		String output = num.toString();
//		while (output.length() < 3)
//			output = "0" + output;
//		return output;
//	}

	
	protected boolean esisteImpresa(ImpresaEntry impresa, SqlMapper mapper) {
		Long count = impresa != null ? mapper.findImpresa(impresa.getCodiceFiscale(), impresa.getStazioneAppaltante())
				: 0L;
		return count != 0L;
	}

	protected static List<String> getSezioneIncaricoByFase(Long numFase) {

		ArrayList<String> risultato = new ArrayList<String>();

		switch (numFase.intValue()) {
		case 1:
			risultato.add("RA");
			risultato.add("PA");
			break;
		case 987:
			risultato.add("RE");
			risultato.add("RS");
			break;
		case 2:
		case 986:
			risultato.add("IN");
			break;
		case 12:
			risultato.add("RQ");
			break;
		case 5:
			risultato.add("CO");
			break;
		case 998:
			risultato.add("NP");
			break;

		}
		return risultato;
	}



	protected List<ValidateEntry> validateImpreseAggiudicatarie(List<ImpresaAggiudicatariaEntry> imprese,
			GaraEntry gara) {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		Map<Long, Long> mapRuoli = new HashMap<Long, Long>();
		int counter = 1;
		if (imprese != null) {
			Map<Long, HashMap<Long, Long>> raggruppamentiMap = new HashMap<Long, HashMap<Long, Long>>();
			boolean invalidImpNumber = false;
			for (ImpresaAggiudicatariaEntry imp : imprese) {
				if (imp.getIdTipoAgg() == null) {
					ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
							"Tipologia del soggetto aggiudicatario dev'essere valorizzato", "E");
					risultato.add(entry);
				}
				if (imp.getIdTipoAgg() == 1L && imp.getRuolo() == null) {
					ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
							"Il campo Ruolo (eventuale) nell'associazione dev'essere valorizzato", "E");
					risultato.add(entry);
				}
				if (StringUtils.isEmpty(imp.getFlagAvvallamento())) {
					ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
							"Il campo L'aggiudicatario ha fatto ricorso all'avvalimento? dev'essere valorizzato", "E");
					risultato.add(entry);
				}

				if (imp.getIdTipoAgg() != 3L && gara.getTipoApp() != null
						&& (gara.getTipoApp() == 9L || gara.getTipoApp() == 17L || gara.getTipoApp() == 18L)) {
					if (!(imp.getIdTipoAgg() == 1L && imp.getRuolo() == 2L) && imp.getImportoAggiudicazione() == null) {
						ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
								"Il campo importo di aggiudicazione dev'essere valorizzato", "E");
						risultato.add(entry);
					}
				}

				if (imp.getIdTipoAgg() == 3L && imprese.size() > 1) {
					invalidImpNumber = true;
				}

				if (mapRuoli.containsKey(imp.getIdTipoAgg())) {
					Long count = mapRuoli.get(imp.getIdTipoAgg());
					count++;
					mapRuoli.put(imp.getIdTipoAgg(), count);
				} else {
					mapRuoli.put(imp.getIdTipoAgg(), 1L);
				}
				
				if(imp.getFlagAvvallamento() != null && !"0".equals(imp.getFlagAvvallamento())) {
					ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
					"Dev'essere presente l'impresa ausiliaria", "E");
					risultato.add(entry);
				}

				if (imp.getImpresa() == null)  {
					ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
							"Non è stata indicata un impresa. Valorizzare il campo denominazione", "E");
					risultato.add(entry);
				}
				
				if (StringUtils.isAllBlank(imp.getNomeLegRap(),imp.getCognomeLegRap(),imp.getCfLegRap())) {
					ValidateEntry entry = new ValidateEntry("Impresa nr " + counter,
							"Dev'essere presente un Legale rappresentante", "E");
					risultato.add(entry);
				} else {
					if (StringUtils.isBlank(imp.getNomeLegRap())) {				
						ValidateEntry entry = new ValidateEntry("Legale rappresentante impresa nr " + counter,
								"Il campo nome dev'essere valorizzato", "E");
						risultato.add(entry);
					} 
					if (StringUtils.isBlank(imp.getCognomeLegRap())) {
						ValidateEntry entry = new ValidateEntry("Legale rappresentante impresa nr " + counter,
								"Il campo cognome dev'essere valorizzato", "E");
						risultato.add(entry);
					} 
					if (StringUtils.isBlank(imp.getCfLegRap())) {
						ValidateEntry entry = new ValidateEntry("Legale rappresentante impresa nr " + counter,
								"Il campo codice fiscale dev'essere valorizzato", "E");
						risultato.add(entry);
					}
				}
					
				

				if (imp.getIdTipoAgg() == 1L) {
					if (!raggruppamentiMap.containsKey(imp.getIdGruppo())) {
						raggruppamentiMap.put(imp.getIdGruppo(), new HashMap<Long, Long>());
					}
					if (raggruppamentiMap.get(imp.getIdGruppo()).containsKey(imp.getRuolo())) {
						Long counterRuolo = raggruppamentiMap.get(imp.getIdGruppo()).get(imp.getRuolo());
						counterRuolo++;
						raggruppamentiMap.get(imp.getIdGruppo()).put(imp.getRuolo(), counterRuolo);
					} else {
						raggruppamentiMap.get(imp.getIdGruppo()).put(imp.getRuolo(), 1L);
					}

				}
				if(imp.getImpresaAusiliaria() != null && imp.getImpresaAusiliaria().getRappresentante() == null) {
					ValidateEntry entry = new ValidateEntry("Impresa ausiliaria nr " + counter,
							"Dev'essere presente un Legale rappresentante", "E");
					risultato.add(entry);
				}
				counter++;

			}

			if (!raggruppamentiMap.keySet().isEmpty()) {

				for (Long idGruppo : raggruppamentiMap.keySet()) {
					Map<Long, Long> valueMap = raggruppamentiMap.get(idGruppo);
					Long countMandatarie = valueMap.get(1L);
					Long countMandanti = valueMap.get(2L);

					if (countMandatarie == null || countMandatarie > 1L) {
						ValidateEntry entry = new ValidateEntry("Imprese aggiudicatarie - raggruppamento " + idGruppo,
								"Dev'essere presente una sola impresa con ruolo mandataria", "E");
						risultato.add(entry);
					}
					if (countMandanti == null) {
						ValidateEntry entry = new ValidateEntry("Imprese aggiudicatarie - raggruppamento " + idGruppo,
								"Dev'essere presente almeno un'impresa con ruolo mandante", "E");
						risultato.add(entry);
					}
				}
			}

			if (mapRuoli.keySet().size() > 1) {
				ValidateEntry entry = new ValidateEntry("Imprese aggiudicatarie",
						"Tutte le imprese devo avere stessa tipologia soggetto aggiudicatario", "E");
				risultato.add(entry);
			}

			if (invalidImpNumber && (gara.getTipoApp() != 9L && gara.getTipoApp() != 17L && gara.getTipoApp() != 18L)) {
				ValidateEntry entry = new ValidateEntry("Imprese aggiudicatarie",
						"Dev'essere presente una sola impresa singola.", "E");
				risultato.add(entry);
			}

		}
		return risultato;
	}

	protected List<ValidateEntry> validateIncarichiProfessionali(List<IncarichiProfEntry> incarichi,
			boolean validazionecompleta) {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		int counter = 1;
		if (incarichi != null) {
			for (IncarichiProfEntry inc : incarichi) {

				if (inc.getTecnico() == null) {
					ValidateEntry entry = new ValidateEntry("Incarico nr " + counter,
							"Non è stato indicato il nominativo dell'incaricato", "E");
					risultato.add(entry);
				}

				if (inc.getIdRuolo() == null) {
					ValidateEntry entry = new ValidateEntry("Incarico nr " + counter,
							"Tipologia del soggetto dev'essere valorizzato", "E");
					risultato.add(entry);
				}

				if (validazionecompleta) {
					if (inc.getIdRuolo() != null && inc.getIdRuolo() == 2L && inc.getCigProgEsterna() == null) {
						ValidateEntry entry = new ValidateEntry("Incarico nr " + counter,
								"Il campo CIG affidamento incarico esterno non è valorizzato", "E");
						risultato.add(entry);
					}
					if (inc.getIdRuolo() != null && inc.getIdRuolo() == 2L && inc.getDataAffProgEsterna() == null) {
						ValidateEntry entry = new ValidateEntry("Incarico nr " + counter,
								"Il campo Data affidamento incarico esterno dev'essere valorizzato", "E");
						risultato.add(entry);
					}
					if (inc.getIdRuolo() != null && inc.getIdRuolo() == 2L && inc.getDataConsProgEsterna() == null) {
						ValidateEntry entry = new ValidateEntry("Incarico nr " + counter,
								"Il campo Data consegna progetto dev'essere valorizzato", "E");
						risultato.add(entry);
					}
				}
				counter++;
			}
		}
		return risultato;
	}

	protected List<ValidateEntry> validateFinaniamenti(List<FinanziamentiEntry> finanziamenti) {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		int counter = 1;
		if (finanziamenti != null) {
			for (FinanziamentiEntry fin : finanziamenti) {

				if (fin.getIdFinanziamento() == null) {
					ValidateEntry entry = new ValidateEntry("Finanziamento nr " + counter,
							"Il campo Tipo di finanziamento dev'essere valorizzato", "E");
					risultato.add(entry);
				}
				if (fin.getImportoFinanziamento() == null || fin.getImportoFinanziamento() == 0d) {
					ValidateEntry entry = new ValidateEntry("Finanziamento nr " + counter,
							"Il campo Importo finanziamento dev'essere valorizzato e maggiore di 0", "E");
					risultato.add(entry);
				}
				counter++;
			}

		}
		return risultato;
	}

	

	protected boolean isS2(LottoEntry lotto, SqlMapper sqlMapper) {
		boolean isExSottoSoglia = false;
		if ("1".equals(lotto.getExsottosoglia())) {
			isExSottoSoglia = true;
		}		
		if (!isExSottoSoglia && "2".contentEquals(lotto.getContrattoEscluso())) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isImportoLottoSoprasoglia(LottoEntry lotto, SqlMapper sqlMapper) {
		Double importoTot = lotto.getImportoTotale();
		if (importoTot != null && importoTot >= 40000) {
			return true;
		} else {
			return false;
		}
	}

	protected String convertiImporto(Double importo) {
		String result = null;

		if (importo == null) {
			importo = new Double(0);
		}

		DecimalFormatSymbols simbols = new DecimalFormatSymbols();
		simbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("0.00", simbols);
		result = decimalFormat.format(importo);

		return result;
	}

	protected BigDecimal sommaImporti(BigDecimal... importi) {
		BigDecimal result = null;

		if (importi != null && importi.length > 0) {
			for (BigDecimal importo : importi) {
				if (result == null) {
					result = importo;
				} else {
					result = result.add(importo);
				}
			}
		}

		return result;
	}

	protected Date getDataVerbAggiudicazione(Long codgara, Long codLotto, Long numAppa, LottoEntry lotto, SqlMapper sqlMapper,
			boolean min) {
		Date dataVerbAggiudicazione = null;
		if (lotto.getMasterCig() != null) {
			if (min == true) {
				dataVerbAggiudicazione = sqlMapper.getDataVerbAggiudicazioneMinMultilotto(codgara, numAppa,
						lotto.getMasterCig());
			} else {
				dataVerbAggiudicazione = sqlMapper.getDataVerbAggiudicazioneMaxMultilotto(codgara, numAppa,
						lotto.getMasterCig());
			}
		} else {
			dataVerbAggiudicazione = sqlMapper.getDataVerbAggiudicazione(codgara, codLotto, numAppa);
		}
		return dataVerbAggiudicazione;
	}

	protected Double getImportoComplessivoAppalto(Long codgara, Long codLotto, Long numAppa, LottoEntry lotto, SqlMapper sqlMapper) {
		Double importoComplessivoAppalto = null;
		if (lotto.getMasterCig() != null) {
			importoComplessivoAppalto = sqlMapper.getImportoComplessivoAppaltoMultilotto(codgara, lotto.getMasterCig());
		} else {
			importoComplessivoAppalto = sqlMapper.getImportoComplessivoAppalto(codgara, codLotto, numAppa);
		}
		if(importoComplessivoAppalto == null) {
			return 0D;
		}
		return importoComplessivoAppalto;
	}

	protected Double getImportoComplessivoIntervento(Long codgara, Long codLotto, Long numAppa, LottoEntry lotto, SqlMapper sqlMapper) {
		Double importoComplessivoIntervento = null;
		if (lotto.getMasterCig() != null) {
			importoComplessivoIntervento = sqlMapper.getImportoComplessivoInterventoMultilotto(codgara, numAppa,
					lotto.getMasterCig());
		} else {
			importoComplessivoIntervento = sqlMapper.getImportoComplessivoIntervento(codgara, codLotto, numAppa);
		}
		return importoComplessivoIntervento;
	}



	public ImpresaEntry setImpresaNomImp(ImpresaEntry impresa) {
		if (impresa.getNomImp() == null && StringUtils.isNotBlank(impresa.getRagioneSociale())) {
			String nomImp = impresa.getRagioneSociale().length() > 61 ? impresa.getRagioneSociale().substring(0, 61)
					: impresa.getRagioneSociale();
			impresa.setNomImp(nomImp);
		}
		return impresa;
	}
	
	private Long getSituazioneGara(Long codGara, SqlMapper mapper) {

		List<LottoFullEntry> lotti = mapper.getFullLottiGara(codGara);
		Set<Long> situazioniLotti = new HashSet<Long>();
		for (LottoEntry lotto : lotti) {
			situazioniLotti.add(lotto.getSituazione());
		}
		if (situazioniLotti.size() == 1 && situazioniLotti.contains(8L)) {
			// Annullata
			return 8L;
		} else if (situazioniLotti.contains(1L)) {
			// In fase di pubblicazioni o affidamento
			return 1L;
		} else if (situazioniLotti.contains(2L)) {
			// Aggiudicato
			return 2L;
		} else if (situazioniLotti.contains(3L)) {
			// Iniziato
			return 3L;
		} else if (situazioniLotti.contains(4L)) {
			// In esecuzione
			return 4L;
		} else if (situazioniLotti.contains(5L)) {
			// Sospeso
			return 5L;
		} else if (situazioniLotti.contains(6L)) {
			// Concluso in attesa di collaudo
			return 6L;
		} else {
			// Monitoraggio terminato
			return 7L;
		}
	}
	
	protected Long getSituazioneLotto(Long codGara, Long codLotto, GaraEntry gara, SqlMapper mapper) {

		Long numAppa = this.getNumAppa(codGara, codLotto, false, mapper);
		List<FaseEntry> fasi = mapper.getFasiLotto(codGara, codLotto, numAppa);
		List<Long> fasiInviate = mapper.getFasiInviate(codGara, codLotto, numAppa);
		
		String esitoProceduraString = mapper.getEsitoProcedura(codGara, codLotto);
		Long esitoProcedura = 0L;
		if(esitoProceduraString != null) {
			esitoProcedura =  Long.valueOf(esitoProceduraString);
		}
		
		LottoEntry lotto = mapper.getDettaglioLotto(codGara, codLotto);
		
		boolean saq = 9L == gara.getTipoApp() || 17L == gara.getTipoApp() || 18L == gara.getTipoApp();
		
		List<Long> esitiProcedura = new ArrayList<Long>();
		esitiProcedura.add(2L);
		esitiProcedura.add(3L);
		esitiProcedura.add(4L);
		esitiProcedura.add(6L);
		esitiProcedura.add(7L);
		
		// Annullato
		if (fasiInviate.contains(984L) && esitiProcedura.contains(esitoProcedura)) {
			return 8L;
		}

		// Sospeso
		if (fasiInviate.contains(6L)) {
			for (FaseEntry f : fasi) {
				if (f.getFase() == 6L) {
					FaseSospensioneEntry faseSospensione = mapper.getFaseSospensione(codGara, codLotto,
							f.getProgressivo());
					if (faseSospensione.getDataVerbRipr() == null) {
						return 5L;
					}
				}
			}
		}

		// Monitoraggio terminato

		if ((fasiInviate.contains(5L) || fasiInviate.contains(985L) || (fasiInviate.contains(4L) && saq))
				|| (lotto.getMasterCig() != null && !lotto.getMasterCig().equals(lotto.getCig()))) {
			return 7L;
		}

		// Concluso in attesa di collaudo o monitoraggio terminato se c'è una fase di
		// esecuzione con interruzione anticipata

		else if (fasiInviate.contains(4L)) {
			return 6L;
		}

		// In esecuzione

		else if (fasiInviate.contains(3L) || fasiInviate.contains(102L)) {
			return 4L;
		}

		// Iniziato

		else if (fasiInviate.contains(2L) || fasiInviate.contains(986L)|| fasiInviate.contains(11L)) {
			return 3L;
		}

		// Aggiudicato

		else if (fasiInviate.contains(1L) || fasiInviate.contains(987L) || fasiInviate.contains(12L)) {
			return 2L;
		}

		// In fase di pubblicazione o affidamento
		else
			return 1L;
	}
	
	public Long getNumAppa(Long codGara, Long codLotto, boolean isInsertFaseAggiudicazione, SqlMapper mapper) {
		Long maxNumAppa = mapper.getMaxNumAppa(codGara, codLotto);
		if (maxNumAppa == null) {
			return 1L;
		}
		return isInsertFaseAggiudicazione ? maxNumAppa + 1 : maxNumAppa;
	}
}
