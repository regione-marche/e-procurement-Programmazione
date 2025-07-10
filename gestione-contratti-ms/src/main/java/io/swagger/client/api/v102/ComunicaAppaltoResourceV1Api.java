package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

import it.appaltiecontratti.pcp.v102.npaGateway.CancellaAppaltoRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.ComunicaAppaltoRequestDTOMapStringObject;
import it.appaltiecontratti.pcp.v102.npaGateway.CustomUserLoaEnum;
import it.appaltiecontratti.pcp.v102.npaGateway.EModalitaInvio;
import it.appaltiecontratti.pcp.v102.npaGateway.InlineResponse400;
import it.appaltiecontratti.pcp.v102.npaGateway.UserIdpTypeEnum;

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
@Component("io.swagger.client.api.v102.ComunicaAppaltoResourceV1Api")
public class ComunicaAppaltoResourceV1Api {
    private ApiClient apiClient;

    public ComunicaAppaltoResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public ComunicaAppaltoResourceV1Api(ApiClient apiClient) {
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
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CancellaAppaltoRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per l&#x27;eliminazione di un Appalto in lavorazione. SINCRONO
     * Eliminazione logica di un Appalto e dei lotti in lavorazione. SINCRONO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CancellaAppaltoRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoCancellaAppaltoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/cancella-appalto").build().toUriString();
        
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
     * API Rest per la conferma dei dati di Appalto propedeutica alla generazione dei CIG. ASINCRONO - ASIMMETRICO
     * Il servizio permette la conferma dei dati dell&#x27;Appalto inviati, avvia la fase di validazione e assegnazione dei CIG ed inizializza il Fascicolo Virtuale dell&#x27;Appalto. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso per invocare il successivo servizio pubblica-avviso. ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (required)
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
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPostWithHttpInfo(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la conferma dei dati di Appalto propedeutica alla generazione dei CIG. ASINCRONO - ASIMMETRICO
     * Il servizio permette la conferma dei dati dell&#x27;Appalto inviati, avvia la fase di validazione e assegnazione dei CIG ed inizializza il Fascicolo Virtuale dell&#x27;Appalto. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso per invocare il successivo servizio pubblica-avviso. ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (required)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPostWithHttpInfo(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAppalto' is set
        if (idAppalto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAppalto' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoConfermaAppaltoIdAppaltoPost");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("idAppalto", idAppalto);
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/conferma-appalto/{idAppalto}").buildAndExpand(uriVariables).toUriString();
        
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
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero del dettaglio dell&#x27;Appalto. SINCRONO
     * Servizio per la consultazione delle informazioni di dettaglio di un Appalto e dei relativi Lotti. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC, alternativo all&#x27;idAppalto (optional)
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC alternativo al CIG. (A UUID specified by FC4122). (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String idAppalto, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGetWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, idAppalto, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero del dettaglio dell&#x27;Appalto. SINCRONO
     * Servizio per la consultazione delle informazioni di dettaglio di un Appalto e dei relativi Lotti. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC, alternativo all&#x27;idAppalto (optional)
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC alternativo al CIG. (A UUID specified by FC4122). (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGetWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String idAppalto, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/consulta-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
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
     * API Rest per la creazione di un Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per l&#x27;inserimento della prima istanza in lavorazione di un Appalto nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
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
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, ComunicaAppaltoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la creazione di un Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per l&#x27;inserimento della prima istanza in lavorazione di un Appalto nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, ComunicaAppaltoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoCreaAppaltoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/crea-appalto").build().toUriString();
        
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
     * API Rest per la modifica dell&#x27;Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per sostituire la precedente istanza dell&#x27;Appalto in lavorazione. L&#x27;Appalto è nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
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
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, ComunicaAppaltoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la modifica dell&#x27;Appalto. SINCRONO
     * Il servizio permette di inviare la scheda per sostituire la precedente istanza dell&#x27;Appalto in lavorazione. L&#x27;Appalto è nel formato ESPD-Request, per tutte le tipologie anche affidamento diretto per le pubblicazioni nazionali, nel formato eForm per le pubblicazioni europee in TED, più un formato definito da ANAC con le informazioni aggiuntive. SINCRONO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, ComunicaAppaltoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoModificaAppaltoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/modifica-appalto").build().toUriString();
        
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
     * API Rest per il recupero dei CIG generati da ANAC e assegnati ai lotti dell&#x27;Appalto. SINCRONO
     * Per un Appalto il servizio restituiti le coppie Lotto - CIG. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, Integer page, Integer perPage, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGetWithHttpInfo(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, page, perPage, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero dei CIG generati da ANAC e assegnati ai lotti dell&#x27;Appalto. SINCRONO
     * Per un Appalto il servizio restituiti le coppie Lotto - CIG. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGetWithHttpInfo(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, Integer page, Integer perPage, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAppalto' is set
        if (idAppalto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAppalto' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/recupera-cig").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));
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
     * API Rest per la ricerca delle Gare-Lotto dell&#x27;Appalto. SINCRONO
     * Viene recuperata la lista con i dati di sintesi dell&#x27;Appalto e dei rispettivi lotti. Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Appalto-Lotto rilasciato da ANAC (optional)
     * @param codiceAppalto Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante (optional)
     * @param dataCreazioneA Data di crezione dell&#x27;Appalto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param lotIdentifier Id univoco del lotto generato dalla stazione appaltante - corrisponde al campo bt-137 - Purpose Lot Identifier del TED - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione del codiceAppalto o cig. (optional)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param stato Codice Stato dell&#x27;Appalto - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione di almeno un altro parametro - fare riferimento ai valori contenuti nel file \&quot;statoAppalto.json\&quot; (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String codiceAppalto, String dataCreazioneA, String dataCreazioneDa, String lotIdentifier, Integer page, Integer perPage, String stato, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGetWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, codiceAppalto, dataCreazioneA, dataCreazioneDa, lotIdentifier, page, perPage, stato, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la ricerca delle Gare-Lotto dell&#x27;Appalto. SINCRONO
     * Viene recuperata la lista con i dati di sintesi dell&#x27;Appalto e dei rispettivi lotti. Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param cig Identificativo univoco Appalto-Lotto rilasciato da ANAC (optional)
     * @param codiceAppalto Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante (optional)
     * @param dataCreazioneA Data di crezione dell&#x27;Appalto - intervallo precedente (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param lotIdentifier Id univoco del lotto generato dalla stazione appaltante - corrisponde al campo bt-137 - Purpose Lot Identifier del TED - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione del codiceAppalto o cig. (optional)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param stato Codice Stato dell&#x27;Appalto - Se valorizzato, il parametro richiedere obbligatoriamente la valorizzazione di almeno un altro parametro - fare riferimento ai valori contenuti nel file \&quot;statoAppalto.json\&quot; (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGetWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String cig, String codiceAppalto, String dataCreazioneA, String dataCreazioneDa, String lotIdentifier, Integer page, Integer perPage, String stato, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/ricerca-appalto").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceAppalto", codiceAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneA", dataCreazioneA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneDa", dataCreazioneDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lotIdentifier", lotIdentifier));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stato", stato));
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
     * API Rest per la validazione dell&#x27;Appalto. ASINCRONO - ASIMMETRICO
     * L&#x27;ultima istanza di appalto inviata viene validata. In base alla tipologia dell&#x27;appalto viene eseguita una validazione sintattica dei dati di input dell&#x27;appalto (eForm, ESPDRequest, anacForm). ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (required)
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
    public InlineResponse400 npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPostWithHttpInfo(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la validazione dell&#x27;Appalto. ASINCRONO - ASIMMETRICO
     * L&#x27;ultima istanza di appalto inviata viene validata. In base alla tipologia dell&#x27;appalto viene eseguita una validazione sintattica dei dati di input dell&#x27;appalto (eForm, ESPDRequest, anacForm). ASINCRONO - ASIMMETRICO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAppalto Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (required)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPostWithHttpInfo(String idAppalto, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAppalto' is set
        if (idAppalto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAppalto' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedComunicaAppaltoVerificaAppaltoIdAppaltoPost");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("idAppalto", idAppalto);
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/comunica-appalto/verifica-appalto/{idAppalto}").buildAndExpand(uriVariables).toUriString();
        
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
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<InlineResponse400> returnType = new ParameterizedTypeReference<InlineResponse400>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
