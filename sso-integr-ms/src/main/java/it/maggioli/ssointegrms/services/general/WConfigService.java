package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaConfigurazioniFormDTO;
import it.maggioli.ssointegrms.dto.RicercaConfigurazioniInizDTO;
import it.maggioli.ssointegrms.dto.WConfigDTO;
import it.maggioli.ssointegrms.dto.WConfigEditDTO;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface WConfigService {

	WConfigDTO getStandardConfiguration(final String chiave);

	WConfigDTO getConfiguration(final String chiave);

	String getConfigurationValue(final String chiave);
	
	String getStandardConfigurationValue(final String chiave);

	RicercaConfigurazioniInizDTO getInizRicercaConfigurazioni();

	ResponseListaDTO loadListaConfigurazioni(final RicercaConfigurazioniFormDTO searchForm);

	WConfigDTO getDettaglioConfiguration(final String codApp, final String chiave);
	
	WConfigDTO updateConfiguration(final WConfigEditDTO form) throws CriptazioneException;

}
