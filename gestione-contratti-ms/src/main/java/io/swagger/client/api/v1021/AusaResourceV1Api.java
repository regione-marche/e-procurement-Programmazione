package io.swagger.client.api.v1021;

import it.appaltiecontratti.pcp.v1021.ApiClient;

import it.appaltiecontratti.pcp.v1021.npaGateway.InlineResponse400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-10-18T11:56:28.206599400+02:00[Europe/Rome]")
@Component("io.swagger.client.api.v1021.AusaResourceV1Api")
public class AusaResourceV1Api {
    private ApiClient apiClient;

    public AusaResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public AusaResourceV1Api(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Lista di documenti AUSA filtrate per codice AUSA o/e codice fiscale
     * Ritorna la lista di documenti AUSA filtrate per codice AUSA o/e codice fiscale
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param codiceAusa Codice AUSA (optional)
     * @param codiceFiscale Codice fiscale (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedAusaGetByGet(String xTenantId, String codiceAusa, String codiceFiscale) throws RestClientException {
        return v1ProtectedAusaGetByGetWithHttpInfo(xTenantId, codiceAusa, codiceFiscale).getBody();
    }

    /**
     * Lista di documenti AUSA filtrate per codice AUSA o/e codice fiscale
     * Ritorna la lista di documenti AUSA filtrate per codice AUSA o/e codice fiscale
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param codiceAusa Codice AUSA (optional)
     * @param codiceFiscale Codice fiscale (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedAusaGetByGetWithHttpInfo(String xTenantId, String codiceAusa, String codiceFiscale) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedAusaGetByGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/ausa/getBy").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceAusa", codiceAusa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceFiscale", codiceFiscale));
        if (xTenantId != null)
            headerParams.add("X-tenantId", apiClient.parameterToString(xTenantId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
