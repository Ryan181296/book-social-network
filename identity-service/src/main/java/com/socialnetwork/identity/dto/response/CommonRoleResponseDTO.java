package com.socialnetwork.identity.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonRoleResponseDTO {
    String name;
    String description;
    Set<CommonPermissionResponseDTO> permissions;
}
