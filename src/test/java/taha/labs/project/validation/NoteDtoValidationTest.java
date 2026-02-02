package taha.labs.project.validation;

import taha.labs.project.model.dto.NoteDto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoteDtoValidationTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void emptyTitle_isRejected() {
        NoteDto dto = new NoteDto();
        dto.setTitle("");
        dto.setContent("Content");

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }
}
