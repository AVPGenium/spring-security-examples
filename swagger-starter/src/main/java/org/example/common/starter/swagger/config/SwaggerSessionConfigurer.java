package org.example.common.starter.swagger.config;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.ByteBuffer;

@Configuration
@RequiredArgsConstructor
@ConditionalOnMissingClass("ru.gpn.common.starter.keycloak.bearer.KeycloakAutoConfiguration")
public class SwaggerSessionConfigurer {

    private static final String ALLOWED_PATHS = "/api/.*";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(true)
                .directModelSubstitute(ByteBuffer.class, String.class)
                .directModelSubstitute(ObjectId.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .paths(PathSelectors.regex(ALLOWED_PATHS))
                .build();
    }


}
