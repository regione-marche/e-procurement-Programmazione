package it.appaltiecontratti.inbox.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.inbox.bl.InvioSCPManager;

@Component
public class ComScpScheduler  implements Runnable {
	
	private static Logger logger = LogManager.getLogger(ComScpScheduler.class);


	@Autowired
	private InvioSCPManager invioScpManager;

	@Override
	public void run() {
    	try {
			this.invioScpManager.exportORtoSCP();
		} catch (Exception e) {
			logger.error("Errore in fase di esecuzione processo allineamento richieste eliminazione", e);
		}
    }
    
}