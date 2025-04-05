package com.example.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @GetMapping("/success")
    public ResponseEntity<?> success(@RequestParam String email) {
        return ResponseEntity.ok("Login Success! Email: " + email);
    }

    @GetMapping("/fail")
    public ResponseEntity<?> fail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.ok("Custom Login Page - go to /oauth2/authorization/google");
    }
}


