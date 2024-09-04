package com.socialnetwork.profile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequestDTO {
    String username;
    boolean enabled;
    String email;
    String firstName;
    String lastName;
    List<Credential> credentials;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Credential {
        String type;
        String value;
        boolean temporary;
    }
}
