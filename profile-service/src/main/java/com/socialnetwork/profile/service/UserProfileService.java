package com.socialnetwork.profile.service;

import com.socialnetwork.profile.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.profile.dto.response.UserProfileCreationResponseDTO;
import com.socialnetwork.profile.entity.UserProfileEntity;
import com.socialnetwork.profile.mapper.JsonMapper;
import com.socialnetwork.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;

    public UserProfileCreationResponseDTO create(UserProfileCreationRequestDTO requestDTO) {
        var userProfileEntity = JsonMapper.map(requestDTO, UserProfileEntity.class);
        if (Objects.isNull(userProfileEntity)) {
            throw new RuntimeException("Cannot parse data");
        }

        var userProfileCreatedEntity = userProfileRepository.save(userProfileEntity);
        return JsonMapper.map(userProfileCreatedEntity, UserProfileCreationResponseDTO.class);
    }
}
