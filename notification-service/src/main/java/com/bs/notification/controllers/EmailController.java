package com.bs.notification.controllers;



import com.bs.notification.dto.requests.SendEmailRequest;
import com.bs.notification.dto.responses.ApiResponse;
import com.bs.notification.dto.responses.EmailResponse;
import com.bs.notification.services.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request){
        return ApiResponse
                .<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .build();
    }
}
