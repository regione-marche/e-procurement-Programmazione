package it.maggioli.ssointegrms.repositories;

import it.maggioli.ssointegrms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristiano Perin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginIgnoreCase(final String username);

    User findByLoginIgnoreCaseAndPassword(final String username, final String password);

    List<User> findByCodiceFiscaleIgnoreCase(final String codiceFiscale);

    @Query("SELECT MAX(u.syscon) FROM User u")
    Optional<Long> getMaxSyscon();

    @Query("SELECT u.syscon FROM User u WHERE u.syspwbou LIKE '%ou89|%' OR u.syspwbou LIKE '%ou11|%'")
    List<Long> getAccountGestoriProfilo();

    @Modifying
    @Query(value = "UPDATE User u SET u.dataUltimoAccesso = :dataUltimoAccesso WHERE u.syscon = :syscon")
    void updateDataUltimoAccesso(@Param(value = "syscon") final Long syscon, @Param(value = "dataUltimoAccesso") final Date dataUltimoAccesso);

    @Modifying
    @Query(value = "UPDATE User u SET u.password = :password WHERE u.syscon = :syscon")
    void updatePassword(@Param(value = "syscon") final Long syscon, @Param(value = "password") final String password);
}
