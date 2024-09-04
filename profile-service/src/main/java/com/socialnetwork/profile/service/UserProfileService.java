package com.socialnetwork.profile.service;

import com.socialnetwork.profile.dto.request.ExchangeTokenRequestDTO;
import com.socialnetwork.profile.dto.request.UserCreationRequestDTO;
import com.socialnetwork.profile.dto.request.UserProfileCreationRequestDTO;
import com.socialnetwork.profile.dto.response.UserProfileCreationResponseDTO;
import com.socialnetwork.profile.entity.UserProfileEntity;
import com.socialnetwork.profile.exception.CustomException;
import com.socialnetwork.profile.exception.ErrorCode;
import com.socialnetwork.profile.mapper.JsonMapper;
import com.socialnetwork.profile.repository.UserProfileRepository;
import com.socialnetwork.profile.repository.clients.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    IdentityClient identityClient;

    @NonFinal
    @Value("${clients.identity.client-id}")
    String identityClientId;

    @NonFinal
    @Value("${clients.identity.client-secret}")
    String identityClientSecret;

    public UserProfileCreationResponseDTO register(UserProfileCreationRequestDTO requestDTO) {
        var userProfileEntity = JsonMapper.map(requestDTO, UserProfileEntity.class);
        if (Objects.isNull(userProfileEntity)) {
            throw new CustomException(ErrorCode.CANNOT_PARSE_DATA);
        }

        // Exchange client token with Keycloak
        var exchangeTokenResponse = identityClient.exchangeToken(ExchangeTokenRequestDTO.builder()
                .clientId(identityClientId)
                .clientSecret(identityClientSecret)
                .scope("openid")
                .grantType("client_credentials")
                .build());
        var accessToken = exchangeTokenResponse.getTokenType() + " " + exchangeTokenResponse.getAccessToken();

        // Create user with Keycloak
        var userCreationResponse = identityClient.createUser(
                accessToken,
                UserCreationRequestDTO.builder()
                        .username(requestDTO.getUsername())
                        .firstName(requestDTO.getFirstName())
                        .lastName(requestDTO.getLastName())
                        .email(requestDTO.getEmail())
                        .enabled(true)
                        .credentials(List.of(UserCreationRequestDTO.Credential.builder()
                                .type("password")
                                .value(requestDTO.getPassword())
                                .temporary(false)
                                .build()))
                        .build());

        userProfileEntity.setUserId(extractUserId(userCreationResponse));
        return JsonMapper.map(userProfileEntity, UserProfileCreationResponseDTO.class);
    }

    private String extractUserId(ResponseEntity<?> responseEntity) {
        var locations = responseEntity.getHeaders().get("Location");
        if (Objects.nonNull(locations)) {
            var values = locations.getFirst().split("/");
            return values[values.length - 1];
        }
        throw new CustomException(ErrorCode.COMMON_MESSAGE);
    }

    public UserProfileCreationResponseDTO create(UserProfileCreationRequestDTO requestDTO) {
        var userProfileEntity = JsonMapper.map(requestDTO, UserProfileEntity.class);
        if (Objects.isNull(userProfileEntity)) {
            throw new CustomException(ErrorCode.CANNOT_PARSE_DATA);
        }

        var userProfileCreatedEntity = userProfileRepository.save(userProfileEntity);
        return JsonMapper.map(userProfileCreatedEntity, UserProfileCreationResponseDTO.class);
    }
}
