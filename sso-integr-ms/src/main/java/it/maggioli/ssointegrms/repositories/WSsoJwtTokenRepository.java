package it.maggioli.ssointegrms.repositories;

import it.maggioli.ssointegrms.domain.WSsoJwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 22, 2025
 */
@Repository
public interface WSsoJwtTokenRepository extends JpaRepository<WSsoJwtToken, String> {

}
