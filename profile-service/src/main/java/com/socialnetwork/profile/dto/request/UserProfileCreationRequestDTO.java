package com.socialnetwork.profile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequestDTO {
    String userId;
    String username;
    String firstName;
    String lastName;
    String password;
    Date dob;
    String email;
    String address;
}
