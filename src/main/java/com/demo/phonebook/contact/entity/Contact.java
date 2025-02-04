package com.demo.phonebook.contact.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import lombok.*;

@Entity(name = "Contact")
@Table(name = "contact")
@Getter
@Setter
@ToString
@NoArgsConstructor // Adds a default constructor
@AllArgsConstructor // Adds a constructor with all fields
public class Contact {

    @Id
    @SequenceGenerator(
            name = "contact_id_sequence",
            sequenceName = "contact_id_sq",
            initialValue = 100,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contact_id_sequence"
    )
    private Long id;

    @Column(nullable = true)
    private String name;

    @Column(
            name = "email",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true
    )
    private String email;

    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "Invalid phone number format")
    @Column(
            name = "phone_number",
            nullable = false,
            columnDefinition = "VARCHAR(20)",
            unique = true
    )
    private String phoneNumber;

    private LocalDate dob;

    @Column
    private String company;

    @Column
    private String job_title;
}
