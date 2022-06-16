package org.example.common.starter.keycloak.bearer;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.example.common.starter.keycloak.config.CorsProperties;

@Configuration
@ComponentScan
@EnableConfigurationProperties({KeycloakSpringBootProperties.class,
        CorsProperties.class})
@PropertySource(value = "classpath:keycloak-starter.properties", encoding = "UTF-8")
@Slf4j
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class KeycloakAutoConfiguration {

}
