package com.socialnetwork.identity.controller;

import com.socialnetwork.identity.dto.request.AuthenticationRequestDTO;
import com.socialnetwork.identity.dto.response.AuthenticationResponseDTO;
import com.socialnetwork.identity.dto.response.ResponseObject;
import com.socialnetwork.identity.service.AuthenticationService;
import com.socialnetwork.identity.service.InvalidatedTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    InvalidatedTokenService invalidatedTokenService;

    @PostMapping("/login")
    public ResponseObject<AuthenticationResponseDTO> getToken(@RequestBody AuthenticationRequestDTO requestDTO) {
        return ResponseObject.<AuthenticationResponseDTO>builder()
                .data(authenticationService.getToken(requestDTO))
                .build();
    }

    @PostMapping("/logout")
    public ResponseObject<Void> logout(@RequestHeader("Authorization") String accessToken) throws ParseException {
        invalidatedTokenService.logout(accessToken);
        return ResponseObject.<Void>builder()
                .build();
    }

    @PostMapping("/refreshToken")
    public ResponseObject<AuthenticationResponseDTO> refreshToken(@RequestHeader("Authorization") String accessToken) throws ParseException {
        return ResponseObject.<AuthenticationResponseDTO>builder()
                .data(authenticationService.refreshToken(accessToken))
                .build();
    }
}
