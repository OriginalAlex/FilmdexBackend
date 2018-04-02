package io.github.originalalex.filmdex.server.dto.validators;

import io.github.originalalex.filmdex.server.dto.annotations.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public void initialize(ValidUsername username) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (!username.matches("^[a-zA-Z0-9_]*$")) {
            context.buildConstraintViolationWithTemplate("Usernames must be alphanumeric").addConstraintViolation();
            return false;
        }
        if (username.length() < 4 || username.length() > 16) {
            context.buildConstraintViolationWithTemplate("Username length must be between 4 and 16 characters").addConstraintViolation();
            return false;
        }
        return true;
    }

}
