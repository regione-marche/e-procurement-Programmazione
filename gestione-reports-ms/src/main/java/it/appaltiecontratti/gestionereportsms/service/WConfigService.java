package it.appaltiecontratti.gestionereportsms.service;

import it.appaltiecontratti.gestionereportsms.dto.WConfigDTO;

/**
 * @author Cristiano Perin
 */
public interface WConfigService {
	
	WConfigDTO getStandardConfiguration(final String chiave);

    WConfigDTO getConfiguration(final String chiave);
}

