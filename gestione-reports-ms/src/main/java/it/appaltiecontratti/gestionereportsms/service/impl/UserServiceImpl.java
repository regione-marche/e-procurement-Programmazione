package it.appaltiecontratti.gestionereportsms.service.impl;

import java.security.PublicKey;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import it.appaltiecontratti.gestionereportsms.domain.WSsoJwtToken;
import it.appaltiecontratti.gestionereportsms.repositories.WSsoJwtTokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.Uffint;
import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.dto.UserDTO;
import it.appaltiecontratti.gestionereportsms.dto.WConfigDTO;
import it.appaltiecontratti.gestionereportsms.exceptions.TokenExpiredException;
import it.appaltiecontratti.gestionereportsms.exceptions.TokenInvalidException;
import it.appaltiecontratti.gestionereportsms.service.BaseService;
import it.appaltiecontratti.gestionereportsms.service.UserService;
import it.appaltiecontratti.gestionereportsms.service.WConfigService;
import it.appaltiecontratti.gestionereportsms.utils.OAuth2RestComponent;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;

/**
 * @author Cristiano Perin
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private OAuth2RestComponent oAuth2RestComponent;
    
    @Autowired
    private WConfigService wConfigService;

    @Autowired
    private WSsoJwtTokenRepository wSsoJwtTokenRepository;
 
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO executeCheckToken(final String token, final String loginMode)
            throws TokenExpiredException, TokenInvalidException {

        LOGGER.debug("Execution start UserServiceImpl::executeCheckToken for token [ {} ]", token);

        if (StringUtils.isBlank(token))
            throw new IllegalArgumentException("token null");

        DecodedJWT jwt = null;
        try {
            byte[] jwtKey = getJWTKey();
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);

            if (!AppConstants.LOGIN_MODE_TOSCANA.equals(loginMode)) {
                Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            } else {
                // TODO da rimuovere prima di subito
                DecodedJWT tempClaims = JWT.decode(token);
                Claim internalClaim = tempClaims.getClaim("internal");
                boolean internal = internalClaim != null && internalClaim.asBoolean() != null ? internalClaim.asBoolean() : false;
                if (internal) {
                    // Verifica interna (richiesta da Toscana per il primo periodo di produzione)
                    Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
                } else {
                    // Verifica Oauth2
                    PublicKey publicKey = oAuth2RestComponent.retrievePublicKey();
                    Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token);
                }
            }

            jwt = JWT.decode(token);
            LOGGER.debug("Token decoded");
            if (jwt.getExpiresAt().getTime() < System.currentTimeMillis())
                throw new TokenExpiredException("Token expired");
        } catch (CriptazioneException e) {
            LOGGER.error("JWT Key error: {}", e.getMessage());
            throw new TokenInvalidException("JWT Key error");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token expired: {}", e.getMessage());
            throw new TokenExpiredException("Token expired");
        } catch (JwtException e) {
            LOGGER.error("Invalid token: {}", e.getMessage());
            throw new TokenInvalidException("Token Invalid");
        }

        LOGGER.debug("isExpired: {}", (jwt.getExpiresAt().getTime() < System.currentTimeMillis()));
        return getUserFromDecodedJWT(token, jwt, loginMode);
    }

    
    private byte[] getJWTKey() throws CriptazioneException {
        byte[] jwtKey = null;
        WConfigDTO configurazione = wConfigService
                .getConfiguration(AppConstants.W_CONFIG_JWT_KEY_CHIAVE);

        if (configurazione != null) {
            if (StringUtils.isBlank(configurazione.getCriptato())
                    || (StringUtils.isNotBlank(configurazione.getCriptato())
                    && "0".equals(configurazione.getCriptato()))) {
                jwtKey = StringUtils.isNotBlank(configurazione.getValore()) ? configurazione.getValore().getBytes()
                        : null;
            } else if ("1".equals(configurazione.getCriptato())) {
                try {
                    ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                            FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, configurazione.getValore().getBytes(),
                            ICriptazioneByte.FORMATO_DATO_CIFRATO);

                    jwtKey = decrypt.getDatoNonCifrato();
                } catch (CriptazioneException e) {
                    LOGGER.error("Errore in fase di decrypt della chiave hash per generazione token", e);
                    throw e;
                }
            }
        }

        return jwtKey;
    }
    
    private UserDTO getUserFromDecodedJWT(final String jwtToken, final DecodedJWT jwt, final String loginMode) throws TokenInvalidException {
        Claim userlogin = jwt.getClaim(USER_LOGIN);

        if (AppConstants.LOGIN_MODE_TOSCANA.equals(loginMode)) {
            userlogin = jwt.getClaim("preferred_username");
        }

        if (userlogin.isNull())
            throw new TokenInvalidException("Token Invalid");

        String username = userlogin.asString();
        if (StringUtils.isEmpty(username))
            throw new TokenInvalidException("Token Invalid");
        try {

            // sanitize
            username = username.toUpperCase().trim();
            if (username.startsWith("TINIT-"))
                username = username.replace("TINIT-", "");

            User user = userRepository.findByLoginIgnoreCase(username);

            if (user == null)
                throw new TokenInvalidException("Token Invalid");

            if (!AppConstants.LOGIN_MODE_TOSCANA.equals(loginMode)) {
                // Check in w_ssojwttoken
                Optional<WSsoJwtToken> optionalWSsoJwtToken = wSsoJwtTokenRepository.findById(username);
                if (optionalWSsoJwtToken.isEmpty())
                    throw new TokenInvalidException("Token Invalid");
                WSsoJwtToken wSsoJwtToken = optionalWSsoJwtToken.get();
                if (!wSsoJwtToken.getJwtToken().equals(jwtToken))
                    throw new TokenInvalidException("Token Invalid");
            }

            return getUserDTOFromUser(user);

        } catch (Exception e) {
            throw new TokenInvalidException("Token Invalid");
        }
    }
    
    private UserDTO getUserDTOFromUser(final User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setSyscon(user.getSyscon());
        userDTO.setUsername(user.getLogin());
        userDTO.setCodiceFiscale(user.getCodiceFiscale());
        userDTO.setNome(user.getSysute());
        userDTO.setCognome("");
        userDTO.setEmail(user.getEmail());

        boolean userLdap = user.getLdap() != null && (user.getLdap() == 1);
        userDTO.setLdap(userLdap);

        // Utente abilitato: sysdisab = null oppure sysdisab = 0, utente disabilitato: sysdisab != 0
        // Per considerare i casi anomali presenti in alcuni DB
        boolean userDisabilitato = !isUserAbilitato(user);

        userDTO.setDisabilitato(userDisabilitato);

        userDTO.setUfficioAppartenenza(user.getUfficioAppartenenza());
        userDTO.setCategoria(user.getCategoria());

        if (StringUtils.isNotBlank(user.getSyspwbou())) {
            userDTO.setOpzioniUtente(getListaOpzioniUtente(user.getSyspwbou()));
        }

        userDTO.setDataScadenzaAccount(user.getDataScadenzaAccount());
        userDTO.setDataUltimoAccesso(user.getDataUltimoAccesso());

        userDTO.setSysab3(user.getSysab3());

        boolean deletable = user.getSyscon() != 48L && user.getSyscon() != 49L && user.getSyscon() != 50L
                && user.getDataUltimoAccesso() == null;
        userDTO.setDeletable(deletable);

        if (user.getUffints() != null)
            userDTO.setUfficiIntestatari(user.getUffints().parallelStream().map(Uffint::getCodice).collect(Collectors.toList()));

        return userDTO;
    }

    private boolean isUserAbilitato(final User user) {

        if (user == null)
            return false;

        return StringUtils.isBlank(user.getDisabilitato())
                || (StringUtils.isNotBlank(user.getDisabilitato()) && AppConstants.SYSDISAB_UTENTE_ABILITATO.equals(user.getDisabilitato()));
    }
  
}
