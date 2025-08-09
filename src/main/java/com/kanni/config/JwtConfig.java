package com.kanni.config;

import com.kanni.utils.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JwtConfig {

    private final RsaKeyProperties keys;

    public JwtConfig(RsaKeyProperties keys) {
        this.keys = keys;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey())
                         .privateKey(keys.getPrivateKey())
                         .build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/token").permitAll()  // allow token generation without auth
                        .anyRequest().authenticated()               // everything else requires JWT
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt()  // use JWTs for authentication
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

}