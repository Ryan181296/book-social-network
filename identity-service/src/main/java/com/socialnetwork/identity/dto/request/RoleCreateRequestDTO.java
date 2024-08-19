package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleCreateRequestDTO {
    @NotBlank(message = "ROLE_NAME_INVALID")
    String name;
    String description;
    Set<String> permissionNames;
}
