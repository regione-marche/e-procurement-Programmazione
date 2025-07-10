package it.appaltiecontratti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ComponentScans(value = {@ComponentScan("it.appaltiecontratti.sicurezza.filter.AuthenticationFilter"),
		@ComponentScan("it.appaltiecontratti.sicurezza.dao"),
        @ComponentScan("it.appaltiecontratti.sicurezza.bl"),
        @ComponentScan("it.appaltiecontratti.monitoraggiocontratti")})
public class RestApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
    }
}