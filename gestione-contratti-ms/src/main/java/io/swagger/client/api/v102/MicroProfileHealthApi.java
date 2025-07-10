package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

import it.appaltiecontratti.pcp.v102.npaGateway.HealthCheckResponse;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:30.797833200+02:00[Europe/Berlin]")
@Component("io.swagger.client.api.v102.MicroProfileHealthApi")
public class MicroProfileHealthApi {
    private ApiClient apiClient;

    public MicroProfileHealthApi() {
        this(new ApiClient());
    }

    @Autowired
    public MicroProfileHealthApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * The Liveness check of this application
     * Check the liveness of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return HealthCheckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HealthCheckResponse microprofileHealthLiveness() throws RestClientException {
        return microprofileHealthLivenessWithHttpInfo().getBody();
    }

    /**
     * The Liveness check of this application
     * Check the liveness of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return ResponseEntity&lt;HealthCheckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HealthCheckResponse> microprofileHealthLivenessWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/q/health/live").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<HealthCheckResponse> returnType = new ParameterizedTypeReference<HealthCheckResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * The Readiness check of this application
     * Check the readiness of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return HealthCheckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HealthCheckResponse microprofileHealthReadiness() throws RestClientException {
        return microprofileHealthReadinessWithHttpInfo().getBody();
    }

    /**
     * The Readiness check of this application
     * Check the readiness of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return ResponseEntity&lt;HealthCheckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HealthCheckResponse> microprofileHealthReadinessWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/q/health/ready").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<HealthCheckResponse> returnType = new ParameterizedTypeReference<HealthCheckResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * An aggregated view of the Liveness, Readiness and Startup of this application
     * Check the health of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return HealthCheckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HealthCheckResponse microprofileHealthRoot() throws RestClientException {
        return microprofileHealthRootWithHttpInfo().getBody();
    }

    /**
     * An aggregated view of the Liveness, Readiness and Startup of this application
     * Check the health of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return ResponseEntity&lt;HealthCheckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HealthCheckResponse> microprofileHealthRootWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/q/health").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<HealthCheckResponse> returnType = new ParameterizedTypeReference<HealthCheckResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * The Startup check of this application
     * Check the startup of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return HealthCheckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HealthCheckResponse microprofileHealthStartup() throws RestClientException {
        return microprofileHealthStartupWithHttpInfo().getBody();
    }

    /**
     * The Startup check of this application
     * Check the startup of the application
     * <p><b>200</b> - OK
     * <p><b>503</b> - Service Unavailable
     * <p><b>500</b> - Internal Server Error
     * @return ResponseEntity&lt;HealthCheckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HealthCheckResponse> microprofileHealthStartupWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/q/health/started").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<HealthCheckResponse> returnType = new ParameterizedTypeReference<HealthCheckResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
