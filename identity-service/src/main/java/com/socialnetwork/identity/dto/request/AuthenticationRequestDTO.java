package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDTO {
    @NotBlank(message = "USERNAME_OR_PASSWORD_INVALID")
    String username;

    @NotBlank(message = "USERNAME_OR_PASSWORD_INVALID")
    String password;
}
