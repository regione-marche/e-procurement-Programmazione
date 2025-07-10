package io.swagger.client.api.v1021;

import it.appaltiecontratti.pcp.v1021.ApiClient;

import it.appaltiecontratti.pcp.v1021.npaGateway.CustomUserLoaEnum;
import it.appaltiecontratti.pcp.v1021.npaGateway.EModalitaInvio;
import it.appaltiecontratti.pcp.v1021.npaGateway.InlineResponse400;
import it.appaltiecontratti.pcp.v1021.npaGateway.UserIdpTypeEnum;

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
@Component("io.swagger.client.api.v1021.ServiziComuniResourceV1Api")
public class ServiziComuniResourceV1Api {
    private ApiClient apiClient;

    public ServiziComuniResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public ServiziComuniResourceV1Api(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per il recupero dell&#x27;esito dell&#x27;operazione. SINCRONO
     * Il compito dispositivo di aggiornare l&#x27;esito delle operazioni provenienti dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dell&#x27;esito operazione da parte del servizio insiste su uno stato dell&#x27;esito PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param tipoOperazione Tipo operazione di cui si vuole conoscere l&#x27;esito - fare riferimento ai valori contenuti nel file tipoOperazione.json (required)
     * @param tipoRicerca Codice Tipo di ricerca da eseguire - fare riferimento ai valori contenuti nel file tipoRicercaOperazione.json (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param esito Codice sintetico esito operazione - fare riferimento ai valori contenuti nel file esito.json (optional)
     * @param idAppalto identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by RFC4122) (optional)
     * @param idPianificazione identificativo univoco della Pianificazione definito da ANAC. Obbligatorio se idAppalto non è stato valorizzato (A UUID specified by RFC4122) (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedServiziComuniEsitoOperazioneGet(String tipoOperazione, String tipoRicerca, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String esito, String idAppalto, String idPianificazione, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedServiziComuniEsitoOperazioneGetWithHttpInfo(tipoOperazione, tipoRicerca, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, esito, idAppalto, idPianificazione, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero dell&#x27;esito dell&#x27;operazione. SINCRONO
     * Il compito dispositivo di aggiornare l&#x27;esito delle operazioni provenienti dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dell&#x27;esito operazione da parte del servizio insiste su uno stato dell&#x27;esito PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param tipoOperazione Tipo operazione di cui si vuole conoscere l&#x27;esito - fare riferimento ai valori contenuti nel file tipoOperazione.json (required)
     * @param tipoRicerca Codice Tipo di ricerca da eseguire - fare riferimento ai valori contenuti nel file tipoRicercaOperazione.json (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param esito Codice sintetico esito operazione - fare riferimento ai valori contenuti nel file esito.json (optional)
     * @param idAppalto identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by RFC4122) (optional)
     * @param idPianificazione identificativo univoco della Pianificazione definito da ANAC. Obbligatorio se idAppalto non è stato valorizzato (A UUID specified by RFC4122) (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedServiziComuniEsitoOperazioneGetWithHttpInfo(String tipoOperazione, String tipoRicerca, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String esito, String idAppalto, String idPianificazione, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'tipoOperazione' is set
        if (tipoOperazione == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tipoOperazione' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'tipoRicerca' is set
        if (tipoRicerca == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tipoRicerca' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedServiziComuniEsitoOperazioneGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/servizi-comuni/esito-operazione").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "esito", esito));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idPianificazione", idPianificazione));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "tipoOperazione", tipoOperazione));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "tipoRicerca", tipoRicerca));
        if (xSACodiceFiscale != null)
            headerParams.add("X-SACodiceFiscale", apiClient.parameterToString(xSACodiceFiscale));
        if (xSAcodiceAUSA != null)
            headerParams.add("X-SAcodiceAUSA", apiClient.parameterToString(xSAcodiceAUSA));
        if (xModalitaInvio != null)
            headerParams.add("X-modalitaInvio", apiClient.parameterToString(xModalitaInvio));
        if (xRegCodiceComponente != null)
            headerParams.add("X-regCodiceComponente", apiClient.parameterToString(xRegCodiceComponente));
        if (xRegCodicePiattaforma != null)
            headerParams.add("X-regCodicePiattaforma", apiClient.parameterToString(xRegCodicePiattaforma));
        if (xTenantId != null)
            headerParams.add("X-tenantId", apiClient.parameterToString(xTenantId));
        if (xTipologicaScheda != null)
            headerParams.add("X-tipologicaScheda", apiClient.parameterToString(xTipologicaScheda));
        if (xUserCodiceFiscale != null)
            headerParams.add("X-userCodiceFiscale", apiClient.parameterToString(xUserCodiceFiscale));
        if (xUserIdpType != null)
            headerParams.add("X-userIdpType", apiClient.parameterToString(xUserIdpType));
        if (xUserLoa != null)
            headerParams.add("X-userLoa", apiClient.parameterToString(xUserLoa));
        if (xUserRole != null)
            headerParams.add("X-userRole", apiClient.parameterToString(xUserRole));
        if (xVersioneTracciato != null)
            headerParams.add("X-versioneTracciato", apiClient.parameterToString(xVersioneTracciato));

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
     * API Rest per il recupero dello stato dell&#x27;Appalto, del Lotto o del Contratto. SINCRONO
     * Servizio per il recupero dello stato in cui si trova l&#x27;Appalto, il Lotto o il Contratto. Il compito dispositivo di aggiornare lo stato proveniente dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dello stato da parte del servizio insiste su uno stato PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param idContratto Identificativo univoco del Contratto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedServiziComuniStatoAppaltoGet(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String idAppalto, String idContratto, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedServiziComuniStatoAppaltoGetWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, idAppalto, idContratto, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero dello stato dell&#x27;Appalto, del Lotto o del Contratto. SINCRONO
     * Servizio per il recupero dello stato in cui si trova l&#x27;Appalto, il Lotto o il Contratto. Il compito dispositivo di aggiornare lo stato proveniente dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dello stato da parte del servizio insiste su uno stato PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param idContratto Identificativo univoco del Contratto definito da ANAC. Alternativo agli altri parametri di input. (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedServiziComuniStatoAppaltoGetWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String idAppalto, String idContratto, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedServiziComuniStatoAppaltoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedServiziComuniStatoAppaltoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedServiziComuniStatoAppaltoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedServiziComuniStatoAppaltoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedServiziComuniStatoAppaltoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedServiziComuniStatoAppaltoGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/servizi-comuni/stato-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idContratto", idContratto));
        if (xSACodiceFiscale != null)
            headerParams.add("X-SACodiceFiscale", apiClient.parameterToString(xSACodiceFiscale));
        if (xSAcodiceAUSA != null)
            headerParams.add("X-SAcodiceAUSA", apiClient.parameterToString(xSAcodiceAUSA));
        if (xModalitaInvio != null)
            headerParams.add("X-modalitaInvio", apiClient.parameterToString(xModalitaInvio));
        if (xRegCodiceComponente != null)
            headerParams.add("X-regCodiceComponente", apiClient.parameterToString(xRegCodiceComponente));
        if (xRegCodicePiattaforma != null)
            headerParams.add("X-regCodicePiattaforma", apiClient.parameterToString(xRegCodicePiattaforma));
        if (xTenantId != null)
            headerParams.add("X-tenantId", apiClient.parameterToString(xTenantId));
        if (xTipologicaScheda != null)
            headerParams.add("X-tipologicaScheda", apiClient.parameterToString(xTipologicaScheda));
        if (xUserCodiceFiscale != null)
            headerParams.add("X-userCodiceFiscale", apiClient.parameterToString(xUserCodiceFiscale));
        if (xUserIdpType != null)
            headerParams.add("X-userIdpType", apiClient.parameterToString(xUserIdpType));
        if (xUserLoa != null)
            headerParams.add("X-userLoa", apiClient.parameterToString(xUserLoa));
        if (xUserRole != null)
            headerParams.add("X-userRole", apiClient.parameterToString(xUserRole));
        if (xVersioneTracciato != null)
            headerParams.add("X-versioneTracciato", apiClient.parameterToString(xVersioneTracciato));

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
