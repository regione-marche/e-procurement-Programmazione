package it.maggioli.ssointegrms.configuration;

import it.maggioli.ssointegrms.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author Cristiano Perin
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Value("${application.currentLoginMode}")
    private String currentLoginMode;

    private static final RequestMatcher SWAGGER_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/swagger-ui/**"), //
            new AntPathRequestMatcher("/v3/api-docs"), //
            new AntPathRequestMatcher("/configuration/ui"), //
            new AntPathRequestMatcher("/swagger-resources/**"), //
            new AntPathRequestMatcher("/webjars/**"), //
            new AntPathRequestMatcher("/api-docs/**") //
    );

    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/internalAuthentication/**"), //
            new AntPathRequestMatcher("/oneGateway/**"), //
            new AntPathRequestMatcher("/standard/**"), //
            new AntPathRequestMatcher("/gestioneUtenti/public/**") //
    );

    private static final RequestMatcher ACTUATOR_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/actuator/health/readiness"), //
            new AntPathRequestMatcher("/actuator/health/liveness"), //
            new AntPathRequestMatcher("/actuator/health") //
    );

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    @SuppressWarnings("java:S4502")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors() //
                .and().csrf(AbstractHttpConfigurer::disable) //
                .authorizeRequests(ex -> ex //
                        .requestMatchers(PUBLIC_URLS).permitAll() //
                        .antMatchers(HttpMethod.OPTIONS).permitAll() //
                        .requestMatchers(SWAGGER_URLS).permitAll() //
                        .requestMatchers(ACTUATOR_URLS).permitAll() //
                        .anyRequest().authenticated()) //
                .sessionManagement(s -> s //
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
                );
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}