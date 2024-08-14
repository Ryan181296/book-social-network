package com.socialnetwork.identity.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleCreateRequestDTO {
    String name;
    String description;
    transient Set<String> permissions;
}
