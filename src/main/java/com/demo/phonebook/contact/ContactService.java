package com.demo.phonebook.contact;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContactService {
    private final ContactRepository contactRepository;


    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }
    public List<Contact> getContacts(){
        return this.contactRepository.findAll();

    }

    public Contact getContactByEmail(String email) {
        return this.contactRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("Contact with email " + email + " not found.")
                );
    }

    public Contact getContactByPhoneNumber(String phone_number){
        return this.contactRepository
                .findByPhoneNumber(phone_number)
                .orElseThrow(
                        () -> new RuntimeException("Contact with this phone number " + phone_number + " not found.")
        );
    }


    @Transactional
    public void addContact(ContactDto contactDto) {
        Contact contact = new Contact(
                contactDto.name(),
                contactDto.email(),
                contactDto.phoneNumber(),
                contactDto.dob(),
                contactDto.company(),
                contactDto.job_title()

        );
        this.contactRepository.save(contact);
    }
    @Transactional
    public void editContact(Long contactId, ContactDto contactDto) {
        Contact oldContact = contactRepository
                .findById(contactId)
                .orElseThrow(
                        () -> new RuntimeException("Contact with id " + contactId + " not found.")
                );
        oldContact.setName(contactDto.name());
        oldContact.setEmail(contactDto.email());
        oldContact.setPhoneNumber(contactDto.phoneNumber());
        oldContact.setDob(contactDto.dob());
        oldContact.setCompany(contactDto.company());
        oldContact.setJob_title(contactDto.job_title());

    }

    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);

    }
}
