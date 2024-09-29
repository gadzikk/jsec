package com.company.jsec.controller;

import com.company.jsec.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<Void> login() {
        var token = authService.auth();
        return ResponseEntity.ok().header("Authorization", token).build();
    }
}
