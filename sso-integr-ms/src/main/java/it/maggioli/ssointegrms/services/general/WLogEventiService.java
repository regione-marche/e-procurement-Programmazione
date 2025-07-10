package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.*;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface WLogEventiService {

	WLogEventiDTO getLastLogin(final Long syscon);

	void insertLogEvent(final WLogEventiDTO dto);

	RicercaEventiInizDTO getInizRicercaEventi();

	ResponseListaDTO loadListaEventi(final RicercaEventiFormDTO form);

	WLogEventiDTO getEvento(final Long idEvento, final Long currentUserSyscon, final String ipAddress);

	ResponseDTO createLogoutEvent(final Long syscon, final String ipAddress);

	ResponseListaDTO loadListaUltimiAccessi(Long syscon);
}
