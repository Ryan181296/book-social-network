package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDTO {
    @NotBlank(message = "USERNAME_OR_PASSWORD_INVALID")
    String username;

    @NotBlank(message = "USERNAME_OR_PASSWORD_INVALID")
    String password;
}
