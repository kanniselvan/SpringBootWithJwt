# SpringBootWithJwt



NimbusDS (from Nimbus Directory Services) refers to a set of open-source Java libraries for working with:

JWTs (JSON Web Tokens)

JOSE (JSON Object Signing and Encryption)

JWK (JSON Web Keys)

It's widely used in the Java ecosystem — especially within Spring Security — for handling cryptographic operations around tokens.


---


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
                  .requestMatchers("/api/token").permitAll() // allow token generation without auth 
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

---

![apiToken](https://github.com/user-attachments/assets/73ca1b0e-5eb3-4dc2-9bf0-6085ccf7ed7a)

![noAuth](https://github.com/user-attachments/assets/0b67d0ff-26e5-4a30-80c9-082ec364539b)

![Success](https://github.com/user-attachments/assets/338e1f11-237b-4674-900a-7b5930661294)

