package it.appaltiecontratti.gestionereportsms.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.appaltiecontratti.gestionereportsms.dto.UserDTO;
import it.appaltiecontratti.gestionereportsms.exceptions.TokenExpiredException;
import it.appaltiecontratti.gestionereportsms.exceptions.TokenInvalidException;
import it.appaltiecontratti.gestionereportsms.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @author Alessio Iezzi
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Value("${application.currentLoginMode}")
    private String currentLoginMode;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;

        try {
            String auth = req.getHeader("Authorization");

            if (StringUtils.isNotBlank(auth)) {

                String token = StringUtils.replace(auth, "Bearer", "").trim();

                if (StringUtils.isNotBlank(token)) {

                    String loginMode = req.getHeader("X-loginMode");

                    if (StringUtils.isBlank(loginMode))
                        throw new TokenInvalidException("Login mode empty");

                    if (currentLoginMode.equals(loginMode)) {
                        UserDTO userDTO = userService.executeCheckToken(token, loginMode);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, null);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (TokenExpiredException | TokenInvalidException e) {
            LOGGER.error("Impossible decodificare il token [ {} ]", e.getMessage());
            request.setAttribute("excMessage", e.getMessage());
        }

        filterChain.doFilter(request, response);

    }

}
