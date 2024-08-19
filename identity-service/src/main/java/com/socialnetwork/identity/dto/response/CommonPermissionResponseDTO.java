package com.socialnetwork.identity.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonPermissionResponseDTO {
    String name;
    String description;
}
