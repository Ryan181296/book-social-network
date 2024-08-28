package com.socialnetwork.identity.dto.request;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoogleAuthenticationRequestDTO {
    @SerializedName("client_id")
    String clientId;

    @SerializedName("client_secret")
    String clientSecret;

    String code;

    @SerializedName("grant_type")
    String grantType;

    @SerializedName("redirect_uri")
    String redirectUri;
}
