package io.swagger.client.api.v1021;

import it.appaltiecontratti.pcp.v1021.ApiClient;

import it.appaltiecontratti.pcp.v1021.gestioneUtenti.AggiungiSoggettoResponse;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.EliminaSoggettoRequest;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.EliminaSoggettoResponse;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.ErrorResponse;
import java.time.OffsetDateTime;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.PresaCaricoRequest;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.PresaCaricoResponse;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.RecuperaProfiloResponse;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.SoggettoListaResponse;
import it.appaltiecontratti.pcp.v1021.gestioneUtenti.SoggettoRequest;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:57.347736900+02:00[Europe/Rome]")
@Component("io.swagger.client.api.v1021.GestioneUtentiApi")
public class GestioneUtentiApi {
    private ApiClient apiClient;

    public GestioneUtentiApi() {
        this(new ApiClient());
    }

    @Autowired
    public GestioneUtentiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per la ricerca dei soggetti incaricati.Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. SINCRONO
     * viene recuperata la lista con i dati di sintesi dei soggetti incaricati. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (required)
     * @param cig Non gestito nella prima release - Identificativo univoco Gara-Lotto rilasciato da ANAC (optional)
     * @param codiceFiscale codice fiscale del soggetto (optional)
     * @param ruolo Codice ruolo di un soggetto - fare riferimento ai valori contenuti nel file [tipoRuolo.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoRuolo.json) (optional)
     * @param servizio Non gestito nella prima release - Codice servizio delegato al soggetto - fare riferimento ai valori contenuti nel file [tipoServizioSoggetto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoServizioSoggetto.json) (optional)
     * @param operazione Non gestito nella prima release - Codice operazione delegata al soggetto - fare riferimento ai valori contenuti nel file [tipoOperazioneSoggetto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoOperazioneSoggetto.json) (optional)
     * @param dataInizioDa Data inizio incarico soggetto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataInizioA Data inizio incarico soggetto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataFineDa Data fine incarico soggetto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataFineA data fine incarico soggetto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @return SoggettoListaResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SoggettoListaResponse idIncaricatoRicerca(UUID idAppalto, String cig, String codiceFiscale, String ruolo, String servizio, String operazione, OffsetDateTime dataInizioDa, OffsetDateTime dataInizioA, OffsetDateTime dataFineDa, OffsetDateTime dataFineA) throws RestClientException {
        return idIncaricatoRicercaWithHttpInfo(idAppalto, cig, codiceFiscale, ruolo, servizio, operazione, dataInizioDa, dataInizioA, dataFineDa, dataFineA).getBody();
    }

    /**
     * API Rest per la ricerca dei soggetti incaricati.Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. SINCRONO
     * viene recuperata la lista con i dati di sintesi dei soggetti incaricati. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (required)
     * @param cig Non gestito nella prima release - Identificativo univoco Gara-Lotto rilasciato da ANAC (optional)
     * @param codiceFiscale codice fiscale del soggetto (optional)
     * @param ruolo Codice ruolo di un soggetto - fare riferimento ai valori contenuti nel file [tipoRuolo.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoRuolo.json) (optional)
     * @param servizio Non gestito nella prima release - Codice servizio delegato al soggetto - fare riferimento ai valori contenuti nel file [tipoServizioSoggetto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoServizioSoggetto.json) (optional)
     * @param operazione Non gestito nella prima release - Codice operazione delegata al soggetto - fare riferimento ai valori contenuti nel file [tipoOperazioneSoggetto.json](https://raw.githubusercontent.com/anticorruzione/npa/main/docs/modello-dati/tipologiche/tipoOperazioneSoggetto.json) (optional)
     * @param dataInizioDa Data inizio incarico soggetto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataInizioA Data inizio incarico soggetto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataFineDa Data fine incarico soggetto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataFineA data fine incarico soggetto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @return ResponseEntity&lt;SoggettoListaResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SoggettoListaResponse> idIncaricatoRicercaWithHttpInfo(UUID idAppalto, String cig, String codiceFiscale, String ruolo, String servizio, String operazione, OffsetDateTime dataInizioDa, OffsetDateTime dataInizioA, OffsetDateTime dataFineDa, OffsetDateTime dataFineA) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAppalto' is set
        if (idAppalto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAppalto' when calling idIncaricatoRicerca");
        }
        String path = UriComponentsBuilder.fromPath("/ricerca-soggetti").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceFiscale", codiceFiscale));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ruolo", ruolo));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "servizio", servizio));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "operazione", operazione));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataInizioDa", dataInizioDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataInizioA", dataInizioA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataFineDa", dataFineDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataFineA", dataFineA));

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<SoggettoListaResponse> returnType = new ParameterizedTypeReference<SoggettoListaResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per l’associazione di un Responsabile di Progetto. SINCRONO
     * servizio utile per l’associazione di un responsabile di progetto. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto generico in ingresso al servizio (required)
     * @return PresaCaricoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PresaCaricoResponse idPresaCarico(PresaCaricoRequest body) throws RestClientException {
        return idPresaCaricoWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per l’associazione di un Responsabile di Progetto. SINCRONO
     * servizio utile per l’associazione di un responsabile di progetto. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto generico in ingresso al servizio (required)
     * @return ResponseEntity&lt;PresaCaricoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PresaCaricoResponse> idPresaCaricoWithHttpInfo(PresaCaricoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idPresaCarico");
        }
        String path = UriComponentsBuilder.fromPath("/presa-carico").build().toUriString();
        
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

        ParameterizedTypeReference<PresaCaricoResponse> returnType = new ParameterizedTypeReference<PresaCaricoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero del profilo utente. SINCRONO
     * Viene recuperata il profilo utente contenente i dati della stazione appaltante che viene rappresentata . SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @return RecuperaProfiloResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public RecuperaProfiloResponse idProfiloRecupera() throws RestClientException {
        return idProfiloRecuperaWithHttpInfo().getBody();
    }

    /**
     * API Rest per il recupero del profilo utente. SINCRONO
     * Viene recuperata il profilo utente contenente i dati della stazione appaltante che viene rappresentata . SINCRONO
     * <p><b>200</b> - Operazione terminato con successo
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @return ResponseEntity&lt;RecuperaProfiloResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecuperaProfiloResponse> idProfiloRecuperaWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/recupera-profilo").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "apiKeyAuth" };

        ParameterizedTypeReference<RecuperaProfiloResponse> returnType = new ParameterizedTypeReference<RecuperaProfiloResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per associare un soggetto delegato. SINCRONO
     * servizio che ha lo scopo aggiungere l’istanza di un soggetto delegato. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto generico in ingresso al servizio (required)
     * @return AggiungiSoggettoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AggiungiSoggettoResponse idSoggettoAggiungi(SoggettoRequest body) throws RestClientException {
        return idSoggettoAggiungiWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per associare un soggetto delegato. SINCRONO
     * servizio che ha lo scopo aggiungere l’istanza di un soggetto delegato. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto generico in ingresso al servizio (required)
     * @return ResponseEntity&lt;AggiungiSoggettoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AggiungiSoggettoResponse> idSoggettoAggiungiWithHttpInfo(SoggettoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSoggettoAggiungi");
        }
        String path = UriComponentsBuilder.fromPath("/aggiungi-soggetto").build().toUriString();
        
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

        ParameterizedTypeReference<AggiungiSoggettoResponse> returnType = new ParameterizedTypeReference<AggiungiSoggettoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per eliminare da un ruolo un soggetto delegato. SINCRONO
     * servizio di cancellazione logica di un soggetto. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto generico in ingresso al servizio (required)
     * @return EliminaSoggettoResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public EliminaSoggettoResponse idSoggettoElimina(EliminaSoggettoRequest body) throws RestClientException {
        return idSoggettoEliminaWithHttpInfo(body).getBody();
    }

    /**
     * API Rest per eliminare da un ruolo un soggetto delegato. SINCRONO
     * servizio di cancellazione logica di un soggetto. SINCRONO
     * <p><b>200</b> - Operazione terminato con successo.
     * <p><b>400</b> - Richiesta malformattata
     * <p><b>404</b> - Dato non trovato
     * <p><b>429</b> - Limite di richieste raggiunto
     * <p><b>500</b> - Errore generico. Operazione terminato con errore. Viene restitutito il dettaglio dell&#x27;errore
     * @param body Oggetto generico in ingresso al servizio (required)
     * @return ResponseEntity&lt;EliminaSoggettoResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EliminaSoggettoResponse> idSoggettoEliminaWithHttpInfo(EliminaSoggettoRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling idSoggettoElimina");
        }
        String path = UriComponentsBuilder.fromPath("/elimina-soggetto").build().toUriString();
        
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

        ParameterizedTypeReference<EliminaSoggettoResponse> returnType = new ParameterizedTypeReference<EliminaSoggettoResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
