package it.appaltiecontratti.inbox.bl;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.inbox.entity.LoginResult;
import it.appaltiecontratti.inbox.entity.OutboxEntry;
import it.appaltiecontratti.inbox.mapper.SCPMapper;

@Component(value = "invioScpManager")
public class InvioSCPManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(InvioSCPManager.class);

	private static final String PROP_WS_PUBBLICAZIONI_URL_LOGIN = "it.eldasoft.pubblicazioni.ws.urlLogin";

	/**
	 * Id Client
	 */
	private static final String PROP_WS_PUBBLICAZIONI_IDCLIENT = "it.eldasoft.pubblicazioni.ws.clientId";

	/**
	 * Key Client
	 */
	private static final String PROP_WS_PUBBLICAZIONI_KEYCLIENT = "it.eldasoft.pubblicazioni.ws.clientKey";

	/**
	 * Username servizi di pubblicazione
	 */
	private static final String PROP_WS_PUBBLICAZIONI_USERNAME = "it.eldasoft.pubblicazioni.ws.username";

	/**
	 * Password servizi di pubblicazione
	 */
	private static final String PROP_WS_PUBBLICAZIONI_PASSWORD = "it.eldasoft.pubblicazioni.ws.password";
	
	/**
	 * Url da sostituire al file allegato per gli avvisi
	 */
	private static final String PROP_FILE_ALLEGATO_PUBBLICAZIONI_AVVISI_URL = "it.eldasoft.pubblicazioni.ws.urlAvvisiDoc";
	
	/**
	 * Url da sostituire al file allegato per gli atti
	 */
	private static final String PROP_FILE_ALLEGATO_PUBBLICAZIONI_ATTI_URL = "it.eldasoft.pubblicazioni.ws.urlAttiDoc";

	@Autowired
	SCPManager scpManager;

	@Autowired
	ContrattiManager contrattiManager;

	@Autowired
	StazioniAppaltantiManager stazioniAppaltantiManager;

	@Autowired
	AvvisiManager avvisiManager;

	@Autowired
	SCPMapper scpMapper;

	public void exportORtoSCP() {
		logger.info("exportORtoSCP: inizio metodo");

		try {

			List<OutboxEntry> listaW9OUTBOX = scpMapper.getDatiDaEsportareSCP();
			String login = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_USERNAME);
			String password = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_PASSWORD);
			String urlLogin = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_URL_LOGIN);
			String idClient = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_IDCLIENT);
			String keyClient = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_KEYCLIENT);
			String fileAllegatoAvvisiUrl = this.scpMapper.getConfigurazione(PROP_FILE_ALLEGATO_PUBBLICAZIONI_AVVISI_URL);
			String fileAllegatoAttiUrl = this.scpMapper.getConfigurazione(PROP_FILE_ALLEGATO_PUBBLICAZIONI_ATTI_URL);

			if (listaW9OUTBOX.size() > 0) {

				Client client = scpManager.getSSLClient();
				WebTarget webTarget = client.target(urlLogin).path("Account/LoginPubblica");
				MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
				formData.add("username", login);
				formData.add("password", password);
				formData.add("clientKey", keyClient);
				formData.add("clientId", idClient);
				Response accesso = webTarget.request(MediaType.APPLICATION_JSON)
						.post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED));
				LoginResult resultAccesso = accesso.readEntity(LoginResult.class);

				if (resultAccesso.isEsito()) {
					String token = resultAccesso.getToken();
					for (OutboxEntry itemW9OUTBOX : listaW9OUTBOX) {
						try {
							scpManager.invioSCP(itemW9OUTBOX, token, fileAllegatoAvvisiUrl, fileAllegatoAttiUrl);
						} catch (Exception e) {
							logger.error(
									"Errore nell'invio a scp del Record con idComun = " + itemW9OUTBOX.getIdcomun(), e);
						}
					}
				} else {
					logger.error(
							"L'accesso al servizio di login ha restituito questo errore : " + resultAccesso.getError());
				}
			}
		} catch (Exception ex) {
			logger.error("errore in exportORtoSCP", ex);
		}
		logger.info("exportORtoSCP: fine metodo");
	}

}
