package com.socialnetwork.profile.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
