package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

import it.appaltiecontratti.pcp.v102.codeList.BaseResponse;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:02.813329700+02:00[Europe/Berlin]")
@Component("io.swagger.client.api.v102.ServiziTecniciApi")
public class ServiziTecniciApi {
    private ApiClient apiClient;

    public ServiziTecniciApi() {
        this(new ApiClient());
    }

    @Autowired
    public ServiziTecniciApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Ritorna lo stato dell&#x27;applicazione. SINCRONO
     * Ritorna lo stato dell&#x27;applicazione. A scopo di test, su base randomica puo&#x27; ritornare un errore. 
     * <p><b>200</b> - Il server ha ritornato lo status. In caso di problemi ritorna sempre un problem+json. 
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Internal Server Error
     * @return BaseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public BaseResponse idStatusShow() throws RestClientException {
        return idStatusShowWithHttpInfo().getBody();
    }

    /**
     * Ritorna lo stato dell&#x27;applicazione. SINCRONO
     * Ritorna lo stato dell&#x27;applicazione. A scopo di test, su base randomica puo&#x27; ritornare un errore. 
     * <p><b>200</b> - Il server ha ritornato lo status. In caso di problemi ritorna sempre un problem+json. 
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Internal Server Error
     * @return ResponseEntity&lt;BaseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<BaseResponse> idStatusShowWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/status").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<BaseResponse> returnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
