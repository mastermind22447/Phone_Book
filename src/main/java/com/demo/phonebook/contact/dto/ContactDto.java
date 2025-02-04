package com.demo.phonebook.contact.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ContactDto(
        Long id,

        @NotBlank
        String name,

        @Email(message = "{error.invalid.email}")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "{error.invalid.phone}")
        String phoneNumber,

        LocalDate dob,

        String company,

        String job_title
) {}

