package org.example.common.starter.swagger.config;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(name = "ru.gpn.common.starter.keycloak.bearer.KeycloakAutoConfiguration")
public class SwaggerBearerConfigurer {

    private static final String OAUTH_NAME = "spring_oauth";
    private static final String ALLOWED_PATHS = "/api/.*";
    private final SwaggerSpringBootProperties properties;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(true)
                .directModelSubstitute(ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .paths(PathSelectors.regex(ALLOWED_PATHS))
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .realm(properties.getRealm())
                .clientId(properties.getResource())
                .clientSecret(properties.getClientSecret())
                .scopeSeparator(" ")
                .build();
    }

    private SecurityScheme securityScheme() {
        String openIdUrl =
                properties.getAuthServerUrl()
                        + "/realms/"
                        + properties.getRealm()
                        + "/protocol/openid-connect";
        GrantType grantType =
                new AuthorizationCodeGrantBuilder()
                        .tokenEndpoint(new TokenEndpoint(openIdUrl + "/token", properties.getResource()))
                        .tokenRequestEndpoint(
                                new TokenRequestEndpoint(openIdUrl + "/auth", properties.getResource(), null))
                        .build();

        return new OAuthBuilder()
                .name(OAUTH_NAME)
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[] {
                new AuthorizationScope("openid", null)};
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(
                        new SecurityReference(OAUTH_NAME, scopes())))
                .forPaths(PathSelectors.regex(ALLOWED_PATHS))
                .build();
    }


}
