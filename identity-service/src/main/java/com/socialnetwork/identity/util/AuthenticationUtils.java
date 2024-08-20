package com.socialnetwork.identity.util;

import com.socialnetwork.identity.entity.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
public class AuthenticationUtils {
    public static String generateAccessToken(UserEntity userEntity, String secretKey) {
        var header = new JWSHeader(JWSAlgorithm.HS512);
        var jwtClaimSet = new JWTClaimsSet.Builder()
                .subject(userEntity.getUsername())
                .claim("scope", buildScope(userEntity))
                .jwtID(UUID.randomUUID().toString())
                .issuer("messenger.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();

        var payload = new Payload(jwtClaimSet.toJSONObject());
        var jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.info("Cannot generate access token {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static String buildScope(UserEntity userEntity) {
        var stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            userEntity.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }
}
