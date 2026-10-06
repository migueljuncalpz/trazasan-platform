package com.migueljuncalp.trazasan.identity.controller;


import com.migueljuncalp.trazasan.identity.controller.dto.user.request.CreateUserRequest;
import com.migueljuncalp.trazasan.identity.controller.dto.user.response.CreateUserResponse;
import com.migueljuncalp.trazasan.identity.service.users.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UsersService usersService;

    public UserController (UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/status")
    public ResponseEntity getStatus(){
        return ResponseEntity.ok("User status ok");
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> getStatus(@Valid @RequestBody CreateUserRequest user){

        return ResponseEntity.ok(this.usersService.create(user));
    }
}
