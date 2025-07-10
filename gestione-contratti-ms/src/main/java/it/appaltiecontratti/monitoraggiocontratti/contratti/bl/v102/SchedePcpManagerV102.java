package it.appaltiecontratti.monitoraggiocontratti.contratti.bl.v102;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.MessageInForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.factory.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.client.api.v102.AusaResourceV1Api;
import io.swagger.client.api.v102.CodeListResourceV1Api;
import io.swagger.client.api.v102.ComunicaAppaltoResourceV1Api;
import io.swagger.client.api.v102.ComunicaPostPubblicazioneResourceV1Api;
import io.swagger.client.api.v102.GestioneUtentiResourceV1Api;
import io.swagger.client.api.v102.ServiziComuniResourceV1Api;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.AbstractManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.DelegheManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.FasiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.WGenChiaviManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.client.api.v102.AuthenticationApi;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.MigrazioneGaraForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.GenericResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.KeycloakTokenResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDettaglioGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseElaborateAppaltoPcp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePubblicaFase;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseRiallineaAnac;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100.BaseResponsePcp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.FasiPcp;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ConsultaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ConsultaGaraBean;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseAppaltoPcp;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseImportaGara;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseInfoDelegati;
import it.appaltiecontratti.pcp.v102.ApiClient;
import it.appaltiecontratti.pcp.v102.codeList.TipologicaItemResponse;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.AppaltoListaResponse;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CigListaResponse;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CigType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.ConsultaAppaltoResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AccordoBonarioType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormAC1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormCL1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormCO1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormCO2Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormCS1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormES1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormI1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormIR1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormM140Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormM1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormM240Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormM2Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormRI1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormRSU1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormS3Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormSA1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormSC1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormSO1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnacFormSQ1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AvanzamentoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AvanzamentoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CPVEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CancellaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CategoriaEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CodIstatEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CollaudoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CollaudoType.EsitoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ConclusioneType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ConclusioneType1;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ConsultaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.CreaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiBaseModificaContrattualeType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiContrattoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiInizioType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiPersonaFisicaType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiPersonaGiuridicaType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DefinizioneType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ElencoIncarichiType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.IncaricoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModalitaPagamentoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModificaContrattuale40Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModificaContrattualeType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModificaContrattualeType1;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModificaContrattualeType2;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModificaSchedaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ModoCollaudoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.MotiviInterruzioneEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.MotiviModificaContrattualeEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.MotiviRevisionePrezziEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.MotivoMancataEsecuzioneSubappaltoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.MotivoMancatoSubappaltoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.MotivoSospensioneEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.OneriEconomiciRisoluzioneRecessoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.PrestazioneType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.QuadroEconomicoConcessioniType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.QuadroEconomicoModificaContrattualeType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.QuadroEconomicoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.RitardoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaAC1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCL1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCO1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCO2Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaCS1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaES1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaI1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaIR1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaListaResponse;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM140Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM240Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaM2Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaPostPubblicazioneType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaRI1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaRSU1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaS3Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSA1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSC1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSO1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaSQ1Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SchedaType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SospensioneType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SospensioneType1;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SospensioneType2;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SubappaltoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SubappaltoType1;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.SubappaltoType2;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.TipoIncaricoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.TipoProgettazioneEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.TipoSoggettoEnum;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.TipologiaComunicazioneEnum;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.AggiungiSoggettoResponse;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.EliminaSoggettoResponse;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.PresaCaricoResponse;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.SoggettoListaResponse;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.SoggettoType;
import it.appaltiecontratti.pcp.v102.npaGateway.CancellaSchedaRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.CreaSchedaRequestDTOMapStringObject;
import it.appaltiecontratti.pcp.v102.npaGateway.CustomUserLoaEnum;
import it.appaltiecontratti.pcp.v102.npaGateway.DatiSchedaRequestDTOMapStringObject;
import it.appaltiecontratti.pcp.v102.npaGateway.EModalitaInvio;
import it.appaltiecontratti.pcp.v102.npaGateway.EliminaSoggettoRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.ErroriEnum;
import it.appaltiecontratti.pcp.v102.npaGateway.GenericSchedaRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.InlineResponse400;
import it.appaltiecontratti.pcp.v102.npaGateway.PresaCaricoRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.SoggettoRequestDTO;
import it.appaltiecontratti.pcp.v102.npaGateway.TipologicaSchemaErroriType;
import it.appaltiecontratti.pcp.v102.npaGateway.UserIdpTypeEnum;
import it.appaltiecontratti.pcp.v102.serviziComuni.AckResponse;
import it.appaltiecontratti.pcp.v102.serviziComuni.EsitoOperazioneListaResponse;
import it.appaltiecontratti.pcp.v102.serviziComuni.EsitoOperazioneType;
import it.appaltiecontratti.pcp.v102.serviziComuni.StatoResponse;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;

@SuppressWarnings("java:S3749")
@Component(value = "schedePcpManagerV102")
public class SchedePcpManagerV102 extends AbstractManager {

	/** Logger di classe. */
	private static final Logger logger = LogManager.getLogger(SchedePcpManagerV102.class);
	
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
	

	public ResponsePubblicaFase pubblicaSchedaPcp(Long codGara, Long codLotto, Long codFase, Long num, Long syscon, String codProfilo, String cf, String loa, String idp, String cfImpersonato) throws Exception {
		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		risultato.setPubblicato(false);				
		String xTipologicaScheda = null;
		
		UserIdpTypeEnum xUserIdpType = null;
		CustomUserLoaEnum xUserLoa = null;			
		String xSAcodiceAUSA = null;	
		String xUserCodiceFiscale = null;
		String xSACodiceFiscale = null;	
		String xUserRole = null;	
		
		try {
			initApi();
			
			
			String schedaJson = null;
			String motivazione = null;
			String idSchedaSchedaPubblicata = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, codFase, num);
			boolean pubblicata = idSchedaSchedaPubblicata != null;
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			
			
			xUserCodiceFiscale = cf;
			xUserRole = null;	
			RupEntry drp = null;
			if(gara.getDrp() != null && gara.getCodiceTecnico() != null && gara.getDrp().equals(gara.getCodiceTecnico())) {
				RupEntry tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
				if(cf.equals(tecnico.getCf())){
					xUserRole = "RP";
				}
			} else {				
				if (gara.getDrp() != null) {
					drp = this.contrattiMapper.getTecnico(gara.getDrp());
					if(cf.equals(drp.getCf())){
						xUserRole = "DRP3";
					}
				} else {
					RupEntry tecnico = null;
					if (gara.getCodiceTecnico() != null) {
						tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
						if(cf.equals(tecnico.getCf())){
							xUserRole = "RP";
						}
					}
				}
			}
						
			if(xUserRole == null) {
				throw new UnauthorizedUserException("Utente non autorizzato");
			}
			
			String codiceSA = gara.getCodiceStazioneAppaltante();
			String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
			String cfSa = this.contrattiMapper.getCFSAByCode(codiceSA);
			
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
			
			
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
			List<LottoBaseEntry> cigAccorpati = this.contrattiMapper.getLottiAccorpati(lotto.getCig());			
			String idContratto = null;
			String idAppalto = gara.getIdAppalto();
			String idScheda = null;
			boolean reinvio = false;	
			Long numAppa = 1L;
			
			//controllo se il cod contratto è valorizzato, altrimenti provo ad estrarlo
			setCodContrattoIfNotExists(codGara, codLotto, num, idAppalto, idScheda, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole,syscon);
								
			String idSchedaWaiting = this.contrattiMapper.checkExistsSchedaWaiting(codGara, codLotto);
			if(StringUtils.isNotBlank(idSchedaWaiting)) {
				List<Long> numList = this.contrattiMapper.getW9fasiNumByIdScheda(idSchedaWaiting, codGara);
				Long codFaseWaiting = this.contrattiMapper.getCodFase(codGara, idSchedaWaiting);
				Long numWaiting = numList != null && !numList.isEmpty() ? numList.get(0) : null;
				GenericResponse<EsitoOperazioneListaResponse> esitoOp = esitoOperazione(null, "cod fase: " + codFaseWaiting, idAppalto, "SC_CONF", "TUTTI_ESITI", null, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
				if(esitoOp != null && esitoOp.getResult() != null && esitoOp.getResult().getListaEsiti() != null && esitoOp.getResult().getListaEsiti().size() > 0) {
					Optional<EsitoOperazioneType> esitoOptional = trovaEsitoConDataControlloPiuVicinaAndEsitoOK(esitoOp.getResult().getListaEsiti(), idSchedaWaiting);
					EsitoOperazioneType esito = null;
					if(esitoOptional.isPresent()) {
						esito = esitoOptional.get();
					} else{
						logger.info("esito OK non trovato, cerco quello generico");
						esitoOptional = trovaEsitoConDataControlloPiuVicina(esitoOp.getResult().getListaEsiti(), idScheda);
						if(esitoOptional.isPresent()) {
							esito = esitoOptional.get();
						}
					}
					if(esito.getIdScheda() != null) {
						if(esito.getIdScheda().toString().equals(idSchedaWaiting)) {								
							if(esito.getEsito().getCodice().equals("OK")){									
								this.contrattiMapper.setW9faseExported(codGara, codLotto, codFaseWaiting, numWaiting);

								Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
								this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
								Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
								this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);
							} else if(esito.getEsito().getCodice().equals("KO")){
								this.contrattiMapper.setW9faseDaExport(codGara, codLotto, codFaseWaiting, numWaiting);
								this.contrattiMapper.deleteFlussoPcpLottoTinvio(codGara,codLotto, codFase, num, 3L);
							} else if(esito.getEsito().getCodice().equals("WT")){
								risultato.setErrorData(BaseResponsePcp.ERROR_SCHEDA_WAITING_PCP);
								return risultato;
							}
						}
					}
											
				} else {
					risultato.setAnacErrors(esitoOp.getAnacErrors());
					risultato.setInternalErrors(esitoOp.getInternalErrors());
					risultato.setValidationErrors(esitoOp.getValidationErrors());
					return risultato;
				}
			}
			switch (codFase.intValue()) {
				case 20:
//					Long invio1 = this.contrattiMapper.getCountFlussi(codGara, codLotto, codFase, 1L);
//					Long invio2 = this.contrattiMapper.getCountFlussi(codGara, codLotto, codFase, 3L);
//					reinvio = invio2 - invio1 == 0L ? true : false;
					List<String> cigs = new ArrayList<String>();
					if(lotto.getMasterCig() != null) {						
						for (LottoBaseEntry lottoBaseEntry : cigAccorpati) {
							cigs.add(lottoBaseEntry.getCig());
						}
					} else {
						cigs.add(lotto.getCig());			
					}
					
					
					SchedaS3Type schedaS3Type = new SchedaS3Type();
					xTipologicaScheda = FasiPcp.S3;
					List<IncarichiProfEntry> s3 = this.contrattiMapper.getIncarichiProfessionali(codGara, codLotto, num);
					for (IncarichiProfEntry incarico : s3) {
						incarico.setTecnico(this.contrattiMapper.getTecnico(incarico.getCodiceTecnico()));
					}
					List<ElencoIncarichiType> s3List = this.incarichiProgessionaliToType(s3);
					if(cigs != null) {
						if(cigs.size() == 1) {
							s3List.get(0).setCig(cigs.get(0));
							AnacFormS3Type afS3 = new AnacFormS3Type();

							afS3.setElencoIncarichi(s3List);
							schedaS3Type.setAnacForm(afS3);
							schedaJson = objectMapper.writeValueAsString(schedaS3Type);
							if(!pubblicata || reinvio) {
								risultato = creaScheda(EModalitaInvio.INVIA, xTipologicaScheda, schedaS3Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
								motivazione = CREA_SCHEDA;															
							} else {								
								risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);				
								motivazione = CONFERMA_SCHEDA;
							}
						} else {
							for (String cig : cigs) {

								s3List.get(0).setCig(cig);
								AnacFormS3Type afS3 = new AnacFormS3Type();

								afS3.setElencoIncarichi(s3List);
								schedaS3Type.setAnacForm(afS3);
								schedaJson = objectMapper.writeValueAsString(schedaS3Type);
								if(!pubblicata) {
									risultato = creaScheda(EModalitaInvio.INVIA, xTipologicaScheda, schedaS3Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
									motivazione = CREA_SCHEDA;
																	
								} else {
									risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
									motivazione = CONFERMA_SCHEDA;
								}

								
							}
						}
					}
					
					
					break;	
				case 13:
					SchedaSC1Type schedaSC1Type = new SchedaSC1Type();
					xTipologicaScheda = FasiPcp.SC1;
					FaseInizialeEsecuzioneEntry sc1 = this.contrattiMapper.getFaseInizialeSottoscrizioneContratto(codGara, codLotto, num);
					DatiContrattoType dct = this.sottoScrizioneContrattoToType(sc1,lotto,codiceAusa);
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, sc1.getNumAppa());
					numAppa = sc1.getNumAppa();
					AnacFormSC1Type afSc1 = new AnacFormSC1Type();				
					afSc1.setDatiContratto(dct);
					schedaSC1Type.setAnacForm(afSc1);
							
					schedaJson = objectMapper.writeValueAsString(schedaSC1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaSC1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CREA_SCHEDA;
						
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}
					
					
					
					break;
				case 2:
					SchedaI1Type schedaI1Type = new SchedaI1Type();
					xTipologicaScheda = FasiPcp.I1;
					FaseInizialeEsecuzioneEntry i1 = this.contrattiMapper.getFaseInizialeEsecuzione(codGara, codLotto, num);
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, i1.getNumAppa());
					numAppa = i1.getNumAppa();
					DatiInizioType dit = this.inizioToType(i1, lotto);
					
					AnacFormI1Type afI1 = new AnacFormI1Type();						
					afI1.setIdContratto(UUID.fromString(idContratto));
					afI1.setDatiInizio(dit);
					schedaI1Type.setAnacForm(afI1);
										
					schedaJson = objectMapper.writeValueAsString(schedaI1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaI1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);				
						motivazione = CONFERMA_SCHEDA;
					}	
					break;						
				case 3:
					SchedaSA1Type schedaSA1Type = new SchedaSA1Type();
					xTipologicaScheda = FasiPcp.SA1;
					FaseAvanzamentoEntry sa1 = this.contrattiMapper.getFaseAvanzamento(codGara, codLotto, num);		
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, sa1.getNumAppa());
					numAppa = sa1.getNumAppa();
					AvanzamentoType at = this.avanzamentoToType(sa1);
					
					AnacFormSA1Type afSa1 = new AnacFormSA1Type();	
					afSa1.setIdContratto(UUID.fromString(idContratto));
					afSa1.setAvanzamento(at);
					schedaSA1Type.setAnacForm(afSa1);
										
					schedaJson = objectMapper.writeValueAsString(schedaSA1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaSA1Type, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;							
				case 4:
					SchedaCO1Type schedaCO1Type = new SchedaCO1Type();
					xTipologicaScheda = FasiPcp.CO1;
					FaseConclusioneEntry co1 = this.contrattiMapper.getFaseConclusioneContratto(codGara, codLotto, num);		
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, co1.getNumAppa());
					numAppa = co1.getNumAppa();
					ConclusioneType c = this.conclusioneToType(co1);
					
					AnacFormCO1Type afCo1 = new AnacFormCO1Type();	
					if(StringUtils.isBlank(idContratto)) {
						afCo1.setCig(lotto.getCig());
					} else {
						afCo1.setIdContratto(UUID.fromString(idContratto));
					}
					
					afCo1.setConclusione(c);
					schedaCO1Type.setAnacForm(afCo1);
										
					schedaJson = objectMapper.writeValueAsString(schedaCO1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaCO1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;
				case 19:
					SchedaCO2Type schedaCO2Type = new SchedaCO2Type();
					xTipologicaScheda = FasiPcp.CO2;
					
					Date dataInizio = this.contrattiMapper.getFaseAffidamentiDirettiIniz(codGara, codLotto, num);
					Double importoCertificato = this.contrattiMapper.getFaseAffidamentiDirettiAvan(codGara, codLotto, num);
					Date dataUltimazione = this.contrattiMapper.getFaseAffidamentiDirettiConc(codGara, codLotto, num);
					
					ConclusioneType1 c1 = this.conclusione1ToType(dataInizio,importoCertificato,dataUltimazione);
					
					AnacFormCO2Type afCo2 = new AnacFormCO2Type();	
					afCo2.setCig(lotto.getCig());
					afCo2.setConclusione(c1);
					schedaCO2Type.setAnacForm(afCo2);
										
					schedaJson = objectMapper.writeValueAsString(schedaCO2Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaCO2Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;
				case 5:
					SchedaCL1Type schedaCL1Type = new SchedaCL1Type();
					xTipologicaScheda = FasiPcp.CL1;
					FaseCollaudoEntry cl = this.contrattiMapper.getFaseCollaudo(codGara, codLotto, num);	
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, cl.getNumAppa());
					numAppa = cl.getNumAppa();
					CollaudoType cl1 = this.collaudoTypeToType(cl);
					
					AnacFormCL1Type afCl1 = new AnacFormCL1Type();
					if(idContratto != null){
						afCl1.setIdContratto(UUID.fromString(idContratto));
					}
					afCl1.setCollaudo(cl1);
					schedaCL1Type.setAnacForm(afCl1);
										
					schedaJson = objectMapper.writeValueAsString(schedaCL1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaCL1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;
				case 7:
					
					boolean sottoSoglia = true;
					boolean concessione = false;
					
					Double importoTotale = 0D;
					if(lotto.getMasterCig() != null) {						
						for (LottoBaseEntry lottoBaseEntry : cigAccorpati) {
							importoTotale = importoTotale + lottoBaseEntry.getImportoNetto();
						}
					} else {
						importoTotale = lotto.getImportoNetto();
					}
					Long tipologia = null;
					if(lotto.getTipologia().equals("L")) {
						tipologia = 1L;
					} else if(lotto.getTipologia().equals("F") || lotto.getTipologia().equals("S")) {
						if (StringUtils.isBlank(gara.getTipoSettore()) || gara.getTipoSettore().equals("O")) {
							tipologia = 2L;
						} else if(StringUtils.isNotBlank(gara.getTipoSettore()) && gara.getTipoSettore().equals("S")) {
							tipologia = 3L;
						}
					}
					Double importoSottoSoglia = 0D;
					String sottoSogliaTabellato = this.contrattiMapper.getValoreTabellato("W9030", tipologia);
					if(sottoSogliaTabellato != null) {
						importoSottoSoglia = Double.valueOf(StringUtils.trim(sottoSogliaTabellato.split("Euro")[0]));
					}
					if(importoTotale > importoSottoSoglia) {
						sottoSoglia = false;
					}
					
					if(gara.getTipoApp() == 3L || gara.getTipoApp() == 4L || gara.getTipoApp() == 20L || gara.getTipoApp() == 21L) {
						concessione = true;
					}
					
					
					FaseVarianteEntry modificaContr = this.contrattiMapper.getFaseVariante(codGara, codLotto, num);
					modificaContr.setMotivazioniVariante(getMotivazioniFaseVariante(codGara, codLotto, num));
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, modificaContr.getNumAppa());
					numAppa = modificaContr.getNumAppa();
					if(sottoSoglia && concessione) {
						xTipologicaScheda = FasiPcp.M2_40;
						SchedaM240Type schedaM240Type = new SchedaM240Type();
							
						ModificaContrattualeType2 m240 = this.modificaContrattualeType2ToType(modificaContr);
						
						AnacFormM240Type afm1 = new AnacFormM240Type();		
						afm1.setIdContratto(UUID.fromString(idContratto));
						afm1.setModificaContrattuale(m240);
						schedaM240Type.setAnacForm(afm1);
											
						schedaJson = objectMapper.writeValueAsString(schedaM240Type);
						if(!pubblicata) {
							risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaM240Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
							motivazione = CREA_SCHEDA;
						} else {
							risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
							motivazione = CONFERMA_SCHEDA;
						}	
											
					} else if(sottoSoglia && !concessione) {
						xTipologicaScheda = FasiPcp.M2;
						SchedaM2Type schedaM2Type = new SchedaM2Type();
							
						ModificaContrattualeType1 m2 = this.modificaContrattualeType1ToType(modificaContr);
						
						AnacFormM2Type afm2 = new AnacFormM2Type();
						
						afm2.setIdContratto(UUID.fromString(idContratto));
						afm2.setModificaContrattuale(m2);
						schedaM2Type.setAnacForm(afm2);						
						schedaJson = objectMapper.writeValueAsString(schedaM2Type);
						if(!pubblicata) {
							risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaM2Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
							motivazione = CREA_SCHEDA;
						} else {
							risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
							motivazione = CONFERMA_SCHEDA;
						}	
											
					} else if(!sottoSoglia && concessione) {
						xTipologicaScheda = FasiPcp.M1_40;
						SchedaM140Type schedaM140Type = new SchedaM140Type();
						
						ModificaContrattuale40Type m140 = this.modificaContrattuale40TypeToType(modificaContr);
						
						AnacFormM140Type afm140 = new AnacFormM140Type();	
						afm140.setIdContratto(UUID.fromString(idContratto));
						afm140.setModificaContrattuale(m140);
						schedaM140Type.setAnacForm(afm140);
						schedaM140Type.setEform(modificaContr.getEform());
						schedaJson = objectMapper.writeValueAsString(schedaM140Type);
						if(!pubblicata) {
							risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaM140Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
							motivazione = CREA_SCHEDA;
						} else {
							risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
							motivazione = CONFERMA_SCHEDA;
						}	
											
					} else if(!sottoSoglia && !concessione) {
						xTipologicaScheda = FasiPcp.M1;
						SchedaM1Type schedaM1Type = new SchedaM1Type();
						
						ModificaContrattualeType m1 = this.modificaContrattualeTypeToType(modificaContr);
						
						AnacFormM1Type afm1 = new AnacFormM1Type();			
						afm1.setIdContratto(UUID.fromString(idContratto));
						afm1.setModificaContrattuale(m1);
						schedaM1Type.setAnacForm(afm1);
						schedaM1Type.setEform(modificaContr.getEform());
						schedaJson = objectMapper.writeValueAsString(schedaM1Type);
						if(!pubblicata) {
							risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaM1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
							motivazione = CREA_SCHEDA;
						} else {
							risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);				
							motivazione = CONFERMA_SCHEDA;
						}	
											
					}
					break;
				case 8:
					
					SchedaAC1Type schedaAC1Type = new SchedaAC1Type();
					xTipologicaScheda = FasiPcp.AC1;
					FaseAccordoBonarioEntry ac = this.contrattiMapper.getFaseAccordoBonario(codGara, codLotto, num);
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, ac.getNumAppa());
					numAppa = ac.getNumAppa();
					AccordoBonarioType ac1 = this.accordoBonarioTypeToType(ac);
					
					AnacFormAC1Type afAc1 = new AnacFormAC1Type();
					afAc1.setIdContratto(UUID.fromString(idContratto));
					afAc1.setAccordoBonario(ac1);
					schedaAC1Type.setAnacForm(afAc1);
										
					schedaJson = objectMapper.writeValueAsString(schedaAC1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaAC1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;					
				case 6:
					SchedaSO1Type schedaSO1Type = new SchedaSO1Type();
					xTipologicaScheda = FasiPcp.SO1;
					FaseSospensioneEntry sosp = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);	
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, sosp.getNumAppa());
					numAppa = sosp.getNumAppa();
					SospensioneType so1 = this.sospensioneTypeToType(sosp);
					
					AnacFormSO1Type afSo1 = new AnacFormSO1Type();		
					afSo1.setIdContratto(UUID.fromString(idContratto));
					afSo1.setSospensione(so1);
					schedaSO1Type.setAnacForm(afSo1);
										
					schedaJson = objectMapper.writeValueAsString(schedaSO1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaSO1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
										
					break;
				case 14:
					SchedaSQ1Type schedaSQ1Type = new SchedaSQ1Type();
					xTipologicaScheda = FasiPcp.SQ1;
					FaseSospensioneEntry sosp1 = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);	
					String ids = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, 6L, num);
					SospensioneType1 sq1 = this.sospensioneType1ToType(sosp1);
					
					AnacFormSQ1Type afSq1 = new AnacFormSQ1Type();
					afSq1.setIdScheda(UUID.fromString(ids));
					afSq1.setSospensione(sq1);
					schedaSQ1Type.setAnacForm(afSq1);
										
					schedaJson = objectMapper.writeValueAsString(schedaSQ1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaSQ1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;
				case 15:					
					SchedaRI1Type schedaRI1Type = new SchedaRI1Type();
					xTipologicaScheda = FasiPcp.RI1;
					FaseSospensioneEntry sosp2 = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);	
					String idScheda2 = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, 6L, num);
					SospensioneType2 ri1 = this.sospensioneType2ToType(sosp2);
					
					AnacFormRI1Type afRi1 = new AnacFormRI1Type();								
					afRi1.setIdScheda(UUID.fromString(idScheda2));
					afRi1.setSospensione(ri1);
					schedaRI1Type.setAnacForm(afRi1);
										
					schedaJson = objectMapper.writeValueAsString(schedaRI1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaRI1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;			
				case 16:
					SchedaRSU1Type schedaRSU1Type = new SchedaRSU1Type();
					xTipologicaScheda = FasiPcp.RSU1;
					FaseSubappaltoEntry richSuba = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, richSuba.getNumAppa());
					numAppa = richSuba.getNumAppa();
					List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, num);
					if (richSuba.getCodImpresaAgg() != null) {
						richSuba.setImpresaAggiudicatrice(this.fasiManager.getImpresa(richSuba.getCodImpresaAgg()));
					}
					if (richSuba.getCodImpresa() != null) {
						richSuba.setImpresaSubappaltatrice(this.fasiManager.getImpresa(richSuba.getCodImpresa()));
					}
					if (richSuba.getIdCpv() != null) {
						richSuba.setDescCpv(this.contrattiMapper.getCpvDesc(richSuba.getIdCpv()));
					}
					if (codiceMandanti != null && codiceMandanti.size() > 0) {
						List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
						for (String cm : codiceMandanti) {
							if (cm != null) {
								me.add(this.fasiManager.getImpresa(cm));
							}
						}
						richSuba.setMandanti(me);
					}
					
					SubappaltoType rsu1 = this.subappaltoTypeToType(richSuba);
					
					AnacFormRSU1Type afRsu1 = new AnacFormRSU1Type();	
					afRsu1.setIdContratto(UUID.fromString(idContratto));
					afRsu1.setSubappalto(rsu1);
					if(richSuba.getDgue() != null){
						schedaRSU1Type.setEspd(richSuba.getDgue());
					}					
					schedaRSU1Type.setAnacForm(afRsu1);
										
					schedaJson = objectMapper.writeValueAsString(schedaRSU1Type);					
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaRSU1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
											
					break;
				case 17:					
					SchedaES1Type schedaES1Type = new SchedaES1Type();
					xTipologicaScheda = FasiPcp.ES1;
					FaseSubappaltoEntry suba2 = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);	
					String idScheda3 = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, 16L, num);
					SubappaltoType1 es1 = this.subappaltoType1ToType(suba2);
					
					AnacFormES1Type afEs1 = new AnacFormES1Type();	
					afEs1.setIdScheda(UUID.fromString(idScheda3));
					afEs1.setSubappalto(es1);
					schedaES1Type.setAnacForm(afEs1);
										
					schedaJson = objectMapper.writeValueAsString(schedaES1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaES1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;	
				case 18:
					
					SchedaCS1Type schedaCS1Type = new SchedaCS1Type();
					xTipologicaScheda = FasiPcp.CS1;
					FaseSubappaltoEntry suba3 = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);	
					String idScheda4 = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, 17L, num);
					SubappaltoType2 cs1 = this.subappaltoType2ToType(suba3);
					
					AnacFormCS1Type afCs1 = new AnacFormCS1Type();
					afCs1.setIdScheda(UUID.fromString(idScheda4));
					afCs1.setSubappalto(cs1);
					schedaCS1Type.setAnacForm(afCs1);
										
					schedaJson = objectMapper.writeValueAsString(schedaCS1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaCS1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);						
						motivazione = CONFERMA_SCHEDA;
					}	 
					break;
				case 10:
					SchedaIR1Type schedaIR1Type = new SchedaIR1Type();
					xTipologicaScheda = FasiPcp.IR1;
					FaseIstanzaRecessoEntry recesso = this.contrattiMapper.getFaseIstanzaRecesso(codGara, codLotto, num);
					idContratto = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, recesso.getNumAppa());
					numAppa = recesso.getNumAppa();
					RitardoType ir1 = this.ritardoTypeToType(recesso);
					
					AnacFormIR1Type afIr1 = new AnacFormIR1Type();
					afIr1.setIdContratto(UUID.fromString(idContratto));
					afIr1.setRitardo(ir1);
					schedaIR1Type.setAnacForm(afIr1);
										
					schedaJson = objectMapper.writeValueAsString(schedaIR1Type);
					if(!pubblicata) {
						risultato = creaScheda( EModalitaInvio.INVIA, xTipologicaScheda, schedaIR1Type,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
						motivazione = CREA_SCHEDA;
					} else {
						risultato = confermaScheda( xTipologicaScheda, idSchedaSchedaPubblicata, idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);					
						motivazione = CONFERMA_SCHEDA;
					}	
					break;
				default:
					break;
			}
		
			
			if(codFase == 13L) {
				if(risultato.getAnacErrors() != null) {
					boolean errorIdPartecipante = risultato.getAnacErrors().stream().anyMatch(s -> s.contains("idPartecipante: is missing"));
					if(errorIdPartecipante) {
						List<String> errors = risultato.getAnacErrors();
						if(errors == null) {
							errors= new ArrayList<>();
						}
						errors.add("idPartecipante non valorizzato, eseguire la funzione di riallineamento ad ANAC");
						risultato.setAnacErrors(errors);
					}
				}
			}
			if(!pubblicata) {
				if(risultato!= null && risultato.getIdScheda() != null) {
					idScheda = risultato.getIdScheda();	
					this.contrattiMapper.setIdSchedaW9fasi(codGara, codLotto, codFase, num, idScheda);						
					allineaPubblicazioneFase(codGara, codLotto, codFase, num, 1L, codiceSA, syscon, motivazione,  schedaJson, risultato.getGovwayMessageId(), risultato.getGovwayTransactionId());				
					risultato = confermaScheda( xTipologicaScheda, idScheda,idAppalto, syscon, codProfilo, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
					motivazione = CONFERMA_SCHEDA;
					elaborateConfermaScheda(codGara, codLotto, codFase, num, syscon, codProfilo, risultato, xTipologicaScheda,
							schedaJson, motivazione, codiceSA, idContratto, idAppalto, idScheda, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, cfImpersonato);
				}
			} else {									
				elaborateConfermaScheda(codGara, codLotto, codFase, num, syscon, codProfilo, risultato, xTipologicaScheda, schedaJson,
						motivazione, codiceSA, idContratto, idAppalto, idSchedaSchedaPubblicata, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, cfImpersonato);
			}
			
			
			
		}catch (Exception e) {
			logger.error("errore nel metodo pubblicaSchedaPcp",e);
			throw e;
		}
		
		return risultato;
	}

	private void setCodContrattoIfNotExists(Long codGara, Long codLotto, Long num, String idAppalto, String idScheda, 
			UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole,Long syscon) throws Exception {
		
		try {
			FaseInizialeEsecuzioneEntry sottoSC1 = this.contrattiMapper.getFaseInizialeSottoscrizioneContratto(codGara, codLotto, num);		
			if(sottoSC1 != null) {
				String idSchedaSC1 = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, 13L, num);
				String idContr = this.contrattiMapper.getIdContrattoW9iniz(codGara, codLotto, sottoSC1.getNumAppa());
				if(StringUtils.isNotBlank(idSchedaSC1) && StringUtils.isBlank(idContr)) {
					GenericResponse<EsitoOperazioneListaResponse> esitoOp = esitoOperazione(null, "SC1", idAppalto, "SC_CONF", "TUTTI_ESITI", null, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
					if(esitoOp != null && esitoOp.getResult() != null && esitoOp.getResult().getListaEsiti() != null && esitoOp.getResult().getListaEsiti().size() > 0) {
						Optional<EsitoOperazioneType> esitoOptional = trovaEsitoConDataControlloPiuVicinaAndEsitoOK(esitoOp.getResult().getListaEsiti(), idSchedaSC1);
						EsitoOperazioneType esito = null;
						if(esitoOptional.isPresent()) {
							esito = esitoOptional.get();
						} else{
							logger.info("esito OK non trovato, cerco quello generico");
							esitoOptional = trovaEsitoConDataControlloPiuVicina(esitoOp.getResult().getListaEsiti(), idScheda);
							if(esitoOptional.isPresent()) {
								esito = esitoOptional.get();
							}
						}							
						if(esito != null && esito.getIdScheda() != null) {
							if(esito.getIdScheda().toString().equals(idSchedaSC1)) {								
								if(esito.getEsito().getCodice().equals("OK")){								
									String idContratto = esito.getIdContratto().toString();
									this.contrattiMapper.setIdContrattoW9iniz(codGara, codLotto, num, idContratto);													
								}
							}
						}																	
					}
				}
			}
		}catch (Exception e) {
			logger.error("errore nel metodo setCodContrattoIfNotExists");
			throw e;
		}
		
	}


	private void elaborateConfermaScheda(Long codGara, Long codLotto, Long codFase, Long num, Long syscon, String codProfilo,
			ResponsePubblicaFase risultato, String xTipologicaScheda, String schedaJson, String motivazione,
			String codiceSA, String idContratto, String idAppalto, String idScheda, 
			UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String cfImpersonato) throws Exception {
		if(risultato!= null && !risultato.isErrors() && risultato.getPubblicato()) {	
			inserimentoFlussoConPcp(codGara, codLotto, codFase, num, 3L,codiceSA, syscon, motivazione,  schedaJson, risultato.getGovwayMessageId(), risultato.getGovwayTransactionId());
			this.contrattiMapper.setW9faseWaiting(codGara, codLotto, codFase, num);
			try {
			    Thread.sleep(2000); // 2000 millisecondi = 2 secondi
			} catch (InterruptedException e) {
			    logger.error("errore durante lo sleep di 2 secondi, metodo: elaborateConfermaScheda codgara: ", codGara);
			}
			GenericResponse<EsitoOperazioneListaResponse> esitoOp = esitoOperazione(null, xTipologicaScheda, idAppalto, "SC_CONF", "TUTTI_ESITI", null, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
			if(esitoOp != null && esitoOp.getResult() != null && esitoOp.getResult().getListaEsiti() != null && esitoOp.getResult().getListaEsiti().size() > 0) {
				Optional<EsitoOperazioneType> esitoOptional = trovaEsitoConDataControlloPiuVicinaAndEsitoOK(esitoOp.getResult().getListaEsiti(), idScheda);
				EsitoOperazioneType esito = null;
				if(esitoOptional.isPresent()) {
					esito = esitoOptional.get();
				} else{
					logger.info("esito OK non trovato, cerco quello generico");
					esitoOptional = trovaEsitoConDataControlloPiuVicina(esitoOp.getResult().getListaEsiti(), idScheda);
					if(esitoOptional.isPresent()) {
						esito = esitoOptional.get();
					}
				}
				
				if(esito != null){
					String logEventiMessage = null;				
					String esitoString = objectMapper.writeValueAsString(esito);
					logEventiMessage = "risultato esitoOperazione" + " " + xTipologicaScheda + " - idAppalto: " + idAppalto + " esito: "+ esitoString;
					if (logEventiMessage.length() > 2000) {
						logEventiMessage = logEventiMessage.substring(0, 2000);
				    }
					Short livento = 1;
					insertLogEventi(syscon, null, LOG_EVENTI_SCHEDA_PCP_COD_EVENTO, logEventiMessage, null, livento, null);				}
				
				
				if(esito != null && esito.getIdScheda() != null) {					
					if(esito.getIdScheda().toString().equals(idScheda)) {								
						if(esito.getEsito().getCodice().equals("OK")){
							if(codFase == 13L && esito.getIdContratto() != null) {
								idContratto = esito.getIdContratto().toString();
								this.contrattiMapper.setIdContrattoW9iniz(codGara, codLotto, num, idContratto);
							}
							this.contrattiMapper.setW9faseExported(codGara, codLotto, codFase, num);

							//VIGILANZA2-488: Al momento del salvataggio in w9reg_invii_pcp,
							// se admin impersona un RUP, allora salvo il nome del rup al posto di admin.
							//In alternativa salvo il syscon dell'utente che sta svolgendo l'operazione.
							String autore;

							if(!StringUtils.isEmpty(cfImpersonato)) {
								GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
								RupEntry tecnico = this.contrattiManager.getRup(gara.getCodiceTecnico());
								autore = tecnico.getNome() + " " + tecnico.getCognome();
							}
							else {
								autore = this.sqlMapper.getNameBySyscon(syscon);
							}

							//Inserimento in w9_reginvii_pcp per registro trasmissioni al cruscotto schede.
							W9RegInviiPcpEntry entry = new W9RegInviiPcpEntry();
							entry.setId(wgcManager.getNextId("W9REG_INVII_PCP"));
							entry.setDataInvio(new Date());
							entry.setAutore(autore);
							entry.setIdScheda(idScheda);
							this.contrattiMapper.insertW9RegInviiPcp(entry);


							Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
							this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
							Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
							this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);

						} else if(esito.getEsito().getCodice().equals("KO")){
							this.contrattiMapper.deleteFlussoPcpLottoTinvio(codGara, codLotto, codFase, num, 3L);
							this.contrattiMapper.setW9faseDaExport(codGara, codLotto, codFase, num);
							risultato.setErrorData(BaseResponse.ERROR_PCP_CONFERMA_ESITO_OP);
						}
					}
				}																	
			}		
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
	
	private List<ElencoIncarichiType> incarichiProgessionaliToType(List<IncarichiProfEntry> s3) {
		List<ElencoIncarichiType> incarichiType = new ArrayList<>();
		
		ElencoIncarichiType incarico = new ElencoIncarichiType();
		
		List<PrestazioneType> prestazioni = new ArrayList<PrestazioneType>();
		List<IncaricoType> incarichi = new ArrayList<IncaricoType>();
		incarico = new ElencoIncarichiType();		
		
		for (IncarichiProfEntry inca : s3) {
			if(inca != null) {				
				if(inca.getIdRuolo() == 1 || inca.getIdRuolo() == 2 || inca.getIdRuolo() == 3 || inca.getIdRuolo() == 4 || inca.getIdRuolo() == 19) {
					DatiPersonaFisicaType personaFisica = new DatiPersonaFisicaType();
					DatiPersonaGiuridicaType personaGiuridica = new DatiPersonaGiuridicaType();
					PrestazioneType p = new PrestazioneType();
					//prestazioni
					
//				if() {
//					ProgettazioneInternaEsternaEnum rogettazioneInternaEsterna = null;
//					p.setProgettazioneInternaEsterna(rogettazioneInternaEsterna);
//				}
					
					if(inca.getIdRuolo() != null) {
						TipoSoggettoEnum tipoSoggetto = new TipoSoggettoEnum();
						tipoSoggetto.setCodice(inca.getIdRuolo().toString());
						tipoSoggetto.setIdTipologica("tipoSoggetto");
						p.setTipoSoggetto(tipoSoggetto);
					}
					
					if(inca.getTipoProgettazione() != null) {
						TipoProgettazioneEnum TipoProgettazione = new TipoProgettazioneEnum();
						TipoProgettazione.setCodice(inca.getTipoProgettazione().toString());
						TipoProgettazione.setIdTipologica("tipoProgettazione");
						p.setTipoProgettazione(TipoProgettazione);
					}
					
					if(inca.getCigProgEsterna() != null) {
						p.setCig(inca.getCigProgEsterna());
					}
					
					if(inca.getDataAffProgEsterna() != null) {
						p.setDataAffidamentoIncarico(dateToOffsetDateTime(inca.getDataAffProgEsterna()));
					}
					
					if(inca.getDataConsProgEsterna() != null) {
						p.setDataConsegna(dateToOffsetDateTime(inca.getDataConsProgEsterna()));
					}
					
					if(inca.getTecnico() != null && inca.getTecnico().getCf() != null && inca.getTecnico().getCf().length() == 16) {
						//persona fisica
						personaFisica = mapPersonaFisica(inca.getTecnico());
						
						p.setDatiPersonaFisica(personaFisica);
					} else if(inca.getTecnico() != null && inca.getTecnico().getCf() != null && inca.getTecnico().getCf().length() == 11) {
						//persona giuridica
						List<DatiPersonaGiuridicaType> personaGiuridicaList = new ArrayList<DatiPersonaGiuridicaType>();
						personaGiuridica = mapPersonaGiuridica(inca.getTecnico());									
						personaGiuridicaList.add(personaGiuridica);
						p.setDatiPersonaGiuridica(personaGiuridicaList);
					}
					prestazioni.add(p);
				} else if(inca.getIdRuolo() == 5 || inca.getIdRuolo() == 6 || inca.getIdRuolo() == 7 || inca.getIdRuolo() == 8 || inca.getIdRuolo() == 10 || inca.getIdRuolo() == 13 || inca.getIdRuolo() == 19) {
					DatiPersonaFisicaType personaFisica = new DatiPersonaFisicaType();
					DatiPersonaGiuridicaType personaGiuridica = new DatiPersonaGiuridicaType();
					IncaricoType i = new IncaricoType();
					//incarichi
					
					if(inca.getIdRuolo() != null) {
						TipoIncaricoEnum tipoIncarico = new TipoIncaricoEnum();
						tipoIncarico.setCodice(inca.getIdRuolo().toString());
						tipoIncarico.setIdTipologica("tipoIncarico");
						i.setTipoIncarico(tipoIncarico);
					}
					
					if(inca.getTecnico() != null) {
						if(inca.getTecnico().getCf() != null && inca.getTecnico().getCf().length() == 16) {
							//persona fisica
							personaFisica = mapPersonaFisica(inca.getTecnico());
							
							i.setDatiPersonaFisica(personaFisica);
						} else if(inca.getTecnico().getCf() != null && inca.getTecnico().getCf().length() == 11) {
							//persona giuridica
							List<DatiPersonaGiuridicaType> personaGiuridicaList = new ArrayList<DatiPersonaGiuridicaType>();
							personaGiuridica = mapPersonaGiuridica(inca.getTecnico());
							
							
							personaGiuridicaList.add(personaGiuridica);
							i.setDatiPersonaGiuridica(personaGiuridicaList);
						}
					}
					
					
					incarichi.add(i);
				}
			}
		
		
		}
		incarico.setIncarichi(incarichi);
		incarico.setPrestazioni(prestazioni);
	
		incarichiType.add(incarico);
		
		return incarichiType;
	}
	
	private DatiPersonaFisicaType mapPersonaFisica(RupEntry tecnico) {
		DatiPersonaFisicaType personaFisica = new DatiPersonaFisicaType();
		
		if(tecnico.getCf() != null) {
			personaFisica.setCodiceFiscale(tecnico.getCf());
		}
		
		if(tecnico.getCognome() != null) {
			personaFisica.setCognome(tecnico.getCognome());	
		}
		
		if(tecnico.getNome() != null) {
			personaFisica.setNome(tecnico.getNome());
		}
		
		if(tecnico.getTelefono() != null) {
			personaFisica.setTelefono(tecnico.getTelefono());
		}
		
		if(tecnico.getFax() != null) {
			personaFisica.setFax(tecnico.getFax());
		}
		
		if(tecnico.getEmail() != null) {
			personaFisica.setEmail(tecnico.getEmail());
		}
		
		if(tecnico.getIndirizzo() != null) {
			personaFisica.setIndirizzo(tecnico.getIndirizzo());
		}
		
		if(tecnico.getCap() != null) {
			personaFisica.setCap(tecnico.getCap());
		}
		
		if(tecnico.getCodIstat() != null) {
			CodIstatEnum codIstat = new CodIstatEnum();
			codIstat.setCodice(tecnico.getCodIstat());
			codIstat.setIdTipologica("codIstat");
			personaFisica.setCodIstat(codIstat);
		}
				
		personaFisica.setIncaricatoEstero(false);
		
		return personaFisica;
	}
	
	private DatiPersonaGiuridicaType mapPersonaGiuridica(RupEntry tecnico) {
		DatiPersonaGiuridicaType personaGiuridica = new DatiPersonaGiuridicaType();
		
		if(tecnico.getCf() != null) {
			personaGiuridica.setCodiceFiscale(tecnico.getCf());
		}
		
		if(tecnico.getNominativo() != null) {
			personaGiuridica.setDenominazione(tecnico.getNominativo());
		}
		
		//campi non popolati
		/*
		RuoloOEEnum ruoloOE = new RuoloOEEnum();
		personaGiuridica.setRuoloOE(ruoloOE);
				
		TipoOEEnum tipoOE = new TipoOEEnum();
		personaGiuridica.setTipoOE(tipoOE);
				
		personaGiuridica.setIdGruppo();
		*/
		
		return personaGiuridica;
	}


	private ModificaContrattualeType modificaContrattualeTypeToType(FaseVarianteEntry modificaContr) {
		ModificaContrattualeType m1 = new ModificaContrattualeType();
		
		if(modificaContr.getDataVerbaleAppr() != null) {
			m1.setDataApprovazione(dateToOffsetDateTime(modificaContr.getDataVerbaleAppr()));
		}
		
		if(modificaContr.getUrlVariantiCo() != null) {
			m1.setUrlDocumentazione(modificaContr.getUrlVariantiCo());
		}
		
		if(modificaContr.getMotivoRevPrezzi() != null) {
			MotiviRevisionePrezziEnum motiviRevisionePrezzi = new MotiviRevisionePrezziEnum();
			motiviRevisionePrezzi.setCodice(modificaContr.getMotivoRevPrezzi().toString());
			motiviRevisionePrezzi.setIdTipologica("motivoRevisionePrezzi");
			m1.setMotivoRevisionePrezzi(motiviRevisionePrezzi);
		}
		
		if(modificaContr.getCigNuovaProc() != null) {
			m1.setCigNuovaProcedura(modificaContr.getCigNuovaProc());
		}
		
		QuadroEconomicoModificaContrattualeType qemc = new QuadroEconomicoModificaContrattualeType();
		
		if(modificaContr.getImportoRideterminatoLavori() != null) {
			qemc.setImpLavori(modificaContr.getImportoRideterminatoLavori());
		}
		
		if(modificaContr.getImportoRideterminatoServizi() != null) {
			qemc.setImpServizi(modificaContr.getImportoRideterminatoServizi());
		}
		
		if(modificaContr.getImportoRideterminatoForniture() != null) {
			qemc.setImpForniture(modificaContr.getImportoRideterminatoForniture());
		}
		
		if(modificaContr.getImportoSicurezza() != null) {
			qemc.setImpTotaleSicurezza(modificaContr.getImportoSicurezza());
		}
		
		if(modificaContr.getImportoProgettazione() != null) {
			qemc.setImpProgettazione(modificaContr.getImportoProgettazione());
		}
		
		if(modificaContr.getImportoNonAssog() != null) {
			qemc.setUlterioriSommeNoRibasso(modificaContr.getImportoNonAssog());
		}
		
		if(modificaContr.getImportoDisposizione() != null) {
			qemc.setSommeADisposizione(modificaContr.getImportoDisposizione());
		}
		
		if(modificaContr.getNumGiorniProroga() != null) {
			qemc.setNumGiorniProroga(new BigDecimal(modificaContr.getNumGiorniProroga()));
		}
				
		m1.setQuadroEconomicoStandardRideterminato(qemc);
		
		return m1;
	}


	private ModificaContrattuale40Type modificaContrattuale40TypeToType(FaseVarianteEntry modificaContr) {
		ModificaContrattuale40Type m140 = new ModificaContrattuale40Type();
		
		if(modificaContr.getDataVerbaleAppr() != null) {
			m140.setDataApprovazione(dateToOffsetDateTime(modificaContr.getDataVerbaleAppr()));
		}
		
		if(modificaContr.getUrlVariantiCo() != null) {
			m140.setUrlDocumentazione(modificaContr.getUrlVariantiCo());
		}
		
		if(modificaContr.getMotivoRevPrezzi() != null) {
			MotiviRevisionePrezziEnum motiviRevisionePrezzi = new MotiviRevisionePrezziEnum();
			motiviRevisionePrezzi.setCodice(modificaContr.getMotivoRevPrezzi().toString());
			motiviRevisionePrezzi.setIdTipologica("motivoRevisionePrezzi");
			m140.setMotivoRevisionePrezzi(motiviRevisionePrezzi);
		}
		
		if(modificaContr.getCigNuovaProc() != null) {
			m140.setCigNuovaProcedura(modificaContr.getCigNuovaProc());
		}
		
		QuadroEconomicoConcessioniType qec = new QuadroEconomicoConcessioniType();
		
		if(modificaContr.getImportoRideterminatoLavori() != null) {
			qec.setImpLavori(modificaContr.getImportoRideterminatoLavori());
		}
		
		if(modificaContr.getImportoRideterminatoServizi() != null) {
			qec.setImpServizi(modificaContr.getImportoRideterminatoServizi());
		}
		
		if(modificaContr.getImportoRideterminatoForniture() != null) {
			qec.setImpForniture(modificaContr.getImportoRideterminatoForniture());
		}
		
		if(modificaContr.getImpFinpa() != null) {
			qec.setFinanziamentiCanoniPA(modificaContr.getImpFinpa());
		}
				
		if(modificaContr.getEntrUtenza() != null) {
			qec.setEntrateUtenza(modificaContr.getEntrUtenza());
		}
		
		if(modificaContr.getIntrAttivo() != null) {
			qec.setIntroitoAttivo(modificaContr.getIntrAttivo());
		}
				
		if(modificaContr.getImportoSicurezza() != null) {
			qec.setImpTotaleSicurezza(modificaContr.getImportoSicurezza());
		}		
		
		if(modificaContr.getImportoNonAssog() != null) {
			qec.setUlterioriSommeNoRibasso(modificaContr.getImportoNonAssog());
		}
		
		if(modificaContr.getImpOpzioni() != null) {
			qec.setSommeOpzioniRinnovi(modificaContr.getImpOpzioni());
		}
		
		if(modificaContr.getImportoDisposizione() != null) {
			qec.setSommeADisposizione(modificaContr.getImportoDisposizione());
		}
		
		
				
		m140.setQuadroEconomicoConcessioniRideterminato(qec);
		
		return m140;
	}


	private ModificaContrattualeType1 modificaContrattualeType1ToType(FaseVarianteEntry modificaContr) {
		ModificaContrattualeType1 m2 = new ModificaContrattualeType1();
		
		if(modificaContr.getDataVerbaleAppr() != null) {
			m2.setDataApprovazione(dateToOffsetDateTime(modificaContr.getDataVerbaleAppr()));
		}
		
		if(modificaContr.getUrlVariantiCo() != null) {
			m2.setUrlDocumentazione(modificaContr.getUrlVariantiCo());
		}
		
		if(modificaContr.getMotivoRevPrezzi() != null) {
			MotiviRevisionePrezziEnum motiviRevisionePrezzi = new MotiviRevisionePrezziEnum();
			motiviRevisionePrezzi.setCodice(modificaContr.getMotivoRevPrezzi().toString());
			motiviRevisionePrezzi.setIdTipologica("motivoRevisionePrezzi");
			m2.setMotivoRevisionePrezzi(motiviRevisionePrezzi);
		}
		
		if(modificaContr.getCigNuovaProc() != null) {
			m2.setCigNuovaProcedura(modificaContr.getCigNuovaProc());
		}
		
		QuadroEconomicoModificaContrattualeType qemc = new QuadroEconomicoModificaContrattualeType();
		
		if(modificaContr.getImportoRideterminatoLavori() != null) {
			qemc.setImpLavori(modificaContr.getImportoRideterminatoLavori());
		}
		
		if(modificaContr.getImportoRideterminatoServizi() != null) {
			qemc.setImpServizi(modificaContr.getImportoRideterminatoServizi());
		}
		
		if(modificaContr.getImportoRideterminatoForniture() != null) {
			qemc.setImpForniture(modificaContr.getImportoRideterminatoForniture());
		}
		
		if(modificaContr.getImportoSicurezza() != null) {
			qemc.setImpTotaleSicurezza(modificaContr.getImportoSicurezza());
		}
		
		if(modificaContr.getImportoProgettazione() != null) {
			qemc.setImpProgettazione(modificaContr.getImportoProgettazione());
		}
		
		if(modificaContr.getImportoNonAssog() != null) {
			qemc.setUlterioriSommeNoRibasso(modificaContr.getImportoNonAssog());
		}
		
		if(modificaContr.getImportoDisposizione() != null) {
			qemc.setSommeADisposizione(modificaContr.getImportoDisposizione());
		}
		
		if(modificaContr.getNumGiorniProroga() != null) {
			qemc.setNumGiorniProroga(new BigDecimal(modificaContr.getNumGiorniProroga()));
		}
				
		m2.setQuadroEconomicoStandardRideterminato(qemc);
		
		DatiBaseModificaContrattualeType dbmc = new DatiBaseModificaContrattualeType();
		
		if(modificaContr.getDataAttoAggiuntivo() != null) {
			dbmc.setDataSottoscrizione(dateToOffsetDateTime(modificaContr.getDataAttoAggiuntivo()));
		}
		
		if(modificaContr.getMotivazioniVariante() != null && modificaContr.getMotivazioniVariante().size() > 0) {
			for (LottoDynamicValue value : modificaContr.getMotivazioniVariante()) {
				if(value.getValue() != null && value.getValue() == 1L) {
					MotiviModificaContrattualeEnum motiviModificaContrattuale = new MotiviModificaContrattualeEnum();
					motiviModificaContrattuale.setCodice(motiviModificaMap.get(value.getCodice().toString()));
					motiviModificaContrattuale.setIdTipologica("motiviModifica");
					dbmc.setMotiviModifica(motiviModificaContrattuale);
				}
			}			
		}
		
		if(modificaContr.getAltreMotivazioni() != null) {
			dbmc.setCausaModifica(modificaContr.getAltreMotivazioni());
		}
		
		m2.setDatiBaseModificaContrattuale(dbmc);
		
		return m2;
	}


	private ModificaContrattualeType2 modificaContrattualeType2ToType(FaseVarianteEntry modificaContr) {
		ModificaContrattualeType2 m240 = new ModificaContrattualeType2();
		
		if(modificaContr.getDataVerbaleAppr() != null) {
			m240.setDataApprovazione(dateToOffsetDateTime(modificaContr.getDataVerbaleAppr()));
		}
		
		if(modificaContr.getUrlVariantiCo() != null) {
			m240.setUrlDocumentazione(modificaContr.getUrlVariantiCo());
		}
		
		if(modificaContr.getMotivoRevPrezzi() != null) {
			MotiviRevisionePrezziEnum motiviRevisionePrezzi = new MotiviRevisionePrezziEnum();
			motiviRevisionePrezzi.setCodice(modificaContr.getMotivoRevPrezzi().toString());
			motiviRevisionePrezzi.setIdTipologica("motiviRevisionePrezzi");
			m240.setMotivoRevisionePrezzi(motiviRevisionePrezzi);
		}
		
		if(modificaContr.getCigNuovaProc() != null) {
			m240.setCigNuovaProcedura(modificaContr.getCigNuovaProc());
		}
		
		QuadroEconomicoConcessioniType qec = new QuadroEconomicoConcessioniType();
		
		if(modificaContr.getImportoRideterminatoLavori() != null) {
			qec.setImpLavori(modificaContr.getImportoRideterminatoLavori());
		}
		
		if(modificaContr.getImportoRideterminatoServizi() != null) {
			qec.setImpServizi(modificaContr.getImportoRideterminatoServizi());
		}
		
		if(modificaContr.getImportoRideterminatoForniture() != null) {
			qec.setImpForniture(modificaContr.getImportoRideterminatoForniture());
		}
		
		if(modificaContr.getImpFinpa() != null) {
			qec.setFinanziamentiCanoniPA(modificaContr.getImpFinpa());
		}
				
		if(modificaContr.getEntrUtenza() != null) {
			qec.setEntrateUtenza(modificaContr.getEntrUtenza());
		}
		
		if(modificaContr.getIntrAttivo() != null) {
			qec.setIntroitoAttivo(modificaContr.getIntrAttivo());
		}
				
		if(modificaContr.getImportoSicurezza() != null) {
			qec.setImpTotaleSicurezza(modificaContr.getImportoSicurezza());
		}		
		
		if(modificaContr.getImportoNonAssog() != null) {
			qec.setUlterioriSommeNoRibasso(modificaContr.getImportoNonAssog());
		}
		
		if(modificaContr.getImpOpzioni() != null) {
			qec.setSommeOpzioniRinnovi(modificaContr.getImpOpzioni());
		}
		
		if(modificaContr.getImportoDisposizione() != null) {
			qec.setSommeADisposizione(modificaContr.getImportoDisposizione());
		}
				
		m240.setQuadroEconomicoConcessioniRideterminato(qec);
		
		DatiBaseModificaContrattualeType dbmc = new DatiBaseModificaContrattualeType();
		
		if(modificaContr.getDataAttoAggiuntivo() != null) {
			dbmc.setDataSottoscrizione(dateToOffsetDateTime(modificaContr.getDataAttoAggiuntivo()));
		}
		
		if(modificaContr.getMotivazioniVariante() != null && modificaContr.getMotivazioniVariante().size() > 0) {
			for (LottoDynamicValue value : modificaContr.getMotivazioniVariante()) {
				if(value.getValue() != null && value.getValue() == 1L) {
					MotiviModificaContrattualeEnum motiviModificaContrattuale = new MotiviModificaContrattualeEnum();
					motiviModificaContrattuale.setCodice(motiviModificaMap.get(value.getCodice().toString()));
					motiviModificaContrattuale.setIdTipologica("motiviModifica");
					dbmc.setMotiviModifica(motiviModificaContrattuale);
				}
			}			
		}
		
		if(modificaContr.getAltreMotivazioni() != null) {
			dbmc.setCausaModifica(modificaContr.getAltreMotivazioni());
		}
		
		m240.setDatiBaseModificaContrattuale(dbmc);
		
		return m240;
	}


	private RitardoType ritardoTypeToType(FaseIstanzaRecessoEntry recesso) {
		RitardoType ritardo = new RitardoType();
		
		if(recesso.getDataTermine() != null) {
			ritardo.setDataTermine(dateToOffsetDateTime(recesso.getDataTermine()));
		}
		
		if(recesso.getTipoComunicazione() != null) {
			TipologiaComunicazioneEnum tipologiaComunicazione = new TipologiaComunicazioneEnum();
			tipologiaComunicazione.setCodice(recesso.getTipoComunicazione());
			tipologiaComunicazione.setIdTipologica("tipoComunicazione");
			ritardo.setTipoComunicazione(tipologiaComunicazione);
		}		
		
		if(recesso.getDurataSospensione() != null) {
			ritardo.setDurataSospensione(new BigDecimal(recesso.getDurataSospensione()));
		}
				
		if(recesso.getMotivoSospensione() != null) {
			ritardo.setMotivoSospensione(recesso.getMotivoSospensione());
		}
				
		if(recesso.getDataIstanzaRecesso() != null) {
			ritardo.setDataIstanzaRecesso(dateToOffsetDateTime(recesso.getDataIstanzaRecesso()));
		}
				
		if(recesso.getFlagAccolta() != null) {
			ritardo.setIstanzaAccolta(recesso.getFlagAccolta().equals("1") ? true : false);
		}
				
		if(recesso.getFlagTardiva() != null) {
			ritardo.setIstanzaTardiva(recesso.getFlagTardiva().equals("1") ? true : false);
		}
				
		if(recesso.getFlagRipresa() != null) {
			ritardo.setIstanzaRipresa(recesso.getFlagRipresa().equals("1") ? true : false);
		}
				
		if(recesso.getFlagRiserva() != null) {
			ritardo.setIstanzaRiserva(recesso.getFlagRiserva().equals("1") ? true : false);
		}
				
		if(recesso.getImportoSpese() != null) {
			ritardo.setImpSpese(recesso.getImportoSpese());
		}		
		
		if(recesso.getImportoOneri() != null) {
			ritardo.setImpOneri(recesso.getImportoOneri());
		}
		
		
		return ritardo;
	}


	private SubappaltoType2 subappaltoType2ToType(FaseSubappaltoEntry conclSuba) {
		SubappaltoType2 suba2  = new SubappaltoType2();
		
		if(conclSuba.getImportoEffettivo() != null) {
			suba2.setImportoEffettivo(conclSuba.getImportoEffettivo());
		}
		
		if(conclSuba.getDataUltimazione() != null) {
			suba2.setDataUltimazione(dateToOffsetDateTime(conclSuba.getDataUltimazione()));
		}
		
		if(conclSuba.getMotivoMancataEsec() != null) {
			MotivoMancataEsecuzioneSubappaltoEnum motivoMancataEsecuzioneSubappalto = new MotivoMancataEsecuzioneSubappaltoEnum();
			motivoMancataEsecuzioneSubappalto.setCodice(conclSuba.getMotivoMancataEsec().toString());
			motivoMancataEsecuzioneSubappalto.setIdTipologica("motivoMancatoSubappalto");
			suba2.setMotivoMancataEsecuzioneSubappalto(motivoMancataEsecuzioneSubappalto);
		}
		
		return suba2;
	}


	private SubappaltoType1 subappaltoType1ToType(FaseSubappaltoEntry esitoSuba) {
		SubappaltoType1 suba1  = new SubappaltoType1();
		
		if(esitoSuba.getDataAuth() != null) {
			suba1.setDataAutorizzazione(dateToOffsetDateTime(esitoSuba.getDataAuth()));
		}
		
		if(esitoSuba.getMotivoMancatoSub() != null) {
			MotivoMancatoSubappaltoEnum motivoMancatoSubappalto = new MotivoMancatoSubappaltoEnum();
			motivoMancatoSubappalto.setCodice(esitoSuba.getMotivoMancatoSub().toString());
			motivoMancatoSubappalto.setIdTipologica("motivoMancatoSubappalto");
			suba1.setMotivoMancatoSubappalto(motivoMancatoSubappalto);
		}
		
		return suba1;
	}


	private SubappaltoType subappaltoTypeToType(FaseSubappaltoEntry richSuba) {		
		SubappaltoType suba  = new SubappaltoType();
					
		if(richSuba.getImpresaSubappaltatrice() != null && richSuba.getImpresaSubappaltatrice().getCodiceFiscale() != null) {
			List<String> cfOe = new ArrayList<String>();
			cfOe.add(richSuba.getImpresaSubappaltatrice().getCodiceFiscale());
			
			
			if(richSuba.getMandanti() != null && richSuba.getMandanti().size() > 0) {
				for (ImpresaEntry man : richSuba.getMandanti()) {
					if(man != null && man.getCodiceFiscale() != null) {
						cfOe.add(man.getCodiceFiscale());
					}
				}
			}
				
			suba.setCodiciFiscaliOE(cfOe);
			
		}
		
		if(richSuba.getImpresaSubappaltatrice() != null) {
			suba.setImpresaEstera(richSuba.getImpresaSubappaltatrice().getNazione() == null || richSuba.getImpresaSubappaltatrice().getNazione() == 1L ? false : true);
		}
		
		if(richSuba.getImpresaAggiudicatrice() != null && richSuba.getImpresaAggiudicatrice().getCodiceFiscale() != null ) {
			suba.setCodiceFiscaleAggiudicatario(richSuba.getImpresaAggiudicatrice().getCodiceFiscale());
		}
			
		if(richSuba.getOggetto() != null) {
			suba.setOggetto(richSuba.getOggetto());
		}
		
		if(richSuba.getImportoPresunto() != null) {
			suba.setImportoPresunto(richSuba.getImportoPresunto());
		}
					
		if(richSuba.getIdCategoria() != null) {
			CategoriaEnum categoria = new CategoriaEnum();
			String categoriaAnac = this.contrattiMapper.getIdCategoriaByDatabaseCategoria(richSuba.getIdCategoria());
			categoria.setCodice(categoriaAnac);
			categoria.setIdTipologica("categoria");
			suba.setCategoria(categoria);
		}
				
		if(richSuba.getIdCpv() != null) {
			CPVEnum cpv = new CPVEnum();
			List<CPVEnum> cpvs = new ArrayList<CPVEnum>();
			if(richSuba.getIdCpv().contains("-")) {
				String[] cpvSplit = richSuba.getIdCpv().split("-");
				if(cpvSplit!= null && cpvSplit[0] != null) {
					cpv.setCodice(cpvSplit[0]);
					cpv.setIdTipologica("CPV");
					cpvs.add(cpv);
					suba.setCpv(cpvs);
				}	
			} else {
				cpv.setCodice(richSuba.getIdCpv());
				cpv.setIdTipologica("CPV");
				cpvs.add(cpv);
				suba.setCpv(cpvs);
			}				
		}
											
		return suba; 
	}


	private SospensioneType2 sospensioneType2ToType(FaseSospensioneEntry sosp2) {
		SospensioneType2 so2 = new SospensioneType2();
		
		if(sosp2.getDataVerbRipr() != null) {
			so2.setDataVerbaleRipresa(dateToOffsetDateTime(sosp2.getDataVerbRipr()));
		}
		
		if(sosp2.getIncCrono() != null) {
			so2.setIncidenzaCronoprogramma(sosp2.getIncCrono());
		}
		
		if(sosp2.getFlagSuperoTempo() != null) {
			so2.setSuperatoTempo(sosp2.getFlagSuperoTempo().equals("1") ? true : false);
		}
		
		if(sosp2.getFlagRiserve() != null) {
			so2.setRiserve(sosp2.getFlagRiserve().equals("1") ? true : false);
		}
		
		if(sosp2.getFlagVerbale() != null) {
			so2.setVerbaleNonSottoscritto(sosp2.getFlagVerbale().equals("1") ? true : false);
		}
						
		return so2;
	}


	private SospensioneType1 sospensioneType1ToType(FaseSospensioneEntry sosp) {
		SospensioneType1 so1 = new SospensioneType1();
		
		if(sosp.getDataSuperQuarto() != null) {
			so1.setDataSuperamento(dateToOffsetDateTime(sosp.getDataSuperQuarto()));
		}
		
		return so1;
	}


	private SospensioneType sospensioneTypeToType(FaseSospensioneEntry sosp) {
		SospensioneType so1 = new SospensioneType();
		
		if(sosp.getSospParziale() != null) {
			so1.setSospensioneParziale(sosp.getSospParziale().equals("1") ? true : false);
		}
				
		if(sosp.getDataVerbSosp() != null) {
			so1.setDataVerbaleSospensione(dateToOffsetDateTime(sosp.getDataVerbSosp()));
		}
				
		if(sosp.getMotivoSosp() != null) {
			MotivoSospensioneEnum motivoSospensione = new MotivoSospensioneEnum();
			motivoSospensione.setCodice(sosp.getMotivoSosp().toString());
			motivoSospensione.setIdTipologica("motivoSospensione");
			so1.setMotivoSospensione(motivoSospensione);
		}
				
		return so1;
	}


	private AccordoBonarioType accordoBonarioTypeToType(FaseAccordoBonarioEntry ac) {
		AccordoBonarioType acT = new AccordoBonarioType();
		
		if(ac.getDataAccordo() != null) {
			acT.setDataAccordo(dateToOffsetDateTime(ac.getDataAccordo()));	
		}	
		
		if(ac.getOneriDerivanti() != null) {
			acT.setOneriDerivanti(ac.getOneriDerivanti());	
		}
		

		if(ac.getNumRiserve() != null) {
			acT.setNumeroRiserve(new BigDecimal(ac.getNumRiserve()));	
		}
					
		return acT;
	}


	private CollaudoType collaudoTypeToType(FaseCollaudoEntry cl) {
		CollaudoType cl1 = new CollaudoType();
		
		if(cl.getDataCollaudoStatico() != null) {
			cl1.setDataCollaudo(dateToOffsetDateTime(cl.getDataCollaudoStatico()));
		}		
				
		if(cl.getDataCertEsecuzione() != null) {
			cl1.setDataCertificato(dateToOffsetDateTime(cl.getDataCertEsecuzione()));
		}
				
		if(cl.getModalitaCollaudo() != null) {
			ModoCollaudoEnum modoCollaudo = new ModoCollaudoEnum();
			modoCollaudo.setCodice(cl.getModalitaCollaudo().toString());
			modoCollaudo.setIdTipologica("modoCollaudo");
			cl1.setModoCollaudo(modoCollaudo);	
		}
				
		if(cl.getDataNomina() != null) {
			cl1.setDataNomina(dateToOffsetDateTime(cl.getDataNomina()));
		}
				
		if(cl.getDataInizioOperazioni() != null) {
			cl1.setDataInizio(dateToOffsetDateTime(cl.getDataInizioOperazioni()));
		}
				
		if(cl.getDataRedazioneCertificato() != null) {
			cl1.setDataRedazioneCertificato(dateToOffsetDateTime(cl.getDataRedazioneCertificato()));
		}
				
		if(cl.getDataDelibera() != null) {
			cl1.setDataDeliberaAmmissibilita(dateToOffsetDateTime(cl.getDataDelibera()));
		}
				
		if(cl.getEsitoCollaudo() != null) {
			EsitoEnum esito = cl.getEsitoCollaudo().equals("P") ? EsitoEnum.POSITIVO : EsitoEnum.NEGATIVO;			
			cl1.setEsito(esito);
		}
	
		QuadroEconomicoType qeType = new QuadroEconomicoType();
		
		if(cl.getImportoFinaleLavori() != null) {
			qeType.setImpLavori(cl.getImportoFinaleLavori());
		}
		
		if(cl.getImportoFinaleServizi() != null) {
			qeType.setImpServizi(cl.getImportoFinaleServizi());
		}
		
		if(cl.getImportoFinaleForniture() != null) {
			qeType.setImpForniture(cl.getImportoFinaleForniture());
		}
		
		if(cl.getImportoFinaleSicurezza() != null) {
			qeType.setImpTotaleSicurezza(cl.getImportoFinaleSicurezza());
		}
		
		if(cl.getImpNonAssog() != null) {
			qeType.setUlterioriSommeNoRibasso(cl.getImpNonAssog());
		}
		
		if(cl.getImportoProgettazione() != null) {
			qeType.setImpProgettazione(cl.getImportoProgettazione());
		}
		
		if(cl.getImpFinaleOpzioni() != null) {
			qeType.setSommeOpzioniRinnovi(cl.getImpFinaleOpzioni());
		}
		
		if(cl.getImpFinaleRipetizioni() != null) {
			qeType.setSommeRipetizioni(cl.getImpFinaleRipetizioni());
		}
		
		if(cl.getImportoDisposizione() != null) {
			qeType.setSommeADisposizione(cl.getImportoDisposizione());
		}
					
		cl1.setQuadroEconomicoStandard(qeType);	
					
		if(cl.getNumRiserve() != null) {
			cl1.setNumeroTotaleRiserve(new BigDecimal(cl.getNumRiserve()));
		}
				
		if(cl.getOneriDerivanti() != null) {
			cl1.setOneri(cl.getOneriDerivanti());
		}
				
		DefinizioneType dType = new DefinizioneType();
		
		if(cl.getNumeroRiserveDaDefinireAmm() != null && cl.getNumeroRiserveDaDefinireAmm()  > 0 && cl.getNumeroRiserveDaDefinireAmm() != null && cl.getNumeroRiserveDaDefinireAmm() > 0) {
			dType.setUlterioriRiserve(true);
		} else {
			dType.setUlterioriRiserve(false);
		}
		
		if(cl.getNumeroRiserveDefiniteAmm() != null) {
			dType.setNumeroRiserveDefinite(new BigDecimal(cl.getNumeroRiserveDefiniteAmm()));
		}
		
		if(cl.getNumeroRiserveDaDefinireAmm() != null) {
			dType.setNumeroRiserveDaDefinire(new BigDecimal(cl.getNumeroRiserveDaDefinireAmm()));
		}

		if(cl.getImportoTotaleRichiestoAmm() != null) {
			dType.setImpTotaleRichiesto(cl.getImportoTotaleRichiestoAmm());
		}

		List<DefinizioneType> modDef = new ArrayList<DefinizioneType>();
		modDef.add(dType);
		
		/* rimossi temporameamente per err40
        issue anac https://github.com/anticorruzione/npa/issues/1192 */
		//cl1.setModalitaDefinizione(modDef);
		cl1.setModalitaDefinizione(null);
					
		return cl1;
	}


	private ConclusioneType1 conclusione1ToType(Date dataInizio, Double importoCertificato, Date dataUltimazione) {
		ConclusioneType1 c1 = new ConclusioneType1();
		
		if(dataInizio != null) {
			c1.setDataInizio(dateToOffsetDateTime(dataInizio));
		}
		
		if(importoCertificato != null) {
			c1.setImporto(importoCertificato);		
		}
		
		if(dataUltimazione != null) {
			c1.setDataUltimazione(dateToOffsetDateTime(dataUltimazione));
		}
		
		return c1;
	}


	private ConclusioneType conclusioneToType(FaseConclusioneEntry co1) {
		ConclusioneType c = new ConclusioneType();
		
		if(co1.getMotivoInterruzione() != null) {			
			MotiviInterruzioneEnum motiviInterruzione = new MotiviInterruzioneEnum();
			motiviInterruzione.setCodice(co1.getMotivoInterruzione().toString());
			motiviInterruzione.setIdTipologica("causaInterruzioneAnticipata");
			c.setCausaInterruzioneAnticipata(motiviInterruzione);				
		}

		if(co1.getDataRisoluzione() != null) {
			c.setDataInterruzioneAnticipata(dateToOffsetDateTime(co1.getDataRisoluzione()));
		}
	
		if(co1.getFlagOneri() != null) {			
			OneriEconomiciRisoluzioneRecessoEnum oneriEconomiciRisoluzioneRecesso = new OneriEconomiciRisoluzioneRecessoEnum();
			oneriEconomiciRisoluzioneRecesso.setCodice(co1.getFlagOneri().toString());
			oneriEconomiciRisoluzioneRecesso.setIdTipologica("oneriEconomiciRisoluzioneRecesso");
			c.setOneriEconomiciRisoluzioneRecesso(oneriEconomiciRisoluzioneRecesso);				
		}
			
		if(co1.getImportoOneri() != null) {
			c.setImporto(co1.getImportoOneri());
		}
				
		if(co1.getFlagPolizza() != null) {
			c.setIncamerataPolizza(co1.getFlagPolizza().equals("1") ? true : false);
		}
				
		if(co1.getDataUltimazione() != null) {
			c.setDataUltimazione(dateToOffsetDateTime(co1.getDataUltimazione()));
		}
		
		if(co1.getNumInfortuni() != null) {
			c.setNumeroInfortuni(new BigDecimal(co1.getNumInfortuni()));
		}
			
		if(co1.getNumInfortuniPermanenti() != null) {
			c.setDiCuiPostumiPermanenti(new BigDecimal(co1.getNumInfortuniPermanenti()));
		}
		
		if(co1.getNumInfortuniMortali() != null) {
			c.setDiCuiMortali(new BigDecimal(co1.getNumInfortuniMortali()));
		}
				
		return c;
	}


	private AvanzamentoType avanzamentoToType(FaseAvanzamentoEntry sa1) {		
		AvanzamentoType at = new AvanzamentoType();
		if(sa1.getDenomAvanzamento() != null) {
			at.setDenominazioneAvanzamento(sa1.getDenomAvanzamento());
		}
		if(sa1.getFlagPagamento() != null) {
			ModalitaPagamentoEnum modalitaPagamento = new ModalitaPagamentoEnum();
			modalitaPagamento.setCodice(sa1.getFlagPagamento().toString());
			modalitaPagamento.setIdTipologica("modalitaPagamento");
			at.setModalitaPagamento(modalitaPagamento);
		}
		if(sa1.getImportoAnticipazione() != null) {
			at.setImpAnticipazione(sa1.getImportoAnticipazione());
		}
		if(sa1.getDataAnticipazione() != null) {
			at.setDataCertificatoAnticipazione(dateToOffsetDateTime(sa1.getDataAnticipazione()));
		}
		if(sa1.getDataRaggiungimento() != null) {
			at.setDataAvanzamento(dateToOffsetDateTime(sa1.getDataRaggiungimento()));
		}
		if(sa1.getImportoSal() != null) {
			at.setImpSal(sa1.getImportoSal());
		}
		if(sa1.getDataCertificato() != null) {
			at.setDataCertificatoMandatoPagamento(dateToOffsetDateTime(sa1.getDataCertificato()));
		}
		if(sa1.getImportoCertificato() != null) {
			at.setImpCertificatoMandatoPagamento(sa1.getImportoCertificato());
		}
		if(sa1.getFlagRitardo() != null) {
			AvanzamentoEnum avanzamento = new AvanzamentoEnum();
			avanzamento.setCodice(flagRitardoMap.get(sa1.getFlagRitardo()));
			avanzamento.setIdTipologica("avanzamento");
			at.setAvanzamento(avanzamento);
		}
		if(sa1.getNumGiorniScost() != null) {
			at.setNumGiorniScostamento(new BigDecimal(sa1.getNumGiorniScost()));
		}
		if(sa1.getNumGiorniProroga() != null) {
			at.setNumGiorniProroga(new BigDecimal(sa1.getNumGiorniProroga()));
		}			
		
		return at;
	}


	private DatiInizioType inizioToType(FaseInizialeEsecuzioneEntry i1, LottoEntry lotto) {
		DatiInizioType dit = new DatiInizioType();
		
		if(i1.getDataInizioProg() != null) {
			dit.setDataDisposizioneInizio(dateToOffsetDateTime(i1.getDataInizioProg()));
		}
		
		if(i1.getDataApprovazioneProg() != null) {
			dit.setDataApprovazione(dateToOffsetDateTime(i1.getDataApprovazioneProg()));
		}
		
		if(i1.getFrazionata() != null) {
			if(lotto.getTipologia() != null && "L".equals(lotto.getTipologia())) {
				dit.setConsegnaFrazionata("1".equals(i1.getFrazionata()) ? true : false);
			} else if(lotto.getTipologia() != null && ("F".equals(lotto.getTipologia()) || "S".equals(lotto.getTipologia()))) {
				dit.setAvvioPerFasi("1".equals(i1.getFrazionata()) ? true : false);
			}				
		}
				
		if(i1.getDataVerbaleCons() != null) {
			dit.setDataVerbalePrimaConsegna(dateToOffsetDateTime(i1.getDataVerbaleCons()));
			dit.setDataAvvioPrimaFase(dateToOffsetDateTime(i1.getDataVerbaleCons()));
		}

		
		if(i1.getDataVerbaleDef() != null) {
			dit.setDataVerbaleConsegnaDefinitiva(dateToOffsetDateTime(i1.getDataVerbaleDef()));
		}
		
		if(i1.getRiserva() != null) {
			dit.setConsegnaSottoRiserva("1".equals(i1.getRiserva()) ? true : false);
		}
		
		if(i1.getDataVerbInizio() != null) {
			dit.setDataEffettivoInizio(dateToOffsetDateTime(i1.getDataVerbInizio()));
		}
		
		if(i1.getDataTermine() != null) {
			dit.setDataFinePrevista(dateToOffsetDateTime(i1.getDataTermine()));
		}
		
		return dit;
	}


	private DatiContrattoType sottoScrizioneContrattoToType(FaseInizialeEsecuzioneEntry fase, LottoEntry lotto, String codiceAusa) {
		
		DatiContrattoType dct = new DatiContrattoType();
					
		List<String> idPartecipanteList = this.contrattiMapper.getIdPartecipanteAggi(Long.valueOf(lotto.getCodgara()), Long.valueOf(lotto.getCodLotto()), Long.valueOf(fase.getNumAppa()));
		String idPartecipante = idPartecipanteList != null && idPartecipanteList.size() > 0 ? idPartecipanteList.get(0) : null;
		if(fase.getDataDecorrenza() != null) {
			OffsetDateTime dataDecorrenza = dateToOffsetDateTime(fase.getDataDecorrenza());
			dct.setDataDecorrenza(dataDecorrenza);
		}				
		if(fase.getDataStipula() != null) {
			OffsetDateTime dataStipula = dateToOffsetDateTime(fase.getDataStipula());
			dct.setDataStipula(dataStipula);
		}
		if(fase.getDataScadenza() != null) {
			OffsetDateTime dataScadenza = dateToOffsetDateTime(fase.getDataScadenza());
			dct.setDataScadenza(dataScadenza);
		}
		if(fase.getDataEsecutivita() != null) {
			OffsetDateTime dataEsecutivita = dateToOffsetDateTime(fase.getDataEsecutivita());
			dct.setDataEsecutivita(dataEsecutivita);
		}
		if(fase.getImportoCauzione() != null) {					
			dct.setImportoCauzione(fase.getImportoCauzione());
		}
		
		List<String> cig = new ArrayList<String>();
		if(lotto.getMasterCig() != null) {
			List<LottoBaseEntry> cigAccorpati = this.contrattiMapper.getLottiAccorpati(lotto.getCig());
			cig.add(lotto.getCig());
			for (LottoBaseEntry lottoBaseEntry : cigAccorpati) {
				cig.add(lottoBaseEntry.getCig());
			}
		} else {
			cig.add(lotto.getCig());			
		}
		dct.setCig(cig);
		
				
		List<String> codiceAusaList = new ArrayList<String>();
		if(codiceAusa != null) {
			codiceAusaList.add(codiceAusa);
		}		
		dct.setCodiceAusa(codiceAusaList);
						
		if(idPartecipante != null) {
			dct.setIdPartecipante(UUID.fromString(idPartecipante));
		}
		
		
		return dct;
	}


	private ResponsePubblicaFase creaScheda(EModalitaInvio xModalitaInvio,String xTipologicaScheda,Object scheda,String idAppalto, Long syscon, String codProfilo, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {
		
		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		risultato.setEsito(true);
		CreaSchedaRequestDTOMapStringObject body = new CreaSchedaRequestDTOMapStringObject();
		String idScheda = "";
		logger.info("execute creaScheda - idappalto: {}", idAppalto);
		try {			
			body.setIdAppalto(idAppalto);
	        Map<String, Object> out = objectMapper.convertValue(scheda, Map.class);
	        body.setBody(out);
	        InlineResponse400 res = null;
	        
	        try {
	        	res = comunicaPostPubblicazioneResourceV1Api.npaGatewayMsV1ProtectedComunicaPostPubblicazioneCreaSchedaPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma,xTipologicaScheda, xVersioneTracciato);
	        		        	
	        	CreaSchedaResponse response = objectMapper.convertValue(res.getData(), CreaSchedaResponse.class);
		        
				risultato.setEsito(res.getDone().equals("Y") ? true : false);
								
				idScheda = response.getIdScheda().toString();
				if(idScheda != null) {
					risultato.setIdScheda(idScheda);					
				}
				
	        } catch(HttpClientErrorException e1){
	        	logger.warn(e1);
	        	res =  objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);
	        		        	
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				}
			}
	        	
	        risultato.setGovwayMessageId(res.getGovwayMessageId());
        	risultato.setGovwayTransactionId(res.getGovwayTransactionId());
        	risultato.setCartId(res.getCartId());
        	
        	String logEventiMessage = null;
			String logEventiError = null;
			String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
			String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";
			String cartId = StringUtils.isNotBlank(risultato.getCartId()) ? risultato.getCartId() : "";
			
			String resString = objectMapper.writeValueAsString(res);
			logEventiMessage = "creaScheda" + " " + xTipologicaScheda + " - idAppalto: " + idAppalto +  " - idScheda: " + idScheda + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + " - CartId: "+ cartId + ") - json di risposta ANAC: " + resString;
			if (logEventiMessage.length() > 2000) {
				logEventiMessage = logEventiMessage.substring(0, 2000);
		    }
			logEventiError = generateLogEventiError(risultato); 
			Short livento = 1;
			if(logEventiError != null) {
				livento = 3;
			}
			insertLogEventi(syscon, codProfilo, LOG_EVENTI_SCHEDA_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);
	        			
		}catch (Exception e) {					
			logger.error("errore nel metodo creaScheda",e);
			throw e;
		}
		
		return risultato;
		
	}
	
	private ResponsePubblicaFase modificaScheda(EModalitaInvio xModalitaInvio, String xTipologicaScheda,  Object scheda, String idAppalto, String idScheda, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		risultato.setEsito(true);
		DatiSchedaRequestDTOMapStringObject body = new DatiSchedaRequestDTOMapStringObject();
		logger.info("execute confermaScheda - idAppalto: {} - idScheda {}",idAppalto, idScheda);
		try {
			body.setIdAppalto(idAppalto);
			body.setIdScheda(idScheda);
			Map<String, Object> out = objectMapper.convertValue(scheda, Map.class);
			body.setBody(out);
			InlineResponse400 res = null;
			try {
				res = comunicaPostPubblicazioneResourceV1Api
						.npaGatewayMsV1ProtectedComunicaPostPubblicazioneModificaSchedaPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

				ModificaSchedaResponse response = objectMapper.convertValue(res.getData(), ModificaSchedaResponse.class);

				risultato.setEsito(res.getDone().equals("Y") ? true : false);

				String idSchedaResponse = response.getIdScheda().toString();
				if (idScheda != null) {
					risultato.setIdScheda(idSchedaResponse);
				}
	        	
			} catch (HttpClientErrorException e1) {
				res = objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);
				
				risultato.setErrors(true);
				if (res.getInternalErrors() != null && res.getInternalErrors().size() > 0) {
					risultato.setInternalErrors(res.getInternalErrors());
				}

				if (res.getValidationErrors() != null && res.getValidationErrors().size() > 0) {
					List<String> validErrors = new ArrayList<String>();
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
						if (e != null) {
							anacErrors.add(e.getCodice() + " - " + e.getDettaglio());
						}
					}
					risultato.setAnacErrors(anacErrors);
				}
			}
			
			risultato.setGovwayMessageId(res.getGovwayMessageId());
        	risultato.setGovwayTransactionId(res.getGovwayTransactionId());

		} catch (Exception e) {
			logger.error("errore nel metodo modificaScheda", e);
			throw e;
		}

	return risultato;
	
	}
	
	private ResponsePubblicaFase confermaScheda(String xTipologicaScheda,String idScheda,String idAppalto, Long syscon, String codProfilo, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {
		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		try {	
			GenericSchedaRequestDTO body = new GenericSchedaRequestDTO();
			
			body.setIdAppalto(idAppalto);
			body.setIdScheda(idScheda);
						
			InlineResponse400 res = null;
			try {																												
				res = comunicaPostPubblicazioneResourceV1Api.npaGatewayMsV1ProtectedComunicaPostPubblicazioneConfermaSchedaPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale,null,xRegCodiceComponente, xRegCodicePiattaforma,xTipologicaScheda, xVersioneTracciato);
	        		        	
				AckResponse response = objectMapper.convertValue(res.getData(), AckResponse.class);		        
				risultato.setEsito(res.getDone().equals("Y") ? true : false);
				if(response.getStatus() == 200) {
					risultato.setPubblicato(true);
				}
						
				
	        } catch(HttpClientErrorException e1){  
	        	logger.warn(e1);
	        	res =  objectMapper.readValue(e1.getResponseBodyAsString(), InlineResponse400.class);
	        	
	        	
	        	
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				}
			}
			
			risultato.setGovwayMessageId(res.getGovwayMessageId());
        	risultato.setGovwayTransactionId(res.getGovwayTransactionId());
        	risultato.setCartId(res.getCartId());
        	
        	String logEventiMessage = null;
			String logEventiError = null;
			String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
			String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";
			String cartId = StringUtils.isNotBlank(risultato.getCartId()) ? risultato.getCartId() : "";		
			
			String resString = objectMapper.writeValueAsString(res);					
			logEventiMessage = "confermaScheda" + " " + xTipologicaScheda + " - idAppalto: " + idAppalto +  " - idScheda: " + idScheda + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + " - CartId: "+ cartId + ") - json di risposta ANAC: " + resString;
			if (logEventiMessage.length() > 2000) {
				logEventiMessage = logEventiMessage.substring(0, 2000);
		    }
			logEventiError = generateLogEventiError(risultato); 
			Short livento = 1;
			if(logEventiError != null) {
				livento = 3;
			}
			insertLogEventi(syscon, codProfilo, LOG_EVENTI_SCHEDA_PCP_COD_EVENTO, logEventiMessage, logEventiError, livento, idAppalto);
			
		}catch (Exception e) {
			logger.error("errore nel metodo confermaScheda",e);
			throw e;
		}
		return risultato;
	}

	public ResponsePubblicaFase verificaSchedaPcp(Long codGara, Long codLotto, Long codFase, Long num) throws Exception {
		ResponsePubblicaFase risultato = new ResponsePubblicaFase();
		risultato.setEsito(true);
		FaseFactory factory = getFactory(codFase);
		if (factory == null) {
			risultato.setEsito(false);
			risultato.setErrorData(ResponsePubblicaFase.ERROR_UNEXPECTED);
			return risultato;
		}
		try {

			List<ValidateEntry> validate = factory.valida(codGara,codLotto,codFase,num,contrattiMapper,sqlMapper);
			risultato.setValidate(validate);
			
		} catch (Exception e) {

			logger.error("Errore in fase di validazione scheda con codGara : " + codGara, e);
			throw e;
		}
		return risultato;
	}
	
	
	
	private FaseFactory getFactory(Long codFase) {
		switch (codFase.intValue()) {
		case 13:
			return new FaseSottoscrizioneContrattoFactory();
		case 2:
			return new FaseInizialeFactory();
		case 3:
			return new FaseAvanzamentoFactory();
		case 4:
			return new FaseConclusioneFactory();
		case 19:
			return new FaseConclusioneAffidamentoDirettoFactory();
		case 5:
			return new FaseCollaudoFactory();
		case 8:
			return new FaseAccordoBonarioFactory();
		case 6:
			return new FaseSospensioneFactory();
		case 14:
			return new FaseSuperamentoQuartoFactory();
		case 15:
			return new FaseRipresaPrestazioneFactory();
		case 7:
			return new FaseVarianteFactory();		
		case 16:
			return new FaseRichiestaSubappaltoFactory();
		case 17:
			return new FaseEsitoSubappaltoFactory();
		case 18:
			return new FaseConclusioneSubappaltoFactory();		
		case 10:
			return new FaseIstanzaRecessoFactory();
		case 20:
			return new FaseIncarichiProfessionaliFactory();
		case 21:
			return new FaseVariazioneAggiudicatariFactory();
		}
		return null;
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
	

	public BaseResponsePcp cancellaSchedaPcp(Long codGara, Long codLotto, Long codFase, Long num, Long syscon, String codProfilo, String cf, String loa, String idp, String motivazione) throws Exception {
		BaseResponsePcp risultato = new BaseResponsePcp();
			
		String xTipologicaScheda = null;
		
		UserIdpTypeEnum xUserIdpType = null;
		CustomUserLoaEnum xUserLoa = null;			
		String xSAcodiceAUSA = null;	
		String xUserCodiceFiscale = null;
		String xSACodiceFiscale = null;	
		String xUserRole = null;
		
		try {
			initApi();
			
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			
			
			xUserCodiceFiscale = cf;
			xUserRole = null;	
			RupEntry drp = null;
			if (gara.getDrp() != null) {
				drp = this.contrattiMapper.getTecnico(gara.getDrp());
				if(cf.equals(drp.getCf())){
					xUserRole = "DRP3";
				}
			} else {
				RupEntry tecnico = null;
				if (gara.getCodiceTecnico() != null) {
					tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
					if(cf.equals(tecnico.getCf())){
						xUserRole = "RP";
					}
				}
			}
						
			if(xUserRole == null) {
				throw new UnauthorizedUserException("Utente non autorizzato");
			}
			
			String codiceSA = gara.getCodiceStazioneAppaltante();
			String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
			String cfSa = this.contrattiMapper.getCFSAByCode(codiceSA);
			
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
			String idScheda = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, codFase, num);
			
			
			
			GenericResponse<CancellaSchedaResponse> cancellazione = cancellaScheda(idAppalto,EModalitaInvio.INVIA, idScheda, motivazione,xTipologicaScheda, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
			if(cancellazione != null && cancellazione.getResult() != null) {				
				this.contrattiMapper.setIdContrattoNullW9fasi(codGara, codLotto, codFase, num);		
				this.cancellazioneFlusso(codGara, codLotto, codFase, num, motivazione);
				Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
				this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
				Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
				this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);
			} else {
				risultato.setErrorData(cancellazione.getErrorData());
				risultato.setAnacErrors(cancellazione.getAnacErrors());
				risultato.setInternalErrors(cancellazione.getInternalErrors());
				risultato.setValidationErrors(cancellazione.getValidationErrors());
				return risultato;
			}
			

			
			
			
		}catch (Exception e) {
			logger.error("errore nel metodo cancellaSchedaPcp",e);
			throw e;
		}
		
		return risultato;
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
			InlineResponse400 res = codeListResourceV1Api.npaGatewayMsV1ProtectedCodeListRecuperaValoreTipologicaGet(codice, idTipologica, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, null, null, null, null, xVersioneTracciato);
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
			Long situazioneLotto = null;
			
			if(numFase == 13) {
				situazioneLotto = 3L;
			} else if(numFase == 19) {
				situazioneLotto = 7L;
			} else {
				situazioneLotto = tipoInvio == -1 ? 95L : contrattiManager.getSituazioneLotto(codGara, codLotto);				
			}

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
		
	private GenericResponse<EsitoOperazioneListaResponse> esitoOperazione(EModalitaInvio xModalitaInvio, String xTipologicaScheda,String idAppalto, String tipoOperazione, String tipoRicerca, String esito, Long syscon, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<EsitoOperazioneListaResponse> risultato = new GenericResponse<EsitoOperazioneListaResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute esitoOperazione");
			InlineResponse400 res = null;
			try {				
				res = serviziComuniResourceV1Api.npaGatewayMsV1ProtectedServiziComuniEsitoOperazioneGet(tipoOperazione, tipoRicerca, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, esito, idAppalto,null, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				EsitoOperazioneListaResponse result = objectMapper.convertValue(res.getData(), EsitoOperazioneListaResponse.class);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<CigListaResponse> recuperaCig(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String cig, String idAppalto, Integer page, Integer perPage, Long syscon, 
			UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<CigListaResponse> risultato = new GenericResponse<CigListaResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute recuperaCig");
			InlineResponse400 res = null;
			try {
				res = comunicaAppaltoResourceV1Api.npaGatewayMsV1ProtectedComunicaAppaltoRecuperaCigGet(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, page, perPage, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				CigListaResponse result = objectMapper.convertValue(res.getData(), CigListaResponse.class);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<SchedaListaResponse> ricercaScheda(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String cig, String idAppalto, Integer page, Integer perPage, Long syscon
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String statoScheda) throws Exception {

		GenericResponse<SchedaListaResponse> risultato = new GenericResponse<SchedaListaResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute ricercaScheda");
			InlineResponse400 res = null;
			try {
				res = comunicaPostPubblicazioneResourceV1Api.npaGatewayMsV1ProtectedComunicaPostPubblicazioneRicercaSchedaGet(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, null, null, null, page, perPage, null, statoScheda, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda,  xVersioneTracciato);
				
				SchedaListaResponse result = objectMapper.convertValue(res.getData(), SchedaListaResponse.class);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<ConsultaSchedaResponse> consultaScheda(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idScheda, Long syscon
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String idAppalto) throws Exception {

		GenericResponse<ConsultaSchedaResponse> risultato = new GenericResponse<ConsultaSchedaResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute consultaScheda");
			InlineResponse400 res = null;
			try {
				res = comunicaPostPubblicazioneResourceV1Api.npaGatewayMsV1ProtectedComunicaPostPubblicazioneConsultaSchedaGet(idScheda, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);				
				ConsultaSchedaResponse result = objectMapper.convertValue(res.getData(), ConsultaSchedaResponse.class);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<SoggettoListaResponse> ricercaSoggetti(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, Long syscon
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<SoggettoListaResponse> risultato = new GenericResponse<SoggettoListaResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute ricercaSoggetti");
			InlineResponse400 res = null;
			try {
				res = gestioneUtentiResourceV1Api.npaGatewayMsV1ProtectedGestioneUtentiRicercaSoggettiGet(idAppalto, xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, null, null, null, null, null, null, null, null, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				SoggettoListaResponse result = objectMapper.convertValue(res.getData(), SoggettoListaResponse.class);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<AggiungiSoggettoResponse> aggiungiSoggetto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, Long syscon, SoggettoRequestDTO body
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<AggiungiSoggettoResponse> risultato = new GenericResponse<AggiungiSoggettoResponse>();
		risultato.setEsito(true);		
		try {			
			InlineResponse400 res = null;
			logger.info("execute aggiungiSoggetto");
			try {
				res = gestioneUtentiResourceV1Api.npaGatewayMsV1ProtectedGestioneUtentiAggiungiSoggettoPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				AggiungiSoggettoResponse result = objectMapper.convertValue(res.getData(), AggiungiSoggettoResponse.class);
				if(result != null) {
					risultato.setInserito(true);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
			}

			risultato.setGovwayMessageId(res.getGovwayMessageId());
			risultato.setGovwayTransactionId(res.getGovwayTransactionId());
			
			String logEventiMessage = null;
			String logEventiError = null;
			String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
			String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";
			
			String resString = objectMapper.writeValueAsString(res);
			logEventiMessage = "aggiungiSoggetto"+ " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
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
			logger.error("errore nel metodo aggiungiSoggetto", e);
			throw e;
		}

		return risultato;
	}
	
	private GenericResponse<EliminaSoggettoResponse> eliminaSoggetto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, Long syscon, EliminaSoggettoRequestDTO body
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<EliminaSoggettoResponse> risultato = new GenericResponse<EliminaSoggettoResponse>();
		risultato.setEsito(true);		
		try {			
			InlineResponse400 res = null;
			logger.info("execute eliminaSoggetto");
			try {
				res = gestioneUtentiResourceV1Api.npaGatewayMsV1ProtectedGestioneUtentiEliminaSoggettoPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType,  xUserLoa, xUserRole,  body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				EliminaSoggettoResponse result = objectMapper.convertValue(res.getData(), EliminaSoggettoResponse.class);
				if(result != null) {
					risultato.setInserito(true);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
			}

			risultato.setGovwayMessageId(res.getGovwayMessageId());
			risultato.setGovwayTransactionId(res.getGovwayTransactionId());
			
			String logEventiMessage = null;
			String logEventiError = null;
			String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
			String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";
			
			String resString = objectMapper.writeValueAsString(res);
			logEventiMessage = "eliminaSoggetto" + " - idAppalto: " + idAppalto + " (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
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
			logger.error("errore nel metodo eliminaSoggetto", e);
			throw e;
		}

		return risultato;
	}
	
	private GenericResponse<PresaCaricoResponse> presaCarico(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppaltoCig, Long syscon, PresaCaricoRequestDTO body
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole, String codProfilo) throws Exception {

		GenericResponse<PresaCaricoResponse> risultato = new GenericResponse<PresaCaricoResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute presaCarico");
			InlineResponse400 res = null;
			
			try {
				res = gestioneUtentiResourceV1Api.npaGatewayMsV1ProtectedGestioneUtentiPresaCaricoPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				PresaCaricoResponse result = objectMapper.convertValue(res.getData(), PresaCaricoResponse.class);
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<ConsultaAppaltoResponse> consultaAppalto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String idAppalto, Long syscon
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<ConsultaAppaltoResponse> risultato = new GenericResponse<ConsultaAppaltoResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute consultaAppalto");
			InlineResponse400 res = null;
			try {
				res = comunicaAppaltoResourceV1Api.npaGatewayMsV1ProtectedComunicaAppaltoConsultaAppaltoGet(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, idAppalto, xSACodiceFiscale, xModalitaInvio,xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

				ConsultaAppaltoResponse response = objectMapper.convertValue(res.getData(), ConsultaAppaltoResponse.class);
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				}
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
	
	private GenericResponse<StatoResponse> statoAppalto(EModalitaInvio xModalitaInvio, String xTipologicaScheda,String idAppalto, String tipoOperazione, String tipoRicerca, Long syscon, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<StatoResponse> risultato = new GenericResponse<StatoResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute statoAppalto");
			InlineResponse400 res = null;
			try {				
				res = serviziComuniResourceV1Api.npaGatewayMsV1ProtectedServiziComuniStatoAppaltoGet(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, null, idAppalto, null, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);
				
				StatoResponse result = objectMapper.convertValue(res.getData(), StatoResponse.class);
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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<AppaltoListaResponse> ricercaAppalto(EModalitaInvio xModalitaInvio, String xTipologicaScheda, String cig, Long syscon
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {

		GenericResponse<AppaltoListaResponse> risultato = new GenericResponse<AppaltoListaResponse>();
		risultato.setEsito(true);		
		try {			
			logger.info("execute ricercaAppalto");
			InlineResponse400 res = null;
			try {
				res = comunicaAppaltoResourceV1Api.npaGatewayMsV1ProtectedComunicaAppaltoRicercaAppaltoGet(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, cig, null, null, null, null, 1, 1, null, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

				AppaltoListaResponse response = objectMapper.convertValue(res.getData(), AppaltoListaResponse.class);
				
						
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private GenericResponse<CancellaSchedaResponse> cancellaScheda(String idAppalto,EModalitaInvio xModalitaInvio, String idScheda, String motivo,String xTipologicaScheda, Long syscon
			, UserIdpTypeEnum xUserIdpType, CustomUserLoaEnum xUserLoa, String xSAcodiceAUSA, String xUserCodiceFiscale, String xSACodiceFiscale, String xUserRole) throws Exception {
		
		GenericResponse<CancellaSchedaResponse> risultato = new GenericResponse<CancellaSchedaResponse>();
		
		CancellaSchedaRequestDTO body = new CancellaSchedaRequestDTO();
		
		try {			
			logger.info("execute cancellaScheda");
			InlineResponse400 res = null;
			try {
				body.setIdAppalto(idAppalto);
				body.setIdScheda(idScheda);
				body.setMotivo(motivo);
				res = comunicaPostPubblicazioneResourceV1Api.npaGatewayMsV1ProtectedComunicaPostPubblicazioneCancellaSchedaPost(xSAcodiceAUSA, xTenantId, xUserCodiceFiscale, xUserIdpType, xUserLoa, xUserRole, body, xSACodiceFiscale, xModalitaInvio, xRegCodiceComponente, xRegCodicePiattaforma, xTipologicaScheda, xVersioneTracciato);

				CancellaSchedaResponse response = objectMapper.convertValue(res.getData(), CancellaSchedaResponse.class);
				
						
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				}
			}

			risultato.setGovwayMessageId(res.getGovwayMessageId());
			risultato.setGovwayTransactionId(res.getGovwayTransactionId());
			
			String logEventiMessage = null;
			String logEventiError = null;
			String govwayMessageId = StringUtils.isNotBlank(risultato.getGovwayMessageId()) ? risultato.getGovwayMessageId() : "";
			String govwayTransactionId = StringUtils.isNotBlank(risultato.getGovwayTransactionId()) ? risultato.getGovwayTransactionId() : "";			
			
			String resString = objectMapper.writeValueAsString(res);
			logEventiMessage = "cancellaScheda" + " idAppalto: " + idAppalto + " - idScheda: "+ idScheda +" - (GovwayMessageId: " + govwayMessageId + " - GovwayTransactionId: " + govwayTransactionId + ") - json di risposta ANAC: " + resString;
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
			logger.error("errore nel metodo cancellaScheda", e);
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
				res = ausaResourceV1Api.npaGatewayMsV1ProtectedAusaGetByGet(xTenantId, null, codiceFiscale);

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
					for (TipologicaSchemaErroriType e : res.getValidationErrors()) {

					}
					risultato.setValidationErrors(validErrors);
				}

				if (res.getAnacErrors() != null && res.getAnacErrors().size() > 0) {
					List<String> anacErrors = new ArrayList<String>();
					for (ErroriEnum e : res.getAnacErrors()) {
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
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
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
	
	private List<LottoDynamicValue> getMotivazioniFaseVariante(Long codGara, Long numLotto, Long num) throws Exception {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			GaraFullEntry gara = contrattiMapper.dettaglioGaraCompleto(codGara);
			LottoEntry lotto = contrattiMapper.getDettaglioLotto(codGara, numLotto);

			if (gara != null && lotto != null) {

				String tipoContrattoLotto = lotto.getTipologia();
				List<TabellatoEntry> tabellatoMotivazioni = new ArrayList<TabellatoEntry>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataRiferimento = sdf.parse("2016-04-18");
				boolean dataPubblicazioneBefore = gara.getDataCreazione() == null
						|| (gara.getDataCreazione() != null && (gara.getDataCreazione().before(dataRiferimento)
								|| gara.getDataCreazione().equals(dataRiferimento)));

				if (dataPubblicazioneBefore) {
					if ("L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar2();						
					} else if (!"L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar1();
					}
				} else {
					if (gara.getVersioneSimog() < 3L) {
						tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar3();						
					}
				}
				
				if(gara.getVersioneSimog() >= 3L && gara.getVersioneSimog() < 9L) {
					tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar4();
				}
				
				if(gara.getVersioneSimog() == 9L) {
					tabellatoMotivazioni = contrattiMapper.getMotivazioniFaseVar5();
				}
				
				List<Long> codiciCondizioni = contrattiMapper.getMotiviVariazioneByFase(codGara, numLotto, num);
				for (TabellatoEntry e : tabellatoMotivazioni) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciCondizioni.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return risultato;
	}


	
	
	public boolean isUUID(String cigIdAvGara) {
		if (cigIdAvGara == null) {
	        return false;
	    }
	    return UUID_REGEX_PATTERN.matcher(cigIdAvGara).matches();
	}

	public ResponseInfoDelegati getDelegatoPcp(Long codGara, Long syscon, String cf, String loa, String idp)  throws Exception {
		ResponseInfoDelegati risultato = new ResponseInfoDelegati();
		risultato.setEsito(true);
		
		EModalitaInvio xModalitaInvio = null;
		String xTipologicaScheda = null;
		List<SoggettoType> soggetti = new ArrayList<SoggettoType>();			
		
		UserIdpTypeEnum xUserIdpType = null;
		CustomUserLoaEnum xUserLoa = null;			
		String xSAcodiceAUSA = null;	
		String xUserCodiceFiscale = null;
		String xSACodiceFiscale = null;	
		String xUserRole = null;
		
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			initApi();
			
			xUserRole = null;
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
			
			if(loa.equals("3")) {
				xUserLoa = CustomUserLoaEnum._3;
			} else if(loa.equals("4")) {
				xUserLoa = CustomUserLoaEnum._4;
			} else {
				throw new Exception("Utente non autorizzato");
			}
			
			String cfSa = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
			String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
			xUserCodiceFiscale = cf;
			xSACodiceFiscale = cfSa;
			xSAcodiceAUSA = codiceAusa;
			xUserIdpType = UserIdpTypeEnum.fromValue(idp);
																		
			GenericResponse<SoggettoListaResponse> sogg = ricercaSoggetti(xModalitaInvio, xTipologicaScheda, gara.getIdAppalto(), syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
			if(sogg != null && sogg.getResult() != null) {					
				soggetti = sogg.getResult().getResult();	
				if(soggetti != null && soggetti.size() > 0) {
					RupEntry drp = null;
					for (SoggettoType soggetto : soggetti) {
						if(soggetto != null && soggetto.getRuolo() != null && StringUtils.isNotBlank(soggetto.getRuolo().getCodice())) {													
							if(soggetto.getRuolo() != null && StringUtils.isNotBlank(soggetto.getRuolo().getCodice())) {
								if(soggetto.getRuolo().getCodice().equals("DRP3") && (soggetto.getDataFine() != null && soggetto.getDataFine().getYear() == 9999)) {									
									List<RupEntry> tecList = this.contrattiMapper.getTecnicoByCfAndSaList(soggetto.getCodiceFiscale(), gara.getCodiceStazioneAppaltante());
									RupEntry tec = tecList != null && !tecList.isEmpty() ? tecList.get(0) : null;
									if(tec == null) {
										tec = new RupEntry();
										String codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");								
										tec.setCodice(codtec);
										tec.setNome("-");
										tec.setCognome("-");
										tec.setNominativo("-");
										tec.setCf(soggetto.getCodiceFiscale());
										tec.setStazioneAppaltante(gara.getCodiceStazioneAppaltante());
										this.contrattiMapper.insertRUP(tec);
									}
									drp = tec;
								} else if(soggetto.getRuolo().getCodice().equals("DRP2") && drp != null && (soggetto.getDataFine() != null && soggetto.getDataFine().getYear() == 9999)) {									
									List<RupEntry> tecList = this.contrattiMapper.getTecnicoByCfAndSaList(soggetto.getCodiceFiscale(), gara.getCodiceStazioneAppaltante());
									RupEntry tec = tecList != null && !tecList.isEmpty() ? tecList.get(0) : null;
									if(tec == null) {
										tec = new RupEntry();
										String codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");								
										tec.setCodice(codtec);
										tec.setNome("-");
										tec.setCognome("-");
										tec.setNominativo("-");
										tec.setCf(soggetto.getCodiceFiscale());
										tec.setStazioneAppaltante(gara.getCodiceStazioneAppaltante());
										this.contrattiMapper.insertRUP(tec);
									}
									drp = tec;
								}
							}
						}
					}
					if(drp != null) {
						this.contrattiMapper.updateDrpW9gara(codGara, drp.getCodice());
						risultato.setDelegato(drp);
					}
				}
			} else {
				risultato.setAnacErrors(sogg.getAnacErrors());
				risultato.setInternalErrors(sogg.getInternalErrors());
				risultato.setValidationErrors(sogg.getValidationErrors());				
			}
								
		} catch (UnauthorizedSAException ex) {
			logger.error("errore nel metodo getDelegatoPcp", ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION_SA);
			return risultato;
		} catch (Exception e) {
			logger.error("errore nel metodo getDelegatoPcp", e);
			throw e;
		}
				
		return risultato;
	}

	public BaseResponsePcp saveDelegatoPcp(String codGara, String codtec, Long syscon, String cf, String loa, String idp) throws Exception {
		BaseResponsePcp risultato = new BaseResponsePcp();
		risultato.setEsito(true);
		risultato.setInserito(false);
		EModalitaInvio xModalitaInvio = null;
		String xTipologicaScheda = null;
		
		UserIdpTypeEnum xUserIdpType = null;
		CustomUserLoaEnum xUserLoa = null;			
		String xSAcodiceAUSA = null;	
		String xUserCodiceFiscale = null;
		String xSACodiceFiscale = null;	
		String xUserRole = null;
		
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(Long.valueOf(codGara));
			
			initApi();
			
			xUserRole = null;
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
			
			if(loa.equals("3")) {
				xUserLoa = CustomUserLoaEnum._3;
			} else if(loa.equals("4")) {
				xUserLoa = CustomUserLoaEnum._4;
			} else {
				throw new Exception("Utente non autorizzato");
			}
			
			String cfSa = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
			String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
			xUserCodiceFiscale = cf;
			xSACodiceFiscale = cfSa;
			xSAcodiceAUSA = codiceAusa;
			xUserIdpType = UserIdpTypeEnum.fromValue(idp);
			boolean drp3IsPresent = false;
			RupEntry drp = this.contrattiMapper.getTecnico(codtec);
			if(drp != null) {	
				List<SoggettoType> soggetti = new ArrayList<SoggettoType>();
				GenericResponse<SoggettoListaResponse> s = ricercaSoggetti(xModalitaInvio, xTipologicaScheda, gara.getIdAppalto(), syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
				if(s != null && s.getResult() != null) {					
					soggetti = s.getResult().getResult();	
					if(soggetti != null && soggetti.size() > 0) {
						for (SoggettoType soggetto : soggetti) {
							if(soggetto != null){
								if(soggetto.getRuolo().getCodice().equals("DRP3") && (soggetto.getDataFine() != null && soggetto.getDataFine().getYear() == 9999)) {
									drp3IsPresent = true;
									if(!soggetto.getCodiceFiscale().equals(drp.getCf())) {												
										EliminaSoggettoRequestDTO body = new EliminaSoggettoRequestDTO();
										body.setCodiceFiscale(soggetto.getCodiceFiscale());
										LocalDateTime currentDateTime = LocalDateTime.now();
										DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
										String formattedDateTime = currentDateTime.format(formatter);		        						      
										body.setDataFine(formattedDateTime);
										body.setIdAppalto(gara.getIdAppalto());
										body.setRuolo("DRP3");
										
										GenericResponse<EliminaSoggettoResponse> sogg = eliminaSoggetto(xModalitaInvio, xTipologicaScheda, gara.getIdAppalto(), syscon, body, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
										if(sogg != null && sogg.getResult() != null) {
											SoggettoRequestDTO bodyAggiungi = new SoggettoRequestDTO();
											bodyAggiungi.setIdAppalto(gara.getIdAppalto());
											bodyAggiungi.setRuolo("DRP3");
											bodyAggiungi.setCodiceFiscale(drp.getCf());				
											LocalDateTime currentDateTimeAggiungi = LocalDateTime.now();
											DateTimeFormatter formatterAggiungi = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
											String formattedDateTimeAggiungi = currentDateTimeAggiungi.format(formatterAggiungi);		        
											bodyAggiungi.setDataInizio(formattedDateTimeAggiungi);   
											GenericResponse<AggiungiSoggettoResponse> soggAggiungi = aggiungiSoggetto(xModalitaInvio, xTipologicaScheda, gara.getIdAppalto(), syscon, bodyAggiungi, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
											if(soggAggiungi != null && soggAggiungi.getResult() != null) {
												risultato.setInserito(true);
												this.contrattiMapper.updateDrpW9gara(Long.valueOf(codGara), codtec);
											} else {
												risultato.setAnacErrors(soggAggiungi.getAnacErrors());
												risultato.setInternalErrors(soggAggiungi.getInternalErrors());
												risultato.setValidationErrors(soggAggiungi.getValidationErrors());				
											}
										} else {
											risultato.setAnacErrors(sogg.getAnacErrors());
											risultato.setInternalErrors(sogg.getInternalErrors());
											risultato.setValidationErrors(sogg.getValidationErrors());				
										}
									}
								}
								
							}
						}
						if(!drp3IsPresent) {

							SoggettoRequestDTO bodyAggiungi2 = new SoggettoRequestDTO();
							bodyAggiungi2.setIdAppalto(gara.getIdAppalto());
							bodyAggiungi2.setRuolo("DRP3");
							bodyAggiungi2.setCodiceFiscale(drp.getCf());				
							LocalDateTime currentDateTimeAggiungi = LocalDateTime.now();
							DateTimeFormatter formatterAggiungi = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
							String formattedDateTimeAggiungi = currentDateTimeAggiungi.format(formatterAggiungi);		        
							bodyAggiungi2.setDataInizio(formattedDateTimeAggiungi);   
							GenericResponse<AggiungiSoggettoResponse> soggAggiungi = aggiungiSoggetto(xModalitaInvio, xTipologicaScheda, gara.getIdAppalto(), syscon, bodyAggiungi2, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
							if(soggAggiungi != null && soggAggiungi.getResult() != null) {
								risultato.setInserito(true);
								this.contrattiMapper.updateDrpW9gara(Long.valueOf(codGara), codtec);
							} else {
								risultato.setAnacErrors(soggAggiungi.getAnacErrors());
								risultato.setInternalErrors(soggAggiungi.getInternalErrors());
								risultato.setValidationErrors(soggAggiungi.getValidationErrors());				
							}
						
						}
						
						
						
					}
				} else {
					risultato.setAnacErrors(s.getAnacErrors());
					risultato.setInternalErrors(s.getInternalErrors());
					risultato.setValidationErrors(s.getValidationErrors());				
				}
			}
								
		} catch (UnauthorizedSAException ex) {
			logger.error("errore nel metodo saveDelegatoPcp", ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION_SA);
			return risultato;
		} catch (Exception e) {
			logger.error("errore nel metodo saveDelegatoPcp", e);
			throw e;
		}
				
		return risultato;
	}

	public BaseResponsePcp verificaStatoScheda(Long codGara, Long codLotto, Long codFase, Long num, Long syscon,
			String codProfilo, String cf, String loa, String idp) throws Exception {
		
		BaseResponsePcp risultato = new BaseResponsePcp();
		risultato.setEsito(true);
		
		UserIdpTypeEnum xUserIdpType = null;
		CustomUserLoaEnum xUserLoa = null;			
		String xSAcodiceAUSA = null;	
		String xUserCodiceFiscale = null;
		String xSACodiceFiscale = null;	
		String xUserRole = null;
		
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			
			
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
			String idScheda = this.contrattiMapper.getIdContrattoPcp(codGara, codLotto, codFase, num);
			if(StringUtils.isNotBlank(idScheda) && StringUtils.isNotBlank(idAppalto)) {
				GenericResponse<EsitoOperazioneListaResponse> esitoOp = esitoOperazione(null, null, idAppalto, "SC_CONF", "TUTTI_ESITI", null, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
				if(esitoOp != null && esitoOp.getResult() != null && esitoOp.getResult().getListaEsiti() != null && esitoOp.getResult().getListaEsiti().size() > 0) {
					Optional<EsitoOperazioneType> esitoOptional = trovaEsitoConDataControlloPiuVicinaAndEsitoOK(esitoOp.getResult().getListaEsiti(), idScheda);
					EsitoOperazioneType esito = null;
					if(esitoOptional.isPresent()) {
						esito = esitoOptional.get();
					} else{
						logger.info("esito OK non trovato, cerco quello generico");
						esitoOptional = trovaEsitoConDataControlloPiuVicina(esitoOp.getResult().getListaEsiti(), idScheda);
						if(esitoOptional.isPresent()) {
							esito = esitoOptional.get();
						}
					}
					if(esito.getIdScheda() != null) {
						if(esito.getIdScheda().toString().equals(idScheda)) {								
							if(esito.getEsito().getCodice().equals("OK")){									
								this.contrattiMapper.setW9faseExported(codGara, codLotto, codFase, num);

								Long situazioneLotto = contrattiManager.getSituazioneLotto(codGara, codLotto);
								this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
								Long situazioneGara = contrattiManager.getSituazioneGara(codGara);
								this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);

							} else if(esito.getEsito().getCodice().equals("KO")){
								this.contrattiMapper.deleteFlussoPcpLottoTinvio(codGara,codLotto, codFase, num, 3L);
								this.contrattiMapper.setW9faseDaExport(codGara, codLotto, codFase, num);
							}
						}
					}												
				}
			}
			
		}catch (Exception e) {
			logger.error("errore nel metodo verificaStatoScheda", e);
			throw e;
		}
		return risultato;
	}


	public Optional<EsitoOperazioneType> trovaEsitoConDataControlloPiuVicinaAndEsitoOK(List<EsitoOperazioneType> listaEsiti, String idScheda) {
		return listaEsiti.stream()
				.filter(esito -> idScheda.equals(esito.getIdScheda()))
				.filter(esito -> esito.getEsito().getCodice().equals("OK"))
				.min((esito1, esito2) ->
						getDifferenzaDiTempo(esito1.getDataControllo(), OffsetDateTime.now())
								.compareTo(getDifferenzaDiTempo(esito2.getDataControllo(), OffsetDateTime.now())));
	}

	public Optional<EsitoOperazioneType> trovaEsitoConDataControlloPiuVicina(List<EsitoOperazioneType> listaEsiti, String idScheda) {
		return listaEsiti.stream()
				.filter(esito -> idScheda.equals(esito.getIdScheda()))
				.min((esito1, esito2) ->
						getDifferenzaDiTempo(esito1.getDataControllo(), OffsetDateTime.now())
								.compareTo(getDifferenzaDiTempo(esito2.getDataControllo(), OffsetDateTime.now())));
	}

    private Long getDifferenzaDiTempo(OffsetDateTime data1, OffsetDateTime data2) {
        return Math.abs(data1.toEpochSecond() - data2.toEpochSecond());
    }

	public BaseResponsePcp cancellaDelegatoPcp(String codGara, Long syscon, String cf, String loa,String idp) throws Exception {
		BaseResponsePcp risultato = new BaseResponsePcp();
		risultato.setEsito(true);
		risultato.setInserito(false);
		EModalitaInvio xModalitaInvio = null;
		String xTipologicaScheda = null;
		
		UserIdpTypeEnum xUserIdpType = null;
		CustomUserLoaEnum xUserLoa = null;			
		String xSAcodiceAUSA = null;	
		String xUserCodiceFiscale = null;
		String xSACodiceFiscale = null;	
		String xUserRole = null;
		
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(Long.valueOf(codGara));
			
			initApi();
			
			xUserRole = null;
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
			
			if(loa.equals("3")) {
				xUserLoa = CustomUserLoaEnum._3;
			} else if(loa.equals("4")) {
				xUserLoa = CustomUserLoaEnum._4;
			} else {
				throw new Exception("Utente non autorizzato");
			}
			
			String cfSa = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
			String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
			xUserCodiceFiscale = cf;
			xSACodiceFiscale = cfSa;
			xSAcodiceAUSA = codiceAusa;
			xUserIdpType = UserIdpTypeEnum.fromValue(idp);
			RupEntry drp = this.contrattiMapper.getTecnico(gara.getDrp());
			this.contrattiMapper.removeDrpW9gara(Long.valueOf(codGara));
			if(drp != null) {
				EliminaSoggettoRequestDTO body = new EliminaSoggettoRequestDTO();
				body.setCodiceFiscale(drp.getCf());
				LocalDateTime currentDateTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
				String formattedDateTime = currentDateTime.format(formatter);		        						      
				body.setDataFine(formattedDateTime);
				body.setIdAppalto(gara.getIdAppalto());
				body.setRuolo("DRP3");
				GenericResponse<EliminaSoggettoResponse> sogg = eliminaSoggetto(xModalitaInvio, xTipologicaScheda, gara.getIdAppalto(), syscon, body, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
				if(sogg != null && sogg.getResult() != null) {
					risultato.setInserito(true);					
				} else {
					risultato.setAnacErrors(sogg.getAnacErrors());
					risultato.setInternalErrors(sogg.getInternalErrors());
					risultato.setValidationErrors(sogg.getValidationErrors());				
				}
			}
								
		} catch (UnauthorizedSAException ex) {
			logger.error("errore nel metodo saveDelegatoPcp", ex);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_PERMISSION_SA);
			return risultato;
		} catch (Exception e) {
			logger.error("errore nel metodo saveDelegatoPcp", e);
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

	public BaseResponsePcp presaCarico(Long codGara, Long syscon, String cf, String loa, String idp) {
		BaseResponsePcp res = new BaseResponsePcp();
		res.setInserito(true);
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
			xUserRole = "RP";
											
			String codiceSA = gara.getCodiceStazioneAppaltante();
			String codiceAusa = this.contrattiMapper.getCodAusaUffint(gara.getCodiceStazioneAppaltante());
			String cfSa = this.contrattiMapper.getCFSAByCode(codiceSA);
			
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

			GenericResponse<ConsultaAppaltoResponse> appalto = consultaAppalto(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole);
			boolean contieneSEC = false;
			if(appalto != null && appalto.getAnacErrors() != null && !appalto.getAnacErrors().isEmpty()) {
				for (String elemento : appalto.getAnacErrors()) {
					if (StringUtils.isNotBlank(elemento) && elemento.contains("SEC05")) {
						contieneSEC = true;
						break;
					}
				}
			}
			if(!contieneSEC){
				this.contrattiManager.aggiornaRup(xUserCodiceFiscale, codein, gara.getCodgara());

				//VIGILANZA2-490: Invio messaggio al RUP precedente di presa in carico da parte del nuovo RUP.
				//Se il RUP precedente è un utente applicativo, allora posso inviare il messaggio.
				String cfTecPrecedente = this.contrattiMapper.getCfFromRUPId(gara.getCodiceTecnico());
				Long sysconPrecedente = this.contrattiMapper.getSysconFromCf(cfTecPrecedente);
				String nomeCognomeNuovoRUP = this.contrattiMapper.getNomTecByCfTec(xUserCodiceFiscale);
				Long sysconNuovoRUP = this.contrattiMapper.getSysconFromCf(xUserCodiceFiscale);
				if(sysconPrecedente != null) {
					MessageInForm inForm = new MessageInForm();
					Long idIn = calculateMessageId();
					inForm.setId(idIn);
					inForm.setCorpoMessaggio(
							Constants.INIZIO_MESSAGGIO_PRESA_CARICO_W_MESSAGE_IN +
									gara.getIdAppalto() +
									Constants.CORPO_MESSAGGIO_PRESA_CARICO_W_MESSAGE_IN +
									nomeCognomeNuovoRUP
					);
					inForm.setDataMessaggio(new Date());
					inForm.setOggetto(Constants.OGGETTO_PRESA_CARICO_W_MESSAGE_IN + gara.getIdAppalto());
					inForm.setSysconReceiver(gara.getSyscon());
					inForm.setSysconSender(sysconNuovoRUP);
					this.contrattiMapper.insertMessageIn(inForm);
				}
			} else{
				PresaCaricoRequestDTO body = new PresaCaricoRequestDTO();
				body.setIdAppalto(idAppalto);
				GenericResponse<PresaCaricoResponse> pcRes = presaCarico(xModalitaInvio, xTipologicaScheda, idAppalto, syscon, body, xUserIdpType, xUserLoa, xSAcodiceAUSA, xUserCodiceFiscale, xSACodiceFiscale, xUserRole, null);
				if(pcRes != null && pcRes.getResult() != null) {
					logger.info("preso in carico funzione puntuale");
					this.contrattiManager.aggiornaRup(xUserCodiceFiscale, codein, gara.getCodgara());
				} else {
					res.setAnacErrors(pcRes.getAnacErrors());
					res.setInternalErrors(pcRes.getInternalErrors());
					res.setValidationErrors(pcRes.getValidationErrors());
					res.setInserito(false);
				}
			}

		} catch (Exception e) {
			res.setInserito(false);		
			res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			logger.error("Errore durante il metodo presaCarico", e);
			return res;
		}
		return res;
	}

	private Long calculateMessageId() {
		Long maxInId = this.contrattiMapper.getMaxMessageInId();

		if (maxInId == null) {
			return 1L;
		} else {
			return maxInId + 1;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteAppaltoPcp(Long codGara) throws Exception {
		BaseResponse risultato = new BaseResponse();
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
