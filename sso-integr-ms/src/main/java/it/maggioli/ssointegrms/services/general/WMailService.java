package it.maggioli.ssointegrms.services.general;

import java.util.List;

import it.maggioli.ssointegrms.dto.ResponseDTO;
import it.maggioli.ssointegrms.dto.WMailDTO;
import it.maggioli.ssointegrms.dto.WMailEditDTO;
import it.maggioli.ssointegrms.dto.WMailEditPasswordDTO;
import it.maggioli.ssointegrms.dto.WMailInizDTO;
import it.maggioli.ssointegrms.dto.WMailTestSendDTO;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface WMailService {

	List<WMailDTO> loadListaWMail();

	WMailDTO getWMail(final String idCfg);

	WMailInizDTO getInizNewWMail();

	WMailDTO insertWMail(final WMailEditDTO form);

	WMailDTO updateWMail(final WMailEditDTO form);

	boolean deleteWMail(final String idCfg);

	ResponseDTO updateWMailPassword(final WMailEditPasswordDTO form) throws CriptazioneException;

	boolean testSendEmail(final WMailTestSendDTO form);
}
