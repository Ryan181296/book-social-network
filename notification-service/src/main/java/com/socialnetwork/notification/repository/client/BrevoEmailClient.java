package com.socialnetwork.notification.repository.client;

import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.dto.response.EmailResponseDTO;
import com.socialnetwork.notification.exception.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "${clients.brevo.names.email}",
        url = "${clients.brevo.url}",
        configuration = {FeignErrorDecoder.class})
public interface BrevoEmailClient {
    @PostMapping(value = "/v3/smtp/email", produces = {MediaType.APPLICATION_JSON_VALUE})
    EmailResponseDTO sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequestDTO requestDTO);
}
