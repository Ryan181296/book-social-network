package com.socialnetwork.profile.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequestDTO {
    String userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String address;
}
