package org.example.common.starter.keycloak.config;

import java.util.ArrayList;
import java.util.List;

import org.example.common.starter.keycloak.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public abstract class AbstractSecurityConfig {
    @Autowired
    protected CorsProperties corsProperties;

    @Bean
    public MatchersConfiguration apiMatchers() {
        List<String> antMatchers = new ArrayList<>();
        antMatchers.add(Constants.API_PREFIX + "/**");
        String accessAttribute = "authenticated";
        return new MatchersConfiguration(antMatchers, accessAttribute);
    }

    @Bean
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>
            .ExpressionInterceptUrlRegistry> authorizeRequestsCustomizer(
            List<MatchersConfiguration> matchersConfigurations) {
        return (requests) -> {
            matchersConfigurations.forEach(matchersConfiguration ->
                    requests
                            .antMatchers(matchersConfiguration.getAntMatchers().toArray(String[]::new))
                            .access(matchersConfiguration.getSpelExp())
            );
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsProperties);
        return source;
    }
}
