package taha.labs.project.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(username == null) return false;

        // must start with letter, only letters, digits and underscore allowed
        return username.matches("[A-Za-z][A-Za-z0-9_]*");
    }
}
