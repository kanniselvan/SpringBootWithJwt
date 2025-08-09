package com.kanni.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenController {

    private final JwtEncoder encoder;

    public TokenController(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String username) {
        Instant now = Instant.now();
        long expiry = 3600L; // 1 hour

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(username)
                .claim("scope", "USER")
                .build();

        JwsHeader headers = JwsHeader.with(() -> "RS256").build();
        Jwt jwt = encoder.encode(JwtEncoderParameters.from(headers, claims));

        return ResponseEntity.ok(jwt.getTokenValue());
    }

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName();
    }

    @GetMapping("/claims")
    public Map<String, Object> claims(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }
}