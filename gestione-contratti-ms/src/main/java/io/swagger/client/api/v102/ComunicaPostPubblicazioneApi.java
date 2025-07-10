package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AckResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CancellaSchedaRequest;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CancellaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ConfermaSchedaRequest;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ConsultaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CreaSchedaRequest;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CreaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiSchedaRequest;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ErrorResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModificaSchedaResponse;
import java.time.OffsetDateTime;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaListaResponse;
import java.util.UUID;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.VerificaSchedaRequest;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:12:50.940473900+02:00[Europe/Berlin]")
@Component("io.swagger.client.api.v102.ComunicaPostPubblicazioneApi")
public class ComunicaPostPubblicazioneApi {
    private ApiClient apiClient;

    public ComunicaPostPubblicazioneApi() {
        this(new ApiClient());
    }

    @Autowired
    public ComunicaPostPubblicazioneApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per l&#x27;eliminazione di una specifica Scheda dati in lavorazione nelle fasi successive alla pubblicazione. SINCRONO
     * Eliminazione della Scheda dati in lavorazione. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return CancellaSchedaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CancellaSchedaResponse idSchedaCancella(CancellaSchedaRequest body) throws RestClientException {
        return idSchedaCancellaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per l&#x27;eliminazione di una specifica Scheda dati in lavorazione nelle fasi successive alla pubblicazione. SINCRONO
     * Eliminazione della Scheda dati in lavorazione. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return ResponseEntity&lt;CancellaSchedaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CancellaSchedaResponse> idSchedaCancellaWithHttpInfo(CancellaSchedaRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSchedaCancella");
        }
        String path = UriComponentsBuilder.fromPath("/cancella-scheda").build().toUriString();
        
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

        ParameterizedTypeReference<CancellaSchedaResponse> returnType = new ParameterizedTypeReference<CancellaSchedaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la conferma dei dati inviati per una specifica Scheda. ASINCRONO - ASIMMETRICO
     * Il servizio consente di confermare i dati inviati per una specifica Scheda e, qualora il tipo di scheda lo consente, avvia la fase di pubblicazione dell&#x27;avviso. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso assegnato. ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return AckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AckResponse idSchedaConferma(ConfermaSchedaRequest body) throws RestClientException {
        return idSchedaConfermaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per la conferma dei dati inviati per una specifica Scheda. ASINCRONO - ASIMMETRICO
     * Il servizio consente di confermare i dati inviati per una specifica Scheda e, qualora il tipo di scheda lo consente, avvia la fase di pubblicazione dell&#x27;avviso. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso assegnato. ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return ResponseEntity&lt;AckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AckResponse> idSchedaConfermaWithHttpInfo(ConfermaSchedaRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSchedaConferma");
        }
        String path = UriComponentsBuilder.fromPath("/conferma-scheda").build().toUriString();
        
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
     * API Rest generica per l&#x27;inserimento di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * API Rest generica per l&#x27;inserimento delle schede dati delle fasi successive alla pubblicazione. A titolo esemplificativo si riportano di seguito alcuni eventi che determinano l&#x27;obbligo di invio dati alla PCP:Elenco Partecipanti, Aggiudicazione, Avvio Contratto, Avanzamento Contratto, Conclusione Contratto, Collaudo Contratto, Sospensione, Ripresa Esecuzione, Contratto, Risoluzione Contratto, Modifica Contratto, Subappalto, Recesso, Contenzioso, Accordo Quadro. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto in ingresso al servizio (required)
     * @return CreaSchedaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CreaSchedaResponse idSchedaCrea(CreaSchedaRequest body) throws RestClientException {
        return idSchedaCreaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest generica per l&#x27;inserimento di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * API Rest generica per l&#x27;inserimento delle schede dati delle fasi successive alla pubblicazione. A titolo esemplificativo si riportano di seguito alcuni eventi che determinano l&#x27;obbligo di invio dati alla PCP:Elenco Partecipanti, Aggiudicazione, Avvio Contratto, Avanzamento Contratto, Conclusione Contratto, Collaudo Contratto, Sospensione, Ripresa Esecuzione, Contratto, Risoluzione Contratto, Modifica Contratto, Subappalto, Recesso, Contenzioso, Accordo Quadro. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto in ingresso al servizio (required)
     * @return ResponseEntity&lt;CreaSchedaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CreaSchedaResponse> idSchedaCreaWithHttpInfo(CreaSchedaRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSchedaCrea");
        }
        String path = UriComponentsBuilder.fromPath("/crea-scheda").build().toUriString();
        
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

        ParameterizedTypeReference<CreaSchedaResponse> returnType = new ParameterizedTypeReference<CreaSchedaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero del dettaglio di una specifica Scheda dati. SINCRONO
     * Viene recuperato il dettaglio della specifica scheda dati. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idScheda Identificativo della singola scheda restituita da ANAC nella fase di inserimento (required)
     * @return ConsultaSchedaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ConsultaSchedaResponse idSchedaDettaglio(UUID idScheda) throws RestClientException {
        return idSchedaDettaglioWithHttpInfo(idScheda).getBody();
    }

    /**
     * API Rest per il recupero del dettaglio di una specifica Scheda dati. SINCRONO
     * Viene recuperato il dettaglio della specifica scheda dati. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idScheda Identificativo della singola scheda restituita da ANAC nella fase di inserimento (required)
     * @return ResponseEntity&lt;ConsultaSchedaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ConsultaSchedaResponse> idSchedaDettaglioWithHttpInfo(UUID idScheda) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idScheda' is set
        if (idScheda == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idScheda' when calling idSchedaDettaglio");
        }
        String path = UriComponentsBuilder.fromPath("/consulta-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idScheda", idScheda));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<ConsultaSchedaResponse> returnType = new ParameterizedTypeReference<ConsultaSchedaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest generica per la modifica di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * Il servizio sostituisce la precedentemente Scheda in lavorazione. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto in ingresso al servizio (required)
     * @return ModificaSchedaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ModificaSchedaResponse idSchedaModifica(DatiSchedaRequest body) throws RestClientException {
        return idSchedaModificaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest generica per la modifica di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * Il servizio sostituisce la precedentemente Scheda in lavorazione. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto in ingresso al servizio (required)
     * @return ResponseEntity&lt;ModificaSchedaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ModificaSchedaResponse> idSchedaModificaWithHttpInfo(DatiSchedaRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSchedaModifica");
        }
        String path = UriComponentsBuilder.fromPath("/modifica-scheda").build().toUriString();
        
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

        ParameterizedTypeReference<ModificaSchedaResponse> returnType = new ParameterizedTypeReference<ModificaSchedaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la ricerca delle Schede dati inserite nelle fasi successive alla pubblicazione. SINCRONO
     * Viene recuperata la lista con i dati di sintesi delle schede.Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco della gara definito da ANAC. (A UUID specified by FC4122). Obbligatorio se cig non è stato valorizzato. (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Obbligatorio se idAppalto non è stato valorizzato. (optional)
     * @param stato Codice stato dell&#x27;Appalto - fare riferimento ai valori contenuti nel file [statoAppalto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/statoAppalto.json) (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneA Data di crezione della Gara - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param codiceScheda Codice Scheda - fare riferimento ai valori contenuti nel file [codiceScheda.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/codiceScheda.json) (optional)
     * @param statoScheda Codice Stato della Scheda - fare riferimento ai valori contenuti nel file [statoScheda.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/statoScheda.json) (optional)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return SchedaListaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SchedaListaResponse idSchedaRicerca(UUID idAppalto, String cig, String stato, java.time.OffsetDateTime dataCreazioneDa, java.time.OffsetDateTime dataCreazioneA, String codiceScheda, String statoScheda, Integer page, Integer perPage) throws RestClientException {
        return idSchedaRicercaWithHttpInfo(idAppalto, cig, stato, dataCreazioneDa, dataCreazioneA, codiceScheda, statoScheda, page, perPage).getBody();
    }

    /**
     * API Rest per la ricerca delle Schede dati inserite nelle fasi successive alla pubblicazione. SINCRONO
     * Viene recuperata la lista con i dati di sintesi delle schede.Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco della gara definito da ANAC. (A UUID specified by FC4122). Obbligatorio se cig non è stato valorizzato. (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Obbligatorio se idAppalto non è stato valorizzato. (optional)
     * @param stato Codice stato dell&#x27;Appalto - fare riferimento ai valori contenuti nel file [statoAppalto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/statoAppalto.json) (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneA Data di crezione della Gara - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param codiceScheda Codice Scheda - fare riferimento ai valori contenuti nel file [codiceScheda.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/codiceScheda.json) (optional)
     * @param statoScheda Codice Stato della Scheda - fare riferimento ai valori contenuti nel file [statoScheda.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/statoScheda.json) (optional)
     * @param page numero della pagina da visualizzare (optional, default to 1)
     * @param perPage numero di elementi per pagina (optional, default to 20)
     * @return ResponseEntity&lt;SchedaListaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SchedaListaResponse> idSchedaRicercaWithHttpInfo(UUID idAppalto, String cig, String stato, java.time.OffsetDateTime dataCreazioneDa, java.time.OffsetDateTime dataCreazioneA, String codiceScheda, String statoScheda, Integer page, Integer perPage) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/ricerca-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stato", stato));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneDa", dataCreazioneDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneA", dataCreazioneA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceScheda", codiceScheda));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "statoScheda", statoScheda));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<SchedaListaResponse> returnType = new ParameterizedTypeReference<SchedaListaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la validazione di una Scheda dati. ASINCRONO - ASIMMETRICO
     * L&#x27;ultimo istanza della Scheda inviata viene validata. La validazione è orchestrata da un Workflow Engine che, tramite il suo motore di regole, verifica sia se la scheda dati è coerente con lo stato dell’Appalto sia la correttezza sintattica dei dati di input.  ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return AckResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AckResponse idSchedaVerifica(VerificaSchedaRequest body) throws RestClientException {
        return idSchedaVerificaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per la validazione di una Scheda dati. ASINCRONO - ASIMMETRICO
     * L&#x27;ultimo istanza della Scheda inviata viene validata. La validazione è orchestrata da un Workflow Engine che, tramite il suo motore di regole, verifica sia se la scheda dati è coerente con lo stato dell’Appalto sia la correttezza sintattica dei dati di input.  ASINCRONO - ASIMMETRICO
     * <p><b>200</b> - Richiesta accettata
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body  (required)
     * @return ResponseEntity&lt;AckResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AckResponse> idSchedaVerificaWithHttpInfo(VerificaSchedaRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSchedaVerifica");
        }
        String path = UriComponentsBuilder.fromPath("/verifica-scheda").build().toUriString();
        
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
}
