package com.migueljuncalp.trazasan.gateway.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;

@Configuration
public class SwaggerCleanPathConfig {


    @Bean
    public OpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> {
            // Forzamos a que el servidor base de ejecución sea la raíz absoluta del Gateway "/"
            // Esto evita que Swagger intente adivinar y duplicar los contextos locales.
            Server rootServer = new Server().url("/");
            openApi.setServers(Collections.singletonList(rootServer));
        };
    }
}