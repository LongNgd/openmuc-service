package vn.atdigital.iot.configuration.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {
    @Bean(name = "customCorsFilter")
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
}
