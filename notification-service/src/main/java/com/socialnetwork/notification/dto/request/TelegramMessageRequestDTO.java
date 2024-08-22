package com.socialnetwork.notification.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramMessageRequestDTO {
    @Size(max = 4096, message = "TELEGRAM_MESSAGE_INVALID")
    String message;
}
