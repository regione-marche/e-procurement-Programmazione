package it.maggioli.ssointegrms;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since mar 24, 2023
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsoIntegrMsApplication.class);
    }

}