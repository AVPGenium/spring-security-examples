package org.example.common.starter.swagger;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.example.common.starter.swagger.config.SwaggerSpringBootProperties;

@Configuration
@ComponentScan
@EnableConfigurationProperties(SwaggerSpringBootProperties.class)
@Slf4j
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class SwaggerAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("Starter: swagger-starter started");
    }
}
