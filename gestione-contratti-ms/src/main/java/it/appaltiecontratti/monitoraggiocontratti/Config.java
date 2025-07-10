package it.appaltiecontratti.monitoraggiocontratti;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class Config {

	private static final Logger LOGGER = LogManager.getLogger(Config.class);

	@Value("${application.enableProxy:false}")
	private boolean enableProxy;

	@Value("${http_proxy:#{null}}")
	private String httpProxy;

	@Value("${no_proxy:#{null}}")
	private String noProxy;

	@Value("${https_proxy:#{null}}")
	private String httpsProxy;

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
		return factory;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		if (enableProxy) {
			LOGGER.info("Proxy applicativo abilitato");
			String proxy = StringUtils.isNotBlank(httpsProxy) ? httpsProxy : httpProxy;
			String proxyHost = null;
			int proxyPort = 0;
			String proxyScheme = "http";
			if (StringUtils.isNotBlank(proxy)) {
				proxyScheme = proxy.startsWith("https") ? "https" : proxyScheme;
				proxy = proxy.replace("http://", "").replace("https://", "");
				proxyHost = proxy.substring(0, proxy.lastIndexOf(":"));
				proxyPort = Integer.valueOf(proxy.substring(proxy.lastIndexOf(":") + 1)).intValue();
			}

			//Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			//SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			//requestFactory.setProxy(p);
			//restTemplate.setRequestFactory(requestFactory);
			requestFactory.setHttpClient(HttpClientBuilder.create()
					.setProxy(new HttpHost(proxyHost, proxyPort, proxyScheme))
					.build());
			LOGGER.info("Proxy applicativo impostato");
		}
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}

}