package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequestDTO {
    @NotBlank(message = "FIRST_NAME_INVALID")
    String firstName;

    @NotBlank(message = "LAST_NAME_INVALID")
    String lastName;
    Set<String> roleNames;
}
