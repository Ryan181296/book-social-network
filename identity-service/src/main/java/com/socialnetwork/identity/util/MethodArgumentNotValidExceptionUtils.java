package com.socialnetwork.identity.util;

import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MethodArgumentNotValidExceptionUtils {
    static final String MIN_ATTRIBUTE = "min";
    static final String MAX_ATTRIBUTE = "max";

    public static String getMessage(MethodArgumentNotValidException e, String errorMessage) {
        var constraintViolation = e.getBindingResult()
                .getAllErrors().getFirst().unwrap(ConstraintViolation.class);
        var attributes = constraintViolation.getConstraintDescriptor().getAttributes();

        if (Objects.nonNull(attributes)) {
            var minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
            var maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));

            return errorMessage
                    .replace("{" + MIN_ATTRIBUTE + "}", minValue)
                    .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
        }

        return errorMessage;
    }
}
