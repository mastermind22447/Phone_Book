package com.demo.phonebook.contact;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;


@Entity(name = "Contact")
@Table(name = "contact")
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

    public Contact(){

    }

    public Contact(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Contact(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
