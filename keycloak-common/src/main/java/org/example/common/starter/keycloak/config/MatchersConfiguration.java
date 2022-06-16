package org.example.common.starter.keycloak.config;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MatchersConfiguration {
    private final List<String> antMatchers;
    private final String spelExp;
}
