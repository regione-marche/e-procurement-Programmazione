package it.appaltiecontratti.monitoraggiocontratti.simog.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.monitoraggiocontratti.simog.bl.ConfigurationManager;

@Component
public class ThreadPoolTaskSchedulerManager {

	private static final Logger LOG = LogManager.getLogger(ThreadPoolTaskSchedulerManager.class);

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	@Autowired
	private ConfigurationManager configurationManager;

	/*
	 * Import Scheduler
	 */
	@Autowired
	private ElencoFeedbackScheduler elencoFeedbackscheduler;

	@PostConstruct
	public void scheduleRunnableWithCronTrigger() {
		CronTrigger cronTrigger = getElencoFeedbackCron();
		if (cronTrigger != null) {
			taskScheduler.schedule(elencoFeedbackscheduler, cronTrigger);
		} else {
			LOG.warn("Attenzione, il record allineamentoRichiesteCancellazioneTrigger nella tabella w_quartz non esiste");
		}
	}

	/*
	 * Definizione Cron
	 */
	private CronTrigger getElencoFeedbackCron() {
		LOG.debug("Loading cron::getElencoFeedbackCron");
		String elencoFeedbackCron = this.configurationManager
				.getQuartzExpression("allineamentoRichiesteCancellazioneTrigger");
		LOG.debug("Loaded cron::getElencoFeedbackCron {}", elencoFeedbackCron);
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