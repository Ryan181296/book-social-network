package com.socialnetwork.notification.service;

import com.socialnetwork.notification.dto.response.TelegramMessageResponseDTO;
import com.socialnetwork.notification.exception.CustomException;
import com.socialnetwork.notification.exception.ErrorCode;
import com.socialnetwork.notification.repository.client.telegram.TelegramClient;
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
public class TelegramService {
    @Autowired
    TelegramClient telegramClient;

    @NonFinal
    @Value("${clients.telegram.chat-id}")
    String chatId;

    public TelegramMessageResponseDTO sendMessage(String message) {
        try {
            var response = telegramClient.sendMessage(chatId, message);
            if (!response.getOk()) {
                throw new CustomException(ErrorCode.SEND_MESSAGE_TO_TELEGRAM_ERROR);
            }
            return response.getResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.SEND_MESSAGE_TO_TELEGRAM_ERROR);
        }
    }
}
