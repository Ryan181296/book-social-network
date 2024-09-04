package com.socialnetwork.profile.controller;

import com.socialnetwork.profile.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.profile.dto.response.ResponseObject;
import com.socialnetwork.profile.dto.response.UserProfileCreationResponseDTO;
import com.socialnetwork.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/profile")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @PostMapping(value = "/registration", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<UserProfileCreationResponseDTO> register(@RequestBody UserProfileCreationRequestDTO requestDTO) {
        return ResponseObject.<UserProfileCreationResponseDTO>builder()
                .result(userProfileService.register(requestDTO))
                .build();
    }
}
