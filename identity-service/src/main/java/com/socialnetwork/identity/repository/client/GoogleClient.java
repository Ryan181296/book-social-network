package com.socialnetwork.identity.repository.client;

import com.socialnetwork.identity.dto.request.GoogleAuthenticationRequestDTO;
import com.socialnetwork.identity.dto.response.GoogleAuthenticationResponseDTO;
import com.socialnetwork.identity.dto.response.GoogleUserInfoResponseDTO;
import com.socialnetwork.identity.exception.FeignErrorDecoder;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "google-client",
        url = "${clients.google.url}",
        configuration = FeignErrorDecoder.class)
public interface GoogleClient {
    @PostMapping(value = "/token", produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    GoogleAuthenticationResponseDTO exchangeAuthorizationCode(@QueryMap GoogleAuthenticationRequestDTO requestDTO);

    @GetMapping(value = "/oauth2/v1/userInfo", produces = {MediaType.APPLICATION_JSON_VALUE})
    GoogleUserInfoResponseDTO getUserInfo(@RequestParam(value = "alt") String alt,
                                          @RequestParam(value = "access_token") String access_token);
}
