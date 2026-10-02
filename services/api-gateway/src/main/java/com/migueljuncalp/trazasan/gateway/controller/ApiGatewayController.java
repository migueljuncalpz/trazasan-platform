package com.migueljuncalp.trazasan.gateway.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080")
        }
)
public class ApiGatewayController {

    @GetMapping("/status")
    ResponseEntity getStatus(){
        return ResponseEntity.ok("Todo correcto");
    }
}
