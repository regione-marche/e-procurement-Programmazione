package it.maggioli.ssointegrms.services.general;

import java.util.List;

import it.maggioli.ssointegrms.domain.Uffint;
import it.maggioli.ssointegrms.dto.UfficioIntestatarioDTO;
import it.maggioli.ssointegrms.dto.UserDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface UffintService {

	List<UfficioIntestatarioDTO> getListaOpzioniUfficioIntestatario(final String searchData, final UserDTO currentUserDTO);

	UfficioIntestatarioDTO getUfficioIntestatario(final String codice);

	Uffint getUffintInternal(final String codice);

	List<UfficioIntestatarioDTO> getListaUfficiIntestatari(final UserDTO currentUserDTO);
}
