package it.maggioli.ssointegrms.services.general;

import java.util.List;

import it.maggioli.ssointegrms.domain.Profilo;
import it.maggioli.ssointegrms.dto.ProfiloDTO;
import it.maggioli.ssointegrms.dto.UserDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface ProfiloService {

	List<ProfiloDTO> getListaOpzioniProfilo(final String searchData);

	ProfiloDTO getProfilo(final String codice);

	Profilo getProfiloInternal(final String codice);
	
	List<ProfiloDTO> getListaProfili(final UserDTO currentUserDTO);

}
