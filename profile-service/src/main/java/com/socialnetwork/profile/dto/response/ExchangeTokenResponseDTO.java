package com.socialnetwork.profile.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExchangeTokenResponseDTO {
    String accessToken;
    Long expiresIn;
    Long refreshExpiresIn;
    String tokenType;
    String idToken;

    @SerializedName("not-before-policy")
    Integer notBeforePolicy;
    String scope;

}
