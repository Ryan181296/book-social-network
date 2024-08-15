package com.socialnetwork.profile_service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationResponseDTO {
    String firstName;
    String lastName;
    LocalDate dob;
    String address;
}
