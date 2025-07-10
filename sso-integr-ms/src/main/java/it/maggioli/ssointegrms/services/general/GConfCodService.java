package it.maggioli.ssointegrms.services.general;

import java.util.List;

import it.maggioli.ssointegrms.dto.GConfCodDTO;
import it.maggioli.ssointegrms.dto.GConfCodEditDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface GConfCodService {

	List<GConfCodDTO> loadListaCodificaAutomatica(final String codApp);

	GConfCodDTO getDettaglio(final String nomEnt, final String codCam, final Long tipCam);
	
	GConfCodDTO updateCodificaAutomatica(final GConfCodEditDTO form);
}
