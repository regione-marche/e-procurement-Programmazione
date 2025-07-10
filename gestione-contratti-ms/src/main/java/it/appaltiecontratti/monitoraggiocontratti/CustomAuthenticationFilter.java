package it.appaltiecontratti.monitoraggiocontratti;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.appaltiecontratti.autenticazione.exceptions.TokenInvalidException;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.sicurezza.VigilanzaSecurityConstants;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.filter.AuthenticationFilter;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter  {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);


    private final ContrattiMapper contrattiMapper;


    private String codiceProdotto = "W9";
    private static final String CONFIG_CHIAVE_APP = "it.eldasoft.ApiEsecuzione.passphrase";

    public CustomAuthenticationFilter(String defaultFilterProcessesUrl, ContrattiMapper contrattiMapper) {
        super(defaultFilterProcessesUrl);
        this.contrattiMapper = contrattiMapper;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Logica per ottenere le credenziali e autenticarle

        HttpServletRequest req = (HttpServletRequest) request;

        try {
            String auth = req.getHeader("Authorization");

            if (StringUtils.isNotBlank(auth)) {

                String token = StringUtils.replace(auth, "Bearer", "").trim();

                if (StringUtils.isNotBlank(token)) {
                    this.executeCheckCustomToken(token);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken("customUser", null, null);
            return authentication;
        } catch (Exception e) {
            LOGGER.error("Impossible decodificare il token [ {} ]", e.getMessage());

        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, null);
        authentication.setAuthenticated(false);
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    public void executeCheckCustomToken(final String token)
            throws TokenExpiredException, TokenInvalidException {

        LOGGER.debug("Execution start UserServiceImpl::executeCheckCustomToken for token [ {} ]", token);

        if (StringUtils.isBlank(token))
            throw new IllegalArgumentException("token null");

        DecodedJWT jwt = null;
        try {
            byte[] jwtKey = getCustomJWTKey();
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey);
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            jwt = JWT.decode(token);
            LOGGER.debug("Token decoded");
            if (jwt.getExpiresAt().getTime() < System.currentTimeMillis())
                throw new TokenExpiredException("Token expired");
        } catch (JWTDecodeException e) {
            LOGGER.error("Invalid token: {}", e.getMessage());
            throw new TokenInvalidException("Token Invalid");
        } catch (CriptazioneException e) {
            LOGGER.error("JWT Key error: {}", e.getMessage());
            throw new TokenInvalidException("JWT Key error");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token expired: {}", e.getMessage());
            throw new TokenExpiredException("Token expired");
        }

        LOGGER.debug("isExpired: {}", (jwt.getExpiresAt().getTime() < System.currentTimeMillis()));
    }

    public byte[] getCustomJWTKey() throws CriptazioneException {

        String cryptedKey = this.contrattiMapper.getCipherKey(codiceProdotto, CONFIG_CHIAVE_APP);
        try {
            ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, cryptedKey.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return decrypt.getDatoNonCifrato();
        } catch (CriptazioneException e) {
            LOGGER.error("CustomAuthenticationFilter::Errore in fase di decrypt della chiave hash per generazione token", e);
            throw e;
        }
    }



}
