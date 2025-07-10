package it.appaltiecontratti.monitoraggiocontratti.simog.bl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.AbstractManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LogEventiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.LoginResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.PresaInCaricoGaraDelegataForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaCollaborazioni;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePresaInCaricoGaraDelegata;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Funzioni;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.BaseRupInfo;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.CPVSecondariaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.CUPLOTTOType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.CategLottoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ConsultaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.DatiCUPType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FaseEsecuzioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FeedbackType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FlagSNType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.GaraType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.GetElencoFeedback;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.InfoUtenteEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.LottoFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.LottoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.NoteAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.PubblicazioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ResponsabiliType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SABaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaCompletaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SituazioneGaraLotto;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaGaraBean;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.GaraInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.LottoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.PubblicazioneInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.mapper.SimogMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.CheckMigrazioneGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseCheckMigrazione;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaGaraIdGara;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaNumeroGaraCig;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseFeedback;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseImportaGara;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;

/**
 * Manager per la gestione della business logic.
 * 
 * @author Michele.diNapoli
 */
@SuppressWarnings("java:S2229")
@Component(value = "simogManager")
public class SimogManager extends AbstractManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(SimogManager.class);

	@Autowired
	private SimogMapper simogMapper;

	@Autowired
	private SqlMapper sqlMapper;

	@Autowired
	private AnacRestClientManager anacrestclientmanager;

	@Autowired
	private SchedeManager schedeManager;

	private static final String OP_PRESA_IN_CARICO = "OP_PRESA_IN_CARICO";

	private static final String OP_PRESA_IN_CARICO_DELEGA_U = "OP_PRESA_IN_CARICO_DELEGA_U";

	private static final String OP_PRESA_IN_CARICO_DELEGA_AC = "OP_PRESA_IN_CARICO_DELEGA_AC";

	private static final String ERROR_PRESA_IN_CARICO_DELEGA = "ERROR_PRESA_IN_CARICO_DELEGA";

	private static final String OP_CONSULTA_GARA = "OP_CONSULTA_GARA";

	private static final String APPLICATION_CODE = "W9";

	private static final String IMPORT_SCHEDE_CONFIG_CODE = "it.eldasoft.simog.consultaGara.importaFasi";
	
	private static final String CONTROLLO_RUP_CONFIG_CODE = "it.eldasoft.sitat.consultaGara.controlloRUP";
	
	private static final String CONTROLLO_CF_SA_CONFIG_CODE = "it.eldasoft.sitat.consultaGara.controlloCFSA";
	
	public static final String CONFIG_CONFIGURAZIONE_APPLICATIVA = "ControlloDati";

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseImportaGara importaGaraDaSimog(String cigIdAvGara, final String cfsa, final Long syscon,
			final boolean sendMail, String authorization, String loginMode) throws SQLException, Exception {

		ResponseImportaGara risultato = new ResponseImportaGara();
		if (logger.isDebugEnabled()) {
			logger.debug("importaGaraDaSimog: inizio metodo");
		}
		List<String> codiciCIG = new ArrayList<String>();
		InfoUtenteEntry utente = this.simogMapper.getInfoUtente(syscon);
		String codUffInt = this.contrattiMapper.getCodeinByCfSa(cfsa);
		try {
			String checkCFSA = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, CONTROLLO_CF_SA_CONFIG_CODE);
			SchedaType scheda = null;
			String cfRup = utente.getCf();
			boolean isCig = this.isCig(cigIdAvGara);

			if (!isCig) {
				ResponseConsultaNumeroGaraCig response = anacrestclientmanager.consultaNumeroGara(cigIdAvGara);

				if (response != null) {

					if (response.isEsito()) {
						codiciCIG = response.getData();
					} else {
						logger.error(
								"Errore in importa gara,idavgara = " + cigIdAvGara + " " + response.getErrorData());
						risultato.setEsito(false);
						risultato.setErrorData(response.getErrorData());
						return risultato;
					}
				} else {
					String strMsg = "La richiesta a SIMOG dei CIG associati alla gara con IdGara=" + cigIdAvGara
							+ " e' terminata con esito negativo. Di seguito il messaggio di ritorno da SIMOG: ";
					logger.error(strMsg);
					risultato.setErrorData(strMsg);
					risultato.setEsito(false);
				}
			} else {
				ResponseConsultaGaraIdGara response = anacrestclientmanager.consultaGara(cigIdAvGara);
				if (response != null) {

					if (response.isEsito()) {
						// inserimento diretto nel DB del cig

						scheda = response.getData();
						String idAvGara = "" + scheda.getDatiGara().getGara().getIDGARA();
						ResponseConsultaNumeroGaraCig responseNumeroGara = anacrestclientmanager
								.consultaNumeroGara(idAvGara);
						if (responseNumeroGara.isEsito()) {
							codiciCIG = responseNumeroGara.getData();
						} else {
							logger.error(
									"Errore in importa gara,idavgara = " + cigIdAvGara + " " + response.getErrorData());
							risultato.setEsito(false);
							risultato.setErrorData(response.getErrorData());
							return risultato;
						}
					} else {
						logger.error(
								"Errore in importa gara,idavgara = " + cigIdAvGara + " " + response.getErrorData());
						risultato.setEsito(false);
						risultato.setErrorData(response.getErrorData());
						return risultato;
					}
				}
			}

			List<ConsultaGaraBean> listaConsultaGaraBean = null;
			String idAvGara = null;
			if (codiciCIG != null && codiciCIG.size() > 0) {
				listaConsultaGaraBean = new ArrayList<ConsultaGaraBean>();

				for (int i = 0; i < codiciCIG.size(); i++) {
					ConsultaGaraBean consultaGaraBean = null;
					Long lottoOccurencies = this.simogMapper.getCountLottiByCigAndSa(codiciCIG.get(i), codUffInt);
					if (lottoOccurencies > 0) {
						consultaGaraBean = new ConsultaGaraBean(codiciCIG.get(i), false, "Lotto esistente");
					} else {
						consultaGaraBean = new ConsultaGaraBean(codiciCIG.get(i), false);
					}
					listaConsultaGaraBean.add(consultaGaraBean);
				}

				int numeroLotti = listaConsultaGaraBean.size();
				int numeroLottiCaricati = 0;
				int numeroLottiEsistenti = 0;
				int numeroLottiConErrore = 0;

				for (int i = 0; i < listaConsultaGaraBean.size(); i++) {
					ConsultaGaraBean consultaGaraBean = listaConsultaGaraBean.get(i);
					ResponseConsultaGaraIdGara response = anacrestclientmanager
							.consultaGara(consultaGaraBean.getCodiceCIG());
					String valore =  this.simogMapper.getConfig(CONFIG_CODICE_APP, CONFIG_CONFIGURAZIONE_APPLICATIVA);
					if(!"SCP".equals(valore)) {
					
					
						if (response != null && response.getData() != null && response.getData().getDatiGara() != null) {
							scheda = response.getData();
							String codgara = this.simogMapper
									.getCodGaraByIdAnac(scheda.getDatiGara().getGara().getIDGARA().toString());
							
							
							
							if (codgara != null) {
								GaraEntry gara = this.contrattiMapper.dettaglioGara(new Long(codgara));
								if (gara != null) {
									String codein = gara.getCodiceStazioneAppaltante();
									if (codein != null && response != null) {
	
										GaraType garaType = scheda.getDatiGara().getGara();
	
										List<String> cfSaGaraList = this.simogMapper.getCFSaByCodice(codein);
										String cfSaGara = null;
										if(cfSaGaraList != null && cfSaGaraList.size() > 0) {
											cfSaGara = cfSaGaraList.get(0);
										}
										if (!(cfSaGara).equals(garaType.getCFAMMINISTRAZIONE())) {
	
											ConsultaGaraEntry entry = new ConsultaGaraEntry();
											entry.setOperation(OP_PRESA_IN_CARICO);
											entry.setCodGara(codgara);
											entry.setCfRup(garaType.getCFUTENTE());
											if (garaType.getIDGARA() != null) {
												entry.setIdAvGara(garaType.getIDGARA().toString());
											}
											
											List<SABaseEntry> saBAseInfoList = this.simogMapper.getBaseSAInfoByCf(cfSaGara);
											SABaseEntry origineSAInfo = null;
											if(saBAseInfoList != null && saBAseInfoList.size() > 0) {
												origineSAInfo = saBAseInfoList.get(0);
											}
											entry.setOrigineSAInfo(origineSAInfo);
											risultato.setData(entry);
											risultato.setEsito(true);
											return risultato;
	
										}
									}
	
								}
							}
	
						}
					}

					if (response != null) {

						if (response.isEsito()) {
							// inserimento diretto nel DB del cig

							scheda = response.getData();

							if (idAvGara == null) {
								idAvGara = "" + scheda.getDatiGara().getGara().getIDGARA();
							}

							GaraType garaType = scheda.getDatiGara().getGara();
							// gestione presa in carico gara delegata
							if (!(cfsa).equals(garaType.getCFAMMINISTRAZIONE()) && (checkCFSA == null || "1".equals(checkCFSA))) {
								if (garaType.getIDFDELEGATE() != null) {
									if (garaType.getCFAMMAGENTEGARA() != null
											&& cfsa.equals(garaType.getCFAMMAGENTEGARA())) {
										Long idFdelegate = new Long(garaType.getIDFDELEGATE());
										String codgara = this.simogMapper.getCodGaraByIdAnacAndSA(
												scheda.getDatiGara().getGara().getIDGARA().toString(), codUffInt);
										boolean iscigIndb = codgara != null;
										if (idFdelegate == 1L || idFdelegate == 2L || idFdelegate == 4L) {
											if (hasEsito(scheda) && (hasAggiudicazione(scheda) || idFdelegate == 4L)) {
												ConsultaGaraEntry entry = new ConsultaGaraEntry();
												String ruolo = this.simogMapper.getRuoloBySyscon(syscon);
												entry.setOperation("U".equals(ruolo) ? OP_PRESA_IN_CARICO_DELEGA_U
														: OP_PRESA_IN_CARICO_DELEGA_AC);
												
												List<SABaseEntry> saBAseInfoList = this.simogMapper.getBaseSAInfoByCf(garaType.getCFAMMINISTRAZIONE());
												SABaseEntry origineSAInfo = null;
												if(saBAseInfoList != null && saBAseInfoList.size() > 0) {
													origineSAInfo = saBAseInfoList.get(0);
												}

												List<BaseRupInfo> listaRup = new ArrayList<BaseRupInfo>();
												if (!"U".equals(ruolo)) {
													listaRup = this.simogMapper.getAllRupBySa(codUffInt);
												} 
												origineSAInfo.setListaRup(listaRup);
												entry.setOrigineSAInfo(origineSAInfo);
												entry.setCigInDb(iscigIndb);
												entry.setCodGara(codgara);
												if (garaType.getIDGARA() != null) {
													entry.setIdAvGara(garaType.getIDGARA().toString());
												}
												risultato.setEsito(true);
												risultato.setData(entry);
												return risultato;
											} else {
												risultato.setEsito(false);
												risultato.setErrorData(ERROR_PRESA_IN_CARICO_DELEGA);
												return risultato;
											}
										}

									} else if(checkCFSA == null || "1".equals(checkCFSA)){

										risultato.setErrorData("CIG di competenza di altra stazione appaltante");
										risultato.setEsito(false);
										return risultato;
									}

								} else if(checkCFSA == null || "1".equals(checkCFSA)){

									risultato.setErrorData("CIG di competenza di altra stazione appaltante");
									risultato.setEsito(false);
									return risultato;
								}

							}

							if (!consultaGaraBean.isCaricato() && StringUtils.isEmpty(consultaGaraBean.getMsg())) {
								String checkRup = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, CONTROLLO_RUP_CONFIG_CODE);
								if (cfRup == null || !cfRup.equals(garaType.getCFUTENTE())) {
									String ruolo = this.simogMapper.getRuoloBySyscon(syscon);
									if   ((checkRup == null || "1".equals(checkRup))&&(!("A".equals(ruolo)) && !"C".equals(ruolo))) {
										risultato.setErrorData("CIG di competenza di altro RUP");
										risultato.setEsito(false);
										return risultato;
									} else {
										List<String> codtecList = this.simogMapper
												.getTecniByCfAndSA(garaType.getCFUTENTE().toUpperCase(), codUffInt);
										String codtec = null;
										if(codtecList != null && codtecList.size() > 0) {
											codtec = codtecList.get(0);
										}
										if (codtec == null) {
											utente = new InfoUtenteEntry();

											codtec = schedeManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
											utente.setCf(garaType.getCFUTENTE().toUpperCase());
											utente.setCodtec(codtec);
											utente.setNominativo(garaType.getCFUTENTE().toUpperCase());
											utente.setCgentei(codUffInt);
											this.simogMapper.insertTecnico(utente);

										}
									}
								}
							
								consultaGaraBean = inserisciGaraDaSimog(consultaGaraBean.getCodiceCIG(), idAvGara,
										codUffInt, syscon, scheda, scheda.getDatiGara().getGara(),
										scheda.getDatiGara().getLotto());

							} else {

								consultaGaraBean.setCaricato(false);
								if (StringUtils.isNotEmpty(response.getErrorData())) {
									consultaGaraBean.setMsg(response.getErrorData());
								} else {
									if (consultaGaraBean.getMsg() == null) {
										consultaGaraBean
												.setMsg("Lotto non importato per risposta incompleta dai servizi ANAC");
									}
								}

								StringBuffer strBuf = new StringBuffer("Il CIG=");
								strBuf.append(consultaGaraBean.getCodiceCIG());
								strBuf.append(" con CFRUP= ");
								strBuf.append(cfRup);
								strBuf.append(" non e' stato caricato perche' Simog ha risposta con esito negativo");

								if (StringUtils.isNotEmpty(response.getErrorData())) {
									strBuf.append(" con il seguente messaggio di errore: ");
									strBuf.append(response.getErrorData());
								}

								logger.error(strBuf.toString());

							}

						}
					}

					if (consultaGaraBean.isCaricato()) {
						numeroLottiCaricati++;
					} else {
						if ("Lotto esistente".equals(consultaGaraBean.getMsg())) {
							numeroLottiEsistenti++;
						} else {
							numeroLottiConErrore++;
						}
					}

					listaConsultaGaraBean.set(i, consultaGaraBean);
				}

			} else {
				// Situazione limite: non c'e' nessun CIG da richiedere al WS WIMOG.
				logger.error("Situazione limite: non c'e' nessun CIG da richiedere a SIMOG");

				String idAvGaraMsg = isCig ? "indicata" : cigIdAvGara;
				String codiceCIGMsg = isCig ? cigIdAvGara : "*";

				String msg = "La gara " + idAvGaraMsg + " non è presente in simog.";
				if (!StringUtils.equals("*", codiceCIGMsg)) {
					msg = msg.concat(" e al relativo lotto con CIG=" + codiceCIGMsg);
				}
				risultato.setEsito(false);
				risultato.setErrorData(msg);
				return risultato;
			}
			String codGara = this.simogMapper.getCodGaraByIdAnacAndSA(idAvGara, codUffInt);
			checkImportoGara(codGara);
			ConsultaGaraEntry entry = new ConsultaGaraEntry();
			entry.setOperation(OP_CONSULTA_GARA);
			entry.setCodGara(codGara);
			entry.setListaLotti(listaConsultaGaraBean);
			entry.setIdAvGara(idAvGara);
			risultato.setEsito(true);

			if(scheda != null && scheda.getDatiGara() != null && scheda.getDatiGara().getGara() != null ) {
				if( !scheda.getDatiGara().getGara().getCFAMMINISTRAZIONE().equals(cfsa)) {
					SABaseEntry saBaseEntry = new SABaseEntry();
					String nomeSA = this.contrattiMapper.getNominativoSaByCf(scheda.getDatiGara().getGara().getCFAMMINISTRAZIONE());
					String nominativoSa = nomeSA == null? scheda.getDatiGara().getGara().getCFAMMINISTRAZIONE() : nomeSA + " - " + scheda.getDatiGara().getGara().getCFAMMINISTRAZIONE();
					saBaseEntry.setNome(nominativoSa);
					entry.setOrigineSAInfo(saBaseEntry);
				}
			}
			boolean perfezionata = true;
			if(scheda != null && scheda.getDatiGara() != null && scheda.getDatiGara().getGara() != null ) {
				perfezionata = scheda.getDatiGara().getGara().getDATAPERFEZIONAMENTOBANDO() != null;
			}
			entry.setPerfezionata(perfezionata);
			
			risultato.setData(entry);
			
		} catch (Exception e) {
			logger.error("Errore in importa dati da SIMOG", e);
			throw e;
		}

		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void checkImportoGara(String codgara) {
		if(codgara == null) {
			return;
		}
		GaraEntry g = this.contrattiMapper.dettaglioGara(Long.valueOf(codgara));
		if(g != null && g.getImportoGara() < 0) {
			List<Double> importi = this.contrattiMapper.getImportoTot(Long.valueOf(codgara));
			Double sommaImporti = 0D;
			for (Double imp : importi) {
				if(imp != null) {
					sommaImporti = imp + sommaImporti;
				}
			}
			this.contrattiMapper.updateImportoGara(Long.valueOf(codgara),sommaImporti);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ConsultaGaraBean inserisciGaraDaSimog(final String codiceCIG, final String idAvGara, final String codUffInt,
			final Long syscon, final SchedaType schedaType, GaraType garaType, LottoType lottoType) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("inserisciGaraDaSimog: inizio metodo");
		}
		String checkCFSA = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, CONTROLLO_CF_SA_CONFIG_CODE);
		ConsultaGaraBean consultaGaraBean = new ConsultaGaraBean(codiceCIG, true);
		String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");	

		long idGara = garaType.getIDGARA();
		String cfUffInt = null;
		String cfAnac = null;
		if("ORA".equals(dbms)) {
			cfUffInt = (String) this.sqlMapper
				.executeReturnString("select CFEIN from UFFINT where CODEIN = '" + codUffInt + "' and rownum = 1");
			cfAnac = (String) this.sqlMapper
				.executeReturnString("select CFANAC from UFFINT where CODEIN = '" + codUffInt + "' and rownum = 1");
		} else {
			cfUffInt = (String) this.sqlMapper
					.executeReturnString("select CFEIN from UFFINT where CODEIN = '" + codUffInt + "' limit 1");
				cfAnac = (String) this.sqlMapper
					.executeReturnString("select CFANAC from UFFINT where CODEIN = '" + codUffInt + "' limit 1");
		}
		String importSchedeString = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, IMPORT_SCHEDE_CONFIG_CODE);
		boolean importaSchede = false;
		if (StringUtils.isNotBlank(importSchedeString)) {
			importaSchede = "1".equals(importSchedeString);
		}		
		if (((StringUtils.isEmpty(idAvGara) || (("" + garaType.getIDGARA()).equals(idAvGara)))
				&& ((garaType.getCFAMMINISTRAZIONE().equals(cfUffInt) || garaType.getCFAMMINISTRAZIONE().equals(cfAnac))))||( "0".equals(checkCFSA))) {

			
			
			boolean perfezionato = garaType.getDATAPERFEZIONAMENTOBANDO() != null;
			
			String acceptNonPerfezionato = this.simogMapper.getConfig(CONFIG_CODICE_APP, CONFIG_CHIAVE_ACCETTA_LOTTO_NON_PERFEZIONATO);
			if(!perfezionato && "0".equals(acceptNonPerfezionato)) {
				consultaGaraBean.setCaricato(false);
				consultaGaraBean.setMsg("Il lotto non è perfezionato");
				return consultaGaraBean;				
			}
			
			// Flag che indica se in DB esiste la gara con idAvGara specificato dall'utente
			boolean esisteGara = this.isGaraEsistente("" + garaType.getIDGARA(), codUffInt);

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Inizio import del xml fornito dal WS dopo controllo su IDAVGARA e CF stazione appaltante");
				logger.debug("Nella base dati " + (esisteGara ? "esiste" : "non esiste") + " la gara con IDAVGARA = '"
						+ idAvGara + "'");
			}

			Long codGara = null;
			if (esisteGara) {
				codGara = Long
						.parseLong(this.sqlMapper.execute("select CODGARA from W9GARA where idAVGARA=" + idGara + " and CODEIN='" + codUffInt + "'"  ) + "");
			} else {
				codGara = this.wgcManager.getNextId("W9GARA");
			}

			// Estrazione del campo TECNI.CODTEC per valorizzare i campi W9GARA.RUP e
			// W9LOTT.RUP
			String codTec = null;
			String cfRup = null;
			if (schedaType != null && schedaType.getDatiScheda() != null
					&& schedaType.getDatiScheda().getDatiComuni() != null) {
				cfRup = schedaType.getDatiScheda().getDatiComuni().getCFRUP();
			}
			if (StringUtils.isEmpty(cfRup)) {
				if (garaType != null) {
					cfRup = garaType.getCFUTENTE();
				}
			}

			if("ORA".equals(dbms)) {
				codTec = this.sqlMapper.executeReturnString("select CODTEC from TECNI where CGENTEI='" + codUffInt
						+ "' and UPPER(CFTEC)='" + cfRup.toUpperCase() + "' and rownum = 1");
			} else {
				codTec = this.sqlMapper.executeReturnString("select CODTEC from TECNI where CGENTEI='" + codUffInt
						+ "' and UPPER(CFTEC)='" + cfRup.toUpperCase() + "' limit 1");
			}

			boolean esisteTecnico = StringUtils.isNotEmpty(codTec);

			if (!esisteTecnico) {
				InfoUtenteEntry utente = new InfoUtenteEntry();

				String codicetec = schedeManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
				utente.setCf(cfRup.toUpperCase());
				utente.setCodtec(codicetec);
				utente.setNominativo(cfRup.toUpperCase());
				utente.setCgentei(codUffInt);
				utente.setSyscon(syscon);
				this.simogMapper.insertTecnico(utente);

			}
			if (!esisteGara) {

				GaraInsertForm garaInsertForm = new GaraInsertForm();
				garaInsertForm.setCodgara(codGara);
				garaInsertForm.setSyscon(syscon);
				garaInsertForm.setOggetto(garaType.getOGGETTO());
				garaInsertForm.setProvenienzaDato(2L);
				garaInsertForm.setCodiceTecnico(codTec);
				garaInsertForm.setImportoGara(garaType.getIMPORTOGARA().doubleValue());
				garaInsertForm.setNumLotti(1L);
				garaInsertForm.setCodiceStazioneAppaltante(codUffInt);
				garaInsertForm.setRicostruzioneAlluvione(null);
				garaInsertForm.setSituazione(1L);
				garaInsertForm.setPrevBando("2");
				garaInsertForm.setModalitaIndizioneAllegato9(garaType.getALLEGATOIX() != null ? Long.valueOf(garaType.getALLEGATOIX()) : null);
				garaInsertForm.setMotivoSommaUrgenza(garaType.getESTREMAURGENZA() != null ? Long.valueOf(garaType.getESTREMAURGENZA()) : null);
				if (garaType.getDURATAACCQUADROCONVENZIONEGARA() != null) {
					garaInsertForm.setDurataAcquadro(Long.valueOf(garaType.getDURATAACCQUADROCONVENZIONEGARA()));
				}
				if (garaType.getFLAGSAAGENTEGARA() != null) {
					garaInsertForm.setFlagSaAgente(
							FlagSNType.S.toString().equals(garaType.getFLAGSAAGENTEGARA().toString()) ? "1" : "2");
				} else {
					garaInsertForm.setFlagSaAgente("2");
				}
				garaInsertForm.setDenomSoggStazioneAppaltante(garaType.getDENAMMAGENTEGARA());
				garaInsertForm.setCfStazioneAppaltante(garaType.getCFAMMAGENTEGARA());
				Long verSimog = null;

//				Calcolo versione SIMOG:
//				
//				Se Gara.DATA_CREAZIONE <23/06/2019 à 1
//
//				Altrimenti
//
//				Se Gara.DATA_CREAZIONE <21/10/2019 à2
//
//				Altrimenti
//
//				Se Gara.DATA_PERFEZIONAMENTO_BANDO<01/01/2020 à 3
//
//				Altrimenti
//
//				Se Gara.DATA_CREAZIONE < 12/05/2020 à4
//
//				Altrimenti à5

				if (garaType.getDATACREAZIONE() != null) {
					Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
					Date dateCompareVer1 = new SimpleDateFormat("dd/MM/yyyy").parse("23/06/2019");
					Date dateCompareVer2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2019");
					if (dataCreazione.before(dateCompareVer1)) {
						verSimog = 1L;
					}
					if (dataCreazione.after(dateCompareVer1) && dataCreazione.before(dateCompareVer2)) {
						verSimog = 2L;
					}

					garaInsertForm.setDataCreazione(dataCreazione);
				}
				if (garaType.getDATAPERFEZIONAMENTOBANDO() != null) {
					Date dataPerfezionamento = garaType.getDATAPERFEZIONAMENTOBANDO().toGregorianCalendar().getTime();

					if (verSimog == null) {
						Date dateCompareVer3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020");
						if (dataPerfezionamento.before(dateCompareVer3)) {
							verSimog = 3L;
						}
					}
					garaInsertForm.setDataPubblicazione(dataPerfezionamento);
				}
				if (garaType.getDATACREAZIONE() != null && verSimog == null) {
					Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
					Date dateCompareVer4 = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2020");
					if (dataCreazione.before(dateCompareVer4)) {
						verSimog = 4L;
					}
				}
				if (garaType.getDATACREAZIONE() != null && verSimog == null) {
					Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
					Date dataSimog = new SimpleDateFormat("dd/MM/yyyy").parse("10/12/2020");
					if (dataCreazione.before(dataSimog)) {
						verSimog = 5L;
					}
				}

				verSimog = verSimog == null ? 6L : verSimog;
				garaInsertForm.setVersioneSimog(verSimog);

				if (verSimog < 5L && schedaType.getDatiScheda() != null) {

					garaInsertForm.setFlagSaAgente(
							FlagSNType.S.equals(schedaType.getDatiScheda().getDatiComuni().getFLAGSAAGENTE()) ? "1"
									: "2");
					garaInsertForm.setDenomSoggStazioneAppaltante(
							schedaType.getDatiScheda().getDatiComuni().getDENAMMAGENTE());
					garaInsertForm.setCfStazioneAppaltante(schedaType.getDatiScheda().getDatiComuni().getCFAMMAGENTE());
				} else {
					garaInsertForm.setFlagSaAgente(FlagSNType.S.equals(garaType.getFLAGSAAGENTEGARA()) ? "1" : "2");
					garaInsertForm.setDenomSoggStazioneAppaltante(garaType.getDENAMMAGENTEGARA());
					garaInsertForm.setCfStazioneAppaltante(garaType.getCFAMMAGENTEGARA());
				}

				if (garaType.getIDGARA() != 0) {

					garaInsertForm.setIdentificativoGara(garaType.getIDGARA());
				}

				if (StringUtils.isNotEmpty(garaType.getMODOREALIZZAZIONE())) {
					garaInsertForm.setTipoApp(new Long(garaType.getMODOREALIZZAZIONE()));
				}

				if (StringUtils.isNotEmpty(garaType.getCIGACCQUADRO())) {
					garaInsertForm.setCigQuadro(garaType.getCIGACCQUADRO());
				}

				if (StringUtils.isNotEmpty(garaType.getMODOINDIZIONE())) {
					garaInsertForm.setModalitaIndizione(new Long(garaType.getMODOINDIZIONE()));

				}

				if (garaType.getTIPOSCHEDA() != null) {
					garaInsertForm.setTipoSettore(garaType.getTIPOSCHEDA().toString());
				}

				String codiceCentroCostoFromSIMOG = garaType.getIDSTAZIONEAPPALTANTE();
				String descrCentroCostoFromSIMOG = garaType.getDENOMSTAZIONEAPPALTANTE();

				String idCdcString = this.sqlMapper
						.executeReturnString("select IDCENTRO from CENTRICOSTO where UPPER(CODCENTRO)='"
								+ codiceCentroCostoFromSIMOG.toUpperCase() + "' and CODEIN='" + codUffInt + "'");

				Long idCentroCosto = idCdcString == null ? null : new Long(idCdcString);

				if (idCentroCosto == null) {

					String maxIdCentroCostoString = this.sqlMapper
							.executeReturnString("select max(IDCENTRO)+1 from CENTRICOSTO");

					if (maxIdCentroCostoString == null) {
						idCentroCosto = new Long(1);
					} else {
						idCentroCosto = new Long(maxIdCentroCostoString);
					}
					this.simogMapper.insertCdc(idCentroCosto, codUffInt, codiceCentroCostoFromSIMOG,
							StringUtils.substring(descrCentroCostoFromSIMOG, 0, 250));

				} else {
					this.simogMapper.updateCdc(idCentroCosto, StringUtils.substring(descrCentroCostoFromSIMOG, 0, 250));
				}

				garaInsertForm.setIdCentroDiCosto(idCentroCosto);

				if (garaType.getURGENZADL133() != null) {
				garaInsertForm.setSommaUrgenza(FlagSNType.S.equals(garaType.getURGENZADL133()) ? 1L : 2L);
				} else {
					garaInsertForm.setSommaUrgenza(2L);
				}

				if (garaType.getIDFDELEGATE() != null) {
					garaInsertForm.setIdFDelegate(Long.valueOf(garaType.getIDFDELEGATE()));
				}

				this.simogMapper.insertGara(garaInsertForm);

				// Pubblicazione
				if (schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getPubblicazione() != null) {
					PubblicazioneType pubblicazione = schedaType.getDatiScheda().getPubblicazione();

					PubblicazioneInsertForm pubbInsertForm = new PubblicazioneInsertForm();

					pubbInsertForm.setCodgara(codGara);
					pubbInsertForm.setCodlott(1L);
					pubbInsertForm.setNum_appa(1L);
					pubbInsertForm.setNum_pubb(1L);
					
					this.simogMapper.deletePubblicazione(codGara);
					
					if (pubblicazione.getDATAGUCE() != null) {
						pubbInsertForm.setData_guce(pubblicazione.getDATAGUCE().toGregorianCalendar().getTime());
					}
					if (pubblicazione.getDATAGURI() != null) {
						pubbInsertForm.setData_guri(pubblicazione.getDATAGURI().toGregorianCalendar().getTime());
					}

					if (pubblicazione.getDATAALBO() != null) {
						pubbInsertForm.setData_albo(pubblicazione.getDATAALBO().toGregorianCalendar().getTime());
					}

					if (pubblicazione.getQUOTIDIANINAZ() > 0) {
						pubbInsertForm.setQuotidiani_naz(new Long(pubblicazione.getQUOTIDIANINAZ()));
					}

					if (pubblicazione.getQUOTIDIANIREG() > 0) {
						pubbInsertForm.setQuotidiani_reg(new Long(pubblicazione.getQUOTIDIANIREG()));
					}

					if (pubblicazione.getPROFILOCOMMITTENTE() != null) {
						pubbInsertForm.setProfilo_committente(
								FlagSNType.S.equals(pubblicazione.getPROFILOCOMMITTENTE()) ? "1" : "2");
					} else {
						pubbInsertForm.setProfilo_committente("2");
					}

					if (pubblicazione.getSITOMINISTEROINFTRASP() != null) {
						pubbInsertForm.setSito_ministero_inf_trasp(
								FlagSNType.S.equals(pubblicazione.getSITOMINISTEROINFTRASP()) ? "1" : "2");
					} else {
						pubbInsertForm.setSito_ministero_inf_trasp("2");
					}

					if (pubblicazione.getSITOOSSERVATORIOCP() != null) {
						pubbInsertForm.setSito_osservatorio_cp(
								FlagSNType.S.equals(pubblicazione.getSITOOSSERVATORIOCP()) ? "1" : "2");
					} else {
						pubbInsertForm.setSito_osservatorio_cp("2");
					}

					if (pubblicazione.getDATABORE() != null) {
						pubbInsertForm.setData_bore(pubblicazione.getDATABORE().toGregorianCalendar().getTime());
					}

					if (pubblicazione.getPERIODICI() > 0) {
						pubbInsertForm.setPeriodici(new Long(pubblicazione.getPERIODICI()));
					}

					// Inserimento W9PUBB
					this.simogMapper.insertPubblicazione(pubbInsertForm);
				}
			}
			Long codLott;
			String codLottString = this.sqlMapper
					.executeReturnString("select max(CODLOTT)+1 from W9LOTT where CODGARA=" + codGara);
			if (codLottString == null) {
				codLott = new Long(1);
			} else {
				codLott = new Long(codLottString);
			}

			Long numeroLotti = Long.parseLong(
					this.sqlMapper.executeReturnString("select count(*) from W9LOTT where CODGARA=" + codGara));
			numeroLotti++;

			LottoInsertForm lottoInsertForm = new LottoInsertForm();

			lottoInsertForm.setCodgara(codGara);
			lottoInsertForm.setCodlott(codLott);
			lottoInsertForm.setOggetto(lottoType.getOGGETTO());
			lottoInsertForm.setRup(codTec);

			if (lottoType.getSOMMAURGENZA() != null) {
			lottoInsertForm.setSomma_urgenza(FlagSNType.S.equals(lottoType.getSOMMAURGENZA()) ? "1" : "2");
			} else {
				lottoInsertForm.setSomma_urgenza("2");
			}

			Long counterCpvSec = 1L;
			if (lottoType.getCPVSecondaria() != null) {
				for (CPVSecondariaType cpvSec : lottoType.getCPVSecondaria()) {
					this.simogMapper.insertCpvSecondariLotto(codGara, codLott, counterCpvSec,
							cpvSec.getCODCPVSECONDARIA());
					counterCpvSec++;
				}
			}

			if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
				lottoInsertForm.setImporto_lotto(new Double(lottoType.getIMPORTOLOTTO().doubleValue()
						- new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA())));
			} else {
				lottoInsertForm.setImporto_lotto(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
			}

			lottoInsertForm.setCpv(lottoType.getCPV());
			lottoInsertForm.setId_scelta_contraente(Long.parseLong(lottoType.getIDSCELTACONTRAENTE()));
			lottoInsertForm.setId_scelta_contraente_50(getIdSceltaContraente50(lottoType.getIDSCELTACONTRAENTE()));

			String categoria = this.sqlMapper.executeReturnString("select CODSITAT from W9CODICI_AVCP where CODAVCP = '"
					+ lottoType.getIDCATEGORIAPREVALENTE() + "'  and TABCOD = 'W3z03' ");
			// se manca corrispondenza inserisco il codice non decodificato
			if (StringUtils.isEmpty(categoria)) {
				categoria = lottoType.getIDCATEGORIAPREVALENTE();
			}
			lottoInsertForm.setId_categoria_prevalente(categoria);
			lottoInsertForm.setNlotto(codLott);
			lottoInsertForm.setCig(codiceCIG);
			lottoInsertForm.setSituazione(new Long(SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione()));
			lottoInsertForm.setData_consulta_gara(new Date());
			if (garaType.getTIPOSCHEDA() != null) {
				lottoInsertForm.setFlag_ente_speciale(garaType.getTIPOSCHEDA().toString());
			}

			lottoInsertForm.setDaexport("1");

			if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
				lottoInsertForm.setImporto_attuazione_sicurezza(new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()));
			} else {
				lottoInsertForm.setImporto_attuazione_sicurezza(new Double(0));
			}

			lottoInsertForm.setImporto_tot(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
			String clascat = "";

			double importoLotto = lottoType.getIMPORTOLOTTO().doubleValue();
			if (importoLotto <= 258000) {
				clascat = "I";
			} else if (importoLotto <= 516000) {
				clascat = "II";
			} else if (importoLotto <= 1033000) {
				clascat = "III";
			} else if (importoLotto <= 1500000) {
				clascat = "IIIB";
			} else if (importoLotto <= 2582000) {
				clascat = "IV";
			} else if (importoLotto <= 3500000) {
				clascat = "IVB";
			} else if (importoLotto <= 5165000) {
				clascat = "V";
			} else if (importoLotto <= 10329000) {
				clascat = "VI";
			} else if (importoLotto <= 15494000) {
				clascat = "VII";
			} else {
				clascat = "VIII";
			}
			lottoInsertForm.setClascat(clascat);

			if (lottoType.getTIPOCONTRATTO() != null) {
				lottoInsertForm.setTipo_contratto(lottoType.getTIPOCONTRATTO().toString());
				if (lottoType.getTIPOCONTRATTO().toString().equalsIgnoreCase("L")) {
					lottoInsertForm.setManod("1");
				} else {
					lottoInsertForm.setManod("2");
				}
			} else {
				lottoInsertForm.setManod("2");
			}

			lottoInsertForm.setComcon("2");
			if (lottoType.getLUOGOISTAT() != null) {
				String luogoIstat = lottoType.getLUOGOISTAT();

				if (luogoIstat.length() == 6) {
					String codiceIstatRegione = (String) this.sqlMapper.executeReturnString(
							"select distinct codistat from g_comuni where codistat like " + "'%".concat(luogoIstat) + "'");
					if (StringUtils.isNotEmpty(codiceIstatRegione)) {
						luogoIstat = codiceIstatRegione;
					} else {
						luogoIstat = null;
					}
				}
				lottoInsertForm.setLuogo_istat(luogoIstat);
			}
			if (lottoType.getLUOGONUTS() != null) {
				String tmp = this.verificaNUTS(lottoType.getLUOGONUTS());
				if (tmp != null)
					lottoInsertForm.setLuogo_nuts(tmp);
				else
					lottoInsertForm.setLuogo_nuts(lottoType.getLUOGONUTS());
			}
			if (lottoType.getFLAGESCLUSO() != null && "S".equals(lottoType.getFLAGESCLUSO().toString())) {
				Long verSimog = getVersioneSimog(garaType);
				if (verSimog <= 2L) {
					lottoInsertForm.setArt_e1("1");
				} else {
					lottoInsertForm.setArt_e1("2");
				}
			} else {
				lottoInsertForm.setArt_e1("2");
			}

			lottoInsertForm.setArt_e2("2");

			if (lottoType.getCUPLOTTO() != null) {
				CUPLOTTOType cupLotto = lottoType.getCUPLOTTO();

				// Per ora si legge solo il primo codice CUP presente nell'array CODICICUP
				// e si suppone che il CUP sia legato al lotto a cui e' associato
				String cup = cupLotto.getCODICICUP().isEmpty() ? "" : cupLotto.getCODICICUP().get(0).getCUP();
				lottoInsertForm.setCup(cup);
				lottoInsertForm.setCupesente("2");

				if(cupLotto.getCODICICUP().size() > 0) {
					Long prog = 1L;
					for (DatiCUPType oggettoCup : cupLotto.getCODICICUP()) {
						this.simogMapper.insertW9lottcup(codGara, codLott, prog, oggettoCup.getCUP(),oggettoCup.getDATIDIPE());
						prog++;
					}
				}
				
			} else {
				lottoInsertForm.setCupesente("1");
			}

			if (lottoType.getDATAPUBBLICAZIONE() != null) {
				lottoInsertForm.setData_pubblicazione(lottoType.getDATAPUBBLICAZIONE().toGregorianCalendar().getTime());
			}

			if (schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getDatiComuni() != null) {
				if (StringUtils.isNotEmpty(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE())) {
					lottoInsertForm.setId_scheda_locale(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE());
				} else {
					lottoInsertForm.setId_scheda_locale(codiceCIG);
				}
				if (StringUtils.isNotEmpty(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDASIMOG())) {
					lottoInsertForm.setId_scheda_simog(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDASIMOG());
				}
			} else {
				lottoInsertForm.setId_scheda_locale(codiceCIG);
			}

			Date date = new GregorianCalendar(2013, 10 - 1, 29).getTime();
			String exsottosoglia = "2";
			if (lottoType.getIMPORTOLOTTO() != null && lottoType.getIMPORTOLOTTO().doubleValue() < 150000
					&& garaType.getDATAPERFEZIONAMENTOBANDO() != null
					&& garaType.getDATAPERFEZIONAMENTOBANDO().toGregorianCalendar().before(date)) {
				exsottosoglia = "1";
			}
			lottoInsertForm.setExsottosoglia(exsottosoglia);

			Long versioneSimog = getVersioneSimog(garaType);

			String contrattoEsclusoAlleggerito = null;
			String esclusioneRegimeSpeciale = null;

			if (versioneSimog != null && versioneSimog > 2L) {
				if (lottoType.getFLAGESCLUSO() != null) {
					contrattoEsclusoAlleggerito = "S".equals(lottoType.getFLAGESCLUSO()) ? "1" : "2";
				} else {
					contrattoEsclusoAlleggerito = "2";
				}

				if (lottoType.getFLAGREGIME() != null) {
					esclusioneRegimeSpeciale = FlagSNType.S.equals(lottoType.getFLAGREGIME()) ? "1" : "2";
				} else {
					esclusioneRegimeSpeciale = "2";
				}
			}

			lottoInsertForm.setContrattoEsclusoAlleggerito(contrattoEsclusoAlleggerito);
			lottoInsertForm.setEsclusioneRegimeSpeciale(esclusioneRegimeSpeciale);			
			if(lottoType.getDATALETTERAINVITO() != null) {
				this.contrattiMapper.updateDataLetteraInvitoGara(codGara,lottoType.getDATALETTERAINVITO().toGregorianCalendar().getTime());
			}
			
			lottoInsertForm.setQualificazioneStazioneAppaltante(lottoType.getDEROGAQUALIFICAZIONESA() != null ? Long.valueOf(lottoType.getDEROGAQUALIFICAZIONESA()) : null);
			
			lottoInsertForm.setCategoriaMerceologica(lottoType.getCATEGORIAMERC() != null ? Long.valueOf(lottoType.getCATEGORIAMERC()) : null);
			
			if(lottoType.getFLAGPREVEDERIP() != null) {
				if(FlagSNType.S.equals(lottoType.getFLAGPREVEDERIP())) {
					lottoInsertForm.setFlagPrevedeRip("1");
				} else {
					lottoInsertForm.setFlagPrevedeRip("2");
				}
			}
			
			lottoInsertForm.setDurataRinnovi(lottoType.getDURATARINNOVI() != null ? Long.valueOf(lottoType.getDURATARINNOVI()) : null);
			
			lottoInsertForm.setMotivoCollegamento(lottoType.getIDMOTIVOCOLLCIG() != null ? Long.valueOf(lottoType.getIDMOTIVOCOLLCIG()) : null);
						
			lottoInsertForm.setCigOrigineRip(lottoType.getCIGORIGINERIP());
			
			if(lottoType.getFLAGPNRRPNC() != null) {
				if(FlagSNType.S.equals(lottoType.getFLAGPNRRPNC())) {
					lottoInsertForm.setFlagPnrrPnc("1");
				} else {
					lottoInsertForm.setFlagPnrrPnc("2");
				}
			}
			if(lottoType.getFLAGPREVISIONEQUOTA() != null) {
				lottoInsertForm.setFlagPrevisioneQuota(lottoType.getFLAGPREVISIONEQUOTA().value());
			}
			if(lottoType.getFLAGMISUREPREMIALI() != null) {
				if(FlagSNType.S.equals(lottoType.getFLAGMISUREPREMIALI())) {
					lottoInsertForm.setFlagMisurePreliminari("1");
				} else {
					lottoInsertForm.setFlagMisurePreliminari("2");
				}
			}			
			if (lottoType.getDATASCADENZAPAGAMENTI() != null) {
				lottoInsertForm.setDataScadenzaPagamenti(lottoType.getDATASCADENZAPAGAMENTI().toGregorianCalendar().getTime());
			}
			
			
			this.simogMapper.insertLotto(lottoInsertForm);

			// Inserimento W9LOTTCATE
			if (lottoType.getCATEGORIE() != null) {
				CategLottoType categorieLotto = lottoType.getCATEGORIE(); // categorieLotto.getCATEGORIAArray();
				if (categorieLotto.getCATEGORIA() != null && categorieLotto.getCATEGORIA().size() > 0) {
					Object[] sqlLottCateParams = new Object[4];
					sqlLottCateParams[0] = codGara; // W9GARA.CODGARA con IDAVGARA = codiceGara
					sqlLottCateParams[1] = codLott; // progressivo per lotto

					long numCate = 1;
					for (int i = 0; i < categorieLotto.getCATEGORIA().size(); i++) {
						sqlLottCateParams[2] = new Long(numCate + i); // progressivo per categoria

						// tabella W9CODICI_AVCP contiene codici per
						// decodificare ID_CATEGORIA_PREVALENTE e CLASCAT
						categoria = (String) this.sqlMapper
								.executeReturnString("select CODSITAT from W9CODICI_AVCP where CODAVCP='"
										+ categorieLotto.getCATEGORIA().get(i) + "'  and TABCOD='W3z03' ");

						if (categoria == null) {
							categoria = categorieLotto.getCATEGORIA().get(i);
						}
						sqlLottCateParams[3] = categoria;
						this.simogMapper.insertLottoCate(new Long(sqlLottCateParams[0] + ""),
								new Long(sqlLottCateParams[1] + ""), new Long(sqlLottCateParams[2] + ""),
								sqlLottCateParams[3] + "");
					}
				}
			}

			if (lottoType.getTipiAppaltoLav() != null && lottoType.getTipiAppaltoLav().size() > 0) {

				Object[] sqlW9AppaLavParams = new Object[4];
				sqlW9AppaLavParams[0] = codGara;
				sqlW9AppaLavParams[1] = codLott; // progressivo per lotto

				for (int i = 0; i < lottoType.getTipiAppaltoLav().size(); i++) {
					sqlW9AppaLavParams[2] = new Long((i + 1)); // progressivo
					sqlW9AppaLavParams[3] = new Long(lottoType.getTipiAppaltoLav().get(i).getIDAPPALTO());
					this.simogMapper.insertAppaLav(new Long(sqlW9AppaLavParams[0] + ""),
							new Long(sqlW9AppaLavParams[1] + ""), new Long(sqlW9AppaLavParams[2] + ""),
							new Long(sqlW9AppaLavParams[3] + ""));
				}
			}

			if (lottoType.getTipiAppaltoForn() != null && lottoType.getTipiAppaltoForn().size() > 0) {

				Object[] sqlW9AppaFornParams = new Object[4];
				sqlW9AppaFornParams[0] = codGara;
				sqlW9AppaFornParams[1] = codLott; // progressivo per lotto

				for (int i = 0; i < lottoType.getTipiAppaltoForn().size(); i++) {
					sqlW9AppaFornParams[2] = new Long((i + 1)); // progressivo
					sqlW9AppaFornParams[3] = new Long(lottoType.getTipiAppaltoForn().get(i).getIDAPPALTO());
					this.simogMapper.insertAppaForn(new Long(sqlW9AppaFornParams[0] + ""),
							new Long(sqlW9AppaFornParams[1] + ""), new Long(sqlW9AppaFornParams[2] + ""),
							new Long(sqlW9AppaFornParams[3] + ""));
				}
			}

			if (esisteGara) {
				// Aggiornamento W9GARA
				this.simogMapper.updateLottiGara(codGara, numeroLotti);
			}

			if (this.isStazioneAppaltanteConPermessiDaApplicareInCreazione(codUffInt)) {

				// Inserimento dei ruolo per il RUP, altrimenti con la gestione dei
				// permessi attiva nessun utente accederebbe alla gara/lotto
				// QUI

				this.simogMapper.insertW9Inca(codGara, codLott, new Long(1), new Long(1), "RA", 14L, codTec);

				this.simogMapper.insertW9Inca(codGara, codLott, new Long(1), new Long(2), "RA", 16L, codTec);

				this.simogMapper.insertW9Inca(codGara, codLott, new Long(1), new Long(3), "RA", 16L, codTec);

				this.simogMapper.insertW9Inca(codGara, codLott, new Long(1), new Long(4), "RA", 17L, codTec);
			}

			if (StringUtils.isNotBlank(garaType.getIDFDELEGATE()) && ("1".equals(garaType.getIDFDELEGATE())
					|| "2".equals(garaType.getIDFDELEGATE()) || "4".equals(garaType.getIDFDELEGATE()))) {

			if ("1".equals(garaType.getIDFDELEGATE()) || "2".equals(garaType.getIDFDELEGATE())) {
				// inserimento W9ESITO
					this.schedeManager.istanziaEsito(codGara, codLott, schedaType);
					ResponsabiliType responsabiliType = schedaType.getResponsabili();
					SchedaCompletaType schedaCompleta = this.schedeManager.getSchedaCompleta(schedaType);
					if (this.schedeManager.hasAggiudicazione(schedaCompleta)) {
						this.schedeManager.istanziaAggiudicazione(codGara, codLott, codUffInt, schedaCompleta,
								schedaType.getAggiudicatari(), responsabiliType);
			}
				}

			if ("4".equals(garaType.getIDFDELEGATE())) {
				// inserimento W9ESITO
					this.schedeManager.istanziaEsito(codGara, codLott, schedaType);
			}

			} else {
				if (importaSchede) {
					schedeManager.insertSchedeLotto(codGara, codLott, codUffInt, schedaType);
				}
			}

			Long situazioneLotto = getSituazioneLotto(codGara, codLott);
			this.simogMapper.updateSituazioneLotto(codGara, codLott, situazioneLotto);
			Long situazioneGara = getSituazioneGara(codGara, codLott);
			this.simogMapper.updateSituazioneGara(codGara, situazioneGara);

			consultaGaraBean.setCaricato(true);
			consultaGaraBean.setMsg("Lotto importato");

			// resultMap.put("esito", Boolean.TRUE);

		} else {

			if ((StringUtils.isNotEmpty(idAvGara) && !("" + garaType.getIDGARA()).equals(idAvGara))
					&& garaType.getCFAMMINISTRAZIONE().equals(cfUffInt)) {
				logger.error(
						"La richiesta consultaGara ha restituito una gara con IDGARA diverso da quello digitato dall'utente");

				consultaGaraBean.setCaricato(false);
				consultaGaraBean.setMsg("Il lotto fa riferimento ad un Numero Gara ANAC diverso da quello indicato");

			} else if (((StringUtils.isEmpty(idAvGara)
					|| (garaType.getIDGARA() > 0 && ("" + garaType.getIDGARA()).equals(idAvGara)))
					&& (!garaType.getCFAMMINISTRAZIONE().equals(cfUffInt))) && (checkCFSA == null || "1".equals(checkCFSA))) {
				logger.error(
						"La richiesta consultaGara e' stata interrotta per il seguente motivo: la stazione appaltante "
								+ "associata al codice CIG e' differente da quella da cui si sta effettuando l’operazione, cioe' il codice fiscale "
								+ "della stazione appaltante in uso (UFFINT.CFEIN) e' diverso dal campo CF_AMMINISTRAZIONE ritornato dal WS dell'AVCP.");

				consultaGaraBean.setCaricato(false);
				consultaGaraBean
						.setMsg("Il lotto fa riferimento ad una stazione appaltante diversa da quella indicata");

			} else if ((StringUtils.isEmpty(idAvGara)
					|| (garaType.getIDGARA() > 0 && ("" + garaType.getIDGARA()).equals(idAvGara)))
					&& garaType.getCFAMMINISTRAZIONE().equals(cfUffInt) && (lottoType.getDATACANCELLAZIONELOTTO() != null)) {
				logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: "
						+ "il codice CIG indicato fa riferimento ad un lotto non più esistente.");
				consultaGaraBean.setCaricato(false);
				consultaGaraBean.setMsg("Il lotto non e' più esistente");
			} else {
				logger.error(
						"La richiesta consultaGara e' stata interrotta per piu' motivi: al codice CIG e' associata "
								+ "una gara con codice gara diverso da quello digitato, la stazione appaltante associata al "
								+ "codice CIG e' differente da quella da cui si sta effettuando l’operazione (cioe' il codice fiscale "
								+ "della stazione appaltante in uso (UFFINT.CFEIN) e' diverso dal campo CF_AMMINISTRAZIONE ritornato "
								+ "dal WS dell'AVCP) e il codice CIG indicato fa riferimento ad un lotto non perfezionato.");

				consultaGaraBean.setCaricato(false);
				consultaGaraBean.setMsg("Il lotto non importabile");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("consultaGaraUnificatoWS: fine metodo");
		}

		return consultaGaraBean;
	}

	private String verificaNUTS(String codiceNUTS) {
		String result = null;

		if ("ITF46".equals(codiceNUTS.toUpperCase())) {
			result = "ITF41";
		} else if ("ITF47".equals(codiceNUTS.toUpperCase())) {
			result = "ITF42";
		} else if ("ITF4C".equals(codiceNUTS.toUpperCase())) {
			result = "ITF45";
		} else if (codiceNUTS.toUpperCase().startsWith("ITH")) {
			result = StringUtils.replace(codiceNUTS, "ITD", "ITH");
		} else if (codiceNUTS.toUpperCase().startsWith("ITE")) {
			result = StringUtils.replace(codiceNUTS, "ITE", "ITI");
		}

		return result;
	}

	private Long getIdSceltaContraente50(String IdSceltaContraente) {
		Long result = null;
		if (IdSceltaContraente != null) {
			if (IdSceltaContraente.equals("1")) {
				return new Long(1);
			} else if (IdSceltaContraente.equals("2")) {
				return new Long(12);
			} else if (IdSceltaContraente.equals("4")) {
				return new Long(6);
			} else if (IdSceltaContraente.equals("6")) {
				return new Long(17);
			} else if (IdSceltaContraente.equals("7")) {
				return null;
			} else if (IdSceltaContraente.equals("8")) {
				return new Long(18);
			} else if (IdSceltaContraente.equals("9")) {
				return new Long(5);
			} else if (IdSceltaContraente.equals("10")) {
				return new Long(6);
			} else if (IdSceltaContraente.equals("11")) {
				return null;
			} else if (IdSceltaContraente.equals("12")) {
				return new Long(2);
			} else if (IdSceltaContraente.equals("13")) {
				return new Long(12);
			} else if (IdSceltaContraente.equals("14")) {
				return null;
			} else if (IdSceltaContraente.equals("15")) {
				return new Long(2);
			} else if (IdSceltaContraente.equals("16")) {
				return new Long(2);
			} else if (IdSceltaContraente.equals("17")) {
				return new Long(9);
			} else if (IdSceltaContraente.equals("18")) {
				return new Long(10);
			} else if (IdSceltaContraente.equals("19")) {
				return new Long(11);
			} else if (IdSceltaContraente.equals("20")) {
				return new Long(13);
			} else if (IdSceltaContraente.equals("25")) {
				return null;
			} else if (IdSceltaContraente.equals("31")) {
				return new Long(2);
			}
		}
		return result;
	}

	private boolean isGaraEsistente(final String idGara, String codUffint) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("isGaraEsistente: inizio metodo");
		}

		boolean result = false;

		try {
			Integer numeroOccorrenze = this.sqlMapper
					.execute("select COUNT(CODGARA) from W9GARA where IDAVGARA='" + idGara + "' and codein='"+ codUffint +"'" );

			if (numeroOccorrenze != null && numeroOccorrenze > 0) {
				result = true;
			}
		} catch (Exception e) {
			logger.error("Errore nel determinare l'esistenza di una gara con codice gara " + "specificato (IDAVGARA = '"
					+ idGara + "')", e);
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isGaraEsistente: fine metodo");
		}
		return result;
	}

	private boolean isStazioneAppaltanteConPermessiDaApplicareInCreazione(String codein) {
		Long numeroRecord = new Long(this.sqlMapper.executeReturnString(
				"select count (*) from W9CF_MODPERMESSI_SA where CODEIN='" + codein + "' and APPLICA='1'"));
		if (numeroRecord != null && numeroRecord > 0) {
			return true;
		} else {
			return false;
		}
	}

//	public void allineamentoRichiesteEliminazione() {
//
//		List<FlussoEntry> listaFlussiRichiesteEliminazione = this.simogMapper.getListaFlussiEliminati();
//		Long codgara, codlotto, fase, num, idflusso;
//		String cfsa, cig, cfrup, idGara;
//		Calendar dataInvio = null;
//		if (listaFlussiRichiesteEliminazione != null && !listaFlussiRichiesteEliminazione.isEmpty()) {
//			for (FlussoEntry flusso : listaFlussiRichiesteEliminazione) {
//
//				codgara = flusso.getKey01();
//				codlotto = flusso.getKey02();
//				fase = flusso.getKey03();
//				num = flusso.getKey04();
//				cfsa = flusso.getCodiceFiscaleSA();
//				idflusso = flusso.getId();
//				if (flusso.getDataInvio() != null) {
//					dataInvio = GregorianCalendar.getInstance();
//					dataInvio.setTimeInMillis(flusso.getDataInvio().getTime());
//				}
//
//				idGara = null;
//				cig = null;
//				cfrup = null;
//				if (codgara != null && codlotto != null && fase != null && num != null && cfsa != null
//						&& dataInvio != null) {
//					idGara = this.simogMapper.getIdGaraByCod(codgara);
//					cig = this.simogMapper.getLottoCig(codgara, codlotto);
//					cfrup = this.simogMapper.getLottoRup(codgara, codlotto);
//
//					if (idGara != null && cig != null && cfrup != null) {
//
//						FaseEsecuzioneType faseEsecuzione = new FaseEsecuzioneType();
//						faseEsecuzione.setCodiceFase(fase.toString());
//						faseEsecuzione.setNum(num.intValue());
//						GetElencoFeedback request = new GetElencoFeedback();
//						request.setIdgara(idGara);
//						request.setCfrup(cfrup);
//						request.setCfsa(cfsa);
//						request.setCig(cig);
//						request.setFaseEsecuzione(faseEsecuzione);
//						request.setTipoFeedBack("3");
//
//						GetElencoFeedbackResponse response = (GetElencoFeedbackResponse) soapConnector
//								.callWebService(urlSimog, request);
//						ResponseElencoFeedback responseElencoFeedBack = response.getElencoFeedback();
//						List<FeedbackType> elenco = null;
//
//						if (responseElencoFeedBack.isSuccess() && responseElencoFeedBack.getElencoFeedback() != null
//								&& responseElencoFeedBack.getElencoFeedback().size() > 0) {
//							elenco = responseElencoFeedBack.getElencoFeedback();
//						} else {
//							if (!responseElencoFeedBack.isSuccess()) {
//								logger.error(
//										"La chiamata getElencoSchede al WsOsservatorio e' terminato con il seguente errore: "
//												+ responseElencoFeedBack.getError());
//							} else {
//								if (responseElencoFeedBack.getElencoFeedback() == null) {
//									logger.error(
//											"La chiamata getElencoFeedback al WsOsservatorio e' terminato successo, ma la risposta "
//													+ "non contiene alcun dato (L'elencoFeedbak dentro l'oggetto responseElencoFeedback e' null)"
//													+ "Messaggio di errore: " + responseElencoFeedBack.getError());
//								} else if (responseElencoFeedBack.getElencoFeedback().size() == 0) {
//									logger.error(
//											"La chiamata getElencoFeedback al WsOsservatorio e' terminato successo, ma la risposta "
//													+ "non contiene alcun dato (L'elencoFeedback dentro l'oggetto responseElencoFeedback ha dimensione 0)"
//													+ "Messaggio di errore: " + responseElencoFeedBack.getError());
//								}
//							}
//						}
//
//						if (elenco != null && elenco.size() > 0) {
//							for (FeedbackType feedBack : elenco) {
//								String esito = feedBack.getEsito();
//
//								if (feedBack.getData().toGregorianCalendar().getTime()
//										.compareTo(dataInvio.getTime()) >= 0) {
//									String motivoCancellazione = "";
//									if (feedBack.getMessaggio() != null) {
//										motivoCancellazione = feedBack.getMessaggio().get(0);
//									}
//									if (esito != null) {
//										if (esito.equals("2")) {
//											// Andata a buon fine
//											try {
//
//												this.allineamentoService.executeAllineaSuccess(codgara, codlotto, fase,
//														num, motivoCancellazione, idflusso);
//
//											} catch (Throwable t) {
//												logger.error(
//														"Allineamento richiesta eliminazione: Errore su esito POSITIVO",
//														t);
//											}
//										} else if (esito.equals("3")) {
//											// errore
//											try {
//
//												this.allineamentoService.executeAllineaFailure(codgara, codlotto, fase,
//														num, motivoCancellazione);
//
//											} catch (Throwable t) {
//												logger.error(
//														"Allineamento richiesta eliminazione: Errore su esito ERRORE",
//														t);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}

	private static final String CONFIG_CODICE_APP = "W9";
	private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";
	private static final String CONFIG_CHIAVE_ACCETTA_LOTTO_NON_PERFEZIONATO = "AccettaLottiNonPerfezionati";
	
	
	public String getJWTKey() throws CriptazioneException {

		String criptedKey = this.simogMapper.getConfig(CONFIG_CODICE_APP, CONFIG_CHIAVE_APP);
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

	@Transactional(propagation = Propagation.REQUIRED)
	public BaseResponse riallineaGaraDaSimog(String idAvGara, final String codUffInt, String authorization,
			String loginMode) throws SQLException, Exception {

		ResponseImportaGara risultato = new ResponseImportaGara();
		if (logger.isDebugEnabled()) {
			logger.debug("riallineaGaraDaSimog: inizio metodo");
		}
		try {

			String codgara = this.simogMapper.getCodGaraByIdAnacAndSA(idAvGara, codUffInt);
			
			if (idAvGara == null) {
				risultato.setEsito(false);
				risultato.setErrorData("La gara non può essere riallineata in quanto non è presente su SIMOG");
				return risultato;
			}

			List<String> cigLottiGara = this.simogMapper.getCigLottiGara(new Long(codgara));
			boolean garaAggiornata = false;

			if (cigLottiGara != null && cigLottiGara.size() > 0) {

				for (String cig : cigLottiGara) {

					ResponseConsultaGaraIdGara response = anacrestclientmanager.consultaGara(cig);
					if (response != null) {

						if (response.isEsito()) {
							// inserimento diretto nel DB del cig

							SchedaType scheda = response.getData();

							GaraType garaType = scheda.getDatiGara().getGara();
							LottoType lottoType = scheda.getDatiGara().getLotto();

							if (!garaAggiornata) {

								GaraInsertForm form = new GaraInsertForm();
								form.setCodgara(new Long(codgara));
								form.setOggetto(garaType.getOGGETTO());
								form.setImportoGara(garaType.getIMPORTOGARA() == null ? null
										: garaType.getIMPORTOGARA().doubleValue());
								form.setTipoApp(garaType.getMODOREALIZZAZIONE() == null ? null
										: new Long(garaType.getMODOREALIZZAZIONE()));
								form.setModalitaIndizione(garaType.getMODOINDIZIONE() == null ? null
										: new Long(garaType.getMODOINDIZIONE()));
								form.setCigQuadro(garaType.getCIGACCQUADRO());
								form.setDurataAcquadro(garaType.getDURATAACCQUADROCONVENZIONEGARA() == null ? null
										: garaType.getDURATAACCQUADROCONVENZIONEGARA().longValue());

								form.setModalitaIndizioneAllegato9(garaType.getALLEGATOIX() != null ? Long.valueOf(garaType.getALLEGATOIX()) : null);
								form.setMotivoSommaUrgenza(garaType.getESTREMAURGENZA() != null ? Long.valueOf(garaType.getESTREMAURGENZA()) : null);
								if (garaType.getIDFDELEGATE() != null) {
									form.setIdFDelegate(Long.valueOf(garaType.getIDFDELEGATE()));
								}
								Long verSimog = null;

//								Calcolo versione SIMOG:
//								
//								Se Gara.DATA_CREAZIONE <23/06/2019 à 1
								//
//								Altrimenti
								//
//								Se Gara.DATA_CREAZIONE <21/10/2019 à2
								//
//								Altrimenti
								//
//								Se Gara.DATA_PERFEZIONAMENTO_BANDO<01/01/2020 à 3
								//
//								Altrimenti
								//
//								Se Gara.DATA_CREAZIONE < 12/05/2020 à4
								//
//								Altrimenti à5

								if (garaType.getDATACREAZIONE() != null) {
									Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
									form.setDataCreazione(dataCreazione);
									Date dateCompareVer1 = new SimpleDateFormat("dd/MM/yyyy").parse("23/06/2019");
									Date dateCompareVer2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2019");
									if (dataCreazione.before(dateCompareVer1)) {
										verSimog = 1L;
									}
									if (dataCreazione.after(dateCompareVer1) && dataCreazione.before(dateCompareVer2)) {
										verSimog = 2L;
									}
								}
								if (garaType.getDATAPERFEZIONAMENTOBANDO() != null) {
									Date dataPerfezionamento = garaType.getDATAPERFEZIONAMENTOBANDO()
											.toGregorianCalendar().getTime();

									if (verSimog == null) {
										Date dateCompareVer3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020");
										if (dataPerfezionamento.before(dateCompareVer3)) {
											verSimog = 3L;
										}
									}
									form.setDataPubblicazione(dataPerfezionamento);
								}
								if (garaType.getDATACREAZIONE() != null && verSimog == null) {
									Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
									Date dateCompareVer4 = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2020");
									if (dataCreazione.before(dateCompareVer4)) {
										verSimog = 4L;
									}
								}
								if (garaType.getDATACREAZIONE() != null && verSimog == null) {
									Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
									Date dataSimog = new SimpleDateFormat("dd/MM/yyyy").parse("10/12/2020");
									if (dataCreazione.before(dataSimog)) {
										verSimog = 5L;
									}
								}

								verSimog = verSimog == null ? 6L : verSimog;
								form.setVersioneSimog(verSimog);

								if (garaType.getURGENZADL133() != null) {
									form.setSommaUrgenza(FlagSNType.S.equals(garaType.getURGENZADL133()) ? 1L : 2L);
								} else {
									form.setSommaUrgenza(2L);
								}

								String codiceCentroCostoFromSIMOG = garaType.getIDSTAZIONEAPPALTANTE();
								String descrCentroCostoFromSIMOG = garaType.getDENOMSTAZIONEAPPALTANTE();

								if (StringUtils.isNotBlank(codiceCentroCostoFromSIMOG)) {
									Long idCentroCosto = this.simogMapper
											.getIdCdc(codiceCentroCostoFromSIMOG.toUpperCase(), codUffInt);

									if (idCentroCosto == null) {

										idCentroCosto = this.simogMapper.getMaxIdCdc();

										this.simogMapper.insertCdc(idCentroCosto, codUffInt, codiceCentroCostoFromSIMOG,
												StringUtils.substring(descrCentroCostoFromSIMOG, 0, 250));
									} else {
										this.simogMapper.updateCdc(idCentroCosto,
												StringUtils.substring(descrCentroCostoFromSIMOG, 0, 250));
									}

									form.setIdCentroDiCosto(idCentroCosto);
								}

								this.simogMapper.updateGara(form);
								garaAggiornata = true;
							}

							if (canUpdateLotto(codgara, cig, lottoType, garaType)) {

								Double importoAttuazioneSicurezza = 0d;
								if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
									importoAttuazioneSicurezza = new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA());
								}
								Double importoLotto = null;
								if (lottoType.getIMPORTOLOTTO() != null) {
									importoLotto = lottoType.getIMPORTOLOTTO().doubleValue();
								}

								LottoInsertForm lottoInsertForm = new LottoInsertForm();
								Long codLotto = this.simogMapper.getCodLottoByCig(cig);
								
								if (lottoType.getCUPLOTTO() != null) {
									CUPLOTTOType cupLotto = lottoType.getCUPLOTTO();

									// Per ora si legge solo il primo codice CUP presente nell'array CODICICUP
									// e si suppone che il CUP sia legato al lotto a cui e' associato
									String cup = cupLotto.getCODICICUP().isEmpty() ? ""
											: cupLotto.getCODICICUP().get(0).getCUP();
									lottoInsertForm.setCup(cup);
									lottoInsertForm.setCupesente("2");
									
									if(cupLotto.getCODICICUP().size() > 0) {
										Long prog = 1L;
										this.simogMapper.deleteW9lottcup(Long.valueOf(codgara), codLotto);
										for (DatiCUPType oggettoCup : cupLotto.getCODICICUP()) {
											this.simogMapper.insertW9lottcup(Long.valueOf(codgara), codLotto, prog, oggettoCup.getCUP(),oggettoCup.getDATIDIPE());
											prog++;
										}
									}
									
								} else {
									lottoInsertForm.setCupesente("1");
								}
								lottoInsertForm.setOggetto(lottoType.getOGGETTO());
								if (lottoType.getFLAGESCLUSO() != null
										&& "S".equals(lottoType.getFLAGESCLUSO().toString())) {
									Long verSimog = getVersioneSimog(garaType);
									if (verSimog <= 2L) {
										lottoInsertForm.setArt_e1("1");
									} else {
										lottoInsertForm.setArt_e1("2");
									}
								} else {
									lottoInsertForm.setArt_e1("2");
								}

								lottoInsertForm.setImporto_tot(importoLotto);
								lottoInsertForm.setImporto_attuazione_sicurezza(importoAttuazioneSicurezza);
								if (importoAttuazioneSicurezza != null && importoLotto != null) {
									lottoInsertForm.setImporto_lotto(importoLotto - importoAttuazioneSicurezza);
								}
								lottoInsertForm.setCpv(lottoType.getCPV());
								String categoria = this.sqlMapper
										.executeReturnString("select CODSITAT from W9CODICI_AVCP where CODAVCP = '"
												+ lottoType.getIDCATEGORIAPREVALENTE() + "'  and TABCOD = 'W3z03' ");
								// se manca corrispondenza inserisco il codice non decodificato
								if (StringUtils.isEmpty(categoria)) {
									categoria = lottoType.getIDCATEGORIAPREVALENTE();
								}
								lottoInsertForm.setId_categoria_prevalente(categoria);
								lottoInsertForm.setId_scelta_contraente(lottoType.getIDSCELTACONTRAENTE() == null ? null
										: new Long(lottoType.getIDSCELTACONTRAENTE()));
								lottoInsertForm.setFlag_ente_speciale(
										garaType.getTIPOSCHEDA() == null ? null : garaType.getTIPOSCHEDA().toString());
								lottoInsertForm.setTipo_contratto(lottoType.getTIPOCONTRATTO());
								lottoInsertForm.setCig(lottoType.getCIG());
								lottoInsertForm.setCodgara(new Long(codgara));

								lottoInsertForm.setQualificazioneStazioneAppaltante(lottoType.getDEROGAQUALIFICAZIONESA() != null ? Long.valueOf(lottoType.getDEROGAQUALIFICAZIONESA()) : null);
								lottoInsertForm.setCategoriaMerceologica(lottoType.getCATEGORIAMERC() != null ? Long.valueOf(lottoType.getCATEGORIAMERC()) : null);
								if(lottoType.getFLAGPREVEDERIP() != null) {
									if(FlagSNType.S.equals(lottoType.getFLAGPREVEDERIP())) {
										lottoInsertForm.setFlagPrevedeRip("1");
									} else {
										lottoInsertForm.setFlagPrevedeRip("2");
									}
								}
								lottoInsertForm.setDurataRinnovi(lottoType.getDURATARINNOVI() != null ? Long.valueOf(lottoType.getDURATARINNOVI()) : null);
								lottoInsertForm.setMotivoCollegamento(lottoType.getIDMOTIVOCOLLCIG() != null ? Long.valueOf(lottoType.getIDMOTIVOCOLLCIG()) : null);
								lottoInsertForm.setCigOrigineRip(lottoType.getCIGORIGINERIP());
								if(lottoType.getFLAGPNRRPNC() != null) {
									if(FlagSNType.S.equals(lottoType.getFLAGPNRRPNC())) {
										lottoInsertForm.setFlagPnrrPnc("1");
									} else {
										lottoInsertForm.setFlagPnrrPnc("2");
									}
								}
								if(lottoType.getFLAGPREVISIONEQUOTA() != null) {
									lottoInsertForm.setFlagPrevisioneQuota(lottoType.getFLAGPREVISIONEQUOTA().value());
								}
								if(lottoType.getFLAGMISUREPREMIALI() != null) {
									if(FlagSNType.S.equals(lottoType.getFLAGMISUREPREMIALI())) {
										lottoInsertForm.setFlagMisurePreliminari("1");
									} else {
										lottoInsertForm.setFlagMisurePreliminari("2");
									}
								}
								if (lottoType.getDATASCADENZAPAGAMENTI() != null) {
									lottoInsertForm.setDataScadenzaPagamenti(lottoType.getDATASCADENZAPAGAMENTI().toGregorianCalendar().getTime());
								}

								if (scheda.getDatiScheda() != null && scheda.getDatiScheda().getDatiComuni() != null) {
									if (StringUtils
											.isNotEmpty(scheda.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE())) {
										lottoInsertForm.setId_scheda_locale(
												scheda.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE());
									} else {
										lottoInsertForm.setId_scheda_locale(lottoType.getCIG());
									}
									if (StringUtils
											.isNotEmpty(scheda.getDatiScheda().getDatiComuni().getIDSCHEDASIMOG())) {
										lottoInsertForm.setId_scheda_simog(
												scheda.getDatiScheda().getDatiComuni().getIDSCHEDASIMOG());
									}
								} else {
									lottoInsertForm.setId_scheda_locale(lottoType.getCIG());
								}

								if (lottoType.getSOMMAURGENZA() != null) {
									lottoInsertForm.setSomma_urgenza(
											FlagSNType.S.equals(lottoType.getSOMMAURGENZA()) ? "1" : "2");
								} else {
									lottoInsertForm.setSomma_urgenza("2");
								}
								
								Long versioneSimog = getVersioneSimog(garaType);

								String contrattoEsclusoAlleggerito = null;
								String esclusioneRegimeSpeciale = null;

								if (versioneSimog != null && versioneSimog > 2L) {
									if (lottoType.getFLAGESCLUSO() != null) {
										contrattoEsclusoAlleggerito = "S".equals(lottoType.getFLAGESCLUSO()) ? "1"
												: "2";
									} else {
										contrattoEsclusoAlleggerito = "2";
									}

									if (lottoType.getFLAGREGIME() != null) {
										esclusioneRegimeSpeciale = FlagSNType.S.equals(lottoType.getFLAGREGIME()) ? "1"
												: "2";
									} else {
										esclusioneRegimeSpeciale = "2";
									}
								}
								
								lottoInsertForm.setContrattoEsclusoAlleggerito(contrattoEsclusoAlleggerito);
								lottoInsertForm.setEsclusioneRegimeSpeciale(esclusioneRegimeSpeciale);								
								if(lottoType.getDATALETTERAINVITO() != null) {
									this.contrattiMapper.updateDataLetteraInvitoGara(lottoInsertForm.getCodgara(),lottoType.getDATALETTERAINVITO().toGregorianCalendar().getTime());
								}
								this.simogMapper.updateLotto(lottoInsertForm);

							} else {
								risultato.setEsito(false);
								risultato.setErrorData(
										"ATTENZIONE: le schede già inserite sono incompatibili con i dati presenti in SIMOG. Contattare un amministratore");
								return risultato;
							}

						} else {

							StringBuffer strBuf = new StringBuffer("Il CIG=");
							strBuf.append(cig);
							strBuf.append(" non e' stato caricato perche' Simog ha risposta con esito negativo");

							if (StringUtils.isNotEmpty(response.getErrorData())) {
								strBuf.append(" con il seguente messaggio di errore: ");
								strBuf.append(response.getErrorData());
							}
							logger.error(strBuf.toString());
							risultato.setEsito(false);
							risultato.setErrorData(strBuf.toString());
							return risultato;
						}
					}
				}

			} else {
				// Situazione limite: non c'e' nessun CIG da richiedere al WS WIMOG.
				logger.error("Situazione limite: non c'e' nessun CIG da richiedere a SIMOG");
				String msg = "Nella gara non esistono lotti da aggiornare";
				risultato.setEsito(false);
				risultato.setErrorData(msg);
				return risultato;
			}
			risultato.setEsito(true);
			
			checkImportoGara(codgara);
		} catch (Exception e) {
			logger.error("Errore in riallinea dati a SIMOG. idavgara:" + idAvGara, e);
			throw e;
		}

		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private boolean canUpdateLotto(String codGara, String cig, LottoType lottoType, GaraType garatype)
			throws Exception {

		try {
			Long codLotto = this.simogMapper.getCodLottoByCig(cig);
			List<FaseEntry> fasiLotto = this.simogMapper.getFasiLotto(new Long(codGara), codLotto);

			Set<Long> codFaseList = new HashSet<Long>();

			for (FaseEntry fase : fasiLotto) {
				codFaseList.add(fase.getFase());
			}

			boolean s2, e1, saq, aaq;

			Date date = new GregorianCalendar(2013, 10 - 1, 29).getTime();
			
			Date dateCreation = new GregorianCalendar(2019, 10 - 1, 21).getTime();
			
			String exsottosoglia = this.contrattiMapper.getSottosogliaLotto(new Long(codGara), codLotto);
			String artE1 = null;
			Long verSimog = getVersioneSimog(garatype);
			if (lottoType.getFLAGESCLUSO() != null
					&& "S".equals(lottoType.getFLAGESCLUSO().toString())) {
				
				if (verSimog <= 2L) {
					artE1 = "1";
				} else {
					artE1 = "2";
				}
			} else {
				artE1 = "2";
			}
			
			s2 = exsottosoglia == null || ("2".equals(exsottosoglia) && "2".equals(artE1));
			e1 = lottoType.getFLAGESCLUSO() != null && "S".equals(lottoType.getFLAGESCLUSO().toString()) && garatype.getDATACREAZIONE() != null 
					&& garatype.getDATACREAZIONE().toGregorianCalendar().getTime().before(dateCreation);
			saq = "9".equals(garatype.getMODOREALIZZAZIONE()) || "17".equals(garatype.getMODOREALIZZAZIONE()) || "18".equals(garatype.getMODOREALIZZAZIONE());
			aaq = "11".equals(garatype.getMODOREALIZZAZIONE()) || "21".equals(garatype.getMODOREALIZZAZIONE());

		
			if((e1 || !s2 ) && (codFaseList.contains(1L) || codFaseList.contains(2L)))
				return false;
			
			if ((s2 || !e1 )
					&& ( (codFaseList.contains(986L) || codFaseList.contains(987L))))
				return false;

			if (!saq && codFaseList.contains(11L))
				return false;

			if (saq
					&& (codFaseList.contains(986L) || codFaseList.contains(2L)))
				return false;

			if (!aaq  && codFaseList.contains(12L))
				return false;

			if (aaq 
					&& (codFaseList.contains(987L) || codFaseList.contains(1L)))
				return false;

		} catch (Exception e) {
			logger.error("Errore in metodo che calcola se un lotto possa essere modificato o meno", e);
			throw e;
		}
		return true;
	}

	public ResponseCheckMigrazione checkMigrazione(String idAvGara, Long syscon, String cfStazioneAppaltante,
			String authorization, String loginMode) {

		ResponseCheckMigrazione risultato = new ResponseCheckMigrazione();
		if (logger.isDebugEnabled()) {
			logger.debug("riallineaGaraDaSimog: inizio metodo");
		}
		try {
			if (idAvGara == null) {
				risultato.setEsito(false);
				risultato.setErrorData("La gara non può essere migrata in quanto non è presente su SIMOG");
				return risultato;
			}
			List<String> codiceSAList = this.simogMapper.getCodiceSaByCf(cfStazioneAppaltante);
			String codiceSA = null;
			if(codiceSAList != null && codiceSAList.size()>0) {
				codiceSA = codiceSAList.get(0);
			}
			
			String codgara = this.simogMapper.getCodGaraByIdAnacAndSA(idAvGara, codiceSA);
			List<String> cigLottiGara = this.simogMapper.getCigLottiGara(new Long(codgara));
			if (cigLottiGara == null || cigLottiGara.size() == 0) {
				risultato.setErrorData(ResponseCheckMigrazione.MIGRAZIONE_GARA_NO_LOTTI);
				risultato.setEsito(false);
				return risultato;
			}

			ResponseConsultaGaraIdGara response = anacrestclientmanager.consultaGara(cigLottiGara.get(0));
			if (response != null) {

				if (response.isEsito()) {
					// inserimento diretto nel DB del cig

					SchedaType scheda = response.getData();

					GaraType garaType = scheda.getDatiGara().getGara();
					String cfNuovaSA = garaType.getCFAMMINISTRAZIONE();
					if (cfNuovaSA.equalsIgnoreCase(cfStazioneAppaltante)) {
						risultato.setEsito(false);
						risultato.setErrorData(ResponseCheckMigrazione.GARA_NON_MIGRATA);
						return risultato;
					}
					List<SABaseEntry> saBAseInfoList = this.simogMapper.getBaseSAInfoByCf(cfNuovaSA);
					SABaseEntry saBAseInfo = null;
					if(saBAseInfoList != null && saBAseInfoList.size() > 0) {
						saBAseInfo = saBAseInfoList.get(0);
					}
					if (saBAseInfo == null || saBAseInfo.getCodice() == null) {
						risultato.setEsito(false);
						risultato.setErrorData(ResponseCheckMigrazione.SA_NON_ESISTENTE);
						return risultato;
					}
					String codein = null;
					String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");	
					if("ORA".equals(dbms)) {
						codein = (String) this.sqlMapper
												.executeReturnString("select codein from usr_ein where CODEIN = '" + saBAseInfo.getCodice()
														+ "' and syscon = " + syscon + " and rownum = 1");
					} else {
					    codein = (String) this.sqlMapper
							.executeReturnString("select codein from usr_ein where CODEIN = '" + saBAseInfo.getCodice()
									+ "' and syscon = " + syscon + " limit 1");
					}
					if (codein == null) {
						risultato.setEsito(false);
						risultato.setErrorData(ResponseCheckMigrazione.SA_NON_ABILITATA);
						return risultato;
					}

					String cfRupGara = garaType.getCFUTENTE();

					CheckMigrazioneGaraEntry checkMigrazioneGaraDto = new CheckMigrazioneGaraEntry();
					checkMigrazioneGaraDto.setStazioneAppaltante(saBAseInfo);
					checkMigrazioneGaraDto.setCodiceFiscaleRupGara(cfRupGara);

					risultato.setEsito(true);
					risultato.setData(checkMigrazioneGaraDto);
					return risultato;
				} else {
					logger.error("Errore in chechMigrazione gara: cfSA=" + cfStazioneAppaltante + " syscon = " + syscon
							+ " idgara= " + idAvGara + "." + response.getErrorData());
					risultato.setEsito(false);
					risultato.setErrorData(response.getErrorData());
				}
			}
		} catch (Exception e) {
			logger.error("Errore in chechMigrazione gara: cfSA=" + cfStazioneAppaltante + " syscon = " + syscon
					+ " idgara= " + idAvGara, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional
	public void allineamentoRichiesteEliminazione() {

		List<FlussoEntry> listaFlussiRichiesteEliminazione = this.simogMapper.getListaFlussiEliminati();
		Long codgara, codlotto, fase, num, idflusso;
		String cfsa, cig, cfrup, idGara;
		Calendar dataInvio = null;
		if (listaFlussiRichiesteEliminazione != null && !listaFlussiRichiesteEliminazione.isEmpty()) {
			for (FlussoEntry flusso : listaFlussiRichiesteEliminazione) {

				codgara = flusso.getKey01();
				codlotto = flusso.getKey02();
				fase = flusso.getKey03();
				num = flusso.getKey04();
				cfsa = flusso.getCodiceFiscaleSA();
				idflusso = flusso.getId();
				if (flusso.getDataInvio() != null) {
					dataInvio = GregorianCalendar.getInstance();
					dataInvio.setTimeInMillis(flusso.getDataInvio().getTime());
				}

				idGara = null;
				cig = null;
				cfrup = null;
				if (codgara != null && codlotto != null && fase != null && num != null && cfsa != null
						&& dataInvio != null) {
					idGara = this.simogMapper.getIdGaraByCod(codgara);
					cig = this.simogMapper.getLottoCig(codgara, codlotto);
					cfrup = this.simogMapper.getLottoRup(codgara, codlotto);

					if (idGara != null && cig != null && cfrup != null) {

						FaseEsecuzioneType faseEsecuzione = new FaseEsecuzioneType();
						faseEsecuzione.setCodiceFase(fase.toString());
						faseEsecuzione.setNum(num.intValue());
						GetElencoFeedback getElencoFeedback = new GetElencoFeedback();
						getElencoFeedback.setIdgara(idGara);
						getElencoFeedback.setCfrup(cfrup);
						getElencoFeedback.setCfsa(cfsa);
						getElencoFeedback.setCig(cig);
						getElencoFeedback.setFaseEsecuzione(faseEsecuzione);
						getElencoFeedback.setTipoFeedBack("3");
						ResponseFeedback responseFeedBack = anacrestclientmanager.getElencoFeedback(getElencoFeedback);
						List<FeedbackType> elenco = null;

						if (responseFeedBack.isEsito() && responseFeedBack.getData() != null
								&& responseFeedBack.getData().getElencoFeedback() != null
								&& responseFeedBack.getData().getElencoFeedback().size() > 0) {
							elenco = responseFeedBack.getData().getElencoFeedback();
						} else {
							if (!responseFeedBack.isEsito()) {
								logger.error(
										"La chiamata getElencoSchede al WsOsservatorio e' terminato con il seguente errore: "
												+ responseFeedBack.getErrorData());
							} else {
								if (responseFeedBack.getData() == null
										|| responseFeedBack.getData().getElencoFeedback() == null) {
									logger.error(
											"La chiamata getElencoFeedback al WsOsservatorio e' terminato successo, ma la risposta "
													+ "non contiene alcun dato (L'elencoFeedbak dentro l'oggetto responseElencoFeedback e' null)"
													+ "Messaggio di errore: " + responseFeedBack.getErrorData());
								} else if (responseFeedBack.getData().getElencoFeedback().size() == 0) {
									logger.error(
											"La chiamata getElencoFeedback al WsOsservatorio e' terminato successo, ma la risposta "
													+ "non contiene alcun dato (L'elencoFeedback dentro l'oggetto responseElencoFeedback ha dimensione 0)"
													+ "Messaggio di errore: " + responseFeedBack.getErrorData());
								}
							}
						}

						if (elenco != null && elenco.size() > 0) {
							for (FeedbackType feedBack : elenco) {
								String esito = feedBack.getEsito();

								if (feedBack.getData() != null && feedBack.getData().toGregorianCalendar().getTime()
										.compareTo(dataInvio.getTime()) >= 0) {
									String motivoCancellazione = "";
									if (feedBack.getMessaggio() != null && !feedBack.getMessaggio().isEmpty()) {
										motivoCancellazione = feedBack.getMessaggio().get(0);
									}
									if (esito != null) {
										if (esito.equals("2")) {
											// Andata a buon fine
											try {

												this.executeAllineaSuccess(codgara, codlotto, fase, num,
														motivoCancellazione, idflusso);

											} catch (Throwable t) {
												logger.error(
														"Allineamento richiesta eliminazione: Errore su esito POSITIVO",
														t);
											}
										} else if (esito.equals("3")) {
											// errore
											try {

												this.executeAllineaFailure(codgara, codlotto, fase, num,
														motivoCancellazione);

											} catch (Throwable t) {
												logger.error(
														"Allineamento richiesta eliminazione: Errore su esito ERRORE",
														t);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public void executeAllineaSuccess(Long codgara, Long codlotto, Long fase, Long num, String motivoCancellazione,
			Long idflusso) {
		// Copia del flusso da cancellare da W9FLUSSI a W9FLUSSI_ELIMINATI
		this.simogMapper.insertFlussoEliminato(codgara, codlotto, fase, num);

		// Aggiornamento di data e motivo cancellazione del flusso eliminato in
		// W9FLUSSI_ELIMINATI
		this.simogMapper.setMotivoFlussoEliminato(codgara, codlotto, fase, num, new Date(), motivoCancellazione);

		// Cancellazione flussi per la fase in SA
		this.simogMapper.deleteFlusso(codgara, codlotto, fase, num);

		// Aggiornamento tinvio flusso di cancellazione
		this.simogMapper.setCancellazioneEvasaFlusso(idflusso);

		// Aggiornamento daExport in W9FASI
		this.simogMapper.setW9faseDaExport(codgara, codlotto, fase, num, 1L);
		this.simogMapper.setIdSchedaSimogNull(codgara, codlotto, fase, num, 1L);

		// Aggiornamento della situazione della gara, solo se non ci sono altre
		// richieste di cancellazione sullo stesso lotto
		Long numeroRichiesteCancellazioni = this.simogMapper.countRichiesteCancellazioneLotto(codgara, codlotto);
		if (numeroRichiesteCancellazioni.longValue() == 0) {
			Long situazioneLotto = getSituazioneLotto(codgara, codlotto);
			this.simogMapper.updateSituazioneLotto(codgara, codlotto, situazioneLotto);
			Long situazioneGara = getSituazioneGara(codgara, codlotto);
			this.simogMapper.updateSituazioneGara(codgara, situazioneGara);
		}

		// inserimento nota in G_NOTEAVVISI
		NoteAvvisoEntry noteAvviso = new NoteAvvisoEntry();
		Long idAvviso = this.simogMapper.getMaxIdAvviso();
		if (idAvviso == null) {
			idAvviso = 1L;
		} else {
			idAvviso++;
		}
		noteAvviso.setNotecod(idAvviso);
		noteAvviso.setNoteprg("W9");
		noteAvviso.setNoteent("W9LOTT");
		noteAvviso.setNotekey1(codgara.toString());
		noteAvviso.setNotekey2(codlotto.toString());
		noteAvviso.setStatonota(1L);
		noteAvviso.setTiponota(3L);
		noteAvviso.setDatanota(new Date());
		noteAvviso.setTitolonota("Richiesta cancellazione");
		noteAvviso.setTestonota(motivoCancellazione);
		this.simogMapper.insertNoteAvviso(noteAvviso);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void executeAllineaFailure(Long codgara, Long codlotto, Long fase, Long num, String motivoCancellazione) {
		Long idflussoRichiestaCancellazione = this.simogMapper.getIdFlusso(codgara, codlotto, fase, num);

		Long idFlussoEliminato = this.simogMapper.getMaxIdFlussoEliminato();
		if (idFlussoEliminato == null || idFlussoEliminato == 0L) {
			idFlussoEliminato = 1L;
		} else {
			idFlussoEliminato++;
		}

		// Inserimento flusso di cancellazione in w9flussi_eliminati
		this.simogMapper.copyFlussoEliminato(idflussoRichiestaCancellazione, idFlussoEliminato);

		// aggiorno la data e la motivazione del flusso eliminato
		this.simogMapper.setMotivoFlussoEliminato(codgara, codlotto, fase, num, new Date(), motivoCancellazione);

		// elimino il flusso di cancellazione dalla w9flussi
		this.simogMapper.deleteFlussoCancellazione(codgara, codlotto, fase, num);

		// Aggiornamento daExport in W9FASI
		this.simogMapper.setW9faseDaExport(codgara, codlotto, fase, num, 2L);

		Long numeroRichiesteCancellazioni = this.simogMapper.countRichiesteCancellazioneLotto(codgara, codlotto);
		if (numeroRichiesteCancellazioni.longValue() == 0) {
			Long situazioneLotto = getSituazioneLotto(codgara, codlotto);
			this.simogMapper.updateSituazioneLotto(codgara, codlotto, situazioneLotto);
			Long situazioneGara = getSituazioneGara(codgara, codlotto);
			this.simogMapper.updateSituazioneGara(codgara, situazioneGara);
		}

		// inserimento nota in G_NOTEAVVISI
		NoteAvvisoEntry noteAvviso = new NoteAvvisoEntry();
		Long idAvviso = this.simogMapper.getMaxIdAvviso();
		if (idAvviso == null) {
			idAvviso = 1L;
		} else {
			idAvviso++;
		}
		noteAvviso.setNotecod(idAvviso);
		noteAvviso.setNoteprg("W9");
		noteAvviso.setNoteent("W9LOTT");
		noteAvviso.setNotekey1(codgara.toString());
		noteAvviso.setNotekey2(codlotto.toString());
		noteAvviso.setStatonota(1L);
		noteAvviso.setTiponota(3L);
		noteAvviso.setDatanota(new Date());
		noteAvviso.setTitolonota("Richiesta cancellazione");
		noteAvviso.setTestonota(motivoCancellazione);
		this.simogMapper.insertNoteAvviso(noteAvviso);
	}

	private Long getSituazioneGara(Long codGara, Long codLotto) {

		List<LottoFullEntry> lotti = this.simogMapper.getFullLottiGara(codGara);
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

	public boolean isCig(final String cigIdAvGara) {
		return Funzioni.controlloCIG(cigIdAvGara) == true;

	}

	public ResponseListaCollaborazioni getListaCollaborazioni(final String cfRup, String username, String password) {
		ResponseListaCollaborazioni risultato = new ResponseListaCollaborazioni();
		try {
			risultato = anacrestclientmanager.getListaCollaborazioni(cfRup, username, password);
		} catch (Exception e) {
			logger.error("Errore in getListaCollaborazioni", e);
			throw e;
		}
		return risultato;
	}

	public ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(final PresaInCaricoGaraDelegataForm form) {
		ResponsePresaInCaricoGaraDelegata risultato = new ResponsePresaInCaricoGaraDelegata();
		try {
			risultato = anacrestclientmanager.presaInCaricoGaraDelegata(form);
		} catch (Exception e) {
			logger.error("Errore in presaInCaricoGaraDelegata", e);
			throw e;
		}
		return risultato;
	}

	private Long getVersioneSimog(GaraType garaType) throws ParseException {
		Long verSimog = null;
		if (garaType.getDATACREAZIONE() != null) {
			Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
			Date dateCompareVer1 = new SimpleDateFormat("dd/MM/yyyy").parse("26/06/2019");
			Date dateCompareVer2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2019");
			if (dataCreazione.before(dateCompareVer1)) {
				verSimog = 1L;
			}
			if (dataCreazione.after(dateCompareVer1) && dataCreazione.before(dateCompareVer2)) {
				verSimog = 2L;
			}
		}
		if (garaType.getDATAPERFEZIONAMENTOBANDO() != null) {
			Date dataPerfezionamento = garaType.getDATAPERFEZIONAMENTOBANDO().toGregorianCalendar().getTime();

			if (verSimog == null) {
				Date dateCompareVer3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020");
				if (dataPerfezionamento.before(dateCompareVer3)) {
					verSimog = 3L;
				}
			}
		}
		if (garaType.getDATACREAZIONE() != null && verSimog == null) {
			Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
			Date dateCompareVer4 = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2020");
			if (dataCreazione.before(dateCompareVer4)) {
				verSimog = 4L;
			}
		}
		if (garaType.getDATACREAZIONE() != null && verSimog == null) {
			Date dataCreazione = garaType.getDATACREAZIONE().toGregorianCalendar().getTime();
			Date dataSimog = new SimpleDateFormat("dd/MM/yyyy").parse("10/12/2020");
			if (dataCreazione.before(dataSimog)) {
				verSimog = 5L;
			}
		}

		verSimog = verSimog == null ? 6L : verSimog;
		return verSimog;
	}

	public String getCodeinByCfein(String cfSA) {
		try {
			return this.sqlMapper.getCodeinByCf(cfSA);
		}catch (Exception e) {
			logger.error("Errore in lettura codein da cfein", e);
		}
		return null;
	}
	
	public LoginResult hasPermission(String user, String pwd){
		LoginResult risultato = new LoginResult();
		risultato.setEsito(true);
		String username = StringUtils.stripToNull(user);
		String password = StringUtils.stripToNull(pwd);
		if (username == null || password == null) {
			risultato = new LoginResult();
			risultato.setEsito(false);
			risultato.setErrorData(LoginResult.ERROR_USERNAME_PASSWORD_EMPTY);
		} else {
			username = username.toUpperCase();
			risultato = getLoginPubblica(username, password);
		}
		return risultato;
	}

	public LoginResult getLoginPubblica(String username, String password) {

		LoginResult risultato = new LoginResult();

		try {

			// recupero dati account
			String pwd = this.simogMapper.getAccountPwd(username);
			if (pwd != null) {
				// verifico se la password � corretta
				if (!isCorrectPassword(password, pwd)) {
					risultato.setEsito(false);
					risultato.setErrorData(LoginResult.ERROR_NOT_FOUND);
				} else {
					risultato.setEsito(true);
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(LoginResult.ERROR_NOT_FOUND);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante il getLoginPubblica username"
							+ username + ", password " + password, t);
			risultato.setEsito(false);
			risultato.setErrorData(LoginResult.ERROR_UNEXPECTED + ": "
					+ t.getMessage());
		}

		return risultato;
	}
	
	private Boolean isCorrectPassword(String passwordInChiaro, String passwordCriptata) {
		if (passwordInChiaro != null) {
			String passwordDecifrata = "";
			try {
				ICriptazioneByte decriptatorePsw = FactoryCriptazioneByte.getInstance("LEG",
						passwordCriptata.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
				passwordDecifrata = new String(decriptatorePsw.getDatoNonCifrato());
			} catch (Exception ex) {
				;
			}
			return passwordDecifrata.equals(passwordInChiaro);
		} else {
			return false;
		}
	}
	



}