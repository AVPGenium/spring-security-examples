package org.example.common.starter.keycloak.bearer.endpoint;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.common.starter.keycloak.bearer.dto.UserInfo;
import org.example.common.starter.keycloak.bearer.utils.KeycloakSecurityUtils;
import org.example.common.starter.keycloak.utils.Constants;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/uaa")
public class KeycloakEndpoint {

    private final KeycloakSpringBootProperties properties;

    @GetMapping("/me")
    public ResponseEntity<UserInfo> me() {
        return ResponseEntity.of(KeycloakSecurityUtils.getUser());
    }

    @GetMapping("/json")
    public Map<String, Object> getKeycloakJson() {
        Map<String, Object> result = new HashMap<>();
        result.put("realm", properties.getRealm());
        result.put("auth-server-url", properties.getAuthServerUrl());
        result.put("ssl-required", properties.getSslRequired());
        result.put("resource", properties.getResource());
        result.put("public-client", properties.isPublicClient());
        result.put("confidential-port", properties.getConfidentialPort());
        return result;
    }
}
