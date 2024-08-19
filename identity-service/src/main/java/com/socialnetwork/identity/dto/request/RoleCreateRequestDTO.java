package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleCreateRequestDTO {
    @NotNull
    String name;
    String description;
    transient Set<String> permissions;
}
