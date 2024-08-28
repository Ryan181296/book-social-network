package com.socialnetwork.identity.service;

import com.socialnetwork.identity.dto.request.AuthenticationRequestDTO;
import com.socialnetwork.identity.dto.request.GoogleAuthenticationRequestDTO;
import com.socialnetwork.identity.dto.request.OutboundAuthenticationRequestDTO;
import com.socialnetwork.identity.dto.request.TokenVerificationRequestDTO;
import com.socialnetwork.identity.dto.response.AuthenticationResponseDTO;
import com.socialnetwork.identity.dto.response.TokenVerificationResponseDTO;
import com.socialnetwork.identity.entity.InvalidatedTokenEntity;
import com.socialnetwork.identity.enums.GoogleGrantType;
import com.socialnetwork.identity.exception.ErrorCode;
import com.socialnetwork.identity.exception.CustomException;
import com.socialnetwork.identity.repository.InvalidatedTokenRepository;
import com.socialnetwork.identity.repository.UserRepository;
import com.socialnetwork.identity.repository.client.GoogleClient;
import com.socialnetwork.identity.util.AuthenticationUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @Autowired
    GoogleClient googleClient;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.secret-key}")
    String secretKey;

    @NonFinal
    @Value("${clients.google.client-secret}")
    String googleClientSecret;

    @NonFinal
    @Value("${clients.google.client-id}")
    String googleClientId;

    static final String ACCESS_TOKEN_TYPE = "Bearer";

    public AuthenticationResponseDTO login(AuthenticationRequestDTO requestDTO) {
        var userEntity = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_OR_PASSWORD_INVALID));

        var passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(requestDTO.getPassword(), userEntity.getPassword())) {
            var accessToken = AuthenticationUtils.generateAccessToken(userEntity, secretKey);
            return AuthenticationResponseDTO.builder()
                    .accessToken(accessToken)
                    .accessTokenType(ACCESS_TOKEN_TYPE)
                    .build();
        } else {
            throw new CustomException(ErrorCode.USERNAME_OR_PASSWORD_INVALID);
        }
    }

    public TokenVerificationResponseDTO verifyToken(TokenVerificationRequestDTO requestDTO) {
        var authenticated = true;
        try {
            verifyToken(requestDTO.getToken());
        } catch (JOSEException | ParseException e) {
            authenticated = false;
        }
        return TokenVerificationResponseDTO.builder()
                .authenticated(authenticated)
                .token(requestDTO.getToken())
                .build();
    }

    public void verifyToken(String token) throws JOSEException, ParseException {
        var signedJWT = SignedJWT.parse(token);

        var verifier = new MACVerifier(secretKey.getBytes());
        var verified = signedJWT.verify(verifier);
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!(verified && expiryTime.after(new Date()))) {
            throw new CustomException(ErrorCode.UNAUTHENTICATED);
        }

        var jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidatedTokenRepository.existsById(jwtId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    public AuthenticationResponseDTO refreshToken(String accessToken) throws ParseException {
        // Save invalidated access token.
        var signedJWT = SignedJWT.parse(accessToken.split(" ")[1]);

        var invalidatedTokenEntity = InvalidatedTokenEntity.builder()
                .id(signedJWT.getJWTClaimsSet().getJWTID())
                .expiryTime(signedJWT.getJWTClaimsSet().getExpirationTime())
                .build();

        invalidatedTokenRepository.save(invalidatedTokenEntity);

        // Generate new access token.
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        var newAccessToken = AuthenticationUtils.generateAccessToken(userEntity, secretKey);
        return AuthenticationResponseDTO.builder()
                .accessToken(newAccessToken)
                .accessTokenType(ACCESS_TOKEN_TYPE)
                .build();
    }

    public AuthenticationResponseDTO loginWithGoogle(OutboundAuthenticationRequestDTO requestDTO) {
        try {
            var response = googleClient.login(GoogleAuthenticationRequestDTO.builder()
                    .code(requestDTO.getAuthorizationCode())
                    .clientId(googleClientId)
                    .clientSecret(googleClientSecret)
                    .grantType(GoogleGrantType.AUTHORIZATION_CODE.getValue())
                    .build());

            return AuthenticationResponseDTO.builder()
                    .accessToken(response.getAccessToken())
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EXCHANGE_GOOGLE_AUTHORIZATION_CODE_ERROR);
        }
    }
}
