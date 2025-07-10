package it.appaltiecontratti.autenticazione.soap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Component
public class SoapClientConnector {

    private static final Logger LOGGER = LogManager.getLogger(SoapClientConnector.class);

    @Value("${application.enableProxy:false}")
    private boolean enableProxy;

    @Value("${http_proxy:#{null}}")
    private String httpProxy;

    @Value("${no_proxy:#{null}}")
    private String noProxy;

    @Value("${https_proxy:#{null}}")
    private String httpsProxy;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public Object callWebService(String url, Object request) {

        if (enableProxy) {
            LOGGER.info("Proxy applicativo abilitato");
            boolean applyProxy = true;
            LOGGER.info("valore variabile noproxy:" + noProxy);
            if (noProxy != null) {
                String[] noProxyUrls = noProxy.split(",");
                for (String noProxyUrl : noProxyUrls) {
                    if (url.equals(noProxyUrl) || url.contains(noProxyUrl)) {
                        applyProxy = false;

                    }

                }
            }
            LOGGER.info("applyProxy" + applyProxy);
            if (applyProxy) {
                String proxy = StringUtils.isNotBlank(httpsProxy) ? httpsProxy : httpProxy;
                String proxyHost = null;
                int proxyPort = 0;

                if (StringUtils.isNotBlank(proxy)) {
                    proxy = proxy.replace("http://", "").replace("https://", "");
                    proxyHost = proxy.substring(0, proxy.lastIndexOf(":"));
                    proxyPort = Integer.valueOf(proxy.substring(proxy.lastIndexOf(":") + 1)).intValue();
                }

                HttpClientBuilder builder = HttpClientBuilder.create();
                builder.addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor());
                HttpHost host = new HttpHost(proxyHost, proxyPort);
                builder.setProxy(host);
                CloseableHttpClient httpClient = builder.build();
                HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient);
                webServiceTemplate.setMessageSender(messageSender);
            }
        }

        return webServiceTemplate.marshalSendAndReceive(url, request);
    }
}
