package taha.labs.project.service;

import taha.labs.project.entity.Note;
import taha.labs.project.model.dto.User;
import taha.labs.project.model.dto.NoteDto;
import taha.labs.project.repository.NoteRepository;
import taha.labs.project.repository.NoteJdbcRepository; 
import taha.labs.project.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteJdbcRepository noteJdbcRepository;
    private final UserRepository userRepository;

    public NoteService(
            NoteRepository noteRepository,
            NoteJdbcRepository noteJdbcRepository,
            UserRepository userRepository
    ) {
        this.noteRepository = noteRepository;
        this.noteJdbcRepository = noteJdbcRepository;
        this.userRepository = userRepository;
    }

    public Note createNote(NoteDto dto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setUserId(user.getId()); 

        return noteRepository.save(note); 
    }

    public List<Note> getUserNotes(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // users only see their own notes
        return noteRepository.findAllByUserId(user.getId());
    }

    public Note getNoteSecure(Long noteId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // users can only fetch their own note by id
        return noteJdbcRepository
                .findByIdAndUserId(noteId, user.getId())
                .orElseThrow(() -> new RuntimeException("Note not found or unauthorized"));
    }

    public Note updateNote(Long noteId, NoteDto dto, String email) {
        Note note = getNoteSecure(noteId, email);

        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());

        return noteRepository.save(note);
    }

    public void deleteNote(Long noteId, String email) {
        // call getNoteSecure first, verify ownership before deleting
        Note note = getNoteSecure(noteId, email);
        noteRepository.deleteById(note.getId());
    }
}