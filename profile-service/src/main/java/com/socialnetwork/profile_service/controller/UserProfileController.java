package com.socialnetwork.profile_service.controller;

import com.socialnetwork.profile_service.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.profile_service.dto.response.UserProfileCreationResponseDTO;
import com.socialnetwork.profile_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/profile")
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @PostMapping
    public UserProfileCreationResponseDTO create(@RequestBody UserProfileCreationRequestDTO requestDTO) {
        return userProfileService.create(requestDTO);
    }
}
