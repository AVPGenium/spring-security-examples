package org.example.common.starter.keycloak.bearer.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@SuppressWarnings("CheckStyle")
@KeycloakConfiguration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurerAdapter extends KeycloakWebSecurityConfigurerAdapter {
    private final Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>
            .ExpressionInterceptUrlRegistry> authorizeRequestsCustomizer;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(authorizeRequestsCustomizer)
                .csrf()
                .disable()
                .cors();
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
