package it.appaltiecontratti.tabellati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import it.appaltiecontratti.sicurezza.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${public.context-path}")
	private String publicContextPath;
    
    @Value("${xloginMode:#{0}}")
    private String xloginMode;

	private static final RequestMatcher SWAGGER_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/swagger-ui.html"), //
			new AntPathRequestMatcher("/v2/api-docs"), //
			new AntPathRequestMatcher("/configuration/ui"), //
			new AntPathRequestMatcher("/swagger-resources/**"), //
			new AntPathRequestMatcher("/webjars/**") //
	);
	
	private static final RequestMatcher ACTUATOR_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/actuator/health/readiness"), //
			new AntPathRequestMatcher("/actuator/health/liveness"), //
			new AntPathRequestMatcher("/actuator/health") //
	);

	@Autowired
	private AuthenticationFilter authenticationFilter;

	@SuppressWarnings("java:S4502")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		String[] publicUrls = new String[1];
		if (publicContextPath != null && !publicContextPath.startsWith("/")) {
			publicContextPath = "/" + publicContextPath;
		}
		if (publicContextPath != null && publicContextPath.endsWith("/")) {
			publicUrls[0] = publicContextPath + "**";
		} else {
			publicUrls[0] = publicContextPath + "/**";
		}
		http.cors() //
				.and().csrf().disable() //
				.authorizeRequests() //
				.antMatchers(publicUrls).permitAll() //
				.antMatchers(HttpMethod.OPTIONS).permitAll() //
				.requestMatchers(SWAGGER_URLS).permitAll() //
				.requestMatchers(ACTUATOR_URLS).permitAll() //
				.anyRequest().authenticated() //
				.and().sessionManagement() //
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}