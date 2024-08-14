package com.socialnetwork.identity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonUserResponseDTO {
    String id;
    String firstName;
    String lastName;
    String username;
    Set<CommonRoleResponseDTO> roles;
}
