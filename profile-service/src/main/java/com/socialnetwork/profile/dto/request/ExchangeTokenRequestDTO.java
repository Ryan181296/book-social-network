package com.socialnetwork.profile.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import feign.form.FormProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExchangeTokenRequestDTO {
    @FormProperty("client_id")
    String clientId;

    @FormProperty("client_secret")
    String clientSecret;

    @FormProperty("grant_type")
    String grantType;

    String scope;
}
