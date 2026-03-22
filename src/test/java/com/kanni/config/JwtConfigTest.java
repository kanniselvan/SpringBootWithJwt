import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncodingContext;
import org.springframework.security.oauth2.jwt.JwtDecodingContext;
import org.springframework.security.test.context.support.WithSecurityContext;

import com.kanni.config.JwtConfig;

@SpringBootTest
public class JwtConfigTest {

    @Autowired
    private JwtConfig jwtConfig;

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    @BeforeEach
    public void setUp() {
        this.jwtEncoder = jwtConfig.jwtEncoder();
        this.jwtDecoder = jwtConfig.jwtDecoder();
    }

    @Test
    public void testJwtEncoder() {
        // Here, we should create a mock JwtEncodingContext and ensure that encoding works as expected
        JwtEncodingContext context = JwtEncodingContext.newBuilder().header("alg", "none").build();
        // Example of encoding a JWT
        String token = jwtEncoder.encode(context);
        assertNotNull(token);
    }

    @Test
    public void testJwtDecoder() {
        // Create a valid JWT token 
        String validToken = "<valid-jwt-token>"; // Replace with a valid token
        JwtDecodingContext context = JwtDecodingContext.newBuilder().token(validToken).build();
        // Ensure decoding works as expected
        assertDoesNotThrow(() -> jwtDecoder.decode(context));
    }

    @WithMockUser
    @Test
    public void testSecurityFilterChain() throws Exception {
        // Given
        HttpSecurity http = mock(HttpSecurity.class);
        // When
        jwtConfig.securityFilterChain(http);
        // Then
        verify(http).authorizeRequests();
        verify(http).csrf().disable();
        // Add further verifications as required
    }
}
