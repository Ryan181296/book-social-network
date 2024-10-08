package com.socialnetwork.identity.config;

import com.nimbusds.jwt.SignedJWT;
import com.socialnetwork.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.socialnetwork.identity.util.AuthenticationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var signedJWT = SignedJWT.parse(token);
            var jwtClaimSet = signedJWT.getJWTClaimsSet();

            return new Jwt(token,
                    jwtClaimSet.getIssueTime().toInstant(),
                    jwtClaimSet.getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    jwtClaimSet.getClaims());
        } catch (ParseException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
