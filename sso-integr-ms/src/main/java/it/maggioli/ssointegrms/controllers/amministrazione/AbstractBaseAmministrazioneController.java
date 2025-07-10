package it.maggioli.ssointegrms.controllers.amministrazione;

import it.maggioli.ssointegrms.controllers.AbstractBaseController;
import it.maggioli.ssointegrms.dto.UserDTO;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.UserNotAdministratorException;
import it.maggioli.ssointegrms.services.general.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

/**
 * @author Cristiano Perin
 */
public abstract class AbstractBaseAmministrazioneController extends AbstractBaseController {

    @Autowired
    protected UserService userService;

    public void validateAdminUser(final Authentication authentication) {
        UserDTO user = (UserDTO) authentication.getPrincipal();
        boolean isCurrentUserAdministrator = userService.isUserDTOAdministrator(user);
        if (!isCurrentUserAdministrator)
            throw new UserNotAdministratorException();
    }
}
