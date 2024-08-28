package com.socialnetwork.identity.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoogleAuthenticationResponseDTO {
    @SerializedName("access_token")
    String accessToken;

    @SerializedName("expires_in")
    Long expiresIn;

    @SerializedName("token_type")
    String tokenType;

    String scope;

    @SerializedName("refresh_token")
    String refreshToken;
}
