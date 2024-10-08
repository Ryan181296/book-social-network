package com.socialnetwork.profile.controller;

import com.socialnetwork.profile.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.profile.dto.response.ResponseObject;
import com.socialnetwork.profile.dto.response.UserProfileCreationResponseDTO;
import com.socialnetwork.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/internal-profile")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @PostMapping
    public ResponseObject<UserProfileCreationResponseDTO> create(@RequestBody UserProfileCreationRequestDTO requestDTO) {
        return ResponseObject.<UserProfileCreationResponseDTO>builder()
                .result(userProfileService.create(requestDTO))
                .build();
    }
}
