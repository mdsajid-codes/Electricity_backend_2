package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import com.example.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin (origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if(token != null){
            return ResponseEntity.ok(new LoginResponse(token, loginRequest.getUsername(), "LoginSuccessful"));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse(null, null, "Invalid Username password!"));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validToken(@RequestHeader ("Authorization") String authHeader){
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            boolean isValid = authService.validateToken(token);
            return ResponseEntity.ok(isValid);
        }
        return ResponseEntity.ok(false);
    }
}
