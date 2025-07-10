package it.appaltiecontratti.gestionereportsms.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.openapi.url}")
    private String serverUrl;

    @Bean
    public OpenAPI reportOpenAPI() {

        if(!StringUtils.isEmpty(serverUrl)) {

            Server reportServer = new Server();
            reportServer.setUrl(serverUrl);
            reportServer.setDescription("Server URL in dev-env for reports search.");

            return new OpenAPI()
                    .info(new Info()
                            .title("Gestione report")
                            .version("1.0")
                            .description("Gestione chiamate http per generazione e visualizzazione report"))
                    .servers(List.of(reportServer));
        }
        else {
            return new OpenAPI()
                .info(new Info()
                    .title("Gestione report")
                    .version("1.0")
                    .description("Gestione chiamate http per generazione e visualizzazione report"));
        }
    }
}
