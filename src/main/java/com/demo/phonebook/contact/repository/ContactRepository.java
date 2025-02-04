package com.demo.phonebook.contact.repository;

import com.demo.phonebook.contact.entity.Contact;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);

    Optional<Contact> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);  // ✅ Check if email exists

    boolean existsByPhoneNumber(String phoneNumber);  // ✅ Check if phone number exists
}