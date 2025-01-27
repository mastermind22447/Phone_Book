package com.demo.phonebook.contact;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record ContactDto(
        Long id,
        @NotBlank(message = "name field should not be empty")
        String name,
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        LocalDate dob,
        String company,
        String job_title

) {
}