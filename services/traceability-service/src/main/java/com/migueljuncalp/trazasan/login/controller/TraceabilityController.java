package com.migueljuncalp.trazasan.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/traceability")
public class TraceabilityController {

    @GetMapping("/status")
    ResponseEntity getStatus(){
        return ResponseEntity.ok("Traceability OK");
    }
}
