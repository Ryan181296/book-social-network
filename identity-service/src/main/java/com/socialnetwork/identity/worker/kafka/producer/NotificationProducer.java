package com.socialnetwork.identity.worker.kafka.producer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationProducer {
    @NonFinal
    @Value("${kafka.producer.topics.notification}")
    String notificationTopic;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void sendData() {
        kafkaTemplate.send(notificationTopic, "notification");
    }
}
