package com.socialnetwork.notification.appconst;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum NotificationChannel {
    EMAIL("EMAIL"),
    TELEGRAM("TELEGRAM"),
    UNKNOWN("UNKNOWN");

    String value;

    public static NotificationChannel of(String value) {
        return Arrays.stream(NotificationChannel.values())
                .filter(s -> s.value.equals(value)).findFirst().orElse(NotificationChannel.UNKNOWN);
    }
}
