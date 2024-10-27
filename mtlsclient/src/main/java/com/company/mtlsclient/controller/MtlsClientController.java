package com.company.mtlsclient.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "mtlsclient", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class MtlsClientController {

    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<String> retrieveToken() {
        var response = restTemplate.postForEntity("https://localhost:8083/mtlsserver", "{}", String.class);
        log.info("RESPONSE PAYLOAD {}", response.toString());
        return ResponseEntity.ok("x");
    }
}
