package com.socialnetwork.identity.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequestDTO {
    String userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String address;
}
