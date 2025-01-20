package com.demo.phonebook.contact;

import jakarta.validation.constraints.*;



public record ContactDto(
        Long id,
        @NotBlank(message = "name field should not be empty")
        String name,
        @Email
        String email,
        @NotBlank
        String phoneNumber

) {
}