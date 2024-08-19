package com.socialnetwork.identity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponseDTO {
    String accessTokenType;
    String accessToken;
}
