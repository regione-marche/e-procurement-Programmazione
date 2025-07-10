package it.appaltiecontratti.monitoraggiocontratti.simog.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.monitoraggiocontratti.simog.bl.SimogManager;

@Component
public class ElencoFeedbackScheduler  implements Runnable {
	
	private static Logger logger = LogManager.getLogger(ElencoFeedbackScheduler.class);


	@Autowired
	private SimogManager simogManager;

	@Override
	public void run() {
    	try {
			this.simogManager.allineamentoRichiesteEliminazione();
		} catch (Exception e) {
			logger.error("Errore in fase di esecuzione processo allineamento richieste eliminazione", e);
		}
    }
    
}