package io.github.originalalex.filmdex.server.dto.validators;

import io.github.originalalex.filmdex.server.dto.annotations.ValidPostBody;
import io.github.originalalex.filmdex.server.dto.annotations.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostBodyValidator implements ConstraintValidator<ValidPostBody, String> {

    @Override
    public void initialize(ValidPostBody body) {

    }

    @Override
    public boolean isValid(String body, ConstraintValidatorContext context) {
        if (body.length() < 8 || body.length() > 6000) {
            context.buildConstraintViolationWithTemplate("Post body must be between 8 and 6000 characters").addConstraintViolation();
            return false;
        }
        return true;
    }

}
