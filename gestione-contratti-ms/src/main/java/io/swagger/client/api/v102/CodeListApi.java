package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

import it.appaltiecontratti.pcp.v102.codeList.ErrorResponse;
import java.time.OffsetDateTime;
import it.appaltiecontratti.pcp.v102.codeList.TipologicaItemResponse;
import it.appaltiecontratti.pcp.v102.codeList.TipologicaListaResponse;
import it.appaltiecontratti.pcp.v102.codeList.TipologicaResponse;

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
@Component("io.swagger.client.api.v102.CodeListApi")
public class CodeListApi {
    private ApiClient apiClient;

    public CodeListApi() {
        this(new ApiClient());
    }

    @Autowired
    public CodeListApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per il recupero dell&#x27;elenco delle tipologiche disponibili. SINCRONO
     * Il servizio restituiti un&#x27;elenco delle tipologiche disponibili. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return TipologicaListaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public TipologicaListaResponse idRecuperaElencoTipologiche(Integer page, Integer perPage) throws RestClientException {
        return idRecuperaElencoTipologicheWithHttpInfo(page, perPage).getBody();
    }

    /**
     * API Rest per il recupero dell&#x27;elenco delle tipologiche disponibili. SINCRONO
     * Il servizio restituiti un&#x27;elenco delle tipologiche disponibili. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return ResponseEntity&lt;TipologicaListaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TipologicaListaResponse> idRecuperaElencoTipologicheWithHttpInfo(Integer page, Integer perPage) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/recupera-elenco-tipologiche").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<TipologicaListaResponse> returnType = new ParameterizedTypeReference<TipologicaListaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero di un singolo item di una tipologica. SINCRONO
     * Il servizio restituiti il valore di un singolo item che si vuole recuperare dalla tipologica. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idTipologica Identificativo della tipologica (required)
     * @param codice Codice del valore che si vuole recuperare dalla tipologica (required)
     * @return TipologicaItemResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public TipologicaItemResponse idRecuperaItemTipologica(String idTipologica, String codice) throws RestClientException {
        return idRecuperaItemTipologicaWithHttpInfo(idTipologica, codice).getBody();
    }

    /**
     * API Rest per il recupero di un singolo item di una tipologica. SINCRONO
     * Il servizio restituiti il valore di un singolo item che si vuole recuperare dalla tipologica. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idTipologica Identificativo della tipologica (required)
     * @param codice Codice del valore che si vuole recuperare dalla tipologica (required)
     * @return ResponseEntity&lt;TipologicaItemResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TipologicaItemResponse> idRecuperaItemTipologicaWithHttpInfo(String idTipologica, String codice) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idTipologica' is set
        if (idTipologica == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idTipologica' when calling idRecuperaItemTipologica");
        }
        // verify the required parameter 'codice' is set
        if (codice == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'codice' when calling idRecuperaItemTipologica");
        }
        String path = UriComponentsBuilder.fromPath("/recupera-valore-tipologica").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idTipologica", idTipologica));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codice", codice));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<TipologicaItemResponse> returnType = new ParameterizedTypeReference<TipologicaItemResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero di una specifica tipologica. SINCRONO
     * Il servizio restituiti l&#x27;elenco di una singola tipologica. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idTipologica Identificativo della tipologica (required)
     * @param dataInizio Data di inizio (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataFine Data di fine (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return TipologicaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public TipologicaResponse idRecuperaTipologica(String idTipologica, java.time.OffsetDateTime dataInizio, java.time.OffsetDateTime dataFine, Integer page, Integer perPage) throws RestClientException {
        return idRecuperaTipologicaWithHttpInfo(idTipologica, dataInizio, dataFine, page, perPage).getBody();
    }

    /**
     * API Rest per il recupero di una specifica tipologica. SINCRONO
     * Il servizio restituiti l&#x27;elenco di una singola tipologica. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idTipologica Identificativo della tipologica (required)
     * @param dataInizio Data di inizio (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataFine Data di fine (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return ResponseEntity&lt;TipologicaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TipologicaResponse> idRecuperaTipologicaWithHttpInfo(String idTipologica, java.time.OffsetDateTime dataInizio, java.time.OffsetDateTime dataFine, Integer page, Integer perPage) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idTipologica' is set
        if (idTipologica == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idTipologica' when calling idRecuperaTipologica");
        }
        String path = UriComponentsBuilder.fromPath("/recupera-tipologica").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idTipologica", idTipologica));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataInizio", dataInizio));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataFine", dataFine));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<TipologicaResponse> returnType = new ParameterizedTypeReference<TipologicaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
