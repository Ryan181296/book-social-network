package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDTO {
    @NotNull
    String username;

    @NotNull
    String password;
}
