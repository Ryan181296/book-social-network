package com.socialnetwork.identity.repository.client;

import com.socialnetwork.identity.config.AuthenticationRequestInterceptor;
import com.socialnetwork.identity.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.dto.response.UserProfileCreationResponseDTO;
import com.socialnetwork.identity.exception.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "profile-service",
        url = "${clients.profile.host}",
        path = "/api",
        configuration = {
                FeignErrorDecoder.class,
                AuthenticationRequestInterceptor.class
        })
public interface ProfileClient {
    @PostMapping(value = "${clients.profile.create-profile-path}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseObject<UserProfileCreationResponseDTO> createProfile(@RequestBody UserProfileCreationRequestDTO requestDTO);
}
