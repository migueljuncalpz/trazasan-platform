package com.migueljuncalp.trazasan.identity.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @GetMapping("/status")
    public ResponseEntity getStatus(){
        return ResponseEntity.ok("User status ok");
    }
}
