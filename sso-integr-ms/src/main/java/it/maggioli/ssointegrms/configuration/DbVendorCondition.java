package it.maggioli.ssointegrms.configuration;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 29, 2023
 */
public class DbVendorCondition extends AnyNestedCondition {
    public DbVendorCondition() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(name = "VENDOR", havingValue = "POS", matchIfMissing = true)
    static class POSCondition { }

    @ConditionalOnProperty(name = "VENDOR", havingValue = "ORA")
    static class ORACondition { }
}
