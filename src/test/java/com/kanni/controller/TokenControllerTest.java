import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.kanni.controller.TokenController;
import com.kanni.service.TokenService;
import com.kanni.model.Token;
import com.kanni.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(TokenController.class)
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenController tokenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build();
    }

    @Test
    public void testGenerateToken() throws Exception {
        User user = new User("testUser", "testPassword");
        Token token = new Token("generatedToken");

        doReturn(token).when(tokenService).generateToken(user);

        mockMvc.perform(post("/api/token/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("generatedToken"));

        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, World!"));
    }

    @Test
    public void testClaimsEndpoint() throws Exception {
        String claims = "{\"sub\": \"testUser\", \"iat\": 1516239022}";
        doReturn(claims).when(tokenService).getClaims("generatedToken");

        mockMvc.perform(get("/api/token/claims")
                .param("token", "generatedToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.claims").value(claims));

        verify(tokenService, times(1)).getClaims("generatedToken");
    }
}