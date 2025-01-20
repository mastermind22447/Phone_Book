package com.demo.phonebook.contact;

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
    @Query(value = "SELECT c FROM Contact c WHERE c.name = ?1 AND c.email = ?2")
    List<Contact> findAllByNameAndEmail(String name, String email);


    Optional<Contact> findByPhoneNumber(String phoneNumber);
    @Query(value = "SELECT c FROM Contact c WHERE c.name = ?1 AND c.phoneNumber = ?2")
    List<Contact> findAllByNameAndPhoneNumber(String name, String phoneNumber);
}