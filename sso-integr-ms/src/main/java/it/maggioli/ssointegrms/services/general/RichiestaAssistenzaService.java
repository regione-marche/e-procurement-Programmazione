package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.RichiestaAssistenzaFormDTO;
import it.maggioli.ssointegrms.dto.RichiestaAssistenzaInitDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface RichiestaAssistenzaService {
	
	boolean isRichiestaAssistenzaAttiva();

	RichiestaAssistenzaInitDTO getInitRichiestaAssistenza();

	boolean postRichiestaAssistenza(final RichiestaAssistenzaFormDTO form);
}
