package com.company.jsec.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostConstruct
    public void init() {
        var token = generateToken("waszkag@o2.pl");
        log.info("=========== " + token + " ===========");
        var body = getTokenBody(token);
        log.info("=========== " + body + " ===========");
        var email = extractEmail(token);
        log.info("=========== " + email + " ===========");
        var isExpired = isExpired(token);
        log.info("=========== " + isExpired + " ===========");
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(20, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return getTokenBody(token).getSubject();
    }

    @SneakyThrows
    public Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isExpired(String token) {
        return getTokenBody(token)
                .getExpiration()
                .before(Date.from(Instant.now()));
    }
}
