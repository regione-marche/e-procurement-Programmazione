package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LoaderAppaltoUsrEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LogEventiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.DelegaInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.DelegaSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.SimogLoginOptions;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDettaglioDelega;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseInsert;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaCollaborazioni;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaDeleghe;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.DelegheMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.Collaborazione;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.AnacRestClientManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.mapper.SimogMapper;

@Component(value = "delegheManager")
public class DelegheManager extends AbstractManager{

	/** Logger di classe. */
	protected static Logger logger = LogManager.getLogger(DelegheManager.class);

	@Autowired
	protected DelegheMapper delegheMapper;
	
	@Autowired
	protected SimogMapper simogMapper;

	@Autowired
	protected SqlMapper sqlMapper;

	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected AnacRestClientManager anacrestclientmanager;


	@Value("${application.codiceProdotto}")
	private String CONFIG_CODICE_APP;

	private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

	private static final String CONFIG_CHIAVE_SIMOG_LOGIN = "it.eldasoft.simog.ws.login";
	private static final String CONFIG_CHIAVE_SIMOG_PASSWORD = "it.eldasoft.simog.ws.password";
	private static final String CONFIG_CHIAVE_SIMOG_RUOLO = "it.eldasoft.simog.ws.ruoloutente";
	
	public static final String LOG_EVENTO_DELEGHE = "DELEGHE";
	public static final String LOG_EVENTO_DELEGHE_DESC = "Attribuzione/Modifica/revoca delega ad utente con SYSCON=%s con RUOLO=%s";


	public ResponseListaDeleghe getListaDeleghe(DelegaSearchForm searchForm) {

		ResponseListaDeleghe risultato = new ResponseListaDeleghe();
		try {

			if(searchForm != null && StringUtils.isNotBlank(searchForm.getCfrup())) {
				searchForm.setCfrup(searchForm.getCfrup().toUpperCase());
			}
			
			int totalCount = this.delegheMapper.countSearchDeleghe(searchForm);
			if (searchForm.getTake() == 0) {
				searchForm.setSkip(0);
				searchForm.setTake(totalCount);
			}

			RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
			risultato.setTotalCount(totalCount);
			List<DelegaBaseEntry> deleghe = this.delegheMapper.searchDeleghe(searchForm, rowBounds);

			for (DelegaBaseEntry delega : deleghe) {
				if (delega.getRuolo() != null) {
					if (delega.getRuolo().intValue() == 1)
						delega.setDesRuolo("Sola compilazione");
					else if (delega.getRuolo().intValue() == 2)
						delega.setDesRuolo("Gestione completa");
				}
			}

			risultato.setData(deleghe);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get dei deleghe.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseDettaglioDelega dettaglioDelega(Integer id) {
		ResponseDettaglioDelega risultato = new ResponseDettaglioDelega();

		try {
			
			DelegaEntry delega = this.delegheMapper.getDelega(id);
			String nomeCollaboratore = this.contrattiMapper.getSysute(delega.getIdCollaboratore());
			delega.setNomeCollaboratore(nomeCollaboratore);
			risultato.setData(delega);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione del dettaglio del delega: id = " + id, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertDelega(DelegaInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		try {
			Long syscon = form.getSyscon();
			String cfSyscon = this.contrattiMapper.getCfFromSyscon(syscon);
			String stazioneAppaltante = form.getStazioneAppaltante();
			String codtec = null;

			// Setup PK sequence
			int id = this.delegheMapper.getMaxId();
			id++;
			form.setId(id);
			Short ruolo = 1;
			form.setRuolo(ruolo);
			// Carica tecnico, crea se non esiste
			List<RupEntry> tecList = this.contrattiMapper.getTecnicoByCfAndSaList(cfSyscon, stazioneAppaltante);
			RupEntry tecnicoPresente = tecList != null && !tecList.isEmpty() ? tecList.get(0) : null;
			
			if (tecnicoPresente == null) {
				// Crea nuovo tecnico
				codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
				RupEntry nuovoTecnico = new RupEntry();
				nuovoTecnico.setCodice(codtec);
				nuovoTecnico.setCf(cfSyscon);
				nuovoTecnico.setNominativo(cfSyscon);
				nuovoTecnico.setStazioneAppaltante(stazioneAppaltante);
				this.contrattiMapper.insertRUP(nuovoTecnico);
			} else {
				codtec = tecnicoPresente.getCodice();
			}

			// Imposta codice RUP
			form.setCfrup(cfSyscon);
			// Insert record
			this.delegheMapper.insertDelega(form);

			// Return PK
			risultato.setData(new Long(form.getId()));

			// Log eventi
			Short livento = 1;
			insertLogEventi(form.getSyscon(), form.getIdProfilo(), LOG_EVENTO_DELEGHE,
					String.format(LOG_EVENTO_DELEGHE_DESC, form.getIdCollaboratore(), form.getRuolo()), null, livento, null);

		} catch (Exception e) {
			logger.error("Errore in insert delega", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateDelega(DelegaInsertForm form) throws Exception {

		BaseResponse risultato = new BaseResponse();

		try {
			Short ruolo = 1;
			form.setRuolo(ruolo);
			this.delegheMapper.updateDelega(form);

			// Log eventi
			Short livento = 1;
			insertLogEventi(form.getSyscon(), form.getIdProfilo(), LOG_EVENTO_DELEGHE,
					String.format(LOG_EVENTO_DELEGHE_DESC, form.getIdCollaboratore(), form.getRuolo()),null,  livento, null);

			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la modifica del delega: id = " + form.getId(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteDelega(Long syscon, String idProfilo, Integer id) throws Exception {
		BaseResponse risultato = new BaseResponse();

		try {
			// Carica delega per poter fare i log
			DelegaEntry delega = this.delegheMapper.getDelega(id);

			this.delegheMapper.deleteDelega(id);
			risultato.setEsito(true);

			// Log eventi
			Short livento = 1;
			insertLogEventi(syscon, idProfilo, LOG_EVENTO_DELEGHE, String.format(LOG_EVENTO_DELEGHE_DESC,
					delega != null ? delega.getIdCollaboratore() : "NULL", delega != null ? delega.getRuolo() : "NULL"), null, livento, null);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la cancellazione del delega: id = " + id, e);
			throw e;
		}
		return risultato;
	}

	

	public ResponseListaCollaborazioni checkRup(Long syscon, String stazioneAppaltante, String cfStazioneAppaltante, boolean skipRpntLogin,
			String simogUsername, String simogPassword, boolean simogSaveCredentials) {
		ResponseListaCollaborazioni risultato = new ResponseListaCollaborazioni();
				
		try {
			// Carica CF da syscon
			String sysCf = this.contrattiMapper.getCfFromSyscon(syscon);

			// Verifica credenziali da utilizzare per login SIMOG
			SimogLoginOptions loginOptions = getSimogLoginOptionsByCf(syscon, sysCf, stazioneAppaltante, skipRpntLogin, simogUsername,
					simogPassword, simogSaveCredentials);

			if (loginOptions.isSuccess()) {
				// Invoke the SIMOG gateway web service - cfRup deve essere passato solo per login RPNT
				risultato = anacrestclientmanager.getListaCollaborazioni(loginOptions.isRpnt() ? loginOptions.getCfRup() : null,loginOptions.getUsername(), loginOptions.getPassword());
				
				if (risultato.isEsito()) {

					// Verifica che tra le collaborazioni ci sia l'azienda selezionata
					boolean found = false;
					List<Collaborazione> collaborazioni = risultato.getData();
					if (collaborazioni != null && collaborazioni.size() > 0) {
						for (Collaborazione collab : collaborazioni) {
							if (collab != null && collab.getAziendaCodiceFiscale() != null
									&& collab.getAziendaCodiceFiscale().equals(cfStazioneAppaltante)) {
								found = true;
								break;
							}
						}
					}
					
					if (!found) {
						risultato.setErrorData("Operazione non possibile: il RUP non ha accesso su SIMOG alla stazione appaltante attiva");
						risultato.setEsito(false);
					} else {
						// Non serve inviare le collaborazioni al client, mi servono solo per verificare se esistono
						risultato.setData(null);
					}

				} else if (risultato.isLoginError()) {
					// Gestione errori login simog
					if (loginOptions.isRpnt()) {
						// Notifica il client, dovra' chiedere all'utente se ritentare usando le credenziali del rup
						risultato.rpntLoginFailed();
					} else {
						// Credenziali non valide, il RUP potra' inserire nuove credenziali nel client
						risultato.rupCredentialsInvalid(loginOptions.getUsername(), loginOptions.getCfRup());
					}
				}
			} else if (loginOptions.isFatalError()) {
				// Errore fatale, utente non puo' far niente
				risultato.setErrorData(loginOptions.getErrorMessage());
			} else {
				// Credenziali non presenti, il RUP potra' inserire nuove credenziali nel client
				risultato.rupCredentialsMissing(loginOptions.getCfRup());
			}

		} catch (Exception e) {
			logger.error("Errore in controllo rup", e);
			throw e;
		}

		return risultato;
	}
	
	
	protected SimogLoginOptions getSimogLoginOptionsByCf(Long syscon, String cfRup, String stazioneappaltante, boolean skipRpnt,
			String simogUsername, String simogPassword, boolean simogSaveCredentials) {
		SimogLoginOptions options = new SimogLoginOptions();
		options.setCfRup(cfRup);

		// Carica dati per login RPNT
		String configSimogLogin = this.simogMapper.getConfig(CONFIG_CODICE_APP, CONFIG_CHIAVE_SIMOG_LOGIN);
		String configSimogPassword = this.simogMapper.getConfig(CONFIG_CODICE_APP, CONFIG_CHIAVE_SIMOG_PASSWORD);
		String configSimogRuolo = this.simogMapper.getConfig(CONFIG_CODICE_APP, CONFIG_CHIAVE_SIMOG_RUOLO);

		// Verifica se presente login/password e ruolo = 3 (RPNT), allora posso effettuare login RPNT
		if (!skipRpnt && configSimogLogin != null && configSimogLogin.trim().length() > 0 && configSimogPassword != null
				&& configSimogPassword.trim().length() > 0 && "3".equals(configSimogRuolo)) {

			// Usa credenziali RPNT
			options.setRpnt(true);
			options.setUsername(configSimogLogin);
			options.setPassword(configSimogPassword);
			options.setSuccess(true);
		} else {
			// Verifica se credenziali passate dal client
			if (simogUsername != null && simogPassword != null) {
				// OK, utilizza credenziali passate dal client
				options.setUsername(simogUsername);
				options.setPassword(simogPassword);
				options.setSuccess(true);

				if (simogSaveCredentials) {
					// Update rup credentials
					insertOrUpdateRupCredentials(syscon, cfRup, simogUsername, simogPassword);
				}
			} else {
				// Usa credenziali del RUP
				List<LoaderAppaltoUsrEntry> rupCredentials = contrattiMapper.getLoaderAppaltoUsrByCfRup(cfRup);
				if (rupCredentials == null || rupCredentials.size() == 0) {
					// Credenziali del RUP non presenti
					options.setErrorRupCredentialsMissing(true);
				} else if (rupCredentials.size() > 1) {
					// Ci sono piu' credenziali per lo stesso codice fiscale, e' un errore fatale sia per il delegato che per il RUP
					options.setFatalError(true);
					options.setErrorMessage(
							"Attenzione, esistono più utenti con il codice fiscale del RUP. Contattare un amministratore di sistema");
				} else {
					// Verifica credenziali non siano vuote
					LoaderAppaltoUsrEntry rupCredential = rupCredentials.get(0);
					if (rupCredential.getSimoguser() != null && rupCredential.getSimoguser().trim().length() > 0
							&& rupCredential.getSimogpass() != null && rupCredential.getSimogpass().trim().length() > 0) {
						// Una sola credenziale, posso usarla
						options.setUsername(rupCredential.getSimoguser());
						options.setPassword(rupCredential.getSimogpass());
						options.setSuccess(true);
					} else {
						// Credenziali del RUP non presenti
						options.setErrorRupCredentialsMissing(true);
					}
				}
			}
		}

		return options;
	}
	
	private void insertOrUpdateRupCredentials(Long syscon, String cfRup, String simogUsername, String simogPassword) {
		List<LoaderAppaltoUsrEntry> rupCredentials = contrattiMapper.getLoaderAppaltoUsrByCfRup(cfRup);
		if (rupCredentials == null || rupCredentials.size() == 0) {
			LoaderAppaltoUsrEntry entry = new LoaderAppaltoUsrEntry();
			entry.setSyscon(syscon);
			entry.setSimoguser(simogUsername);
			entry.setSimogpass(simogPassword);
			contrattiMapper.insertLoaderAppaltoUsr(entry);
		} else {
			LoaderAppaltoUsrEntry entry = rupCredentials.get(0);
			entry.setSimoguser(simogUsername);
			entry.setSimogpass(simogPassword);
			contrattiMapper.updateLoaderAppaltoUsr(entry);
		}
	}
	


	public Long getExistsCollaboratore(String cfrup, String codein, Long syscon) {
		return this.delegheMapper.getExistsDelegaByCfrupCodeinSyscon(cfrup, codein, syscon);
	}
}
