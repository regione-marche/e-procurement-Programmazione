package it.appaltiecontratti.programmi.rl.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.cert.X509Certificate;

@Configuration
public class RestConfig {

    @Value("${application.enableProxy:false}")
    private boolean enableProxy;

    @Bean
    public RestTemplate restTemplate() {
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            HostnameVerifier hostnameVerifier = (hostname, session) -> true;
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            HttpHost proxy = new HttpHost("127.0.0.1", 9999);
            RequestConfig config = RequestConfig.custom()
                    //.setProxy(proxy)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .setDefaultRequestConfig(config)
                    .build();

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return new RestTemplate(factory);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella configurazione di RestTemplate", e);
        }
    }
}
