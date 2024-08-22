package com.socialnetwork.identity.service;

import com.socialnetwork.identity.dto.request.UserCreationRequestDTO;
import com.socialnetwork.identity.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.identity.dto.request.UserUpdateRequestDTO;
import com.socialnetwork.identity.dto.response.CommonUserResponseDTO;
import com.socialnetwork.identity.entity.UserEntity;
import com.socialnetwork.identity.exception.CustomException;
import com.socialnetwork.identity.exception.ErrorCode;
import com.socialnetwork.identity.mapper.JsonMapper;
import com.socialnetwork.identity.repository.RoleRepository;
import com.socialnetwork.identity.repository.UserRepository;
import com.socialnetwork.identity.repository.client.ProfileClient;
import com.socialnetwork.identity.worker.kafka.producer.NotificationProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProfileClient profileClient;

    @Autowired
    NotificationProducer notificationProducer;


    public CommonUserResponseDTO create(UserCreationRequestDTO requestDTO) {
        var userEntity = JsonMapper.map(requestDTO, UserEntity.class);
        if (Objects.isNull(userEntity)) {
            throw new CustomException(ErrorCode.CANNOT_PARSE_DATA);
        }

        // encode password.
        var passwordEncoder = new BCryptPasswordEncoder(10);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        try {
            var userCreated = userRepository.save(userEntity);

            // create profile.
            var profileRequestDTO = JsonMapper.map(userCreated, UserProfileCreationRequestDTO.class);
            if (Objects.nonNull(profileRequestDTO)) {
                profileRequestDTO.setUserId(userCreated.getId());
                var responseData = profileClient.createProfile(profileRequestDTO);
                // TODO: handle business logic based on responseData here!
            }
            return JsonMapper.map(userCreated, CommonUserResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.USER_EXISTED);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CREATE_USER_PROFILE_ERROR);
        }
    }

    public CommonUserResponseDTO getById(String userId) {
        var userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return JsonMapper.map(userEntity, CommonUserResponseDTO.class);
    }

    public CommonUserResponseDTO[] getAll() {
        var allUserEntities = userRepository.findAll();
        return JsonMapper.map(allUserEntities, CommonUserResponseDTO[].class);
    }

    public CommonUserResponseDTO getProfile() {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();

        var userEntity = userRepository.findByUsername(name).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return JsonMapper.map(userEntity, CommonUserResponseDTO.class);
    }

    public CommonUserResponseDTO update(UserUpdateRequestDTO requestDTO) {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        var userUpdateEntity = userRepository.findByUsername(name).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userUpdateEntity.setFirstName(requestDTO.getFirstName());
        userUpdateEntity.setLastName(requestDTO.getLastName());

        var userUpdatedEntity = userRepository.save(userUpdateEntity);
        return JsonMapper.map(userUpdatedEntity, CommonUserResponseDTO.class);
    }

    public CommonUserResponseDTO updateById(String userId, UserUpdateRequestDTO requestDTO) {
        var userUpdateEntity = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userUpdateEntity.setFirstName(requestDTO.getFirstName());
        userUpdateEntity.setLastName(requestDTO.getLastName());

        var roleEntities = roleRepository.findAllByName(requestDTO.getRoleNames());
        userUpdateEntity.setRoles(new HashSet<>(roleEntities));

        var userUpdatedEntity = userRepository.save(userUpdateEntity);
        return JsonMapper.map(userUpdatedEntity, CommonUserResponseDTO.class);
    }
}
