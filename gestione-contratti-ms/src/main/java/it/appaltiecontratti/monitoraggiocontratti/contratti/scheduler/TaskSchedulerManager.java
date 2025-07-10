package it.appaltiecontratti.monitoraggiocontratti.contratti.scheduler;

import it.appaltiecontratti.monitoraggiocontratti.simog.bl.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskSchedulerManager {

	private static final Logger LOG = LogManager.getLogger(TaskSchedulerManager.class);

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	@Autowired
	private ConfigurationManager configurationManager;

	/*
	 * Import Scheduler
	 */
	@Autowired
	private AlertRupScheduler alertRupscheduler;

	@PostConstruct
	public void scheduleRunnableWithCronTrigger() {
		CronTrigger cronTrigger = getAlertRupCron();
		if (cronTrigger != null) {
			taskScheduler.schedule(alertRupscheduler, cronTrigger);
		} else {
			LOG.warn("Attenzione, il record alertRupTrigger nella tabella w_quartz non esiste");
		}
	}

	/*
	 * Definizione Cron
	 */
	private CronTrigger getAlertRupCron() {
		LOG.debug("Loading cron::getAlertRupCron");
		String elencoFeedbackCron = this.configurationManager
				.getQuartzExpression("alertRupTrigger");
		LOG.debug("Loaded cron::getAlertRupCron {}", elencoFeedbackCron);
		String dbCron = parseQuartzToCron(elencoFeedbackCron);
		if (dbCron == null)
			return null;
		return new CronTrigger(dbCron);
	}

	private String parseQuartzToCron(final String quartz) {
		if (StringUtils.isBlank(quartz))
			return null;

		List<String> listaComponenti = new ArrayList<>(Arrays.asList(quartz.trim().split(" ")));
		// controllo che siano presenti 7 elementi (l'ultimo e' l'anno che non e'
		// presente nel cron)
		if (listaComponenti.size() == 7) {
			listaComponenti.remove(listaComponenti.size() - 1);
		}
		return String.join(" ", listaComponenti);
	}
}