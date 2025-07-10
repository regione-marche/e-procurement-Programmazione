package it.appaltiecontratti.monitoraggiocontratti;

import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
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

@SuppressWarnings("java:S4502")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${public.context-path}")
	private String  publicContextPath;

	@Value("${protected.context-path}")
	private String  protectedContextPath;
	
    @Value("${xloginMode:#{0}}")
    private String xloginMode;

	private static final RequestMatcher SWAGGER_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/swagger-ui.html"), //
			new AntPathRequestMatcher("/v2/api-docs"), //
			new AntPathRequestMatcher("/configuration/ui"), //
			new AntPathRequestMatcher("/swagger-resources/**"), //
			new AntPathRequestMatcher("/webjars/**") //
	);
	
	@Autowired
	private AuthenticationFilter authenticationFilter;

	@Autowired
	private ContrattiMapper contrattiMapper;
	
	private static final RequestMatcher ACTUATOR_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/actuator/health/readiness"), //
			new AntPathRequestMatcher("/actuator/health/liveness"), //
			new AntPathRequestMatcher("/actuator/health") //
	);

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		String[] publicUrls = new String[6];
		if(publicContextPath != null && !publicContextPath.startsWith("/")) {
			publicContextPath = "/" + publicContextPath;
		}
		if(publicContextPath != null && publicContextPath.endsWith("/")) {
			 publicUrls[0] = publicContextPath + "**";
		} else {
			 publicUrls[0] = publicContextPath + "/**";
		} 
		publicUrls[1]="/internalAuthentication/**";
		publicUrls[2]="/oneGateway/**";
		publicUrls[3]="/sitar/**";
		publicUrls[4]="/standard/**";
		publicUrls[5]="/gestioneUtenti/public/**";
		

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
		http.addFilterBefore(new CustomAuthenticationFilter(protectedContextPath+"/gestioneContratti/getJsonListaSchede", contrattiMapper), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new CustomAuthenticationFilter(protectedContextPath+"/gestioneContratti/getListaCig", contrattiMapper), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}