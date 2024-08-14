package com.socialnetwork.identity.service;

import com.socialnetwork.identity.entity.InvalidatedTokenEntity;
import com.socialnetwork.identity.repository.InvalidatedTokenRepository;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenService {
    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    public void logout(String accessToken) throws ParseException {
        var signedJWT = SignedJWT.parse(accessToken.split(" ")[1]);

        var invalidatedTokenEntity = InvalidatedTokenEntity.builder()
                .id(signedJWT.getJWTClaimsSet().getJWTID())
                .expiryTime(signedJWT.getJWTClaimsSet().getExpirationTime())
                .build();

        invalidatedTokenRepository.save(invalidatedTokenEntity);
    }
}
