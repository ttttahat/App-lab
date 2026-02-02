package taha.labs.project.service;

import taha.labs.project.entity.Note;
import taha.labs.project.model.dto.User;
import taha.labs.project.model.dto.NoteDto;
import taha.labs.project.repository.NoteRepository;
import taha.labs.project.repository.NoteJdbcRepository;
import taha.labs.project.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteJdbcRepository noteJdbcRepository; 

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void createNote_savesNoteForUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        NoteDto dto = new NoteDto();
        dto.setTitle("Title");
        dto.setContent("Content");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(noteRepository.save(any(Note.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Note saved = noteService.createNote(dto, "test@test.com");


        assertThat(saved.getTitle()).isEqualTo("Title");

        assertThat(saved.getUserId()).isEqualTo(1L);

        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void updateNote_deniesAccessForDifferentUser() {

        Long noteId = 1L;
        String unauthorizedEmail = "other@test.com";
        
        User otherUser = new User();
        otherUser.setId(99L); 
        otherUser.setEmail(unauthorizedEmail);

        when(userRepository.findByEmail(unauthorizedEmail))
                .thenReturn(Optional.of(otherUser));


        when(noteJdbcRepository.findByIdAndUserId(noteId, otherUser.getId()))
                .thenReturn(Optional.empty());

        NoteDto dto = new NoteDto();


        assertThrows(RuntimeException.class, () -> 
            noteService.updateNote(noteId, dto, unauthorizedEmail)
        );
    }
}