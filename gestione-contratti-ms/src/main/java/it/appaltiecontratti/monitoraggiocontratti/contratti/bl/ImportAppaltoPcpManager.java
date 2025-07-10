package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.client.api.v1021.*;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102.SchedePcpManagerV102;
import it.appaltiecontratti.monitoraggiocontratti.contratti.client.api.v1021.AuthenticationApi;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.MessageInForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.MigrazioneGaraForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.FasiPcp;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ConsultaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaGaraBean;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseAppaltoPcp;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseImportaGara;
import it.appaltiecontratti.pcp.v1021.ApiClient;
import it.appaltiecontratti.pcp.v1021.codeList.TipologicaItemResponse;
import it.appaltiecontratti.pcp.v1021.npaGateway.*;
import it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum;
import it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Pattern;

@Component(value = "ImportAppaltoPcpManager")
public class ImportAppaltoPcpManager extends AbstractManager {

    /** Logger di classe. */
    private static final Logger logger = LogManager.getLogger(ImportAppaltoPcpManager.class);

    @SuppressWarnings("java:S2387")
    @Autowired
    private ContrattiMapper contrattiMapper;

    @SuppressWarnings("java:S2387")
    @Autowired
    protected WGenChiaviManager wgcManager;

    @Autowired
    protected SqlMapper sqlMapper;

    @Autowired
    private FasiManager fasiManager;

    @Autowired
    private ContrattiManager contrattiManager;

    @Autowired
    private ComunicaPostPubblicazioneResourceV1Api comunicaPostPubblicazioneResourceV1Api;

    @Autowired
    private CodeListResourceV1Api codeListResourceV1Api;

    @Autowired
    private ComunicaAppaltoResourceV1Api comunicaAppaltoResourceV1Api;

    @Autowired
    private GestioneUtentiResourceV1Api gestioneUtentiResourceV1Api;

    @Autowired
    private ServiziComuniResourceV1Api serviziComuniResourceV1Api;

    @Autowired
    private AusaResourceV1Api ausaResourceV1Api;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ElaborateSchedeGeneralManager elaborateSchedePcp;

    @Autowired
    private SchedePcpManagerV102 schedePcpManagerV102;

    @Autowired
    private UserManager userManager;

    @Autowired
    private DelegheManager delegheManager;

    private AuthenticationApi authenticationApi;

    private String anacGatewayPath;

    private ApiClient api;

    private ApiClient apiKeyClock;

    private ObjectMapper objectMapper;

    private static final String VERSIONE_TRACCIATO_PCP = "1.0.2";

    private static final String LOG_EVENTI_SCHEDA_PCP_COD_EVENTO = "SCHEDA_PCP";
    private static final String LOG_EVENTI_APPALTO_PCP_COD_EVENTO = "APPALTO_PCP";

    private static final String CREA_SCHEDA = "Crea scheda";
    private static final String CONFERMA_SCHEDA = "Conferma scheda";

    private static final String W_CONFIG_CODAPP = "W9";
    private static final String W_CONFIG_NPA_URL = "ws.inviopcp.url";
    private static final String W_CONFIG_TENANT_ID = "ws.inviopcp.tenantid";
    private static final String W_CONFIG_CODICE_PIATTAFORMA = "ws.inviopcp.codicePiattaforma";
    private static final String W_CONFIG_CODICE_COMPONENTE = "ws.inviopcp.codiceComponente";

    private static final String W_CONFIG_KEYCLOACK_URL = "keycloack.url";
    private static final String W_CONFIG_KEYCLOACK_CLIENT_ID = "keycloack.clientId";
    private static final String W_CONFIG_KEYCLOACK_CLIENT_SECRET = "keycloack.clientSecret";
    private static final String W_CONFIG_KEYCLOACK_GRANT_TYPE = "keycloack.grantType";

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    private String xVersioneTracciato = null;
    private String xTenantId = null;

    private String keyClockClientId = null;
    private String keyClockClientSecret = null;
    private String keyClockGrantType = null;

    private String xRegCodiceComponente = null;
    private String xRegCodicePiattaforma = null;

    private static final Map<String, String> flagRitardoMap;

    private static final Map<String, String> motiviModificaMap;
    static {
        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put("P","9");
        aMap.put("A","10");
        aMap.put("R","11");
        flagRitardoMap = Collections.unmodifiableMap(aMap);

        Map<String, String> mMap = new HashMap<String, String>();
        mMap.put("31","add-wss");
        mMap.put("32","mod-cir");
        mMap.put("33","mod-minv");
        mMap.put("34","mod-nons");
        mMap.put("35","mod-repl");
        mMap.put("36","mod-rev");
        mMap.put("37","other");
        motiviModificaMap = Collections.unmodifiableMap(mMap);
    }


    @PostConstruct
    private void startUp() {

        xVersioneTracciato = VERSIONE_TRACCIATO_PCP;
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void initApi() throws Exception {
        try {
            logger.info("chiamata ai servizi {}",xVersioneTracciato);
            anacGatewayPath = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_NPA_URL);
            if (!anacGatewayPath.matches(".*/npa-gateway-ms(/|-[^/]+/)?$")) {
                anacGatewayPath = anacGatewayPath + (anacGatewayPath.endsWith("/") ? "npa-gateway-ms/" : "/npa-gateway-ms/");
            }
            logger.info("url anac {}",anacGatewayPath);
            xRegCodiceComponente = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_CODICE_COMPONENTE);
            xRegCodicePiattaforma = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_CODICE_PIATTAFORMA);
            xTenantId = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_TENANT_ID);

            comunicaPostPubblicazioneResourceV1Api = new ComunicaPostPubblicazioneResourceV1Api();
            codeListResourceV1Api = new CodeListResourceV1Api();
            comunicaAppaltoResourceV1Api = new ComunicaAppaltoResourceV1Api();
            gestioneUtentiResourceV1Api = new GestioneUtentiResourceV1Api();
            serviziComuniResourceV1Api = new ServiziComuniResourceV1Api();
            ausaResourceV1Api = new AusaResourceV1Api();


            api = new ApiClient(restTemplate);
            api.setBasePath(anacGatewayPath);


            String keyCloackUrl = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_KEYCLOACK_URL);
            if(StringUtils.isNotBlank(keyCloackUrl)) {

                keyClockClientId = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_KEYCLOACK_CLIENT_ID);
                keyClockClientSecret = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_KEYCLOACK_CLIENT_SECRET);
                keyClockGrantType = this.contrattiMapper.getConfigurazione(W_CONFIG_CODAPP, W_CONFIG_KEYCLOACK_GRANT_TYPE);

                apiKeyClock = new ApiClient(restTemplate);
                apiKeyClock.setBasePath(keyCloackUrl);

                authenticationApi = new AuthenticationApi();
                authenticationApi.setApiClient(apiKeyClock);
                KeycloakTokenResponse res= authenticationApi.getToken(keyClockClientId, keyClockClientSecret, keyClockGrantType);
                String token = res.getAccessToken();

                api.addDefaultHeader("Authorization", "Bearer " + token);
            }

            comunicaPostPubblicazioneResourceV1Api.setApiClient(api);
            codeListResourceV1Api.setApiClient(api);
            comunicaAppaltoResourceV1Api.setApiClient(api);
            gestioneUtentiResourceV1Api.setApiClient(api);
            serviziComuniResourceV1Api.setApiClient(api);
            ausaResourceV1Api.setApiClient(api);

        }catch (Exception e) {
            logger.error("errore metodo initApi",e);
            throw e;

        }
    }



    private String generateLogEventiError(ResponsePubblicaFase risultato) {
        String logEventiError = null;
        if(risultato != null) {
            if(risultato.getAnacErrors() != null && risultato.getAnacErrors().size() > 0) {
                logEventiError = "Errori Anac: " + String.join(" - ", risultato.getAnacErrors());
            } else if(risultato.getInternalErrors() != null && risultato.getInternalErrors().size() > 0) {
                logEventiError = "Errori npa gateway: " + String.join(" - ", risultato.getInternalErrors());
            } else if(risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
                logEventiError = "Errori di validazione: " + String.join(" - ", risultato.getValidationErrors());
            }
        }
        return logEventiError;
    }

    private String generateLogEventiError(GenericResponse<?> risultato) {
        String logEventiError = null;
        if(risultato != null) {
            if(risultato.getAnacErrors() != null && risultato.getAnacErrors().size() > 0) {
                logEventiError = "Errori Anac: " + String.join(" - ", risultato.getAnacErrors());
            } else if(risultato.getInternalErrors() != null && risultato.getInternalErrors().size() > 0) {
                logEventiError = "Errori npa gateway: " + String.join(" - ", risultato.getInternalErrors());
            } else if(risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
                logEventiError = "Errori di validazione: " + String.join(" - ", risultato.getValidationErrors());
            }
        }
        return logEventiError;
    }



    private OffsetDateTime dateToOffsetDateTime(Date date) {
        // Get the timezone of the original Date object
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Rome"); // Replace with the actual timezone of the Date

        // Get the offset in seconds
        int offsetSeconds = timeZone.getRawOffset() / 1000;

        // Convert Date to Instant
        long milliseconds = date.getTime();
        Instant instant = Instant.ofEpochMilli(milliseconds);

        // Create OffsetDateTime from Instant and ZoneOffset
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofTotalSeconds(offsetSeconds));
        return offsetDateTime;
    }


    public void cancellazioneFlusso(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                                    final Long numProgressivoFase, final String motivoCancellazione) {

        try {

            this.contrattiMapper.insertFlussoEliminato(codGara, codLotto, faseEsecuzione, numProgressivoFase);
            this.contrattiMapper.updateDataMotivoFlussoEliminato(codGara, codLotto, faseEsecuzione, numProgressivoFase,
                    new Date(), motivoCancellazione);
            this.contrattiMapper.deleteFlusso(codGara, codLotto, faseEsecuzione, numProgressivoFase);

        } catch (Exception e) {
            logger.error("Si sono verificati degli errori durante la cancellazione logica dei flussi", e);
        }
    }


    public String getValoreTipologica(String codice, String idTipologica, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) {
        String messaggio = "";
        try {
            InlineResponse400 res = codeListResourceV1Api.v1ProtectedCodeListRecuperaValoreTipologicaGet(codice, idTipologica, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, null, null, null, null, xVersioneTracciato);
            if(res != null && res.getData() != null) {
                TipologicaItemResponse response = objectMapper.convertValue(res.getData(), TipologicaItemResponse.class);
                if(response.getTipologica() != null) {
                    messaggio = response.getTipologica().getCodice() + " - " + response.getTipologica().getDescrizione().getIt();
                }

            }
        }catch (Exception e) {
            logger.error("errore durante la conversione del valore della tipologica, metodo: getValoreTipologica ", e);
        }
        return messaggio;

    }


    private void allineaPubblicazioneFase(Long codGara, Long codLotto, Long numFase, Long num, Long tipoInvio,
                                          String codiceSA, Long syscon, String motivazione, String schedaJson, String govwayMessageId, String govwayTransactionId) throws Exception {

        FlussoEntry flusso = new FlussoEntry();
        try {
            String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(codiceSA);
            Long idFlusso = wgcManager.getNextId("W9FLUSSI");
            flusso.setId(idFlusso);
            flusso.setArea(1L);
            flusso.setKey01(codGara);
            flusso.setKey02(codLotto);
            flusso.setKey03(numFase);
            flusso.setKey04(num);
            flusso.setTipoInvio(tipoInvio);
            flusso.setDataInvio(new Date());
            flusso.setCodiceFiscaleSA(cfStazioneAppaltante);
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            if (motivazione != null) {
                flusso.setNote(motivazione);
            }
            flusso.setJson(schedaJson);
            flusso.setGovwayMessageId(govwayMessageId);
            flusso.setGovwayTransactionId(govwayTransactionId);
            this.contrattiMapper.insertFlusso(flusso);

            Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
            this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
            Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
            this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);

        } catch (Exception ex) {
            logger.error("Errore in allinemanto dati pubblicazione schedaPCP: codgara =" + codGara + " codLotto= "
                    + codLotto + " numFase=" + numFase, ex);
            throw ex;
        }
    }

    private void inserimentoFlussoConPcp(Long codGara, Long codLotto, Long numFase, Long num, Long tipoInvio,
                                         String codiceSA, Long syscon, String motivazione, String schedaJson, String govwayMessageId, String govwayTransactionId) throws Exception {

        FlussoEntry flusso = new FlussoEntry();
        try {
            String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(codiceSA);
            Long idFlusso = wgcManager.getNextId("W9FLUSSI");
            flusso.setId(idFlusso);
            flusso.setArea(1L);
            flusso.setKey01(codGara);
            flusso.setKey02(codLotto);
            flusso.setKey03(numFase);
            flusso.setKey04(num);
            flusso.setTipoInvio(tipoInvio);
            flusso.setDataInvio(new Date());
            flusso.setCodiceFiscaleSA(cfStazioneAppaltante);
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            if (motivazione != null) {
                flusso.setNote(motivazione);
            }
            flusso.setJson(schedaJson);
            flusso.setGovwayMessageId(govwayMessageId);
            flusso.setGovwayTransactionId(govwayTransactionId);
            this.contrattiMapper.insertFlusso(flusso);


        } catch (Exception ex) {
            logger.error("Errore metodo inserimentoFlussoComPcp: codgara =" + codGara + " codLotto= "
                    + codLotto + " numFase=" + numFase, ex);
            throw ex;
        }
    }

    private GenericResponse<Map<String,Object>> esitoOperazione(EModalitaInvio xModalitaInvio, String xTipologicaScheda,String idAppalto, String tipoOperazione, String tipoRicerca, String esito, Long syscon, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute esitoOperazione");
            InlineResponse400 res = null;
            try {
                res = serviziComuniResourceV1Api.v1ProtectedServiziComuniEsitoOperazioneGet(tipoOperazione, tipoRicerca, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, esito, idAppalto,null, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "esitoOperazione" + " " + xTipologicaScheda + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo esitoOperazione", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> recuperaCig(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String cig, String idAppalto, Integer page, Integer perPage, Long syscon,
                                                          UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute recuperaCig");
            InlineResponse400 res = null;
            try {
                res = comunicaAppaltoResourceV1Api.v1ProtectedComunicaAppaltoRecuperaCigGet(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, page, perPage, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "recuperaCig" + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo recuperaCig", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> ricercaScheda(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String cig, String idAppalto, Integer page, Integer perPage, Long syscon
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String statoScheda) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute ricercaScheda");
            InlineResponse400 res = null;
            try {
                res = comunicaPostPubblicazioneResourceV1Api.v1ProtectedComunicaPostPubblicazioneRicercaSchedaGet(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, null, null, null, page, perPage, null, statoScheda, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda,  xVersioneTracciato);

                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "ricercaScheda" + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo ricercaScheda", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> consultaScheda(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idScheda, Long syscon
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String idAppalto) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute consultaScheda");
            InlineResponse400 res = null;
            try {
                res = comunicaPostPubblicazioneResourceV1Api.v1ProtectedComunicaPostPubblicazioneConsultaSchedaGet(idScheda, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "consultaScheda" + " idScheda: " + idScheda + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo consultaScheda", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> ricercaSoggetti(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, Long syscon
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute ricercaSoggetti");
            InlineResponse400 res = null;
            try {
                res = gestioneUtentiResourceV1Api.v1ProtectedGestioneUtentiRicercaSoggettiGet(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, null, null, null, null, null, null, null, null, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "ricercaSoggetti" + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo ricercaSoggetti", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> presaCarico(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppaltoCig, Long syscon, PresaCaricoRequestDTO body
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String codProfilo) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute presaCarico");
            InlineResponse400 res = null;

            try {
                res = gestioneUtentiResourceV1Api.v1ProtectedGestioneUtentiPresaCaricoPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "presaCarico" + " - idAppalto/cig: " + idAppaltoCig + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, codProfilo, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, null);

        } catch (Exception e) {
            logger.error("errore nel metodo presaCarico", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> consultaAppalto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, Long syscon
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute consultaAppalto");
            InlineResponse400 res = null;
            try {
                res = comunicaAppaltoResourceV1Api.v1ProtectedComunicaAppaltoConsultaAppaltoGet(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, idAppalto, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> response = objectMapper.convertValue(res.getData(), Map.class);
                //Map<String, Object> body = objectMapper.convertValue(res.getData(), Map.class);

                //SchedaComunicaAppaltoType scheda = objectMapper.convertValue(body.get("scheda"), SchedaComunicaAppaltoType.class);

//				ResponseImportaGaraPcp r = new ResponseImportaGaraPcp();
//				r.setScheda(scheda);
//				r.setIdAppalto((String) body.get("idAppalto"));
                risultato.setResult(response);


            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            } catch(IllegalArgumentException e3){
                logger.error("Errore in fase di conversione json in classe dell'appalto pcp: consultaAppalto",e3);
                throw new AppaltoPcpCastException();
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "consultaAppalto" + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo consultaAppalto", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> statoAppalto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, String tipoOperazione, String tipoRicerca, Long syscon, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute statoAppalto");
            InlineResponse400 res = null;
            try {
                res = serviziComuniResourceV1Api.v1ProtectedServiziComuniStatoAppaltoGet(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, idAppalto, null, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> result = objectMapper.convertValue(res.getData(), Map.class);
                if(result != null) {
                    risultato.setResult(result);
                }

            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "statoAppalto" + " " + xTipologicaScheda + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);

        } catch (Exception e) {
            logger.error("errore nel metodo statoAppalto", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> ricercaAppalto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String cig, Long syscon
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute ricercaAppalto");
            InlineResponse400 res = null;
            try {
                res = comunicaAppaltoResourceV1Api.v1ProtectedComunicaAppaltoRicercaAppaltoGet(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, null, null, null, null, 1, 1, null, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

                Map<String,Object> response = objectMapper.convertValue(res.getData(), Map.class);


                risultato.setResult(response);


            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "ricercaAppalto" + " - cig: " + cig + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, null);

        } catch (Exception e) {
            logger.error("errore nel metodo ricercaAppalto", e);
            throw e;
        }

        return risultato;
    }

    private GenericResponse<Map<String,Object>> getCodAusa(String codiceFiscale, Long syscon
            , UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

        GenericResponse<Map<String,Object>> risultato = new GenericResponse<Map<String,Object>>();
        risultato.setEsito(true);
        try {
            logger.info("execute getCodAusa");
            InlineResponse400 res = null;
            try {
                res = ausaResourceV1Api.v1ProtectedAusaGetByGet(xTenantId, null, codiceFiscale);

                Map<String,Object> response = objectMapper.convertValue(res.getData(), Map.class);


                risultato.setResult(response);


            } catch (HttpClientErrorException e1) {
                logger.warn(e1);
                res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (it.appaltiecontratti.pcp.v1021.npaGateway.ErroriEnum e : res.getAnacErrors()) {
                        if (e != null) {
                            String descrizioneTipologica = getValoreTipologica(e.getCodice(), e.getIdTipologica(), xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                            anacErrors.add(descrizioneTipologica);
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }
            }catch(HttpServerErrorException e2){
                logger.warn(e2);
                res =  objectMapper.readValue(e2.getResponseBodyAsString(), InlineResponse400.class);

                risultato.setErrors(true);
                if(res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
                    risultato.setInternalErrors(res.getInternalErrors());
                }

                if(res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
                    List<String> validErrors = new ArrayList<String>();
                    for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

                    }
                    risultato.setValidationErrors(validErrors);
                }

                if(res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
                    List<String> anacErrors = new ArrayList<String>();
                    for (ErroriEnum e : res.getAnacErrors()) {
                        if(e != null) {
                            anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
                        }
                    }
                    risultato.setAnacErrors(anacErrors);
                }

                if(risultato.getAnacErrors() == null && risultato.getInternalErrors() == null && risultato.getValidationErrors() == null) {
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_UNEXPECTED);
                }
            }

            risultato.setGovwayMessageId(res.getGovwayMessageId());
            risultato.setGovwayTransactionId(res.getGovwayTransactionId());

            String logEventiMessage = null;
            String logEventiError = null;
            String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
            String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";

            String resString = objectMapper.writeValueAsString(res);
            logEventiMessage = "getCodAusa" + " - codiceFiscale: " + codiceFiscale + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
            if (logEventiMessage.length() > 2000) {
                logEventiMessage = logEventiMessage.substring(0, 2000);
            }
            logEventiError = generateLogEventiError(risultato);
            Short livento = 1;
            if(logEventiError != null) {
                livento = 3;
            }
            insertLogEventi(syscon, null, LOG_EVENTI_APPALTO_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, null);

        } catch (Exception e) {
            logger.error("errore nel metodo getCodAusa", e);
            throw e;
        }

        return risultato;
    }


    private static Map<String, String> extractCodiceAusa(JsonNode rootNode) {
        Map<String, String> resultMap = new HashMap<>();
        JsonNode itemsNode = rootNode.path("items");
        if (itemsNode.isArray()) {
            Iterator<JsonNode> itemsIterator = itemsNode.elements();
            while (itemsIterator.hasNext()) {
                JsonNode itemNode = itemsIterator.next();
                JsonNode stazioneAppaltanteNode = itemNode.path("scheda").path("stazioneAppaltante");
                if (!stazioneAppaltanteNode.isMissingNode()) {
                    JsonNode codiceAusaNode = stazioneAppaltanteNode.path("codiceAusa");
                    if (!codiceAusaNode.isMissingNode()) {
                        String codiceAusa = codiceAusaNode.asText();
                        resultMap.put("codiceAusa", codiceAusa);
                    }
                }
            }
        }
        return resultMap;
    }

    public ResponseImportaGara importaGaraPcp(String cfSa, String cig, String idAppalto, Long syscon, String cf, String loa, String idp, String authorization, String loginMode) throws Exception {
        ResponseImportaGara risultato = new ResponseImportaGara();
        risultato.setEsito(true);

        GenericResponse<Map<String,Object>> appalto = new GenericResponse<Map<String,Object>>();

        EModalitaInvio xModalitaInvio = null;
        String xTipologicaScheda = null;

        List<Map<String,Object>> schedeAgg = new ArrayList<Map<String,Object>>();
        Map<String,String> lotIdCigMap = new HashMap<String,String>();
        Map<String, String> idContrattoCig = new HashMap<String,String>();
        List<Map<String,Object>> soggetti = new ArrayList<Map<String,Object>>();
        ConsultaGaraEntry gara = new ConsultaGaraEntry();
        gara.setPresaCarico(false);
        gara.setOperation("OP_IMPORT_ANAC");
        gara.setInserito(false);
        String idContratto = null;

        UserIdpTypeEnum xUserIdpType = null;
        CustomUserLoaEnum xUserLoa = null;
        String xSAcodiceAUSA = null;
        String xUserCodiceFiscale = null;
        String xSACodiceFiscale = null;
        String xUserRole = null;

        try {
            initApi();
            xUserRole = "RP";
            if(loa.equals("3")) {
                xUserLoa = CustomUserLoaEnum._3;
            } else if(loa.equals("4")) {
                xUserLoa = CustomUserLoaEnum._4;
            } else {
                throw new Exception("Utente non autorizzato");
            }

            String codein = this.contrattiMapper.getCodeinByCfSa(cfSa);
            String codiceAusa = this.contrattiMapper.getCodAusaUffint(codein);

            if(StringUtils.isBlank(codiceAusa)) {
                GenericResponse<Map<String,Object>> saAnag = getCodAusa(cfSa, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if(saAnag.getResult() != null) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(objectMapper.writeValueAsString(saAnag.getResult()));

                    Map<String, String> resultMap = extractCodiceAusa(rootNode);

                    if(resultMap != null && StringUtils.isNotBlank(resultMap.get("codiceAusa"))) {
                        codiceAusa = resultMap.get("codiceAusa");
                        this.contrattiMapper.updateCodusaUffint(codiceAusa, codein);
                    }
                } else {
                    gara.setAnacErrors(saAnag.getAnacErrors());
                    gara.setInternalErrors(saAnag.getInternalErrors());
                    gara.setValidationErrors(saAnag.getValidationErrors());
                    risultato.setData(gara);
                    return risultato;
                }
            }

            if(StringUtils.isBlank(codiceAusa)) {
                risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_COD_AUSA_NOT_SET);
                risultato.setData(gara);
                return risultato;
            }

            xUserCodiceFiscale = cf;
            xSACodiceFiscale = cfSa;
            xSAcodiceAUSA = codiceAusa;
            xUserIdpType = UserIdpTypeEnum.fromValue(idp);
            if(idAppalto == null && cig != null) {
                GenericResponse<Map<String,Object>> res = ricercaAppalto(xModalitaInvio, xTipologicaScheda,  cig, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if (res.getResult() != null) {
                    Object totRowsObj = res.getResult().get("totRows");
                    Object resultObj = res.getResult().get("result");

                    // Controllo che totRows sia un numero e maggiore di 0
                    int totRows = 0;
                    if (totRowsObj instanceof Number) {
                        totRows = ((Number) totRowsObj).intValue();
                    }

                    // Controllo che result sia una lista e abbia almeno un elemento
                    if (totRows > 0 && resultObj instanceof List<?>) {
                        List<?> resultList = (List<?>) resultObj;

                        if (!resultList.isEmpty() && resultList.get(0) instanceof Map<?, ?>) {
                            Map<?, ?> firstResult = (Map<?, ?>) resultList.get(0);
                            Object idAppaltoObj = firstResult.get("idAppalto");

                            if (idAppaltoObj != null) {
                                idAppalto = idAppaltoObj.toString();
                            }
                        }
                        if(StringUtils.isBlank(idAppalto)){
                            risultato.setEsito(false);
                            risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND_IN_PCP);
                            return risultato;
                        }
                    } else {
                        risultato.setEsito(false);
                        risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND_IN_PCP);
                        return risultato;
                    }

                } else {
                    if(res.getAnacErrors() != null && !res.getAnacErrors().isEmpty()) {
                        boolean contieneSEC = false;
                        for (String elemento : res.getAnacErrors()) {
                            if (StringUtils.isNotBlank(elemento) && elemento.contains("SEC05")) {
                                contieneSEC = true;
                                break;
                            }
                        }
                        if(contieneSEC) {
                            gara.setPresaCarico(true);
                            risultato.setData(gara);
                            return risultato;
                        } else {
                            gara.setAnacErrors(res.getAnacErrors());
                            gara.setInternalErrors(res.getInternalErrors());
                            gara.setValidationErrors(res.getValidationErrors());
                            risultato.setData(gara);
                            return risultato;
                        }
                    }
                }
            }

            GaraEntry g = this.contrattiMapper.dettaglioGaraByIdAppalto(idAppalto.toUpperCase());
            appalto = consultaAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);

            boolean contieneSEC = false;
            if(appalto.getAnacErrors() != null && !appalto.getAnacErrors().isEmpty()) {
                for (String elemento : appalto.getAnacErrors()) {
                    if (StringUtils.isNotBlank(elemento) && elemento.contains("SEC05")) {
                        contieneSEC = true;
                        break;
                    }
                }
            }
            if(g == null) {
                if(appalto.getResult() != null && appalto.getResult().get("appalto") != null) {
                    Map<String, Object> appaltoMap = (Map<String, Object>) appalto.getResult().get("appalto");
                    idAppalto = appaltoMap.get("idAppalto").toString();

                    GenericResponse<Map<String, Object>> stato = statoAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, codein, codiceAusa, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                    if(stato.getResult() != null) {

                        Map<String, Object> esitoStato = (Map<String, Object>) stato.getResult().get("esitoStato");
                        Map<String, String> statoMap = esitoStato != null && esitoStato.get("stato") != null
                                ? (Map<String, String>) esitoStato.get("stato")
                                : null;

                        if(statoMap != null && statoMap.get("codice") != null && !statoMap.get("codice").equals("PUBB")) {
                            risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_STATO_APPALTO_PCP);
                            risultato.setData(gara);
                            return risultato;
                        }

                        GenericResponse<Map<String, Object>> cigs = recuperaCig(xModalitaInvio, xTipologicaScheda, cig, idAppalto, 1, 1, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                        if(cigs.getResult() != null) {
                            Integer totRows = (Integer) cigs.getResult().get("totRows");
                            Integer totRowsSearch = totRows > 20 ? 20 : totRows;
                            for(int page = 0; page * totRowsSearch < totRows; page++) {
                                cigs = recuperaCig(xModalitaInvio, xTipologicaScheda, cig, idAppalto, page + 1, totRowsSearch, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                                if(cigs.getResult() != null) {
                                    List<Map<String, Object>> resultList = (List<Map<String, Object>>) cigs.getResult().get("result");

                                    for (Map<String, Object> c : resultList) {
                                        if(StringUtils.isNotBlank((String) c.get("cig"))) {
                                            lotIdCigMap.put((String) c.get("lotIdentifier"), (String) c.get("cig"));
                                        }
                                    }
                                }
                            }
                        } else {
                            gara.setAnacErrors(cigs.getAnacErrors());
                            gara.setInternalErrors(cigs.getInternalErrors());
                            gara.setValidationErrors(cigs.getValidationErrors());
                            risultato.setData(gara);
                        }
                        if(lotIdCigMap.isEmpty()) {
                            risultato.setEsito(false);
                            risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_NO_CIG_PCP);
                            return risultato;
                        }

                        GenericResponse<Map<String, Object>> sogg = ricercaSoggetti(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                        if(sogg.getResult() != null) {
                            soggetti = (List<Map<String, Object>>) sogg.getResult().get("result");
                        } else {
                            gara.setAnacErrors(sogg.getAnacErrors());
                            gara.setInternalErrors(sogg.getInternalErrors());
                            gara.setValidationErrors(sogg.getValidationErrors());
                            risultato.setData(gara);
                        }

                        GenericResponse<Map<String, Object>> schede = ricercaScheda(xModalitaInvio, xTipologicaScheda, cig, idAppalto, 1, 1, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);

                        if(schede.getResult() != null) {
                            Integer totRows = (Integer) schede.getResult().get("totRows");
                            Integer totRowsSearch = totRows > 20 ? 20 : totRows;
                            for(int page = 0; page * totRowsSearch < totRows; page++) {
                                schede = ricercaScheda(xModalitaInvio, xTipologicaScheda, null, idAppalto, page + 1 , totRowsSearch, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                                if(schede.getResult() != null) {
                                    for (Map<String, Object> s : (List<Map<String, Object>>) schede.getResult().get("result")) {
                                        String codice = (String) ((Map<String, Object>)s.get("codice")).get("codice");
                                        if((!codice.startsWith("AD") && !codice.startsWith("P") && !codice.equals("A3_6")) && ((Map<String, Object>)s.get("stato")).get("codice").equals("CONF")) {
                                            GenericResponse<Map<String,Object>> scheda = consultaScheda(xModalitaInvio, xTipologicaScheda, s.get("idScheda").toString(), syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, idAppalto);
                                            if(scheda.getResult() != null) {
                                                schedeAgg.add((Map<String, Object>) scheda.getResult().get("scheda"));
                                                if(((Map<String, Object>)((Map<String, Object>) scheda.getResult().get("scheda")).get("codice")).get("codice").equals("SC1")) {
                                                    GenericResponse<Map<String,Object>> esitoOp = esitoOperazione(null, FasiPcp.SCHEDASC1, idAppalto, "SC_CONF", "TUTTI_ESITI", "OK", syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                                                    if(esitoOp.getResult() != null && esitoOp.getResult().get("listaEsiti") != null && ((List<Map<String, Object>>) esitoOp.getResult().get("listaEsiti")).size() > 0) {
                                                        for (Map<String, Object> op : (List<Map<String, Object>>) esitoOp.getResult().get("listaEsiti")) {
                                                            if(op.get("idScheda") != null) {
                                                                if(op.get("idScheda").toString().equals(((Map<String, Object>) scheda.getResult().get("scheda")).get("idScheda").toString()) && op.get("idContratto") != null) {
                                                                    idContratto = op.get("idContratto").toString();
                                                                    Map<String, Object>  schedaMap = (Map<String, Object>)scheda.getResult().get("scheda");
                                                                    Map<String, Object>  body = (Map<String, Object>)schedaMap.get("body");
                                                                    Map<String, Object> anacForm = (Map<String, Object>) body.get("anacForm");
                                                                    Map<String, Object> datiContratto = (Map<String, Object>) anacForm.get("datiContratto");
                                                                    List<String> cigList = (List<String>) datiContratto.get("cig");
                                                                    for(String c : cigList){
                                                                        idContrattoCig.put(c,idContratto);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                gara.setAnacErrors(scheda.getAnacErrors());
                                                gara.setInternalErrors(scheda.getInternalErrors());
                                                gara.setValidationErrors(scheda.getValidationErrors());
                                                risultato.setData(gara);
                                                return risultato;
                                            }
                                        }
                                    }
                                } else {
                                    gara.setAnacErrors(schede.getAnacErrors());
                                    gara.setInternalErrors(schede.getInternalErrors());
                                    gara.setValidationErrors(schede.getValidationErrors());
                                    risultato.setData(gara);
                                    return risultato;
                                }
                            }
                            ResponseElaborateAppaltoPcp res = this.elaborateSchedePcp.insertAppaltoClassByCodiceScheda((Map<String, Object>)((Map<String, Object>) appalto.getResult().get("appalto")).get("scheda"), codein, schedeAgg, lotIdCigMap, soggetti, idAppalto, syscon, idContrattoCig, null, false);
                            if(res != null && res.getCigCodLotMap() != null) {
                                gara.setInserito(true);
                                Long codGara = (Long) res.getCodGara();
                                gara.setCodGara(codGara.toString());
                                List<ConsultaGaraBean> listaLotti = new ArrayList<>();
                                ConsultaGaraBean lotto = null;
                                for (Map.Entry<String, Long> entry : res.getCigCodLotMap().entrySet()) {
                                    lotto = new ConsultaGaraBean(entry.getKey(), true);
                                    listaLotti.add(lotto);
                                    Long codLotto = entry.getValue();
                                    Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
                                    this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
                                    Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
                                    this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);
                                }
                                gara.setListaLotti(listaLotti);
                                risultato.setData(gara);
                            }
                        } else {
                            gara.setAnacErrors(schede.getAnacErrors());
                            gara.setInternalErrors(schede.getInternalErrors());
                            gara.setValidationErrors(schede.getValidationErrors());
                            risultato.setData(gara);
                        }

                    } else {
                        gara.setAnacErrors(stato.getAnacErrors());
                        gara.setInternalErrors(stato.getInternalErrors());
                        gara.setValidationErrors(stato.getValidationErrors());
                        risultato.setData(gara);
                    }
                } else {
                    if(contieneSEC) {
                        gara.setPresaCarico(true);
                        risultato.setData(gara);
                        return risultato;
                    } else {
                        gara.setAnacErrors(appalto.getAnacErrors());
                        gara.setInternalErrors(appalto.getInternalErrors());
                        gara.setValidationErrors(appalto.getValidationErrors());
                        risultato.setData(gara);
                        return risultato;
                    }
                }
            } else {
                if(g.getCodiceStazioneAppaltante().equals(codein)) {
                    boolean permission = this.hasPermission(g.getCodgara(), authorization, loginMode);
                    if(!permission && !contieneSEC) {
                        this.contrattiManager.aggiornaRup(xUserCodiceFiscale, codein, g.getCodgara());
                        gara.setInserito(true);
                        gara.setCodGara(g.getCodgara().toString());
                        risultato.setData(gara);
                    } else if(contieneSEC) {
                        gara.setPresaCarico(true);
                        risultato.setData(gara);
                        return risultato;
                    } else{
                        gara.setInserito(true);
                        gara.setCodGara(g.getCodgara().toString());
                        risultato.setData(gara);
                    }
                } else {
                    if(contieneSEC) {
                        gara.setPresaCarico(true);
                        risultato.setData(gara);
                        return risultato;
                    } else {
                        if(appalto != null && appalto.getResult() != null && appalto.getResult().get("appalto") != null) {
                            Map<String,Object> appa = (Map<String, Object>) appalto.getResult().get("appalto");
                            Boolean isSaAttiva = this.elaborateSchedePcp.getCfSaPcp((Map<String, Object>) appa.get("scheda"), cfSa);
                            if(isSaAttiva) {
                                MigrazioneGaraForm form = new MigrazioneGaraForm();
                                form.setCodGara(g.getCodgara());
                                form.setCodiceFiscaleRupGara(xUserCodiceFiscale);
                                form.setStazioneAppaltante(codein);
                                this.contrattiManager.migraGara(form);
                                gara.setInserito(true);
                                gara.setCodGara(g.getCodgara().toString());
                                risultato.setData(gara);
                            } else {
                                risultato.setEsito(false);
                                risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_PERMISSION_SA_PCP);
                                return risultato;
                            }
                        }
                    }
                }
            }

        } catch (UnauthorizedSAException ex) {
            logger.error("errore nel metodo importaGaraPcp UnauthorizedSAException", ex);
            risultato.setEsito(false);
            risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_PERMISSION_SA_PCP);
            return risultato;
        } catch (AppaltoAnnullatoException ex3) {
            logger.error("errore nel metodo importaGaraPcp AppaltoAnnullatoException", ex3);
            risultato.setEsito(false);
            risultato.setErrorData(ex3.getMessage());
            return risultato;
        } catch (Exception e) {
            logger.error("errore nel metodo importaGaraPcp", e);
            throw e;
        }

        return risultato;
    }


    public ResponseImportaGara executePresaCaricoImportGaraPcp(String cfSa, String cig, String idAppalto, Long syscon, String cf, String loa, String idp,String authorization, String loginMode) throws Exception {
        ResponseImportaGara risultato = new ResponseImportaGara();
        risultato.setEsito(true);
        GenericResponse<Map<String,Object>> appalto = new GenericResponse<Map<String,Object>>();

        EModalitaInvio xModalitaInvio = null;
        String xTipologicaScheda = null;


        ConsultaGaraEntry gara = new ConsultaGaraEntry();
        gara.setOperation("OP_IMPORT_ANAC");
        gara.setInserito(false);
        gara.setPresaCaricoCompleted(false);

        UserIdpTypeEnum xUserIdpType = null;
        CustomUserLoaEnum xUserLoa = null;
        String xSAcodiceAUSA = null;
        String xUserCodiceFiscale = null;
        String xSACodiceFiscale = null;
        String xUserRole = null;

        try {

            if(idAppalto == null && cig != null) {
                risultato.setEsito(false);
                risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_PCP_PRESA_CARICO_NO_IDAPPALTO);
                return risultato;
            }

            initApi();
            xUserRole = "RP";
            if(loa.equals("3")) {
                xUserLoa = CustomUserLoaEnum._3;
            } else if(loa.equals("4")) {
                xUserLoa = CustomUserLoaEnum._4;
            } else {
                throw new Exception("Utente non autorizzato");
            }

            String codein = this.contrattiMapper.getCodeinByCfSa(cfSa);
            String codiceAusa = this.contrattiMapper.getCodAusaUffint(codein);

            if(StringUtils.isBlank(codiceAusa)) {
                GenericResponse<Map<String,Object>> saAnag = getCodAusa(cfSa, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if(saAnag.getResult() != null) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(objectMapper.writeValueAsString(saAnag.getResult()));

                    Map<String, String> resultMap = extractCodiceAusa(rootNode);

                    if(resultMap != null && StringUtils.isNotBlank(resultMap.get("codiceAusa"))) {
                        codiceAusa = resultMap.get("codiceAusa");
                        this.contrattiMapper.updateCodusaUffint(codiceAusa, codein);
                    }
                } else {
                    gara.setAnacErrors(saAnag.getAnacErrors());
                    gara.setInternalErrors(saAnag.getInternalErrors());
                    gara.setValidationErrors(saAnag.getValidationErrors());
                    risultato.setData(gara);
                    return risultato;
                }
            }

            if(StringUtils.isBlank(codiceAusa)) {
                risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_COD_AUSA_NOT_SET);
                risultato.setData(gara);
                return risultato;
            }

            xUserCodiceFiscale = cf;
            xSACodiceFiscale = cfSa;
            xSAcodiceAUSA = codiceAusa;
            xUserIdpType = UserIdpTypeEnum.fromValue(idp);

            GaraEntry g = this.contrattiMapper.dettaglioGaraByIdAppalto(idAppalto.toUpperCase());
            if(g == null) {
                PresaCaricoRequestDTO body = new PresaCaricoRequestDTO();
                body.setIdAppalto(idAppalto);
                GenericResponse<Map<String,Object>> pcRes = presaCarico(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, body, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                if(pcRes != null && pcRes.getResult() != null) {
                    logger.info("preso in carico e lancio funzione ricorsiva");
                    risultato =  importaGaraPcp(cfSa, cig, idAppalto, syscon, cf, loa, idp, authorization, loginMode);
                    gara = risultato.getData();
                    gara.setPresaCaricoCompleted(true);
                    risultato.setData(gara);
                } else {
                    gara.setAnacErrors(pcRes.getAnacErrors());
                    gara.setInternalErrors(pcRes.getInternalErrors());
                    gara.setValidationErrors(pcRes.getValidationErrors());
                    risultato.setData(gara);
                    return risultato;
                }
            } else {
                if(g.getCodiceStazioneAppaltante().equals(codein)) {
                    PresaCaricoRequestDTO body = new PresaCaricoRequestDTO();
                    body.setIdAppalto(idAppalto);
                    GenericResponse<Map<String,Object>> pcRes = presaCarico(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, body, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                    if(pcRes != null && pcRes.getResult() != null) {
                        logger.info("preso in carico con gara in db e sa attiva e utente con permessi");
                        gara.setPresaCaricoCompleted(true);
                        this.contrattiManager.aggiornaRup(xUserCodiceFiscale, codein, g.getCodgara());

                        //VIGILANZA2-490: Invio messaggio al RUP precedente di presa in carico da parte del nuovo RUP.
                        //Se il RUP precedente è un utente applicativo, allora posso inviare il messaggio.
                        String cfTecPrecedente = this.contrattiMapper.getCfFromRUPId(g.getCodiceTecnico());
                        Long sysconPrecedente = this.contrattiMapper.getSysconFromCf(cfTecPrecedente);
                        String nomeCognomeNuovoRUP = this.contrattiMapper.getNomTecByCfTec(xUserCodiceFiscale);
                        Long sysconNuovoRUP = this.contrattiMapper.getSysconFromCf(xUserCodiceFiscale);
                        if(sysconPrecedente != null) {
                            MessageInForm inForm = new MessageInForm();
                            Long idIn = calculateMessageId();
                            inForm.setId(idIn);
                            inForm.setCorpoMessaggio(
                                    Constants.INIZIO_MESSAGGIO_PRESA_CARICO_W_MESSAGE_IN +
                                            g.getIdAppalto() +
                                            Constants.CORPO_MESSAGGIO_PRESA_CARICO_W_MESSAGE_IN +
                                            nomeCognomeNuovoRUP
                            );
                            inForm.setDataMessaggio(new Date());
                            inForm.setOggetto(Constants.OGGETTO_PRESA_CARICO_W_MESSAGE_IN + g.getIdAppalto());
                            inForm.setSysconReceiver(g.getSyscon());
                            inForm.setSysconSender(sysconNuovoRUP);
                            this.contrattiMapper.insertMessageIn(inForm);
                        }

                        gara.setInserito(true);
                        gara.setCodGara(g.getCodgara().toString());
                        risultato.setData(gara);
                    } else {
                        gara.setAnacErrors(pcRes.getAnacErrors());
                        gara.setInternalErrors(pcRes.getInternalErrors());
                        gara.setValidationErrors(pcRes.getValidationErrors());
                        risultato.setData(gara);
                    }
                } else {
                    PresaCaricoRequestDTO body = new PresaCaricoRequestDTO();
                    body.setIdAppalto(idAppalto);
                    GenericResponse<Map<String,Object>> pcRes = presaCarico(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, body, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                    if(pcRes != null && pcRes.getResult() != null) {
                        logger.info("preso in carico con gara in db e sa non attiva e utente senza permessi in pcp");
                        gara.setPresaCaricoCompleted(true);
                        this.contrattiManager.aggiornaRup(xUserCodiceFiscale, codein, g.getCodgara());

                        //VIGILANZA2-490: Invio messaggio al RUP precedente di presa in carico da parte del nuovo RUP.
                        //Se il RUP precedente è un utente applicativo, allora posso inviare il messaggio.
                        String cfTecPrecedente = this.contrattiMapper.getCfFromRUPId(g.getCodiceTecnico());
                        Long sysconPrecedente = this.contrattiMapper.getSysconFromCf(cfTecPrecedente);
                        String nomeCognomeNuovoRUP = this.contrattiMapper.getNomTecByCfTec(xUserCodiceFiscale);
                        Long sysconNuovoRUP = this.contrattiMapper.getSysconFromCf(xUserCodiceFiscale);
                        if(sysconPrecedente != null) {
                            MessageInForm inForm = new MessageInForm();
                            Long idIn = calculateMessageId();
                            inForm.setId(idIn);
                            inForm.setCorpoMessaggio(
                                    Constants.INIZIO_MESSAGGIO_PRESA_CARICO_W_MESSAGE_IN +
                                            g.getIdAppalto() +
                                            Constants.CORPO_MESSAGGIO_PRESA_CARICO_W_MESSAGE_IN +
                                            nomeCognomeNuovoRUP
                            );
                            inForm.setDataMessaggio(new Date());
                            inForm.setOggetto(Constants.OGGETTO_PRESA_CARICO_W_MESSAGE_IN + g.getIdAppalto());
                            inForm.setSysconReceiver(g.getSyscon());
                            inForm.setSysconSender(sysconNuovoRUP);
                            this.contrattiMapper.insertMessageIn(inForm);
                        }

                        appalto = consultaAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                        Boolean isSaAttiva = false;
                        if(appalto != null && appalto.getResult() != null && appalto.getResult().get("appalto") != null) {
                            Map<String, Object> appa = (Map<String, Object>) appalto.getResult().get("appalto") ;
                            isSaAttiva = this.elaborateSchedePcp.getCfSaPcp((Map<String, Object>) appa.get("scheda"), cfSa);
                            if(isSaAttiva) {
                                MigrazioneGaraForm form = new MigrazioneGaraForm();
                                form.setCodGara(g.getCodgara());
                                form.setCodiceFiscaleRupGara(xUserCodiceFiscale);
                                form.setStazioneAppaltante(codein);
                                this.contrattiManager.migraGara(form);
                                gara.setInserito(true);
                                gara.setCodGara(g.getCodgara().toString());
                                risultato.setData(gara);
                            } else {
                                risultato.setEsito(false);
                                risultato.setErrorData(BaseResponse.ERROR_PERMISSION_SA_PCP);
                                return risultato;
                            }
                        } else {
                            gara.setAnacErrors(appalto.getAnacErrors());
                            gara.setInternalErrors(appalto.getInternalErrors());
                            gara.setValidationErrors(appalto.getValidationErrors());
                            risultato.setData(gara);
                        }
                    } else {
                        gara.setAnacErrors(pcRes.getAnacErrors());
                        gara.setInternalErrors(pcRes.getInternalErrors());
                        gara.setValidationErrors(pcRes.getValidationErrors());
                        risultato.setData(gara);
                    }
                }
            }
        }catch (Exception e) {
            logger.error("errore nel metodo executePresaCaricoImportGaraPcp", e);
            throw e;
        }
        return risultato;
    }

    private Long calculateMessageId() {
        Long maxInId = this.contrattiMapper.getMaxMessageInId();

        if (maxInId == null) {
            return 1L;
        } else {
            return maxInId + 1;
        }
    }


    public boolean isUUID(String cigIdAvGara) {
        if (cigIdAvGara == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(cigIdAvGara).matches();
    }


    public ResponseRiallineaAnac riallineaAnac(Long codGara, Long syscon, String cf, String loa, String idp, Boolean cancellaDatiEse) throws Exception {

        ResponseRiallineaAnac risultato = new ResponseRiallineaAnac();
        risultato.setAllineato(true);
        risultato.setEsito(true);
        EModalitaInvio xModalitaInvio = null;
        String xTipologicaScheda = null;

        UserIdpTypeEnum xUserIdpType = null;
        CustomUserLoaEnum xUserLoa = null;
        String xSAcodiceAUSA = null;
        String xUserCodiceFiscale = null;
        String xSACodiceFiscale = null;
        String xUserRole = null;

        try {
            GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
            String codein = gara.getCodiceStazioneAppaltante();
            initApi();
            xUserCodiceFiscale = cf;
            xUserRole = null;
            RupEntry drp = null;
            if (gara.getDrp() != null) {
                drp = this.contrattiMapper.getTecnico(gara.getDrp());
                if(cf.equals(drp.getCf())){
                    xUserRole = "DRP3";
                }
            }
            RupEntry tecnico = null;
            if (gara.getCodiceTecnico() != null) {
                tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
                if(cf.equals(tecnico.getCf())){
                    xUserRole = "RP";
                }
            }

            if(xUserRole == null) {
                throw new UnauthorizedUserException("Utente non autorizzato");
            }

            String codiceSA = gara.getCodiceStazioneAppaltante();
            String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
            String cfSa = this.contrattiMapper.getCFSAByCode(codiceSA);
            List<LottoBaseEntry> lottiCig = this.contrattiMapper.getLottiGara(codGara);

            xSACodiceFiscale = cfSa;
            xSAcodiceAUSA = codiceAusa;
            xUserIdpType = UserIdpTypeEnum.fromValue(idp);
            if(loa.equals("3")) {
                xUserLoa = CustomUserLoaEnum._3;
            } else if(loa.equals("4")) {
                xUserLoa = CustomUserLoaEnum._4;
            } else {
                throw new UnauthorizedUserException("Utente non autorizzato");
            }

            String idAppalto = gara.getIdAppalto() != null ? gara.getIdAppalto() : null;
            String idContratto = null;
            List<Map<String, Object>> schedeAgg = new ArrayList<Map<String, Object>>();


            ResponseElaborateAppaltoPcp cigMapObj = new ResponseElaborateAppaltoPcp();
            Map<String,Long> cigCodLotMap = new HashMap<String, Long>();
            Map<String,String> cigLotIdMap = new HashMap<String, String>();
            Map<String,String> lotIdCigMap = new HashMap<String, String>();
            Map<String, String> idContrattoCig = new HashMap<String, String>();
            cigMapObj.setCodGara(codGara);
            List<Map<String,Object>> soggetti = new ArrayList<Map<String,Object>>();




            //riallino gara e lotto
            GenericResponse<Map<String,Object>> appalto = consultaAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
            if(appalto.getResult() != null && appalto.getResult().get("appalto") != null) {

                GenericResponse<Map<String,Object>> cigs = recuperaCig(xModalitaInvio, xTipologicaScheda, null, idAppalto, 1, 1, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if(cigs != null && cigs.getResult() != null) {
                    cigs = recuperaCig(xModalitaInvio, xTipologicaScheda, null, idAppalto, 1, (Integer)cigs.getResult().get("totRows"), syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                    if(cigs != null && cigs.getResult() != null) {
                        List<Map<String, Object>> resultList = (List<Map<String, Object>>) cigs.getResult().get("result");

                        for (Map<String, Object> c : resultList) {
                            if(StringUtils.isNotBlank((String) c.get("cig"))) {
                                lotIdCigMap.put((String) c.get("lotIdentifier"), (String) c.get("cig"));
                            }
                        }
                    }
                } else {
                    risultato.setAnacErrors(cigs.getAnacErrors());
                    risultato.setInternalErrors(cigs.getInternalErrors());
                    risultato.setValidationErrors(cigs.getValidationErrors());
                }
                if(lotIdCigMap.isEmpty()) {
                    risultato.setEsito(false);
                    risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_NO_CIG_PCP);
                    return risultato;
                }
                GenericResponse<Map<String,Object>> sogg = ricercaSoggetti(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if(sogg.getResult() != null) {
                    soggetti = (List<Map<String, Object>>) sogg.getResult().get("result");
                } else {
                    risultato.setAnacErrors(sogg.getAnacErrors());
                    risultato.setInternalErrors(sogg.getInternalErrors());
                    risultato.setValidationErrors(sogg.getValidationErrors());
                }
                GenericResponse<Map<String, Object>> schede = ricercaScheda(xModalitaInvio, xTipologicaScheda, null, idAppalto, 1, 1, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                if(schede.getResult() != null) {
                    Integer totRows = (Integer) schede.getResult().get("totRows");
                    Integer totRowsSearch = totRows > 20 ? 20 : totRows;
                    for(int page = 0; page * totRowsSearch < totRows; page++) {
                        schede = ricercaScheda(xModalitaInvio, xTipologicaScheda, null, idAppalto, page + 1 , totRowsSearch, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                        if(schede.getResult() != null) {
                            for (Map<String, Object> s : (List<Map<String, Object>>) schede.getResult().get("result")) {
                                String codice = (String) ((Map<String, Object>)s.get("codice")).get("codice");
                                if((!codice.startsWith("AD") && !codice.startsWith("P") && !codice.equals("A3_6")) && (((Map<String, Object>)s.get("stato")).get("codice").equals("CONF") || ((Map<String, Object>)s.get("stato")).get("codice").equals("IN_LAV"))) {
                                    GenericResponse<Map<String,Object>> scheda = consultaScheda(xModalitaInvio, xTipologicaScheda, s.get("idScheda").toString(), syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, idAppalto);
                                    if(scheda.getResult() != null) {
                                        schedeAgg.add((Map<String, Object>) scheda.getResult().get("scheda"));
                                        if(((Map<String, Object>)((Map<String, Object>) scheda.getResult().get("scheda")).get("codice")).get("codice").equals("SC1")) {
                                            GenericResponse<Map<String,Object>> esitoOp = esitoOperazione(null, FasiPcp.SCHEDASC1, idAppalto, "SC_CONF", "TUTTI_ESITI", "OK", syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                                            if(esitoOp.getResult() != null && esitoOp.getResult().get("listaEsiti") != null && ((List<Map<String, Object>>) esitoOp.getResult().get("listaEsiti")).size() > 0) {
                                                for (Map<String, Object> op : (List<Map<String, Object>>) esitoOp.getResult().get("listaEsiti")) {
                                                    if(op.get("idScheda") != null) {
                                                        if(op.get("idScheda").toString().equals(((Map<String, Object>) scheda.getResult().get("scheda")).get("idScheda").toString()) && op.get("idContratto") != null) {
                                                            idContratto = op.get("idContratto").toString();
                                                            Map<String, Object>  schedaMap = (Map<String, Object>)scheda.getResult().get("scheda");
                                                            Map<String, Object>  body = (Map<String, Object>)schedaMap.get("body");
                                                            Map<String, Object> anacForm = (Map<String, Object>) body.get("anacForm");
                                                            Map<String, Object> datiContratto = (Map<String, Object>) anacForm.get("datiContratto");
                                                            List<String> cigList = (List<String>) datiContratto.get("cig");
                                                            for(String c : cigList){
                                                                idContrattoCig.put(c,idContratto);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        risultato.setAnacErrors(scheda.getAnacErrors());
                                        risultato.setInternalErrors(scheda.getInternalErrors());
                                        risultato.setValidationErrors(scheda.getValidationErrors());
                                        return risultato;
                                    }
                                }
                            }
                        } else {
                            risultato.setAnacErrors(schede.getAnacErrors());
                            risultato.setInternalErrors(schede.getInternalErrors());
                            risultato.setValidationErrors(schede.getValidationErrors());
                            return risultato;
                        }
                    }

                    ResponseElaborateAppaltoPcp res = this.elaborateSchedePcp.insertAppaltoClassByCodiceScheda((Map<String, Object>)((Map<String, Object>) appalto.getResult().get("appalto")).get("scheda"),codein,schedeAgg,lotIdCigMap,soggetti,idAppalto,syscon,idContrattoCig, codGara, cancellaDatiEse);
                    if(res != null && res.getCigCodLotMap() != null) {
                        List<ConsultaGaraBean> listaLotti = new ArrayList<ConsultaGaraBean>();
                        ConsultaGaraBean lotto = null;
                        for (Map.Entry<String, Long> entry : res.getCigCodLotMap().entrySet()) {
                            lotto = new ConsultaGaraBean(entry.getKey(), true);
                            listaLotti.add(lotto);
                            Long codLotto = entry.getValue();

                            Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
                            this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
                            Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
                            this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);

                            //VIGILANZA-383: Aggiorno l'autore della w9flussi con l'autore della w9reg_invii_pcp
                            this.contrattiMapper.updateAutoreInvioSchede(codGara, codLotto);
                        }
                    }
                } else {
                    risultato.setAnacErrors(schede.getAnacErrors());
                    risultato.setInternalErrors(schede.getInternalErrors());
                    risultato.setValidationErrors(schede.getValidationErrors());
                }


            } else {
                risultato.setAnacErrors(appalto.getAnacErrors());
                risultato.setInternalErrors(appalto.getInternalErrors());
                risultato.setValidationErrors(appalto.getValidationErrors());
            }

        } catch (UnauthorizedSAException ex) {
            logger.error("errore nel metodo riallineaAnac", ex);
            risultato.setAllineato(false);
            risultato.setEsito(false);
            risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_PERMISSION_SA);
            return risultato;
        } catch (AppaltoAnnullatoEliminareException ex4) {
            logger.error("errore nel metodo importaGaraPcp AppaltoAnnullatoException", ex4);
            risultato.setEsito(false);
            risultato.setAllineato(false);
            risultato.setSchedaAnn(true);
            return risultato;
        } catch (SottoSogliaLottoException ex2) {
            logger.error("errore nel metodo riallineaAnac", ex2);
            risultato.setAllineato(false);
            risultato.setEsito(false);
            risultato.setErrorData(ex2.getMessage());
            return risultato;
        }catch (Exception e) {
            logger.error("errore nel metodo riallineaAnac", e);
            throw e;
        }

        return risultato;
    }

    public boolean hasPermission(long codGara, String authorization, String loginMode) {

        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);

            String cf = userAuthClaimsDTO.getCf();
            Long syscon = userAuthClaimsDTO.getSyscon();
            ResponseDettaglioGara dettaglioGara = this.contrattiManager.dettaglioGara(codGara,authorization,loginMode);
            GaraEntry gara = dettaglioGara.getData();
            if (gara == null) {
                return false;
            }
            String ruolo = this.contrattiManager.getRuolo(syscon);
            List<String> saList = this.contrattiManager.getSAList(syscon);
            String abilitazioniUtente = this.contrattiManager.getAbilitazioniUtente(syscon);

            if ((abilitazioniUtente != null && !abilitazioniUtente.contains("ou238")) && !saList.contains(gara.getCodiceStazioneAppaltante())) {
                return false;
            }

            if (gara.getSyscon() != null && gara.getSyscon().equals(syscon)) {
                return true;
            }

            if (!"A".equals(ruolo) && !"C".equals(ruolo)) {

                Boolean isRup = true;
                Boolean isCollab = true;
                Boolean isDrp = true;
                Boolean collaboratoreDrp = true;

                RupEntry tecnico = this.contrattiManager.getRup(gara.getCodiceTecnico());
                if (tecnico != null && StringUtils.isNotBlank(cf) && !cf.equals(tecnico.getCf())) {
                    isRup = false;
                }

                if(gara.getDrp() != null && gara.isPcp()) {
                    RupEntry drp = this.contrattiManager.getRup(gara.getDrp());
                    if (drp != null && StringUtils.isNotBlank(cf) && !cf.equals(drp.getCf())) {
                        isDrp = false;
                    }
                    if(drp != null) {
                        Long collaboratore = this.delegheManager.getExistsCollaboratore(drp.getCf(),gara.getCodiceStazioneAppaltante(),syscon);
                        if (collaboratore == 0L) {
                            collaboratoreDrp = false;
                        }
                    }
                } else {
                    isDrp = false;
                    collaboratoreDrp = false;
                }

                if(tecnico != null) {
                    Long collaboratore = this.delegheManager.getExistsCollaboratore(tecnico.getCf(),gara.getCodiceStazioneAppaltante(),syscon);
                    if (collaboratore == 0L) {
                        isCollab = false;
                    }
                }

                if(gara.isPcp()) {
                    if(!isRup && !isCollab && !isDrp && !collaboratoreDrp) {
                        return false;
                    }
                } else {
                    if(!isRup && !isCollab) {
                        return false;
                    }
                }


            }
        } catch (Exception e) {
            logger.error("Errore durante il controllo dei permessi", e);
            return false;
        }
        return true;
    }



    public ResponseAppaltoPcp getDatiAppaltoPcp(String cfSa, String idAppalto, String cig, Long syscon, String cf, String loa, String idp, String codiceAusa) throws Exception {
        ResponseAppaltoPcp risultato = new ResponseAppaltoPcp();
        risultato.setEsito(true);

        GenericResponse<Map<String,Object>> appalto = new GenericResponse<Map<String,Object>>();

        EModalitaInvio xModalitaInvio = null;
        String xTipologicaScheda = null;

        List<Map<String, Object>> schedeAgg = new ArrayList<Map<String, Object>>();
        Map<String,String> lotIdCigMap = new HashMap<String,String>();
        List<Map<String, Object>> soggetti = new ArrayList<Map<String, Object>>();
        Map<String, String> idContrattoCig = new HashMap<String,String>();

        String idContratto = null;

        UserIdpTypeEnum xUserIdpType = null;
        CustomUserLoaEnum xUserLoa = null;
        String xSAcodiceAUSA = null;
        String xUserCodiceFiscale = null;
        String xSACodiceFiscale = null;
        String xUserRole = null;

        try {
            initApi();
            xUserRole = "RP";
            if (loa.equals("3")) {
                xUserLoa = CustomUserLoaEnum._3;
            } else if (loa.equals("4")) {
                xUserLoa = CustomUserLoaEnum._4;
            } else {
                throw new Exception("Utente non autorizzato");
            }

            xUserCodiceFiscale = cf;
            xSACodiceFiscale = cfSa;
            xSAcodiceAUSA = codiceAusa;
            xUserIdpType = UserIdpTypeEnum.fromValue(idp);

            if(idAppalto == null && cig != null){
                GenericResponse<Map<String,Object>> res = ricercaAppalto(xModalitaInvio, xTipologicaScheda,  cig, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if(res.getResult() != null){
                    Object totRowsObj = res.getResult().get("totRows");
                    Object resultObj = res.getResult().get("result");
                    int totRows = 0;
                    if (totRowsObj instanceof Number) {
                        totRows = ((Number) totRowsObj).intValue();
                    }
                    if (totRows > 0 && resultObj instanceof List<?>) {
                        List<?> resultList = (List<?>) resultObj;

                        if (!resultList.isEmpty() && resultList.get(0) instanceof Map<?, ?>) {
                            Map<?, ?> firstResult = (Map<?, ?>) resultList.get(0);
                            Object idAppaltoObj = firstResult.get("idAppalto");

                            if (idAppaltoObj != null) {
                                idAppalto = idAppaltoObj.toString();
                            }
                        }
                    } else {
                        risultato.setEsito(false);
                        risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_NOT_FOUND_IN_PCP);
                        return risultato;
                    }
                } else {
                    if(res.getAnacErrors() != null && !res.getAnacErrors().isEmpty()) {
                        risultato.setEsito(false);
                        risultato.setAnacErrors(res.getAnacErrors());
                        risultato.setInternalErrors(res.getInternalErrors());
                        risultato.setValidationErrors(res.getValidationErrors());
                        return risultato;
                    }
                }
            }
            if(idAppalto == null){
                risultato.setEsito(false);
                risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_NOT_FOUND_IN_PCP);
                return risultato;
            }

            GaraEntry g = this.contrattiMapper.dettaglioGaraByIdAppalto(idAppalto.toUpperCase());
            appalto = consultaAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);

            if(appalto.getResult() != null && appalto.getResult().get("appalto") != null) {
                Map<String, Object> appaltoMap = (Map<String, Object>) appalto.getResult().get("appalto");
                idAppalto = appaltoMap.get("idAppalto").toString();

                GenericResponse<Map<String, Object>> stato = statoAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, null, codiceAusa, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                if(stato.getResult() != null) {
                    Map<String, Object> esitoStato = (Map<String, Object>) stato.getResult().get("esitoStato");
                    Map<String, String> statoMap = esitoStato != null && esitoStato.get("stato") != null
                            ? (Map<String, String>) esitoStato.get("stato")
                            : null;
                    if(statoMap != null && statoMap.get("codice") != null && !statoMap.get("codice").equals("PUBB")) {
                        risultato.setErrorData(it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse.ERROR_STATO_APPALTO_PCP);
                        return risultato;
                    }

                    GenericResponse<Map<String, Object>> cigs = recuperaCig(xModalitaInvio, xTipologicaScheda, cig, idAppalto, 1, 1, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                    if(cigs.getResult() != null) {
                        Integer totRows = (Integer) cigs.getResult().get("totRows");
                        Integer totRowsSearch = totRows > 20 ? 20 : totRows;
                        for(int page = 0; page * totRowsSearch < totRows; page++) {
                            cigs = recuperaCig(xModalitaInvio, xTipologicaScheda, cig, idAppalto, page + 1, totRowsSearch, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                            if(cigs.getResult() != null) {
                                List<Map<String, Object>> resultList = (List<Map<String, Object>>) cigs.getResult().get("result");

                                for (Map<String, Object> c : resultList) {
                                    if(StringUtils.isNotBlank((String) c.get("cig"))) {
                                        lotIdCigMap.put((String) c.get("lotIdentifier"), (String) c.get("cig"));
                                    }
                                }
                            }
                        }
                    } else {
                        risultato.setAnacErrors(cigs.getAnacErrors());
                        risultato.setInternalErrors(cigs.getInternalErrors());
                        risultato.setValidationErrors(cigs.getValidationErrors());
                        return risultato;
                    }
                    if(lotIdCigMap.isEmpty()) {
                        risultato.setEsito(false);
                        risultato.setErrorData(BaseResponse.ERROR_NO_CIG_PCP);
                        return risultato;
                    }
                    GenericResponse<Map<String, Object>> sogg = ricercaSoggetti(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                    if(sogg.getResult() != null) {
                        soggetti = (List<Map<String, Object>>) sogg.getResult().get("result");
                    } else {
                        risultato.setAnacErrors(sogg.getAnacErrors());
                        risultato.setInternalErrors(sogg.getInternalErrors());
                        risultato.setValidationErrors(sogg.getValidationErrors());
                    }
                    GenericResponse<Map<String,Object>> schede = ricercaScheda(xModalitaInvio, xTipologicaScheda, null, idAppalto, 1, 1, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                    if (schede != null && schede.getResult() != null) {
                        Integer totRows = (Integer) schede.getResult().get("totRows");
                        Integer totRowsSearch = totRows > 20 ? 20 : totRows;
                        for (int page = 0; page * totRowsSearch < totRows; page++) {
                            schede = ricercaScheda(xModalitaInvio, xTipologicaScheda, null, idAppalto, page + 1, totRowsSearch, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
                            if(schede.getResult() != null) {
                                for (Map<String, Object> s : (List<Map<String, Object>>) schede.getResult().get("result")) {
                                    String codice = (String) ((Map<String, Object>)s.get("codice")).get("codice");
                                    if((!codice.startsWith("AD") && !codice.startsWith("P") && !codice.equals("A3_6")) && ((Map<String, Object>)s.get("stato")).get("codice").equals("CONF")) {
                                        GenericResponse<Map<String,Object>> scheda = consultaScheda(xModalitaInvio, xTipologicaScheda, s.get("idScheda").toString(), syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, idAppalto);
                                        if(scheda.getResult() != null) {
                                            schedeAgg.add((Map<String, Object>) scheda.getResult().get("scheda"));
                                            if(((Map<String, Object>)((Map<String, Object>) scheda.getResult().get("scheda")).get("codice")).get("codice").equals("SC1")) {
                                                GenericResponse<Map<String,Object>> esitoOp = esitoOperazione(null, FasiPcp.SCHEDASC1, idAppalto, "SC_CONF", "TUTTI_ESITI", "OK", syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
                                                if(esitoOp.getResult() != null && esitoOp.getResult().get("listaEsiti") != null && ((List<Map<String, Object>>) esitoOp.getResult().get("listaEsiti")).size() > 0) {
                                                    for (Map<String, Object> op : (List<Map<String, Object>>) esitoOp.getResult().get("listaEsiti")) {
                                                        if(op.get("idScheda") != null) {
                                                            if(op.get("idScheda").toString().equals(((Map<String, Object>) scheda.getResult().get("scheda")).get("idScheda").toString()) && op.get("idContratto") != null) {
                                                                idContratto = op.get("idContratto").toString();
                                                                Map<String, Object>  schedaMap = (Map<String, Object>)scheda.getResult().get("scheda");
                                                                Map<String, Object>  body = (Map<String, Object>)schedaMap.get("body");
                                                                Map<String, Object> anacForm = (Map<String, Object>) body.get("anacForm");
                                                                Map<String, Object> datiContratto = (Map<String, Object>) anacForm.get("datiContratto");
                                                                List<String> cigList = (List<String>) datiContratto.get("cig");
                                                                for(String c : cigList){
                                                                    idContrattoCig.put(c,idContratto);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            risultato.setAnacErrors(scheda.getAnacErrors());
                                            risultato.setInternalErrors(scheda.getInternalErrors());
                                            risultato.setValidationErrors(scheda.getValidationErrors());
                                        }
                                    }
                                }
                            } else {
                                risultato.setAnacErrors(schede.getAnacErrors());
                                risultato.setInternalErrors(schede.getInternalErrors());
                                risultato.setValidationErrors(schede.getValidationErrors());
                            }
                        }
                        String codein = this.contrattiMapper.getCodeinByCfSa(cfSa);
                        risultato = this.elaborateSchedePcp.getAppaltoClassByCodiceScheda((Map<String, Object>)((Map<String, Object>) appalto.getResult().get("appalto")).get("scheda"), codein, schedeAgg, lotIdCigMap, soggetti, idAppalto, syscon, idContrattoCig);


                    } else {
                        risultato.setAnacErrors(schede.getAnacErrors());
                        risultato.setInternalErrors(schede.getInternalErrors());
                        risultato.setValidationErrors(schede.getValidationErrors());
                    }

                } else {
                    risultato.setAnacErrors(stato.getAnacErrors());
                    risultato.setInternalErrors(stato.getInternalErrors());
                    risultato.setValidationErrors(stato.getValidationErrors());
                }
            } else {
                risultato.setAnacErrors(appalto.getAnacErrors());
                risultato.setInternalErrors(appalto.getInternalErrors());
                risultato.setValidationErrors(appalto.getValidationErrors());
            }

        } catch (GetDatiAppaltoException ex3) {
            risultato = ex3.getAdditionalInfo();
            risultato.setEsito(true);
        } catch (UnauthorizedSAException ex) {
            logger.error("errore nel metodo getDatiAppaltoPcp UnauthorizedSAException", ex);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_PERMISSION_SA_PCP);
            return risultato;
        } catch (Exception e) {
            logger.error("errore nel metodo getDatiAppaltoPcp", e);
            throw e;
        }

        return risultato;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse deleteAppaltoPcp(Long codGara) throws Exception {
        it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse risultato = new BaseResponse();
        try {
            //appalto
            this.contrattiMapper.deleteGara(codGara);
            this.contrattiMapper.deletePubblicitaDiGara(codGara);
            this.contrattiMapper.deleteAttiGara(codGara);
            this.contrattiMapper.deleteDocumentiAttiGara(codGara);

            //lotti
            this.contrattiMapper.deleteLotti(codGara);

            //fase agg-imprese-esito-incarichi
            this.contrattiMapper.deleteFaseAggiudicazionePcp(codGara, 1L);
            this.contrattiMapper.deleteFinanziamentiAllPcp(codGara, 1L);
            this.contrattiMapper.deleteImpreseAggiudicatarieAll(codGara, 1L);
            this.contrattiMapper.deleteFaseImpresaAllPcp(codGara);
            this.contrattiMapper.deleteFaseComEsitoAllPcp(codGara);
            this.contrattiMapper.deleteIncarichiProfAll(codGara);

            this.contrattiMapper.deleteW9FasePcp(codGara, 1L);
            this.contrattiMapper.deleteW9FasePcp(codGara, 101L);
            this.contrattiMapper.deleteW9FasePcp(codGara, 984L);
            this.contrattiMapper.deleteW9FasePcp(codGara, 20L);

            this.contrattiMapper.deleteFlussoPcp(codGara, 1L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 101L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 984L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 20L);

            // elimino I1
            this.contrattiMapper.deleteFaseInizialeEsecuzionePcp(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 2L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 2L);

            // elimino SA1
            this.contrattiMapper.deleteFaseAvanzamentoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 3L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 3L);

            // elimino CO1
            this.contrattiMapper.deleteFaseConclusioneContrattoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 4L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 4L);

            // elimino CO2
            //per eliminare la CO2 devo cancellare i record su w9avan , w9conc, w9iniz ma viene già fatto
            this.contrattiMapper.deleteW9FasePcp(codGara, 19L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 19L);

            // elimino CL1
            this.contrattiMapper.deleteFaseCollaudoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 5L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 5L);

            // elimino AC1
            this.contrattiMapper.deleteFaseAccordoBonarioAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 8L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 8L);

            // elimino IR1
            this.contrattiMapper.deleteFaseIstanzaRecessoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 10L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 10L);

            // elimino M1 M1-40 M2 M2-40
            this.contrattiMapper.deleteFaseVariantiAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 7L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 7L);

            // elimino SO1
            this.contrattiMapper.deleteFaseSospensioneAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 6L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 6L);

            // elimino SQ1
            this.contrattiMapper.deleteW9FasePcp(codGara, 14L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 14L);

            // elimino RI1
            this.contrattiMapper.deleteW9FasePcp(codGara, 15L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 15L);

            // elimino RSU1
            this.contrattiMapper.deleteFaseSubappaltoAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 16L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 16L);

            // elimino ES1
            this.contrattiMapper.deleteW9FasePcp(codGara, 17L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 17L);

            // elimino CS1
            this.contrattiMapper.deleteW9FasePcp(codGara, 18L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 18L);

            // elimino S4
            this.contrattiMapper.deleteFaseVariazioneAggiudicatariAll(codGara);
            this.contrattiMapper.deleteW9FasePcp(codGara, 21L);
            this.contrattiMapper.deleteFlussoPcp(codGara, 21L);

            risultato.setEsito(true);
            risultato.setDelete(true);
        } catch (Exception e) {
            logger.error("deleteAppaltoPcp: Errore inaspettato durante la delete dell'appalto PCP: " + codGara, e);
            throw e;
        }
        return risultato;
    }

}
