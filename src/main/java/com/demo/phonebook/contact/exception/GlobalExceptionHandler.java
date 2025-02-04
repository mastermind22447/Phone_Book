package com.demo.phonebook.contact.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        errorMap.put("error", "Validation failed");
        errorMap.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("details", ex.getMessage());
        errorResponse.put("error", "Requested entity not found");
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("details", ex.getMessage());
        response.put("error", "An unexpected error occurred");
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateKeyException(DataIntegrityViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();

        String detailedMessage = ex.getMostSpecificCause().getMessage();

        String cleanedMessage = extractDuplicateKeyMessage(detailedMessage);

            // Determine the appropriate localized message based on the field
            String userFriendlyMessage;
            if (cleanedMessage.contains("email")) {
                userFriendlyMessage = messageSource.getMessage("error.duplicate.email", null, "Email already exists.", locale);
            } else if (cleanedMessage.contains("phone_number")) {
                userFriendlyMessage = messageSource.getMessage("error.duplicate.phone", null, "Phone number already exists.", locale);
            } else {
                userFriendlyMessage = messageSource.getMessage("error.duplicate.general", null, "Duplicate value error.", locale);
            }

        errorMap.put("error", userFriendlyMessage);
        errorMap.put("status", String.valueOf(HttpStatus.CONFLICT.value()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
    }

    private String extractDuplicateKeyMessage(String rawMessage) {
        if (rawMessage == null) {
            return "Duplicate entry error.";
        }

        // Check if the message contains constraint violation details
        if (rawMessage.contains("Detail: Key (")) {
            // Extract everything after "Detail: Key ("
            String keyDetails = rawMessage.substring(rawMessage.indexOf("Detail: Key (") + "Detail: Key (".length());

            // Find the closing bracket
            int endIndex = keyDetails.indexOf(")");
            if (endIndex != -1) {
                return keyDetails.substring(0, endIndex).trim();
            }
        }

        return "Duplicate entry error.";
    }

}
