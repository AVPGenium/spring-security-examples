package org.example.common.starter.keycloak.utils;

import org.springframework.core.Ordered;

public final class Constants {

    public static final String API_PREFIX = "/api";
    public static final int COMMON_PRIORITY = Ordered.HIGHEST_PRECEDENCE + 1;

    private Constants() {
    }
}
