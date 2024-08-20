package com.socialnetwork.gateway.repository;

import com.socialnetwork.gateway.dto.request.TokenVerificationRequestDTO;
import com.socialnetwork.gateway.dto.response.ResponseObject;
import com.socialnetwork.gateway.dto.response.TokenVerificationResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/v1/auth/verify-token", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseObject<TokenVerificationResponseDTO>> verifyToken(@RequestBody TokenVerificationRequestDTO requestDTO);
}
