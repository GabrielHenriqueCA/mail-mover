package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.EmailNotFoundException;
import com.ms.email.services.impl.EmailServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/mailMover")
public class EmailController {

    private static final Logger log = LogManager.getLogger(EmailController.class);

    final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sending-email")
    public ResponseEntity<String> sendingEmail(@RequestBody @Valid final EmailDto emailDto) {
        log.info("Received request to send email: {}", emailDto);
        return emailService.sendEmailToRecipient(emailDto);
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<EmailDto>> getEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) final Pageable pageable) {
        log.info("Received request to get emails");
        return emailService.getAllEmails(pageable);
    }

    @GetMapping("/emails/{emailId}")
    public ResponseEntity<EmailDto> getEmailById(@PathVariable(value = "emailId") final UUID id) throws EmailNotFoundException {
        log.info("Received request to get email by ID: {}", id);
        return emailService.getEmailById(id);
    }
}