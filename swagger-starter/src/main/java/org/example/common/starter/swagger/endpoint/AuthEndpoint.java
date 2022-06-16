package org.example.common.starter.swagger.endpoint;

import lombok.RequiredArgsConstructor;
import org.example.common.starter.swagger.config.SwaggerSpringBootProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@ConditionalOnMissingClass("ru.gpn.common.starter.keycloak.bearer.KeycloakAutoConfiguration")
@RequestMapping("/api/uaa")
public class AuthEndpoint {
    private final SwaggerSpringBootProperties properties;

    @GetMapping("/json")
    public Map<String, Object> getAuthInfoJson() {
        Map<String, Object> result = new HashMap<>();
        result.put("realm", properties.getRealm());
        result.put("auth-server-url", properties.getAuthServerUrl());
        result.put("client-secret", properties.getClientSecret());
        result.put("resource", properties.getResource());
        result.put("scopes", properties.getScopes());
        return result;
    }
}
