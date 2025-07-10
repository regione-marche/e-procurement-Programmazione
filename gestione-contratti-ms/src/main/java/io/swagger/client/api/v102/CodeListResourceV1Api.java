package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

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
@Component("io.swagger.client.api.v102.CodeListResourceV1Api")
public class CodeListResourceV1Api {
    private ApiClient apiClient;

    public CodeListResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public CodeListResourceV1Api(ApiClient apiClient) {
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
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
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
    public InlineResponse400 npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, Integer page, Integer perPage, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGetWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, page, perPage, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero dell&#x27;elenco delle tipologiche disponibili. SINCRONO
     * Il servizio restituiti un&#x27;elenco delle tipologiche disponibili. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGetWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, Integer page, Integer perPage, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedCodeListRecuperaElencoTipologicheGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/code-list/recupera-elenco-tipologiche").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
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
     * API Rest per il recupero di una specifica tipologica. SINCRONO
     * Il servizio restituiti l&#x27;elenco di una singola tipologica. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idTipologica Identificativo della tipologica (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param dataFine Data di fine (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataInizio Data di inizio (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
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
    public InlineResponse400 npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet(String idTipologica, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String dataFine, String dataInizio, Integer page, Integer perPage, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGetWithHttpInfo(idTipologica, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, dataFine, dataInizio, page, perPage, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero di una specifica tipologica. SINCRONO
     * Il servizio restituiti l&#x27;elenco di una singola tipologica. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idTipologica Identificativo della tipologica (required)
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param dataFine Data di fine (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param dataInizio Data di inizio (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGetWithHttpInfo(String idTipologica, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String dataFine, String dataInizio, Integer page, Integer perPage, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idTipologica' is set
        if (idTipologica == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idTipologica' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedCodeListRecuperaTipologicaGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/code-list/recupera-tipologica").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataFine", dataFine));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataInizio", dataInizio));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idTipologica", idTipologica));
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
     * API Rest per il recupero di un singolo item di una tipologica. SINCRONO
     * Il servizio restituiti il valore di un singolo item che si vuole recuperare dalla tipologica. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param codice Codice del valore che si vuole recuperare dalla tipologica (required)
     * @param idTipologica Identificativo della tipologica (required)
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
    public InlineResponse400 npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet(String codice, String idTipologica, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGetWithHttpInfo(codice, idTipologica, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero di un singolo item di una tipologica. SINCRONO
     * Il servizio restituiti il valore di un singolo item che si vuole recuperare dalla tipologica. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param codice Codice del valore che si vuole recuperare dalla tipologica (required)
     * @param idTipologica Identificativo della tipologica (required)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGetWithHttpInfo(String codice, String idTipologica, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'codice' is set
        if (codice == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'codice' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'idTipologica' is set
        if (idTipologica == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idTipologica' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/code-list/recupera-valore-tipologica").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codice", codice));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idTipologica", idTipologica));
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
