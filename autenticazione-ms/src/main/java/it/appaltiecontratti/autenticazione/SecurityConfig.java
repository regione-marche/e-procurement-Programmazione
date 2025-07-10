package it.appaltiecontratti.autenticazione;

import it.appaltiecontratti.sicurezza.filter.AuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
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

@Configuration
@EnableWebSecurity
@SuppressWarnings("java:S4502")
public class SecurityConfig {

    @Value("${public.context-path}")
    private String publicContextPath;

    @Value("${sso.context-path:#{null}}")
    private String ssoContextPath;
    
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

	private static final RequestMatcher ACTUATOR_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/actuator/health/readiness"), //
            new AntPathRequestMatcher("/actuator/health/liveness"), //
            new AntPathRequestMatcher("/actuator/health") //
	);
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors() //
                .and().csrf().disable() //
                .authorizeRequests() //
                .antMatchers(buildPublicUrls()).permitAll() //
                .antMatchers(HttpMethod.OPTIONS).permitAll() //
                .requestMatchers(SWAGGER_URLS).permitAll() //
                .requestMatchers(ACTUATOR_URLS).permitAll() //
                .anyRequest().authenticated() //
                .and().sessionManagement() //
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
    }


    private String[] buildPublicUrls() {
        String[] publicUrls = new String[2];

        publicUrls[0] = "/ssoAuthentication/**";

        if (StringUtils.isNotBlank(ssoContextPath)) {
            if (!ssoContextPath.startsWith("/"))
                ssoContextPath = "/" + ssoContextPath;

            if (ssoContextPath.endsWith("/")) {
                publicUrls[0] = ssoContextPath + "**";
            } else {
                publicUrls[0] = ssoContextPath + "/**";
            }
        }

        if (StringUtils.isNotBlank(publicContextPath)) {
            if (!publicContextPath.startsWith("/"))
                publicContextPath = "/" + publicContextPath;

            if (publicContextPath.endsWith("/")) {
                publicUrls[1] = publicContextPath + "**";
            } else {
                publicUrls[1] = publicContextPath + "/**";
            }
        }
        return publicUrls;
    }

}