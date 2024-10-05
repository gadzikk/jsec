package com.company.suplementary.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "suplementary", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class SuplementaryController {

    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<String> retrieveToken() {
        var response = restTemplate.postForEntity("https://localhost:8081/auth/login", "{}", String.class);
        log.info("RESPONSE PAYLOAD {}", response.toString());
        return ResponseEntity.ok("x");
    }
}
