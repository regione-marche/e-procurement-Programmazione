package it.appaltiecontratti.pubblprogrammi.filter;

import it.appaltiecontratti.autenticazione.exceptions.TokenInvalidException;
import it.appaltiecontratti.sicurezza.bl.UserManager;
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Component
public class AuthenticationSimpleFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSimpleFilter.class);

	@Autowired
	private UserManager userService;
	
	@Value("${TOKEN_HEADER:#{\"Authorization\"}}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;

		try {
			String auth = req.getHeader(tokenHeader);

			if (StringUtils.isNotBlank(auth)) {

				String token = StringUtils.replace(auth, "Bearer", "").trim();

				if (StringUtils.isNotBlank(token)) {

					String loginMode = req.getHeader("X-loginMode");

					if (StringUtils.isBlank(loginMode))
						throw new TokenInvalidException("Login mode empty");
	
					userService.executeCheckToken(token, loginMode, true);
					Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, null);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

		} catch (Exception e) {
			LOGGER.error("Impossible decodificare il token [ {} ]", e.getMessage());
			request.setAttribute("excMessage", e.getMessage());
		}
		filterChain.doFilter(request, response);

	}

}
