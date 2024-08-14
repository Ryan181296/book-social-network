package com.socialnetwork.identity.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonRoleResponseDTO {
    String name;
    String description;
    Set<CommonPermissionResponseDTO> permissions;
}
