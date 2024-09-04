package com.socialnetwork.notification.repository.client.telegram;

import com.socialnetwork.notification.dto.response.TelegramMessageResponseDTO;
import com.socialnetwork.notification.dto.response.TelegramResponseObject;
import com.socialnetwork.notification.exception.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "telegram-client",
        url = "${clients.telegram.url}",
        configuration = {FeignErrorDecoder.class})
public interface TelegramClient {
    @GetMapping(value = "/sendMessage", consumes = {MediaType.APPLICATION_JSON_VALUE})
    TelegramResponseObject<TelegramMessageResponseDTO> sendMessage(@RequestParam("chat_id") String chatId,
                                                                   @RequestParam("text") String message);
}
