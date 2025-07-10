package it.appaltiecontratti.inbox.bl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.inbox.bl.impl.SimogGatewayRestClientManager;
import it.appaltiecontratti.inbox.common.CostantiW9;
import it.appaltiecontratti.inbox.entity.ChiaveGaraEntry;
import it.appaltiecontratti.inbox.entity.responses.BaseResponse;
import it.appaltiecontratti.inbox.entity.responses.ResponseConsultaGaraIdGara;
import it.appaltiecontratti.inbox.mapper.InboxMapper;
import it.avlp.simog.massload.xmlbeans.AccordiBonariType;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AdesioneType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SospensioniType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.massload.xmlbeans.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;


@SuppressWarnings("java:S1314")
@Component(value = "simogManager")
public class SimogManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(SCPManager.class);

	@Autowired
	private InboxMapper inboxMapper;

	@Autowired
	private SimogGatewayRestClientManager simogGatewayRestClientManager;

	public BaseResponse riallineaIndiciSimog(final String codiceCIG) {

		if (logger.isDebugEnabled()) {
			logger.debug("riallineaIndiciSimogDaOsservatorio: inizio metodo");
		}
		BaseResponse risultato = new BaseResponse();
		ChiaveGaraEntry chiaveGara = inboxMapper.getChiaveGaraLottoByCig(codiceCIG);
		if (chiaveGara == null || chiaveGara.getCodGara() == null) {
			risultato.setEsito(false);
			risultato.setErrorData("CIG-NOT-FOUND");
			return risultato;
		}
		ResponseConsultaGaraIdGara response = simogGatewayRestClientManager.consultaGara(codiceCIG);
		if (response != null && response.getData() != null && response.getData().getDatiGara() != null) {
			SchedaType scheda = response.getData();
			riallineaIndiciSchede(codiceCIG, chiaveGara.getCodGara(), chiaveGara.getCodLotto(), scheda);

		} else {
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
			logger.error(
					"Il consulta gara ha risposto negativamente alla richiesta di importazione del CIG:" + codiceCIG);
		}
		return risultato;
	}

	private BaseResponse riallineaIndiciSchede(String codiceCIG, Long codGara, Long codLotto, SchedaType schedaType) {

		if (logger.isDebugEnabled()) {
			logger.debug("riallineaIndiciSimogUnificato: inizio metodo");
		}
		BaseResponse risultato = new BaseResponse();

		try {
			if (schedaType != null) {
				DatiAggiudicazioneType datiAggiudicazione = schedaType.getDatiScheda();
				if (datiAggiudicazione != null) {
					DatiComuniType datiComuni = datiAggiudicazione.getDatiComuni();
					if (datiComuni != null && datiComuni.getIDSCHEDALOCALE() != null) {
						inboxMapper.updateIdSchedaLocale(datiComuni.getIDSCHEDALOCALE(), codiceCIG);
					}
					if (datiComuni != null && datiComuni.getIDSCHEDASIMOG() != null) {
						inboxMapper.updateIdSchedaSimog(datiComuni.getIDSCHEDASIMOG(), codiceCIG);
					} else {
						inboxMapper.updateIdSchedaSimog(null, codiceCIG);
					}

					for (int i = 0; i < datiAggiudicazione.getSchedaCompleta().size(); i++) {
						SchedaCompletaType schedaCompleta = datiAggiudicazione.getSchedaCompleta().get(i);
						Long numappa = inboxMapper.getNumAppaByCodCui(schedaCompleta.getCUI());
						if (numappa == null) {
							numappa = 1L;
						}
						inboxMapper.updateCodCuiByCig(schedaCompleta.getCUI(), codiceCIG);
						inboxMapper.updateCodCuiAppa(schedaCompleta.getCUI(), codGara, codLotto, numappa);
						if (schedaCompleta.getAdesione() != null) {
							this.aggiornaIdSchedaAdesione(codGara, codLotto, numappa, schedaCompleta.getAdesione());
						}
						if (schedaCompleta.getAggiudicazione() != null) {
							this.aggiornaIdSchedaAggiudicazione(codGara, codLotto, numappa,
									schedaCompleta.getAggiudicazione());
						} else {
							this.aggiornaIdSchedaAggiudicazione(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiAccordi() != null) {
							this.aggiornaIdSchedaAccordi(codGara, codLotto, numappa, schedaCompleta.getDatiAccordi());
						} else {
							this.aggiornaIdSchedaAccordi(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiAvanzamenti() != null) {
							this.aggiornaIdSchedaAvanzamenti(codGara, codLotto, numappa,
									schedaCompleta.getDatiAvanzamenti());
						} else {
							this.aggiornaIdSchedaAvanzamenti(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiCollaudo() != null) {
							this.aggiornaIdSchedaCollaudo(codGara, codLotto, numappa, schedaCompleta.getDatiCollaudo());
						} else {
							this.aggiornaIdSchedaCollaudo(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiConclusione() != null) {
							this.aggiornaIdSchedaConclusione(codGara, codLotto, numappa,
									schedaCompleta.getDatiConclusione());
						} else {
							this.aggiornaIdSchedaConclusione(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiInizio() != null) {
							this.aggiornaIdSchedaInizio(codGara, codLotto, numappa,
									schedaCompleta.getDatiInizio().getInizio());
						} else {
							this.aggiornaIdSchedaInizio(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiSospensioni() != null) {
							this.aggiornaIdSchedaSospensioni(codGara, codLotto, numappa,
									schedaCompleta.getDatiSospensioni());
						} else {
							this.aggiornaIdSchedaSospensioni(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiStipula() != null
								&& schedaCompleta.getDatiStipula().getStipula() != null) {
							this.aggiornaIdSchedaStipula(codGara, codLotto, numappa,
									schedaCompleta.getDatiStipula().getStipula());
						} else {
							this.aggiornaIdSchedaStipula(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiSubappalti() != null) {
							this.aggiornaIdSchedaSubappalti(codGara, codLotto, numappa,
									schedaCompleta.getDatiSubappalti());
						} else {
							this.aggiornaIdSchedaSubappalti(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getDatiVarianti() != null) {
							this.aggiornaIdSchedaVarianti(codGara, codLotto, numappa, schedaCompleta.getDatiVarianti());
						} else {
							this.aggiornaIdSchedaVarianti(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getEscluso() != null) {
							this.aggiornaIdSchedaEscluso(codGara, codLotto, numappa, schedaCompleta.getEscluso());
						} else {
							this.aggiornaIdSchedaEscluso(codGara, codLotto, numappa, null);
						}

						if (schedaCompleta.getSottosoglia() != null) {
							this.aggiornaIdSchedaSottosoglia(codGara, codLotto, numappa,
									schedaCompleta.getSottosoglia());
						} else {
							this.aggiornaIdSchedaSottosoglia(codGara, codLotto, numappa, null);
						}
					}
				} else {
					// Pulizia indici SIMOG nel caso non ci siano dati comeuni nella gara
					this.inboxMapper.pulisciIdSchedaSimogAndCodCui(codGara, codLotto);
					this.inboxMapper.pulisciCodCuiAggiudicazione(codGara, codLotto);
					this.inboxMapper.pulisciIdSchedaSimogW9Fasi(codGara, codLotto);
				}
			}
		} catch (Exception e) {
			logger.error("Errore in riallineamento indici SIMOG", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	private void aggiornaIdSchedaLocale(final long codGara, final long codLotto, final long fase, final long numProg,
			final long numAppa, final String idSchedaLocale) {
		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaIdSchedaLocale: inizio metodo");
		}
		if (StringUtils.isNotEmpty(idSchedaLocale)) {
			if (numProg == -1) {
				this.inboxMapper.updateIdSchedaLocaleFaseSingola(idSchedaLocale, codGara, codLotto, fase, numAppa);
			} else {
				this.inboxMapper.updateIdSchedaLocaleFaseMultipla(idSchedaLocale, codGara, codLotto, fase, numProg);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaIdSchedaLocale: fine metodo");
		}
	}

	private void aggiornaIdSchedaSimog(final long codGara, final long codLotto, final long fase, final long numProg,
			final long numAppa, final String idSchedaSimog) {
		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaIdSchedaSimog: inizio metodo");
		}
		if (numProg == -1) {
			this.inboxMapper.updateIdSchedaSimogFaseSingola(idSchedaSimog, codGara, codLotto, fase, numAppa);
		} else {
			this.inboxMapper.updateIdSchedaSimogFaseMultipla(idSchedaSimog, codGara, codLotto, fase, numProg);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("aggiornaIdSchedaSimog: fine metodo");
		}
	}

	private Long getFase(String idSchedaLocale) {
		Long result = null;

		if (idSchedaLocale.matches("\\d{3}_\\d{3}_\\d{3}")) {
			String[] temp = idSchedaLocale.split("_");
			result = Long.valueOf(StringUtils.stripStart(temp[1], "0"));
		}
		return result;
	}

	private Long getProgressivoFase(String idSchedaLocale) {
		Long result = null;

		if (idSchedaLocale.matches("\\d{3}_\\d{3}_\\d{3}")) {
			String[] temp = idSchedaLocale.split("_");
			result = Long.valueOf(StringUtils.stripStart(temp[2], "0"));
		}
		return result;
	}

	private void aggiornaIdSchedaAdesione(final long codGara, final long codLott, final long numappa,
			final AdesioneType adesione) {
		this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.ADESIONE_ACCORDO_QUADRO, -1, numappa,
				adesione.getAppalto().getIDSCHEDALOCALE());
		this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.ADESIONE_ACCORDO_QUADRO, -1, numappa,
				adesione.getAppalto().getIDSCHEDASIMOG());
	}

	private void aggiornaIdSchedaAggiudicazione(final long codGara, final long codLott, final long numappa,
			final AggiudicazioneType aggiudicazione) throws SQLException {
		if (aggiudicazione != null) {
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, -1, numappa,
					aggiudicazione.getAppalto().getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, -1, numappa,
					aggiudicazione.getAppalto().getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaAccordi(final long codGara, final long codLott, final long numappa,
			final AccordiBonariType datiAccordi) throws SQLException {

		if (datiAccordi != null) {
			List<AccordoBonarioType> listaAccordi = datiAccordi.getAccordoBonario();

			for (int i = 0; i < listaAccordi.size(); i++) {
				AccordoBonarioType accordo = listaAccordi.get(i);

				if (accordo.getIDSCHEDASIMOG() != null) {
					
					Long numProg = null;
					if(accordo.getIDSCHEDALOCALE() != null) {
						numProg = this.getProgressivoFase(accordo.getIDSCHEDALOCALE());
					} 
					if(numProg == null && accordo.getDATAACCORDO() != null){
						Date date = accordo.getDATAACCORDO().toGregorianCalendar().getTime();
						
						Date inizio = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(00,00,00).atZone(ZoneId.systemDefault()).toInstant());
						
						Date fine = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant());
						
						numProg = inboxMapper.getNumAccoByCodgaraCodlottoDataAccordo(codGara,codLott,inizio,fine);
					}
					if (numProg != null) {
						Long numeroFasi = inboxMapper.countFasiPerRiallineamento(codGara,
								codLott, Long.valueOf(CostantiW9.ACCORDO_BONARIO), numProg);
						if (numeroFasi.intValue() == 1) {
							this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.ACCORDO_BONARIO, numProg, numappa,
									accordo.getIDSCHEDASIMOG());
						}
					}
				}
			}
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.ACCORDO_BONARIO, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaAvanzamenti(final long codGara, final long codLott, final long numappa,
			final AvanzamentiType datiAvanzamenti) throws SQLException {
		if (datiAvanzamenti != null) {
			List<AvanzamentoType> listAvanzamenti = datiAvanzamenti.getAvanzamento();

			for (int i = 0; i < listAvanzamenti.size(); i++) {
				AvanzamentoType avanzamento = listAvanzamenti.get(i);
				if (avanzamento.getIDSCHEDASIMOG() != null) {	
					Long numProg = null;
					if(avanzamento.getIDSCHEDALOCALE() != null) {
						numProg = this.getProgressivoFase(avanzamento.getIDSCHEDALOCALE());
					} 
					if(numProg == null && avanzamento.getDATARAGGIUNGIMENTO() != null){
						Date date = avanzamento.getDATARAGGIUNGIMENTO().toGregorianCalendar().getTime();
						
						Date inizio = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(00,00,00).atZone(ZoneId.systemDefault()).toInstant());
						
						Date fine = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant());
						
						numProg = inboxMapper.getNumAvanByCodgaraCodlottoDataraggiugnimento(codGara,codLott,inizio,fine);
					}
					
					if (numProg != null) {
						Long numeroFasi = inboxMapper.countFasiPerRiallineamento(codGara, codLott, Long.valueOf(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA), numProg);
						if (numeroFasi.intValue() == 1) {
							this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA,
									numProg, numappa, avanzamento.getIDSCHEDASIMOG());
						}
					}
				}
			}
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					null);
		}
	}

	private void aggiornaIdSchedaCollaudo(final long codGara, final long codLott, final long numappa,
			final DatiCollaudoType datiCollaudo) throws SQLException {
		if (datiCollaudo != null) {
			CollaudoType collaudo = datiCollaudo.getCollaudo();
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.COLLAUDO_CONTRATTO, -1, numappa,
					collaudo.getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.COLLAUDO_CONTRATTO, -1, numappa,
					collaudo.getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.COLLAUDO_CONTRATTO, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaConclusione(final long codGara, final long codLott, final long numappa,
			final ConclusioneType conclusione) throws SQLException {
		if (conclusione != null) {
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					conclusione.getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					conclusione.getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					null);
		}
	}

	private void aggiornaIdSchedaInizio(final long codGara, final long codLott, final long numappa,
			final InizioType inizio) throws SQLException {
		if (inizio != null) {
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					inizio.getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					inizio.getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, -1, numappa,
					null);
		}
	}

	private void aggiornaIdSchedaSospensioni(final long codGara, final long codLott, final long numappa,
			final SospensioniType datiSospensioni) throws SQLException {
		if (datiSospensioni != null) {
			List<SospensioneType> listSospensioni = datiSospensioni.getSospensione();

			for (int i = 0; i < listSospensioni.size(); i++) {
				SospensioneType sospensione = listSospensioni.get(i);

				if (sospensione.getIDSCHEDASIMOG() != null) {
					Long numProg = null;
					
					if(sospensione.getIDSCHEDALOCALE() != null) {
						numProg = this.getProgressivoFase(sospensione.getIDSCHEDALOCALE());
					} 
					if(numProg == null && sospensione.getDATAVERBSOSP() != null){
						Date date = sospensione.getDATAVERBSOSP().toGregorianCalendar().getTime();
						
						Date inizio = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(00,00,00).atZone(ZoneId.systemDefault()).toInstant());
						
						Date fine = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant());
						
						numProg = inboxMapper.getNumSospByCodgaraCodlottoDataVerbSosp(codGara,codLott,inizio,fine);
					}
					if (numProg != null) {
						Long numeroFasi = inboxMapper.countFasiPerRiallineamento(codGara, codLott, Long.valueOf(CostantiW9.SOSPENSIONE_CONTRATTO), numProg);
						if (numeroFasi.intValue() == 1) {
							this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.SOSPENSIONE_CONTRATTO, numProg,
									numappa, sospensione.getIDSCHEDASIMOG());
						}
					}
				}
			}
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.SOSPENSIONE_CONTRATTO, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaStipula(final long codGara, final long codLott, final long numappa,
			final StipulaType stipula) throws SQLException {
		if (stipula != null) {
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.STIPULA_ACCORDO_QUADRO, -1, numappa,
					stipula.getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.STIPULA_ACCORDO_QUADRO, -1, numappa,
					stipula.getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.STIPULA_ACCORDO_QUADRO, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaSubappalti(final long codGara, final long codLott, final long numappa,
			final SubappaltiType datiSubappalti) throws SQLException {
		if (datiSubappalti != null) {
			List<SubappaltoType> listSubappalti = datiSubappalti.getSubappalto();

			for (int i = 0; i < listSubappalti.size(); i++) {
				SubappaltoType subappalto = listSubappalti.get(i);
				if (subappalto.getIDSCHEDASIMOG() != null) {
					Long numProg = null;
					
					if(subappalto.getIDSCHEDALOCALE() != null) {
						numProg = this.getProgressivoFase(subappalto.getIDSCHEDALOCALE());
					} 
					if(numProg == null && subappalto.getDATAAUTORIZZAZIONE() != null){
						Date date = subappalto.getDATAAUTORIZZAZIONE().toGregorianCalendar().getTime();
						
						Date inizio = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(00,00,00).atZone(ZoneId.systemDefault()).toInstant());
						
						Date fine = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant());
						
						numProg = inboxMapper.getNumSubaByCodgaraCodlottoDataAutorizzazione(codGara,codLott,inizio,fine);
					}
					if (numProg != null) {
						Long numeroFasi = inboxMapper.countFasiPerRiallineamento(codGara, codLott, Long.valueOf(CostantiW9.SUBAPPALTO), numProg);
						if (numeroFasi.intValue() == 1) {
							this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.SUBAPPALTO, numProg, numappa,
									subappalto.getIDSCHEDASIMOG());
						}
					}
				}
			}
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.SUBAPPALTO, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaVarianti(final long codGara, final long codLott, final long numappa,
			final VariantiType datiVarianti) throws SQLException {
		if (datiVarianti != null) {
			List<VarianteType> listVarianti = datiVarianti.getVariante();

			for (int i = 0; i < listVarianti.size(); i++) {
				VarianteType variante = listVarianti.get(i);
				if (variante.getVariante().getIDSCHEDASIMOG() != null) {
					Long numProg = null;
					
					if(variante.getVariante().getIDSCHEDALOCALE() != null) {
						numProg = this.getProgressivoFase(variante.getVariante().getIDSCHEDALOCALE());
					} 
					if(numProg == null && variante.getVariante().getDATAVERBAPPR() != null){
						Date date = variante.getVariante().getDATAVERBAPPR().toGregorianCalendar().getTime();
						
						Date inizio = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(00,00,00).atZone(ZoneId.systemDefault()).toInstant());
						
						Date fine = Date.from(Instant.ofEpochMilli(date.getTime())
			        		      .atZone(ZoneId.systemDefault())
			        		      .toLocalDate().atTime(23,59,59).atZone(ZoneId.systemDefault()).toInstant());
						
						numProg = inboxMapper.getNumVariByCodgaraCodlottodataVerbAppr(codGara,codLott,inizio,fine);
					}
					if (numProg != null) {
						Long numeroFasi = inboxMapper.countFasiPerRiallineamento(codGara, codLott, Long.valueOf(CostantiW9.VARIANTE_CONTRATTO), numProg);
						if (numeroFasi.intValue() == 1) {
							this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.VARIANTE_CONTRATTO, numProg,
									numappa, variante.getVariante().getIDSCHEDASIMOG());
						}
					}
				}
			}
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.VARIANTE_CONTRATTO, -1, numappa, null);
		}
	}

	private void aggiornaIdSchedaEscluso(final long codGara, final long codLott, final long numappa,
			final SchedaEsclusoType escluso) throws SQLException {
		if (escluso != null) {
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, -1, numappa,
					escluso.getAppalto().getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, -1, numappa,
					escluso.getAppalto().getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, -1, numappa,
					null);
		}
	}

	private void aggiornaIdSchedaSottosoglia(final long codGara, final long codLott, final long numappa,
			final SchedaSottosogliaType sottoSoglia) throws SQLException {
		if (sottoSoglia != null) {
			this.aggiornaIdSchedaLocale(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, -1, numappa,
					sottoSoglia.getAppalto().getIDSCHEDALOCALE());
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, -1, numappa,
					sottoSoglia.getAppalto().getIDSCHEDASIMOG());
		} else {
			this.aggiornaIdSchedaSimog(codGara, codLott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, -1, numappa,
					null);
		}
	}

}
