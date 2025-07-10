package it.appaltiecontratti.monitoraggiocontratti.contratti.scheduler;

import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.AlertManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.SimogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static it.appaltiecontratti.monitoraggiocontratti.contratti.bl.ContrattiManager.APPLICATION_CODE;

@Component
public class AlertRupScheduler implements Runnable {
	
	private static Logger logger = LogManager.getLogger(AlertRupScheduler.class);


	@Autowired
	private AlertManager alertManager;

	@Autowired
	private ContrattiMapper contrattiMapper;

	@Override
	public void run() {
    	try {
			String dbms = this.contrattiMapper.getConfigurazione(APPLICATION_CODE, "it.eldasoft.dbms");
			if("POS".equals(dbms)) {
				boolean existTable = this.contrattiMapper.checkExistsTable("w_alert_schede");
				if(existTable) {
					this.alertManager.alertRup();
				} else{
					logger.warn("AlertRupScheduler::tabella w_alert_schede non esistente");
				}
			} else{
				logger.warn("AlertRupScheduler::dbms diverso da postgres");
			}

		} catch (Exception e) {
			logger.error("Errore in fase di esecuzione processo di invio di alert al rup", e);
		}
    }
    
}