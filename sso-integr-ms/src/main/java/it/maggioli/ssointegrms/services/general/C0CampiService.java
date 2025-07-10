package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaC0CampiFormDTO;

/**
 * @author Cristiano Perin
 */
public interface C0CampiService {

	ResponseListaDTO loadListaC0Campi(final RicercaC0CampiFormDTO form);
}
