package it.maggioli.ssointegrms.services.oneGateway.impl;

import it.maggioli.ssointegrms.common.OneGatewayAppConstants;
import it.maggioli.ssointegrms.model.ApplicationInfo;
import it.maggioli.ssointegrms.services.oneGateway.ServiceProviderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Servizio di gestione dei service providers applicativi (redirect url, ecc...)
 *
 * @author Cristiano Perin
 */
@Service("serviceProviderService")
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    @Value("${application.baseUrl:}")
    private String baseUrl;

    @Override
    public String getRedirectUrlByMimsClientId(final String mimsClientId) {

        LOGGER.info("Execution start ServiceProviderServiceImpl::getRedirectUrlByMimsClientId() mimsClientId [{}]",
                mimsClientId);

        if (StringUtils.isBlank(mimsClientId))
            return null;

        if (OneGatewayAppConstants.SERVICE_PROVIDERS == null || OneGatewayAppConstants.SERVICE_PROVIDERS.isEmpty())
            return null;

        ApplicationInfo ai = OneGatewayAppConstants.getApplicationInfoByMimsClientId(mimsClientId);

        if (ai != null) {
            String url = null;
            if (StringUtils.isNotBlank(baseUrl))
                url = UriComponentsBuilder.fromHttpUrl(baseUrl + ai.getRedirectUrl()).toUriString();
            else
                url = UriComponentsBuilder.fromHttpUrl(ai.getRedirectUrl()).toUriString();
            return url;
        }

        return null;
    }

    @Override
    public boolean existsClientId(final String clientId) {

        LOGGER.info("Execution start ServiceProviderServiceImpl::existsClientId() clientId {}", clientId);

        if (StringUtils.isBlank(clientId))
            return false;

        if (OneGatewayAppConstants.SERVICE_PROVIDERS == null || OneGatewayAppConstants.SERVICE_PROVIDERS.isEmpty())
            return false;

        return OneGatewayAppConstants.SERVICE_PROVIDERS.containsKey(clientId);
    }

    @Override
    public boolean existsMimsClientId(final String mimsClientId) {

        LOGGER.info("Execution start ServiceProviderServiceImpl::existsMimsClientId() mimsClientId {}", mimsClientId);

        if (StringUtils.isBlank(mimsClientId))
            return false;

        if (OneGatewayAppConstants.SERVICE_PROVIDERS == null || OneGatewayAppConstants.SERVICE_PROVIDERS.isEmpty())
            return false;

        ApplicationInfo ai = OneGatewayAppConstants.getApplicationInfoByMimsClientId(mimsClientId);

        return ai != null;
    }

}
