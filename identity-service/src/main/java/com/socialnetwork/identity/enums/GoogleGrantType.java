package com.socialnetwork.identity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum GoogleGrantType {
    TOKEN("token"),
    AUTHORIZATION_CODE("authorization_code");

    String value;
}
