package com.socialnetwork.gateway.service;

import com.socialnetwork.gateway.dto.request.TokenVerificationRequestDTO;
import com.socialnetwork.gateway.dto.response.ResponseObject;
import com.socialnetwork.gateway.dto.response.TokenVerificationResponseDTO;
import com.socialnetwork.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    @Autowired
    IdentityClient identityClient;

    public Mono<ResponseObject<TokenVerificationResponseDTO>> verifyToken(String token) {
        return identityClient.verifyToken(TokenVerificationRequestDTO.builder()
                .token(token).build());
    }
}
