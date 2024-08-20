package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.AuthenticationRequestDTO;
import com.socialnetwork.identity.dto.request.TokenVerificationRequestDTO;
import com.socialnetwork.identity.dto.response.AuthenticationResponseDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.dto.response.TokenVerificationResponseDTO;
import com.socialnetwork.identity.service.AuthenticationService;
import com.socialnetwork.identity.service.InvalidatedTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    InvalidatedTokenService invalidatedTokenService;

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        return ResponseObject.<AuthenticationResponseDTO>builder()
                .data(authenticationService.login(requestDTO))
                .build();
    }

    @PostMapping(value = "/logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<Void> logout(@RequestHeader("Authorization") String accessToken) throws ParseException {
        invalidatedTokenService.logout(accessToken);
        return ResponseObject.<Void>builder()
                .build();
    }

    @PostMapping(value = "/refresh-token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<AuthenticationResponseDTO> refreshToken(@RequestHeader("Authorization") String accessToken) throws ParseException {
        return ResponseObject.<AuthenticationResponseDTO>builder()
                .data(authenticationService.refreshToken(accessToken))
                .build();
    }

    @PostMapping(value = "/verify-token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseObject<TokenVerificationResponseDTO> verifyToken(@RequestBody TokenVerificationRequestDTO requestDTO) {
        return ResponseObject.<TokenVerificationResponseDTO>builder()
                .data(authenticationService.verifyToken(requestDTO))
                .build();
    }
}
