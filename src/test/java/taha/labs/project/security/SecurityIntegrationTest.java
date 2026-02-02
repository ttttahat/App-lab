package taha.labs.project.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedUser_isRedirectedToLogin() throws Exception {

        mockMvc.perform(get("/user/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "taha@test.com")
    void authenticatedUser_canAccessHome() throws Exception {
        mockMvc.perform(get("/user/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "taha@test.com")
    void authenticatedUser_isRedirectedFromWelcomeToHome() throws Exception {

        mockMvc.perform(get("/welcome"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/home"));
    }
    @Test
    @WithMockUser
    void testGetPagesForCoverage() throws Exception {
        mockMvc.perform(get("/notes")).andExpect(status().isOk());
        mockMvc.perform(get("/notes/new")).andExpect(status().isOk());

}    
}