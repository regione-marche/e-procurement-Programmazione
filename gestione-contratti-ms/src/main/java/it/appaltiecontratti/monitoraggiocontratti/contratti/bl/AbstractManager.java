package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.DatiIdSchedeW9FasiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseAggiudicazionePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FinanziamentiPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ImpresaAggiudicatariaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ImpresaFasePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.IncarichiProfPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.LegaleRappresentantePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;
import it.appaltiecontratti.sicurezza.bl.WConfigManager;

public abstract class AbstractManager {

	@Autowired
	protected ContrattiMapper contrattiMapper;

	@Autowired
	protected WGenChiaviManager wgcManager;
	
	@Autowired
	private SqlMapper sqlMapper;
	
	@Autowired
	protected WConfigManager wConfigManager;

	protected static final String ERROR_NUM_IMPRESE = "ERROR-NUM-IMPRESE";
	
	private final static Logger logger = LoggerFactory.getLogger(AbstractManager.class);
	
	public Long getSituazioneLotto(Long codGara, Long codLotto) {

		Long numAppa = this.getNumAppa(codGara, codLotto, false);
		List<FaseEntry> fasi = this.contrattiMapper.getFasiLotto(codGara, codLotto, numAppa);
		List<Long> fasiInviate = this.contrattiMapper.getFasiInviate(codGara, codLotto, numAppa);
		List<Long> esitoProceduraList = this.contrattiMapper.getEsitoProcedura(codGara, codLotto);
		Long esitoProcedura = null;
		if(esitoProceduraList != null && esitoProceduraList.size() > 0) {
			esitoProcedura = esitoProceduraList.get(0);
		}
		LottoBaseEntry lotto = this.contrattiMapper.getBaseDettaglioLotto(codGara, codLotto);
		GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
		
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
					FaseSospensioneEntry faseSospensione = this.contrattiMapper.getFaseSospensione(codGara, codLotto,
							f.getProgressivo());
					if (faseSospensione.getDataVerbRipr() == null) {
						return 5L;
					}
				}
			}
		}

		// Monitoraggio terminato

		if (fasiInviate.contains(19L) || (fasiInviate.contains(5L) || fasiInviate.contains(985L) || (fasiInviate.contains(4L) && saq))
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

		else if (fasiInviate.contains(2L) || fasiInviate.contains(986L) || fasiInviate.contains(11L) || fasiInviate.contains(13L)) {
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

	public Long getNumAppa(Long codGara, Long codLotto, boolean isInsertFaseAggiudicazione) {
		Long maxNumAppa = this.contrattiMapper.getMaxNumAppa(codGara, codLotto);
		if (maxNumAppa == null) {
			return 1L;
		}
		return isInsertFaseAggiudicazione ? maxNumAppa + 1 : maxNumAppa;
	}

	protected boolean hasAggiudicazione(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getAggiudicazione() != null;
		}
		return false;
	}

	protected boolean hasEsito(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getDatiComuni() != null) {
			return scheda.getDatiScheda().getDatiComuni().getESITOPROCEDURA() != null;
		}
		return false;
	}

	protected boolean hasAggiudicazioneSottosoglia(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getSottosoglia() != null;
		}
		return false;
	}

	protected boolean hasAggiudicazioneEsclusa(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getEscluso() != null;
		}
		return false;
	}

	protected boolean hasAdesione(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getAdesione() != null;
		}
		return false;
	}

	protected boolean hasInizio(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiInizio() != null;
		}
		return false;
	}

	protected boolean hasStipula(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiStipula() != null;
		}
		return false;
	}

	protected boolean hasAvanzamento(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiAvanzamenti() != null;
		}
		return false;
	}

	protected boolean hasConclusione(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiConclusione() != null;
		}
		return false;
	}

	protected boolean hasCollaudo(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiCollaudo() != null;
		}
		return false;
	}

	protected boolean hasRitardo(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiRitardi() != null;
		}
		return false;
	}

	protected boolean hasAccordoBonario(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiAccordi() != null;
		}
		return false;
	}

	protected boolean hasSospensioni(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiSospensioni() != null;
		}
		return false;
	}

	protected boolean hasVarianti(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiVarianti() != null;
		}
		return false;
	}

	protected boolean hasSubappalti(SchedaType scheda) {
		if (scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0) != null) {
			return scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiSubappalti() != null;
		}
		return false;
	}

	protected String getRequestFaseAggiudicazione(Long codGara, Long codLotto, Long num) throws Exception {
		FaseAggiudicazionePubbEntry risultato = new FaseAggiudicazionePubbEntry();

		try {
			Long numAppa = getNumAppa(codGara, codLotto, false);

			FaseAggiudicazioneEntry faseEsistente = this.contrattiMapper.getFaseAggiudicazione(codGara, codLotto,
					numAppa);
			DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
					new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA), numAppa);
			Long countAppalav = this.contrattiMapper.countAppalav(codGara, codLotto);
			if (faseEsistente != null) {
				risultato.setAltreSomme(faseEsistente.getAltreSomme());
				risultato.setAstaElettronica(faseEsistente.getAstaElettronica());
				risultato.setCodGara(codGara);
				risultato.setCodLotto(codLotto);
				risultato.setCodStrumento(faseEsistente.getCodStrumento());
				risultato.setCounter(faseEsistente.getCounter());
				risultato.setDataAtto(faseEsistente.getDataAtto());
				risultato.setDataInvito(faseEsistente.getDataInvito());
				risultato.setDataManifestInteresse(faseEsistente.getDataManifestInteresse());
				risultato.setDataScadenzaPresOfferta(faseEsistente.getDataScadenzaPresOfferta());
				risultato.setDataScadRichiestaInvito(faseEsistente.getDataScadRichiestaInvito());
				risultato.setDataVerbAggiudicazione(faseEsistente.getDataVerbAggiudicazione());
				risultato.setFlagImporti(faseEsistente.getFlagImporti());
				risultato.setFlagLivelloProgettuale(faseEsistente.getFlagLivelloProgettuale());
				risultato.setFlagRichSubappalto(faseEsistente.getFlagRichSubappalto());
				risultato.setFlagSicurezza(faseEsistente.getFlagSicurezza());
				risultato.setFlagSubappFinReg(faseEsistente.getFlagSubappFinReg());
				risultato.setIdIndizione(faseEsistente.getIdIndizione());
				risultato.setImpEsclusInsuffGiust(faseEsistente.getImpEsclusInsuffGiust());
				risultato.setImpNonAssog(faseEsistente.getImpNonAssog());
				risultato.setImportoAggiudicazione(faseEsistente.getImportoAggiudicazione());
				risultato.setImportoComplAppalto(faseEsistente.getImportoComplAppalto());
				risultato.setImportoComplIntervento(faseEsistente.getImportoComplIntervento());
				risultato.setImportoDisposizione(faseEsistente.getImportoDisposizione());
				risultato.setImportoForniture(faseEsistente.getImportoForniture());
				risultato.setImportoIva(faseEsistente.getImportoIva());
				risultato.setImportoLavori(faseEsistente.getImportoLavori());
				risultato.setImportoProgettazione(faseEsistente.getImportoProgettazione());
				risultato.setImportoServizi(faseEsistente.getImportoServizi());
				risultato.setImportoSicurezza(faseEsistente.getImportoSicurezza());
				risultato.setImportosubtotale(faseEsistente.getImportosubtotale());
				risultato.setIva(faseEsistente.getIva());
				risultato.setModalitaRiaggiudicazione(faseEsistente.getModalitaRiaggiudicazione());
				risultato.setNumeroAtto(faseEsistente.getNumeroAtto());
				risultato.setNumImpreseInvitate(faseEsistente.getNumImpreseInvitate());
				risultato.setNumImpreseOfferenti(faseEsistente.getNumImpreseOfferenti());
				risultato.setNumImpreseRichiedenti(faseEsistente.getNumImpreseRichiedenti());
				risultato.setNumManifestInteresse(faseEsistente.getNumManifestInteresse());
				risultato.setNumOfferteAmmesse(faseEsistente.getNumOfferteAmmesse());
				risultato.setNumOfferteEscluse(faseEsistente.getNumOfferteEscluse());
				risultato.setNumOfferteFuoriSoglia(faseEsistente.getNumOfferteFuoriSoglia());
				risultato.setOffertaMassima(faseEsistente.getOffertaMassima());
				risultato.setOffertaMinima(faseEsistente.getOffertaMinima());
				risultato.setOpereUrbanizScomputo(faseEsistente.getOpereUrbanizScomputo());
				risultato.setPercentRibassoAgg(faseEsistente.getPercentRibassoAgg());
				risultato.setPercOffAumento(faseEsistente.getPercOffAumento());
				risultato.setPreinformazione(faseEsistente.getPreinformazione());
				risultato.setProceduraAcc(faseEsistente.getProceduraAcc());
				risultato.setRequisitiSettSpec(faseEsistente.getRequisitiSettSpec());
				risultato.setTermineRidotto(faseEsistente.getTermineRidotto());
				risultato.setTipoAtto(faseEsistente.getTipoAtto());
				risultato.setValoreSogliaAnomalia(faseEsistente.getValoreSogliaAnomalia());
				risultato.setVerificaCampione(faseEsistente.getVerificaCampione());
				risultato.setCodCui(faseEsistente.getCodCui());
				risultato.setCountAppalav(countAppalav);
				if (idSchede != null) {
					if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
						risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
					}
					if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
						risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
					}
				}

				List<FinanziamentiPubbEntry> finanziamenti = this.contrattiMapper.getFinanziamentiFasePubb(codGara,
						codLotto, num);

				List<String> sezioni = getSezioneIncaricoByFase(1L);
				List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();
				risultato.setFlagRelazioneUnica(faseEsistente.getFlagRelazioneUnica());

				for (String sezione : sezioni) {
					List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper
							.getIncarichiFasePubb(codGara, codLotto, num, sezione);
					if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
						incarichi.addAll(incarichiPerSezione);
					}
				}

				List<ImpresaAggiudicatariaPubbEntry> impreseAgg = this.contrattiMapper.getImpAggFasePubb(codGara,
						codLotto, numAppa);

				for (IncarichiProfPubbEntry i : incarichi) {
					if (i.getCodiceTecnico() != null) {
						i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
					}
				}

				for (ImpresaAggiudicatariaPubbEntry i : impreseAgg) {
					if (i.getCodImpresa() != null) {
						i.setImpresa(this.getImpresaForRequestFase(i.getCodImpresa()));
					}
					if (i.getCodImpAus() != null) {
						i.setImpresaAusiliaria(this.getImpresaForRequestFase(i.getCodImpAus()));
					}
				}

				risultato.setFinanziamenti(finanziamenti);
				risultato.setImpreseAggiudicatarie(impreseAgg);
				risultato.setIncarichiProfessionali(incarichi);

			}
			return new ObjectMapper().writeValueAsString(risultato);
		} catch (JsonProcessingException e) {
			throw e;
		}
	}

	protected ImpresaFasePubbEntry getImpresaForRequestFase(String codImpresa) {

		ImpresaFasePubbEntry impresa = this.contrattiMapper.getImpresaForRequestFase(codImpresa);
		if (impresa == null) {
			return null;
		}
		LegaleRappresentantePubbEntry legale = this.contrattiMapper.getLegaleImpresaPubb(codImpresa);
		impresa.setRappresentante(legale);
		return impresa;
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

	protected ImpresaEntry setImpresaNomImp(ImpresaEntry impresa) {
		if (impresa.getNomImp() == null && StringUtils.isNotBlank(impresa.getRagioneSociale())) {
			String nomImp = impresa.getRagioneSociale().length() > 61 ? impresa.getRagioneSociale().substring(0, 61)
					: impresa.getRagioneSociale();
			impresa.setNomImp(nomImp);
		}
		return impresa;
	}
	
	public void insertLogEventi(Long syscon, String codProfilo, String codEvento, String descr, String errmsg, Short livento, String oggetto) {
		try {
			
			LogEventiEntry entry = new LogEventiEntry();
			entry.setIdevento(wgcManager.getNextId("W_LOGEVENTI"));
			entry.setCodapp("W9");
			entry.setSyscon(syscon);
			entry.setCodProfilo(codProfilo != null ? codProfilo : "");
			entry.setDataora(new Date());
			entry.setCodevento(codEvento);
			entry.setDescr(descr);
			entry.setErrmsg(errmsg);
			entry.setLivevento(livento);
			entry.setOggevento(oggetto);
			contrattiMapper.insertLogEventi(entry);
		}catch (Exception e) {
			throw e;
			
		}
	}
	
	public String calcolaCodificaAutomatica(String entita, String campoChiave) throws Exception {
		String codice = "1";
		String formatoCodice = null;
		String codcal = null;
		Long cont = null;
		try {
			String query = "select CODCAL, CONTAT from G_CONFCOD where NOMENT = '" + entita + "'";
			List<Map<String, Object>> confcod = sqlMapper.select(query);
			if (confcod != null && confcod.size() > 0) {
				for (Map<String, Object> row : confcod) {
					if (row.containsKey("CODCAL")) {
						codcal = row.get("CODCAL").toString();
					} else {
						codcal = row.get("codcal").toString();
					}
					if (row.containsKey("CONTAT")) {
						cont = new Long(row.get("CONTAT").toString());
					} else {
						cont = new Long(row.get("contat").toString());
					}
					break;
				}
				boolean codiceUnivoco = false;
				int numeroTentativi = 0;
				StringBuffer strBuffer = null;
				long tmpContatore = cont.longValue();
				while (!codiceUnivoco && numeroTentativi < 5) {
					strBuffer = new StringBuffer("");
					// Come prima cosa eseguo l'update del contatore
					tmpContatore++;
					sqlMapper.execute(
							"update G_CONFCOD set contat = " + tmpContatore + " where NOMENT = '" + entita + "'");

					strBuffer = new StringBuffer("");
					formatoCodice = codcal;
					while (formatoCodice.length() > 0) {
						switch (formatoCodice.charAt(0)) {
						case '<': // Si tratta di un'espressione numerica
							String strNum = formatoCodice.substring(1, formatoCodice.indexOf('>'));
							if (strNum.charAt(0) == '0') {
								// Giustificato a destra
								for (int i = 0; i < (strNum.length() - String.valueOf(tmpContatore).length()); i++)
									strBuffer.append('0');
							}
							strBuffer.append(String.valueOf(tmpContatore));

							formatoCodice = formatoCodice.substring(formatoCodice.indexOf('>') + 1);
							break;
						case '"': // Si tratta di una parte costante
							strBuffer.append(formatoCodice.substring(1, formatoCodice.indexOf('"', 1)));
							formatoCodice = formatoCodice.substring(formatoCodice.indexOf('"', 1) + 1);
							break;
						}
					}
					int occorrenze = sqlMapper
							.count(entita + " WHERE " + campoChiave + " ='" + strBuffer.toString() + "'");
					if (occorrenze == 0) {
						codiceUnivoco = true;
						codice = strBuffer.toString();
					} else {
						numeroTentativi++;
					}
				}
				if (!codiceUnivoco) {
					logger.error("numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
					throw new Exception(
							"numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
				}
			}
		} catch (Exception ex) {
			logger.error("Errore inaspettato durante il calcolo per la codifica automatica " + entita, ex);
			throw new Exception(ex);
		}
		return codice;
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
}
