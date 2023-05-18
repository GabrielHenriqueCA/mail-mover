package com.ms.email.consumers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.services.impl.EmailServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private static final Logger log = LogManager.getLogger(EmailConsumer.class);
    private final EmailServiceImpl emailService;

    public EmailConsumer(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @CircuitBreaker(name = "email-service-circuit-breaker", fallbackMethod = "fallbackMethod")
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto) {
        emailService.sendEmailToRecipient(emailDto);
    }

    public void fallbackMethod(EmailDto emailDto, Exception ex) {
        log.error("Circuit is open. Fallback is running. EmailDto: {}, Exception: {}", emailDto, ex.getMessage());
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}