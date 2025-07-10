package it.appaltiecontratti.programmi.bl;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.appaltiecontratti.programmi.entity.BaseResponse;
import it.appaltiecontratti.programmi.entity.ChiaveConfigurazione;
import it.appaltiecontratti.programmi.entity.DettaglioCupForm;
import it.appaltiecontratti.programmi.entity.DettaglioCupRequest;
import it.appaltiecontratti.programmi.entity.ResponseDettaglioCup;
import it.appaltiecontratti.programmi.mapper.ProgrammiMapper;

@Service("anacrestclientmanager")
public class RestClientManager extends AbstractRestManager{

	private static Logger LOG = LogManager.getLogger(RestClientManager.class);

	@Value("${url-anac-gateway}")
	private String urlDettaglioCup;

	@Autowired
	private ProgrammiMapper programmiMapper;

	@Autowired
	private WConfigManager wConfigManager;
	
	private static final String CONSULTA_CUP_ENDPOINT = "dettaglioCup";
	

	public ResponseDettaglioCup dettaglioCup(DettaglioCupForm form) {

		ResponseDettaglioCup risultato = new ResponseDettaglioCup();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			DettaglioCupRequest request = new DettaglioCupRequest();
			getChiaviAccesso(request);
			request.setData(form);
			WebTarget webTarget = client.target(buildUrl(urlDettaglioCup, CONSULTA_CUP_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<DettaglioCupRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(ResponseDettaglioCup.class));
		} catch (Exception ex) {
			LOG.error(ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	
	protected <T extends it.appaltiecontratti.programmi.entity.BaseConsultaAnacRequest> void getChiaviAccesso(T chiaviAccesso) {
		chiaviAccesso.setUsername(wConfigManager.getConfigurationValue("it.eldasoft.pubblicazioni.ws.username"));
		chiaviAccesso.setPassword(wConfigManager.getConfigurationValue("it.eldasoft.pubblicazioni.ws.password"));
	}
}