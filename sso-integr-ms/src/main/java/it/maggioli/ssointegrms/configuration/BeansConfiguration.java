package it.maggioli.ssointegrms.configuration;

import it.maggioli.ssointegrms.services.general.WLogEventiService;
import it.maggioli.ssointegrms.services.general.impl.WLogEventiServiceImpl;
import it.maggioli.ssointegrms.services.general.impl.WLogEventiServiceSQLServerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 29, 2023
 */
@Configuration
public class BeansConfiguration {

    @Bean
    @Conditional(DbVendorCondition.class)
    public WLogEventiService dbVendorPostgresOrOracle() {
        return new WLogEventiServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "VENDOR", havingValue = "MSQ")
    public WLogEventiService dbVendorSQLServer() {
        return new WLogEventiServiceSQLServerImpl();
    }
}
