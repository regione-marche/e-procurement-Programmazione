package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.TokenExpiredException;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.TokenInvalidException;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;

import java.util.List;

/**
 * @author Cristiano Perin
 */
public interface UserService {

    LoginSuccessDTO executeAuthentication(final AuthenticationDTO authenticationDTO) throws CriptazioneException;

    ResponseDTO executeChangePassword(final ChangePasswordDTO changePasswordDTO) throws CriptazioneException;

    ResponseDTO executeUserChangePassword(final UserDTO currentUser, final ChangePasswordUserDTO changePasswordUserDTO) throws CriptazioneException;

    UserDTO executeCheckToken(final String token, final String loginMode)
            throws TokenExpiredException, TokenInvalidException;

    InitRicercaUtentiDTO initRicercaUtenti(final UserDTO currentUserDTO);

    ResponseListaDTO loadListaUtenti(final RicercaUtentiFormDTO form, final UserDTO currentUserDTO);

    ResponseDTO insertUtente(final UserDTO currentUserDTO, final UserInsertDTO form, final String ipAddress)
            throws CriptazioneException;

    UserDTO getUtente(final Long syscon, final UserDTO currentUserDTO);

    ResponseDTO updateUtente(final UserDTO currentUserDTO, final Long syscon, final UserEditDTO form,
                             final String ipAddress) throws CriptazioneException;

    boolean abilitaDisabilitaUtente(final UserDTO currentUserDTO, final Long syscon, final String operazione, final String ipAddress);

    boolean deleteUtente(final UserDTO currentUserDTO, final Long syscon, final String ipAddress);

    List<ProfiloDTO> getProfiliUtente(final UserDTO currentUser, final Long syscon);

    boolean setProfiliUtente(final UserDTO currentUserDTO, final Long syscon, final List<String> listaProfili,
                             final String ipAddress);

    List<UfficioIntestatarioDTO> getUfficiIntestatariUtente(final UserDTO currentUser, final Long syscon);

    boolean setUfficiIntestatariUtente(final UserDTO currentUserDTO, final Long syscon,
                                       final List<String> listaStazioniAppaltanti);

    ResponseDTO changePasswordAdminUtente(final UserDTO currentUserDTO, final Long syscon,
                                          final UserChangePasswordAdminDTO form, final String ipAddress) throws CriptazioneException;

    boolean isUserDTOAdministrator(final UserDTO userDTO);

    ResponseDTO requestPasswordRecovery(final PasswordRecoveryRequestDTO form) throws Exception;

    boolean checkPasswordRecoveryToken(final String token, final String ipAddress);

    ResponseDTO executePasswordRecovery(final PasswordRecoveryExecutionDTO form) throws CriptazioneException;

    UserConnectedDTO getUtenteConnesso(final UserDTO currentUser);

    ResponseDTO updateUtenteConnesso(final UserDTO currentUser, final UserConnectedEditDTO form);

    ResponseDTO loadCsvUtenti(final RicercaUtentiFormDTO form, final UserDTO currentUserDTO);

    boolean executeCheckMToken(final CheckMTokenDTO checkMTokenDTO);
}
