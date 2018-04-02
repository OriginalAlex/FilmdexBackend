package io.github.originalalex.filmdex.server.dto.validators;


import io.github.originalalex.filmdex.server.dto.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private boolean containsCapital(String string) {
        for (char c : string.toCharArray()) {
            if (Character.isUpperCase(c)) return true;
        }
        return false;
    }

    @Override
    public void initialize(ValidPassword password) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if(password.length() < 5 || password.length() > 100) {
            context.buildConstraintViolationWithTemplate("Password length must be between 4 and 100").addConstraintViolation();
            return false;
        }
        if (!containsCapital(password)) {
            context.buildConstraintViolationWithTemplate("Passwords must contain at least one capital").addConstraintViolation();
            return false;
        }
        return true;
    }

}
