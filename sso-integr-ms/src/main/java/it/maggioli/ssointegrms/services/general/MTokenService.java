package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.AuthenticationDTO;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public interface MTokenService {

    boolean loginMToken(final AuthenticationDTO authenticationDTO);
}
