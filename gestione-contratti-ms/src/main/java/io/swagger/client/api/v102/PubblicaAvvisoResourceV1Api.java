package io.swagger.client.api.v102;

import it.appaltiecontratti.pcp.v102.ApiClient;

import it.appaltiecontratti.pcp.v102.npaGateway.AvvisoRequestDTOMapStringObject;
import it.appaltiecontratti.pcp.v102.npaGateway.CancellaAvvisoRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.CustomUserLoaEnum;
import it.appaltiecontratti.pcp.v102.npaGateway.EModalitaInvio;
import it.appaltiecontratti.pcp.v102.npaGateway.InlineResponse400;
import it.appaltiecontratti.pcp.v102.npaGateway.PubblicaAvvisoRequestDTO;
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
@Component("io.swagger.client.api.v102.PubblicaAvvisoResourceV1Api")
public class PubblicaAvvisoResourceV1Api {
    private ApiClient apiClient;

    public PubblicaAvvisoResourceV1Api() {
        this(new ApiClient());
    }

    @Autowired
    public PubblicaAvvisoResourceV1Api(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * API Rest per la richiesta di annullamento di pubblicazione di un avviso non ancora pubblicato. ASINCRONO - ASIMMETRICO
     * Il servizio permette di sospensione una una richiesta di pubblicazione, sia nazionale sia europea, di un avviso non ancora pubblicato. ASINCRONO ASIMMETRICO
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CancellaAvvisoRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la richiesta di annullamento di pubblicazione di un avviso non ancora pubblicato. ASINCRONO - ASIMMETRICO
     * Il servizio permette di sospensione una una richiesta di pubblicazione, sia nazionale sia europea, di un avviso non ancora pubblicato. ASINCRONO ASIMMETRICO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, CancellaAvvisoRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoCancellaAvvisoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/cancella-avviso").build().toUriString();
        
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
     * API Rest per il recupero del dettaglio di un Avviso. SINCRONO
     * Viene resituita la definizione di un Avviso con tutte le sue informazioni di dettaglio.
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAvviso Identificativo dell&#x27;Avviso generato da ANAC. (required)
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet(String idAvviso, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGetWithHttpInfo(idAvviso, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero del dettaglio di un Avviso. SINCRONO
     * Viene resituita la definizione di un Avviso con tutte le sue informazioni di dettaglio.
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAvviso Identificativo dell&#x27;Avviso generato da ANAC. (required)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGetWithHttpInfo(String idAvviso, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAvviso' is set
        if (idAvviso == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAvviso' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaAvvisoIdAvvisoGet");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("idAvviso", idAvviso);
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/consulta-avviso/{idAvviso}").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per il recupero del PDF di un Avviso. SINCRONO
     * Viene resituito il file in formato PDF relativo all&#x27; Avviso.
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAvviso Identificativo dell&#x27;Avviso generato da ANAC. (required)
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet(String idAvviso, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGetWithHttpInfo(idAvviso, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero del PDF di un Avviso. SINCRONO
     * Viene resituito il file in formato PDF relativo all&#x27; Avviso.
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAvviso Identificativo dell&#x27;Avviso generato da ANAC. (required)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGetWithHttpInfo(String idAvviso, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAvviso' is set
        if (idAvviso == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAvviso' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoConsultaRenderingAvvisoIdAvvisoGet");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("idAvviso", idAvviso);
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/consulta-rendering-avviso/{idAvviso}").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * API Rest per la creazione di un avviso che sostituisce il precedente non ancora pubblicato sia a livello nazionale e sia europeo. ASINCRONO - ASIMMETRICO
     * API Rest per la richiesta di pubblicazione di un&#x27;avviso sia a livello nazionale sia europeo. L&#x27;avviso sostituisce il precedente non ancora pubblicato. Il servizio gestisce la pubblicazione europea mediante l&#x27;acquisizione di dati nel formato definito da TED (eForm) e, la pubblicazione nazionale mediante l&#x27;acquisizione di dati definiti dall&#x27;ESPD Request più dati aggiuntivi richiesti da ANAC. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso di modifica assegnato. ASINCRONO ASIMMETRICO
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, AvvisoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la creazione di un avviso che sostituisce il precedente non ancora pubblicato sia a livello nazionale e sia europeo. ASINCRONO - ASIMMETRICO
     * API Rest per la richiesta di pubblicazione di un&#x27;avviso sia a livello nazionale sia europeo. L&#x27;avviso sostituisce il precedente non ancora pubblicato. Il servizio gestisce la pubblicazione europea mediante l&#x27;acquisizione di dati nel formato definito da TED (eForm) e, la pubblicazione nazionale mediante l&#x27;acquisizione di dati definiti dall&#x27;ESPD Request più dati aggiuntivi richiesti da ANAC. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso di modifica assegnato. ASINCRONO ASIMMETRICO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, AvvisoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoModificaAvvisoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/modifica-avviso").build().toUriString();
        
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
     * API Rest per la richiesta di pubblicazione di un&#x27;avviso sia a livello nazionale sia europeo. ASINCRONO - ASIMMETRICO
     * Il servizio avvia la richiesta di pubblicazione di un Avviso, a livello nazionale e/o europeo. A seguito della chiamata a questo servizio, l&#x27;avviso transita in stato \&quot;IN ATTESA PUBBLICAZIONE\&quot;. ASINCRONO - ASIMMETRICO
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, PubblicaAvvisoRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la richiesta di pubblicazione di un&#x27;avviso sia a livello nazionale sia europeo. ASINCRONO - ASIMMETRICO
     * Il servizio avvia la richiesta di pubblicazione di un Avviso, a livello nazionale e/o europeo. A seguito della chiamata a questo servizio, l&#x27;avviso transita in stato \&quot;IN ATTESA PUBBLICAZIONE\&quot;. ASINCRONO - ASIMMETRICO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, PubblicaAvvisoRequestDTO body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoPubblicaAvvisoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/pubblica-avviso").build().toUriString();
        
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
     * API Rest per la creazione di un avviso che rettifica il precedente pubblicato sia a livello nazionale e sia europeo. ASINCRONO - ASIMMETRICO
     * API Rest per la richiesta di pubblicazione di un&#x27;avviso di rettifica sia a livello nazionale sia europeo. L&#x27;avviso rettifica il precedente già pubblicato. Il servizio gestisce la pubblicazione europea mediante l&#x27;acquisizione di dati nel formato definito da TED (eForm) e, la pubblicazione nazionale mediante l&#x27;acquisizione di dati definiti dall&#x27;ESPD Request più dati aggiuntivi richiesti da ANAC. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso di rettifica assegnato. ASINCRONO ASIMMETRICO
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, AvvisoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPostWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la creazione di un avviso che rettifica il precedente pubblicato sia a livello nazionale e sia europeo. ASINCRONO - ASIMMETRICO
     * API Rest per la richiesta di pubblicazione di un&#x27;avviso di rettifica sia a livello nazionale sia europeo. L&#x27;avviso rettifica il precedente già pubblicato. Il servizio gestisce la pubblicazione europea mediante l&#x27;acquisizione di dati nel formato definito da TED (eForm) e, la pubblicazione nazionale mediante l&#x27;acquisizione di dati definiti dall&#x27;ESPD Request più dati aggiuntivi richiesti da ANAC. Con la successiva chiamata al servizio esito-operazione è possibile recuperare l&#x27;idAvviso di rettifica assegnato. ASINCRONO ASIMMETRICO
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPostWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, AvvisoRequestDTOMapStringObject body, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRettificaAvvisoPost");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/rettifica-avviso").build().toUriString();
        
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
     * API Rest per la ricerca degli avvisi dell&#x27;appalto. SINCRONO
     * Viene recuperata la lista con i dati di sintesi degli Avvisi. Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param azioneAvviso Codice Tipo di azione dell&#x27;avviso - fare riferimento ai valori contenuti nel file tipoAzioneAvviso.json (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Obbligatorio se idAppalto e codiceAppalto non sono stati valorizzati. (optional)
     * @param codiceAppalto Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante. Obbligatorio se idAppalto e cig non sono stati valorizzati. (optional)
     * @param dataCreazioneA Data di crezione della Appalto - intervallo precedente (A date-time specified by RF333) (optional)
     * @param dataCreazioneAvvisoA Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (optional)
     * @param dataCreazioneAvvisoDa Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param idAppalto Identificativo univoco dell&#x27; Appalto definito da ANAC. (A UUID specified by FC4122). Obbligatorio se codiceAppalto e cig non sono stati valorizzati. (optional)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param stato Codice Stato dell&#x27;Appalto - fare riferimento ai valori contenuti nel file statoAppalto.json (optional)
     * @param statoAvviso Codice Stato in cui si trova l&#x27; Avviso - fare riferimento ai valori contenuti nel file statoAvviso.json (optional)
     * @param tipoAvviso Codice Tipo di avviso - fare riferimento ai valori contenuti nel file tipoAvviso.json (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return InlineResponse400
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String azioneAvviso, String cig, String codiceAppalto, String dataCreazioneA, String dataCreazioneAvvisoA, String dataCreazioneAvvisoDa, String dataCreazioneDa, String idAppalto, Integer page, Integer perPage, String stato, String statoAvviso, String tipoAvviso, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGetWithHttpInfo(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, azioneAvviso, cig, codiceAppalto, dataCreazioneA, dataCreazioneAvvisoA, dataCreazioneAvvisoDa, dataCreazioneDa, idAppalto, page, perPage, stato, statoAvviso, tipoAvviso, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per la ricerca degli avvisi dell&#x27;appalto. SINCRONO
     * Viene recuperata la lista con i dati di sintesi degli Avvisi. Le informazioni restituite dal servizio saranno filtrate, oltre in base ai parametri di ricerca, anche in base agli appalti di competenza dell&#x27; RP e della SA per la quale sta operando. Almeno un filtro di ricerca deve essere valorizzato. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param xSAcodiceAUSA Codice AUSA della stazione appaltante (required)
     * @param xTenantId Tenant ID per recuperare le credenziali di autenticazione ai servizi PCP (required)
     * @param xUserCodiceFiscale Codice fiscale utente che eseguira&#x27; l&#x27;operazione (Responsabile del progetto o Delegato del responsabile del progetto) (required)
     * @param xUserIdpType Tipologia di IDP di autenticazione dell&#x27;utente (SPID, CIE, CNS, EIDAS o CUSTOM) (required)
     * @param xUserLoa Livello di autenticazione dell&#x27;utente (3 o 4) (required)
     * @param xUserRole Ruolo utente (RP &#x3D; Responsabile del progetto, DRP &#x3D; Delegato del responsabile del progetto) (required)
     * @param azioneAvviso Codice Tipo di azione dell&#x27;avviso - fare riferimento ai valori contenuti nel file tipoAzioneAvviso.json (optional)
     * @param cig Identificativo univoco Gara-Lotto rilasciato da ANAC. Obbligatorio se idAppalto e codiceAppalto non sono stati valorizzati. (optional)
     * @param codiceAppalto Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante. Obbligatorio se idAppalto e cig non sono stati valorizzati. (optional)
     * @param dataCreazioneA Data di crezione della Appalto - intervallo precedente (A date-time specified by RF333) (optional)
     * @param dataCreazioneAvvisoA Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (optional)
     * @param dataCreazioneAvvisoDa Identificativo univoco dell&#x27;Appalto definito da ANAC. (A UUID specified by FC4122). (optional)
     * @param dataCreazioneDa Data di crezione dell&#x27;Appalto - intervallo successivo (A date-time specified by ISO 8601 as profiled by RFC 3339) (optional)
     * @param idAppalto Identificativo univoco dell&#x27; Appalto definito da ANAC. (A UUID specified by FC4122). Obbligatorio se codiceAppalto e cig non sono stati valorizzati. (optional)
     * @param page numero della pagina da visualizzare (optional)
     * @param perPage numero di elementi per pagina (optional)
     * @param stato Codice Stato dell&#x27;Appalto - fare riferimento ai valori contenuti nel file statoAppalto.json (optional)
     * @param statoAvviso Codice Stato in cui si trova l&#x27; Avviso - fare riferimento ai valori contenuti nel file statoAvviso.json (optional)
     * @param tipoAvviso Codice Tipo di avviso - fare riferimento ai valori contenuti nel file tipoAvviso.json (optional)
     * @param xSACodiceFiscale codice Fiscale della stazione appaltante. Puo&#x27; essere nullo in caso di soggetti non dotati di personalita&#x27; giuridica (optional)
     * @param xModalitaInvio Modalità invio (Verifica o Invia). (optional)
     * @param xRegCodiceComponente codice identificativo del componente client (optional)
     * @param xRegCodicePiattaforma codice identificativo della piattaforma (optional)
     * @param xTipologicaScheda Codice tipologica scheda (es. P2_16) (optional)
     * @param xVersioneTracciato Versione tracciato Anac PCP (es. 1.0) (optional)
     * @return ResponseEntity&lt;InlineResponse400&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGetWithHttpInfo(String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String azioneAvviso, String cig, String codiceAppalto, String dataCreazioneA, String dataCreazioneAvvisoA, String dataCreazioneAvvisoDa, String dataCreazioneDa, String idAppalto, Integer page, Integer perPage, String stato, String statoAvviso, String tipoAvviso, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoRicercaAvvisoGet");
        }
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/ricerca-avviso").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "azioneAvviso", azioneAvviso));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cig", cig));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "codiceAppalto", codiceAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneA", dataCreazioneA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneAvvisoA", dataCreazioneAvvisoA));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneAvvisoDa", dataCreazioneAvvisoDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataCreazioneDa", dataCreazioneDa));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "idAppalto", idAppalto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "perPage", perPage));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stato", stato));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "statoAvviso", statoAvviso));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "tipoAvviso", tipoAvviso));
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
     * API Rest per il recupero dello stato dell&#x27;Avviso. SINCRONO
     * Il compito dispositivo di aggiornare lo stato dell&#x27;avviso proveniente dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dello stato da parte del servizio insiste su uno stato avviso PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAvviso Identificativo dell&#x27;Avviso generato da ANAC. (required)
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
    public InlineResponse400 npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet(String idAvviso, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        return npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGetWithHttpInfo(idAvviso, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato).getBody();
    }

    /**
     * API Rest per il recupero dello stato dell&#x27;Avviso. SINCRONO
     * Il compito dispositivo di aggiornare lo stato dell&#x27;avviso proveniente dai sistemi esterni, TED e/o PPL-ANAC, sarà in carico ad un task schedulato PCP. Pertanto il recupero dello stato da parte del servizio insiste su uno stato avviso PCP che restituisce anche la data in cui il task schedulato ha effettuato il controllo sui sistemi esterni. SINCRONO
     * <p><b>400</b> - Bad Request
     * <p><b>500</b> - Internal Server Error
     * <p><b>200</b> - Success
     * @param idAvviso Identificativo dell&#x27;Avviso generato da ANAC. (required)
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
    public ResponseEntity<InlineResponse400> npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGetWithHttpInfo(String idAvviso, String xSAcodiceAUSA, String xTenantId, String xUserCodiceFiscale, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xUserRole, String xSACodiceFiscale, EModalitaInvio xModalitaInvio, String xRegCodiceComponente, String xRegCodicePiattaforma, String xTipologicaScheda, String xVersioneTracciato) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idAvviso' is set
        if (idAvviso == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idAvviso' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xSAcodiceAUSA' is set
        if (xSAcodiceAUSA == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xSAcodiceAUSA' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xTenantId' is set
        if (xTenantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xTenantId' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserCodiceFiscale' is set
        if (xUserCodiceFiscale == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserCodiceFiscale' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserIdpType' is set
        if (xUserIdpType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserIdpType' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserLoa' is set
        if (xUserLoa == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserLoa' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // verify the required parameter 'xUserRole' is set
        if (xUserRole == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'xUserRole' when calling npaGatewayMsV1ProtectedPubblicaAvvisoStatoAvvisoIdAvvisoGet");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("idAvviso", idAvviso);
        String path = UriComponentsBuilder.fromPath("/npa-gateway-ms/v1/protected/pubblica-avviso/stato-avviso/{idAvviso}").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
