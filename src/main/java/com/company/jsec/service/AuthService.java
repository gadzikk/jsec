package com.company.jsec.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public String auth() {
        var email = "waszkag@o2.pl";
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, "password"));
        return jwtService.generateToken(email);
    }
}
