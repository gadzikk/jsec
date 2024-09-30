package com.company.jsec.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "unsecured", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnsecuredController {

    @GetMapping
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
