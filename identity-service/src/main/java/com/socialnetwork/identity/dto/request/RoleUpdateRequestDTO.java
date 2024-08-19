package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleUpdateRequestDTO {
    @NotBlank(message = "ROLE_NAME_INVALID")
    String name;
    String description;
}
