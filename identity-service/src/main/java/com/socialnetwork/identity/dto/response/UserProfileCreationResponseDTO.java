package com.socialnetwork.identity.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationResponseDTO {
    String id;
    String userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String address;
}
