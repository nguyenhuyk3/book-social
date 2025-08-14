package com.bs.notification.controllers;

import com.bs.event.dto.NotificationEvent;
import com.bs.notification.dto.requests.Recipient;
import com.bs.notification.dto.requests.SendEmailRequest;
import com.bs.notification.services.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        emailService.sendEmail(SendEmailRequest
                .builder()
                .to(Recipient
                        .builder()
                        .email(message.getRecipient())
                        .build())
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build());
    }
}