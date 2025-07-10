package it.appaltiecontratti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ComponentScans(value = {@ComponentScan("it.appaltiecontratti.sicurezza.filter.AuthenticationFilter"),
		@ComponentScan("it.appaltiecontratti.sicurezza.dao"),
		@ComponentScan("it.appaltiecontratti.sicurezza.bl"),
        @ComponentScan("it.appaltiecontratti.monitoraggiocontratti"),
        @ComponentScan("io.swagger.client.api"),
        @ComponentScan("io.swagger.client")})
@EnableTransactionManagement
@EnableScheduling
public class RestApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
    }
    
}