package io.swagger.client.api.v1021;

import it.appaltiecontratti.pcp.v1021.ApiClient;

import it.appaltiecontratti.pcp.v1021.comunicaAppalto.AckResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.AppaltoListaResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CancellaAppaltoRequest;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CancellaAppaltoResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CigListaResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ConfermaAppaltoRequest;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ConsultaAppaltoResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CreaAppaltoRequest;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CreaAppaltoResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiAppaltoRequest;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ErrorResponse;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ModificaAppaltoResponse;
import java.time.OffsetDateTime;
import java.util.UUID;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.VerificaAppaltoRequest;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")
@Component("io.swagger.client.api.v1021.ComunicaAppaltoApi")
public class ComunicaAppaltoApi {
    private ApiClient apiClient;

    public ComunicaAppaltoApi() {
        this(new ApiClient());
    }

    @Autowired
    public ComunicaAppaltoApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per l&#x27;eliminazione di un Appalto in lavorazione. SINCRONO
     * Eliminazione logica di un Appalto e dei lotti in lavorazione. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return CancellaAppaltoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CancellaAppaltoResponse idAppaltoCancella(CancellaAppaltoRequest body) throws RestClientException {
        return idAppaltoCancellaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per l&#x27;eliminazione di un Appalto in lavorazione. SINCRONO
     * Eliminazione logica di un Appalto e dei lotti in lavorazione. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return ResponseEntity&lt;CancellaAppaltoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CancellaAppaltoResponse> idAppaltoCancellaWithHttpInfo(CancellaAppaltoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idAppaltoCancella");
        }
        String path = UriComponentsBuilder.fromPath("/cancella-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<CancellaAppaltoResponse> returnType = new ParameterizedTypeReference<CancellaAppaltoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la conferma dei dati di Appalto propedeutica alla generazione dei CIG. ASINCRONO - ASIMMETRICO
     * Il servizio permette la conferma dei dati dell&#x27;Appalto inviati, avvia la fase di validazione e assegnazione dei CIG ed inizializza il Fascicolo Virtuale dell&#x27;Appalto. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso per invocare il successivo servizio pubblica-avviso. ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return AckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AckResponse idAppaltoConferma(ConfermaAppaltoRequest body) throws RestClientException {
        return idAppaltoConfermaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per la conferma dei dati di Appalto propedeutica alla generazione dei CIG. ASINCRONO - ASIMMETRICO
     * Il servizio permette la conferma dei dati dell&#x27;Appalto inviati, avvia la fase di validazione e assegnazione dei CIG ed inizializza il Fascicolo Virtuale dell&#x27;Appalto. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso per invocare il successivo servizio pubblica-avviso. ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return ResponseEntity&lt;AckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AckResponse> idAppaltoConfermaWithHttpInfo(ConfermaAppaltoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idAppaltoConferma");
        }
        String path = UriComponentsBuilder.fromPath("/conferma-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<AckResponse> returnType = new ParameterizedTypeReference<AckResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la creazione di un Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per l&#x27;inserimento della prima istanza in lavorazione di un Appalto nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo. Viene restituito l&#x27;idAppalto generato da ANAC
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Inserimento Appalto con tutti i dati aggiuntivi (required)
     * @return CreaAppaltoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CreaAppaltoResponse idAppaltoCrea(CreaAppaltoRequest body) throws RestClientException {
        return idAppaltoCreaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per la creazione di un Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per l&#x27;inserimento della prima istanza in lavorazione di un Appalto nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo. Viene restituito l&#x27;idAppalto generato da ANAC
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Inserimento Appalto con tutti i dati aggiuntivi (required)
     * @return ResponseEntity&lt;CreaAppaltoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CreaAppaltoResponse> idAppaltoCreaWithHttpInfo(CreaAppaltoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idAppaltoCrea");
        }
        String path = UriComponentsBuilder.fromPath("/crea-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<CreaAppaltoResponse> returnType = new ParameterizedTypeReference<CreaAppaltoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero del dettaglio dell&#x27;Appalto. SINCRONO
     * Servizio per la consultazione delle informazioni di dettaglio di un Appalto e dei relativi Lotti. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC alternativo al CIG. (A UUID specified by FC4122). (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC, alternativo all&#x27;idAppalto (optional)
     * @return ConsultaAppaltoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ConsultaAppaltoResponse idAppaltoDettaglio(UUID idAppalto, String cig) throws RestClientException {
        return idAppaltoDettaglioWithHttpInfo(idAppalto, cig).getBody();
    }

    /**
     * API Rest per il recupero del dettaglio dell&#x27;Appalto. SINCRONO
     * Servizio per la consultazione delle informazioni di dettaglio di un Appalto e dei relativi Lotti. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC alternativo al CIG. (A UUID specified by FC4122). (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC, alternativo all&#x27;idAppalto (optional)
     * @return ResponseEntity&lt;ConsultaAppaltoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ConsultaAppaltoResponse> idAppaltoDettaglioWithHttpInfo(UUID idAppalto, String cig) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/consulta-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<ConsultaAppaltoResponse> returnType = new ParameterizedTypeReference<ConsultaAppaltoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la modifica dell&#x27;Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per sostituire la precedente istanza dell’Appalto in lavorazione. L&#x27;Appalto è nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Modifica dell&#x27;Appalto con tutti i dati aggiuntivi (required)
     * @return ModificaAppaltoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ModificaAppaltoResponse idAppaltoModifica(DatiAppaltoRequest body) throws RestClientException {
        return idAppaltoModificaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per la modifica dell&#x27;Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per sostituire la precedente istanza dell’Appalto in lavorazione. L&#x27;Appalto è nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Modifica dell&#x27;Appalto con tutti i dati aggiuntivi (required)
     * @return ResponseEntity&lt;ModificaAppaltoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ModificaAppaltoResponse> idAppaltoModificaWithHttpInfo(DatiAppaltoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idAppaltoModifica");
        }
        String path = UriComponentsBuilder.fromPath("/modifica-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<ModificaAppaltoResponse> returnType = new ParameterizedTypeReference<ModificaAppaltoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la ricerca delle Gare-Lotto dell&#x27;Appalto. SINCRONO
     * Viene recuperata la lista con i dati di sintesi dell&#x27;Appalto e dei rispettivi lotti. Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param codiceAppalto Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante (optional)
     * @param cig Identificativo univoco Appalto-Lotto rilasciato da ANAC (optional)
     * @param lotIdentifier Id univoco del lotto generato dalla stazione appaltante - corrisponde al campo bt-137 - Purpose Lot Identifier del TED - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione del codiceAppalto o cig. (optional)
     * @param stato Codice Stato dell&#x27;Appalto - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione di almeno un altro parametro - fare riferimento ai valori contenuti nel file [statoAppalto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/statoAppalto.json) (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneA Data di crezione dell&#x27;Appalto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return AppaltoListaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AppaltoListaResponse idAppaltoRicerca(String codiceAppalto, String cig, String lotIdentifier, String stato, OffsetDateTime dataCreazioneDa, OffsetDateTime dataCreazioneA, Integer page, Integer perPage) throws RestClientException {
        return idAppaltoRicercaWithHttpInfo(codiceAppalto, cig, lotIdentifier, stato, dataCreazioneDa, dataCreazioneA, page, perPage).getBody();
    }

    /**
     * API Rest per la ricerca delle Gare-Lotto dell&#x27;Appalto. SINCRONO
     * Viene recuperata la lista con i dati di sintesi dell&#x27;Appalto e dei rispettivi lotti. Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param codiceAppalto Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante (optional)
     * @param cig Identificativo univoco Appalto-Lotto rilasciato da ANAC (optional)
     * @param lotIdentifier Id univoco del lotto generato dalla stazione appaltante - corrisponde al campo bt-137 - Purpose Lot Identifier del TED - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione del codiceAppalto o cig. (optional)
     * @param stato Codice Stato dell&#x27;Appalto - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione di almeno un altro parametro - fare riferimento ai valori contenuti nel file [statoAppalto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/statoAppalto.json) (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneA Data di crezione dell&#x27;Appalto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return ResponseEntity&lt;AppaltoListaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AppaltoListaResponse> idAppaltoRicercaWithHttpInfo(String codiceAppalto, String cig, String lotIdentifier, String stato, OffsetDateTime dataCreazioneDa, OffsetDateTime dataCreazioneA, Integer page, Integer perPage) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/ricerca-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceAppalto", codiceAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lotIdentifier", lotIdentifier));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stato", stato));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneDa", dataCreazioneDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneA", dataCreazioneA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<AppaltoListaResponse> returnType = new ParameterizedTypeReference<AppaltoListaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la validazione dell&#x27;Appalto. ASINCRONO - ASIMMETRICO
     * L&#x27;ultima istanza di appalto inviata viene validata. In base alla tipologia dell&#x27;appalto viene eseguita una validazione sintattica dei dati di input dell&#x27;appalto (eForm, ESPDRequest, anacForm). ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return AckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AckResponse idAppaltoVerifica(VerificaAppaltoRequest body) throws RestClientException {
        return idAppaltoVerificaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per la validazione dell&#x27;Appalto. ASINCRONO - ASIMMETRICO
     * L&#x27;ultima istanza di appalto inviata viene validata. In base alla tipologia dell&#x27;appalto viene eseguita una validazione sintattica dei dati di input dell&#x27;appalto (eForm, ESPDRequest, anacForm). ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return ResponseEntity&lt;AckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AckResponse> idAppaltoVerificaWithHttpInfo(VerificaAppaltoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idAppaltoVerifica");
        }
        String path = UriComponentsBuilder.fromPath("/verifica-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<AckResponse> returnType = new ParameterizedTypeReference<AckResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero dei CIG generati da ANAC e assegnati ai lotti dell’Appalto. SINCRONO
     * Per un Appalto il servizio restituiti le coppie Lotto - CIG. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC (required)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return CigListaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CigListaResponse idCIGRecupera(UUID idAppalto, Integer page, Integer perPage) throws RestClientException {
        return idCIGRecuperaWithHttpInfo(idAppalto, page, perPage).getBody();
    }

    /**
     * API Rest per il recupero dei CIG generati da ANAC e assegnati ai lotti dell’Appalto. SINCRONO
     * Per un Appalto il servizio restituiti le coppie Lotto - CIG. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC (required)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return ResponseEntity&lt;CigListaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CigListaResponse> idCIGRecuperaWithHttpInfo(UUID idAppalto, Integer page, Integer perPage) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAppalto' is set
        if (idAppalto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAppalto' when calling idCIGRecupera");
        }
        String path = UriComponentsBuilder.fromPath("/recupera-cig").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<CigListaResponse> returnType = new ParameterizedTypeReference<CigListaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
