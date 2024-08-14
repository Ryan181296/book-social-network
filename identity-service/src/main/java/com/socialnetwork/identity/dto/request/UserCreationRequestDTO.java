package com.socialnetwork.identity.dto.request;

import com.socialnetwork.identity.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequestDTO {
    @Size(min = 8, max = 16, message = "USERNAME_INPUT_INVALID")
    String username;

    @Size(min = 8, max = 30, message = "PASSWORD_INPUT_INVALID")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "BIRTHDAY_INVALID")
    LocalDate dob;

    transient Set<String> roles;
}
