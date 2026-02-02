package taha.labs.project.repository;

import taha.labs.project.entity.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NoteJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoteJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Note> noteRowMapper = (rs, rowNum) -> {
        Note note = new Note();
        note.setId(rs.getLong("id"));
        note.setUserId(rs.getLong("user_id"));
        note.setTitle(rs.getString("title"));
        note.setContent(rs.getString("content"));
        return note;
    };

    // finding a note by id and userid using prepared statement
    public Optional<Note> findByIdAndUserId(Long noteId, Long userId) {
        String sql = "SELECT * FROM notes WHERE id = ? AND user_id = ?";
        

        return jdbcTemplate.query(sql, noteRowMapper, noteId, userId)
                .stream()
                .findFirst();
    }
}