package com.socialnetwork.notification.worker.kafka.consumer;

import com.socialnetwork.event.dto.NotificationDTO;
import com.socialnetwork.notification.appconst.NotificationChannel;
import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.service.EmailService;
import com.socialnetwork.notification.service.TelegramService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    @Autowired
    EmailService emailService;

    @Autowired
    TelegramService telegramService;

    @KafkaListener(topics = "${kafka.consumer.topics.notification}")
    public void notificationListener(NotificationDTO notificationDTO) {
        log.info("Receive data <- {}", notificationDTO);
        final var channel = NotificationChannel.of(notificationDTO.getChannel().toUpperCase());
        switch (channel) {
            case EMAIL -> emailService.sendEmail(EmailRequestDTO.builder()
                    .subject(notificationDTO.getSubject())
                    .htmlContent(notificationDTO.getBody())
                    .build());

            case TELEGRAM -> telegramService.sendMessage(notificationDTO.getBody());
        }
    }
}
