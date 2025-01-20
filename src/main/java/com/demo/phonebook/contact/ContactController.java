package com.demo.phonebook.contact;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contacts")
public class ContactController  {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    List<Contact> getContacts() {
        return contactService.getContacts();}

    @PostMapping
    void addContact(@RequestBody @Valid ContactDto contactDto) {

        contactService.addContact(contactDto);
    }
    @GetMapping("/email/{email}")
    Contact getContactByEmail(@PathVariable String email) {

        return contactService.getContactByEmail(email);
    }

    @GetMapping("/phone/{phoneNumber}")
    Contact getContactByPhoneNumber(@PathVariable String phoneNumber) {

        return contactService.getContactByPhoneNumber(phoneNumber);
    }

    @PutMapping(path = "/{contact_id}")
    void editContact(@PathVariable("contact_id") Long contactId,
                      @RequestBody ContactDto contactDto) {
        contactService.editContact(contactId, contactDto);
    }

    @DeleteMapping(path = "/{contact_id}")
    void deleteContact(@PathVariable("contact_id") Long contactId){

        contactService.deleteContact(contactId);
    }
}