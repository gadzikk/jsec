package com.company.mtlsserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "mtlsserver", produces = MediaType.APPLICATION_JSON_VALUE)
public class MtlsServerController {

    @PostMapping
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("Server OK", HttpStatus.OK);
    }
}
