package com.example.demo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Getter
public class ValidationErrorResponse {

    private final String path;

    private final Set<String> messages;

    private ValidationErrorResponse(String path, Set<String> messages) {
        this.path = path;
        this.messages = Set.copyOf(messages);
    }

    public static List<ValidationErrorResponse> from(BindingResult bindingResult) {
        if (Objects.isNull(bindingResult)) {
            return List.of();
        }
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField))
                .entrySet()
                .stream()
                .map(entry -> map(entry.getKey(), entry.getValue()))
                .toList();
    }

    public static List<ValidationErrorResponse> from(ConstraintViolationException ex) {
        if (Objects.isNull(ex)) {
            return List.of();
        }
        return ex.getConstraintViolations().stream().map(ValidationErrorResponse::map).toList();
    }

    private static ValidationErrorResponse map(String key, List<FieldError> fieldErrors) {
        var errors = extractErrorMessages(fieldErrors);
        return new ValidationErrorResponse(key, errors);
    }

    private static ValidationErrorResponse map(ConstraintViolation<?> violation) {
        String fieldName = null;
        for (Path.Node node : violation.getPropertyPath()) {
            fieldName = node.getName();
        }
        var errors = Set.of(violation.getMessage());
        return new ValidationErrorResponse(fieldName, errors);
    }

    private static Set<String> extractErrorMessages(List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("path='" + path + "'")
                .add("messages=" + messages)
                .toString();
    }
}
