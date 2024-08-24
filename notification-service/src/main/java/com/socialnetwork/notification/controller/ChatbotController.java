package com.socialnetwork.notification.controller;

import com.socialnetwork.notification.dto.request.TelegramMessageRequestDTO;
import com.socialnetwork.notification.dto.response.ResponseObject;
import com.socialnetwork.notification.dto.response.TelegramMessageResponseDTO;
import com.socialnetwork.notification.service.TelegramService;
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
@RequestMapping(value = "/v1/chatbot")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatbotController {
    @Autowired
    TelegramService telegramService;

    @PostMapping(value = "/send-message", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<TelegramMessageResponseDTO> sendMessage(@Valid @RequestBody TelegramMessageRequestDTO requestDTO) {
        return ResponseObject.<TelegramMessageResponseDTO>builder()
                .result(telegramService.sendMessage(requestDTO.getMessage()))
                .build();
    }
}
