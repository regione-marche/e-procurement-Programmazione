package it.appaltiecontratti.inbox.scheduler;

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

import it.appaltiecontratti.inbox.bl.ConfigurationManager;

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
	private ComScpScheduler comScpScheduler;

	@PostConstruct
	public void scheduleRunnableWithCronTrigger() {
		CronTrigger cronTrigger = getComScpCron();
		if (cronTrigger != null) {
			taskScheduler.schedule(comScpScheduler, cronTrigger);
		} else {
			LOG.warn("Attenzione, il record inviaORtoSCPSchedulerTrigger nella tabella w_quartz non esiste");
		}
	}

	/*
	 * Definizione Cron
	 */
	private CronTrigger getComScpCron() {
		LOG.debug("Loading cron::getComScpCron");
		String comScpCron = this.configurationManager.getQuartzExpression("inviaORtoSCPSchedulerTrigger");
		LOG.debug("Loaded cron::getComScpCron {}", comScpCron);
		String dbCron = parseQuartzToCron(comScpCron);
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