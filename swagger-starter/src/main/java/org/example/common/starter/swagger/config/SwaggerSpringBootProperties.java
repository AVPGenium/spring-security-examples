package org.example.common.starter.swagger.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "starters")
@Data
public class SwaggerSpringBootProperties {
    private String authServerUrl;
    private String resource;
    private String realm;
    private String clientSecret;
    private List<String> scopes;
}
