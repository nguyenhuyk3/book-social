package com.bs.notification.services;

import com.bs.notification.dto.requests.EmailRequest;
import com.bs.notification.dto.requests.SendEmailRequest;
import com.bs.notification.dto.requests.Sender;
import com.bs.notification.dto.responses.EmailResponse;
import com.bs.notification.exceptions.AppException;
import com.bs.notification.exceptions.ErrorCode;
import com.bs.notification.repositories.HttpClient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevoApikey}")
    @NonFinal
    String API_KEY;

    @Value("${notification.email.senderName}")
    @NonFinal
    String SENDER_NAME;

    @Value("${notification.email.senderEmail}")
    @NonFinal
    String SENDER_EMAIL;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest
                .builder()
                .sender(Sender
                        .builder()
                        .name(SENDER_NAME)
                        .email(SENDER_EMAIL)
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(API_KEY, emailRequest);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
