package com.socialnetwork.identity.repository.client;

import com.socialnetwork.identity.dto.request.GoogleAuthenticationRequestDTO;
import com.socialnetwork.identity.dto.response.GoogleAuthenticationResponseDTO;
import com.socialnetwork.identity.exception.FeignErrorDecoder;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        url = "${clients.google.url}",
        configuration = FeignErrorDecoder.class)
public interface GoogleClient {
    @PostMapping(value = "/token", produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    GoogleAuthenticationResponseDTO login(@QueryMap GoogleAuthenticationRequestDTO requestDTO);
}
