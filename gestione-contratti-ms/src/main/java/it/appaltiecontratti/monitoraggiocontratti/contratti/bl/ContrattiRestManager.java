package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicazioneAttoResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.LoginResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaAttoServerEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaSmartCigEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicazioneResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.rest.bl.AbstractRestManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaviAccesso;

@Service("contrattirestmanager")
public class ContrattiRestManager extends AbstractRestManager{

	@Value("${url-pubblicazione-atti}")
	private String urlPubbAtti;
	
	private static final String PUBBLICAZIONE_LOGIN_ENDPOINT = "serviceAccessPubblica";
	private static final String PUBBLICA_GARA_ENDPOINT = "PubblicaGaraLotti";
	private static final String PUBBLICA_ATTO_ENDPOINT = "PubblicaAtto";
	private static final String PUBBLICA_SMARTCIG_ENDPOINT = "PubblicaSmartCig";
	
	private static Logger LOG = LogManager.getLogger(ContrattiRestManager.class);

	public LoginResult loginPubblicazioni(ChiaviAccesso chiavi, String applicationToken) {
		LoginResult risultato = new LoginResult();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			WebTarget webTarget = client.target(buildUrl(urlPubbAtti, PUBBLICAZIONE_LOGIN_ENDPOINT)).queryParam("clientId", chiavi.getClientId()).queryParam("clientKey", chiavi.getClientKey()).queryParam("username", chiavi.getUsername()).queryParam("password", chiavi.getPassword());
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, applicationToken);
			Response response = invocationBuilder.get();
			risultato = (response.readEntity(LoginResult.class));//(response.readEntity(Object.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	public PubblicazioneResult pubblicaGara(PubblicaGaraEntry request, boolean onlyValidate, String authorization , String applicationToken) {
		PubblicazioneResult risultato = new PubblicazioneResult();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			WebTarget webTarget = client.target(buildUrl(urlPubbAtti, PUBBLICA_GARA_ENDPOINT)).queryParam("modalitaInvio", onlyValidate == true ? "1" : "2");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorization).header("applicationToken", applicationToken);;
			Response response = invocationBuilder.post(Entity.json(request));
			risultato = (response.readEntity(PubblicazioneResult.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	public PubblicazioneResult pubblicaSmartcig(PubblicaSmartCigEntry request, boolean onlyValidate, String authorization , String applicationToken) {
		PubblicazioneResult risultato = new PubblicazioneResult();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			WebTarget webTarget = client.target(buildUrl(urlPubbAtti, PUBBLICA_SMARTCIG_ENDPOINT)).queryParam("modalitaInvio", onlyValidate == true ? "1" : "2");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorization).header("applicationToken", applicationToken);;
			Response response = invocationBuilder.post(Entity.json(request));
			risultato = (response.readEntity(PubblicazioneResult.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	
	public PubblicazioneAttoResult pubblicaAtto(PubblicaAttoServerEntry request, boolean onlyValidate, String authorization , String applicationToken) {
		PubblicazioneAttoResult risultato = new PubblicazioneAttoResult();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			WebTarget webTarget = client.target(buildUrl(urlPubbAtti, PUBBLICA_ATTO_ENDPOINT)).queryParam("modalitaInvio", onlyValidate == true ? "1" : "2").queryParam("pubblicaGara", false);
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorization).header("applicationToken", applicationToken);
			Response response = invocationBuilder.post(Entity.json(request));
			risultato = (response.readEntity(PubblicazioneAttoResult.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
}
