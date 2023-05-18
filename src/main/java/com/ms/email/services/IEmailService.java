package com.ms.email.services;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.EmailNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IEmailService {

    /**
     * Sends an email to the recipient based on the data provided in the EmailDto object.
     *
     * @param emailDto The EmailDto object containing the email data to be sent.
     * @return ResponseEntity containing a success or error message.
     */
    public ResponseEntity<String> sendEmailToRecipient(EmailDto emailDto);

    /**
     * Retrieves all emails in a pageable structure based on the provided Pageable object.
     *
     * @param pageable The Pageable object for pagination and sorting of the results.
     * @return ResponseEntity containing the page of emails in Page<EmailDto> format.
     */
    public ResponseEntity<Page<EmailDto>> getAllEmails(Pageable pageable);

    /**
     * Retrieves an email based on the provided ID.
     *
     * @param id The ID of the email to be retrieved.
     * @return ResponseEntity containing the found email in EmailDto format, if it exists.
     * @throws EmailNotFoundException Exception thrown when the email is not found.
     */
    public ResponseEntity<EmailDto> getEmailById(UUID id) throws EmailNotFoundException;
}