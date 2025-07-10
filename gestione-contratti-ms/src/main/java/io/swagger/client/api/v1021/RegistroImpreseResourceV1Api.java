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
@Component("io.swagger.client.api.v1021.RegistroImpreseResourceV1Api")
public class RegistroImpreseResourceV1Api {
    private ApiClient apiClient;

    public RegistroImpreseResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public RegistroImpreseResourceV1Api(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * ricerca dettaglio impresa tramite codice fiscale
     * ricerca dettaglio impresa tramite codice fiscale
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param codiceFiscale Codice Fiscale dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedRegistroImpreseDettaglioCodicefiscaleGet(String codiceFiscale, String xTenantId) throws RestClientException {
        return v1ProtectedRegistroImpreseDettaglioCodicefiscaleGetWithHttpInfo(codiceFiscale, xTenantId).getBody();
    }

    /**
     * ricerca dettaglio impresa tramite codice fiscale
     * ricerca dettaglio impresa tramite codice fiscale
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param codiceFiscale Codice Fiscale dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedRegistroImpreseDettaglioCodicefiscaleGetWithHttpInfo(String codiceFiscale, String xTenantId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'codiceFiscale' is set
        if (codiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'codiceFiscale' when calling v1ProtectedRegistroImpreseDettaglioCodicefiscaleGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedRegistroImpreseDettaglioCodicefiscaleGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/registro-imprese/dettaglio/codicefiscale").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
    /**
     * ricerca dettaglio impresa tramite provincia e n-rea dell&#x27;impresa
     * ricerca dettaglio impresa tramite provincia e n-rea dell&#x27;impresa
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param numeroRea Numero REA dell&#x27;impresa (required)
     * @param siglaProvincia Sigla Cciaa dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedRegistroImpreseDettaglioNreaGet(String numeroRea, String siglaProvincia, String xTenantId) throws RestClientException {
        return v1ProtectedRegistroImpreseDettaglioNreaGetWithHttpInfo(numeroRea, siglaProvincia, xTenantId).getBody();
    }

    /**
     * ricerca dettaglio impresa tramite provincia e n-rea dell&#x27;impresa
     * ricerca dettaglio impresa tramite provincia e n-rea dell&#x27;impresa
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param numeroRea Numero REA dell&#x27;impresa (required)
     * @param siglaProvincia Sigla Cciaa dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedRegistroImpreseDettaglioNreaGetWithHttpInfo(String numeroRea, String siglaProvincia, String xTenantId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'numeroRea' is set
        if (numeroRea == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'numeroRea' when calling v1ProtectedRegistroImpreseDettaglioNreaGet");
        }
        // verify the required parameter 'siglaProvincia' is set
        if (siglaProvincia == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'siglaProvincia' when calling v1ProtectedRegistroImpreseDettaglioNreaGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedRegistroImpreseDettaglioNreaGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/registro-imprese/dettaglio/nrea").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "numeroRea", numeroRea));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "siglaProvincia", siglaProvincia));
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
    /**
     * ricerca impresa tramite codice fiscale
     * ricerca impresa tramite codice fiscale
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param codiceFiscale Codice Fiscale dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedRegistroImpreseRicercaCodicefiscaleGet(String codiceFiscale, String xTenantId) throws RestClientException {
        return v1ProtectedRegistroImpreseRicercaCodicefiscaleGetWithHttpInfo(codiceFiscale, xTenantId).getBody();
    }

    /**
     * ricerca impresa tramite codice fiscale
     * ricerca impresa tramite codice fiscale
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param codiceFiscale Codice Fiscale dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedRegistroImpreseRicercaCodicefiscaleGetWithHttpInfo(String codiceFiscale, String xTenantId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'codiceFiscale' is set
        if (codiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'codiceFiscale' when calling v1ProtectedRegistroImpreseRicercaCodicefiscaleGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedRegistroImpreseRicercaCodicefiscaleGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/registro-imprese/ricerca/codicefiscale").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
    /**
     * ricerca impresa tramite denominazione e tramite provincia (opzionale)
     * ricerca impresa tramite denominazione e tramite provincia (opzionale)
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param denominazione Denominazione dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param siglaProvincia Provincia dell&#x27;impresa (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedRegistroImpreseRicercaDenominazioneGet(String denominazione, String xTenantId, String siglaProvincia) throws RestClientException {
        return v1ProtectedRegistroImpreseRicercaDenominazioneGetWithHttpInfo(denominazione, xTenantId, siglaProvincia).getBody();
    }

    /**
     * ricerca impresa tramite denominazione e tramite provincia (opzionale)
     * ricerca impresa tramite denominazione e tramite provincia (opzionale)
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param denominazione Denominazione dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param siglaProvincia Provincia dell&#x27;impresa (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedRegistroImpreseRicercaDenominazioneGetWithHttpInfo(String denominazione, String xTenantId, String siglaProvincia) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'denominazione' is set
        if (denominazione == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'denominazione' when calling v1ProtectedRegistroImpreseRicercaDenominazioneGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedRegistroImpreseRicercaDenominazioneGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/registro-imprese/ricerca/denominazione").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "denominazione", denominazione));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "siglaProvincia", siglaProvincia));
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
    /**
     * ricerca impresa tramite provincia e n-rea
     * ricerca impresa tramite provincia e n-rea
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param numeroRea Numero REA dell&#x27;impresa (required)
     * @param siglaProvincia Sigla Cciaa dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedRegistroImpreseRicercaNreaGet(String numeroRea, String siglaProvincia, String xTenantId) throws RestClientException {
        return v1ProtectedRegistroImpreseRicercaNreaGetWithHttpInfo(numeroRea, siglaProvincia, xTenantId).getBody();
    }

    /**
     * ricerca impresa tramite provincia e n-rea
     * ricerca impresa tramite provincia e n-rea
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param numeroRea Numero REA dell&#x27;impresa (required)
     * @param siglaProvincia Sigla Cciaa dell&#x27;impresa (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedRegistroImpreseRicercaNreaGetWithHttpInfo(String numeroRea, String siglaProvincia, String xTenantId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'numeroRea' is set
        if (numeroRea == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'numeroRea' when calling v1ProtectedRegistroImpreseRicercaNreaGet");
        }
        // verify the required parameter 'siglaProvincia' is set
        if (siglaProvincia == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'siglaProvincia' when calling v1ProtectedRegistroImpreseRicercaNreaGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedRegistroImpreseRicercaNreaGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/registro-imprese/ricerca/nrea").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "numeroRea", numeroRea));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "siglaProvincia", siglaProvincia));
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
