package com.socialnetwork.notification.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramMessageResponseDTO {
    @SerializedName("message_id")
    String messageId;
    Receiver from;
    Sender chat;
    Long date;
    String text;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Receiver {
        String id;

        @SerializedName("is_bot")
        Boolean isBot;

        @SerializedName("first_name")
        String firstName;
        String username;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Sender {
        String id;

        @SerializedName("first_name")
        String firstName;
        String username;
        String type;
    }
}
