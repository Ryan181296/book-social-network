package com.socialnetwork.post.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDateFormatUtil {
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static final Map<Long, Function<Date, String>> strategyMap = new LinkedHashMap<>();

    static {
        strategyMap.put(60L, PostDateFormatUtil::formatInSeconds);
        strategyMap.put(3600L, PostDateFormatUtil::formatInMinutes);
        strategyMap.put(8640L, PostDateFormatUtil::formatInHours);
        strategyMap.put(Long.MAX_VALUE, PostDateFormatUtil::formatDate);
    }

    public static String format(Date date) {
        var seconds = ChronoUnit.SECONDS.between(date.toInstant(), Instant.now());
        var entry = strategyMap.entrySet().stream().filter(s -> seconds < s.getKey()).findFirst();

        return entry.isPresent() ? entry.get().getValue().apply(date) : "";
    }

    private static String formatInSeconds(Date date) {
        var seconds = ChronoUnit.SECONDS.between(date.toInstant(), Instant.now());
        return seconds + " seconds";
    }

    private static String formatInMinutes(Date date) {
        var minutes = ChronoUnit.MINUTES.between(date.toInstant(), Instant.now());
        return minutes + " minutes";
    }

    private static String formatInHours(Date date) {
        var hours = ChronoUnit.HOURS.between(date.toInstant(), Instant.now());
        return hours + " hours";
    }

    private static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
