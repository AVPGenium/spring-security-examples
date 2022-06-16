package org.example.common.starter.keycloak.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "starters.cors")
public class CorsProperties extends CorsConfiguration {
}
