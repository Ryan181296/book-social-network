package com.socialnetwork.identity.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequestDTO {
    @Size(min = 8, max = 30, message = "PASSWORD_INPUT_INVALID")
    String password;
    String firstName;
    String lastName;
    transient Set<String> roles;
}
