package com.socialnetwork.profile.repository.clients;

import com.socialnetwork.profile.dto.request.ExchangeTokenRequestDTO;
import com.socialnetwork.profile.dto.request.UserCreationRequestDTO;
import com.socialnetwork.profile.dto.response.ExchangeTokenResponseDTO;
import com.socialnetwork.profile.exception.FeignErrorDecoder;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "identity-client",
        url = "${clients.identity.url}",
        configuration = {FeignErrorDecoder.class})
public interface IdentityClient {
    @PostMapping(value = "/realms/booksn/protocol/openid-connect/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ExchangeTokenResponseDTO exchangeToken(@QueryMap ExchangeTokenRequestDTO requestDTO);

    @PostMapping(value = "/admin/realms/booksn/users", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<?> createUser(@RequestHeader(value = "Authorization") String accessToken,
                                 @RequestBody UserCreationRequestDTO requestDTO);
}
