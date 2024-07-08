package com.example.demo;

import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRORS = "errors";
    private static final String ERROR_LOG_MESSAGE = "Exception type: {} with translated message: {}";

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        if (ex.getBindingResult().hasFieldErrors()) {
            return handleFieldErrors(ex, headers, status, request);
        }
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        var validationError = ValidationErrorResponse.from(ex);
        return badRequestProblemDetail(validationError);
    }

    private ResponseEntity<Object> handleFieldErrors(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var errors = ValidationErrorResponse.from(ex.getBindingResult());
        var problemDetail = badRequestProblemDetail(errors);
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private ProblemDetail badRequestProblemDetail(List<ValidationErrorResponse> validationError) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
               ""
        );
        problemDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        problemDetail.setProperty(ERRORS, validationError);
        return problemDetail;
    }

}