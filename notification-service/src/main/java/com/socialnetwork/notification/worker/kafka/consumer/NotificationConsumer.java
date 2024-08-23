package com.socialnetwork.notification.worker.kafka.consumer;

import com.socialnetwork.event.dto.NotificationDTO;
import com.socialnetwork.notification.appconst.NotificationChannel;
import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.service.EmailService;
import com.socialnetwork.notification.service.TelegramService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    @NonFinal
    @Value("${clients.brevo.email}")
    String senderEmail;

    @NonFinal
    @Value("${clients.brevo.name}")
    String senderName;

    @Autowired
    EmailService emailService;

    @Autowired
    TelegramService telegramService;

    @KafkaListener(topics = "${kafka.consumer.topics.notification}")
    public void notificationListener(NotificationDTO notificationDTO) {
        log.info("Receive data <- {}", notificationDTO);
        try {
            final var channel = NotificationChannel.of(notificationDTO.getChannel().toUpperCase());
            switch (channel) {
                case EMAIL -> emailService.sendEmail(EmailRequestDTO.builder()
                        .sender(EmailRequestDTO.Sender.builder()
                                .email(senderEmail)
                                .name(senderName)
                                .build())
                        .to(List.of(EmailRequestDTO.Recipient.builder()
                                .email(notificationDTO.getRecipient())
                                .build()))
                        .subject(notificationDTO.getSubject())
                        .htmlContent(notificationDTO.getBody())
                        .build());

                case TELEGRAM -> telegramService.sendMessage(notificationDTO.getBody());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
