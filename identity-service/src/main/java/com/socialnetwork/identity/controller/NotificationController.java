package com.socialnetwork.identity.controller;

import com.socialnetwork.event.dto.NotificationDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.worker.kafka.producer.NotificationProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "v1/notification")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    @Autowired
    NotificationProducer notificationProducer;

    @PostMapping
    public ResponseObject<?> send(@RequestBody NotificationDTO notificationDTO) {
        notificationProducer.send(notificationDTO);
        return ResponseObject.builder().build();
    }
}
