package io.swagger.client.api.v1021;

import it.appaltiecontratti.pcp.v1021.ApiClient;

import it.appaltiecontratti.pcp.v1021.serviziComuni.ErrorResponse;
import it.appaltiecontratti.pcp.v1021.serviziComuni.EsitoOperazioneListaResponse;
import it.appaltiecontratti.pcp.v1021.serviziComuni.StatoResponse;
import java.util.UUID;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:36.947730900+02:00[Europe/Rome]")
@Component("io.swagger.client.api.v1021.ServiziComuniApi")
public class ServiziComuniApi {
    private ApiClient apiClient;

    public ServiziComuniApi() {
        this(new ApiClient());
    }

    @Autowired
    public ServiziComuniApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per il recupero dello stato dell&#x27;Appalto, del Lotto o del Contratto. SINCRONO
     * Servizio per il recupero dello stato in cui si trova l’Appalto, il Lotto o il Contratto. Il compito dispositivo di aggiornare lo stato proveniente dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dello stato da parte del servizio insiste su uno stato PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param idContratto Identificativo univoco del Contratto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @return StatoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StatoResponse idAppaltoStato(UUID idAppalto, String cig, UUID idContratto) throws RestClientException {
        return idAppaltoStatoWithHttpInfo(idAppalto, cig, idContratto).getBody();
    }

    /**
     * API Rest per il recupero dello stato dell&#x27;Appalto, del Lotto o del Contratto. SINCRONO
     * Servizio per il recupero dello stato in cui si trova l’Appalto, il Lotto o il Contratto. Il compito dispositivo di aggiornare lo stato proveniente dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dello stato da parte del servizio insiste su uno stato PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param idContratto Identificativo univoco del Contratto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @return ResponseEntity&lt;StatoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatoResponse> idAppaltoStatoWithHttpInfo(UUID idAppalto, String cig, UUID idContratto) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/stato-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idContratto", idContratto));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<StatoResponse> returnType = new ParameterizedTypeReference<StatoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero dell&#x27;esito dell&#x27;operazione. SINCRONO
     * Il compito dispositivo di aggiornare l&#x27;esito delle operazioni provenienti dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dell&#x27;esito operazione da parte del servizio insiste su uno stato dell&#x27;esito PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>200</b> - Esecuzione avvenuta con successo
     * <p><b>404</b> - Identificativo non trovato
     * <p><b>400</b> - Richiesta malformata
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore interno avvenuto
     * @param tipoOperazione Tipo operazione di cui si vuole conoscere l&#x27;esito - fare riferimento ai valori contenuti nel file [tipoOperazione.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoOperazione.json) (required)
     * @param tipoRicerca Codice Tipo di ricerca da eseguire - fare riferimento ai valori contenuti nel file [tipoRicercaOperazione.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoRicercaOperazione.json) (required)
     * @param idAppalto identificativo univoco dell&#x27;Appalto definito da ANAC. Obbligatorio se idPianificazione non è stato valorizzato (A UUID specified by RFC4122) (optional)
     * @param idPianificazione identificativo univoco della Pianificazione definito da ANAC. Obbligatorio se idAppalto non è stato valorizzato (A UUID specified by RFC4122) (optional)
     * @param esito Codice sintetico esito operazione - fare riferimento ai valori contenuti nel file [esito.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/esito.json) (optional)
     * @return EsitoOperazioneListaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public EsitoOperazioneListaResponse idOperazioneEsito(String tipoOperazione, String tipoRicerca, UUID idAppalto, UUID idPianificazione, String esito) throws RestClientException {
        return idOperazioneEsitoWithHttpInfo(tipoOperazione, tipoRicerca, idAppalto, idPianificazione, esito).getBody();
    }

    /**
     * API Rest per il recupero dell&#x27;esito dell&#x27;operazione. SINCRONO
     * Il compito dispositivo di aggiornare l&#x27;esito delle operazioni provenienti dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dell&#x27;esito operazione da parte del servizio insiste su uno stato dell&#x27;esito PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>200</b> - Esecuzione avvenuta con successo
     * <p><b>404</b> - Identificativo non trovato
     * <p><b>400</b> - Richiesta malformata
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore interno avvenuto
     * @param tipoOperazione Tipo operazione di cui si vuole conoscere l&#x27;esito - fare riferimento ai valori contenuti nel file [tipoOperazione.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoOperazione.json) (required)
     * @param tipoRicerca Codice Tipo di ricerca da eseguire - fare riferimento ai valori contenuti nel file [tipoRicercaOperazione.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoRicercaOperazione.json) (required)
     * @param idAppalto identificativo univoco dell&#x27;Appalto definito da ANAC. Obbligatorio se idPianificazione non è stato valorizzato (A UUID specified by RFC4122) (optional)
     * @param idPianificazione identificativo univoco della Pianificazione definito da ANAC. Obbligatorio se idAppalto non è stato valorizzato (A UUID specified by RFC4122) (optional)
     * @param esito Codice sintetico esito operazione - fare riferimento ai valori contenuti nel file [esito.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/esito.json) (optional)
     * @return ResponseEntity&lt;EsitoOperazioneListaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EsitoOperazioneListaResponse> idOperazioneEsitoWithHttpInfo(String tipoOperazione, String tipoRicerca, UUID idAppalto, UUID idPianificazione, String esito) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'tipoOperazione' is set
        if (tipoOperazione == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tipoOperazione' when calling idOperazioneEsito");
        }
        // verify the required parameter 'tipoRicerca' is set
        if (tipoRicerca == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tipoRicerca' when calling idOperazioneEsito");
        }
        String path = UriComponentsBuilder.fromPath("/esito-operazione").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idPianificazione", idPianificazione));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "tipoOperazione", tipoOperazione));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "tipoRicerca", tipoRicerca));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "esito", esito));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<EsitoOperazioneListaResponse> returnType = new ParameterizedTypeReference<EsitoOperazioneListaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
