package com.demo.phonebook.contact.service;

import com.demo.phonebook.contact.dto.ContactDto;
import com.demo.phonebook.contact.entity.Contact;
import com.demo.phonebook.contact.mapper.ContactMapper;
import com.demo.phonebook.contact.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final MessageSource messageSource;

    public List<ContactDto> findAll() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContactDto addContact(ContactDto contactDto) {
        Contact contact = contactMapper.toEntity(contactDto);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDto(savedContact);
}

    public ContactDto getContactByEmail(String email) {
        Contact contact = contactRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(messageSource.getMessage("contact.email.not.found",
                        new Object[]{email}, LocaleContextHolder.getLocale()))
                );
        return contactMapper.toDto(contact);
    }

    public ContactDto getContactByPhone(String phoneNumber) {
        Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException(messageSource.getMessage("contact.phone.not.found",
                        new Object[]{phoneNumber}, LocaleContextHolder.getLocale()))
                );
        return contactMapper.toDto(contact);
    }

    @Transactional
    public void editContact(Long contactId, ContactDto contactDto) {
        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("contact.id.not.found",
                        new Object[]{contactId},LocaleContextHolder.getLocale()))
                );

        if (contactRepository.existsByEmail(contactDto.email()) && !existingContact.getEmail()
                .equals(contactDto.email())) {
            throw new IllegalArgumentException(messageSource.getMessage("error.duplicate.email",
                    new Object[]{contactDto.email()},LocaleContextHolder.getLocale())
            );
        }
        if (contactRepository.existsByPhoneNumber(contactDto.phoneNumber()) && !existingContact
                .getPhoneNumber().equals(contactDto.phoneNumber())) {
            throw new IllegalArgumentException(messageSource.getMessage("error.duplicate.phone",
                    new Object[]{contactDto.phoneNumber()},LocaleContextHolder.getLocale())
            );
        }

        existingContact.setName(contactDto.name());
        existingContact.setEmail(contactDto.email());
        existingContact.setPhoneNumber(contactDto.phoneNumber());
        existingContact.setDob(contactDto.dob());
        existingContact.setCompany(contactDto.company());
        existingContact.setJob_title(contactDto.job_title());

        contactRepository.save(existingContact);
    }

    @Transactional
    public void deleteContact(Long contactId) {
        if (!contactRepository.existsById(contactId)) {
            throw new EntityNotFoundException(messageSource.getMessage("contact.id.not.found",
                    new Object[]{contactId}, LocaleContextHolder.getLocale())
            );
        }
        contactRepository.deleteById(contactId);
    }
}
