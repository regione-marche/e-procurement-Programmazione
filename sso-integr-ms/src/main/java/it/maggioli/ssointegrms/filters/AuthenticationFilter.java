package it.maggioli.ssointegrms.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.maggioli.ssointegrms.dto.UserDTO;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.TokenExpiredException;
import it.maggioli.ssointegrms.exceptions.gestioneUtenti.TokenInvalidException;
import it.maggioli.ssointegrms.services.general.UserService;

/**
 * 
 * @author Cristiano Perin
 *
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
