package com.socialnetwork.identity.repository.client;

import com.socialnetwork.identity.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.identity.dto.response.UserProfileCreationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile}")
public interface ProfileClient {
    @PostMapping(value = "/v1/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileCreationResponseDTO createProfile(@RequestBody UserProfileCreationRequestDTO requestDTO);
}
