package it.appaltiecontratti.monitoraggiocontratti.simog.bl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.monitoraggiocontratti.simog.mapper.SimogMapper;

@Component(value = "configurationManager")
public class ConfigurationManager {

	private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);

	@Value("${application.codiceProdotto}")
	private String CONFIG_CODICE_APP;

	@Autowired
	private SimogMapper simogMapper;

	public String getQuartzExpression(final String beanId) {
		LOG.debug("Execution start::getQuartzExpression");

		String quartz = null;

		quartz = this.simogMapper.getCronExpression(CONFIG_CODICE_APP, beanId);

		LOG.debug("Execution end::getQuartzExpression");

		return quartz;
	}
}
