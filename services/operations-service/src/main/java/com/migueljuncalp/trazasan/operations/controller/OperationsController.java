package com.migueljuncalp.trazasan.operations.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operations")
public class OperationsController {
    
    @GetMapping("/status")
    ResponseEntity getStatus(){
        return ResponseEntity.ok("Operations OK");
    }
}