package it.appaltiecontratti.inbox.bl.impl;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.inbox.entity.BaseConsultaAnacRequest;
import it.appaltiecontratti.inbox.entity.LoaderAppaltoRequest;
import it.appaltiecontratti.inbox.entity.form.ConsultaGaraRequest;
import it.appaltiecontratti.inbox.entity.responses.BaseResponse;
import it.appaltiecontratti.inbox.entity.responses.FullResponseLoaderAppalto;
import it.appaltiecontratti.inbox.entity.responses.ResponseConsultaGaraIdGara;
import it.appaltiecontratti.inbox.mapper.AccountMapper;
import it.avlp.simog.massload.xmlbeans.LoaderAppalto;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;

@SuppressWarnings("java:S4830")
@Service("simogGatewayRestClientManager")
public class SimogGatewayRestClientManager {

	private static final Logger LOG = LogManager.getLogger(SimogGatewayRestClientManager.class);

	@Value("${url-gateway}")
	private String urlGateway;

	@Value("${ssl-bypass-self-signed-certificate:#{false}}")
	private boolean bypassSslSelfSignedCertificate;

	@Value("${or.authentication.username}")
	private String orAuthenticationUsername;

	@Autowired
	private AccountMapper accountMapper;

	private static final String LOADER_APPALTO_ENDPOINT = "loaderAppalto";
	
	private static final String CONSULTA_GARA_ENDPOINT = "consultaGara";
	
	public ResponseConsultaGaraIdGara consultaGara(String cig) {

		ResponseConsultaGaraIdGara risultato = new ResponseConsultaGaraIdGara();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			ConsultaGaraRequest request = new ConsultaGaraRequest();
			getChiaviAccesso(request);
			request.setData(cig);
			WebTarget webTarget = client.target(buildUrl(urlGateway, CONSULTA_GARA_ENDPOINT));
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

	public FullResponseLoaderAppalto postLoaderAppalto(final LoaderAppalto loaderAppalto) {
		FullResponseLoaderAppalto risultato = new FullResponseLoaderAppalto();
		risultato.setEsito(true);
		try {
			Client client = this.getClient();
			LoaderAppaltoRequest request = new LoaderAppaltoRequest();
			request = getChiaviAccesso(request);
			ObjectMapper objectMapper = new ObjectMapper();
			String encodedLoaderAppalto = objectMapper.writeValueAsString(loaderAppalto);
			request.setData(encodedLoaderAppalto);
			WebTarget webTarget = client.target(buildUrl(LOADER_APPALTO_ENDPOINT));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Entity<LoaderAppaltoRequest> entity = Entity.json(request);
			Response response = invocationBuilder.post(entity);
			risultato = (response.readEntity(FullResponseLoaderAppalto.class));
		} catch (Exception ex) {
			LOG.error("Error during postLoaderAppalto", ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			throw new RuntimeException(ex);
		}
		return risultato;
	}

	private <T extends BaseConsultaAnacRequest> T getChiaviAccesso(T chiaviAccesso) {

		String username = this.orAuthenticationUsername.toUpperCase();
		String accountPassword = this.accountMapper.getAccountPwd(username);

		chiaviAccesso.setUsername(username);
		chiaviAccesso.setPassword(getPasswordInChiaro(accountPassword));

		return chiaviAccesso;
	}

	private String buildUrl(final String endpoint) {
		String controller = urlGateway;
		if (controller == null)
			return null;
		if (!controller.endsWith("/"))
			controller += "/";
		String url = controller + endpoint;
		LOG.info("URL: {}", url);
		return url;
	}
	
	private String buildUrl(final String baseUrl, final String endpoint) {
		String controller = baseUrl;
		if (controller == null)
			return null;
		if (!controller.endsWith("/"))
			controller += "/";
		String url = controller + endpoint;
		LOG.info("URL: {}", url);
		return url;
	}

	private Client getClient() throws Exception {
		return bypassSslSelfSignedCertificate ? this.getSSLClient() : ClientBuilder.newClient();
	}

	@SuppressWarnings("java:S5527")
	private Client getSSLClient() throws Exception {
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

		} }, new SecureRandom());

		Client client = ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
		return client;
	}

	public String getPasswordInChiaro(String passwordCriptata) {
		if (passwordCriptata != null) {
			String passwordInChiaro = "";
			try {
				ICriptazioneByte decriptatorePsw = FactoryCriptazioneByte.getInstance("LEG",
						passwordCriptata.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
				passwordInChiaro = new String(decriptatorePsw.getDatoNonCifrato());
			} catch (Exception ex) {
			}
			return passwordInChiaro;
		}
		return null;
	}
}
