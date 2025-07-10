package it.appaltiecontratti.programmi.bl;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

@SuppressWarnings("java:S4830")
public class AbstractRestManager {
	
	private Logger LOG = LogManager.getLogger(AbstractRestManager.class);
	
	@Value("${ssl-bypass-self-signed-certificate:#{false}}")
	private boolean bypassSslSelfSignedCertificate;

	protected Client getClient() throws Exception {
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
	
	protected String buildUrl(final String baseUrl, final String endpoint) {
		String controller = baseUrl;
		if (controller == null)
			return null;
		if (!controller.endsWith("/"))
			controller += "/";
		String url = controller + endpoint;
		LOG.info("URL: {}", url);
		return url;
	}

}
