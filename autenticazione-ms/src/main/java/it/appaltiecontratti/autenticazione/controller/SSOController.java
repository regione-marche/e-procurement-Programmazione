package it.appaltiecontratti.autenticazione.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import it.appaltiecontratti.autenticazione.bl.SSOGeneralManager;
import it.appaltiecontratti.autenticazione.entity.ResponseSpidAccount;
import it.appaltiecontratti.autenticazione.entity.ResponseSpidIniz;
import it.appaltiecontratti.autenticazione.entity.ResponseSpidUrl;
import it.appaltiecontratti.autenticazione.entity.StandardAuthenticationModel;

/**
 * Servizio REST esposto al path "/ssoAuthentication".
 */
@Controller
@CrossOrigin
@RequestMapping(value = {"${sso.context-path:#{'/ssoAuthentication'}}"})
@SuppressWarnings("java:S5122")
public class SSOController {

    /**
     * Logger di classe.
     */
    private static final Logger LOGGER = LogManager.getLogger(SSOController.class);

    @Value("${SSO_HOME_PATH:#{'/launcher-fe/index.html'}}")
    private String homePath;

    @Value("${SSO_HOME_HOST:#{'localhost:8100'}}")
    private String homeHost;

    @Value("${SSO_HOME_SCHEME:#{'http'}}")
    private String homeScheme;

    @Autowired
    private SSOGeneralManager ssoGeneralManager;

    @RequestMapping(method = RequestMethod.GET, value = "/executeAuthentication")
    public RedirectView executeAuthentication(@RequestHeader Map<String, String> headers,
                                                          @RequestHeader("host") String host) {
        LOGGER.info("executeAuthentication: Inizio Metodo");
        return elaborateGetSSOAuthentication(headers, host, null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/executeAuthentication/{authType}")
    public RedirectView executeAuthenticationWithAuthType(@RequestHeader Map<String, String> headers,
                                                          @RequestHeader("host") String host, @PathVariable(value = "authType") String authType) {
        LOGGER.info("executeAuthenticationWithAuthType: Inizio Metodo authType [ {} ]", authType);
        return elaborateGetSSOAuthentication(headers, host, authType);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/executeAuthentication", //
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView executeAuthenticationPost(@RequestHeader("host") String host,
                                                  StandardAuthenticationModel standardAuthenticationModel) {

        String hostToUse = StringUtils.isBlank(homeHost) ? host : homeHost;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme(homeScheme).host(hostToUse).path(homePath);

        try {
            String authorizationCode = ssoGeneralManager
                    .authenticateUserStandard(standardAuthenticationModel.getJwtToken());
            LOGGER.info("Authorization code [ {} ]", authorizationCode);

            uriComponentsBuilder = uriComponentsBuilder.queryParam("authorizationCode", authorizationCode);

        } catch (Exception e) {
            LOGGER.error(e);
        }

        String url = uriComponentsBuilder.build().toUriString();
        LOGGER.info("URL: [ " + url + " ]");

        return new RedirectView(url);
    }

    
    @RequestMapping(method = RequestMethod.POST, value = "/executeAuthenticationCohesion", //
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView executeAuthenticationCohesion(String token) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme(homeScheme).host(homeHost).path(homePath);

        try {
            String authorizationCode = ssoGeneralManager
                    .authenticateUserCohesion(token);
            LOGGER.info("Authorization code [ {} ]", authorizationCode);

            uriComponentsBuilder = uriComponentsBuilder.queryParam("authorizationCode", authorizationCode);

        } catch (Exception e) {
            LOGGER.error(e);
        }

        String url = uriComponentsBuilder.build().toUriString();
        LOGGER.info("URL: [ " + url + " ]");
        return new RedirectView(url);
    }
    
    
    private RedirectView elaborateGetSSOAuthentication(final Map<String, String> headers, final String host, final String authType) {
        LOGGER.info("elaborateGetSSOAuthentication: Inizio Metodo");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            LOGGER.info("key: " + entry.getKey() + "; value: " + entry.getValue());
        }

        String hostToUse = StringUtils.isBlank(homeHost) ? host : homeHost;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme(homeScheme).host(hostToUse).path(homePath);
        try {
            String authorizationCode = ssoGeneralManager.authenticateUser(headers, authType);
            LOGGER.info("Authorization code [ {} ]", authorizationCode);

            uriComponentsBuilder = uriComponentsBuilder.queryParam("authorizationCode", authorizationCode);

        } catch (Exception e) {
            LOGGER.error("Errore", e);
        }

        String url = uriComponentsBuilder.build().toUriString();
        LOGGER.info("URL: [ " + url + " ]");

        return new RedirectView(url);
    }
    
    
}
