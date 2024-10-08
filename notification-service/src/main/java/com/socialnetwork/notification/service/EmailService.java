package com.socialnetwork.notification.service;

import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.dto.response.EmailResponseDTO;
import com.socialnetwork.notification.exception.CustomException;
import com.socialnetwork.notification.exception.ErrorCode;
import com.socialnetwork.notification.repository.client.brevo.BrevoClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    @Autowired
    BrevoClient brevoEmailClient;

    @NonFinal
    @Value("${clients.brevo.api-key}")
    String brevoApiKey;

    public EmailResponseDTO sendEmail(EmailRequestDTO requestDTO) {
        try {
            return brevoEmailClient.sendEmail(brevoApiKey, requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.SEND_EMAIL_ERROR);
        }
    }
}
