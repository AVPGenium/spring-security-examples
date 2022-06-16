package org.example.common.starter.keycloak.bearer.config;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.example.common.starter.keycloak.config.AbstractSecurityConfig;
import org.example.common.starter.keycloak.config.MatchersConfiguration;
import org.example.common.starter.keycloak.utils.Constants;

@Configuration
@RequiredArgsConstructor
public class DefaultSecurityConfig extends AbstractSecurityConfig {
    @Bean
    public MatchersConfiguration publicMatchers() {
        List<String> antMatchers = new ArrayList<>();
        antMatchers.add(Constants.API_PREFIX + "/uaa/json");
        antMatchers.add(Constants.API_PREFIX + "/uaa/me");
        String accessAttribute = "permitAll";
        return new MatchersConfiguration(antMatchers, accessAttribute);
    }
}
