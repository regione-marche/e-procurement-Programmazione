package it.appaltiecontratti.inbox.bl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.inbox.bl.impl.SimogGatewayRestClientManager;
import it.appaltiecontratti.inbox.common.Constants;
import it.appaltiecontratti.inbox.common.CostantiW9;
import it.appaltiecontratti.inbox.common.StatoComunicazione;
import it.appaltiecontratti.inbox.entity.AnomaliaEntry;
import it.appaltiecontratti.inbox.entity.ComScpEntry;
import it.appaltiecontratti.inbox.entity.ComScpListaEntry;
import it.appaltiecontratti.inbox.entity.ComScpSearchForm;
import it.appaltiecontratti.inbox.entity.FeedbackEntry;
import it.appaltiecontratti.inbox.entity.FeedbackListaEntry;
import it.appaltiecontratti.inbox.entity.FeedbackSearchForm;
import it.appaltiecontratti.inbox.entity.FeedbackUpdateForm;
import it.appaltiecontratti.inbox.entity.FlussiListaEntry;
import it.appaltiecontratti.inbox.entity.FlussiSearchForm;
import it.appaltiecontratti.inbox.entity.FlussoEntry;
import it.appaltiecontratti.inbox.entity.InboxRecordEntry;
import it.appaltiecontratti.inbox.entity.NumeroErroriComunicazioniEntry;
import it.appaltiecontratti.inbox.entity.ReinvioSchedaEntry;
import it.appaltiecontratti.inbox.entity.RichiestaCancellazioneActionForm;
import it.appaltiecontratti.inbox.entity.RichiestaCancellazioneEntry;
import it.appaltiecontratti.inbox.entity.RichiestaCancellazioneForm;
import it.appaltiecontratti.inbox.entity.W9FlussiEntry;
import it.appaltiecontratti.inbox.entity.W9outboxEntry;
import it.appaltiecontratti.inbox.entity.form.CancellaAnomalieForm;
import it.appaltiecontratti.inbox.entity.form.ReinviaSchedaForm;
import it.appaltiecontratti.inbox.entity.form.RisolviAnomalieForm;
import it.appaltiecontratti.inbox.entity.form.SearchCountAnomalieForm;
import it.appaltiecontratti.inbox.entity.responses.BaseResponse;
import it.appaltiecontratti.inbox.entity.responses.FullResponse;
import it.appaltiecontratti.inbox.entity.responses.FullResponseLoaderAppalto;
import it.appaltiecontratti.inbox.entity.responses.ResponseConsultaGaraIdGara;
import it.appaltiecontratti.inbox.entity.responses.ResponseDettaglioComScp;
import it.appaltiecontratti.inbox.entity.responses.ResponseDettaglioFeedback;
import it.appaltiecontratti.inbox.entity.responses.ResponseDettaglioFlusso;
import it.appaltiecontratti.inbox.entity.responses.ResponseListaComScp;
import it.appaltiecontratti.inbox.entity.responses.ResponseListaFeedback;
import it.appaltiecontratti.inbox.entity.responses.ResponseListaFlussi;
import it.appaltiecontratti.inbox.entity.responses.ResponseListaRichiesteCancellazione;
import it.appaltiecontratti.inbox.entity.responses.ResponseNumeroErroriComunicazioni;
import it.appaltiecontratti.inbox.entity.responses.ResponseRichiestaCancellazione;
import it.appaltiecontratti.inbox.mapper.InboxMapper;
import it.appaltiecontratti.inbox.utility.Utility;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AnomaliaType;
import it.avlp.simog.massload.xmlbeans.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.LoaderAppalto;
import it.avlp.simog.massload.xmlbeans.LoaderAppaltoResponse;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;
import it.avlp.simog.massload.xmlbeans.ResponseLoaderAppalto;
import it.avlp.simog.massload.xmlbeans.ResponseLoaderAppalto.FeedBack.AnomalieSchede;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SospensioniType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.massload.xmlbeans.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.TipiSchedeType;
import it.avlp.simog.massload.xmlbeans.TrasferimentoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;

@Component(value = "inboxManager")
public class InboxManager {

	/**
	 * Logger di classe.
	 */
	private static final Logger logger = LogManager.getLogger(InboxManager.class);
	/**
	 * Dao MyBatis con le primitive di estrazione dei dati.
	 */
	@Autowired
	private InboxMapper inboxMapper;

	@Autowired
	private LoaderAppaltoService loaderAppaltoService;

	@Autowired
	private SimogGatewayRestClientManager simogGatewayRestClientManager;

	private static final String CONFIG_CODICE_APP = "W9";
	private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

	public String getJWTKey() throws CriptazioneException {

		String criptedKey = this.inboxMapper.getCipherKey(CONFIG_CODICE_APP, CONFIG_CHIAVE_APP);
		try {
			ICriptazioneByte decript = FactoryCriptazioneByte.getInstance(
					FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, criptedKey.getBytes(),
					ICriptazioneByte.FORMATO_DATO_CIFRATO);

			return new String(decript.getDatoNonCifrato());
		} catch (CriptazioneException e) {
			logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
			throw e;
		}
	}

	public ResponseDettaglioFlusso getDettaglioFlusso(Long idComunicazione) {
		ResponseDettaglioFlusso risultato = new ResponseDettaglioFlusso();
		try {
			FlussoEntry flussoEntry = this.inboxMapper.getDettaglioFlusso(idComunicazione);
			risultato.setData(flussoEntry);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la get dettaglio del flusso.", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public ResponseListaFlussi getListaFlussi(FlussiSearchForm searchForm) {
		ResponseListaFlussi risultato = new ResponseListaFlussi();

		try {
			if (searchForm.getCodOggetto() != null) {
				searchForm.setCodOggetto("%" + searchForm.getCodOggetto().toUpperCase() + "%");
			}

			int totalCount = this.inboxMapper.countSearchFlussi(searchForm.getArea(), searchForm.getCodFisc(),
					searchForm.getCodOggetto(), searchForm.getFaseEsecuzione(), searchForm.getDataRicezioneDa(),
					searchForm.getDataRicezioneA(), searchForm.getNumeroProg());
			RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
			risultato.setTotalCount(totalCount);
			List<FlussiListaEntry> flussi = this.inboxMapper.getlistaFlussi(searchForm.getArea(),
					searchForm.getCodFisc(), searchForm.getCodOggetto(), searchForm.getFaseEsecuzione(),
					searchForm.getDataRicezioneDa(), searchForm.getDataRicezioneA(), searchForm.getNumeroProg(),
					searchForm.getSort(), searchForm.getSortDirection(), rowBounds);
			if (flussi != null) {
				for (FlussiListaEntry flusso : flussi) {
					if (flusso.getArea() != null && flusso.getArea() == 4L && searchForm.getCodOggetto() != null) {
						flusso.setCodOggetto(this.inboxMapper.getIdProgramma(Long.valueOf(searchForm.getCodOggetto())));
					}
				}
			}

			risultato.setData(flussi);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get dei flussi.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	@SuppressWarnings("unused")
	private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public ResponseListaFeedback getListaFeedBack(FeedbackSearchForm searchForm) {
		ResponseListaFeedback risultato = new ResponseListaFeedback();
		try {

			if (searchForm.getCig() != null) {
				searchForm.setCig("%" + searchForm.getCig().toUpperCase() + "%");

			}

			searchForm.setEscludiEliminazioni(false);
			if (searchForm.getPresenzaErrori() != null) {
				searchForm.setNumErrore(searchForm.getPresenzaErrori().equals("1") ? 1L : null);
				searchForm.setEscludiEliminazioni(true);
			}
			
			if(searchForm.getSort() != null && searchForm.getSort().equals("numProgressivoFaseString")) {
				searchForm.setSort("numProgressivoFase");
			}

			int totalCount = this.inboxMapper.countSearchFeedback(searchForm.getNumErrore(), searchForm.getCig(),
					searchForm.getCodFisc(), searchForm.getDataTrasmissioneDa(), searchForm.getFeedbackAnalisi(),
					searchForm.getDataTrasmissioneA(), searchForm.getCodiceAnomalia(), searchForm.getFase() != null ? Long.valueOf(searchForm.getFase()) : null,
					searchForm.getFaseNum(),searchForm.getEscludiEliminazioni());
			RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
			risultato.setTotalCount(totalCount);
			List<FeedbackListaEntry> listaFeedbacks = this.inboxMapper.getListaFeedback(searchForm.getNumErrore(),
					searchForm.getCig(), searchForm.getCodFisc(), searchForm.getDataTrasmissioneDa(),
					searchForm.getFeedbackAnalisi(), searchForm.getDataTrasmissioneA(), searchForm.getCodiceAnomalia(),
					searchForm.getFase() != null ? Long.valueOf(searchForm.getFase()) : null, searchForm.getFaseNum(), searchForm.getSort(), searchForm.getSortDirection(),
					searchForm.getEscludiEliminazioni(),rowBounds);

			for (FeedbackListaEntry feedback : listaFeedbacks) {
				boolean hasErrors = feedback.getNumErrore() != null && feedback.getNumErrore() > 0L;
				boolean canRetry = hasErrors && feedback.getFaseEsecuzione() != null && feedback.getNumXml() != null;
				feedback.setCanRetry(canRetry);
				if (hasErrors && (StringUtils.isBlank(feedback.getFeedbackAnalisi())
						|| (StringUtils.isNotBlank(feedback.getFeedbackAnalisi())
								&& "2".equals(feedback.getFeedbackAnalisi())))) {
					// da analizzare
					feedback.setEsito(2L);
				} else if (hasErrors && (StringUtils.isNotBlank(feedback.getFeedbackAnalisi())
						&& "1".equals(feedback.getFeedbackAnalisi()))) {
					// errore bloccante
					feedback.setEsito(3L);
				} else if (feedback.getNumErrore() == null) {
					// non trasmesso
					feedback.setEsito(null);
				} else {
					// esito positivo
					feedback.setEsito(1L);
				}
				
				
				if(feedback.getXml() != null && feedback.getXml().contains("<SchedeEliminate")) {
					feedback.setContieneXmlELiminazione(true);
				} else {
					feedback.setContieneXmlELiminazione(false);
				}
				
			}

			risultato.setData(listaFeedbacks);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la getListaFeedback.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public ResponseDettaglioFeedback getDettaglioFeedback(Long numXml, Long codGara, Long codLott) {
		ResponseDettaglioFeedback risultato = new ResponseDettaglioFeedback();
		try {
			FeedbackEntry feedbackEntry = this.inboxMapper.getDettFeedback(numXml, codGara, codLott);
			List<AnomaliaEntry> anomaliaEntry = this.inboxMapper.getDettAnomalia(numXml, codGara, codLott);
			if (feedbackEntry.getNumeroErrore() != null && feedbackEntry.getNumeroErrore() > 0) {
				feedbackEntry.setEsito("ERRORI");
			} else if (feedbackEntry.getNumeroWarning() != null && feedbackEntry.getNumeroWarning() > 0) {
				feedbackEntry.setEsito("CARICATA CON SEGNALAZIONI");
			} else {
				feedbackEntry.setEsito("CARICATA");
			}
			feedbackEntry.setAnomaliaEntry(anomaliaEntry);
			risultato.setData(feedbackEntry);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la get dettaglio del feedback.", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public BaseResponse updateFeedback(FeedbackUpdateForm form) {
		BaseResponse risultato = new BaseResponse();
		try {
			this.inboxMapper.updateFeedback(form);
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'aggiornamento di un feedback:", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public ResponseListaComScp getListaComScp(ComScpSearchForm searchForm) {
		ResponseListaComScp risultato = new ResponseListaComScp();

		try {

			int totalCount = this.inboxMapper.countSearchComScp(searchForm.getArea(), searchForm.getCodFisc(),
					searchForm.getCodice(), searchForm.getStato(), searchForm.getDataInvioDa(),
					searchForm.getDataInvioA());
			RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
			risultato.setTotalCount(totalCount);
			List<ComScpListaEntry> comScp = this.inboxMapper.getlistaComScp(searchForm.getArea(),
					searchForm.getCodFisc(), searchForm.getCodice(), searchForm.getStato(), searchForm.getDataInvioDa(),
					searchForm.getDataInvioA(), searchForm.getSort(), searchForm.getSortDirection(), rowBounds);
			for (ComScpListaEntry comScpListaEntry : comScp) {
				comScpListaEntry.setDenominazione(this.inboxMapper.getNameByCF(comScpListaEntry.getCodFisc()));

				// mappo i campi di default
				if (comScpListaEntry.getKey01() != null) {
					comScpListaEntry.setKey01S(comScpListaEntry.getKey01().toString());
				}

				if (comScpListaEntry.getKey02() != null) {
					comScpListaEntry.setKey02S(comScpListaEntry.getKey02().toString());
				}

				if (comScpListaEntry.getKey03() != null) {
					comScpListaEntry.setKey03S(comScpListaEntry.getKey03().toString());
				}

				if (comScpListaEntry.getKey04() != null) {
					comScpListaEntry.setKey04S(comScpListaEntry.getKey04().toString());
				}

				if (comScpListaEntry.getArea() != null) {
					if (comScpListaEntry.getArea() == 1L) {
						// Fase
						comScpListaEntry.setCodOggetto(this.inboxMapper.getCigFromLotto(comScpListaEntry.getKey01(),
								comScpListaEntry.getKey02()));
					} else if (comScpListaEntry.getArea() == 2L) {
						// Gara o Atto
						comScpListaEntry.setCodOggetto(this.inboxMapper.getIdGara(comScpListaEntry.getKey01()));
					} else if (comScpListaEntry.getArea() == 3L) {
						comScpListaEntry.setCodOggetto(comScpListaEntry.getKey01S());
					} else if (comScpListaEntry.getArea() == 4L) {
						// Programma
						comScpListaEntry.setCodOggetto(this.inboxMapper.getIdProgramma(comScpListaEntry.getKey01()));
					}
				}

				if ("0".equals(comScpListaEntry.getCodOggetto())) {
					comScpListaEntry
							.setCodOggetto(this.inboxMapper.getSmartCigFromCodgara(comScpListaEntry.getKey01()));
				}
			}
			risultato.setData(comScp);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get delle comunicazioni di scp.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public ResponseDettaglioComScp getDettaglioComscp(Long idComunicazione) {
		ResponseDettaglioComScp risultato = new ResponseDettaglioComScp();
		ComScpEntry comScpEntry = new ComScpEntry();
		try {
			W9outboxEntry w9outboxEntry = this.inboxMapper.getDettComScp(idComunicazione);

			comScpEntry.setIdcomun(w9outboxEntry.getIdcomun());
			comScpEntry.setArea(w9outboxEntry.getArea());

			comScpEntry.setDataInvio(w9outboxEntry.getDataInvio());
			comScpEntry.setStato(w9outboxEntry.getStato());
			comScpEntry.setContenutoJson(w9outboxEntry.getContenutoJson());
			comScpEntry.setMessaggio(w9outboxEntry.getMessaggio());
			comScpEntry.setIdRicevuto(w9outboxEntry.getIdRicevuto());
			comScpEntry.setCodFisc(w9outboxEntry.getCodFisc());
			comScpEntry.setFaseEsecuzione(w9outboxEntry.getKey03());
			comScpEntry.setDenominazione(this.inboxMapper.getNameByCF(comScpEntry.getCodFisc()));

			if (comScpEntry.getArea() != null && comScpEntry.getArea() == 2) {
				comScpEntry.setIdGara(this.inboxMapper.getIdGara(w9outboxEntry.getKey01()));
				comScpEntry.setNumeroAtto(w9outboxEntry.getKey04());
			}
			if (comScpEntry.getArea() != null && comScpEntry.getArea() == 3) {
				comScpEntry.setIdAvviso(w9outboxEntry.getKey01());
			}
			if (comScpEntry.getArea() != null && comScpEntry.getArea() == 4) {
				comScpEntry.setIdProgramma(this.inboxMapper.getIdProgramma(w9outboxEntry.getKey01()));
			}

			if ("0".equals(comScpEntry.getIdGara())) {
				comScpEntry.setSmartCig(this.inboxMapper.getSmartCigFromCodgara(w9outboxEntry.getKey01()));
			}

			risultato.setData(comScpEntry);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la get dettaglio delle comunicazioni di scp.", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public BaseResponse deleteComunicationScp(Long idComunicazione) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);

		try {
			this.inboxMapper.deleteComunicazione(idComunicazione);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la cancellazione della comunicazione con idComunicazione: [{}]", idComunicazione, e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}

		logger.info("end metodo InboxManager:deleteComunicationScp con idComunicazione: [{}]", idComunicazione);
		return risultato;
	}

	public ResponseListaRichiesteCancellazione getListaRichiesteCancellazione(RichiestaCancellazioneForm searchForm) {
		ResponseListaRichiesteCancellazione risultato = new ResponseListaRichiesteCancellazione();
		try {

			int totalCount = this.inboxMapper.countSearchRichiesteCancellazione();
			RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
			risultato.setTotalCount(totalCount);
			List<RichiestaCancellazioneEntry> flussi = this.inboxMapper
					.getListaRichiesteCancellazione(searchForm.getSort(), searchForm.getSortDirection(), rowBounds);

			risultato.setData(flussi);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get delle richieste di cancellazione.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public BaseResponse rifiutaRichiestaCancellazione(final RichiestaCancellazioneActionForm form) {
		BaseResponse risultato = new BaseResponse();
		try {
			Long idComunicazione = form.getIdComunicazione();
			String motivazione = form.getMotivazione();

			W9FlussiEntry flussoEntry = this.inboxMapper.getFlussoByIdComunicazione(idComunicazione);

			Long stato = StatoComunicazione.STATO_ERRATA.getStato();

			this.inboxMapper.updateStatoComunicazione(idComunicazione, stato);
			if (motivazione != null) {
				this.inboxMapper.updateMotivazioneComunicazione(idComunicazione, "Richiesta rifiutata. " + motivazione);
			}

			this.inboxMapper.updateDataImpW9Flusso(flussoEntry.getIdFlusso(), new Date());

			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante il rifiuto della richiesta di cancellazione {}.",
					form.getIdComunicazione(), t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseRichiestaCancellazione accettaRichiestaCancellazione(final RichiestaCancellazioneActionForm form) {
		ResponseRichiestaCancellazione risultato = new ResponseRichiestaCancellazione();
		try {
			Long idComunicazione = form.getIdComunicazione();
			risultato.setCancellaSchedePrecedenti(false);
			InboxRecordEntry inboxEntry = this.inboxMapper.loadRecordByIdComunicazione(idComunicazione);
			inboxEntry.setStato(StatoComunicazione.STATO_WARNING.getStato());
			inboxEntry.setMsg(null);

			W9FlussiEntry flussoEntry = this.inboxMapper.getFlussoByIdComunicazione(idComunicazione);

			Long codGara = flussoEntry.getCodGara();
			Long codLotto = flussoEntry.getCodLotto();
			Long faseEsecuzione = flussoEntry.getFaseEsecuzione();
			Long numProgressivoFase = flussoEntry.getNumProgressivoFase();
            if (this.isEliminabile(codGara, codLotto, faseEsecuzione)) {

				if (codGara != null && codLotto != null && faseEsecuzione != null && numProgressivoFase != null) {
					int result = this.eliminaSchedaSimogInORT(flussoEntry);
					switch (result) {
					case 0:// Conclusione positiva
						inboxEntry.setStato(StatoComunicazione.STATO_IMPORTATA.getStato());
						inboxEntry.setMsg("La richiesta di cancellazione si è conclusa positivamente");
						risultato.setEsito(true);
						break;
					case 1:// Abort procedura
						inboxEntry.setStato(StatoComunicazione.STATO_WARNING.getStato());
						inboxEntry.setMsg(
								"La richiesta di cancellazione è stata interrotta per problemi con il servizio SIMOG");
						risultato.setEsito(false);
						break;
					case 2:// Conclusione negativa
							// datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, "La
							// richiesta di cancellazione si è conclusa negativamente");
						inboxEntry.setStato(StatoComunicazione.STATO_ERRATA.getStato());
//						inboxEntry.setMsg("La richiesta di cancellazione si è conclusa negativamente");
						risultato.setEsito(false);
						break;
					case 3:// La scheda non esiste in Simog
						inboxEntry.setStato(StatoComunicazione.STATO_IMPORTATA.getStato());
						inboxEntry.setMsg(
								"La richiesta di cancellazione fa riferimento ad una scheda non presente in SIMOG");
						risultato.setEsito(false);
						break;
					case 4:// conclusione positiva con errori in cancellazione dati comuni
						inboxEntry.setStato(StatoComunicazione.STATO_IMPORTATA.getStato());
						inboxEntry.setMsg("La richiesta di cancellazione si è conclusa positivamente");
						risultato.setErrorData(Constants.STATO_IMPORTATA_ERRORE_CANCELLAZIONE_DATI_COMUNI);
						risultato.setEsito(true);
						break;
					case 5:// conclusione negativa per via di schede precedenti
						risultato.setCancellaSchedePrecedenti(true);
						risultato.setEsito(true);
						break;
					default:
						break;
					}
				} else {
					// Errore nel record in cancellazione in W9LFUSSI perche' e' nullo almeno uno
					// dei campo
					// key01, key02, key03 e key04 che rappresentano rispettivamente codice gara,
					// codice lotto,
					// fase esecuzione e progressivo della fase della fase che si vuole cancellare.
					logger.error(
							"Cancellazione fase interrotta per inconsistenza dei dati. Nella tabella W9FLUSSI il record con idFlusso="
									+ flussoEntry.getIdFlusso() + " e idComun= " + idComunicazione
									+ " ha almeno uno dei campi key01,key02,key03,key04 non valorizzati. "
									+ "Questo non pemette di salire alla fase che si desidera cancellare. Si consiglia di risalire alla richiesta di cancellazione a partire dal campo W9FLUSSI.CODOGG");
					throw new RuntimeException(
							"Non è possibile proseguire con la cancellazione della fase per incoerenza dei dati presenti in base dati. Contattare ");
				}
			}

			this.inboxMapper.updateW9Inbox(inboxEntry);
			this.inboxMapper.updateDataImpW9Flusso(flussoEntry.getIdFlusso(), new Date());

			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'accettazione della richiesta di cancellazione {}.",
					form.getIdComunicazione(), t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
			throw new RuntimeException("Errore inaspettato durante l'accettazione della richiesta di cancellazione", t);
		}
		return risultato;
	}

	private boolean isEliminabile(final Long codGara, final Long codLotto, final Long faseEsecuzione) {
		boolean result = true;
		try {
			List<Integer> listaFasi = null;
			Integer[] tempList = null;
			switch (faseEsecuzione.intValue()) {
			case CostantiW9.COMUNICAZIONE_ESITO:
				return true;

			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
				tempList = new Integer[] { //
						CostantiW9.STIPULA_ACCORDO_QUADRO, //
						CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, //
						CostantiW9.SUBAPPALTO, //
						CostantiW9.ISTANZA_RECESSO, //
						CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, //
						CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;

			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
				tempList = new Integer[] { //
						CostantiW9.STIPULA_ACCORDO_QUADRO, //
						CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, //
						CostantiW9.SUBAPPALTO, //
						CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, //
						CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;

			case CostantiW9.STIPULA_ACCORDO_QUADRO:
				tempList = new Integer[] { //
						CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, //
						CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;

			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
				tempList = new Integer[] { //
						CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, //
						CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, //
						CostantiW9.SUBAPPALTO, //
						CostantiW9.ISTANZA_RECESSO, //
						CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, //
						CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;

			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
				tempList = new Integer[] { //
						CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, //
						CostantiW9.SOSPENSIONE_CONTRATTO, //
						CostantiW9.ACCORDO_BONARIO, //
						CostantiW9.VARIANTE_CONTRATTO, //
						CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI, //
						CostantiW9.APERTURA_CANTIERE //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;

			case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
				tempList = new Integer[] { //
						CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO, //
						CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI, //
						CostantiW9.APERTURA_CANTIERE //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;
			case CostantiW9.ANAGRAFICA_GARA_LOTTI:
			case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
				tempList = new Integer[] { //
						CostantiW9.COLLAUDO_CONTRATTO //
				};
				listaFasi = new ArrayList<>(Arrays.asList(tempList));
				break;

			case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
			case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
			case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:
			case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
			case CostantiW9.COLLAUDO_CONTRATTO:
			case CostantiW9.SOSPENSIONE_CONTRATTO:
			case CostantiW9.VARIANTE_CONTRATTO:
			case CostantiW9.ACCORDO_BONARIO:
			case CostantiW9.SUBAPPALTO:
			case CostantiW9.ISTANZA_RECESSO:
			case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
			case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
			case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
			case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
			case CostantiW9.APERTURA_CANTIERE:
			default:
				break;
			}

			if (listaFasi != null && listaFasi.size() > 0) {
				Long numeroFasiSuccessiveDipendenti = this.inboxMapper.countFasiSuccessiveDipendenti(codGara, codLotto,
						listaFasi);

				if (numeroFasiSuccessiveDipendenti.longValue() > 0) {
					result = false;
				}
			}
		} catch (Exception e) {
			logger.error("Errore durante la verifica della eliminibilita' della scheda", e);
		}
		return result;
	}

	private int eliminaSchedaSimogInORT(final W9FlussiEntry flussoEntry) {

		logger.debug("Execution start::eliminaSchedaSimogInORT");

		int result = 0;
		boolean cancellazioneLogica = false;
		// variabile che viene messa a true solamente se la cancellazione comporta anche
		// una chiamata a SIMOG
		boolean cancellazioneSimog = false;
		FullResponseLoaderAppalto response = null;
		try {

			Long idFlusso = flussoEntry.getIdFlusso();
			Long codGara = flussoEntry.getCodGara();
			Long codLotto = flussoEntry.getCodLotto();
			Long faseEsecuzione = flussoEntry.getFaseEsecuzione();
			Long numProgressivoFase = flussoEntry.getNumProgressivoFase();
			String noteInvio = flussoEntry.getNoteInvio();

			String codiceCui = this.loaderAppaltoService.getCUI(codGara, codLotto, faseEsecuzione, numProgressivoFase);

			// Se la fase e' oggetto di monitoraggio simog e il campo W9LOTT.CODCUI e'
			// valorizzato
			// (se il codice non e' valorizzato significa che in SIMOG non e' mai arrivata
			// l'aggiudicazione o qualsiasi altra fase successiva)
			if (Constants.TipiSchede.containsKey(faseEsecuzione) && StringUtils.isNotEmpty(codiceCui)) {
				response = this.loaderAppaltoService.cancellaScheda(codGara, codLotto, faseEsecuzione,
						numProgressivoFase, idFlusso);
				cancellazioneSimog = true;
				if (response != null) {
					if (response.getValidationErrors() != null && response.getValidationErrors().size() > 0) {
						throw new RuntimeException();
					}
					if (!response.isEsito()) {
						result = 1;
					} else {
						// se la chiamata al loader appalto va a buon fine
						Long numXml = this.inboxMapper.getNumXml(idFlusso);
						if (numXml != null) {
							String codice = this.inboxMapper.getCodiceXmlAnom(codGara, codLotto, numXml);
							if (codice != null) {

								if (codice.equals("SIMOGWS_GARALOTTOMANAGER_APP_09")
										|| codice.equals("SIMOGWS_CONNECTIONWSMANAGER_SQL_03")
										|| codice.equals("SIMOG_MASSLOADER_205")) {
									result = 1;

								} else if (codice.equals("SIMOG_MASSLOADER_178")) {
									result = 5;
								} else if (codice.equals("ELIMINAZIONE") || codice.equals("SIMOG_MASSLOADER_191")
										|| codice.equals("SIMOG_MASSLOADER_187")) {
									// cancellazione andata a buon fine oppure la scheda non è presente in SIMOG
									// sposto i flussi in W9FLUSSI_ELIMINATI
									cancellazioneLogica = true;
									if (codice.equals("SIMOG_MASSLOADER_191")
											|| codice.equals("SIMOG_MASSLOADER_187")) {
										// la scheda non esiste in simog
										result = 3;
									}
								} else {
									// conclusione negativa
									result = 2;

									// Si riporta il messaggio di errore nel campo W9INBOX.MSG
									Long idComunicazione = this.inboxMapper.getIdComunicazione(codGara, codLotto,
											faseEsecuzione, numProgressivoFase, idFlusso);

									if (idComunicazione != null && idFlusso != null) {
										String descrizione = this.inboxMapper.getDescrizioneXmlAnom(idFlusso,
												idComunicazione);
										if (StringUtils.isNotEmpty(descrizione)) {
											this.inboxMapper.updateMsgComunicazione(idComunicazione, descrizione);
										}
									}
								}
							} else {
								result = 1;
							}
						}
					}
				} else {
					result = 1;
				}
			} else {
				cancellazioneLogica = true;
			}

			if (cancellazioneLogica) {
				// se la scheda non è oggetto di monitoraggio simog
				// sposto i flussi in W9FLUSSI_ELIMINATI
				this.cancellazioneFlusso(codGara, codLotto, faseEsecuzione, numProgressivoFase, noteInvio);
				// cancella scheda in OR
				this.deleteFase(codGara, codLotto, faseEsecuzione, numProgressivoFase);

				// Se aggiudicazione sopra sotto o adesione sbiancare il CODCUI in W9LOTT
				// 20210121: commentata cancellazione CUI su W9LOTT perche' spostato su W9APPA e
				// quindi
				// viene cancellato dal metodo deleteFase
//				if (CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == faseEsecuzione.intValue()
//						|| CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == faseEsecuzione.intValue()
//						|| CostantiW9.ADESIONE_ACCORDO_QUADRO == faseEsecuzione.intValue()) {
//					this.inboxMapper.deleteCui(codGara, codLotto);
//				}
			}

			// VIGILANZA-88
			// esito positivo in cancellazione a SIMOG, la scheda e' una AGGIUDICAZIONE o
			// equivalente
			// e il lotto non ha un record in W9ESITO con ESITO_PROCEDURA > 1
			if (cancellazioneLogica && //
					result == 0 && //
					cancellazioneSimog && //
					isFaseAggiudicazioneOEquivalente(faseEsecuzione) && //
					!hasLottoEsitoProcedura(codGara, codLotto) //
			) {
				// elimino anche la scheda dati comuni
				boolean hasErrors = false;
				try {
					hasErrors = this.loaderAppaltoService.cancellaDatiComuni(codGara, codLotto);

					// e, se non ho errori, sbianco l'id_scheda_simog in W9LOTT
					if (!hasErrors) {
						inboxMapper.pulisciIdSchedaSimogW9Lotto(codGara, codLotto);
					}
				} catch (Exception ex) {
					logger.error(
							"Errore durante l'invio della richiesta di cancellazione dati comuni a SIMOG, non procedo con l'eliminazione dell'id_scheda_simog da W9LOTT",
							ex);
					hasErrors = true;
				}

				// imposto result a 4 per indicare che la cancellazione si e' conclusa
				// positivamente
				// ma ci sono stati errori nella cancellazione dei dati comuni
				if (hasErrors)
					result = 4;
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Errore nella gestione delle credenziali di accesso al servizio LoaderAppalto di SIMOG", e);
		}

		logger.debug("Execution end::eliminaSchedaSimogInORT");
		return result;
	}

	public void cancellazioneFlusso(final Long codGara, final Long codLotto, final Long faseEsecuzione,
			final Long numProgressivoFase, final String motivoCancellazione) {

		try {

			this.inboxMapper.insertFlussoEliminato(codGara, codLotto, faseEsecuzione, numProgressivoFase);
			this.inboxMapper.updateDataMotivoFlussoEliminato(codGara, codLotto, faseEsecuzione, numProgressivoFase,
					new Date(), motivoCancellazione);
			this.inboxMapper.deleteFlusso(codGara, codLotto, faseEsecuzione, numProgressivoFase);

		} catch (Exception e) {
			logger.error("Si sono verificati degli errori durante la cancellazione logica dei flussi", e);
		}
	}

	private void deleteFase(final Long codGara, final Long codLotto, final Long faseEsecuzione,
			final Long numProgressivoFase) {
		logger.debug("Execution start::deleteFase");

		try {
			int faseEsecuzioneInt = faseEsecuzione.intValue();

			switch (faseEsecuzioneInt) {
			case CostantiW9.COMUNICAZIONE_ESITO:
				this.inboxMapper.deleteW9Esito(codGara, codLotto);
				if (!existsFase(codGara, codLotto, CostantiW9.STIPULA_ACCORDO_QUADRO)) {
					this.inboxMapper.deleteW9Pues(codGara, codLotto, numProgressivoFase);
				}
				break;
			case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:
				this.inboxMapper.deleteW9Imprese(codGara, codLotto);
				break;
			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
				this.inboxMapper.deleteW9Appa(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Inca(codGara, codLotto, numProgressivoFase,
						Arrays.asList("PA", "RA", "RS", "RE"));
				this.inboxMapper.deleteW9Aggi(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Fina(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Requ(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
				this.inboxMapper.deleteW9Appa(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Inca(codGara, codLotto, numProgressivoFase, List.of("RQ"));
				this.inboxMapper.deleteW9Aggi(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Fina(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Requ(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
			case CostantiW9.STIPULA_ACCORDO_QUADRO:
				this.inboxMapper.deleteW9Iniz(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Inca(codGara, codLotto, numProgressivoFase, Arrays.asList("IN", "SI"));
				this.inboxMapper.deleteW9Missic(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Sic(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Pues(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
			case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
				this.inboxMapper.deleteW9Avan(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
			case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
				this.inboxMapper.deleteW9Conc(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.COLLAUDO_CONTRATTO:
				this.inboxMapper.deleteW9Coll(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Inca(codGara, codLotto, numProgressivoFase, List.of("CO"));
				break;
			case CostantiW9.SOSPENSIONE_CONTRATTO:
				this.inboxMapper.deleteW9Sosp(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.VARIANTE_CONTRATTO:
				this.inboxMapper.deleteW9Vari(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Moti(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.ACCORDO_BONARIO:
				this.inboxMapper.deleteW9Acco(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.SUBAPPALTO:
				this.inboxMapper.deleteW9Suba(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.ISTANZA_RECESSO:
				this.inboxMapper.deleteW9Rita(codGara, codLotto, numProgressivoFase);
				break;
			/*
			 * case CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA:
			 * queryCancellazione.add("DELETE FROM W9MISSIC " + condizioneWhereComune +
			 * " AND NUM_MISS=" + numeroFase);
			 * queryCancellazione.add("DELETE FROM W9DOCFASE " + condizioneWhereComune +
			 * " AND FASE_ESECUZIONE = 993 AND NUM_FASE = " + numeroFase); break;
			 */
			case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
				this.inboxMapper.deleteW9Infor(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
				this.inboxMapper.deleteW9Inasic(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
				this.inboxMapper.deleteW9Regcon(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
				this.inboxMapper.deleteW9Tecpro(codGara, codLotto, numProgressivoFase);
				break;
			case CostantiW9.APERTURA_CANTIERE:
				this.inboxMapper.deleteW9Cant(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Cantdest(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Cantimp(codGara, codLotto, numProgressivoFase);
				this.inboxMapper.deleteW9Inca(codGara, codLotto, numProgressivoFase, List.of("NP"));
				break;
			default:
				break;
			}

			this.inboxMapper.deleteFase(codGara, codLotto, faseEsecuzione, numProgressivoFase);

		} catch (Exception e) {
			throw new RuntimeException("Errore durante la cancellazione di una Fase", e);
		}

		logger.debug("Execution end::deleteFase");
	}

	private boolean existsFase(final Long codGara, final Long codLotto, final int faseEsecuzione) {
		Long count = this.inboxMapper.countFasi(codGara, codLotto, faseEsecuzione);
		return count > 0L;
	}

	public ResponseNumeroErroriComunicazioni getNumeroErroriComunicazioni() {
		ResponseNumeroErroriComunicazioni risultato = new ResponseNumeroErroriComunicazioni();
		try {

			NumeroErroriComunicazioniEntry entry = new NumeroErroriComunicazioniEntry();
			entry.setCancellazioniNonEvase(0L);
			entry.setErroriFeedback(0L);
			entry.setErroriComunicazioniSCP(0L);

			int countCancellazioniNonEvaseInt = this.inboxMapper.countSearchRichiesteCancellazione();
			if (countCancellazioniNonEvaseInt != 0) {
				Long countCancellazioniNonEvase = Long.valueOf(countCancellazioniNonEvaseInt);
				entry.setCancellazioniNonEvase(countCancellazioniNonEvase);
			}

			Long countErroriFeedback = this.inboxMapper.countErroriFeedback();
			if (countErroriFeedback != null) {
				entry.setErroriFeedback(countErroriFeedback);
			}

			Long countErroriComunicazioniSCP = this.inboxMapper.countErroriComunicazioniSCP();
			if (countErroriComunicazioniSCP != null) {
				entry.setErroriComunicazioniSCP(countErroriComunicazioniSCP);
			}

			risultato.setData(entry);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la get del numero di errori comunicazioni.", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public FullResponse<List<String>> getListaCodiciAnomaliaFeedback() {
		FullResponse<List<String>> risultato = new FullResponse<List<String>>();
		risultato.setData(new ArrayList<>());
		try {

			List<String> listaCodici = this.inboxMapper.getListaCodiciAnomaliaFeedback();

			risultato.setData(listaCodici);
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante il recupero della lista dei codici anomalia.", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}

		return risultato;
	}

	public FullResponse<Boolean> reinviaScheda(final ReinviaSchedaForm form) {

		logger.debug("Execution start::reinviaScheda");

		if (form == null || form.getCodGara() == null || form.getCodLotto() == null || form.getFaseEsecuzione() == null
				|| form.getNumProgressivoFase() == null) {
			throw new IllegalArgumentException("Errore parametri in ingresso non validi");
		}

		Long codGara = form.getCodGara();
		Long codLotto = form.getCodLotto();
		Long faseEsecuzione = form.getFaseEsecuzione();
		Long numProgressivoFase = form.getNumProgressivoFase();
		Long idFlusso = form.getIdFlusso();
		Long numXml = form.getNumXml();

		logger.debug(
				"Reinvia Scheda: codGara [{}], codLotto [{}], faseEsecuzione [{}], numProgressivoFase [{}], idFlusso [{}]",
				codGara, codLotto, faseEsecuzione, numProgressivoFase, idFlusso);

		FullResponse<Boolean> risultato = new FullResponse<Boolean>();
		risultato.setEsito(true);
		risultato.setData(true);

		try {

			boolean risultatoPubblicazione = this.loaderAppaltoService.pubblicaScheda(codGara, codLotto, faseEsecuzione,
					numProgressivoFase, idFlusso);

			if (!risultatoPubblicazione) {
				Long numeroErrori = this.inboxMapper.getNumeroErroriFeedbackByInvio(codGara, codLotto, numXml);
				if (numeroErrori == null || (numeroErrori != null && numeroErrori.longValue() == 0)) {
					risultatoPubblicazione = true;
				}
			}

			risultato.setData(risultatoPubblicazione);
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error(
					"Errore inaspettato durante il reinvio della scheda a SIMOG: codGara [{}], codLotto [{}], faseEsecuzione [{}], numProgressivoFase [{}].",
					codGara, codLotto, faseEsecuzione, numProgressivoFase, e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}

		logger.debug("Execution end::reinviaScheda");

		return risultato;
	}

	public FullResponse<Long> countAnomalie(SearchCountAnomalieForm form) {
		logger.debug("Execution start::countAnomalie");

		FullResponse<Long> risultato = new FullResponse<Long>();
		risultato.setEsito(true);
		try {
			if (form.getDescrizioneAnomalia() != null) {
				form.setDescrizioneAnomalia("%" + form.getDescrizioneAnomalia().toUpperCase() + "%");
			}
			Long count = this.inboxMapper.countAnomalie(form);
			risultato.setData(count);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante il conteggio delle anomalie", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		logger.debug("Execution end::countAnomalie");
		return risultato;
	}

	public BaseResponse reinviaAnomalie(SearchCountAnomalieForm form) {
		logger.debug("Execution start::reinviaAnomalie");
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			if (form.getDescrizioneAnomalia() != null) {
				form.setDescrizioneAnomalia("%" + form.getDescrizioneAnomalia().toUpperCase() + "%");
			}
			List<ReinvioSchedaEntry> anomalieDaReinviare = this.inboxMapper.searchAnomalie(form);
			for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
				inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
						scheda.getNum(), "1");
				insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato durante il reinvio delle anomalie", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		logger.debug("Execution end::reinviaAnomalie");
		return risultato;
	}

	private void insertW9xml(final Long codGara, final Long codLotto, final Long faseEsecuzione,
			final Long numProgressivoFase) {

		Long counter = inboxMapper.selectIdW9Xml(codGara, codLotto);
		if (counter != null && counter.longValue() > 0) {
			counter++;
		} else {
			counter = Long.valueOf(1L);
		}
		inboxMapper.insertW9Xml(codGara, codLotto, counter, new Date(), faseEsecuzione, numProgressivoFase);
	}

	public BaseResponse risolviAnomalie(RisolviAnomalieForm form) {
		logger.debug("Execution start::risolviAnomalie");
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			List<ReinvioSchedaEntry> anomalieDaReinviare;
			for (String anomalia : form.getErroriSelezionati()) {
				switch (anomalia) {
				case "1":
					inboxMapper.updateCui1(form.getDataInvioDa(), form.getDataInvioA());
					inboxMapper.updateCui2(form.getDataInvioDa(), form.getDataInvioA());
					anomalieDaReinviare = this.inboxMapper.selectCui(form.getDataInvioDa(), form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					break;
				case "2":
					inboxMapper.updateAggPresente1(form.getDataInvioDa(), form.getDataInvioA());
					inboxMapper.updateAggPresente1(form.getDataInvioDa(), form.getDataInvioA());
					anomalieDaReinviare = this.inboxMapper.selectAggPresente(form.getDataInvioDa(),
							form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					break;
				case "3":
					anomalieDaReinviare = this.inboxMapper.selectNoFaseInizConc1(form.getDataInvioDa(),
							form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					anomalieDaReinviare = this.inboxMapper.selectNoFaseInizConc2(form.getDataInvioDa(),
							form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					break;
				case "4":
					inboxMapper.updateNoIdLocale1(form.getDataInvioDa(), form.getDataInvioA());
					inboxMapper.updateNoIdLocale2(form.getDataInvioDa(), form.getDataInvioA());
					anomalieDaReinviare = this.inboxMapper.selectNoIdLocale(form.getDataInvioDa(),
							form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					break;
				case "5":
					anomalieDaReinviare = this.inboxMapper.selectErroreSimog1(form.getDataInvioDa(),
							form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					anomalieDaReinviare = this.inboxMapper.selectErroreSimog2(form.getDataInvioDa(),
							form.getDataInvioA());
					for (ReinvioSchedaEntry scheda : anomalieDaReinviare) {
						inboxMapper.setFaseDaExport(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(),
								scheda.getNum(), "1");
						insertW9xml(scheda.getCodGara(), scheda.getCodLotto(), scheda.getFase(), scheda.getNum());
					}
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la risoluzione delle anomalie", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		logger.debug("Execution end::risolviAnomalie");
		return risultato;
	}

	public BaseResponse cancellaAnomalie(CancellaAnomalieForm form) {
		logger.debug("Execution start::cancellaAnomalie");
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			for (String anomalia : form.getErroriSelezionati()) {
				switch (anomalia) {
				case "1":
					if (form.getDelete()) {
						inboxMapper.cancellaErroriSchedeRitrasmesse(form.getDataInvioDa(), form.getDataInvioA());
					} else {
						inboxMapper.modificaErroriSchedeRitrasmesse(form.getDataInvioDa(), form.getDataInvioA());
					}
					break;
				case "2":
					if (form.getDelete()) {
						inboxMapper.cancellaErroriSchedeNonDovute1(form.getDataInvioDa(), form.getDataInvioA());
						inboxMapper.cancellaErroriSchedeNonDovute2(form.getDataInvioDa(), form.getDataInvioA());
						inboxMapper.cancellaErroriSchedeNonDovute3(form.getDataInvioDa(), form.getDataInvioA());
						inboxMapper.cancellaErroriSchedeNonDovute4(form.getDataInvioDa(), form.getDataInvioA());
					} else {
						inboxMapper.modificaErroriSchedeNonDovute1(form.getDataInvioDa(), form.getDataInvioA());
						inboxMapper.modificaErroriSchedeNonDovute2(form.getDataInvioDa(), form.getDataInvioA());
						inboxMapper.modificaErroriSchedeNonDovute3(form.getDataInvioDa(), form.getDataInvioA());
						inboxMapper.modificaErroriSchedeNonDovute4(form.getDataInvioDa(), form.getDataInvioA());
					}
					break;
				default:
					break;
				}
			}
			this.inboxMapper.cancellaAnomalieOrfane();
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la cancellazione delle anomalie", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		logger.debug("Execution end::cancellaAnomalie");
		return risultato;
	}

	public BaseResponse reinviaComScp(Long idComunicazione) {
		BaseResponse risultato = new BaseResponse();
		try {
			this.inboxMapper.updateMsgJsonStato(idComunicazione);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get delle comunicazioni di scp.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public FullResponse<Boolean> deleteFeedback(final Long codGara, final Long codLotto, final Long numXml) {

		logger.info("Execution start InboxManager::deleteFeedback for codGara [ {} ], codLotto [ {} ], numXml [ {} ]",
				codGara, codLotto, numXml);

		FullResponse<Boolean> risultato = new FullResponse<Boolean>();

		try {

			FeedbackEntry feedbackEntry = this.inboxMapper.getDettFeedback(numXml, codGara, codLotto);

			if (feedbackEntry == null) {

				logger.error("Feedback not found for codGara [ {} ], codLotto [ {} ], numXml [ {} ]", codGara, codLotto,
						numXml);

				risultato.setEsito(false);
				risultato.setErrorData(FullResponse.ERROR_NOT_FOUND);
				return risultato;
			}

			if (feedbackEntry.getNumeroErrore() == null
					|| (feedbackEntry.getNumeroErrore() != null && feedbackEntry.getNumeroErrore() == 0L)) {

				logger.error(
						"Cannot delete feedback for codGara [ {} ], codLotto [ {} ], numXml [ {} ] since it hasn't got errors",
						codGara, codLotto, numXml);

				risultato.setEsito(false);
				risultato.setErrorData(FullResponse.ERROR_NO_DELETE);
				return risultato;
			}

			this.inboxMapper.deleteW9XmlAnom(codGara, codLotto, numXml);
			this.inboxMapper.deleteW9Xml(codGara, codLotto, numXml);

			risultato.setData(true);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la deleteFeedback.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	/**
	 * Metodo che controlla se la fase e' di AGGIUDICAZIONE, SOTTOSOGLIA, ESCLUSO
	 * oppure ACCORDO QUADRO
	 *
	 * @param faseEsecuzione codice fase esecuzione
	 * @return il risultato della verifica
	 */
	private boolean isFaseAggiudicazioneOEquivalente(final Long faseEsecuzione) {
		return faseEsecuzione != null && Constants.TipiSchedeAggiudicazioneOEquivalente.containsKey(faseEsecuzione);
	}

	/**
	 * Metodo che verifica se per un dato lotto si ha un record in W9ESITO con
	 * ESITO_PROCEDURA > 1
	 *
	 * @param codGara  codice gara
	 * @param codLotto codice lotto
	 * @return il risultato della verifica
	 */
	private boolean hasLottoEsitoProcedura(final Long codGara, final Long codLotto) {
		boolean found = false;

		if (codGara != null && codLotto != null) {
			Integer esitoProcedura = inboxMapper.getEsitoProceduraFromW9Esito(codGara, codLotto);
			found = esitoProcedura != null && esitoProcedura > 1;
		}

		return found;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	 public BaseResponse cancellaCascata(@Valid RichiestaCancellazioneActionForm form) {
			BaseResponse risultato = new BaseResponse();
			risultato.setEsito(true);
	        try {
	        	Long idComunicazione = form.getIdComunicazione();
	            W9FlussiEntry flussoEntry = this.inboxMapper.getFlussoByIdComunicazione(idComunicazione);

	            Long codGara = flussoEntry.getCodGara();
	            Long codLotto = flussoEntry.getCodLotto();
	            Long faseEsecuzione = flussoEntry.getFaseEsecuzione();
	        	String codiceCIG = this.inboxMapper.getCigFromLotto(codGara, codLotto);
	        	ResponseConsultaGaraIdGara response = simogGatewayRestClientManager.consultaGara(codiceCIG);
	    		if (response != null && response.getData() != null && response.getData().getDatiGara() != null) {
	    			SchedaType scheda = response.getData();
	    			boolean ok= true;
	    			switch (faseEsecuzione.intValue()) {
		    			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
		    			case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
		    			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
		    				if(ok) {
		    					ok = deleteFaseCollaudo(scheda, codGara, codLotto);
		    				}
		    				if(ok) {
		    					ok = deleteFaseConclusione(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {
		    					ok = deleteFaseSubappalto(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {
		    					ok = deleteFaseVariante(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {
		    					ok = deleteFaseAvanzamento(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {	    				
		    					ok = deleteFaseIniziale(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				break;

		    			
		    			case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
		    			case CostantiW9.STIPULA_ACCORDO_QUADRO:
		    				if(ok) {
		    					ok = deleteFaseCollaudo(scheda, codGara, codLotto);
		    				}
		    				if(ok) {
		    					ok = deleteFaseConclusione(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {
		    					ok = deleteFaseSubappalto(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {
		    					ok = deleteFaseVariante(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				if(ok) {
		    					ok = deleteFaseAvanzamento(scheda, codGara, codLotto);
		    				} else {
		    					risultato.setEsito(false);
		    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    					return risultato;
		    				}
		    				break;
	
		    			case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
		    				if(ok) {
		    					ok = deleteFaseCollaudo(scheda, codGara, codLotto);
		    				}
		    				break;
		    			default:
		    				break;
	    			}
	    			if(ok == false) {
	    				risultato.setEsito(false);
    					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
    					return risultato;
	    			}
	    		} else {
	    			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	    			risultato.setEsito(false);
	    			logger.error(
	    					"Il consulta gara ha risposto negativamente alla richiesta del CIG:" + codiceCIG);
	    		}
	        } catch (Throwable t) {
	            logger.error("Errore inaspettato durante l'accettazione della richiesta di cancellazione a cascata {}.",
	                    form.getIdComunicazione(), t);
	            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	            risultato.setEsito(false);
	            throw new RuntimeException("Errore inaspettato durante l'accettazione della richiesta di cancellazione a cascata", t);
	        }
	        return risultato;
	    }

	private boolean deleteFaseCollaudo(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiCollaudo() != null) {
			
			CollaudoType collaudo = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiCollaudo().getCollaudo();
			String codCui = null;
			String idSchedaSimog = null;
			String idSchedaLocale = null;
			if(collaudo != null) {
				idSchedaSimog = collaudo.getIDSCHEDASIMOG();
				idSchedaLocale = collaudo.getIDSCHEDALOCALE();
				codCui = scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI();
			}
			return cancellasingolaFase(codGara, codLotto, 
					new Long(CostantiW9.COLLAUDO_CONTRATTO), idSchedaLocale, idSchedaSimog, codCui);
		}
		return true;
		
	}

	private boolean deleteFaseAvanzamento(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiAvanzamenti() != null) {
			AvanzamentiType avanzamentiType = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiAvanzamenti();
			if (avanzamentiType.getAvanzamento() != null) {
				for (AvanzamentoType avanzamento : avanzamentiType.getAvanzamento()) {
					return cancellasingolaFase(codGara, codLotto,
							new Long(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA), avanzamento.getIDSCHEDALOCALE(), avanzamento.getIDSCHEDASIMOG(), scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI());
				}
			}
		}
		return true;
	}

	private boolean deleteFaseAccordoBonario(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiAccordi() != null) {
			
			List<AccordoBonarioType> accordi = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiAccordi().getAccordoBonario();
			if (accordi != null) {
				for (AccordoBonarioType accordo : accordi) {
					return cancellasingolaFase(codGara, codLotto,
							new Long(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA), accordo.getIDSCHEDALOCALE(), accordo.getIDSCHEDASIMOG(), scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI());
				}
			}
		}
		return true;
	}

	private boolean deleteFaseSospensione(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiSospensioni() != null) {
			SospensioniType sospensioniType = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiSospensioni();
			if (sospensioniType.getSospensione() != null) {
				for (SospensioneType sospensione : sospensioniType.getSospensione()) {
					return cancellasingolaFase(codGara, codLotto,
							new Long(CostantiW9.SOSPENSIONE_CONTRATTO), sospensione.getIDSCHEDALOCALE(), sospensione.getIDSCHEDASIMOG(), scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI());
				}
			}
		}
		return true;
		
	}

	private boolean deleteFaseStipula(SchedaType scheda, Long codGara, Long codLotto) {

		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiStipula() != null) {
			
			StipulaType stipula = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiStipula().getStipula();
			String codCui = null;
			String idSchedaSimog = null;
			String idSchedaLocale = null;
			if(stipula != null) {
				idSchedaSimog = stipula.getIDSCHEDASIMOG();
				idSchedaLocale = stipula.getIDSCHEDALOCALE();
				codCui = scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI();
			}
			return cancellasingolaFase(codGara, codLotto, 
					new Long(CostantiW9.STIPULA_ACCORDO_QUADRO), idSchedaLocale, idSchedaSimog, codCui);
		}
		return true;
	}

	private boolean deleteFaseIniziale(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiInizio() != null) {
			InizioType inizio = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiInizio().getInizio();
			String codCui = null;
			String idSchedaSimog = null;
			String idSchedaLocale = null;
			if(inizio != null) {
				idSchedaSimog = inizio.getIDSCHEDASIMOG();
				idSchedaLocale = inizio.getIDSCHEDALOCALE();
				codCui = scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI();
			}
			return cancellasingolaFase(codGara, codLotto, 
					new Long(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA), idSchedaLocale, idSchedaSimog, codCui);
		}
		return true;

	}

	private boolean deleteFaseConclusione(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiConclusione() != null) {
			ConclusioneType conslusione = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiConclusione();
			String codCui = null;
			String idSchedaSimog = null;
			String idSchedaLocale = null;
			if(conslusione != null) {
				idSchedaSimog = conslusione.getIDSCHEDASIMOG();
				idSchedaLocale = conslusione.getIDSCHEDALOCALE();
				codCui = scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI();
			}
			return cancellasingolaFase(codGara, codLotto,
					new Long(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA), idSchedaLocale, idSchedaSimog, codCui);
		}
		return true;

	}

	private boolean deleteFaseVariante(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiVarianti() != null) {
			VariantiType variantiType = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiVarianti();
			if (variantiType.getVariante() != null) {
				for (VarianteType variante : variantiType.getVariante()) {
					return cancellasingolaFase(codGara, codLotto,
							new Long(CostantiW9.VARIANTE_CONTRATTO), variante.getVariante().getIDSCHEDALOCALE(), variante.getVariante().getIDSCHEDASIMOG(), scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI());
				}
			}
		}
		return true;

	}

	private boolean deleteFaseSubappalto(SchedaType scheda, Long codGara, Long codLotto) {
		if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getSchedaCompleta() != null
				&& scheda.getDatiScheda().getSchedaCompleta().size() > 0
				&& scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiSubappalti() != null) {
			SubappaltiType subappaltiType = scheda.getDatiScheda().getSchedaCompleta().get(0).getDatiSubappalti();
			if (subappaltiType.getSubappalto() != null) {
				for (SubappaltoType subappalto : subappaltiType.getSubappalto()) {
					return cancellasingolaFase(codGara, codLotto,
							new Long(CostantiW9.SUBAPPALTO), subappalto.getIDSCHEDALOCALE(), subappalto.getIDSCHEDASIMOG(), scheda.getDatiScheda().getSchedaCompleta().get(0).getCUI());
				}
			}
		}
		return true;

	}

	private boolean cancellasingolaFase(Long codGara, Long codLotto, Long codFase, String idSchedaLocale, String idSchedaSimog, String codCui) {
		
		try {
			LoaderAppalto la = new LoaderAppalto();
			String cig = this.inboxMapper.getCigFromLotto(codGara, codLotto);

  		    LoaderAppalto.TrasferimentoDati td = getTrasferimentoDati(codGara, codLotto, codFase, cig,
  		    		codCui, idSchedaLocale, idSchedaSimog);
			la.setTrasferimentoDati(td);
			FullResponseLoaderAppalto risultato = simogGatewayRestClientManager.postLoaderAppalto(la);

				if (StringUtils.isNotBlank(risultato.getErrorData())
						&& FullResponseLoaderAppalto.ERROR_XML_VALIDATION.equals(risultato.getErrorData())
						&& risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
					return false;
				} else if (risultato.getData() != null) {
					String encodedLoaderAppaltoResponse = risultato.getData();
					if (StringUtils.isNotBlank(encodedLoaderAppaltoResponse)) {
						ObjectMapper objectMapper = new ObjectMapper();

						LoaderAppaltoResponse loaderAppaltoResponse;
						loaderAppaltoResponse = objectMapper.readValue(encodedLoaderAppaltoResponse,
								LoaderAppaltoResponse.class);
						if (loaderAppaltoResponse != null && loaderAppaltoResponse.getReturn() != null) {
							ResponseLoaderAppalto response = loaderAppaltoResponse.getReturn();
							if (risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
								return false;
							}
							if (!risultato.isEsito()) {
								return false;
							} else {								
								if (response != null && response.getFeedBack() != null
										&& response.getFeedBack().getAnomalieSchede() != null
										&& response.getFeedBack().getAnomalieSchede().size() > 0) {
									AnomalieSchede anomaliaScheda = response.getFeedBack().getAnomalieSchede()
											.get(0);
									if (anomaliaScheda != null && anomaliaScheda.getAnomalia() != null
											&& anomaliaScheda.getAnomalia().size() > 0) {
										 for (AnomaliaType anomalia : anomaliaScheda.getAnomalia()) {
											 if(anomalia.getLIVELLO() != null && ("ERRORE").equals(anomalia.getLIVELLO().value())) {
												 return false;
				                             }
										 }
	
									}
								}
							}
						} else {
							return false;
						}

					}
				}
		} catch (Exception e) {
			logger.error("Errore in cancellazione fase " + codFase, e);
			return false;
		}
		return true;
	}

	public LoaderAppalto.TrasferimentoDati getTrasferimentoDati(final Long codGara, final Long codLotto,
			final Long faseEsecuzione, final String cig, final String cui, final String idSchedaLocale, final String idSchedaSimog) {
		
		LoaderAppalto.TrasferimentoDati trasferimentoDati = new LoaderAppalto.TrasferimentoDati();

		TrasferimentoType trasferimento = new TrasferimentoType();
		trasferimento.setDATACREAZIONEFLUSSO(Utility.convertTodayToCalendar());
		trasferimento.setNUMSCHEDE(1);
		trasferimentoDati.setInfoTrasferimento(trasferimento);

		RecIdSchedaElimType schedeEliminate = new RecIdSchedaElimType();
	
		if (cig != null) {
			schedeEliminate.setCIG(cig);
		}

		if (cui != null) {
			schedeEliminate.setCUI(cui);
		}

		schedeEliminate.setSCHEDA(TipiSchedeType.fromValue(Constants.TipiSchede.get(faseEsecuzione)));

		
		if (StringUtils.isNotBlank(idSchedaLocale)) {
			schedeEliminate.setIDSCHEDALOCALE(idSchedaLocale);
		}
		if (StringUtils.isNotBlank(idSchedaSimog)) {
			schedeEliminate.setIDSCHEDASIMOG(idSchedaSimog);
		}

		trasferimentoDati.getSchedeEliminate().add(schedeEliminate);

		return trasferimentoDati;
	}

}
