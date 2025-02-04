package com.demo.phonebook.contact.controller;

import com.demo.phonebook.contact.dto.ContactDto;
import com.demo.phonebook.contact.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDto>> findAll() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @PostMapping
    public ResponseEntity<ContactDto> addContact(@RequestBody @Valid ContactDto contactDto) {
        ContactDto savedContact = contactService.addContact(contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{contact_id}")
    public ResponseEntity<String>    editContact(@PathVariable @Min(1) Long contact_id,
                                            @RequestBody @Valid ContactDto contactDto) {
        contactService.editContact(contact_id, contactDto);
        return ResponseEntity.ok("Contact updated successfully.");
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<ContactDto> getContactByEmail(@PathVariable String email) {
        return ResponseEntity.ok(contactService.getContactByEmail(email));
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<ContactDto> getContactByPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(contactService.getContactByPhone(phoneNumber));
    }

    @DeleteMapping("/{contact_id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long contact_id){
        contactService.deleteContact(contact_id);
        return ResponseEntity.ok().build();
    }
}
