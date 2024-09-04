package com.socialnetwork.notification.repository.client.brevo;

import com.socialnetwork.notification.dto.request.EmailRequestDTO;
import com.socialnetwork.notification.dto.response.EmailResponseDTO;
import com.socialnetwork.notification.exception.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "brevo-client",
        url = "${clients.brevo.url}",
        configuration = {FeignErrorDecoder.class})
public interface BrevoClient {
    @PostMapping(value = "/v3/smtp/email", consumes = {MediaType.APPLICATION_JSON_VALUE})
    EmailResponseDTO sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequestDTO requestDTO);
}
