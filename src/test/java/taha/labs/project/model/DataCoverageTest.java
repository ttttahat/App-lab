package taha.labs.project.model;

import org.junit.jupiter.api.Test;
import taha.labs.project.entity.Note;
import taha.labs.project.model.dto.NoteDto;
import taha.labs.project.model.dto.User;
import static org.assertj.core.api.Assertions.assertThat;

class DataCoverageTest {

    @Test
    void testGetterSetters() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test");
        note.setContent("Content");
        note.setUserId(1L);
        assertThat(note.getId()).isEqualTo(1L);
        assertThat(note.getTitle()).isEqualTo("Test");
        
        NoteDto dto = new NoteDto();
        dto.setTitle("Dto");
        dto.setContent("Body");
        assertThat(dto.getTitle()).isEqualTo("Dto");
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setPassword("pass");
        assertThat(user.getEmail()).isEqualTo("test@test.com");
    }
}