package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AlertRupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.MessageInForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.DelegheMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.SchedeMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.monitoraggiocontratti.sicraweb.SicrawebRestClient;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.AnacRestClientManager;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


@Component(value = "AlertManager")
@Transactional(propagation = Propagation.SUPPORTS)
@SuppressWarnings("java:S2229")
public class AlertManager extends AbstractManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(AlertManager.class);
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

	public void alertRup(){
		logger.info("scheduler alert rup");

		//Giorni dalla data di aggiudicazione per lotti senza la sottoscrizione del contratto AGG-INI
		List<AlertRupEntry> aggIniList = this.contrattiMapper.getAlertRupAggIni();
		if(aggIniList != null){
			for (AlertRupEntry l : aggIniList){
				insertAlertRup(l.getSyscon(), l.getIdAppalto(), l.getCig(), "AGG-INI");
			}
		}
		//Giorni dalla data di stipula del contratto per lotti senza l’inizio esecuzione contratto STI-INI
		List<AlertRupEntry> stiIniList = this.contrattiMapper.getAlertRupStiIni();
		if(stiIniList != null){
			for (AlertRupEntry l : stiIniList){
				insertAlertRup(l.getSyscon(), l.getIdAppalto(), l.getCig(), "STI-INI");
			}
		}
		//Giorni dalla data di inizio per lotti senza avanzamenti o conclusione INI-SAL
		List<AlertRupEntry> IniSalList = this.contrattiMapper.getAlertRupIniSal();
		if(IniSalList != null){
			for (AlertRupEntry l : IniSalList){
				insertAlertRup(l.getSyscon(), l.getIdAppalto(), l.getCig(), "INI-SAL");
			}
		}

		//Giorni dalla data del SAL per lotti senza avanzamenti successivi o conclusione SAL-CONC
		List<AlertRupEntry> salConcList = this.contrattiMapper.getAlertRupSalConc();
		if(salConcList != null){
			for (AlertRupEntry l : salConcList){
				insertAlertRup(l.getSyscon(), l.getIdAppalto(), l.getCig(), "SAL-CONC");
			}
		}
		//Giorni dalla data di conclusione per lotti senza il collaudo CONC-COLL
		List<AlertRupEntry> concCollList = this.contrattiMapper.getAlertRupConcColl();
		if(concCollList != null){
			for (AlertRupEntry l : concCollList){
				insertAlertRup(l.getSyscon(), l.getIdAppalto(), l.getCig(), "CONC-COLL");
			}
		}


	}

	private void insertAlertRup(Long syscon, String idAppalto, String cig, String dodiceAlert){

		MessageInForm inForm = new MessageInForm();

		Long maxInId = this.contrattiMapper.getMaxMessageInId();

		if (maxInId == null) {
			maxInId = 1L;
		} else {
			maxInId = maxInId + 1;
		}

		Long idIn = maxInId;
		inForm.setId(idIn);
		inForm.setCorpoMessaggio(
				""
		);
		inForm.setDataMessaggio(new Date());
		inForm.setOggetto("");
		inForm.setSysconReceiver(syscon);
		inForm.setSysconSender(48L);
		this.contrattiMapper.insertMessageIn(inForm);
	}
}
