package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoMotivazioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.SchedeMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseSchedeTrasmessePcp;
import javax.crypto.SecretKey;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.apkappa.services.APKServiceConfig;
import it.apkappa.services.ArrayOfOutGetImpegni;
import it.apkappa.services.ArrayOfOutGetMandati;
import it.apkappa.services.AuthHeaderSOAPHandler;
import it.apkappa.services.FinanziariaAPKService;
import it.apkappa.services.FinanziariaAPKServiceSoap;
import it.apkappa.services.InGetImpegni;
import it.apkappa.services.InGetMandati;
import it.apkappa.services.OutGetImpegni;
import it.apkappa.services.OutGetMandati;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import javax.crypto.SecretKey;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.AggiudicazioneAppaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.AttoDocument;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CategoriaLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.ComunicazioneType;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CondizioneAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.ExistingAttoDocument;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.IdPubblicati;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.StatiId;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AccorpaMultilottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AccorpamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttiPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttiPubblicatiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttoLottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.CentriCostoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.CigIdAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DateVersionPcpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DatiAccorpamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DettaglioAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriImpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInadempienzeSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInidoneitaContributivaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeEsecuzioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSubappaltoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussiAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussiGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussoJsonSchedaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullDettaglioAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullFlussiFase;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpegnoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IndicatoreBean;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IndicatoreEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IndicatorePreliminareBean;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IndicatorePremEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LegaleRappresentanteEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ListaSingoloAttoInfoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoAccorpabileEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoDynamicForPublish;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoIndEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.MatriceAtti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PagamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicaSmartcigResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicazioneAttoResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicitaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RecordFaseImpreseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SchedaSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SmartCigEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.StatoCig;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoIstatEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.UffEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.W9AssociazioneCampiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.AggiudicatarioEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.AllegatoAttiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.AppaFornEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.AppaLavEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.CategoriaLottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.CpvLottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.DatiGeneraliTecnicoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ImpresaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.LoginResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.MotivazioneProceduraNegoziataEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaAttoServerEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaLottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaSmartCigEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicazioneBandoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicazioneResult;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.LegaleRappresentantePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.AttoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.AttoUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.CambioReferenteForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.CentroDiCostoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.CredenzialiSimog;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseAvanzamentoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseConclusioneInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseInizialeEsecuzioneInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.GaraInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.GaraSmartCigUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.GaraUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.GareNonPaginateSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.GareSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.ImpersonificaRupForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.ImpresaAggiudicatariaAttoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.ImpreseAggiudicatarieAttoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.LottoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.LottoSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.LottoUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.MigrazioneGaraForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.PubblicitaGaraInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.RupGaraUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.SmartCigInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.SmartCigUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.GeneralBaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseAccorpaMultilotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseAnagraficaGaraPubblicata;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseAtti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseAttiAssociatiLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseAttiLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDatiContabilita;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDettaglioAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDettaglioGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDettaglioLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDettaglioSmartCig;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseDto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseGaraSmartCig;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseInizImportSmartcig;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseInizInsertLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseInsert;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaCollaborazioni;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaGare;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaGareNonPaginata;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaInviiFasi;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaLotti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaPubblicazioneAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaPubblicazioneFase;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseListaPubblicazioneGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseLottoAccorpabile;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseMatriceAtti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseMultilotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePubbAtti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePubblicaSmartCig;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponsePubblicitaGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseRequestPubblicazione;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseRequestPubblicazioneAtti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseRequestPubblicazioneSmartCig;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseResultCsv;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseRiepilogoAccorpamenti;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.ResponseUpdateRupGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.DelegheMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.SchedeMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.CsvUtil;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.SicrawebRestClient;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaBudgetImpegniResponse;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaBudgetImpegniResponse.ListaBudget;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaMandatiResponse;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.RicercaMandatiResponse.Mandato;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity.SicrawebServiceConfig;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.Collaborazione;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.AnacRestClientManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaveConfigurazione;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaviAccesso;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaComunicazioneResult;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Manager per la gestione della business logic.
 * 
 * @author Michele.DiNapoli
 */
@Component(value = "contrattiManager")
@Transactional(propagation = Propagation.SUPPORTS)
@SuppressWarnings("java:S2229")
public class ContrattiManager extends AbstractManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(ContrattiManager.class);
	/**
	 * Dao MyBatis con le primitive di estrazione dei dati.
	 */
	public static final String APPLICATION_CODE = "W9";	
	public static final String PROP_SIMOG_WS_SMARTCIG_URL = "it.eldasoft.simog.ws.smartcig.url";
	public static final String PROP_SIMOG_WS_URL = "it.eldasoft.simog.ws.url";
	public static final String PROP_PCP_WS_URL = "ws.inviopcp.url";

	private final static Pattern UUID_REGEX_PATTERN =
	        Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
	
	@Autowired
	AnacRestClientManager anacRestClientManager;
	
	@Autowired
	ContrattiRestManager contrattiRestClientManager;

	@Autowired
	private SqlMapper sqlMapper;
	
	@Autowired
	private DelegheMapper delegheMapper;

	@Autowired
	private SchedeMapper schedeMapper;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private SicrawebRestClient sicrawebRestClient;

	public ResponseInsert insertGara(GaraInsertForm form) {
		ResponseInsert risultato = new ResponseInsert();
		try {
			Long codgara = this.wgcManager.getNextId("W9GARA");
			form.setCodgara(codgara);
			form.setNumLotti(0L);
			form.setProvenienzaDato(1L);			
			this.contrattiMapper.insertGara(form); 				
			risultato.setData(codgara);
		} catch (Exception e) {
			logger.error("Errore in insert gara");
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertSmartCig(SmartCigInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {

			List<String> errorParam = new ArrayList<String>();
			String saCig = this.contrattiMapper.countCigBySa(form.getSmartCig());			
			if (saCig != null) {
				if(saCig.equals(form.getStazioneAppaltante())) {
					risultato.setEsito(false);
					errorParam.add(form.getSmartCig());
					risultato.setErrorDataParameters(errorParam);
					risultato.setErrorData("ERRORS.ERROR-CIG-GIA-INSERITO");
					return risultato;				
				}else {
					String nomeSa = this.contrattiMapper.getNominativoSa(saCig);		
					risultato.setEsito(false);
					errorParam.add(form.getSmartCig());
					errorParam.add(nomeSa);
					risultato.setErrorDataParameters(errorParam);
					risultato.setErrorData("ERRORS.ERROR-CIG-GIA-INSERITO-SA");
					return risultato;
				}
			}						

			Long codgara = this.wgcManager.getNextId("W9GARA");
			GaraInsertForm insertGaraForm = new GaraInsertForm();
			insertGaraForm.setCodgara(codgara);
			insertGaraForm.setIdentificativoGara("0");
			insertGaraForm.setNumLotti(1L);
			insertGaraForm.setImportoGara(form.getImportoTotale());
			// insertGaraForm.setProvenienzaDato("Inserimento manuale SmartCIG");
			insertGaraForm.setOggetto(form.getOggetto());
			insertGaraForm.setTipoApp(form.getModalitaRealizzazione());
			insertGaraForm.setCodiceTecnico(form.getRup());
			insertGaraForm.setCodiceStazioneAppaltante(form.getStazioneAppaltante());
			insertGaraForm.setIdUfficio(form.getIdUfficio());
			insertGaraForm.setIndirizzoSede(form.getIndirizzoSede());
			insertGaraForm.setComuneSede(form.getComuneSede());
			insertGaraForm.setProvinciaSede(form.getProvinciaSede());
			insertGaraForm.setFlagSaAgente(form.getFlagSaAgente());
			insertGaraForm.setDenomSoggStazioneAppaltante(form.getDenomSoggStazioneAppaltante());
			insertGaraForm.setCfAgenteStazioneAppaltante(form.getCfAgenteStazioneAppaltante());
			insertGaraForm.setTipologiaProcedura(form.getTipologiaProcedura());
			insertGaraForm.setTipologiaStazioneAppaltante(form.getTipologiaStazioneAppaltante());
			insertGaraForm.setFlagStipula(form.getFlagStipula());
			insertGaraForm.setSituazione(1L);
			insertGaraForm.setSyscon(form.getSyscon());
			insertGaraForm.setProvenienzaDato(4L);
			insertGaraForm.setTipoSettore(form.getTipoSettore());
			this.contrattiMapper.insertGara(insertGaraForm);

			LottoInsertForm insertLottoForm = new LottoInsertForm();

			Long codLotto = 1L;

			insertLottoForm.setCodGara(codgara);
			insertLottoForm.setCodLotto(codLotto);
			String smartCig = form.getSmartCig();
			if (StringUtils.isNotBlank(smartCig)) {
				smartCig = smartCig.toUpperCase();
			}
			insertLottoForm.setCig(smartCig);
			insertLottoForm.setOggetto(form.getOggetto());
			insertLottoForm.setTipologia(form.getTipoAppalto());
			insertLottoForm.setSceltaContraente(form.getSceltaContraente());
			insertLottoForm.setCriteriAggiudicazione(form.getCriteriAggiudicazione());
			insertLottoForm.setTipoSettore(form.getTipoSettore());
			insertLottoForm.setRup(form.getRup());
			insertLottoForm.setImportoNetto(form.getImportoNetto());
			insertLottoForm.setImportoSicurezza(form.getImportoSicurezza());
			insertLottoForm.setImportoTotale(form.getImportoTotale());

			insertLottoForm.setEsenteCup(form.getEsenteCup());
			insertLottoForm.setCup(form.getCup());
			insertLottoForm.setCpv(form.getCpv());
			insertLottoForm.setNumLotto(1L);
			insertLottoForm.setCategoriaPrev(form.getCategoriaPrev());
			insertLottoForm.setClasseCategoriaPrev(form.getClasseCategoriaPrev());
			insertLottoForm.setLuogoIstat(form.getLuogoIstat());
			insertLottoForm.setLuogoNuts(form.getLuogoNuts());
			insertLottoForm.setSituazione(1L);
			this.contrattiMapper.insertLotto(insertLottoForm);

			if (form.getDataVerbInizio() != null) {
				FaseInizialeEsecuzioneInsertForm formIniz = new FaseInizialeEsecuzioneInsertForm();
				formIniz.setCodGara(codgara);
				formIniz.setCodLotto(codLotto);
				formIniz.setNum(1L);
				formIniz.setNumAppa(getNumAppa(codgara, codLotto, false));
				formIniz.setDataVerbInizio(form.getDataVerbInizio());
				contrattiMapper.insertFaseInizialeEsecuzione(formIniz);

			}
			if (form.getDataUltimazione() != null) {
				FaseConclusioneInsertForm formConclusione = new FaseConclusioneInsertForm();
				formConclusione.setCodGara(codgara);
				formConclusione.setCodLotto(codLotto);
				formConclusione.setNum(1L);
				formConclusione.setNumAppa(getNumAppa(codgara, codLotto, false));
				formConclusione.setDataUltimazione(form.getDataUltimazione());
				contrattiMapper.insertFaseConclusioneContratto(formConclusione);
			}

			if (form.getImportoCertificato() != null) {
				FaseAvanzamentoInsertForm formAvanzamento = new FaseAvanzamentoInsertForm();
				formAvanzamento.setCodGara(codgara);
				formAvanzamento.setCodLotto(codLotto);
				formAvanzamento.setNum(1L);
				formAvanzamento.setNumAppa(getNumAppa(codgara, codLotto, false));
				formAvanzamento.setImportoCertificato(form.getImportoCertificato());
				contrattiMapper.insertFaseAvanzamento(formAvanzamento);
			}

			// update daExport lotto
			this.contrattiMapper.updateDaExportLotto(codgara, codLotto, "1");

			risultato.setData(codgara);
		} catch (Exception e) {
			logger.error("Errore in insert gara -SMART CIG", e);
			throw e;
		}
		return risultato;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponsePubblicaSmartCig updatePubblicaSmartCig(String authorization, SmartCigUpdateForm form,Long syscon, String cf) throws Exception {
		ResponsePubblicaSmartCig risultato = new ResponsePubblicaSmartCig();
		risultato.setEsito(true);
		BaseResponse response = updateSmartCig(form);
		PubblicaSmartcigResult pubbSmartcigResult = new PubblicaSmartcigResult();
		if(response.isEsito()) {
			Long codgara = form.getCodGara();
			pubbSmartcigResult.setCodGara(codgara);
			this.contrattiMapper.setLottiDaexport(3L, codgara);
			PubblicaSmartCigEntry requestSmartCig = getRequestPubblicazioneSmartCig(codgara,syscon,cf).getData();
			pubbSmartcigResult.setCreate(true);
			LoginResult responseLogin = this.contrattiRestClientManager.loginPubblicazioni(getChiaviAccessoORT(), authorization);
			SmartCigEntry entry = getSmartCig(codgara,syscon,cf);
			if (responseLogin != null && responseLogin.isEsito()) {
				String token = responseLogin.getToken();
				PubblicazioneResult pubbResult = this.contrattiRestClientManager.pubblicaSmartcig(requestSmartCig, false, authorization, token);
				if(pubbResult != null && pubbResult.isEsito()) {
					pubbSmartcigResult.setPublished(true);
					BaseResponse responseAllineamento = this.allineaPubblicazioneGara(codgara, entry.getStazioneAppaltante(), pubbResult.getId(), 1L, entry.getSyscon(), new ObjectMapper().writeValueAsString(requestSmartCig.getSmartcig()));
					if(responseAllineamento.isEsito()) {
						risultato.setData(pubbSmartcigResult);
						return risultato;
					} else {
						logger.error("ATTENZIONE : Smartcig pubblicato ma allineamento fallito. Codgara="+codgara);
						pubbSmartcigResult.setPublished(false);
						risultato.setData(pubbSmartcigResult);
						return risultato;
					}
					
				} else {
					pubbSmartcigResult.setPublished(false);
					risultato.setData(pubbSmartcigResult);
					return risultato;
				}

			} else {
				pubbSmartcigResult.setPublished(false);
				risultato.setData(pubbSmartcigResult);
				return risultato;
			}
			
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(response.getErrorData());
			risultato.setErrorDataParameters(response.getErrorDataParameters());
			pubbSmartcigResult.setCreate(false);
			pubbSmartcigResult.setPublished(false);
			risultato.setData(pubbSmartcigResult);
			return risultato;
		}
	}
	
	
	
	
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponsePubblicaSmartCig pubblicaSmartCig(String authorization, SmartCigInsertForm form, Long syscon, String cf) throws Exception {
		ResponsePubblicaSmartCig risultato = new ResponsePubblicaSmartCig();
		risultato.setEsito(true);
		ResponseInsert responseInsert = insertSmartCig(form);
		PubblicaSmartcigResult pubbSmartcigResult = new PubblicaSmartcigResult();
		if(responseInsert.isEsito()) {
			Long codgara = responseInsert.getData();
			pubbSmartcigResult.setCodGara(codgara);
			this.contrattiMapper.setLottiDaexport(3L, codgara);
			PubblicaSmartCigEntry requestSmartCig = getRequestPubblicazioneSmartCig(responseInsert.getData(),syscon, cf).getData();
			pubbSmartcigResult.setCreate(true);
			LoginResult responseLogin = this.contrattiRestClientManager.loginPubblicazioni(getChiaviAccessoORT(), authorization);
			if (responseLogin != null && responseLogin.isEsito()) {
				String token = responseLogin.getToken();
				PubblicazioneResult pubbResult = this.contrattiRestClientManager.pubblicaSmartcig(requestSmartCig, false, authorization, token);
				if(pubbResult != null && pubbResult.isEsito()) {
					pubbSmartcigResult.setPublished(true);
					BaseResponse responseAllineamento = this.allineaPubblicazioneGara(codgara, form.getStazioneAppaltante(), pubbResult.getId(), 1L, form.getSyscon(), new ObjectMapper().writeValueAsString(requestSmartCig.getSmartcig()));
					if(responseAllineamento.isEsito()) {
						risultato.setData(pubbSmartcigResult);
						return risultato;
						
					} else {
						logger.error("ATTENZIONE : Smartcig pubblicato ma allineamento fallito. Codgara="+codgara);
						pubbSmartcigResult.setPublished(false);
						risultato.setData(pubbSmartcigResult);
						return risultato;
					}
					
				} else {
					pubbSmartcigResult.setPublished(false);
					risultato.setData(pubbSmartcigResult);
					return risultato;
				}

			} else {
				pubbSmartcigResult.setPublished(false);
				risultato.setData(pubbSmartcigResult);
				return risultato;
			}
			
		} else {
			risultato.setEsito(false);
			risultato.setErrorData(responseInsert.getErrorData());
			risultato.setErrorDataParameters(responseInsert.getErrorDataParameters());
			pubbSmartcigResult.setCreate(false);
			pubbSmartcigResult.setPublished(false);
			risultato.setData(pubbSmartcigResult);
			return risultato;
		}
	}
	
	

	public ResponseListaGare searchGare(GareSearchForm form, String authorization, String loginMode) {

		ResponseListaGare risultato = new ResponseListaGare();
		try {
			
			// Estrazione Cod Fiscale
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
			String cf = userAuthClaimsDTO.getCf();
			Long syscon = userAuthClaimsDTO.getSyscon();
			boolean cfNull = false;
			String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");	
			
						
			List<GaraEntry> gare = new ArrayList<GaraEntry>();
			// Controllo il ruolo dell'utente. Se non è amministratore le gare vanno
			// filtrate per il codice
			// RUP associato al suo record della tabella tecni
			form.setSyscon(syscon);
			String ruolo = this.contrattiMapper.getRuolo(form.getSyscon());
			if("*".equals(form.getStazioneAppaltante())) {
				form.setStazioneAppaltante(null);
			}
			form.setCfTecnico(StringUtils.isNotBlank(cf) ? cf.toUpperCase() : null);
			String cfTecnico = form.getCfTecnico();
			if ("A".equals(ruolo) || "C".equals(ruolo)) {
				form.setCfTecnico(null);
			} else{
				if(StringUtils.isBlank(cfTecnico)) {
					cfNull = true;
				}
			}
			// Formatto i campi per la ricerca in like
			RowBounds rowBounds = new RowBounds(form.getSkip(), form.getTake());
			if (form.getSearchString() != null) {
				form.setSearchString("%" + form.getSearchString().toUpperCase() + "%");
			}
			if (form.getOggetto() != null) {
				form.setOggetto("%" + form.getOggetto().toUpperCase() + "%");
			}
			if (form.getCigLotto() != null) {
				form.setCigLotto("%" + form.getCigLotto().toUpperCase() + "%");
			}
			if (form.getOggettoLotto() != null) {
				form.setOggettoLotto("%" + form.getOggettoLotto().toUpperCase() + "%");
			}
			if (form.getCupLotto() != null) {
				form.setCupLotto("%" + form.getCupLotto().toUpperCase() + "%");
			}
			if (form.getNumGara() != null) {
				form.setNumGara("%" + form.getNumGara() + "%");
			}
			form.setCfNull(cfNull);
			int totalCount = 0;
			totalCount = this.contrattiMapper.countSearchGare(form);
			if("ORA".equals(dbms)) {
				gare = this.contrattiMapper.searchGareOracle(form, rowBounds);
			} else {
				gare = this.contrattiMapper.searchGare(form, rowBounds);
			}
			
			for (GaraEntry gara : gare) {
				
				RupEntry tecnico = null;
				if (gara.getRup() != null) {
					tecnico = this.contrattiMapper.getTecnico(gara.getRup());
					gara.setTecnico(tecnico);
				}
				
				boolean readOnly = false;
				if(tecnico != null && StringUtils.isNotBlank(tecnico.getCf())) {
					if (StringUtils.isNotBlank(cf) && cf.equalsIgnoreCase(tecnico.getCf()) && !"A".equals(ruolo) && !"C".equals(ruolo)) {					
						DelegaEntry collaboratore = this.delegheMapper.getDelegaByCfrupCodeinSyscon(tecnico.getCf(),gara.getCodiceStazioneAppaltante(),form.getSyscon());					
						if(collaboratore != null && collaboratore.getRuolo() != null && collaboratore.getRuolo() == 3) {
							 readOnly = true;
						}
					}
				}
				
				List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(gara.getCodgara());
				Long numPubblicazioni = this.contrattiMapper.esistonoPubblicazioniGara(gara.getCodgara());
				boolean deletable = (lottiGara == null || lottiGara.size() == 0)
						&& (numPubblicazioni == null || numPubblicazioni == 0L) && !readOnly;
				if(lottiGara != null && lottiGara.size() == 1) {
					gara.setCodlott(Long.valueOf(lottiGara.get(0).getCodLotto()));
				}
				gara.setNominativoStazioneAppaltante(this.contrattiMapper.getNominativoSa(gara.getCodiceStazioneAppaltante()));
				gara.setDeletable(deletable);
				gara.setTrasferimentoRUP(isUserAdminOrRUPGara(ruolo, cfTecnico, gara));
				String cigLotti = gara.getCigLotti() == null ? "" : gara.getCigLotti() ;
				boolean isSmartCig = false;
				if (lottiGara != null && lottiGara.size() == 1
						&& (cigLotti.startsWith("X") || cigLotti.startsWith("Y") || cigLotti.startsWith("Z"))) {
					isSmartCig = true;
					gara.setDeletable(numPubblicazioni == 0L  && !readOnly);
				}
				gara.setSmartCig(isSmartCig);
				
				
				
			}
			risultato.setTotalCount(totalCount);
			risultato.setData(gare);
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione della lista gare:" + "stazioneAppaltante: "
					+ form.getStazioneAppaltante() + "codiceRup: " + form.getCfTecnico(), t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}
	
	public ResponseResultCsv exportListaGare(GareSearchForm form, String authorization, String loginMode) {

		ResponseResultCsv risultato = new ResponseResultCsv();
		
		try {
			// Estrazione Cod Fiscale
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
			String cf = userAuthClaimsDTO.getCf();
			Long syscon = userAuthClaimsDTO.getSyscon();
			boolean cfNull = false;
			List<GaraEntry> gare = new ArrayList<GaraEntry>();

			if("*".equals(form.getStazioneAppaltante())) {
				form.setStazioneAppaltante(null);
			}

			String ruolo = this.contrattiMapper.getRuolo(form.getSyscon());

			form.setSyscon(syscon);
			form.setCfTecnico(StringUtils.isNotBlank(cf) ? cf.toUpperCase() : null);
			String cfTecnico = form.getCfTecnico();
			if ("A".equals(ruolo) || "C".equals(ruolo)) {
				form.setCfTecnico(null);
			} else{
				if(StringUtils.isBlank(cfTecnico)) {
					cfNull = true;
				}
			}
			// Formatto i campi per la ricerca in like			
			if (form.getSearchString() != null) {
				form.setSearchString("%" + form.getSearchString().toUpperCase() + "%");
			}
			if (form.getOggetto() != null) {
				form.setOggetto("%" + form.getOggetto().toUpperCase() + "%");
			}
			if (form.getCigLotto() != null) {
				form.setCigLotto("%" + form.getCigLotto().toUpperCase() + "%");
			}
			if (form.getOggettoLotto() != null) {
				form.setOggettoLotto("%" + form.getOggettoLotto().toUpperCase() + "%");
			}
			if (form.getCupLotto() != null) {
				form.setCupLotto("%" + form.getCupLotto().toUpperCase() + "%");
			}
			if (form.getNumGara() != null) {
				form.setNumGara("%" + form.getNumGara() + "%");
			}
			form.setCfNull(cfNull);
			String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");	
			if("ORA".equals(dbms)) {
				gare = this.contrattiMapper.searchGareAllOracle(form);

			} else {
				gare = this.contrattiMapper.searchGareAll(form);
			}
			if(gare != null && gare.size() > 0) {
				risultato.setRowNumber(Long.valueOf(gare.size()));
				if(gare.size() > 1000) {
					gare = gare.subList(0, 1000);
				}
				if(form.getStazioneAppaltante() != null) {
					String nominativo = this.contrattiMapper.getNominativoSa(form.getStazioneAppaltante());
					for (GaraEntry gara : gare) {																			
						gara.setNominativoStazioneAppaltante(nominativo);									
					}
				}
						
				String csvFile = extractCsv(gare,form.getStazioneAppaltante());			
				risultato.setData(csvFile);
				risultato.setEsito(true);
			} else {
				risultato.setRowNumber(0L);
				risultato.setData("");
				risultato.setEsito(true);
			}
			
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione della lista gare:" + "stazioneAppaltante: "
					+ form.getStazioneAppaltante() + "codiceRup: " + form.getCfTecnico(), t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	public ResponseDettaglioGara dettaglioGara(Long codGara, String authorization, String loginMode) {

		ResponseDettaglioGara risultato = new ResponseDettaglioGara();
		try {
			
			// Estrazione Cod Fiscale
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, loginMode);
			String cf = userAuthClaimsDTO.getCf();
			Long syscon = userAuthClaimsDTO.getSyscon();
			String ruolo = this.contrattiMapper.getRuolo(syscon);
			
			// Estrazione gara
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
					
			if(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
				gara.setPcp(true);
			} else {
				gara.setPcp(false);
			}
			
			Long countLotti = this.contrattiMapper.countLotti(codGara);
			if(countLotti!= null && countLotti == 1) {
				gara.setCodlott(this.contrattiMapper.getCodLott(codGara));
			}
			// Estrazione Tecnico
			RupEntry tecnico = null;
			if (gara.getCodiceTecnico() != null) {
				tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
				gara.setTecnico(tecnico);
			}
			// Estrazione Delegato
			RupEntry drp = null;
			if (gara.getDrp() != null) {
				drp = this.contrattiMapper.getTecnico(gara.getDrp());
				gara.setDelegato(drp);
			}
			// Estrazione ufficio
			if (gara.getIdUfficio() != null) {
				UffEntry ufficio = this.contrattiMapper.getUfficio(gara.getIdUfficio());
				gara.setIdUfficio(gara.getIdUfficio());
				gara.setUfficio(ufficio);
			}

			if (StringUtils.isNotBlank(gara.getCodiceStazioneAppaltante())) {
				gara.setNominativoStazioneAppaltante(
						this.contrattiMapper.getNominativoSa(gara.getCodiceStazioneAppaltante()));
			}
			
			PubblicitaGaraEntry pubblicitaGara = this.contrattiMapper.getPubblicitaGara(codGara);
			if(pubblicitaGara != null) {
				gara.setPubblicitaGara(pubblicitaGara);
			}
			
			// Estrazione centri di costo e stazione appaltante
			if (gara.getIdCentroDiCosto() != null && StringUtils.isNotBlank(gara.getCodiceStazioneAppaltante())) {
				CentriCostoEntry infocdc = this.contrattiMapper.getCentroCosto(gara.getIdCentroDiCosto());
				if (infocdc != null) {
					gara.setNominativoCentroCosto(infocdc.getNominativoCDC());
					gara.setCodiceCentroCosto(infocdc.getCodiceCDC());
					gara.setCentroDicosto(infocdc);
				}
			}

			List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(gara.getCodgara());
			boolean smartCig = false;
			if (lottiGara != null && lottiGara.size() == 1 && lottiGara.get(0).getCig()!= null && (lottiGara.get(0).getCig().startsWith("X")
					|| lottiGara.get(0).getCig().startsWith("Y") || lottiGara.get(0).getCig().startsWith("Z"))) {
				smartCig = true;

			}
			
			
			List<LottoFullEntry> lotti = this.contrattiMapper.getFullLottiGara(codGara);

			Boolean visibleDataLetteraInvito = false;
			Boolean perfezionata = false;
			Boolean sceltaContra4 = false;
			gara.setSceltaContraenteValorizzata(false);
			gara.setRiallineaVisible(true);
			
			gara.setExistsFasiNonPubb(this.contrattiMapper.checkExistsFasiNonInviate(codGara) > 0 ? true : false);
								
			for (LottoFullEntry lottoFullEntry : lotti) {
				if(lottoFullEntry.getSceltaContraente() != null) {
					if(lottoFullEntry.getSceltaContraente() == 4) {
						sceltaContra4 = true;
					}
					if(lottoFullEntry.getSceltaContraente() != null && (lottoFullEntry.getSceltaContraente() == 2 || lottoFullEntry.getSceltaContraente() == 4 || lottoFullEntry.getSceltaContraente() == 8 || lottoFullEntry.getSceltaContraente() == 9 ||
					   lottoFullEntry.getSceltaContraente() == 13 || lottoFullEntry.getSceltaContraente() == 25 || lottoFullEntry.getSceltaContraente() == 28 ||
					   lottoFullEntry.getSceltaContraente() == 29 || lottoFullEntry.getSceltaContraente() == 30 ||	lottoFullEntry.getSceltaContraente() == 34)) {
						visibleDataLetteraInvito = true;
					}
					if(lottoFullEntry.getSceltaContraente() != null && (lottoFullEntry.getSceltaContraente() == 2 || lottoFullEntry.getSceltaContraente() == 8 || lottoFullEntry.getSceltaContraente() == 9 ||
					   lottoFullEntry.getSceltaContraente() == 13 || lottoFullEntry.getSceltaContraente() == 25 || lottoFullEntry.getSceltaContraente() == 28 ||
					   lottoFullEntry.getSceltaContraente() == 29 || lottoFullEntry.getSceltaContraente() == 30 ||	lottoFullEntry.getSceltaContraente() == 34)) {
						perfezionata = true;				
					}
					if(lottoFullEntry.getSceltaContraente() != null) {
						gara.setSceltaContraenteValorizzata(true);
					}
				}
							
			}
			
			
			gara.setVisibleDataLetteraInvito(visibleDataLetteraInvito);
			gara.setSceltaContra4(sceltaContra4);
			
			if(gara.getIdFDelegate() != null) {
				String idFDelegateDescription = this.contrattiMapper.getValoreTabellato("W3038", gara.getIdFDelegate());
				if(idFDelegateDescription != null) {
					gara.setIdFDelegateDescription(idFDelegateDescription);
				}
			}
			
			if(gara.getDataPubblicazione() == null || (perfezionata && gara.getDataLetteraInvito() == null && gara.getVersioneSimog() > 1)) {
				gara.setPerfezionata(true);
			} else {
				gara.setPerfezionata(false);
			}
			
								
			gara.setReadOnly(false);
			if(tecnico != null && StringUtils.isNotBlank(tecnico.getCf())) {
				if (StringUtils.isNotBlank(cf) && !cf.equalsIgnoreCase(tecnico.getCf()) && !"A".equals(ruolo) && !"C".equals(ruolo)) {					
					DelegaEntry collaboratore = this.delegheMapper.getDelegaByCfrupCodeinSyscon(tecnico.getCf(),gara.getCodiceStazioneAppaltante(),syscon);					
					if(collaboratore != null && collaboratore.getRuolo() != null && collaboratore.getRuolo() == 3) {
						gara.setReadOnly(true);
					}
					if(collaboratore != null && collaboratore.getRuolo()!= null) {
						gara.setRuoloCollaboratore(Long.valueOf(collaboratore.getRuolo()));
					}
				}
			}
								
			gara.setSmartCig(smartCig);
			risultato.setData(gara);
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione del dettaglio della gara: " + codGara, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	public ResponseDettaglioSmartCig dettaglioSmartCig(Long codGara, Long syscon, String cf) {

		ResponseDettaglioSmartCig risultato = new ResponseDettaglioSmartCig();
		try {								
			SmartCigEntry entry = getSmartCig(codGara,syscon,cf);
			risultato.setData(entry);
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione del dettaglio della gara - SMARTCIG: " + codGara, t);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	private SmartCigEntry getSmartCig(Long codGara, Long syscon,String cf) {
		// Estrazione gara
		GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
		// Estrazione lotto
		LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, 1L);
		Long daExport = this.contrattiMapper.getLottoDaexport(codGara);
		SmartCigEntry entry = new SmartCigEntry();
		entry.setCodGara(codGara);
		entry.setOggetto(gara.getOggetto());
		entry.setModalitaRealizzazione(gara.getTipoApp());
		entry.setIdRicevuto(gara.getIdRicevuto());
		entry.setDaExport(daExport);
		// Estrazione Tecnico
		RupEntry tecnico = null;
		if (gara.getCodiceTecnico() != null) {
			tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
			entry.setRup(tecnico);
		}

		// Estrazione ufficio
		if (gara.getIdUfficio() != null) {
			UffEntry ufficio = this.contrattiMapper.getUfficio(gara.getIdUfficio());
			entry.setIdUfficio(gara.getIdUfficio());
			entry.setUfficio(ufficio);
		}

		if (gara.getCodiceStazioneAppaltante() != null) {
			entry.setNominativoStazioneAppaltante(
					this.contrattiMapper.getNominativoSa(gara.getCodiceStazioneAppaltante()));
		}
		FaseInizialeEsecuzioneEntry faseIniziale = this.contrattiMapper.getFaseInizialeEsecuzione(codGara, 1L, 1L);
		FaseConclusioneEntry faseConclusione = this.contrattiMapper.getFaseConclusioneContratto(codGara, 1L, 1L);
		FaseAvanzamentoEntry faseAvanzamento = this.contrattiMapper.getFaseAvanzamento(codGara, 1L, 1L);
		entry.setStazioneAppaltante(gara.getCodiceStazioneAppaltante());
		entry.setIndirizzoSede(gara.getIndirizzoSede());
		entry.setComuneSede(gara.getComuneSede());
		entry.setProvinciaSede(gara.getProvinciaSede());
		entry.setFlagSaAgente(gara.getFlagSaAgente());
		entry.setDenomSoggStazioneAppaltante(gara.getDenomSoggStazioneAppaltante());
		entry.setCfAgenteStazioneAppaltante(gara.getCfAgenteStazioneAppaltante());
		entry.setTipologiaProcedura(gara.getTipologiaProcedura());
		entry.setTipologiaStazioneAppaltante(gara.getTipologiaStazioneAppaltante());
		entry.setFlagStipula(gara.getFlagStipula());
		entry.setImportoTotale(gara.getImportoGara());
		entry.setSmartCig(lotto.getCig());
		entry.setTipoAppalto(lotto.getTipologia());
		entry.setSceltaContraente(lotto.getSceltaContraente());
		entry.setCriteriAggiudicazione(lotto.getCriteriAggiudicazione());
		entry.setTipoSettore(gara.getTipoSettore());
		entry.setImportoNetto(lotto.getImportoNetto());
		entry.setImportoSicurezza(lotto.getImportoSicurezza());
		entry.setEsenteCup(lotto.getEsenteCup());
		entry.setCup(lotto.getCup());
		entry.setCpv(lotto.getCpv());
		if (entry.getCpv() != null && !entry.getCpv().equals("")) {
			entry.setDescCpv(this.contrattiMapper.getCpvDesc(entry.getCpv()));
		}
		entry.setSituazione(gara.getSituazione());
		entry.setImportoSicurezza(lotto.getImportoSicurezza());
		entry.setCategoriaPrev(lotto.getCategoriaPrev());
		entry.setClasseCategoriaPrev(lotto.getClasseCategoriaPrev());
		entry.setLuogoIstat(lotto.getLuogoIstat());
		entry.setLuogoNuts(lotto.getLuogoNuts());
		entry.setAutore(sqlMapper.getNameBySyscon(syscon));
		if (faseConclusione != null) {
			entry.setDataUltimazione(faseConclusione.getDataUltimazione());
		}
		if (faseIniziale != null) {
			entry.setDataVerbInizio(faseIniziale.getDataVerbInizio());
		}
		if (faseAvanzamento != null) {
			entry.setImportoCertificato(faseAvanzamento.getImportoCertificato());
		}
		
		entry.setReadOnly(false);
		if(tecnico != null && StringUtils.isNotBlank(tecnico.getCf())) {
			String ruolo = this.contrattiMapper.getRuolo(syscon);
			if (StringUtils.isNotBlank(cf) && !cf.equals(tecnico.getCf()) && !"A".equals(ruolo) && !"C".equals(ruolo)) {					
				DelegaEntry collaboratore = this.delegheMapper.getDelegaByCfrupCodeinSyscon(tecnico.getCf(),gara.getCodiceStazioneAppaltante(),syscon);					
				if(collaboratore != null && collaboratore.getRuolo() != null && collaboratore.getRuolo() == 3) {
					entry.setReadOnly(true);
				}
			}
		}
		return entry;
	}

	public ResponseGaraSmartCig isGaraSmartCig(final Long codGara) {

		ResponseGaraSmartCig risultato = new ResponseGaraSmartCig();
		risultato.setData(false);
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(gara.getCodgara());

			String cigLotti = "";
			int counter = 1;

			for (LottoBaseEntry lotto : lottiGara) {
				cigLotti += counter == lottiGara.size() ? lotto.getCig() : lotto.getCig() + " - ";
				counter++;
			}

			risultato.setData(lottiGara != null && lottiGara.size() == 1
					&& (cigLotti.startsWith("X") || cigLotti.startsWith("Y") || cigLotti.startsWith("Z")));
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione della gara: " + codGara, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	public BaseResponse updateGara(GaraUpdateForm form) {

		BaseResponse risultato = new BaseResponse();
		try {

			Long tipoApp = form.getTipoApp();
			Set<Long> fasiSet = new HashSet<Long>();

			List<Long> lottiIds = this.contrattiMapper.getIdLottiGara(form.getCodGara());
			for (Long idLotto : lottiIds) {
				Long numAppa = getNumAppa(form.getCodGara(), idLotto, false);
				List<FaseEntry> fasiLotto = this.contrattiMapper.getFasiLotto(form.getCodGara(), idLotto, numAppa);
				for (FaseEntry f : fasiLotto) {
					fasiSet.add(f.getFase());
				}
			}
			if (tipoApp != null) {

				if ((tipoApp == 11L && (fasiSet.contains(1L) || fasiSet.contains(987L)))
						|| (tipoApp != 11L && (fasiSet.contains(12L)))
						|| ((tipoApp == 9L || tipoApp == 17L || tipoApp == 18L)
								&& (fasiSet.contains(2L) || fasiSet.contains(986L)))
						|| ((tipoApp != 9L && tipoApp != 17L && tipoApp != 18L) && fasiSet.contains(11L))) {
					risultato.setEsito(false);
					risultato.setErrorData("TIPO_APP_NON_COMPATIBILE");
					return risultato;
				}

			}
			this.contrattiMapper.updateGara(form);
			
			PubblicitaGaraEntry pubblicitaGara = this.contrattiMapper.getPubblicitaGara(form.getCodGara());
			if(form.getPubblicitaGara() != null) {			
				if(pubblicitaGara != null) {			
					form.getPubblicitaGara().setCodiceGara(form.getCodGara());
					this.contrattiMapper.updatePubblicitaGara(form.getPubblicitaGara());
				} else {
					form.getPubblicitaGara().setCodiceGara(form.getCodGara());
					this.contrattiMapper.insertPubblicitaGara(form.getPubblicitaGara());
				}
			}
			
			if (form.getCodiceTecnico() != null) {
				this.contrattiMapper.setRupLotti(form.getCodGara(), form.getCodiceTecnico());
			}

			// update daExport lotti
			this.contrattiMapper.updateDaExportLotti(form.getCodGara(), "1");
			this.contrattiMapper.updateTipoSettoreLotti(form.getCodGara(), form.getTipoSettore());
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'update della gara: " + form.getCodGara(), t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateSmartCig(SmartCigUpdateForm form) throws Exception {

		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			GaraSmartCigUpdateForm updateGaraForm = new GaraSmartCigUpdateForm();
			updateGaraForm.setCodGara(form.getCodGara());
			updateGaraForm.setOggetto(form.getOggetto());
			updateGaraForm.setImportoGara(form.getImportoTotale());
			updateGaraForm.setTipoApp(form.getModalitaRealizzazione());
			updateGaraForm.setCodiceTecnico(form.getRup());
			updateGaraForm.setIdUfficio(form.getIdUfficio());
			updateGaraForm.setIndirizzoSede(form.getIndirizzoSede());
			updateGaraForm.setComuneSede(form.getComuneSede());
			updateGaraForm.setProvinciaSede(form.getProvinciaSede());
			updateGaraForm.setFlagSaAgente(form.getFlagSaAgente());
			updateGaraForm.setDenomSoggStazioneAppaltante(form.getDenomSoggStazioneAppaltante());
			updateGaraForm.setCfAgenteStazioneAppaltante(form.getCfAgenteStazioneAppaltante());
			updateGaraForm.setTipologiaProcedura(form.getTipologiaProcedura());
			updateGaraForm.setTipologiaStazioneAppaltante(form.getTipologiaStazioneAppaltante());
			updateGaraForm.setFlagStipula(form.getFlagStipula());
			updateGaraForm.setTipoSettore(form.getTipoSettore());
			this.contrattiMapper.updateGaraSmartCig(updateGaraForm);
			LottoInsertForm insertLottoForm = new LottoInsertForm();

			Long codLotto = 1L;
			Long codGara = form.getCodGara();

			insertLottoForm.setCodGara(form.getCodGara());
			insertLottoForm.setCodLotto(codLotto);
			String smartCig = form.getSmartCig();
			if (StringUtils.isNotBlank(smartCig)) {
				smartCig = smartCig.toUpperCase();
			}
			insertLottoForm.setCig(smartCig);
			insertLottoForm.setOggetto(form.getOggetto());
			insertLottoForm.setTipologia(form.getTipoAppalto());
			insertLottoForm.setSceltaContraente(form.getSceltaContraente());
			insertLottoForm.setCriteriAggiudicazione(form.getCriteriAggiudicazione());
			insertLottoForm.setTipoSettore(form.getTipoSettore());
			insertLottoForm.setRup(form.getRup());
			insertLottoForm.setImportoNetto(form.getImportoNetto());
			insertLottoForm.setImportoSicurezza(form.getImportoSicurezza());
			insertLottoForm.setImportoTotale(form.getImportoTotale());
			insertLottoForm.setEsenteCup(form.getEsenteCup());
			insertLottoForm.setCup(form.getCup());
			insertLottoForm.setCpv(form.getCpv());
			insertLottoForm.setCategoriaPrev(form.getCategoriaPrev());
			insertLottoForm.setClasseCategoriaPrev(form.getClasseCategoriaPrev());
			insertLottoForm.setLuogoIstat(form.getLuogoIstat());
			insertLottoForm.setLuogoNuts(form.getLuogoNuts());
			insertLottoForm.setNumLotto(1L);
			this.contrattiMapper.updateLottoSmartCig(insertLottoForm);

			if (form.getDataVerbInizio() != null) {
				FaseInizialeEsecuzioneInsertForm formIniz = new FaseInizialeEsecuzioneInsertForm();
				formIniz.setCodGara(codGara);
				formIniz.setCodLotto(codLotto);
				formIniz.setNum(1L);
				formIniz.setNumAppa(getNumAppa(codGara, codLotto, false));
				formIniz.setDataVerbInizio(form.getDataVerbInizio());
				if (contrattiMapper.getFaseInizialeEsecuzione(codGara, 1L, 1L) == null) {
					contrattiMapper.insertFaseInizialeEsecuzione(formIniz);
				} else {
					contrattiMapper.updateFaseInizialeEsecuzione(formIniz);
				}
			} else {
				if (contrattiMapper.getFaseInizialeEsecuzione(codGara, 1L, 1L) != null) {
					this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, 1L, 1L);
				}
			}

			if (form.getDataUltimazione() != null) {
				FaseConclusioneInsertForm formConclusione = new FaseConclusioneInsertForm();
				formConclusione.setCodGara(codGara);
				formConclusione.setCodLotto(codLotto);
				formConclusione.setNum(1L);
				formConclusione.setNumAppa(getNumAppa(codGara, codLotto, false));
				formConclusione.setDataUltimazione(form.getDataUltimazione());
				if (contrattiMapper.getFaseConclusioneContratto(codGara, 1L, 1L) == null) {
					contrattiMapper.insertFaseConclusioneContratto(formConclusione);
				} else {
					contrattiMapper.updateFaseConclusioneContratto(formConclusione);
				}
			} else {
				if (contrattiMapper.getFaseConclusioneContratto(codGara, 1L, 1L) != null) {
					this.contrattiMapper.deleteFaseConclusioneContratto(codGara, 1L, 1L);
				}
			}

			if (form.getImportoCertificato() != null) {
				FaseAvanzamentoInsertForm formAvanzamento = new FaseAvanzamentoInsertForm();
				formAvanzamento.setCodGara(codGara);
				formAvanzamento.setCodLotto(codLotto);
				formAvanzamento.setNum(1L);
				formAvanzamento.setNumAppa(getNumAppa(codGara, codLotto, false));
				formAvanzamento.setImportoCertificato(form.getImportoCertificato());
				if (contrattiMapper.getFaseAvanzamento(codGara, 1L, 1L) == null) {
					contrattiMapper.insertFaseAvanzamento(formAvanzamento);
				} else {
					contrattiMapper.updateFaseAvanzamento(formAvanzamento);
				}
			} else {
				if (contrattiMapper.getFaseAvanzamento(codGara, 1L, 1L) != null) {
					this.contrattiMapper.deleteFaseAvanzamento(codGara, 1L, 1L);
				}
			}

			// update daExport lotto
			this.contrattiMapper.updateDaExportLotto(form.getCodGara(), codLotto, "1");
		} catch (Exception e) {
			logger.error("Errore in update SmartCig", e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteGara(Long codGara) throws Exception {
		BaseResponse risultato = new BaseResponse();
		try {
			this.contrattiMapper.deleteGara(codGara);
			this.contrattiMapper.deletePubblicitaDiGara(codGara);
			this.contrattiMapper.deleteAttiGara(codGara);
			this.contrattiMapper.deleteDocumentiAttiGara(codGara);
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante la delete della gara: " + codGara, e);
			throw e;
		}
		return risultato;
	}

	// LOTTI

	public ResponseListaLotti getListaLotti(LottoSearchForm searchForm) {

		ResponseListaLotti risultato = new ResponseListaLotti();
		try {

			int totalCount = this.contrattiMapper.countSearchLotti(searchForm);
			if (searchForm.getTake() == 0) {
				searchForm.setSkip(0);
				searchForm.setTake(totalCount);
			}

			RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
			risultato.setTotalCount(totalCount);
			List<LottoBaseEntry> lotti = this.contrattiMapper.searchLotti(searchForm, rowBounds);
			for (LottoBaseEntry lotto : lotti) {

				boolean deletable = ((lotto.getNumFasi() == null || lotto.getNumFasi() == 0) && (lotto.getNumPubblicazioneAtti() == null || lotto.getNumPubblicazioneAtti() == 0));
				lotto.setDeletable(deletable);
				if (lotto.getMasterCig() != null) {
					lotto.setLottiAccorpati(this.contrattiMapper.getLottiAccorpati(lotto.getMasterCig()));
				}

			}
			risultato.setData(lotti);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get dei lotti.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	
	
	
	public ResponseResult getTracciatoFlusso(Long idFlusso) {

		ResponseResult risultato = new ResponseResult();
		try {
			String xml = this.contrattiMapper.getXmlFlusso(idFlusso);
			if(xml == null) {
				xml = "Flusso non presente";
			}
			risultato.setData(xml);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la getTracciatoFlusso.", t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	

	public ResponseInizInsertLotto getInizInsertLotto() {
		ResponseInizInsertLotto risultato = new ResponseInizInsertLotto();
		try {
			List<TabellatoEntry> tabellatoModAcq = this.contrattiMapper.getModalitaAcquisizioneTab();
			List<TabellatoEntry> tabellatoModTip = this.contrattiMapper.getModalitaTipologiaLavoroTab();
			List<TabellatoEntry> tabellatoCondizioni = this.contrattiMapper.getCondizioniRicorsoTab();
			risultato.setCondizioniRicorsoProcNeg(tabellatoCondizioni);
			risultato.setModalitaAcquisizione(tabellatoModAcq);
			risultato.setModalitaTipologiaLavoro(tabellatoModTip);
		} catch (Exception ex) {
			logger.error("Errore inaspettato durante la get dei campi dinamici per la insert lotto", ex);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertLotto(LottoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		try {
			Long codLotto = this.contrattiMapper.getMaxCodLotto(form.getCodGara());
			if (codLotto == null) {
				codLotto = 1L;
			} else {
				codLotto++;
			}
			form.setCodLotto(codLotto);
			this.contrattiMapper.insertLotto(form);
			this.contrattiMapper.updateNlottoGara(form.getCodGara());
			risultato.setData(codLotto);
			Long counter = 1L;
			for (LottoDynamicValue v : form.getModalitaAcquisizione()) {

				if (v.getValue() != null && v.getValue() == 1L) {
					this.contrattiMapper.insertModalitaAcquisizione(form.getCodGara(), form.getCodLotto(), counter,
							v.getCodice());
					counter++;
				}
			}
			counter = 1L;
			for (LottoDynamicValue v : form.getModalitaTipologiaLavoro()) {
				if (v.getValue() != null && v.getValue() == 1L) {
					this.contrattiMapper.insertModalitaTipologiaLavoroLotto(form.getCodGara(), form.getCodLotto(),
							counter, v.getCodice());
					counter++;
				}
			}

			for (LottoDynamicValue v : form.getCondizioniRicorsoProcNeg()) {
				if (v.getValue() != null && v.getValue() == 1L) {
					this.contrattiMapper.insertCondizioniRicorsoLotto(form.getCodGara(), form.getCodLotto(),
							v.getCodice());
				}
			}
			counter = 1L;
			for (CPVSecondario c : form.getCpvSecondari()) {
				if (c.getCodCpv() != null && !"".equals(c.getCodCpv())) {
					this.contrattiMapper.insertCpvSecondariLotto(form.getCodGara(), form.getCodLotto(), counter,
							c.getCodCpv());
					counter++;
				}
			}
			Long numCate = 1L;
			for (CategoriaLotto c : form.getUlterioriCategorie()) {
				this.contrattiMapper.insertCategorieLotto(form.getCodGara(), form.getCodLotto(), numCate,
						c.getCategoria(), c.getClasse(), c.getScorporabile(), c.getSubappaltabile());
				numCate++;
			}
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore in insert lotto", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteSmartcig(Long codGara) throws Exception {
		BaseResponse risultato = new BaseResponse();
		try {
			this.contrattiMapper.deleteAssociazioniLottoAtto(codGara, 1L);
			this.contrattiMapper.deleteLotto(codGara, 1L);
			this.contrattiMapper.deleteAttiGara(codGara);
			this.contrattiMapper.deleteDocumentiAttiGara(codGara);
			this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, 1L, 1L);
			this.contrattiMapper.deleteFaseConclusioneContratto(codGara, 1L, 1L);
			this.contrattiMapper.deleteFaseAvanzamento(codGara, 1L, 1L);
			this.contrattiMapper.deleteAllImpreseFase(codGara, 1L);
			this.deleteGara(codGara);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la cancellazione dello smartCig : codgara = " + codGara, e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteLotto(Long codGara, Long codLotto) throws Exception {
		BaseResponse risultato = new BaseResponse();
		try {
			// Cancello tutti i record delle collezioni collegate al lotto per poi
			this.contrattiMapper.deleteModalitaAcquisizioneLotto(codGara, codLotto);
			this.contrattiMapper.deleteModalitaTipologiaLavoroLotto(codGara, codLotto);
			this.contrattiMapper.deleteCondizioniRicorsoLotto(codGara, codLotto);
			this.contrattiMapper.deleteCpvSecondariLotto(codGara, codLotto);
			this.contrattiMapper.deleteCategorieLotto(codGara, codLotto);
			this.contrattiMapper.deleteAssociazioniLottoAtto(codGara, codLotto);
			this.contrattiMapper.deleteLotto(codGara, codLotto);
			this.contrattiMapper.deleteW9LottoCup(codGara, codLotto);
			this.contrattiMapper.updateNlottoGara(codGara);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la cancellazione del lotto: codgara = " + codGara + " codLotto="
					+ codLotto, e);
			throw e;
		}
		return risultato;

	}

	public ResponseAttiAssociatiLotto getAttiAssociatiLotto(Long codGara, Long codLotto) {

		ResponseAttiAssociatiLotto risultato = new ResponseAttiAssociatiLotto();
		List<FullDettaglioAttoEntry> atti = new ArrayList<FullDettaglioAttoEntry>();
		try {
			List<Long> idAtti = this.contrattiMapper.getAttiLotto(codGara, codLotto);
			List<CondizioneAtto> condizioniAtti = this.contrattiMapper.getAllAtti();
			for (Long id : idAtti) {
				FullDettaglioAttoEntry atto = this.contrattiMapper.getAtto(codGara, id);
				for (CondizioneAtto c : condizioniAtti) {
					if (c.getId() == atto.getTipDoc()) {
						atto.setCampiObbligatori(c.getCampiObbligatori());
						atto.setCampiVisibili(c.getCampiVisibili());
						atto.setTipDocDesc(c.getNome());
					}
				}
				atti.add(atto);
			}
			risultato.setData(atti);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la get deglio atti di un lotto: codgara = " + codGara
					+ " codLotto=" + codLotto, e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateLotto(LottoUpdateForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		try {

			GaraEntry gara = this.contrattiMapper.dettaglioGara(form.getCodGara());
			// set rup lotto preso dalla gara
			form.setRup(gara.getCodiceTecnico());

			this.contrattiMapper.updateLotto(form);

			// Cancello tutti i record delle collezioni collegate al lotto per poi
			// reinserire quelle passate dal client.
			// Questo approccio è utilizzato per evitare complessità nell'algoritmo di
			// ricerca delle sottocollezioni cancellate/modificate/inserite
			this.contrattiMapper.deleteModalitaAcquisizioneLotto(form.getCodGara(), form.getCodLotto());
			this.contrattiMapper.deleteModalitaTipologiaLavoroLotto(form.getCodGara(), form.getCodLotto());
			this.contrattiMapper.deleteCondizioniRicorsoLotto(form.getCodGara(), form.getCodLotto());
			this.contrattiMapper.deleteCpvSecondariLotto(form.getCodGara(), form.getCodLotto());
			this.contrattiMapper.deleteCategorieLotto(form.getCodGara(), form.getCodLotto());
			Long counter = 1L;
			// Inserisco tutti i record delle tabelle collegate al lotto
			for (LottoDynamicValue v : form.getModalitaAcquisizione()) {

				if (v.getValue() != null && v.getValue() == 1L) {
					this.contrattiMapper.insertModalitaAcquisizione(form.getCodGara(), form.getCodLotto(), counter,
							v.getCodice());
					counter++;
				}
			}
			counter = 1L;
			for (LottoDynamicValue v : form.getModalitaTipologiaLavoro()) {
				if (v.getValue() != null && v.getValue() == 1L) {
					this.contrattiMapper.insertModalitaTipologiaLavoroLotto(form.getCodGara(), form.getCodLotto(),
							counter, v.getCodice());
					counter++;
				}
			}

			for (LottoDynamicValue v : form.getCondizioniRicorsoProcNeg()) {
				if (v.getValue() != null && v.getValue() == 1L) {
					this.contrattiMapper.insertCondizioniRicorsoLotto(form.getCodGara(), form.getCodLotto(),
							v.getCodice());
				}
			}
			counter = 1L;
			for (CPVSecondario c : form.getCpvSecondari()) {
				if (c.getCodCpv() != null && !"".equals(c.getCodCpv())) {
					this.contrattiMapper.insertCpvSecondariLotto(form.getCodGara(), form.getCodLotto(), counter,
							c.getCodCpv());
					counter++;
				}
			}
			Long numCate = 1L;
			for (CategoriaLotto c : form.getUlterioriCategorie()) {
				this.contrattiMapper.insertCategorieLotto(form.getCodGara(), form.getCodLotto(), numCate,
						c.getCategoria(), c.getClasse(), c.getScorporabile(), c.getSubappaltabile());
				numCate++;
			}

			// update daExport lotto
			this.contrattiMapper.updateDaExportLotto(form.getCodGara(), form.getCodLotto(), "1");

			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante la modifica del lotto: codgara = " + form.getCodGara()
					+ " codLotto=" + form.getNumLotto(), e);
			throw e;
		}
		return risultato;

	}

	public ResponseDettaglioLotto dettaglioLotto(Long codGara, Long numLotto) {

		ResponseDettaglioLotto risultato = new ResponseDettaglioLotto();
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, numLotto);


			if (lotto.getCpv() != null && !lotto.getCpv().equals("")) {
				String descCpv = this.contrattiMapper.getCpvDesc(lotto.getCpv());
				if(StringUtils.isNotBlank(descCpv)) {
					lotto.setDescCpv(descCpv);
				} else {
					String cpvLike = lotto.getCpv() + "%";
					List<String> descCpvList = this.contrattiMapper.getCpvDescLike(cpvLike);
					if(descCpvList != null && descCpvList.size() > 0 && descCpvList.get(0) != null) {
						lotto.setDescCpv(descCpvList.get(0));
					}
				}
						
			}
			// Reperisco le collezioni dinamiche associate al lotto e corrispondenti a
			// valori di SI/NO associate ai tabellati
			List<LottoDynamicValue> modalitaAcq = getModalitaAcquisizione(codGara, numLotto);
			List<LottoDynamicValue> modalitaTip = getModalitaTipologiaLavoro(codGara, numLotto);
			List<LottoDynamicValue> condizioniRicorso = getCondizioniRicorso(codGara, numLotto);
			List<CPVSecondario> cpvSecondari = this.contrattiMapper.getCpvSecondariLotto(codGara, numLotto);
			List<CategoriaLotto> categorieLotto = this.contrattiMapper.getCategorieLotto(codGara, numLotto);
			List<String> cupList = this.contrattiMapper.getCupFromW9lottcup(codGara, numLotto);
			String listaCup = null;
			if(cupList != null && cupList.size() > 0) {
				listaCup = cupList.stream()
				  .collect(Collectors.joining("; "));
			}
			lotto.setListaCup(listaCup);
			lotto.setModalitaAcquisizione(modalitaAcq);
			lotto.setModalitaTipologiaLavoro(modalitaTip);
			lotto.setCondizioniRicorsoProcNeg(condizioniRicorso);
			lotto.setCpvSecondari(cpvSecondari);
			lotto.setUlterioriCategorie(categorieLotto);
			
			
			Double importoTotale = 0D;
			if(lotto.getMasterCig() != null) {
				List<LottoBaseEntry> cigAccorpati = this.contrattiMapper.getLottiAccorpati(lotto.getCig());
				for (LottoBaseEntry lottoBaseEntry : cigAccorpati) {
					importoTotale = importoTotale + lottoBaseEntry.getImportoNetto();
				}
			} else {
				importoTotale = lotto.getImportoNetto();
			}
			Long tipologia = null;
			if(lotto.getTipologia() != null) {
				if(lotto.getTipologia().equals("L")) {
					tipologia = 1L;
				} else if(lotto.getTipologia().equals("F") || lotto.getTipologia().equals("S")) {
					if (StringUtils.isNotBlank(gara.getTipoSettore())){
						if (gara.getTipoSettore().equals("O")) {
							tipologia = 2L;
						} else if(gara.getTipoSettore().equals("S")) {
							tipologia = 3L;
						}
					}					
				}
			}
			
			lotto.setHasAggiudicazione(false);
			Long CountFaseAgg = this.contrattiMapper.countFaseAggiudicazione(codGara, numLotto, 1L);
			if(CountFaseAgg != null && CountFaseAgg > 0) {
				lotto.setHasAggiudicazione(true);
			}
			
			
			Double importoSottoSoglia = 0D;
			String sottoSogliaTabellato = this.contrattiMapper.getValoreTabellato("W9030", tipologia);
			if(sottoSogliaTabellato != null) {
				importoSottoSoglia = Double.valueOf(StringUtils.trim(sottoSogliaTabellato.split("Euro")[0]));
				if(importoSottoSoglia == null) {
					importoSottoSoglia = 0D;
				}
			}
			if(importoTotale > importoSottoSoglia) {
				lotto.setSottoSoglia(false);
			} else {
				lotto.setSottoSoglia(true);
			}
			
			risultato.setData(lotto);
			risultato.setEsito(true);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione del dettaglio del lotto: codgara = " + codGara
					+ " codLotto=" + numLotto, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	private List<LottoDynamicValue> getModalitaAcquisizione(Long codGara, Long numLotto) {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			// Trovo la lista completa del tabellato
			List<TabellatoEntry> tabellatoModAcq = this.contrattiMapper.getModalitaAcquisizioneTab();
			// Cerco le chiavi del tabellato con valore SI
			List<Long> codiciModAcq = this.contrattiMapper.getModalitaAcquisizioneLotto(codGara, numLotto);
			// Preparo la lista di valori tabellati con value = 1 (SI) associato ai codici
			// trovati nella precedente chiamata
			for (TabellatoEntry e : tabellatoModAcq) {
				if (e.getArchiviato() == null || e.getArchiviato() != 1L) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciModAcq.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}
		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio del lotto - modalita acquisizione: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;
	}

	private List<LottoDynamicValue> getModalitaTipologiaLavoro(Long codGara, Long numLotto) {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			// Trovo la lista completa del tabellato
			List<TabellatoEntry> tabellatoModTip = this.contrattiMapper.getModalitaTipologiaLavoroTab();
			// Cerco le chiavi del tabellato con valore SI
			List<Long> codiciModTip = this.contrattiMapper.getModalitaTipologiaLavoroLotto(codGara, numLotto);
			// Preparo la lista di valori tabellati con value = 1 (SI) associato ai codici
			// trovati nella precedente chiamata
			for (TabellatoEntry e : tabellatoModTip) {
				if (e.getArchiviato() == null || e.getArchiviato() != 1L) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciModTip.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio del lotto - modalita tipologia lavoro: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;

	}

	private List<LottoDynamicValue> getCondizioniRicorso(Long codGara, Long numLotto) {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			// Trovo la lista completa del tabellato
			List<TabellatoEntry> tabellatoCondizioni = this.contrattiMapper.getCondizioniRicorsoTab();
			// Cerco le chiavi del tabellato con valore SI
			List<Long> codiciCondizioni = this.contrattiMapper.getModalitaCondizioniRicorsoLotto(codGara, numLotto);
			// Preparo la lista di valori tabellati con value = 1 (SI) associato ai codici
			// trovati nella precedente chiamata
			for (TabellatoEntry e : tabellatoCondizioni) {
				LottoDynamicValue v = new LottoDynamicValue();
				v.setCodice(e.getCodice());
				v.setDescrizione(e.getDescrizione());
				v.setValue(codiciCondizioni.contains(e.getCodice()) ? 1L : 2L);
				risultato.add(v);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio del lotto - modalita condizioni ricorso: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;

	}

	public ResponseAtti getListaAtti(Long codGara) {
		ResponseAtti risultato = new ResponseAtti();
		List<AttoEntry> atti = new ArrayList<AttoEntry>();
		try {
			// Lista di tutti gli atti possibili con condizione di visibilità. La colonna a
			// db contiene la where da applicare alla query
			List<CondizioneAtto> condizioniAtti = this.contrattiMapper.getAllAttiCompleta(codGara);
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			for (CondizioneAtto c : condizioniAtti) {

				// applico le condizioni di visibilità dell'atto per la gara e lotto. Se il
				// risultato non è 0 l'atto è visibile.
				int hasAtto = this.contrattiMapper.hasAtto(codGara, c.getCondizione());
				if (hasAtto != 0 || c.getNumAtti() > 0) {
					AttoEntry a = new AttoEntry();
					a.setCampiVisibili(c.getCampiVisibili());
					a.setNome(c.getNome());
					a.setId(c.getId());
					a.setStatiId(getStatoAtto(codGara, c, gara));
					a.setTipo(c.getTipo());
					a.setCampiObbligatori(c.getCampiObbligatori());
					atti.add(a);
				}
			}
			risultato.setData(atti);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione degli atti di gara: codgara = " + codGara, t);
		}
		return risultato;
	}

	private StatiId getStatoAtto(Long codGara, CondizioneAtto c, GaraEntry gara) {
		StatiId risultato = new StatiId();

		Long tipoDocumento = c.getId();
		Long nrLotti = gara.getNumLotti() == null ? 0L : gara.getNumLotti();
		
		int numeroPubblicazioniTotali = c.getNumAtti();
		if (numeroPubblicazioniTotali > 0) {
			int nrPubblicazioni = c.getNumLottiConAtto();
			int nrFlussi = c.getNumLottiAttoPubblicato();
			
			
			if(c.getNumLottiAttoPubblicato() > 0) {
				if(c.getNumLottiAttoPubblicato() == gara.getNumLotti().intValue()) {
					risultato.setStato("Pubblicato");
				} else {
					risultato.setStato("PubblicatoParziale-"+ c.getNumLottiAttoPubblicato());
				}
			} else {
				if(c.getNumLottiConAtto() == gara.getNumLotti().intValue()) {
					risultato.setStato("Compilazione");
				} else {
					risultato.setStato("CompilazioneParziale-"+c.getNumLottiConAtto());
				}
			}
			List<IdPubblicati> idPubblicati = new ArrayList<IdPubblicati>();
			List<Long> ids = this.contrattiMapper.getIdPubblicazioniAtti(codGara, tipoDocumento);
			for (Long numPubb : ids) {
				List<Long> lottiIds = this.contrattiMapper.getAttoLotti(codGara, numPubb);
				IdPubblicati idp = new IdPubblicati();
				int countPubblicazioni = this.contrattiMapper.countPubblicazioniAtto(codGara, numPubb);
				idp.setId(numPubb);
				idp.setPubblicato(countPubblicazioni > 0);
				if(lottiIds != null && lottiIds.size() > 0) {
					idp.setParziale( lottiIds.size() != nrLotti);
					idp.setCountParziale(Long.valueOf(lottiIds.size()));
					idp.setTotalParziale(nrLotti);
				}
				ListaSingoloAttoInfoEntry singoloAttoEntry = this.contrattiMapper.getAttoInfo(codGara, numPubb);

				if (singoloAttoEntry != null) {
					idp.setDescrizione(singoloAttoEntry.getDescrizione());
					idp.setDataPubblicazione(singoloAttoEntry.getDataPubblicazione());
					idp.setPrimaPubblicazione(singoloAttoEntry.getPrimaPubblicazione());
					idp.setDataPubbSistema(singoloAttoEntry.getDataPubbSistema());
					idp.setUtenteCancellazione(singoloAttoEntry.getUtenteCancellazione());
					idp.setDataCancellazione(singoloAttoEntry.getDataCancellazione());
					idp.setMotivoCancellazione(singoloAttoEntry.getMotivoCancellazione());
					idp.setPubblicatoAtto(idp.getDataPubbSistema() != null && idp.getDataPubbSistema().before(new Date()));
					idp.setAnnullato(idp.getMotivoCancellazione() != null && idp.getDataCancellazione() != null && idp.getUtenteCancellazione() != null);
				}

				idPubblicati.add(idp);
			}

			risultato.setIds(idPubblicati);
		}
		return risultato;
	}

	public ResponseDettaglioAtto dettaglioAtto(Long codGara, Long tipoDocumento, Long numPubb) {

		ResponseDettaglioAtto risultato = new ResponseDettaglioAtto();
		try {
			DettaglioAttoEntry atto = this.contrattiMapper.getDettaglioAtto(codGara, tipoDocumento, numPubb);
			

			
			if (atto != null) {
				if(atto.getDataPubb() != null) {
					logger.debug("DETTAGLIO-ATTO: codgara : "+atto.getCodGara());
					logger.debug("DATA-PUBB:" + atto.getDataPubb().toString());
				}
				int countPubblicazioni = this.contrattiMapper.countPubblicazioniAtto(codGara, numPubb);
				Integer countLotti = this.contrattiMapper.countLottiGara(codGara);
				atto.setCountLotti(countLotti != null ? countLotti : 0);
				atto.setDeletable(countPubblicazioni == 0);

				List<AllegatoEntry> allegati = this.contrattiMapper.getListaAllegatiEntry(codGara.toString(), numPubb.toString());

				atto.setDocuments(allegati);
				risultato.setData(atto);
			}
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione dell'atto: codgara = " + codGara + " tipoDocumento="
					+ tipoDocumento + " numPubb=" + numPubb, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertAtto(AttoInsertForm form, String authorization, String xLoginMode) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		try {
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
			Long syscon = userAuthClaimsDTO.getSyscon();

			// Calcolo id univoco atto
			Long numPubb = this.contrattiMapper.getMaxNumPubb(form.getCodGara());
			if (numPubb == null) {
				numPubb = 1L;
			} else {
				numPubb++;
			}
			form.setNumPubb(numPubb);
			if(form.getDataPubb() != null) {
				logger.debug("INSERT-ATTO: codgara : "+form.getCodGara());
				logger.debug("DATA-PUBB:" + form.getDataPubb().toString());
			}
			
			// Inserisco atto
			this.contrattiMapper.insertAtto(form);
			Long numdoc = 1L;

			//INSERISCO ALLEGATI
			//Se presenti, vengono inseriti nella w9allegati
			if(form.getUpdateDocuments() != null) {

				for(AllegatoEntry allegato : form.getUpdateDocuments()){

					AllegatoEntry nuovoAllegato = new AllegatoEntry();

					Long chiaveAllegato = this.wgcManager.getNextId("W9ALLEGATI");

					nuovoAllegato.setIdAllegato(chiaveAllegato);
					nuovoAllegato.setKey01(form.getCodGara() != null ? form.getCodGara().toString() : null);
					nuovoAllegato.setKey02(String.valueOf(numPubb));
					nuovoAllegato.setKey03(null);
					nuovoAllegato.setTabella("W9PUBBLICAZIONI");
					nuovoAllegato.setDescrizione(allegato.getDescrizione());
					nuovoAllegato.setUtenteCreatore(syscon);
					nuovoAllegato.setTipoFile(allegato.getTipoFile());
					nuovoAllegato.setNumOrdine(0L);
					nuovoAllegato.setDataAggiunta(null);
					nuovoAllegato.setUtenteCanc(null);
					nuovoAllegato.setDataCanc(null);
					nuovoAllegato.setMotivoCanc(null);

					if(StringUtils.equals(form.getPrimaPubblicazione(), Constants.PRIMA_PUBBLICAZIONE_ATTO)){
						if (StringUtils.isNotBlank(allegato.getBinary())) {
							byte[] dedcodedFile = Base64.getDecoder().decode(allegato.getBinary().getBytes());
							nuovoAllegato.setFileAllegato(dedcodedFile);
						}
					}
					//PRIMA_PUBB = 2
					else {
						//Controllare validità URL, forse a FE in inserimento.
						nuovoAllegato.setUrl(allegato.getUrl());
					}

					this.contrattiMapper.insertAllegatoAtto(nuovoAllegato);
				}
			}

			// Se è specificato lato client uno o più codici lotto associo l'atto a quelli,
			// altrimenti lo associo a tutti i lotti della gara.
			if (form.getCodLotto() != null) {
				this.contrattiMapper.insertPubbLotto(form.getCodGara(), numPubb, form.getCodLotto());
			} else {
				List<Long> lottiIds = this.contrattiMapper.getIdLottiGara(form.getCodGara());
				for (Long idLotto : lottiIds) {
					this.contrattiMapper.insertPubbLotto(form.getCodGara(), numPubb, idLotto);
				}
			}
			risultato.setData(numPubb);
			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante la creazione dell'atto: codgara = " + form.getCodGara()
					+ " tipoDocumento=" + form.getTipDoc(), t);
			throw t;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateAtto(AttoUpdateForm form, String authorization, String xLoginMode) throws Exception {
		BaseResponse risultato = new BaseResponse();
		try {
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
			Long syscon = userAuthClaimsDTO.getSyscon();

			this.contrattiMapper.updateAtto(form);

			//GESTIONE ALLEGATI: Vengono gestiti solo l'aggiunta e l'aggiornamento di ogni allegato perché
			// la cancellazione è demandata tramite pulsante con altro metodo.
			List<AllegatoEntry> allegatiEsistenti = this.contrattiMapper.getListaAllegatiEntry(form.getCodGara().toString(), form.getNumPubb().toString());
			List<AllegatoEntry> daInserire = new ArrayList<>();
			List<AllegatoEntry> daAggiornare = new ArrayList<>();
			List<AllegatoEntry> daEliminare = new ArrayList<>();
			List<AllegatoMotivazioneEntry> daAnnullare = new ArrayList<>();

			for(AllegatoEntry allegatoEsistente : allegatiEsistenti){
				boolean isPresent = form.getDocuments().stream()
						.anyMatch(allegato ->
								allegato.getIdAllegato() != null &&
										allegato.getIdAllegato().toString().equals(allegatoEsistente.getIdAllegato().toString()) &&
										allegato.getKey01() != null &&
										allegato.getKey01().equals(allegatoEsistente.getKey01()) &&
										allegato.getTabella().equals("W9PUBBLICAZIONI") &&
										allegatoEsistente.getTabella().equals("W9PUBBLICAZIONI")
						);

				if(!isPresent && (form.getDataPubbSistema() == null || form.getDataPubbSistema().after(new Date()))){
					daEliminare.add(allegatoEsistente);
				}
			}

			for(AllegatoMotivazioneEntry allegato : form.getAllegatiDaAnnullare()){
				if(!StringUtils.isEmpty(allegato.getMotivoCanc())){
					daAnnullare.add(allegato);
				}
			}

			//Elimino gli allegati eliminati dall'utente
			for(AllegatoEntry allegatoDaEliminare : daEliminare){
				this.contrattiMapper.deleteAllegatiByCodGaraKey01(allegatoDaEliminare.getKey01());
			}

			//Questi allegati sono da "annullare". Si procede con un'eliminazione logica:
			//Vanno popolati i campi utenteCanc, dataCanc, motivoCanc. Questi campi indicano che l'allegato è stato eliminato,
			//ma non cancellato dalla w9allegati.
			for(AllegatoMotivazioneEntry allegatoDaAnnullare : daAnnullare){
				this.contrattiMapper.deleteLogicaAttoDocumento(allegatoDaAnnullare.getKey01(), allegatoDaAnnullare.getIdAllegato(), syscon, new Date(), allegatoDaAnnullare.getMotivoCanc());
			}

			if(!allegatiEsistenti.isEmpty() && form.getUpdateDocuments() != null && !form.getUpdateDocuments().isEmpty()) {

				for (AllegatoEntry nuovoAllegato : form.getUpdateDocuments()) {
					AllegatoEntry esistente = allegatiEsistenti.stream()
							.filter(allegato -> allegato.getIdAllegato() != null && allegato.getIdAllegato().equals(nuovoAllegato.getIdAllegato()))
							.findFirst()
							.orElse(null);

					if (esistente == null) {
						daInserire.add(nuovoAllegato);
					} else {
						//Controllo descrizione modificata
						if (!StringUtils.equals(esistente.getDescrizione(), nuovoAllegato.getDescrizione())) {
							daAggiornare.add(nuovoAllegato);
						}
						//Controllo URL modificato
						else if (!StringUtils.equals(esistente.getUrl(), nuovoAllegato.getUrl())) {
							daAggiornare.add(nuovoAllegato);
						}
						//Controllo file allegato modificato
						else if (Arrays.equals(esistente.getFileAllegato(), nuovoAllegato.getFileAllegato())) {
							daAggiornare.add(nuovoAllegato);
						}
					}
				}

				//Inserisco gli allegati nuovi
				for (AllegatoEntry nuovoAllegato : daInserire) {

					Long chiave = this.wgcManager.getNextId("W9ALLEGATI");

					AllegatoEntry nuovoAllegatoEntry = new AllegatoEntry();
					nuovoAllegatoEntry.setIdAllegato(chiave);
					nuovoAllegatoEntry.setKey01(form.getCodGara() != null ? form.getCodGara().toString() : null);
					nuovoAllegatoEntry.setKey02(form.getNumPubb() != null ? form.getNumPubb().toString() : null);
					nuovoAllegatoEntry.setKey03(null);
					nuovoAllegatoEntry.setTabella("W9PUBBLICAZIONI");
					nuovoAllegatoEntry.setDescrizione(nuovoAllegato.getDescrizione());
					nuovoAllegatoEntry.setUtenteCreatore(syscon);
					nuovoAllegatoEntry.setTipoFile(nuovoAllegato.getTipoFile());
					nuovoAllegatoEntry.setNumOrdine(0L);
					nuovoAllegatoEntry.setUtenteCanc(null);
					nuovoAllegatoEntry.setDataCanc(null);
					nuovoAllegatoEntry.setMotivoCanc(null);
					nuovoAllegatoEntry.setUrl(nuovoAllegato.getUrl());

					if(StringUtils.equals(form.getPrimaPubblicazione(), Constants.PRIMA_PUBBLICAZIONE_ATTO)){
						//Decode binary e setto fileAllegato
						if (StringUtils.isNotBlank(nuovoAllegato.getBinary())) {
							byte[] decodedFile = Base64.getDecoder().decode(nuovoAllegato.getBinary().getBytes());
							nuovoAllegatoEntry.setFileAllegato(decodedFile);
						}
					}

					if(form.getDataPubbSistema() != null && form.getDataPubbSistema().before(new Date())){
						nuovoAllegatoEntry.setDataAggiunta(new Date());

						this.contrattiMapper.pubblicaAttoSingolo(form.getCodGara(), form.getNumPubb(), new Date());
					}

					this.contrattiMapper.insertAllegatoAtto(nuovoAllegatoEntry);
				}

				//A partire dagli allegati esistenti.
				boolean toUpdate = false;
				for (AllegatoEntry allegatoEsistente : allegatiEsistenti) {
					//Aggiorno gli stessi con un UPDATE
					for (AllegatoEntry allegatoUpdate : daAggiornare) {

						if (Objects.equals(allegatoUpdate.getIdAllegato(), allegatoEsistente.getIdAllegato())) {

							if (allegatoUpdate.getUrl() != null) {
								allegatoEsistente.setUrl(allegatoUpdate.getUrl());
								toUpdate = true;
							}
							if (allegatoUpdate.getFileAllegato() != null) {
								allegatoEsistente.setFileAllegato(allegatoUpdate.getFileAllegato());
								toUpdate = true;
							}
							if (allegatoUpdate.getDescrizione() != null) {
								allegatoEsistente.setDescrizione(allegatoUpdate.getDescrizione());
								toUpdate = true;
							}
						}
					}
					if (toUpdate) {
						this.contrattiMapper.updateAllegatoAtto(allegatoEsistente);
					}
					toUpdate = false;
				}
			}

			if(form.getDataPubb() != null) {
				logger.debug("UPDATE-ATTO: codgara : "+form.getCodGara());
				logger.debug("DATA-PUBB:" + form.getDataPubb().toString());
			}

			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante l'update dell'atto: codgara = " + form.getCodGara()
					+ " tipoDocumento=" + form.getTipDoc() + " numPubb=" + form.getNumPubb(), t);
			throw t;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteAtto(Long codGara, Long numPubb) throws Exception {
		BaseResponse risultato = new BaseResponse();
		try {
			// Cancello atto e collezioni associate (documenti dell'atto e record di
			// associazione tra atto e lotti)
			this.contrattiMapper.deleteAtto(codGara, numPubb);
			this.contrattiMapper.deleteAttoDocuments(codGara.toString(), numPubb.toString());
			this.contrattiMapper.deletePubLotto(codGara, numPubb);
			this.contrattiMapper.deleteImpreseAggiudicatarieAtto(codGara, numPubb);
			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante la cancellazione dell'atto: codgara = " + codGara + " numpubb"
					+ numPubb, t);
			throw t;
		}
		return risultato;
	}

	public ResponseAttiLotto getAttoLotti(Long codGara, Long numPubb) {
		ResponseAttiLotto risultato = new ResponseAttiLotto();
		try {
			// Prendo la lista dei lotti di gara
			List<LottoBaseEntry> lotti = this.contrattiMapper.getLottiGara(codGara);
			// Prendo la lista dei lotti associati all'atto
			List<Long> lottiIds = this.contrattiMapper.getAttoLotti(codGara, numPubb);
			List<AttoLottoEntry> listaLotti = new ArrayList<AttoLottoEntry>();
			// Popolo la lista dei lotti per il client specificando se sia o meno associato
			// all'atto.
			for (LottoBaseEntry l : lotti) {
				AttoLottoEntry al = new AttoLottoEntry();
				al.setCig(l.getCig());
				al.setCodgara(l.getCodgara());
				al.setCodLotto(l.getCodLotto());
				al.setImportoNetto(l.getImportoNetto());
				al.setOggetto(l.getOggetto());
				al.setSituazione(l.getSituazione());
				al.setTipologia(l.getTipologia());
				al.setAssociato(lottiIds.contains(new Long(l.getCodLotto())));
				listaLotti.add(al);
			}
			risultato.setData(listaLotti);
			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante la get dei lotti associati dell'atto: codgara = " + codGara
					+ " numpubb" + numPubb, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse associaAttoLotti(Long codGara, Long numPubb, List<Long> codsLotto) throws Exception {
		ResponseAttiLotto risultato = new ResponseAttiLotto();
		try {
			// Cancella le pubblicazioni dell'atto per i lotti e le reinserisce con i record
			// aggiornati passati dal client
			this.contrattiMapper.deletePubLotto(codGara, numPubb);
			for (Long codLotto : codsLotto) {
				this.contrattiMapper.insertPubbLotto(codGara, numPubb, codLotto);
			}
			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante l'associazione dell'atto ai lotti: codGara = " + codGara
					+ " numpubb" + numPubb, t);
			throw t;
		}
		return risultato;
	}

	// PUBBLICITA GARA

	public ResponsePubblicitaGara getPubblicitaGara(Long codGara) {
		ResponsePubblicitaGara risultato = new ResponsePubblicitaGara();
		try {
			PubblicitaGaraEntry p = this.contrattiMapper.getPubblicitaGara(codGara);
			risultato.setData(p);
			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante l'estrazione della pubblicita. codGara = " + codGara, t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public BaseResponse insertPubblicitaGara(PubblicitaGaraInsertForm form) {
		BaseResponse risultato = new BaseResponse();
		try {
			PubblicitaGaraEntry p = this.contrattiMapper.getPubblicitaGara(form.getCodiceGara());
			if (p == null) {
				this.contrattiMapper.insertPubblicitaGara(form);
			} else {
				this.contrattiMapper.updatePubblicitaGara(form);
			}
			risultato.setEsito(true);

		} catch (Exception t) {
			logger.error("Errore inaspettato durante l'inserimento/update della pubblicita. codGara = "
					+ form.getCodiceGara(), t);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public String getRuolo(long syscon) {
		return this.contrattiMapper.getRuolo(syscon);
	}

	public List<String> getSAList(long syscon) {
		return this.contrattiMapper.getSAList(syscon);
	}

	public String getCodiceRup(long syscon) {
		return this.contrattiMapper.getCodiceRup(syscon);
	}

	public Long getSysconFromCf(String cf) {
		if(StringUtils.isNotBlank(cf)) {
			cf = cf.toUpperCase();
		}
		Long syscon =  this.contrattiMapper.getSysconFromCf(cf);
		if(syscon == null) {
			syscon = this.contrattiMapper.getSysconFromSysCf(cf);
		}
		return syscon;
	}

	public BaseResponse health() {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.health();

		} catch (Throwable t) {
			logger.error("Health: Errore inaspettato.", t);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED + ": " + t.getMessage());
		}
		return risultato;
	}

	// FASI


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseRequestPubblicazione getRequestPubblicazioneGara(Long codGara, Long syscon) throws Exception {
		ResponseRequestPubblicazione risultato = new ResponseRequestPubblicazione();
		risultato.setEsito(true);
		try {
			PubblicaGaraEntry garaRequest = new PubblicaGaraEntry();
			GaraFullEntry gara = this.dettaglioGaraCompleto(codGara);

			if (gara == null) {
				logger.error("Errore inaspettato in lettura dati gara per la pubblicazione. La gara " + codGara
						+ "non esiste.");
				risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND);
				risultato.setEsito(false);
				return risultato;
			}
			garaRequest.setAltreSA(gara.getAltreSA());
			// garaRequest.setAtti(atti);
			garaRequest.setCentraleCommittenza(gara.getFlagStipula() == null ? null : "" + gara.getFlagStipula());
			garaRequest.setCentroCosto(gara.getNominativoCentroCosto());
			garaRequest.setCfAgente(gara.getCfAgenteStazioneAppaltante());
			garaRequest.setCigAccQuadro(gara.getCigQuadro());
			garaRequest.setClientId("SitatSA");
			garaRequest.setCodiceCentroCosto(gara.getCodiceCentroCosto());
			garaRequest.setDataCreazione(gara.getDataCreazione());
			String cfSA = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
			garaRequest.setCodiceFiscaleSA(cfSA);
			garaRequest.setComune(gara.getComuneSede());
			garaRequest.setCriteriAmbientali(gara.getDispArtDlgs() == null ? null : "" + gara.getDispArtDlgs());
			garaRequest.setDataPerfezionamentoBando(gara.getDataPubblicazione());
			garaRequest.setDataPubblicazione(gara.getDataPubblicazione());
			garaRequest.setDataScadenza(gara.getDataScadenza());
			garaRequest.setDurataAccordoQuadro(gara.getDurataAcquadro());
			garaRequest.setId(null);
			garaRequest.setIdAnac(gara.getIdentificativoGara());
			garaRequest.setIdCentroCosto(null);
			garaRequest.setIdEnte(gara.getCodiceStazioneAppaltante());
			garaRequest.setIdRup(gara.getCodiceTecnico());
			garaRequest.setIdUfficio(gara.getIdUfficio());
			garaRequest.setImportoGara(gara.getImportoGara());
			garaRequest.setIndirizzo(gara.getIndirizzoSede());
			garaRequest.setModoIndizione(gara.getModalitaIndizione());
			garaRequest.setNomeSA(gara.getDenomSoggStazioneAppaltante());
			garaRequest.setOggetto(gara.getOggetto());
			garaRequest.setProvenienzaDato(gara.getProvenienzaDato());
			garaRequest.setProvincia(gara.getProvinciaSede());
			garaRequest.setRealizzazione(gara.getTipoApp());
			garaRequest.setRicostruzioneAlluvione(
					gara.getRicostruzioneAlluvione() == null ? null : "" + gara.getRicostruzioneAlluvione());
			garaRequest.setSaAgente(gara.getFlagSaAgente() == null ? null : "" + gara.getFlagSaAgente());
			garaRequest.setSettore(gara.getTipoSettore());
			garaRequest.setSisma(gara.getSisma() == null ? null : "" + gara.getSisma());
			garaRequest.setSituazione(gara.getSituazione());
			garaRequest.setSommaUrgenza(gara.getSommaUrgenza() == null ? null : "" + gara.getSommaUrgenza());
			garaRequest.setSyscon(gara.getSyscon());
			if (gara.getTecnico() != null) {
				garaRequest.setTecnicoRup(mappingRup(gara.getTecnico()));
			}
			garaRequest.setTipoProcedura(
					gara.getTipologiaProcedura() == null ? null : new Long(gara.getTipologiaProcedura()));
			garaRequest.setTipoSA(gara.getTipologiaStazioneAppaltante());
			garaRequest.setUfficio(gara.getNominativoUfficio());
			garaRequest.setVersioneSimog(gara.getVersioneSimog());
			garaRequest.setIdRicevuto(gara.getIdRicevuto());
			garaRequest.setDataLetteraInvito(gara.getDataLetteraInvito());
			garaRequest.setAutore(sqlMapper.getNameBySyscon(syscon));
			List<PubblicaLottoEntry> lotti = listaLottiCompleto(codGara);
			garaRequest.setLotti(lotti);
			garaRequest.setModalitaIndizioneAllegato9(gara.getModalitaIndizioneAllegato9());
			garaRequest.setMotivoSommaUrgenza(gara.getMotivoSommaUrgenza());
			PubblicazioneBandoEntry pubblicazioneBando = getPubblicitaGaraForPublish(codGara);
			garaRequest.setPubblicazioneBando(pubblicazioneBando);
			risultato.setData(garaRequest);

		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati gara per la pubblicazione. codgara = " + codGara, e);
			throw e;
		}
		return risultato;
	}

	private PubblicazioneBandoEntry getPubblicitaGaraForPublish(Long codGara) {
		PubblicazioneBandoEntry risultato = null;

		PubblicitaGaraEntry pubblicita = this.contrattiMapper.getPubblicitaGara(codGara);
		if (pubblicita != null) {
			risultato = new PubblicazioneBandoEntry();
			risultato.setDataAlbo(pubblicita.getAlboPretorioComuni());
			risultato.setDataBore(pubblicita.getDataBollettino());
			risultato.setDataGuce(pubblicita.getGazzettaUffEuropea());
			risultato.setDataGuri(pubblicita.getGazzettaUffItaliana());
			risultato.setIdGara(null);
			risultato.setPeriodici(pubblicita.getNumPeriodici());
			risultato.setProfiloCommittente(pubblicita.getProfiloCommittente());
			risultato.setProfiloInfTrasp(pubblicita.getSitoMinisteriInfr());
			risultato.setProfiloOsservatorio(pubblicita.getSitoOsservatorioContratti());
			risultato.setQuotidianiLocali(pubblicita.getNumQuotidianiLocali());
			risultato.setQuotidianiNazionali(pubblicita.getNumQuotidianiNazionali());
		}
		return risultato;
	}

	private GaraFullEntry dettaglioGaraCompleto(Long codGara) {

		GaraFullEntry gara = new GaraFullEntry();
		try {
			// Estrazione gara
			gara = this.contrattiMapper.dettaglioGaraCompleto(codGara);
			// Estrazione Tecnico
			if (gara.getCodiceTecnico() != null) {
				RupEntry tecnico = this.contrattiMapper.getTecnico(gara.getCodiceTecnico());
				gara.setTecnico(tecnico);
			}
			// Estrazione ufficio
			if (gara.getIdUfficio() != null) {
				String nominativoUfficio = this.contrattiMapper.getNomeUfficio(gara.getIdUfficio());
				gara.setNominativoUfficio(nominativoUfficio);
			}

			if (StringUtils.isNotBlank(gara.getCodiceStazioneAppaltante())) {
				gara.setNominativoStazioneAppaltante(
						this.contrattiMapper.getNominativoSa(gara.getCodiceStazioneAppaltante()));
			}

			// Estrazione centri di costo e stazione appaltante
			if (gara.getIdCentroDiCosto() != null && StringUtils.isNotBlank(gara.getCodiceStazioneAppaltante())) {
				CentriCostoEntry infocdc = this.contrattiMapper.getCentroCosto(gara.getIdCentroDiCosto());
				if (infocdc != null) {
					gara.setNominativoCentroCosto(infocdc.getNominativoCDC());
					gara.setCodiceCentroCosto(infocdc.getCodiceCDC());
					gara.setCentroDicosto(infocdc);
				}
			}

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione del dettaglio della gara: " + codGara, t);
			return null;
		}
		return gara;

	}

	private List<PubblicaLottoEntry> listaLottiCompleto(Long codGara) {
		List<PubblicaLottoEntry> risultato = new ArrayList<PubblicaLottoEntry>();
		try {

			List<LottoFullEntry> lotti = this.contrattiMapper.getFullLottiGara(codGara);
			for (LottoFullEntry lotto : lotti) {

				Long codLotto = new Long(lotto.getCodLotto());

				PubblicaLottoEntry lottoForPublish = mappingLotto(lotto);
				// Reperisco le collezioni dinamiche associate al lotto e corrispondenti a
				// valori di SI/NO associate ai tabellati
				List<CPVSecondario> cpvSecondari = this.contrattiMapper.getCpvSecondariLotto(codGara, codLotto);
				lottoForPublish.setCpvSecondari(mappingCpvSecondari(codGara, codLotto, cpvSecondari));
				lottoForPublish
						.setModalitaAcquisizioneForniture(mappingModalitaAcquisizioneForniture(codGara, codLotto));
				lottoForPublish.setTipologieLavori(mappingTipologieLavori(codGara, codLotto));
				lottoForPublish.setMotivazioniProceduraNegoziata(mappingModalitaRicorso(codGara, codLotto));
				lottoForPublish.setCategorie(mappingUlterioriCategorie(codGara, codLotto));
				risultato.add(lottoForPublish);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio di lotti per la pubblicazione: " + codGara,
					t);
			return null;
		}
		return risultato;
	}

	private List<CategoriaLottoEntry> mappingUlterioriCategorie(Long codGara, Long codLotto) {

		List<CategoriaLotto> categorie = this.contrattiMapper.getCategorieLotto(codGara, codLotto);
		List<CategoriaLottoEntry> risultato = new ArrayList<CategoriaLottoEntry>();
		for (CategoriaLotto categoria : categorie) {
			CategoriaLottoEntry catLotto = new CategoriaLottoEntry();
			catLotto.setIdGara(codGara);
			catLotto.setIdLotto(codLotto);
			catLotto.setCategoria(categoria.getCategoria());
			catLotto.setClasse(categoria.getClasse());
			catLotto.setNumCategoria(categoria.getNum());
			catLotto.setScorporabile(categoria.getScorporabile());
			catLotto.setSubappaltabile(categoria.getSubappaltabile());
			risultato.add(catLotto);
		}
		return risultato;
	}

	private List<AppaLavEntry> mappingTipologieLavori(Long codGara, Long codLotto) {
		List<LottoDynamicForPublish> tipologieLavori = this.contrattiMapper.getFullTipologiaLavoriLotto(codGara,
				codLotto);
		List<AppaLavEntry> risultato = new ArrayList<AppaLavEntry>();
		for (LottoDynamicForPublish tipologia : tipologieLavori) {
			AppaLavEntry tipLavori = new AppaLavEntry();
			tipLavori.setIdGara(codGara);
			tipLavori.setIdLotto(codLotto);
			tipLavori.setNumAppaLav(tipologia.getNum());
			tipLavori.setTipologiaLavoro(tipologia.getNumAppalto());
			risultato.add(tipLavori);
		}
		return risultato;
	}

	private List<MotivazioneProceduraNegoziataEntry> mappingModalitaRicorso(Long codGara, Long codLotto) {

		List<LottoDynamicForPublish> condizioniricorso = this.contrattiMapper.getFullCondizioniRicorsoLotto(codGara,
				codLotto);
		List<MotivazioneProceduraNegoziataEntry> risultato = new ArrayList<MotivazioneProceduraNegoziataEntry>();
		for (LottoDynamicForPublish condizione : condizioniricorso) {
			MotivazioneProceduraNegoziataEntry motivazione = new MotivazioneProceduraNegoziataEntry();
			motivazione.setIdGara(codGara);
			motivazione.setIdLotto(codLotto);
			motivazione.setNumCondizione(condizione.getNum());
			motivazione.setCondizione(condizione.getNumAppalto());

			risultato.add(motivazione);
		}
		return risultato;
	}

	private List<AppaFornEntry> mappingModalitaAcquisizioneForniture(Long codGara, Long codLotto) {

		List<LottoDynamicForPublish> modalitaAcq = this.contrattiMapper.getFullModalitaAcquisizioneLotto(codGara,
				codLotto);
		List<AppaFornEntry> risultato = new ArrayList<AppaFornEntry>();
		for (LottoDynamicForPublish modalita : modalitaAcq) {
			AppaFornEntry appaForn = new AppaFornEntry();
			appaForn.setIdGara(codGara);
			appaForn.setIdLotto(codLotto);
			appaForn.setModalitaAcquisizione(modalita.getNumAppalto());
			appaForn.setNumAppaForn(modalita.getNum());

			risultato.add(appaForn);
		}
		return risultato;

	}

	private List<CpvLottoEntry> mappingCpvSecondari(Long codGara, Long codLotto, List<CPVSecondario> cpvSecondari) {

		List<CpvLottoEntry> risultato = new ArrayList<CpvLottoEntry>();
		Long counter = 1L;
		for (CPVSecondario cpvSecondario : cpvSecondari) {
			CpvLottoEntry cpvLottoEntry = new CpvLottoEntry();

			cpvLottoEntry.setCpv(cpvSecondario.getDescCpv());
			cpvLottoEntry.setIdGara(codGara);
			cpvLottoEntry.setIdLotto(codLotto);
			cpvLottoEntry.setNumCpv(counter + "");
			cpvLottoEntry.setCpv(cpvSecondario.getCodCpv());
			counter++;

			risultato.add(cpvLottoEntry);
		}
		return risultato;
	}

	private PubblicaLottoEntry mappingLotto(LottoFullEntry lottoEntry) {

		PubblicaLottoEntry lottoForPublish = new PubblicaLottoEntry();

		lottoForPublish.setCategoria(lottoEntry.getCategoriaPrev());
		lottoForPublish.setCig(lottoEntry.getCig());
		lottoForPublish.setClasse(lottoEntry.getClasseCategoriaPrev());
		lottoForPublish.setCodiceIntervento(lottoEntry.getCodiceInterno());
		lottoForPublish.setContrattoEsclusoArt16e17e18(lottoEntry.getContrattoEscluso161718());
		lottoForPublish.setContrattoEsclusoArt19e26(lottoEntry.getContrattoEscluso());
		lottoForPublish.setCpv(lottoEntry.getCpv());
		lottoForPublish.setCriterioAggiudicazione(lottoEntry.getCriteriAggiudicazione());
		lottoForPublish.setCui(lottoEntry.getCui());
		lottoForPublish.setCup(lottoEntry.getCup());
		lottoForPublish.setCupEsente(lottoEntry.getEsenteCup());
		lottoForPublish.setIdGara(new Long(lottoEntry.getCodgara()));
		lottoForPublish.setIdLotto(new Long(lottoEntry.getCodLotto()));
		lottoForPublish.setIdRup(lottoEntry.getRup());
		lottoForPublish.setIdSceltaContraente(lottoEntry.getSceltaContraente());
		lottoForPublish.setIdSceltaContraente50(lottoEntry.getSceltaContraenteLgs());
		lottoForPublish.setImportoLotto(lottoEntry.getImportoNetto());
		lottoForPublish.setImportoSicurezza(lottoEntry.getImportoSicurezza());
		lottoForPublish.setImportoTotale(lottoEntry.getImportoTotale());
		lottoForPublish.setLuogoIstat(lottoEntry.getLuogoIstat());
		lottoForPublish.setLuogoNuts(lottoEntry.getLuogoNuts());
		lottoForPublish.setManodopera(lottoEntry.getManodopera());
		lottoForPublish.setNumeroLotto(lottoEntry.getNumLotto());
		lottoForPublish.setOggetto(lottoEntry.getOggetto());
		lottoForPublish.setPrestazioniComprese(lottoEntry.getPrestazioneComprese());
		lottoForPublish.setSettore(lottoEntry.getTipoSettore());
		lottoForPublish.setSommaUrgenza(lottoEntry.getSommaUrgenza());
		lottoForPublish.setMasterCig(lottoEntry.getMasterCig());				
		lottoForPublish.setQualificazioneStazioneAppaltante(lottoEntry.getQualificazioneSa());
		lottoForPublish.setCategoriaMerceologica(lottoEntry.getCategoriaMerceologica());
		lottoForPublish.setFlagPrevedeRip(lottoEntry.getFlagPrevedeRip());
		lottoForPublish.setDurataRinnovi(lottoEntry.getDurataRinnovi());
		lottoForPublish.setMotivoCollegamento(lottoEntry.getMotivoCollegamento());
		lottoForPublish.setCigOrigineRip(lottoEntry.getCigOrigineRip());
		lottoForPublish.setFlagPnrrPnc(lottoEntry.getFlagPnrrPnc());
		lottoForPublish.setFlagPrevisioneQuota(lottoEntry.getFlagPrevisioneQuota());
		lottoForPublish.setFlagMisurePreliminari(lottoEntry.getFlagMisurePreliminari());		
		lottoForPublish.setDataScadenzaPagamenti(lottoEntry.getDataScadenzaPagamenti());
		
		if (lottoEntry.getRup() != null) {

			RupEntry tecnico = this.contrattiMapper.getTecnico(lottoEntry.getRup());
			if (tecnico != null) {
				lottoForPublish.setTecnicoRup(mappingRup(tecnico));
			}
		}
		lottoForPublish.setTipoAppalto(lottoEntry.getTipologia());
		lottoForPublish.setUrlCommittente(lottoEntry.getUrlCommittente());
		lottoForPublish.setUrlPiattaformaTelematica(lottoEntry.getUrlEproc());
		lottoForPublish.setSettore(lottoEntry.getTipoSettore());
		lottoForPublish.setIdSchedaLocale(lottoEntry.getIdSchedalocale());
		lottoForPublish.setIdSchedaSimog(lottoEntry.getIdSchedaSimog());
		lottoForPublish.setContrattoEsclusoAlleggerito(lottoEntry.getContrattoEsclusoAlleggerito());
		lottoForPublish.setEsclusioneRegimeSpeciale(lottoEntry.getEsclusioneRegimeSpeciale());
		lottoForPublish.setExsottosoglia(lottoEntry.getExsottosoglia());
		return lottoForPublish;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseRequestPubblicazioneAtti getRequestPubblicazioneAtti(Long codGara, Long tipoDocumento, Long numPubb, Long syscon)
			throws Exception {
		ResponseRequestPubblicazioneAtti risultato = new ResponseRequestPubblicazioneAtti();
		risultato.setEsito(true);
		try {
			PubblicaAttoEntry attoRequest = new PubblicaAttoEntry();
			DettaglioAttoEntry atto = this.contrattiMapper.getDettaglioAtto(codGara, tipoDocumento, numPubb);
			ResponseRequestPubblicazione garaPubblicazione = getRequestPubblicazioneGara(codGara,syscon);
			if (garaPubblicazione.getData() != null) {

				attoRequest.setClientId("SitatSA");
				attoRequest.setDataAggiudicazione(atto.getDataVerbAggiudicazione());
				attoRequest.setDataDecreto(atto.getDataDecreto());
				attoRequest.setDataProvvedimento(atto.getDataProvvedimento());
				if(atto.getDataPubb() != null) {
					logger.debug("REQUEST-ATTO: codgara : "+atto.getCodGara());
					logger.debug("DATA-PUBB:" + atto.getDataPubb().toString());
				}
				attoRequest.setDataPubblicazione(atto.getDataPubb());
				attoRequest.setDataScadenza(atto.getDataScad());
				attoRequest.setDataStipula(atto.getDataStipula());
				// attoRequest.setAggiudicatari(aggiudicatari);
				attoRequest.setEventualeSpecificazione(atto.getDescriz());
				attoRequest.setGara(garaPubblicazione.getData());
				attoRequest.setIdGara(atto.getCodGara());
				attoRequest.setIdRicevuto(atto.getIdRicevuto());
				attoRequest.setImportoAggiudicazione(atto.getImportoAggiudicazione());
				attoRequest.setNumeroProvvedimento(atto.getNumProvvedimento());
				attoRequest.setNumeroPubblicazione(atto.getNumPubb());
				attoRequest.setNumeroRepertorio(atto.getNumRepertorio());
				attoRequest.setOffertaAumento(atto.getPercOffAumento());
				attoRequest.setRibassoAggiudicazione(atto.getPercRibassoAgg());
				attoRequest.setTipoDocumento(atto.getTipDoc());
				attoRequest.setUrlCommittente(atto.getUrlCommittente());
				attoRequest.setUrlEProcurement(atto.getUrlEproc());
				attoRequest.setAutore(sqlMapper.getNameBySyscon(syscon));
				List<AllegatoAttiEntry> docs = mappingAttoDocuments(codGara, numPubb);
				attoRequest.setDocumenti(docs);

				List<ImpresaAggiudicatariaAttoEntry> impAggiudicatarie = this.contrattiMapper
						.getImpreseAggiudicatarieAtto(codGara, numPubb);
				List<AggiudicatarioEntry> aggiudicatari = mappingAggiudicatari(impAggiudicatarie);
				attoRequest.setAggiudicatari(aggiudicatari);

				List<String> listaCigAssociati = this.contrattiMapper.getCigAssociatiAdAtto(codGara, numPubb);
				attoRequest.setCigAssociati(listaCigAssociati);

				risultato.setData(attoRequest);
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati atto per la pubblicazione. codgara = " + codGara
					+ " num pubb= " + numPubb, e);
			throw e;
		}
		return risultato;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PubblicaAttoServerEntry getRequestPubblicazioneAttiServer(Long codGara, Long tipoDocumento, Long numPubb, Long syscon)
			throws Exception {
		try {
			PubblicaAttoServerEntry attoRequest = new PubblicaAttoServerEntry();
			DettaglioAttoEntry atto = this.contrattiMapper.getDettaglioAtto(codGara, tipoDocumento, numPubb);
			ResponseRequestPubblicazione garaPubblicazione = getRequestPubblicazioneGara(codGara,syscon);
			if (garaPubblicazione.getData() != null) {

				attoRequest.setClientId("SitatSA");
				if(atto.getDataVerbAggiudicazione() != null) {
					attoRequest.setDataAggiudicazione(new SimpleDateFormat("dd/MM/yyyy").format(atto.getDataVerbAggiudicazione()));
				}
				if(atto.getDataDecreto() != null) {
					attoRequest.setDataDecreto(new SimpleDateFormat("dd/MM/yyyy").format(atto.getDataDecreto()));
					
				}	
					
				if(atto.getDataProvvedimento() != null) {
					attoRequest.setDataProvvedimento(new SimpleDateFormat("dd/MM/yyyy").format(atto.getDataProvvedimento()));
				}
				if(atto.getDataPubb() != null) {
					attoRequest.setDataPubblicazione(new SimpleDateFormat("dd/MM/yyyy").format(atto.getDataPubb()));
				}
				if(atto.getDataScad() != null) {
					attoRequest.setDataScadenza(new SimpleDateFormat("dd/MM/yyyy").format(atto.getDataScad()));
				}
				if(atto.getDataStipula() != null) {
					attoRequest.setDataStipula(new SimpleDateFormat("dd/MM/yyyy").format(atto.getDataStipula()));
				}
				// attoRequest.setAggiudicatari(aggiudicatari);
				attoRequest.setEventualeSpecificazione(atto.getDescriz());
				attoRequest.setGara(garaPubblicazione.getData());
				attoRequest.setIdGara(atto.getCodGara());
				attoRequest.setIdRicevuto(atto.getIdRicevuto());
				attoRequest.setImportoAggiudicazione(atto.getImportoAggiudicazione());
				attoRequest.setNumeroProvvedimento(atto.getNumProvvedimento());
				attoRequest.setNumeroPubblicazione(atto.getNumPubb());
				attoRequest.setNumeroRepertorio(atto.getNumRepertorio());
				attoRequest.setOffertaAumento(atto.getPercOffAumento());
				attoRequest.setRibassoAggiudicazione(atto.getPercRibassoAgg());
				attoRequest.setTipoDocumento(atto.getTipDoc());
				attoRequest.setUrlCommittente(atto.getUrlCommittente());
				attoRequest.setUrlEProcurement(atto.getUrlEproc());
				attoRequest.setAutore(sqlMapper.getNameBySyscon(syscon));
				List<AllegatoAttiEntry> docs = mappingAttoDocuments(codGara, numPubb);
				attoRequest.setDocumenti(docs);

				List<ImpresaAggiudicatariaAttoEntry> impAggiudicatarie = this.contrattiMapper
						.getImpreseAggiudicatarieAtto(codGara, numPubb);
				List<AggiudicatarioEntry> aggiudicatari = mappingAggiudicatari(impAggiudicatarie);
				attoRequest.setAggiudicatari(aggiudicatari);

				List<String> listaCigAssociati = this.contrattiMapper.getCigAssociatiAdAtto(codGara, numPubb);
				attoRequest.setCigAssociati(listaCigAssociati);

				return attoRequest;
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati atto per la pubblicazione. codgara = " + codGara
					+ " num pubb= " + numPubb, e);
			throw e;
		}
		return null;
	}
	
	

	private List<AggiudicatarioEntry> mappingAggiudicatari(List<ImpresaAggiudicatariaAttoEntry> impAggiudicatarie) {

		List<AggiudicatarioEntry> risultato = new ArrayList<AggiudicatarioEntry>();
		if (impAggiudicatarie == null) {
			return null;
		}
		for (ImpresaAggiudicatariaAttoEntry imp : impAggiudicatarie) {
			AggiudicatarioEntry agg = new AggiudicatarioEntry();

			agg.setCodiceImpresa(imp.getCodImpresa());
			agg.setIdGruppo(imp.getIdGruppo());
			agg.setNumeroAggiudicatario(imp.getNumAggi());
			agg.setNumeroPubblicazione(imp.getNumPubb());
			agg.setRuolo(imp.getRuolo());
			agg.setTipoAggiudicatario(imp.getIdTipoAgg());
			ImpresaEntry impresa = this.getImpresa(imp.getCodImpresa());
			if (impresa != null) {
				ImpresaPubbEntry impPubb = new ImpresaPubbEntry();
				impPubb.setCap(impresa.getCap());
				impPubb.setCodiceFiscale(impresa.getCodiceFiscale());
				impPubb.setCodiceSA(impresa.getStazioneAppaltante());
				impPubb.setFax(impresa.getFax());
				impPubb.setIndirizzo(impresa.getIndirizzo());
				impPubb.setLocalita(impresa.getComune());
				impPubb.setNumeroCCIAA(impresa.getCodiceInail());
				impPubb.setNumeroCivico(impresa.getNumeroCivico());
				impPubb.setPartitaIva(impresa.getPartitaIva());
				impPubb.setProvincia(impresa.getProvincia());
				impPubb.setRagioneSociale(impresa.getRagioneSociale());
				impPubb.setNomImp(impresa.getNomImp());
				impPubb.setTelefono(impresa.getTelefono());
				impPubb.setCodiceNazione(impresa.getNazione());
				impPubb.setNazione(getCodiceNazioneByNumber(impresa.getNazione()));

				if (impresa.getRappresentante() != null) {
					LegaleRappresentantePubbEntry rappresentante = new LegaleRappresentantePubbEntry();
					rappresentante.setNome(impresa.getRappresentante().getNome());
					rappresentante.setCognome(impresa.getRappresentante().getCognome());
					rappresentante.setCf(impresa.getRappresentante().getCf());
					impPubb.setRappresentante(rappresentante);
				}

				agg.setImpresa(impPubb);
			}
			risultato.add(agg);
		}
		return risultato;

	}

	private List<AllegatoAttiEntry> mappingAttoDocuments(Long codGara, Long numPubb) {

		List<AllegatoAttiEntry> requestDocumenti = new ArrayList<AllegatoAttiEntry>();
		List<AttoDocument> documenti = this.contrattiMapper.getAttoDocuments(codGara, numPubb);

		for (AttoDocument doc : documenti) {
			AllegatoAttiEntry docRequest = new AllegatoAttiEntry();
			if (doc.getFileAllegato() != null) {
				byte[] encodedFile = Base64.getEncoder().encode(doc.getFileAllegato());
				docRequest.setBinary(new String(encodedFile, StandardCharsets.UTF_8));
				doc.setFileAllegato(null);
			}
			docRequest.setFile(doc.getFileAllegato());
			docRequest.setIdGara(doc.getCodGara());
			docRequest.setNrDoc(doc.getNumDoc());
			docRequest.setNrPubblicazione(doc.getNumPubb());
			docRequest.setTitolo(doc.getTitolo());
			docRequest.setUrl(doc.getUrl());
			docRequest.setTipoFile(doc.getTipoFile());
			requestDocumenti.add(docRequest);

		}
		return requestDocumenti;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse allineaPubblicazioneGara(Long codGara, String codiceFiscaleSA, Long idRicevuto, Long tipoInvio,
			Long syscon, String payload) throws Exception {

		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		FlussoEntry flusso = new FlussoEntry();
		try {
			this.contrattiMapper.aggiornaIdRicevuto(codGara, idRicevuto);
			this.contrattiMapper.setLottiDaexport(2L, codGara);
			Long idFlusso =  wgcManager.getNextId("W9FLUSSI");
			flusso.setId(idFlusso);
			flusso.setArea(new Long(2));
			flusso.setKey01(codGara);
			flusso.setKey03(new Long(988));
			flusso.setTipoInvio(tipoInvio);
			flusso.setDataInvio(new Date());
			flusso.setCodiceFiscaleSA(codiceFiscaleSA);
			flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
			flusso.setJson(payload);
			this.contrattiMapper.insertFlusso(flusso);
			// update daExport dei lotti
			this.contrattiMapper.updateDaExportDopoPubblicazione(codGara);
		} catch (Exception ex) {
			logger.error("Errore in allinemanto dati pubblicazione gara: codgara =" + codGara, ex);
			risultato.setEsito(false);
			throw ex;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse allineaPubblicazioneAtto(Long codGara, Long tipoDocumento, Long numPubb, Long tipoInvio,
			Long idGara, Long idRicevuto, Long syscon, String payload) throws Exception {

		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		FlussoEntry flusso = new FlussoEntry();
		try {
			this.contrattiMapper.aggiornaIdRicevutoAtto(codGara, tipoDocumento, numPubb, idRicevuto);
			this.contrattiMapper.aggiornaIdRicevuto(codGara, idGara);
			DettaglioAttoEntry atto = this.contrattiMapper.getDettaglioAtto(codGara, tipoDocumento, numPubb);
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			Long idFlusso =  wgcManager.getNextId("W9FLUSSI");
			flusso.setId(idFlusso);
			flusso.setArea(new Long(2));
			flusso.setKey01(codGara);
			flusso.setKey03(new Long(901));
			flusso.setKey04(atto.getNumPubb());
			flusso.setTipoInvio(tipoInvio);
			flusso.setDataInvio(new Date());
			flusso.setCodiceFiscaleSA(gara.getCodiceStazioneAppaltante());
			flusso.setOggetto(gara.getIdentificativoGara());
			flusso.setJson(payload);
			flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
			this.contrattiMapper.insertFlusso(flusso);
		} catch (Exception ex) {
			logger.error("Errore in allinemanto dati pubblicazione atto: codgara =" + codGara, ex);
			risultato.setEsito(false);
			throw ex;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse allineaPubblicazioneFase(Long codGara, Long codLotto, Long numFase, Long num, Long tipoInvio,
			String codiceSA, Long syscon, String motivazione) throws Exception {

		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		FlussoEntry flusso = new FlussoEntry();
		try {
			String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(codiceSA);
			Long idFlusso =  wgcManager.getNextId("W9FLUSSI");
			flusso.setId(idFlusso);
			flusso.setArea(new Long(1));
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
			this.contrattiMapper.insertFlusso(flusso);
			this.contrattiMapper.setW9faseExported(codGara, codLotto, numFase, num);
			Long situazioneLotto = tipoInvio == -1 ? 95L : getSituazioneLotto(codGara, codLotto);
			this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
			Long situazioneGara = getSituazioneGara(codGara);
			this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);

		} catch (Exception ex) {
			logger.error("Errore in allinemanto dati pubblicazione scheda: codgara =" + codGara + " codLotto= "
					+ codLotto + " numFase=" + numFase, ex);
			risultato.setEsito(false);
			throw ex;
		}
		return risultato;
	}

	public Long getSituazioneGara(Long codGara) {

		List<LottoFullEntry> lotti = this.contrattiMapper.getFullLottiGara(codGara);
		Set<Long> situazioniLotti = new HashSet<Long>();
		for (LottoEntry lotto : lotti) {
			situazioniLotti.add(lotto.getSituazione());
		}
		if (situazioniLotti.size() == 1 && situazioniLotti.contains(8L)) {
			// Annullata
			return 8L;
		} else if (situazioniLotti.contains(1L)) {
			// In fase di pubblicazioni o affidamento
			return 1L;
		} else if (situazioniLotti.contains(2L)) {
			// Aggiudicato
			return 2L;
		} else if (situazioniLotti.contains(3L)) {
			// Iniziato
			return 3L;
		} else if (situazioniLotti.contains(4L)) {
			// In esecuzione
			return 4L;
		} else if (situazioniLotti.contains(5L)) {
			// Sospeso
			return 5L;
		} else if (situazioniLotti.contains(6L)) {
			// Concluso in attesa di collaudo
			return 6L;
		} else {
			// Monitoraggio terminato
			return 7L;
		}
	}

	public ResponseListaPubblicazioneFase getPubblicazioniFase(Long codGara, Long codLotto, Long numFase, Long num) {
		ResponseListaPubblicazioneFase risultato = new ResponseListaPubblicazioneFase();
		List<FullFlussiFase> pubblicazioniFase = new ArrayList<FullFlussiFase>();
		try {
			pubblicazioniFase = this.contrattiMapper.getListaPubblicazioniFase(codGara, codLotto, numFase, num);
			risultato.setData(pubblicazioniFase);
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore in get lista pubblicazioni fase. codgara =" + codGara + " codLotto=" + codLotto
					+ " num=" + num, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseListaInviiFasi getFlussiFase(Long codGara, Long codLotto, int skip, int take, String orderby,
			String direction) {
		ResponseListaInviiFasi risultato = new ResponseListaInviiFasi();

		List<FullFlussiFase> flussi = new ArrayList<FullFlussiFase>();
		RowBounds rowBounds = new RowBounds(skip, take);

		try {
			int count = this.contrattiMapper.countListaFlussiFase(codGara, codLotto);
			flussi = this.contrattiMapper.getListaFlussiFase(codGara, codLotto, orderby, direction, rowBounds);

			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);

            risultato.setMoreInfoGara(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L);
			risultato.setData(flussi);
			risultato.setTotalCount(count);
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore in get lista flussi fase. codgara =" + codGara + " codLotto=" + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseListaPubblicazioneGara getListaPubblicazioniGara(Long codGara) {

		ResponseListaPubblicazioneGara risultato = new ResponseListaPubblicazioneGara();
		List<FlussiGara> flussi = new ArrayList<FlussiGara>();
		try {
			flussi = this.contrattiMapper.getListaPubblicazioniGara(codGara);
			risultato.setData(flussi);
			risultato.setEsito(false);
		} catch (Exception e) {
			logger.error("Errore in get lista pubblicazioni gara. codgara =" + codGara, e);
			risultato.setEsito(false);
		}
		return risultato;
	}

	public ResponseListaPubblicazioneAtto getListaPubblicazioniAtto(Long codGara, Long num) {
		ResponseListaPubblicazioneAtto risultato = new ResponseListaPubblicazioneAtto();
		List<FlussiAtto> flussi = new ArrayList<FlussiAtto>();
		try {
			flussi = this.contrattiMapper.getListaPubblicazioniAtto(codGara, num);
			risultato.setData(flussi);
			risultato.setEsito(false);
		} catch (Exception e) {
			logger.error("Errore in get lista pubblicazioni atto. codgara =" + codGara + "num = " + num, e);
			risultato.setEsito(false);
		}
		return risultato;
	}

	private DatiGeneraliTecnicoEntry mappingRup(RupEntry rup) {
		DatiGeneraliTecnicoEntry risultato = new DatiGeneraliTecnicoEntry();
		risultato.setCap(rup.getCap());
		risultato.setCfPiva(rup.getCf());
		risultato.setCivico(rup.getNumCivico());
		risultato.setCodice(rup.getCodice());
		risultato.setCodiceSA(rup.getStazioneAppaltante());
		risultato.setCognome(rup.getCognome());
		risultato.setFax(rup.getFax());
		risultato.setIndirizzo(rup.getIndirizzo());
		risultato.setLocalita(rup.getProvincia());
		risultato.setLuogoIstat(rup.getCodIstat());
		risultato.setMail(rup.getEmail());
		risultato.setNome(rup.getNome());
		risultato.setNomeCognome(rup.getNominativo());
		risultato.setProvincia(rup.getProvincia());
		risultato.setTelefono(rup.getTelefono());
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseUpdateRupGara updateRupGara(RupGaraUpdateForm form) throws Exception {

		ResponseUpdateRupGara risultato = new ResponseUpdateRupGara();
		Long codGara = form.getCodGara();
		String codiceTecnico = form.getRup();
		GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
		RupEntry rup = this.contrattiMapper.getTecnico(codiceTecnico);

		if (rup == null) {
			String msg = "Il tecnico selezionato non esiste";
			logger.error(msg);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setErrorMessage(msg);
			return risultato;
		}

		if (!gara.getCodiceStazioneAppaltante().equals(rup.getStazioneAppaltante())) {
			String msg = "La stazione appaltante della gara non coincide con quella del tecnico selezionato";
			logger.error(msg);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setErrorMessage(msg);
			return risultato;
		}

		try {

			this.contrattiMapper.updateRupLottoByGara(codGara, codiceTecnico);
			this.contrattiMapper.updateRupGara(codGara, codiceTecnico);

			risultato.setData(true);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica RUP della gara. codGara = " + form.getCodGara(), e);
			throw e;
		}
		return risultato;
	}

	private boolean isUserAdminOrRUPGara(final String ruolo, final String cfTEcnico, final GaraBaseEntry gara) {
		RupEntry tecnico = this.contrattiMapper.getTecnico(gara.getRup());
		return (ruolo.equals("A")
				|| (!ruolo.equals("A") && cfTEcnico != null && tecnico != null && cfTEcnico.equals(tecnico.getCf()))) && (gara.getVersioneSimog() != null && gara.getVersioneSimog() != 9L);
	}

	public String calcolaCodificaAutomatica(String entita, String campoChiave) throws Exception {
		String codice = "1";
		String formatoCodice = null;
		String codcal = null;
		Long cont = null;
		try {
			String query = "select CODCAL, CONTAT from G_CONFCOD where NOMENT = '" + entita + "'";
			List<Map<String, Object>> confcod = sqlMapper.select(query);
			if (confcod != null && confcod.size() > 0) {
				for (Map<String, Object> row : confcod) {
					if (row.containsKey("CODCAL")) {
						codcal = row.get("CODCAL").toString();
					} else {
						codcal = row.get("codcal").toString();
					}
					if (row.containsKey("CONTAT")) {
						cont = new Long(row.get("CONTAT").toString());
					} else {
						cont = new Long(row.get("contat").toString());
					}
					break;
				}
				boolean codiceUnivoco = false;
				int numeroTentativi = 0;
				StringBuffer strBuffer = null;
				long tmpContatore = cont.longValue();
				while (!codiceUnivoco && numeroTentativi < 5) {
					strBuffer = new StringBuffer("");
					// Come prima cosa eseguo l'update del contatore
					tmpContatore++;
					sqlMapper.execute(
							"update G_CONFCOD set contat = " + tmpContatore + " where NOMENT = '" + entita + "'");

					strBuffer = new StringBuffer("");
					formatoCodice = codcal;
					while (formatoCodice.length() > 0) {
						switch (formatoCodice.charAt(0)) {
						case '<': // Si tratta di un'espressione numerica
							String strNum = formatoCodice.substring(1, formatoCodice.indexOf('>'));
							if (strNum.charAt(0) == '0') {
								// Giustificato a destra
								for (int i = 0; i < (strNum.length() - String.valueOf(tmpContatore).length()); i++)
									strBuffer.append('0');
							}
							strBuffer.append(String.valueOf(tmpContatore));

							formatoCodice = formatoCodice.substring(formatoCodice.indexOf('>') + 1);
							break;
						case '"': // Si tratta di una parte costante
							strBuffer.append(formatoCodice.substring(1, formatoCodice.indexOf('"', 1)));
							formatoCodice = formatoCodice.substring(formatoCodice.indexOf('"', 1) + 1);
							break;
						}
					}
					int occorrenze = sqlMapper
							.count(entita + " WHERE " + campoChiave + " ='" + strBuffer.toString() + "'");
					if (occorrenze == 0) {
						codiceUnivoco = true;
						codice = strBuffer.toString();
					} else {
						numeroTentativi++;
					}
				}
				if (!codiceUnivoco) {
					logger.error("numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
					throw new Exception(
							"numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
				}
			}
		} catch (Exception ex) {
			logger.error("Errore inaspettato durante il calcolo per la codifica automatica " + entita, ex);
			throw new Exception(ex);
		}
		return codice;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse migraGara(final MigrazioneGaraForm form) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("migraGara: inizio metodo");
		}
		BaseResponse risultato = new BaseResponse();

		Long codiceGara = form.getCodGara();
		String codeinSaDestinazione = form.getStazioneAppaltante();
		String codiceFiscaleRupGara = form.getCodiceFiscaleRupGara();

		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codiceGara);
//			String codiceTecnicoSorgente = (String) this.sqlMapper
//					.executeReturnString("select RUP from W9GARA where CODGARA=" + codiceGara);
			String codiceTecnicoDestinazione = null;
			List<RupEntry> tecList = this.contrattiMapper.getTecnicoByCfAndSaList(codiceFiscaleRupGara,
					codeinSaDestinazione);
			RupEntry tecnicoPresente = tecList != null && !tecList.isEmpty() ? tecList.get(0) : null;
			
			if (tecnicoPresente == null) {
				String codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
				RupEntry nuovoTecnico = new RupEntry();
				nuovoTecnico.setCodice(codtec);
				nuovoTecnico.setCf(codiceFiscaleRupGara.toUpperCase());
				nuovoTecnico.setNominativo(codiceFiscaleRupGara);
				nuovoTecnico.setStazioneAppaltante(codeinSaDestinazione);
				this.contrattiMapper.insertRUP(nuovoTecnico);
				codiceTecnicoDestinazione = codtec;
			} else {
				codiceTecnicoDestinazione = tecnicoPresente.getCodice();
			}

			Long idCentroCosto = gara.getIdCentroDiCosto();

			if (idCentroCosto == null) {
				String maxIdCentroCosto = (String) this.sqlMapper
						.executeReturnString("select max(IDCENTRO) from CENTRICOSTO");
				if (maxIdCentroCosto == null) {
					idCentroCosto = new Long(1);
				} else {
					idCentroCosto = new Long(maxIdCentroCosto);
				}
				CentriCostoEntry cdc = this.contrattiMapper.getCentroCosto(gara.getIdCentroDiCosto());
				CentroDiCostoInsertForm cdcInsertForm = new CentroDiCostoInsertForm();
				if(cdc != null){
					cdcInsertForm.setId(idCentroCosto);
					cdcInsertForm.setCodiceCentro(cdc.getCodiceCDC());
					cdcInsertForm.setDenominazione(cdc.getNominativoCDC());
					cdcInsertForm.setNote(cdc.getNote());
					cdcInsertForm.setStazioneAppaltante(codeinSaDestinazione);
					if (cdc.getTipologia() != null)
						cdcInsertForm.setTipologia(new Long(cdc.getTipologia()));
				}

			}

			// Aggiornamento della Gara: RUP e CODEIN.
			this.sqlMapper.execute("update W9GARA set RUP='" + codiceTecnicoDestinazione + "', CODEIN='"
					+ codeinSaDestinazione + "', IDCC=" + idCentroCosto + " where CODGARA=" + codiceGara);

			// Aggiornamento del RUP e del DAEXPORT per tutti i lotti della gara. Il campo
			// DAEXPORT
			// viene impostato a 1 per imporre l'invio dell'anagrafica gara-lotto a OR
			this.sqlMapper.execute("update W9LOTT set RUP='" + codiceTecnicoDestinazione
					+ "', DAEXPORT='1' where CODGARA=" + codiceGara);

			// Gestione degli incarichi professionali diversi dal RUP.
			List<String> listaIncaricati = this.contrattiMapper.getIncaricatiGara(codiceGara);

			if (listaIncaricati != null) {
				for (String codiceTec : listaIncaricati) {
					String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");	
					String codTecDest = null;
					if("ORA".equals(dbms)) {
						codTecDest = (String) this.sqlMapper
								.executeReturnString("select CODTEC from TECNI where CGENTEI='" + codeinSaDestinazione
										+ "' and CFTEC=(select CFTEC from TECNI where CODTEC='" + codiceTec + "') and rownum = 1");
					} else {
						codTecDest = (String) this.sqlMapper
							.executeReturnString("select CODTEC from TECNI where CGENTEI='" + codeinSaDestinazione
									+ "' and CFTEC=(select CFTEC from TECNI where CODTEC='" + codiceTec + "') limit 1");
					}
					if (StringUtils.isEmpty(codTecDest)) {
						// Non esiste il tecnico associato alla nuova Stazione appaltante.
						// Si provvede ad inserirne uno, copaiando i dati dal tecnico sorgente

						RupEntry tecnicoVecchio = this.contrattiMapper.getTecnico(codiceTec);
						if (tecnicoVecchio != null) {
							String codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
							tecnicoVecchio.setCodice(codtec);
							tecnicoVecchio.setStazioneAppaltante(codeinSaDestinazione);
							this.contrattiMapper.insertRUP(tecnicoVecchio);
						}
					}

					this.sqlMapper.execute("update W9INCA set CODTEC='" + codTecDest + "' where CODGARA=" + codiceGara
							+ " and CODTEC='" + codiceTec + "'");
				}
			}

			// Gestione delle imprese associate alla gara: W9AGGI, W9SUBA, W9CANTIMP,
			// W9IMPRESE,
			// W9INASIC, W9INFOR, W9MISSCI, W9SIC, W9REGCON e dei legali rappresentanti.

			migraW9Aggi(codiceGara, codeinSaDestinazione);

			migraW9Suba(codiceGara, codeinSaDestinazione);

			migraW9Cantimp(codiceGara, codeinSaDestinazione);

			migraW9Imprese(codiceGara, codeinSaDestinazione);

			migraW9Inasic(codiceGara, codeinSaDestinazione);

			migraW9Sic(codiceGara, codeinSaDestinazione);

			migraW9RegCon(codiceGara, codeinSaDestinazione);

		} catch (Exception e) {
			logger.error("Errore in migrazione gara: codgara = " + codiceGara + " codiceSaDestinazione= "
					+ codeinSaDestinazione);
			logger.error(e);
			throw e;
		}
		risultato.setEsito(true);
		return risultato;
	}

	private void migraW9RegCon(Long codiceGara, String codeinSaDestinazione) throws Exception {

		List<FaseInidoneitaContributivaEntry> fasiInidoneitaContributiva = this.contrattiMapper
				.getFasiInidoneitaContributivaGara(codiceGara);
		for (FaseInidoneitaContributivaEntry faseIni : fasiInidoneitaContributiva) {
			if (faseIni.getCodImpresa() != null) {
				ImpresaEntry impresa = this.getImpresa(faseIni.getCodImpresa());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}
		}

	}

	private void migraW9Sic(Long codiceGara, String codeinSaDestinazione) throws Exception {

		List<SchedaSicurezzaEntry> schedeSicurezza = this.contrattiMapper.getschedeSicurezzaGara(codiceGara);
		for (SchedaSicurezzaEntry schedaSic : schedeSicurezza) {
			if (schedaSic.getCodImp() != null) {
				ImpresaEntry impresa = this.getImpresa(schedaSic.getCodImp());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}
		}
	}

	private void migraW9Inasic(Long codiceGara, String codeinSaDestinazione) throws Exception {
		List<FaseInadempienzeSicurezzaEntry> fasiInadempienzeSicurezza = this.contrattiMapper
				.getFasiInadempienzeSicurezzaGara(codiceGara);
		for (FaseInadempienzeSicurezzaEntry faseInadempienza : fasiInadempienzeSicurezza) {
			if (faseInadempienza.getCodImpresa() != null) {
				ImpresaEntry impresa = this.getImpresa(faseInadempienza.getCodImpresa());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}
		}

	}

	private void migraW9Imprese(Long codiceGara, String codeinSaDestinazione) throws Exception {
		List<RecordFaseImpreseEntry> fasiImprese = this.contrattiMapper.getFaseImpreseGara(codiceGara);
		for (RecordFaseImpreseEntry faseImp : fasiImprese) {

			if (faseImp.getCodImpresa() != null) {
				ImpresaEntry impresa = this.getImpresa(faseImp.getCodImpresa());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}
		}
	}

	private void migraW9Cantimp(Long codiceGara, String codeinSaDestinazione) throws Exception {

		List<FaseCantieriImpEntry> fasiCantieri = this.contrattiMapper.getImpreseFaseCantieriGara(codiceGara);
		for (FaseCantieriImpEntry faseCant : fasiCantieri) {

			if (faseCant.getCodiceImpresa() != null) {
				ImpresaEntry impresa = this.getImpresa(faseCant.getCodiceImpresa());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}
		}
	}

	private void migraW9Suba(Long codiceGara, String codeinSaDestinazione) throws Exception {
		List<FaseSubappaltoEntry> fasiSubappaltoEntries = this.contrattiMapper.getFaseSubappaltoGara(codiceGara);
		for (FaseSubappaltoEntry faseSubb : fasiSubappaltoEntries) {

			if (faseSubb.getCodImpresa() != null) {
				ImpresaEntry impresa = this.getImpresa(faseSubb.getCodImpresa());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}
			if (faseSubb.getCodImpresaAgg() != null) {
				ImpresaEntry impresaSubappaltatrice = this.getImpresa(faseSubb.getCodImpresaAgg());
				if (impresaSubappaltatrice != null) {
					migraImpresa(impresaSubappaltatrice, codeinSaDestinazione);
				}
			}
		}
	}

	private void migraW9Aggi(Long codiceGara, String codeinSaDestinazione) throws Exception {

		List<ImpresaAggiudicatariaEntry> impreseAggiudicatarie = this.contrattiMapper
				.getImpreseAggiudicatarieGara(codiceGara);
		for (ImpresaAggiudicatariaEntry impresaAggiudicataria : impreseAggiudicatarie) {

			if (impresaAggiudicataria.getCodImpresa() != null) {
				ImpresaEntry impresa = this.getImpresa(impresaAggiudicataria.getCodImpresa());
				if (impresa != null) {
					migraImpresa(impresa, codeinSaDestinazione);
				}
			}

			if (impresaAggiudicataria.getCodImpAus() != null) {
				ImpresaEntry impresaAus = this.getImpresa(impresaAggiudicataria.getCodImpAus());
				if (impresaAus != null) {
					migraImpresa(impresaAus, codeinSaDestinazione);
				}
			}
		}
	}

	private void migraImpresa(ImpresaEntry impresa, String codeinSaDestinazione) throws Exception {
		if (contrattiMapper.esisteImpresa(impresa.getCodiceImpresa(), codeinSaDestinazione) == 0L) {
			String codiceImpresaDestinazione = calcolaCodificaAutomatica("IMPR", "CODIMP");
			impresa.setCodiceImpresa(codiceImpresaDestinazione);
			impresa.setStazioneAppaltante(codeinSaDestinazione);
			impresa = super.setImpresaNomImp(impresa);
			contrattiMapper.insertImpresa(impresa);
			LegaleRappresentanteEntry legale = this.contrattiMapper.getLegalempresa(impresa.getCodiceImpresa());
			if (legale != null) {
				this.contrattiMapper.insertTeim(codiceImpresaDestinazione, impresa.getRappresentante().getNome(),
						impresa.getRappresentante().getCognome(), impresa.getRappresentante().getCf());
				Long id = wgcManager.getNextId("IMPLEG");
				this.contrattiMapper.insertImpleg(id, codiceImpresaDestinazione,
						impresa.getRappresentante().getNome() + " " + impresa.getRappresentante().getCognome());
			}

		}
	}

	public BaseResponse archiviaGara(Long codGara) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("codGara non valorizzato");
		} else {
			try {
				this.contrattiMapper.updateSituazioneGara(codGara, 91L);
			} catch (Exception e) {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				logger.error("Errore inaspettato durante l'archiviazione gara : codgara=" + codGara, e);
			}
		}
		return risultato;
	}

	public BaseResponse annullaArchiviaGara(Long codGara) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		if (codGara == null) {
			risultato.setEsito(false);
			risultato.setErrorData("codGara non valorizzato");
		} else {
			try {
				Long situazioneGara = getSituazioneGara(codGara);
				this.contrattiMapper.updateSituazioneGara(codGara, situazioneGara);
			} catch (Exception e) {
				risultato.setEsito(false);
				risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
				logger.error("Errore inaspettato durante l'annulamento archiviazione gara : codgara=" + codGara, e);
			}
		}
		return risultato;
	}

	public String getCodtecByCfAndSA(String cf, String codiceStazioneAppaltante) {
		return this.contrattiMapper.getCodtecByCfAndSA(cf, codiceStazioneAppaltante);
	}
	
	public RupEntry getRup(String codiceRup) {
		return this.contrattiMapper.getTecnico(codiceRup);
	}	

	public ResponseAnagraficaGaraPubblicata isAnagraficaGaraPubblicata(final Long codGara,
			final Boolean checkSmartCig) {
		ResponseAnagraficaGaraPubblicata risultato = new ResponseAnagraficaGaraPubblicata();
		try {

			boolean condition = false;

			boolean isSmartCig = false;

			Long countLotti = this.contrattiMapper.countLotti(codGara);
			if (countLotti == 1) {
				String cig = "";
				List<LottoBaseEntry> lotti = this.contrattiMapper.getLottiGara(codGara);
				for(LottoBaseEntry lotto : lotti) {
					if(lotto.getCig()!=null) {
						cig = lotto.getCig();					
					}
				}
				isSmartCig = cig.startsWith("X") || cig.startsWith("Y") || cig.startsWith("Z");
			}

			Long countPubblicazioni = this.contrattiMapper.countPubblicazioniAnagraficaGara(codGara);

			Long countLottiDaEsportare = this.contrattiMapper.countLottiDaEsportare(codGara);

			condition = (isSmartCig && !checkSmartCig) || (countPubblicazioni > 0 && countLottiDaEsportare == 0);

			risultato.setData(condition);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore isAnagraficaGaraPubblicata", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	private ImpresaEntry getImpresa(final String codiceImpresa) {
		if (StringUtils.isNotBlank(codiceImpresa)) {
			ImpresaEntry impresa = this.contrattiMapper.getImpresa(codiceImpresa);
			LegaleRappresentanteEntry legale = this.contrattiMapper.getLegaleImpresa(codiceImpresa);
			impresa.setRappresentante(legale);
			if (impresa.getComune() != null && !"".equals(impresa.getComune())) {
				TabellatoIstatEntry comune = this.contrattiMapper.getComuneByDesc(impresa.getComune().toUpperCase());
				if (comune != null) {
					impresa.setCodComune(comune.getCodice());
				}
			}
			return impresa;
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertImpresaAggiudicatariaAtto(ImpreseAggiudicatarieAttoInsertForm form) {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			if(form.getIdTipoAgg() != null && form.getIdTipoAgg() == 2L) {
				if(form.getImprese() == null || (form.getImprese() != null && form.getImprese().size() < 2)) {
					risultato.setEsito(false);
					risultato.setErrorData(ERROR_NUM_IMPRESE);
					return risultato;
				}
			}
			
			// Calcolo l'id per l'inserimento del record			
			Long id = this.contrattiMapper.checkNumAggiImpresaAggiudicatariaAtto(form.getCodGara(), form.getNumPubb());										
			if(id == null) {
				id = 1L;
			}
			if (form.getImprese() != null) {
				for (ImpresaAggiudicatariaAttoInsertForm impresa : form.getImprese()) {
					Long gruppoId = form.getImprese().size() > 1 ? 1L : null;
					this.contrattiMapper.insertImpresaAggiudicatariaAtto(form.getCodGara(), form.getNumPubb(), id,
							form.getIdTipoAgg(), impresa.getRuolo(), impresa.getCodImpresa(), gruppoId);
					id++;
				}
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento impresa aggiudicataria atto.", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateImpresaAggiudicatariaAtto(ImpreseAggiudicatarieAttoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			if(form.getIdTipoAgg() != null && form.getIdTipoAgg() == 2L) {
				if(form.getImprese() == null || (form.getImprese() != null && form.getImprese().size() < 2)) {
					risultato.setEsito(false);
					risultato.setErrorData(ERROR_NUM_IMPRESE);
					return risultato;
				}
			}
			
			this.contrattiMapper.deleteImpreseAggiudicatarieAtto(form.getCodGara(), form.getNumPubb());
			Long id = 1L;
			if (form.getImprese() != null) {
				for (ImpresaAggiudicatariaAttoInsertForm impresa : form.getImprese()) {
					Long gruppoId = form.getImprese().size() > 1 ? 1L : null;
					this.contrattiMapper.insertImpresaAggiudicatariaAtto(form.getCodGara(), form.getNumPubb(), id,
							form.getIdTipoAgg(), impresa.getRuolo(), impresa.getCodImpresa(), gruppoId);
					id++;
				}
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato la modifica impresa aggiudicataria. codgara = " + form.getCodGara()
					+ " numPubb = " + form.getNumPubb(), e);
			throw e;
		}
		return risultato;
	}

	private static final String CONFIG_CODICE_APP = "W9";
	private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

	public String getJWTKey() throws CriptazioneException {

		String criptedKey = this.contrattiMapper.getCipherKey(CONFIG_CODICE_APP, CONFIG_CHIAVE_APP);
		try {
			ICriptazioneByte decript = FactoryCriptazioneByte.getInstance(
					FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, criptedKey.getBytes(),
					ICriptazioneByte.FORMATO_DATO_CIFRATO);

			return new String(decript.getDatoNonCifrato());
		} catch (CriptazioneException e) {
			logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
			throw e;
		}
	}

	public ResponseLottoAccorpabile checkLottiAccorpatiGara(Long codGara) {

		ResponseLottoAccorpabile risultato = new ResponseLottoAccorpabile();
		risultato.setEsito(true);
		boolean hasMultiLotto = false;
		boolean allowMultiLotto = false;

		try {
			List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(codGara);
			if (lottiGara == null || lottiGara.size() < 2) {
				LottoAccorpabileEntry entry = new LottoAccorpabileEntry();
				entry.setAllowMultiotto(allowMultiLotto);
				entry.setHasMultiLotto(hasMultiLotto);
				risultato.setData(entry);
				return risultato;
			}
			for (LottoBaseEntry lotto : lottiGara) {
				if (lotto.getMasterCig() != null) {
					hasMultiLotto = true;
					break;
				}
			}

			allowMultiLotto = this.contrattiMapper.checkAllowMultilotto(codGara) > 0;

		} catch (Exception e) {
			logger.error("Errore in checLottiAccorpatiGara. codgara = " + codGara, e);
			LottoAccorpabileEntry entry = new LottoAccorpabileEntry();
			entry.setAllowMultiotto(false);
			entry.setHasMultiLotto(false);
			risultato.setData(entry);
			return risultato;
		}
		LottoAccorpabileEntry entry = new LottoAccorpabileEntry();
		entry.setAllowMultiotto(allowMultiLotto);
		entry.setHasMultiLotto(hasMultiLotto);
		risultato.setData(entry);
		return risultato;
	}

	public ResponseMultilotto getMultiLottoOptions(Long codGara, String occurrence) {
		ResponseMultilotto risultato = new ResponseMultilotto();
		risultato.setEsito(true);
		if (occurrence == null || "".equals(occurrence)) {
			return risultato;
		} else {
			occurrence = "%" + occurrence.toUpperCase() + "%";
		}
		try {
			List<LottoBaseEntry> lottiGaraOptions = this.contrattiMapper.getMultiLottoOptions(codGara, occurrence);
//			List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(codGara);
//
//			if (lottiGaraOptions != null && lottiGaraOptions.size() > 0) {
//				for (LottoBaseEntry lottoOption : lottiGaraOptions) {
//					if (isLottoAcccorpabile(lottoOption)) {
//						for (LottoBaseEntry lottoOption2 : lottiGara) {
//							if (lottoOption.getCig() != lottoOption2.getCig()) {
//								if (lottoOption2.getCodLotto() == "64") {
//									System.out.println("QUI!!!!");
//								}
//
//								if (isLottoAcccorpabile(lottoOption2)
//										&& areLottiAccorpabili(lottoOption, lottoOption2)) {
//									cigAccorpabili.add(lottoOption);
//									break;
//								}
//							}
//						}
//					}
//				}
//
//			}
			if (lottiGaraOptions != null && lottiGaraOptions.size() > 0) {
				risultato.setData(lottiGaraOptions);
			}

		} catch (Exception e) {
			logger.error("Errore in get CIG multilotto options per codgara " + codGara, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	private boolean isLottoAcccorpabile(LottoBaseEntry lotto) {

		boolean hasAggiudicazione = false;
		Long codGara = new Long(lotto.getCodgara());
		Long codLotto = new Long(lotto.getCodLotto());
		Long numAppa = getNumAppa(codGara, codLotto, false);
		List<Long> fasiPresentiw9Flussi = this.contrattiMapper.getFasiInviate(codGara, codLotto, numAppa);
		if (fasiPresentiw9Flussi != null) {
			for (Long codiceFase : fasiPresentiw9Flussi) {
				if (codiceFase == 1L) {
					hasAggiudicazione = true;
				}
			}
		}
		return hasAggiudicazione;
	}

	private boolean areLottiAccorpabili(LottoBaseEntry lotto1, LottoBaseEntry lotto2) {
		//controllo su imprese aggiudicatarie
		Long numAppa = getNumAppa(new Long(lotto1.getCodgara()), new Long(lotto1.getCodLotto()), false);
		List<String> codimp1 = this.contrattiMapper.getCodiciImpreseAggiudicatarie(new Long(lotto1.getCodgara()),
				new Long(lotto1.getCodLotto()), numAppa);
		List<String> codimp2 = this.contrattiMapper.getCodiciImpreseAggiudicatarie(new Long(lotto2.getCodgara()),
				new Long(lotto2.getCodLotto()), numAppa);
		if (codimp1 == null || codimp1.size() == 0 || codimp2 == null || codimp2.size() == 0) {
			return false;
		}
		for (String cod1 : codimp1) {
			if (!codimp2.contains(cod1)) {
				return false;
			}
		}
		for (String cod2 : codimp2) {
			if (!codimp1.contains(cod2)) {
				return false;
			}
		}
		
		//controllo sulla fase inizio e nuova fase 13 di sitat 7
		Long fasiLot1 = this.contrattiMapper.getFaseNonAccorpabili(Long.valueOf(lotto1.getCodgara()),Long.valueOf(lotto1.getCodLotto()));
		
		Long fasiLot2 = this.contrattiMapper.getFaseNonAccorpabili(Long.valueOf(lotto2.getCodgara()),Long.valueOf(lotto2.getCodLotto()));
		
		if((fasiLot1 != null && fasiLot1 > 0) || (fasiLot2 != null && fasiLot2 > 0)) {
			return false;
		}
		
		//controllo se la fase di aggiudicazione è stata pubblicata
		Long aggPubb1 = this.contrattiMapper.checkFaseAggPubblicata(Long.valueOf(lotto1.getCodgara()),Long.valueOf(lotto1.getCodLotto()));
		
		Long aggPubb2 = this.contrattiMapper.checkFaseAggPubblicata(Long.valueOf(lotto2.getCodgara()),Long.valueOf(lotto2.getCodLotto()));
		
		if((aggPubb1 != null && aggPubb1 == 0 || aggPubb1 == null) || (aggPubb2 != null && aggPubb2 == 0 || aggPubb2 == null)) {
			return false;
		}
		
		return true;
	}

	public ResponseRiepilogoAccorpamenti getRiepilogoAccorpamenti(Long codGara) {

		ResponseRiepilogoAccorpamenti risultato = new ResponseRiepilogoAccorpamenti();
		Map<String, List<String>> accorpamentiMap = new HashMap<String, List<String>>();
		List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(codGara);

		boolean allowMultiLotto = false;
		Map<String, Boolean> cigAccorpabileMap = new HashMap<String, Boolean>();
		for (LottoBaseEntry lotto : lottiGara) {

			if (lotto.getMasterCig() != null && !lotto.getMasterCig().equals(lotto.getCig())) {
				if (accorpamentiMap.containsKey(lotto.getMasterCig())) {
					accorpamentiMap.get(lotto.getMasterCig()).add(lotto.getCig());
				} else {
					List<String> lottiAccorpati = new ArrayList<String>();
					lottiAccorpati.add(lotto.getCig());
					accorpamentiMap.put(lotto.getMasterCig(), lottiAccorpati);
				}
			}
			if (lotto.getMasterCig() == null) {
				boolean accorpabile = isLottoAcccorpabile(lotto);
				cigAccorpabileMap.put(lotto.getCig(), accorpabile);
			}
		}

		for (LottoBaseEntry lotto : lottiGara) {
			if (cigAccorpabileMap.containsKey(lotto.getCig()) && cigAccorpabileMap.get(lotto.getCig()) == true) {

				for (LottoBaseEntry lotto2 : lottiGara) {
					if (lotto.getCig() != lotto2.getCig() && cigAccorpabileMap.get(lotto2.getCig()) == true) {
						if (areLottiAccorpabili(lotto, lotto2)) {
							allowMultiLotto = true;
							break;
						}
					}
				}
			}
		}

		DatiAccorpamentoEntry entry = new DatiAccorpamentoEntry();
		List<AccorpamentoEntry> accorpamenti = new ArrayList<AccorpamentoEntry>();

		for (String cigMaster : accorpamentiMap.keySet()) {
			AccorpamentoEntry accorpamento = new AccorpamentoEntry();
			accorpamento.setCigMaster(cigMaster);
			accorpamento.setCigAccorpati(accorpamentiMap.get(cigMaster));
			accorpamenti.add(accorpamento);
		}
		entry.setAccorpamenti(accorpamenti);
		entry.setAccorpamentiDisponibili(allowMultiLotto);
		risultato.setData(entry);
		return risultato;

	}

	public ResponseMultilotto getLottiAccorpabili(Long codGara, Long codLotto) {

		ResponseMultilotto risultato = new ResponseMultilotto();
		risultato.setEsito(true);
		List<LottoBaseEntry> lottiAccorpabili = new ArrayList<LottoBaseEntry>();
		try {
			LottoBaseEntry lottoMaster = this.contrattiMapper.getBaseDettaglioLotto(codGara, codLotto);
			List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(codGara);

			if (isLottoAcccorpabile(lottoMaster)) {
				for (LottoBaseEntry lottoGara : lottiGara) {
					if (!lottoMaster.getCig().equals(lottoGara.getCig()) && isLottoAcccorpabile(lottoGara)
							&& areLottiAccorpabili(lottoGara, lottoMaster)) {
						if (lottoGara.getMasterCig() == null) {
							lottiAccorpabili.add(lottoGara);
						}
					}
				}
			}

			risultato.setData(lottiAccorpabili);

		} catch (Exception e) {
			logger.error("Errore nel metodo getLottiAccorpabili. codgara  " + codGara + ", codlotto " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			return risultato;
		}
		return risultato;
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseAccorpaMultilotto accorpaMultilotto(Long codgara, Long codLottoMaster, List<Long> codLottoAccorpati,
			String authorization, Long syscon) throws Exception {
							
		ResponseAccorpaMultilotto risultato = new ResponseAccorpaMultilotto();
		risultato.setEsito(true);
		AccorpaMultilottoEntry accorpaMultilottoEntry = new AccorpaMultilottoEntry();
		List<String> cigInvalidi = new ArrayList<String>();
		PubblicazioneResult pubblicaGaraResult = null;
		String token = "";
		GaraEntry gara = this.contrattiMapper.dettaglioGara(codgara);
		
		if(gara.isPcp()) {
			try {
				LottoEntry lottoMaster = this.contrattiMapper.getDettaglioLotto(codgara, codLottoMaster);
				// Update MASTER_CIG_ML e SITUAZIONE
				for (Long codLottoAccorpato : codLottoAccorpati) {
					if (codLottoAccorpato != codLottoMaster) {
						this.contrattiMapper.updateSituazioneLotto(codgara, codLottoAccorpato, 7L);
					}
					this.contrattiMapper.updateMasterCig(codgara, codLottoAccorpato, lottoMaster.getCig());
				}
			}catch (Exception e) {
				logger.error("Errore in fase accorpaMultilottoPcp per il codgara: {} , {}",codgara,e);
				throw e;
			}
		} else {
			LoginResult responseLogin = this.contrattiRestClientManager.loginPubblicazioni(getChiaviAccessoORT(), authorization);
			if (responseLogin != null && responseLogin.isEsito()) {
				token = responseLogin.getToken();
			} else {
				logger.error("accorpaMultilotto : Errore in fase  di login ai servizi di pubblicazione gara");
				throw new Exception("Errore in fase di login ai servizi di pubblicazione gara");
			}
			ResponseRequestPubblicazione requestPubblicazioneGara = this.getRequestPubblicazioneGara(codgara,syscon);
			if (requestPubblicazioneGara != null && requestPubblicazioneGara.isEsito() == true
					&& requestPubblicazioneGara.getData() != null) {
				pubblicaGaraResult = this.contrattiRestClientManager.pubblicaGara(requestPubblicazioneGara.getData(), true,
						authorization, token);
			} else {
				logger.error("accorpaMultilotto : Errore in fase  di login ai servizi di pubblicazione gara");
				throw new Exception("Errore in fase di chiamata ai servizi di pubblicazione gara");
			}
			if (pubblicaGaraResult != null && isValidazioneSuperata(pubblicaGaraResult.getValidate())) {
				

				String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
				accorpaMultilottoEntry.setAnagraficaOk(true);
				codLottoAccorpati.add(codLottoMaster);
				for (Long codLottoAccorpato : codLottoAccorpati) {
					LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codgara, codLottoAccorpato);
					String request = this.getRequestFaseAggiudicazione(codgara, codLottoAccorpato, 1L);
					PubblicazioneResult pubblicaFaseResult = this.anacRestClientManager.pubblicaAggiudicazione(request,
							true, cfStazioneAppaltante, lotto.getCig(), gara, lotto,  authorization, token);
					if (!(pubblicaFaseResult != null && pubblicaFaseResult.getErrorData() == null
							&& isValidazioneSuperata(pubblicaFaseResult.getValidate()))) {
						cigInvalidi.add(lotto.getCig());
					}
				}
				accorpaMultilottoEntry.setAggiudicazioniOk(cigInvalidi.size() == 0);
				accorpaMultilottoEntry.setCigInvalidi(cigInvalidi);
				if (accorpaMultilottoEntry.isAggiudicazioniOk() == true
						&& accorpaMultilottoEntry.isAnagraficaOk() == true) {

					LottoEntry lottoMaster = this.contrattiMapper.getDettaglioLotto(codgara, codLottoMaster);
					// Update MASTER_CIG_ML e SITUAZIONE
					for (Long codLottoAccorpato : codLottoAccorpati) {
						if (codLottoAccorpato != codLottoMaster) {
							this.contrattiMapper.updateSituazioneLotto(codgara, codLottoAccorpato, 7L);
						}
						this.contrattiMapper.updateMasterCig(codgara, codLottoAccorpato, lottoMaster.getCig());
					}

					requestPubblicazioneGara = this.getRequestPubblicazioneGara(codgara,syscon);
					if (requestPubblicazioneGara != null && requestPubblicazioneGara.isEsito() == true
							&& requestPubblicazioneGara.getData() != null) {
						pubblicaGaraResult = this.contrattiRestClientManager.pubblicaGara(requestPubblicazioneGara.getData(),
								false, authorization, token);
						allineaPubblicazioneGara(codgara, cfStazioneAppaltante, new Long(pubblicaGaraResult.getId()), 2L,
								syscon,  new ObjectMapper().writeValueAsString(requestPubblicazioneGara.getData() ));
					} else {
						logger.error("accorpaMultilotto : Errore in fase  di login ai servizi di pubblicazione gara");
						throw new Exception("Errore in fase di chiamata ai servizi di pubblicazione gara");
					}
					for (Long codLottoAccorpato : codLottoAccorpati) {
						LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codgara, codLottoAccorpato);
						String request = this.getRequestFaseAggiudicazione(codgara, codLottoAccorpato, 1L);
						PubblicazioneResult pubblicaFaseResult = this.anacRestClientManager.pubblicaAggiudicazione(request,
								true, cfStazioneAppaltante, lotto.getCig(), gara, lotto, authorization, token);
						if ((pubblicaFaseResult != null && pubblicaFaseResult.getErrorData() == null
								&& isValidazioneSuperata(pubblicaFaseResult.getValidate()))) {
							allineaPubblicazioneFase(codgara, codLottoAccorpato, 1L, 1L, 2L,
									gara.getCodiceStazioneAppaltante(), syscon, null);
						} else {
							throw new Exception("Errore nella pubblicazione dell'aggiudicazione per la gara: " + codgara
									+ " , lottto" + codLottoAccorpato);
						}
					}
				}
				risultato.setData(accorpaMultilottoEntry);
			} else {
				accorpaMultilottoEntry.setAnagraficaOk(false);
				risultato.setData(accorpaMultilottoEntry);	
			}
			
		}
		return risultato;
	}

	public boolean isValidazioneSuperata(List<ValidateEntry> validazioni) {
		if (validazioni == null) {
			return true;
		}
		for (ValidateEntry v : validazioni) {
			if ("E".equals(v.getTipo())) {
				return false;
			}
		}
		return true;
	}

	public BaseResponse cleanAccorpamentiMultilotto(Long codGara) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			List<Long> codlottoAccorpati = this.contrattiMapper.getCodLottoAccorpati(codGara);
			this.contrattiMapper.cleanMasterCig(codGara);
			for (Long codLotto : codlottoAccorpati) {
				Long situazione = getSituazioneLotto(codGara, codLotto);
				this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazione);
			}

		} catch (Exception e) {
			logger.error("Errore in pulizia accorpamenti multilotto per la gara:" + codGara);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	private ChiaviAccesso getChiaviAccessoORT() {
		List<ChiaveConfigurazione> chiaviAccessoOrt = this.contrattiMapper.getChiaviAccessoOrt();
		ChiaviAccesso chiaviAccesso = new ChiaviAccesso();
		for (ChiaveConfigurazione chiave : chiaviAccessoOrt) {
			if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.username")) {
				chiaviAccesso.setUsername(chiave.getValore());
			}
			if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.clientKey")) {
				chiaviAccesso.setClientKey(chiave.getValore());
			}
			if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.clientId")) {
				chiaviAccesso.setClientId(chiave.getValore());
			}
			if (chiave.getChiave().equals("it.eldasoft.pubblicazioni.ws.password")) {
				chiaviAccesso.setPassword(chiave.getValore());
			}
		}
		return chiaviAccesso;
	}

	private String getCodiceNazioneByNumber(final Long nazioneNumber) {
		logger.debug("Execution start::getCodiceNazioneByNumber");

		String codiceNazione = null;

		if (nazioneNumber != null) {
			codiceNazione = nazioneNumber == 1L ? "IT" : this.contrattiMapper.getCodiceNazioneByNumber(nazioneNumber);
		}

		logger.debug("Execution end::getCodiceNazioneByNumber");

		return codiceNazione;
	}

	public AllegatoEntry downloadDocumentoAtto(final Long codGara, final Long idAllegato) {

		if (codGara != null && idAllegato != null) {
			AllegatoEntry doc = this.contrattiMapper.getAllegatoByCodGaraAndIdAllegato(codGara.toString(), idAllegato);
			if (doc != null) {
				if (doc.getFileAllegato() != null) {
					byte[] encodedFile = Base64.getEncoder().encode(doc.getFileAllegato());
					doc.setBinary(new String(encodedFile, StandardCharsets.UTF_8));
				}
				return doc;
			}
		}

		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseRequestPubblicazioneSmartCig getRequestPubblicazioneSmartCig(Long codGara, Long syscon, String cf) {
		ResponseRequestPubblicazioneSmartCig risultato = new ResponseRequestPubblicazioneSmartCig();
		SmartCigEntry smartCig = null;
		List<RecordFaseImpreseEntry> imprese;
		risultato.setEsito(true);
		try {
			PubblicaSmartCigEntry fullSmartCig = new PubblicaSmartCigEntry();
			smartCig = getSmartCig(codGara,syscon,cf);
			if (smartCig == null) {
				logger.error("Errore inaspettato in lettura dati gara per la pubblicazione. La gara " + codGara
						+ "non esiste.");
				risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND);
				risultato.setEsito(false);
				return risultato;
			}
			if(smartCig.getStazioneAppaltante() != null) {
				smartCig.setStazioneAppaltante(this.contrattiMapper.getCFSAByCode(smartCig.getStazioneAppaltante()));
			}
			
			imprese = this.contrattiMapper.getFaseImprese(codGara, 1L);
			if (imprese != null) {
				for (RecordFaseImpreseEntry impresa : imprese) {
					impresa.setImpresa(contrattiMapper.getImpresa(impresa.getCodImpresa()));
				}
			}
			fullSmartCig.setSmartcig(smartCig);
			fullSmartCig.setImprese(imprese);
			risultato.setData(fullSmartCig);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati gara per la pubblicazione. codgara = " + codGara, e);
			throw e;
		}
		return risultato;
	}

	public ResponseListaGareNonPaginata getListaGareNonPaginata(final GareNonPaginateSearchForm searchForm) {
		ResponseListaGareNonPaginata risultato = new ResponseListaGareNonPaginata();
		try {
			List<GaraBaseEntry> entries = new ArrayList<GaraBaseEntry>();

			if (StringUtils.isBlank(searchForm.getSearchString())) {
				risultato.setData(entries);
				risultato.setEsito(true);
				return risultato;
			} else {
				searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
			}

			// Controllo il ruolo dell'utente. Se non è amministratore le gare vanno
			// filtrate per il codice
			// RUP associato al suo record della tabella tecni
			String ruolo = this.contrattiMapper.getRuolo(searchForm.getSyscon());
			List<String> codiceTecnicoList = this.contrattiMapper.getCodiceForSA(searchForm.getSyscon(),
					searchForm.getStazioneAppaltante());
			String codiceTecnico = null;
			if(codiceTecnicoList != null && codiceTecnicoList.size() >0) {
				codiceTecnico = codiceTecnicoList.get(0);
			}
			
			if (!"A".equals(ruolo) && !"C".equals(ruolo)) {
				if (StringUtils.isNotBlank(codiceTecnico)) {
					searchForm.setCodiceTecnico(codiceTecnico);
				}
			} else {
				// set syscon a null per controllare il caso in cui ho creato la gara (ad es.
				// smartCIG)
				// e non sono rup della stazione appaltante
				searchForm.setSyscon(null);
			}

			if(searchForm.getStazioneAppaltante() != null && "*".equals(searchForm.getStazioneAppaltante())) {
				searchForm.setStazioneAppaltante(null);
			}
			entries = this.contrattiMapper.getListaGareNonPaginata(searchForm);

			for (GaraBaseEntry gara : entries) {
				List<LottoBaseEntry> lottiGara = this.contrattiMapper.getLottiGara(gara.getCodgara());
				Long numPubblicazioni = this.contrattiMapper.esistonoPubblicazioniGara(gara.getCodgara());
				boolean deletable = (lottiGara == null || lottiGara.size() == 0)
						&& (numPubblicazioni == null || numPubblicazioni == 0L);
				gara.setDeletable(deletable);
				gara.setTrasferimentoRUP(isUserAdminOrRUPGara(ruolo, codiceTecnico, gara));
				String cigLotti = "";
				int counter = 1;
				for (LottoBaseEntry lotto : lottiGara) {
					cigLotti += (counter == lottiGara.size() || counter == 5) ? lotto.getCig() : lotto.getCig() + " - ";
					if (counter == 5 && counter < lottiGara.size()) {
						cigLotti += "...";
						break;
					}
					counter++;
				}
				gara.setCigLotti(cigLotti);

				boolean isSmartCig = false;
				if (lottiGara != null && lottiGara.size() == 1
						&& (cigLotti.startsWith("X") || cigLotti.startsWith("Y") || cigLotti.startsWith("Z"))) {
					isSmartCig = true;
					gara.setDeletable(numPubblicazioni == 0L);
				}
				gara.setSmartCig(isSmartCig);
			}

			risultato.setData(entries);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore nel recupero della lista gare non paginata", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert importaSmartcigAnac(String data, String username, String password, String codiceStazioneAppaltante, Boolean save, Long syscon) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		try {
			
			if(save != null && save == true) {
				String encriptedPwd = this.getEncryptedPwd(password);
				CredenzialiSimog credenziali = this.contrattiMapper.getSimogCredentials(syscon);
				if(credenziali != null && credenziali.getSimogUser()!= null && credenziali.getSimogPassword() != null) {
					this.contrattiMapper.updateCredenzialiSimog(username, encriptedPwd, syscon);
				} else {
					this.contrattiMapper.insertCredenzialiSimog(username, encriptedPwd, syscon);
				}
			}
			
			ResponseListaCollaborazioni collaborazioni =  this.anacRestClientManager.getListaCollaborazioni(null, username, password);
			ResponseConsultaComunicazioneResult res = null;
			if(!collaborazioni.isEsito()) {
				risultato.setErrorData(collaborazioni.getErrorData());
				risultato.setEsito(false);
				return risultato;
			}
			
			if(collaborazioni != null && collaborazioni.getData() != null && collaborazioni.getData().size() > 0) {
				for(Collaborazione coll : collaborazioni.getData()) {
					if(coll.getUfficioId() != null && !"null".equals(coll.getUfficioId())  && !"".equals(coll.getUfficioId())) {
						res = this.anacRestClientManager.importSmartcig(data, coll.getUfficioId(), username,  password);
						if(res.isEsito() == true) {
							SmartCigInsertForm insertForm = mappingSmartCig(res.getComunicazione(), codiceStazioneAppaltante, username);
							risultato =   this.insertSmartCig(insertForm);
							return risultato;
						}
					}
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData("NO_VALID_INDEX");
			}
			risultato.setEsito(false);
			if(res != null) {
				risultato.setErrorData(res.getErrorData());
			} else {
				risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			}
		} catch (Exception e) {
			logger.error("Errore durante il recupero dello smartcig da anac", e);
			throw e;
		}
		
		return risultato;
	}

	private String getEncryptedPwd(String password) {
		try {
			ICriptazioneByte criptatore = FactoryCriptazioneByte.getInstance(
					FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, password.getBytes(),
					ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

			return new String(criptatore.getDatoCifrato());
		} catch (CriptazioneException e) {
			logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
			return null;
		}
	}

	private String getDecryptedPwd(String password) {
		try {
			ICriptazioneByte decript = FactoryCriptazioneByte.getInstance(
					FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, password.getBytes(),
					ICriptazioneByte.FORMATO_DATO_CIFRATO);

			return new String(decript.getDatoNonCifrato());
		} catch (CriptazioneException e) {
			logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private SmartCigInsertForm mappingSmartCig(ComunicazioneType comunicazione, String codiceStazioneAppaltante, String cfRup) throws Exception {
		
		String codtec = this.contrattiMapper.getCodtecByCfAndSA(cfRup.toUpperCase(), codiceStazioneAppaltante);
		if(codtec == null) {
			codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
			RupEntry nuovoTecnico = new RupEntry();
			nuovoTecnico.setCodice(codtec);
			nuovoTecnico.setCf(cfRup.toUpperCase());
			nuovoTecnico.setNominativo(cfRup);
			nuovoTecnico.setStazioneAppaltante(codiceStazioneAppaltante);
			this.contrattiMapper.insertRUP(nuovoTecnico);
		}
		
		
		SmartCigInsertForm smartcigForm = new SmartCigInsertForm();
		smartcigForm.setCup(comunicazione.getCup());
		smartcigForm.setFlagSaAgente(2L);
		double importo = comunicazione.getImporto() == null ? null : comunicazione.getImporto().doubleValue();
		smartcigForm.setImportoNetto(importo);
		smartcigForm.setImportoSicurezza(new Double(0));
		double impportoTotale = comunicazione.getImporto() == null ? null : comunicazione.getImporto().doubleValue();
		smartcigForm.setImportoTotale(impportoTotale);
		smartcigForm.setOggetto(comunicazione.getOggetto());
		smartcigForm.setRup(codtec);
		String sceltaContraenteString = comunicazione.getCodiceProceduraSceltaContraente() == null? null : this.contrattiMapper.getIdSceltaContraente(comunicazione.getCodiceProceduraSceltaContraente());
		Long idSceltaContraente = sceltaContraenteString == null? null : new Long (sceltaContraenteString);
		smartcigForm.setSceltaContraente(idSceltaContraente);
		smartcigForm.setSmartCig(comunicazione.getCig());
		smartcigForm.setStazioneAppaltante(codiceStazioneAppaltante);
		smartcigForm.setTipoAppalto(comunicazione.getCodiceClassificazioneGara());
		return smartcigForm;

	}

	public ResponseInizImportSmartcig getInizImportSmartcig(Long syscon) {
		ResponseInizImportSmartcig risultato = new ResponseInizImportSmartcig();
		risultato.setEsito(true);
		try {
			CredenzialiSimog credenziali = this.contrattiMapper.getSimogCredentials(syscon);
			if(credenziali != null && credenziali.getSimogPassword() != null) {
				String passwordInChiaro = this.getDecryptedPwd(credenziali.getSimogPassword());
				credenziali.setSimogPassword(passwordInChiaro);
			} else {
				credenziali = new CredenzialiSimog();
			}
			risultato.setData(credenziali);
			return risultato;
		} catch (Exception e) {
			logger.error("Errore in getInizImportSmartcig", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			return risultato;
		}
	}

	public String getAbilitazioniUtente(Long syscon) {
			return this.contrattiMapper.getAbilitazioniUtente(syscon);
	}

	private String extractCsv(List<GaraEntry> gare, String stazioneAppaltante) throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			/*
			StringBuilder builder = new StringBuilder();
			String columnNamesList = "Codice univoco della gara;Oggetto di gara;Situazione di gara;Tipologia di gara;Importo della gara;Flag cancellazione abilitata/disabilitata;"
					+ "Numero ANAC della gara;Codici CIG dei lotti della gara;RUP della gara;Nominativo RUP della gara;Flag trasferimento RUP abilitato/disabilitato;Flag che identifica se la gara è uno SMART CIG;Data pubblicazione gara";
			builder.append(columnNamesList + "\n");



			
			for(GaraBaseEntry entry : gare){
				builder.append(entry.getCodgara() == null? "" : entry.getCodgara());
				builder.append(";");
				builder.append(entry.getOggetto() == null? "" : CsvUtil.escapeCsv(entry.getOggetto()));
				builder.append(";");
				builder.append(entry.getSituazione() == null? "" : entry.getSituazione());
				builder.append(";");
				builder.append(entry.getTipoApp() == null? "" : entry.getTipoApp());
				builder.append(";");
				builder.append(formatImporto(entry.getImportoGara()));
				builder.append(";");
				builder.append(entry.isDeletable());
				builder.append(";");
				builder.append(entry.getIdentificativoGara() == null? "" : CsvUtil.escapeCsv(entry.getIdentificativoGara()));
				builder.append(";");
				builder.append(entry.getCigLotti() == null? "" : CsvUtil.escapeCsv(entry.getCigLotti()));
				builder.append(";");
				builder.append(entry.getRup() == null? "" : CsvUtil.escapeCsv(entry.getRup()));
				builder.append(";");
				builder.append(entry.getDescRup() == null? "" : CsvUtil.escapeCsv(entry.getDescRup()));
				builder.append(";");
				builder.append(entry.isTrasferimentoRUP());
				builder.append(";");
				builder.append(entry.isSmartCig());
				builder.append(";");
				builder.append(entry.getDataPubblicazione() == null? "" : CsvUtil.escapeCsv(sdf.format(entry.getDataPubblicazione())));
				builder.append("\n");
				
			}
			return builder.toString();
			*/
			
			StringWriter out = new StringWriter();
			
			if(stazioneAppaltante == null){
				String[] HEADERS = {"Stazione Appaltante",
						"Numero gara",
						"Oggetto",
						"stato",
						"Importo totale",
						"CIG",
						"RUP",
						"Data pubblicazione"};
											
				
								
				final CSVFormat csvFormat = CSVFormat.Builder.create()
					        .setHeader(HEADERS)
					        .setDelimiter(";")
					        .setAllowMissingColumnNames(true)
					        .build();
					
				CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);
				
			
				for (GaraEntry entry : gare) {
					String situazione = "";
					if(entry.getTipoApp() != null) {
						situazione = this.contrattiMapper.getValoreTabellato("W9007", entry.getSituazione());
					}
					
					csvPrinter.printRecord(
							entry.getNominativoStazioneAppaltante(),
							entry.getIdentificativoGara(),
							entry.getOggetto() == null? "" : CsvUtil.escapeCsv(entry.getOggetto()),
							situazione,
							formatImporto(entry.getImportoGara()),
							entry.getCigLotti()	,				
							entry.getRup() == null? "" : CsvUtil.escapeCsv(entry.getDescRup()),
							entry.getDataPubblicazione() == null? "" : CsvUtil.escapeCsv(sdf.format(entry.getDataPubblicazione())));
													
			    }
			} else {
				String[] HEADERS = {"Numero gara",
						"Oggetto",
						"stato",
						"Importo totale",
						"CIG",
						"RUP",
						"Data pubblicazione"};
											
				
								
				final CSVFormat csvFormat = CSVFormat.Builder.create()
					        .setHeader(HEADERS)
					        .setDelimiter(";")
					        .setAllowMissingColumnNames(true)
					        .build();
					
				CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);
				
				for (GaraBaseEntry entry : gare) {
					String situazione = "";
					if(entry.getSituazione() != null) {
						situazione = this.contrattiMapper.getValoreTabellato("W9007", entry.getSituazione());
					}
					
					csvPrinter.printRecord(
							entry.getIdentificativoGara(),
							entry.getOggetto() == null? "" : CsvUtil.escapeCsv(entry.getOggetto()),
							situazione,
							formatImporto(entry.getImportoGara()),
							entry.getCigLotti()	,				
							entry.getRup() == null? "" : CsvUtil.escapeCsv(entry.getDescRup()),
							entry.getDataPubblicazione() == null? "" : CsvUtil.escapeCsv(sdf.format(entry.getDataPubblicazione())));
													
			    }
			}
			
			
		            
		   
			return out.toString();
			
		} catch (Exception e) {
			logger.error("Errore in estrazione file CSV", e);
			throw e;
		}
	}

	private String formatImporto(Double importo){
		if(importo == null){
			return "";
		}
        NumberFormat decimalFormat = new DecimalFormat("#.00");
        String importoStringa = decimalFormat.format(importo);
       	return ".00".equals(importoStringa) ? "0.00" : importoStringa;
	}

	public ResponseResult pubblicaAtto(String authorization, String xLoginMode, Long codGara, Long numPubb){

		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);

		try {

			DettaglioAttoEntry attoDaPubblicare = this.contrattiMapper.getDettaglioAttoSingolo(codGara, numPubb);

			if(attoDaPubblicare != null){

				//Modifico dataPubbSistema con la data corrente.
				this.contrattiMapper.pubblicaAttoSingolo(codGara, numPubb, new Date());
			}

			risultato.setData(codGara.toString());
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			logger.error("Si è verificato un errore nel metodo pubblicaAtto",e);
		}

		return risultato;
	}

	public ResponsePubbAtti pubblicaAtti(String authorization,Long codGara, Long syscon, Boolean pubblicatoTutto) {
		ResponsePubbAtti risultato = new ResponsePubbAtti();
		risultato.setEsito(true);
		List<AttiPubblicatiEntry> pubbAttiResultMap = new ArrayList<AttiPubblicatiEntry>();
		try {
			List<AttiPubbEntry> attiList = this.contrattiMapper.getListaAtti(codGara);
			
			List<PubblicaAttoServerEntry> pubbAttiList = new ArrayList<PubblicaAttoServerEntry>();			
			if(attiList != null && attiList.size() > 0) {	
				Long attoPubblicato = 0L;				
				for (AttiPubbEntry attoEntry : attiList) {	
					attoPubblicato = 0L;
					if(!pubblicatoTutto) {
						attoPubblicato = this.contrattiMapper.checkIfAttoisPubblicato(codGara,attoEntry.getNumPubb());
						if(attoPubblicato == 0L) {
							PubblicaAttoServerEntry pubbAtti = this.getRequestPubblicazioneAttiServer(codGara, attoEntry.getTipoDoc(), attoEntry.getNumPubb(),syscon);
							if(pubbAtti != null) {					
								pubbAttiList.add(pubbAtti);
							}
						}
					} else {
						PubblicaAttoServerEntry pubbAtti = this.getRequestPubblicazioneAttiServer(codGara, attoEntry.getTipoDoc(), attoEntry.getNumPubb(),syscon);
						if(pubbAtti != null) {					
							pubbAttiList.add(pubbAtti);
						}
					}									
				}
			} else {
				risultato.setEsito(false);
				risultato.setErrorData(ResponsePubbAtti.PUBBLICAZIONI_NO_ATTI);
				return risultato;
			}
			AttiPubblicatiEntry temp = new AttiPubblicatiEntry();
			PubblicazioneAttoResult pubbResult = null;
			LoginResult responseLogin = this.contrattiRestClientManager.loginPubblicazioni(getChiaviAccessoORT(), authorization);
			for(PubblicaAttoServerEntry atto : pubbAttiList) {
				temp = new AttiPubblicatiEntry();
				String token = null;
				if(responseLogin != null && responseLogin.isEsito() && responseLogin.getToken() != null) {					
					token = responseLogin.getToken();
				} else {
					risultato.setEsito(false);
					risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
					return risultato;
				} 
				pubbResult = this.contrattiRestClientManager.pubblicaAtto(atto, false, authorization, token);
				if(pubbResult.getEsito()) {				
					this.allineaPubblicazioneAtto(codGara, atto.getTipoDocumento(), atto.getNumeroPubblicazione(), 1L, pubbResult.getIdGara(), pubbResult.getIdExArt29(), syscon, new ObjectMapper().writeValueAsString(atto));
					temp.setValidato(true);
					
				} else {					
					temp.setValidato(false);					
				}
				String label = atto.getEventualeSpecificazione();
				if(StringUtils.isBlank(label) && atto.getDataPubblicazione() != null) {
					label = atto.getDataPubblicazione().substring(0,10);
				}
				temp.setLabel(label);
				temp.setNome(this.contrattiMapper.getNomeAtto(atto.getTipoDocumento()));	
				temp.setNumPub(atto.getNumeroPubblicazione());
				temp.setTipDoc(atto.getTipoDocumento());
				pubbAttiResultMap.add(temp);
			}
			risultato.setData(pubbAttiResultMap);
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			logger.error("Si è verificato un errore nel metodo pubblicaAtti",e);
		}
		
				
		 return risultato;
	}

	public BaseResponse deleteFlussoGara(Long codGara) {
		BaseResponse risultato = new BaseResponse();
		try {
			this.contrattiMapper.deleteFlussoGara(codGara);			
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante la delete del flusso della pubblicazione della gara con codgara: " + codGara, e);
			throw e;
		}
		return risultato;
	}

	public BaseResponse deleteFlussoAtto(Long codGara,Long numPubb) {
		BaseResponse risultato = new BaseResponse();
		try {
			this.contrattiMapper.deleteFlussoAtto(codGara,numPubb);			
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante la delete del flusso della pubblicazione degli atti della gara con codgara: " + codGara + " e numPubb: " + numPubb, e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFlussoLotto(Long codGara,Long codLotto) {
		BaseResponse risultato = new BaseResponse();
		try {
			this.contrattiMapper.deleteFlussoLotto(codGara,codLotto);	
			this.contrattiMapper.deleteFlussoEliminatiLotto(codGara,codLotto);	
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante la delete del flusso della pubblicazione delle schede della gara con codgara: " + codGara + " e codlotto: " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public BaseResponse trasferisciReferenteGara(CambioReferenteForm form) {
		BaseResponse risultato = new  BaseResponse();
		try {
			
			GaraEntry gara = this.contrattiMapper.dettaglioGara(form.getCodGara());	
			if(gara == null) {
				logger.error("errore in trasferisciReferenteGara. Non esiste gara con il codgara " + form.getCodGara());
				risultato.setEsito(false);
				risultato.setErrorData("La gara non esiste");
				return risultato;

			}
			String ruolo = this.contrattiMapper.getRuolo(form.getSyscon());	
			if(ruolo == null) {
				logger.error("errore in trasferisciReferenteGara. Non esiste utente con syscon " + form.getSyscon());
				risultato.setEsito(false);
				risultato.setErrorData("La gara non esiste");
				return risultato;

			}
			this.contrattiMapper.updateSysconGara(form.getCodGara(), form.getSyscon());
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante la trasferisciReferenteGara. codgara: " + form.getCodGara() + " syscon: " + form.getSyscon(), e);
			throw e;
		}		
		return risultato;		
	}

	public GeneralBaseResponse < String > creaReportAnomalia(Long codGara, Long codLot, String cig, String stazioneAppaltante) {
	    GeneralBaseResponse < String > result = new GeneralBaseResponse < String > ();
	    result.setEsito(true);
	    try {

	        String denominazioneEnte = this.contrattiMapper.getNominativoSa(stazioneAppaltante);

	        List < String > denominazioneRUPList = this.contrattiMapper.getNomtecByCodgara(codGara);
	        String denominazioneRUP = denominazioneRUPList != null && denominazioneRUPList.size() > 0 ? denominazioneRUPList.get(0) : "";

	        List < LottoIndEntry > lotti = this.contrattiMapper.getLottoByCig(cig);
	        if (lotti != null && lotti.size() > 0) {
	            LottoIndEntry lotto = lotti.get(0);
	            String oggettoLotto = lotto.getOggettoLotto();
	            String codiceCPV = lotto.getCodiceCPV();
	            String tipoContratto = lotto.getTipoContratto();
	            String descrizioneTipoContratto = lotto.getDescrizioneTipoContratto();
	            String idCategoriaPrevalente = lotto.getIdCategoriaPrevalente();
	            String tipoAppalto = lotto.getTipoAppalto();
	            String descrizioneTipoAppalto = lotto.getDescrizioneTipoAppalto();
	            String idSceltaContraente = lotto.getIdSceltaContraente();
	            String classeImporto = lotto.getClasseImporto();
	            String criterioAggiudicazione = lotto.getCriterioAggiudicazione();
	            Double importoTotaleLotto = lotto.getImportoTotaleLotto();
	            Double importoAggiudicazione = lotto.getImportoAggiudicazione();

	            String strImportoTotaleLotto = "";
	            String strImportoAggiudicazione = "";
	            if (importoTotaleLotto != null) {
	                strImportoTotaleLotto = String.format("%.2f", importoTotaleLotto);
	            }

	            if (importoAggiudicazione != null) {
	                strImportoAggiudicazione = String.format("%.2f", importoAggiudicazione);
	            }

	            Long idAppalto = null;
	            if (StringUtils.isNotEmpty(tipoContratto)) {
	                if (tipoContratto.indexOf("L") >= 0) {
	                    idAppalto = this.contrattiMapper.getIdAppaltoLav(codGara, codLot);
	                } else {
	                    idAppalto = this.contrattiMapper.getIdAppaltoForn(codGara, codLot);
	                }
	            }
	            String tipologiaLavori = "";
	            if (idAppalto != null) {
	                tipologiaLavori = ContrattiManager.fillLeft(idAppalto.toString(), '0', 2);
	            }

	            List < IndicatoreBean > listaIndicatoriAffidamento = this.calcolaIndicatori(cig, true);
	            List < IndicatoreBean > listaIndicatoriEsecuzione = this.calcolaIndicatori(cig, false);

	            String descrizioneIncongruita = null;

	            if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
	                IndicatoreBean indicatore = listaIndicatoriAffidamento.get(0);
	                if (StringUtils.isNotEmpty(indicatore.getDescrizioneIncongruita())) {
	                    descrizioneIncongruita = indicatore.getDescrizioneIncongruita();
	                }
	            } else {
	                descrizioneIncongruita = "";
	            }

	            String PROP_JRREPORT_SOURCE_DIR = "jrReport/IndicatoriLotto/";

	            String jrReportSourceDir = null;
	            // Input stream del file sorgente
	            InputStream inputStreamJrReport = null;

	            Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "indicatoriLotto.jasper");
	            inputStreamJrReport = resource.getInputStream();
	            jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;

	            // Parametri
	            HashMap < String, Object > jrParameters = new HashMap < String, Object > ();
	            jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
	            jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);

	            Map < String, Object > parameters = new HashMap < String, Object > ();
	            parameters.put("denominazioneEnte", denominazioneEnte);
	            parameters.put("denominazioneRUP", denominazioneRUP);
	            parameters.put("codiceCIG", cig);
	            parameters.put("oggettoLotto", oggettoLotto);
	            parameters.put("codiceCPV", codiceCPV);
	            parameters.put("tipoContratto", tipoContratto);
	            parameters.put("descTipoContratto", descrizioneTipoContratto);
	            parameters.put("idCategoriaPrevalente", idCategoriaPrevalente);
	            parameters.put("idSceltaContraente", idSceltaContraente);
	            parameters.put("classeImporto", classeImporto);
	            parameters.put("tipologiaLavori", tipologiaLavori);
	            parameters.put("criterioAggiudicazione", criterioAggiudicazione);
	            parameters.put("importoTotaleLotto", strImportoTotaleLotto);
	            parameters.put("importoAggiudicazione", strImportoAggiudicazione);

	            if (StringUtils.isNotEmpty(tipoAppalto)) {
	                if (StringUtils.isNotEmpty(descrizioneTipoAppalto)) {
	                    parameters.put("tipoAppalto", tipoAppalto.concat(" - ").concat(descrizioneTipoAppalto));
	                } else {
	                    parameters.put("tipoAppalto", tipoAppalto);
	                }
	            } else {
	                parameters.put("tipoAppalto", "");
	            }

	            parameters.put("messaggioValoriNonCongrui01", descrizioneIncongruita);
	            if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
	                parameters.put("indicatoriAffidamento", listaIndicatoriAffidamento);
	            } else {
	                parameters.put("indicatoriAffidamento", null);
	            }
	            if (listaIndicatoriEsecuzione != null && listaIndicatoriEsecuzione.size() > 0) {
	                parameters.put("indicatoriEsecuzione", listaIndicatoriEsecuzione);
	            } else {
	                parameters.put("indicatoriEsecuzione", null);
	            }

	            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStreamJrReport, parameters, new JREmptyDataSource());

	            // Output stream del risultato
	            ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();

	            JRPdfExporter jrExporter = new JRPdfExporter();
	            jrExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	            jrExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baosJrReport));
	            jrExporter.exportReport();


	            inputStreamJrReport.close();
	            baosJrReport.close();

	            //UtilityWeb.download("reportAnomalia_" + cig + ".pdf", baosJrReport.toByteArray(), response);

	            String encodedPdf = Base64.getEncoder().encodeToString(baosJrReport.toByteArray());
	            result.setData(encodedPdf);
	        }

	    } catch (SQLException se) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore nell'estrazione dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG=" + cig, se);
	    } catch (JRException je) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore nella generazione del pdf con jasperReport", je);
	    } catch (IOException ioe) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore di accesso al pdf generato con jasperReport", ioe);
	    } catch (Exception ge) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore nell'estrazione degli importi dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG=" + cig, ge);
	    }

	    return result;
	}

	public GeneralBaseResponse < String > creaReportPreliminare(Long codGara, Long codLot, String cig, String stazioneAppaltante) {


	    GeneralBaseResponse < String > result = new GeneralBaseResponse < String > ();
	    result.setEsito(true);

	    try {

	        String denominazioneEnte = this.contrattiMapper.getNominativoSa(stazioneAppaltante);

	        List < String > denominazioneRUPList = this.contrattiMapper.getNomtecByCodgara(codGara);
	        String denominazioneRUP = denominazioneRUPList != null && denominazioneRUPList.size() > 0 ? denominazioneRUPList.get(0) : "";

	        List < LottoIndEntry > lotti = this.contrattiMapper.getLottoByCig(cig);


	        if (lotti != null && lotti.size() > 0) {
	            LottoIndEntry lotto = lotti.get(0);
	            String oggettoLotto = lotto.getOggettoLotto();
	            String codiceCPV = lotto.getCodiceCPV();
	            String tipoContratto = lotto.getTipoContratto();
	            String descrizioneTipoContratto = lotto.getDescrizioneTipoContratto();
	            String idCategoriaPrevalente = lotto.getIdCategoriaPrevalente();
	            String tipoAppalto = lotto.getTipoAppalto();
	            String descrizioneTipoAppalto = lotto.getDescrizioneTipoAppalto();
	            String idSceltaContraente = lotto.getIdSceltaContraente();
	            String classeImporto = lotto.getClasseImporto();
	            String criterioAggiudicazione = lotto.getCriterioAggiudicazione();
	            Double importoTotaleLotto = lotto.getImportoTotaleLotto();
	            Double importoAggiudicazione = lotto.getImportoAggiudicazione();

	            String strImportoTotaleLotto = "";
	            String strImportoAggiudicazione = "";
	            if (importoTotaleLotto != null) {
	                strImportoTotaleLotto = String.format("%.2f", importoTotaleLotto);
	            }

	            if (importoAggiudicazione != null) {
	                strImportoAggiudicazione = String.format("%.2f", importoAggiudicazione);
	            }

	            Long idAppalto = null;
	            if (StringUtils.isNotEmpty(tipoContratto)) {
	                if (tipoContratto.indexOf("L") >= 0) {
	                    idAppalto = this.contrattiMapper.getIdAppaltoLav(codGara, codLot);
	                } else {
	                    idAppalto = this.contrattiMapper.getIdAppaltoForn(codGara, codLot);
	                }
	            }
	            String tipologiaLavori = "";
	            if (idAppalto != null) {
	                tipologiaLavori = ContrattiManager.fillLeft(idAppalto.toString(), '0', 2);
	            }


	            List < IndicatorePreliminareBean > listaIndicatoriAffidamento = this.calcolaIndicatoriPreliminare(cig, stazioneAppaltante, true);
	            List < IndicatorePreliminareBean > listaIndicatoriEsecuzione = this.calcolaIndicatoriPreliminare(cig, stazioneAppaltante, false);

	            String descrizioneIncongruita = null;

	            if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
	                IndicatorePreliminareBean indicatore = listaIndicatoriAffidamento.get(0);
	                if (StringUtils.isNotEmpty(indicatore.getDescrizioneIncongruita())) {
	                    descrizioneIncongruita = indicatore.getDescrizioneIncongruita();
	                }
	            } else {
	                descrizioneIncongruita = "";
	            }

	            String PROP_JRREPORT_SOURCE_DIR = "jrReport/IndicatoriLotto/";

	            String jrReportSourceDir = null;
	            // Input stream del file sorgente
	            InputStream inputStreamJrReport = null;

	            Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "indicatoriLotto.jasper");
	            inputStreamJrReport = resource.getInputStream();
	            jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;

	            // Parametri
	            HashMap < String, Object > jrParameters = new HashMap < String, Object > ();
	            jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
	            jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);

	            Map < String, Object > parameters = new HashMap < String, Object > ();
	            parameters.put("denominazioneEnte", denominazioneEnte);
	            parameters.put("denominazioneRUP", denominazioneRUP);
	            parameters.put("codiceCIG", cig);
	            parameters.put("oggettoLotto", oggettoLotto);
	            parameters.put("codiceCPV", codiceCPV);
	            parameters.put("tipoContratto", tipoContratto);
	            parameters.put("descTipoContratto", descrizioneTipoContratto);
	            parameters.put("idCategoriaPrevalente", idCategoriaPrevalente);
	            parameters.put("idSceltaContraente", idSceltaContraente);
	            parameters.put("classeImporto", classeImporto);
	            parameters.put("criterioAggiudicazione", criterioAggiudicazione);
	            parameters.put("tipologiaLavori", tipologiaLavori);
	            parameters.put("importoTotaleLotto", strImportoTotaleLotto);
	            parameters.put("importoAggiudicazione", strImportoAggiudicazione);

	            parameters.put("messaggioValoriNonCongrui01", descrizioneIncongruita);
	            if (listaIndicatoriAffidamento != null && listaIndicatoriAffidamento.size() > 0) {
	                parameters.put("indicatoriAffidamento", listaIndicatoriAffidamento);
	            } else {
	                parameters.put("indicatoriAffidamento", null);
	            }
	            if (listaIndicatoriEsecuzione != null && listaIndicatoriEsecuzione.size() > 0) {
	                parameters.put("indicatoriEsecuzione", listaIndicatoriEsecuzione);
	            } else {
	                parameters.put("indicatoriEsecuzione", null);
	            }

	            if (StringUtils.isNotEmpty(tipoAppalto)) {
	                if (StringUtils.isNotEmpty(descrizioneTipoAppalto)) {
	                    parameters.put("tipoAppalto", tipoAppalto.concat(" - ").concat(descrizioneTipoAppalto));
	                } else {
	                    parameters.put("tipoAppalto", tipoAppalto);
	                }
	            } else {
	                parameters.put("tipoAppalto", "");
	            }

	            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStreamJrReport, parameters, new JREmptyDataSource());

	            // Output stream del risultato
	            ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();

	            JRPdfExporter jrExporter = new JRPdfExporter();
	            jrExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	            jrExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baosJrReport));
	            jrExporter.exportReport();


	            inputStreamJrReport.close();
	            baosJrReport.close();

	            //UtilityWeb.download("reportPreliminari_" + codiceCIG + ".pdf", baosJrReport.toByteArray(), response);

	            String encodedPdf = Base64.getEncoder().encodeToString(baosJrReport.toByteArray());
	            result.setData(encodedPdf);
	        }
	    } catch (SQLException se) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore nell'estrazione dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG=" + cig, se);
	    } catch (JRException je) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore nella generazione del pdf con jasperReport", je);
	    } catch (IOException ioe) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore di accesso al pdf generato con jasperReport", ioe);
	    } catch (Exception ge) {
	        result.setEsito(false);
	        result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
	        logger.error("Errore nell'estrazione degli importi dei dati generali del lotto nel calcolo degli indicatori del lotto con CIG=" + cig, ge);
	    }

	    return result;

	}


	public List < IndicatoreBean > calcolaIndicatori(String codiceCIG, boolean isAffidamento) throws SQLException {


	    String tipoDiContratto = this.contrattiMapper.getTipoByCig(codiceCIG);

	    List < IndicatoreEntry > listaIndicatori = null;

	    if (isAffidamento) {
	        listaIndicatori = this.contrattiMapper.getListaIndicatoriFaseA(tipoDiContratto);
	    } else {
	        listaIndicatori = this.contrattiMapper.getListaIndicatoriFaseE(tipoDiContratto);
	    }

	    List < IndicatoreBean > listaIndicatoriBean = new ArrayList < IndicatoreBean > ();

	    if (listaIndicatori != null && listaIndicatori.size() > 0) {
	        for (IndicatoreEntry ind: listaIndicatori) {

	            if (ind.getAffidabilitaStima() != null) {

	                String congruo = ind.getCongruo();
	                String calcolabile = ind.getCalcolabile();
	                Double sogliaInferiore = ind.getSogliaInferiore();
	                Double sogliaSuperiore = ind.getSogliaSuperiore();
	                String descrizione = ind.getDescrizione();
	                String unitaDiMisura = ind.getUnitaDiMisura();
	                String strSql = ind.getStrSql();
	                String tipo = ind.getTipo();
	                String tipoSoglia = ind.getTipoSoglia();
	                String livello = ind.getLivello();
	                String descrIncongruita = ind.getDescrIncongruita();
	                String affidabilitaStima = ind.getAffidabilitaStima();
	                String numerositaStima = ind.getNumerositaStima();

	                IndicatoreBean indicatore = new IndicatoreBean();
	                indicatore.setDescrizione(descrizione);
	                indicatore.setCalcolabile("1".equals(calcolabile));

	                Object valore = null;
	                if (StringUtils.isNotEmpty(strSql)) {
	                    strSql = strSql.replace("?", "'" + codiceCIG + "'");
	                    valore = this.sqlMapper.executeReturnString(strSql);
	                }

	                if (StringUtils.isNotEmpty(unitaDiMisura)) {
	                    indicatore.setUnitaDiMisura(unitaDiMisura);
	                }

	                if (StringUtils.isNotEmpty(congruo)) {
	                    indicatore.setCongruo(congruo);
	                }

	                if (StringUtils.isNotEmpty(descrIncongruita)) {
	                    indicatore.setDescrizioneIncongruita(descrIncongruita);
	                }

	                if (indicatore.getCalcolabile()) {
	                    if (StringUtils.isNotEmpty(tipoSoglia)) {
	                        indicatore.setTipoSoglia(tipoSoglia);

	                        // Determinazione dei valori delle soglie
	                        if ("I".equals(tipoSoglia)) {
	                            if (sogliaInferiore != null) {
	                                indicatore.setSogliaInferiore(String.valueOf(sogliaInferiore));
	                            } else {
	                                indicatore.setSogliaInferiore("");
	                            }
	                            indicatore.setSogliaSuperiore("-");
	                        } else if ("S".equals(tipoSoglia)) {
	                            if (sogliaSuperiore != null) {
	                                indicatore.setSogliaSuperiore(String.valueOf(sogliaSuperiore));
	                            } else {
	                                indicatore.setSogliaSuperiore("");
	                            }
	                            indicatore.setSogliaInferiore("-");
	                        } else {
	                            indicatore.setSogliaInferiore(String.valueOf(sogliaInferiore));
	                            indicatore.setSogliaSuperiore(String.valueOf(sogliaSuperiore));
	                        }
	                    } else {
	                        if (sogliaInferiore != null) {
	                            if (sogliaSuperiore != null && sogliaInferiore.equals(sogliaSuperiore)) {
	                                indicatore.setSogliaSuperiore("= ".concat(String.valueOf(sogliaSuperiore)));
	                            } else {
	                                indicatore.setSogliaInferiore(String.valueOf(sogliaInferiore));
	                            }
	                        } else {
	                            indicatore.setSogliaInferiore("");
	                        }
	                        if (sogliaSuperiore != null) {
	                            indicatore.setSogliaSuperiore(String.valueOf(sogliaSuperiore));
	                        } else {
	                            indicatore.setSogliaSuperiore("");
	                        }
	                    }

	                    if ("SN".equals(tipo)) {
	                        if (("1").equals((String) valore)) {
	                            indicatore.setValore("Si");
	                        } else if (("2").equals((String) valore)) {
	                            indicatore.setValore("No");
	                        } else {
	                            indicatore.setValore("N.D.");
	                        }
	                    } else if ("D".equals(tipo)) { // Double
	                        if (valore != null) {

	                            Double valoreDouble = null;
	                            if (valore instanceof Double) {
	                                valoreDouble = new Double((Double) valore);
	                            }
	                            if (valore instanceof Long) {
	                                valoreDouble = new Double((Long) valore);
	                            }

	                            indicatore.setValore(String.valueOf(valoreDouble));

	                            // Valorizzazione dell'asterisco
	                            if (sogliaInferiore != null && sogliaSuperiore != null) {
	                                if (valoreDouble != null && (sogliaInferiore.compareTo(valoreDouble) > 0 ||
	                                    (valoreDouble).compareTo(sogliaSuperiore) > 0)) {
	                                    indicatore.setAsterisco("*");
	                                }
	                            } else if (sogliaInferiore == null && sogliaSuperiore != null) {
	                                if (valoreDouble != null && (valoreDouble).compareTo(sogliaSuperiore) > 0) {
	                                    indicatore.setAsterisco("*");
	                                }
	                            } else if (sogliaInferiore != null && sogliaSuperiore == null) {
	                                if (valoreDouble != null && sogliaInferiore.compareTo(valoreDouble) > 0) {
	                                    indicatore.setAsterisco("*");
	                                }
	                            }

	                        } else {
	                            indicatore.setValore("N.D.");
	                        }

	                    } else if ("N".equals(tipo)) { // Intero
	                        if (valore != null) {

	                            Double valoreDouble = null;
	                            if (valore instanceof Double) {
	                                logger.warn("Attenzione: l'indicatore con descrizione '".concat(descrizione).concat(
	                                    "' ha un valore decimale, ma ci si aspetterebbe fosse intero (SQL=").concat(
	                                    strSql).concat(")"));
	                                valoreDouble = new Double(((Double) valore).longValue());
	                                indicatore.setValore(String.valueOf(valoreDouble));
	                            }
	                            if (valore instanceof Long) {
	                                valoreDouble = new Double((Long) valore);
	                                indicatore.setValore(valore.toString());
	                            }

	                            // Valorizzazione dell'asterisco
	                            if (sogliaInferiore != null && sogliaSuperiore != null) {
	                                if (valoreDouble != null && (sogliaInferiore.compareTo(valoreDouble) > 0 ||
	                                    valoreDouble.compareTo(sogliaSuperiore) > 0)) {
	                                    indicatore.setAsterisco("*");
	                                }
	                            } else if (sogliaInferiore == null && sogliaSuperiore != null) {
	                                if (valoreDouble != null && valoreDouble.compareTo(sogliaSuperiore) > 0) {
	                                    indicatore.setAsterisco("*");
	                                }
	                            } else if (sogliaInferiore != null && sogliaSuperiore == null) {
	                                if (valoreDouble != null && sogliaInferiore.compareTo(valoreDouble) > 0) {
	                                    indicatore.setAsterisco("*");
	                                }
	                            }
	                        } else {
	                            indicatore.setValore("N.D.");
	                        }
	                    }

	                } else {
	                    //indicatore.setSogliaSuperiore("(soglie non disponibili)");
	                    indicatore.setSogliaSuperiore("N.D.");
	                    indicatore.setSogliaInferiore("N.D.");
	                }

	                if (StringUtils.isNotEmpty(livello)) {
	                    indicatore.setLivello(livello);
	                }

	                if (StringUtils.isNotEmpty(affidabilitaStima)) {
	                    if ("4".equals(affidabilitaStima))
	                        indicatore.setAffidabilitaStima("*");
	                    else if ("3".equals(affidabilitaStima))
	                        indicatore.setAffidabilitaStima("**");
	                    else if ("2".equals(affidabilitaStima))
	                        indicatore.setAffidabilitaStima("***");
	                    else if ("1".equals(affidabilitaStima))
	                        indicatore.setAffidabilitaStima("****");
	                    else if ("0".equals(affidabilitaStima))
	                        indicatore.setAffidabilitaStima("*****");
	                    else if ("99".equals(affidabilitaStima))
	                        indicatore.setAffidabilitaStima("-");
	                    else
	                        indicatore.setAffidabilitaStima("");
	                }

	                if (StringUtils.isNotEmpty(numerositaStima)) {
	                    indicatore.setContrattiSimiliStima(numerositaStima);
	                }

	                listaIndicatoriBean.add(indicatore);
	            }
	        }
	    }

	    return listaIndicatoriBean;


	}

	public List < IndicatorePreliminareBean > calcolaIndicatoriPreliminare(String codiceCIG, String codiceUffint, boolean isAffidamento) throws SQLException {

	    String tipoDiContratto = this.contrattiMapper.getTipoByCig(codiceCIG);

	    String codiceIstatComune = null;

	    List < String > codiceIstatComuneList = this.contrattiMapper.getLuogoIstat(codiceCIG);
	    if (codiceIstatComuneList != null && codiceIstatComuneList.size() > 0) {
	        codiceIstatComune = codiceIstatComuneList.get(0);
	    }

	    if (StringUtils.isEmpty(codiceIstatComune)) {
	        codiceIstatComuneList = this.contrattiMapper.getCodCit(codiceUffint);
	        if (codiceIstatComuneList != null && codiceIstatComuneList.size() > 0) {
	            codiceIstatComune = codiceIstatComuneList.get(0);
	        }
	    }
	    List < IndicatorePremEntry > listaIndicatori = null;

	    if (isAffidamento) {
	        listaIndicatori = this.contrattiMapper.getListaIndicatoriPremFaseA(tipoDiContratto, codiceIstatComune);
	    } else {
	        listaIndicatori = this.contrattiMapper.getListaIndicatoriPremFaseE(tipoDiContratto, codiceIstatComune);
	    }
	    List < IndicatorePreliminareBean > listaIndicatoriBean = new ArrayList < IndicatorePreliminareBean > ();

	    if (listaIndicatori != null && listaIndicatori.size() > 0) {
	        for (IndicatorePremEntry ind: listaIndicatori) {


	            if (StringUtils.isNotEmpty(ind.getAffidabilitaStima())) {

	                String descrizione = ind.getDescrizione();
	                String unitaDiMisura = ind.getUnitaDiMisura();
	                String affidabilitaStima = ind.getAffidabilitaStima();
	                String numerositaStima = ind.getNumerositaStima();
	                Double percentile25 = ind.getPercentile25();
	                Double percentile50 = ind.getPercentile50();
	                Double percentile75 = ind.getPercentile75();
	                Double sogliaInferiore = ind.getSogliaInferiore();
	                Double sogliaSuperiore = ind.getSogliaSuperiore();
	                Double media = ind.getMedia();
	                Double stessoComune = ind.getStessoComune();
	                Double stessaProvincia = ind.getStessaProvincia();
	                Double stessaRegione = ind.getStessaRegione();

	                IndicatorePreliminareBean indicatorePreliminare = new IndicatorePreliminareBean();
	                indicatorePreliminare.setDescrizione(descrizione);

	                if (StringUtils.isNotEmpty(unitaDiMisura)) {
	                    indicatorePreliminare.setUnitaDiMisura(unitaDiMisura);
	                } else {
	                    indicatorePreliminare.setUnitaDiMisura("");
	                }

	                if (StringUtils.isNotEmpty(affidabilitaStima)) {
	                    if ("1".equals(affidabilitaStima))
	                        indicatorePreliminare.setAffidabilitaStima("*");
	                    else if ("2".equals(affidabilitaStima))
	                        indicatorePreliminare.setAffidabilitaStima("**");
	                    else if ("3".equals(affidabilitaStima))
	                        indicatorePreliminare.setAffidabilitaStima("***");
	                    else if ("4".equals(affidabilitaStima))
	                        indicatorePreliminare.setAffidabilitaStima("****");
	                    else if ("5".equals(affidabilitaStima))
	                        indicatorePreliminare.setAffidabilitaStima("*****");
	                    else if ("99".equals(affidabilitaStima))
	                        indicatorePreliminare.setAffidabilitaStima("-");
	                    else
	                        indicatorePreliminare.setAffidabilitaStima("");
	                }


	                if (StringUtils.isNotEmpty(numerositaStima)) {
	                    indicatorePreliminare.setContrattiSimiliStima(numerositaStima);
	                } else {
	                    indicatorePreliminare.setContrattiSimiliStima("");
	                }

	                if (sogliaInferiore != null) {
	                    indicatorePreliminare.setSogliaInferiore(String.valueOf(sogliaInferiore));
	                } else {
	                    indicatorePreliminare.setSogliaInferiore("");
	                }

	                if (percentile25 != null) {
	                    indicatorePreliminare.setPercentile25(String.valueOf(percentile25));
	                } else {
	                    indicatorePreliminare.setPercentile25("");
	                }

	                if (percentile50 != null) {
	                    indicatorePreliminare.setPercentile50(String.valueOf(percentile50));
	                } else {
	                    indicatorePreliminare.setPercentile50("");
	                }

	                if (percentile75 != null) {
	                    indicatorePreliminare.setPercentile75(String.valueOf(percentile75));
	                } else {
	                    indicatorePreliminare.setPercentile75("");
	                }

	                if (sogliaSuperiore != null) {
	                    indicatorePreliminare.setSogliaSuperiore(String.valueOf(sogliaSuperiore));
	                } else {
	                    indicatorePreliminare.setSogliaSuperiore("");
	                }

	                if (media != null) {
	                    indicatorePreliminare.setMedia(String.valueOf(media));
	                } else {
	                    indicatorePreliminare.setMedia("");
	                }

	                if (stessoComune != null) {
	                    indicatorePreliminare.setStessoComune(String.valueOf(stessoComune));
	                } else {
	                    indicatorePreliminare.setStessoComune("");
	                }
	                if (stessaProvincia != null) {
	                    indicatorePreliminare.setStessaProvincia(String.valueOf(stessaProvincia));
	                } else {
	                    indicatorePreliminare.setStessaProvincia("");
	                }
	                if (stessaRegione != null) {
	                    indicatorePreliminare.setStessaRegione(String.valueOf(stessaRegione));
	                } else {
	                    indicatorePreliminare.setStessaRegione("");
	                }

	                listaIndicatoriBean.add(indicatorePreliminare);
	            }
	        }
	    }
	    return listaIndicatoriBean;


	}

	public static String fillLeft(final String stringaQualsiasi, final char filler, final int dimensioneMax) {
	    String risultato = null;

	    if (stringaQualsiasi != null && dimensioneMax > 0) {
	        // creo una stringa a partire dalla stringa di input ed al pi di
	        // dimensioneMax caratteri
	        if (stringaQualsiasi.length() > dimensioneMax) {
	            risultato = stringaQualsiasi.substring(0, dimensioneMax);
	        } else {
	            risultato = stringaQualsiasi;
	            // ora si riempie il risultato a sinistra con un numero di caratteri
	            // tale
	            // da riempire la stringa fino a dimensioneMax
	            for (int i = risultato.length(); i < dimensioneMax; i++) {
	                risultato = filler + risultato;
	            }
	        }
	    }
	    return risultato;
	}

	public GeneralBaseResponse<Boolean> isAttivoIndicatoriLotto() {
		GeneralBaseResponse<Boolean> res = new GeneralBaseResponse<Boolean>();
		res.setEsito(true);
		try {
			boolean isAttivoIndicatoriLotto = this.contrattiMapper.checkExistsIndTable();
			res.setData(isAttivoIndicatoriLotto);
		}catch (Exception e) {
			res.setEsito(false);
			res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		    logger.error("Attenzione si è verificato un errore nel metodo isAttivoIndicatoriLotto");
		}
		return res;
	}

	public ResponseMatriceAtti matriceAtti(Long codGara) {
		
		ResponseMatriceAtti risultato = new ResponseMatriceAtti();
		risultato.setEsito(true);
		Map<String, List<StatoCig>> resultMatrice = new HashMap<String, List<StatoCig>>();
		try {
			List<CigIdAtto> mappaAtti = this.contrattiMapper.getCigTipoAtto(codGara);
			List<Map<String, String>> allcig = this.contrattiMapper.getCigLottiOggetto(codGara);
			for(CigIdAtto record: mappaAtti) {
				
				if(resultMatrice.containsKey(record.getNome())) {
					List<StatoCig> cigs = resultMatrice.get(record.getNome());
					StatoCig statoCig = new StatoCig();
					statoCig.setCig(record.getCig());
					statoCig.setPubblicato(record.getPubblicato());
					cigs.add(statoCig);
					resultMatrice.put(record.getNome(), cigs);
				} else {
					List<StatoCig> cigs = new ArrayList<StatoCig>();
					StatoCig statoCig = new StatoCig();
					statoCig.setCig(record.getCig());
					statoCig.setPubblicato(record.getPubblicato());
					cigs.add(statoCig);
					resultMatrice.put(record.getNome(), cigs);
				}
			}
			MatriceAtti data = new MatriceAtti();
			data.setAttiLotti(resultMatrice);
			data.setColumns(new ArrayList<String>(resultMatrice.keySet()));
			data.setRows(allcig);
			risultato.setData(data);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione della matrice degli atti di gara: codgara = " + codGara, t);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	
	public ResponseResult checkExistSimogEndpoint() {
		ResponseResult res = new ResponseResult();
		res.setEsito(true);
		try {
			String val = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, PROP_SIMOG_WS_URL);
			if(StringUtils.isNotBlank(val)) {
				res.setData("true");
			} else {
				res.setData("false");
			}
		}catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo checkExistSimogEndpoint",e);
			res.setEsito(false);
			res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return res;
	}
	
	public ResponseResult checkExistPcpEndpoint() {
		ResponseResult res = new ResponseResult();
		res.setEsito(true);
		try {
			String val = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, PROP_PCP_WS_URL);
			if(StringUtils.isNotBlank(val)) {
				res.setData("true");
			} else {
				res.setData("false");
			}
		}catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo checkExistSimogEndpoint",e);
			res.setEsito(false);
			res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return res;
	}

	public void impersonificaRupLogEventi(ImpersonificaRupForm form, Long syscon) {
		
		try {
			String logEventiMessage = null;
			logEventiMessage = "ADMIN - RP "+form.getCfImpersonato()+ " - "+form.getIdpImpersonato()+" - "+ form.getLoaImpersonato();
			Short livento = 1;
			insertLogEventi(syscon, null, "SUBNPA_UP", logEventiMessage, null, livento, null);
			
		}catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo impersonificaRupLogEventi",e);
			throw e;
		}
		
	}

	public Long getIdpUserLoa(Long syscon, String codein) {
		Long idps = 0L;
		try {
			idps = this.contrattiMapper.getIdpUserLoa(syscon, codein);
		}catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo getIdpUserLoa",e);			
		}
		return idps;
	}
	
	public String getMultiEnteConfig() {
		String isMultiEnte = null;
		try {
			isMultiEnte = this.contrattiMapper.getConfigurazione(CONFIG_CODICE_APP,"loginMultiEnte");
		}catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo getMultiEnteConfig",e);			
		}
		return isMultiEnte;
	}
	
	public String versionPcp() {
		String version = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        
			Date now = new Date();
			String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");	
			List<DateVersionPcpEntry> dateVersion = null;
			if("ORA".equals(dbms)) {
				dateVersion = this.contrattiMapper.getVersionsPcpOra();
			} else if("MSQ".equals(dbms)) {
				dateVersion = this.contrattiMapper.getVersionsPcpMsq();
			}else {
				dateVersion = this.contrattiMapper.getVersionsPcp();
			}
			
			if(dateVersion != null && dateVersion.size() > 0) {
				
				for (DateVersionPcpEntry dateVersionPcpEntry : dateVersion) {
					Date versioDate = sdf.parse(dateVersionPcpEntry.getDateVersion());
					if(versioDate.equals(now) || versioDate.before(now)) {
						version = dateVersionPcpEntry.getVersion();
					}
				}
			}
		}catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo versionPcp",e);	
		}
		return version;
	}
	
	public boolean isUUID(String cigIdAvGara) {
		if (cigIdAvGara == null) {
	        return false;
	    }
	    return UUID_REGEX_PATTERN.matcher(cigIdAvGara).matches();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void aggiornaRup(String xUserCodiceFiscale, String codein, Long codgara) throws Exception{
		try {			
			List<RupEntry> tecList = this.contrattiMapper.getTecnicoByCfAndSaList(xUserCodiceFiscale, codein);
			RupEntry tec = tecList != null && !tecList.isEmpty() ? tecList.get(0) : null;
			if(tec == null) {
				tec = new RupEntry();
				String codtec;			
				codtec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");									
				tec.setCodice(codtec);
				tec.setNome("-");
				tec.setCognome("-");
				tec.setNominativo("-");
				tec.setCf(xUserCodiceFiscale);
				tec.setStazioneAppaltante(codein);
				this.contrattiMapper.insertRUP(tec);
			}
			this.contrattiMapper.updateRupGara(codgara, tec.getCodice());
		} catch (Exception e) {
			logger.error("errore metodo: aggiornaRup",e);
			throw e;
		}	
	}
    
    public Jws<Claims> decryptJwt(String jwtToken) {
    	byte[] jwtKey;
		try {
			jwtKey = userManager.getJWTKey();	
			SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);        
        	return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la decifratura del token JWT", e);
        }
    }

    public ResponseDto getJsonListaSchede(String cig) {
		ResponseDto result = new ResponseDto();
		result.setEsito(true);
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			List<Long> codGaraList = this.contrattiMapper.getCodgaraByCig(cig);
			Long codGara = null;
			if(codGaraList != null && !codGaraList.isEmpty()){
				codGara = codGaraList.get(0);
			}
			if(codGara == null){
				logger.warn("getListaSchede::cig non esistente");
				result.setErrorData(BaseResponse.ERROR_NOT_FOUND);
				result.setEsito(false);
				return result;
			}
			GaraEntry g = this.contrattiMapper.dettaglioGara(codGara);

			String cfRup = this.contrattiMapper.getcfRup(g.getCodiceTecnico());
			result.setCfRup(cfRup);
			Long codLotto = this.contrattiMapper.getCodLottByCig(cig, codGara);

			List<Long> numAppaList = this.contrattiMapper.getlistaNumAppa(codGara, codLotto);
			List<AggiudicazioneAppaEntry> aggiudicazioni = new ArrayList<>();
			AggiudicazioneAppaEntry aggiudicazione;
			
			for (Long numAppa : numAppaList){
				aggiudicazione = new AggiudicazioneAppaEntry();
				List<FlussoJsonSchedaEntry> sottoscrContr = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 13L, numAppa);
				FlussoJsonSchedaEntry SC1 = sottoscrContr != null && !sottoscrContr.isEmpty()  ? sottoscrContr.get(0) : null;
				List<FlussoJsonSchedaEntry> inizio = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 2L, numAppa);
				FlussoJsonSchedaEntry I1 = inizio != null && !inizio.isEmpty()  ? inizio.get(0) : null;
				List<FlussoJsonSchedaEntry> SA1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 3L, numAppa);
				List<FlussoJsonSchedaEntry> conclusione = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 4L, numAppa);
				FlussoJsonSchedaEntry CO1 = conclusione != null && !conclusione.isEmpty()  ? conclusione.get(0) : null;
				List<FlussoJsonSchedaEntry> conclusione5k = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 19L, numAppa);
				FlussoJsonSchedaEntry CO2 = conclusione5k != null && !conclusione5k.isEmpty()  ? conclusione5k.get(0) : null;
				List<FlussoJsonSchedaEntry> collaudo = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 5L, numAppa);
				FlussoJsonSchedaEntry CL1 = collaudo != null && !collaudo.isEmpty()  ? collaudo.get(0) : null;
				List<FlussoJsonSchedaEntry> M = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 7L, numAppa);
				List<FlussoJsonSchedaEntry> AC1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 8L, numAppa);
				List<FlussoJsonSchedaEntry> SO1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 6L, numAppa);
				List<FlussoJsonSchedaEntry> SQ1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 14L, numAppa);
				List<FlussoJsonSchedaEntry> RI1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 15L, numAppa);
				List<FlussoJsonSchedaEntry> RSU1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 16L, numAppa);
				List<FlussoJsonSchedaEntry> ES1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 17L, numAppa);
				List<FlussoJsonSchedaEntry> CS1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 18L, numAppa);
				List<FlussoJsonSchedaEntry> IR1 = this.schedeMapper.getFlussoJsonScheda(codGara,codLotto, 10L, numAppa);

				aggiudicazione.setSC1(convertStringToJsonObject(SC1));
				aggiudicazione.setI1(convertStringToJsonObject(I1));
				aggiudicazione.setSA1(convertStringListToJsonObjectList(SA1));
				aggiudicazione.setCO1(convertStringToJsonObject(CO1));
				aggiudicazione.setCO2(convertStringToJsonObject(CO2));
				aggiudicazione.setCL1(convertStringToJsonObject(CL1));
				aggiudicazione.setM(convertStringListToJsonObjectList(M));
				aggiudicazione.setAC1(convertStringListToJsonObjectList(AC1));
				aggiudicazione.setSO1(convertStringListToJsonObjectList(SO1));
				aggiudicazione.setSQ1(convertStringListToJsonObjectList(SQ1));
				aggiudicazione.setRI1(convertStringListToJsonObjectList(RI1));
				aggiudicazione.setRSU1(convertStringListToJsonObjectList(RSU1));
				aggiudicazione.setES1(convertStringListToJsonObjectList(ES1));
				aggiudicazione.setCS1(convertStringListToJsonObjectList(CS1));
				aggiudicazione.setIR1(convertStringListToJsonObjectList(IR1));

				aggiudicazioni.add(aggiudicazione);
			}
			result.setData(aggiudicazioni);
		}catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: getListaSchede", e);
			result.setErrorData(e.getMessage());
			result.setEsito(false);
		}
		return result;
    }


	public static List<Object> convertStringListToJsonObjectList(List<FlussoJsonSchedaEntry> jsonStringList) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Object> jsonObjectList = new ArrayList<>();

		try {
			for (FlussoJsonSchedaEntry jsonString : jsonStringList) {
				if(jsonString != null){

					// Converti la stringa JSON in una mappa
					Map<String, Object> jsonObject = objectMapper.readValue(jsonString.getXml(), new TypeReference<Map<String, Object>>() {});

					// Aggiungi la proprietà isScheda all'oggetto
					jsonObject.put("idScheda", jsonString.getIdScheda());
					jsonObject.put("idDocumento", jsonString.getCodGara().toString() + jsonString.getCodLotto() + jsonString.getFaseEsecuzione().toString() + jsonString.getNum() );
					jsonObjectList.add(jsonObject);
				}
			}
		} catch (IOException e) {
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: convertStringListToJsonObjectList", e);
			throw  e;
		}

		return jsonObjectList;
	}

	public static Object convertStringToJsonObject(FlussoJsonSchedaEntry jsonString) throws JsonProcessingException {
		try {
			if(jsonString != null && jsonString.getXml() != null ){
				ObjectMapper objectMapper = new ObjectMapper();
				// Converti la stringa JSON in una mappa
				Map<String, Object> jsonObject = objectMapper.readValue(jsonString.getXml(), new TypeReference<Map<String, Object>>() {});

				// Aggiungi la proprietà isScheda all'oggetto
				jsonObject.put("idScheda", jsonString.getIdScheda());
				jsonObject.put("idDocumento", jsonString.getCodGara().toString() + jsonString.getCodLotto() + jsonString.getFaseEsecuzione().toString() + jsonString.getNum() );
				// Restituisci l'oggetto aggiornato (la mappa viene serializzata automaticamente)
				return jsonObject;
			}

		} catch (JsonProcessingException e) {
					logger.error("Si è verificato un errore durante l'esecuzione del metodo: convertStringToJsonObject", e);
			throw e;  // Rilancia l'eccezione per propagare l'errore
		}
		return null;
	}

	public ResponseSchedeTrasmessePcp searchSchedeTrasmessePcp(SchedeTrasmessePcpForm form, String authorization, String xLoginMode){
		ResponseSchedeTrasmessePcp response = new ResponseSchedeTrasmessePcp();
        response.setEsito(true);

        try {

			// Estrazione Cod Fiscale
			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
			String cf = userAuthClaimsDTO.getCf();
			Long syscon = userAuthClaimsDTO.getSyscon();
			boolean cfNull = false;

			String ruolo = this.contrattiMapper.getRuolo(form.getSyscon());
			if(form.getStazioneAppaltante() != null && form.getStazioneAppaltante().contains("*")) {
				form.setStazioneAppaltante(null);
			}
			form.setSyscon(syscon);
			form.setCfTecnico(StringUtils.isNotBlank(cf) ? cf.toUpperCase() : null);
			String cfTecnico = form.getCfTecnico();
			if ("A".equals(ruolo) || "C".equals(ruolo)) {
				form.setCfTecnico(null);
			} else{
				if(StringUtils.isBlank(cfTecnico)) {
					cfNull = true;
				}
			}
			form.setCfNull(cfNull);

			RowBounds rowBounds = new RowBounds(form.getSkip(), form.getTake());
			int totalCount = this.contrattiMapper.countSearchListaSchedeTrasmessePcp(form, rowBounds);
            List<RicercaSchedeTrasmessePcpEntry> schedeTrasmesse = this.contrattiMapper.getListaSchedeTrasmessePcp(form, rowBounds);

			response.setData(schedeTrasmesse);
			response.setTotalCount(totalCount);

        } catch (Exception e) {
            logger.error("Errore durante la ricerca delle schede trasmesse PCP", e);
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return response;
	}


	public ResponseDto getListaCig(String dataDa, String dataA) {
		ResponseDto result = new ResponseDto();
		result.setEsito(true);

		try{
			List<String> listaCig;
			if(dataDa != null && dataA != null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				listaCig = this.schedeMapper.getListaCigDataFilter(dateFormat.parse(dataDa), dateFormat.parse(dataA));
			} else{
				listaCig = this.schedeMapper.getListaCig();
			}
			result.setData(listaCig);
		}catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: getListaCig", e);
			result.setErrorData(e.getMessage());
			result.setEsito(false);
		}
		return result;
	}

	private APKServiceConfig getAPKConfig() {

		var res = new APKServiceConfig();

		String url = wConfigManager.getConfigurationValue("integrazione.apk.URL");
		String user = wConfigManager.getConfigurationValue("integrazione.apk.user");
		String pwd = wConfigManager.getConfigurationValue("integrazione.apk.password");

		res.setUrl(url);
		res.setUser(user);
		res.setPassword(pwd);

		return res;

	}

	private SicrawebServiceConfig getSicrawebConfig() {

		var res = new SicrawebServiceConfig();

		String url = wConfigManager.getConfigurationValue("integrazione.jserfin.URL");
		String alias = wConfigManager.getConfigurationValue("integrazione.jserfin.alias");
		String user = wConfigManager.getConfigurationValue("integrazione.jserfin.user");
		String pwd = wConfigManager.getConfigurationValue("integrazione.jserfin.password");

		res.setUrl(url);
		res.setAlias(alias);
		res.setUser(user);
		res.setPassword(pwd);

		return res;

	}

	private List<W9AssociazioneCampiEntry> getW9AssociazioneCampi(String codGara, String codLotto) {

		List<W9AssociazioneCampiEntry> res = this.contrattiMapper.getW9AssociazioneCampiByCodGaraAndCodLotto(Long.valueOf(codGara), Long.valueOf(codLotto));

		return res;
	}



	private String getNomeAppEsterno() {
		var integrazioneJSerfin = wConfigManager.getConfigurationValue("integrazione.jserfin");
		var integrazioneApk = wConfigManager.getConfigurationValue("integrazione.apk");

		return getNomeAppEsterno(integrazioneJSerfin, integrazioneApk);
	}

	private String getNomeAppEsterno(String integrazioneJSerfin, String integrazioneApk) {
		String appEsterno = null;
		if ("1".equals(integrazioneJSerfin)) {
			appEsterno = "sicra";
		} else if ("1".equals(integrazioneApk)) {
			appEsterno = "apk";
		}
		return appEsterno;
	}

	public ResponseDatiContabilita getDatiContabilita(String cig) {

		var res = new ResponseDatiContabilita();

		try {

			var lotto = this.contrattiMapper.getDettaglioLottoByCig(cig);

			var associazioni = getW9AssociazioneCampi(lotto.getCodgara(), lotto.getCodLotto());

			List<ImpegnoEntry> impegni = new ArrayList<ImpegnoEntry>();
			res.setImpegni(impegni);

			List<PagamentoEntry> pagamenti = new ArrayList<PagamentoEntry>();
			res.setPagamenti(pagamenti);

			var integrazioneJSerfin = wConfigManager.getConfigurationValue("integrazione.jserfin");
			var integrazioneApk = wConfigManager.getConfigurationValue("integrazione.apk");

			var appEsterno = getNomeAppEsterno(integrazioneJSerfin, integrazioneApk);

			if ("1".equals(integrazioneJSerfin)) {

				String modeCodImpegno = wConfigManager.getConfigurationValue("integrazione.jserfin.impegni.codiceunivoco");
				String modeCodPagamento = wConfigManager.getConfigurationValue("integrazione.jserfin.pagamenti.codiceunivoco");


				//TEST --> cig = "1234567891";

				var sicrawebImpegni = getSicrawebImpegni(cig);
				for (ListaBudget sicrawebImpegno : sicrawebImpegni) {

					String codImpegno = getCodiceImpegnoSicraweb(modeCodImpegno, sicrawebImpegno);

					var impegno = new ImpegnoEntry();
					impegno.setCodiceImpegno(codImpegno);
					impegno.setDataImpegno(sicrawebImpegno.getDataatto());
					impegno.setImporto(sicrawebImpegno.getImpegnato());
					impegno.setDescrizione(sicrawebImpegno.getDescrizione());
					impegno.setAtto(nullToBlank(sicrawebImpegno.getNumAtto()) + "/" + nullToBlank(sicrawebImpegno.getAnnoAtto()));
					impegno.setImpresa(sicrawebImpegno.getRagionesocialeanagrafica());

					impegni.add(impegno);
				}

				var sicrawebPagamenti = getSicrawebPagamenti(cig);

				int idx = 0;
				for (Mandato sicrawebPagamento : sicrawebPagamenti) {

					String codPagamento = getCodPagamentoSicraweb(modeCodPagamento, sicrawebPagamento);

					var pagamento = new PagamentoEntry();
					pagamento.setIdx(idx++);
					pagamento.setCodicePagamento(codPagamento);
					pagamento.setOggetto(sicrawebPagamento.getOggetto());
					pagamento.setDataPagamento(sicrawebPagamento.getDataquietanza());
					pagamento.setImpresa(sicrawebPagamento.getNominativo());
					pagamento.setImporto(sicrawebPagamento.getImportolordo());

					String utilizzato = getDatiContabilitaUtilizzato(associazioni, appEsterno, codPagamento);
					pagamento.setUtilizzato(utilizzato);

					pagamenti.add(pagamento);
				}

			} else if ("1".equals(integrazioneApk)) {

				String modeCodImpegno = wConfigManager.getConfigurationValue("integrazione.apk.impegni.codiceunivoco");
				String modeCodPagamento = wConfigManager.getConfigurationValue("integrazione.apk.pagamenti.codiceunivoco");


				//TEST CIGS: ZE526181B6, ZD03955FEF, Z5B38C076E
				//TEST --> cig = "Z5B38C076E";

				var apkImpegni = getAPKImpegni(cig);
				for (OutGetImpegni apkImpegno : apkImpegni) {

					String codImpegno = getCodiceImpegnoAPK(modeCodImpegno, apkImpegno);

					Date dataAtto = null;
					if (apkImpegno.getAttoData() != null) {
						dataAtto = apkImpegno.getAttoData().toGregorianCalendar().getTime();
					}

					var impegno = new ImpegnoEntry();
					impegno.setCodiceImpegno(codImpegno);
					if (apkImpegno.getAttoData() != null) {
						impegno.setDataImpegno(dataAtto);
					}
					impegno.setImporto(apkImpegno.getImportoImpegno());
					impegno.setDescrizione(apkImpegno.getDescrizione());
					impegno.setAtto(nullToBlank(apkImpegno.getAttoNumero()) + "/" + nullToBlank(getYear(dataAtto)));
					impegno.setImpresa(apkImpegno.getBeneficiarioNominativo());

					impegni.add(impegno);
				}

				var apkPagamenti = getAPKMandati(cig);
				for (OutGetMandati apkPagamento : apkPagamenti) {

					String codPagamento = getCodPagamentoAPK(modeCodPagamento, apkPagamento);

					var pagamento = new PagamentoEntry();
					pagamento.setCodicePagamento(codPagamento);
					pagamento.setOggetto(apkPagamento.getDescrizione());
					Date dataAtto = null;
					if (apkPagamento.getAttoData() != null) {
						dataAtto = apkPagamento.getAttoData().toGregorianCalendar().getTime();
					}
					pagamento.setDataPagamento(dataAtto);
					pagamento.setImpresa(apkPagamento.getBeneficiarioNominativo());
					pagamento.setImporto(apkPagamento.getImportoPagato());

					String utilizzato = getDatiContabilitaUtilizzato(associazioni, appEsterno, codPagamento);
					pagamento.setUtilizzato(utilizzato);

					pagamenti.add(pagamento);
				}
			}

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la get dei dati contabilita.", t);
			res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}

		return res;
	}



	private String getDatiContabilitaUtilizzato(List<W9AssociazioneCampiEntry> associazioni, String appEsterno, String idEsterno) {
	    return associazioni.stream()
	        .filter(e -> Objects.equals(e.getIdEsterno(), idEsterno))
	        .filter(e -> Objects.equals(e.getAppEsterno(), appEsterno))
	        .sorted(Comparator
	            .comparing(W9AssociazioneCampiEntry::getFaseDesc, Comparator.nullsLast(String::compareTo))
	            .thenComparing(W9AssociazioneCampiEntry::getNum, Comparator.nullsLast(Comparator.naturalOrder()))
	            .thenComparing(W9AssociazioneCampiEntry::getNomeCampoDesc, Comparator.nullsLast(String::compareTo)))
	        .map(e -> String.format("%s (N. %s): %s", e.getFaseDesc(), e.getNum(), e.getNomeCampoDesc()))
	        .collect(Collectors.joining("; "));
	}



	public List<ListaBudget> getSicrawebImpegni(String cig) {

		var svcConfig = getSicrawebConfig();
		String token = sicrawebRestClient.logon(svcConfig.getUrl(), svcConfig.getAlias(), svcConfig.getUser(), svcConfig.getPassword());
		RicercaBudgetImpegniResponse ricercaBudgetImpegniResp = sicrawebRestClient.ricercaBudgetImpegni(svcConfig.getUrl(), token, new Date(), cig);

		///RicercaBudgetImpegniResponse ricercaBudgetImpegniResp = testGetRicercaBudgetImpegniResponseFromJson("C:\\temp\\_dl229\\CUP_C19J21046090001.json");
//		RicercaBudgetImpegniResponse ricercaBudgetImpegniResp = testGetRicercaBudgetImpegniResponseFromJson("C:\\temp\\_sitat\\cig_" + cig + "_impegni.json");


		List<ListaBudget> sicrawebImpegni = ricercaBudgetImpegniResp.getListaBudget();
		if(sicrawebImpegni == null){
			sicrawebImpegni = new ArrayList<ListaBudget>();
		}

		return sicrawebImpegni;
	}


	public List<OutGetImpegni> getAPKImpegni(String cig) {


		//TEST CIGS: ZE526181B6, ZD03955FEF, Z5B38C076E
		//cig = "Z5B38C076E";

		var config = getAPKConfig();

		FinanziariaAPKService service = new FinanziariaAPKService();
		FinanziariaAPKServiceSoap port = service.getFinanziariaAPKServiceSoap();

		// Attach the custom header handler
		BindingProvider bindingProvider = (BindingProvider) port;
		List<Handler> handlerChain = new ArrayList<>();
		handlerChain.add(new AuthHeaderSOAPHandler(config.getUser(), config.getPassword()));
		((BindingProvider) port).getBinding().setHandlerChain(handlerChain);

		// Optionally override endpoint
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,config.getUrl());

		int esercizio = LocalDate.now().getYear();

		InGetImpegni req = new InGetImpegni();
		req.setEsercizio(esercizio);
		req.setCodiceCIG(cig);

		ArrayOfOutGetImpegni resp = port.getImpegni(req);
		List<OutGetImpegni> apkImpegni = resp.getOutGetImpegni();
		if(apkImpegni == null){
			apkImpegni = new ArrayList<OutGetImpegni>();
		}

		return apkImpegni;
	}

	public List<OutGetMandati> getAPKMandati(String cig) {

		//TEST CIGS: Z5B38C076E
		//cig = "Z5B38C076E";

		var config = getAPKConfig();

		FinanziariaAPKService service = new FinanziariaAPKService();
		FinanziariaAPKServiceSoap port = service.getFinanziariaAPKServiceSoap();

		// Attach the custom header handler
		BindingProvider bindingProvider = (BindingProvider) port;
		List<Handler> handlerChain = new ArrayList<>();
		handlerChain.add(new AuthHeaderSOAPHandler(config.getUser(), config.getPassword()));
		((BindingProvider) port).getBinding().setHandlerChain(handlerChain);

		// Optionally override endpoint
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,config.getUrl());

		int esercizio = LocalDate.now().getYear();

		InGetMandati req = new InGetMandati();
		req.setEsercizio(esercizio);
		req.setCodiceCIG(cig);

		ArrayOfOutGetMandati resp = port.getMandati(req);
		List<OutGetMandati> apkMandati = resp.getOutGetMandati();
		if(apkMandati == null){
			apkMandati = new ArrayList<OutGetMandati>();
		}

		return apkMandati;
	}

	public List<Mandato> getSicrawebPagamenti(String cig) {

		var svcConfig = getSicrawebConfig();
		String token = sicrawebRestClient.logon(svcConfig.getUrl(), svcConfig.getAlias(), svcConfig.getUser(), svcConfig.getPassword());
		RicercaMandatiResponse resp = sicrawebRestClient.ricercaMandati(svcConfig.getUrl(), token, cig);


//		RicercaMandatiResponse resp = testGetRicercaMandatiResponseFromJson("C:\\temp\\_sitat\\cig_" + cig + "_mandati.json");

		List<Mandato> sicrawebMandati = resp.getListaMandati();
		if(sicrawebMandati == null){
			sicrawebMandati = new ArrayList<Mandato>();
		}

		return sicrawebMandati;
	}

	private RicercaBudgetImpegniResponse testGetRicercaBudgetImpegniResponseFromJson(String filePath ) {
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
            RicercaBudgetImpegniResponse res = objectMapper.readValue(new File(filePath), RicercaBudgetImpegniResponse.class);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

	private RicercaMandatiResponse testGetRicercaMandatiResponseFromJson(String filePath ) {
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
        	RicercaMandatiResponse res = objectMapper.readValue(new File(filePath), RicercaMandatiResponse.class);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



	private String getCodiceImpegnoSicraweb(String mode, ListaBudget listaBudgetEntry) {

		if ("codimpegno".equals(mode)) {
			return nullToBlank(listaBudgetEntry.getCodimpegno());
		} else if ("codannuale_anno".equals(mode)) {
			return nullToBlank(listaBudgetEntry.getCodannuale()) + "/" + nullToBlank(listaBudgetEntry.getAnno());
		} else if ("anno_codannuale".equals(mode)) {
			return nullToBlank(listaBudgetEntry.getAnno()) + "/" + nullToBlank(listaBudgetEntry.getCodannuale());
		} else {
			throw new RuntimeException("Invalid mode for codice impegno:" + mode);
		}
	}

	private String getCodPagamentoSicraweb(String mode, Mandato mandato) {

		if ("nummandato_annodatamandato".equals(mode)) {
			var year = getYear(mandato.getDatamandato());
			String codPagamento = mandato.getNummandato() + "/" + (year == null ? "" : year);
			return codPagamento;
		} else if ("annodatamandato_nummandato".equals(mode)) {
			var year = getYear(mandato.getDatamandato());
			String codPagamento = (year == null ? "" : year) + "/" + mandato.getNummandato();
			return codPagamento;
		} else {
			throw new RuntimeException("Invalid mode for codice pagamento:" + mode);
		}
	}

	private String getCodiceImpegnoAPK(String mode, OutGetImpegni impegno) {

		if ("numero_anno".equals(mode)) {
			return nullToBlank(impegno.getNumero()) + "/" + nullToBlank(impegno.getAnno());
		} else if ("anno_numero".equals(mode)) {
			return nullToBlank(impegno.getAnno()) + "/" + nullToBlank(impegno.getNumero());
		} else {
			throw new RuntimeException("Invalid mode for codice impegno:" + mode);
		}
	}

	private String getCodPagamentoAPK(String mode, OutGetMandati mandato) {
		if ("numero_anno".equals(mode)) {
			Date dataAtto = null;
			if (mandato.getAttoData() != null) {
				dataAtto = mandato.getAttoData().toGregorianCalendar().getTime();
			}
			var year = getYear(dataAtto);

			String codPagamento = mandato.getNumero() + "/" + (year == null ? "" : year);
			return codPagamento;
		} else if ("anno_numero".equals(mode)) {
			Date dataAtto = null;
			if (mandato.getAttoData() != null) {
				dataAtto = mandato.getAttoData().toGregorianCalendar().getTime();
			}
			var year = getYear(dataAtto);

			String codPagamento = (year == null ? "" : year) + "/" + mandato.getNumero();
			return codPagamento;
		} else {
			throw new RuntimeException("Invalid mode for codice pagamento:" + mode);
		}
	}

//	private String getCodPagamentoAPK(String mode, OutGetpagamenti pagamento) {
//
//		if ("numero_anno".equals(mode)) {
//			return nullToBlank(impegno.getNumero()) + "/" + nullToBlank(impegno.getAnno());
//		} else if ("anno_numero".equals(mode)) {
//			return nullToBlank(impegno.getAnno()) + "/" + nullToBlank(impegno.getNumero());
//		} else {
//			throw new RuntimeException("Invalid mode for codice pagamento:" + mode);
//		}
//	}



	public static Integer getYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}


	private Double subtract(Double importo, Double sicrawebImporto) {
		return nullToZero(importo) - nullToZero(sicrawebImporto);
	}

	private String nullToBlank(String str) {
		return str == null ? "" : str;
	}

	private String nullToBlank(Integer num) {
		return num == null ? "" : num.toString();
	}

	private static double nullToZero(Double num) {
		return num != null ? num : 0.0;
	}

	private static Double zeroToNull(Double num) {
		if(num == null) {
			return num;
		}
		return Double.valueOf(0.0).equals(num) ? null : num;
	}

	public ResponseResult deleteAttoSingolo(Long codGara, Long numPubb){
		ResponseResult result = new ResponseResult();
		result.setEsito(true);

		try{

			this.contrattiMapper.deleteAttoSingolo(codGara, numPubb);

			result.setData(null);
		}catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: deleteAttoSingolo", e);
			result.setErrorData(e.getMessage());
			result.setEsito(false);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult programmaPubblicazioneAtto(Long codGara, Long numPubb, Date dataProgrammazione){
		ResponseResult result = new ResponseResult();
		result.setEsito(true);

		try{

			this.contrattiMapper.programmaPubblicazioneAtto(codGara, numPubb, dataProgrammazione);

			result.setData(null);
		}catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: deleteAttoSingolo", e);
			result.setErrorData(e.getMessage());
			result.setEsito(false);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult annullaPubblicazione(Long codGara, Long numPubb){
		ResponseResult result = new ResponseResult();
		result.setEsito(true);

		try{

			this.contrattiMapper.annullaPubblicazione(codGara, numPubb, null);

			result.setData(null);
		}catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: deleteAttoSingolo", e);
			result.setErrorData(e.getMessage());
			result.setEsito(false);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult annullaPubblicazioneMotivazione(String authorization, String xLoginMode, Long codGara, Long numPubb, String motivazione){
		ResponseResult result = new ResponseResult();
		result.setEsito(true);

		try{

			UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
			Long syscon = userAuthClaimsDTO.getSyscon();

			this.contrattiMapper.annullaPubblicazioneMotivazione(codGara, numPubb, motivazione, syscon, new Date());

			result.setData(null);
		}catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: deleteAttoSingolo", e);
			result.setErrorData(e.getMessage());
			result.setEsito(false);
		}
		return result;
	}

	public ResponseDto getIdSchedaLocale(Long codGara, Long codLotto, Long num, Long numFase) {
		ResponseDto result = new ResponseDto();
		result.setEsito(true);

		try {

			String idSchedaLocale = this.contrattiMapper.getIdSchedaLocale(codGara, codLotto, num, numFase);
			result.setData(StringUtils.isEmpty(idSchedaLocale) ? "N" : idSchedaLocale);
		} catch (Exception e){
			logger.error("Si è verificato un errore durante l'esecuzione del metodo: getIdSchedaLocale con codgara: {}, codlotto: {}, num: {}, numfase: {}", codGara, codLotto, num, numFase, e);
			result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			result.setEsito(false);
		}
		return result;
	}
}
