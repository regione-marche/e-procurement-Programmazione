package it.appaltiecontratti.gestionereportsms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionereportsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionereportsmsApplication.class, args);
	}

}
