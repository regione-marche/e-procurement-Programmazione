package io.swagger.client.api.v1021;

import it.appaltiecontratti.pcp.v1021.ApiClient;

import it.appaltiecontratti.pcp.v1021.npaGateway.CancellaSchedaRequestDTO;
import it.appaltiecontratti.pcp.v1021.npaGateway.CreaSchedaRequestDTOMapStringObject;
import it.appaltiecontratti.pcp.v1021.npaGateway.CustomUserLoaEnum;
import it.appaltiecontratti.pcp.v1021.npaGateway.DatiSchedaRequestDTOMapStringObject;
import it.appaltiecontratti.pcp.v1021.npaGateway.EModalitaInvio;
import it.appaltiecontratti.pcp.v1021.npaGateway.GenericSchedaRequestDTO;
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
@Component("io.swagger.client.api.v1021.ComunicaPostPubblicazioneResourceV1Api")
public class ComunicaPostPubblicazioneResourceV1Api {
    private ApiClient apiClient;

    public ComunicaPostPubblicazioneResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public ComunicaPostPubblicazioneResourceV1Api(ApiClient apiClient) {
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
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CancellaSchedaRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneCancellaSchedaPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per l&#x27;eliminazione di una specifica Scheda dati in lavorazione nelle fasi successive alla pubblicazione. SINCRONO
     * Eliminazione della Scheda dati in lavorazione. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneCancellaSchedaPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CancellaSchedaRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneCancellaSchedaPost");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/cancella-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la conferma dei dati inviati per una specifica Scheda. ASINCRONO - ASIMMETRICO
     * Il servizio consente di confermare i dati inviati per una specifica Scheda e, qualora il tipo di scheda lo consente, avvia la fase di pubblicazione dell&#x27;avviso. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso assegnato. ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, GenericSchedaRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneConfermaSchedaPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la conferma dei dati inviati per una specifica Scheda. ASINCRONO - ASIMMETRICO
     * Il servizio consente di confermare i dati inviati per una specifica Scheda e, qualora il tipo di scheda lo consente, avvia la fase di pubblicazione dell&#x27;avviso. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso assegnato. ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneConfermaSchedaPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, GenericSchedaRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneConfermaSchedaPost");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/conferma-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero del dettaglio di una specifica Scheda dati. SINCRONO
     * Viene recuperato il dettaglio della specifica scheda dati. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idScheda Identificativo della singola scheda restituita da ANAC nella fase di inserimento (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet(String idScheda, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneConsultaSchedaGetWithHttpInfo(idScheda, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero del dettaglio di una specifica Scheda dati. SINCRONO
     * Viene recuperato il dettaglio della specifica scheda dati. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idScheda Identificativo della singola scheda restituita da ANAC nella fase di inserimento (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneConsultaSchedaGetWithHttpInfo(String idScheda, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idScheda' is set
        if (idScheda == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idScheda' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/consulta-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idScheda", idScheda));
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
     * API Rest generica per l&#x27;inserimento di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * API Rest generica per l&#x27;inserimento delle schede dati delle fasi successive alla pubblicazione. A titolo esemplificativo si riportano di seguito alcuni eventi che determinano l&#x27;obbligo di invio dati alla PCP:Elenco Partecipanti, Aggiudicazione, Avvio Contratto, Avanzamento Contratto, Conclusione Contratto, Collaudo Contratto, Sospensione, Ripresa Esecuzione, Contratto, Risoluzione Contratto, Modifica Contratto, Subappalto, Recesso, Contenzioso, Accordo Quadro. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneCreaSchedaPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CreaSchedaRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneCreaSchedaPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest generica per l&#x27;inserimento di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * API Rest generica per l&#x27;inserimento delle schede dati delle fasi successive alla pubblicazione. A titolo esemplificativo si riportano di seguito alcuni eventi che determinano l&#x27;obbligo di invio dati alla PCP:Elenco Partecipanti, Aggiudicazione, Avvio Contratto, Avanzamento Contratto, Conclusione Contratto, Collaudo Contratto, Sospensione, Ripresa Esecuzione, Contratto, Risoluzione Contratto, Modifica Contratto, Subappalto, Recesso, Contenzioso, Accordo Quadro. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneCreaSchedaPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CreaSchedaRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneCreaSchedaPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneCreaSchedaPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneCreaSchedaPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneCreaSchedaPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneCreaSchedaPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneCreaSchedaPost");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/crea-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest generica per la modifica di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * Il servizio sostituisce la precedentemente Scheda in lavorazione. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneModificaSchedaPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, DatiSchedaRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneModificaSchedaPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest generica per la modifica di una scheda dati per le fasi successive alla pubblicazione. SINCRONO
     * Il servizio sostituisce la precedentemente Scheda in lavorazione. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneModificaSchedaPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, DatiSchedaRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneModificaSchedaPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneModificaSchedaPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneModificaSchedaPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneModificaSchedaPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneModificaSchedaPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneModificaSchedaPost");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/modifica-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la ricerca delle Schede dati inserite nelle fasi successive alla pubblicazione. SINCRONO
     * Viene recuperata la lista con i dati di sintesi delle schede.Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco della gara definito da ANAC. (A UUID specified by FC4122). Obbligatorio se cig non è stato valorizzato. (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Obbligatorio se idAppalto non è stato valorizzato. (optional)
     * @param codiceScheda Codice Scheda - fare riferimento ai valori contenuti nel file codiceScheda.json (optional)
     * @param dataCreazioneA Data di creazione della Gara - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneDa Data di creazione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param stato Codice stato dell&#x27;Appalto - fare riferimento ai valori contenuti nel file statoAppalto.json (optional)
     * @param statoScheda Codice Stato della Scheda - fare riferimento ai valori contenuti nel file statoScheda.json (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String codiceScheda, String dataCreazioneA, String dataCreazioneDa, Integer page, Integer perPage, String stato, String statoScheda, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneRicercaSchedaGetWithHttpInfo(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, codiceScheda, dataCreazioneA, dataCreazioneDa, page, perPage, stato, statoScheda, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la ricerca delle Schede dati inserite nelle fasi successive alla pubblicazione. SINCRONO
     * Viene recuperata la lista con i dati di sintesi delle schede.Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco della gara definito da ANAC. (A UUID specified by FC4122). Obbligatorio se cig non è stato valorizzato. (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Obbligatorio se idAppalto non è stato valorizzato. (optional)
     * @param codiceScheda Codice Scheda - fare riferimento ai valori contenuti nel file codiceScheda.json (optional)
     * @param dataCreazioneA Data di creazione della Gara - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneDa Data di creazione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param stato Codice stato dell&#x27;Appalto - fare riferimento ai valori contenuti nel file statoAppalto.json (optional)
     * @param statoScheda Codice Stato della Scheda - fare riferimento ai valori contenuti nel file statoScheda.json (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneRicercaSchedaGetWithHttpInfo(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String codiceScheda, String dataCreazioneA, String dataCreazioneDa, Integer page, Integer perPage, String stato, String statoScheda, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAppalto' is set
        if (idAppalto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAppalto' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/ricerca-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceScheda", codiceScheda));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneA", dataCreazioneA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneDa", dataCreazioneDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stato", stato));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "statoScheda", statoScheda));
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
     * API Rest per la validazione di una Scheda dati. ASINCRONO - ASIMMETRICO
     * L&#x27;ultimo istanza della Scheda inviata viene validata. La validazione è orchestrata da un Workflow Engine che, tramite il suo motore di regole, verifica sia se la scheda dati è coerente con lo stato dell&#x27;Appalto sia la correttezza sintattica dei dati di input. ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, GenericSchedaRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return v1ProtectedComunicaPostPubblicazioneVerificaSchedaPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la validazione di una Scheda dati. ASINCRONO - ASIMMETRICO
     * L&#x27;ultimo istanza della Scheda inviata viene validata. La validazione è orchestrata da un Workflow Engine che, tramite il suo motore di regole, verifica sia se la scheda dati è coerente con lo stato dell&#x27;Appalto sia la correttezza sintattica dei dati di input. ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param body  (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> v1ProtectedComunicaPostPubblicazioneVerificaSchedaPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, GenericSchedaRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling v1ProtectedComunicaPostPubblicazioneVerificaSchedaPost");
        }
        String path = UriComponentsBuilder.fromPath("/v1/protected/comunica-post-pubblicazione/verifica-scheda").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
