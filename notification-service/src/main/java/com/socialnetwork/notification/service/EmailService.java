package com.socialnetwork.notification.service;

import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.dto.response.EmailResponseDTO;
import com.socialnetwork.notification.exception.CustomException;
import com.socialnetwork.notification.exception.ErrorCode;
import com.socialnetwork.notification.repository.client.BrevoEmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    @Autowired
    BrevoEmailClient brevoEmailClient;

    @NonFinal
    @Value("${clients.brevo.api-key}")
    String brevoAPIKey;

    public EmailResponseDTO sendEmail(EmailRequestDTO requestDTO) {
        try {
            return brevoEmailClient.sendEmail(brevoAPIKey, requestDTO);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SEND_EMAIL_ERROR);
        }
    }
}
