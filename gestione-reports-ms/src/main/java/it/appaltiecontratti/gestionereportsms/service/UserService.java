package it.appaltiecontratti.gestionereportsms.service;

import it.appaltiecontratti.gestionereportsms.dto.UserDTO;
import it.appaltiecontratti.gestionereportsms.exceptions.TokenExpiredException;
import it.appaltiecontratti.gestionereportsms.exceptions.TokenInvalidException;

/**
 * @author Cristiano Perin
 */
public interface UserService {

 
    UserDTO executeCheckToken(final String token, final String loginMode)
            throws TokenExpiredException, TokenInvalidException;

}
