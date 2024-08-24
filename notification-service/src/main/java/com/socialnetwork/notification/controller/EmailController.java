package com.socialnetwork.notification.controller;

import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.dto.response.EmailResponseDTO;
import com.socialnetwork.notification.dto.response.ResponseObject;
import com.socialnetwork.notification.service.EmailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<EmailResponseDTO> sendEmail(@Valid @RequestBody EmailRequestDTO requestDTO) {
        return ResponseObject.<EmailResponseDTO>builder()
                .result(emailService.sendEmail(requestDTO))
                .build();
    }
}
