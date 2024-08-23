package com.socialnetwork.identity.worker.kafka.producer;

import com.socialnetwork.event.dto.NotificationDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationProducer {
    @NonFinal
    @Value("${kafka.producer.topics.notification}")
    String notificationTopic;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    public void send(NotificationDTO notificationDTO) {
        log.info("Send data -> {}", notificationDTO);
        kafkaTemplate.send(notificationTopic, notificationDTO);
    }
}
