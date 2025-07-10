package it.maggioli.ssointegrms.services.general;

import java.util.List;

import it.maggioli.ssointegrms.dto.WQuartzDTO;
import it.maggioli.ssointegrms.dto.WQuartzEditDTO;

/**
 * @author Cristiano Perin
 */
public interface WQuartzService {

	List<WQuartzDTO> listaPianificazioni();
	
	WQuartzDTO getPianificazione(final String codApp, final String beanId);
	
	WQuartzDTO updatePianificazione(final WQuartzEditDTO form);
}
