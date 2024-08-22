package com.socialnetwork.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.socialnetwork.identity.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequestDTO {
    @Size(min = 8, max = 16, message = "USERNAME_INPUT_INVALID")
    String username;

    @Size(min = 8, max = 30, message = "PASSWORD_INPUT_INVALID")
    String password;

    @NotBlank(message = "FIRST_NAME_INVALID")
    String firstName;

    @NotBlank(message = "LAST_NAME_INVALID")
    String lastName;

    @DobConstraint(min = 18, message = "BIRTHDAY_INVALID")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;

    Set<String> roleNames;
}
