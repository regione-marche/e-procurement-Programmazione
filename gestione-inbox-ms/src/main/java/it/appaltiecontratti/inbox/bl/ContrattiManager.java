package it.appaltiecontratti.inbox.bl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.inbox.entity.ImpresaEntry;
import it.appaltiecontratti.inbox.entity.TabellatoEntry;
import it.appaltiecontratti.inbox.entity.contratti.AggiudicatarioEntry;
import it.appaltiecontratti.inbox.entity.contratti.AllegatoAttiEntry;
import it.appaltiecontratti.inbox.entity.contratti.AppaFornEntry;
import it.appaltiecontratti.inbox.entity.contratti.AppaLavEntry;
import it.appaltiecontratti.inbox.entity.contratti.AttoDocument;
import it.appaltiecontratti.inbox.entity.contratti.CPVSecondario;
import it.appaltiecontratti.inbox.entity.contratti.CategoriaLotto;
import it.appaltiecontratti.inbox.entity.contratti.CategoriaLottoEntry;
import it.appaltiecontratti.inbox.entity.contratti.CentriCostoEntry;
import it.appaltiecontratti.inbox.entity.contratti.CpvLottoEntry;
import it.appaltiecontratti.inbox.entity.contratti.DatiGeneraliTecnicoEntry;
import it.appaltiecontratti.inbox.entity.contratti.DettaglioAttoEntry;
import it.appaltiecontratti.inbox.entity.contratti.GaraFullEntry;
import it.appaltiecontratti.inbox.entity.contratti.ImpresaAggiudicatariaAttoEntry;
import it.appaltiecontratti.inbox.entity.contratti.ImpresaPubbEntry;
import it.appaltiecontratti.inbox.entity.contratti.LottoDynamicForPublish;
import it.appaltiecontratti.inbox.entity.contratti.LottoDynamicValue;
import it.appaltiecontratti.inbox.entity.contratti.LottoFullEntry;
import it.appaltiecontratti.inbox.entity.contratti.MotivazioneProceduraNegoziataEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicaAttoEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicaGaraEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicaLottoEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicazioneBandoEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicitaGaraEntry;
import it.appaltiecontratti.inbox.entity.contratti.RupEntry;
import it.appaltiecontratti.inbox.mapper.ContrattiMapper;

@Component(value = "contrattiManager")
public class ContrattiManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(ContrattiManager.class);


	@Autowired
	private ContrattiMapper contrattiMapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PubblicaAttoEntry valorizzaAtto(Long codGara, Long numeroPubblicazione, final String fileAllegatoAttiUrl) {

		PubblicaAttoEntry attoRequest = new PubblicaAttoEntry();
		try {
			DettaglioAttoEntry atto = this.contrattiMapper.getDettaglioAtto(codGara, numeroPubblicazione);
			if (atto == null) {
				return null;
			}
			PubblicaGaraEntry garaPubblicazione = valorizzaGara(codGara);
			if (garaPubblicazione != null) {
				attoRequest.setClientId("SitatSA");
				attoRequest.setDataAggiudicazione(atto.getDataVerbAggiudicazione());
				attoRequest.setDataDecreto(atto.getDataDecreto());
				attoRequest.setDataProvvedimento(atto.getDataProvvedimento());
				attoRequest.setDataPubblicazione(atto.getDataPubb());
				attoRequest.setDataScadenza(atto.getDataScad());
				attoRequest.setDataStipula(atto.getDataStipula());
				// attoRequest.setAggiudicatari(aggiudicatari);
				attoRequest.setEventualeSpecificazione(atto.getDescriz());
				attoRequest.setGara(garaPubblicazione);
				attoRequest.setIdGara(atto.getCodGara());
				attoRequest.setIdRicevuto(atto.getIdRicevuto());
				attoRequest.setImportoAggiudicazione(atto.getImportoAggiudicazione());
				attoRequest.setNumeroProvvedimento(atto.getNumProvvedimento());
				attoRequest.setNumeroPubblicazione(atto.getNumPubb());
				attoRequest.setNumeroRepertorio(atto.getNumRepertorio());
				attoRequest.setOffertaAumento(atto.getPercOffAumento());
				attoRequest.setRibassoAggiudicazione(atto.getPercRibassoAgg());
				attoRequest.setTipoDocumento(atto.getTipDoc());
				attoRequest.setUrlCommittente(atto.getUrlCommittente());
				attoRequest.setUrlEProcurement(atto.getUrlEproc());
				List<AllegatoAttiEntry> docs = mappingAttoDocuments(codGara, numeroPubblicazione, fileAllegatoAttiUrl);
				attoRequest.setDocumenti(docs);

				List<ImpresaAggiudicatariaAttoEntry> impAggiudicatarie = this.contrattiMapper
						.getImpreseAggiudicatarieAtto(codGara, numeroPubblicazione);
				List<AggiudicatarioEntry> aggiudicatari = mappingAggiudicatari(impAggiudicatarie);
				attoRequest.setAggiudicatari(aggiudicatari);

			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati atto per la pubblicazione. codgara = " + codGara
					+ " num pubb= " + numeroPubblicazione, e);
			attoRequest = null;
		}
		return attoRequest;

	}

	private List<AllegatoAttiEntry> mappingAttoDocuments(Long codGara, Long numPubb, final String fileAllegatoAttiUrl) {

		List<AllegatoAttiEntry> requestDocumenti = new ArrayList<AllegatoAttiEntry>();
		List<AttoDocument> documenti = this.contrattiMapper.getAttoDocuments(codGara, numPubb);

		for (AttoDocument doc : documenti) {
			AllegatoAttiEntry docRequest = new AllegatoAttiEntry();

			if (StringUtils.isNotBlank(doc.getUrl())) {
				docRequest.setUrl(doc.getUrl());
			} else {
				String replacedFileAllegatoAttiUrl = new String(fileAllegatoAttiUrl);
				replacedFileAllegatoAttiUrl = replacedFileAllegatoAttiUrl.replace("<CODGARA>", String.valueOf(codGara))
						.replace("<NUM_PUBB>", String.valueOf(doc.getNumPubb()))
						.replace("<NUMDOC>", String.valueOf(doc.getNumDoc()));
				docRequest.setUrl(replacedFileAllegatoAttiUrl);
			}

			docRequest.setIdGara(doc.getCodGara());
			docRequest.setNrDoc(doc.getNumDoc());
			docRequest.setNrPubblicazione(doc.getNumPubb());
			docRequest.setTitolo(doc.getTitolo());
			requestDocumenti.add(docRequest);

		}
		return requestDocumenti;

	}

	private List<AggiudicatarioEntry> mappingAggiudicatari(List<ImpresaAggiudicatariaAttoEntry> impAggiudicatarie) {

		List<AggiudicatarioEntry> risultato = new ArrayList<AggiudicatarioEntry>();
		if (impAggiudicatarie == null) {
			return null;
		}
		for (ImpresaAggiudicatariaAttoEntry imp : impAggiudicatarie) {
			AggiudicatarioEntry agg = new AggiudicatarioEntry();

			agg.setCodiceImpresa(imp.getCodImpresa());
			agg.setIdGruppo(imp.getIdGruppo());
			agg.setNumeroAggiudicatario(imp.getNumAggi());
			agg.setNumeroPubblicazione(imp.getNumPubb());
			agg.setRuolo(imp.getRuolo());
			agg.setTipoAggiudicatario(imp.getIdTipoAgg());
			ImpresaEntry impresa = this.contrattiMapper.getImpresa(imp.getCodImpresa());
			if (impresa != null) {
				ImpresaPubbEntry impPubb = new ImpresaPubbEntry();
				impPubb.setCap(impresa.getCap());
				impPubb.setCodiceFiscale(impresa.getCodiceFiscale());
				impPubb.setCodiceSA(impresa.getStazioneAppaltante());
				impPubb.setFax(impresa.getFax());
				impPubb.setIndirizzo(impresa.getIndirizzo());
				impPubb.setLocalita(impresa.getComune());
				impPubb.setNumeroCCIAA(impresa.getCodiceInail());
				impPubb.setNumeroCivico(impresa.getNumeroCivico());
				impPubb.setPartitaIva(impresa.getPartitaIva());
				impPubb.setProvincia(impresa.getProvincia());
				String ragioneSociale = impresa.getRagioneSociale();
				if (StringUtils.isNotBlank(ragioneSociale)) {
					if (ragioneSociale.length() > 61) {
						ragioneSociale = ragioneSociale.substring(0, 61);
					}
					impPubb.setRagioneSociale(ragioneSociale);
				}
				impPubb.setTelefono(impresa.getTelefono());
				impPubb.setNazione(getCodiceNazioneByNumber(impresa.getNazione()));
				agg.setImpresa(impPubb);
			}
			risultato.add(agg);
		}
		return risultato;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PubblicaGaraEntry valorizzaGara(Long codGara) throws Exception {
		PubblicaGaraEntry garaRequest = new PubblicaGaraEntry();
		try {
			GaraFullEntry gara = this.dettaglioGaraCompleto(codGara);

			if (gara == null) {
				logger.error("Errore inaspettato in lettura dati gara per la pubblicazione. La gara " + codGara
						+ "non esiste.");
				return null;
			}
			garaRequest.setAltreSA(gara.getAltreSA());
			// garaRequest.setAtti(atti);
			garaRequest.setCentraleCommittenza(gara.getFlagStipula() == null ? null : "" + gara.getFlagStipula());
			garaRequest.setCentroCosto(gara.getNominativoCentroCosto());
			garaRequest.setCfAgente(gara.getCfAgenteStazioneAppaltante());
			garaRequest.setCigAccQuadro(gara.getCigQuadro());
			garaRequest.setClientId("SitatSA");
			garaRequest.setCodiceCentroCosto(gara.getCodiceCentroCosto());

			String cfSA = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
			garaRequest.setCodiceFiscaleSA(cfSA);
			garaRequest.setComune(gara.getComuneSede());
			garaRequest.setCriteriAmbientali(gara.getDispArtDlgs() == null ? null : "" + gara.getDispArtDlgs());
			garaRequest.setDataPerfezionamentoBando(gara.getDataPubblicazione());
			garaRequest.setDataPubblicazione(gara.getDataPubblicazione());
			garaRequest.setDataScadenza(gara.getDataScadenza());
			garaRequest.setDurataAccordoQuadro(gara.getDurataAcquadro());
			garaRequest.setId(gara.getCodgara());
			garaRequest.setIdAnac(gara.getIdentificativoGara());
			garaRequest.setIdCentroCosto(gara.getIdCentroDiCosto());
			garaRequest.setIdEnte(gara.getCodiceStazioneAppaltante());
			garaRequest.setIdRup(gara.getCodiceTecnico());
			garaRequest.setIdUfficio(gara.getIdUfficio());
			garaRequest.setImportoGara(gara.getImportoGara());
			garaRequest.setIndirizzo(gara.getIndirizzoSede());
			garaRequest.setModoIndizione(gara.getModalitaIndizione());
			garaRequest.setNomeSA(gara.getDenomSoggStazioneAppaltante());
			garaRequest.setOggetto(gara.getOggetto());
			garaRequest.setProvenienzaDato(gara.getProvenienzaDato());
			garaRequest.setProvincia(gara.getProvinciaSede());
			garaRequest.setRealizzazione(gara.getTipoApp());
			garaRequest.setRicostruzioneAlluvione(
					gara.getRicostruzioneAlluvione() == null ? null : "" + gara.getRicostruzioneAlluvione());
			garaRequest.setSaAgente(gara.getFlagSaAgente() == null ? null : "" + gara.getFlagSaAgente());
			garaRequest.setSettore(gara.getTipoSettore());
			garaRequest.setSisma(gara.getSisma() == null ? null : "" + gara.getSisma());
			garaRequest.setSituazione(gara.getSituazione());
			garaRequest.setSommaUrgenza(gara.getSommaUrgenza() == null ? null : "" + gara.getSommaUrgenza());
			garaRequest.setSyscon(gara.getSyscon());
			if (gara.getTecnico() != null) {
				garaRequest.setTecnicoRup(mappingRup(gara.getTecnico()));
			}
			garaRequest.setTipoProcedura(
					gara.getTipologiaProcedura() == null ? null : Long.valueOf(gara.getTipologiaProcedura()));
			garaRequest.setTipoSA(gara.getTipologiaStazioneAppaltante());
			garaRequest.setUfficio(gara.getNominativoUfficio());
			garaRequest.setVersioneSimog(gara.getVersioneSimog());
			garaRequest.setIdRicevuto(gara.getIdRicevuto());

			List<PubblicaLottoEntry> lotti = listaLottiCompleto(codGara);
			garaRequest.setLotti(lotti);

			PubblicazioneBandoEntry pubblicazioneBando = getPubblicitaGaraForPublish(codGara);
			garaRequest.setPubblicazioneBando(pubblicazioneBando);

		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati gara per la pubblicazione. codgara = " + codGara, e);
			throw e;
		}
		return garaRequest;
	}

	private GaraFullEntry dettaglioGaraCompleto(Long codGara) {

		GaraFullEntry gara = new GaraFullEntry();
		try {
			// Estrazione gara
			gara = this.contrattiMapper.dettaglioGaraCompleto(codGara);
			// Estrazione Tecnico
			if (gara.getCodiceTecnico() != null) {
				RupEntry tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
				gara.setTecnico(tecnico);
			}
			// Estrazione ufficio
			if (gara.getIdUfficio() != null) {
				String nominativoUfficio = this.contrattiMapper.getNomeUfficio(gara.getIdUfficio());
				gara.setNominativoUfficio(nominativoUfficio);
			}
			// Estrazione centri di costo e stazione appaltante
			if (gara.getIdCentroDiCosto() != null && gara.getCodiceStazioneAppaltante() != null) {
				CentriCostoEntry infocdc = this.contrattiMapper.getCentroCosto(gara.getIdCentroDiCosto(),
						gara.getCodiceStazioneAppaltante());
				if (infocdc != null) {
					gara.setNominativoCentroCosto(infocdc.getNominativoCDC());
					gara.setCodiceCentroCosto(infocdc.getCodiceCDC());
					gara.setCentroDicosto(infocdc);
				}
				gara.setNominativoStazioneAppaltante(
						this.contrattiMapper.getNominatioSa(gara.getCodiceStazioneAppaltante()));
			}

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione del dettaglio della gara: " + codGara, t);
			return null;
		}
		return gara;

	}

	private DatiGeneraliTecnicoEntry mappingRup(RupEntry rup) {
		DatiGeneraliTecnicoEntry risultato = new DatiGeneraliTecnicoEntry();
		risultato.setCap(rup.getCap());
		risultato.setCfPiva(rup.getCf());
		risultato.setCivico(rup.getNumCivico());
		risultato.setCodice(rup.getCodice());
		risultato.setCodiceSA(rup.getStazioneAppaltante());
		risultato.setCognome(rup.getCognome());
		risultato.setFax(rup.getFax());
		risultato.setIndirizzo(rup.getIndirizzo());
		risultato.setLocalita(rup.getProvincia());
		risultato.setLuogoIstat(rup.getCodIstat());
		risultato.setMail(rup.getEmail());
		risultato.setNome(rup.getNome());
		risultato.setNomeCognome(rup.getNominativo());
		risultato.setProvincia(rup.getProvincia());
		risultato.setTelefono(rup.getTelefono());
		return risultato;
	}

	private List<PubblicaLottoEntry> listaLottiCompleto(Long codGara) {
		List<PubblicaLottoEntry> risultato = new ArrayList<PubblicaLottoEntry>();
		try {

			List<LottoFullEntry> lotti = this.contrattiMapper.getFullLottiGara(codGara);
			for (LottoFullEntry lotto : lotti) {

				Long codLotto = Long.valueOf(lotto.getCodLotto());

				PubblicaLottoEntry lottoForPublish = mappingLotto(lotto);
				// Reperisco le collezioni dinamiche associate al lotto e corrispondenti a
				// valori di SI/NO associate ai tabellati
				List<LottoDynamicValue> modalitaAcq = getModalitaAcquisizione(codGara, codLotto);
				List<LottoDynamicValue> modalitaTip = getModalitaTipologiaLavoro(codGara, codLotto);
				List<LottoDynamicValue> condizioniRicorso = getCondizioniRicorso(codGara, codLotto);
				List<CPVSecondario> cpvSecondari = this.contrattiMapper.getCpvSecondariLotto(codGara, codLotto);
				List<CategoriaLotto> categorieLotto = this.contrattiMapper.getCategorieLotto(codGara, codLotto);
				lotto.setModalitaAcquisizione(modalitaAcq);
				lotto.setModalitaTipologiaLavoro(modalitaTip);
				lotto.setCondizioniRicorsoProcNeg(condizioniRicorso);
				lotto.setCpvSecondari(cpvSecondari);
				lotto.setUlterioriCategorie(categorieLotto);
				lottoForPublish.setCpvSecondari(mappingCpvSecondari(codGara, codLotto, cpvSecondari));
				lottoForPublish
						.setModalitaAcquisizioneForniture(mappingModalitaAcquisizioneForniture(codGara, codLotto));
				lottoForPublish.setTipologieLavori(mappingTipologieLavori(codGara, codLotto));
				lottoForPublish.setMotivazioniProceduraNegoziata(mappingModalitaRicorso(codGara, codLotto));
				lottoForPublish.setCategorie(mappingUlterioriCategorie(codGara, codLotto));
				risultato.add(lottoForPublish);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio di lotti per la pubblicazione: " + codGara,
					t);
			return null;
		}
		return risultato;
	}

	private PubblicaLottoEntry mappingLotto(LottoFullEntry lottoEntry) {

		PubblicaLottoEntry lottoForPublish = new PubblicaLottoEntry();

		lottoForPublish.setCategoria(lottoEntry.getCategoriaPrev());
		lottoForPublish.setCig(lottoEntry.getCig());
		lottoForPublish.setClasse(lottoEntry.getClasseCategoriaPrev());
		lottoForPublish.setCodiceIntervento(lottoEntry.getCodiceInterno());
		lottoForPublish.setContrattoEsclusoArt16e17e18(lottoEntry.getContrattoEscluso161718());
		lottoForPublish.setContrattoEsclusoArt19e26(lottoEntry.getContrattoEscluso());
		lottoForPublish.setCpv(lottoEntry.getCpv());
		lottoForPublish.setCriterioAggiudicazione(lottoEntry.getCriteriAggiudicazione());
		lottoForPublish.setCui(lottoEntry.getCui());
		lottoForPublish.setCup(lottoEntry.getCup());
		lottoForPublish.setCupEsente(lottoEntry.getEsenteCup());
		lottoForPublish.setIdGara(Long.valueOf(lottoEntry.getCodgara()));
		lottoForPublish.setIdLotto(Long.valueOf(lottoEntry.getCodLotto()));
		lottoForPublish.setIdRup(lottoEntry.getRup());
		lottoForPublish.setIdSceltaContraente(lottoEntry.getSceltaContraente());
		lottoForPublish.setIdSceltaContraente50(lottoEntry.getSceltaContraenteLgs());
		lottoForPublish.setImportoLotto(lottoEntry.getImportoNetto());
		lottoForPublish.setImportoSicurezza(lottoEntry.getImportoSicurezza());
		lottoForPublish.setImportoTotale(lottoEntry.getImportoTotale());
		lottoForPublish.setLuogoIstat(lottoEntry.getLuogoIstat());
		lottoForPublish.setLuogoNuts(lottoEntry.getLuogoNuts());
		lottoForPublish.setManodopera(lottoEntry.getManodopera());
		lottoForPublish.setNumeroLotto(lottoEntry.getNumLotto() == null ? 1L : lottoEntry.getNumLotto());
		lottoForPublish.setOggetto(lottoEntry.getOggetto());
		lottoForPublish.setPrestazioniComprese(lottoEntry.getPrestazioneComprese());
		lottoForPublish.setSettore(lottoEntry.getTipoSettore());
		lottoForPublish.setSommaUrgenza(lottoEntry.getSommaUrgenza());
		if (lottoEntry.getRup() != null) {

			RupEntry tecnico = this.contrattiMapper.getTecnico(lottoEntry.getRup());
			if (tecnico != null) {
				lottoForPublish.setTecnicoRup(mappingRup(tecnico));
			}
		}
		lottoForPublish.setTipoAppalto(lottoEntry.getTipologia());
		lottoForPublish.setUrlCommittente(lottoEntry.getUrlCommittente());
		lottoForPublish.setUrlPiattaformaTelematica(lottoEntry.getUrlEproc());
		lottoForPublish.setSettore(lottoEntry.getTipoSettore());

		return lottoForPublish;

	}

	private List<LottoDynamicValue> getModalitaAcquisizione(Long codGara, Long numLotto) {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			// Trovo la lista completa del tabellato
			List<TabellatoEntry> tabellatoModAcq = this.contrattiMapper.getModalitaAcquisizioneTab();
			// Cerco le chiavi del tabellato con valore SI
			List<Long> codiciModAcq = this.contrattiMapper.getModalitaAcquisizioneLotto(codGara, numLotto);
			// Preparo la lista di valori tabellati con value = 1 (SI) associato ai codici
			// trovati nella precedente chiamata
			for (TabellatoEntry e : tabellatoModAcq) {
				if (e.getArchiviato() == null || e.getArchiviato() != 1L) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciModAcq.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}
		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio del lotto - modalita acquisizione: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;
	}

	private List<LottoDynamicValue> getModalitaTipologiaLavoro(Long codGara, Long numLotto) {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			// Trovo la lista completa del tabellato
			List<TabellatoEntry> tabellatoModTip = this.contrattiMapper.getModalitaTipologiaLavoroTab();
			// Cerco le chiavi del tabellato con valore SI
			List<Long> codiciModTip = this.contrattiMapper.getModalitaTipologiaLavoroLotto(codGara, numLotto);
			// Preparo la lista di valori tabellati con value = 1 (SI) associato ai codici
			// trovati nella precedente chiamata
			for (TabellatoEntry e : tabellatoModTip) {
				if (e.getArchiviato() == null || e.getArchiviato() != 1L) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciModTip.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio del lotto - modalita tipologia lavoro: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;

	}

	private List<LottoDynamicValue> getCondizioniRicorso(Long codGara, Long numLotto) {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			// Trovo la lista completa del tabellato
			List<TabellatoEntry> tabellatoCondizioni = this.contrattiMapper.getCondizioniRicorsoTab();
			// Cerco le chiavi del tabellato con valore SI
			List<Long> codiciCondizioni = this.contrattiMapper.getModalitaCondizioniRicorsoLotto(codGara, numLotto);
			// Preparo la lista di valori tabellati con value = 1 (SI) associato ai codici
			// trovati nella precedente chiamata
			for (TabellatoEntry e : tabellatoCondizioni) {
				LottoDynamicValue v = new LottoDynamicValue();
				v.setCodice(e.getCodice());
				v.setDescrizione(e.getDescrizione());
				v.setValue(codiciCondizioni.contains(e.getCodice()) ? 1L : 2L);
				risultato.add(v);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio del lotto - modalita condizioni ricorso: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;

	}

	private List<CpvLottoEntry> mappingCpvSecondari(Long codGara, Long codLotto, List<CPVSecondario> cpvSecondari) {

		List<CpvLottoEntry> risultato = new ArrayList<CpvLottoEntry>();
		Long counter = 1L;
		for (CPVSecondario cpvSecondario : cpvSecondari) {
			CpvLottoEntry cpvLottoEntry = new CpvLottoEntry();

			cpvLottoEntry.setCpv(cpvSecondario.getDescCpv());
			cpvLottoEntry.setIdGara(codGara);
			cpvLottoEntry.setIdLotto(codLotto);
			cpvLottoEntry.setNumCpv(counter + "");
			cpvLottoEntry.setCpv(cpvSecondario.getCodCpv());
			counter++;

			risultato.add(cpvLottoEntry);
		}
		return risultato;
	}

	private List<AppaFornEntry> mappingModalitaAcquisizioneForniture(Long codGara, Long codLotto) {

		List<LottoDynamicForPublish> modalitaAcq = this.contrattiMapper.getFullModalitaAcquisizioneLotto(codGara,
				codLotto);
		List<AppaFornEntry> risultato = new ArrayList<AppaFornEntry>();
		for (LottoDynamicForPublish modalita : modalitaAcq) {
			AppaFornEntry appaForn = new AppaFornEntry();
			appaForn.setIdGara(codGara);
			appaForn.setIdLotto(codLotto);
			appaForn.setModalitaAcquisizione(modalita.getNumAppalto());
			appaForn.setNumAppaForn(modalita.getNum());

			risultato.add(appaForn);
		}
		return risultato;

	}

	private PubblicazioneBandoEntry getPubblicitaGaraForPublish(Long codGara) {
		PubblicazioneBandoEntry risultato = new PubblicazioneBandoEntry();

		PubblicitaGaraEntry pubblicita = this.contrattiMapper.getPubblicitaGara(codGara);
		if (pubblicita != null) {
			risultato.setDataAlbo(pubblicita.getAlboPretorioComuni());
			risultato.setDataBore(pubblicita.getDataBollettino());
			risultato.setDataGuce(pubblicita.getGazzettaUffEuropea());
			risultato.setDataGuri(pubblicita.getGazzettaUffItaliana());
			risultato.setIdGara(codGara);
			risultato.setPeriodici(pubblicita.getNumPeriodici());
			risultato.setProfiloCommittente(pubblicita.getProfiloCommittente());
			risultato.setProfiloInfTrasp(pubblicita.getSitoMinisteriInfr());
			risultato.setProfiloOsservatorio(pubblicita.getSitoOsservatorioContratti());
			risultato.setQuotidianiLocali(pubblicita.getNumQuotidianiLocali());
			risultato.setQuotidianiNazionali(pubblicita.getNumQuotidianiNazionali());
		}
		return risultato;
	}

	private List<AppaLavEntry> mappingTipologieLavori(Long codGara, Long codLotto) {
		List<LottoDynamicForPublish> tipologieLavori = this.contrattiMapper.getFullTipologiaLavoriLotto(codGara,
				codLotto);
		List<AppaLavEntry> risultato = new ArrayList<AppaLavEntry>();
		for (LottoDynamicForPublish tipologia : tipologieLavori) {
			AppaLavEntry tipLavori = new AppaLavEntry();
			tipLavori.setIdGara(codGara);
			tipLavori.setIdLotto(codLotto);
			tipLavori.setNumAppaLav(tipologia.getNum());
			tipLavori.setTipologiaLavoro(tipologia.getNumAppalto());
			risultato.add(tipLavori);
		}
		return risultato;
	}

	private List<MotivazioneProceduraNegoziataEntry> mappingModalitaRicorso(Long codGara, Long codLotto) {

		List<LottoDynamicForPublish> condizioniricorso = this.contrattiMapper.getFullCondizioniRicorsoLotto(codGara,
				codLotto);
		List<MotivazioneProceduraNegoziataEntry> risultato = new ArrayList<MotivazioneProceduraNegoziataEntry>();
		for (LottoDynamicForPublish condizione : condizioniricorso) {
			MotivazioneProceduraNegoziataEntry motivazione = new MotivazioneProceduraNegoziataEntry();
			motivazione.setIdGara(codGara);
			motivazione.setIdLotto(codLotto);
			motivazione.setNumCondizione(condizione.getNum());
			motivazione.setCondizione(condizione.getNumAppalto());

			risultato.add(motivazione);
		}
		return risultato;
	}

	private List<CategoriaLottoEntry> mappingUlterioriCategorie(Long codGara, Long codLotto) {

		List<CategoriaLotto> categorie = this.contrattiMapper.getCategorieLotto(codGara, codLotto);
		List<CategoriaLottoEntry> risultato = new ArrayList<CategoriaLottoEntry>();
		for (CategoriaLotto categoria : categorie) {
			CategoriaLottoEntry catLotto = new CategoriaLottoEntry();
			catLotto.setIdGara(codGara);
			catLotto.setIdLotto(codLotto);
			catLotto.setCategoria(categoria.getCategoria());
			catLotto.setClasse(categoria.getClasse());
			catLotto.setNumCategoria(categoria.getNum());
			catLotto.setScorporabile(categoria.getScorporabile());
			catLotto.setSubappaltabile(categoria.getSubappaltabile());
			risultato.add(catLotto);
		}
		return risultato;
	}

	public void updateIdRicevutoAtto(Long idRicevuto, Long codiceGara, Long numPubb) {
		this.contrattiMapper.updateIdRicevutoAtto(idRicevuto, codiceGara, numPubb);
	}

	public void updateIdRicevutoGara(Long idRicevuto, Long codiceGara) {
		this.contrattiMapper.updateIdRicevutoGara(idRicevuto, codiceGara);
	}

	private String getCodiceNazioneByNumber(final Long nazioneNumber) {
		logger.debug("Execution start::getCodiceNazioneByNumber");

		String codiceNazione = null;

		if (nazioneNumber != null) {
			codiceNazione = nazioneNumber == 1L ? "IT" : this.contrattiMapper.getCodiceNazioneByNumber(nazioneNumber);
		}

		logger.debug("Execution end::getCodiceNazioneByNumber");

		return codiceNazione;
	}

}
