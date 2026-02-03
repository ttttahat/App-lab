package taha.labs.project.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import taha.labs.project.service.NoteService; 

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class NoteCsrfIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    @WithMockUser(username = "testuser")
    void postWithCsrf_isAllowed() throws Exception {
        mockMvc.perform(
                post("/notes") 
                        .with(csrf())
                        .param("title", "Test Title")
                        .param("content", "Test Content")
        ).andExpect(status().is3xxRedirection()); 
    }

    @Test
    @WithMockUser
    void postWithoutCsrf_isRejected() throws Exception {
        mockMvc.perform(
                post("/notes")
        ).andExpect(status().is3xxRedirection())
         .andExpect(redirectedUrl("/login"));
    }
}