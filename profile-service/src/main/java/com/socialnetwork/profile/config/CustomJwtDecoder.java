package com.socialnetwork.profile.config;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;

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
