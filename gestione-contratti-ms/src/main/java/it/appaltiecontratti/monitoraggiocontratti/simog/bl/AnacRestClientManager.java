package it.appaltiecontratti.monitoraggiocontratti.simog.bl;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicazioneResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.PresaInCaricoGaraDelegataForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.PubblicaSchedaInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaCollaborazioni;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePresaInCaricoGaraDelegata;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.rest.bl.AbstractRestManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.GetElencoFeedback;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.BaseConsultaAnacRequest;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaveConfigurazione;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaComunicazioneForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaGaraRequest;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaNumeroGaraRequest;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaSmartCigForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ElencoFeedbackRequest;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ListaCollaborazioniRequest;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.PresaInCaricoGaraDelegataRequest;
import it.appaltiecontratti.monitoraggiocontratti.simog.mapper.SimogMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaComunicazioneResult;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaGaraIdGara;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaNumeroGaraCig;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseFeedback;

@Service("anacrestclientmanager")
public class AnacRestClientManager extends AbstractRestManager{

	private static Logger LOG = LogManager.getLogger(AnacRestClientManager.class);

	@Value("${url-anac-gateway}")
	private String urlAnac;

	@Value("${url-pubblicazione-contratti-public}")
	private String urlPubbContrattiPublic;
	
	@Value("${url-pubblicazione-contratti-private}")
	private String urlPubbContrattiPrivate;
	
	@Value("${url-pubblicazione-atti}")
	private String urlPubbAtti;

	@Autowired
	private SimogMapper simogMapper;
	
	@Autowired
	protected ContrattiMapper contrattiMapper;

	private static final String CONSULTA_GARA_ENDPOINT = "consultaGara";
	private static final String CONSULTA_NUMERO_GARA_ENDPOINT = "consultaNumeroGara";
	private static final String CONSULTA_SMARTCIG_ENDPOINT = "consultaComunicazione";
	private static final String GET_ELENCO_FEEDBACK_ENDPOINT = "getElencoFeedback";
	private static final String GET_LISTA_COLLABORAZIONI_ENDPOINT = "getListaCollaborazioni";
	private static final String PRESA_IN_CARICO_GARA_DELEGATA_ENDPOINT = "presaInCaricoGaraDelegata";
	private static final String PUBBLICA_FASE_AGG_ENDPOINT = "pubblicaScheda";
	public static final String APPLICATION_CODE = "W9";	
	public static final String PROP_SIMOG_WS_SMARTCIG_URL = "it.eldasoft.simog.ws.smartcig.url";
	

	public ResponseConsultaGaraIdGara consultaGara(String cig) {

		ResponseConsultaGaraIdGara risultato = new ResponseConsultaGaraIdGara();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			ConsultaGaraRequest request = new ConsultaGaraRequest();
			getChiaviAccesso(request);
			request.setData(cig);
			WebTarget webTarget = client.target(buildUrl(urlAnac, CONSULTA_GARA_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<ConsultaGaraRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(ResponseConsultaGaraIdGara.class));// response.readEntity( Object.class )
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseConsultaNumeroGaraCig consultaNumeroGara(String idAvGara) {

		ResponseConsultaNumeroGaraCig risultato = new ResponseConsultaNumeroGaraCig();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			ConsultaNumeroGaraRequest request = new ConsultaNumeroGaraRequest();
			getChiaviAccesso(request);
			request.setData(idAvGara);
			WebTarget webTarget = client.target(buildUrl(urlAnac, CONSULTA_NUMERO_GARA_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<ConsultaNumeroGaraRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(ResponseConsultaNumeroGaraCig.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	public ResponseConsultaComunicazioneResult importSmartcig(String smartcig, String indiceCollaborazione, String username, String password) {

		ResponseConsultaComunicazioneResult risultato = new ResponseConsultaComunicazioneResult();
	
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			ConsultaSmartCigForm request = new ConsultaSmartCigForm();
			getChiaviAccesso(request);
			ConsultaComunicazioneForm data = new ConsultaComunicazioneForm();
			data.setCig(smartcig);
			data.setCollaborazioneUfficioId(indiceCollaborazione);
			request.setUsernameSimog(username);
			request.setPasswordSimog(password);
			request.setData(data);
			WebTarget webTarget = client.target(buildUrl(urlAnac, CONSULTA_SMARTCIG_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<ConsultaSmartCigForm> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);		
			risultato = (response.readEntity(ResponseConsultaComunicazioneResult.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFeedback getElencoFeedback(GetElencoFeedback getElencoFeedback) {
		ResponseFeedback risultato = new ResponseFeedback();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			ElencoFeedbackRequest request = new ElencoFeedbackRequest();
			getChiaviAccesso(request);
			request.setData(getElencoFeedback);
			WebTarget webTarget = client.target(buildUrl(urlPubbContrattiPublic, GET_ELENCO_FEEDBACK_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<ElencoFeedbackRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(ResponseFeedback.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	public PubblicazioneResult pubblicaAggiudicazione(String payload, boolean onlyValidate, String cfSa, String cig, GaraEntry gara, LottoEntry lotto, String authorization , String applicationToken) {
		PubblicazioneResult risultato = new PubblicazioneResult();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			PubblicaSchedaInsertForm request = new PubblicaSchedaInsertForm();
			request.setCfStazioneAppaltante(cfSa);
			request.setCig(cig);
			request.setCodFase(1L);
			request.setModalitaInvio(2L);
			request.setOggettoScheda(payload);
			request.setTipoInvio(2L);
			request.setGara(gara);
			request.setLotto(lotto);		
			
			WebTarget webTarget = client.target(buildUrl(urlPubbContrattiPrivate, PUBBLICA_FASE_AGG_ENDPOINT)).queryParam("modalitaInvio", onlyValidate == true ? "1" : "2");
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

	protected <T extends BaseConsultaAnacRequest> void getChiaviAccesso(T chiaviAccesso) {
		List<ChiaveConfigurazione> chiaviAccessoOrt = this.simogMapper.getChiaviAccessoOrt();
		for (ChiaveConfigurazione chiave : chiaviAccessoOrt) {
			if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.username")) {
				chiaviAccesso.setUsername(chiave.getValore());
			}
			if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.password")) {
				chiaviAccesso.setPassword(chiave.getValore());
			}
		}
	}

	
	public ResponseListaCollaborazioni getListaCollaborazioni(final String cfRup, String username, String password) {

		ResponseListaCollaborazioni risultato = new ResponseListaCollaborazioni();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			ListaCollaborazioniRequest request = new ListaCollaborazioniRequest();
			getChiaviAccesso(request);
			request.setData(cfRup);
			request.setPasswordSimog(password);
			request.setUsernameSimog(username);
			WebTarget webTarget = client.target(buildUrl(urlAnac, GET_LISTA_COLLABORAZIONI_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<ListaCollaborazioniRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(ResponseListaCollaborazioni.class));
		} catch (Exception ex) {
			LOG.error("Errori durante la chiamata di getListaCollaborazioni", ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	
	public ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(final PresaInCaricoGaraDelegataForm form) {
		ResponsePresaInCaricoGaraDelegata risultato = new ResponsePresaInCaricoGaraDelegata();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			PresaInCaricoGaraDelegataRequest request = new PresaInCaricoGaraDelegataRequest();
			getChiaviAccesso(request);
			request.setData(form);
			WebTarget webTarget = client.target(buildUrl(urlAnac, PRESA_IN_CARICO_GARA_DELEGATA_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<PresaInCaricoGaraDelegataRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(ResponsePresaInCaricoGaraDelegata.class));
		} catch (Exception ex) {
			LOG.error("Errori durante la chiamata di presaInCaricoGaraDelegata", ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

}