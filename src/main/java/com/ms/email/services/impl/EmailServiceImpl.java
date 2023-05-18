package com.ms.email.services.impl;

import com.ms.email.converters.EmailConverter;
import com.ms.email.dtos.EmailDto;
import com.ms.email.enums.StatusEmail;
import com.ms.email.exceptions.EmailNotFoundException;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import com.ms.email.services.IEmailService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailServiceImpl implements IEmailService {

    private static final Logger log = LogManager.getLogger(EmailServiceImpl.class);
    private final EmailConverter emailConverter;
    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;


    public EmailServiceImpl(EmailConverter emailConverter,
                            EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailConverter = emailConverter;
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    public ResponseEntity<String> sendEmailToRecipient(@Valid EmailDto emailDto) {
        try {
            EmailModel emailModel = emailConverter.emailDtoToModel(emailDto);
            emailModel.setSendDateEmail(LocalDateTime.now());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
            emailRepository.save(emailModel);

            log.info("Email successfully sent: {}", emailModel.getEmailId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Email successfully sent!");

        } catch (MailException e) {
            EmailModel emailModel = emailConverter.emailDtoToModel(emailDto);
            emailModel.setStatusEmail(StatusEmail.ERROR);
            emailRepository.save(emailModel);

            log.error("Error sending email: {}", emailModel.getEmailId(), e);
            return ResponseEntity.internalServerError().body("Error sending email, please try again in a moment");
        }
    }

    public ResponseEntity<Page<EmailDto>> getAllEmails(Pageable pageable) {
        Page<EmailModel> emailModel = emailRepository.findAll(pageable);
        Page<EmailDto> listEmailDto = emailConverter.emailModelPageToDtoPage(emailModel);
        return ResponseEntity.ok(listEmailDto);
    }

    public ResponseEntity<EmailDto> getEmailById(UUID id) throws EmailNotFoundException {
        EmailModel emailModel = emailRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException(String.format("Email with id %s not found", id)));

        EmailDto emailDto = emailConverter.emailModelToDto(emailModel);
        return ResponseEntity.ok(emailDto);
    }
}