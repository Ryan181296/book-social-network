package com.socialnetwork.identity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonUserResponseDTO {
    String id;
    String firstName;
    String lastName;
    String username;
    Set<CommonRoleResponseDTO> roles;
}
