package com.socialnetwork.notification.worker.kafka.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    @KafkaListener(topics = "${kafka.consumer.topics.notification}")
    public void onNotificationListener(String message) {
        log.info("Receive email message <- {}", message);
    }
}
